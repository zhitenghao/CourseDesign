<template>
  <div class="follows" style="height: 100%">
    <el-tabs :tab-position="'left'" v-model="activeName">
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
        <follows-list :follows="follows1"></follows-list>
      </el-tab-pane>
<!--      <el-tab-pane label="特别关注" name="second">-->
<!--        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">-->
<!--          特别关注-->
<!--        </div>-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="默认分组" name="third">-->
<!--        <div style="height: 30px;text-align: left;margin: 10px;line-height: 30px;">-->
<!--          默认分组-->
<!--        </div>-->
<!--      </el-tab-pane>-->
      <el-tab-pane label="我的粉丝" disabled="disabled">
        <span slot="label">
          <i class="el-icon-caret-bottom">
            <span style="padding-left: 8px;">我的粉丝</span>
          </i>
        </span>
      </el-tab-pane>
      <el-tab-pane label="全部粉丝">
        <div class="follow-head">
          粉丝列表
        </div>
        <follows-list :follows="follows2"></follows-list>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import FollowsList from "@/components/Personal/FollowList";

export default {
  name: "myConcerns",
  components: { FollowsList },
  data() {
    return {
      activeName:'first',
      disabled: true,
      follows1: '/concernUserList?userId='+this.$route.query.id,
      follows2: '/fansUserList?userId='+this.$route.query.id,
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