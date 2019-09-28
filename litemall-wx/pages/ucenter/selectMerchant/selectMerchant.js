var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var area = require('../../../utils/area.js');

var app = getApp();
Page({
  data: {
    merchantList: [],//门店地址列表
  },
  getMerchantList() {//获得门店的列表
    let that = this;
    util.request(api.MerchantList, {}, 'POST').then(function (res) {
      if (res.errno === 0) {
        that.setData({
          merchantList: res.data.list,
          total: res.data.total
        });
      }
    });
  },

  bindinputMerchantRoleType(event) {

  },
  cancelAddress() {
    wx.navigateBack();
  },

  selectMerchant(event) {
    console.log(event)
    //返回之前，先取出上一页对象，并设置storeId
    // var pages = getCurrentPages();
    // var prevPage = pages[pages.length - 2];

    if (true) {
      try {
        wx.setStorageSync('storeId', event.currentTarget.dataset.storeId);
      } catch (e) {
      }
      let storeId = event.currentTarget.dataset.storeId;
      wx.navigateTo({
        url: '/pages/checkout/checkout?storeId=' + storeId
      })
    }
  },
  onLoad: function () {

  },
  onReady: function () {

  },

  onShow: function () {
    // 页面显示
    this.getMerchantList();
  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  }
})