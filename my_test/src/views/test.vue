<template>
  <div class="articles">
<!--    <div v-for="(item,index) in items" :key="item.id">-->
<!--      <el-button @click="one(index)">点赞</el-button>-->
<!--      <div v-if="unknown">123</div>-->
<!--&lt;!&ndash;      <div>{{item.index}}</div>&ndash;&gt;-->
<!--    </div>-->

  </div>
</template>

<script>
import axios from 'axios'// 导入axios

export default {
  name: "test",
  data(){
    return {
      //flag: true,
      unknown: false,
      items:[
        {
          id: 0,
          name: 'sy1',
          isLike: true
        },
        {
          id: 1,
          name: 'sy2',
          isLike: true
        },
        {
          id: 2,
          name: 'sy3',
          isLike: true
        },
      ]
    }
  },
  created() {
    //this.getArticle()
  },
  methods:{
    // getArticle(){
    //   axios.get('http://localhost:8081/love/add').then(res => {
    //     console.log(res)
    //   })
    // },
    one(index){
      this.unknown = ! this.unknown
      //document.getElementById('index'). = ;
      //console.log(item)
    },
  }
}

</script>

<style scoped>

</style>