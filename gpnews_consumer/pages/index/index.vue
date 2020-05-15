<template>
    <view>
		<!-- 顶部选项卡 -->
		<scroll-view id="nav-bar" class="nav-bar" scroll-x scroll-with-animation :scroll-left="scrollLeft">
			<view 
				v-for="(item) in categoryList" :key="item.id"
				class="nav-item"
				:class="{current: item.id === query.categoryId}"
				@click="changeCategory(item)"
			>{{item.name}}</view>
		</scroll-view>


		<view class="uni-list">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in listData" :key="key" >
				<view v-if="value.type!=='ad'" class="uni-media-list" @click="getDetail(value)">
					<image class="uni-media-list-logo" :src="value.headImage" v-if="query.categoryId != -1 && value.headImage != ''"></image>
					<view class="uni-media-list-body">
						<view class="uni-media-list-text-top">{{ value.title }}</view>
						<view class="uni-media-list-text-bottom">
							<text>{{ value.username }}</text>
							<text>{{ getFormatDate(value.publishTime) }}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<uni-load-more :status="status"  :icon-size="16" :content-text="contentText" />
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
                banner: {},
                listData: [],
                reload: false,
				categoryList: [
					{
						"id": '',
						"name": "全部",
					},
					{
						"id": '-1',
						"name": "全网"
					}
				],
				query: {
					currPage: 0,
					rows: 10,
					categoryId: '',
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
            }
        },
		computed: {
			getFormatDate() {
				return (date) => Time.dateTimeformat(new Date(date).getTime())
			}
		},
        onLoad() {
			this.query.endPubTime = this.getTimeNow();
			this.getBanner();
			this.getCategoryList()
			this.getList();
        },
        onPullDownRefresh() {
			this.query.currPage= 0;
			this.query.endPubTime = this.getTimeNow();
			this.reload = true;
			this.getBanner();
			this.getList();
			uni.stopPullDownRefresh();
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
			// 获取分类列表
			async getCategoryList(){
				console.log("hello");
				let ret = await this.$http({
					url: '/category/all'
				})
				ret.data.forEach(e => {
					this.categoryList.push(e);
				})
			},
			// 更改分类的处理
			changeCategory(e){
				this.query.categoryId = e.id;
				uni.startPullDownRefresh();
			},
            getBanner() {
                let data = {
                    column: "id,post_id,title,author_name,cover,published_at" //需要的字段名
                };
                uni.request({
                    url: 'https://unidemo.dcloud.net.cn/api/banner/36kr',
                    data: data,
                    success: (data) => {
                        uni.stopPullDownRefresh();
                        if (data.statusCode == 200) {
                            this.banner = data.data;
                        }
                    },
                    fail: (data, code) => {
                        console.log('fail' + JSON.stringify(data));
                    }
                })
            },
            async getList() {
				if (this.query.currPage != 0 && this.query.currPage == this.totalPage) return;
				this.query.currPage++;
				
				this.status = 'loading'
				
				let url = '/article'
				if (this.query.categoryId == -1) url = '/inetArticle'
				let ret = await this.$http({
					url: url,
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
				if (this.query.categoryId != -1){
					this.$http({url: "/article/addNum", data: {id: e.id, type: 1}})
					uni.navigateTo({
						url: "/pages/details/details?id=" + e.id
					})
				}else{
					uni.navigateTo({
						url: "/pages/details/inet-details?id=" + e.id,
						fail(e) {
							console.log(e);
						}
					})
				}
            }
        },
    }
</script>

<style lang="scss">
	/* 顶部tabbar */
	.nav-bar{
		position: relative;
		z-index: 10;
		height: 90upx;
		white-space: nowrap;
		box-shadow: 0 2upx 8upx rgba(0,0,0,.06);
		background-color: #fff;
		.nav-item{
			display: inline-block;
			width: 150upx;
			height: 90upx;
			text-align: center;
			line-height: 90upx;
			font-size: 30upx;
			color: #303133;
			position: relative;
			&:after{
				content: '';
				width: 0;
				height: 0;
				border-bottom: 4upx solid #007aff;
				position: absolute;
				left: 50%;
				bottom: 0;
				transform: translateX(-50%);
				transition: .3s;
			}
		}
		.current{
			color: #007aff;
			&:after{
				width: 50%;
			}
		}
	}
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
