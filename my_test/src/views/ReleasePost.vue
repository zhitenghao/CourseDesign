<template>
  <div>
    <el-button type="primary" @click="releasePost">发帖</el-button> <!-- 模仿主页的发帖按钮，点击发帖按钮打开发帖容器 -->
    <div class="releasePostContainer" v-if="releasePostFlag">
      <el-form class="releaseForm" v-model="releaseForm">
          <!-- 待发布帖子内容 -->
          <el-form-item style="width: 87%; margin-left: 40px;">
            <span>请输入待发布帖子的内容 :</span>
            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入.." v-model="releaseForm.postContent"></el-input>
          </el-form-item>
          <!--发布图片或视频 -->
          <el-form-item style="width: 87%; margin-left: 40px;">
            <div> <!-- 单选框按钮：选择发图片 或 发视频，并且该单选框会控制 上传图片 和 上传视频 的div -->
              <el-button class="el-button el-button--primary" @click="choosePicture">发图片</el-button>
              <el-button class="el-button el-button--primary" @click="chooseVideo">发视频</el-button>
              <span style="font-size: small; color: chocolate; margin-left: 15px">( <b>注意 : </b>您可以发布9张图片或1个短视频 )</span>
            </div>
            <div v-if="releasePictureFlag"> <!-- 上传图片的div -->
              <el-form-item style="margin-top: 10px"> <!--待发布帖子的图片-->
                <!--上传图片action 如果是手动上传 action随便填一下就好了，自动上传填接口地址就行-->
                <!-- :limit="9" 限制上传的图片数量 -->
                <el-upload
                  action="https://jsonplaceholder.typicode.com/posts/"
                  list-type="picture-card"
                  :on-preview="handlePictureCardPreview"
                  :limit="9"
                  multiple
                  :auto-upload="false"
                  :on-remove="handleRemove">
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
          <el-form-item style="width: 23%; margin-left: 495px;">
            <el-button @click="cancelRelease">取消</el-button> <!--取消按钮将会关闭发布帖子弹窗-->
            <el-button type="primary" @click="onsubmit">发布</el-button>
          </el-form-item>
        </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReleasePost',
  data () {
    return {
      releasePostFlag: false,
      releaseForm: {
        postContent:'' // 发布帖子表单的内容
      },
      releasePictureFlag: false,
      releaseVideoFlag: false,
      dialogImageUrl: '',
      dialogVideoUrl: '',
      dialogVisible: false,
      //
      param: "",
      fileList: []
    }
  },
  methods: {
    releasePost() { // 控制 “发帖“ 按钮
      this.releasePostFlag = !this.releasePostFlag
    },
    choosePicture() {
      this.releasePictureFlag = !this.releasePictureFlag
      this.releaseVideoFlag = false
    },
    chooseVideo() {
      this.releaseVideoFlag = !this.releaseVideoFlag
      this.releasePictureFlag = false
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleVideoCardPreview(file) {
      this.dialogVideoUrl = file.url;
      this.dialogVisible = true;
    },
    // 提交帖子
    onsubmit() {
      console.log('submit!');
      this.$message('您已提交');
    },
    // 取消发布帖子
    cancelRelease() {
      this.releasePostFlag = false
      this.$message('您已取消发布');
    }
  }
}
</script>

<style>
.releasePostContainer{
  border-radius: 15px;
  background-clip: padding-box;
  margin: 20px auto;
  padding: 10px 10px 10px 10px;
  background: rgba(255,255,255,1);
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  width: 700px;
  /*height: 300px;*/
  position: absolute;
  left: 420px;
  top: 60px;
  z-index: 20; /*使发帖的组件置于最高层*/
}
el-input{
  height: 600px;
}
.releaseForm{
  /*border: 1px solid #e52121;*/
}
</style>
