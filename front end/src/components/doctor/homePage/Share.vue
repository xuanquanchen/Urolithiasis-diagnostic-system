<!--
  * @FileDescription:用户分享应用组件
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-dialog
  :visible.sync="visible"
  title="分享应用"
  width="300px"
  top="20vh"
  :before-close="close">
    <div>请保存或扫描下方二维码进行应用分享</div>
    <img src="@/assets/qrcode.png" draggable="false" style="width: 90%;"/>
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
