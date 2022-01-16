<!--
  * @FileDescription:医生端左侧导航栏
  * @Date:2021/10/3
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-menu
  :default-active="active"
  background-color="rgb(255, 255, 255)"
  text-color="#25262b"
  active-text-color="rgb(46, 62, 81)"
  style="height: 100%;"
  @select="tabSelect">
    <el-menu-item
    v-for="(tab, index) in tabs"
    :key="index"
    :index="index.toString()">
      <el-tooltip
      :open-delay="500"
      :hide-after="1500"
      :content="tab.name"
      placement="right">
        <router-link :to="{path: '/doctor'+tab.path}" replace>
          <div><i :class="tab.ico"></i></div>
        </router-link>
      </el-tooltip>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  props: {
    active: {
      type: String,
      default: '0'
    }
  },
  data () {
    return {
      tabs: [
        { name: '诊断台', ico: 'el-icon-s-operation', path: '' },
        { name: '诊断录', ico: 'el-icon-tickets', path: '/record' },
        { name: '趋势变化', ico: 'el-icon-data-analysis', path: '/tendency' }
      ]
    }
  },
  methods: {
    tabSelect (key) {
      if (key !== this.$props.active) {
        this.$emit('select', key)
      }
    }
  }
}
</script>

<style scoped>
.el-menu-item {
  padding: 0 !important;
  margin: 10px;
  border-radius: 10px;
}
.el-menu-item.is-active {
  background-color: rgb(236, 236, 238) !important;
}
.el-menu-item i {
  font-size: 28px;
  color: rgb(144, 147, 153);
}
.el-menu-item.is-active i {
  color: rgb(48, 62, 82) !important;
}
</style>
