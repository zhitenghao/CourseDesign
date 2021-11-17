<template>
  <div class="articles">
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
      <li v-for="item in articles" :key="item.id" class="infinite-list-item">
        <div class="onePostContainer">
          <div class="header">
            <el-avatar :size="55" :src="item.reUserDto.userAvatar" style="float: left"></el-avatar>
            <span style="float: left; margin: 15px 10px;">
              <b>{{item.reUserDto.userName}}</b>
            </span>
            <div class="concern" v-if="!item.myself"> <!-- 设置关注此用户 -->
              <el-button type="warning" v-if="item.concern" @click="cancelAttention(item)" style="margin-left: 420px" size="small" round plain>已关注</el-button>
              <el-button type="warning" v-else @click="addAttention(item)" style="margin-left: 420px" size="small" round plain>+ 关注</el-button>
            </div>
          </div>
          <div class="postContent">
            <!-- 设置可自适应文本高度的文本域：从后端获取帖子内容以后，把内容放在<span>标签里，textContentFlag可以设置是否展示文字内容 -->
            <div v-if="textContentFlag" style="width: 83%; margin: 0 57px">
              <span class="textContent">{{item.articleContent}}</span>
            </div>
            <!-- 设置图片或者视频展示区域 -->
            <div v-if="imageOrVideoFlag" style="width: 83%; margin: 0 57px;">
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
              <!-- 发表评论的输入框 -->
              <el-input v-model="commentForm.commentContent" placeholder="请输入您的评论" style="width: 71%; padding-left: 60px"></el-input>
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
                  <div class="commentArea" v-if="comment.replyShow1"> <!-- 发表评论的输入框 -->
                    <el-input v-model="replyForm.replyContent" placeholder="请输入您的评论" style="width: 86%;"></el-input>
                    <el-button type="primary" @click="submitReply(comment)">确认</el-button>
                  </div>
                </div>
                <!-- 回复部分 -->
                <div class="replyContainer">
                  <div class="reply" v-for="reply in comment.replyList" :key="reply.replyeTime">
                    <span style="font-size: small; color: chocolate">{{reply.replyUser.userName}}</span>
                    <span style="font-size: small"> :回复@ </span>
                    <span style="font-size: small; color: chocolate">{{reply.replyedUser.userName}}:</span>
                    <span style="font-size: small"> {{reply.replyContent}}</span>
                    <br>
                    <span style="font-size: 10px; color: #99a2aa">{{ reply.replyTime }}</span> <!-- 回复时间 -->
                    <span style="font-size: small; cursor: pointer; margin-left: 20px; color: #99a2aa" @click="switchReply2(reply)">回复</span>
                    <div class="commentArea" v-if="reply.replyShow2"> <!-- 发表评论的输入框 -->
                      <el-input v-model="replyForm.replyContent" placeholder="请输入您的评论" style="width: 86%;"></el-input>
                      <el-button type="primary" @click="submitReply(reply)">确认</el-button>
                    </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="unfoldComments" v-if="item.commentNum >= 3" @click="unfoldComments">
                <span style="font-size: small;">{{ foldFlag? '点击展开更多评论' : '收起' }}</span>
              </div>
            </div>
          </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      textContentFlag: true, // 设置帖子是否有文字内容，如果有则设置为true，没有则设置为false
      imageOrVideoFlag: true, // 设置帖子是否有图片或视频内容，如果有则设置为true，没有则设置为false
      foldFlag: true,


      articles: [],//帖子的集合
      commentForm:{ //评论提交表单
        articleId: '',
        commentContent: '',
        userId: ''
      },
      replyForm:{
        commentId: '',
        replyContent: '',
        userId: ''
      }
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
        //给articles循环每项插入属性commentsShow，每项的所有评论插入replyShow1，每个评论的所有回复插入replyShow2
        this.articles = this.articles.map(item => {
          item.reCommentDtos = item.reCommentDtos.map(item => {
            item.replyList = item.replyList.map(item => {
              return {...item,replyShow2: false}
            })
            return {...item,replyShow1: false}
          })
          return {...item,commentsShow: false}
        })
        // console.log(this.articles)
        //所有图片需要在属性前加一段前缀再渲染到界面上
        for(let i=0;i<this.articles.length;i++){
          for(let j=0;j<this.articles[i].urls.length;j++){
            this.articles[i].urls[j] = "http://localhost:8081/downloadPhoto/" + this.articles[i].urls[j]
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
          this.message.error('关注失败')
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
          this.message.error('取消关注失败')
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
          this.message.error('收藏失败')
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
          this.message.error('收藏失败')
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
      this.commentForm.articleId = item.id
      this.postRequest('/auth/comment/add/',this.commentForm).then(res => {
        if(res.data.code === 200){
          item.reCommentDtos.push(res.data.data) //res返回的评论在前端中即时刷新
          this.$message.success('评论成功！')
          this.commentForm.commentContent = ''
          item.commentNum++
        }else{
          this.message.error('评论失败')
        }
      })
    },
    // 打开/关闭评论的回复框
    switchReply(comment){
      comment.replyShow1 = !comment.replyShow1
    },
    // 打开/关闭回复的回复框
    switchReply2(reply){
      reply.replyShow2 = !reply.replyShow2
    },
    // 提交回复
    submitReply(comment){

    },


    // 点赞
    like (item) {
      this.getRequest('/auth/love/add/'+item.id+'').then(res => {
        if(res.data.code === 200){
          item.like = !item.like
          //点击后点赞数加一，前端修改即时呈现
          item.likeNum++
        }else{
          this.message.error('点赞失败')
        }
        //console.log(res)
      })

    },
    //取消点赞
    unlike (item) {
      this.getRequest('/auth/love/delete/'+item.id+'').then(res => {
        if(res.data.code === 200){
          item.like = !item.like
          //点击后点赞数减一
          item.likeNum--
        }else{
          this.message.error('取消点赞失败')
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
.concern{
  margin-top: 13px;
  margin-right: 30px;
  float: right;
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
  padding: 10px 10px 10px 10px;
  box-shadow: 0 0 25px #cac6c6;
  background: rgba(255,255,255,0.9);
  background-clip: padding-box;
}
.header{
  height: 50px;
  width: 100%;
  /*border: 1px solid #e52121;*/
}
.postContent{
  /*border: #e52121 1px solid;*/
  margin-top: 10px;
}
.textContent{
  font-size: 14px;
  line-height: 1.7em;
  /*border: 1px solid #e52121;*/
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
