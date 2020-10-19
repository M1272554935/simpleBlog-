<template>
  <div>
    <li v-for="article in articleList" :key="article">
       <span class="blogpic">
         <router-link :to="{name:'page',params:{articleId:article.aid}}">
           <el-image style="width: 200px; height: 150px" :src="article.titlePic" :fit="contain">
            </el-image>
         </router-link>
       </span>
      <h3 class="blogtitle">
        <router-link :to="{name:'page',params:{articleId:article.aid}}">{{article.title}}</router-link>
      </h3>
      <div class="bloginfo">
        <p>{{article.info}}</p><br>
      </div>
      <div class="autor">
            <span class="lm">
              <a title="CSS3|Html5" target="_blank" class="classname">{{article.labelId}}</a></span>
        <span class="dtime">{{article.createTime}}</span>
        <span class="viewnum">浏览（<a >{{article.hits}}</a>）</span>
        <span class="readmore">
              <router-link :to="{name:'page',params:{articleId:article.aid}}">阅读原文</router-link>
        </span>
      </div>
    </li>
  </div>

</template>

<script>
    export default {
        name: "ArticleListChild",
        props: {
          articleList: []
        }
    }
</script>

<style scoped>
  @import "../../../static/css/index.css";
  @import "../../../static/css/m.css";
  @import "../../../static/css/base.css";
</style>
