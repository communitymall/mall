<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>
      <el-input v-model="listQuery.orderSn" clearable class="filter-item" style="width: 200px;" placeholder="请输入订单编号"/>
      <el-select
        v-model="listQuery.orderStatusArray"
        multiple
        style="width: 200px"
        class="filter-item"
        placeholder="请选择订单状态">
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value"/>
      </el-select>
      <el-button v-permission="['GET /admin/order/list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找
      </el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出
      </el-button>

    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" min-width="100" label="订单编号" prop="orderSn"/>

      <van-checkbox :key="orderSn" :name="orderSn" v-model="orderSn"/>

      <el-table-column align="center" label="用户ID" prop="userId"/>

      <el-table-column align="center" label="订单状态" prop="orderStatus">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.orderStatus | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单金额" prop="orderPrice"/>

      <el-table-column align="center" label="支付金额" prop="actualPrice"/>

      <el-table-column align="center" label="支付时间" prop="payTime"/>

      <el-table-column align="center" label="物流单号" prop="shipSn"/>

      <el-table-column align="center" label="物流公司" prop="shipChannel"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['GET /admin/order/detail']"
            type="primary"
            size="mini"
            @click="handleDetail(scope.row)">详情
          </el-button>
          <el-button
            v-permission="['POST /admin/order/refund']"
            v-if="scope.row.orderStatus==8"
            type="primary"
            size="mini"
            @click="handleRefund(scope.row)">退款
          </el-button>
          <el-button
            v-permission="['POST /admin/order/approved']"
            v-if="scope.row.orderStatus==0"
            type="primary"
            size="mini"
            @click="auditOrder(scope.row)">审核
          </el-button>
          <el-button
            v-permission="['POST /admin/order/approved']"
            v-if="scope.row.orderStatus==1"
            type="primary"
            size="mini"
            @click="completeGoods(scope.row)">已备货
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"/>

    <!-- 订单详情对话框 -->
    <el-dialog :visible.sync="orderDialogVisible" title="订单详情" width="800">

      <el-form :data="orderDetail" label-position="left">
        <el-form-item label="订单编号">
          <span>{{ orderDetail.order.orderSn }}</span>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-tag>{{ orderDetail.order.orderStatus | orderStatusFilter }}</el-tag>
        </el-form-item>
        <el-form-item label="订单用户">
          <span>{{ orderDetail.user.nickname }}</span>
        </el-form-item>
        <el-form-item label="用户留言">
          <span>{{ orderDetail.order.message }}</span>
        </el-form-item>
        <el-form-item label="收货信息"><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;收货人:{{ orderDetail.order.consignee }} </span><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;手机号:{{ orderDetail.order.mobile }}</span><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;地址:{{ orderDetail.order.address }}</span><br>
        </el-form-item>
        <el-form-item label="商品信息">
          <el-table :data="orderDetail.orderGoods" border fit highlight-current-row>
            <el-table-column align="center" label="商品名称" prop="goodsName"/>
            <el-table-column align="center" label="商品编号" prop="goodsSn"/>
            <el-table-column align="center" label="货品规格" prop="specifications"/>
            <el-table-column align="center" label="货品价格" prop="price"/>
            <el-table-column align="center" label="货品数量" prop="number"/>
            <el-table-column align="center" label="货品图片" prop="picUrl">
              <template slot-scope="scope">
                <img :src="scope.row.picUrl" width="40">
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item label="费用信息">
          <span>
            (实际费用){{ orderDetail.order.actualPrice }}元 =
            (商品总价){{ orderDetail.order.goodsPrice }}元 +
            (快递费用){{ orderDetail.order.freightPrice }}元 -
            (优惠减免){{ orderDetail.order.couponPrice }}元 -
            (积分减免){{ orderDetail.order.integralPrice }}元
          </span>
        </el-form-item>
        <el-form-item label="支付信息">
          <span>支付渠道:微信支付 &nbsp;&nbsp;支付时间:{{ orderDetail.order.payTime }}</span>
          <!--          <span>支付时间:{{ orderDetail.order.payTime }}</span>-->
        </el-form-item>
        <el-form-item label="快递信息"><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;物流公司:{{ orderDetail.order.shipChannel }}</span><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;物流单号:{{ orderDetail.order.shipSn }}</span><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;发货时间:{{ orderDetail.order.shipTime }}</span><br>
        </el-form-item>
        <el-form-item label="收货信息">
          <span>确认收货时间:{{ orderDetail.order.confirmTime }}</span>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 订单审核对话框 -->
    <el-dialog :visible.sync="auditOrderDialogVisible" title="订单详情" width="800">
      <el-form
        ref="approvedForm"
        :model="approvedForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;">
        <el-form :data="orderDetail" label-position="left">
          <el-form-item label="订单编号">
            <span>{{ orderDetail.order.orderSn }}</span>
          </el-form-item>
        </el-form>

        <el-form-item label="订单状态">
          <el-tag>{{ orderDetail.order.orderStatus | orderStatusFilter }}</el-tag>
        </el-form-item>
        <el-form-item label="订单用户">
          <span>{{ orderDetail.user.nickname }}</span>
        </el-form-item>
        <el-form-item label="用户留言">
          <span>{{ orderDetail.order.message }}</span>
        </el-form-item>
        <el-form-item label="收货信息"><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;收货人:{{ orderDetail.order.consignee }} </span><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;手机号:{{ orderDetail.order.mobile }}</span><br>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;地址:{{ orderDetail.order.address }}</span><br>
        </el-form-item>
        <el-form-item label="商品信息">
          <el-table :data="orderDetail.orderGoods" border fit highlight-current-row>
            <el-table-column align="center" label="商品名称" prop="goodsName"/>
            <el-table-column align="center" label="商品编号" prop="goodsSn"/>
            <el-table-column align="center" label="货品规格" prop="specifications"/>
            <el-table-column align="center" label="货品价格" prop="price"/>
            <el-table-column align="center" label="货品数量" prop="number"/>
            <el-table-column align="center" label="货品图片" prop="picUrl">
              <template slot-scope="scope">
                <img :src="scope.row.picUrl" width="40">
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item label="费用信息">
          <span>
            (实际费用){{ orderDetail.order.actualPrice }}元 =
            (商品总价){{ orderDetail.order.goodsPrice }}元 +
            (快递费用){{ orderDetail.order.freightPrice }}元 -
            (优惠减免){{ orderDetail.order.couponPrice }}元 -
            (积分减免){{ orderDetail.order.integralPrice }}元
          </span>
        </el-form-item>

        <el-form-item label="操作">
          <el-button type="primary" @click="approved">通过</el-button>
          <el-button align="center" type="primary" @click="confirmShip">驳回</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 退款对话框 -->
    <el-dialog :visible.sync="refundDialogVisible" title="退款">
      <el-form ref="refundForm" :model="refundForm" status-icon label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="退款金额" prop="refundMoney">
          <el-input v-model="refundForm.refundMoney" :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="refundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRefund">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listOrder, unApprovedOrder, refundOrder, detailOrder, approvedOrder, completeGoods } from '@/api/order'
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
  10: '交易完成',
  11: '订单未过审'
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
      statusMap,
      orderDialogVisible: false,
      orderDetail: {
        order: {},
        user: {},
        orderGoods: []
      },
      shipForm: {
        orderId: undefined,
        shipChannel: undefined,
        shipSn: undefined
      },
      shipDialogVisible: false,
      refundForm: {
        orderId: undefined,
        refundMoney: undefined
      },

      // 订单审核
      approvedForm: {
        orderSn: undefined,
        orderid: undefined
      },

      auditOrderDialogVisible: false,

      refundDialogVisible: false,
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true
      listOrder(this.listQuery).then(response => {
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
    handleDetail(row) {
      detailOrder(row.id).then(response => {
        this.orderDetail = response.data.data
      })
      this.orderDialogVisible = true
    },
    handleShip(row) {
      this.shipForm.orderId = row.id
      this.shipForm.shipChannel = row.shipChannel
      this.shipForm.shipSn = row.shipSn

      this.shipDialogVisible = true
      this.$nextTick(() => {
        this.$refs['shipForm'].clearValidate()
      })
    },
    auditOrder(row) {
      this.approvedForm.orderSn = row.orderSn
      this.approvedForm.orderId = row.id
      detailOrder(row.id).then(response => {
        this.orderDetail = response.data.data
      })
      this.auditOrderDialogVisible = true
    },
    // 审核通过的方法
    approved() {
      this.$confirm('此操作将货到付款的订单通过审核, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs['approvedForm'].validate((valid) => {
          if (valid) {
            approvedOrder(this.approvedForm).then(response => {
              this.auditOrderDialogVisible = false
              this.$notify.success({
                title: '成功',
                message: '订单审核成功'
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
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    // 订单备货完成
    completeGoods(row) {
      this.$confirm('此操作将完成商品的备货, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.approvedForm.orderSn = row.orderSn
        this.approvedForm.orderId = row.id
        completeGoods(this.approvedForm).then(response => {
          this.$notify.success({
            title: '成功',
            message: '操作成功'
          })
          this.getList()
        }).catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },

    confirmShip() {
      this.$confirm('此操作将该订单驳回, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        unApprovedOrder(this.approvedForm).then(response => {
          this.auditOrderDialogVisible = false
          this.$notify.success({
            title: '成功',
            message: '操作成功'
          })
          this.getList()
        }).catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleRefund(row) {
      this.refundForm.orderId = row.id
      this.refundForm.refundMoney = row.actualPrice

      this.refundDialogVisible = true
      this.$nextTick(() => {
        this.$refs['refundForm'].clearValidate()
      })
    },
    confirmRefund() {
      this.$refs['refundForm'].validate((valid) => {
        if (valid) {
          refundOrder(this.refundForm).then(response => {
            this.refundDialogVisible = false
            this.$notify.success({
              title: '成功',
              message: '确认退款成功'
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
          const tHeader = ['订单编号', '用户ID', '订单状态', '订单金额', '支付金额', '支付时间', '物流单号', '物流公司']
          const filterVal = ['orderSn', 'userId', 'orderStatus', 'orderPrice', 'actualPrice', 'payTime', 'shipSn', 'shipChannel']
          excel.export_json_to_excel2(tHeader, this.list, filterVal, '订单信息')
          this.downloadLoading = false
        })
    }
  }
}
</script>
