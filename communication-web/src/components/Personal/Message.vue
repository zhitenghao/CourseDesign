<template>
  <div class="messages" style="height: 100%">
    <el-tabs :tab-position="'left'" v-model="activeName">
      <el-tab-pane disabled="disabled">
        <span slot="label">
          <i class="el-icon-caret-bottom">
            <span style="padding-left: 8px;">我的消息</span>
          </i>
        </span>
      </el-tab-pane>
      <el-tab-pane label="回复我的" name="first">
        <div class="message-head">
          <span>回复我的</span>
        </div>
        <message-list :type="'replies'"></message-list>
      </el-tab-pane>
      <el-tab-pane label="收到的赞" name="second">
        <div class="message-head">
          <span>收到的赞</span>
        </div>
        <message-list :type="'loves'"></message-list>
      </el-tab-pane>
      <el-tab-pane label="新的关注" name="third">
        <div class="message-head">
          <span>新的关注</span>
        </div>
        <message-list :type="'concerns'"></message-list>
      </el-tab-pane>
      <el-tab-pane label="新的收藏" name="fourth">
        <div class="message-head">
          <span>新的收藏</span>
        </div>
        <message-list :type="'collections'"></message-list>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import MessageList from "@/components/Personal/MessageList";

export default {
  name: 'Message',
  components: { MessageList },
  data() {
    return {
      activeName:'first',
      disabled: true,

    }
  },
  methods: {


  }
}
</script>

<style scoped>
::v-deep .el-tabs__item {
  /*单个tab*/
  padding-right: 30px;
}
.message-head{
  height: 30px;
  margin: 10px 20px 0;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
  text-align: left;
  font-size: 18px;
  line-height: 30px;
  /* border: 1px solid #e52121; */
}
</style>
