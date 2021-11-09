<template>
  <div class="personal">
    <el-container class="outcontainer">
      <el-aside class="aside">
        <div id="div1" style="border-bottom:solid;border-bottom-color: #D3D3D3">
          <h1 style="font-size: 40px;">个人主页</h1>
        </div>
        <el-menu class="menu" default-active="none" style="background-color:#FFFFF0">
          <el-menu-item index="1">
            <i class="el-icon-user"></i>
            <span slot="title">我的主页</span>
          </el-menu-item>
          <el-menu-item index="2" @click="dialogFormVisible = true">
            <i class="el-icon-edit-outline"></i>
            <span slot="title">编辑资料</span>
          </el-menu-item>
          <el-menu-item index="3">
            <i class="el-icon-picture-outline"></i>
            <span slot="title">发帖</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-chat-dot-square"></i>
            <span slot="title">消息</span>
          </el-menu-item>
          <el-menu-item index="5">
            <i class="el-icon-star-off"></i>
            <span slot="title">收藏夹</span>
          </el-menu-item>
          <el-menu-item index="6">
            <i class="el-icon-user"></i>
            <span slot="title">关注</span>
          </el-menu-item>
          <el-menu-item index="7">
            <i class="el-icon-user"></i>
            <span slot="title">粉丝</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container class="insidecontainer">
        <el-header>
          <div class="div2">
            <div class="headimg">
              <el-avatar :src="require('../assets/images/cat.jpg')" :size="80">
              </el-avatar>
            </div>
            <div class="fans">
              <h1>某某人</h1>
              <span>粉丝</span>
              <span>123</span>
              &nbsp;
              <span>关注</span>
              <span>130</span>
            </div>
          </div>
        </el-header>
        <el-main>
<!--          中间部分-->
          <dairui/>
        </el-main>
      </el-container>
    </el-container>

    <!--用户信息编辑弹窗-->
    <el-dialog title="账号信息设置" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-avatar lebel="circleAvatar" :src="require('../assets/images/home/avatar.jpg')" :size="80"/>
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
<!--        userSex传int值,form中是string-->
<!--        <el-form-item label="年龄" :label-width="formLabelWidth">-->
<!--          <el-input v-model="form.age" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
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
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUser(form)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import dairui from '@/views/dairui'

export default {
  components: {
    dairui
  },
  data(){
    return {
      dialogFormVisible: false,
      collegeData: '',
      form: {
        userAccount: '',
        userAddress: '',
        userBirthday: '',
        userCollege: '',
        userDescription: '',
        userName: '',
        userPhone: '',
        userSex: 0,
        userAvatar:''
      },
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
      ]
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    errorHandler() {
      return true
    },
    //获取用户资料
    getUserInfo() {
      this.getRequest('/auth/user/userInfo').then(res => {
        //console.log(res)
        this.form.userAccount = res.data.data.userAccount
        this.form.userName = res.data.data.userName
        this.form.userDescription = res.data.data.userDescription
        this.form.userCollege = res.data.data.userCollege
        this.form.userSex = res.data.data.userSex
        this.form.userBirthday = res.data.data.userBirthday
        this.form.userAddress = res.data.data.userAddress
        this.collegeData = this.form.userCollege
      })
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
        this.dialogFormVisible=false
        this.getUserInfo()
      })
    },

  }
}

</script>

<style>
.personal{
  background-color: #FFFFF0;
  margin: 0;
  height: 100%;
  width: 100%;
}
.outcontainer{
  background-color: #FFFFF0;
  margin: 0;
  height: 100%;
  width: 100%;
  /*display: inline-table;*/
}
.aside{
  overflow: hidden;
  background-color: #FFFFF0;
  width: 30%;
  height: 100%;
  border: solid;
  border-color:#D3D3D3;
}

.menu {
  border-right:0!important;
}

.insidecontainer{
  background-size: 100%;
  background-image: url(../assets/images/天鹅.jpg);
  margin: 0px;
  height: 100%;
  width: 100%;
}
	
el-header{
  height: 35%;
  margin: 0;;
}
	
#div1{
  height: 30%;
  width: 100%;
  padding:60px;
}
	
.div2{
  background-color: #FFFFFF;
  height: 100px;
  width: 65%;
  margin: auto;
  margin-top:25px;
}
	
el-avatar{
  margin: auto;
}
	
.headimg {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  margin-left: 10px;
  margin-top: 10px;
  float: left;
}
	
.fans{
  background-color: #FFFFFF;
  height: 100px;
  width: 20%;
  float: left;
  margin-left: 10px;
}


el-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  margin: auto;
  margin-top: 100px;
}
	
</style>
