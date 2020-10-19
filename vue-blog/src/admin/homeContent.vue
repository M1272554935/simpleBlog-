<template>
  <div class="manage_page fillcontain">
    <el-row style="height: 100%;">
      <el-col :xs="3" :sm="3" :md="4" :lg="4" :xl="4" :span="4"  style="min-height: 100%; background-color: #324057;">
        <el-menu :default-active="defaultActive"
                 background-color="#324057"
                 text-color="#fff"
                 style="min-height: 100%;" heme="dark" router>
          <el-menu-item index="admin"  class="child_item">
            <i class="el-icon-menu"></i>首页</el-menu-item>
          <el-submenu class="child_item" index="2">
            <template slot="title"><i class="el-icon-document"></i>文章管理</template>
            <el-menu-item  index="articleList" route="/articleList">文章列表</el-menu-item>
            <el-menu-item index="draftList" route="/draftList">草稿箱</el-menu-item>
            <el-menu-item index="labelList" route="/labelList">标签列表</el-menu-item>
          </el-submenu>
          <el-submenu index="3" class="child_item">
            <template slot="title"><i class="el-icon-plus"></i>添加数据</template>
            <el-menu-item index="addLabel" route="/addLabel">添加标签</el-menu-item>
          </el-submenu>
          <el-submenu index="4"  class="child_item" disabled>
            <template slot="title"><i class="el-icon-star-on"></i>图表</template>
          </el-submenu>
          <el-submenu index="5"  class="child_item">
            <template slot="title"><i class="el-icon-edit"></i>编辑</template>
            <el-menu-item index="articleEdit" route="/articleEdit">文章编辑</el-menu-item>
          </el-submenu>
          <el-submenu index="6"  class="child_item2">
            <template slot="title"><i class="el-icon-setting"></i>用户中心</template>
            <el-menu-item index="adminUser" route="/adminUser">用户列表</el-menu-item>
            <el-menu-item >
              <router-link :to="{name:'adminLogin'}">
                <el-button type="primary" size="mini" v-if="!loginS">登录</el-button>
              </router-link>
              <el-button type="primary" size="mini" @click="goLogout" v-if="loginS">注销登录</el-button>
            </el-menu-item>
          </el-submenu>

        </el-menu>
      </el-col>
      <el-col :xs="21" :sm="21" :md="20" :lg="20" :xl="20":span="20" style="height: 100%;overflow: auto;">
        <router-view name="other"></router-view>
<!--        <keep-alive>-->
<!--        </keep-alive>-->
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "homeContent",
        data:{
            loginS:false //登录的状态，true为已登录
        },
        mounted () {
          var s1 = sessionStorage.getItem('login')
          if (s1 == 'false' || s1.length >4){
            this.loginS = false
          }else {
            this.loginS = true
          }
        },
        methods:{
          goLogout(){
            this.$axios.get("/api/logout").then((res =>{
              if (res.data === 200){
                this.$message({
                  message: '注销登录成功',
                  center: true,
                  type: 'success'
                });
                sessionStorage.setItem('login','false');
                this.$router.push("/login")
              }else {
                this.$message({
                  message: "服务器错误",
                  center: true,
                  type: 'error'
                });
              }

            }))
          }
        }
    }
</script>


<style scoped>
  .manage_page fillcontain{
    margin:0; padding:0;
  }
 .child_item{

   text-align: left;

 }
 .child_item2{
   border-bottom: 580px solid #324057;
   text-align: left;

 }
</style>
