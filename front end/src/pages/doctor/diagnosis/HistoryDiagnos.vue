<!--
  * @FileDescription:医生端诊断历史页
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <div>
    <el-row :gutter="20" style="margin-bottom: 13px;">
      <el-col :sm="10">
        <search
        :expand="searchExpand"
        placeholder="姓名"
        @expandList="expand"
        @searching="search">
        </search>
        <search-result :pageSize="pageSize" :currentPage="currentPage"
        :total="total" :list="list" :chosen="chosen" :types="types.labels"
        :style="`height: ${ searchExpand?'350px':'410px' };`" @sizeChange="pageSizeChange"
        @pageChange="currentPageChange" @chosen="listChosen">
        </search-result>
      </el-col>
      <el-col :sm="14">
        <search-result-view :instance="list[chosen]" :defineList="defineList" :types="types" style="height: 502px;"></search-result-view>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Search from '@/components/doctor/Search'
import SearchResult from '@/components/doctor/SearchResult'
import SearchResultView from '@/components/doctor/SearchResultView.vue'

export default {
  components: {
    Search,
    SearchResult,
    SearchResultView
  },
  data () {
    return {
      searchName: '',
      searchTimes: null,
      searchExpand: false,
      list: [],
      types: JSON.parse(window.localStorage.getItem('typeOfStone')),
      chosen: 0,
      defineList: [],
      currentPage: 1,
      pageSize: 10,
      total: 10
    }
  },
  methods: {
    search (name, times, page) {
      if (!page) {
        this.pageSize = 10
        this.currentPage = 1
      }
      var data = {}
      this.searchName = name
      this.searchTimes = times
      if (name && name !== '') {
        data.name = name
      }
      if (times) {
        data.beginTime = times[0]
        data.endTime = times[1]
      }
      data.currentPage = this.currentPage
      data.pageSize = this.pageSize
      this.axios.post('/api/record/search/doctor?_t' + new Date().getTime(), data)
        .then(res => {
          this.list = res.data.data.result
          this.total = res.data.data.total
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
      /** 获取结石填写列表定义 */
      this.axios.get('/list.json?_t=' + new Date().getTime())
        .then(res => {
          this.defineList = res.data.list
          window.localStorage.setItem('typeOfStone', JSON.stringify(res.data.typeOfStone)) // 记录下typeOfStone，方便display页面调用
        })
        .catch(e => {
          if (/Network/.test(e)) {
            this.$message.error('请检查网络后刷新!')
          }
        })
    },
    listChosen (index) {
      this.chosen = index
    },
    expand (status) {
      if (status) {
        this.searchExpand = !this.searchExpand
      }
    },
    pageSizeChange (size) {
      this.pageSize = size
      this.search(this.searchName, this.searchTimes, true)
    },
    currentPageChange (index) {
      this.currentPage = index
      this.search(this.searchName, this.searchTimes, true)
    }
  },
  beforeRouteEnter (from, to, next) {
    next(vm => {
      vm.getList()
      vm.search()
    })
  }
}
</script>

<style scoped>

</style>
