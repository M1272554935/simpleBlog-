<template>
  <div>
    <div style="height: 40px">
      <div style="height: 10px"></div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>草稿编辑</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="text-align: left">
      <el-tag effect="dark">文章标题:</el-tag>
      <input v-model="articleTitle" type="text" class="titleInp"></input>

      <el-tag effect="dark">文章简介:</el-tag>
      <input v-model="articleInfo" type="text" class="infoInp"></input>
    </div>

    <el-drawer
      title=" "
      :visible.sync="drawer"
      :with-header="false">

      <div >
        <el-tag effect="dark">文章标签:</el-tag>
        <el-select v-model="checkLabel" multiple placeholder="请选择文章标签">
          <el-option
            v-for="item in labelList"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </div>

      <div >
        <el-tag effect="dark">文章类型:</el-tag>
        <el-select v-model="type" clearable placeholder="请选择文章类型">
          <el-option
            v-for="item in typeList"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </div>

    </el-drawer>


    <el-dialog
      title="请上传封面:"
      :visible.sync="centerDialogVisible"
      width="30%"
      center="true">
      <el-upload
        action="/api/admin/upload"
        list-type="picture-card"
        :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove" >
        <i class="el-icon-plus"></i>
      </el-upload>
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

    <!--  markdown编辑-->
    <mavon-editor class="editer" v-model="articleContent" ref=md @imgAdd="imgAdd" />



    <div class="submit_btn">
      <el-button @click="drawer = true"  style="margin-left: 10px;" type="primary">
        选择文章类型及标签
      </el-button>

      <el-button type="primary" @click="centerDialogVisible = true">上传文章封面</el-button>
      <el-button type="primary" @click="saveArt">保 存</el-button>
      <el-button type="primary" @click="submit">发 布</el-button>
    </div>
  </div>
</template>

<script>


    export default {
        name: "draftEdit",

      data(){
        return {
          aid : this.$route.params.articleId,
          articleContent: "",                                   //文章内容
          type: "",                                             //文章类型
          articleTitle:"",                                      //文章标题
          articleInfo:"",                                       //简介
          checkLabel:[],                                        //选择的标签
          typeList:['技术','海洋','随笔'],                        //类型列表
          labelList:[],                                         //标签列表
          dialogImageUrl: '',                                   //封面图片url
          count:'',                                             //倒计时
          dialogVisible: false,
          drawer: false,
          centerDialogVisible: false,
          toolbars: {
            bold: true,         // 粗体
            italic: true,       // 斜体
            header: true,       // 标题
            underline: true,    // 下划线
            mark: true,         // 标记
            superscript: true,  // 上角标
            quote: true,        // 引用
            ol: true,           // 有序列表
            link: true,         // 链接
            imagelink: true,    // 图片链接
            help: true,         // 帮助
            code: true,         // code
            subfield: true,     // 是否需要分栏
            fullscreen: true,   // 全屏编辑
            readmodel: true,    // 沉浸式阅读
            /* 1.3.5 */
            undo: true,         // 上一步
            trash: true,        // 清空
            save: true,         // 保存（触发events中的save事件）
            /* 1.4.2 */
            navigation: true    // 导航目录
          }
        }
      },
      mounted () {

        this.getDraftArticle(this.$route.params.articleId);
        this.getLabelList();
      },
      watch: {
        '$route.params.articleId':function(val, old) {
          if (val !== old) {
            this.firstMe();
          }
        },
      },
      methods: {
        firstMe: function(){
          if ( this.$route.params.articleId != null){
            this.getDraftArticle(this.$route.params.articleId);
          }

        },
        getDraftArticle(Aid){
          this.$axios.get('/api/admin/draft', {
            params: {
              aid: Aid
            }
          }).then(res => {
            this.articleContent = res.data.article.content;
            this.type = res.data.article.type;
            this.articleTitle = res.data.article.title;
            this.articleInfo = res.data.article.info;
            this.dialogImageUrl = res.data.article.type;
            this.checkLabel = res.data.label;

          })

        } ,
        getLabelList(){

          this.$axios.get('/api/admin/push').then(res => {

            this.labelList = res.data.label;
          })

        },
        checkArticle(){
          if (this.articleContent == null || this.articleContent == ""){
            alert("内容不能为空！");
            return false
          }
          if (this.type == null || this.type == ""){
            alert("请选择文章类型！");
            return false
          }
          if (this.articleInfo == null || this.articleInfo == ""){
            alert("文章简介不能为空！");
            return false
          }
          if (this.articleTitle === null || this.articleTitle == ""){
            alert("标题不能为空！");
            return false
          }
          if (this.checkLabel.length == 0){
            alert("请选择文章标签！");
            return false
          }
        },
        //发布
        submit(){
          this.checkArticle();
          var qs = require('qs');
          this.$axios.post('/api/admin/push/add',qs.stringify(
            {
              content: this.articleContent,
              type: this.type,
              title: this.articleTitle,
              info: this.articleInfo,
              label: JSON.stringify(this.checkLabel),
              titleImg: this.dialogImageUrl
            }, { indices: false }

          )  ).then(res => {
            if (res.data === 200){
              this.$message({
                message: '发布成功 , 即将跳转首页!',
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
                    this.$router.push("/admin")
                  }
                },1000)
              }
            }else {
              this.$message({
                message: '发布失败!',
                center: true,
                type: 'error'
              });
            }
          })
        },
        //保存
        saveArt(){
          this.checkArticle();
          var qs = require('qs');
          this.$axios.post('/api/admin/push/save',qs.stringify(
            {
              aid: this.aid,
              content: this.articleContent,
              type: this.type,
              title: this.articleTitle,
              info: this.articleInfo,
              label: JSON.stringify(this.checkLabel),
              titleImg: this.dialogImageUrl
            }, { indices: false }

          )  ).then(res => {
            if (res.data=== 200 ||res.status === 200){
              this.$message({
                message: '保存成功!',
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
                    //this.$router.push("/admin")
                  }
                },1000)
              }
            }else {
              this.$message({
                message: '发布失败!',
                center: true,
                type: 'error'
              });
            }
          })
        },
        imgAdd(pos, $file){

          var formdata = new FormData();
          formdata.append('image', $file);
          this.$axios.post("/api/admin/uploadImg",formdata).then((res) => {

            this.$refs.md.$img2Url(pos, res.data.imgUrl) //回显
          })
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePictureCardPreview(file) {
          this.dialogImageUrl = file.url;
          this.dialogVisible = true;

        },
      },
    }
</script>

<style scoped>
  edit_container{
    padding: 40px;
    margin-bottom: 40px;
  }
  .editer{
    height: 780px;
  }
  .submit_btn{
    text-align: center;
  }
  .infoInp{
    width: 40%;
    height: 40px;
    margin-right: 20px;
  }
  .titleInp{
    width: 34%;
    height: 30px;
    margin-right: 60px;
  }
</style>
