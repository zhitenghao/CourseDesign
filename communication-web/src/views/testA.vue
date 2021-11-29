<template>
  <div>
    <el-button @click="fun" ref="aa">{{data}}</el-button>
    {{text}}

    <p></p>
    {{textB}}
  </div>
</template>

<script>
import { encrypt,decrypt } from '@/utils/rsaEncrypt'

export default {
  name: "test",
  components:{
  },
  data(){
    return {
      data: 'ab',
      text:'',
      textB:''
    }
  },
  created() {
    //this.getConcerns()
    this.fun1()
  },
  methods:{
    fun1(){
      this.text = encrypt("123")
      console.log(this.text)
      this.textB= decrypt(this.text)
    },
    fun(){
      let that=this;
      that.data="修改后的值";
      console.log(that.$refs.aa.innerText);
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
</style>