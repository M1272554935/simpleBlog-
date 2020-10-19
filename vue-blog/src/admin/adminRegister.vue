<template>
  <div id="wrapper">
    <div>
      <h2></h2>
    </div>
    <nav class="switch_nav">
      <a href="javascript:;" id="switch_signup" class="switch_btn on">注册</a>
      <a ><router-link :to="{name:'adminLogin'}">登录</router-link></a>
      <div class="switch_bottom" id="switch_bottom"></div>
    </nav>
    <div  class="formD">
       <input v-model="screenname" type="text" placeholder="昵称"  />
       <input v-model="email" type="text" placeholder="邮箱" />
       <input v-model="username" type="text" placeholder="账号 "  />
       <input v-model="password" type="password" placeholder="密码(不少于6位)"  />
        <div class="yzCode">
          <input v-model="yzCode" type="text"  placeholder="验证码"/>
          <div class="yzD2"><img :src="imgHtml" class="img1" @click="getCode"></div>
        </div>
      <button  class="submit_btn" @click="checkResForm()">注册</button>
    </div>
  </div>
</template>

<script>
    export default {
        name: "adminRegister",
      data() {
        return {
          screenname: '',
          email:'',
          username: '',
          password:'',
          yzCode:'',                  //验证码
          imgHtml: '/api/getCode',    //验证码图片
        }
      },
      mounted () {
        this.getCode();
      },
      methods: {
        getCode() {
          var num=Math.ceil(Math.random()*10);//生成一个随机数（防止缓存）
          this.imgHtml = "/api/getCode?" + num;
        },
        checkResForm(){
          var regSName = /[~#^$@%&!*()<>:;'"{}【】  ]/;
          var checkEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
          var regUsnAndPsd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;

          if (this.screenname ==='' || this.screenname.length === 0 ||  this.screenname.length >20 || regSName.test(this.screenname))
          {
            this.$message.error('昵称非空，长度1-20位，不包含特殊字符！');
            return false;
          }
          if (this.email ==='' || this.email.length === 0 || !checkEmail.test(this.email)){
            this.$message.error('邮箱格式错误！');
            return false
          }
          if (this.username ==='' || this.username.length === 0 || !regUsnAndPsd.test(this.username)){
            this.$message.error('账号为6-20位字母、数字的组合！');
            return false
          }
          if (this.password ==='' || this.password.length === 0 || !regUsnAndPsd.test(this.password)){
            this.$message.error('密码为6-20位字母、数字的组合！');
            return false
          }
          if (this.yzCode === '' || this.yzCode.length === 0){
            this.$message.error('请输入验证码！');
            return false
          }
          this.register();

        },
        register() {
          var qs = require('qs');
          this.$axios.post('/api/toRegister',
            qs.stringify(
              {
                email: this.email,
                screenname: this.screenname,
                username: this.username,
                password: this.password,
                verificationCode: this.yzCode
              }, { indices: false }

            )).then(res => {

            if (res.data == '200'){
              this.$message({
                message: '注册成功,即将跳转登录!',
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
                    this.$router.push("/login")
                  }
                },1000)
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
  .submit_btn :focus{
    outline: 0;
    border-color: #0f88eb ;
  }
</style>
