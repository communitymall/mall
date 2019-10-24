var util = require('../../utils/util.js');
var api = require('../../config/api.js');

var app = getApp();

Page({
  data: {
    checkedGoodsList: [],
    checkedAddress: {},
    availableCouponLength: 0, // 可用的优惠券数量
    goodsTotalPrice: 0.00, //商品总价
    freightPrice: 0.00, //快递费
    couponPrice: 0.00, //优惠券的价格
    grouponPrice: 0.00, //团购优惠价格
    orderTotalPrice: 0.00, //订单总价
    actualPrice: 0.00, //实际需要支付的总价
    cartId: 0,
    addressId: 0,
    couponId: 0,
    message: '',
    grouponLinkId: 0, //参与的团购，如果是发起则为0
    grouponRulesId: 0, //团购规则ID

    storeId: 0,
    merchantInfo: {},
    merchantConsigneeInfo: {
      name: '',
      mobile: '',
    },
    payType: 0,
    checked: 0,
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
  },

  //获取checkou信息
  getCheckoutInfo: function() {
    let that = this;
    util.request(api.CartCheckout, {
      cartId: that.data.cartId,
      addressId: that.data.addressId,
      couponId: that.data.couponId,
      grouponRulesId: that.data.grouponRulesId,

    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          checkedGoodsList: res.data.checkedGoodsList,
          checkedAddress: res.data.checkedAddress,
          availableCouponLength: res.data.availableCouponLength,
          actualPrice: res.data.actualPrice,
          couponPrice: res.data.couponPrice,
          grouponPrice: res.data.grouponPrice,
          freightPrice: res.data.freightPrice,
          goodsTotalPrice: res.data.goodsTotalPrice,
          orderTotalPrice: res.data.orderTotalPrice,
          addressId: res.data.addressId,
          couponId: res.data.couponId,
          grouponRulesId: res.data.grouponRulesId,
        });
      }
      wx.hideLoading();
    });
  },

  //获取收货门店的信息
  getMerchantInfo(options) {
    let checked = this.data.checked;
    let that = this;
    let merchantConsigneeInfo = this.data.merchantConsigneeInfo;

    var storeId = wx.getStorageSync('storeId');
    util.request(api.MerchantDetail, {
      storeId: storeId
    }, 'POST').then(function(res) {
      if (res.errno === 0) {
        that.setData({
          merchantInfo: res.data,
        });
        if (res.data.consigneeId != -2) {
          util.request(api.MerchantFindUser, {
            id: res.data.consigneeId
          }, 'POST').then(function(res1) {
            if (res1.errno === 0) {
              that.setData({
                merchantConsigneeInfo: res1.data,
                checked: 1,
              });
            }
          });
        } else {
          that.setData({
            checked: 0,
          });
        }
      }
    });
  },
  selectAddress() {
    wx.navigateTo({
      url: '/pages/ucenter/selectMerchant/selectMerchant',
    })
  },


  selectCoupon() {
    wx.navigateTo({
      url: '/pages/ucenter/couponSelect/couponSelect',
    })
  },
  selectPayType() {
    wx.navigateTo({
      url: '/pages/ucenter/payTypeSelect/payTypeSelect',
    })
  },

  bindMessageInput: function(e) {
    this.setData({
      message: e.detail.value
    });
  },
  onReady: function() {
    // 页面渲染完成

  },
  onShow: function() {

    this.getMerchantInfo();


    // 页面显示
    wx.showLoading({
      title: '加载中...',
    });
    try {
      var cartId = wx.getStorageSync('cartId');
      if (cartId === "") {
        cartId = 0;
      }
      var addressId = wx.getStorageSync('addressId');
      if (addressId === "") {
        addressId = 0;
      }
      var couponId = wx.getStorageSync('couponId');
      if (couponId === "") {
        couponId = 0;
      }
      var grouponRulesId = wx.getStorageSync('grouponRulesId');
      if (grouponRulesId === "") {
        grouponRulesId = 0;
      }
      var grouponLinkId = wx.getStorageSync('grouponLinkId');
      if (grouponLinkId === "") {
        grouponLinkId = 0;
      }

      var storeId = wx.getStorageSync('storeId');
      if (storeId === "") {
        storeId = 0;
      }

      var payType = wx.getStorageSync('payType');
      if (payType === "") {
        payType = 0;
      }

      this.setData({
        cartId: cartId,
        addressId: addressId,
        couponId: couponId,
        grouponRulesId: grouponRulesId,
        grouponLinkId: grouponLinkId,

        storeId: storeId,
        payType: payType,
      });

    } catch (e) {
      // Do something when catch error
      console.log(e);
    }

    this.getCheckoutInfo();
  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  },
  submitOrder: function() {
    if (this.data.storeId <= 0) {
      util.showErrorToast('请选择收货门店');
      return false;
    }
    util.request(api.OrderSubmit, {
      payType: this.data.payType,
      storeId: this.data.storeId,
      cartId: this.data.cartId,
      addressId: this.data.addressId,
      couponId: this.data.couponId,
      message: this.data.message,
      grouponRulesId: this.data.grouponRulesId,
      grouponLinkId: this.data.grouponLinkId
    }, 'POST').then(res => {

      if (res.errno === 0) {
        // 下单成功，重置couponId
        try {
          wx.setStorageSync('couponId', 0);
        } catch (error) {

        }

        if (this.data.payType == 2) { //货到付款的订单，直接返回，不进行支付
          wx.redirectTo({
            url: '/pages/payResult/payResult?payType=2&orderId=' + orderId
          });
          return;
        };

        const orderId = res.data.orderId;
        util.request(api.OrderPrepay, {
          orderId: orderId
        }, 'POST').then(function(res) {
          if (res.errno === 0) {
            const payParam = res.data;
            console.log("支付过程开始");
            wx.requestPayment({
              'timeStamp': payParam.timeStamp,
              'nonceStr': payParam.nonceStr,
              'package': payParam.packageValue,
              'signType': payParam.signType,
              'paySign': payParam.paySign,
              'success': function(res) {
                console.log("支付过程成功");
                wx.redirectTo({
                  url: '/pages/payResult/payResult?status=1&orderId=' + orderId
                });
              },
              'fail': function(res) {
                console.log("支付过程失败");
                wx.redirectTo({
                  url: '/pages/payResult/payResult?status=0&orderId=' + orderId
                });
              },
              'complete': function(res) {
                console.log("支付过程结束")
              }
            });
          } else {
            wx.redirectTo({
              url: '/pages/payResult/payResult?status=0&orderId=' + orderId
            });
          }
        });

      } else {
        wx.redirectTo({
          url: '/pages/payResult/payResult?status=0&orderId=' + orderId
        });
      }
    });
  }
});