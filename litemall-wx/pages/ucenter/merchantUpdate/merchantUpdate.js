var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var area = require('../../../utils/area.js');

var app = getApp();
Page({
  data: {
    merchant :{
      id: 0,
      merchantName:'',
      merchantCode:'',
      merchantAddress:'',
      merchantPic:'',
      merchantPhone:'',
      merchantLeader:'',
    },
    storeId: 0,
    merchantInfo :{},
    node : '',
    merchantPic:'',
  },
  bindinputMerchantLeader(event) {
    let merchant = this.data.merchant;
    merchant.merchantLeader = event.detail.value;
    this.setData({
      merchant: merchant
    });
  },
  bindinputMerchantName(event) {
    let merchant = this.data.merchant;
    merchant.merchantName = event.detail.value;
    this.setData({
      merchant: merchant
    });
  },
  bindinputMerchantAddress(event) {
    let merchant = this.data.merchant;
    merchant.merchantAddress = event.detail.value;
    this.setData({
      merchant: merchant
    });
  },
  bindinputMerchantPhone(event) {
    let merchant = this.data.merchant;
    let node = this.data.node;
    var phone = event.detail.value;
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
    if (!myreg.test(phone)) {
      util.showErrorToast('手机号有误！');
      this.setData({
        node: false
      });
    } else {
      this.setData({
        node: true
      });
    }
    merchant.merchantPhone = event.detail.value;
    this.setData({
      merchant: merchant
    });
  },
  bindinputMerchantCode(event) {
    let merchant = this.data.merchant;
    merchant.merchantCode = event.detail.value;
    this.setData({
      merchant: merchant
    });
  },

  getMerchantDetail(options) {
    let that = this;
    util.request(api.MerchantDetail, {
      storeId: this.options.id
    },'POST').then(function (res) {
      if (res.errno === 0) {
        if (res.data) {
          that.setData({
            merchant: res.data,
            merchantPic:res.data.merchantPic
          });
        }
      }
    });
  },

  //点击图片选择手机相册或者电脑本地图片
  changeAvatar: function (e) {
    var _this = this
    wx.chooseImage({
      count: 3,// 默认3
      sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        _this.setData({
          merchantPic: tempFilePaths[0],
        })
      
      }
    })
  },

  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log(options)
    if (options.id && options.id != 0) {
      this.setData({
        storeId: options
      });
      this.getMerchantDetail();
    }
  },
  onReady: function () {

  },

  cancelAddress() {
    wx.navigateBack();
  },
  
  saveMerchant(options) {//保存门店信息
    let merchant = this.data.merchant;
    let merchantPic = this.data.merchantPic;

    if (merchant.merchantName == '') {
      util.showErrorToast('请输入门店名称');
      return false;
    }

    if (merchant.merchantPhone == '') {
      util.showErrorToast('请输入门店电话');
      return false;
    }
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
    if (!myreg.test(merchant.merchantPhone)) {
      util.showErrorToast('手机号有误！');
      return false;
    } 

    // if (address.areaCode == 0) {
    //   util.showErrorToast('请输入省市区');
    //   return false;
    // }

    // if (address.addressDetail == '') {
    //   util.showErrorToast('请输入详细地址');
    //   return false;
    // }

    // if (!check.isValidPhone(merchant.merchantPhone)) {
    //   util.showErrorToast('手机号不正确');
    //   return false;
    // }

    let that = this;
    util.request(api.MerchantUpdate, {
      id: this.options.id,
      merchantName: merchant.merchantName,
      merchantCode: merchant.merchantCode,
      merchantAddress: merchant.merchantAddress,
      merchantPic: merchant.merchantPic,
      merchantPhone: merchant.merchantPhone,
      merchantLeader: merchant.merchantLeader,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        console.log("okok" + merchant.id)
        console.log(merchant.merchantPic)
        console.log(merchantPic)
        //这里是上传操作
        wx.uploadFile({
          url: 'http://39.97.235.28:8082/wx/wxImageUpload/upload', //里面填写你的上传图片服务器API接口的路径 
          filePath: merchantPic,//要上传文件资源的路径 String类型 
          name: 'imagefile',//按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数
          header: {
            "Content-Type": "multipart/form-data"//
          },
          formData: {
            //和服务器约定的token, 一般也可以放在header中
            'storeId': merchant.id,
            "merchantPic": merchant.merchantPic,
          },
          success: function (res) {
            //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
            if (res.statusCode === 200) {
              var data = JSON.parse(res.data);
              var picName = data.data
              console.log("picName=" + picName);
              that.setData({
                merchantPic: picName
              })

            }
          }
        })

        //返回之前，先取出上一页对象，并设置addressId
        var pages = getCurrentPages();
        var prevPage = pages[pages.length - 1];
        console.log(prevPage);
        if (prevPage.route == "pages/checkout/checkout") {
          prevPage.setData({
          })
          try {
            wx.setStorageSync('merchant', res.data);
          } catch (e) {
          }
          console.log("set merchant");
        }
         wx.navigateBack();
      }
    });

  },

  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  }
})