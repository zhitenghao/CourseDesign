<template>
  <div>
    <div class="follow-sidenav">
      <el-menu class="menu" default-active="none">
        <el-menu-item index="1">
          <span slot="title" style="color: #99a2aa">我的关注</span>
        </el-menu-item>
        <el-menu-item index="2" @click="toFollows">
          <i class="el-icon-connection"></i>
          <span slot="title">全部关注</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-chat-dot-square"></i>
          <span slot="title">特别关注</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-s-tools"></i>
          <span slot="title">默认分组</span>
        </el-menu-item>
        <el-menu-item index="5">
          <span slot="title" style="color: #99a2aa">我的粉丝</span>
        </el-menu-item>
        <el-menu-item index="6" @click="toFans">
          <i class="el-icon-location"></i>
          <span slot="title">全部粉丝</span>
        </el-menu-item>
      </el-menu>
    </div>
    <router-view/>
  </div>

</template>

<script>

export default {
  data() {
    return{

    }
  },
  methods: {
    toFollows(){
      this.$router.push({
        name: "follows",
        query: {id:this.userId}
      })
    },
    toFans(){
      this.$router.push({
        name: "fans",
        query: {id:this.userId}
      })
    }
  }
}
</script>

<style scoped>
.follow-sidenav{
  position: relative;
  height: 100%;
  width: 160px
}
</style>