<template>
<!--  发帖组件测试-->
  <div class="releasePostContainer" v-if="releasePostFlag">
    <el-form class="releaseForm" v-model="postForm">
      <!-- 待发布帖子的分类-单选框-->
      <el-form-item style="width: 87%; margin: 5px auto 5px 40px;">
        <el-radio-group size="small" v-model="postForm.articleCategory">
          <el-radio-button label="16079fd48923558881177ab3e6beb60c">考研</el-radio-button>
          <el-radio-button label="d7006f3df83c0d660f8970c9276636e5">考公</el-radio-button>
          <el-radio-button label="1cc762969af87c11be75284bcd205a1d">四六级</el-radio-button>
          <el-radio-button label="66cfc92080b8c440158282558b0191b0">教资</el-radio-button>
          <el-radio-button label="bac73f5fdf3ba2d3ea80e4399c92ce0a">其他</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <!-- 待发布帖子内容 -->
      <el-form-item style="width: 87%; margin-left: 40px;">
        <span>请输入待发布帖子的内容 :</span>
        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入.." v-model="postForm.articleContent"></el-input>
      </el-form-item>
      <!--发布图片或视频 -->
      <el-form-item style="width: 87%; margin-left: 40px;">
        <div> <!-- 单选框按钮：选择发图片 或 发视频，并且该单选框会控制 上传图片 和 上传视频 的div -->
          <el-button class="el-button el-button--primary" @click="addPicture">发图片</el-button>
          <el-button class="el-button el-button--primary" @click="addVideo">发视频</el-button>
        </div>
        <div v-if="releasePictureFlag"> <!-- 上传图片的div -->
          <el-form-item style="margin-top: 10px"> <!--待发布帖子的图片-->
            <el-upload
                ref="upload"
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
                :on-success="handleSuccess"
                >
              <i class="el-icon-plus"></i>
            </el-upload>

            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </el-form-item>
        </div>

        <div v-if="releaseVideoFlag"> <!-- 上传视频的div -->
          <el-form-item style="margin-top: 10px"> <!-- 待发布帖子的视频 -->
            <el-upload
                action="https://jsonplaceholder.typicode.com/posts/"
                list-type="picture-card"
                :on-preview="handleVideoCardPreview"
                :limit="1"
                multiple
                :auto-upload="false"
                :on-remove="handleRemove">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogVideoUrl" alt="">
            </el-dialog>
          </el-form-item>
        </div>
      </el-form-item>
      <!--待发布帖子的发布或取消-->
      <el-form-item style="width: 100%;">
        <span style="font-size: small; color: chocolate; margin-left: 40px;">
          ( <b>注意 : </b>您可以发布9张图片或1个短视频 , 且分类选项为必填项 )</span>
        <el-button style="margin-left: 95px" @click="cancelRelease">取消</el-button> <!--取消按钮将会关闭发布帖子弹窗-->
        <el-button type="primary" @click="onsubmit">发布</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      releasePostFlag: true,//是否显示
      releasePictureFlag: false,
      releaseVideoFlag: false,
      dialogImageUrl: '',
      dialogVideoUrl: '',
      dialogVisible: false,
      postForm: { // 发帖表单
        articleCategory:'',//帖子分类
        articleContent:'',
        isVideo: 0,
        urls: []
      },

    }
  },
  methods: {
    addPicture() {
      this.releasePictureFlag = !this.releasePictureFlag
      this.releaseVideoFlag = false
    },
    addVideo() {
      this.releaseVideoFlag = !this.releaseVideoFlag
      this.releasePictureFlag = false
    },

    // 上传文件之前的钩子
    handleBeforeUpload(file){
      //console.log('before')
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
    // 文件超出个数限制时的钩子
    handleExceed(files, fileList) {
      this.message.error("最多只能添加9张图片")
    },
    //on-preview钩子,点击已上传的文件触发
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // handleVideoCardPreview(file) {
    //   this.dialogVideoUrl = file.url;
    //   this.dialogVisible = true;
    // },
    //on-remove移除文件时钩子
    handleRemove(file, fileList) {
      let index = this.postForm.urls.indexOf(file.response.data.id)
      if(index !== -1){
        this.postForm.urls.splice(index,1)
      }
      console.log('remove',file, fileList);
    },
    //上传图片成功的钩子,res接收上传接口的返回信息
    handleSuccess(res,file){
      this.postForm.urls.push(res.data.id)
      console.log('上传url',res.data,file)
    },
    // 提交帖子
    onsubmit() {
      // this.$refs.upload.submit()
      console.log('form',this.postForm)
      //提交表单
      this.postRequest('/auth/article/add',this.postForm).then(res => {
        console.log(res)
      })
      // this.$message.success('您已成功提交');
      // this.$message.error('提交失败');
    },
    // 取消发布帖子
    cancelRelease() {
      this.releasePostFlag = false
      this.$message('您已取消发布');
    },

  }
}
</script>

<style scoped>
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
</style>
