<template>
  <el-container style="height: 100%">
    <el-aside class="follow-sidenav" width=160px>
      <el-menu class="menu" default-active="2" @select="toFollows">
        <el-menu-item index="1">
          <span slot="title" style="color: #99a2aa">我的关注</span>
        </el-menu-item>
        <el-menu-item index="2">
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
    </el-aside>
    <el-main class="follow-main" style="padding: 0">
      <router-view/>
    </el-main>
  </el-container>

<!--    <el-tabs :tab-position="'left'" v-model="activeName" @tab-click="handleClick" lazy>-->
<!--      <el-tab-pane disabled="disabled">-->
<!--        <span slot="label">-->
<!--          <i class="el-icon-caret-bottom">-->
<!--            <span style="padding-left: 8px;">我的关注</span>-->
<!--          </i>-->
<!--        </span>-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="全部关注" name="first" lazy>-->
<!--        <div class="follow-head">-->
<!--          全部关注-->
<!--        </div>-->
<!--        <follows-list :follows="follows1" @changeConcern="changeConcern"></follows-list>-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="特别关注" name="second" lazy>-->
<!--        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">-->
<!--          特别关注-->
<!--        </div>-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="默认分组" name="third" lazy>-->
<!--        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">-->
<!--          默认分组-->
<!--        </div>-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="我的粉丝" disabled="disabled">-->
<!--        <span slot="label">-->
<!--          <i class="el-icon-caret-bottom">-->
<!--            <span style="padding-left: 8px;">我的粉丝</span>-->
<!--          </i>-->
<!--        </span>-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="全部粉丝" name="fourth" lazy>-->
<!--        <div class="follow-head">-->
<!--          粉丝列表-->
<!--        </div>-->
<!--        <follows-list :follows="follows2" @changeConcern="changeConcern"></follows-list>-->
<!--      </el-tab-pane>-->
<!--    </el-tabs>-->
</template>

<script>
export default {
  name: "myConcerns",
  data() {
    return {
      follows: '/concernUserList?userId='+this.$route.query.id,
      fans: '/fansUserList?userId='+this.$route.query.id
    }
  },
  mounted(){
    console.log('myConcerns',this.$route.query.id)
  },
  created() {
    //this.toFollows()
  },
  methods: {
    toFollows(index){
      console.log(index)
      // this.$router.push({
      //   name: "follows",
      //   query: { follows : this.follows }
      // })
    },
    toFans(){
      this.$router.push({
        name: "follows",
        query: { follows : this.fans }
      })
    },

    changeConcern(e) {
      this.$emit('changeConcern',e)
    },
    handleClick(tab){

    }
  }
}
</script>

<style scoped>
.follows{
  position: relative;
}
.follow-sidenav{
  position: relative;
  height: 100%;
  width: 160px
}

.follow-head{
  height: 30px;
  margin: 10px 20px 0;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
  text-align: left;
  font-size: 18px;
  line-height: 30px;
}
</style>