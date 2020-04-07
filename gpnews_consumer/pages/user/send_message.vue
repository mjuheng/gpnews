<template>
	<view class="send_message">
		<view class='feedback-title'>
			<text>标题</text>
		</view>
		<view class="feedback-body">
			<input class="feedback-input" v-model="query.title" placeholder="请概括你的问题和意见"/>
		</view>

		<view class='feedback-title'>
			<text>问题和意见</text>
		</view>
		<view class="feedback-body">
			<textarea placeholder="请详细描述你的问题和意见..." v-model="query.content" class="feedback-textare" />
		</view>
        
        <button type="primary" class="feedback-submit" @tap="saveMessage">提交</button>
    </view>
</template>

<script>
    export default {
        data() {
            return {
				query: {
					title: '',
					content: '',
					type: 1
				}
                
            }
        },
        methods: {
			async saveMessage(){
				console.log(this.query);
				if (this.query.title == '' || this.query.content == ''){
					uni.showToast({
						icon: 'none',
						title: '标题和内容描述不能为空'
					})
					return
				}
				
				let ret = await this.$http({
					url: '/msg/add',
					method: 'POST',
					data: this.query
				})
				if (ret.code == 0){
					uni.showToast({title: ret.message})
					
					setTimeout(function(){
						console.log('执行跳转');
						uni.switchTab({
							url: '/pages/user/index',
							fail: (e) => {
								console.log(e);
							}
						})
					}, 1000)
					console.log("执行结束");
				}
				
			}
        }
    }
</script>

<style>
    @font-face {
    	font-family: uniicons;
    	font-weight: normal;
    	font-style: normal;
    	src: url('https://img-cdn-qiniu.dcloud.net.cn/fonts/uni.ttf') format('truetype');
    }
    .send_message {
        background-color: #EFEFF4;
		padding-left: 20upx;
		padding-right: 20upx;
		height: 1334upx;
    }
    view{
        font-size: 28upx;
    }

 
    
    /*问题反馈*/
    .feedback-title {
    	display: flex;
    	flex-direction: row;
    	justify-content: space-between;
    	align-items: center;
    	color: #8f8f94;
    	font-size: 28upx;
    }


    .feedback-body {
    	background: #fff;
    }
    .feedback-textare {
    	height: 200upx;
    	font-size: 34upx;
    	line-height: 50upx;
    	width: 100%;
    	box-sizing: border-box;
    	padding: 20upx 30upx 0;
    }
    .feedback-input {
    	font-size: 34upx;
    	height: 60upx;
    	min-height: 50upx;
    	padding: 15upx 20upx;
    	line-height: 50upx;
    }

    .feedback-submit {
    	background: #007AFF;
    	color: #FFFFFF;
		margin-top: 40upx;
    }
</style>
