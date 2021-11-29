<template>
  <div class="myPage">
    <el-container style="height: 100%">
      <el-aside class="aside" width=240px>
        <div class="head-aside">
          <div class="visitor-avatar">
            <el-avatar :src="userInfo.userAvatar" :size="60" :key="userInfo.userAvatar"/>
          </div>
          <div class="username">
            <h3>{{userInfo.userName}}</h3>
            <div style="font-size: 12px;color: #909399;">{{ userInfo.userDescription }}</div>
          </div>
        </div>
        <div class="middle-aside">
          <div class="visitor-concern" style="border-right: 3px solid #f5f6f7;cursor:pointer" @click="toFans">
            <div style="padding-bottom: 10px">关注</div>
            <strong style="font-size: 22px;color: #121212">{{ userInfo.userConcern }}</strong>
          </div>
          <div class="visitor-concern" style="cursor:pointer" @click="toFans">
            <div style="padding-bottom: 10px">粉丝</div>
            <strong style="font-size: 22px;color: #121212">{{ userInfo.userConcerned }}</strong>
          </div>
        </div>
        <div class="menu-aside">
          <el-menu class="menu" default-active="none">
            <el-menu-item index="1" @click="toPersonalIndex">
              <i class="el-icon-user"></i>
              <span slot="title">ta的主页</span>
            </el-menu-item>
            <el-menu-item index="2" @click="toFans">
              <i class="el-icon-connection"></i>
              <span slot="title">关注/粉丝</span>
            </el-menu-item>
            <el-menu-item index="3" @click="toIndex">
              <i class="el-icon-location"></i>
              <span slot="title">返回首页</span>
            </el-menu-item>
            <el-menu-item index="4" @click="logout">
              <i class="el-icon-switch-button"></i>
              <span slot="title">退出登录</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>

      <el-main class="main">
        <el-tabs class="tabs" v-if="isTabsAlive" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="ta的帖子" name="first"><visitor-articles :type="''" :id="userId"/></el-tab-pane>
          <el-tab-pane label="ta的点赞" name="second"><visitor-articles :type="'Like'" :id="userId"/></el-tab-pane>
          <el-tab-pane label="ta的收藏夹" name="third"><visitor-articles :type="'Concern'" :id="userId"/></el-tab-pane>
        </el-tabs>
        <concerns v-if="!isTabsAlive" :id="userId"/>
      </el-main>
    </el-container>

  </div>
</template>

<script>
import VisitorArticles from "@/components/Articles/visitor-articles";
import Concerns from "@/components/Personal/MyConcerns";

export default {
  name:"index",
  components: { VisitorArticles,Concerns },
  provide(){
    return {
      reload:this.reload
    }
  },
  data(){
    return {
      userInfo: {},
      isRouterAlive: true,
      isTabsAlive: true,
      activeName: 'first', //刷新后默认选中的tab
      userId: this.$route.query.id
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    //获取访客资料
    getUserInfo() {
      this.getRequest('/auth/user/userInfoById/'+this.$route.query.id).then(res => {
        if(res.data.code === 200){
          console.log('userInfo',res)
          this.userInfo = res.data.data
          this.userInfo.userAvatar = "http://localhost:8081/downloadPhoto/" + this.userInfo.userAvatar
          //console.log('userInfo',this.userInfo)
        }else{
          this.$message.error("加载失败")
        }
      })
    },

    toPersonalIndex(){
      this.isTabsAlive = true
    },
    toFans(){
      this.isTabsAlive = false
    },
    toIndex(){
      this.$router.replace('/')
    },
    logout(){
      window.localStorage.removeItem('tokenStr')
      this.$router.replace('/login')
    },

    //刷新帖子组件
    reload () {
      this.isRouterAlive = false
      this.$nextTick(() => (this.isRouterAlive = true))
    },
    handleClick(tab, event) {
      // console.log(tab,event);
    }

  }
}

</script>

<style>
/*侧边栏*/
.myPage{
  width: 1200px;
  height: 100%;
  margin: 0 auto;
  background-color: #FFFFFF;
}
.main{
  overflow-y: auto;
  height: 100%;
  padding: 0;
}
.main::-webkit-scrollbar{
  display: none;
}
.aside{
  position: relative;
  height: 100%;
  border-right: 10px solid #f5f6f7;
}
.head-aside{
  height: 90px;
  border-top: 1px solid #f5f6f7;
  border-bottom: 10px solid #f5f6f7;
}
.visitor-avatar{
  width: 60px;
  height: 60px;
  margin: 10px 0 0 10px;
  border-radius: 50%;
  float: left;
}
.username{
  float: left;
  height: 80px;
  width: 110px;
  margin: 0 0 0 8px;
}
h3{
  -webkit-margin-after: 12px;
  -webkit-margin-before: 14px;
}
.middle-aside{
  display: flex;
  height: 100px;
  border-bottom: 10px solid #f5f6f7;
}
.visitor-concern{
  float: left;
  width: 50%;
  padding: 20px 0;
  text-align: center;
  font-size: 15px;
  color:#8590a6;
}
/*main*/
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
