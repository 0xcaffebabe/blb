<template>
  <div>
    <el-card>
      <div slot="header">
        最近一周顾客趋势
      </div>
      <div ref="weekChart" style="width:100%;height:400px"></div>
    </el-card>
    <el-row :gutter="20" style="margin-top:40px">
      <el-col :span="10">
        <el-card>
          <div slot="header">顾客性别</div>
          <div ref="genderChart" style="height:200px;width:100%"></div>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card>
          <div slot="header">
            年龄分布
          </div>
          <div ref="ageChart" style="width:100%;height:200px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
const echarts = require('echarts')
export default {
  data () {
    return {}
  },
  methods: {
    generateWeekChart () {
      const myChart = echarts.init(this.$refs.weekChart)
      // 绘制图表
      const option = {
        height: 300,
        width: '80%',
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: [820, 932, 901, 934, 1290, 1330, 1320],
          type: 'line'
        }]
      }

      myChart.setOption(option)
    },
    generateGenderChart () {
      const chart = echarts.init(this.$refs.genderChart)
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 10,
          data: ['男', '女']
        },
        series: [
          {
            name: '占比',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '30',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {
                value: 335,
                name: '男',
                itemStyle: {
                  // 设置扇形的颜色
                  color: 'rgb(72, 218, 217)'
                }
              },
              {
                value: 25,
                name: '女',
                itemStyle: {
                  // 设置扇形的颜色
                  color: 'rgb(255, 242, 69)'
                }
              }
            ]
          }
        ]
      }
      chart.setOption(option)
    },
    generateAgeChart () {
      const chart = echarts.init(this.$refs.ageChart)
      const option = {
        color: ['#3398DB'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
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
            data: ['0-18岁', '18-23岁', '24-30岁', '31-40岁', '41-50岁', '50+'],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '下单',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52, 200, 334, 390, 330, 220]
          }
        ]
      }
      chart.setOption(option)
    }
  },
  mounted () {
    this.generateWeekChart()
    this.generateGenderChart()
    this.generateAgeChart()
  }
}
</script>

<style lang='less' scoped>
</style>
