<template>
    <div>
      <div class="type-line1">
        <div class="type1">
          <div class="type-blogs">
            <li v-if="articleList.length >0">
                    <span class="blogpic">
                      <a href="/">
                        <el-image style="width: 200px; height: 150px" :src="articleList[0].titlePic" :fit="contain">
                        </el-image>
                      </a>
                    </span>
              <h3 class="blogtitle">
                <router-link :to="{name:'page',params:{articleId:articleList[0].aid}}">{{articleList[0].title}}</router-link>
              </h3>
              <div class="bloginfo">
                <p>{{articleList[0].info}}</p><br>
              </div>
              <div class="autor">
                      <span class="lm">
                        <a title="CSS3|Html5" target="_blank" class="classname"></a>
                      </span>
                <span class="dtime">{{articleList[0].createTime}}</span>
                <span class="viewnum">浏览（<a >{{articleList[0].hits}}</a>）</span>
                <span class="readmore">
                        <router-link :to="{name:'page',params:{articleId:articleList[0].aid}}">阅读原文</router-link>
                 </span>
              </div>
            </li>

            <li v-if="articleList.length >2">
                    <span class="blogpic">
                      <a href="/">
                        <el-image style="width: 200px; height: 150px" :src="articleList[2].titlePic" :fit="contain">
                        </el-image>
                      </a>
                    </span>
              <h3 class="blogtitle">
                <router-link :to="{name:'page',params:{articleId:articleList[2].aid}}">{{articleList[2].title}}</router-link>
              </h3>
              <div class="bloginfo">
                <p>{{articleList[2].info}}</p><br>
              </div>
              <div class="autor">
                      <span class="lm">
                        <a title="CSS3|Html5" target="_blank" class="classname">{{articleList[2].labelId}}</a>
                      </span>
                <span class="dtime">{{articleList[2].createTime}}</span>
                <span class="viewnum">浏览（<a >{{articleList[2].hits}}</a>）</span>
                <span class="readmore">
                        <router-link :to="{name:'page',params:{articleId:articleList[2].aid}}">阅读原文</router-link>
                      </span>
              </div>
            </li>

            <li v-if="articleList.length >4">
                    <span class="blogpic">
                      <a href="/">
                        <el-image style="width: 200px; height: 150px" :src="articleList[4].titlePic" :fit="contain">
                        </el-image>
                      </a>
                    </span>
              <h3 class="blogtitle">
                <router-link :to="{name:'page',params:{articleId:articleList[4].aid}}">{{articleList[4].title}}</router-link>
              </h3>
              <div class="bloginfo">
                <p>{{articleList[4].info}}</p><br>
              </div>
              <div class="autor">
                      <span class="lm">
                        <a title="CSS3|Html5" target="_blank" class="classname">{{articleList[4].labelId}}</a>
                      </span>
                <span class="dtime">{{articleList[4].createTime}}</span>
                <span class="viewnum">浏览（<a >{{articleList[4].hits}}</a>）</span>
                <span class="readmore">
                        <router-link :to="{name:'page',params:{articleId:articleList[4].aid}}">阅读原文</router-link>
                      </span>
              </div>
            </li>

          </div>
        </div>


        <div class="type1">
          <div class="type-blogs">

            <li v-if="articleList.length >1">
                    <span class="blogpic">
                      <a href="/">
                        <el-image style="width: 200px; height: 150px" :src="articleList[1].titlePic" :fit="contain">
                        </el-image>
                      </a>
                    </span>
              <h3 class="blogtitle">
                <router-link :to="{name:'page',params:{articleId:articleList[1].aid}}">{{articleList[1].title}}</router-link>
              </h3>
              <div class="bloginfo">
                <p>{{articleList[1].info}}</p><br>
              </div>
              <div class="autor">
                      <span class="lm">
                        <a title="CSS3|Html5" target="_blank" class="classname"></a>
                      </span>
                <span class="dtime">{{articleList[1].createTime}}</span>
                <span class="viewnum">浏览（<a >{{articleList[1].hits}}</a>）</span>
                <span class="readmore">
                        <router-link :to="{name:'page',params:{articleId:articleList[1].aid}}">阅读原文</router-link>
                      </span>
              </div>
            </li>

            <li v-if="articleList.length >3">
                    <span class="blogpic">
                      <a href="/">
                        <el-image style="width: 200px; height: 150px" :src="articleList[3].titlePic" :fit="contain">
                        </el-image>
                      </a>
                    </span>
              <h3 class="blogtitle">
                <router-link :to="{name:'page',params:{articleId:articleList[3].aid}}">{{articleList[3].title}}</router-link>
              </h3>
              <div class="bloginfo">
                <p>{{articleList[3].info}}</p><br>
              </div>
              <div class="autor">
                      <span class="lm">
                        <a title="CSS3|Html5" target="_blank" class="classname">{{articleList[3].labelId}}</a>
                      </span>
                <span class="dtime">{{articleList[3].createTime}}</span>
                <span class="viewnum">浏览（<a >{{articleList[3].hits}}</a>）</span>
                <span class="readmore">
                        <router-link :to="{name:'page',params:{articleId:articleList[3].aid}}">阅读原文</router-link>
                      </span>
              </div>
            </li>

            <li v-if="articleList.length >5">
                    <span class="blogpic">
                      <a href="/">
                        <el-image style="width: 200px; height: 150px" :src="articleList[5].titlePic" :fit="contain">
                        </el-image>
                      </a>
                    </span>
              <h3 class="blogtitle">
                <router-link :to="{name:'page',params:{articleId:articleList[5].aid}}">{{articleList[5].title}}</router-link>
              </h3>
              <div class="bloginfo">
                <p>{{articleList[5].info}}</p><br>
              </div>
              <div class="autor">
                      <span class="lm">
                        <a title="CSS3|Html5" target="_blank" class="classname">{{articleList[5].labelId}}</a>
                      </span>
                <span class="dtime">{{articleList[5].createTime}}</span>
                <span class="viewnum">浏览（<a >{{articleList[5].hits}}</a>）</span>
                <span class="readmore">
                        <router-link :to="{name:'page',params:{articleId:articleList[5].aid}}">阅读原文</router-link>
                      </span>
              </div>
            </li>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
      name: "TypeListChild",
      props: {
        articleList: []
      }
    }
</script>

<style scoped>
  @import "../../../static/css/index.css";
  @import "../../../static/css/m.css";
  @import "../../../static/css/base.css";

  .type-line1 {
    display: flex;
  }
  .type1 {
    float: left;
    width: 50%;
    height: auto;
    border: 1px solid #ffdfe4;
    margin: 10px;
  }
  .type-blogs {
    float: left;
    width: 100%;
    overflow: hidden;
  }

  .type-blogs li {
    border: 1px solid #ffdfe4;
    margin-bottom: 10px;
    overflow: hidden;
    padding: 20px;
    background: #fff
  }
  h1.my_nav span {
    float: right;
    color: #999
  }
  h1.my_nav a {
    width: 100px;
    display: block;
    text-align: center;
    color: #fff;
    float: left
  }
</style>
