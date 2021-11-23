<template>
<!--  发帖组件原始版-->
  <el-form :model="form">
    <el-button class="el-button el-button--primary" @click="releasePictureFlag=!releasePictureFlag">发图片</el-button>
    <div v-if="releasePictureFlag">
      <el-form-item label="上传图片" :label-width="formLabelWidth">
        <el-upload
            ref="uploadPic"
            action="http://localhost:8081/upload"
            accept="image/png,image/gif,image/jpg,image/jpeg"
            list-type="picture-card"
            :limit=limitNum
            :auto-upload="false"
            :on-exceed="handleExceed"
            :before-upload="handleBeforeUpload"
            :on-preview="handlePictureCardPreview"
            :on-success="success"
            :on-remove="handleRemove">
          <i class="el-icon-plus"></i>
        </el-upload>

        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
    </div>

    <el-form-item>
      <el-button size="small" type="primary" @click="uploadFile">立即上传</el-button>
      <el-button size="small">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>

export default {
  data() {
    return{
      dialogImageUrl: '',
      dialogVisible: false,
      releasePictureFlag: true,
      formLabelWidth: '80px',
      limitNum: 3,
      form: {},
      articleUrls: []
    }
  },
  methods: {
    // 上传文件之前的钩子
    handleBeforeUpload(file){
      console.log('before')
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

    },
    // 文件列表移除文件时的钩子
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // 点击文件列表中已上传的文件时的钩子
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    uploadFile() {
      this.$refs.uploadPic.submit()
      console.log('finished')
      this.releasePictureFlag = false
    },
    success(res,file,fileList){
      //file的内容是回调信息
      console.log('ok',res,file)
      // this.articleUrls.push(res.data.id)
      // console.log('urls',this.articleUrls)
    }
  }
}
</script>

<style scoped>

</style>