<template>
  <div class="fillcontain">
    <div style="height: 40px">
      <div style="height: 10px"></div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="table_container">
      <el-table
        :data="userList"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="用户名:" >
                <span class="item_span">{{ props.row.username }}</span>
              </el-form-item>
              <el-form-item label="邮箱:" >
                <span>{{ props.row.email }}</span>
              </el-form-item>
              <el-form-item label="创建时间:">
                <span>{{ props.row.createTime }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="昵称"
          prop="screenName">
        </el-table-column>
        <el-table-column
          label="角色"
          prop="role">
        </el-table-column>
        <el-table-column label="操作" width="200" >
          <template slot-scope="scope">
            <el-button
              size="small"
              type="warning"
              @click="changeRole(scope.$index)">角色管理</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--删除文章对话框-->
      <el-dialog
        title="设置角色"
        :visible.sync="roleVisible"
        width="30%"
        :before-close="handleClose">
        <el-select v-model="checkRole" clearable placeholder="请选择角色">
          <el-option
            v-for="item in roleList"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
        <span slot="footer" class="dialog-footer">
          <el-button @click="roleVisible = false">取 消</el-button>
          <el-button type="primary" @click="roleHandler">确 定</el-button>
        </span>
      </el-dialog>
<!--      删除对话框-->
      <el-dialog
        title="提示"
        :visible.sync="deleteVisible"
        width="30%"
        :before-close="handleClose">
        <span>是否删除该用户</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="deleteVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteUser">确 定</el-button>
        </span>
      </el-dialog>

      <div class="Pagination">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>

    </div>
  </div>

</template>

<script>
    import {formatTimeToStr} from "../config/date";

    export default {
        name: "userList",
      data () {
        return {
          userList:[],
          roleList: ['v1','v8'],
          currentPage:1,// 当前页码
          pageSize:10,// 每页大小
          total:1,
          roleVisible: false, //对话框
          deleteVisible: false,
          checkRole:'',
          changeUid:null,
          deleteUid:null,
        }
      },
      created () {
        this.getUserList();
      },
      methods: {
        getUserList: function () {
          this.$axios.get('/api/admin/getUserList', {
            params: {
              currentPage: this.currentPage,
              pageSize: this.pageSize,
            }
          } ).then(res => {

            this.currentPage = res.data.current;
            this.pageSize = res.data.size;
            this.total = res.data.total;
            this.userList = res.data.records;
            for(var i=0; i<this.userList.length; i++){
              this.userList[i].createTime = formatTimeToStr(this.userList[i].createTime, 'yyyy-MM-dd')
            }

          })
        },
        //? 长度改变----改变每页显示的条数的时候  自动触发
        handleSizeChange(val){
          this.pageSize = val
          this.getUserList();

        },
        // 当前改变----当前页码改变之后，触发这个函数
        handleCurrentChange(val){

          this.currentPage = val;
          this.getUserList();
        },
        //设置角色
        changeRole(rowIndex){
          this.roleVisible = true;
          this.changeUid = this.userList[rowIndex].uid;

        },
        roleHandler(){
          this.roleVisible = false;
          this.$axios.get("/api/admin/changeRole",{
            params:{
              uid: this.changeUid,
              role:this.checkRole
            }
          }).then((res =>{
            if (res.data ===200 ||res.data === '200'){
              this.$message({
                message: '设置成功',
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
                    this.$router.go(0);
                  }
                },1000)
              }
            }else {
              this.$message({
                message: res.data,
                center: true,
                type: 'error'
              });
            }

          }))

        },
        //删除
        handleDelete(rowIndex){
          this.deleteVisible = true;
          this.deleteUid = this.userList[rowIndex].uid

        },
        deleteUser(){
          this.deleteVisible = false;
          this.$axios.get("/api/admin/deleteUser", {
            params:{uid: this.deleteUid}
          }).then((res) => {

            if (res.data === '200' ||res.data === 200){
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
                    this.$router.go(0);
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
        },
      }
    }
</script>

<style scoped>

</style>
