(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6e02ef45"],{1781:function(t,e,i){"use strict";i.d(e,"a",function(){return r});var n=i("bdc9"),a=i("5ca3"),s=i("ad9f"),r=function(t){var e=t.parent,i=t.bem,r=t.role;return{mixins:[Object(a["a"])(e)],props:{name:null,value:null,disabled:Boolean,iconSize:[Number,String],checkedColor:String,labelPosition:String,labelDisabled:Boolean,shape:{type:String,default:"round"}},computed:{isDisabled:function(){return this.parent&&this.parent.disabled||this.disabled},iconStyle:function(){var t=this.checkedColor;if(t&&this.checked&&!this.isDisabled)return{borderColor:t,backgroundColor:t}},tabindex:function(){return this.isDisabled||"radio"===r&&!this.checked?-1:0}},methods:{onClick:function(t){var e=this.$refs.label,i=t.target,n=e&&(e===i||e.contains(i));this.isDisabled||n&&this.labelDisabled||this.toggle(),this.$emit("click",t)}},render:function(){var t=arguments[0],e=this.slots,a=this.checked,c=e("icon",{checked:a})||t(n["a"],{attrs:{name:"success"},style:this.iconStyle}),d=e()&&t("span",{ref:"label",class:i("label",[this.labelPosition,{disabled:this.isDisabled}])},[e()]),o=[t("div",{class:i("icon",[this.shape,{disabled:this.isDisabled,checked:a}]),style:{fontSize:Object(s["a"])(this.iconSize)}},[c])];return"left"===this.labelPosition?o.unshift(d):o.push(d),t("div",{attrs:{role:r,tabindex:this.tabindex,"aria-checked":String(this.checked)},class:i(),on:{click:this.onClick}},[o])}}}},"1c30":function(t,e,i){"use strict";var n=i("5f8d"),a=i("5ca3"),s=Object(n["a"])("radio-group"),r=s[0],c=s[1];e["a"]=r({mixins:[Object(a["b"])("vanRadio")],props:{value:null,disabled:Boolean},watch:{value:function(t){this.$emit("change",t)}},render:function(){var t=arguments[0];return t("div",{class:c(),attrs:{role:"radiogroup"}},[this.slots()])}})},"1d6c":function(t,e,i){"use strict";var n=i("23c4"),a=i.n(n),s=i("5f8d"),r=i("2e65"),c=i("bc50"),d=i("1c30"),o=i("77e3"),l=i("bdc9"),u=i("1c68"),f=i("1f0b"),h=Object(s["a"])("address-item"),b=h[0],p=h[1];function v(t,e,i,n){var s=e.disabled,c=e.switchable;function d(){c&&Object(r["a"])(n,"select"),Object(r["a"])(n,"click")}var h=function(){return t(l["a"],{attrs:{name:"edit"},class:p("edit"),on:{click:function(t){t.stopPropagation(),Object(r["a"])(n,"edit"),Object(r["a"])(n,"click")}}})},b=function(){var i=e.data,n=[t("div",{class:p("name")},[i.name+"，"+i.tel]),t("div",{class:p("address")},[i.address])];return c&&!s?t(f["a"],{attrs:{name:i.id,iconSize:16,checkedColor:o["k"]}},[n]):n};return t(u["a"],a()([{class:p({disabled:s}),attrs:{valueClass:p("value"),clickable:c&&!s},scopedSlots:{default:b,"right-icon":h},on:{click:d}},Object(r["b"])(n)]))}v.props={data:Object,disabled:Boolean,switchable:Boolean};var m=b(v),g=Object(s["a"])("address-list"),k=g[0],x=g[1],O=g[2];function j(t,e,i,n){function s(i,a){if(i)return i.map(function(i,s){return t(m,{attrs:{data:i,disabled:a,switchable:e.switchable},key:i.id,on:{select:function(){Object(r["a"])(n,a?"select-disabled":"select",i,s),a||Object(r["a"])(n,"input",i.id)},edit:function(){Object(r["a"])(n,a?"edit-disabled":"edit",i,s)},click:function(){Object(r["a"])(n,"click-item",i,s)}}})})}var o=s(e.list),l=s(e.disabledList,!0);return t("div",a()([{class:x()},Object(r["b"])(n)]),[i.top&&i.top(),t(d["a"],{attrs:{value:e.value},on:{input:function(t){Object(r["a"])(n,"input",t)}}},[o]),e.disabledText&&t("div",{class:x("disabled-text")},[e.disabledText]),l,i.default&&i.default(),t(c["a"],{attrs:{square:!0,size:"large",type:"danger",text:e.addButtonText||O("add")},class:x("add"),on:{click:function(){Object(r["a"])(n,"add")}}})])}j.props={list:Array,disabledList:Array,disabledText:String,addButtonText:String,value:[Number,String],switchable:{type:Boolean,default:!0}};e["a"]=k(j)},"1f0b":function(t,e,i){"use strict";var n=i("5f8d"),a=i("1781"),s=Object(n["a"])("radio"),r=s[0],c=s[1];e["a"]=r({mixins:[Object(a["a"])({bem:c,role:"radio",parent:"vanRadio"})],computed:{currentValue:{get:function(){return this.parent?this.parent.value:this.value},set:function(t){(this.parent||this).$emit("input",t)}},checked:function(){return this.currentValue===this.name}},methods:{toggle:function(){this.currentValue=this.name}}})},2166:function(t,e,i){"use strict";var n=i("9a4e"),a=i.n(n);a.a},"384b":function(t,e,i){},4910:function(t,e,i){"use strict";i("b628"),i("6025"),i("384b"),i("90f9")},"5ca3":function(t,e,i){"use strict";i.d(e,"a",function(){return s}),i.d(e,"b",function(){return r});var n=i("6e6d");function a(t){var e=[];function i(t){t.forEach(function(t){e.push(t),t.children&&i(t.children)})}return i(t),e}function s(t,e){var i,s;void 0===e&&(e={});var r=e.indexKey||"index";return n["a"].extend({inject:(i={},i[t]={default:null},i),computed:(s={parent:function(){return this[t]}},s[r]=function(){return this.bindRelation(),this.parent.children.indexOf(this)},s),mounted:function(){this.bindRelation()},beforeDestroy:function(){var t=this;this.parent&&(this.parent.children=this.parent.children.filter(function(e){return e!==t}))},methods:{bindRelation:function(){if(this.parent&&-1===this.parent.children.indexOf(this)){var t=[].concat(this.parent.children,[this]),e=a(this.parent.slots());t.sort(function(t,i){return e.indexOf(t.$vnode)-e.indexOf(i.$vnode)}),this.parent.children=t}}}})}function r(t){return{provide:function(){var e;return e={},e[t]=this,e},data:function(){return{children:[]}}}}},"6a65":function(t,e,i){"use strict";i.r(e);var n,a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("van-nav-bar",{attrs:{title:"收货地址","left-text":"返回","left-arrow":""},on:{"click-left":t.goback}}),i("van-address-list",{attrs:{list:t.addressList},on:{add:t.onAdd,edit:t.onEdit,select:t.onSelect},model:{value:t.chosenAddressId,callback:function(e){t.chosenAddressId=e},expression:"chosenAddressId"}})],1)},s=[],r=i("a506"),c=(i("4910"),i("1d6c")),d=(i("e4b3"),i("bff1")),o=(i("7364"),i("4ec3")),l=i("3c96"),u={data:function(){return{chosenAddressId:-1,addressList:[]}},created:function(){this.loadAddress()},methods:{onAdd:function(){this.$router.push({name:"address-edit",query:{addressId:-1}})},onEdit:function(t,e){this.$router.push({name:"address-edit",query:{addressId:t.id}})},onSelect:function(t,e){Object(l["c"])({AddressId:t.id}),this.$router.go(-1)},goback:function(){this.$router.go(-1)},loadAddress:function(){var t=this;Object(o["e"])().then(function(e){for(var i=e.data.data.list,n=0;n<i.length;n++){var a=i[n];t.addressList.push({id:a.id,name:a.name,tel:a.tel,address:a.province+a.city+a.county+" "+a.addressDetail})}})}},components:(n={},Object(r["a"])(n,d["a"].name,d["a"]),Object(r["a"])(n,c["a"].name,c["a"]),n)},f=u,h=(i("2166"),i("6691")),b=Object(h["a"])(f,a,s,!1,null,"a8f04c84",null);e["default"]=b.exports},"90f9":function(t,e,i){},"9a4e":function(t,e,i){},a506:function(t,e,i){"use strict";function n(t,e,i){return e in t?Object.defineProperty(t,e,{value:i,enumerable:!0,configurable:!0,writable:!0}):t[e]=i,t}i.d(e,"a",function(){return n})},bff1:function(t,e,i){"use strict";var n=i("23c4"),a=i.n(n),s=i("5f8d"),r=i("ef3a"),c=i("2e65"),d=i("77e3"),o=i("bdc9"),l=Object(s["a"])("nav-bar"),u=l[0],f=l[1];function h(t,e,i,n){var s;return t("div",a()([{class:[f({fixed:e.fixed}),(s={},s[d["c"]]=e.border,s)],style:{zIndex:e.zIndex}},Object(c["b"])(n)]),[t("div",{class:f("left"),on:{click:n.listeners["click-left"]||r["e"]}},[i.left?i.left():[e.leftArrow&&t(o["a"],{class:f("arrow"),attrs:{name:"arrow-left"}}),e.leftText&&t("span",{class:f("text")},[e.leftText])]]),t("div",{class:[f("title"),"van-ellipsis"]},[i.title?i.title():e.title]),t("div",{class:f("right"),on:{click:n.listeners["click-right"]||r["e"]}},[i.right?i.right():e.rightText&&t("span",{class:f("text")},[e.rightText])])])}h.props={title:String,fixed:Boolean,leftText:String,rightText:String,leftArrow:Boolean,border:{type:Boolean,default:!0},zIndex:{type:Number,default:1}},e["a"]=u(h)},e4b3:function(t,e,i){"use strict";i("b628"),i("6025"),i("e814")},e814:function(t,e,i){}}]);