<template>
  <div class="myPage">
    <el-container style="height: 100%">
      <el-aside class="aside" width=240px>
        <div class="head-aside">
          <div class="avatar" style="cursor:pointer" @click="editAvatarDialogForm = true">
            <el-avatar :src="form.userAvatar" :size="60" title="点击修改头像"/>
          </div>
          <div class="username">
            <h3>{{form.userName}}</h3>
            <div style="font-size: 12px;color: #909399;">{{ form.userDescription }}</div>
          </div>
        </div>
        <div class="middle-aside">
          <div class="concern" style="border-right: 3px solid #f5f6f7;cursor:pointer" @click="toFans">
            <div style="padding-bottom: 10px">关注</div>
            <strong style="font-size: 22px;color: #121212">{{ this.form.userConcern }}</strong>
          </div>
          <div class="concern" style="cursor:pointer" @click="toFans">
            <div style="padding-bottom: 10px">粉丝</div>
            <strong style="font-size: 22px;color: #121212">{{ this.form.userConcerned }}</strong>
          </div>
        </div>
        <div class="menu-aside">
          <el-menu class="menu" default-active="none">
            <el-menu-item index="1" @click="toMyIndex">
              <i class="el-icon-user"></i>
              <span slot="title">我的主页</span>
            </el-menu-item>
            <el-menu-item index="2" @click="toFans">
              <i class="el-icon-user"></i>
              <span slot="title">关注/粉丝</span>
            </el-menu-item>
            <el-menu-item index="3" @click="toFans">
              <i class="el-icon-user"></i>
              <span slot="title">消息中心</span>
            </el-menu-item>
            <el-menu-item index="4" @click="editInfoDialogForm = true">
              <i class="el-icon-edit-outline"></i>
              <span slot="title">编辑资料</span>
            </el-menu-item>
            <el-menu-item index="5">
              <i class="el-icon-edit-outline"></i>
              <span slot="title">修改密码</span>
            </el-menu-item>
            <el-menu-item index="6" @click="toIndex">
              <i class="el-icon-user"></i>
              <span slot="title">返回首页</span>
            </el-menu-item>
            <el-menu-item index="7" @click="logout">
              <i class="el-icon-user"></i>
              <span slot="title">退出登录</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>

      <el-main class="main">
        <router-view></router-view>
      </el-main>
    </el-container>

    <!--用户头像编辑弹窗-->
    <el-dialog title="编辑头像" :visible.sync="editAvatarDialogForm" >
      <div class="editAvatar">
        <el-avatar :src="form.userAvatar" shape="square" :size="300" style="width: 500px"/>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editAvatarDialogForm = false">取 消</el-button>
        <el-button type="primary" @click="updateUser(form)">保存</el-button>
      </div>
    </el-dialog>
    <!--用户信息编辑弹窗-->
    <el-dialog title="账号信息设置" :visible.sync="editInfoDialogForm">
      <el-form :model="form">
        <el-avatar label="circleAvatar" :src="form.userAvatar" :size="80"/>
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

export default {
  name:"myPage",
  components: {
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
      collegeData: '',
      formLabelWidth: '120px',
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
      editAvatarDialogForm: false,
      editInfoDialogForm: false,
      post: true
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    //获取用户资料
    getUserInfo() {
      this.getRequest('/auth/user/userInfo').then(res => {
        console.log(res.data.data)
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
        this.form.userAvatar = res.data.data.userAvatar
        this.form.userAvatar = "http://localhost:8081/downloadPhoto/" + this.form.userAvatar
      })
    },

    toMyIndex(){
      this.$router.replace('/personal')
    },
    toPost(){
      this.$router.replace('/personal/index')
    },
    toFans(){
      this.$router.replace('/personal/fans')
    },
    toIndex(){
      this.$router.replace('/')
    },
    logout(){
      window.localStorage.removeItem('tokenStr')
      this.$router.replace('/login')
    },

    //修改头像
    editAvatar(){

    },
    //修改学院事件
    changeCollege(){
      this.form.userCollege = this.collegeData
      //console.log(this.form.userCollege)
    },
    //提交修改给后端
    updateUser() {
      console.log("form的数据", this.form)
      this.postRequest('/auth/user/updateUser',this.form).then(res=>{
        this.$message.success("提交成功")
        this.editInfoDialogForm=false
        this.getUserInfo()
      })
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
.avatar{
  width: 60px;
  height: 60px;
  margin: 10px 0 0 10px;
  border-radius: 50%;
  float: left;
}
.editAvatar{
  /*width: 300px;*/
  /*height: 300px;*/
  cursor: move;
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
