<template>
  <el-popover
  trigger="click"
  popper-class="menubar-popover">
    <div style="font-size: 20px;padding: 10px 15px;">医生建议</div>
    <el-divider></el-divider>
    <ul class="list-container">
      <li
      v-for="(item, index) in list"
      :key="index"
      class="list-item"
      @click="showIdea(index)">
        <div style="display: flex;">
          <span style="font-size: 15px;text-align: left;padding: 0 10px 0 0;line-height: 25px;overflow: hidden;text-overflow: ellipsis;white-space:nowrap;">{{ item.text }}</span>
        </div>
        <div style="text-align: right;font-size: 2px;line-height: 1;">{{ item.createTime }}</div>
      </li>
      <el-empty v-if="list.length==0" description="还没有相关建议" style="height: 100%;"></el-empty>
    </ul>
    <a class="menu-bar-button" slot="reference">建议</a>
  </el-popover>
</template>

<script>
export default {
  data () {
    return {
      list: [
        { text: '注意膳食结构，尿石的生成和饮食结构有一定的关系。因此，注意调整膳食结构能够预防结石复发。根据尿石成分的不同，饮食调理应该采取不同的方案。如草酸钙结石患者宜少食草酸钙含量高的食品，如菠菜、西红柿、马铃薯、草莓等。', createTime: '2021-10-14 20:08:28' },
        { text: '根据结石的成分调节饮食结构，决定预防结石的饮食。尿酸结石应采用低嘌呤饮食，膀胱酸结石应采用低蛋氨酸饮食。水果、蔬菜能使尿液转为碱性，对防止尿酸和胱氨酸结石较好，肉类食物使尿呈酸性，对防止感染结石较好。对磷酸结石采用低钙、低磷饮食，含钙肾结石宜避免高钙、高盐、高草酸、高动物蛋白、高动物脂肪及高糖饮食。采用高纤维饮食，一般认为患者有肾结石的病人最好能少吃盐和动物性蛋白，坚持大量饮水，保持尿量在2000~3000毫升/天，这样不但起到预防肾结石复发的作用，还能保证钙摄入量，对身体其它方面都有好处。', createTime: '2021-10-10 12:32:58' },
        { text: '多饮水。应该养成多喝水的习惯以增加尿量，称为“内洗涤”，有利于体内多种盐类、矿物质的排除。当然，应该注意饮水卫生，注意水质，避免饮用含钙过高的水。', createTime: '2021-10-06 08:19:56' },
        { text: '多活动。平时要多活动，如散步、慢跑等。体力好的时候还可以原地跳跃，同样有利于预防泌尿系结石复发。', createTime: '2021-09-30 17:12:34' }
      ]
    }
  },
  methods: {
    showIdea (index) {
      this.$alert(this.list[index].text, '医生建议', {
        showConfirmButton: false,
        customClass: 'menu-bar-message-box',
        callback: () => {}
      })
    }
  }
}
</script>

<style scoped>
.el-divider {
  margin: 0;
}
.list-container {
  height: calc(100% - 50px);
  overflow-y: auto;
  padding: 0;
  margin: 0;
}
.list-item {
  padding: 15px 13px 0;
  border-bottom: 1px solid #edf1f7;
  background-color: #FFF;
  list-style: none;
  line-height: 10px;
  color: #606266;
  cursor: pointer;
}
.list-item:hover {
  background-color: rgb(237, 245, 255);
}
.list-container::-webkit-scrollbar {
  width: 5px;
}
.list-container::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background-color: rgba(114, 147, 153, 0.3);
  transition: background-color 0.3s;
  -webkit-transition: background-color 0.3s;
}
.list-container::-webkit-scrollbar-track {
  border-radius: 5px;
}
</style>
