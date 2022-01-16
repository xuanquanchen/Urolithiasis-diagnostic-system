<template>
  <el-popover
  trigger="click"
  popper-class="menubar-popover">
    <div style="display: flex;padding: 10px 15px;">
      <div style="font-size: 20px;flex: 1 0 auto;">历史记录</div>
      <div style="text-align: right;flex: 1 0 auto;font-size: 13px;line-height: 1.6;">
        <span class="reality">诊断</span>
        <span class="predict" style="margin-right: 8px;">预测</span>
      </div>
    </div>
    <el-divider></el-divider>
    <ul class="list-container">
      <li
      v-for="(item, index) in history"
      :key="index"
      class="list-item"
      @click="showRecord(index)">
        <div style="display: flex;">
          <span style="font-size: 20px;flex: 0 2 30%;text-align: left;padding: 0 10px 0 0;line-height: 25px;">{{ item.form.name }}</span>
          <span style="flex: 1 2 auto;"><span class="predict">{{ types[item.form.predict] }}</span></span>
          <span style="flex: 1 2 auto;"><span class="reality">{{ item.form.reality?types[item.form.reality]:'未知' }}</span></span>
        </div>
        <div style="text-align: right;font-size: 2px;line-height: 1.5;">{{ item.form.createTime }}</div>
      </li>
      <el-empty v-if="history.length==0" description="还没有诊断历史" style="height: 100%;"></el-empty>
    </ul>
    <a class="menu-bar-button" slot="reference">历史</a>
  </el-popover>
</template>

<script>
export default {
  data () {
    return {
      history: window.localStorage.getItem('touristHistory') ? JSON.parse(window.localStorage.getItem('touristHistory')) : []
    }
  },
  props: {
    types: {
      default: () => {
        return []
      },
      type: Array
    }
  },
  methods: {
    showRecord (index) {
      window.localStorage.setItem('result', JSON.stringify(this.history[index].predict))
      this.$router.push({ name: 'userResult' })
    }
  }
}
</script>

<style scoped>
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
  height: calc(100% - 50px);
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
.el-divider {
  margin: 0;
}
</style>
