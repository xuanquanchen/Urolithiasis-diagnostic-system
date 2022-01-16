<!--
  * @FileDescription:医生端单次诊断页
  * @Date:2021/10/3
  * @LastEditTime:2021/10/5
 -->
<template>
  <basic-form
  :loading="loading" :list="list"
  :values="values" style="animation: flowin .7s;"
  @change="valueChange" @submit="submit"></basic-form>
</template>

<script>
import BasicForm from '@/components/doctor/diagnosis/BasicForm'

export default {
  components: {
    BasicForm
  },
  data () {
    return {
      loading: true,
      list: [],
      values: []
    }
  },
  methods: {
    submit () {
      this.values[0] = this.values[0].replaceAll(' ', '')
      if (this.values[0] === '') {
        this.$message.error(this.list[0].label + '不允许为空或只含空格！')
        return
      }
      var data = {}
      this.list.forEach((item, index) => {
        data[item.name] = this.values[index]
      })
      this.axios.post('/api/diagnosis/recommand?_t=' + new Date().getTime(), data)
        .then(res => {
          if (res.data.code) {
            this.$router.push('/doctor/diagnos/history')
          }
        })
        .catch(e => {
          if (/Network/.test(e)) {
            this.$message.error('请检查网络后重试!')
          } else if (/code [401|301]/.test(e)) {
            this.$message.error('用户需要登录！')
          } else {
            this.$message.error('出错了，请重试！')
          }
        })
    },
    getList () {
      this.loading = true
      /** 获取结石填写列表定义 */
      this.axios.get('/list.json?_t=' + new Date().getTime())
        .then(res => {
          this.loading = false
          this.list = res.data.list
          /** 读入上一次填写数据 */
          this.values = new Array(this.list.length).fill(0)
          this.values[0] = '' // name初始化为''
          this.values[2] = 1 // age初始化为1
          window.localStorage.setItem('typeOfStone', JSON.stringify(res.data.typeOfStone)) // 记录下typeOfStone，方便display页面调用
        })
        .catch(e => {
          if (/Network/.test(e)) {
            this.$message.error('请检查网络后刷新!')
          }
        })
    },
    valueChange (index, value) {
      this.values[index] = value
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      setTimeout(() => {
        vm.getList()
      }, 650)
    })
  }
}
</script>

<style>
@keyframes flowin {
  0% {
    transform: scale(0.2,0.2) translate(-500%,-500%);
  }
  70% {
    transform: scale(0.7,0.7) translate(0,0);
  }
  100% {
    transform: scale(1,1) translate(0,0);
  }
}
</style>
