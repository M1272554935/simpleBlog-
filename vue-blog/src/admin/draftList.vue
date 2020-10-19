<template>
  <div class="fillcontain">
    <div style="height: 40px">
      <div style="height: 10px"></div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>草稿箱</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="table_container">
      <el-table
        :data="articleList"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="创建时间">
                <span>{{ props.row.createTime }}</span>
              </el-form-item>
              <el-form-item label="修改时间">
                <span>{{ props.row.updateTime }}</span>
              </el-form-item>
<!--              <el-form-item label="文章标签">-->
<!--                <span>{{ props.row.label}}</span>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="">-->
<!--              </el-form-item>-->
              <el-form-item label="文章简介:">
                <span>{{ props.row.info }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="文章标题"
          prop="title">
        </el-table-column>
        <el-table-column
          label="文章分类"
          prop="type" width="500">
        </el-table-column>
        <el-table-column label="操作" width="300">
          <template slot-scope="scope">

            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(scope.$index)">删 除</el-button>
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="gotoDraft(scope.$index)">编 辑</el-button>
            <el-button
              size="mini"
              type="success"
              @click="handlePush(scope.$index)">发 布</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--删除文章对话框-->
      <el-dialog
        title="提示"
        :visible.sync="deleteVisible"
        width="30%"
        :before-close="handleClose">
        <span>是否删除该文章</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="deleteVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteArticle">确 定</el-button>
        </span>
      </el-dialog>

      <!--发布文章对话框-->
      <el-dialog
        title="提示"
        :visible.sync="pushVisible"
        width="30%"
        :before-close="handleClose">
        <span>是否发布该文章</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="pushVisible = false">取 消</el-button>
          <el-button type="primary" @click="pushArticle">确 定</el-button>
        </span>
      </el-dialog>
      <!--分页-->
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
        name: "draftList",
      data () {
        return {
          articleList: [],
          currentPage:1,// 当前页码
          pageSize:10,// 每页大小
          total:1,
          deleteVisible: false,
          pushVisible: false,
          currentDelAid: null,
          currentPushAid: null,
        }
      },
      created () {
        this.getDate();
      },
      methods: {
        getDate: function () {
          this.$axios.get('/api/admin/draftList', {
            params: {
              currentPage: this.currentPage,
              pageSize: this.pageSize,
            }
          } ).then(res => {

            this.currentPage = res.data.current;
            this.pageSize = res.data.size;
            this.total = res.data.pages;
            this.articleList = res.data.records;
            for(var i=0; i<this.articleList.length; i++){
              this.articleList[i].createTime = formatTimeToStr(this.articleList[i].createTime, 'yyyy-MM-dd');
              this.articleList[i].updateTime = formatTimeToStr(this.articleList[i].updateTime, 'yyyy-MM-dd')
            }

          })
        },
        //? 长度改变----改变每页显示的条数的时候  自动触发
        handleSizeChange(val){
          this.pageSize = val

        },
        // 当前改变----当前页码改变之后，触发这个函数
        handleCurrentChange(val){

          this.currentPage = val;
          this.getDate();
        },
        //删除文章按钮
        handleDelete(rowIndex){

          this.deleteVisible = true;
          this.currentDelAid = this.articleList[rowIndex].aid
        },
        //删除文章
        deleteArticle(){
          this.deleteVisible = false;
          this.deleteVisible = false;
          this.$axios.get("/api/admin/deleteArt", {
            params:{aid: this.currentDelAid}
          }).then((res) => {
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
        },
        //发布按钮
        handlePush(rowIndex){
          this.pushVisible = true;
          this.currentPushAid = this.articleList[rowIndex].aid;
        },
        //发布文章
        pushArticle(){
          this.pushVisible = false;
          this.$axios.get("/api/admin/draft2push",{
            params:{aid: this.currentPushAid}
          }).then((res) => {

            if (res.data === 200){
              this.$message({
                message: '发布成功!',
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
                    this.reload();
                  }
                },1000)
              }
            }else {
              this.$message({
                message: '发布失败!',
                center: true,
                type: 'error'
              });
            }

          })
        },
        //跳转到草稿编辑
        gotoDraft(rowIndex){
          this.$router.push({
            name:'draftEdit',
            params :{
              articleId: this.articleList[rowIndex].aid
            }
          })
        },

      }
    }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .table_container{
    padding: 20px;
  }
  .Pagination{
    display: flex;
    justify-content: flex-start;
    margin-top: 8px;
  }
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
    width: 120px;
    height: 120px;
    display: block;
  }
</style>
