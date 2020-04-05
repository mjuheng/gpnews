<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:160px;">
                    <div class="user-info">
                        <img :src="userInfo.headImage" class="user-avator" onerror="../../../assets/img/default.png" />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{userInfo.username}}</div>
                            <div>{{userInfo.intro}}</div>
                        </div>
                    </div>
                </el-card>
                <el-card shadow="hover" style="height:280px;">
                    <div slot="header" class="clearfix">
                        <span>分类占比</span>
                    </div>
					<div v-for="(data, index) in articleData" :key="index">
						{{ data.name }}<el-progress :percentage="data.percent" :color="articleRankColor[index]"></el-progress>
					</div>
                </el-card>
            </el-col>
            <el-col :span="16">
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-lx-people grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{ summaryData.userNum }}</div>
                                    <div>用户数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-notebook-1 grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{ summaryData.articleNum }}</div>
                                    <div>文章数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-lx-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{ summaryData.commNum }}</div>
                                    <div>评论数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-card shadow="hover" style="height:340px;">
                    <div slot="header" class="clearfix">
                        <span>最新未读消息</span>
                    </div>
                    <el-table :show-header="false" :data="msgData" style="width:100%;">
                        <el-table-column>
                            <template slot-scope="scope">
                                <div class="message-title" @click="openMsg(scope.row)">{{scope.row.title}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="60">
                            <template slot-scope="scope">
								<el-tooltip class="item" effect="dark" content="标为已读" placement="top">
									<i class="el-icon-reading" @click="handleRead(scope.row.id)"></i>
								</el-tooltip>
                                <!-- <i class="el-icon-delete"></i> -->
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="24">
                <el-card shadow="hover">
                    <schart ref="schart" class="schart" canvasId="line" :options="options"></schart>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import local from '@/utils/local'
import Schart from 'vue-schart';
export default {
    name: 'dashboard',
    data() {
        return {
			userInfo: {},
			articleData: {},
			articleRankColor: ['#42b983', '#f1e05a', '#00eeee', '#f56c6c', '#b3b3b3'],
			msgQuery: {
				type: 1,
				isRead: 0,
				currPage: 1,
				rows: 5
			},
			msgData: [],
			summaryData: {
				articleNum: 0,
				userNum: 0,
				commNum: 0,
			},
			
			systemData: {},
			chartsData: {
				publishNum: [],
				visitNum: [],
				commentNum: []
			},
            options: {
                type: 'line',
                title: {
                    text: '系统动态图'
                },
                bgColor: '#fbfbfb',
                labels: ['4月','5月','6月', '7月', '8月', '9月', '10月'],
                datasets: [
                    {
                        label: '图文发布量',
                        data: [0,0,0,0,0,0,0]
                    },
                    {
                        label: '访问量',
                        data: [0,0,0,0,0,0,0]
                    },
                    {
                        label: '评论量',
                        data: [0,0,0,0,0,0,0]
                    }
                ]
            },
  
        };
    },
    components: {
        Schart
    },
	mounted() {
		this.userInfo = local.getUser()
		this.getArticleData();
		this.getMsgData();
		this.getSummaryData();
		this.getSystemData();
	},
    methods: {
		async getArticleData(){
			let ret = await local.sendGet(this.ADMIN + '/article/eachCount')
			if (ret != null) {
				for (let i = 0; i < ret.data.length; i++) this.summaryData.articleNum += ret.data[i].num
				this.articleData = ret.data.splice(0,5)
			}
		},
		async getMsgData(){
			let ret = await local.sendGet(this.ADMIN + '/msg', this.msgQuery)
			if (ret != null) this.msgData = ret.data.dataList
		},
		async getSummaryData(){
			let ret = await local.sendGet(this.ADMIN + '/user/countUser')
			if (ret != null) this.summaryData.userNum = ret.data
			ret = await local.sendGet(this.ADMIN + '/comment/count')
			if (ret != null) this.summaryData.commNum = ret.data
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
			if (ret != null) this.getMsgData()
		},
		// 获取系统动态数据
		async getSystemData(){
			let ret = await local.sendGet(this.ADMIN + '/visits', {type: 3})
			if (ret != null){
				this.systemData = ret.data
				this.setChartsData()
				this.setChartsLabel()
				
				console.log(this.options);
			}
		},
		// 设置系统动态表格数据
		setChartsData(){
			this.chartsData.publishNum = []
			this.chartsData.visitNum = []
			this.chartsData.commentNum = []
			for (let i = 0; i < this.systemData.length; i++){
				this.chartsData.publishNum.push(this.systemData[i] == null || this.systemData[i].publishNum == null ? 0:this.systemData[i].publishNum) 
				this.chartsData.visitNum.push(this.systemData[i] == null || this.systemData[i].visitNum == null ? 0:this.systemData[i].visitNum) 
				this.chartsData.commentNum.push(this.systemData[i] == null || this.systemData[i].commentNum == null ? 0:this.systemData[i].commentNum) 
			}
			this.options.datasets[0].data = this.chartsData.publishNum
			this.options.datasets[1].data = this.chartsData.visitNum
			this.options.datasets[2].data = this.chartsData.commentNum
		},
		// 设置表格X轴的显示
		setChartsLabel(){
			this.options.labels = []
			let now = new Date()
			
			for (let i = 0; i < 6 ; i++){
				now.addMonths(-1)
				this.options.labels.unshift(now.Format("yyyy-MM"))
			}
			// 补全type == 1 或者 type == 3 时的数据
			now = new Date();
			this.options.labels.push(now.Format("yyyy-MM"))
		}
    }
};
</script>


<style scoped>
.message-title{
	cursor: pointer;
}
.el-message-box{
	position: absolute;
	top: 30px;
	transform: translate(-50%,0%);
}
.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>
