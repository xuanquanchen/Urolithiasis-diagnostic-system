<!--
  * @FileDescription:医生端多次诊断页
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <div :style="`${loading?'height: 100%;':''}`" style="width: 100%;display: inline;">
    <div v-show="!loading" class="buttonArea">
      <div class="el-icon-circle-plus" style="color: #72c22d;padding-right: 5px;" @click="addPage"></div>
      <div class="el-icon-remove" style="color: #ec6b6a;" @click="removePage"></div>
    </div>
    <basic-form v-for="(item, index) in values" :key="item.id" v-show="index==currentPage-1"
    :loading="loading" :list="list" :values="item.value" :index="index"
    style="animation: flowfromleft .5s;" @change="valueChange" @submit="submit">
    </basic-form>
    <div v-show="!loading">
      <div class="hoverArea"></div>
      <div :style="`${paginationShow?'bottom: 0;':''}`" class="pagination">
        <el-pagination
          :current-page.sync="currentPage"
          :total="values.length*10"
          layout="prev, pager, next, jumper"
          background>
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import BasicForm from '@/components/doctor/diagnosis/BasicForm'

export default {
  components: {
    BasicForm
  },
  data () {
    return {
      currentPage: 1,
      loading: true,
      paginationShow: false,
      list: [],
      values: [{ value: [], id: 0 }] // id为标志位
    }
  },
  methods: {
    submit () {
      var dataList = []
      for (let i = 0; i < this.values.length; i++) {
        this.values[i].value[0] = this.values[i].value[0].replaceAll(' ', '')
        if (this.values[i].value[0] === '') {
          this.$message.error(this.list[0].label + '不允许为空或包含空格！')
          this.currentPage = i + 1
          return
        }
        const data = {}
        this.values[i].value.forEach((it, ind) => {
          data[this.list[ind].name] = it
        })
        dataList.push(data)
      }
      this.axios.post('/api/diagnosis/recommand/multi?_t=' + new Date().getTime(), dataList)
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
          this.values = [{ value: new Array(this.list.length).fill(0), id: new Date().getTime() }]
          this.values[0].value[0] = '' // name初始化为''
          this.values[0].value[2] = 1 // age初始化为1
          window.localStorage.setItem('typeOfStone', JSON.stringify(res.data.typeOfStone)) // 记录下typeOfStone，方便display页面调用
          this.showPagination()
        })
        .catch(e => {
          if (/Network/.test(e)) {
            this.$message.error('请检查网络后刷新!')
          }
        })
    },
    valueChange (index, value, pageIndex) {
      this.values[pageIndex].value[index] = value
    },
    addPage () {
      const newValues = new Array(this.list.length).fill(0)
      newValues[0] = '' // name初始化为''
      newValues[2] = 1 // age初始化为1
      this.values.push({ value: newValues, id: new Date().getTime() })
      this.currentPage = this.values.length
    },
    removePage () {
      if (this.values.length === 1) {
        this.$message.error('删除失败，当前只有一个表单！')
      } else {
        this.values.splice(this.currentPage - 1, 1)
      }
    },
    showPagination () {
      this.paginationShow = true
      setTimeout(() => {
        this.paginationShow = false
      }, 2000)
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

<style scoped>
.buttonArea {
  position: absolute;
  right: 0;
  transform: translate(-25px,-40px);
}
.buttonArea div {
  font-size: 35px;
  cursor: pointer;
}
.pagination {
  position: fixed;
  bottom: -52px;
  padding: 10px 0;
  width: calc(100% - 125px);
  background-color: #0000001a;
  border-radius: 10px 10px 0 0;
  transition: bottom 0.5s ease-in-out;
  z-index: 200;
}
.hoverArea {
  position: fixed;
  bottom: 0;
  right: 0;
  width: calc(100% - 80px);
  height: 50px;
  z-index: 200;
}
.hoverArea:hover + .pagination {
  bottom: 0;
}
.pagination:hover {
  bottom: 0;
}
</style>

<style>
@keyframes flowfromleft {
  0% {
    transform: translate(-100%,0);
  }
  100% {
    transform: translate(0,0);
  }
}
</style>
