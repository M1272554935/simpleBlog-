<template>
  <div class="fillcontain">
    <div style="height: 40px">
      <div style="height: 10px"></div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>文章列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="table_container">
      <el-table
        :data="articleList"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="文章分类:" >
                <span class="item_span">{{ props.row.type }}</span>
              </el-form-item>
              <el-form-item label="发布时间:" >
                <span>{{ props.row.createTime }}</span>
              </el-form-item>
<!--              <el-form-item label="标签:">-->
<!--                <span>{{ props.row.label }}</span>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="">-->
<!--              </el-form-item>-->
              <el-form-item label="简介:">
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
          label="浏览量"
          prop="hits">
        </el-table-column>
        <el-table-column label="操作" width="200" >
          <template slot-scope="scope">
              <el-button
                size="small"
                type="warning"
                @click="gotoArticle(scope.$index)">预览</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index)">删除</el-button>
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
        name: "articleList",
      data () {
        return {
          articleList: [],
          currentPage:1,// 当前页码
          pageSize:10,// 每页大小
          total:1,
          deleteVisible: false, //对话框
          deleteAid: null,    //想要删除的文章的id
        }
      },
      created () {
        this.getArDate();
      },
      methods: {
        getArDate: function () {
          this.$axios.get('/api/admin/articleList', {
            params: {
              currentPage: this.currentPage,
              pageSize: this.pageSize,
            }
          } ).then(res => {

            this.currentPage = res.data.current;
            this.pageSize = res.data.size;
            this.total = res.data.total;
            this.articleList = res.data.records;
            for(var i=0; i<this.articleList.length; i++){
              this.articleList[i].createTime = formatTimeToStr(this.articleList[i].createTime, 'yyyy-MM-dd')
            }

          })
        },
        //? 长度改变----改变每页显示的条数的时候  自动触发
        handleSizeChange(val){
          this.pageSize = val
          this.getDate();

        },
        // 当前改变----当前页码改变之后，触发这个函数
        handleCurrentChange(val){

          this.currentPage = val;
          this.getDate();
        },
        //预览
        gotoArticle(rowIndex){
          this.$router.push({
            name:'page',
            params :{
              articleId: this.articleList[rowIndex].aid
            }
          })
        },
        handleDelete(rowIndex){

          this.deleteVisible = true;
          this.deleteAid = this.articleList[rowIndex].aid
        },
        //删除文章
        deleteArticle(){
          this.deleteVisible = false;
          this.$axios.get("/api/admin/deleteArt", {
            params:{aid: this.deleteAid}
          }).then((res) => {

            if (res.data === 200){
              this.$message({
                message: '删除成功!',
                center: true,
                type: 'success'
              });
              const CountTime = 10;
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
                },100)
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
  .item_span{
    font-size: 15px;
  }

</style>
