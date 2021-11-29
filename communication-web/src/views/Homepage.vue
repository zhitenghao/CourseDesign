<template>
  <div class="homepage">
    <el-container>
      <el-aside class="aside-left" width=15%>
        <left-sidebar/>
      </el-aside>

      <el-container>
        <el-header>
          <div style="text-align: center;padding-top: 10px">
            <el-select
                v-model="value"
                multiple
                filterable
                remote
                reserve-keyword
                placeholder="请输入关键词"
                :remote-method="remoteMethod"
                :loading="loading">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>

        </el-header>
        <el-main>
          <router-view :id="categoryId"/>
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
  components: { LeftSidebar, RightSidebar },
  data () {
    return {
      categoryId: '',
      loading: false,
      options: [],
      value: [],
      list: [],
      states: ["混血小汤包两岁...","冬天和烤红薯...","正道的光","明晚6点守在CC...",
        "宇宙的尽头是编制...","考研考研考研...","【考研倒计时26天】","易烊千玺晚安","友情提醒：大年初一易烊千玺...",
          "还有一个月就考研啦...","香香~","准备冲上海某top2...","柬埔寨砍树有多“先进”...","大叔用2000℃铁水"
      ]
    }
  },
  mounted() {
    this.list = this.states.map(item => {
      return { value: `value:${item}`, label: `${item}` };
    });
  },
  methods: {
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.options = this.list.filter(item => {
            return item.label.toLowerCase()
                .indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.options = [];
      }
    }
  }
}
</script>

<style scoped>
.homepage {
  /*overflow: auto;*/
  position: relative;
  /*height: 100%;*/
  width: 1200px;
  margin: 0 auto;
  background-color: #FFFFFF;
  background-image: url(../assets/images/home/Swan.jpg);
  background-size: cover;
  background-attachment:fixed;
  background-repeat: no-repeat;
  opacity: 1.0;
}
.aside-left {
  overflow-y:auto;
  position: fixed;
  height: 100%;
  left: 100px;
  box-sizing: border-box;
  background-color: #545c64;
  flex-shrink: 0;
}
.aside-right {
  overflow-y:auto;
  position: fixed;
  height: 100%;
  right: 100px;
  box-sizing: border-box;
  background-color: #545c64;
  flex-shrink: 0;
}
.aside-right::-webkit-scrollbar{
  display: none;
}

</style>
