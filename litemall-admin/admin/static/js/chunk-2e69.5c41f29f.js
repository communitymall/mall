(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-2e69"],{PHx8:function(t,e,a){"use strict";a.r(e);var i=a("FyfS"),n=a.n(i),o=a("P2sY"),l=a.n(o),r=a("t3Un");var s=a("rs3x"),d=a("X4fA"),c={name:"Ad",components:{Pagination:a("Mz3J").a},data:function(){return{uploadPath:s.e,list:[],total:0,listLoading:!0,listQuery:{page:1,limit:20,name:void 0,content:void 0,sort:"add_time",order:"desc"},dataForm:{id:void 0,name:void 0,content:void 0,url:void 0,link:void 0,position:1,enabled:!0},dialogFormVisible:!1,dialogStatus:"",textMap:{update:"编辑",create:"创建"},rules:{name:[{required:!0,message:"广告标题不能为空",trigger:"blur"}],content:[{required:!0,message:"广告内容不能为空",trigger:"blur"}],url:[{required:!0,message:"广告链接不能为空",trigger:"blur"}]},downloadLoading:!1}},computed:{headers:function(){return{"X-Litemall-Admin-Token":Object(d.a)()}}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.listLoading=!0,function(t){return Object(r.a)({url:"/ad/list",method:"get",params:t})}(this.listQuery).then(function(e){t.list=e.data.data.list,t.total=e.data.data.total,t.listLoading=!1}).catch(function(){t.list=[],t.total=0,t.listLoading=!1})},handleFilter:function(){this.listQuery.page=1,this.getList()},resetForm:function(){this.dataForm={id:void 0,name:void 0,content:void 0,url:void 0,link:void 0,position:1,enabled:!0}},handleCreate:function(){var t=this;this.resetForm(),this.dialogStatus="create",this.dialogFormVisible=!0,this.$nextTick(function(){t.$refs.dataForm.clearValidate()})},uploadUrl:function(t){this.dataForm.url=t.data.url},checkFileSize:function(t){return!(t.size>1048576)||(this.$message.error(t.name+"文件大于1024KB，请选择小于1024KB大小的图片"),!1)},createData:function(){var t=this;this.$refs.dataForm.validate(function(e){e&&function(t){return Object(r.a)({url:"/ad/create",method:"post",data:t})}(t.dataForm).then(function(e){t.list.unshift(e.data.data),t.dialogFormVisible=!1,t.$notify.success({title:"成功",message:"创建成功"})}).catch(function(e){t.$notify.error({title:"失败",message:e.data.errmsg})})})},handleUpdate:function(t){var e=this;this.dataForm=l()({},t),this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick(function(){e.$refs.dataForm.clearValidate()})},updateData:function(){var t=this;this.$refs.dataForm.validate(function(e){e&&function(t){return Object(r.a)({url:"/ad/update",method:"post",data:t})}(t.dataForm).then(function(){var e=!0,a=!1,i=void 0;try{for(var o,l=n()(t.list);!(e=(o=l.next()).done);e=!0){var r=o.value;if(r.id===t.dataForm.id){var s=t.list.indexOf(r);t.list.splice(s,1,t.dataForm);break}}}catch(t){a=!0,i=t}finally{try{!e&&l.return&&l.return()}finally{if(a)throw i}}t.dialogFormVisible=!1,t.$notify.success({title:"成功",message:"更新广告成功"})}).catch(function(e){t.$notify.error({title:"失败",message:e.data.errmsg})})})},handleDelete:function(t){var e=this;(function(t){return Object(r.a)({url:"/ad/delete",method:"post",data:t})})(t).then(function(a){e.$notify.success({title:"成功",message:"删除成功"});var i=e.list.indexOf(t);e.list.splice(i,1)}).catch(function(t){e.$notify.error({title:"失败",message:t.data.errmsg})})},handleDownload:function(){var t=this;this.downloadLoading=!0,Promise.all([a.e("chunk-0d49"),a.e("chunk-2088")]).then(a.bind(null,"S/jZ")).then(function(e){e.export_json_to_excel2(["广告ID","广告标题","广告内容","广告图片","广告位置","活动链接","是否启用"],t.list,["id","name","content","url","postion","link","enabled"],"广告信息"),t.downloadLoading=!1})}}},u=(a("Z1GF"),a("KHd+")),m=Object(u.a)(c,function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"filter-container"},[a("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{clearable:"",placeholder:"请输入广告标题"},model:{value:t.listQuery.name,callback:function(e){t.$set(t.listQuery,"name",e)},expression:"listQuery.name"}}),t._v(" "),a("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{clearable:"",placeholder:"请输入广告内容"},model:{value:t.listQuery.content,callback:function(e){t.$set(t.listQuery,"content",e)},expression:"listQuery.content"}}),t._v(" "),a("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["GET /admin/ad/list"],expression:"['GET /admin/ad/list']"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:t.handleFilter}},[t._v("查找\n    ")]),t._v(" "),a("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["POST /admin/ad/create"],expression:"['POST /admin/ad/create']"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-edit"},on:{click:t.handleCreate}},[t._v("添加\n    ")]),t._v(" "),a("el-button",{staticClass:"filter-item",attrs:{loading:t.downloadLoading,type:"primary",icon:"el-icon-download"},on:{click:t.handleDownload}},[t._v("导出\n    ")])],1),t._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],attrs:{data:t.list,"element-loading-text":"正在查询中。。。",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{align:"center",label:"广告ID",prop:"id",sortable:""}}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"广告标题",prop:"name"}}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"广告内容",prop:"content"}}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"广告图片",prop:"url"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.url?a("img",{attrs:{src:e.row.url,width:"80"}}):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"广告位置",prop:"position"}}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"活动链接",prop:"link"}}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"是否启用",prop:"enabled"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",{attrs:{type:e.row.enabled?"success":"error"}},[t._v(t._s(e.row.enabled?"启用":"不启用"))])]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"center",label:"操作",width:"200","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["POST /admin/ad/update"],expression:"['POST /admin/ad/update']"}],attrs:{type:"primary",size:"mini"},on:{click:function(a){t.handleUpdate(e.row)}}},[t._v("编辑\n        ")]),t._v(" "),a("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["POST /admin/ad/delete"],expression:"['POST /admin/ad/delete']"}],attrs:{type:"danger",size:"mini"},on:{click:function(a){t.handleDelete(e.row)}}},[t._v("删除\n        ")])]}}])})],1),t._v(" "),a("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.listQuery.page,limit:t.listQuery.limit},on:{"update:page":function(e){t.$set(t.listQuery,"page",e)},"update:limit":function(e){t.$set(t.listQuery,"limit",e)},pagination:t.getList}}),t._v(" "),a("el-dialog",{attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[a("el-form",{ref:"dataForm",staticStyle:{width:"400px","margin-left":"50px"},attrs:{rules:t.rules,model:t.dataForm,"status-icon":"","label-position":"left","label-width":"100px"}},[a("el-form-item",{attrs:{label:"广告标题",prop:"name"}},[a("el-input",{model:{value:t.dataForm.name,callback:function(e){t.$set(t.dataForm,"name",e)},expression:"dataForm.name"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"广告内容",prop:"content"}},[a("el-input",{model:{value:t.dataForm.content,callback:function(e){t.$set(t.dataForm,"content",e)},expression:"dataForm.content"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"广告图片",prop:"url"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{headers:t.headers,action:t.uploadPath,"show-file-list":!1,"on-success":t.uploadUrl,"before-upload":t.checkFileSize,accept:".jpg,.jpeg,.png,.gif"}},[t.dataForm.url?a("img",{staticClass:"avatar",attrs:{src:t.dataForm.url}}):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"}),t._v(" "),a("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[t._v("只能上传jpg/png文件，且不超过1024kb")])])],1),t._v(" "),a("el-form-item",{attrs:{label:"广告位置",prop:"position"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:t.dataForm.position,callback:function(e){t.$set(t.dataForm,"position",e)},expression:"dataForm.position"}},[a("el-option",{attrs:{value:1,label:"首页"}})],1)],1),t._v(" "),a("el-form-item",{attrs:{label:"活动链接",prop:"link"}},[a("el-input",{model:{value:t.dataForm.link,callback:function(e){t.$set(t.dataForm,"link",e)},expression:"dataForm.link"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"是否启用",prop:"enabled"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:t.dataForm.enabled,callback:function(e){t.$set(t.dataForm,"enabled",e)},expression:"dataForm.enabled"}},[a("el-option",{attrs:{value:!0,label:"启用"}}),t._v(" "),a("el-option",{attrs:{value:!1,label:"不启用"}})],1)],1)],1),t._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取消")]),t._v(" "),"create"==t.dialogStatus?a("el-button",{attrs:{type:"primary"},on:{click:t.createData}},[t._v("确定")]):a("el-button",{attrs:{type:"primary"},on:{click:t.updateData}},[t._v("确定")])],1)],1)],1)},[],!1,null,null,null);m.options.__file="ad.vue";e.default=m.exports},Y5bG:function(t,e,a){"use strict";a.d(e,"a",function(){return n}),Math.easeInOutQuad=function(t,e,a,i){return(t/=i/2)<1?a/2*t*t+e:-a/2*(--t*(t-2)-1)+e};var i=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)};function n(t,e,a){var n=document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop,o=t-n,l=0;e=void 0===e?500:e;!function t(){l+=20,function(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}(Math.easeInOutQuad(l,n,o,e)),l<e?i(t):a&&"function"==typeof a&&a()}()}},Z1GF:function(t,e,a){"use strict";var i=a("tLs4");a.n(i).a},rs3x:function(t,e,a){"use strict";a.d(e,"c",function(){return n}),a.d(e,"a",function(){return o}),a.d(e,"d",function(){return l}),a.d(e,"b",function(){return r}),a.d(e,"e",function(){return s});var i=a("t3Un");function n(t){return Object(i.a)({url:"/storage/list",method:"get",params:t})}function o(t){return Object(i.a)({url:"/storage/create",method:"post",data:t})}function l(t){return Object(i.a)({url:"/storage/update",method:"post",data:t})}function r(t){return Object(i.a)({url:"/storage/delete",method:"post",data:t})}var s="https://39.97.235.28/admin/storage/create"},tLs4:function(t,e,a){}}]);