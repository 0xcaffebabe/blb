<template>
	<view>
		<h2 class="title">骑手登录</h2>
		<label for="用户名"></label>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="用户名" v-model="username">
		</div>
		<div class="input-wrapper">
			<input type="password" class="input" placeholder="密码" v-model="password">
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
	import RiderService from '../../service/RiderService.js'
	export default {
		data() {
			return {
				username: '',
				password: '',
			}
		},
		methods: {
			async login () {
				if (!this.username) {
					uni.showToast({
						title: '请输入用户名',
						icon:'none'
					})
					return
				}
				if (!this.password) {
					uni.showToast({
						title: '请输入密码',
						icon:'none'
					})
					return
				}
				try {
					const data = await RiderService.login(this.username, this.password)
					uni.showToast({
						title: data.greeting,
						complete() {
							setTimeout(()=>{
								uni.navigateTo({
									url:'/pages/index/index'
								})
							},1500)
						}
					})
				} catch (e) {
					uni.showToast({
						title: e.message,
						icon:'none'
					})
				}
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
