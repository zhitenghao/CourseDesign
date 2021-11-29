<template>
  <div class="login">
    <el-form :rules="loginRules" ref="loginForm" :model="loginForm" class="loginContainer" v-if="!registerFlag">
      <h1 class="loginTitle">交大社区登录</h1>
      <div class="toRegister" style="text-align: center">还没有账号？
        <el-button type="text" @click.native.prevent="toRegister">注册</el-button>
      </div>
      <el-form-item prop="username">
        用户名
        <el-input type="text" auto-complete="false" v-model="loginForm.username" @keyup.enter.native="submitLogin"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        密码
        <el-input type="password" auto-complete="false" v-model="loginForm.password" show-password @keyup.enter.native="submitLogin"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        验证码
        <div class="code-img">
          <el-input type="text" auto-complete="false" v-model="loginForm.code" placeholder="点击图片更换验证码" style="width: 45%; margin-right: 20px" @keyup.enter.native="submitLogin"></el-input>
          <img :src="captchaUrl" @click="updateCaptcha">  <!--连到验证码图片路径-->
        </div>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="rememberMe">记住账号</el-checkbox>
        <el-button type="text" style="margin-left: 190px">忘记密码？</el-button>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" style="width: 100%" @click.native.prevent="submitLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>

    <div class="register" v-if="registerFlag">
      <el-form :rules="registerRules" ref="registerForm" :model="registerForm" class="registerContainer">
        <h1 class="registerTitle">注册</h1>
        <div class="backToLogin" style="text-align: center">已有帐号？
          <el-button type="text" @click="toRegister">登录</el-button>
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
          <el-input type="password" auto-complete="false" v-model="registerForm.userPwd" show-password></el-input>
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
  </div>

</template>

<script>
import qs from 'qs'
import Cookies from 'js-cookie'
import { encrypt } from '@/utils/rsaEncrypt'

export default {
  name: 'Login',
  data () {
    return {
      captchaUrl: '', // 验证码图片路径,  /captcha是接口, 加上+new Date()可以点击更换验证码
       loginForm: {
        username: '',
        password: '',
        code: '',
        token: ''
      },
      loginRules: {
        username: [{required: true, message: '请输入有效的用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
      },
      cookiePass: '',//加密后的密码存放到Cookie中
      rememberMe: false,
      loading:false,
      registerFlag: false,
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
  created () {
    this.updateCaptcha()
    this.getCookie()
  },
  methods: {
    getCookie() {
      const username = Cookies.get('username')
      let password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      // 保存cookie里面的加密后的密码
      this.cookiePass = password === undefined ? '' : password
      password = password === undefined ? this.loginForm.password : password
      //从cookie中取出info放入登陆表单中提交
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password,
        code: ''
      }
      this.rememberMe = this.rememberMe === undefined ? false : Boolean(rememberMe)
    },
    updateCaptcha () { // 点击验证码图片会更换验证码图片，跨域请求后端接口有关文件 vue.config.js
      this.getRequest('/auth/captcha').then(res => {
        this.captchaUrl = res.data.data.captcha
        this.loginForm.token = res.data.data.token
      })
    },
    //切换登录/注册
    toRegister () {
      this.registerFlag = !this.registerFlag
    },
    submitLogin () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 每一个需要调用后端接口的事件，都要判断请求是否成功，过于复杂，因此，可以封装起来用统一的响应拦截器得到相应的结果进行判断是否成功，如果失败就统一提示
          // console.log(this.loginForm)
          const user = {
            username: this.loginForm.username,
            password: this.loginForm.password,
            code: this.loginForm.code,
            token:this.loginForm.token
          }
          if (user.password !== this.cookiePass) {
            //密码加密传输
            user.password = encrypt(user.password)
          }
          if (this.rememberMe) {
            Cookies.set('username', user.username, { expires: 7 })
            Cookies.set('password', user.password, { expires: 7 })
            Cookies.set('rememberMe', this.rememberMe, { expires: 7 })
          } else {
            Cookies.remove('username')
            Cookies.remove('password')
            Cookies.remove('rememberMe')
          }
          this.loading = true
          console.log(user)
          this.postRequest('/auth/login', qs.stringify(user)).then(resp => {
            console.log(resp)
            if (resp.data.code === 200) {
              this.$message.success('登录成功！')
              const jwt = resp.headers.authorization
              // 存储用户token
              // const jwt = resp.headers['authorization']
              // this.$store.commit('SET_TOKEN', jwt)
              // const tokenStr = resp.obj.tokenHead + resp.obj.token
              window.localStorage.setItem('tokenStr', jwt)
              // 跳转首页
              this.$router.push('/')
            } else{
              this.$message.error("登陆失败，请重新登录！")
              this.loading = false
              this.updateCaptcha()
            }
          }).catch(()=>{
            this.loading = false
            this.updateCaptcha()
          })
        } else {
          this.$message.error('请重新输入登录信息')
          return false
        }
      })
    },
    register(){
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          //密码加密传输
          //this.registerForm.userPwd = encrypt(this.registerForm.userPwd)
          this.postRequest('/auth/user/addUser',this.registerForm).then(res => {
            if(res.data.code === 200){
              this.$message.success("注册成功！请登录")
              this.toRegister()
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
.login{
  overflow: auto;
  height: 100%;
  background-image:url("../assets/backgroundImg.png");
  background-size: 100% 100%;
  background-attachment:fixed;
  background-repeat: no-repeat;
}
.loginContainer {
  width: 350px;
  margin: 90px 100px 120px auto;
  border: 1px solid #eaeaea;
  border-radius: 15px;
  padding: 15px 35px 15px 35px;
  box-shadow: 0 0 25px #cac6c6;
  background: rgba(255,255,255,0.9);
  /*background-clip: padding-box;不起作用*/
}
.loginTitle {
  text-align: center;
  margin: 20px auto 15px auto;
}
.code-img img{
  width: 140px;
  vertical-align: middle;
  border-radius: 2px;
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
