<template>
    <div>
      <h1 class="t_nav"><a  class="n1">分类：</a><a class="n1">技术</a></h1>

        <TypeListChild :article-list="itList"></TypeListChild>
      <el-divider content-position="right" class="el-divider--horizontal">
        <el-button type="success" class="moreBtn" @click="btnClick('it')">阅读更多</el-button>
      </el-divider>

      <h1 class="my_nav"><a  class="n1">分类：</a><a class="n1">海洋</a></h1>

      <TypeListChild :article-list="oceanList"></TypeListChild>
      <el-divider content-position="right" class="el-divider--horizontal">
        <el-button type="success" class="moreBtn" @click="btnClick('ocean')">阅读更多</el-button>
      </el-divider>

      <h1 class="my_nav"><a  class="n1">分类：</a><a class="n1">随笔</a></h1>

        <TypeListChild :article-list="essayList"></TypeListChild>
      <el-divider content-position="right" class="el-divider--horizontal">
        <el-button type="success" class="moreBtn" @click="btnClick('essay')">阅读更多</el-button>
      </el-divider>


    </div>
</template>

<script>
    import {formatTimeToStr} from "../../config/date";
    import ArticleListChild from "../childComponents/ArticleListChild";
    import TypeListChild from "../childComponents/TypeListChild";

    export default {
      name: "typeContent",
      components: {TypeListChild, ArticleListChild},
      data () {
        return {
          itList: [],
          essayList: [],
          oceanList: [],
        }
      },
      created () {
        this.getDate();
      },
      methods: {

        btnClick: function (val) {
          this.$router.push({
            name:'typeList',
            params :{
              type: val
            }
          })
        },
        getDate: function () {
          this.$axios.get('/api/articleType').then(res => {
            this.itList = res.data.it.records;
            this.essayList = res.data.essay.records;
            this.oceanList = res.data.ocean.records;
            for(var i=0; i<this.itList.length; i++){
              this.itList[i].createTime = formatTimeToStr(this.itList[i].createTime, 'yyyy-MM-dd')
            }
            for(var i=0; i<this.essayList.length; i++){
              this.essayList[i].createTime = formatTimeToStr(this.essayList[i].createTime, 'yyyy-MM-dd')
            }
            for(var i=0; i<this.oceanList.length; i++){
              this.oceanList[i].createTime = formatTimeToStr(this.oceanList[i].createTime, 'yyyy-MM-dd')
            }

          })
        }
      }
    }
</script>

<style scoped>
  @import "../../../static/css/index.css";
  @import "../../../static/css/m.css";
  @import "../../../static/css/base.css";


  .type-blogs li {
    border: 1px solid #ffdfe4;
    margin-bottom: 10px;
    overflow: hidden;
    padding: 20px;
    background: #fff
  }
  .moreBtn{
    width: 60px;
    height: 35px;
  }
  .el-divider--horizontal{
    margin: 8px 0;
    background: #d8f8ff;
    border-top: 1px dashed #e8eaec;
  }
  h1.my_nav span {
    float: right;
    color: #999
  }

  h1.my_nav {
    font-size: 14px;
    font-weight: normal;
    line-height: 40px;
    width: 100%;
    overflow: hidden;
    border-top: 25px solid #ffdfe4;
  }

  h1.my_nav a {
    width: 100px;
    display: block;
    text-align: center;
    color: #fff;
    float: left
  }
</style>
