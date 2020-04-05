<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades">任务信息</i> 
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" @click="getData">刷新</el-button>
            </div>
            <el-table
                :data="data"
                border
                class="table"
                ref="dataList"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column prop="name" label="任务名"></el-table-column>
                <el-table-column prop="cron" label="cron表达式"></el-table-column>
                <el-table-column label="状态">
					<template slot-scope="scope">
						<el-tag type="info" v-if="scope.row.status == 0">停止</el-tag>
					    <el-tag v-if="scope.row.status == 1">运行中</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="停止">
					<template slot-scope="scope">
						<el-switch
						  v-model="scope.row.isStop"
						  inactive-color="#13ce66"
						  active-color="#ff4949"
						  @change="saveStop(scope.row)">
						</el-switch>
					</template>
				</el-table-column>
                <el-table-column prop="modifiedTime" label="更新时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
						<el-button
						    type="text"
						    icon="el-icon-edit"
						    @click="handleEdit(scope.row)"
						>编辑</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-video-play"
                            @click="runTask(scope.row.id)"
                        >运行</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
		<!-- 编辑弹出框 -->
        <el-dialog title="修改" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="90px">
                <el-form-item label="任务名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="cron表达式">
                    <el-input v-model="form.cron"></el-input>
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
    name: 'TaskInfo',
    data() {
        return {
            data: [],
			
			editVisible: false,
			form: {
				id: null,
				name: null,
				cron: null
			},
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取用户数据
        async getData() {
            let ret = await local.sendGet(this.ADMIN + '/task/info')
            if (ret != null){
                this.data = ret.data
            }
        },
		// 运行任务
		async runTask(id){
			let ret = await local.sendGet(this.ADMIN + '/task/info/runTask', {id: id})
			if (ret != null) this.getData()
		},
		// 保存状态变更
		saveStop(row){
			local.sendPost(this.ADMIN + '/task/info/update', row)
		},
		// 保存编辑
		async saveEdit() {
			if (typeof this.form.name == 'undefined' || this.form.name == ''){
				this.$message.error("任务名不能为空")
				return
			}
			let ret = await local.sendPost(this.ADMIN + '/task/info/update', this.form)
			if (ret != null){
				this.editVisible = false;
				this.getData()
			}
		},
		// 编辑操作
		handleEdit(row) {
			Object.assign(this.form, row)
		    this.editVisible = true;
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
