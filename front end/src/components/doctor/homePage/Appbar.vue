<!--
  * @FileDescription:医生端导航栏
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <div class="appbar">
    <img src="/favicon.ico" draggable="false" style="margin: 5px 14.5px;height: calc(100% - 10px);float: left;">
    <span class="col" style="float: left;display: flex;align-items: center;">
      <span></span>
      <span class="title">结石类型辅助诊断</span>
      <span class="el-divider el-divider--vertical" style="height: 30px;"></span>
      <span class="sub-title">{{ tabs[active] }}</span>
    </span>
    <span class="col appbar-right">
      <div @click="showHelp"><i class="el-icon-info ico"></i></div>
      <notice :notices="notices"></notice>
      <account :user="user" :options="options" style="padding-right: 20px;" @select="optionSelect"></account>
    </span>
  </div>
</template>

<script>
import Notice from '@/components/doctor/homePage/appbar-notes'
import Account from '@/components/doctor/homePage/appbar-account'

export default {
  components: {
    Notice,
    Account
  },
  props: {
    active: {
      type: String,
      default: '0'
    },
    user: {
      default: () => {
        return {
          name: '', circleUrl: ''
        }
      },
      type: Object
    },
    notices: {
      default: () => {
        return []
      },
      type: Array
    },
    helpText: {
      default: '',
      type: String
    }
  },
  data () {
    return {
      tabs: [
        '诊断台',
        '诊断录',
        '趋势变化'
      ],
      options: [
        { name: '更改账户信息', divider: true },
        { name: '分享应用', divider: true },
        { name: '向我反馈', divider: false },
        { name: '版本信息', divider: true },
        { name: '退出账号', divider: false }
      ]
    }
  },
  methods: {
    optionSelect (index) {
      switch (index) {
        case 0:
          this.$emit('setting')
          break
        case 1:
          this.$emit('share')
          break
        case 2:
          this.$emit('feedback')
          break
        case 3:
          this.$emit('versionShow')
          break
        case 4:
          this.$emit('logout')
          break
        default:
          break
      }
    },
    showHelp () {
      this.$alert(this.$props.helpText, '使用帮助', {
        showConfirmButton: false,
        customClass: 'menu-bar-message-box',
        callback: () => {}
      })
    }
  }
}
</script>

<style scoped>
.title {
  padding: 0 5px;
  font-size: 25px;
}
.sub-title {
  font-size: 20px;
}
@media only screen and (max-width: 768px) {
  .title {
    display: none;
  }
  .el-divider {
    display: none;
  }
}
@media only screen and (max-width: 350px) {
  .sub-title {
    display: none;
  }
}
.appbar{
  width: 100%;
  height: 100%;
  box-shadow: 0 2px 3px #dadfe6;
}
.col {
  height: 100%;
}
.ico {
  margin: 15px 0 10px 0;
  font-size: 1.9rem;
  color: #606266;
}
.appbar-right {
  float: right;
  display: flex;
}
.appbar-right div {
  padding: 0 12px;
}
.appbar-right div:hover {
  background-color: #f5f7fa;
  border-radius: 10px;
  cursor: pointer;
}
</style>
