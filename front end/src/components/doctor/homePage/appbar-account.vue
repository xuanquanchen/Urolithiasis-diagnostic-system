<!--
  * @FileDescription:顶部导航栏账户设置
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-dropdown @command="select" :show-timeout="100" :hide-timeout="300">
    <span>
      <el-avatar
      :size="40"
      :src="user.circleUrl"
      fit="contain"
      placement="bottom"
      style="margin: 10px 5px 0 0;">
        {{ user.name[0]+user.name[1] }}
      </el-avatar>
      <i class="el-icon-arrow-down ico"></i>
    </span>
    <el-dropdown-menu style="width: 210px;" slot="dropdown">
      <div style="display: flex;align-items: center;margin: 2px 0 8px 17px;">
        <el-avatar
        :size="45"
        :src="user.circleUrl"
        fit="contain"
        placement="bottom">
          {{ user.name[0]+user.name[1] }}
        </el-avatar>
        <span style="font-size: 18px;
        margin-left: 8px;margin-right: 6px;margin-right: 6px;
        overflow: hidden;width: 80px;text-overflow: ellipsis;white-space: nowrap;">
          {{ user.name }}
        </span>
        <span class="userType">医生</span>
      </div>
      <div v-for="(option, index) in options" :key="index">
        <el-dropdown-item :command="index" :class="{'accountInfo': index === 0, 'exitInfo': index === options.length-1 }">{{ option.name }}</el-dropdown-item>
        <el-divider v-if="option.divider" class="divider"></el-divider>
      </div>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  props: {
    user: {
      default: () => {
        return {
          name: '',
          circleUrl: ''
        }
      },
      type: Object
    },
    options: {
      default: () => {
        return []
      },
      type: Array
    }
  },
  methods: {
    select (index) {
      this.$emit('select', index)
    }
  }
}
</script>

<style scoped>
.ico {
  position: absolute;
  top: 25px;
}
.userType {
  color: white;
  border-radius: 2px;
  background-color: rgba(133, 178, 255, 0.834);
  padding: 0 5px;
  font-size: 12px;
  line-height: 20px;
}
.divider {
  margin: 0;
}
.accountInfo {
  font-weight: 600;
}
.exitInfo {
  color: rgba(243, 128, 128, 0.834);
}
</style>
