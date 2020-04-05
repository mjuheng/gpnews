<template>
	<view>
<!-- 		<view class="banner" @click="goDetail(banner)">
			<image class="banner-img" :src="banner.cover"></image>
			<view class="banner-title">{{ banner.title }}</view>
		</view> -->
		<view class="uni-list">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in listData" :key="key" >
				<view v-if="value.type!=='ad'" class="uni-media-list" @click="getDetail(value)">
					<image class="uni-media-list-logo" :src="value.headImage" v-if="value.headImage != ''"></image>
					<view class="uni-media-list-body">
						<view class="uni-media-list-text-top">{{ value.title }}</view>
						<view class="uni-media-list-text-bottom">
							<text>{{ value.username }}</text>
							<text>{{ value.publishTime }}</text>
						</view>
					</view>
				</view>
                <!--  信息流广告 -->
<!--                <view v-if="value.type =='ad'" style="padding-left: 22rpx;">
                    <ad unit-id="adunit-01b7a010bf53d74e"></ad>
                </view> -->
			</view>
		</view>
		<uni-load-more :status="status"  :icon-size="16" :content-text="contentText" />
	</view>
</template>

<script>
import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';

export default {
	components: {
		uniLoadMore
	},
	data() {
		return {
			listData: [],
			reload: false,
			query: {
				currPage: 0,
				rows: 10,
				pubTime: '',
				status: '2',
				endPubTime: '',
			},
			totalPage: 0,
			status: 'more',
			contentText: {
				contentdown: '上拉加载更多',
				contentrefresh: '加载中',
				contentnomore: '没有更多'
			}
		};
	},
	onLoad() {
		this.query.endPubTime = this.getTimeNow();
		this.getBanner();
		this.getList();
	},
	onPullDownRefresh() {
		this.query.currPage= 0;
		this.query.endPubTime = this.getTimeNow();
		this.reload = true;
		this.getBanner();
		this.getList();
		uni.stopPullDownRefresh()
	},
	onReachBottom() {
		this.getList();
	},
	methods: {
		getBanner() {
			let data = {
				column: 'id,post_id,title,author_name,cover,published_at' //需要的字段名
			};
			uni.request({
				url: 'https://unidemo.dcloud.net.cn/api/banner/36kr',
				data: data,
				success: data => {
					uni.stopPullDownRefresh();
					if (data.statusCode == 200) {
						this.banner = data.data;
					}
				},
				fail: (data, code) => {
					console.log('fail' + JSON.stringify(data));
				}
			});
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
		async getList() {
			if (this.query.currPage != 0 && this.query.currPage == this.totalPage) return;
			this.query.currPage++;
			
			this.status = 'loading'
			let ret = await this.$http({
				url: '/article/pageByFan',
				data: this.query
			});
			this.listData = this.reload ? ret.data.dataList: this.listData.concat(ret.data.dataList)
			this.reload = false;
			this.totalPage = ret.data.totalPage
			// 判断是否没有数据
			if (this.totalPage == this.query.currPage) this.status = 'noMore'
			else this.status = 'more'
			// var data = {
			// 	column: 'id,post_id,title,author_name,cover,published_at' //需要的字段名
			// };
			// if (this.last_id) {
			// 	//说明已有数据，目前处于上拉加载
			// 	this.status = 'loading';
			// 	data.minId = this.last_id;
			// 	data.time = new Date().getTime() + '';
			// 	data.pageSize = 10;
			// }
			// uni.request({
			// 	url: 'https://unidemo.dcloud.net.cn/api/news',
			// 	data: data,
			// 	success: data => {
			// 		if (data.statusCode == 200) {
			// 			let list = this.setTime(data.data);
			// 			this.listData = this.reload ? list : this.listData.concat(list);
			// 			this.last_id = list[list.length - 1].id;
			// 			this.reload = false;
			// 		}
			// 	},
			// 	fail: (data, code) => {
			// 		console.log('fail' + JSON.stringify(data));
			// 	}
			// });
		},
		getDetail (e) {
			this.$http({url: "/article/addNum", data: {id: e.id}})
			uni.navigateTo({
				url: "/pages/details/details?id=" + e.id
			})
		},
		setTime: function(items) {
			var newItems = [];
			items.forEach(e => {
                //信息流中插入广告，微信限制一页只允许出现一个广告 
                if( this.listData.length==0 && newItems.length==5){
                    newItems.push({type:'ad'});
                }
				newItems.push({
					author_name: e.author_name,
					cover: e.cover,
					id: e.id,
					post_id: e.post_id,
					published_at: dateUtils.format(e.published_at),
					title: e.title
				});
			});
			return newItems;
		}
	}
};
</script>

<style>
.banner {
	height: 360upx;
	overflow: hidden;
	position: relative;
	background-color: #ccc;
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

.uni-media-list-logo {
	width: 180upx;
	height: 140upx;
}

.uni-media-list-body {
	height: auto;
	justify-content: space-around;
}

.uni-media-list-text-top {
	height: 74upx;
	font-size: 28upx;
	overflow: hidden;
}

.uni-media-list-text-bottom {
	display: flex;
	flex-direction: row;
	justify-content: space-between;
}
</style>
