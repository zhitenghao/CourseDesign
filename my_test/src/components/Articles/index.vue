<template>
  <div class="articles">
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
      <li v-for="item in articles" :key="item.id" class="infinite-list-item">
        <div class="onePostContainer">
          <div class="header">
            <div class="cover">
              <el-avatar :src="item.reUserDto.userAvatar" :size="55"></el-avatar>
            </div>
            <div class="top-bar">
              <div class="title">
                {{ item.reUserDto.userName }}
              </div>
              <div class="postTime">
                {{ item.addTime }}&nbsp&nbsp&nbsp&nbsp分类
<!--                {{item.categoryName}}-->
              </div>
              <div class="action">
                <div v-if="!item.myself"> <!-- 设置关注此用户 -->
                  <el-button type="warning" v-if="item.concern" @click="cancelAttention(item)" style="margin-left: 420px" size="small" round plain>已关注</el-button>
                  <el-button type="warning" v-else @click="addAttention(item)" style="margin-left: 420px" size="small" round plain>+ 关注</el-button>
                </div>
                <div v-else>
                  <el-button type="warning" @click="deleteArticle(item)" style="margin-left: 420px" size="small" round plain>删除该帖</el-button>
                </div>
              </div>
            </div>
          </div>
          <div class="postContent">
            <!-- 设置可自适应文本高度的文本域：从后端获取帖子内容以后，把内容放在<span>标签里，textContentFlag可以设置是否展示文字内容 -->
            <div style="width: 83%; margin: 0 57px">
              <span class="textContent">{{item.articleContent}}</span>
            </div>
            <!-- 设置图片或者视频展示区域 -->
            <div style="width: 83%; margin: 0 57px;">
              <el-image class="imageOrVideos" v-for="oneImageOrVideoUrl in item.urls.slice(0, 9)" :key="oneImageOrVideoUrl" :src="oneImageOrVideoUrl" lazy></el-image>
            </div>
            <div class="commentAndLike">
              <el-badge :value="item.collectionNum" class="activity"> <!-- 设置收藏该帖子的数量 -->
                <el-button type="text" v-if="item.collection" style="margin-left: 125px" @click="cancelCollect(item)">已收藏</el-button>
                <el-button type="text" v-else style="margin-left: 125px" @click="addCollect(item)">收藏</el-button>
              </el-badge>
              <el-badge :value="item.commentNum" class="activity"> <!-- 设置该帖子的评论数量 -->
                <el-button type="text" style="margin-left: 130px" @click="switchComment(item)">评论</el-button> <!-- 点击评论按钮，该按钮设置了commentAndShowFlag的值，该值用来控制是否展开用户评论div和该帖子的评论内容， -->
              </el-badge>
              <el-badge :value="item.likeNum" class="activity"> <!-- 设置该帖子的点赞数量 -->
                <el-button type="text" v-if="item.like" style="margin-left: 125px" @click="unlike(item)">已点赞</el-button>
                <el-button type="text" v-else style="margin-left: 125px" @click="like(item)">点赞</el-button>
              </el-badge>
            </div>
          </div>
          <!-- 评论部分 -->
          <div class="commentAndShow" v-if="item.commentsShow">
            <div class="commentArea">
              <!-- 帖子评论的输入框 -->
              <el-input v-model="item.commentForm.commentContent" placeholder="请输入您的评论" style="width: 71%; padding-left: 60px" @keyup.enter.native="submitComment(item)"></el-input>
              <el-button type="primary" @click="submitComment(item)">确认</el-button>
            </div>
            <div class="showArea"> <!-- 展示用户们的评论 -->
              <div class="showPerComment" v-for="comment in item.reCommentDtos" :key="comment.commentTime">
                <div> <!-- 用户的头像和用户名 -->
                  <el-avatar :size="30" :src="comment.commentUser.userAvatar" style="margin: 4px 0 0 4px;"></el-avatar>
                  <span style="font-size: small; position: relative;bottom: 11px; margin-left: 8px; margin-right: 3px; color: chocolate"> {{comment.commentUser.userName}}: </span>
                </div>
                <div style="margin-left: 45px; position: relative;bottom: 7px;"> <!-- 用户的评论内容+回复内容 -->
                  <span style="font-size: small;">{{ comment.commentContent }}</span>
                  <br>
                  <span style="font-size: 10px; color: #99a2aa">{{ comment.commentTime }}</span>
                  <span style="font-size: small; cursor: pointer; margin-left: 20px; color: #99a2aa" @click="switchReply(comment)">回复</span>
                </div>
                <!-- 回复部分 -->
                <div class="replyContainer">
                  <div class="reply" v-for="reply in comment.replyList" :key="reply.replyeTime">
                    <span style="font-size: small; color: chocolate">{{reply.replyUser.userName}}</span>
                    <span style="font-size: small"> :回复@ </span>
                    <span style="font-size: small; color: chocolate">{{reply.repliedUser.userName}}:</span>
                    <span style="font-size: small"> {{reply.replyContent}}</span>
                    <br>
                    <span style="font-size: 10px; color: #99a2aa">{{ reply.replyTime }}</span> <!-- 回复时间 -->
                    <span style="font-size: small; cursor: pointer; margin-left: 20px; color: #99a2aa" @click="switchReply2(reply,comment)">回复</span>
                  </div>
                </div>
              </div>
              <div class="unfoldComments" v-if="item.commentNum >= 3" @click="unfoldComments">
                <span style="font-size: small;">{{ foldFlag? '点击展开更多评论' : '收起' }}</span>
              </div>
            </div>
          </div>
        </div>
      </li>
    </ul>

    <!--评论回复弹窗-->
    <el-dialog class="replyDialog" title="回复" :visible.sync="replyDialogFormVisible" @close="replyForm.replyContent = ''">
      <el-form :model="replyForm">
        <el-input v-model="replyForm.replyContent" placeholder="请输入您的评论" style="width: 86%;"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitReply(commentInfo.commentId,commentInfo.commentUser.id)">确 定</el-button>
      </div>
    </el-dialog>
    <!--回复回复弹窗-->
    <el-dialog title="回复" :visible.sync="replyDialog2FormVisible" @close="replyForm.replyContent = ''">
      <el-form :model="replyForm">
        <el-input v-model="replyForm.replyContent" placeholder="请输入您的评论" style="width: 86%;"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitReply(commentInfo.commentId,commentInfo.repliedUser.id)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      articles: [],//帖子的集合
      replyDialogFormVisible: false,
      replyDialog2FormVisible: false,
      commentInfo: {},
      replyForm:{ //回复表单
        commentId: '',
        replyContent: '',
        repliedUser: ''
      },
      foldFlag: true,
    }
  },
  props:[
    'categoryId'
  ],
  created() {
    this.getArticle()
  },
  methods:{
    //获取帖子
    getArticle() {
      this.getRequest('/auth/article'+this.categoryId).then(res => {
        this.articles = res.data.data
        //给articles循环每项插入属性commentForm,commentsShow
        this.articles = this.articles.map(item => {
          return {...item,commentsShow: false,commentForm:{articleId: '',commentContent: '',userId: ''}}
          // item.reCommentDtos = item.reCommentDtos.map(item => {
          //   item.replyList = item.replyList.map(item => {
          //     return {...item,replyShow2: false}
          //   })
          //   return {...item,replyShow1: false}
          // })
        })
        //console.log(this.articles)
        //所有图片需要在属性前加一段前缀再渲染到界面上
        for(let i=0;i<this.articles.length;i++){
          for(let j=0;j<this.articles[i].urls.length;j++){
            this.articles[i].urls[j] = "http://localhost:8081/downloadPhoto/" + this.articles[i].urls[j]
          }
          for(let j=0;j<this.articles[i].reCommentDtos.length;j++){
            this.articles[i].reCommentDtos[j].commentUser.userAvatar = "http://localhost:8081/downloadPhoto/" + this.articles[i].reCommentDtos[j].commentUser.userAvatar
          }
          this.articles[i].reUserDto.userAvatar = "http://localhost:8081/downloadPhoto/" + this.articles[i].reUserDto.userAvatar
        }
        console.log(this.articles)
      })
    },

    // 添加关注
    addAttention (item) {
      this.getRequest('/auth/concern/add/'+item.reUserDto.id+'').then(res => {
        if(res.data.code === 200){
          //所有该用户的“关注”全变为“已关注”
          for(let i = 0;i < this.articles.length;i++){
            if(this.articles[i].reUserDto.id === item.reUserDto.id){
              this.articles[i].concern = !this.articles[i].concern
            }
          }
        }else{
          this.$message.error('关注失败')
        }
      })
    },
    //取消关注
    cancelAttention(item){
      this.getRequest('/auth/concern/delete/'+item.reUserDto.id+'').then(res => {
        if(res.data.code === 200){
          //所有该用户的“已关注”全变为“关注”
          for(let i = 0;i < this.articles.length;i++){
            if(this.articles[i].reUserDto.id === item.reUserDto.id){
              this.articles[i].concern = !this.articles[i].concern
            }
          }
        }else{
          this.$message.error('取消关注失败')
        }
      })
    },

    // 添加收藏
    addCollect (item) {
      this.getRequest('/auth/collection/add/'+item.id).then(res => {
        if(res.data.code === 200){
          item.collection = !item.collection
          item.collectionNum++
        }else{
          this.$message.error('收藏失败')
        }
        //console.log(res)
      })
    },
    // 取消收藏
    cancelCollect (item) {
      this.getRequest('/auth/collection/delete/'+item.id).then(res => {
        if(res.data.code === 200){
          item.collection = !item.collection
          item.collectionNum--
        }else{
          this.$message.error('收藏失败')
        }
        //console.log(res)
      })
    },

    // 打开/关闭评论框
    switchComment (item) {
      // 点击评论按钮，修改commentsShow的值来显示评论区域
      item.commentsShow = !item.commentsShow;
    },
    // 提交用户评论
    submitComment (item) {
      item.commentForm.articleId = item.id
      //评论不能为空
      if(item.commentForm.commentContent !== ''){
        this.postRequest('/auth/comment/add/',item.commentForm).then(res => {
          if(res.data.code === 200){
            this.$message.success('评论成功！')
            // console.log('评论res',res.data.data)
            //评论后给res先修改&添加属性，再传给前端刷新数据
            res.data.data.commentUser.userAvatar = "http://localhost:8081/downloadPhoto/" + res.data.data.commentUser.userAvatar
            this.$set(res.data.data,'replyShow1',false)
            item.reCommentDtos.push(res.data.data) //res返回的评论在前端中即时刷新
            item.commentForm.commentContent = '' //清空评论框
            item.commentNum++
          }else{
            this.$message.error('评论失败')
          }
        })
      }else{
        this.$message.error('请输入内容')
      }
    },
    // 打开评论的回复框
    switchReply(comment){
      this.replyDialogFormVisible = true
      this.commentInfo = comment
      //console.log('commentInfo',this.commentInfo)
    },
    // 打开回复的回复框
    switchReply2(reply,comment){
      this.replyDialog2FormVisible = true
      this.commentInfo = reply
      this.commentInfo.commentId = comment.commentId
      console.log('replyInfo',this.commentInfo)
    },
    // 提交评论的回复
    submitReply(id,userId){
      //需要评论的id，评论人的id=comment.commentUser.id
      this.replyForm.commentId = id
      this.replyForm.repliedUser = userId
      if(this.replyForm.replyContent !== ''){
        //console.log('form',this.replyForm)
        this.postRequest('/auth/reply/add/',this.replyForm).then(res => {
          if(res.data.code === 200){
            this.$message.success('回复成功！')
            //console.log('评论res',res)
            //this.$set(res.data.data,'replyShow1',false)
            //item.reCommentDtos.push(res.data.data) //res返回replyList对象添加到前端中即时刷新（找到对应的comment）
            for(let i = 0; i < this.articles.length;i++) {
              for (let j = 0; j < this.articles[i].reCommentDtos.length; j++) {
                if(this.articles[i].reCommentDtos[j].commentId === id){
                  console.log('comment',this.articles[i].reCommentDtos[j])
                  this.articles[i].reCommentDtos[j].replyList.push(res.data.data)
                }
              }
            }
            this.replyForm.replyContent = '' //清空回复框
            this.replyDialogFormVisible = false
            this.replyDialog2FormVisible = false
          }else{
            this.$message.error('评论失败')
          }
        })
      }else{
        this.$message.error('请输入内容')
      }
    },
    // 提交回复的回复
    submitReply2(id,userId){
      //需要评论的id，评论人的id=comment.commentUser.id
      this.replyForm.commentId = id
      this.replyForm.repliedUser = userId
      if(this.replyForm.replyContent !== ''){
        this.postRequest('/auth/reply/add/',this.replyForm).then(res => {
          if(res.data.code === 200){
            this.$message.success('回复成功！')
            //console.log('评论res',res)
            //this.$set(res.data.data,'replyShow1',false)
            //item.reCommentDtos.push(res.data.data) //res返回replyList对象添加到前端中即时刷新（找到对应的comment）
            for(let i = 0; i < this.articles.length;i++) {
              for (let j = 0; j < this.articles[i].reCommentDtos.length; j++) {
                if(this.articles[i].reCommentDtos[j].commentId === id){
                  //console.log('comment',this.articles[i].reCommentDtos[j])
                  this.articles[i].reCommentDtos[j].replyList.push(res.data.data)
                }
              }
            }
            this.replyForm.replyContent = '' //清空回复框
            this.replyDialog2FormVisible = false
          }else{
            this.$message.error('评论失败')
          }
        })
      }else{
        this.$message.error('请输入内容')
      }
    },

    // 点赞
    like (item) {
      this.getRequest('/auth/love/add/'+item.id).then(res => {
        if(res.data.code === 200){
          item.like = !item.like
          //点击后点赞数加一，前端修改即时呈现
          item.likeNum++
        }else{
          this.$message.error('点赞失败')
        }
        //console.log(res)
      })

    },
    //取消点赞
    unlike (item) {
      this.getRequest('/auth/love/delete/'+item.id).then(res => {
        if(res.data.code === 200){
          item.like = !item.like
          //点击后点赞数减一
          item.likeNum--
        }else{
          this.$message.error('取消点赞失败')
        }
      })
    },

    //删除帖子
    deleteArticle(item){
      this.deleteRequest('/auth/article/deleteArticle/'+item.id).then(res => {
        if(res.data.code === 200){
          this.$message.success('删除成功')
          //即时响应:从articles中去除
          let index = this.articles.indexOf(item)
          if(index !== -1){
            this.articles.splice(index,1)
          }
        }else{
          this.$message.error('删除失败')
        }
      })
    },


    // 从后端获取帖子数量赋值给count
    load () {
      // this.count += 2
      this.count = 10 // 需要所有用户全部帖子的数量接口
    },
    // 展开评论
    unfoldComments () {
      this.foldFlag = !this.foldFlag;
    }
  }
}
</script>

<style scoped>
ul{
  list-style: none;
  padding-left: 0;
}
.activity {
  margin-top: 13px;
  margin-right: 30px;  /*border: 1px solid #e52121;*/
}
.onePostContainer{
  width: 690px;
  margin: 7px auto;
  border: 1px solid #eaeaea;
  border-radius: 15px;
  padding: 10px;
  box-shadow: 0 0 25px #cac6c6;
  background: rgba(255,255,255,0.9);
  background-clip: padding-box;
}
.header{
  position: relative;
  height: 55px;
  width: 100%;
  text-align: left;
  line-height: 55px;
}
.cover {
  position: absolute;
  overflow: visible;
  float: left;
  width: 55px;
  height: 55px;
  border-radius: 50%;
}
.top-bar {
  float: left;
  position: relative;
  height: 55px;
  width: 460px;
  margin-top: 6px;
  margin-left: 65px;
  padding-right: 200px;
  text-align: left;
  line-height: 50px;
}
.title {
  overflow: hidden;
  text-overflow: ellipsis;
  height: 25px;
  max-width: 500px;
  margin-bottom: 5px;
  font-size: 16px;
  font-weight: 600;
  line-height: 25px;
}
.postTime {
  max-width: 500px;
  text-overflow: ellipsis;
  text-align: left;
  line-height: 20px;
  font-size: 12px;
  color: #909399;
}
.action {
  position: absolute;
  right: 50px;
  top: 0;
}

.postContent{
  margin-top: 5px;
}
.textContent{
  font-size: 14px;
  line-height: 1.7em;
  margin-left: 7px;
}

.imageOrVideos{
  width: 185px;
  height: 185px;
  margin: 5px 0 0 5px;
  -moz-border-radius:10px;
  -webkit-border-radius:10px;
  cursor: pointer;
  transition: all 0.6s;
  /*border: 1px solid #e52121;*/
}
.commentAndLike{
  width: 100%;
  height: 40px;
  /*border: 1px solid #e52121;*/
  margin: 2px;
  padding-bottom: 4px;
}
.commentAndShow{
  width: 100%;
  /*border: 1px solid #e52121;*/
}
.commentArea{
  /*border: 1px solid #e52121;*/
  margin-bottom: 10px;
  margin-top: 10px;
}
.showArea{
  border: 1px solid #e52121;
  width: 81%;
  margin-left: 61px;
  margin-top: 2px;
}
.showPerComment{
  border-bottom: 1px solid #99a2aa;
}
.replyContainer{
  width: 91%;
  /*border: 1px solid #e52121;*/
  border-left: #cac6c6 solid 2px;
  margin-left: 47px;
}
.unfoldComments{
  border: 1px solid #e52121;
  width: 100%;
  text-align: center;
  padding: 5px 0 5px 0;
}
</style>
