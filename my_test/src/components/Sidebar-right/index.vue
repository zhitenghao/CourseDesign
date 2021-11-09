<template>
  <div class="sidebar-right">
    <div id="hotSpot" style="margin-top: 150px;">
      <h2 style="color:#E6A23C;">全站十大</h2>
    </div>
    <div id="content">
      <h2>内容</h2>
    </div>
    <div id="classification">
      <el-row class="tac">
        <el-menu default-active="2" class="menu-right"
                 background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <!-- <i class="el-icon-menu"></i> -->
              <span>学校院网</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">土木工程学院</el-menu-item>
              <el-menu-item index="1-2">机械工程学院</el-menu-item>
              <el-menu-item index="1-3">电气工程学院</el-menu-item>
              <el-menu-item index="1-4">信息科学与技术学院</el-menu-item>
              <el-menu-item index="1-5">计算机与人工智能学院</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item :index="item.id" v-for="(item,key) in menuList" :key="key">
            <template slot="title">
              <!--                  <svg-icon slot="prefix" icon-class="system"/>-->
              {{ item.categoryName }}
            </template>
          </el-menu-item>
        </el-menu>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data(){
    return{
      menuList: [],
    }
  },
  created () {
    this.getMenus()
  },
  methods:{
    getMenus () {
      this.getRequest('/auth/category/list').then(res => {
        this.menuList = res.data.data
        console.log(this.menuList)
      })
      // this.getRequest('/article/listByCategory/'+item.id).then(res => {
      //
      // })
    },
  }
}
</script>

<style scoped>
#content {
  padding: 0;
  margin: 0;
  height: 250px;
  width: 100%;
  background-color: #FFFFFF;
}
.content {
  /* background-color: white; */
  width: 50%;
  opacity: 50;
}
</style>