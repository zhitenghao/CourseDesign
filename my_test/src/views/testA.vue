<template>
  <div>

  </div>
</template>

<script>
import axios from 'axios'// 导入axios
import testB from '@/views/testB'

export default {
  name: "test",
  components:{
    testB
  },
  data(){
    return {
      title:"原值0",
      allConcerns:[
        {src:require('../assets/images/beauty3.jpg'),name:"一路生花",introduce:"愿你一路披荆斩棘，繁花似锦"},
        {src:require('../assets/images/boy1.jpg'),name:"小兰不难",introduce:"一个积极废物男大学生"},
        {src:require('../assets/images/littlegirl.jpg'),name:"糖果超级甜~",introduce:"世界辣么美，糖果辣么甜！"},
        {src:require('../assets/images/beauty1.jpg'),name:"酒醉三分醒",introduce:"当你活得足够优秀，你会发现，有人活成了你的影子，而你成了他们的方向。"},
        {src:require('../assets/images/beauty4.jpg'),name:"清欢",introduce:"即使生命如尘，仍愿岁月如歌。"},
        {src:require('../assets/images/boy2.jpg'),name:"时光倒影",introduce:"那些让我们哭过的事,总有一天会笑着说出来。"},
        {src:require('../assets/images/beauty2.jpg'),name:"人间妄想",introduce:"这个人很懒，什么都没有留下哦~"},
        {src:require('../assets/images/cute1.jpg'),name:"李萌不萌",introduce:"李萌一点也不萌，谢谢！"},
        {src:require('../assets/images/cute2.jpg'),name:"散发着仙气的小仙女",introduce:"你是小仙女，不能泄露了仙气。"}
      ],
      concerns:[]
    }
  },
  created() {
    this.getConcerns()
  },
  methods:{
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
ul{
  list-style: none;
  padding-left: 10px;
}
.list-item{
  position: relative;
  float: left;
  height: 70px;
  width: 800px;
  border: 1px solid #eee;
  padding: 15px 0;
  text-align: left;
  line-height: 70px;
}
.cover{
  position: absolute;
  overflow: visible;
  float: left;
  width: 70px;
  height: 70px;
  margin-left:15px;
  border-radius: 50%;
}
.content{
  float: left;
  position: relative;
  height: 70px;
  width: 500px;
  margin-top: 6px;
  margin-left: 105px;
  padding-right: 200px;
  text-align: left;
  line-height: 50px;
}
.title{
  overflow: hidden;
  text-overflow: ellipsis;
  height: 25px;
  max-width: 500px;
  margin-bottom: 12px;
  font-size: 16px;
  line-height: 25px;
}
.introduce{
  max-width: 500px;
  text-overflow: ellipsis;
  text-align: left;
  line-height: 20px;
  font-size: 13px;
  color: #909399;
}
.fans-action{
  position: absolute;
  right: 20px;
  top: 4px;
}
</style>