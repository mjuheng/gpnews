<template>
	<view>
		<uni-search-bar radius="100" v-model="query.title" placeholder="搜索" @confirm="search" />
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
		<uni-load-more :status="status"  :icon-size="16" />
	</view>
</template>

<script>
	import uniSearchBar from '@/components/uni-search-bar/uni-search-bar.vue'
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	export default {
		components: {
			uniSearchBar,
			uniLoadMore
		},
		data() {
			return {
				listData: [],
				reload: false,
				query: {
					currPage: 0,
					rows: 10,
					title: '',
					endCreatedTime: '',
				},
				totalPage: 0,
				status: 'more',
			}
		},
		onLoad() {
			this.query.endCreatedTime = this.getTimeNow();
			this.getList();
		},
		onPullDownRefresh() {
			this.query.title = '';
			this.query.currPage= 0;
			this.query.endCreatedTime = this.getTimeNow();
			this.reload = true;
			this.getList();
			uni.stopPullDownRefresh()
		},
		onReachBottom() {
			this.getList();
		},
		methods: {
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
			search(){
				this.query.currPage= 0;
				this.reload = true;
				this.getList();
			},
			async getList() {
				if (this.query.currPage != 0 && this.query.currPage == this.totalPage) return;
				this.query.currPage++;
				
				this.status = 'loading'
				let ret = await this.$http({
					url: '/favorites',
					data: this.query
				});
				this.listData = this.reload ? ret.data.dataList: this.listData.concat(ret.data.dataList)
				this.reload = false;
				this.totalPage = ret.data.totalPage
				// 判断是否没有数据
				if (this.totalPage == this.query.currPage) this.status = 'noMore'
				else this.status = 'more'
			},
			getDetail (e) {
				this.$http({url: "/article/addNum", data: {id: e.id}})
				uni.navigateTo({
					url: "/pages/details/details?id=" + e.id
				})
			},
		}
	}
</script>

<style>
	
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
