<!--
  * @FileDescription:顶部导航栏消息
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-dropdown @command="showNotices" :show-timeout="100" :hide-timeout="300">
    <span>
      <i class="el-icon-message-solid ico"></i>
    </span>
    <el-dropdown-menu style="width: 250px;padding: 3px 0 5px;" slot="dropdown">
      <span style="padding: 0 10px;font-weight: 600;">通知</span>
      <el-button style="float: right;font-size: 0.5rem;padding: 5px;" type="text">查看所有通知</el-button>
      <el-divider class="divider"></el-divider>
      <div class="notice">
        <el-dropdown-item
        v-for="(notice, index) in notices"
        :key="index"
        :command="index"
        :divided="true"
        :class="[{'removeDivider': index === 0}]">
          {{ notice }}
        </el-dropdown-item>
      </div>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  props: {
    notices: {
      default: () => {
        return []
      },
      type: Array
    }
  },
  methods: {
    showNotices (index) {
      this.$alert(this.$props.notices[index], '通知', {
        showConfirmButton: false,
        customClass: 'menu-bar-message-box',
        callback: () => {}
      })
    }
  }
}
</script>

<style scoped>
.ico {
  margin: 15px 0 10px 0;
  font-size: 1.9rem;
  color: #606266;
}
.divider {
  position: absolute;
  top: 3px;
  box-shadow: 0 2px 3px #dadfe6;
}
.notice {
  width: 100%;
  height: 250px;
  overflow-y: auto;
}
.notice::-webkit-scrollbar {
  width: 5px;
}
.notice::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background-color: rgba(114, 147, 153, 0.3);
  transition: background-color 0.3s;
  -webkit-transition: background-color 0.3s;
}
.notice::-webkit-scrollbar-track {
  border-radius: 5px;
}
.el-dropdown-menu__item {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.removeDivider {
  border-top: none !important;
}
</style>
