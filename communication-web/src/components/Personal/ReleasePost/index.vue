<template>
  <div>
    <div class="releasePostContainer" v-if="releasePostFlag">
      <el-form class="releaseForm" :rules="rules" ref="postForm" :model="postForm">
        <!-- 待发布帖子的分类-单选框-->
        <el-form-item prop="articleCategory" style="width: 87%; margin: 5px auto 5px 40px;">
          <el-radio-group size="small" v-model="postForm.articleCategory">
            <el-radio-button label="16079fd48923558881177ab3e6beb60c">考研</el-radio-button>
            <el-radio-button label="d7006f3df83c0d660f8970c9276636e5">考公</el-radio-button>
            <el-radio-button label="1cc762969af87c11be75284bcd205a1d">四六级</el-radio-button>
            <el-radio-button label="66cfc92080b8c440158282558b0191b0">教资</el-radio-button>
            <el-radio-button label="bac73f5fdf3ba2d3ea80e4399c92ce0a">其他</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <!-- 待发布帖子内容 -->
        <el-form-item prop="articleContent" style="width: 87%; margin-left: 40px;">
          <span>请输入待发布帖子的内容 :</span>
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入.." v-model="postForm.articleContent"></el-input>
        </el-form-item>
        <!--发布图片或视频 -->
        <el-form-item style="width: 87%; margin-left: 40px;">
          <div> <!-- 单选框按钮：选择发图片 或 发视频，并且该单选框会控制 上传图片 和 上传视频 的div -->
            <el-button class="el-button el-button--primary" @click="addPicture" :disabled="addPictureFlag">发图片</el-button>
            <el-button class="el-button el-button--primary" @click="addVideo" :disabled="addVideoFlag">发视频</el-button>
          </div>
          <div v-if="releasePictureFlag"> <!-- 上传图片的div -->
            <el-form-item label="图片上传" style="margin-top: 10px"> <!--待发布帖子的图片-->
              <el-upload
                  ref="uploadPic"
                  action="http://localhost:8081/upload"
                  accept="image/png,image/gif,image/jpg,image/jpeg"
                  list-type="picture-card"
                  multiple
                  :limit="9"
                  :auto-upload="true"
                  :on-exceed="handleExceed"
                  :before-upload="handleBeforeUpload"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :on-success="handleSuccess">
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
          </div>
          <div v-if="releaseVideoFlag"> <!-- 上传视频的div -->
            <el-form-item label="视频上传" style="margin-top: 10px"> <!-- 待发布帖子的视频 -->
              <el-upload
                  ref="uploadVid"
                  action="http://localhost:8081/upload"
                  list-type="picture-card"
                  :show-file-list="false"
                  :before-upload="beforeUploadVideo"
                  :on-progress="uploadVideoProcess"
                  :on-success="handleVideoSuccess"
                  >
                <video class="video" v-if = "postForm.urls.length !== 0 && videoFlag === false" :src="videoOriginUrl" controls="controls">
                  您的浏览器不支持视频播放
                </video>
                <i v-else-if = "postForm.urls.length === 0 && videoFlag === false" class="el-icon-upload"></i>
                <el-progress v-if="videoFlag === true" type="circle" :percentage="videoUploadPercent" style="margin-top:10px;">
                </el-progress>
              </el-upload>
            </el-form-item>
          </div>
        </el-form-item>
        <!--待发布帖子的发布或取消-->
        <el-form-item style="width: 100%;">
        <span style="font-size: small; color: chocolate; margin-left: 40px;">
          ( <b>注意 : </b>您可以发布至多9张图片或者1个短视频 , 且分类选项为必填项  )</span>
          <el-button style="margin-left: 70px" @click="cancelRelease">取消</el-button> <!--取消按钮将会关闭发布帖子弹窗-->
          <el-button type="primary" @click="onsubmit">发布</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'index',
  data() {
    return {
      releasePostFlag: true, //显示发帖组件
      releasePictureFlag: false,
      releaseVideoFlag: false,
      addPictureFlag: false,
      addVideoFlag: false,
      dialogImageUrl: '',
      dialogVideoUrl: '',
      dialogVisible: false,

      videoFlag: false,//是否显示进度条el-progress
      videoUploadPercent: 0,
      videoOriginUrl: '',

      postForm: { // 发帖表单
        articleCategory:'',//帖子分类
        articleContent:'',
        isVideo: 0,
        urls: []
      },
      rules: {
        articleCategory: [{required: true, message: '请选择分类', trigger: 'blur'}],
        articleContent: [{required: true, message: '请输入内容', trigger: 'blur'}]
      }
    }
  },
  watch: {
    'postForm.urls': {
      handler(newVal,oldVal){
        //console.log(newVal)
        if(this.postForm.isVideo === 0){
          if(newVal.length === 0){
            this.addVideoFlag = false
          }else{
            this.addVideoFlag = true
          }
        }else{
          if(newVal.length === 0){
            this.addPictureFlag = false
          }else{
            this.addPictureFlag = true
          }
        }
      },
      deep:true
    }
  },
  methods: {
    addPicture() {
      this.releasePictureFlag = !this.releasePictureFlag
      this.releaseVideoFlag = false
      this.postForm.isVideo = 0
    },
    addVideo() {
      this.releaseVideoFlag = !this.releaseVideoFlag
      this.releasePictureFlag = false
      this.postForm.isVideo = 1
    },
    // 上传图片之前的钩子
    handleBeforeUpload(file){
      if(!(file.type === 'image/png' || file.type === 'image/gif' || file.type === 'image/jpg' || file.type === 'image/jpeg')) {
        this.$notify.warning({
          title: '警告',
          message: '请上传格式为image/png, image/gif, image/jpg, image/jpeg的图片'
        })
      }
      let size = file.size / 1024 / 1024 / 2
      if(size > 2) {
        this.$notify.warning({
          title: '警告',
          message: '图片大小必须小于2M'
        })
      }
    },
    // 图片文件超出个数限制时的钩子
    handleExceed() {
      this.$message.error("最多只能添加9张图片")
    },
    // on-preview钩子,点击已上传的图片触发
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // on-remove移除图片时钩子
    handleRemove(file) {
      console.log('remove',file)
      //在表单中删除该图片的url
      let index = this.postForm.urls.indexOf(file.response.data.id)
      if(index !== -1){
        this.postForm.urls.splice(index,1)
      }
    },
    // 上传图片成功的钩子
    handleSuccess(res){
      console.log(res)
      if(res.code === 200){
        //将接口返回的res中返回图片的id存入表单中
        this.postForm.urls.push(res.data.id)
        // this.$message.success('添加图片成功');
      }else{
        this.$message.error('添加图片失败');
      }
    },

    // 上传前检查视频格式和大小
    beforeUploadVideo(file) {
      const isLt50M = file.size / 1024 / 1024  < 50;
      if (['video/mp4', 'video/ogg', 'video/flv','video/avi','video/wmv','video/rmvb'].indexOf(file.type) === -1) {
        this.$message.error('请上传正确的视频格式');
        return false;
      }
      if (!isLt50M) {
        this.$message.error('上传视频大小不能超过50MB哦!');
        return false;
      }
    },
    // 视频上传中进度显示
    uploadVideoProcess(event, file, fileList){
      this.videoFlag = true;
      this.videoUploadPercent = Math.floor(event.percent)
    },
    // 视频上传成功
    handleVideoSuccess(res, file) {
      this.videoFlag = false;
      this.videoUploadPercent = 0;
      console.log(res)
      if(res.code === 200){
        this.videoOriginUrl = "http://localhost:8081/downloadVideo/" + res.data.id
        this.postForm.urls.push(res.data.id)
      }else{
        this.$message.error('视频上传失败，请重新上传！');
      }
    },
    // 提交帖子
    onsubmit() {
      // this.$refs.upload.submit()
      console.log('form',this.postForm)
      //提交表单
      this.$refs.postForm.validate((valid) => {
        if(valid){
          this.postRequest('/auth/article/add',this.postForm).then(res => {
            //console.log(res)
            if(res.data.code === 200){
              this.$message.success('您已成功提交');
            }else{
              this.$message.error('提交失败');
            }
          })
          //提交后前端清空发帖内容
          this.postForm.articleCategory = ''
          this.postForm.articleContent = ''
          this.postForm.urls = []
          this.videoOriginUrl = ''
          this.releasePictureFlag = false
          this.releaseVideoFlag = false
          this.$emit("refresh")
        }else{
          this.$message.error('请完善内容')
          return false
        }
      })
    },
    // 取消发布帖子
    cancelRelease() {
      //this.releasePostFlag = false
      this.postForm.articleCategory = ''
      this.postForm.articleContent = ''
      this.releasePictureFlag = false
      this.releaseVideoFlag = false
      this.postForm.urls = []
      this.videoOriginUrl = ''
      this.$message('您已取消发布');
    },
  }
}
</script>

<style>
.releasePostContainer{
  position: relative;
  width: 700px;
  margin: 5px auto 0 65px;
  border: 1px solid #eaeaea;
  border-radius: 15px;
  padding: 10px 10px 10px 10px;
  box-shadow: 0 0 25px #cac6c6;
  z-index: 20; /*使发帖的组件置于最高层*/
  background-clip: padding-box;
  background: rgba(255,255,255,1);
}
.releaseForm{
  /*border: 1px solid #e52121;*/
}
/deep/ .el-upload{
  width: 100%;
}
/deep/ .el-upload--picture-card{
  height: 260px;
}
.video{
  object-fit: fill;
  width:100%;
  height:100%;
}
</style>
