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
      if (res.errno === 407) {
        wx.showModal({
          title: '提示',
          content: '门店名称已被注册！',
          success(res) {
            if (res.confirm) {
              console.log('用户点击确定')
            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })
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