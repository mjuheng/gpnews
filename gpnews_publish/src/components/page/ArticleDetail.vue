<template>
	<div>
		<div class="crumbs">
		    <el-breadcrumb separator="/">
		        <el-breadcrumb-item>
		            <i class="el-icon-lx-cascades"></i> 文章详情
		        </el-breadcrumb-item>
		    </el-breadcrumb>
		</div>
		<div class="container">
			<h1>{{ data.title }}</h1>
			<div v-html="data.content" style="margin-top: 20px;"></div>
		</div> 
	</div>
</template>

<script>
import local from '@/utils/local';
export default {
	name: 'articleDetail',
	data() {
		return {
			data: {},
			articleId: '',
			query: {
				currPage: 1,
				rows: 1,
				status: '1'
			}
		}
	},
	mounted() {
		this.articleId = this.$route.query.id
		this.getData()
	},
	watch: {
		$route(to, from){
			this.articleId = this.$route.query.id
			this.getData()
		}
	},
	methods: {
		async getData(){
			let ret = await local.sendGet(this.ADMIN + '/article/' + this.articleId)
			if (ret != null){
				this.data = ret.data
			}
		},
		// 处理审批结果
		async handleOpt(id, status){
			let params = {
				id: id,
				status: status
			}
			let ret = await local.sendGet(this.ADMIN + '/article/changeStatus', params)
			if (ret != null) this.getNextData()
		},
		// 获取下一个待审批数据
		async getNextData(){
			let ret = await local.sendGet(this.ADMIN + '/article', this.query)
			if (ret != null){
				if (ret.data.dataList.length > 0){
					this.articleId = ret.data.dataList[0].id
					this.getData()
				}else {
					this.$message.error("无更多待审批数据")
					this.data = {}
				}
			}
		}
	},
}
</script>
<style>
	p {
		text-indent: 2em;
	}
</style>
