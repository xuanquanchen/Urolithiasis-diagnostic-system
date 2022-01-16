<!--
  * @FileDescription:用户提交反馈组件
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-dialog
  top="25vh"
  :visible.sync="visible"
  title="反馈"
  width="300px"
  :before-close="close"
  :close-on-click-modal="false">
    <el-input v-model="feedbackText" :rows="4" type="textarea" placeholder="请输入反馈信息"></el-input>
    <div style="height: 20px;"></div>
    <el-button @click="close">取消</el-button>
    <el-button type="primary" @click="submit">提交</el-button>
  </el-dialog>
</template>

<script>
export default {
  props: {
    visible: {
      default: false,
      type: Boolean
    }
  },
  data () {
    return {
      feedbackText: ''
    }
  },
  methods: {
    close () {
      this.$emit('close')
    },
    submit () {}
  }
}
</script>
