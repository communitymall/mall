var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({
  data: {
    userList: [],
    showType: 0,
    page: 1,
    limit: 10,
    totalPages: 1,
    storeId: '',
    merchantInfo : {},
    merchantLeaderInfo: {},
  },
  onLoad: function (options) {
  },
  getMerchantDetail(options) {
    let that = this;
    util.request(api.MerchantDetail, {
      storeId: this.options.id
    },'POST').then(function (res) {
      if (res.errno === 0) {
        //console.log(res.data);
        that.setData({
          merchantInfo: res.data,
        });
      }
    });
  },

  getMerchantUser(options) {
    let that = this;
    util.request(api.MerchantUser, {
      storeId: this.options.id
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        //console.log(res.data);
        that.setData({
          userList: res.data,
        });
      }
    });
  },

  findMerchantLeader(options){
    let that = this;
    util.request(api.MerchantFindLeader, {
      storeId: this.options.id
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        //console.log(res.data);
        that.setData({
          merchantLeaderInfo: res.data,
        });
      }
    });
  },

  switchTab: function (event) {
    let showType = event.currentTarget.dataset.index;
    this.setData({
      
      showType: showType,
    });
    if ( showType==0){
      this.getMerchantDetail();
    }else{
      this.getMerchantUser();
    }  
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
    this.getMerchantDetail();
    this.findMerchantLeader();
    this.onLoad();
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})