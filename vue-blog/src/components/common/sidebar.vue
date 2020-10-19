<template>
    <div>
<!--      name="show" value="title" name="tempid" value="1" name="tbname" value="news"-->
      <div class="sidebar">
        <div class="search">
          <form  v-on:submit.prevent="clickBtn" name="searchform" id="searchform" method="get">
            <input v-model="keywords"  name="keyboard" id="keyboard" class="input_text"
                   value="请输入关键字" style="color: rgb(153, 153, 153);"
                   onfocus="if(value=='请输入关键字'){this.style.color='#000';value=''}"
                   onblur="if(value==''){this.style.color='#999';value='请输入关键字'}"
                   type="text">
            <input  type="hidden">
            <input  type="hidden">
            <input  type="hidden">
            <button  class="input_submit"  >搜索</button>
          </form>
        </div>
        <div class="cloud">
          <h2 class="hometitle">标签云</h2>
          <ul >
            <a v-for="lab in labelList" :key="lab">{{lab}}</a>
          </ul>
        </div>
        <div class="paihang" >
          <h2 class="hometitle">点击排行</h2>
          <div v-for="article in sidebarList" :key="article">
            <ul>
              <li>
                <router-link :to="{name:'page',params:{articleId:article.aid}}">
                  <b><a  target="_blank">{{article.title}}</a></b>
                  <p>{{article.info}}</p>
                </router-link>
              </li>
            </ul>
          </div>

        </div>

      </div>
    </div>
</template>

<script>

    export default {
      name: "sidebar",
      inject:['reload'],
      data () {
        return {
          labelList:[],
          sidebarList: [],
          keywords: null,
        }
      },
      created () {
        this.getDate();
        this.getLabel();
      },
      methods: {
        getDate: function () {
          this.$axios.get('/api/sidebarList').then(res => {
            this.sidebarList = res.data.records;

          })
        },
        getLabel(){
          this.$axios.get('/api/admin/push').then(res => {
            this.labelList = res.data.label;

          })
        },
        clickBtn(){

          if (this.keywords != null){
            this.$router.push({
              name:'search',
              params :{
                keywords:this.keywords
              }
            })
          }
          return false
        }
      }
    }
</script>

<style scoped>
  @import "../../../static/css/index.css";
  @import "../../../static/css/base.css";
</style>
