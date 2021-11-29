<template>
<!--  可改名为Layout-->
  <div class="homepage" style="width:100%">
    <el-container>
      <el-aside class="aside-left" width=15%>
        <left-sidebar/>
      </el-aside>

      <el-container>
        <el-header>
          <div style="text-align: center">Header</div>
        </el-header>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>

      <el-aside class="aside-right" width=15%>
        <right-sidebar/>
      </el-aside>
    </el-container>
  </div>
</template>

<script>
import LeftSidebar from '@/components/Sidebar-left'
import RightSidebar from '@/components/Sidebar-right'

export default {
  name: 'home',
  components: {
    LeftSidebar,
    RightSidebar,
  },
  data () {
    return {
    }
  },
  methods: {
  }
}
</script>

<style scoped>
.homepage {
  overflow: auto;
  height: 100%;
  width: 100%;
  background-image: url(../assets/images/home/Swan.jpg);
  background-size: cover;
  background-attachment:fixed;
  background-repeat: no-repeat;
}
.aside-left {
  overflow-y:auto;
  position: fixed;
  height: 100%;
  left: 0;
  box-sizing: border-box;
  background-color: #545c64;
  flex-shrink: 0;
}
.aside-right {
  overflow-y:auto;
  position: fixed;
  height: 100%;
  right: 0;
  box-sizing: border-box;
  background-color: #545c64;
  flex-shrink: 0;

}
.aside-right::-webkit-scrollbar{
  display: none;
}

</style>
