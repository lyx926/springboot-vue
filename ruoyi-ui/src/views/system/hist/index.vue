<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资源名称" prop="resourceName">
        <el-input v-model="queryParams.resourceName" placeholder="请输入资源名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="唯一标识" prop="uniqueCode">
        <el-input
          v-model="queryParams.uniqueCode"
          placeholder="请输入唯一展示标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="UUID" prop="uuidCode">
        <el-input
          v-model="queryParams.uuidCode"
          placeholder="请输入UUID"
          clearable
          type="number"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="histList" @selection-change="handleSelectionChange"  :cell-class-name="tableColumnClassName">
 <el-table-column label="资源名称" align="center" prop="resourceName" />
      <el-table-column label="唯一标识" align="center" prop="uniqueCode" />
      <el-table-column label="UUID" width="180px" align="center" prop="uuidCode" />
      <el-table-column label="CPU核心数" align="center" prop="cpuNumCpu" />
      <el-table-column label="CPU当前空闲率" align="center" prop="freeCpu">
        <template slot-scope="scope">
          {{scope.row.freeCpu}}%
        </template>
      </el-table-column>
     <el-table-column label="CPU系统使用率" align="center" prop="sysCpu" />
      <el-table-column label="CPU用户使用率" align="center" prop="usedCpu" />
      <el-table-column label="CPU当前等待率" align="center" prop="waitCpu" />
      <el-table-column label="剩余内存" align="center" prop="freeMem" />
      <el-table-column label="内存总量" align="center" prop="totalMem" />
      <el-table-column label="已用内存" align="center" prop="usedMem" />
      <el-table-column label="内存使用率" sortable="" align="center" prop="usageMem">
       <template slot-scope="scope">
          {{scope.row.usageMem}}%
        </template>
      </el-table-column>
      <el-table-column prop="num" label="磁盘(I/O)"  width="240px" align="center" >
        <!-- :formatter="formatter" -->
        <template slot-scope="scope" v-if="scope.row.dirName">
          <span v-for="(bean,index) in formatter(scope.row.dirName)" style="display: flex;flex-direction: row;"
            v-if="formatter(scope.row.usageDisk,index)>0">
            <div style="width: 20%;text-align: right;padding-right: 10px;">
              {{bean}}
            </div>
            <div style="width: 70%;" class="progress">
              <!-- scope.row.dirName  color="#57DCDD" -->
              <el-progress :stroke-width="18" type="line" :percentage="formatter(scope.row.usageDisk,index)"
                :color="formatterColor(scope.row.usageDisk,index)" :text-inside="true" />
            </div>
          </span>

        </template>
      </el-table-column>
      <el-table-column label="服务器IP" align="center" prop="computerIpSys"  width="140" />
      <el-table-column label="采集时间" width="95px" align="center" prop="updateTime">
        <template slot-scope="scope" v-if="scope.row.updateTime">
          {{scope.row.updateTime.replace(' ','\n')}}
        </template>
      </el-table-column>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listHist, getHist, delHist, addHist, updateHist } from "@/api/system/hist";

export default {
  name: "Hist",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 接收历史表格数据
      histList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        uuidCode: null,
        cpuNumCpu: null,
        freeCpu: null,
        sysCpu: null,
        usedCpu: null,
        waitCpu: null,
        freeMem: null,
        totalMem: null,
        usageMem: null,
        usedMem: null,
        computerIpSys: null,
        uniqueCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
   formatterColor(str, index) {
     if (str.split(",")[index] >= 92) {
       return '#ff557f'
     }
     return '#57DCDD'
   },
   formatter(str, index) {
     if (index != null && index >= 0) {
       return str.split(",")[index]
     }
     return str.split(",")
   },
   tableColumnClassName({
     row,
     column,
     rowIndex,
     columnIndex
   }) {
     if (row.usageMem > 90) {
       if (column.label == '内存使用率') {
         return 'warning-row';
       }
     }
     if (row.freeCpu < 10) {
       if (column.label == 'CPU当前空闲率') {
         return 'warning-row';
       }
     }
     return '';
   },
   tableRowClassName({
     row,
     rowIndex
   }) {
     if (row.usageMem > 90) {
       return 'warning-row';
     }
     return '';
   },
    /** 查询接收历史列表 */
    getList() {
      this.loading = true;
      listHist(this.queryParams).then(response => {
        this.histList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        uuidCode: null,
        cpuNumCpu: null,
        freeCpu: null,
        sysCpu: null,
        usedCpu: null,
        waitCpu: null,
        freeMem: null,
        totalMem: null,
        usageMem: null,
        usedMem: null,
        computerIpSys: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        uniqueCode: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加接收历史";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHist(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改接收历史";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHist(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHist(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除接收历史编号为"' + ids + '"的数据项？').then(function() {
        return delHist(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/hist/export', {
        ...this.queryParams
      }, `hist_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-table__cell>div{
   table-layout: fixed;
   word-wrap: break-all;
   word-break: normal;
   overflow: hidden;
  }
</style>
