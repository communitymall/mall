<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.orderId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户订单编号"/>
      <el-input
        v-model="listQuery.transitId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入物流订单编号"/>
      <el-input
        v-model="listQuery.companyId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入物流公司编号"/>
      <el-input
        v-model="listQuery.licensePlateNumber"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入车牌照"/>
      <el-button
        v-permission="['GET /admin/logistics/listLogisticsOrder']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter">查找
      </el-button>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload">导出
      </el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>
      <el-table-column align="center" label="物流订单编号" prop="transitId" sortable/>

      <el-table-column align="center" label="所属物流公司ID" prop="companyId"/>

      <el-table-column align="center" label="车牌号" prop="licensePlateNumber"/>

      <el-table-column align="center" label="司机" prop="driver"/>

      <el-table-column align="center" label="运费" prop="freight"/>

      <el-table-column align="center" label="物流配送状态" prop="transitStatus"/>

      <el-table-column align="center" label="创建时间" prop="createTime"/>

      <el-table-column align="center" label="创建人" prop="createUser"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /admin/logistics/updateTruck']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)">详情
          </el-button>
          <!--          <el-button-->
          <!--            v-permission="['POST /admin/logistics/delTruck']"-->
          <!--            type="danger"-->
          <!--            size="mini"-->
          <!--            @click="handleDelete(scope.row)">详情-->
          <!--          </el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"/>

    <!-- 修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;">

        <el-form-item label="物流订单编号" prop="transitId" >
          <el-input v-model="dataForm.transitId" disabled="disabled"/>
        </el-form-item>
        <el-form-item label="运输车牌号" prop="licensePlateNumber">
          <el-input v-model="dataForm.licensePlateNumber"/>
        </el-form-item>
        <el-form-item label="物流运输状态" prop="transitStatus">
          <el-input v-model="dataForm.transitStatus" disabled="disabled"/>
        </el-form-item>
        <el-form-item label="运费" prop="freight">
          <el-input v-model="dataForm.freight"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

    <!--    &lt;!&ndash; detil详情的弹框 &ndash;&gt;-->
    <!--    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">-->
    <!--      <el-form-->
    <!--        ref="dataForm"-->
    <!--        :rules="rules"-->
    <!--        :model="dataForm"-->
    <!--        status-icon-->
    <!--        label-position="left"-->
    <!--        label-width="100px"-->
    <!--        style="width: 400px; margin-left:50px;">-->

    <!--        <el-form-item label="物流订单编号" prop="transitId">-->
    <!--          <el-input v-model="dataForm.transitId"/>-->
    <!--        </el-form-item>-->
    <!--        <el-form-item label="用户订单号" prop="orderSn">-->
    <!--          <el-input v-model="dataForm.orderSn"/>-->
    <!--        </el-form-item>-->
    <!--        <el-form-item label="运输状态" prop="shipStatus">-->
    <!--          <el-input v-model="dataForm.shipStatus"/>-->
    <!--        </el-form-item>-->
    <!--        <el-form-item label="创建时间" prop="detailCreateTime">-->
    <!--          <el-input v-model="dataForm.detailCreateTime"/>-->
    <!--        </el-form-item>-->
    <!--      </el-form>-->
    <!--      <div slot="footer" class="dialog-footer">-->
    <!--        <el-button @click="dialogFormVisible = false">取消</el-button>-->
    <!--        <el-button v-else type="primary" @click="updateData">确定</el-button>-->
    <!--      </div>-->
    <!--    </el-dialog>-->

  </div>
</template>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }

  .avatar {
    width: 145px;
    height: 145px;
    display: block;
  }
</style>

<script>
import { listLogisticsOrder, FindLogisticsTransit } from '@/api/logistics'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'LogisticsOrder',
  components: { Pagination },
  data() {
    return {
      uploadPath,
      logisticsDetailList: [],
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        transitId: undefined,
        orderId: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      dataForm: {
        transitId: undefined,
        id: undefined,
        licensePlateNumber: undefined,
        driver: undefined,
        freight: undefined,
        createUser: undefined,
        transitStatus: undefined,
        createTime: undefined,

        orderSn: undefined,
        shipStatus: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑订单信息',
        create: '添加车辆信息'
      },
      rules: {
        licensePlateNumber: [
          { required: true, message: '车牌号不能为空', trigger: 'blur' }
        ],
        driver: [
          { required: true, message: '司机不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { required: String, min: 11, max: 11, message: '手机号应该是11位', trigger: 'blur' }
        ]
      },
      downloadLoading: false
    }
  },
  computed: {
    headers() {
      return {
        'X-Litemall-Admin-Token': getToken()
      }
    }
  },
  created() {
    this.getList()
    this.init()
  },
  methods: {
    getList() {
      this.listLoading = true
      listLogisticsOrder(this.listQuery)
        .then(response => {
          this.list = response.data.data.list
          this.total = response.data.data.total
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.total = 0
          this.listLoading = false
        })
    },

    /*
      查询detail
       */
    getDetailList() {
      FindLogisticsTransit(this.dataForm.transitId)
        .then(response => {
          this.logisticsDetailList = response.data.data.list
          this.total = response.data.data.total
        })
        .catch(() => {
          this.list = []
          this.total = 0
        })
    },

    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        transitId: undefined,
        id: undefined,
        licensePlateNumber: undefined,
        driver: undefined,
        freight: undefined,
        createUser: undefined,
        transitStatus: undefined,
        createTime: undefined,

        orderSn: undefined,
        shipStatus: undefined
      }
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    uploadUrl: function(response) {
      this.dataForm.url = response.data.url
    },
    handleUpdate(row) {
      FindLogisticsTransit(row).then(response => {
        this.$notify.success({
          title: '成功',
          message: '查找成功'
        })
        // this.list.splice(index, 1)   //执行后删除当前选中的行
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      })

      this.dataForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // updateData() {
    //   this.$refs['dataForm'].validate(valid => {
    //     if (valid) {
    //       updateLogisticsTrucks(this.dataForm)
    //         .then(() => {
    //           for (const v of this.list) {
    //             if (v.id === this.dataForm.id) {
    //               const index = this.list.indexOf(v)
    //               this.list.splice(index, 1, this.dataForm)
    //               break
    //             }
    //           }
    //           this.dialogFormVisible = false
    //           this.$notify.success({
    //             title: '成功',
    //             message: '更新成功'
    //           })
    //         })
    //         .catch(response => {
    //           this.$notify.error({
    //             title: '失败',
    //             message: response.data.errmsg
    //           })
    //         })
    //     }
    //   })
    // },
    handleDownload() {
      this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = [
            '所属公司ID',
            '车牌号',
            '司机',
            '联系电话',
            '载重量（kg）',
            '创建人'
          ]
          const filterVal = [
            'companyId',
            'licensePlateNumber',
            'driver',
            'phone',
            'load',
            'createUser'
          ]
          excel.export_json_to_excel2(tHeader, this.list, filterVal, '车辆配置信息')
          this.downloadLoading = false
        })
    }
  }
}
</script>
