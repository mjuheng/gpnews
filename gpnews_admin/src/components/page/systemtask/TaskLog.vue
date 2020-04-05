<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades">任务记录</i> 
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" class="mr10" @click="getData">刷新</el-button>
				<el-input v-model="query.name" placeholder="任务名" class="handle-input mr10"></el-input>
				<el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                :data="data"
                border
                class="table"
                ref="dataList"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column prop="taskInfoName" label="任务名"></el-table-column>
                <el-table-column prop="startTime" label="开始时间"></el-table-column>
                <el-table-column prop="endTime" label="结束时间"></el-table-column>
                <el-table-column prop="description" label="备注"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
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
export default {
    name: 'TaskLog',
    data() {
        return {
            query: {
                name: '',
                currPage: 1,
                rows: 10
            },
            data: [],
            total: 0,
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取用户数据
        async getData() {
            let ret = await local.sendGet(this.ADMIN + '/task/monitor', this.query)
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
        // 删除操作
        async handleDelete(id) {
            // 二次确认删除
            this.$confirm('确定要删除吗，此操作不可撤销？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                let ret = await local.sendGet(this.ADMIN + '/task/monitor', {id: id})
                if (ret != null) this.getData()
            })
            .catch(() => {});
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'currPage', val);
            this.getData();
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
