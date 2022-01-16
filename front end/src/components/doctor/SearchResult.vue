<!--
  * @FileDescription:诊断记录查找结果
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <div class="result-container">
    <div style="display: flex;padding: 15px 13px;">
      <div style="text-align: left;flex: 1 0 auto;">本次搜索结果如下</div>
      <div style="text-align: right;flex: 1 0 auto;font-size: 13px;">
        <span class="reality">诊断</span>
        <span class="predict" style="margin-right: 8px;">预测</span>
      </div>
    </div>
    <el-divider></el-divider>
    <ul class="list-container">
      <li
      v-for="(item, index) in list"
      :key="index"
      :class="[{'chosen': chosen == index}]"
      class="list-item"
      @click="listChosen(index)">
        <div style="display: flex;">
          <span style="font-size: 20px;flex: 0 2 30%;text-align: left;padding: 0 10px 0 0;line-height: 25px;">{{ item.name }}</span>
          <span style="flex: 1 2 auto;"><span class="predict">{{ types[item.predict] }}</span></span>
          <span style="flex: 1 2 auto;"><span class="reality">{{ item.reality?types[item.reality]:'未知' }}</span></span>
        </div>
        <div style="text-align: right;font-size: 2px;line-height: 1.5;">{{ item.createTime }}</div>
      </li>
    </ul>
    <el-pagination
    :page-sizes="[10, 20, 30, 50]"
    :page-size="pageSize"
    :current-page="currentPage"
    :total="total" hide-on-single-page
    layout="sizes, prev, pager, next"
    class="search-result-pagination" small
    @size-change="sizeChange"
    @current-change="currentChange">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data () {
    return {}
  },
  props: {
    list: {
      default: () => {
        return []
      },
      type: Array
    },
    chosen: {
      default: 0,
      type: Number
    },
    types: {
      default: () => {
        return []
      },
      type: Array
    },
    pageSize: {
      default: 1,
      type: Number
    },
    currentPage: {
      default: 1,
      type: Number
    },
    total: {
      default: 1,
      type: Number
    }
  },
  methods: {
    listChosen (index) {
      this.$emit('chosen', index)
    },
    sizeChange (size) {
      this.$emit('sizeChange', size)
    },
    currentChange (index) {
      this.$emit('pageChange', index)
    }
  }
}
</script>

<style scoped>
.result-container {
  margin-bottom: 10px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
}
.el-divider {
  margin: 0;
}
.predict {
  border: 2px #e0a12f solid;
  border-radius: 5px;
  float: right;
}
.reality {
  border: 2px #539fff solid;
  border-radius: 5px;
  float: right;
}
.chosen {
  background-color: #eef5ff !important;
}
.list-container {
  height: calc(100% - 80px);
  overflow-y: auto;
  padding: 0;
  margin: 0;
}
.list-item {
  padding: 15px 13px 0;
  border-bottom: 1px solid #edf1f7;
  background-color: #FFF;
  list-style: none;
  line-height: 20px;
  color: #606266;
  cursor: pointer;
}
.list-item:hover {
  background-color: rgb(237, 245, 255);
}
.list-container::-webkit-scrollbar {
  width: 5px;
}
.list-container::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background-color: rgba(114, 147, 153, 0.3);
  transition: background-color 0.3s;
  -webkit-transition: background-color 0.3s;
}
.list-container::-webkit-scrollbar-track {
  border-radius: 5px;
}
</style>

<style>
.search-result-pagination .el-pagination__sizes .el-input--mini .el-input__inner {
  height: 20px;
}
.search-result-pagination .el-pagination__sizes .el-input--mini .el-input__suffix {
  height: 24px;
}
</style>
