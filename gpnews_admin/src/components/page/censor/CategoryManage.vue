<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 分类管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
		
		<div class="container">
		    <div class="handle-box">
				<el-button type="primary" icon="el-icon-document-add" @click="openAddDialog">新增</el-button>
		        <el-button
		            type="primary"
		            icon="el-icon-delete"
		            class="handle-del mr10"
		            @click="delAllSelection"
		        >批量删除</el-button>
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
				<el-table-column prop="name" label="名称"></el-table-column>
				<el-table-column prop="createdTime" label="创建时间"></el-table-column>
				<el-table-column prop="modifiedTime" label="最后修改时间"></el-table-column>
				
				<el-table-column label="操作" width="180" align="center">
					<template slot-scope="scope">
						<el-button
							type="text"
							icon="el-icon-edit"
							@click="handleEdit(scope.$index, scope.row)"
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
		<!-- 编辑弹出框 -->
		<el-dialog :title="dialogTitle" :visible.sync="editVisible" width="30%">
			<el-form ref="form" :model="form" label-width="70px">
				<el-form-item label="分类名" prop="username">
					<el-input v-model="form.name"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="editVisible = false">取 消</el-button>
				<el-button type="primary" @click="saveEdit">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import local from '@/utils/local';
export default {
    name: 'categoryManage',
    data() {
        return {
            query: {
                currPage: 1,
                rows: 10
            },
			form: {
				id: null,
				name: null
			},
			multipleSelection: [],
            data: [],
            total: 0,
			editVisible: false,
			dialogTitle: '新增'
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取基础数据
        async getData() {
            let ret = await local.sendGet(this.ADMIN + '/category', this.query)
            if (ret != null){
                this.data = ret.data.dataList
                this.total = ret.data.totalCount
            }
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'currPage', val);
            this.getData();
        },
		// 删除操作
		async handleDelete(id) {
		    // 二次确认删除
		    this.$confirm('确定要删除吗，此操作不可撤销？', '提示', {
		        type: 'warning'
		    })
		    .then(async () => {
		        let ret = await local.sendGet(this.ADMIN + '/category/del', {id: id})
		        if (ret != null){
		            this.getData()()
		        }
		    })
		    .catch(() => {});
		},
		// 多选操作
		handleSelectionChange(val) {
		    this.multipleSelection = val;
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
		// 打开新增模态框
		openAddDialog(){
			this.editVisible = true
			this.dialogTitle = '新增'
			this.form = {}
		},
		// 编辑操作
		handleEdit(index, row) {
			Object.assign(this.form, row);
			this.dialogTitle = '更新'
		    this.editVisible = true;
		},
		// 保存编辑
		async saveEdit() {
			if (typeof this.form.name == 'undefined' || this.form.name == ''){
				this.$message.error("分类名不能为空")
				return
			}
			let ret = await local.sendPost(this.ADMIN + '/category/addOrUpdate', this.form)
			if (ret != null){
				this.getData()
				this.editVisible = false; 
			}
			
		},
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
