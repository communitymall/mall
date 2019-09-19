/*
物流公司的管理页面
*/

<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.id" clearable class="filter-item" style="width: 200px;" placeholder="请输入物流公司id"/>
      <el-input
        v-model="listQuery.name"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入物流公司名称"/>
      <el-button
        v-permission="['GET /admin/logistics/listCompany']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter">查找
      </el-button>
      <el-button
        v-permission="['POST /admin/logistics/addCompany']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate">添加
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

      <el-table-column align="center" label="物流公司ID" prop="id" sortable/>

      <el-table-column align="center" label="物流公司名称" prop="name"/>

      <el-table-column align="center" label="物流公司地址" prop="address"/>

      <el-table-column align="center" label="联系电话" prop="phone"/>

      <el-table-column align="center" label="联系人" prop="contact"/>

      <el-table-column align="center" label="客服电话" prop="serviceTel"/>

      <el-table-column align="center" label="公司性质" prop="logisticsType">
        <template slot-scope="scope">
          <el-tag :type="scope.row.logisticsType ? '0' : '1' ">{{ scope.row.logisticsType ? '自营' : '第三方' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建人" prop="createUser"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /admin/logistics/updateCompany']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)">编辑
          </el-button>
          <el-button
            v-permission="['POST /admin/logistics/delCompany']"
            type="danger"
            size="mini"
            @click="handleDelete(scope.row)">删除
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;">
        <el-form-item label="物流公司名称" prop="name">
          <el-input v-model="dataForm.name"/>
        </el-form-item>
        <el-form-item label="物流公司地址" prop="address">
          <el-input v-model="dataForm.address"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="dataForm.phone"/>
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="dataForm.contact"/>
        </el-form-item>
        <el-form-item label="客服电话" prop="serviceTel">
          <el-input v-model="dataForm.serviceTel"/>
        </el-form-item>
        <el-form-item label="公司性质" prop="logisticsType">
          <el-select v-model="dataForm.logisticsType" placeholder="请选择">
            <el-option :value="1" label="自营"/>
            <el-option :value="0" label="第三方"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

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
import { listLogistics, createLogistics, updateLogistics, deleteLogistics } from '@/api/logistics'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Logistics',
  components: { Pagination },
  data() {
    return {
      uploadPath,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined,
        contact: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      dataForm: {
        id: undefined,
        name: undefined,
        address: undefined,
        phone: undefined,
        contact: undefined,
        serviceTel: undefined,
        logisticsType: undefined,
        createUser: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        name: [
          { required: true, message: '物流公司名称不能为空', trigger: 'blur' }
        ],
        createUser: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        logisticsType: [{ required: true, message: '公司性质不能为空', trigger: 'blur' }]
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
  },
  methods: {
    getList() {
      this.listLoading = true
      listLogistics(this.listQuery)
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
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: undefined,
        address: undefined,
        phone: undefined,
        contact: undefined,
        serviceTel: undefined,
        logisticsType: 0,
        createUser: undefined
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
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          createLogistics(this.dataForm)
            .then(response => {
              this.list.unshift(response.data.data)
              this.dialogFormVisible = false
              this.getList()
              this.$notify.success({
                title: '成功',
                message: '创建成功'
              })
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
        }
      })
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateLogistics(this.dataForm)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.dataForm)
                  break
                }
              }
              this.dialogFormVisible = false
              this.getList()
              this.$notify.success({
                title: '成功',
                message: '更新成功'
              })
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
        }
      })
    },
    handleDelete(row) {
      deleteLogistics(row)
        .then(response => {
          this.$notify.success({
            title: '成功',
            message: '删除成功'
          })
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
        })
        .catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
    },
    handleDownload() {
      this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = [
            '物流公司ID',
            '物流公司名称',
            '物流公司地址',
            '联系电话',
            '联系人',
            '客服电话',
            '公司性质',
            '创建人'
          ]
          const filterVal = [
            'id',
            'name',
            'address',
            'phone',
            'contact',
            'serviceTel',
            'logisticsType',
            'createUser'
          ]
          excel.export_json_to_excel2(tHeader, this.list, filterVal, '物流公司信息')
          this.downloadLoading = false
        })
    }
  }
}
</script>
