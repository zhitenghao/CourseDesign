<template>
  <div class="register">
    <el-form :rules="registerRules" ref="registerForm" :model="registerForm" class="registerContainer">
      <h1 class="registerTitle">注册</h1>
      <div class="backToLogin" style="text-align: center">已有帐号？
        <el-button type="text" @click="returnLogin">登录</el-button>
      </div>
      <el-form-item prop="username">
        用户名
        <el-input type="text" auto-complete="false" v-model="registerForm.userAccount"></el-input>
      </el-form-item>
      <el-form-item prop="telephoneNumber">
        电话
        <el-input type="text" auto-complete="false" v-model="registerForm.userPhone"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        密码
        <el-input type="text" auto-complete="false" v-model="registerForm.userPwd"></el-input>
      </el-form-item>
      <el-form-item prop="gender" style="text-align: center">
        <el-radio v-model="registerForm.userSex" label="0" >男</el-radio>
        <el-radio v-model="registerForm.userSex" label="1" >女</el-radio>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" @click.native.prevent="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { encrypt } from '@/utils/rsaEncrypt'

export default {
  name: 'Register',
  data () {
    return {
      registerForm: {
        userAccount: '',
        userPhone: '',
        userPwd: '',
        userSex: '0',
      },
      registerRules: {
        userAccount: [{required: true, message: '请输入您的用户名', trigger: 'blur'}],
        userPhone: [{required: true, message: '请输入一个有效的联系电话', trigger: 'blur'}],
        userPwd: [{required: true, message: '密码至少8个字符且必须包含特殊符号', trigger: 'blur'}],
        userSex: [{required: true, message: '请选择您的性别', trigger: 'blur'}]
      }

    }
  },
  methods: {
    returnLogin () {
      this.$router.push({ path: '/login' })
    },
    register(){
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          //密码加密传输
          //this.registerForm.userPwd = encrypt(this.registerForm.userPwd)
          this.postRequest('/auth/user/addUser',this.registerForm).then(res => {
            if(res.data.code === 200){
              this.$message.success("注册成功！请登录")
              this.$router.push('/')
            }
            else{
              this.$message.error("用户名已存在，请重新注册！")
            }
          }).catch(()=>{
          })
        } else {
          this.$message.error('请重新输入注册信息')
        }
      })
    }
  }
}
</script>

<style>
.register{
  overflow: auto;
  height: 100%;
  background-image:url("../assets/backgroundImg.png");
  background-size: 100% 100%;
  background-attachment:fixed;
  background-repeat: no-repeat;
}
.registerContainer {
  width: 350px;
  margin: 80px auto;
  border: 1px solid #eaeaea;
  border-radius: 15px;
  padding: 15px 35px 15px 35px;
  box-shadow: 0 0 25px #cac6c6;
  background: rgba(255,255,255,0.9);
  /*background-clip: padding-box;*/
}
.registerTitle {
  margin: 20px auto 15px auto;
  text-align: center;
}
</style>
