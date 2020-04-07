<template>
	<view class="content">
		<view class="forget-bg">
			<view class="forget-card">
				<view class="forget-input forget-margin-b">
					<input type="number" v-model="query.username" placeholder="请输入用户名" />
				</view>
				<view class="forget-input forget-margin-b">
					<input type="password" v-model="query.password" placeholder="请输入密码(8-16位)" />
				</view>
				<view class="forget-input forget-margin-b">
					<input type="number" v-model="query.phone" placeholder="请输入手机号" />
				</view>
				<view class="forget-input forget-margin-b">
					<input type="text" v-model="query.email" placeholder="请输入邮箱" />
				</view>
				<view class="forget-input forget-margin-b">
					<view class="verify-left">
						<input type="number" v-model="query.verify" placeholder="请输入验证码" />
					</view>
					<view class="verify-right">
						<button class="verify-btn" type="primary" @click="sendVerify">获取验证码</button>
					</view>
				</view>
			</view>
		</view>
		<view class="forget-btn">
			<button class="landing" type="primary" @click="commitData">提交</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				query: {
					username: null,
					password: null,
					phone: null,
					email: null,
					verify: null
				}
			}
		},
		onLoad() {

		},
		methods: {
			// 发送验证码
			async sendVerify(){
				if (typeof this.query.email == 'undefined' || this.query.email == ''){
					uni.showToast({
						icon: none,
						title: '请填写注册时的邮箱地址'
					})
					return;
				}
				let ret = await this.$http({
					url: '/sendVerify',
					data: {address: this.query.email}
				})
				if (ret.code == 0) uni.showToast({title: ret.message})
			},
			// 提交注册信息
			async commitData(){
				let ret = await this.$http({
					url: "/register",
					method: 'POST',
					data: this.query
				})
				if (ret.code == 0){
					uni.showToast({
						title: ret.message
					})
					uni.navigateTo({
						url: '/pages/login/login'
					})
				}else {
					uni.showToast({
						icon: 'none',
						title: ret.message
					})
				}
			}
		}
	}
</script>

<style>
	.verify-left{
		width: calc(100% - 260upx);
	}
	.verify-right{
		padding-left: 20upx;
	}
	.verify-btn{
		height: 80upx;
		line-height: 80upx;
		font-size: 28upx;
		width: 240upx;
		border-radius: 8upx;
		background: linear-gradient(left,#FF978D, #FFBB69);
	}
	.verify-left,.verify-right{
		float: left;
	}
	.landing{
		height: 84upx;
		line-height: 84upx;
		border-radius: 44upx;
		font-size: 32upx;
		background: linear-gradient(left,#FF978D, #FFBB69);
	}
	.forget-btn{
		padding: 10upx 20upx;
		margin-top: 380upx;
	}

	.forget-input input{
		background: #F2F5F6;
		font-size: 28upx;
		padding: 10upx 25upx;
		height: 62upx;
		line-height: 62upx;
		border-radius: 8upx;
	}
	.forget-margin-b{
		margin-bottom: 25upx;
	}
	.forget-input{
		padding: 10upx 20upx;
		overflow: auto;
	}
	.forget-card{
		background: #fff;
		border-radius: 12upx;
		padding: 60upx 25upx;
		box-shadow: 0 6upx 18upx rgba(0,0,0,0.12);
		position: relative;
		margin-top: 120upx;
	}
	.forget-bg {
		height: 350rpx;
		padding: 25upx;
		background: linear-gradient(#FF978D, #FFBB69);
	}
</style>
