<template>
  <div>
    <ul class="message-list" style="overflow: auto;">
      <li class="list-item" v-for="item in allMessages">
        <div class="message-avatar" style="cursor:pointer" @click="toUserPage(item.myself,item.reUserDto.id)">
          <el-avatar :src="item.reUserDto.userAvatar" :size="50"></el-avatar>
        </div>
        <div class="center-content">
          <div class="line-1">
            <span class="name-field" style="cursor:pointer" @click="toUserPage(item.myself,item.reUserDto.id)">{{ item.reUserDto.userName }}</span>
            <span class="desc-field" v-if="type==='replies'">回复了你</span>
            <span class="desc-field" v-if="type==='loves'">赞了你</span>
            <span class="desc-field" v-if="type==='concerns'">关注了你</span>
            <span class="desc-field" v-if="type==='collections'">收藏了你的帖子</span>
          </div>
          <div class="line-2">{{ item.content }}</div>
          <div class="line-3">
            <span style="margin-right: 15px">{{ item.addTime }}</span>
            <span style="cursor: pointer" v-if="type==='replies'" @click="item.replyInputShow=!item.replyInputShow"><i class="el-icon-edit-outline"></i> 回复</span>
            <div v-if="item.replyInputShow" style="margin-top: 20px"> <!-- 发表评论的输入框 -->
              <el-input v-model="item.replyForm.replyContent" placeholder="请输入您的回复" style="width: 85%" @keyup.enter.native="submitReply(item)"></el-input>
              <el-button type="primary" @click="submitReply(item)">确认</el-button>
            </div>
          </div>
          <div class="action">
<!--            <el-button @submit="func" type="warning" size="small" round plain>-->
<!--              已关注-->
<!--            </el-button>-->
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
      allMessages: [],
      NoneData: NoneData,
      dataFlag: false,
      pagerFlag: false
    }
  },
  props:['type'],
  created() {
    this.getMessages()
  },
  watch: {
    'allMessages': {
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
    //获取所有消息列表
    getMessages() {
      this.getRequest('/auth/message/'+this.type).then(res => {
        if(res.data.code === 200){
          this.allMessages = res.data.data
          console.log('allMessages',this.allMessages)
          for(let i=0;i<this.allMessages.length;i++){
            this.allMessages[i].reUserDto.userAvatar = "http://localhost:8081/downloadPhoto/" + this.allMessages[i].reUserDto.userAvatar
            this.$set(this.allMessages[i],'replyInputShow',false)
            this.$set(this.allMessages[i],'replyForm',{comReId: '',processType: 0,repliedUser: '',replyContent: ''})
          }
        }else {
          this.message.error('读取错误')
        }
      })
    },

    toUserPage(isMyself,userId){
      //如果是自己跳转到我的主页，不是自己跳个人页面
      if(!isMyself){
        let routeUrl = this.$router.resolve({
          path: "/visitor",
          query: { id:userId }
        })
        window.open(routeUrl.href, '_blank')
      }else{
        let routeUrl = this.$router.resolve({
          path: "/personal",
        })
        window.open(routeUrl.href, '_blank')
      }
    },
    submitReply(item){
      item.replyForm.comReId = item.comReId
      item.replyForm.processType = item.processType
      item.replyForm.repliedUser = item.reUserDto.id
      if(item.replyForm.replyContent !== ''){
        this.postRequest('/auth/message/addReply',item.replyForm).then(res=>{
          if(res.data.code === 200){
            this.$message.success("回复成功")
            item.replyInputShow = false
            item.replyForm.replyContent = ''
          }else{
            this.$message.error("回复失败")
          }
        })
      }else{ this.$message.error('请输入内容') }

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
  width: 800px;
  border-bottom: 1px solid #e5e9ef;
  padding: 15px 0;
  text-align: left;
  line-height: 70px;
}

.message-avatar {
  position: absolute;
  overflow: visible;
  float: left;
  width: 50px;
  height: 50px;
  margin-left: 15px;
  border-radius: 50%;
}

.center-content {
  float: left;
  position: relative;
  height: 100%;
  width: 600px;
  margin-left: 80px;
  padding-right: 100px;
  text-align: left;
}

.line-1 {
  overflow: hidden;
  text-overflow: ellipsis;
  height: 25px;
  width: 100%;
  max-width: 600px;
  margin-bottom: 10px;
  font-size: 14px;
  line-height: 20px;
}
.name-field{
  font-weight: bold;
  margin-right: 10px;
}
.desc-field{
  color: #505050;
}
.line-2 {
  max-width: 600px;
  text-overflow: ellipsis;
  text-align: left;
  margin-bottom: 10px;
  font-size: 14px;
  line-height: 20px;
}
.line-3 {
  text-align: left;
  line-height: 20px;
  font-size: 12px;
  color: #909399;
}

.action {
  position: absolute;
  right: 20px;
  top: 4px;
}

.be-pager {
  margin: 20px 0;
  text-align: center;
}
</style>