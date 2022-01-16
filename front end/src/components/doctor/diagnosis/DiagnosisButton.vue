<!--
  * @FileDescription:医生端诊断台按钮按钮
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <router-link :to="button.to" class="dignos-button" draggable="false">
    <div :class="button.ico" :style="{color: button.color}" style="margin-top: 34px;margin-bottom: 10px;font-size: 60px;"></div>
    <div>{{ button.name }}</div>
  </router-link>
</template>

<script>
export default {
  props: {
    button: {
      default: () => {
        return {}
      },
      type: Object
    }
  }
}
</script>

<style scoped>
.dignos-button {
  height: 150px;
  width: 150px;
  text-decoration: none;
  color: #2c3e50;
  background-image: linear-gradient(278deg,#fff,#f3f5f8);
  border: 2px solid #fff;
  margin: 30px;
  border-radius: 4px;
  box-shadow: 8px 8px 20px 0 rgb(55 99 170 / 10%), -8px -8px 20px 0 #fff;
  text-align: center;
}
.dignos-button:hover {
  box-shadow: 8px 8px 20px 0 rgb(55 99 170 / 20%);
}
</style>
