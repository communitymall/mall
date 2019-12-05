var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({
  data: {
    userList: [],
    showType: 0,
    page: 1,
    limit: 10,
    totalPages: 1,
    storeId: '',
    merchantPic:'',
    merchantInfo : {},
    merchantLeaderInfo: {},
    userId:'',
  },
  onLoad: function (options) {
  },
  getMerchantDetail(options) {
    let that = this;
    util.request(api.MerchantDetail, {
      storeId: this.options.id
    },'POST').then(function (res) {
      if (res.errno === 0) {
        that.setData({
          merchantInfo: res.data,
          storeId: res.data.id,
          merchantPic:res.data.merchantPic
        });
        console.log(res.data.merchantName)
       
        wx.setStorageSync("merchantName", res.data.merchantName)
        wx.setStorageSync("storeId", res.data.id)
        wx.setStorageSync("merchantPic", res.data.merchantPic)
        wx.setStorageSync("merchantCodePic", res.data.merchantCodePic)
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

  getMerchantLeader(options) {
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
    let merchantStatus = this.data.merchantInfo.merchantStatus;
    if(merchantStatus==0){  
      util.showErrorToast('门店审核中！');
      return;
    }
    if (merchantStatus == 1) {
      util.showErrorToast('门店审核未通过！');
      return;
    }
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


  //点击图片选择手机相册或者电脑本地图片
  // changeAvatar: function (e) {
  //   var _this = this 
  //   wx.chooseImage({
  //     count: 3,// 默认3
  //     sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
  //     sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
  //     success: function (res) {
  //       // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
  //       var tempFilePaths = res.tempFilePaths;
  //       _this.setData({
  //         merchantPic: tempFilePaths[0],

  //       })
  //       //这里是上传操作
  //       wx.uploadFile({
  //         url:'http://39.97.235.28:8082/wx/wxImageUpload/upload', //里面填写你的上传图片服务器API接口的路径 
  //         filePath: tempFilePaths[0],//要上传文件资源的路径 String类型 
  //         name: 'imagefile',//按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
  //         header: {
  //           "Content-Type": "multipart/form-data"//
  //         },
      
  //         formData: {
  //           //和服务器约定的token, 一般也可以放在header中
  //           'X-Litemall-Token': wx.getStorageSync('X-Litemall-Token'),
  //           'storeId': _this.data.storeId, 
  //           "merchantPic": _this.data.merchantInfo.merchantPic,
  //         },
  //         success: function (res) {   
  //           //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
  //           if (res.statusCode === 200) {
  //             var data = JSON.parse(res.data);
             
  //             var picName= data.data
  //             console.log("picName=" + picName);
  //             _this.setData({
  //               merchantPic: picName
  //             })
            
  //           }
  //         }
  //       })
  //     }
  //   })
  // },

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