<template>
  <div class="hello">
    <el-image :src="pic"></el-image>
  </div>
</template>

<script>
export default {
  name: "testB",
  props:{
  },
  data(){
    return{
      pic: ''
    }
  },
  created() {
    //this.fun()
  },
  methods:{
    fun(){
      this.getRequest('/auth/downloadPhoto/'+'2222.jpg').then(res=>{
        console.log(res)
        this.pic = res.data.data.imgSrc
      })
    }
  }
}
</script>

<style scoped>
.hello{
  background-color: #E6A23C;
}
</style>