<template>
  <div class="login">
    <el-form :rules="rules" ref="loginForm" :model="loginForm" class="loginContainer">
      <h1 class="loginTitle">交大社区登录</h1>
      <div class="toRegister" style="text-align: center">还没有账号？
        <el-button type="text" @click.native.prevent="register">注册</el-button>
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
        <el-checkbox>记住账号</el-checkbox>
        <el-button type="text" style="margin-left: 190px">忘记密码？</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" @click.native.prevent="submitLogin">登录</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>

import qs from 'qs'

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
      rules: {
        username: [{
          required: true,
          message: '请输入有效的用户名',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '请输入验证码',
          trigger: 'blur'
        }]
      }
    }
  },
  created () {
    this.updateCaptcha()
  },
  methods: {
    updateCaptcha () { // 点击验证码图片会更换验证码图片，跨域请求后端接口有关文件 vue.config.js
      this.getRequest('/auth/captcha').then(res => {
        console.log(res)
        this.captchaUrl = res.data.data.captcha
        this.loginForm.token = res.data.data.token
      })
    },
    submitLogin () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 可以用axios 调用后端接口，得到相应的返回，进行相应的处理
          // 安装npm install axios
          // 每一个需要调用后端接口的事件，都要判断请求是否成功，过于复杂，因此，可以封装起来用统一的响应拦截器得到相应的结果进行判断是否成功，如果失败就统一提示
          // 响应拦截器在utils/api.js文件中
          //
          // 校验登录信息，若信息正确页面跳转到网站首页
          console.log(this.loginForm)
          this.postRequest('/auth/login', qs.stringify(this.loginForm)).then(resp => {
            if (resp.data.code === 200) {
              this.$message.success('登录成功！')
              const jwt = resp.headers.authorization
              // console.log(jwt)
              // }
              // 存储用户token
              // const jwt = resp.headers['authorization']
              // this.$store.commit('SET_TOKEN', jwt)
              // const tokenStr = resp.obj.tokenHead + resp.obj.token
              window.localStorage.setItem('tokenStr', jwt)
              // 跳转首页
              this.$router.push('/index')
            }
        else{
          this.$message.error("登陆失败，请重新登录！")
              this.updateCaptcha()
            }
          })
        } else {
          this.$message.error('请重新输入登录信息')
          return false
        }
      })
    },
    register () {
      this.$router.push({ path: '/register' })
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
</style>
