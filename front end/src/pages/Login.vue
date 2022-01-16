<!--
  * @FileDescription:登陆页面
  * @Date:2021/10/1
  * @LastEditTime:2021/10/5
 -->
<template>
  <div class="center">
    <!-- 页面背景图片，拆分为左右以适应宽度 -->
    <img src="https://cos.raccooncc.top/health/login_bg_left.png" draggable="false" style="left: 0;" class="bg">
    <img src="https://cos.raccooncc.top/health/login_bg_right.png" draggable="false" style="right: 0;" class="bg">
    <!-- 账户密码输入框 -->
    <div class="login-card" style="width: 290px;z-index: 200;">
      <img src="@/assets/title.png" draggable="false" style="width: 100%;margin-bottom: 10px;">
      <!-- 医生患者登陆分离-->
      <div style="border-radius: 20px;overflow: hidden;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-bottom: 50px;">
        <el-tabs v-model="loginType" type="border-card">
          <el-tab-pane label="用户登录" name="user">
            <user :logining="logining" @login=login @tourLogin=tourLogin></user>
          </el-tab-pane>
          <el-tab-pane label="医生登录" name="doctor">
            <doctor :logining="logining" @login=login @signin=signin></doctor>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <!-- 备案信息位置 -->
    <icp></icp>
    <!-- 注入页面element dialog位置 -->
    <el-dialog
      title="提示"
      :visible.sync="dialog"
      width="250px"
      style="margin-top: 18vh;">
      <span style="font-size: 3vh;">{{tips}}</span>
      <span slot="footer">
        <el-button type="primary" style="width: 100%;align-items: center;" @click="dialog = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import User from '@/components/login/user'
import Doctor from '@/components/login/doctor'
import Icp from '@/components/Icp'
import encrypt from '@/utils/EncryptUtils.js'

export default {
  components: {
    User,
    Doctor,
    Icp
  },
  data () {
    return {
      loginType: 'user',
      dialog: false,
      logining: false,
      tips: '',
      isMobile: window.matchMedia('(max-width: 450px)').matches
    }
  },
  mounted () {
    if (this.isMobile) {
      this.showTips('暂未适配手机网页端,推荐在电脑上登录！')
    }
  },
  methods: {
    login (userName, password, userType) {
      if (/ +/.test(password + '')) {
        this.showTips('密码不能包含空格!')
        return
      }
      userName = (userName + '').replace(' ', '')
      password = password + ''
      const pl = Math.floor(Math.random() * 10)
      if (userName === '' || password === '') {
        this.showTips('用户名或密码为空！')
      } else {
        this.logining = true
        this.axios.post('/api/log/in?_t=' + new Date().getTime(), {
          ul: userName.length,
          pl: pl,
          pw: encrypt(userType + userName + password, pl)
        })
          .then(res => {
            this.logining = false
            if (res.data.code) {
              window.localStorage.setItem('expire_time', new Date().getTime() + 7200000) // 记录登录时长为2小时
              window.localStorage.setItem('userType', 1)
              this.$router.replace('/doctor').catch(() => {})
            } else {
              this.showTips(res.data.msg)
            }
          })
          .catch(e => {
            this.logining = false
            if (/Network/.test(e)) {
              this.showTips('请检查网络后重试!')
            } else {
              this.showTips('登录失败,请重试!')
            }
          })
      }
    },
    signin () {
      this.showTips('当前暂不开放用户注册，请联系管理员！')
    },
    tourLogin () {
      this.axios.post('/api/log/in/tourist?_t=' + new Date().getTime())
        .then(res => {
          if (res.data.code) {
            window.localStorage.setItem('expire_time', new Date().getTime() + 7200000) // 记录登录时长为2小时
            window.localStorage.setItem('nickName', res.data.data.nickName)
            window.localStorage.setItem('userType', -1)
            this.$router.replace('/user').catch(() => {})
          } else {
            this.showTips(res.data.msg)
          }
        })
        .catch(e => {
          if (/Network/.test(e)) {
            this.showTips('请检查网络后重试!')
          } else {
            this.showTips('登录失败,请重试!')
          }
        })
    },
    showTips (text) {
      this.tips = text
      this.dialog = true
    }
  }
}

</script>

<style scoped>
.center{
  height: 100vh;
  display: flex;
  background-color: #e5f3ff;
  align-items: center;
  justify-content: center;
}
.bg{
  position: absolute;
  height: 100%;
  bottom: 0;
}
</style>

<style>
.login-card .el-tabs .el-tabs__item {
  line-height: 50px;
  height: 50px;
  font-size: 20px;
  width: 50%;
  border: 0 !important;
}
.login-card .el-tabs__nav {
  height: 50px;
  width: 100%;
}
.login-card .el-tabs {
  border: 0;
}
.login-card input {
  background-color: inherit;
}
.login-card .el-input i{
  color: #4a4b4d;
}
</style>
