<template>
  <div>
    <post-panel @refresh="reload"></post-panel>
    <el-tabs class="tabs" v-if="isTabsAlive" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="我的帖子" name="first"><my-articles/></el-tab-pane>
      <el-tab-pane label="我的关注" name="second"><concern/></el-tab-pane>
      <el-tab-pane label="点赞" name="third"><like/></el-tab-pane>
      <el-tab-pane label="收藏夹" name="fourth"><collection/></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import PostPanel from '@/components/Personal/ReleasePost/index'
import MyArticles from '@/components/Articles/myarticles'
import Concern from "@/components/Articles/concern";
import Like from "@/components/Articles/like";
import Collection from "@/components/Articles/collection";

export default {
  name: "index",
  components:{ PostPanel, MyArticles, Concern, Like, Collection },
  data() {
    return {
      isTabsAlive: true,
      activeName: 'first'//刷新后默认选中的tab
    };
  },
  methods:{
    //刷新帖子组件
    reload () {
      this.isTabsAlive = false
      this.$nextTick(() => (this.isTabsAlive = true))
    },
    handleClick(tab, event) {
      // console.log(tab,event);
    }
  }
}
</script>

<style scoped>
.tabs{
  height: 50px;
  padding: 15px 145px 0 35px;
  /*line-height: 50px;*/
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