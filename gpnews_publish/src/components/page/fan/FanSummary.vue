<template>
	<div>
		<div class="crumbs">
		    <el-breadcrumb separator="/">
		        <el-breadcrumb-item>
		            <i class="el-icon-lx-cascades"></i> 粉丝概述
		        </el-breadcrumb-item>
		    </el-breadcrumb>
		</div>
		<div class="fan-summary">
			
			  <div class="visit-type">
				  <el-radio-group v-model="query.type" size="small" @change="getData">
					<el-radio-button  label="1">日</el-radio-button>
					<el-radio-button  label="2">周</el-radio-button>
					<el-radio-button  label="3">月</el-radio-button>
				  </el-radio-group>
			  </div>
			
			<el-row :gutter="40">
			  <el-col :span="8">
				<div class="grid-content">
				  <i></i>
				  <div>
					<div>{{sumPublish}} 个</div>
					<span>图文发布量</span>
				  </div>
				</div>
			  </el-col>
			  <el-col :span="8">
				<div class="grid-content">
				  <i></i>
				  <div>
					<div>{{sumVists}} 个</div>
					<span>访问量</span>
				  </div>
				</div>
			  </el-col>
			  <el-col :span="8">
				<div class="grid-content">
				  <i></i>
				  <div>
					<div>{{sumComment}}</div>
					<span>评论量</span>
				  </div>
				</div>
			  </el-col>
			</el-row>
			<el-row>
				<el-col :span="22" class="schart-box">
					<schart ref="schart" class="schart" canvasId="line" :options="options"></schart>
				</el-col>
			</el-row>
		</div>
	</div>	
</template>

<script>
import local from '@/utils/local';
import Schart from 'vue-schart';
export default {
    components: {
        Schart
    },
	name: 'fanSummary',
	data() {
		return {
			query: {
				userId: local.getUser().id,
				type: 1
			},
			data: [],
			sumPublish: 0,
			sumVists: 0,
			sumComment: 0,
			chartsData: {
				publishNum: [],
				visitNum: [],
				commentNum: []
			},
			options: {
			    type: 'line',
			    title: {
			        text: '关注度趋势图'
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
			
		}
	},
	watch: {
		data: function(newValue, oldValue){
			this.sumPublish = 0
			this.sumVists = 0
			this.sumComment = 0
			for (let i = 0; i < this.data.length; i++){
				if (this.data[i] != null){
					this.sumPublish += this.data[i].publishNum == null ? 0:this.data[i].publishNum
					this.sumVists += this.data[i].visitNum == null ? 0:this.data[i].visitNum
					this.sumComment += this.data[i].commentNum == null ? 0:this.data[i].commentNum
				}
			}
			
		}
	},
	mounted() {
		this.getData()
	},
	methods: {
		async getData(){
			let ret = await local.sendGet(this.ADMIN + '/visits', this.query)
			if (ret != null){
				this.data = ret.data
				this.setChartsData()
				this.setChartsLabel()
			}
		},
		// 设置表格数据
		setChartsData(){
			this.chartsData.publishNum = []
			this.chartsData.visitNum = []
			this.chartsData.commentNum = []
			for (let i = 0; i < this.data.length; i++){
				this.chartsData.publishNum.push(this.data[i] == null || this.data[i].publishNum == null ? 0:this.data[i].publishNum) 
				this.chartsData.visitNum.push(this.data[i] == null || this.data[i].visitNum == null ? 0:this.data[i].visitNum) 
				this.chartsData.commentNum.push(this.data[i] == null || this.data[i].commentNum == null ? 0:this.data[i].commentNum) 
			}
			this.options.datasets[0].data = this.chartsData.publishNum
			this.options.datasets[1].data = this.chartsData.visitNum
			this.options.datasets[2].data = this.chartsData.commentNum
		},
		// 设置表格X轴的显示
		setChartsLabel(){
			this.options.labels = []
			let now = new Date()
			
			// type == 2时 需要多一次循环
			let i = 0
			if (this.query.type == 2){ i-- }
			
			for (; i < 6 ; i++){
				if (this.query.type == 1){
					now.addDays(-1)
					this.options.labels.unshift(now.Format("yyyy-MM-dd"))
				} else if (this.query.type == 2){
					let str = '';
					str += now.Format("yyyy-MM-dd")
					now.addWeeks(-1)
					str = now.Format("yyyy-MM-dd") + '~' + str
					this.options.labels.unshift(str)
				} else if (this.query.type == 3){
					now.addMonths(-1)
					this.options.labels.unshift(now.Format("yyyy-MM"))
				}
			}
			// 补全type == 1 或者 type == 3 时的数据
			now = new Date();
			if (this.query.type == 1){
				this.options.labels.push(now.Format("yyyy-MM-dd"))
			}else if (this.query.type == 3){
				this.options.labels.push(now.Format("yyyy-MM"))
			}
		}
	}
}
</script>

<style lang="less">
	.fan-summary {
	  background-color: #ffffff;
	  text-align: left;
	  border: 1px solid #e7e7e9;
	  padding-bottom: 30px;
	  header {
	    color: #323745;
	    font-size: 14px;
	    height: 55px;
	    line-height: 55px;
	    padding: 0 15px;
	    background-color: #fbfbfb;
	    border-bottom: 1px solid #e8e8e8;
	  }
	  .tabBar {
	    font-size: 14px;
	    padding: 0 15px;
	    height: 55px;
	    line-height: 55px;
	    border-bottom: 1px dashed #cccccc;
	    a {
	      margin-right: 35px;
	      color: #323745;
	      &.active {
	        color: #3296fa;
	      }
	    }
	  }
	  .visit-type{
		  padding: 20px 0 20px 20px;
		  span {
			  font-size: 16px;
			  padding: 10px 23px;
		  }
	  }
	  .filter {
	    font-size: 14px;
	    padding: 20px 0 20px 20px;
	    span {
	      border: 1px solid #3296fa;
	      color: #000000;
		  font-size: 18px;
	      padding: 10px 20px;
		  
	      cursor: pointer;
	      &:nth-child(1){
	        border-right: none;
	      }
	      &:nth-child(2){
	        border-right: none;
	      }
	      &.active {
	        background-color: #3296fa;
	        color: #ffffff;
	      }
	    }
	    .el-date-editor {
	      margin-left: 20px;
	    }
	  }
	  .chart {
	    padding: 0 20px;
	    overflow: hidden;
	    margin-bottom: 30px;
	  }
	}
	
	.el-row {
	  padding-left: 20px;
	  .el-col {
	    .grid-content {
	      background-color: #f8f8f8;
	      padding: 30px 0;
	      display: flex;
	      justify-content: center;
	      i {
	        width: 50px;
	        height: 50px;
	        background-color: red;
	        border-radius: 50%;
	      }
	      & > div {
	        margin: 0px 0 0 10px;
	        span {
	          color: #444444;
	          font-size: 14px;
	        }
	        div {
	          font-size: 24px;
	        }
	      }
	    }
	  }
	}
	.schart-box {
	    display: inline-block;
	    margin: 20px;
		.schart {
		    height: 400px;
			width: 100%;
		}
	}

</style>
