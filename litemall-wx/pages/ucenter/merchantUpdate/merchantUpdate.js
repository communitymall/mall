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
  bindinputMerchantPic(event) {
    let merchant = this.data.merchant;
    merchant.merchantPic = event.detail.value;
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
            merchant: res.data
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
    console.log(this.data.merchant)
    let merchant = this.data.merchant;

    if (merchant.merchantName == '') {
      util.showErrorToast('请输入门店名称');
      return false;
    }

    if (merchant.merchantPhone == '') {
      util.showErrorToast('请输入门店电话');
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