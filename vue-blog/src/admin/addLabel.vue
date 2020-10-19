<template>
  <div>
    <div style="height: 40px">
      <div style="height: 10px"></div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>添加标签</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row style="margin-top: 20px;">
      <el-col :span="12" :offset="4">
        <el-form  label-width="110px" class="demo-formData">
          <el-form-item label="标签名称" prop="name">
            <el-input v-model="lname"></el-input>
          </el-form-item>
          <el-form-item class="button_submit">
            <el-button type="primary" @click="addLabel">立即创建</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "addLabel",
      data(){
        return {
          lname: ''
        }
      },
      methods:{
          addLabel(){
            this.$axios.get("/api/admin/addLabel", {
                params: {
                  lname: this.lname
                }
            }).then((res) => {

              if (res.data === 200){
                this.$message({
                  message: '添加成功!',
                  center: true,
                  type: 'success'
                });
                const CountTime = 1;
                if (!this.timer){
                  this.count = CountTime
                  this.show = false
                  this.timer = setInterval(() =>{
                    if (this.count >0 && this.count <=CountTime){
                      this.count--
                    }else {
                      this.show = true
                      clearInterval(this.timer)
                      this.timer = null
                      this.$router.go(0)
                    }
                  },1000)
                }
              }else {
                this.$message({
                  message: '添加失败!',
                  center: true,
                  type: 'error'
                });
              }

            })
          }
      }
    }
</script>

<style scoped>

</style>
