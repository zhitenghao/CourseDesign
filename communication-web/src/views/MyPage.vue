<template>
  <div class="myPage">
    <el-container style="height: 100%">
      <el-aside class="aside" width=240px>
        <div class="head-aside">
          <div class="my-avatar" style="cursor:pointer" @click="editAvatarDialogForm = true">
            <el-avatar :src="form.userAvatar" :size="60" :key="form.userAvatar" title="点击修改头像"/>
          </div>
          <myUpload
              :modelValue.sync="editAvatarDialogForm"
              :field="name"
              :url="updateAvatarApi"
              @crop-success="cropSuccess"
              @crop-upload-success="cropUploadSuccess"
              @crop-upload-fail="cropUploadFail"
          />
          <div class="username">
            <h3>{{form.userName}}</h3>
            <div style="font-size: 12px;color: #909399;">{{ form.userDescription }}</div>
          </div>
        </div>
        <div class="middle-aside">
          <div class="concern" style="border-right: 3px solid #f5f6f7;cursor:pointer" @click="toFans">
            <div style="padding-bottom: 10px">关注</div>
            <strong style="font-size: 22px;color: #121212">{{ form.userConcern }}</strong>
          </div>
          <div class="concern" style="cursor:pointer" @click="toFans">
            <div style="padding-bottom: 10px">粉丝</div>
            <strong style="font-size: 22px;color: #121212">{{ form.userConcerned }}</strong>
          </div>
        </div>
        <div class="menu-aside">
          <el-menu class="menu" default-active="none">
            <el-menu-item index="1" @click="toMyIndex">
              <i class="el-icon-user"></i>
              <span slot="title">我的主页</span>
            </el-menu-item>
            <el-menu-item index="2" @click="toFans">
              <i class="el-icon-connection"></i>
              <span slot="title">关注/粉丝</span>
            </el-menu-item>
            <el-menu-item index="3" @click="toMessages">
              <i class="el-icon-chat-dot-square"></i>
              <span slot="title">消息中心</span>
            </el-menu-item>
            <el-menu-item index="4" @click="editInfoDialogForm = true">
              <i class="el-icon-s-tools"></i>
              <span slot="title">编辑资料</span>
            </el-menu-item>
            <el-menu-item index="5">
              <i class="el-icon-edit-outline"></i>
              <span slot="title">修改密码</span>
            </el-menu-item>
            <el-menu-item index="6" @click="toIndex">
              <i class="el-icon-location"></i>
              <span slot="title">返回首页</span>
            </el-menu-item>
            <el-menu-item index="7" @click="logout">
              <i class="el-icon-switch-button"></i>
              <span slot="title">退出登录</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>

      <el-main class="main">
        <router-view v-if="isRouterAlive"></router-view>
      </el-main>
    </el-container>

    <!--用户信息编辑弹窗-->
    <el-dialog title="账号信息设置" :visible.sync="editInfoDialogForm">
      <el-form :model="form">
        <div style="text-align: center;margin-bottom: 10px">
          <el-avatar label="circleAvatar" :src="form.userAvatar" :size="90" :key="form.userAvatar"/>
        </div>
        <el-form-item label="昵称" :label-width="formLabelWidth">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" :label-width="formLabelWidth">
          <el-input v-model="form.userDescription" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学院" :label-width="formLabelWidth">
          <el-select v-model="collegeData" placeholder="请选择所在学院" @change="changeCollege">
            <el-option v-for="item in options" :lable="item.value" :value="item.value" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-radio-group v-model="form.userSex">
            <el-radio :label=0>男</el-radio>
            <el-radio :label=1>女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期" :label-width="formLabelWidth">
          <div class="block">
            <el-date-picker
                v-model="form.userBirthday"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="date"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="家乡" :label-width="formLabelWidth">
          <el-input v-model="form.userAddress" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editInfoDialogForm = false">取 消</el-button>
        <el-button type="primary" @click="updateUser(form)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import 'babel-polyfill'
import myUpload from 'vue-image-crop-upload'

export default {
  name:"myPage",
  components: { myUpload },
  provide(){
    return {
      reload:this.reload
    }
  },
  data(){
    return {
      form: {
        userAccount: '',
        userAddress: '',
        userBirthday: '',
        userCollege: '',
        userConcern: 0,
        userConcerned: 0,
        userDescription: '',
        userName: '',
        userPhone: '',
        userSex: 0,
        userAvatar:''
      },
      userId: '',
      editAvatarDialogForm: false,
      name: "file",//头像上传name
      updateAvatarApi: 'http://localhost:8081/upload',
      avatarOriginUrl: '',//保存头像原始url
      editInfoDialogForm: false,
      formLabelWidth: '70px',//编辑栏每项的宽度
      collegeData: '',
      options: [
        {
          value:'数学学院'
        }, {
          value:'智慧城市与交通学院'
        },{
          value:'计算机与人工智能学院'
        },{
          value:'利兹学院'
        },{
          value:'茅以升学院'
        },{
          value:'马克思主义学院'
        },{
          value:'土木工程学院'
        },{
          value:'信息科学与技术学院'
        },{
          value:'力学与工程学院'
        },{
          value:'生命科学与工程学院'
        },{
          value:'人文学院'
        },{
          value:'物理科学与技术学院'
        },{
          value:'建筑与设计学院'
        },{
          value:'地球科学与环境工程学院'
        },{
          value:'材料科学与工程学院'
        },{
          value:'交通运输与物流学院学院'
        },{
          value:'外国语学院'
        },{
          value:'经济管理学院'
        },{
          value:'电气工程学院'
        },{
          value:'机械工程学院'
        },{
          value:'公共管理学院'
        },{
          value:'体育学院'
        },{
          value:'其它'
        }
      ],
      isRouterAlive: true,
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    //获取用户资料
    getUserInfo() {
      this.getRequest('/auth/user/userInfo').then(res => {
        if(res.data.code === 200){
          this.form.userAccount = res.data.data.userAccount
          this.form.userName = res.data.data.userName
          this.form.userDescription = res.data.data.userDescription
          this.form.userCollege = res.data.data.userCollege
          this.form.userConcern = res.data.data.userConcern
          this.form.userConcerned = res.data.data.userConcerned
          this.form.userSex = res.data.data.userSex
          this.form.userBirthday = res.data.data.userBirthday
          this.form.userAddress = res.data.data.userAddress
          this.collegeData = this.form.userCollege
          this.avatarOriginUrl = res.data.data.userAvatar
          this.form.userAvatar = "http://localhost:8081/downloadPhoto/" + this.avatarOriginUrl
          this.userId = res.data.data.id
          console.log('userInfo',res.data.data)
        }else{
          this.$message.error("加载失败")
        }
      })
    },

    //上传头像成功
    cropSuccess(imgDataUrl,field){
      //console.log('cropSuccess')
    },
    //保存上传头像成功
    cropUploadSuccess(jsonData,field){
      //console.log('jsonData',jsonData)
      this.avatarOriginUrl = jsonData.data.id
      this.form.userAvatar = this.avatarOriginUrl
      this.postRequest('/auth/user/updateUser',this.form)
      this.form.userAvatar = "http://localhost:8081/downloadPhoto/" + this.avatarOriginUrl
      this.reload()//刷新帖子，使头像同步
      this.$message.success('修改成功')
    },
    //保存上传失败
    cropUploadFail(status, field){
      console.log(status, field)
      this.$message.error('修改失败')
    },
    //刷新帖子组件
    reload () {
      this.isRouterAlive = false
      this.$nextTick(() => (this.isRouterAlive = true))
    },
    //修改学院事件
    changeCollege(){
      this.form.userCollege = this.collegeData
    },
    //提交修改资料
    updateUser() {
      this.form.userAvatar = this.avatarOriginUrl
      this.postRequest('/auth/user/updateUser',this.form).then(res=>{
        if(res.data.code === 200){
          this.$message.success("修改成功")
          this.editInfoDialogForm = false
          this.getUserInfo()
        }else{
          this.$message.error("提交失败")
        }
      })
    },

    toMyIndex(){
      this.$router.replace('/personal')
    },
    toPost(){
      this.$router.replace('/personal/index')
    },
    toFans(){
      this.$router.push({
        name:"fans",
        query:{id:this.userId}
      })
    },
    toMessages(){
      this.$router.replace('/personal/messages')
    },
    toIndex(){
      this.$router.replace('/')
    },
    logout(){
      window.localStorage.removeItem('tokenStr')
      this.$router.replace('/login')
    },

  }
}

</script>

<style>
.myPage{
  width: 1200px;
  height: 100%;
  margin: 0 auto;
  background-color: #FFFFFF;
}
.main{
  overflow-y: auto;
  height: 100%;
  padding: 0;
}
.main::-webkit-scrollbar{
  display: none;
}
.aside{
  position: relative;
  height: 100%;
  border-right: 10px solid #f5f6f7;
}
.head-aside{
  height: 90px;
  border-top: 1px solid #f5f6f7;
  border-bottom: 10px solid #f5f6f7;
}
.my-avatar{
  width: 60px;
  height: 60px;
  margin: 10px 0 0 10px;
  border-radius: 50%;
  float: left;
}
.username{
  float: left;
  height: 80px;
  width: 110px;
  margin: 0 0 0 8px;
}
h3{
  -webkit-margin-after: 12px;
  -webkit-margin-before: 14px;
}
.middle-aside{
  display: flex;
  height: 100px;
  border-bottom: 10px solid #f5f6f7;
}
.concern{
  float: left;
  width: 50%;
  padding: 20px 0;
  text-align: center;
  font-size: 15px;
  color:#8590a6;
}

</style>
