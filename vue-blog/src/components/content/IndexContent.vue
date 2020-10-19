<template>
  <div class="indexCon">
    <div class="blogs" >
      <ArticleListChild :article-list="articleList"></ArticleListChild>
    </div>
  </div>
</template>

<script>
    import {formatTimeToStr} from "../../config/date";
    import ArticleListChild from "../childComponents/ArticleListChild";

    export default {
      name: "IndexContent",
      components: {ArticleListChild},
      data () {
        return {
          articleList: [],
          pageSize: 6
        }
      },
      created () {
        this.getDate();
      },
      methods: {
        getDate: function () {
          this.$axios.get('/api/index', {
            params: {
              pageSize: this.pageSize
            }
          }).then(res => {

            this.articleList = res.data.records;
            for(var i=0; i<this.articleList.length; i++){
              this.articleList[i].createTime = formatTimeToStr(this.articleList[i].createTime, 'yyyy-MM-dd')
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

</style>
