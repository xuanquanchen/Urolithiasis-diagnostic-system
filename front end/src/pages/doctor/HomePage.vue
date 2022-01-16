<!--
  * @FileDescription:医生端主页
  * @Date:2021/10/3
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-container style="height: 100%;">
    <el-header style="padding: 0;">
      <appbar :active="active" :user="user" :helpText="helpText"
      :notices="notices" @share="share = true"
      @feedback="feedback = true" @versionShow="versionInfo = true"
      @setting="setting = true" @logout="logout"></appbar>
    </el-header>
    <el-container>
      <el-aside width="80px">
        <tabs @select='tabSelect' :active="active"></tabs>
      </el-aside>
      <el-main class="main">
        <keep-alive>
          <router-view/>
        </keep-alive>
      </el-main>
    </el-container>
    <account-setting :visible="setting" @close="setting = false"/>
    <share :visible="share" @close="share = false"/>
    <feedback :visible="feedback" @close="feedback = false"/>
    <version-info :visible="versionInfo" @close="versionInfo = false"></version-info>
    <el-dialog
    top="30vh"
    :visible.sync="logoutDialog"
    title="提示"
    width="250px">
      <span style="font-size: 20px;">真的要退出登录吗?</span>
      <span slot="footer">
        <el-button style="width: 46%;float: left;" plain @click="logoutDialog = false">取 消</el-button>
        <el-button style="width: 46%;" type="danger" plain @click="logoutComfirm">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
import Tabs from '@/components/doctor/homePage/Tabs'
import Appbar from '@/components/doctor/homePage/Appbar'
import Share from '@/components/doctor/homePage/Share'
import Feedback from '@/components/doctor/homePage/Feedback'
import VersionInfo from '@/components/doctor/homePage/VersionInfo'
import AccountSetting from '@/components/doctor/homePage/AccountSetting'

export default {
  components: {
    Tabs,
    Appbar,
    Feedback,
    Share,
    VersionInfo,
    AccountSetting
  },
  data () {
    return {
      active: '0',
      user: { name: '暨大测试员uuui', vip: false, circleUrl: '' },
      notices: [
        '欢迎您使用泌尿结石诊断辅助系统，在使用前请您先阅读使用帮助！\n有什么需求及问题欢迎向我们反馈！',
        '版本Beta 1.0正式上线！'
      ],
      helpText: '欢迎使用泌尿结石诊断辅助系统\n' +
      '泌尿结石诊断辅助系统管理/诊断端包括用户登录、单次诊断、多次诊断、查看历史诊断、查看所有用户诊断记录、查看近期结石类型趋势变化、查看使用帮助、查看通知、更改账户信息、分享应用、发起反馈、查看版本信息功能。',
      logoutDialog: false,
      setting: false,
      share: false,
      feedback: false,
      versionInfo: false
    }
  },
  methods: {
    logout () {
      this.logoutDialog = true
    },
    logoutComfirm () {
      this.logoutDialog = false
      this.axios.get('/api/log/out?_t' + new Date().getTime())
        .catch(e => {
          if (/network/.test(e)) {
            this.$message.error('网络错误!')
          } else if (/code [401|301]/.test(e)) {
            window.localStorage.removeItem('expire_time')
            this.$router.replace('/login')
          }
        })
    },
    tabSelect (key) {
      this.active = key
    }
  },
  beforeRouteEnter (to, from, next) {
    if (to.name === 'doctorRecord') {
      next(vm => {
        vm.active = '1'
      })
    } else if (to.name === 'doctorTrend') {
      next(vm => {
        vm.active = '2'
      })
    } else {
      next()
    }
  }
}
</script>

<style scoped>
.main {
  background-color: #f3f5f8;
  overflow-x: hidden;
  height: calc(100vh - 60px);
  padding-bottom: 5px;
}
.main::-webkit-scrollbar {
  width: 8px;
}
.main::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background-color: rgba(114, 147, 153, 0.3);
  transition: background-color 0.3s;
  -webkit-transition: background-color 0.3s;
}
.main::-webkit-scrollbar-track {
  border-radius: 5px;
}
</style>
