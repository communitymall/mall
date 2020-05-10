var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({
  data: {
    merchantUser: {
      storeId:0,
      name: '',
      mobile: '',
      roleType: '',
    },
  },
  onLoad: function (optons) {
  },
  bindinputMerchantUserMobile(event) {
    let merchantUser = this.data.merchantUser;
    merchantUser.mobile = event.detail.value;
    this.setData({
      merchantUser: merchantUser
    });
  },
  bindinputMerchantUserName(event) {
    let merchantUser = this.data.merchantUser;
    merchantUser.name = event.detail.value;
    this.setData({
      merchantUser: merchantUser
    });
  },
  bindinputMerchantUserRoleType(event) {
    let merchantUser = this.data.merchantUser;
    merchantUser.roleType = event.detail.value;
    this.setData({
      merchantUser: merchantUser
    });
  },
  
  merchantAddUser(options) {
    console.log(options.storeId);
    let merchantUser = this.data.merchantUser;

    if (merchantUser.name == '') {
      util.showErrorToast('请输入店员名称');
      return false;
    }

    if (merchantUser.mobile == '') {
      util.showErrorToast('请输入店员电话');
      return false;
    }

    if (merchantUser.roleType == '') {
      util.showErrorToast('请输入店员角色');
      return false;
    }
    util.request(api.MerchantAddUser, {
      name: merchantUser.name,
      mobile: merchantUser.mobile,
      roleType: merchantUser.roleType,
      storeId: this.options.storeId
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
            wx.setStorageSync('merchantUser', res.data);
          } catch (e) {

          }
          console.log("set merchantUser");
        }
        wx.navigateBack();
      }
    });
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