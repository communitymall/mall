(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3d9d2675"],{"1c7c":function(t,e,a){"use strict";a.r(e);var n,r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"order_list"},[a("van-nav-bar",{attrs:{title:"门店详情","left-text":"返回","left-arrow":""},on:{"click-left":t.goback}}),a("van-tabs",{attrs:{"swipe-threshold":5},on:{click:t.handleTabClick},model:{value:t.activeIndex,callback:function(e){t.activeIndex=e},expression:"activeIndex"}},t._l(t.tabTitles,function(e,n){return a("van-tab",{key:n,attrs:{title:e}},[0===t.activeIndex?a("van-cell-group",[a("van-cell",{attrs:{title:"商户名称",value:t.merchantName,isLink:""},on:{click:t.onSetManchantName}}),a("van-cell",{attrs:{title:"商户地址",value:t.merchantAddress,isLink:""},on:{click:t.onSetManchantAddress}}),a("van-cell",{attrs:{title:"商户电话",value:t.merchantPhone,isLink:""},on:{click:t.onSetManchantPhone}}),a("van-cell",{attrs:{title:"商户负责人",value:t.merchantLeader,isLink:""},on:{click:t.onSetManchantLeader}}),a("van-cell",{attrs:{title:"商户状态",isLink:""},on:{click:t.onSetManchantAudit}},[a("span",[t._v("  "+t._s(t._f("detailMerchantStatusFilter")(t.merchantStatus)))])]),a("van-cell",{staticClass:"cell_middle",attrs:{title:"门店照片"}})],1):t._e(),0===t.activeIndex?a("img",{attrs:{src:t.pic,width:"100%"}}):t._e(),1===t.activeIndex?a("van-cell-group",[t._l(t.merchantList,function(e,n){return a("div",{key:n,staticClass:"card-goods__item"},[3===e.roleType?a("van-cell",{attrs:{value:"编辑","is-link":""},on:{click:function(a){return t.onEdit(e,n)}}},[a("template",{slot:"title"},[a("span",{staticClass:"custom-title"},[t._v(t._s(t.merchantLeader))]),a("van-tag",{attrs:{type:"danger"}},[t._v(t._s(t._f("detailStatusFilter")(e.roleType)))]),a("br"),a("span",[t._v(t._s(t.merchantPhone))])],1)],2):t._e(),3!==e.roleType?a("van-cell",{attrs:{value:"编辑","is-link":""},on:{click:function(a){return t.onEdit(e,n)}}},[a("template",{slot:"title"},[a("span",{staticClass:"custom-title"},[t._v(t._s(e.name))]),a("van-tag",{attrs:{type:"danger"}},[t._v(t._s(t._f("detailStatusFilter")(e.roleType)))]),a("br"),a("span",[t._v(t._s(e.mobile))])],1)],2):t._e()],1)}),a("div",{attrs:{align:"center"}},[a("van-button",{attrs:{type:"primary",size:"small"},on:{click:t.addMerchantUser}},[t._v("添加人员")])],1)],2):t._e()],1)}),1)],1)},i=[],s=a("a506"),c=(a("e4b3"),a("bff1")),d=(a("9cd4"),a("7cfc")),o=(a("a541"),a("2ab2")),h=(a("d0bb"),a("5b9b")),u=(a("bd8f"),a("69a2")),l=(a("1def"),a("35b1")),m=(a("7364"),a("ae9e"),a("003e")),v=(a("d4d5"),a("813c"),a("85a0")),p=(a("a710"),a("49b7")),f=a("4ec3"),b=a("6e6d");b["a"].use(p["a"],v["a"]);var I={0:"管理员",1:"厨师",2:"店员",3:"负责人"},y={0:"审核中",1:"审核未通过",2:"审核通过"},_={filters:{detailStatusFilter:function(t){return I[t]},detailMerchantStatusFilter:function(t){return y[t]}},name:"merchant-detail",props:{active:{type:[String,Number],default:0}},created:function(){this.init(),this.findStrorUser()},data:function(){return{activeIndex:Number(this.active),tabTitles:["门店信息","人员信息"],merchantList:[],page:0,limit:10,loading:!1,finished:!1,shipData:{storeId:""},leaderData:{id:"",merchantLeader:"",merchantPhone:""},merchantName:"",merchantCode:"",merchantAddress:"",merchantPic:"",merchantPhone:"",merchantLeader:"",merchantStatus:"",avatar:"",usId:"",uId:"",roleType:"",mobile:"",storeId:"",pic:""}},methods:{afterRead:function(t){var e=this,a=this.$route.query.storeId,n=t.file,r=this.merchantPic,i=new FormData;i.append("imagefile",n),i.append("storeId",a),i.append("merchantPic",r),Object(f["U"])(i).then(function(t){e.merchantPic=t.data.data,e.pic=t.data.data+"?"+Math.random()})},init:function(){this.page=0,this.getMerchantList()},onSetManchantName:function(){var t=this.$route.query.storeId;this.$router.push({name:"merchant-detail-setMerchantName",query:{storeId:t}})},onSetManchantAddress:function(){var t=this.$route.query.storeId;this.$router.push({name:"merchant-detail-setMerchantAddress",query:{storeId:t}})},onSetManchantLeader:function(){var t=this.$route.query.storeId;this.$router.push({name:"merchant-detail-setMerchantLeader",query:{storeId:t}})},onSetManchantPhone:function(){var t=this.$route.query.storeId;this.$router.push({name:"merchant-detail-setMerchantPhone",query:{storeId:t}})},onSetManchantCode:function(){var t=this.$route.query.storeId;this.$router.push({name:"merchant-detail-setMerchantCode",query:{storeId:t}})},onSetManchantAudit:function(){var t=this.$route.query.storeId;this.$router.push({name:"merchant-audit",query:{storeId:t}})},onEdit:function(t,e){this.$router.push({name:"merchant-detail-setMerchantUser",query:{userId:t.uId,storeId:t.storeId,roleType:t.roleType}})},onEditLeader:function(){this.$router.push({name:"merchantLeader-detail",query:{id:this.leaderData.id}})},addMerchantUser:function(){var t=this.$route.query.storeId;this.$router.push({name:"merchant-detail-addMerchantUser",query:{storeId:t}})},goback:function(){this.$router.go(-1)},handleTabClick:function(){1===this.activeIndex&&(this.findStrorUser(),this.findLeader())},findStrorUser:function(){var t=this;this.shipData.storeId=this.$route.query.storeId,Object(f["O"])(this.shipData).then(function(e){var a=e.data.data;t.merchantList=e.data.data;for(var n=0;n<a.length;n++)a[n]})},findLeader:function(){var t=this;this.shipData.storeId=this.$route.query.storeId,Object(f["G"])(this.shipData).then(function(e){t.leaderData.merchantLeader=e.data.data.merchantLeader,t.leaderData.merchantPhone=e.data.data.merchantPhone,t.leaderData.id=e.data.data.id})},getMerchantList:function(){var t=this,e=this.$route.query.storeId;this.shipData.storeId=e,Object(f["S"])(this.shipData).then(function(e){t.merchantName=e.data.data.merchantName,t.merchantAddress=e.data.data.merchantAddress,t.merchantPic=e.data.data.merchantPic,t.merchantCode=e.data.data.merchantCode,t.merchantPhone=e.data.data.merchantPhone,t.merchantLeader=e.data.data.merchantLeader,t.merchantStatus=e.data.data.merchantStatus,t.pic=e.data.data.merchantPic+"?"+Math.random()}),this.page++}},components:(n={},Object(s["a"])(n,m["a"].name,m["a"]),Object(s["a"])(n,l["a"].name,l["a"]),Object(s["a"])(n,u["a"].name,u["a"]),Object(s["a"])(n,h["a"].name,h["a"]),Object(s["a"])(n,o["a"].name,o["a"]),Object(s["a"])(n,d["a"].name,d["a"]),Object(s["a"])(n,c["a"].name,c["a"]),n)},k=_,M=(a("ef20"),a("6691")),L=Object(M["a"])(k,r,i,!1,null,"4ac46bc2",null);e["default"]=L.exports},"8bed":function(t,e,a){},ef20:function(t,e,a){"use strict";var n=a("8bed"),r=a.n(n);r.a}}]);