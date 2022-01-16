<!--
  * @FileDescription:版本信息
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-dialog
  top="30vh"
  :visible.sync="visible"
  title="版本信息"
  width="300px"
  :before-close="close">
    当前版本：泌尿结石诊断辅助系统 V1.0
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
  methods: {
    close () {
      this.$emit('close')
    }
  }
}
</script>
