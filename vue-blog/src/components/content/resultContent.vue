<template>
  <div>
    <h1 class="_nav"><p class="n3">搜索结果 : </p><a class="n1">‘{{keyword}}’</a></h1>

    <div class="blogs">
      <div class="mt20"></div>

      <div class="noResult" v-if="total === 0">
        <li>
          <div class="resDiv">
            <p >没有找到与 '{{keyword}}' 的相关结果</p>
          </div>
        </li>
      </div>

      <ArticleListChild :article-list="resultList"></ArticleListChild>

      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[1, 10, 30]"
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
      name: "resultContent",
      components: {ArticleListChild},
      data () {
        return {
          resultList: [],
          keyword: null,
          currentPage:1,// 当前页码
          pageSize:10,// 每页大小
          total: 0
        }
      },
      mounted() {
        this.firstMe();
      },
      watch: {
        '$route.params.keywords':function(val, old) {
          if (val !== old) {
            this.firstMe();
          }
        },
      },
      methods: {
        firstMe: function(){
          if ( this.$route.params.keywords != null){
            this.keyword = this.$route.params.keywords
          }
          this.getDate();
        },
        getDate: function () {
          this.$axios.get('/api/search',{
            params: {
              keyword :this.keyword
            }
          }).then(res => {

            this.currentPage = res.data.current;
            this.pageSize = res.data.size;
            this.total = res.data.pages;
            this.resultList = res.data.records;
            for(var i=0; i<this.resultList.length; i++){
              this.resultList[i].createTime = formatTimeToStr(this.resultList[i].createTime, 'yyyy-MM-dd')
            }

          })
        },
        //改变每页显示的条数的时候  自动触发
        handleSizeChange(val){

          this.pageSize = val
        },
        //当前页码改变之后，触发这个函数
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

  .noResult{

  }
  .noResult li{
    width: auto;
    height: 800px;
  }
  .resDiv{
    color: #888;
    line-height: 24px;
  }
  .resDiv p{
    margin-top: 100px;
  }
  .n3{
    background: #5EA51B;
    width: 100px;
    text-align: center;
    color: #fff;
    float: left
  }
  h1._nav {
    border-bottom: #bfbfbf 1px solid;
    font-size: 14px;
    font-weight: normal;
    line-height: 40px;
    width: 100%;
    overflow: hidden
  }
  h1._nav a {
    width: auto;
    display: block;
    text-align: center;
    color: #fff;
    float: left
  }
</style>
