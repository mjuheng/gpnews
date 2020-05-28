<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户信息
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
                <el-select v-model="query.isLock" placeholder="状态" class="handle-select mr10">
                    <el-option key="0" label="" value=""></el-option>
                    <el-option key="1" label="锁定" value="1"></el-option>
                    <el-option key="2" label="正常" value="0"></el-option>
                </el-select>
                <el-input v-model="query.username" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-unlock" @click="changeLock(false)">解锁</el-button>
                <el-button type="primary" icon="el-icon-lock" @click="changeLock(true)">锁定</el-button>
            </div>
            <el-table
                :data="userList"
                border
                class="table"
                ref="userList"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="phone" label="手机号"></el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.photo"
                            :preview-src-list="[scope.row.photo]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column label="状态" align="center">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.isLock==0">正常</el-tag>
                        <el-tag type="danger" v-if="scope.row.isLock==1">锁定</el-tag>
                    </template>
                </el-table-column>

                <el-table-column prop="createdTime" label="注册时间"></el-table-column>
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
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
				<el-form-item label="密码" prop="password" v-if="typeof form.id == 'undefined'">
					<el-input type="password" v-model="form.password"></el-input>
				</el-form-item>
				<el-form-item label="角色">
				  <el-select :value="roleChecked" width="100%">
					<el-option :value="roleChecked" :label="roleChecked" class="selectHeight">
						<el-tree
						  ref="roleData"
						  node-key="id"
						  :data="roleData"
						  :props="roleTreeProps"
						  show-checkbox
						  @check-change="handleTreeCheckChange"
						  :default-checked-keys="defRoleCheckedKeys"
						  default-expand-all
						  :check-strictly = "true"
						></el-tree>
					</el-option>
				  </el-select>
				</el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="form.phone"></el-input>
                </el-form-item>
				<el-form-item label="锁定">
					<el-switch v-model="form.isLock"></el-switch>
				</el-form-item>
                <el-form-item label="头像">
                    <el-upload
                        :on-success="handleSuccess"
                        :show-file-list="false"
                        :action='this.FILE + "/image/upload"'
                        name="image"
                    >
                    <img :src="form.photo" class="avatar" v-if="form.photo" width="100%" height="100%"/>
                    </el-upload>
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
    name: 'userManage',
    data() {
        return {
			dialogTitle: '',
            query: {
                username: '',
                isLock: '',
                currPage: 1,
                rows: 10
            },
            userList: [],
            multipleSelectionUser: [],
            delList: [],
            editVisible: false,
            total: 0,
            form: {
				roleId: ''
			},
            id: -1,
            formRules: {
                username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
            },
			
			roleChecked: '',
			roleData: [],
			roleTreeProps: {
				label: 'name'
			},
			defRoleCheckedKeys: [],
        };
    },
	watch: {
		editVisible(value, old){
			if (value == false){
				this.clearForm();
			}
		}
	},
    created() {
        this.getUser();
		this.getRoleData()
    },
    methods: {
		// 获取角色数据
		async getRoleData(){
			let ret = await local.sendGet(this.ADMIN + '/role/getTree')
			this.roleData = ret.data
		},
		clearForm(){
			this.form = {
				roleId: ''
			}
			this.$refs.roleData.setCheckedNodes([])
			this.roleChecked = ''
		},
		handleTreeCheckChange(data, checked, indeterminate){
			if (checked == true){
				this.roleChecked += data.name + ";"
				this.form.roleId += data.id + ";"
			}else{
				this.roleChecked = this.roleChecked.replace(data.name + ";", "")
				this.form.roleId = this.form.roleId.replace(data.id + ";", "")
			}
		},
		// 打开新增模态框
		openAddDialog(){
			this.editVisible = true
			this.dialogTitle = '新增'
			this.form = {
				roleId: '',
			}
		},
        // 获取用户数据
        async getUser() {
            let ret = await local.sendGet(this.ADMIN + '/user', this.query)
            if (ret != null){
                this.userList = ret.data.dataList
                this.total = ret.data.totalCount
            }
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, 'currPage', 1);
            this.getUser();
        },
        handleSuccess (res) {
            // 预览
			this.$set(this.form, "photo", res.data)
        },
        // 删除操作
        async handleDelete(id) {
            // 二次确认删除
            this.$confirm('确定要删除吗，此操作不可撤销？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                let ret = await local.sendGet(this.ADMIN + '/user/delUser', {id: id})
                if (ret != null){
                    this.getUser()
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
        async changeLock(isLock){
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
            let ret = await local.sendGet(this.ADMIN + '/user/changeLock', {id: ids, isLock: isLock})
            if (ret != null) this.getUser()
        },
        // 编辑操作
        async handleEdit(index, row) {
			this.form = row
			this.form.roleId = ''
			this.dialogTitle = '编辑'
            this.editVisible = true;
			// 设置角色树的勾选状态
			let ret = await local.sendGet(this.ADMIN + '/role/getByUserId', {userId: row.id})
			let array = []
			for (let i = 0; i < ret.data.length; i++){
				array.push(ret.data[i].role_id)
			}
			this.defRoleCheckedKeys = array
        },
        // 保存编辑
        async saveEdit() {
			if (typeof this.form.username == 'undefined' || this.form.username == '' || typeof this.form.password == 'undefined' || this.form.password == ''){
				this.$message.error("名字与密码不能为空")
				return
			}
			let ret = await local.sendPost(this.ADMIN + '/updateUser', this.form)
			if (ret != null){
				this.editVisible = false;    
			}
			
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'currPage', val);
            this.getUser();
        }
    }
};
</script>

<style scoped lang='less'>
.selectHeight{
	height: 100%;
}
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
