<template>
	<view>
		<scroll-view scroll-y style="height:1200rpx">
			<component v-bind:is="currentTab"></component>
		</scroll-view>
		<div>
			<ul class="nav">
				<li :class="{active: currentTab == 'grab'}" @click="currentTab = 'grab'">
					<uni-icons type="cart" size="30"></uni-icons>
					<p>接单</p>
				</li>
				<li :class="{active: currentTab == 'order'}" @click="currentTab = 'order'">
					<uni-icons type="list" size="30"></uni-icons>
					<p>订单</p>
				</li>
				<li :class="{active: currentTab == 'my'}" @click="currentTab = 'my'">
					<uni-icons type="person" size="30"></uni-icons>
					<p>我的</p>
				</li>
			</ul>
		</div>
		
	</view>
</template>

<script>
	import OrderGrab from '../../blb-components/OrderGrab.vue'
	import OrderList from '../../blb-components/OrderList.vue'
	import My from '../../blb-components/My.vue'
	export default {
		data() {
			return {
				currentTab: 'grab'
			}
		},
		onLoad() {

		},
		methods: {

		},
		components: {
			'grab': OrderGrab,
			'order': OrderList,
			'my': My
		}
	}
</script>

<style lang="less" scoped>
	.nav {
		z-index: 999;
		background-color: #fff;
		display: flex;
		width: 100%;
		position: fixed;
		bottom: 0;
		list-style: none;
		border-top: 2px solid #ccc;
		li {
			flex: 0 0 33.33%;
		}
	}
	.active {
		color: #007aff;
	}
</style>
