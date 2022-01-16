<!--
  * @FileDescription:医生端表格
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->

<template>
  <div class="inside-container" :style="`${loading?'height: calc(100% - 60px);':''}`">
    <!-- banner -->
    <div class="title">请在下表填入相关信息：</div>
    <!-- 特征填写处 -->
    <div class="option-container"
    :style="`${loading?'height: calc(90% - 40px);align-items: center;justify-content: center;':''}`">
      <!-- 加载时的背景 -->
      <div v-show="loading">
        <img draggable="false" src="@/assets/loading.svg" style="height: 30vh;">
        <div style="text-align: center;padding-right: 4vh;color: rgb(244, 221, 170);">加载中...</div>
      </div>
      <!-- 载入选择器 -->
      <selector
      v-for="(item,index) in list"
      :key="index" :name="item.label"
      :index="index" :option="item.option"
      :type="item.type" :weight="item.weight"
      :value="values[index]" @valueChange="valueChange">
      </selector>
    </div>
    <div v-show="!loading">
      <el-button style="margin: 20px 10px 0;" type="warning" round @click="submit">开始预测</el-button>
      <div class="footer">说明：该结果目前仅供参考！</div>
    </div>
  </div>
</template>

<script>
import Selector from '@/components/Selector'

export default {
  components: {
    Selector
  },
  props: {
    loading: {
      default: true,
      type: Boolean
    },
    values: {
      default: () => {
        return []
      },
      type: Array
    },
    list: {
      default: () => {
        return []
      },
      type: Array
    },
    index: {
      default: 0,
      type: Number
    }
  },
  methods: {
    submit () {
      this.$emit('submit')
    },
    valueChange (index, value) {
      this.$emit('change', index, value, this.$props.index)
    }
  }
}
</script>

<style scoped>
.inside-container {
  position: relative;
  z-index: 200;
  display: inline-block;
  width: calc(100% - 30px);
  margin-bottom: 20px;
  padding: 10px 15px 0;
  border-radius: 10px;
  background-color: #f7f7e8;
  box-shadow: 0 2px 20px 2px rgb(0, 0, 0, 0.1);
}
.title {
  color: #e1bd7c;
  font-size: 17px;
  text-align: left;
  line-height: 1.8;
}
.footer {
  color: #e1bd7c;
  font-size: 10px;
  text-align: left;
  line-height: 1.8;
  padding-bottom: 1vh;
  text-align: right;
}
.option-container {
  height: 100%;
  width: 100%;
  text-align: left;
  font-size: 15px;
  display: flex;
  flex-wrap: wrap;
}
</style>
