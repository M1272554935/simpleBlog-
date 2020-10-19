<template>
  <div class="fillcontain">
    <div style="height: 40px">
      <div style="height: 10px"></div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标签列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="table_container">
      <el-table
        :data="labelList"
        highlight-current-row
        style="width: 100%">
        <el-table-column
          type="index"
          width="160">
        </el-table-column>
        <el-table-column
          property="lname"
          label="标签名称"
          width="500">
        </el-table-column>
        <el-table-column
          property="createTime"
          label="创建时间"
          width="500">
        </el-table-column>
        <el-table-column label="操作" width="400" >
          <template slot-scope="scope">
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--删除对话框-->
      <el-dialog
        title="提示"
        :visible.sync="deleteVisible"
        width="30%"
        :before-close="handleClose">
        <span>是否删除该标签</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="deleteVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteHandler">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
    import {formatTimeToStr} from "../config/date";

    export default {
        name: "labelList",
      data(){
        return {
          labelList:[],
          deleteLid: null,
          deleteVisible: false
        }
      },
      mounted () {
          this.getLabelList()
      },
      methods:{

        getLabelList(){

          this.$axios.get('/api/admin/label').then(res => {
            this.labelList = res.data.label;
            for(var i=0; i<this.labelList.length; i++){
              this.labelList[i].createTime = formatTimeToStr(this.labelList[i].createTime, 'yyyy-MM-dd')
            }
          })
        },
        handleDelete(rowIndex){
          this.deleteVisible = true;
          this.deleteLid = this.labelList[rowIndex].lid
        },
        deleteHandler(){
          this.deleteVisible = false;
          this.$axios.get('/api/admin/deleteLabel',{
            params:{
              lid: this.deleteLid
            }
          }).then(res => {
            if (res.data === 200){
              this.$message({
                message: '删除成功!',
                center: true,
                type: 'success'
              });
              const CountTime = 1;
              if (!this.timer){
                this.count = CountTime
                this.show = false
                this.timer = setInterval(() =>{
                  if (this.count >0 && this.count <=CountTime){
                    this.count--
                  }else {
                    this.show = true
                    clearInterval(this.timer)
                    this.timer = null
                    this.$router.go(0)
                  }
                },1000)
              }
            }else {
              this.$message({
                message: '删除失败!',
                center: true,
                type: 'error'
              });
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
