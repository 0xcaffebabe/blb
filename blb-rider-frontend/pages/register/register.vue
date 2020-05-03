<template>
	<view>
		<h2 class="title">骑手注册</h2>
		<label for="用户名"></label>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="用户名">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="手机号码">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="邮箱">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="真实姓名">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="身份证号码">
		</div>
		<div class="input-wrapper">
			<uni-combox class="combo-box" label="性别" :candidates="['男','女']" placeholder="" v-model="value"></uni-combox>
		</div>
		<div class="input-wrapper">
			<input type="password" class="input" placeholder="密码">
		</div>
		<div class="input-wrapper">
			<input type="password" class="input" placeholder="重复密码">
		</div>
		<div class="input-wrapper">
			<button type="primary" class="login-btn" @click="register">注册</button>
		</div>
	</view>
</template>

<script>
	import uniCombox from "@/components/uni-combox/uni-combox"
	export default {
		data() {
			return {

			}
		},
		methods: {
			register () {
				uni.showToast({
					title:'注册'
				})
			}
		},
		components: {
			uniCombox
		}
	}
</script>

<style>
	.title {
		text-align: center;
		margin-top: 200rpx;
	}

	page {
		background-color: #eee;
	}
	.input-wrapper {
		padding: 10px;
	}
	.input {
		background-color: #fff;
		height: 56px;
		padding: 0 10px;
		font-size: 22px;
	}
	.login-btn {
		height: 56px;
		line-height: 56px;
		font-size: 22px;
	}
	.combo-box {
		padding: 0 10px;
		background-color: #fff;
		height: 56px;
		line-height: 56px;
		font-size: 22px;
	}
</style>
