<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>
      <el-input v-model="listQuery.orderSn" clearable class="filter-item" style="width: 200px;" placeholder="请输入订单编号"/>
      <el-button
        v-permission="['GET /admin/order/list']"
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

      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="speedupCheckbox">发货
      </el-button>
    </div>
    <!-- 查询结果 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange">

      <el-table-column v-model="multipleSelection" type="selection" width="35"/>

      <el-table-column align="center" min-width="100" label="订单编号" prop="orderSn"/>

      <el-table-column align="center" label="用户ID" prop="userId"/>

      <el-table-column align="center" label="订单状态" prop="orderStatus">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.orderStatus | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="支付金额" prop="actualPrice"/>

      <el-table-column align="center" label="支付时间" prop="payTime"/>

      <el-table-column align="center" label="收货人" prop="consignee"/>

      <el-table-column align="center" label="收货人联系电话" prop="mobile"/>

      <el-table-column align="center" label="收货地址" prop="address"/>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"/>
    <!-- 发货对话框 -->
    <el-dialog :visible.sync="shipDialogVisible" title="发货">
      <el-form
        ref="shipForm"
        :model="shipForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;">
        <el-form-item label="发货物流公司" prop="companyId">
          <el-select v-model="shipForm.companyId" @change="checkLicensePlateNumber">
            <el-option v-for="item in logisticsList" :key="item.id" :label="item.label" :value="item.id">{{ item.name }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运输车辆牌照" prop="companyId">
          <el-select v-model="shipForm.licensePlateNumber">
            <el-option v-for="item in logisticsTrucksList" :key="item.licensePlateNumber" :label="item.label" :value="item.licensePlateNumber">{{ item.licensePlateNumber }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运费" prop="freight">
          <el-input v-model="shipForm.freight"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="speedupData">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { shipOrder, checkDeliveryOrder } from '@/api/order'
import { createOrder, listLogistics, listLogisticsTrucks } from '@/api/logistics'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数
const statusMap = {
  0: '未审核',
  1: '未备货',
  2: '已备货',
  3: '未派送',
  4: '已发货',
  5: '已收货',
  6: '手动收货',
  7: '待支付',
  8: '取消订单',
  9: '订单超时',
  10: '交易完成'
}

export default {
  name: 'Order',
  components: { Pagination },
  filters: {
    orderStatusFilter(status) {
      return statusMap[status]
    }
  },
  data() {
    return {
      list: [],
      logisticsList: [],
      logisticsTrucksList: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        name: undefined,
        orderStatusArray: [],
        sort: 'add_time',
        order: 'desc'
      },

      formInline: {
        id: null,
        scene: '',
        project_name: '',
        submit_date: [new Date(), new Date()]

      },
      tableData5: [],
      multipleSelection: [],

      statusMap,
      orderDialogVisible: false,
      orderDetail: {
        order: {},
        user: {},
        orderGoods: []
      },
      shipForm: {
        orders: [
          {
            multipleSelection: []
          }
        ],
        companyId: undefined,
        licensePlateNumber: undefined,
        freight: undefined
      },

      shipDialogVisible: false,
      refundForm: {
        orderId: undefined,
        refundMoney: undefined
      },
      refundDialogVisible: false,
      downloadLoading: false,
      dForm: {
        companyId: undefined
      }
    }
  },
  created() {
    this.getList()
    this.init()
  },
  methods: {
    init: function() {
      listLogistics().then(response => {
        this.logisticsList = response.data.data.list
      })
    },

    checkLicensePlateNumber() {
      this.dForm.companyId = this.shipForm.companyId
      listLogisticsTrucks(this.dForm).then(response => {
        this.logisticsTrucksList = response.data.data.list
      })
    },

    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    speedupData() {
      var arr = this.multipleSelection
      const multis = []
      for (var i = 0; i < arr.length; i++) {
        multis.push(arr[i].orderSn)
        console.log(multis)
      }
      this.shipForm.orders = multis
      this.$refs['shipForm'].validate((valid) => {
        if (valid) {
          createOrder(this.shipForm).then(response => {
            this.shipDialogVisible = false
            this.$notify.success({
              title: '成功',
              message: '确认发货成功'
            })
            this.getList()
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }
      })
      // 以上为将数组中key值为id的数据遍历出来，然后，将所得到的数据传入mutis数组中，最后将multis数据提交至后端
      // var url = this.HOST + "/job";
      // this.$axios.post(url,{
      //   operate:'speedup',
      //   job_ids:multis
      // }).then(rs=>{
      //   if(multis.length!==null){
      //     this.$message.success("操作成功")
      //   }else{
      //     this.$message.error("操作失败")
      //   }
      //   console.log(rs);
      // })
      // this.$refs.multipleTable.clearSelection();            //操作完成清除勾选框
    },
    // 全速选择框
    speedupCheckbox() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: '请至少勾选一项，再进行操作',
          type: 'warning'
        })
      } else {
        // this.speedupData()
        this.shipDialogVisible = true
      }
    },

    checkPermission,
    getList() {
      this.listLoading = true
      checkDeliveryOrder(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleShip() {
      this.shipDialogVisible = true
      this.$nextTick(() => {
        this.$refs['shipForm'].clearValidate()
      })
    },
    confirmShip() {
      this.$refs['shipForm'].validate((valid) => {
        if (valid) {
          shipOrder(this.shipForm).then(response => {
            this.shipDialogVisible = false
            this.$notify.success({
              title: '成功',
              message: '确认发货成功'
            })
            this.getList()
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }
      })
    },

    handleDownload() {
      this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['订单编号', '用户ID', '订单状态', '收货人', '收货联系电话', '收货地址']
          const filterVal = ['orderSn', 'userId', 'orderStatus', 'consignee', 'mobile', 'address']
          excel.export_json_to_excel2(tHeader, this.list, filterVal, '待发货订单信息')
          this.downloadLoading = false
        })
    }
  }
}
</script>
