(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6fab81eb"],{"02de":function(t,n,e){"use strict";var a=e("0ce3"),i=e.n(a);i.a},"0ce3":function(t,n,e){},3116:function(t,n,e){},"35e5":function(t,n,e){"use strict";e("b628"),e("3116")},6209:function(t,n,e){},"6f7b":function(t,n,e){"use strict";var a=e("5f8d"),i=Object(a["a"])("col"),r=i[0],s=i[1];n["a"]=r({props:{span:[Number,String],offset:[Number,String],tag:{type:String,default:"div"}},computed:{gutter:function(){return this.$parent&&Number(this.$parent.gutter)||0},style:function(){var t=this.gutter/2+"px";return this.gutter?{paddingLeft:t,paddingRight:t}:{}}},methods:{onClick:function(t){this.$emit("click",t)}},render:function(){var t,n=arguments[0],e=this.span,a=this.offset;return n(this.tag,{style:this.style,class:s((t={},t[e]=e,t["offset-"+a]=a,t)),on:{click:this.onClick}},[this.slots()])}})},"75d0":function(t,n,e){"use strict";var a=e("6209"),i=e.n(a);i.a},7767:function(t,n,e){"use strict";e.r(n);var a,i,r,s=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"tabbar-user"},[e("user-header",{attrs:{isLogin:t.isLogin}}),e("order-group"),e("coupon-group"),e("user-module")],1)},c=[],o=e("a506"),u=(e("7364"),function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"user_header",style:{backgroundImage:"url("+t.background_image+")"}},[e("van-icon",{staticClass:"user_set",attrs:{name:"set"},on:{click:t.toSetting}}),e("div",{staticClass:"user_avatar"},[e("img",{attrs:{src:t.avatar,alt:"头像",width:"55",height:"55"}})]),e("div",[t._v(t._s(t.nickName))])],1)}),l=[],d=e("9c85"),f=e.n(d),v=e("ac93"),h=e.n(v),g=e("3c96"),p=e("4ec3"),m={name:"user-header",props:{isLogin:{type:Boolean,default:!1}},data:function(){return{nickName:"昵称",avatar:f.a,background_image:h.a}},activated:function(){this.getUserInfo()},methods:{getUserInfo:function(){var t=this;Object(p["h"])().then(function(n){t.avatar=n.data.data.avatar,t.nickName=n.data.data.nickName});var n=Object(g["a"])("nickName","avatar");this.avatar=n.avatar||f.a,this.nickName=n.nickName||"昵称"},toSetting:function(){this.$router.push({name:"user-information"})}}},b=m,_=(e("75d0"),e("6691")),k=Object(_["a"])(b,u,l,!1,null,"0fd35653",null),j=k.exports,O=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",[e("van-cell-group",[e("van-cell",{attrs:{title:"我的订单",isLink:""}},[e("router-link",{staticClass:"text-desc",attrs:{to:"/user/order/list/0"}},[t._v("全部订单")])],1)],1),e("van-row",{staticClass:"order_status"},[e("van-col",{attrs:{span:"6"}},[e("div",{staticClass:"order_status_icon",on:{click:function(n){return t.$router.push({path:"/user/order/list/1"})}}},[e("van-icon",{attrs:{name:"daifukuan",info:t.order.unpaid>0?t.order.unpaid:""}})],1),e("div",[t._v("待付款")])]),e("van-col",{attrs:{span:"6"}},[e("div",{staticClass:"order_status_icon",on:{click:function(n){return t.$router.push({path:"/user/order/list/2"})}}},[e("van-icon",{attrs:{name:"daifahuo",info:t.order.unship>0?t.order.unship:""}})],1),e("div",[t._v("待发货")])]),e("van-col",{attrs:{span:"6"}},[e("div",{staticClass:"order_status_icon",on:{click:function(n){return t.$router.push({path:"/user/order/list/3"})}}},[e("van-icon",{attrs:{name:"wuliu",info:t.order.unrecv>0?t.order.unrecv:""}})],1),e("div",[t._v("待收货")])]),e("van-col",{attrs:{span:"6"}},[e("div",{staticClass:"order_status_icon",on:{click:function(n){return t.$router.push({path:"/user/order/list/4"})}}},[e("van-icon",{attrs:{name:"shouhouguanli",info:t.order.uncomment>0?t.order.uncomment:""}})],1),e("div",[t._v("已完成")])])],1)],1)},y=[],C=(e("35e5"),e("6f7b")),$=(e("aa27"),e("b377")),L={name:"order-group",data:function(){return{order:[]}},created:function(){this.init()},methods:{init:function(){var t=this;Object(p["kb"])().then(function(n){t.order=n.data.data.order})}},components:(a={},Object(o["a"])(a,$["a"].name,$["a"]),Object(o["a"])(a,C["a"].name,C["a"]),a)},S=L,w=(e("b3cd"),Object(_["a"])(S,O,y,!1,null,"30310566",null)),x=w.exports,N=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div")},E=[],I={name:"coupon-group",components:(i={},Object(o["a"])(i,$["a"].name,$["a"]),Object(o["a"])(i,C["a"].name,C["a"]),i)},J=I,R=(e("ad7c"),Object(_["a"])(J,N,E,!1,null,"3a4ff652",null)),U=R.exports,z=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"user_module"},[e("van-cell-group",[e("van-cell",{attrs:{icon:"shoucang",title:"我的收藏",to:"/user/collect",isLink:""}}),e("van-cell",{attrs:{icon:"dingwei",title:"门店管理",to:"/user/merchant",isLink:""}}),e("van-cell",{attrs:{icon:"kefu",title:"服务中心",to:"/user/server",isLink:""}})],1)],1)},A=[],B={name:"user-module"},P=B,q=(e("02de"),Object(_["a"])(P,z,A,!1,null,"47aa3d11",null)),D=q.exports,F={data:function(){return{isLogin:!1}},activated:function(){this.getLoginStatus()},methods:{getLoginStatus:function(){this.isLogin=!!localStorage.getItem("Authorization")}},components:(r={},Object(o["a"])(r,j.name,j),Object(o["a"])(r,x.name,x),Object(o["a"])(r,U.name,U),Object(o["a"])(r,D.name,D),r)},G=F,H=(e("e490"),Object(_["a"])(G,s,c,!1,null,"265cd08c",null));n["default"]=H.exports},9741:function(t,n,e){},"9c85":function(t,n,e){t.exports=e.p+"static/img/avatar_default.89845f4e.png"},a506:function(t,n,e){"use strict";function a(t,n,e){return n in t?Object.defineProperty(t,n,{value:e,enumerable:!0,configurable:!0,writable:!0}):t[n]=e,t}e.d(n,"a",function(){return a})},aa27:function(t,n,e){"use strict";e("b628"),e("c7c8")},ac93:function(t,n,e){t.exports=e.p+"static/img/user_head_bg.38dfa566.png"},ad7c:function(t,n,e){"use strict";var a=e("9741"),i=e.n(a);i.a},b377:function(t,n,e){"use strict";var a=e("5f8d"),i=Object(a["a"])("row"),r=i[0],s=i[1];n["a"]=r({props:{type:String,align:String,justify:String,tag:{type:String,default:"div"},gutter:{type:[Number,String],default:0}},methods:{onClick:function(t){this.$emit("click",t)}},render:function(){var t,n=arguments[0],e=this.align,a=this.justify,i="flex"===this.type,r="-"+Number(this.gutter)/2+"px",c=this.gutter?{marginLeft:r,marginRight:r}:{};return n(this.tag,{style:c,class:s((t={flex:i},t["align-"+e]=i&&e,t["justify-"+a]=i&&a,t)),on:{click:this.onClick}},[this.slots()])}})},b3cd:function(t,n,e){"use strict";var a=e("d059"),i=e.n(a);i.a},bf2e:function(t,n,e){},c7c8:function(t,n,e){},d059:function(t,n,e){},e490:function(t,n,e){"use strict";var a=e("bf2e"),i=e.n(a);i.a}}]);