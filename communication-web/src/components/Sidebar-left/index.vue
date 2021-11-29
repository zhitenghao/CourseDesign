<template>
  <div class="sidebar-left">
    <div id="user-info">
      <div class="have-login">
        <div class="circlePic" style="cursor:pointer" @click="toPersonalIndex">
          <el-avatar :src="userAvatar" :size="110"/>
        </div>
        <div class="divide-line">
          <el-divider class="divider" style="width: 200px;right: 24px;"></el-divider>
        </div>
      </div>
    </div>
    <div class="menu" style="margin-top: 0;">
      <el-row class="tac">
        <el-col :span="24">
          <el-menu default-active="" router class="menu-left"
                   background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="1"  @click="toPersonalIndex">
              <template slot="title">
                <i class="el-icon-house"></i>
                <span>个人中心</span>
              </template>
            </el-menu-item>
            <el-menu-item index="2" @click="toMessages">
              <i class="el-icon-chat-dot-square"></i>
              <span slot="title">消息</span>
            </el-menu-item>
            <el-menu-item index="3" @click="toFans">
              <i class="el-icon-connection"></i>
              <span slot="title">关注/粉丝</span>
            </el-menu-item>
            <el-menu-item index="4" @click="logout">
              <i class="el-icon-switch-button"></i>
              <span slot="title">退出登录</span>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "Sidebar-left",
  data(){
    return{
      userAvatar: ''
    }
  },
  created() {
    this.getUserAvatar()
  },
  methods:{
    //获取用户头像
    getUserAvatar() {
      this.getRequest('/auth/user/userInfo').then(res => {
        if(res.data.code === 200){
          this.userAvatar = "http://localhost:8081/downloadPhoto/" + res.data.data.userAvatar
        }
      })
    },
    toPersonalIndex(){
      this.$router.replace('/personal')
    },
    toMessages() {
      this.$router.replace('/personal/messages')
    },
    toFans(){
      this.$router.replace('/personal/fans')
    },
    logout(){
      window.localStorage.removeItem('tokenStr')
      this.$router.replace('/login')
    }
  }
}
</script>

<style scoped>
.circlePic {
  width: 110px;
  height: 110px;
  border-radius: 50%;
  overflow: hidden;
  margin: 150px auto auto auto;
}
.divider {
  background-color: cornflowerblue;
  position: relative;
  margin: 20px 0;
}
.menu-left {
  border-right: 0 !important;
  font-size: 20px;
}

</style>