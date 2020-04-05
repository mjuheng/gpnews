<template>
	<view class="content">
		<view class="login-bg">
			<view class="login-card">
				<view class="login-head">请输入你的账户</view>
				<view class="login-input login-margin-b">
					<input type="text" v-model="query.username" placeholder="用户名" />
				</view>
				<view class="login-input">
					<input type="password" v-model="query.password" placeholder="密码" />
				</view>
				<view class="login-function">
					<view class="login-forget" @click="go_forget">忘记密码</view>
					<view class="login-register" @click="go_register">快速注册></view>
				</view>
			</view>
		</view>
		<view class="login-btn">
			<button class="landing" type="primary" @click="commitLogin">登陆</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello',
				query: {
					username: '',
					password: ''
				}
			}
		},
		onLoad(e) {
			if(typeof e != 'undefined'){
				uni.showToast({
					icon: 'none',
					title: e.msg
				})
			}
		},
		methods: {
			// 登录
			async commitLogin(){
				let ret = await this.$http({
					url: '/login',
					method: "POST",
					data: this.query
				})
				if (ret.code == 0){
					uni.setStorageSync('sessionId', ret.data.sessionId);
					uni.setStorageSync('userInfo', JSON.parse(ret.data.user))
					uni.switchTab({
						url: '/pages/index/index'
					})
				}else{
					uni.showToast({
						icon: 'none',
						title: ret.message
					})
				}
			},
			go_forget(){
				console.log('haha');
				uni.navigateTo({
				    url: '/pages/login/forget'
				})
			},
			go_register(){
				uni.navigateTo({
					url: '/pages/login/register'
				})
			}
			
		}
	}
</script>

<style>
	.landing{
		height: 84upx;
		line-height: 84upx;
		border-radius: 44upx;
		font-size: 32upx;
		background: linear-gradient(left,#FF978D, #FFBB69);
	}
	.login-btn{
		padding: 10upx 20upx;
		margin-top: 350upx;
	}
	.login-function{
		overflow: auto;
		padding: 20upx 20upx 30upx 20upx;
	}
	.login-forget{
		float: left;
		font-size: 26upx;
		color: #999;
	}
	.login-register{
		color: #666;
		float: right;
		font-size: 26upx;

	}
	.login-input input{
		background: #F2F5F6;
		font-size: 28upx;
		padding: 10upx 25upx;
		height: 62upx;
		line-height: 62upx;
		border-radius: 8upx;
	}
	.login-margin-b{
		margin-bottom: 25upx;
	}
	.login-input{
		padding: 10upx 20upx;
	}
	.login-head{
		font-size: 34upx;
		text-align: center;
		padding: 25upx 10upx 55upx 10upx;
	}
	.login-card{
		background: #fff;
		border-radius: 12upx;
		padding: 10upx 25upx;
		box-shadow: 0 6upx 18upx rgba(0,0,0,0.12);
		position: relative;
		margin-top: 120upx;
	}
	.login-bg {
		height: 260upx;
		padding: 25upx;
		background: linear-gradient(#FF978D, #FFBB69);
	}
</style>
