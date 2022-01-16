<template>
  <el-popover
  trigger="click"
  popper-class="menubar-popover__account">
    <div style="font-size: 20px;padding: 10px 15px;text-align: center;">账户信息</div>
    <el-divider></el-divider>
    <div style="display: flex;justify-content: center;">
      <div>
        <div style="display: flex;justify-content: center;">
          <el-avatar
          :size="60"
          :src="user.circleUrl"
          fit="contain"
          placement="bottom"
          style="margin-top: 30px;font-size: 20px;">
            {{ user.name[0]+user.name[1] }}
          </el-avatar>
        </div>
        <div style="margin: 10px 0 20px;font-size: 15px;text-align: center;">{{ user.name }}</div>
        <el-button-group>
          <el-button size="medium" plain @click="changeAccount">修改信息</el-button>
          <el-button size="medium" type="danger" @click="logout">退出登录</el-button>
        </el-button-group>
      </div>
    </div>
    <a class="menu-bar-button" slot="reference">我的</a>
  </el-popover>
</template>

<script>
export default {
  data () {
    return {
      user: { circleUrl: '', name: window.localStorage.getItem('nickName') }
    }
  },
  methods: {
    changeAccount () {
      this.$message.error('游客不允许修改信息。')
    },
    logout () {
      this.axios.get('/api/log/out?_t' + new Date().getTime())
        .catch(e => {
          if (/network/.test(e)) {
            this.$message.error('网络错误!')
          } else if (/code [401|301]/.test(e)) {
            window.localStorage.removeItem('expire_time')
            this.$router.replace('/login')
          }
        })
    }
  }
}
</script>

<style scoped>
.el-divider {
  margin: 0;
}
</style>
