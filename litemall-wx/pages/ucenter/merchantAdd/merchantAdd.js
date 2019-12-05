var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({
  data: {
    merchant: {
      merchantName: '',
      merchantCode: '',
      merchantAddress: '',
      merchantPic: '',
      merchantPhone: '',
      merchantLeader: '',
    },
    node : false,
    storeId : '',
    picNode : false,
  },
  onLoad: function (optons) {

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
    let node =this.data.node;
    merchant.merchantPhone = event.detail.value;
    var phone = event.detail.value;
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
    if (!myreg.test(phone)) {
      util.showErrorToast('手机号有误！');
      this.setData({
        node: false
      });
    }else{
      this.setData({
        node: true
      });
    }
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
  merchantCreate(options) {
    let merchant = this.data.merchant;
    let merchantPic = this.data.merchantPic;
    let picNode = this.data.picNode;
    if (merchant.merchantName == '') {
      util.showErrorToast('请输入门店名称');
      return false;
    }

    if (merchant.merchantPhone == '') {
      util.showErrorToast('请输入门店电话');
      return false;
    }
    let node = this.data.node;
    if (node == false) {
      util.showErrorToast('手机号有误！');
      return false;
    }

    if (merchant.merchantLeader == '') {
      util.showErrorToast('请输入门店负责人');
      return false;
    }
    wx.setStorageSync("merchantName", merchant.merchantName)
    util.request(api.MerchantCreate, {
      merchantName: merchant.merchantName,
      merchantCode: merchant.merchantCode,
      merchantAddress: merchant.merchantAddress,
      merchantPic: merchant.merchantPic,
      merchantPhone: merchant.merchantPhone,
      merchantLeader: merchant.merchantLeader,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.navigateTo({
          url: '/pages/ucenter/merchantAudit/merchantAudit',
        })
        wx.setStorageSync("storeId", res.data.storeid)
  
  // switchTab: function (event) {
  //   let showType = event.currentTarget.dataset.index;
  //   this.setData({

  //     showType: showType,
  //   });
  //   if (showType == 0) {
  //     this.getMerchantDetail();
  //   } else {
  //     this.getMerchantUser();
  //   }
  // },
      }
      if (res.errno === 407) {
        wx.showModal({
          title: '提示',
          content: '门店名称已被注册！',
          success(res) {
            if (res.confirm) {
              console.log('用户点击确定')
            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })
      }
    })
  },
  onReady: function () {
    // 页面渲染完成
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