<template>
  <div class="articles">
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
      <li v-for="item in articles" :key="item.id" class="infinite-list-item">
        <div class="onePostContainer">
          <div class="header">
            <el-avatar shape="square" :size="50" :src="headPortrait" style="float: left"></el-avatar>
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
            <div v-if="textContentFlag" style="width: 83%; margin: 0px 57px">
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
          <!--          评论部分-->
          <div class="commentAndShow" v-if="item.commentAndShowFlag">
            <div class="commentArea">
              <el-input v-model="commentForm.commentContent" placeholder="请输入您的评论" style="width: 71%; padding-left: 60px"></el-input>
              <el-button type="primary" @click="submitComment(item)">确认</el-button>
            </div> <!-- 发表评论的输入框 -->
            <div class="showArea"> <!-- 展示用户们的评论 -->
              <div class="showPerComment" v-for="comment in item.reCommentDtos" :key="comment.commentTime">
                <div> <!-- 用户的头像和用户名调用后端的接口 -->
                  <el-avatar shape="square" :size="35" :src="headPortrait" style="margin: 2px 0 0 2px;"></el-avatar>
                  <span style="font-size: small; position: relative;bottom: 11px; margin-left: 8px; margin-right: 3px; color: chocolate"> {{comment.commentUser.userName}}: </span>
                </div>
                <div style="margin-left: 45px; position: relative;bottom: 7px;"> <!-- 用户的评论内容也要调用后端的接口 -->
                  <span style="font-size: small;">{{ comment.commentContent }}</span>
                </div>
                <div class="replyContainer">
                  <div class="reply" v-for="reply in comment.replyList" :key="reply.replyeTime">
                    <span style="font-size: small; color: chocolate">{{reply.replyUser.userName}}</span>
                    <span style="font-size: small">:回复@</span>
                    <span style="font-size: small; color: chocolate">{{reply.replyedUser.userName}}:</span>
                    <span style="font-size: small">{{reply.replyContent}}</span>
                  </div>
                </div>
              </div>
              <div class="unfoldComments" v-if="comments.length > 4" @click="unfoldComments">
                <span style="font-size: small;">{{ foldFlag? '点击展开更多评论' : '收起' }}</span>
              </div>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Post',
  data () {
    return {
      //count: 0, // 循环数量
      headPortrait: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg', // 需要用户的头像接口
      textContentFlag: true, // 设置帖子是否有文字内容，如果有则设置为true，没有则设置为false
      imageOrVideoFlag: true, // 设置帖子是否有图片或视频内容，如果有则设置为true，没有则设置为false
      foldFlag: true,
      imageOrVideosUrl: [ // 图片或视频的数组，调用帖子的图片或视频接口
        'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
        'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
        'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
        // 'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
        // 'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
        // 'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
        // 'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
        // 'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
        // 'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
        // 'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
        // 'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
        // 'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
        // 'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg'
      ],
      comments: [ // 评论区里的网友评论
        '太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了太难了',
        'edg牛逼',
        'edg好样的',
        '你们太吵了啊，我要睡觉',
        'shut up',
        '求求了，你们要赢啊'
      ],



      articles: [],//所有帖子的集合
      commentAndShowFlag: false,
      commentForm:{ //评论表单
        articleId: '',
        commentContent: '',
        userId: ''
      }
    }
  },
  created() {
    this.getArticle()
  },
  methods:{
    getArticle() {
      this.getRequest('/auth/article/listMyself').then(res => {
        console.log(res)
        this.articles = res.data.data
        //console.log(this.articles);
        //给articles循环每项插入属性commentAndShowFlag
        this.articles = this.articles.map(item => {
          return {...item,commentAndShowFlag: false}
        })
        console.log(this.articles)
      })
    },
    // 添加关注
    addAttention (item) {
      //所有该用户的“关注”全变为“已关注”
      for(let i = 0;i < this.articles.length;i++){
        if(this.articles[i].reUserDto.id === item.reUserDto.id){
          this.articles[i].concern = !this.articles[i].concern
        }
      }
      this.getRequest('/auth/concern/add/'+item.reUserDto.id+'').then(res => {
      })
    },
    //取消关注
    cancelAttention(item){
      //所有该用户的“已关注”全变为“关注”
      for(let i = 0;i < this.articles.length;i++){
        if(this.articles[i].reUserDto.id === item.reUserDto.id){
          this.articles[i].concern = !this.articles[i].concern
        }
      }
      this.getRequest('/auth/concern/delete/'+item.reUserDto.id+'').then(res => {
      })
    },
    // 添加收藏
    addCollect (item) {
      item.collection = !item.collection
      this.getRequest('/auth/collection/add/'+item.id).then(res => {
        //console.log(res)
      })
      item.collectionNum++
    },
    // 取消收藏
    cancelCollect (item) {
      item.collection = !item.collection
      this.getRequest('/auth/collection/delete/'+item.id).then(res => {
        //console.log(res)
      })
      item.collectionNum--
    },
    // 打开/关闭评论
    switchComment (item) {
      // 点击评论按钮，修改commentAndShowFlag的值来显示评论区域
      item.commentAndShowFlag = !item.commentAndShowFlag;
    },
    // 提交用户评论
    submitComment (item) {
      this.commentForm.articleId = item.id
      // this.commentForm.userI
      this.postRequest('/auth/comment/add/',this.commentForm).then(res => {
        //res返回评论在前端评论中显示
        item.reCommentDtos.push(res.data.data)
      })
      this.$message.success('评论成功！')
      this.commentForm.commentContent = ''
      item.commentNum++
    },
    // 点赞
    like (item) {
      item.like = !item.like
      this.getRequest('/auth/love/add/'+item.id+'').then(res => {
        //console.log(res)
      })
      //点击后点赞数加一，前端修改即时呈现
      item.likeNum++
    },
    //取消点赞
    unlike (item) {
      item.like = !item.like
      this.getRequest('/auth/love/delete/'+item.id+'').then(res => {
      })
      //点击后点赞数减一
      item.likeNum--
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
  width: 700px;
  box-shadow: 0 0 25px #cac6c6;
  background-clip: padding-box;
  margin: 7px auto;
  border: 1px solid #eaeaea;
  border-radius: 15px;
  padding: 10px 10px 10px 10px;
  background: rgba(255,255,255,0.9);
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
  /*border: 1px solid #e52121;*/
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
