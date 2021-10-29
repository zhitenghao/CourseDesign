<template>
  <div>
    <el-form :rules="rules" ref="registerForm" :model="registerForm" class="registerContainer">
      <h1 class="registerTitle">注册</h1>
      已有帐号？
      <el-button type="text" @click="returnLogin">登录</el-button>
      <el-form-item prop="username">
        用户名
        <el-input type="text" auto-complete="false" v-model="registerForm.username"></el-input>
      </el-form-item>
      <el-form-item prop="telephoneNumber">
        电话
        <el-input type="text" auto-complete="false" v-model="registerForm.telephoneNumber"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        密码
        <el-input type="text" auto-complete="false" v-model="registerForm.password"></el-input>
      </el-form-item>
      <el-form-item prop="gender">
        <el-radio v-model="gender" label="1">男</el-radio>
        <el-radio v-model="gender" label="2">女</el-radio>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" >注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      gender: '1',
      registerForm: {
        username: '',
        telephoneNumber: '',
        password: ''
      },
      rules: {
        username: [{
          required: true,
          message: '请输入您的用户名',
          trigger: 'blur'
        }],
        telephoneNumber: [{
          required: true,
          message: '请输入一个有效的联系电话',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '密码至少8个字符且必须包含特殊符号',
          trigger: 'blur'
        }],
        gender: [{
          required: true,
          message: '请选择您的性别',
          trigger: 'blur'
        }]
      }

    }
  },
  methods: {
    returnLogin () {
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style>
.registerContainer {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 80px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: rgba(255,255,255,0.9);
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.registerTitle {
  margin: 20px auto 15px auto;
  text-align: center;
}
</style>
