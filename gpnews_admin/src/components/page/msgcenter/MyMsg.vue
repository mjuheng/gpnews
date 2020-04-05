<template>
    <div class="my-msg">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 我的消息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-tabs v-model="message" @tab-click="changeTab">
                <el-tab-pane :label="`未读消息`" name="unread">
                    <el-table :data="data" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template slot-scope="scope">
                                <span class="message-title" @click="openMsg(scope.row)">{{scope.row.title}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createdTime" width="180"></el-table-column>
                        <el-table-column width="120">
                            <template slot-scope="scope">
                                <el-button size="small" @click="handleRead(scope.row.id)">标为已读</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="handle-row">
                        <el-button type="primary" @click="updateCurrPage('read')">本页标为已读</el-button>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`已读消息`" name="read">
                    <template v-if="message === 'read'">
                        <el-table :data="data" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title" @click="openMsg(scope.row)">{{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createdTime" width="150"></el-table-column>
                            <el-table-column width="120">
                                <template slot-scope="scope">
                                    <el-button type="danger" @click="handleDel(scope.row.id)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger" @click="updateCurrPage('del')">本页全部删除</el-button>
                        </div>
                    </template>
                </el-tab-pane>
                
            </el-tabs>
        </div>
    </div>
</template>

<script>
	import local from '@/utils/local';
    export default {
        name: 'myMsg',
        data() {
            return {
				message: 'unread',
                query: {
					isRead: 0,
					type: 1,
					currPage: 1,
					rows: 10
				},
				data: [],
				total: 0,
            }
        },
		mounted() {
			this.getData()
		},
        methods: {
			async getData(){
				let ret = await local.sendGet(this.ADMIN + '/msg', this.query)
				if (ret != null){
					this.data = ret.data.dataList
					this.total = ret.data.totalCount
				}
			},
			changeTab(tab, event){
				if (tab.name == 'unread'){
					this.query = {isRead: 0, type: 1, currPage: 1, rows: 10};
				} else if (tab.name = 'read'){
					this.query = {isRead: 1, type: 1, currPage: 1, rows: 10};
				}
				this.getData()
			},
			async updateCurrPage(opt){
				let ids = ''
				for (let i = 0; i < this.data.length; i++){
				    ids += ',' + this.data[i].id
				}
				ids = ids.substring(1)
				
				if (opt == 'read'){
					this.handleRead(ids)
				} else if (opt == 'del'){
					this.handleDel(ids)
				}
			},
			openMsg(row){
				this.$alert(row.content, row.title, {
				  confirmButtonText: '确定',
				});
				this.handleRead(row.id)
			},
			
			
			
            async handleRead(id) {
				let param = {id: id, read: 1}
                let ret = await local.sendGet(this.ADMIN + '/msg/updateRead', param)
				if (ret != null) this.getData()
            },
            async handleDel(id) {
				let param = {id: id}
                let ret = await local.sendGet(this.ADMIN + '/msg/del', param)
				if (ret != null) this.getData()
            }
        }
    }

</script>

<style lang="less">
.message-title{
    cursor: pointer;
}
.handle-row{
    margin-top: 30px;
}
.el-message-box{
	position: absolute;
	top: 30px;
	transform: translate(-50%,0%); /*自己的50% */
}
</style>

