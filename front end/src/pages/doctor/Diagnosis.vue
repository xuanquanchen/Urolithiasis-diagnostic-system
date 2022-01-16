<!--
  * @FileDescription:医生端诊断主页
  * @Date:2021/10/3
  * @LastEditTime:2021/10/5
 -->
<template>
  <div class="diagnos-container">
    <diagnosis-button v-for="(item, index) in buttons" :key="index" :button="item"></diagnosis-button>
    <icp></icp>
  </div>
</template>

<script>
import Icp from '@/components/Icp'
import DiagnosisButton from '@/components/doctor/diagnosis/DiagnosisButton'

export default {
  components: {
    Icp,
    DiagnosisButton
  },
  data () {
    return {
      buttons: [
        { name: '单次诊断', ico: 'el-icon-circle-plus-outline', color: '#99cf7e', to: '/doctor/diagnos/single' },
        { name: '多次诊断', ico: 'el-icon-zoom-in', color: '#94c5ff', to: '/doctor/diagnos/multi' },
        { name: '历史诊断', ico: 'el-icon-time', color: '#fbc36f', to: '/doctor/diagnos/history' }
      ]
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
.diagnos-container {
  position: relative;
  height: auto;
  width: 100%;
  padding: 0;
  padding-bottom: 30px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
@media only screen and (min-width: 550px) {
  .diagnos-container {
    height: calc(100% - 30px);
    align-content: center;
  }
}
</style>
