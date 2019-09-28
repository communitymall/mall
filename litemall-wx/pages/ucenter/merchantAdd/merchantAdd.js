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
    merchant.merchantPhone = event.detail.value;
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

    if (merchant.merchantName == '') {
      util.showErrorToast('请输入门店名称');
      return false;
    }

    if (merchant.merchantPhone == '') {
      util.showErrorToast('请输入门店电话');
      return false;
    }

    if (merchant.merchantLeader == '') {
      util.showErrorToast('请输入门店负责人');
      return false;
    }
    util.request(api.MerchantCreate, {
      merchantName: merchant.merchantName,
      merchantCode: merchant.merchantCode,
      merchantAddress: merchant.merchantAddress,
      merchantPic: merchant.merchantPic,
      merchantPhone: merchant.merchantPhone,
      merchantLeader: merchant.merchantLeader,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        //返回之前，先取出上一页对象，并设置addressId
        var pages = getCurrentPages();
        var prevPage = pages[pages.length - 1];
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

  switchTab: function (event) {
    let showType = event.currentTarget.dataset.index;
    this.setData({

      showType: showType,
    });
    if (showType == 0) {
      this.getMerchantDetail();
    } else {
      this.getMerchantUser();
    }
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