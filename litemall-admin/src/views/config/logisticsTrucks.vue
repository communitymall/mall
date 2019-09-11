<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.companyId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入物流公司ID"/>
      <el-input
        v-model="listQuery.licensePlateNumber"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入车牌号"/>
      <el-button
        v-permission="['GET /admin/logistics/listTruck']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter">查找
      </el-button>
      <el-button
        v-permission="['POST /admin/logistics/addTruck']"
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

      <el-table-column align="center" label="所属公司ID" prop="companyId" sortable/>

      <el-table-column align="center" label="车牌号" prop="licensePlateNumber"/>

      <el-table-column align="center" label="司机" prop="driver"/>

      <el-table-column align="center" label="联系电话" prop="phone"/>

      <el-table-column align="center" label="载重量(kg)" prop="load"/>

      <el-table-column align="center" label="所属地区" prop="province" />

      <el-table-column align="center" label="车辆状态" prop="vehicle">
        <template slot-scope="scope">
          <el-tag :type="scope.row.vehicle ? '0' : '1' ">{{ scope.row.vehicle ? '空闲' : '繁忙' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建人" prop="createUser"/>

      <el-table-column align="center" label="时间" prop="createTime"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /admin/logistics/updateTruck']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)">编辑
          </el-button>
          <el-button
            v-permission="['POST /admin/logistics/delTruck']"
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

        <el-form-item label="车辆所属地区">
          <v-distpicker :province="dataForm.province" :city="dataForm.city" :area="dataForm.area" @selected="onSelected"/>
        </el-form-item>

        <el-form-item label="所属物流公司" prop="companyId">
          <el-select v-model="dataForm.companyId">
            <!--            <el-option v-for="item in logisticsList" :key="item.id" :label="item.label"  value="item.id" :value="item.name" />-->
            <el-option v-for="item in logisticsList" :key="item.id" :label="item.label" :value="item.id">{{ item.name }}
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="车牌号" prop="licensePlateNumber">
          <el-input v-model="dataForm.licensePlateNumber"/>
        </el-form-item>
        <el-form-item label="司机" prop="driver">
          <el-input v-model="dataForm.driver"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="dataForm.phone"/>
        </el-form-item>
        <el-form-item label="载重量（kg）" prop="load">
          <el-input v-model="dataForm.load"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="ModifyDialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;">

        <el-form-item label="车辆所属地区">
          <v-distpicker :province="dataForm.province" :city="dataForm.city" :area="dataForm.area" @selected="onSelected"/>
        </el-form-item>

        <el-form-item label="所属物流公司" prop="companyId">
          <el-select v-model="dataForm.companyId">
            <!--            <el-option v-for="item in logisticsList" :key="item.id" :label="item.label"  value="item.id" :value="item.name" />-->
            <el-option v-for="item in logisticsList" :key="item.id" :label="item.label" :value="item.id">{{ item.name }}
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="车牌号" prop="licensePlateNumber">
          <el-input v-model="dataForm.licensePlateNumber" disabled="true"/>
        </el-form-item>

        <el-form-item label="司机" prop="driver">
          <el-input v-model="dataForm.driver"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="dataForm.phone"/>
        </el-form-item>

        <el-form-item label="载重量（kg）" prop="load">
          <el-input v-model="dataForm.load" disabled="true"/>
        </el-form-item>

        <el-form-item label="车辆状态" prop="vehicle">
          <el-select v-model="dataForm.vehicle" placeholder="请选择">
            <el-option :value="1" label="空闲"/>
            <el-option :value="0" label="繁忙"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="ModifyDialogFormVisible = false">取消</el-button>
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
import {
  listLogisticsTrucks,
  createLogisticsTrucks,
  updateLogisticsTrucks,
  deleteLogisticsTrucks,
  listLogistics
} from '@/api/logistics'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { listRegion } from '@/api/region'

import VDistpicker from 'v-distpicker'

export default {
  name: 'LogisticsTrucks',
  components: {
    Pagination,
    VDistpicker
  },

  data() {
    return {
      uploadPath,
      logisticsList: [],
      list: [],
      regionList: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined,
        id: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      dataForm: {
        id: undefined,
        companyId: undefined,
        licensePlateNumber: undefined,
        driver: undefined,
        phone: undefined,
        createUser: undefined,
        load: undefined,
        vehicle: undefined,
        province: '',
        city: '',
        area: ''
      },
      dialogFormVisible: false,
      ModifyDialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑车辆信息',
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

      // select: { province: '广东省', city: '广州市', area: '海珠区' },
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
    this.getAreaList()
  },
  methods: {
    init: function() {
      listLogistics().then(response => {
        this.logisticsList = response.data.data.list
      })
    },
    handleCategoryChange(value) {
      this.logisticsList.name = value[value.length - 1]
    },
    getList() {
      this.listLoading = true
      listLogisticsTrucks(this.listQuery)
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

    getAreaList() {
      listRegion().then(response => {
        this.regionList = response.data.data.list
        this.listLoading = false
      }).catch(() => {
        this.regionList = []
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

        companyId: undefined,
        licensePlateNumber: undefined,
        driver: undefined,
        phone: undefined,
        createUser: undefined,
        load: undefined,

        vehicle: undefined,
        province: undefined,
        city: undefined,
        area: undefined
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
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          createLogisticsTrucks(this.dataForm)
            .then(response => {
              this.list.unshift(response.data.data)
              this.dialogFormVisible = false
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
      this.ModifyDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },

    onSelected(data) {
      this.dataForm.province = data.province.value
      this.dataForm.city = data.city.value
      this.dataForm.area = data.area.value
    },

    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateLogisticsTrucks(this.dataForm)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.dataForm)
                  break
                }
              }
              this.ModifyDialogFormVisible = false
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
      deleteLogisticsTrucks(row)
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
