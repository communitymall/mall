var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({
  data: {
    node: false,
    storeId: '',
    picNode: false,
    merchantCodePic: '',
    merchantPic: '',
    merchantName: '',
  },
  onLoad: function (optons) {
   
  },

  create(){
    let that = this;
    that.setData({
      merchantName : wx.getStorageSync("merchantName"),
      storeId: wx.getStorageSync("storeId"),
      merchantCodePic: wx.getStorageSync("merchantCodePic"),
      merchantPic: wx.getStorageSync("merchantPic"),
    });
    
  },

  merchantPicUpload(options) {
    let merchantCodePic = this.data.merchantCodePic;
    let merchantPic = this.data.merchantPic;
    let picNode = this.data.picNode;
    let storeId = wx.getStorageSync("storeId");
    if (merchantCodePic == '') {
      util.showErrorToast('请上传门店营业执照！');
      return false;
    }
    if (picNode) {
      wx.uploadFile({
        url: 'http://192.168.1.223:8082/wx/wxImageUpload/businessLicensesPicUpload', //里面填写你的上传图片服务器API接口的路径 
        filePath: merchantCodePic,//要上传文件资源的路径 String类型 
        name: 'imagefile',//按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
        header: {
          "Content-Type": "multipart/form-data"//记得设置
        },
        formData: { 'storeId': storeId }, // HTTP 请求中其他额外的 form data
        success: function (res) {
          //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
          if (res.errno = 0) {
            var data = res.data

          }
        }
      })
    }

    if (picNode) {
      wx.uploadFile({
        url: 'http://localhost:8082/wx/wxImageUpload/upload', //里面填写你的上传图片服务器API接口的路径 
        filePath: merchantPic,//要上传文件资源的路径 String类型 
        name: 'imagefile',//按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
        header: {
          "Content-Type": "multipart/form-data"//记得设置
        },
        formData: { 'storeId': storeId }, // HTTP 请求中其他额外的 form data
        success: function (res) {
          //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
          if (res.errno = 0) {
            var data = res.data
          }
        }
      })
    }

   
    wx.navigateBack({
      delta:2
    })
  },

  //点击图片选择手机相册或者电脑本地图片
  changeAvatar: function (e) {
    let storeId = wx.getStorageSync("storeId");
    var _this = this
    wx.chooseImage({
      count: 3,// 默认3
      sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        console.log('开始')
        console.log(tempFilePaths)
        console.log('结束')
        _this.setData({
          merchantPic: tempFilePaths[0],
          picNode: true,
        })
     
      }
    })
  },

  //点击图片选择手机相册或者电脑本地图片
  changeAvatar2: function (e) {
    let storeId = wx.getStorageSync("storeId");
    var _this = this
    wx.chooseImage({
      count: 3,// 默认3
      sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        console.log('开始')
        console.log(tempFilePaths)
        console.log('结束')
        _this.setData({
          merchantCodePic: tempFilePaths[0],
          picNode: true,
        })
      }
    })
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
    this.create();
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})