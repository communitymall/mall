(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-b80f85fa"],{"02de":function(t,i,e){"use strict";function n(t){return"none"===window.getComputedStyle(t).display||null===t.offsetParent}e.d(i,"a",function(){return n})},"28fb":function(t,i,e){"use strict";e.r(i);var n=function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("div",{staticClass:"goods_topic_list"},[e("van-list",{attrs:{finished:t.finished,"immediate-check":!1,"finished-text":"没有更多了"},on:{load:t.getTopicList},model:{value:t.loading,callback:function(i){t.loading=i},expression:"loading"}},t._l(t.list,function(i,n){return e("div",{key:n,staticClass:"topic-info",on:{click:function(e){return t.itemClick(i.id)}}},[e("div",{staticClass:"name"},[e("img",{staticClass:"img",attrs:{src:i.picUrl,"background-size":"cover"}}),e("div",{staticClass:"info-box"},[e("div",{staticClass:"txt"},[t._v(t._s(i.title))]),e("div",{staticClass:"line"}),e("div",{staticClass:"price"},[t._v("阅读次数："+t._s(i.readCount))])])]),e("div",{staticClass:"desc"},[t._v("\n        "+t._s(i.subtitle)+"\n      ")])])}),0)],1)},s=[],o=e("bd86"),c=(e("7f7f"),e("2994"),e("2bdd")),a=e("75fc"),r=e("4ec3"),l={data:function(){return{list:[],page:0,limit:10,loading:!1,finished:!1}},created:function(){this.init()},methods:{init:function(){this.page=0,this.list=[],this.getTopicList()},getTopicList:function(){var t=this;this.page++,Object(r["ib"])({page:this.page,limit:this.limit}).then(function(i){var e;(e=t.list).push.apply(e,Object(a["a"])(i.data.data.list)),t.loading=!1,t.finished=i.data.data.page>=i.data.data.pages})},itemClick:function(t){this.$router.push("/items/topic/".concat(t))}},components:Object(o["a"])({},c["a"].name,c["a"])},d=l,h=(e("ba0b"),e("2877")),f=Object(h["a"])(d,n,s,!1,null,"a771fc86",null);i["default"]=f.exports},2994:function(t,i,e){"use strict";e("68ef"),e("c0c2")},"2bdd":function(t,i,e){"use strict";var n=e("d282"),s=e("02de"),o=e("5fbe"),c=e("a8c1"),a=e("543e"),r=Object(n["a"])("list"),l=r[0],d=r[1],h=r[2];i["a"]=l({mixins:[Object(o["a"])(function(t){this.scroller||(this.scroller=Object(c["c"])(this.$el)),t(this.scroller,"scroll",this.check)})],model:{prop:"loading"},props:{error:Boolean,loading:Boolean,finished:Boolean,errorText:String,loadingText:String,finishedText:String,immediateCheck:{type:Boolean,default:!0},offset:{type:Number,default:300},direction:{type:String,default:"down"}},mounted:function(){this.immediateCheck&&this.$nextTick(this.check)},watch:{loading:function(){this.$nextTick(this.check)},finished:function(){this.$nextTick(this.check)}},methods:{check:function(){if(!(this.loading||this.finished||this.error)){var t,i=this.$el,e=this.scroller,n=this.offset,o=this.direction;t=e.getBoundingClientRect?e.getBoundingClientRect():{top:0,bottom:e.innerHeight};var c=t.bottom-t.top;if(!c||Object(s["a"])(i))return!1;var a=!1,r=this.$refs.placeholder.getBoundingClientRect();a="up"===o?r.top-t.top<=n:r.bottom-t.bottom<=n,a&&(this.$emit("input",!0),this.$emit("load"))}},clickErrorText:function(){this.$emit("update:error",!1),this.$nextTick(this.check)}},render:function(){var t=arguments[0],i=t("div",{ref:"placeholder",class:d("placeholder")});return t("div",{class:d(),attrs:{role:"feed","aria-busy":this.loading}},["down"===this.direction?this.slots():i,this.loading&&t("div",{class:d("loading"),key:"loading"},[this.slots("loading")||t(a["a"],{attrs:{size:"16"}},[this.loadingText||h("loading")])]),this.finished&&this.finishedText&&t("div",{class:d("finished-text")},[this.finishedText]),this.error&&this.errorText&&t("div",{on:{click:this.clickErrorText},class:d("error-text")},[this.errorText]),"up"===this.direction?this.slots():i])}})},"2e28":function(t,i,e){},"454f":function(t,i,e){e("46a7");var n=e("584a").Object;t.exports=function(t,i,e){return n.defineProperty(t,i,e)}},"46a7":function(t,i,e){var n=e("63b6");n(n.S+n.F*!e("8e60"),"Object",{defineProperty:e("d9f6").f})},"85f2":function(t,i,e){t.exports=e("454f")},ba0b:function(t,i,e){"use strict";var n=e("2e28"),s=e.n(n);s.a},bd86:function(t,i,e){"use strict";e.d(i,"a",function(){return o});var n=e("85f2"),s=e.n(n);function o(t,i,e){return i in t?s()(t,i,{value:e,enumerable:!0,configurable:!0,writable:!0}):t[i]=e,t}},c0c2:function(t,i,e){}}]);