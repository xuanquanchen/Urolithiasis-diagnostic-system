<!--
  * @FileDescription:诊断记录显示
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <div class="instance-container">
    <div style="padding: 10px 20px 0;color: #e1bd7c;">诊断结果</div>
    <div class="inside-container">
      <el-row :gutter="30" justify="space-between">
        <el-col v-for="(item, index) in defineList" :key="index" class="el-col-xs-24 el-col-sm-12 item">
          <span style="float: left;">{{ item.label }}</span>
          <span style="float: right;">{{ item.type==0?item.option[instance[item.name]]:instance[item.name] }}</span>
        </el-col>
        <el-col class="el-col-xs-24 el-col-sm-12 item">
          <span style="float: left;">诊断结果</span>
          <span style="float: right;">{{ instance.reality?types.labels[instance.reality]:'无诊断' }}</span>
        </el-col>
      </el-row>
      <div style="line-height: 2;color: #e1bd7c;">预测结果</div>
      <div style="display: flex;justify-content: space-between;line-height: 1.7;">
        <span v-for="(item, index) in types.names" :key="index">
          <el-tag type="warning" size="mini">{{ types.labels[index] }}</el-tag>
          {{ Math.floor(instance[item]*10000)/100 }}%
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  props: {
    instance: {
      default: () => {
        return {}
      },
      type: Object
    },
    defineList: {
      default: () => {
        return []
      },
      type: Array
    },
    types: {
      default: () => {
        return {}
      },
      type: Object
    }
  }
}
</script>

<style scoped>
.instance-container {
  margin-bottom: 10px;
  background-color: #f7f7e8;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
}
.inside-container {
  height: calc(100% - 65px);
  padding: 10px 20px;
  overflow-y: auto;
}
.inside-container::-webkit-scrollbar {
  width: 5px;
}
.inside-container::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background-color: rgba(114, 147, 153, 0.3);
  transition: background-color 0.3s;
  -webkit-transition: background-color 0.3s;
}
.inside-container::-webkit-scrollbar-track {
  border-radius: 5px;
}
.el-divider {
  margin: 0;
}
.item {
  line-height: 1.7;
}
</style>
