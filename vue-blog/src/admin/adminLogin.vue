<template>
  <div id="wrapper">
    <div>
      <h2></h2>
    </div>
    <nav class="switch_nav">
      <a ><router-link :to="{name:'adminRegister'}">注册</router-link></a>
      <a href="javascript:;"  class="switch_btn on">登陆</a>
      <div class="switch_bottom" id="switch_bottom"></div>
    </nav>
    <div id="login">
      <div class="formD">
        <input v-model="username" type="text" class="mobile required"  placeholder="账号" />
        <input v-model="password" type="password" class="psd required"  placeholder="密码" />
        <div class="yzCode">
          <input v-model="yzCode" type="text"  placeholder="验证码"/>
          <div class="yzD2"><img :src="imgHtml" class="img1" @click="toggleCode"></div>
        </div>
        <button  class="submit_btn" @click="checkForm()">登陆</button>
      </div>
    </div>

  </div>

</template>

<script>
    export default {
        name: "adminLogin",
        data() {
          return {
            username: '',
            password:'',
            imgHtml: '/api/getCode',
            yzCode:''       //验证码
          }
        },
        mounted () {
          this.toggleCode();
        },
        methods: {
          toggleCode() {
            var num=Math.ceil(Math.random()*10);//生成一个随机数（防止缓存）
            this.imgHtml = "/api/getCode?" + num;
          },
          checkForm(){
            if (this.username === '' || this.username.length === 0){
              this.$message.error('请输入账号！');
              return false
            }
            if (this.password ==='' || this.password.length === 0){
              this.$message.error('请输入密码！');
              return false
            }
            if (this.yzCode ==='' || this.yzCode.length === 0){
              this.$message.error('请输入验证码！');
              return false
            }
            this.login();
          },
          login(){
            var qs = require('qs');
            this.$axios.post('/api/toLogin',
              qs.stringify(
                {
                  username: this.username,
                  password: this.password,
                  verificationCode:this.yzCode,
                }, { indices: false }

              )).then(res => {

              if (res.data == '200'){
                this.$message({
                  message: '登录成功',
                  center: true,
                  type: 'success'
                });
                sessionStorage.setItem('login','true');
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
                      this.$router.push("/admin")
                    }
                  },500)
                }
              }else {
                this.$message({
                  message: res.data,
                  center: true,
                  type: 'error'
                });
                this.toggleCode();
              }

            })

          }
        }
    }
</script>

<style scoped>
  a{
    text-decoration: none;
  }
  #wrapper{
    width:300px;
    margin:0 auto;
    padding-top:30px;
    position: relative;
  }
  h2{
    margin:30px 0;
    font-weight: 400;
    font-size: 18px;
  }
  .switch_nav{
    margin:10px 0 20px;
  }
  .switch_nav a{
    color: #878787;
    font-size: 18px;
    text-align: center;
    margin:0 10px;
    display: inline-block;
  }
  .switch_nav a:hover{
    color: #555;
  }
  .switch_nav .switch_btn.on{
    color: #0f88eb;
    border-bottom: 2px solid #0f88eb;
  }
  .switch_nav .switch_btn{
    color: #878787;

  }
  .switch_btn{
    color:#878787;
    text-decoration: none;
  }
  .formD input{
    width: 274px;
    padding: 16px 12px;
    margin: -1px;
    border:1px solid #d5d5d5;
    border-radius: 3px;
    background-color: #fff;

  }
  .formD input:focus {
    outline: 0;
    border-color: #0f88eb;
  }
  .yzCode{
    display:flex;
    width: 100%;

  }
  .yzCode input{
    width: 100%;
    padding: 16px 12px;
    margin: -1px;
    border:1px solid #d5d5d5;
    border-radius: 3px;
    background-color: #fff;
  }
  .submit_btn{
    width: 100%;
    box-shadow: none;
    border:0 ;
    line-height: 45px;
    font-size: 15px;
    display: block;
    margin:20px 0;
    color: #fff;
    background-color: #0f88eb;
    border-radius: 3px;

  }
  .submit_btn :focus{
    outline: 0;
    border-color: #0f88eb ;
  }

</style>
