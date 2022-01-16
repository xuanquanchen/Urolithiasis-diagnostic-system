<!--
  * @FileDescription:表格内部元素
  * @Date:2021/10/1
  * @LastEditTime:2021/10/5
 -->
<!-- 适配各类型选择输入 -->
<template>
  <div class="selector" :class="weight==1?'el-col-xs-24 el-col-sm-12':'el-col-xs-24 el-col-sm-24'">
    <span style="padding-right: 30px;color: #374151;line-height: 40px;">{{ name }}</span>
    <el-radio-group v-if="type==0 && option.length<5" v-model="input" style="margin-left: 10px;" @change="change">
      <el-radio v-for="(item,ind) in option" :key="ind" :label="ind" style="height: 13px;">{{ item }}</el-radio>
    </el-radio-group>
    <el-select v-else-if="type==0" v-model="input" size="small" placeholder="请选择" @change="change">
      <el-option v-for="(item,ind) in option" :key="ind" :label="item" :value="ind" />
    </el-select>
    <el-input-number
    v-else-if="type==1"
    v-model="input"
    size="small"
    controls-position="right"
    :min="1"
    :max="120"
    :label="'请输入'+name"
    @change="change" />
    <el-input v-else-if="type==2" v-model="input"
    maxlength="15" size="small"
    :placeholder="`请输入${name}`"
    style="width: 130px;" show-word-limit
    @change="change" />
  </div>
</template>

<script>

export default {
  data () {
    return {
      input: this.$props.value // 初始化input值为父类组件传入值
    }
  },
  props: {
    name: {
      default: '',
      type: String
    },
    option: {
      default: () => { return '' }
    },
    type: {
      default: 0,
      type: Number
    },
    weight: {
      default: 1,
      type: Number
    },
    index: {
      default: 0,
      type: Number
    },
    value: {
      default: 0
    }
  },
  methods: {
    change () {
      this.$emit('valueChange', this.index, this.input) // 子类传值
    }
  }
}
</script>

<style scoped>
.selector {
  display: flex;
  padding: 0 10px;
  align-items: center;
  float: none;
  justify-content: space-between;
  flex-wrap: wrap;
}
</style>

<style>
.selector .el-select .el-input--small {
  width: 130px;
}
</style>
