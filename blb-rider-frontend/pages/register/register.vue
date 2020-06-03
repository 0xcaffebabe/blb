<template>
	<view>
		<h2 class="title">骑手注册</h2>
		<label for="用户名"></label>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="用户名" v-model="registerForm.username">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="手机号码" v-model="registerForm.phone">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="邮箱" v-model="registerForm.email">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="真实姓名" v-model="registerForm.realName">
		</div>
		<div class="input-wrapper">
			<input type="text" class="input" placeholder="身份证号码" v-model="registerForm.idNumber">
		</div>
		<div class="input-wrapper">
			<uni-combox class="combo-box" label="性别" :candidates="['男','女']" placeholder=""></uni-combox>
		</div>
		<div class="input-wrapper">
			<input type="password" class="input" placeholder="密码" v-model="registerForm.password">
		</div>
		<div class="input-wrapper">
			<input type="password" class="input" placeholder="重复密码" v-model="registerForm.repeatPassword">
		</div>
		<div class="input-wrapper">
			<button type="primary" class="login-btn" @click="register">注册</button>
		</div>
	</view>
</template>

<script>
	import uniCombox from "@/components/uni-combox/uni-combox"
	import RiderService from '../../service/RiderService.js'
	export default {
		data() {
			return {
				registerForm: {
					username: '',
					phone: '',
					email: '',
					realName: '',
					idNumber: '',
					password: '',
					repeatPassword: ''
				}
			}
		},
		methods: {
			async register () {
				if (!this.registerForm.username) {
					uni.showToast({
						title: "请输入用户名",
						icon: 'none'
					})
					return
				}
				if (!this.registerForm.phone) {
					uni.showToast({
						title: "请输入手机号码",
						icon: 'none'
					})
					return
				}
				if (!this.registerForm.email) {
					uni.showToast({
						title: "请输入电子邮箱",
						icon: 'none'
					})
					return
				}
				if (!this.registerForm.realName) {
					uni.showToast({
						title: "请输入真实姓名",
						icon: 'none'
					})
					return
				}
				if (!this.registerForm.idNumber) {
					uni.showToast({
						title: "请输入身份证号码",
						icon: 'none'
					})
					return
				}
				if (!this.registerForm.password) {
					uni.showToast({
						title: "请输入密码",
						icon: 'none'
					})
					return
				}
				if (!this.registerForm.repeatPassword) {
					uni.showToast({
						title: "请再输入一遍密码",
						icon: 'none'
					})
					return
				}
				if (!this.registerForm.password !== !this.registerForm.repeatPassword) {
					uni.showToast({
						title: "两次输入的密码不一致",
						icon: 'none'
					})
					return
				}
				try {
					const data = await RiderService.register(this.registerForm)
					uni.showToast({
						title: data,
						complete() {
							setTimeout(()=>{
								uni.navigateTo({
									url:'/pages/login/login'
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
