(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7064e24d"],{"2ed4":function(t,e,n){"use strict";var i=n("c31d"),a=n("d282"),r=n("a142"),o=n("ad06"),c=n("6f2f"),s=n("48f4"),u=n("9884"),f=Object(a["a"])("tabbar-item"),h=f[0],d=f[1];e["a"]=h({mixins:[Object(u["a"])("vanTabbar")],props:Object(i["a"])({},s["c"],{dot:Boolean,icon:String,name:[Number,String],info:[Number,String]}),data:function(){return{active:!1}},computed:{routeActive:function(){var t=this.to,e=this.$route;if(t&&e){var n=Object(r["c"])(t)?t.path:t;return e.path===n}}},methods:{onClick:function(t){this.parent.onChange(this.name||this.index),this.$emit("click",t),Object(s["b"])(this.$router,this)}},render:function(){var t=arguments[0],e=this.icon,n=this.slots,i=this.parent.route?this.routeActive:this.active,a=this.parent[i?"activeColor":"inactiveColor"];return t("div",{class:d({active:i}),style:{color:a},on:{click:this.onClick}},[t("div",{class:d("icon",{dot:this.dot})},[n("icon",{active:i})||e&&t(o["a"],{attrs:{name:e}}),t(c["a"],{attrs:{info:this.info}})]),t("div",{class:d("text")},[n("default",{active:i})])])}})},"454f":function(t,e,n){n("46a7");var i=n("584a").Object;t.exports=function(t,e,n){return i.defineProperty(t,e,n)}},"46a7":function(t,e,n){var i=n("63b6");i(i.S+i.F*!n("8e60"),"Object",{defineProperty:n("d9f6").f})},"537a":function(t,e,n){"use strict";n("68ef"),n("9312")},"6b48":function(t,e,n){"use strict";n.r(e);var i,a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("van-tabbar",{staticStyle:{"z-index":"1999"},model:{value:t.active,callback:function(e){t.active=e},expression:"active"}},t._l(t.tabbar,function(e,i){return n("van-tabbar-item",{key:i,attrs:{icon:e.icon,to:e.path,dot:e.dot,info:e.info}},[t._v("\n\t\t"+t._s(e.name)+"\n\t")])}),1)},r=[],o=n("bd86"),c=(n("a52c"),n("2ed4")),s=(n("537a"),n("ac28")),u=(n("ac6a"),n("7f7f"),{data:function(){return{active:0,tabbar:[{name:"精选",path:"/",pathName:"home",icon:"compass-full",dot:!1,info:""},{name:"分类",path:"/items",pathName:"class",icon:"class-full",dot:!1,info:""},{name:"购物车",path:"/order",pathName:"cart",icon:"cart-full",dot:!1,info:""},{name:"我的",path:"/user",pathName:"user",icon:"wode",dot:!1,info:""}]}},watch:{$route:"changeActive"},created:function(){var t=this.$route.name;this.setActive(t)},methods:{changeActive:function(t){var e=t.name;this.setActive(e)},setActive:function(t){var e=this;this.tabbar.forEach(function(n,i){if(n.pathName==t)return e.active=i,!1})}},components:(i={},Object(o["a"])(i,s["a"].name,s["a"]),Object(o["a"])(i,c["a"].name,c["a"]),i)}),f=u,h=n("2877"),d=Object(h["a"])(f,a,r,!1,null,null,null);e["default"]=d.exports},"85f2":function(t,e,n){t.exports=n("454f")},9312:function(t,e,n){},9884:function(t,e,n){"use strict";n.d(e,"a",function(){return r}),n.d(e,"b",function(){return o});var i=n("2b0e");function a(t){var e=[];function n(t){t.forEach(function(t){e.push(t),t.children&&n(t.children)})}return n(t),e}function r(t,e){var n,r;void 0===e&&(e={});var o=e.indexKey||"index";return i["a"].extend({inject:(n={},n[t]={default:null},n),computed:(r={parent:function(){return this[t]}},r[o]=function(){return this.bindRelation(),this.parent.children.indexOf(this)},r),mounted:function(){this.bindRelation()},beforeDestroy:function(){var t=this;this.parent&&(this.parent.children=this.parent.children.filter(function(e){return e!==t}))},methods:{bindRelation:function(){if(this.parent&&-1===this.parent.children.indexOf(this)){var t=[].concat(this.parent.children,[this]),e=a(this.parent.slots());t.sort(function(t,n){return e.indexOf(t.$vnode)-e.indexOf(n.$vnode)}),this.parent.children=t}}}})}function o(t){return{provide:function(){var e;return e={},e[t]=this,e},data:function(){return{children:[]}}}}},a52c:function(t,e,n){"use strict";n("68ef"),n("09fe"),n("ae73")},ac28:function(t,e,n){"use strict";var i=n("d282"),a=n("9884"),r=Object(i["a"])("tabbar"),o=r[0],c=r[1];e["a"]=o({mixins:[Object(a["b"])("vanTabbar")],props:{route:Boolean,activeColor:String,inactiveColor:String,safeAreaInsetBottom:Boolean,value:{type:[Number,String],default:0},border:{type:Boolean,default:!0},fixed:{type:Boolean,default:!0},zIndex:{type:Number,default:1}},watch:{children:function(){this.setActiveItem()},value:function(){this.setActiveItem()}},methods:{setActiveItem:function(){var t=this;this.children.forEach(function(e,n){e.active=(e.name||n)===t.value})},onChange:function(t){t!==this.value&&(this.$emit("input",t),this.$emit("change",t))}},render:function(){var t=arguments[0];return t("div",{style:{zIndex:this.zIndex},class:[{"van-hairline--top-bottom":this.border},c({fixed:this.fixed,"safe-area-inset-bottom":this.safeAreaInsetBottom})]},[this.slots()])}})},ae73:function(t,e,n){},bd86:function(t,e,n){"use strict";n.d(e,"a",function(){return r});var i=n("85f2"),a=n.n(i);function r(t,e,n){return e in t?a()(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}}}]);