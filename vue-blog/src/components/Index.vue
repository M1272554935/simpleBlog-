<template>
  <div>
    <router-view name="header" ></router-view>
    <div class="color_bg">
      <router-view name="imageLine"></router-view>

      <el-row>
        <el-col :xs="1" :sm="1" :md="2" :lg="4" :xl="4"><div class="grid-content bg-purple"></div></el-col>

        <el-col :xs="22" :sm="22" :md="20" :lg="16" :xl="16">
          <div class="grid-content bg-purple-light">
            <router-view name="content" ></router-view>
            <router-view name="sidebar" ></router-view>
          </div>
        </el-col>

        <el-col :xs="1" :sm="1" :md="2" :lg="4" :xl="4"><div class="grid-content bg-purple"></div></el-col>
      </el-row>

      <router-view name="footer" ></router-view>
    </div>
  </div>
</template>

<script>
    export default {
      name: "Index",


    }
</script>

<style scoped>

  .color_bg{
    background: #ffdfe4;
  }

  .bg-purple {
    background: #ffdfe4;
  }
  .bg-purple-light {
    background: #ffdfe4;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }


</style>
