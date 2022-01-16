<!-- 用户端注入的路由 -->
<template>
  <div>
    <template v-if="!$route.meta.main">
      <router-link to="/user" replace class="menu-bar-button"
      style="position: fixed;right: 25px;top: 20px;z-index: 300;line-height: 38px;width: 40px;padding-top: 2px;">
        <i class="el-icon-s-home" style="font-size: 30px;"></i>
      </router-link>
    </template>
    <router-view/>
  </div>
</template>

<style>
.menu-bar-button {
  width: 60px;
  line-height: 60px;
  margin: 5px 10px;
  color: #2194E0;
  border: 2px solid #2194E0;
  border-radius: 50%;
  font-size: 24px;
  cursor: pointer;
  display: inline-block;
  transition: all 0.2s ease-in-out;
  position: relative;
  overflow: hidden;
  text-align: center;
  text-decoration: none;
}
.menu-bar-button::before {
  content: "";
  background-color: rgba(255,255,255,0.5);
  height: 100%;
  width: 3em;
  display: block;
  position: absolute;
  top: 0;
  left: -4.5em;
  transform: skewX(-45deg) translateX(0);
  transition: none;
}
.menu-bar-button:hover {
  background-color: #2194E0;
  color: #fff;
  border-bottom: 4px solid darken(#2194E0, 10%);
}
.menu-bar-button:hover:before {
  transform: skewX(-45deg) translateX(13.5em);
  transition: all 0.5s ease-in-out;
}
</style>
