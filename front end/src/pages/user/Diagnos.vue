<template>
  <div class="container" style="overflow-y: auto;">
    <!-- 二级容器，突出内部 -->
    <div class="inside-container" :style="[{height: loading?'calc(100% - 40px)':''}]">
      <!-- banner -->
      <div class="title">为了提高预测准确性和灵敏度，请如实填写以下信息：</div>
      <div class="footer">说明：该结果目前仅供参考！</div>
      <!-- 特征填写处 -->
      <div class="option-container" :style="[{height: loading?'calc(90% - 40px)':'','align-items': loading?'center':'', 'justify-content': loading?'center':''}]">
        <!-- 加载时的背景 -->
        <div v-if="loading">
          <img draggable="false" src="@/assets/loading.svg" style="height: 30vh;">
          <div style="text-align: center;padding-right: 4vh;color: rgb(244, 221, 170);">加载中...</div>
        </div>
        <!-- 加载 -->
        <selector
        v-for="(item,index) in list"
        :key="index" :name="item.label"
        :list="item.value"
        :weight="item.weight"
        :index="index"
        :option="item.option"
        :value="values[index]"
        :type="item.type"
        @valueChange="valueChange">
        </selector>
      </div>
      <el-button v-if="!loading" style="margin: 10px;" type="warning" @click="submit" round>开始预测</el-button>
    </div>
  </div>
</template>

<script>
import Selector from '@/components/Selector'

export default {
  data () {
    return {
      list: [],
      values: [],
      loading: true
    }
  },
  components: {
    Selector
  },
  methods: {
    submit () {
      this.values[0] = this.values[0].replaceAll(' ', '')
      if (this.values[0] === '') {
        this.$message.error(this.list[0].label + '不允许为空或只含空格！')
        return
      }
      window.localStorage.setItem('userLastForm', JSON.stringify(this.values))
      var data = {}
      this.list.forEach((item, index) => {
        data[item.name] = this.values[index]
      })
      this.axios.post('/api/diagnosis/recommand/tourist?_t=' + new Date().getTime(), data)
        .then(res => {
          if (res.data.code) {
            window.localStorage.setItem('result', JSON.stringify(res.data.data.result))
            this.record(data, res.data.data.result)
            this.$router.push({ name: 'userResult' })
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch(e => {
          console.log(e)
          if (/Network/.test(e)) {
            this.$message.error('网络错误！')
          } else if (/code [401|301]/.test(e)) {
            this.$message.error('用户需要登录！')
          } else {
            this.$message.error('出错了，请重试！')
          }
        })
    },
    record (data, result) {
      var history = window.localStorage.getItem('touristHistory') === null ? [] : JSON.parse(window.localStorage.getItem('touristHistory'))
      if (history[0] !== undefined && history[0].predict[0].name !== undefined) {
        history = []
      }
      data.createTime = this.dateFormat()
      data.typeA = result[0]
      data.typeB = result[1]
      data.typeC = result[2]
      data.typeD = result[3]
      var max = 0 // 假定0为最大，index从1开始比较
      for (let i = 1; i < result.length; i++) {
        if (result[i] > result[max]) {
          max = i
        }
      }
      data.predict = max
      data.reality = null
      history.push({ predict: result, form: data })
      window.localStorage.setItem('touristHistory', JSON.stringify(history))
    },
    valueChange (index, value) {
      this.values[index] = value
    },
    getList () {
      this.loading = true
      /** 获取结石填写列表定义 */
      this.axios.get('/list.json?_t=' + new Date().getTime())
        .then(res => {
          this.loading = false
          this.list = res.data.list
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
    dateFormat () {
      let month = new Date().getMonth() + '-'
      let date = new Date().getDate() + ' '
      let hour = new Date().getHours() + ':'
      let minute = new Date().getMinutes() + ':'
      let second = new Date().getSeconds()
      month = month.length === 3 ? month : 0 + month
      date = date.length === 3 ? date : 0 + date
      hour = hour.length === 3 ? hour : 0 + hour
      minute = minute.length === 3 ? minute : 0 + minute
      second = second.length === 2 ? second : 0 + second
      return new Date().getFullYear() + '-' + month + date + hour + minute + second
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.getList()
    })
  }
}
</script>

<style scoped>
.inside-container {
  box-shadow: 0 2px 20px 2px rgb(0, 0, 0, 0.1);
  background-color: #f7f7e8;
  position: relative;
  padding: 2vh 3vw 4vh 3vw;
}
.title {
  color: #e1bd7c;
  font-size: 17px;
  text-align: left;
  line-height: 1.8;
}
.footer {
  position: absolute;
  bottom: 0;
  right: 0;
  color: #e1bd7c;
  font-size: 10px;
  text-align: left;
  line-height: 1.8;
  padding: 1vh 1.5vw;
}
.option-container {
  height: 100%;
  width: 100%;
  text-align: left;
  font-size: 15px;
  display: flex;
  flex-wrap: wrap;
}
.container {
  height: calc(100vh - 40px);
  width: calc(100vw - 50px);
  padding: 20px 25px;
}
.container::-webkit-scrollbar {
  width: 6px;
}
.container::-webkit-scrollbar-thumb {
  border-radius: 5px;
  width: 6px;
  background: #b4bccc;
}
.container::-webkit-sscrollbar-corner,.container::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.01);
}
.container::-webkit-scrollbar-track-piece {
  background: rgba(255, 255, 255, 0.01);
  width: 6px;
}
</style>
