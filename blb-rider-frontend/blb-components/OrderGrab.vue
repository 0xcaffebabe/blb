<template>
	<div>
		<ul class="order-list">
			<li class="order-item" v-for="item in 12" :key="item">
				<image src="../static/logo.png" mode="" class="shop-logo"></image>
				<div class="order-desc">
					<h4>黄焖鸡米饭 - cxk 的订单</h4>
					<p>金额：35.00</p>
					<p>距离：2km</p>
					<button size="mini" type="primary" class="grab-btn" style="float:right" @click="grabOrder">接单</button>
				</div>
			</li>
		</ul>
	</div>
</template>

<script>
	export default {
		data () {
			return {}
		},
		methods: {
			grabOrder () {
				uni.showToast({
					title: '接单成功'
				})
				setTimeout(function(){
					uni.navigateTo({
						url:'/pages/grabDetail/grabDetail'
					})
				},300)
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
