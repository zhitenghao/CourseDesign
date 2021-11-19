<template>
  <div class="hello">
    <ul class="relation-list" style="overflow: auto;">
      <li class="list-item" v-for="item in allConcerns">
        <div class="cover">
          <el-avatar :src="item.userAvatar" :size="70"></el-avatar>
        </div>
        <div class="content">
          <div class="title">
            {{ item.userName }}
          </div>
          <div class="introduce">
            {{ item.userDescription }}
          </div>
          <div class="fans-action">
            <el-dropdown>
              <el-button
                  v-if="item.isConcerned == 1" @click="cancelConcerned(item)"
                  type="warning" size="small" round plain>
                已关注
              </el-button>
              <el-button
                  v-else-if="item.isConcerned == 2" @click="cancelConcerned(item)"
                  type="warning" size="small" round plain>
                ⇆互相关注
              </el-button>
              <el-button
                  v-else @click="addConcern(item)"
                  type="warning" size="small" round plain>
                + 关注
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>取消关注</el-dropdown-item>
                <el-dropdown-item>设置分组</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "testB",
  props:{
  },
  data(){
    return{
      allConcerns: [],
    }
  },
  created() {
    this.getConcerns()
  },
  methods:{
    getConcerns() {
      this.getRequest('/auth/user/myConcernUserList').then(res => {
        this.allConcerns = res.data.data.records
        //将userAvatar加上前缀后可展示出图片
        for (let i = 0; i < this.allConcerns.length; i++) {
          this.allConcerns[i].userAvatar = "http://localhost:8081/downloadPhoto/" + this.allConcerns[i].userAvatar
        }
        console.log(this.allConcerns)
      })
    },

  }
}
</script>

<style scoped>
ul {
  list-style: none;
  padding-left: 10px;
}

.list-item {
  position: relative;
  float: left;
  height: 70px;
  width: 800px;
  border-bottom: 1px solid #eee;
  padding: 15px 0;
  text-align: left;
  line-height: 70px;
}

.cover {
  position: absolute;
  overflow: visible;
  float: left;
  width: 70px;
  height: 70px;
  margin-left: 15px;
  border-radius: 50%;
}

.content {
  float: left;
  position: relative;
  height: 70px;
  width: 500px;
  margin-top: 6px;
  margin-left: 105px;
  padding-right: 200px;
  text-align: left;
  line-height: 50px;
}

.title {
  overflow: hidden;
  text-overflow: ellipsis;
  height: 25px;
  max-width: 500px;
  margin-bottom: 12px;
  font-size: 16px;
  line-height: 25px;
}

.introduce {
  max-width: 500px;
  text-overflow: ellipsis;
  text-align: left;
  line-height: 20px;
  font-size: 12px;
  color: #909399;
}

.fans-action {
  position: absolute;
  right: 20px;
  top: 4px;
}

</style>