<template>
  <div class="Swan">
    <el-container>
      <el-aside class="aside-left" width=15%>
        <left-sidebar/>
      </el-aside>

      <el-container>
        <el-header>
          Header
        </el-header>
        <el-main>
          <articles/>
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
import Articles from '@/components/Articles'

export default {
  name: 'test',
  components: {
    LeftSidebar,
    RightSidebar,
    Articles,
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
.Swan {
  height: 100%;
  width: 100%;
  display: flex;
  background-size: cover;
  background-image: url(../assets/images/home/Swan.jpg);
}

.aside-left {
  background-color: #545c64;
  overflow: auto;
  box-sizing: border-box;
  flex-shrink: 0;
}

.aside-right {
  background-color: #545c64;
  overflow: auto;
  box-sizing: border-box;
  flex-shrink: 0;
  height: 100%;
  padding: 0;
}

</style>
