var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({
  data: {
    merchant: {
      merchantName: '',
      merchantCode: '',
      merchantAddress: '',
      merchantPic: '',
      merchantPhone: '',
      merchantLeader: '',
    },
    node : false,
    storeId : '',
    picNode : false,
  },
  onLoad: function (optons) {

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
    let node =this.data.node;
    merchant.merchantPhone = event.detail.value;
    var phone = event.detail.value;
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
    if (!myreg.test(phone)) {
      util.showErrorToast('手机号有误！');
      this.setData({
        node: false
      });
    }else{
      this.setData({
        node: true
      });
    }
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
  bindinputMerchantPic(event) {
    let merchant = this.data.merchant;
    merchant.merchantPic = event.detail.value;
    this.setData({
      merchant: merchant
    });
  },
  merchantCreate(options) {
    let merchant = this.data.merchant;

    let merchantPic = this.data.merchantPic;

    let picNode = this.data.picNode;
  
    if (merchant.merchantName == '') {
      util.showErrorToast('请输入门店名称');
      return false;
    }

    if (merchant.merchantPhone == '') {
      util.showErrorToast('请输入门店电话');
      return false;
    }
    let node = this.data.node;
    if (node == false) {
      util.showErrorToast('手机号有误！');
      return false;
    }

    if (merchant.merchantLeader == '') {
      util.showErrorToast('请输入门店负责人');
      return false;
    }
    util.request(api.MerchantCreate, {
      merchantName: merchant.merchantName,
      merchantCode: merchant.merchantCode,
      merchantAddress: merchant.merchantAddress,
      merchantPic: merchant.merchantPic,
      merchantPhone: merchant.merchantPhone,
      merchantLeader: merchant.merchantLeader,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        if(picNode){
          wx.uploadFile({
            url: 'http://39.97.235.28:8082/wx/wxImageUpload/upload', //里面填写你的上传图片服务器API接口的路径 
            filePath: merchantPic,//要上传文件资源的路径 String类型 
            name: 'imagefile',//按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
            header: {
              "Content-Type": "multipart/form-data"//记得设置
            },
            formData: { 'storeId': res.data.storeid }, // HTTP 请求中其他额外的 form data
            success: function (res) {
              //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
              if (res.errno = 0) {
                var data = res.data
             
              }
            }
          })
        }
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
      }else {
        util.showErrorToast('输入信息有误！');
      }
    });
  },

  switchTab: function (event) {
    let showType = event.currentTarget.dataset.index;
    this.setData({

      showType: showType,
    });
    if (showType == 0) {
      this.getMerchantDetail();
    } else {
      this.getMerchantUser();
    }
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
        console.log('开始')
        console.log(tempFilePaths)
        console.log('结束')
        _this.setData({
          merchantPic: tempFilePaths[0],
          picNode: true,
        })
        //这里是上传操作
        // wx.uploadFile({
        //   url: 'http://localhost:8082/wx/wxImageUpload/upload', //里面填写你的上传图片服务器API接口的路径 
        //   filePath: tempFilePaths[0],//要上传文件资源的路径 String类型 
        //   name: 'imagefile',//按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
        //   header: {
        //     "Content-Type": "multipart/form-data"//记得设置
        //   },
        //   formData: { 'storeId': "procomment" }, // HTTP 请求中其他额外的 form data
        //   success: function (res) {
        //     //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
        //     if (res.errno = 0) {
        //        var data = res.data
        //       // var statusCode = res.statusCode
        //       // console.log("返回值1" + data);
        //       // console.log("返回值2" + statusCode)
        //       //这里调用后台的修改操作， tempFilePaths[0],是上面uploadFile上传成功，然后赋值到修改这里。
        //       // wx.request({
        //       //   url: getApp().globalData.clientUrl + '/http://localhost:8082/wx/store/update?avatar=' + tempFilePaths[0], //真正修改操作,填写你们修改的API
        //       //   header: {
        //       //     'content-type': 'application/json',
        //       //   },
        //       //   method: 'POST',
        //       //   success: function (res) {
        //       //     if (res.data.code == 200) {
        //       //       wx.showToast({
        //       //         title: '修改成功',
        //       //         icon: 'success',
        //       //         duration: 2500
        //       //       })
        //       //       //wx.uploadFile自已有一个this，我们刚才上面定义的var _this = this 把this带进来
        //       //       _this.setData({
        //       //         "user.avatar": tempFilePaths[0]
        //       //       });
        //       //     }
        //       //   },
        //       // })
        //     }
        //   }
        // })
      }
    })
  },
  onReady: function () {
    // 页面渲染完成
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