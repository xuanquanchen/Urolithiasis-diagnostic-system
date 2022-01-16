<template>
  <div>
    <div style="display: flex;height: 100vh;align-items: center;justify-content: center;padding: 0 5vw;">
      <div class="content">
        <div>欢迎进入泌尿结石诊断辅助系统!</div>
        <div>在这里，你将体验到科技为疾病诊断带来的轻松体验！</div>
        <el-button style="font-size: 25px;border-radius: 15px;" type="primary" @click="start">发起诊断</el-button>
        <menu-bar :typeOfStone="typeOfStone"></menu-bar>
      </div>
      <!-- 背景图片 -->
      <img :style="isMobile?'width: 100%;':''" src="@/assets/menu_banner.svg"
      style="width: 50%;position: fixed;z-index: -1000;bottom: 0;opacity: 0.5;">
    </div>
  </div>
</template>

<script>
import MenuBar from '@/components/user/start/MenuBar'

export default {
  components: {
    MenuBar
  },
  data () {
    return {
      isMobile: window.matchMedia('(max-width: 450px)').matches,
      typeOfStone: []
    }
  },
  methods: {
    start () {
      this.$router.replace('/user/diagnos')
    },
    getList () {
      /** 获取结石填写列表定义 */
      this.axios.get('/list.json?_t=' + new Date().getTime())
        .then(res => {
          this.typeOfStone = res.data.typeOfStone.labels
          window.localStorage.setItem('typeOfStone', JSON.stringify(res.data.typeOfStone)) // 记录下typeOfStone，方便display页面调用
        })
        .catch(e => {
          if (/Network/.test(e)) {
            this.$message.error('请检查网络后刷新!')
          }
        })
    }
  },
  beforeRouteEnter (from, to, next) {
    next(vm => {
      vm.getList()
    })
  }
}
</script>

<style scoped>
.content>div {
  margin: 20px;
  font-size: 20px;
}
.content>button {
  margin: 5px;
  padding: 13px 18px;
}
.content {
  margin-top: 30vh;
}
</style>
