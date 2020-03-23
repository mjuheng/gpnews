<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 粉丝列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
			

			  <div class="fans_list">
				<div :key="item.id" class="fans_item" v-for="item in data">
				  <el-avatar :size="80" :src="item.photo"></el-avatar>
				  <p style="margin-top: 15px;">{{item.username}}</p>
				  <el-button style="margin-top: 15px;" plain size="small" v-if="item.isFan" @click="delFan(item)">取消关注</el-button>
				  <el-button style="margin-top: 15px;" plain size="small" type="primary" v-else @click="addFan(item)">+关注</el-button>
				</div>
			  </div>
			  <!-- 分页 -->
			  <el-pagination
				:current-page="query.currPage"
				:page-size="query.rows"
				:total="total"
				@current-change="pager"
				background
				layout="prev, pager, next"
			  ></el-pagination>

        </div>
    </div>
</template>

<script>
import echarts from 'echarts'
import local from '@/utils/local';
export default {
    name: 'fanList',
    data() {
        return {
			data: [],
			query: {
				currPage: 1,
				rows: 21
			},
			total: 0
        };
    },
	mounted () {
		this.drawVisits()
	},
    created() {
		this.getData()
    },
    methods: {
		pager (newPage) {
		  this.query.currPage = newPage
		  this.getData()
		},
		// 关注用户
		async addFan(item) {
		  let ret = await local.sendGet(this.ADMIN + '/fan/addFan', {toUserId: item.id})
		  if (ret != null) { 
			item.isFan = !item.isFan
		  }
		},
		// 取消关注
		async delFan(item) {
		  let ret = await local.sendGet(this.ADMIN + '/fan/delFan', {toUserId: item.id})
		  if (ret != null) {
			item.isFan = !item.isFan
		  } 
		},
		async getData () {
		  let ret = await local.sendGet(this.ADMIN + '/fan/pageFans', this.reqParams)
		  if (ret != null){
			this.data = ret.data.dataList
			this.total = ret.data.totalCount
		  }
		}
    }
};
</script>

<style scoped lang='less'>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
.fans_item {
  width: 150px;
  height: 190px;
  padding-top: 20px;
  border: 1px dashed #ddd;
  display: inline-block;
  margin-right: 20px;
  margin-bottom: 20px;
  text-align: center;
}
</style>
