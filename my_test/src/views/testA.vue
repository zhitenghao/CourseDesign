<template>
  <div>
    <div style="cursor: pointer">
      <img :src="avatar" title="点击上传头像" class="avatar" @click="toggleShow">
    </div>
<!--    <div style="cursor: pointer">-->
<!--      <el-avatar :src="avatar" title="点击上传头像"/>-->
<!--    </div>-->
    <myUpload
        :modelValue.sync="show"
        :headers="headers"
        :url="updateAvatarApi"
        @srcFileSet="srcFileSet"
        @crop-success="cropSuccess"
        @crop-upload-success="cropUploadSuccess"
        @crop-upload-fail="cropUploadFail"
    />
<!--    @crop-upload-success上传成功事件 @crop-success截取完成事件，上传前-->
  </div>
</template>

<script>
import 'babel-polyfill'
import myUpload from 'vue-image-crop-upload'

export default {
  name: "test",
  components:{
    myUpload,
  },
  data(){
    return {
      show: false,
      avatar: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
      headers: {
        'Authorization': window.localStorage.getItem('tokenStr')
      },
      // updateAvatarApi: 'http://localhost:8081/upload'
      //updateAvatarApi: 'http://localhost:8000/api/users/updateAvatar'
    }
  },
  created() {
    //this.getConcerns()
  },
  methods:{
    toggleShow() {
      this.show = !this.show
    },
    srcFileSet(fileName, fileType, fileSize){
      console.log(fileName, fileType, fileSize)
    },
    cropSuccess(imgDataUrl,field){
      console.log('cropsuccess')
      console.log('img',imgDataUrl)
      console.log('field',field)
    },
    cropUploadSuccess(imgDataUrl,field){
      //  imgDataUrl其实就是图片的base64data码
      this.avatar = imgDataUrl;
      console.log(imgDataUrl)//这里打印出来的是base64格式的资源
      console.log(field)//这里打印出来的是base64格式的资源
      this.$message.success('修改成功')
    },
    cropUploadFail(status, field){
      console.log(status, field)
      this.$message.error('修改失败')
    },
    getConcerns(){
      this.getRequest('/auth/user/myConcernUserList').then(res => {
        this.concerns = res.data.data.records
        //console.log('1',res.data.data.records)
        //将userAvatar加上前缀后可展示出图片
        for(let i=0;i<this.concerns.length;i++){
          this.concerns[i].userAvatar = "http://localhost:8081/downloadPhoto/" + this.concerns[i].userAvatar
        }
        //console.log(this.Concerns)
      })
    },
  }
}

</script>

<style scoped>
.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}
</style>