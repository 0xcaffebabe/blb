<template>
	<div>
		<h3>历史订单列表</h3>
		<ul>
			<li v-for="item in 4">
				<p>黄焖鸡米饭 - cxk 的订单</p>
				<p>金额:35</p>
				<p>创建时间: 2020-01-24</p>
			</li>
		</ul>
	</div>
</template>

<script>
	
	export default {
		data () {
			return {}
		},
		created() {
			
		},
		methods: {
			
		}
	}
</script>

<style lang="less" scoped>
	
</style>
