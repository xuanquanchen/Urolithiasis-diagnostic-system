<!--
  * @FileDescription:医生端诊断主页
  * @Date:2021/10/1
  * @LastEditTime:2021/10/5
 -->
<template>
  <div style="height: 100%;width: 100%;">
    <template v-if="$route.meta.main">
      <router-view/>
    </template>
    <template v-else>
      <el-breadcrumb separator="/" style="padding-bottom: 16px;">
        <el-breadcrumb-item :to="{ path: '/doctor' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/doctor' }">诊断台</el-breadcrumb-item>
        <el-breadcrumb-item>{{ nextItem[$route.meta.index] }}</el-breadcrumb-item>
      </el-breadcrumb>
      <router-view/>
    </template>
  </div>
</template>

<script>
export default {
  data () {
    return {
      nextItem: ['单次诊断', '多次诊断', '历史诊断']
    }
  }
}
</script>

<style scoped>

</style>
