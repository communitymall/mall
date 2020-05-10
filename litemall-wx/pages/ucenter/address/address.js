var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({
  data: {
    addressList: [],
    total: 0,
    merchantList:[],
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    // 页面显示
    this.getAddressList();

    this.getMerchantList();    
  },
  getAddressList() {
    let that = this;
    util.request(api.AddressList).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          addressList: res.data.list,
          total: res.data.total
        });
      }
    });
  },

  getMerchantList(){//获得门店的列表
    let that = this;
    util.request(api.MerchantList, {}, 'POST').then(function(res){
      if(res.errno === 0){
        that.setData({
          merchantList: res.data.list,
          total : res.data.total
        });
      }
    });
  },

  addressAddOrUpdate(event) {
    console.log(event)

    //返回之前，先取出上一页对象，并设置addressId
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];

    if (prevPage.route == "pages/checkout/checkout") {
      try {
        wx.setStorageSync('addressId', event.currentTarget.dataset.addressId);
      } catch (e) {

      }

      let addressId = event.currentTarget.dataset.addressId;
      if (addressId && addressId != 0) {
        wx.navigateBack();
      } else {
        wx.navigateTo({
          url: '/pages/ucenter/addressAdd/addressAdd?id=' + addressId
        })
      }

    } else {
      wx.navigateTo({
        url: '/pages/ucenter/addressAdd/addressAdd?id=' + event.currentTarget.dataset.addressId
      })
    }
  },

  merchantDetail(event) {
    console.log(event)
    //返回之前，先取出上一页对象，并设置storeId
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];

    if (true) {
      try {
        wx.setStorageSync('addressId', event.currentTarget.dataset.addressId);
      } catch (e) {

      }

      let addressId = event.currentTarget.dataset.addressId;
     
      wx.navigateTo({
        url: '/pages/ucenter/merchantDetail/merchantDetail?id=' + addressId
      })

      if (addressId && addressId != 0) {
        wx.navigateBack();
      } else {
        wx.navigateTo({
          url: '/pages/ucenter/merchantDetail/merchantDetail?id=' + addressId
        })
      }

    } else {
      wx.navigateTo({
        url: '/pages/ucenter/merchantDetail/merchantDetail?id=' + event.currentTarget.dataset.addressId
      })
    }
  },


  deleteAddress(event) {
    console.log(event.target)
    let that = this;
    wx.showModal({
      title: '',
      content: '确定要删除地址？',
      success: function(res) {
        if (res.confirm) {
          let addressId = event.target.dataset.addressId;
          util.request(api.AddressDelete, {
            id: addressId
          }, 'POST').then(function(res) {
            if (res.errno === 0) {
              that.getAddressList();
              wx.removeStorage({
                key: 'addressId',
                success: function(res) {},
              })
            }
          });
          console.log('用户点击确定')
        }
      }
    })
    return false;

  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  }
})