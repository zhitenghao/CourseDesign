<template>
  <div class="sidebar-left">
    <div id="user-info">
      <div class="have-login">
        <div class="circlePic" style="cursor:pointer" @click="toPersonalIndex">
          <el-avatar :src="require('../../assets/images/home/avatar.jpg')" :size="110"/>
        </div>
        <div class="divide-line">
          <el-divider class="divider" style="width: 200px;right: 24px;"></el-divider>
        </div>
      </div>
      <div class="not-login" style="margin-top: 0;"></div>
    </div>

    <div id="menu" style="margin-top: 0;">
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
            <el-menu-item index="2">
              <i class="el-icon-picture-outline"></i>
              <span slot="title">发帖</span>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-chat-dot-square"></i>
              <span slot="title">消息</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-star-off"></i>
              <span slot="title">收藏夹</span>
            </el-menu-item>
            <el-menu-item index="5">
              <i class="el-icon-user"></i>
              <span slot="title">关注/粉丝</span>
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
    }
  },
  methods:{
    toPersonalIndex(){
      this.$router.replace('/personal')
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