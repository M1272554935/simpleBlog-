<template>
  <div>
    <div style="height: 40px">
      <div style="height: 10px"></div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <section class="data_section">
      <header class="section_title">数据统计</header>
      <el-row :gutter="20" style="margin-bottom: 10px;">
        <el-col :span="4"><div class="data_list today_head"><span class="data_num head">当日数据：</span></div></el-col>
        <el-col :span="4"><div class="data_list"><span class="data_num">{{dayArticle}}</span> 新增文章</div></el-col>
        <el-col :span="4"><div class="data_list"><span class="data_num">{{dayHit}}</span> 浏览量</div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="4"><div class="data_list all_head"><span class="data_num head">总数据：</span></div></el-col>
        <el-col :span="4"><div class="data_list"><span class="data_num">{{articleCount}}</span> 篇文章</div></el-col>
        <el-col :span="4"><div class="data_list"><span class="data_num">{{hitsCount}}</span> 浏览量</div></el-col>
      </el-row>
    </section>
  </div>
</template>

<script>

    export default {
        name: "adminIndex",
      data () {
        return {
          //日数据
          dayHit: null,
          dayArticle: null,
          //总数据
          articleCount: null,
          hitsCount:null,
        }
      },
      mounted() {
        this.getDate();

      },
      methods: {
        getDate: function () {
          this.$axios.get('/api/admin/index').then(res => {
                this.dayHit = res.data.dayHit;
                this.dayArticle = res.data.dayArticle;
                this.articleCount = res.data.articleCount;
                this.hitsCount = res.data.hitsCount;

          })
        },
      }
    }
</script>

<style scoped>
  .data_section{
    padding: 20px;
    margin-bottom: 40px;
  }
      .section_title{
        text-align: center;
        font-size: 30px;
        margin-bottom: 10px;
      }
      .data_list{
        text-align: center;
        font-size: 14px;
        color: #666;
        border-radius: 6px;
        background: #E5E9F2;
      }
        .data_num{
          color: #333;
          font-size: 26px;

        }
        .head{
          border-radius: 6px;
          font-size: 22px;
          padding: 4px 0;
          color: #fff;
          display: inline-block;
        }

    .today_head{
      background: #FF9800;
    }
    .all_head{
      background: #20A0FF;
    }


</style>
