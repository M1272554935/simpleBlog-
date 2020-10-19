<template>
  <div>
    <h1 class="t_nav"><a class="n1">文章列表</a><a class="n2">学无止境</a></h1>

    <div class="blogs">
      <div class="mt20"></div>

      <ArticleListChild :article-list="articleList"></ArticleListChild>

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
</template>

<script>
    import {formatTimeToStr} from "../../config/date";
    import ArticleListChild from "../childComponents/ArticleListChild";

    export default {
      name: "listContent",
      components: {ArticleListChild},
      data () {
        return {
          articleList: [],
          currentPage:1,// 当前页码
          pageSize:10,// 每页大小
          total:1
        }
      },
      created () {
        this.getDate();
      },
      methods: {
        getDate: function () {
          this.$axios.get('/api/list', {
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
                this.articleList[i].createTime = formatTimeToStr(this.articleList[i].createTime, 'yyyy-MM-dd')
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

        }
      }
    }

</script>

<style scoped>
  @import "../../../static/css/index.css";
  @import "../../../static/css/m.css";
  @import "../../../static/css/base.css";
</style>
