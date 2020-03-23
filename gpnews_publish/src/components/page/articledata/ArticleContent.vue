<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 内容管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
                >批量删除</el-button>
				<el-input v-model="query.title" placeholder="标题" class="handle-input mr10"></el-input>
				<el-select v-model="query.status" placeholder="全部状态" class="handle-select mr10">
				    <el-option key="0" label="" value=""></el-option>
				    <el-option key="1" label="草稿" value="0"></el-option>
				    <el-option key="2" label="待审核" value="1"></el-option>
				    <el-option key="3" label="审核通过" value="2"></el-option>
				    <el-option key="4" label="审核失败" value="3"></el-option>
				</el-select>
                <channel v-model="query.categoryId" class="mr10"></channel>
				<el-date-picker
					@change="changeDate"
					end-placeholder="结束日期"
					range-separator="至"
					start-placeholder="开始日期"
					type="daterange"
					v-model="dateArr"
					value-format="yyyy-MM-dd"
					class="mr10"
				></el-date-picker>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                :data="data"
                border
                class="table"
                ref="dataTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column label="封面(查看大图)" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.headImage"
                            :preview-src-list="[scope.row.headImage]"
                        ></el-image>
                    </template>
                </el-table-column>
				<el-table-column label="标题" prop="title"></el-table-column>
				<el-table-column label="状态">
				  <template slot-scope="scope">
					<el-tag type="info" v-if="scope.row.status===0">草稿</el-tag>
					<el-tag v-if="scope.row.status===1">待审核</el-tag>
					<el-tag type="success" v-if="scope.row.status===2">审核通过</el-tag>
					<el-tag type="warning" v-if="scope.row.status===3">审核失败</el-tag>
					<el-tag type="danger" v-if="scope.row.status===4">已删除</el-tag>
				  </template>
				</el-table-column>
				<el-table-column label="最后修改时间" prop="modifiedTime"></el-table-column>
				<el-table-column label="发布时间" prop="publishTime"></el-table-column>

                <el-table-column label="操作" width="180" align="center">
					<template slot-scope="scope">
						<el-button
						    type="text"
						    icon="el-icon-edit"
						    @click="handleEdit(scope.row.id)"
							v-if="scope.row.status == 0 || scope.row.status == 3"
						>编辑</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.row.id)"
                        >删除</el-button>
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
export default {
    name: 'articleContent',
	components: {
		channel
	},
    data() {
        return {
            query: {
				userId: null,
				status: null,
				title: null,
				categoryId: null,
				beginPubTime: null,
				endPubTime: null,
                currPage: 1,
                rows: 10
            },
            data: [],
            multipleSelection: [],
            delList: [],
			dateArr: [],
            total: 0
        };
    },
    created() {
		// 获取用户id
		this.query.userId = local.getUser().id;
        this.getData();
    },
    methods: {
		// 获取文章列表数据
		async getData(){
		  let ret = await local.sendGet(this.ADMIN + '/article', this.query);
		  if (ret != null){
			this.data = ret.data.dataList;
			this.total = ret.data.totalCount;
		  }
		},
		// 多选操作
		handleSelectionChange(val) {
		    this.multipleSelection = val;
		},
		// 触发搜索按钮
		handleSearch() {
		    this.$set(this.query, 'currPage', 1);
		    this.getData();
		},
		// 分页导航
		handlePageChange(val) {
		    this.$set(this.query, 'currPage', val);
		    this.getData();
		},
		// 选择日期
		changeDate (dateArr) {
		  if (dateArr) {
			this.query.beginPubTime = dateArr[0]
			this.query.endPubTime = dateArr[1]
		  } else {
			this.query.beginPubTime = null
			this.query.endPubTime = null
		  }
		},
		// 筛选
		search () {
		  if (this.query.categoryId === '') this.query.categoryId = null
		  // 把页码换成1
		  this.query.currPage = 1
		  this.getData()
		},
		// 去编辑
		handleEdit (id) {
		  this.$router.push({ path: '/articlePublish', query: { id } })
		},
		delAllSelection() {
		    let ids = '';
		    const length = this.multipleSelection.length;
		    if (length <= 0){
		        this.$message.error('至少勾选一个选项')
		        return
		    }
		    for (let i = 0; i < length; i++){
		        ids += ',' + this.multipleSelection[i].id
		    }
		    ids = ids.substring(1)
		    this.handleDelete(ids)
		},
		// 删除
		async handleDelete (id) {
		  this.$confirm('您确定删除该文章吗？此操作不可撤销', '温馨提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		  }).then(async () => {
			let ret = await local.sendGet(this.ADMIN + '/article/del/' + id);
			if (ret != null){
			  this.getData()
			}
		  })
	
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
