(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5cd3e6a0"],{"0a26":function(t,e,i){"use strict";i.d(e,"a",function(){return s});var n=i("ad06"),a=i("9884"),r=i("ea8e"),s=function(t){var e=t.parent,i=t.bem,s=t.role;return{mixins:[Object(a["a"])(e)],props:{name:null,value:null,disabled:Boolean,iconSize:[Number,String],checkedColor:String,labelPosition:String,labelDisabled:Boolean,shape:{type:String,default:"round"}},computed:{isDisabled:function(){return this.parent&&this.parent.disabled||this.disabled},iconStyle:function(){var t=this.checkedColor;if(t&&this.checked&&!this.isDisabled)return{borderColor:t,backgroundColor:t}},tabindex:function(){return this.isDisabled||"radio"===s&&!this.checked?-1:0}},methods:{onClick:function(t){var e=this.$refs.label,i=t.target,n=e&&(e===i||e.contains(i));this.disabled||n&&this.labelDisabled||this.toggle(),this.$emit("click",t)}},render:function(){var t=arguments[0],e=this.slots,a=this.checked,o=e("icon",{checked:a})||t(n["a"],{attrs:{name:"success"},style:this.iconStyle}),c=e()&&t("span",{ref:"label",class:i("label",[this.labelPosition,{disabled:this.isDisabled}])},[e()]),l=[t("div",{class:i("icon",[this.shape,{disabled:this.isDisabled,checked:a}]),style:{fontSize:Object(r["a"])(this.iconSize)}},[o])];return"left"===this.labelPosition?l.unshift(c):l.push(c),t("div",{attrs:{role:s,tabindex:this.tabindex,"aria-checked":String(this.checked)},class:i(),on:{click:this.onClick}},[l])}}}},1146:function(t,e,i){},"309f":function(t,e,i){},"32d7":function(t,e,i){"use strict";i.d(e,"a",function(){return a}),i.d(e,"b",function(){return r});var n=i("a142");function a(){return!n["d"]&&/android/.test(navigator.userAgent.toLowerCase())}function r(){return!n["d"]&&/ios|iphone|ipad|ipod/.test(navigator.userAgent.toLowerCase())}},"454f":function(t,e,i){i("46a7");var n=i("584a").Object;t.exports=function(t,e,i){return n.defineProperty(t,e,i)}},"46a7":function(t,e,i){var n=i("63b6");n(n.S+n.F*!i("8e60"),"Object",{defineProperty:i("d9f6").f})},"4ddd":function(t,e,i){"use strict";i("68ef"),i("09fe"),i("dde9")},5246:function(t,e,i){"use strict";i("68ef"),i("09fe"),i("8a0b")},"565f":function(t,e,i){"use strict";var n=i("2638"),a=i.n(n),r=i("c31d"),s=i("ad06"),o=i("7744"),c=i("dfaf"),l=i("1325"),u=i("a8fa"),h=i("d282"),d=i("a142"),f=i("ea8e"),b=Object(h["a"])("field"),p=b[0],v=b[1];e["a"]=p({inheritAttrs:!1,props:Object(r["a"])({},c["a"],{error:Boolean,readonly:Boolean,autosize:[Boolean,Object],leftIcon:String,rightIcon:String,clearable:Boolean,labelClass:null,labelWidth:[Number,String],labelAlign:String,inputAlign:String,errorMessage:String,errorMessageAlign:String,type:{type:String,default:"text"}}),data:function(){return{focused:!1}},watch:{value:function(){this.$nextTick(this.adjustSize)}},mounted:function(){this.format(),this.$nextTick(this.adjustSize)},computed:{showClear:function(){return this.clearable&&this.focused&&""!==this.value&&Object(d["b"])(this.value)&&!this.readonly},listeners:function(){var t=Object(r["a"])({},this.$listeners,{input:this.onInput,keypress:this.onKeypress,focus:this.onFocus,blur:this.onBlur});return delete t.click,t},labelStyle:function(){var t=this.labelWidth;if(t)return{width:Object(f["a"])(t)}}},methods:{focus:function(){this.$refs.input&&this.$refs.input.focus()},blur:function(){this.$refs.input&&this.$refs.input.blur()},format:function(t){if(void 0===t&&(t=this.$refs.input),t){var e=t,i=e.value,n=this.$attrs.maxlength;return"number"===this.type&&Object(d["b"])(n)&&i.length>n&&(i=i.slice(0,n),t.value=i),i}},onInput:function(t){t.target.composing||this.$emit("input",this.format(t.target))},onFocus:function(t){this.focused=!0,this.$emit("focus",t),this.readonly&&this.blur()},onBlur:function(t){this.focused=!1,this.$emit("blur",t),Object(u["a"])()},onClick:function(t){this.$emit("click",t)},onClickLeftIcon:function(t){this.$emit("click-left-icon",t)},onClickRightIcon:function(t){this.$emit("click-right-icon",t)},onClear:function(t){Object(l["c"])(t),this.$emit("input",""),this.$emit("clear",t)},onKeypress:function(t){if("number"===this.type){var e=t.keyCode,i=-1===String(this.value).indexOf("."),n=e>=48&&e<=57||46===e&&i||45===e;n||Object(l["c"])(t)}"search"===this.type&&13===t.keyCode&&this.blur(),this.$emit("keypress",t)},adjustSize:function(){var t=this.$refs.input;if("textarea"===this.type&&this.autosize&&t){t.style.height="auto";var e=t.scrollHeight;if(Object(d["c"])(this.autosize)){var i=this.autosize,n=i.maxHeight,a=i.minHeight;n&&(e=Math.min(e,n)),a&&(e=Math.max(e,a))}e&&(t.style.height=e+"px")}},renderInput:function(){var t=this.$createElement,e=this.slots("input");if(e)return t("div",{class:v("control",this.inputAlign)},[e]);var i={ref:"input",class:v("control",this.inputAlign),domProps:{value:this.value},attrs:Object(r["a"])({},this.$attrs,{readonly:this.readonly}),on:this.listeners,directives:[{name:"model",value:this.value}]};return"textarea"===this.type?t("textarea",a()([{},i])):t("input",a()([{attrs:{type:this.type}},i]))},renderLeftIcon:function(){var t=this.$createElement,e=this.slots("left-icon")||this.leftIcon;if(e)return t("div",{class:v("left-icon"),on:{click:this.onClickLeftIcon}},[this.slots("left-icon")||t(s["a"],{attrs:{name:this.leftIcon}})])},renderRightIcon:function(){var t=this.$createElement,e=this.slots,i=e("right-icon")||this.rightIcon;if(i)return t("div",{class:v("right-icon"),on:{click:this.onClickRightIcon}},[e("right-icon")||t(s["a"],{attrs:{name:this.rightIcon}})])}},render:function(){var t,e=arguments[0],i=this.slots,n=this.labelAlign,a={icon:this.renderLeftIcon};return i("label")&&(a.title=function(){return i("label")}),e(o["a"],{attrs:{icon:this.leftIcon,size:this.size,title:this.label,center:this.center,border:this.border,isLink:this.isLink,required:this.required,clickable:this.clickable,titleStyle:this.labelStyle,titleClass:[v("label",n),this.labelClass],arrowDirection:this.arrowDirection},class:v((t={error:this.error,disabled:this.$attrs.disabled},t["label-"+n]=n,t["min-height"]="textarea"===this.type&&!this.autosize,t)),scopedSlots:a,on:{click:this.onClick}},[e("div",{class:v("body")},[this.renderInput(),this.showClear&&e(s["a"],{attrs:{name:"clear"},class:v("clear"),on:{touchstart:this.onClear}}),this.renderRightIcon(),i("button")&&e("div",{class:v("button")},[i("button")])]),this.errorMessage&&e("div",{class:v("error-message",this.errorMessageAlign)},[this.errorMessage])])}})},"5dfd":function(t,e,i){"use strict";i.r(e);var n,a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"set_nickname"},[i("van-nav-bar",{attrs:{title:"修改店员信息","left-text":"返回","left-arrow":""},on:{"click-left":t.goback}}),i("van-cell-group",[3!==t.shipData.roleType?i("van-field",{attrs:{label:"店员姓名",placeholder:"请输入店员姓名"},model:{value:t.userData.name,callback:function(e){t.$set(t.userData,"name",e)},expression:"userData.name"}}):t._e(),3!==t.shipData.roleType?i("van-field",{attrs:{label:"店员手机",placeholder:"请输入店员手机"},model:{value:t.userData.mobile,callback:function(e){t.$set(t.userData,"mobile",e)},expression:"userData.mobile"}}):t._e(),3==t.shipData.roleType?i("van-field",{attrs:{label:"负责人姓名",disabled:"disabled",placeholder:"请输入负责人姓名"},model:{value:t.merchantInfo.merchantLeader,callback:function(e){t.$set(t.merchantInfo,"merchantLeader",e)},expression:"merchantInfo.merchantLeader"}}):t._e(),3==t.shipData.roleType?i("van-field",{attrs:{label:"负责人手机",disabled:"disabled",placeholder:"请输入负责人手机"},model:{value:t.merchantInfo.merchantPhone,callback:function(e){t.$set(t.merchantInfo,"merchantPhone",e)},expression:"merchantInfo.merchantPhone"}}):t._e(),3!==t.shipData.roleType?i("van-cell-group",[i("van-radio-group",{model:{value:t.shipData.roleType,callback:function(e){t.$set(t.shipData,"roleType",e)},expression:"shipData.roleType"}},[i("van-cell-group",[i("van-cell",{attrs:{title:"管理员",clickable:""},on:{click:function(e){t.shipData.roleType="0"}}},[i("van-radio",{attrs:{slot:"right-icon",name:"0"},slot:"right-icon"})],1),i("van-cell",{attrs:{title:"厨师",clickable:""},on:{click:function(e){t.shipData.roleType="1"}}},[i("van-radio",{attrs:{slot:"right-icon",name:"1"},slot:"right-icon"})],1),i("van-cell",{attrs:{title:"店员",clickable:""},on:{click:function(e){t.shipData.roleType="2"}}},[i("van-radio",{attrs:{slot:"right-icon",name:"2"},slot:"right-icon"})],1)],1)],1)],1):t._e()],1),i("div",{staticClass:"bottom_btn"},[i("van-button",{attrs:{size:"large",type:"danger"},on:{click:t.setConsignee}},[t._v("设置为默认收货人")])],1),i("div",{staticClass:"bottom_btn"},[i("van-button",{attrs:{size:"large",type:"danger"},on:{click:t.saveMerchantUser}},[t._v("保存")])],1)],1)},r=[],s=i("bd86"),o=(i("5246"),i("6b41")),c=(i("be7f"),i("565f")),l=(i("7f7f"),i("c194"),i("7744")),u=(i("0653"),i("34e9")),h=(i("4ddd"),i("9f14")),d=(i("a44c"),i("e27c")),f=i("4ec3"),b=i("2b0e");b["a"].use(d["a"]),b["a"].use(h["a"]),b["a"].use(l["a"]).use(u["a"]);var p={data:function(){return{name:"",roleType:"",shipData:{usId:"",name:"",roleType:"",mobile:""},userData:{id:"",mobile:"",name:""},consigneeData:{userId:"",storeId:"",roleType:""},merchantInfo:{}}},init:function(){this.findMerchantUser()},created:function(){this.findMerchantUser()},methods:{saveMerchantUser:function(){var t=this,e=this.$route.query.userId;if(this.shipData.usId=e,this.shipData.name=this.userData.name,!/^1[34578]\d{9}$/.test(this.userData.mobile))return alert("电话号码格式错误"),!1;this.shipData.mobile=this.userData.mobile,Object(f["kb"])(this.shipData).then(function(e){return t.$dialog.alert({message:"保存成功"})}).then(function(){t.$router.go(-1)}).catch(function(e){return t.$dialog.alert({message:"参数错误！"})})},findMerchantUser:function(){var t=this,e=this.$route.query.userId,i=this.$route.query.roleType;if(3===i){var n=this.$route.query.storeId;this.consigneeData.storeId=n,Object(f["H"])(this.consigneeData).then(function(e){t.merchantInfo=e.data.data})}this.shipData.roleType=i,this.userData.id=e,Object(f["I"])(this.userData).then(function(e){t.userData=e.data.data}).then(function(){})},setConsignee:function(){var t=this,e=this.$route.query.userId,i=this.$route.query.storeId,n=this.$route.query.roleType;this.consigneeData.storeId=i,this.consigneeData.userId=e,this.consigneeData.roleType=n,Object(f["gb"])(this.consigneeData).then(function(e){return t.$dialog.alert({message:"已经设为默认收货人！"})}).then(function(){})},goback:function(){this.$router.go(-1)}},components:(n={},Object(s["a"])(n,c["a"].name,c["a"]),Object(s["a"])(n,o["a"].name,o["a"]),n)},v=p,g=(i("c5e3"),i("2877")),m=Object(g["a"])(v,a,r,!1,null,"0edd2dde",null);e["default"]=m.exports},"6b41":function(t,e,i){"use strict";var n=i("2638"),a=i.n(n),r=i("d282"),s=i("a142"),o=i("ba31"),c=i("ad06"),l=Object(r["a"])("nav-bar"),u=l[0],h=l[1];function d(t,e,i,n){return t("div",a()([{class:[h({fixed:e.fixed}),{"van-hairline--bottom":e.border}],style:{zIndex:e.zIndex}},Object(o["b"])(n)]),[t("div",{class:h("left"),on:{click:n.listeners["click-left"]||s["e"]}},[i.left?i.left():[e.leftArrow&&t(c["a"],{class:h("arrow"),attrs:{name:"arrow-left"}}),e.leftText&&t("span",{class:h("text")},[e.leftText])]]),t("div",{class:[h("title"),"van-ellipsis"]},[i.title?i.title():e.title]),t("div",{class:h("right"),on:{click:n.listeners["click-right"]||s["e"]}},[i.right?i.right():e.rightText&&t("span",{class:h("text")},[e.rightText])])])}d.props={title:String,fixed:Boolean,leftText:String,rightText:String,leftArrow:Boolean,border:{type:Boolean,default:!0},zIndex:{type:Number,default:1}},e["a"]=u(d)},"85f2":function(t,e,i){t.exports=i("454f")},"8a0b":function(t,e,i){},9884:function(t,e,i){"use strict";i.d(e,"a",function(){return r}),i.d(e,"b",function(){return s});var n=i("2b0e");function a(t){var e=[];function i(t){t.forEach(function(t){e.push(t),t.children&&i(t.children)})}return i(t),e}function r(t,e){var i,r;void 0===e&&(e={});var s=e.indexKey||"index";return n["a"].extend({inject:(i={},i[t]={default:null},i),computed:(r={parent:function(){return this[t]}},r[s]=function(){return this.bindRelation(),this.parent.children.indexOf(this)},r),mounted:function(){this.bindRelation()},beforeDestroy:function(){var t=this;this.parent&&(this.parent.children=this.parent.children.filter(function(e){return e!==t}))},methods:{bindRelation:function(){if(this.parent&&-1===this.parent.children.indexOf(this)){var t=[].concat(this.parent.children,[this]),e=a(this.parent.slots());t.sort(function(t,i){return e.indexOf(t.$vnode)-e.indexOf(i.$vnode)}),this.parent.children=t}}}})}function s(t){return{provide:function(){var e;return e={},e[t]=this,e},data:function(){return{children:[]}}}}},"9f14":function(t,e,i){"use strict";var n=i("d282"),a=i("0a26"),r=Object(n["a"])("radio"),s=r[0],o=r[1];e["a"]=s({mixins:[Object(a["a"])({bem:o,role:"radio",parent:"vanRadio"})],computed:{currentValue:{get:function(){return this.parent?this.parent.value:this.value},set:function(t){(this.parent||this).$emit("input",t)}},checked:function(){return this.currentValue===this.name}},methods:{toggle:function(){this.currentValue=this.name}}})},a44c:function(t,e,i){"use strict";i("68ef")},a8fa:function(t,e,i){"use strict";i.d(e,"a",function(){return s});var n=i("32d7"),a=i("a8c1"),r=Object(n["b"])();function s(){r&&Object(a["e"])(Object(a["b"])())}},bd86:function(t,e,i){"use strict";i.d(e,"a",function(){return r});var n=i("85f2"),a=i.n(n);function r(t,e,i){return e in t?a()(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}},be7f:function(t,e,i){"use strict";i("68ef"),i("09fe"),i("1146")},c5e3:function(t,e,i){"use strict";var n=i("309f"),a=i.n(n);a.a},dde9:function(t,e,i){},e27c:function(t,e,i){"use strict";var n=i("d282"),a=i("9884"),r=Object(n["a"])("radio-group"),s=r[0],o=r[1];e["a"]=s({mixins:[Object(a["b"])("vanRadio")],props:{value:null,disabled:Boolean},watch:{value:function(t){this.$emit("change",t)}},render:function(){var t=arguments[0];return t("div",{class:o(),attrs:{role:"radiogroup"}},[this.slots()])}})}}]);