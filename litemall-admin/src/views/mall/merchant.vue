<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.id" clearable class="filter-item" style="width: 200px;" placeholder="请输入门店ID"/>
      <el-input
        v-model="listQuery.merchantName"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入门店名称"/>
      <el-select
        v-model="listQuery.merchantStatusArray"
        multiple
        style="width: 200px"
        class="filter-item"
        placeholder="请选择门店状态">
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value"/>
      </el-select>
      <el-button
        v-permission="['GET /admin/merchant/list']"
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

      <el-table-column align="center" min-width="100" label="门店编号" prop="id"/>

      <el-table-column align="center" label="门店名称" prop="merchantName"/>

      <el-table-column align="center" label="门店状态" prop="orderStatus">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.merchantStatus | merchantStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="门店负责人" prop="merchantLeader"/>

      <el-table-column align="center" label="门店联系手机" prop="merchantPhone"/>

      <el-table-column align="center" label="门店营业执照编号" prop="merchantCode"/>

      <el-table-column align="center" label="门店地址" prop="merchantAddress"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['GET /admin/merchant/detail']"
            type="primary"
            size="mini"
            @click="handleDetail(scope.row)">详情
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

    <!-- 门店详情对话框 -->
    <el-dialog :visible.sync="merchantDialogVisible" :data="merchantDetail" title="门店详情" width="800">
      <el-form :data="merchantDetail" label-position="left">
        <el-form-item label="门店编号">
          <span>{{ merchantDetail.id }}</span>
        </el-form-item>
        <el-form-item label="门店名称">
          <span>{{ merchantDetail.merchantName }}</span>
        </el-form-item>
        <el-form-item label="门店地址">
          <span>{{ merchantDetail.merchantAddress }}</span>
        </el-form-item>
        <el-form-item label="门店营业执照编号">
          <span>{{ merchantDetail.merchantCode }}</span>
        </el-form-item>
        <el-form-item label="门店负责人">
          <span>{{ merchantDetail.merchantLeader }}</span>
        </el-form-item>
        <el-form-item label="门店联系电话">
          <span>{{ merchantDetail.merchantPhone }}</span>
        </el-form-item>
        <el-form-item label="门店图片">
          <img :src="merchantDetail.merchantPic" width="40">
        </el-form-item>

        <el-form-item v-if="merchantDetail.merchantStatus==0" label="操作">
          <el-button type="primary" @click="approved(merchantDetail.id)">通过</el-button>
          <el-button align="center" type="primary" @click="confirmShip(merchantDetail.id)">驳回</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { listMerchant, detailMerchant, auditMerchant } from '@/api/merchant'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

const statusMap = {
  0: '待审核',
  1: '审核未通过',
  2: '审核通过'
}

export default {
  name: 'Merchant',
  components: { Pagination },
  filters: {
    merchantStatusFilter(status) {
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
        merchantName: undefined,
        merchantStatusArray: [],
        sort: 'id',
        order: 'desc'
      },
      statusMap,
      merchantDialogVisible: false,
      merchantDetail: {},
      // ，门店审核
      approvedForm: {
        merchantStatus: undefined,
        id: undefined
      },
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
      listMerchant(this.listQuery).then(response => {
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
      detailMerchant(row.id).then(response => {
        this.merchantDetail = response.data.data
      })
      this.merchantDialogVisible = true
    },
    // 门店审核通过的方法
    approved(id) {
      this.approvedForm.id = id
      this.approvedForm.merchantStatus = 2
      auditMerchant(this.approvedForm).then(response => {
        this.merchantDialogVisible = false
        this.$notify.success({
          title: '操作成功',
          message: '该门店通过审核！'
        })
        this.getList()
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      })
    },
    // 门店没有通过审核通过的方法
    confirmShip(id) {
      this.approvedForm.id = id
      this.approvedForm.merchantStatus = 1
      auditMerchant(this.approvedForm).then(response => {
        this.merchantDialogVisible = false
        this.$notify.success({
          title: '操作成功',
          message: '该门店没有通过审核！'
        })
        this.getList()
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      })
    },

    handleDownload() {
      this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['门店编号', '门店名称', '门店状态', '门店负责人', '门店联系手机', '门店营业执照编号', '门店地址']
          const filterVal = ['id', 'merchantName', 'merchantStatus', 'merchantLeader', 'merchantPhone', 'merchantCode', 'merchantAddress']
          excel.export_json_to_excel2(tHeader, this.list, filterVal, '门店信息')
          this.downloadLoading = false
        })
    }
  }
}
</script>
