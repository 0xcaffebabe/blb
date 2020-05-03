<template>
	<view>
		<h2 class="title">骑手登录</h2>
		<label for="用户名"></label>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="用户名">
		</div>
		<div class="input-wrapper">
			<input type="password" class="input" placeholder="密码">
		</div>
		<div class="input-wrapper">
			<button type="primary" class="login-btn" @click="login">登录</button>
		</div>
		<div class="input-wrapper">
			<button type="success" class="login-btn" @click="register">注册</button>
		</div>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {

			}
		},
		methods: {
			login () {
				uni.showToast({
				    title: 'xxx',
				    duration: 2000
				});
			},
			register () {
				uni.navigateTo({
					url:'/pages/register/register'
				})
			}
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
</style>
