<template>
	<view>
		<map name="" class="map"></map>
		<h3>订单号：{{orderDetail.orderId}}</h3>
		<h3>商家出餐码：{{orderDetail.dinnerOutCode}}</h3>
		<h3>用户取餐码：{{orderDetail.takeMealCode}}</h3>
		<h3>店铺名称: {{orderDetail.shopName}}</h3>
		<h3>店铺地址: {{orderDetail.shopAddress}}</h3>
		<button type="primary" @click="shipOrder(orderDetail.orderId)">已从商家取餐</button>
		<button type="primary" style="margin-top: 20rpx;" @click="completeOrder(orderDetail.orderId)">消费者已取餐</button>
	</view>
</template>

<script>
	import RiderService from '../../service/RiderService.js'
	export default {
		data() {
			return {
				orderDetail: {}
			}
		},
		onLoad(option) {
			this.getOrderDetail(option.orderId)
		},
		methods: {
			async getOrderDetail (orderId) {
				try {
					const data = await RiderService.getOrderDetail(orderId)
					this.orderDetail = data
				} catch (e) {
					uni.showToast({
						title: e.message,
						icon:'none'
					})
				}
			},
			async shipOrder (orderId) {
				try {
					if( await RiderService.shipOrder(orderId)){
							uni.showToast({
								title:'进行配送'
							})
					}
				} catch (e) {
					uni.showToast({
						title: e.message,
						icon:'none'
					})
				}
			},
			async completeOrder (orderId) {
				try {
					if( await RiderService.completeOrder(orderId,this.orderDetail.takeMealCode)){
							uni.showToast({
								title:'订单已完结',
								complete() {
									setTimeout(()=>{
										uni.navigateTo({
											url:"/pages/index/index"
										})
									},1000)
								}
							})
					}
				} catch (e) {
					uni.showToast({
						title: e.message,
						icon:'none'
					})
				}
			}, 
		}
	}
</script>

<style lang="less" scoped>
	.map {
		width:100%;
		height: 500rpx;
	}
</style>
