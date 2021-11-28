<template>
  <div class="sidebar-right">
    <div class="hotSpot" style="margin: 40px 0 20px 0">
      <h2 style="color:#E6A23C;">全站十大</h2>
    </div>
    <div class="content">
      <h2>内容</h2>
    </div>
    <div class="classification">
      <el-row class="tac">
        <el-menu default-active="2" class="menu-right"
                 background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <span>学校院网</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1" class="academy">土木工程学院</el-menu-item>
              <el-menu-item index="1-2" class="academy">机械工程学院</el-menu-item>
              <el-menu-item index="1-3" class="academy">电气工程学院</el-menu-item>
              <el-menu-item index="1-4" class="academy">信息科学与技术学院</el-menu-item>
              <el-menu-item index="1-5" class="academy">计算机与人工智能学院</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="allArticles" @click="allArticles">
            <template slot="title">
              <span>全部内容</span>
            </template>
          </el-menu-item>
          <el-menu-item :index="item.id" v-for="(item,key) in menuList" :key="key" @click="selectCategory(item.path)">
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
    //获取分类菜单
    getMenus () {
      this.getRequest('/auth/category/list').then(res => {
        if(res.data.code === 200){
          this.menuList = res.data.data
          console.log('菜单栏',this.menuList)
        }
      })
    },
    //返回首页全部帖子
    allArticles(){
      this.$router.push('/')
    },
    //根据分类跳转子路由
    selectCategory(path){
      this.$router.replace('/'+path).catch(err=>err)
    }
  }
}
</script>

<style scoped>
.content {
  padding: 0;
  margin: 0;
  height: 250px;
  width: 100%;
  background-color: #FFFFFF;
}
.academy{
  padding-left: 32px !important;
}
</style>