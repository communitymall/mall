var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();
Page({
  data: {
    payTypeInfo: [{
        name: '在线支付',
        payType: 1,
      },
      {
        name: '货到付款',
        payType: 2,
      },
    ],
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {},

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },


 
  selectCoupon: function(e) {
    console.log(e);
    try {
      wx.setStorageSync('payType', e.currentTarget.dataset.paytype);
    } catch (error) {
    }
    wx.navigateBack();
  },
})