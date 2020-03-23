<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 评论管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
		<div class="container">
		<div class="handle-box">
			<el-input v-model="query.title" placeholder="标题" class="handle-input mr10"></el-input>
			<el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
		</div>
		  <el-table
		      :data="data"
		      border
		      class="table"
		      ref="dataTable"
		      header-cell-class-name="table-header"
		  >
			<el-table-column label="标题" prop="title" width="400"></el-table-column>
			<el-table-column label="阅读数" prop="readNum"></el-table-column>
			<el-table-column label="评论数" prop="commentNum"></el-table-column>
			<el-table-column label="状态" align="center">
			    <template slot-scope="scope">
			        <el-tag type="success" v-if="scope.row.isComment==1">正常</el-tag>
			        <el-tag type="danger" v-if="scope.row.isComment==0">锁定</el-tag>
			    </template>
			</el-table-column>
			<el-table-column label="操作" width="120">
			  <template slot-scope="scope">
				<el-button
				  @click="toggleStatus(scope.row)"
				  size="small"
				  type="danger"
				  v-if="scope.row.isComment"
				>关闭评论</el-button>
				<el-button @click="toggleStatus(scope.row)" size="small" type="success" v-else>打开评论</el-button>
			  </template>
			</el-table-column>
		  </el-table>
		  <div class="pagination">
		      <el-pagination
		          background
		          layout="total, prev, pager, next"
		          :current-page="query.currPage"
		          :page-size="query.rows"
		          :total="total"
		          @current-change="handlePageChange"
		      ></el-pagination>
		  </div>
		</div>
    </div>
</template>

<script>
import local from '@/utils/local';
import channel from '@/components/common/my-channel.vue'
import selectedImage from '@/components/common/my-image.vue'
export default {
    name: 'userManage',
    data() {
        return {
			query: {
				title: null,
				currPage: 1,
				rows: 20
			},
			data: [],
			total: 0
        };
    },
    created() {
	  this.getData()
    },
    methods: {
		// 切换 评论状态  正常  关闭
		toggleStatus (row) {
		  // row 有 id  comment_status
		  const text = row.isComment
			? '您确认关闭该文章的评论功能吗？如果关闭用户将不能对该文章进行评论'
			: '您确认打开评论功能吗？'
		  // 打开对话框
		  this.$confirm(text, '温馨提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		  }).then(async () => {
			  let params = {
				id: row.id, 
				isComment: !row.isComment
			  }
			  let ret = await local.sendPost(this.ADMIN + '/article/updateCommStatus', params)
			  if (ret != null){
				row.isComment = !row.isComment
			  }
			}
		  )
		},
		// 获取文章数据
		async getData () {
		  let ret = await local.sendGet(this.ADMIN + '/article', this.query)
		  this.data = ret.data.dataList
		  this.total = ret.data.totalCount
		},
		// 触发搜索按钮
		handleSearch() {
		    this.$set(this.query, 'currPage', 1);
		    this.getData();
		},
		// 分页函数
		handlePageChange (newPage) {
		  // 修改当前的页码为新的页码
		  this.query.currPage = newPage
		  // 重新获取数据
		  this.getData()
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
</style>
