<template>
    <view>

		<view class="article-meta" style="font-size: 40rpx;color: rgb(0,0,0);">{{data.title}}</view>
		<view class="article-meta">
		    <text class="article-author">{{data.username}}</text>
		    <text class="article-text">发表于</text>
		    <text class="article-time">{{getFormatDate(data.publishTime)}}</text>
		</view>
		<view class="article-content">
			<view v-html="data.content"></view>
		</view>
		<!-- 文章评论 -->
        <view class="uni-padding-wrap">
            <view class="" style="font-size: 30rpx; margin-top: 15rpx;">
                评论
            </view>
            <!-- 评论区 start -->
            <view class="uni-comment">
                <view class="uni-comment-list" v-for="(item,idx) in commentData" :key="idx">
                    <view class="uni-comment-face">
                        <image :src="item.userPhoto" mode="widthFix"></image>
                    </view>
                    <view class="uni-comment-body">
                        <view class="uni-comment-top">
                            <text>{{item.username}}</text>
                        </view>
                        <view class="uni-comment-content" @click="showCommInput(item)">{{item.content}}</view>
                        <view class="uni-comment-date">
                            <view>{{getFormatDate(item.createdTime)}}</view>
							<view @tap="delReply(item.id)"  v-if="item.userId == currUserId" class="uni-comment-replay-btn" >删除</view>
                            <view @tap="tapReply(item,idx)" v-if="item.children && item.children.length" class="uni-comment-replay-btn">{{item.children.length+"  "}}回复</view>
                        </view>
                        <view v-if="idx==index" class="uni-comment-list" v-for="(item2,idx2) in item.children" :key="idx2">
                            <view class="uni-comment-face">
                                <image :src="item2.userPhoto" mode="widthFix"></image>
                            </view>
                            <view class="uni-comment-body">
                                <view class="uni-comment-top">
                                    <text>{{item2.username}}</text>
                                </view>
                                <view class="uni-comment-content">{{item2.content}}</view>
                                <view class="uni-comment-date">
                                    <view>{{getFormatDate(item2.createdTime)}}</view>
									<view @tap="delReply(item.id)"  v-if="item.userId == currUserId" class="uni-comment-replay-btn" >删除</view>
                                </view>
                            </view>
                        </view>
                    </view>
                </view>
            </view>
            <!-- 评论区 end -->
			
			<!-- 显示评论框 -->
			<view class="bottom" v-if="showBtnType == 1">
				<view class="input-box">
					<text class="yticon icon-huifu"></text>
					<input 
						class="input"
						type="text" 
						:placeholder="commentInputTitle" 
						placeholder-style="color:#adb1b9;"
						focus
						v-model="queryComment.content"
						@blur="changeBtnType(0)"
						@confirm="commitComment"
					/>
				</view>
			</view>
			<!-- 显示功能按钮 -->
			<view class="uni-form-item uni-column btn btnBar" v-if="showBtnType == 0">
				<view class="iconfont icon-pinglun" style="margin-left: 75rpx;" @click="handleOpenComment"></view>
				<button class="shareBtn" plain style="border: 0 none; position: relative; left: -10rpx;" open-type="share"><view class="iconfont icon-fenxiang" style="margin-left: 220rpx;"></view></button>
			</view>
        </view>
		<uni-load-more :status="status"  :icon-size="16" :content-text="contentText" />
		<view style="padding-top: 70rpx;"></view>
    </view>
</template>

<script>
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	import Time from '../../common/Time.js';
    export default {
		components: {
			uniLoadMore
		},
        data() {
            return {
				currUserId: '',
                index: 0,
				banner: {},
				data: {
					id: '',
					userId: '',
				},
				userInfo: {},
				isFan: false,
				isFavorites: false,
				commentData: [],
				query: {
					currPage: 0,
					rows: 6,
					articleId: '',
					endCreatedTime: ''
				},
				totalPage: 0,
				showBtnType: 0,		// 0. 所有功能按钮 1. 评论按钮
				commentInputTitle: '写下你的评论...',
				queryComment: {
					content: '',
					articleId: '',
					parentId: ''
				},
				status: 'more',
				contentText: {
					contentdown: '上拉加载更多',
					contentrefresh: '加载中',
					contentnomore: '没有更多'
				}
            }
        },
		onLoad(event) {
			this.userInfo = uni.getStorageSync("userInfo")
			this.queryComment.articleId = event.id;
			this.data.id = event.id
			this.query.articleId = event.id;
			this.query.endCreatedTime = this.getTimeNow();
			this.currUserId = uni.getStorageSync("userInfo").id
			
			this.getDetail();
			this.getComment();
			this.getIsFavorites();
		},
		computed: {
			getFormatDate() {
				return (date) => Time.dateTimeformat(new Date(date).getTime())
			}
		},
        methods: {
			// 更改要显示的按钮
			changeBtnType(type){
				this.showBtnType = type;
			},
			// 上传评论数据
			async commitComment(){
				let ret = await this.$http({
					url: "/comment/add",
					method: 'POST',
					data: this.queryComment
				})
				let newComment = ret.data
				console.log(newComment);
				if (newComment.parentId == ''){
					this.commentData.unshift(newComment)
				}else {
					this.commentData.forEach(e => {
						if (e.id == newComment.parentId){
							e.children = e.children!=null?e.children:[];
							e.children.unshift(newComment);
						}
					})
				}
				this.changeBtnType(0);
			},
			// 回复文章处理
			handleOpenComment(){
				this.changeBtnType(1)
				this.commentInputTitle = '写下你的评论...';
				this.queryComment.parentId = '';
				this.queryComment.content = '';
			},
			// 获取当前时间
			getTimeNow(){
				let d = new Date()
				let format = d.getFullYear() + '-' + 
							(d.getMonth() + 1 < 10 ? "0" + (d.getMonth() + 1) : d.getMonth() + 1) + '-' + 
							(d.getDate()<10 ? "0" +d.getDate():d.getDate())+ ' ' + 
							(d.getHours()<10 ?"0"+d.getHours():d.getHours()) + ':' + 
							(d.getMinutes()<10 ? "0" + d.getMinutes() : d.getMinutes() ) + ':' + 
							(d.getSeconds()<10 ? "0" + d.getSeconds() : d.getSeconds() );
				return format
			},
			// 获取数据
			async getDetail() {
				console.log("hello");
				let ret = await this.$http({
					url: '/inetArticle/' + this.data.id.toString()
				})
				this.data = ret.data
				this.data.content = this.data.content.replace(/<img/g, '<img width="100%"')
			},
			// 获取评论数据
			async getComment() {
				if (this.query.currPage != 0 && this.query.currPage == this.totalPage) return;
				this.query.currPage++;
				
				this.status = 'loading'
				let ret = await this.$http({
					url: '/comment/',
					data: this.query
				})
				ret.data.dataList.forEach(e => {
					this.commentData.push(e)
				})
				this.totalPage = ret.data.totalPage
				
				if (this.totalPage != this.query.currPage) this.status = 'more'
				else this.status = 'noMore'
			},
			// 切换评论
            tapReply(e, idx) {
                if (this.index != idx) {
                    this.index = idx
                }else{
                    this.index=null
                }
            },
			// 楼中楼回复处理
			showCommInput(item){
				this.changeBtnType(1)
				this.commentInputTitle = "回复：" + item.username;
				this.queryComment.content = '';
				this.queryComment.parentId = item.id
			},
			// 删除评论
			async delReply(id){
				let ret = await this.$http({
					url: '/comment/del',
					data: {id: id}
				})
				if (ret.code == 0){
					this.commentData = []
					this.query = {
						currPage: 0,
						rows: 6,
						articleId: this.data.id,
						endCreatedTime: this.getTimeNow()
					}
					this.getComment();
				}
				
			}
        }
    }
</script>

<style lang="scss">

p{
	text-indent: 2em;
}
/* 底部分享 */
	.uni-share {
		/* #ifndef APP-NVUE */
		display: flex;
		flex-direction: column;
		/* #endif */
		background-color: #fff;
	}

	.uni-share-title {
		line-height: 60rpx;
		font-size: 24rpx;
		padding: 15rpx 0;
		text-align: center;
	}

	.uni-share-content {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 15px;
	}

	.uni-share-content-box {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		align-items: center;
		width: 200rpx;
	}

	.uni-share-content-image {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: center;
		align-items: center;
		width: 60rpx;
		height: 60rpx;
		overflow: hidden;
		border-radius: 10rpx;
	}

	.content-image {
		width: 60rpx;
		height: 60rpx;
	}

	.uni-share-content-text {
		font-size: 26rpx;
		color: #333;
		padding-top: 5px;
		padding-bottom: 10px;
		
		
	}

	.uni-share-btn {
		height: 90rpx;
		line-height: 90rpx;
		font-size: 14px;
		border-top-color: #f5f5f5;
		border-top-width: 1px;
		border-top-style: solid;
		text-align: center;
		color: #666;
	}

    .uni-comment-body {
		background-color: #FFFFFF;
        /* background: rgb(240, 240, 240); */
        padding: 0 10upx;
    }

    .uni-comment-face {
        background: #FFFFFF;
    }

    .uni-comment-content {
        /*    background: #EEEEEE;
    padding:0 10upx; */
    }
	
	view {
	    font-size: 28upx;
	    line-height: 1.8;
	}
	
	.banner {
	    height: 360upx;
	    overflow: hidden;
	    position: relative;
	    background-color: #ccc;
	}
	
	.uni-comment-replay-btn {
		background-color: rgb(240,240,240);
	}
	
	.banner-img {
	    width: 100%;
	}
	
	.banner-title {
	    max-height: 84upx;
	    overflow: hidden;
	    position: absolute;
	    left: 30upx;
	    bottom: 30upx;
	    width: 90%;
	    font-size: 32upx;
	    font-weight: 400;
	    line-height: 42upx;
	    color: white;
	    z-index: 11;
	}
	
	.article-meta {
	    padding: 20upx 40upx;
	    display: flex;
	    flex-direction: row;
	    justify-content: flex-start;
	    color: gray;
	}
	
	.article-text {
	    font-size: 26upx;
	    line-height: 50upx;
	    margin: 0 20upx;
	}
	
	.article-author,
	.article-time {
	    font-size: 30upx;
	}
	
	.article-content {
	    padding: 0 30upx;
	    overflow: hidden;
	    font-size: 30upx;
	    margin-bottom: 30upx;
	}
	
	.btn {
		position: fixed;
		bottom: 0;
		font-size: 25rpx;
		color: rgb(0,0,0);
		width: 750rpx;
		height: 70rpx;
		line-height: 10rpx;
		background: #E0E0E0;
		background-color: rgb(255,255,255);
	}
	
	.btnBar {
		display: flex; 
		flex-direction: row; 
	}
	
	/* 底部 */
	.bottom{
		flex-shrink: 0;
		display: flex;
		align-items: center;
		height: 70rpx;
		box-shadow: 0 -1px 3px rgba(0,0,0,.04); 
		position: relative;
		// z-index: 999;
		
		.input-box{
			position: fixed;
			bottom: 5rpx;
			display: flex;
			align-items: center;
			flex: 1;
			padding-left: 20rpx;
			width: 650rpx;
			height: 60upx;
			background: #f2f3f3;
			border-radius: 50px;
			
			.icon-huifu{
				font-size: 28upx;
				color: #909399;
				padding-right: 20rpx;
			}
			.input{
				
				font-size: 28upx;
				color: #303133;
			}
		}
		.shareBtn{
			border: 0 none;
			position: relative;
			left: -10rpx;
		}
	}
</style>
