<template>
  <div>
    consumer
    <div ref="chart" style="width:400px;height:200px"></div>
  </div>
</template>

<script>
const echarts = require('echarts')
export default {
  data () {
    return {}
  },
  created () {
    // 基于准备好的dom，初始化echarts实例
    const myChart = echarts.init(this.$refs.chart)
    // 绘制图表
    myChart.setOption({
      title: {
        text: 'ECharts 入门示例'
      },
      tooltip: {},
      xAxis: {
        data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
      },
      yAxis: {},
      series: [
        {
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20]
        }
      ]
    })
  }
}
</script>

<style lang='less' scoped>
  #chart {
    height: 200px;
    width: 400px;
  }
</style>
