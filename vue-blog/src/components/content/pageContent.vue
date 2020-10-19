<template>
    <div>
      <h1 class="t_nav"><a  class="n1">文章</a></h1>
      <div class="infos">
        <div class="newsview">
          <h3 class="news_title">{{article.title}}</h3><br>
          <div class="news_author">
            <span class="au01">
              <a >{{article.author}}</a>
            </span>
            <span class="au02">{{article.createTime}}</span>
            <span class="au03">共<b>{{article.hits}}</b>人围观</span>
          </div>
          <div class="tags">
            <a  target="_blank" v-for="lab in labels" :key="lab">{{lab}}</a> &nbsp;
          </div>
          <div class="news_about">
            <strong>简介</strong>
            <p>{{article.info}}</p>
          </div>
          <div class="news_infos">
            <div class="markdown-body" v-html="content" ></div>
          </div>
        </div>
        <div class="share"></div>

        <div class="news_pl">
          <h2>文章评论</h2>
          <div  id="gitalk-container"  ></div>
        </div>
      </div>
    </div>
</template>

<script>
    import {formatTimeToStr} from "../../config/date";
    import marked from 'marked'

    export default {
      name: "pageContent",

      mounted () {
          this.article = {};
          this.getArticle(this.$route.params.articleId);
          this.gitalk.render("gitalk-container");
      },
      watch: {
        '$route.params.articleId':function(val, old) {
          if (val !== old) {

            this.getArticle()
          }
        },
      },
      methods: {
        getArticle: function () {
          this.$axios.get('/api/page', {
            params: {
              aid: this.$route.params.articleId
            }
          }).then(res => {
            this.labels = res.data.label;
            this.article = res.data.article;
            this.article.createTime = formatTimeToStr(this.article.createTime, 'yyyy-MM-dd')
            this.content = marked(this.article.content)
          })
        }
      },
      data() {

          return {
            article: {},
            content: '',
            labels:[],
            //...
            gitalk : new Gitalk({
              id: this.$route.params.articleId,
              clientID: '12b68f84396646369e52',
              clientSecret: 'aae00e4d0c0a3454a84eaa93bfa403c43e20ffd6',
              repo: 'MyBlog',
              owner: 'M1272554935',//
              admin: ['M1272554935'],//github用户名
              labels: ['M1272554935'],
              perPage: 50,
              distractionFreeMode: false
            }),
            //...
          }

      }
    }

</script>

<style scoped>
  @import "../../../static/css/index.css";
  @import "../../../static/css/m.css";
  @import "../../../static/css/base.css";

  .mkCont img{
    width: 200px;
    height: 150px;
  }
</style>
