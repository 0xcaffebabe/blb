<template>
	<view>
		<map name="" class="map"></map>
		<h3>订单号：1234444444</h3>
		<h3>商家出餐码：3621</h3>
		<h3>用户取餐码：3621</h3>
		<h3>店铺名称: 黄焖鸡米饭</h3>
		<h3>店铺地址: 鲤城区媒人桥路2号</h3>
		<button type="primary">已从商家取餐</button>
		<button type="primary" style="margin-top: 20rpx;">消费者已取餐</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="less" scoped>
	.map {
		width:100%;
		height: 500rpx;
	}
</style>
