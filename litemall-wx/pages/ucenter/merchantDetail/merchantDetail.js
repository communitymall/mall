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
    merchant: {
      merchantPic :undefined,
    },
    merchantPic:'',
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

          storeId: res.data.id,
          merchantPic:res.data.merchantPic
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


  //点击图片选择手机相册或者电脑本地图片
  changeAvatar: function (e) {
    var _this = this
    console.log(this.data.merchant.merchantPic)
    wx.chooseImage({
      count: 5,// 默认9
      sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        console.log('开始')
        console.log(tempFilePaths)
        console.log('结束')

        _this.setData({
          merchantPic : tempFilePaths
        })

        //需要上传到数据库
        util.request(api.MerchantUpdate, {
          id: _this.data.storeId,
          merchantPic: tempFilePaths[0]
        }, 'POST').then(function (res) {
          if (res.errno === 0) {
            console.log(res.data);
            _this.setData({
              merchantPic: tempFilePaths[0]
            })
          }
        });

        //这里是上传操作
        wx.uploadFile({
          url: getApp().globalData.clientUrl + 'http://localhost:8082/wx/store/update', //里面填写你的上传图片服务器API接口的路径 
          filePath: tempFilePaths[0],//要上传文件资源的路径 String类型 
          name: 'avatar',//按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
          header: {
            "Content-Type": "multipart/form-data"//记得设置
          },
          formData: {
            //和服务器约定的token, 一般也可以放在header中
            'X-Litemall-Token': wx.getStorageSync('X-Litemall-Token')
          },
          success: function (res) {
            //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
            if (res.statusCode = 200) {
              // var data = res.data
              // var statusCode = res.statusCode
              // console.log("返回值1" + data);
              // console.log("返回值2" + statusCode)
              //这里调用后台的修改操作， tempFilePaths[0],是上面uploadFile上传成功，然后赋值到修改这里。
              wx.request({
                url: getApp().globalData.clientUrl + '/http://localhost:8082/wx/store/update?avatar=' + tempFilePaths[0], //真正修改操作,填写你们修改的API
                header: {
                  'content-type': 'application/json',
                },
                method: 'POST',
                success: function (res) {
                  if (res.data.code == 200) {
                    wx.showToast({
                      title: '修改成功',
                      icon: 'success',
                      duration: 2500
                    })

                    //wx.uploadFile自已有一个this，我们刚才上面定义的var _this = this 把this带进来
                    _this.setData({
                      "user.avatar": tempFilePaths[0]
                    });
                  }
                },
              })
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