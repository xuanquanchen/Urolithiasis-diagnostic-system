<!--
  * @FileDescription:医生端登陆
  * @Date:2021/10/1
  * @LastEditTime:2021/10/5
 -->
<template>
  <div>
    <el-input
      v-model="userName"
      prefix-icon="el-icon-s-custom"
      placeholder="请输入用户名"
      clearable
      @change="enterCheck(0)"
      @blur="blur(0)"
      @focus="focus(0)">
      </el-input>
      <!-- 密码输入 -->
      <el-input
      v-model="password"
      prefix-icon="el-icon-key"
      style="margin-top: 20px;"
      type="password"
      placeholder="请输入密码"
      clearable
      @change="enterCheck(1)"
      @blur="blur(1)"
      @focus="focus(1)" />
      <!-- 用户操作 -->
      <div style="display: flex;">
        <el-button
        class="button"
        @click="signin">注册</el-button>
        <el-button
        :loading="logining"
        class="button login-button"
        @click="login">{{logining?'登录中':'登录'}}</el-button>
      </div>
  </div>
</template>

<script>
export default {
  props: {
    logining: {
      default: true,
      type: Boolean
    }
  },
  data () {
    return {
      userName: '',
      password: '',
      active: [0, 0] // 用以记录输入框是否被选中了，以判断回车键时是否提交
    }
  },
  methods: {
    enterCheck (index) {
      // 判断是否是回车触发了@change，如果@blur被触发说明不是回车触发
      setTimeout(() => {
        if (this.active[index] && this.userName !== '' && this.password !== '') {
          this.login()
        }
      }, 200)
    },
    blur (index) {
      this.active[index] = 0
    },
    focus (index) {
      this.active[index] = 1
    },
    signin () {
      this.$emit('signin')
    },
    login () {
      this.$emit('login', this.userName, this.password, 1)
    }
  }
}
</script>

<style scoped>
.button{
  width: 48%;
  margin-top: 20px;
}
.button:hover {
  background-image: linear-gradient(129.12deg, #446dff 0%, rgba(99, 125, 255, 0.75) 100%);
  color: #fff;
}
.login-button {
  background-image: linear-gradient(170deg, #446dff 0%, rgba(99, 125, 255, 0.75) 100%) !important;
  color: #fff;
}
</style>
