<template>
  <div>
    <el-button @click="fun1">{{data}}</el-button>
    <el-tabs :tab-position="'left'" v-model="activeName">
      <el-tab-pane label="全部关注" name="first">
        <div class="follow-head">
          全部关注
        </div>
      </el-tab-pane>
      <el-tab-pane label="特别关注" name="second">
        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">
          特别关注
        </div>
      </el-tab-pane>
      <el-tab-pane label="默认分组" name="third">
        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">
          默认分组
        </div>
      </el-tab-pane>
      <el-tab-pane label="全部粉丝" name="fourth">
        <div class="follow-head">
          粉丝列表
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

export default {
  name: "test",
  components:{
  },
  data(){
    return {
      data: 'ab',
      activeName: 'first'
    }
  },
  created() {
    //this.getConcerns()
    //this.fun1()
  },
  methods:{
    fun1(){
      this.activeName = 'third'
      // this.$router.push({
      //   name:"Test",
      // })
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