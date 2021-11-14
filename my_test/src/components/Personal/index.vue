<template>
  <div>
    <publish-panel/>
    <el-tabs class="tabs" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="我的帖子" name="first"><my-articles/></el-tab-pane>
      <el-tab-pane label="我的关注" name="second">2</el-tab-pane>
      <el-tab-pane label="点赞" name="third">3</el-tab-pane>
      <el-tab-pane label="收藏夹" name="fourth">4</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import PublishPanel from '@/components/Personal/ReleasePost.vue'
import MyArticles from '@/components/Articles/myarticles.vue'

export default {
  name: "index",
  components:{
    PublishPanel,
    MyArticles,
  },
  data() {
    return {
      activeName: 'first'//刷新后默认选中的tab
    };
  },
  methods:{
    handleClick(tab, event) {
      // console.log(tab,event);
    }
  }
}
</script>

<style scoped>
.tabs{
  height: 50px;
  padding-left: 35px;
  padding-right: 145px;
  line-height: 50px;
}
::v-deep .el-tabs__header{
  margin: 0 0 8px;
}
::v-deep .el-tabs__item {
  /*单个tab*/
  padding-right: 10px;
}
::v-deep .el-tabs__item.is-active{
  /*文字颜色*/
  color:#00b5e5;
}
::v-deep .el-tabs__active-bar{
  /*bar颜色*/
  background-color:#00a1d6;
}
::v-deep .el-tabs__item:hover {
  color: #00b5e5;
}
</style>