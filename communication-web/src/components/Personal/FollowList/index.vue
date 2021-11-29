<template>
  <div>
    <ul class="relation-list" style="overflow: auto;">
      <li class="list-item" v-for="item in allConcerns">
        <div class="follow-avatar" style="cursor:pointer" @click="toUserPage(item.id)">
          <el-avatar :src="item.userAvatar" :size="70"></el-avatar>
        </div>
        <div class="follow-content">
          <div class="follow-title" style="cursor:pointer" @click="toUserPage(item.id)">
            {{ item.userName }}
          </div>
          <div class="follow-introduce">
            {{ item.userDescription }}
          </div>
          <div class="fans-action" v-if="!item.myself">
            <el-dropdown>
              <el-button
                  v-if="item.isConcerned === 1" @click="cancelConcerned(item)"
                  type="warning" size="small" round plain>
                已关注
              </el-button>
              <el-button
                  v-else-if="item.isConcerned === 2" @click="cancelConcerned(item)"
                  type="warning" size="small" round plain>
                ⇆互相关注
              </el-button>
              <el-button
                  v-else @click="addConcern(item)"
                  type="warning" size="small" round plain>
                + 关注
              </el-button>
<!--              <el-dropdown-menu slot="dropdown">-->
<!--                <el-dropdown-item>取消关注</el-dropdown-item>-->
<!--                <el-dropdown-item>设置分组</el-dropdown-item>-->
<!--              </el-dropdown-menu>-->
            </el-dropdown>
          </div>
        </div>
      </li>
    </ul>
    <div class="be-pager" v-if="pagerFlag">
      <el-pagination background layout="prev, pager, next" :total="100"></el-pagination>
    </div>
    <div v-if="dataFlag">
      <img width="100%" :src="NoneData" alt="">
    </div>
  </div>
</template>

<script>
import NoneData from '@/assets/none-data.jpg'

export default {
  name: "index",
  data() {
    return {
      allConcerns: [],
      NoneData: NoneData,
      dataFlag: false,
      pagerFlag: false
    }
  },
  props:[ 'follows' ],
  created() {
    this.getConcerns()
  },
  watch: {
    'allConcerns': {
      handler(newVal,oldVal){
        if(newVal.length === 0){
          this.dataFlag = true
        }
        if(newVal.length > 10){
          this.pagerFlag = true
        }
      }
    }
  },
  methods: {
    //获取所有关注列表
    getConcerns() {
      this.getRequest('/auth/user' + this.follows).then(res => {
        this.allConcerns = res.data.data.records
        //将userAvatar加上前缀后可展示出图片
        for (let i = 0; i < this.allConcerns.length; i++) {
          this.allConcerns[i].userAvatar = "http://localhost:8081/downloadPhoto/" + this.allConcerns[i].userAvatar
        }
        console.log('res',this.allConcerns)
      })
    },

    //跳转个人页面
    toUserPage(userId){
      //如果是自己跳转到我的主页，不是自己跳个人页面
      let routeUrl = this.$router.resolve({
        path: "/visitor",
        query: { id:userId }
      })
      window.open(routeUrl.href, '_blank')
    },
    //关注
    addConcern(item){
      this.getRequest('/auth/concern/add/'+item.id).then(res => {
        if(res.data.code === 200){
          item.isConcerned = res.data.data.isConcerned
          //增加关注，关注框应+1
        }else{
          this.message.error('关注失败')
        }
      })
    },
    //取消关注
    cancelConcerned(item){
      this.getRequest('/auth/concern/delete/'+item.id).then(res => {
        if(res.data.code === 200){
          item.isConcerned = 0
          //取消关注，关注框应-1
        }else{
          this.message.error('取消关注失败')
        }
      })
    }
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

.follow-avatar {
  position: absolute;
  overflow: visible;
  float: left;
  width: 70px;
  height: 70px;
  margin-left: 15px;
  border-radius: 50%;
}

.follow-content {
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

.follow-title {
  overflow: hidden;
  text-overflow: ellipsis;
  height: 25px;
  width: fit-content;
  max-width: 500px;
  margin-bottom: 12px;
  font-size: 16px;
  line-height: 25px;
}

.follow-introduce {
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

.be-pager {
  margin: 20px 0;
  text-align: center;
}
</style>