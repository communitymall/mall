(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0e2a050e"],{"0376":function(t,e,n){},"0aeb":function(t,e,n){"use strict";var i=n("5f8d"),s=n("ef3a"),a=n("adfc"),o=Object(i["a"])("popup"),r=o[0],u=o[1];e["a"]=r({mixins:[a["a"]],props:{round:Boolean,duration:Number,transition:String,position:{type:String,default:"center"},overlay:{type:Boolean,default:!0},closeOnClickOverlay:{type:Boolean,default:!0}},beforeCreate:function(){var t=this,e=function(e){return function(n){return t.$emit(e,n)}};this.onClick=e("click"),this.onOpened=e("opened"),this.onClosed=e("closed")},render:function(){var t,e=arguments[0];if(this.shouldRender){var n=this.round,i=this.position,a=this.duration,o=this.transition||("center"===i?"van-fade":"van-popup-slide-"+i),r={};return Object(s["b"])(a)&&(r.transitionDuration=a+"s"),e("transition",{attrs:{name:o},on:{afterEnter:this.onOpened,afterLeave:this.onClosed}},[e("div",{directives:[{name:"show",value:this.value}],style:r,class:u((t={round:n},t[i]=i,t)),on:{click:this.onClick}},[this.slots()])])}}})},4198:function(t,e,n){"use strict";n("b628"),n("f8e2")},"56e3":function(t,e,n){"use strict";n.r(e);var i,s=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"user_information"},[n("van-cell-group",[n("van-cell",{staticClass:"cell_middle",attrs:{title:"头像"}},[n("van-uploader",{attrs:{afterRead:t.avatarAfterRead}},[n("div",{staticClass:"user_avatar_upload"},[t.pic?n("img",{attrs:{src:t.pic+"?x-oss-process=image/resize,m_fill,h_80,w_50",alt:"你的头像"}}):n("van-icon",{attrs:{name:"camera_full"}})],1)])],1),n("van-cell",{attrs:{title:"昵称",to:"/user/information/setNickname",value:t.nickName,isLink:""}}),n("van-cell",{attrs:{title:"性别",value:t.genderText,isLink:""},on:{click:function(e){t.showSex=!0}}}),n("van-cell",{attrs:{title:"密码设置",to:"/user/information/setPassword",isLink:""}}),n("van-cell",{attrs:{title:"手机号",to:"/user/information/setMobile",value:t.mobile,isLink:""}})],1),n("van-button",{staticClass:"user_quit",attrs:{size:"large"},on:{click:t.loginOut}},[t._v("退出当前账户")]),n("van-popup",{attrs:{position:"bottom"},model:{value:t.showSex,callback:function(e){t.showSex=e},expression:"showSex"}},[n("van-picker",{attrs:{showToolbar:"",columns:t.sexColumns,title:"选择性别"},on:{cancel:function(e){t.showSex=!1},confirm:t.onSexConfirm}})],1)],1)},a=[],o=n("a506"),r=(n("6ca8"),n("0aeb")),u=(n("4198"),n("9b61")),c=(n("a710"),n("49b7")),l=(n("7364"),n("6cd4"),n("bc50")),h=n("3c96"),f=n("4ec3"),d={data:function(){return{sexColumns:[{values:["保密","男","女"],defaultIndex:0}],showSex:!1,avatar:"",nickName:"",gender:2,mobile:"",userId:"",pic:""}},computed:{genderText:function(){var t=["保密","男","女"];return t[this.gender]||""}},created:function(){this.getUserInfo()},methods:{avatarAfterRead:function(t){var e=this,n="image/jpeg"==t.file.type||"image/png"==t.file.type;if(!n)return this.$message({type:"warning",message:"上传图片只能是 JPG、PNG 格式!"}),!1;var i=this.userId,s=t.file,a=this.avatar,o=new FormData;o.append("imagefile",s),o.append("userId",i),o.append("avatar",a),Object(f["lb"])(o).then(function(t){e.pic=t.data.data+"?"+Math.random()})},onSexConfirm:function(t,e){Object(f["k"])({gender:e[0]}),this.showSex=!1},getUserInfo:function(){var t=this;Object(f["h"])().then(function(e){t.avatar=e.data.data.avatar,t.nickName=e.data.data.nickName,t.gender=e.data.data.gender,t.mobile=e.data.data.mobile,t.userId=e.data.data.userId,t.pic=e.data.data.avatar+"?"+Math.random()})},loginOut:function(){var t=this;Object(f["j"])().then(function(e){Object(h["b"])("Authorization"),Object(h["b"])("avatar"),Object(h["b"])("nickName"),t.$router.push({name:"home"})})}},components:(i={},Object(o["a"])(i,l["a"].name,l["a"]),Object(o["a"])(i,c["a"].name,c["a"]),Object(o["a"])(i,u["a"].name,u["a"]),Object(o["a"])(i,r["a"].name,r["a"]),i)},m=d,p=(n("c73c"),n("6691")),v=Object(p["a"])(m,s,a,!1,null,"2f01a417",null);e["default"]=v.exports},"6ca8":function(t,e,n){"use strict";n("b628"),n("8564")},"7b55":function(t,e,n){"use strict";(function(t){n.d(e,"b",function(){return u}),n.d(e,"a",function(){return c});var i=n("ef3a"),s=Date.now();function a(t){var e=Date.now(),n=Math.max(0,16-(e-s)),i=setTimeout(t,n);return s=e+n,i}var o=i["d"]?t:window,r=o.requestAnimationFrame||a;o.cancelAnimationFrame||o.clearTimeout;function u(t){return r.call(o,t)}function c(t){u(function(){u(t)})}}).call(this,n("a1bb"))},"9b61":function(t,e,n){"use strict";var i=n("f9c5"),s=n("5f8d"),a=n("0c4d"),o=n("9f89");function r(t){return Array.isArray(t)?t.map(function(t){return r(t)}):"object"===typeof t?Object(o["a"])({},t):t}var u=n("b2d4"),c=n("77e3"),l=n("27e0"),h=n("ef3a"),f=n("dddd"),d=n("4c5e"),m=200,p=1e3,v=300,g=15,b=Object(s["a"])("picker-column"),x=b[0],I=b[1];function O(t){var e=window.getComputedStyle(t),n=e.transform||e.webkitTransform,i=n.slice(7,n.length-1).split(", ")[5];return Number(i)}function C(t){return Object(h["c"])(t)&&t.disabled}var y=x({mixins:[d["a"]],props:{valueKey:String,className:String,itemHeight:Number,defaultIndex:Number,visibleItemCount:Number,initialOptions:{type:Array,default:function(){return[]}}},data:function(){return{offset:0,duration:0,options:r(this.initialOptions),currentIndex:this.defaultIndex}},created:function(){this.$parent.children&&this.$parent.children.push(this),this.setIndex(this.currentIndex)},destroyed:function(){var t=this.$parent.children;t&&t.splice(t.indexOf(this),1)},watch:{defaultIndex:function(){this.setIndex(this.defaultIndex)}},computed:{count:function(){return this.options.length}},methods:{onTouchStart:function(t){if(this.touchStart(t),this.moving){var e=O(this.$refs.wrapper);this.startOffset=Math.min(0,e)}else this.startOffset=this.offset;this.duration=0,this.moving=!1,this.transitionEndTrigger=null,this.touchStartTime=Date.now(),this.momentumOffset=this.startOffset},onTouchMove:function(t){this.moving=!0,this.touchMove(t),"vertical"===this.direction&&Object(a["c"])(t,!0),this.offset=Object(f["a"])(this.startOffset+this.deltaY,-this.count*this.itemHeight,this.itemHeight);var e=Date.now();e-this.touchStartTime>v&&(this.touchStartTime=e,this.momentumOffset=this.offset)},onTouchEnd:function(){var t=this.offset-this.momentumOffset,e=Date.now()-this.touchStartTime,n=e<v&&Math.abs(t)>g;if(n)this.momentum(t,e);else if(this.offset!==this.startOffset){this.duration=m;var i=this.getIndexByOffset(this.offset);this.setIndex(i,!0)}},onTransitionEnd:function(){this.stopMomentum()},onClickItem:function(t){this.duration=m,this.setIndex(t,!0)},adjustIndex:function(t){t=Object(f["a"])(t,0,this.count);for(var e=t;e<this.count;e++)if(!C(this.options[e]))return e;for(var n=t-1;n>=0;n--)if(!C(this.options[n]))return n},getOptionText:function(t){return Object(h["c"])(t)&&this.valueKey in t?t[this.valueKey]:t},setIndex:function(t,e){var n=this;t=this.adjustIndex(t)||0,this.offset=-t*this.itemHeight;var i=function(){t!==n.currentIndex&&(n.currentIndex=t,e&&n.$emit("change",t))};this.moving?this.transitionEndTrigger=i:i()},setValue:function(t){for(var e=this.options,n=0;n<e.length;n++)if(this.getOptionText(e[n])===t)return this.setIndex(n)},getValue:function(){return this.options[this.currentIndex]},getIndexByOffset:function(t){return Object(f["a"])(Math.round(-t/this.itemHeight),0,this.count-1)},momentum:function(t,e){var n=Math.abs(t/e);t=this.offset+n/.002*(t<0?-1:1);var i=this.getIndexByOffset(t);this.duration=p,this.setIndex(i,!0)},stopMomentum:function(){this.moving=!1,this.duration=0,this.transitionEndTrigger&&(this.transitionEndTrigger(),this.transitionEndTrigger=null)}},render:function(){var t=this,e=arguments[0],n=this.itemHeight,i=this.visibleItemCount,s=n*(i-1)/2,a={transform:"translate3d(0, "+(this.offset+s)+"px, 0)",transitionDuration:this.duration+"ms",transitionProperty:this.duration?"all":"none",lineHeight:n+"px"},o={height:n+"px"};return e("div",{class:[I(),this.className],on:{touchstart:this.onTouchStart,touchmove:this.onTouchMove,touchend:this.onTouchEnd,touchcancel:this.onTouchEnd}},[e("ul",{ref:"wrapper",style:a,class:I("wrapper"),on:{transitionend:this.onTransitionEnd}},[this.options.map(function(n,i){return e("li",{style:o,class:["van-ellipsis",I("item",{disabled:C(n),selected:i===t.currentIndex})],domProps:{innerHTML:t.getOptionText(n)},on:{click:function(){t.onClickItem(i)}}})})])])}}),w=Object(s["a"])("picker"),T=w[0],k=w[1],j=w[2];e["a"]=T({props:Object(i["a"])({},u["a"],{defaultIndex:{type:Number,default:0},columns:{type:Array,default:function(){return[]}},toolbarPosition:{type:String,default:"top"},valueKey:{type:String,default:"text"}}),data:function(){return{children:[]}},computed:{simple:function(){return this.columns.length&&!this.columns[0].values}},watch:{columns:"setColumns"},methods:{setColumns:function(){var t=this,e=this.simple?[{values:this.columns}]:this.columns;e.forEach(function(e,n){t.setColumnValues(n,r(e.values))})},emit:function(t){this.simple?this.$emit(t,this.getColumnValue(0),this.getColumnIndex(0)):this.$emit(t,this.getValues(),this.getIndexes())},onChange:function(t){this.simple?this.$emit("change",this,this.getColumnValue(0),this.getColumnIndex(0)):this.$emit("change",this,this.getValues(),t)},getColumn:function(t){return this.children[t]},getColumnValue:function(t){var e=this.getColumn(t);return e&&e.getValue()},setColumnValue:function(t,e){var n=this.getColumn(t);n&&n.setValue(e)},getColumnIndex:function(t){return(this.getColumn(t)||{}).currentIndex},setColumnIndex:function(t,e){var n=this.getColumn(t);n&&n.setIndex(e)},getColumnValues:function(t){return(this.children[t]||{}).options},setColumnValues:function(t,e){var n=this.children[t];n&&JSON.stringify(n.options)!==JSON.stringify(e)&&(n.options=e,n.setIndex(0))},getValues:function(){return this.children.map(function(t){return t.getValue()})},setValues:function(t){var e=this;t.forEach(function(t,n){e.setColumnValue(n,t)})},getIndexes:function(){return this.children.map(function(t){return t.currentIndex})},setIndexes:function(t){var e=this;t.forEach(function(t,n){e.setColumnIndex(n,t)})},onConfirm:function(){this.children.map(function(t){return t.stopMomentum()}),this.emit("confirm")},onCancel:function(){this.emit("cancel")}},render:function(t){var e=this,n=this.itemHeight,i=n*this.visibleItemCount,s=this.simple?[this.columns]:this.columns,o={height:n+"px"},r={height:i+"px"},u={backgroundSize:"100% "+(i-n)/2+"px"},h=this.showToolbar&&t("div",{class:[c["g"],k("toolbar")]},[this.slots()||[t("div",{attrs:{role:"button",tabindex:"0"},class:k("cancel"),on:{click:this.onCancel}},[this.cancelButtonText||j("cancel")]),this.slots("title")||this.title&&t("div",{class:["van-ellipsis",k("title")]},[this.title]),t("div",{attrs:{role:"button",tabindex:"0"},class:k("confirm"),on:{click:this.onConfirm}},[this.confirmButtonText||j("confirm")])]]);return t("div",{class:k()},["top"===this.toolbarPosition?h:t(),this.loading?t(l["a"],{class:k("loading"),attrs:{color:c["a"]}}):t(),t("div",{class:k("columns"),style:r,on:{touchmove:a["c"]}},[s.map(function(n,i){return t(y,{attrs:{valueKey:e.valueKey,className:n.className,itemHeight:e.itemHeight,defaultIndex:n.defaultIndex||e.defaultIndex,visibleItemCount:e.visibleItemCount,initialOptions:e.simple?n:n.values},on:{change:function(){e.onChange(i)}}})}),t("div",{class:k("mask"),style:u}),t("div",{class:[c["h"],k("frame")],style:o})]),"bottom"===this.toolbarPosition?h:t()])}})},a506:function(t,e,n){"use strict";function i(t,e,n){return e in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}n.d(e,"a",function(){return i})},a710:function(t,e,n){"use strict";n("b628"),n("6025"),n("8564"),n("fa69"),n("e2bb"),n("451a"),n("6fc8")},b2d4:function(t,e,n){"use strict";n.d(e,"a",function(){return i});var i={title:String,loading:Boolean,showToolbar:Boolean,cancelButtonText:String,confirmButtonText:String,visibleItemCount:{type:Number,default:5},itemHeight:{type:Number,default:44}}},c73c:function(t,e,n){"use strict";var i=n("0376"),s=n.n(i);s.a},f8e2:function(t,e,n){}}]);