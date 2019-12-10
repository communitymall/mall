var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var area = require('../../../utils/area.js');

var app = getApp();
Page({
  data: {
    // merchantUser: {
    //   roleType: '',
    // },
    merchantUserInfo: {},
    roleType: '',
    merchantLeader:'',
  },
  bindinputMerchantUserName(event) {
    let merchantUserInfo = this.data.merchantUserInfo;
    merchantUserInfo.name = event.detail.value;
    this.setData({
      merchantUserInfo: merchantUserInfo
    });
  },
  bindinputMerchantRoleType(event) {
    let roleType = this.data.roleType;
    roleType = event.detail.value;
    this.setData({
      roleType: roleType
    });
  },
  bindinputMerchantUserMobile(event) {
    let merchantUserInfo = this.data.merchantUserInfo;
    merchantUserInfo.mobile = event.detail.value;
    this.setData({
      merchantUserInfo: merchantUserInfo
    });
  },
  onLoad: function() {

  },
  onReady: function() {

  },

  cancelAddress() {
    wx.navigateBack();
  },

  saveMerchantUser(options) { //保存门店店员信息
    let merchantUserInfo = this.data.merchantUserInfo;
    let roleType = this.data.roleType;
    if (merchantUserInfo.name == '') {
      util.showErrorToast('请输入名称');
      return false;
    }
    let that = this;
    util.request(api.MerchantUserUpdate, {
      usId: this.options.uId,
      name: merchantUserInfo.name,
      roleType: roleType,
      mobile: merchantUserInfo.mobile,
    }, 'POST').then(function(res) {
      if (res.errno === 0) {
        // var pages = getCurrentPages();
        // var prevPage = pages[pages.length - 3];
        // console.log(prevPage);
        // if (prevPage.route == "pages/checkout/checkout") {
        //   prevPage.setData({
        //   })
        //   try {
        //     wx.setStorageSync('merchant', res.data);
        //   } catch (e) {
        //   }
        //   console.log("set merchant");
        // }
        
        wx.navigateBack();       
      }
    });
  },

  findMerchantUser() {
    let that = this;
    util.request(api.MerchantFindUser, {
      id: this.options.uId,
    }, 'POST').then(function(res) {
      if (res.errno === 0) {
        that.setData({
          merchantUserInfo: res.data
        })
      }
    });
  },
  setRoleType(options) {
    let roleType = this.data.roleType;
    let merchantUer = this.data.merchantUer;
    let that = this;
    console.log(this.options.roleType)
    if (this.options.roleType==3){
      that.setData({
        merchantLeader: this.options.merchantLeader,
      });
    }
    that.setData({
      roleType: this.options.roleType,
    });
  },

  setConsignee(options){//设置默认收货人
    util.request(api.MerchantSetConsignee,{
      userId: this.options.uId,
      storeId: this.options.storeId,
      roleType: this.data.roleType,
    },'POST').then(function(res){
      if(res.errno==0){
        wx.showToast({
          title: '操作成功！',
          icon: 'success',
          duration: 2000
        })  
      }
    })
  },

  onShow: function() {
    this.findMerchantUser();
    this.setRoleType();
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  }
})