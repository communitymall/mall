var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({
  data: {
    merchantList: [],
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
    this.getMerchantList();
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


  merchantDetail(event) {
    console.log(event)
    //返回之前，先取出上一页对象，并设置storeId
    // var pages = getCurrentPages();
    // var prevPage = pages[pages.length - 2];

    if (true) {
      try {
        wx.setStorageSync('merchantId', event.currentTarget.dataset.merchantId);
      } catch (e) {
      }
      let merchantId = event.currentTarget.dataset.merchantId;
      wx.navigateTo({
        url: '/pages/ucenter/merchantDetail/merchantDetail?id=' + merchantId
      })
    }
  },

  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})