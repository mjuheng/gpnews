<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 角色信息
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
                <el-input v-model="query.name" placeholder="角色名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
				<el-button type="primary" icon="el-icon-refresh-left" @click="reloadData">刷新</el-button>
            </div>
            <el-table
                :data="roleList"
                border
                class="table"
                ref="roleList"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="角色名"></el-table-column>
                <el-table-column prop="description" label="备注"></el-table-column>
                <el-table-column prop="createdTime" label="创建时间"></el-table-column>
                <el-table-column prop="modifiedTime" label="修改时间"></el-table-column>
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
            <el-form ref="form" :model="form" label-width="70px" :rules="roleRules">
                <el-form-item label="角色名" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>

				<el-form-item label="父角色">
				  <el-select :value="roleParentChecked">
					<el-option :value="roleParentChecked" :label="roleParentChecked" class="selectHeight">
						<el-tree
						  ref="roleTree"
						  node-key="id"
						  :data="roleTree"
						  :props="roleTreeProps"
						  show-checkbox
						  check-strictly
						  :default-checked-keys="defRoleCheckedKeys"
						  @check-change="handleRoleTreeCheck"
						  default-expand-all
						></el-tree>
					</el-option>
				  </el-select>
				</el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>
                <el-form-item label="权限">
				  <el-select :value="permChecked" width="100%">
					<el-option :value="permChecked" :label="permChecked" class="selectHeight">
						<el-tree
						  ref="permTree"
						  node-key="id"
						  :data="permList"
						  :props="treeProps"
						  show-checkbox
						  @check-change="handleTreeCheckChange"
						  :default-checked-keys="defPermCheckedKeys"
						  default-expand-all
						></el-tree>
					</el-option>
				  </el-select>
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
    name: 'roleManage',
    data() {
        return {
			dialogTitle: '',
			permChecked: '',
			roleParentChecked: '',
            query: {
                name: '',
                currPage: 1,
                rows: 10
            },
			defRoleCheckedKeys: [],
			defPermCheckedKeys: [],
            roleList: [],
			roleTree: [],
			parentRole: {},
			permList: [],
			
			treeProps: {
				label: 'cname',
				children: 'children'
			},
			roleTreeProps: {
				label: 'name'
			},
			
            multipleSelectionRole: [],
            delList: [],
            editVisible: false,
            total: 0,
            form: {
				permId: ''
			},
			roleRules: {
				name: [{ required: true, message: '角色名不能为空', trigger: 'blur' }],
			},
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
        this.getRole();
		this.getPerm();
		this.getRoleTree();
    },
    methods: {
		// 重新获取数据
		reloadData(){
			this.getRole();
			this.getRoleTree();
		},
		// 打开新增界面模态框
		openAddDialog(){
			this.editVisible = true
			this.dialogTitle = '新增'
		},
		// 获取角色的属性结构
		async getRoleTree(){
			let ret = await local.sendGet(this.ADMIN + '/role/getTree')
			if (ret != null) this.roleTree = ret.data
		},
		// 获取权限列表
		async getPerm() {
			let ret = await local.sendGet(this.ADMIN + '/perm/getTree')
			if (ret != null) this.permList = ret.data
		},
        // 获取角色数据
        async getRole() {
            let ret = await local.sendGet(this.ADMIN + '/role', this.query)
            if (ret != null){
                this.roleList = ret.data.dataList
                this.total = ret.data.totalCount
            }
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, 'currPage', 1);
            this.getRole();
        },
        // 删除操作
        async handleDelete(id) {
            // 二次确认删除
            this.$confirm('确定要删除吗，此操作不可撤销？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                let ret = await local.sendGet(this.ADMIN + '/role/delRole', {id: id})
                if (ret != null){
                    this.reloadData()
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
        // 编辑操作
        async handleEdit(index, row) {
            this.form = JSON.parse(JSON.stringify(row))
			this.form.permId = ''
            this.editVisible = true;
			let ret = await local.sendGet(this.ADMIN + '/role/' + row.parentId)
			if (ret != null && ret.data != null){
				this.form.parentId = ret.data.id
				this.roleParentChecked = ret.data.name
			}
			this.defPermCheckedKeys = row.permId == null?[]:row.permId.split(";")
			
			// 树形控件设置默认的勾选状态，数组一定要用 = 进行赋值，push赋值不会改变
			let array = [this.form.parentId]
			this.defRoleCheckedKeys = array
			
			this.dialogTitle = '编辑'
        },
        // 保存编辑
        async saveEdit() {
			this.$refs.form.validate(async valid => {
				if (valid){
					let ret = await local.sendPost(this.ADMIN + 'role/updateRole', this.form)
					if (ret != null) {
						this.editVisible = false
						this.reloadData()
					}
				}
			})
        },
		// 清空表单数据
		clearForm(){
			this.form = {
				permId: ''
			}
			this.$refs.roleTree.setCheckedNodes([])
			this.$refs.permTree.setCheckedNodes([])
			this.permChecked = ''
			this.roleParentChecked = ''
		},
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'currPage', val);
            this.getRole();
        },
		// 监听树形控件的改变
		handleTreeCheckChange(data, checked, indeterminate){
			if (checked == true){
				this.permChecked += data.cname + ";"
				this.form.permId += data.id + ";"
			}else{
				this.permChecked = this.permChecked.replace(data.cname + ";", "")
				this.form.permId = this.form.permId.replace(data.id + ";", "")
			}
		},
		handleRoleTreeCheck(data, checked, node){
			if (checked) {
				this.$refs.roleTree.setCheckedNodes([data])
				this.roleParentChecked = data.name
				this.form.parentId = data.id
			}
			if (this.$refs.roleTree.getCheckedNodes().length == 0){
				this.roleParentChecked = ''
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
.selectHeight{
	height: 100%;
}
</style>

 