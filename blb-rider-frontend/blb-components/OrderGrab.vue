<template>
	<div>
		<ul class="order-list">
			<li class="order-item" v-for="item in orderList" :key="item.orderId">
				<image :src="item.shopLogo" mode="" class="shop-logo"></image>
				<div class="order-desc">
					<h4>{{item.shopId}} - {{item.consumerName}} 的订单</h4>
					<p>金额：{{item.orderAmount}}</p>
					<p>距离：2km</p>
					<button size="mini" type="primary" class="grab-btn" style="float:right" @click="grabOrder(item.orderId)">接单</button>
				</div>
			</li>
		</ul>
	</div>
</template>

<script>
	import RiderService from '../service/RiderService.js'
	export default {
		data () {
			return {
				orderList: []
			}
		},
		created() {
			this.getUndeliveryOrder()
				this.getOrderList()
		},
		methods: {
			async grabOrder (orderId) {
				try {
					const data = await RiderService.grabOrder(orderId)
					uni.showToast({
						title: data
					})
					setTimeout(function(){
						uni.navigateTo({
							url:'/pages/grabDetail/grabDetail?orderId='+orderId
						})
					},300)
				} catch (e) {
						uni.showToast({
							title: e.message,
							icon:'none'
						})
				}
				
			},
			async getOrderList () {
				try {
					const data = await RiderService.getDeliveryOrder()
					this.orderList = data
					console.log(this.orderList)
				} catch (e) {
					uni.showToast({
						title: e.message,
						icon: 'none'
					})
				}
			},
			async getUndeliveryOrder (){
				try {
					const data = await RiderService.getUndeliveryOrder()
					if (data) {
						uni.showToast({
							title: '您还有未完成的订单',
							icon:'none',
							complete() {
								setTimeout(()=>{
									uni.navigateTo({
										url:'/pages/grabDetail/grabDetail?orderId='+data.orderId
									})
								},1000)
							}
						})
					}
				} catch (e) {
					uni.showToast({
						title: e.message,
						icon: 'none'
					})
				}
			}
		}
	}
</script>

<style lang="less" scoped>
	.order-list {
		list-style: none;
		margin-top: 20rpx;
		.order-item {
			display: flex;
			padding: 20rpx 0;
		}
		.shop-logo {
			flex: 0 0 25%;
			max-width: 100rpx;
			max-height: 100rpx;
		}
		.order-desc {
			flex: 0 0 75%;
			margin-left:20rpx;
		}
	}
	.grab-btn {
		font-size: 28rpx;
	}
</style>
