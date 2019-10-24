var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var area = require('../../../utils/area.js');

var app = getApp();
Page({
  data: {
    merchantLeader: {
      merchantLeader:'',
      merchantPhone:'',
    },
    merchantLeaderInfo: {},
    id: '',
  },
  bindinputMerchantLeaderName(event) {
    let merchantLeaderInfo = this.data.merchantLeaderInfo;
    merchantLeaderInfo.merchantLeader = event.detail.value;
    this.setData({
      merchantLeaderInfo: merchantLeaderInfo
    });
  },

  bindinputMerchantLeaderMobile(event) {
    let merchantLeaderInfo = this.data.merchantLeaderInfo;
    merchantLeaderInfo.merchantPhone = event.detail.value;
    this.setData({
      merchantLeaderInfo: merchantLeaderInfo
    });
  },
  onLoad: function () {

  },
  onReady: function () {

  },

  cancelAddress() {
    wx.navigateBack();
  },

  saveMerchantLeader(options) { //保存信息
    let merchantLeaderInfo = this.data.merchantLeaderInfo;
    if (merchantLeaderInfo.merchantLeader == '') {
      util.showErrorToast('请输入名称');
      return false;
    }
    let that = this;
    util.request(api.MerchantUpdate, {
      id: this.options.storeId,
      merchantLeader: merchantLeaderInfo.merchantLeader,
      merchantPhone: merchantLeaderInfo.merchantPhone,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        var pages = getCurrentPages();
        var prevPage = pages[pages.length - 3];
        console.log(prevPage);
        if (prevPage.route == "pages/checkout/checkout") {
          prevPage.setData({
          })
          try {
            wx.setStorageSync('merchantLeader', res.data);
          } catch (e) {
          }
          console.log("set merchantLeader");
        }
        wx.navigateBack();
      }
    });
  },

  findMerchantLeader() {
    let that = this;
    util.request(api.MerchantFindLeader, {
      storeId: this.options.storeId,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        that.setData({
          merchantLeaderInfo: res.data
        })
      }
    });
  },


  setConsignee(options) {//设置默认收货人
    util.request(api.MerchantSetConsignee, {
      userId: -2,
      storeId: this.options.storeId
    }, 'POST').then(function (res) {
      if (res.errno == 0) {
        wx.showToast({
          title: '操作成功！',
          icon: 'success',
          duration: 2000
        })
      }
    })
  },

  onShow: function () {
    this.findMerchantLeader();
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  }
})