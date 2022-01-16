<template>
  <div class="container" style="overflow-y: auto;">
    <div class="inside-container">
      <!-- 结果页banner -->
      <div class="title">您好：</div>
      <div class="footer">说明：该结果目前仅供参考！</div>
      <!-- 结果列出 -->
      <div style="text-align: left;line-height: 2;font-size: 20px;">
        <div class="el-col-xs-24 el-col-sm-24 el-col-md-12" style="float: left;">根据系统智能评估预测结果，您各类泌尿结石患病的概率如图：<br /></div>
        <div ref="resultChart" class="el-col-xs-24 el-col-sm-24 el-col-md-12" style="height: 450px;float: right;"></div>
        <div class="el-col-xs-24 el-col-sm-24 el-col-md-12" style="float: left;">
          您当前结石类型<span style="color: #ec6b6a;">{{list[max]>80?'极有可能':list[max]>50?'很有可能':'有可能'}}</span>为
          <span style="color: #e0a12f;">{{typeOfStone[max]}}</span>，<br />
          请您及时咨询医生，遵从医嘱，<br />
          祝您早日康复！
          <el-card style="width: calc(100% - 25px);margin-right: 20px;" :body-style="{padding: '5px 15px', 'background-color': '#ffffff00'}">
            <div style="font-size: 15px;">
              <div style="color: #9b9d9f;font-weight: 800;">饮食注意</div>
              <el-tag type="danger" class="tag">不可以吃</el-tag>
              含糖饮料。高果糖浆，常作为营养性甜味剂广泛运用在碳酸饮料、果汁饮料、运动饮料、糖浆、果冻和其他含糖产品中。<br />
              <el-tag type="warning" class="tag">限制吃</el-tag>
              猪牛羊红肉，肉类中的红肉和白肉对痛风的影响不同。红肉有丰富的饱和脂肪酸和胆固醇，增加了痛风和心血管疾病的发病风险，因此痛风患者应限制。<br />
              <el-tag type="success" class="tag">适当多吃</el-tag>
              低脂牛奶、蔬菜。<br />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;另外，还建议痛风患者少吃苹果、无花果、橙子、荔枝、柿子、桂圆、香蕉、杨梅、石榴等果糖含量较高的水果。海鲜和啤酒海鲜嘌呤含量高，可导致血尿酸水平升高及远期发展为痛风的风险增加，像海鲜浓汤、鱿鱼、墨鱼等都应尽量少吃。
            </div>
          </el-card>
        </div>
      </div>
      <!-- 返回 -->
      <div style="width: 100%;display: inline-block;">
        <el-button style="margin: 10px 0 30px 0;" type="warning" @click="back" round>返回重试</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts/core'
import { TooltipComponent, LegendComponent } from 'echarts/components'
import { RadarChart } from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'

echarts.use([
  TooltipComponent,
  LegendComponent,
  RadarChart,
  CanvasRenderer
])

export default {
  data () {
    return {
      list: [],
      max: 0,
      typeOfStone: [],
      option: {
        color: ['#56A3F1', '#FF917C'],
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 80
        },
        radar: [
          {
            shape: 'circle',
            indicator: [],
            center: ['50%', '50%'],
            radius: '60%',
            axisName: {
              color: '#666',
              borderRadius: 3,
              padding: [3, 5]
            }
          }
        ],
        series: [
          {
            type: 'radar',
            radarIndex: 0,
            data: [
              {
                value: [50, 50, 50, 50],
                name: '50%',
                symbol: 'rect',
                symbolSize: 12,
                lineStyle: {
                  type: 'dashed'
                }
              },
              {
                value: [],
                name: '结石类型',
                areaStyle: {
                  color: new echarts.graphic.RadialGradient(0.1, 0.6, 1, [
                    {
                      color: 'rgba(255, 145, 124, 0.1)',
                      offset: 0
                    },
                    {
                      color: 'rgba(255, 145, 124, 0.9)',
                      offset: 1
                    }
                  ])
                },
                label: {
                  show: true,
                  formatter: function (params) {
                    return params.value + '%'
                  }
                }
              }
            ]
          }
        ]
      }
    }
  },
  mounted () {
    const indicators = []
    this.typeOfStone.forEach(item => {
      indicators.push({ text: item, max: 100 })
    })
    this.option.radar[0].indicator = indicators
    const resultChart = echarts.init(this.$refs.resultChart)
    this.option.series[0].data[1].value = this.list
    resultChart.setOption(this.option)
    window.onresize = () => {
      resultChart.resize()
    }
  },
  methods: {
    back () {
      this.$router.back()
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      const list = JSON.parse(window.localStorage.getItem('result')).map(item => {
        return Math.round(item * 10000) / 100
      })
      vm.list = list
      var max = 0 // 假定0为最大，index从1开始比较
      for (let i = 1; i < list.length; i++) {
        if (list[i] > list[max]) {
          max = i
        }
      }
      vm.max = max
      vm.typeOfStone = JSON.parse(window.localStorage.getItem('typeOfStone')).labels // 读取记录的typeOfStone定义
    })
  }
}
</script>

<style scoped>
.inside-container {
  box-shadow: 0 2px 20px 2px rgb(0, 0, 0, 0.1);
  background-color: #f7f7e8;
  position: relative;
  padding-left: 3vw;
}
.title {
  color: #e1bd7c;
  font-size: 20px;
  text-align: left;
  line-height: 1.8;
  padding-top: 2vh;
}
.footer {
  position: absolute;
  bottom: 0;
  right: 0;
  color: #e1bd7c;
  font-size: 10px;
  text-align: left;
  line-height: 1.8;
  padding: 1vh 1.5vw;
}
.container {
  height: calc(100vh - 40px);
  width: calc(100vw - 50px);
  padding: 20px 25px;
}
.container::-webkit-scrollbar {
  width: 6px;
}
.container::-webkit-scrollbar-thumb {
  border-radius: 5px;
  width: 6px;
  background: #b4bccc;
}
.container::-webkit-sscrollbar-corner,.container::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.01);
}
.container::-webkit-scrollbar-track-piece {
  background: rgba(255, 255, 255, 0.01);
  width: 6px;
}
.tag {
  padding: 0 2px;
  line-height: 23px;
  height: 23px;
}
</style>
