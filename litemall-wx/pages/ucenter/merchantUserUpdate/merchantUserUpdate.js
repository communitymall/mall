var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var area = require('../../../utils/area.js');

var app = getApp();
Page({
  data: {
    merchantUser: {
      usId: 0,
      name: '',
      roleType: '',
    },
  },
  bindinputMerchantUserName(event) {
    let merchantUser = this.data.merchantUser;
    merchantUser.name = event.detail.value;
    this.setData({
      merchantUser: merchantUser
    });
  },
  bindinputMerchantRoleType(event) {
    let merchantUser = this.data.merchantUser;
    merchantUser.roleType = event.detail.value;
    this.setData({
      merchantUser: merchantUser
    });
  },
  bindinputMerchantAddress(event) {
    let merchant = this.data.merchant;
    merchant.merchantAddress = event.detail.value;
    this.setData({
      merchant: merchant
    });
  },
  

  onLoad: function () {

  },
  onReady: function () {

  },

  cancelAddress() {
    wx.navigateBack();
  },

  saveMerchantUser(options) {//保存门店店员信息
    let merchantUser = this.data.merchantUser;
    if (merchantUser.name == '') {
      util.showErrorToast('请输入名称');
      return false;
    }

    let that = this;
    util.request(api.MerchantUserUpdate, {
      usId: this.options.uId,
      name: merchantUser.name,
      roleType: merchantUser.roleType,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        //返回之前，先取出上一页对象，并设置addressId
        var pages = getCurrentPages();
        var prevPage = pages[pages.length - 2];
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