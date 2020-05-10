(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-405b"],{"9kER":function(t,e,i){"use strict";i.r(e);var a=i("FyfS"),n=i.n(a),s=i("P2sY"),o=i.n(s),l=i("t3Un");var r={name:"Issue",components:{Pagination:i("Mz3J").a},data:function(){return{list:null,total:0,listLoading:!0,listQuery:{page:1,limit:20,question:void 0,sort:"add_time",order:"desc"},dataForm:{id:void 0,question:"",answer:""},dialogFormVisible:!1,dialogStatus:"",textMap:{update:"编辑",create:"创建"},rules:{question:[{required:!0,message:"问题不能为空",trigger:"blur"}],answer:[{required:!0,message:"回复不能为空",trigger:"blur"}]},downloadLoading:!1}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.listLoading=!0,function(t){return Object(l.a)({url:"/issue/list",method:"get",params:t})}(this.listQuery).then(function(e){t.list=e.data.data.list,t.total=e.data.data.total,t.listLoading=!1}).catch(function(){t.list=[],t.total=0,t.listLoading=!1})},handleFilter:function(){this.listQuery.page=1,this.getList()},resetForm:function(){this.dataForm={id:void 0,question:"",answer:""}},handleCreate:function(){var t=this;this.resetForm(),this.dialogStatus="create",this.dialogFormVisible=!0,this.$nextTick(function(){t.$refs.dataForm.clearValidate()})},createData:function(){var t=this;this.$refs.dataForm.validate(function(e){e&&function(t){return Object(l.a)({url:"/issue/create",method:"post",data:t})}(t.dataForm).then(function(e){t.list.unshift(e.data.data),t.dialogFormVisible=!1,t.$notify.success({title:"成功",message:"创建成功"})}).catch(function(e){t.$notify.error({title:"失败",message:e.data.errmsg})})})},handleUpdate:function(t){var e=this;this.dataForm=o()({},t),this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick(function(){e.$refs.dataForm.clearValidate()})},updateData:function(){var t=this;this.$refs.dataForm.validate(function(e){e&&function(t){return Object(l.a)({url:"/issue/update",method:"post",data:t})}(t.dataForm).then(function(){var e=!0,i=!1,a=void 0;try{for(var s,o=n()(t.list);!(e=(s=o.next()).done);e=!0){var l=s.value;if(l.id===t.dataForm.id){var r=t.list.indexOf(l);t.list.splice(r,1,t.dataForm);break}}}catch(t){i=!0,a=t}finally{try{!e&&o.return&&o.return()}finally{if(i)throw a}}t.dialogFormVisible=!1,t.$notify.success({title:"成功",message:"更新成功"})}).catch(function(e){t.$notify.error({title:"失败",message:e.data.errmsg})})})},handleDelete:function(t){var e=this;(function(t){return Object(l.a)({url:"/issue/delete",method:"post",data:t})})(t).then(function(i){e.$notify.success({title:"成功",message:"删除成功"});var a=e.list.indexOf(t);e.list.splice(a,1)}).catch(function(t){e.$notify.error({title:"失败",message:t.data.errmsg})})},handleDownload:function(){var t=this;this.downloadLoading=!0,Promise.all([i.e("chunk-0d49"),i.e("chunk-2088")]).then(i.bind(null,"S/jZ")).then(function(e){e.export_json_to_excel2(["问题ID","问题内容","问题回复"],t.list,["id","question","answer"],"常见问题信息"),t.downloadLoading=!1})}}},d=i("KHd+"),u=Object(d.a)(r,function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"app-container"},[i("div",{staticClass:"filter-container"},[i("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{clearable:"",placeholder:"请输入问题"},model:{value:t.listQuery.question,callback:function(e){t.$set(t.listQuery,"question",e)},expression:"listQuery.question"}}),t._v(" "),i("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["GET /admin/issue/list"],expression:"['GET /admin/issue/list']"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:t.handleFilter}},[t._v("查找")]),t._v(" "),i("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["POST /admin/issue/create"],expression:"['POST /admin/issue/create']"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-edit"},on:{click:t.handleCreate}},[t._v("添加")]),t._v(" "),i("el-button",{staticClass:"filter-item",attrs:{loading:t.downloadLoading,type:"primary",icon:"el-icon-download"},on:{click:t.handleDownload}},[t._v("导出")])],1),t._v(" "),i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],attrs:{data:t.list,"element-loading-text":"正在查询中。。。",border:"",fit:"","highlight-current-row":""}},[i("el-table-column",{attrs:{align:"center",width:"100px",label:"问题ID",prop:"id",sortable:""}}),t._v(" "),i("el-table-column",{attrs:{align:"center","min-width":"200px",label:"问题内容",prop:"question"}}),t._v(" "),i("el-table-column",{attrs:{align:"center","min-width":"400px",label:"问题回复",prop:"answer"}}),t._v(" "),i("el-table-column",{attrs:{align:"center",label:"操作",width:"250","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["POST /admin/issue/update"],expression:"['POST /admin/issue/update']"}],attrs:{type:"primary",size:"mini"},on:{click:function(i){t.handleUpdate(e.row)}}},[t._v("编辑")]),t._v(" "),i("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["POST /admin/issue/delete"],expression:"['POST /admin/issue/delete']"}],attrs:{type:"danger",size:"mini"},on:{click:function(i){t.handleDelete(e.row)}}},[t._v("删除")])]}}])})],1),t._v(" "),i("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.listQuery.page,limit:t.listQuery.limit},on:{"update:page":function(e){t.$set(t.listQuery,"page",e)},"update:limit":function(e){t.$set(t.listQuery,"limit",e)},pagination:t.getList}}),t._v(" "),i("el-dialog",{attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[i("el-form",{ref:"dataForm",staticStyle:{width:"400px","margin-left":"50px"},attrs:{rules:t.rules,model:t.dataForm,"status-icon":"","label-position":"left","label-width":"100px"}},[i("el-form-item",{attrs:{label:"问题",prop:"question"}},[i("el-input",{model:{value:t.dataForm.question,callback:function(e){t.$set(t.dataForm,"question",e)},expression:"dataForm.question"}})],1),t._v(" "),i("el-form-item",{attrs:{label:"回复",prop:"answer"}},[i("el-input",{attrs:{rows:8,type:"textarea",placeholder:"请输入内容"},model:{value:t.dataForm.answer,callback:function(e){t.$set(t.dataForm,"answer",e)},expression:"dataForm.answer"}})],1)],1),t._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取消")]),t._v(" "),"create"==t.dialogStatus?i("el-button",{attrs:{type:"primary"},on:{click:t.createData}},[t._v("确定")]):i("el-button",{attrs:{type:"primary"},on:{click:t.updateData}},[t._v("确定")])],1)],1)],1)},[],!1,null,null,null);u.options.__file="issue.vue";e.default=u.exports},Y5bG:function(t,e,i){"use strict";i.d(e,"a",function(){return n}),Math.easeInOutQuad=function(t,e,i,a){return(t/=a/2)<1?i/2*t*t+e:-i/2*(--t*(t-2)-1)+e};var a=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)};function n(t,e,i){var n=document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop,s=t-n,o=0;e=void 0===e?500:e;!function t(){o+=20,function(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}(Math.easeInOutQuad(o,n,s,e)),o<e?a(t):i&&"function"==typeof i&&i()}()}}}]);