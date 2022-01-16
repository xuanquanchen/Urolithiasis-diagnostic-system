<template>
  <div style="margin: 20vh 0 0;" class="menu-container">
    <div ref="container" style="cursor: pointer;">
      <div class="button-container">
        <menu-bar-doctor-idea></menu-bar-doctor-idea>
        <menu-bar-history :types="typeOfStone"></menu-bar-history>
        <menu-bar-account></menu-bar-account>
      </div>
    </div>
  </div>
</template>

<script>
import MenuBarHistory from '@/components/user/start/MenuBarHistory'
import MenuBarAccount from '@/components/user/start/MenuBarAccount'
import MenuBarDoctorIdea from '@/components/user/start/MenuBarDoctorIdea'

export default {
  components: {
    MenuBarAccount,
    MenuBarHistory,
    MenuBarDoctorIdea
  },
  data () {
    return {
      doctorIdea: true
    }
  },
  props: {
    typeOfStone: {
      default: () => {
        return []
      },
      type: Array
    }
  },
  mounted () {
    const docStyle = document.documentElement.style
    const container = this.$refs.container
    const boundingClientRect = container.getBoundingClientRect()
    container.onmousemove = e => {
      const x = e.clientX - boundingClientRect.left
      const y = e.clientY - boundingClientRect.top
      const xc = boundingClientRect.width / 2
      const yc = boundingClientRect.height / 2
      const dx = x - xc
      const dy = y - yc
      docStyle.setProperty('--rx', `${dy / -1}deg`)
      docStyle.setProperty('--ry', `${dx / 10}deg`)
    }
    container.onmouseleave = e => {
      docStyle.setProperty('--ty', '0')
      docStyle.setProperty('--rx', '0')
      docStyle.setProperty('--ry', '0')
    }
    container.onmousedown = e => {
      docStyle.setProperty('--tz', '-20px')
    }
    document.body.onmouseup = e => {
      docStyle.setProperty('--tz', '-12px')
    }
  }
}
</script>

<style scoped>
.menu-container {
  width: 100%;
  display: flex;
  justify-content: center;
}
.button-container {
  padding: 5px 10px;
  border-radius: 35px;
  background-color: rgba(255, 255, 255, .2);
  backdrop-filter: blur(30px);
  transform: translateY(var(--ty, 0)) rotateX(var(--rx, 0)) rotateY(var(--ry, 0)) translateZ(var(--tz, -12px));
}
</style>

<style>
.menu-bar-message-box {
  width: 50vw;
}
.menubar-popover {
  padding: 0;
  height: 300px;
  width: 300px;
}
.menubar-popover__account {
  padding: 0;
  height: 250px;
  width: 230px;
}
</style>
