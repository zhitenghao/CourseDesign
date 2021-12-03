<template>
  <div class="follows" style="height: 100%">
    <el-tabs :tab-position="'left'" v-model="activeName" @tab-click="handleClick" lazy>
      <el-tab-pane disabled="disabled">
        <span slot="label">
          <i class="el-icon-caret-bottom">
            <span style="padding-left: 8px;">我的关注</span>
          </i>
        </span>
      </el-tab-pane>
      <el-tab-pane label="全部关注" name="first">
        <div class="follow-head">
          全部关注
        </div>
        <follows-list :follows="follows1" @changeConcern="changeConcern"></follows-list>
      </el-tab-pane>
      <el-tab-pane label="特别关注" name="second">
        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">
          特别关注
        </div>
      </el-tab-pane>
      <el-tab-pane label="默认分组" name="third">
        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">
          默认分组
        </div>
      </el-tab-pane>
      <el-tab-pane label="我的粉丝" disabled="disabled">
        <span slot="label">
          <i class="el-icon-caret-bottom">
            <span style="padding-left: 8px;">我的粉丝</span>
          </i>
        </span>
      </el-tab-pane>
      <el-tab-pane label="全部粉丝" name="fourth" lazy>
        <div class="follow-head">
          粉丝列表
        </div>
        <follows-list :follows="follows2" @changeConcern="changeConcern"></follows-list>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import FollowsList from "@/components/Personal/FollowList";

export default {
  name: "myConcerns",
  components: { FollowsList },
  props:[
    'value'
  ],
  data() {
    return {
      activeName: this.$route.query.order,
      disabled: true,
      follows1: '/concernUserList?userId='+this.$route.query.id,
      follows2: '/fansUserList?userId='+this.$route.query.id,
    }
  },
  watch:{
    'value':{
      handler(newVal){
        console.log('newVal',newVal)
        //this.activeName = newVal
      }
    }
  },
  created(){
  },
  methods: {
    changeConcern(e) {
      this.$emit('changeConcern',e)
    },
    handleClick(tab){
      // if (tab.name === 'first') {
      //   this.isFirst = true
      //   this.isSecond = false
      // } else if (tab.name === 'fourth') {
      //   this.isFirst = false
      //   this.isSecond = true
      // }
      // console.log(tab,event)
      //console.log(this.value)
    }
  }
}
</script>

<style scoped>
::v-deep .el-tabs__item {
  /*单个tab*/
  padding-right: 30px;
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