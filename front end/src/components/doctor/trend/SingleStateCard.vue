<template>
  <el-card style="margin-bottom: 20px;">
    <div slot="header" style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">{{ name }}增量</div>
    <el-progress :color="percentage>=0?'':'#e0a12f'" :width="100" type="circle" :percentage="Math.abs(percentage)" :format="setType"></el-progress>
    <div>较昨日</div>
  </el-card>
</template>

<script>
export default {
  props: {
    name: {
      default: '',
      type: String
    },
    percentage: {
      default: 0,
      type: Number
    }
  },
  data () {
    return {}
  },
  methods: {
    setType (p) {
      if (this.$props.percentage >= 0) {
        return (p + '% ↑')
      } else {
        return (p + '% ↓')
      }
    }
  }
}
</script>

<style scoped>

</style>
