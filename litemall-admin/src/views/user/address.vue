<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.merchantName" clearable class="filter-item" style="width: 200px;" placeholder="请输入门店名称"/>
      <el-input v-model="listQuery.consigneeName" clearable class="filter-item" style="width: 200px;" placeholder="请输入收货人名称"/>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>
      <el-table-column align="center" width="100px" label="门店ID" prop="id" sortable/>
      I
      <!--      <el-table-column align="center" min-width="100px" label="收货人ID" prop="consigneeId"/>-->

      <el-table-column align="center" min-width="100px" label="收货人名称" prop="consigneeName"/>

      <el-table-column align="center" min-width="100px" label="手机号码" prop="consigneePhone"/>

      <el-table-column align="center" min-width="100px" label="门店名称" prop="merchantName"/>

      <!--      <el-table-column align="center" min-width="300px" label="门店名称">-->
      <!--        <template slot-scope="scope">-->
      <!--          {{ scope.row.province + scope.row.city + scope.row.county }}-->
      <!--        </template>-->
      <!--      </el-table-column>-->

      <el-table-column align="center" min-width="300px" label="门店地址" prop="merchantAddress"/>

      <!--      <el-table-column align="center" min-width="80px" label="默认" prop="isDefault">-->
      <!--        <template slot-scope="scope">-->
      <!--          {{ scope.row.isDefault ? '是' : '否' }}-->
      <!--        </template>-->
      <!--      </el-table-column>-->

      <el-table-column align="center" property="picUrl" label="门店图片">
        <template slot-scope="scope">
          <img v-if="scope.row.merchantPic" :src="scope.row.merchantPic" width="80">
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { listAddress } from '@/api/user'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'UserAddress',
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        merchantName: undefined,
        consigneeName: undefined,
        userId: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listAddress(this.listQuery).then(response => {
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
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['门店ID', '收货人名称', '手机号码', '门店名称', '门店地址']
        const filterVal = ['id', 'consigneeName', 'consigneePhone', 'merchantName', 'merchantAddress']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '门店地址信息')
        this.downloadLoading = false
      })
    }
  }
}
</script>
