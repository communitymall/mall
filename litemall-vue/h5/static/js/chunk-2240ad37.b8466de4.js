(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2240ad37"],{1146:function(t,e,n){},"32d7":function(t,e,n){"use strict";n.d(e,"a",function(){return r}),n.d(e,"b",function(){return a});var i=n("a142");function r(){return!i["d"]&&/android/.test(navigator.userAgent.toLowerCase())}function a(){return!i["d"]&&/ios|iphone|ipad|ipod/.test(navigator.userAgent.toLowerCase())}},3852:function(t,e,n){var i=n("96f3"),r=n("e2c0");function a(t,e){return null!=t&&r(t,e,i)}t.exports=a},"3b42":function(t,e,n){},"565f":function(t,e,n){"use strict";var i=n("2638"),r=n.n(i),a=n("c31d"),o=n("ad06"),s=n("7744"),c=n("dfaf"),l=n("1325"),u=n("a8fa"),d=n("d282"),f=n("a142"),h=n("ea8e"),b=Object(d["a"])("field"),p=b[0],g=b[1];e["a"]=p({inheritAttrs:!1,props:Object(a["a"])({},c["a"],{error:Boolean,readonly:Boolean,autosize:[Boolean,Object],leftIcon:String,rightIcon:String,clearable:Boolean,labelClass:null,labelWidth:[Number,String],labelAlign:String,inputAlign:String,errorMessage:String,errorMessageAlign:String,type:{type:String,default:"text"}}),data:function(){return{focused:!1}},watch:{value:function(){this.$nextTick(this.adjustSize)}},mounted:function(){this.format(),this.$nextTick(this.adjustSize)},computed:{showClear:function(){return this.clearable&&this.focused&&""!==this.value&&Object(f["b"])(this.value)&&!this.readonly},listeners:function(){var t=Object(a["a"])({},this.$listeners,{input:this.onInput,keypress:this.onKeypress,focus:this.onFocus,blur:this.onBlur});return delete t.click,t},labelStyle:function(){var t=this.labelWidth;if(t)return{width:Object(h["a"])(t)}}},methods:{focus:function(){this.$refs.input&&this.$refs.input.focus()},blur:function(){this.$refs.input&&this.$refs.input.blur()},format:function(t){if(void 0===t&&(t=this.$refs.input),t){var e=t,n=e.value,i=this.$attrs.maxlength;return"number"===this.type&&Object(f["b"])(i)&&n.length>i&&(n=n.slice(0,i),t.value=n),n}},onInput:function(t){t.target.composing||this.$emit("input",this.format(t.target))},onFocus:function(t){this.focused=!0,this.$emit("focus",t),this.readonly&&this.blur()},onBlur:function(t){this.focused=!1,this.$emit("blur",t),Object(u["a"])()},onClick:function(t){this.$emit("click",t)},onClickLeftIcon:function(t){this.$emit("click-left-icon",t)},onClickRightIcon:function(t){this.$emit("click-right-icon",t)},onClear:function(t){Object(l["c"])(t),this.$emit("input",""),this.$emit("clear",t)},onKeypress:function(t){if("number"===this.type){var e=t.keyCode,n=-1===String(this.value).indexOf("."),i=e>=48&&e<=57||46===e&&n||45===e;i||Object(l["c"])(t)}"search"===this.type&&13===t.keyCode&&this.blur(),this.$emit("keypress",t)},adjustSize:function(){var t=this.$refs.input;if("textarea"===this.type&&this.autosize&&t){t.style.height="auto";var e=t.scrollHeight;if(Object(f["c"])(this.autosize)){var n=this.autosize,i=n.maxHeight,r=n.minHeight;i&&(e=Math.min(e,i)),r&&(e=Math.max(e,r))}e&&(t.style.height=e+"px")}},renderInput:function(){var t=this.$createElement,e=this.slots("input");if(e)return t("div",{class:g("control",this.inputAlign)},[e]);var n={ref:"input",class:g("control",this.inputAlign),domProps:{value:this.value},attrs:Object(a["a"])({},this.$attrs,{readonly:this.readonly}),on:this.listeners,directives:[{name:"model",value:this.value}]};return"textarea"===this.type?t("textarea",r()([{},n])):t("input",r()([{attrs:{type:this.type}},n]))},renderLeftIcon:function(){var t=this.$createElement,e=this.slots("left-icon")||this.leftIcon;if(e)return t("div",{class:g("left-icon"),on:{click:this.onClickLeftIcon}},[this.slots("left-icon")||t(o["a"],{attrs:{name:this.leftIcon}})])},renderRightIcon:function(){var t=this.$createElement,e=this.slots,n=e("right-icon")||this.rightIcon;if(n)return t("div",{class:g("right-icon"),on:{click:this.onClickRightIcon}},[e("right-icon")||t(o["a"],{attrs:{name:this.rightIcon}})])}},render:function(){var t,e=arguments[0],n=this.slots,i=this.labelAlign,r={icon:this.renderLeftIcon};return n("label")&&(r.title=function(){return n("label")}),e(s["a"],{attrs:{icon:this.leftIcon,size:this.size,title:this.label,center:this.center,border:this.border,isLink:this.isLink,required:this.required,clickable:this.clickable,titleStyle:this.labelStyle,titleClass:[g("label",i),this.labelClass],arrowDirection:this.arrowDirection},class:g((t={error:this.error,disabled:this.$attrs.disabled},t["label-"+i]=i,t["min-height"]="textarea"===this.type&&!this.autosize,t)),scopedSlots:r,on:{click:this.onClick}},[e("div",{class:g("body")},[this.renderInput(),this.showClear&&e(o["a"],{attrs:{name:"clear"},class:g("clear"),on:{touchstart:this.onClear}}),this.renderRightIcon(),n("button")&&e("div",{class:g("button")},[n("button")])]),this.errorMessage&&e("div",{class:g("error-message",this.errorMessageAlign)},[this.errorMessage])])}})},"66fd":function(t,e,n){"use strict";var i=n("2638"),r=n.n(i),a=n("d282"),o=n("a142"),s=n("ba31"),c=n("a3e2"),l=n("44bf"),u=Object(a["a"])("card"),d=u[0],f=u[1];function h(t,e,n,i){var a=e.thumb,u=n.num||Object(o["b"])(e.num),d=n.price||Object(o["b"])(e.price),h=n["origin-price"]||Object(o["b"])(e.originPrice),b=u||d||h;function p(t){Object(s["a"])(i,"click-thumb",t)}function g(){if(n.tag||e.tag)return t("div",{class:f("tag")},[n.tag?n.tag():t(c["a"],{attrs:{mark:!0,type:"danger"}},[e.tag])])}function m(){if(n.thumb||a)return t("a",{attrs:{href:e.thumbLink},class:f("thumb"),on:{click:p}},[n.thumb?n.thumb():t(l["a"],{attrs:{src:a,width:"100%",height:"100%",fit:"contain","lazy-load":e.lazyLoad}}),g()])}function v(){return n.title?n.title():e.title?t("div",{class:f("title")},[e.title]):void 0}function y(){return n.desc?n.desc():e.desc?t("div",{class:[f("desc"),"van-ellipsis"]},[e.desc]):void 0}function O(){if(d)return t("div",{class:f("price")},[n.price?n.price():e.currency+" "+e.price])}function I(){if(h){var i=n["origin-price"];return t("div",{class:f("origin-price")},[i?i():e.currency+" "+e.originPrice])}}function S(){if(u)return t("div",{class:f("num")},[n.num?n.num():"x "+e.num])}function j(){if(n.footer)return t("div",{class:f("footer")},[n.footer()])}return t("div",r()([{class:f()},Object(s["b"])(i,!0)]),[t("div",{class:f("header")},[m(),t("div",{class:f("content",{centered:e.centered})},[v(),y(),n.tags&&n.tags(),b&&t("div",{class:"van-card__bottom"},[O(),I(),S(),n.bottom&&n.bottom()])])]),j()])}h.props={tag:String,desc:String,thumb:String,title:String,centered:Boolean,lazyLoad:Boolean,thumbLink:String,num:[Number,String],price:[Number,String],originPrice:[Number,String],currency:{type:String,default:"¥"}},e["a"]=d(h)},9550:function(t,e,n){"use strict";var i=n("99cf"),r=n.n(i);r.a},"96f3":function(t,e){var n=Object.prototype,i=n.hasOwnProperty;function r(t,e){return null!=t&&i.call(t,e)}t.exports=r},"99cf":function(t,e,n){},"9b7e":function(t,e,n){},"9cb7":function(t,e,n){"use strict";n("68ef"),n("9b7e"),n("09fe"),n("ea82")},a3e2:function(t,e,n){"use strict";var i=n("2638"),r=n.n(i),a=n("d282"),o=n("ba31"),s=Object(a["a"])("tag"),c=s[0],l=s[1];function u(t,e,n,i){var a,s=e.type,c=e.mark,u=e.plain,d=e.color,f=e.round,h=e.size,b=u?"color":"backgroundColor",p=(a={},a[b]=d,a);e.textColor&&(p.color=e.textColor);var g={mark:c,plain:u,round:f};return h&&(g[h]=h),t("span",r()([{style:p,class:[l([g,s]),{"van-hairline--surround":u}]},Object(o["b"])(i,!0)]),[n.default&&n.default()])}u.props={size:String,mark:Boolean,color:String,plain:Boolean,round:Boolean,textColor:String,type:{type:String,default:"default"}},e["a"]=c(u)},a8fa:function(t,e,n){"use strict";n.d(e,"a",function(){return o});var i=n("32d7"),r=n("a8c1"),a=Object(i["b"])();function o(){a&&Object(r["e"])(Object(r["b"])())}},be39:function(t,e,n){"use strict";n("68ef"),n("09fe"),n("3b42")},be7f:function(t,e,n){"use strict";n("68ef"),n("09fe"),n("1146")},c292:function(t,e,n){"use strict";n.r(e);var i,r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"order_detail"},[n("div",{staticClass:"order-goods"},[t._l(t.orderGoods,function(t){return n("van-card",{key:t.id,attrs:{title:t.goodsName,desc:"暂无描述",num:t.number,price:t.price,thumb:t.picUrl}})}),n("van-cell-group",[n("van-cell",{attrs:{title:"商品金额"}},[n("span",{staticClass:"red"},[t._v(t._s(t._f("yuan")(100*t.orderInfo.goodsPrice)))])]),n("van-cell",{attrs:{title:"运输费用"}},[n("span",{staticClass:"red"},[t._v(t._s(t._f("yuan")(100*t.orderInfo.freightPrice)))])])],1)],2),n("van-cell-group",{staticStyle:{"margin-top":"20px"}},[n("van-cell",{attrs:{icon:"dingwei",title:t.orderInfo.consignee+"  "+t.orderInfo.mobile,label:t.orderInfo.address}})],1),n("van-cell-group",{staticStyle:{"margin-top":"20px"}},[n("van-cell",{attrs:{title:"下单时间"}},[n("span",[t._v(t._s(t.orderInfo.addTime))])]),n("van-cell",{attrs:{title:"订单编号"}},[n("span",[t._v(t._s(t.orderInfo.orderSn))])]),n("van-cell",{attrs:{title:"订单备注"}},[n("span",[t._v(t._s(t.orderInfo.remark))])]),n("van-cell",{attrs:{title:"实付款："}},[n("span",{staticClass:"red"},[t._v(t._s(t._f("yuan")(100*t.orderInfo.actualPrice)))])]),n("van-cell",[t.handleOption.cancel?n("van-button",{staticStyle:{float:"right"},attrs:{size:"small",round:"",type:"danger"},on:{click:function(e){return t.cancelOrder(t.orderInfo.id)}}},[t._v("取消订单")]):t._e(),t.handleOption.pay?n("van-button",{staticStyle:{float:"right"},attrs:{size:"small",round:"",type:"danger"},on:{click:function(e){return t.payOrder(t.orderInfo.id)}}},[t._v("去支付")]):t._e(),t.handleOption.delete?n("van-button",{staticStyle:{float:"right"},attrs:{size:"small",type:"danger"},on:{click:function(e){return t.deleteOrder(t.orderInfo.id)}}},[t._v("删除订单")]):t._e(),t.handleOption.confirm?n("van-button",{staticStyle:{float:"right"},attrs:{size:"small",type:"danger"},on:{click:function(e){return t.confirmOrder(t.orderInfo.id)}}},[t._v("确认收货")]):t._e(),t.handleOption.refund?n("van-button",{staticStyle:{float:"right"},attrs:{size:"small",type:"danger"},on:{click:function(e){return t.refundOrder(t.orderInfo.id)}}},[t._v("退款")]):t._e()],1)],1),t.showExp()?n("van-cell-group",{staticStyle:{"margin-top":"20px"}},[n("van-cell",{attrs:{title:"物流公司"}},[n("span",[t._v(t._s(t.orderInfo.expCode))])]),n("van-cell",{attrs:{title:"物流编号"}},[n("span",[t._v(t._s(t.orderInfo.expNo))])])],1):t._e()],1)},a=[],o=n("bd86"),s=(n("be7f"),n("565f")),c=(n("9cb7"),n("66fd")),l=(n("be39"),n("efa0")),u=(n("66b9"),n("b650")),d=(n("c194"),n("7744")),f=(n("0653"),n("34e9")),h=(n("7f7f"),n("e17f"),n("2241")),b=n("3852"),p=n.n(b),g=n("4ec3"),m={data:function(){return{isSubmit:!1,isDisabled:!1,orderInfo:{},orderGoods:[],handleOption:{},expressInfo:{}}},created:function(){this.init()},methods:{showExp:function(){return p()(this.orderInfo,"expNo")},deleteOrder:function(t){var e=this;this.$dialog.confirm({message:"确定要删除该订单吗?"}).then(function(){Object(g["ab"])({orderId:t}).then(function(){e.$toast("已删除订单"),e.$router.go(-1)})}).catch(function(){})},cancelOrder:function(t){var e=this;this.$dialog.confirm({message:"确定要取消该订单吗?"}).then(function(){Object(g["Y"])({orderId:t}).then(function(){e.init(),e.$toast("已取消该订单")})}).catch(function(){})},confirmOrder:function(t){var e=this;this.$dialog.confirm({message:"请确认收到货物, 确认收货后无法撤销!"}).then(function(){Object(g["Z"])({orderId:t}).then(function(){e.init(),e.$toast("已确认收货")})}).catch(function(){})},refundOrder:function(t){var e=this;this.$dialog.confirm({message:"确定要申请退款吗?"}).then(function(){Object(g["eb"])({orderId:t}).then(function(){e.init(),e.$toast("已申请订单退款")})}).catch(function(){})},commentOrder:function(t){},toPay:function(t){this.$router.push({name:"payment",params:{orderId:t}})},init:function(){var t=this,e=this.$route.query.orderId;Object(g["bb"])({orderId:e}).then(function(e){var n=e.data.data;t.orderInfo=n.orderInfo,t.orderGoods=n.orderGoods,t.handleOption=n.orderInfo.handleOption,t.expressInfo=n.expressInfo})}},components:(i={},Object(o["a"])(i,h["a"].name,h["a"]),Object(o["a"])(i,f["a"].name,f["a"]),Object(o["a"])(i,d["a"].name,d["a"]),Object(o["a"])(i,u["a"].name,u["a"]),Object(o["a"])(i,l["a"].name,l["a"]),Object(o["a"])(i,c["a"].name,c["a"]),Object(o["a"])(i,s["a"].name,s["a"]),i)},v=m,y=(n("9550"),n("2877")),O=Object(y["a"])(v,r,a,!1,null,"67c0ba4b",null);e["default"]=O.exports},ea82:function(t,e,n){},efa0:function(t,e,n){"use strict";var i=n("2638"),r=n.n(i),a=n("d282"),o=n("ba31"),s=n("b650"),c=n("ad06"),l=Object(a["a"])("submit-bar"),u=l[0],d=l[1],f=l[2];function h(t,e,n,i){var a=e.tip,l=e.price,u=e.tipIcon;function h(){if("number"===typeof l){var n=e.currency+" "+(l/100).toFixed(e.decimalLength);return t("div",{class:d("text")},[t("span",[e.label||f("label")]),t("span",{class:d("price")},[n]),e.suffixLabel&&t("span",{class:d("suffix-label")},[e.suffixLabel])])}}function b(){if(n.tip||a)return t("div",{class:d("tip")},[u&&t(c["a"],{class:d("tip-icon"),attrs:{name:u}}),a&&t("span",{class:d("tip-text")},[a]),n.tip&&n.tip()])}return t("div",r()([{class:d({"safe-area-inset-bottom":e.safeAreaInsetBottom})},Object(o["b"])(i)]),[n.top&&n.top(),b(),t("div",{class:d("bar")},[n.default&&n.default(),h(),t(s["a"],{attrs:{square:!0,size:"large",type:e.buttonType,loading:e.loading,disabled:e.disabled,text:e.loading?"":e.buttonText},class:d("button"),on:{click:function(){Object(o["a"])(i,"submit")}}})])])}h.props={tip:String,label:String,price:Number,tipIcon:String,loading:Boolean,disabled:Boolean,buttonText:String,suffixLabel:String,safeAreaInsetBottom:Boolean,decimalLength:{type:Number,default:2},currency:{type:String,default:"¥"},buttonType:{type:String,default:"danger"}},e["a"]=u(h)}}]);