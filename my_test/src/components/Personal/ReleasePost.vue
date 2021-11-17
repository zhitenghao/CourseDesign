<template>
  <div>
    <div class="releasePostContainer" v-if="releasePostFlag">
      <el-form class="releaseForm" v-model="releaseForm">
        <!-- 待发布帖子的分类-->
        <el-form-item style="width: 87%; margin: 5px auto 5px 40px;">
          <el-radio-group v-model="radioButton" size="small">
            <el-radio-button label="考研"></el-radio-button>
            <el-radio-button label="考公"></el-radio-button>
            <el-radio-button label="四六级"></el-radio-button>
            <el-radio-button label="教资"></el-radio-button>
            <el-radio-button label="其他"></el-radio-button>
          </el-radio-group>
        </el-form-item>
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
        <el-form-item style="width: 100%;">
          <span style="font-size: small; color: chocolate; margin-left: 40px;">( <b>注意 : </b>您可以发布9张图片或1个短视频 , 且分类选项为必填项 )</span>
          <el-button style="margin-left: 95px" @click="cancelRelease">取消</el-button> <!--取消按钮将会关闭发布帖子弹窗-->
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
      releasePostFlag: true,
      releaseForm: {
        postContent:'' // 发布帖子表单的内容
      },
      releasePictureFlag: false,
      releaseVideoFlag: false,
      dialogImageUrl: '',
      dialogVideoUrl: '',
      dialogVisible: false,
      radioButton:'',
      param: "",
      fileList: []
    }
  },
  methods: {
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
