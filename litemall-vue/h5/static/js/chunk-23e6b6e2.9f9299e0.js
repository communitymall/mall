(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-23e6b6e2"],{"0d9c":function(t,e,i){"use strict";i("b628"),i("6025"),i("1468")},1468:function(t,e,i){},1781:function(t,e,i){"use strict";i.d(e,"a",function(){return a});var n=i("bdc9"),s=i("5ca3"),r=i("ad9f"),a=function(t){var e=t.parent,i=t.bem,a=t.role;return{mixins:[Object(s["a"])(e)],props:{name:null,value:null,disabled:Boolean,iconSize:[Number,String],checkedColor:String,labelPosition:String,labelDisabled:Boolean,shape:{type:String,default:"round"}},computed:{isDisabled:function(){return this.parent&&this.parent.disabled||this.disabled},iconStyle:function(){var t=this.checkedColor;if(t&&this.checked&&!this.isDisabled)return{borderColor:t,backgroundColor:t}},tabindex:function(){return this.isDisabled||"radio"===a&&!this.checked?-1:0}},methods:{onClick:function(t){var e=this.$refs.label,i=t.target,n=e&&(e===i||e.contains(i));this.isDisabled||n&&this.labelDisabled||this.toggle(),this.$emit("click",t)}},render:function(){var t=arguments[0],e=this.slots,s=this.checked,c=e("icon",{checked:s})||t(n["a"],{attrs:{name:"success"},style:this.iconStyle}),o=e()&&t("span",{ref:"label",class:i("label",[this.labelPosition,{disabled:this.isDisabled}])},[e()]),l=[t("div",{class:i("icon",[this.shape,{disabled:this.isDisabled,checked:s}]),style:{fontSize:Object(r["a"])(this.iconSize)}},[c])];return"left"===this.labelPosition?l.unshift(o):l.push(o),t("div",{attrs:{role:a,tabindex:this.tabindex,"aria-checked":String(this.checked)},class:i(),on:{click:this.onClick}},[l])}}}},"1c30":function(t,e,i){"use strict";var n=i("5f8d"),s=i("5ca3"),r=Object(n["a"])("radio-group"),a=r[0],c=r[1];e["a"]=a({mixins:[Object(s["b"])("vanRadio")],props:{value:null,disabled:Boolean},watch:{value:function(t){this.$emit("change",t)}},render:function(){var t=arguments[0];return t("div",{class:c(),attrs:{role:"radiogroup"}},[this.slots()])}})},"1f0b":function(t,e,i){"use strict";var n=i("5f8d"),s=i("1781"),r=Object(n["a"])("radio"),a=r[0],c=r[1];e["a"]=a({mixins:[Object(s["a"])({bem:c,role:"radio",parent:"vanRadio"})],computed:{currentValue:{get:function(){return this.parent?this.parent.value:this.value},set:function(t){(this.parent||this).$emit("input",t)}},checked:function(){return this.currentValue===this.name}},methods:{toggle:function(){this.currentValue=this.name}}})},"2f12":function(t,e,i){},"384b":function(t,e,i){},5907:function(t,e,i){"use strict";i("b628")},"5ca3":function(t,e,i){"use strict";i.d(e,"a",function(){return r}),i.d(e,"b",function(){return a});var n=i("6e6d");function s(t){var e=[];function i(t){t.forEach(function(t){e.push(t),t.children&&i(t.children)})}return i(t),e}function r(t,e){var i,r;void 0===e&&(e={});var a=e.indexKey||"index";return n["a"].extend({inject:(i={},i[t]={default:null},i),computed:(r={parent:function(){return this[t]}},r[a]=function(){return this.bindRelation(),this.parent.children.indexOf(this)},r),mounted:function(){this.bindRelation()},beforeDestroy:function(){var t=this;this.parent&&(this.parent.children=this.parent.children.filter(function(e){return e!==t}))},methods:{bindRelation:function(){if(this.parent&&-1===this.parent.children.indexOf(this)){var t=[].concat(this.parent.children,[this]),e=s(this.parent.slots());t.sort(function(t,i){return e.indexOf(t.$vnode)-e.indexOf(i.$vnode)}),this.parent.children=t}}}})}function a(t){return{provide:function(){var e;return e={},e[t]=this,e},data:function(){return{children:[]}}}}},"98f4":function(t,e,i){"use strict";i.d(e,"a",function(){return a});var n=i("eda8"),s=i("13ee"),r=Object(n["b"])();function a(){r&&Object(s["e"])(Object(s["b"])())}},a506:function(t,e,i){"use strict";function n(t,e,i){return e in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}i.d(e,"a",function(){return n})},bc70:function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"set_nickname"},[i("van-cell-group",[i("van-field",{attrs:{label:"创建人姓名",placeholder:"请输入用户姓名"},model:{value:t.merchantInfo.merchantLeader,callback:function(e){t.$set(t.merchantInfo,"merchantLeader",e)},expression:"merchantInfo.merchantLeader"}}),i("van-field",{attrs:{label:"创建人手机",placeholder:"创建人手机"},model:{value:t.merchantInfo.merchantPhone,callback:function(e){t.$set(t.merchantInfo,"merchantPhone",e)},expression:"merchantInfo.merchantPhone"}})],1),i("div",{staticClass:"bottom_btn"},[i("van-button",{attrs:{size:"large",type:"danger"},on:{click:t.setConsignee}},[t._v("设置为默认收货人")])],1),i("div",{staticClass:"bottom_btn"},[i("van-button",{attrs:{size:"large",type:"danger"},on:{click:t.saveMerchantUser}},[t._v("返回")])],1)],1)},s=[],r=i("a506"),a=(i("7364"),i("0d9c"),i("c8e7")),c=(i("73e5"),i("1c68")),o=(i("2075"),i("bd14")),l=(i("d676"),i("1f0b")),u=(i("5907"),i("1c30")),h=i("4ec3"),d=i("6e6d");d["a"].use(u["a"]),d["a"].use(l["a"]),d["a"].use(c["a"]).use(o["a"]);var f={data:function(){return{name:"",roleType:"",shipData:{usId:"",name:"",roleType:""},merchantInfo:{},userData:{storeId:""},consigneeData:{userId:"",storeId:""}}},init:function(){this.findMerchantUser(),this.findLeader()},created:function(){this.findMerchantUser()},methods:{saveMerchantUser:function(){this.$router.go(-1)},findMerchantUser:function(){var t=this,e=this.$route.query.id;this.userData.storeId=e,Object(h["G"])(this.userData).then(function(e){t.merchantInfo=e.data.data}).then(function(){})},setConsignee:function(){var t=this,e=-2,i=this.merchantInfo.id;this.consigneeData.storeId=i,this.consigneeData.userId=e,Object(h["fb"])(this.consigneeData).then(function(e){return t.$dialog.alert({message:"已经设为默认收货人！"})}).then(function(){})}},components:Object(r["a"])({},a["a"].name,a["a"])},b=f,p=(i("f5df"),i("6691")),v=Object(p["a"])(b,n,s,!1,null,"665bf330",null);e["default"]=v.exports},c8e7:function(t,e,i){"use strict";var n=i("23c4"),s=i.n(n),r=i("f9c5"),a=i("bdc9"),c=i("1c68"),o=i("97ec"),l=i("0c4d"),u=i("98f4"),h=i("5f8d"),d=i("ef3a"),f=i("ad9f"),b=Object(h["a"])("field"),p=b[0],v=b[1];e["a"]=p({inheritAttrs:!1,props:Object(r["a"])({},o["a"],{error:Boolean,readonly:Boolean,autosize:[Boolean,Object],leftIcon:String,rightIcon:String,clearable:Boolean,labelClass:null,labelWidth:[Number,String],labelAlign:String,inputAlign:String,errorMessage:String,errorMessageAlign:String,type:{type:String,default:"text"}}),data:function(){return{focused:!1}},watch:{value:function(){this.$nextTick(this.adjustSize)}},mounted:function(){this.format(),this.$nextTick(this.adjustSize)},computed:{showClear:function(){return this.clearable&&this.focused&&""!==this.value&&Object(d["b"])(this.value)&&!this.readonly},listeners:function(){var t=Object(r["a"])({},this.$listeners,{input:this.onInput,keypress:this.onKeypress,focus:this.onFocus,blur:this.onBlur});return delete t.click,t},labelStyle:function(){var t=this.labelWidth;if(t)return{width:Object(f["a"])(t)}}},methods:{focus:function(){this.$refs.input&&this.$refs.input.focus()},blur:function(){this.$refs.input&&this.$refs.input.blur()},format:function(t){if(void 0===t&&(t=this.$refs.input),t){var e=t,i=e.value,n=this.$attrs.maxlength;return"number"===this.type&&Object(d["b"])(n)&&i.length>n&&(i=i.slice(0,n),t.value=i),i}},onInput:function(t){t.target.composing||this.$emit("input",this.format(t.target))},onFocus:function(t){this.focused=!0,this.$emit("focus",t),this.readonly&&this.blur()},onBlur:function(t){this.focused=!1,this.$emit("blur",t),Object(u["a"])()},onClick:function(t){this.$emit("click",t)},onClickLeftIcon:function(t){this.$emit("click-left-icon",t)},onClickRightIcon:function(t){this.$emit("click-right-icon",t)},onClear:function(t){Object(l["c"])(t),this.$emit("input",""),this.$emit("clear",t)},onKeypress:function(t){if("number"===this.type){var e=t.keyCode,i=-1===String(this.value).indexOf("."),n=e>=48&&e<=57||46===e&&i||45===e;n||Object(l["c"])(t)}"search"===this.type&&13===t.keyCode&&this.blur(),this.$emit("keypress",t)},adjustSize:function(){var t=this.$refs.input;if("textarea"===this.type&&this.autosize&&t){t.style.height="auto";var e=t.scrollHeight;if(Object(d["c"])(this.autosize)){var i=this.autosize,n=i.maxHeight,s=i.minHeight;n&&(e=Math.min(e,n)),s&&(e=Math.max(e,s))}e&&(t.style.height=e+"px")}},renderInput:function(){var t=this.$createElement,e=this.slots("input");if(e)return t("div",{class:v("control",this.inputAlign)},[e]);var i={ref:"input",class:v("control",this.inputAlign),domProps:{value:this.value},attrs:Object(r["a"])({},this.$attrs,{readonly:this.readonly}),on:this.listeners,directives:[{name:"model",value:this.value}]};return"textarea"===this.type?t("textarea",s()([{},i])):t("input",s()([{attrs:{type:this.type}},i]))},renderLeftIcon:function(){var t=this.$createElement,e=this.slots("left-icon")||this.leftIcon;if(e)return t("div",{class:v("left-icon"),on:{click:this.onClickLeftIcon}},[this.slots("left-icon")||t(a["a"],{attrs:{name:this.leftIcon}})])},renderRightIcon:function(){var t=this.$createElement,e=this.slots,i=e("right-icon")||this.rightIcon;if(i)return t("div",{class:v("right-icon"),on:{click:this.onClickRightIcon}},[e("right-icon")||t(a["a"],{attrs:{name:this.rightIcon}})])}},render:function(){var t,e=arguments[0],i=this.slots,n=this.labelAlign,s={icon:this.renderLeftIcon};return i("label")&&(s.title=function(){return i("label")}),e(c["a"],{attrs:{icon:this.leftIcon,size:this.size,title:this.label,center:this.center,border:this.border,isLink:this.isLink,required:this.required,clickable:this.clickable,titleStyle:this.labelStyle,titleClass:[v("label",n),this.labelClass],arrowDirection:this.arrowDirection},class:v((t={error:this.error,disabled:this.$attrs.disabled},t["label-"+n]=n,t["min-height"]="textarea"===this.type&&!this.autosize,t)),scopedSlots:s,on:{click:this.onClick}},[e("div",{class:v("body")},[this.renderInput(),this.showClear&&e(a["a"],{attrs:{name:"clear"},class:v("clear"),on:{touchstart:this.onClear}}),this.renderRightIcon(),i("button")&&e("div",{class:v("button")},[i("button")])]),this.errorMessage&&e("div",{class:v("error-message",this.errorMessageAlign)},[this.errorMessage])])}})},d676:function(t,e,i){"use strict";i("b628"),i("6025"),i("384b")},eda8:function(t,e,i){"use strict";i.d(e,"a",function(){return s}),i.d(e,"b",function(){return r});var n=i("ef3a");function s(){return!n["d"]&&/android/.test(navigator.userAgent.toLowerCase())}function r(){return!n["d"]&&/ios|iphone|ipad|ipod/.test(navigator.userAgent.toLowerCase())}},f5df:function(t,e,i){"use strict";var n=i("2f12"),s=i.n(n);s.a}}]);