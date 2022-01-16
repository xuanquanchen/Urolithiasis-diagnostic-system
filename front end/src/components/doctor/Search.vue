<template>
  <el-card
  shadow="hover"
  style="margin-bottom: 10px;transition: height .2s;"
  :style="`height: ${ expand?'140px':'80px' };`">
    <el-input v-model="input" :placeholder="`请输入${ placeholder }`" maxlength="15" show-word-limit>
      <el-button slot="prepend" icon="el-icon-date" @click="expandList"></el-button>
      <el-button slot="append" icon="el-icon-search" @click="searching"></el-button>
    </el-input>
    <div v-show="expand">
      <el-divider></el-divider>
      <el-date-picker
        v-model="times"
        :picker-options="pickerOptions"
        value-format="yyyy-MM-dd HH:mm:ss"
        type="datetimerange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        style="width: 100%;">
      </el-date-picker>
    </div>
  </el-card>
</template>

<script>
export default {
  data () {
    return {
      input: '',
      times: null,
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const time = new Date()
            picker.$emit('pick', [new Date().setTime(time.getTime() - 604800000), time])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const time = new Date()
            picker.$emit('pick', [new Date().setTime(time.getTime() - 2592000000), time])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const time = new Date()
            picker.$emit('pick', [new Date().setTime(time.getTime() - 7776000000), time])
          }
        }]
      }
    }
  },
  props: {
    placeholder: {
      default: '',
      type: String
    },
    expand: {
      default: true,
      type: Boolean
    }
  },
  methods: {
    expandList () {
      this.$emit('expandList', true)
    },
    searching () {
      var i = this.input.replaceAll(' ', '')
      this.$emit('searching', i, this.times)
    },
    load () {
      this.count += 2
    }
  }
}
</script>

<style scoped>
.el-divider {
  margin: 10px 0;
}
</style>
