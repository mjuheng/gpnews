<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 系统日志
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.username" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-input v-model="query.opt" placeholder="操作" class="handle-input mr10"></el-input>
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
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="operation" label="操作"></el-table-column>
                <el-table-column prop="time" label="用时(毫秒)"></el-table-column>
                <el-table-column prop="method" label="方法"></el-table-column>
                <el-table-column prop="ip" label="ip地址"></el-table-column>
                <el-table-column prop="createdTime" label="访问时间"></el-table-column>

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
    name: 'systemLog',
    data() {
        return {
            query: {
                username: '',
                opt: '',
                currPage: 1,
                rows: 10
            },
            data: [],
            multipleSelection: [],
            delList: [],
            total: 0
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取基础数据
        async getData() {
            let ret = await local.sendGet(this.ADMIN + '/log', this.query)
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
            this.$confirm('确定要强制选中用户下线吗？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                let ret = await local.sendGet(this.ADMIN + '/log/delLog', {id: id})
                if (ret != null){
                    this.getData()
                }
            })
            .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelectionUser = val;
        },
        delAllSelection() {
            let ids = '';
            const length = this.multipleSelectionUser.length;
            if (length <= 0){
                this.$message.error('至少勾选一个选项')
                return
            }
            for (let i = 0; i < length; i++){
                ids += ',' + this.multipleSelectionUser[i].id
            }
            ids = ids.substring(1)
            this.handleDelete(ids)
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
