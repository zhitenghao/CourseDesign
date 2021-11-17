<template>
  <div class="hello">
    <el-radio-group size="small" v-model="postForm.articleCategory">
      <el-radio-button label="16079fd48923558881177ab3e6beb60c">考研</el-radio-button>
      <el-radio-button label="d7006f3df83c0d660f8970c9276636e5">考公</el-radio-button>
      <el-radio-button label="1cc762969af87c11be75284bcd205a1d">四六级</el-radio-button>
      <el-radio-button label="66cfc92080b8c440158282558b0191b0">教资</el-radio-button>
      <el-radio-button label="bac73f5fdf3ba2d3ea80e4399c92ce0a">其他</el-radio-button>
    </el-radio-group>
    <div>
      <el-button @click="fun">button</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "testB",
  props:{
  },
  data(){
    return{
      postForm: { // 发帖表单
        articleCategory:'',//帖子分类
      },
    }
  },
  created() {
    //this.fun()
  },
  methods:{
    fun(){
      console.log('分类',this.postForm.articleCategory)
    }

  }
}
</script>

<style scoped>
.hello{
  background-color: #E6A23C;
}
</style>