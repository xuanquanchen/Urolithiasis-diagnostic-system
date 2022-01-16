<!--
  * @FileDescription:医生端结石趋势页
  * @Date:2021/10/3
  * @LastEditTime:2021/10/5
 -->
<template>
  <div>
    <el-row :gutter="15" style="margin-bottom: 13px;">
      <el-col v-for="(item, index) in todaySingleFlow" :key="index" :sm="6" :xs="12">
        <single-state-card :name="item.name" :percentage="item.percentage" :up="item.up"></single-state-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-bottom: 13px;">
      <el-col :sm="10">
        <el-card style="margin-bottom: 20px;">
          <div slot="header">结石类型预测统计</div>
          <div ref="totalChart" style="height: 300px;width: 100%;"></div>
        </el-card>
      </el-col>
      <el-col :sm="14">
        <el-card style="margin-bottom: 20px;">
          <div slot="header">近一周结石类型预测变化</div>
          <div ref="rec7Chart" style="height: 300px;width: 100%;"></div>
        </el-card>
      </el-col>
    </el-row>
    <icp class="icp" style="position: relative;"></icp>
  </div>
</template>

<script>
import Icp from '@/components/Icp'
import SingleStateCard from '@/components/doctor/trend/SingleStateCard'

import * as echarts from 'echarts/core'
import { TooltipComponent, LegendComponent, GridComponent, ToolboxComponent } from 'echarts/components'
import { PieChart, LineChart } from 'echarts/charts'
import { LabelLayout, UniversalTransition } from 'echarts/features'
import { CanvasRenderer } from 'echarts/renderers'

echarts.use([
  TooltipComponent,
  LegendComponent,
  PieChart,
  CanvasRenderer,
  LabelLayout,
  GridComponent,
  LineChart,
  UniversalTransition,
  ToolboxComponent
])

export default {
  components: {
    Icp,
    SingleStateCard
  },
  mounted () {
    const totalChart = echarts.init(this.$refs.totalChart)
    const rec7Chart = echarts.init(this.$refs.rec7Chart)
    // 注入近一个星期
    this.rec7ChartOption.xAxis[0].data = this.getDays()

    totalChart.setOption(this.totalChartOption)
    rec7Chart.setOption(this.rec7ChartOption)
    window.onresize = () => {
      totalChart.resize()
      rec7Chart.resize()
    }
  },
  data () {
    return {
      todaySingleFlow: [
        { name: '钙结石', percentage: 0 },
        { name: '尿酸结石', percentage: 0 },
        { name: '鹿角形结石', percentage: 0 },
        { name: '胱氨酸结石', percentage: 0 }
      ],
      totalChartOption: {
        tooltip: {
          trigger: 'item'
        },
        toolbox: {
          feature: {
            saveAsImage: {
              title: '保存',
              name: 'total_' + new Date().getTime()
            }
          }
        },
        legend: {
          show: true
        },
        series: [
          {
            name: '结石类型',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 0, name: '钙结石' },
              { value: 0, name: '尿酸结石' },
              { value: 0, name: '鹿角形结石' },
              { value: 0, name: '胱氨酸结石' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            label: {
              formatter: '{d}%'
            }
          }
        ]
      },
      rec7ChartOption: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          show: true
        },
        toolbox: {
          feature: {
            saveAsImage: {
              title: '保存',
              name: 'rec7_' + new Date().getTime()
            }
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '钙结石',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 0, 0, 0, 0, 0],
            smooth: true
          },
          {
            name: '尿酸结石',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 0, 0, 0, 0, 0],
            smooth: true
          },
          {
            name: '鹿角形结石',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 0, 0, 0, 0, 0],
            smooth: true
          },
          {
            name: '胱氨酸结石',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 0, 0, 0, 0, 0],
            smooth: true
          }
        ]
      }
    }
  },
  methods: {
    getTendency () {
      this.axios.get('/api/tendency/today?_t' + new Date().getTime())
        .then(res => {
          res.data.data.todayFlow.forEach((item, index) => {
            this.todaySingleFlow[index].percentage = item * 100
          })
          res.data.data.totalType.forEach((item, index) => {
            this.totalChartOption.series[0].data[index].value = item
          })
          res.data.data.weeklyTendency.forEach((item, index) => {
            this.rec7ChartOption.series[index].data = item.reverse()
          })
          this.updateCharts()
        })
        .catch(e => {
          if (/Network/.test(e)) {
            this.$message.error('请检查网络后重试!')
          } else if (/code [401|301]/.test(e)) {
            this.$message.error('用户需要登录！')
          } else {
            this.$message.error('出错了，请重试！')
          }
        })
    },
    updateCharts () {
      const totalChart = echarts.getInstanceByDom(this.$refs.totalChart)
      const rec7Chart = echarts.getInstanceByDom(this.$refs.rec7Chart)
      totalChart.setOption(this.totalChartOption)
      rec7Chart.setOption(this.rec7ChartOption)
    },
    getDays () {
      let day = new Date().getDay() - 6
      const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
      const result = []
      for (let i = 0; i < 7; i++) {
        if (day < 0) {
          result.push(days[day + 7])
        } else {
          result.push(days[day])
        }
        day++
      }
      return result
    }
  },
  beforeRouteEnter (from, to, next) {
    next(vm => {
      vm.getTendency()
    })
  }
}
</script>

<style scoped>
.icp {
  width: auto;
}
@media only screen and (min-height: 786px) {
  .icp {
    position: absolute !important;
    width: calc(100% - 120px);
  }
}
@media only screen and (max-width: 768px) {
  .icp {
    position: relative !important;
    width: auto !important;
  }
}
</style>
