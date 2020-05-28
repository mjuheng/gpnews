<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 待审文章
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
		
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.username" placeholder="发布人" class="handle-input mr10"></el-input>
				<el-input v-model="query.title" placeholder="标题" class="handle-input mr10"></el-input>
				<channel v-model="query.categoryId" class="mr10"></channel>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
				<el-button type="primary" icon="el-icon-refresh-left" @click="getData">刷新</el-button>
            </div>
            <el-table
                :data="data"
                border
                class="table"
                ref="dataTable"
                header-cell-class-name="table-header"
            >
                <el-table-column prop="title" label="标题"></el-table-column>
                <el-table-column label="封面(查看大图)" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.headImage"
                            :preview-src-list="[scope.row.headImage]"
                        >
							<div slot="error">
								<img src="@/assets/error.png" />
							</div>
						</el-image>
                    </template>
                </el-table-column>
				<el-table-column prop="categoryName" label="分类"></el-table-column>
				<el-table-column prop="username" label="作者"></el-table-column>
				<el-table-column prop="modifiedTime" label="最后修改时间"></el-table-column>
				
                <el-table-column label="操作" width="180" align="center">
					<template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-zoom-in"
                            @click="handleDetail(scope.row)"
                        >查看</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-circle-check"
                            @click="handleOpt(scope.row.id, 2)"
                        >通过</el-button>
						<el-button
						    type="text"
						    icon="el-icon-circle-close"
						    class="red"
						    @click="openRefuseDialog(scope.row.id)"
						>拒绝</el-button>
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
		<!-- 文章详情抽屉 -->
		<el-drawer
		  :title="articleDetail.title"
		  :withHeader='false'
		  :visible.sync="articleDetailVisible"
		  :append-to-body='true'
		  :show-close='false'
		  custom-class='a'
		  size="30%"
		  >
			<div v-html="articleDetail.content" />
		</el-drawer>
    </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import local from '@/utils/local';
import channel from '@/components/common/my-channel.vue'
export default {
    name: 'articleReview',
	components: {
		channel,
		mavonEditor
	},
    data() {
        return {
            query: {
                username: '',
				title: '',
				categoryId: '',
				status: '1',
                currPage: 1,
                rows: 10
            },
			articleDetail: {},
            data: [],
            total: 0,
			articleDetailVisible: false
        };
    },
    created() {
        this.getData();
    },
    methods: {
		// 查看文章内容
		async handleDetail(row){
			this.$router.push({path: 'articleDetail', query: {id: row.id}})
		},
        // 获取基础数据
        async getData() {
            let ret = await local.sendGet(this.ADMIN + '/article', this.query)
            if (ret != null){
                this.data = ret.data.dataList
                this.total = ret.data.totalCount
            }
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, 'currPage', 1);
            this.getData();
        },
		// 处理审批结果
        async handleOpt(id, status, comment){
			let params = {
				id: id,
				status: status,
				comment: comment
			}
			let ret = await local.sendGet(this.ADMIN + '/article/changeStatus', params)
			if (ret != null) this.getData()
		},
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'currPage', val);
            this.getData();
        },
		// 弹出拒绝的备注信息
		openRefuseDialog(id) {
			this.$prompt('请输入拒绝通过原因', '提示', {
			  confirmButtonText: '确定',
			  cancelButtonText: '取消',
			}).then(({ value }) => {
			  this.handleOpt(id, 3, value)
			});
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
