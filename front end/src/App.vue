<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style>
body, html {
  overflow: hidden;
  margin: 0;
  padding: 0;
  user-select: none;
  font-family: 'SourceHanSansCN', 'Avenir', Helvetica, Arial, sans-serif;
}
p {
  white-space: pre-line;
}
#app {
  height: 100vh;
  width: 100vw;
  font-weight: 600;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
