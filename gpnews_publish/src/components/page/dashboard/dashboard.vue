<template>
	<div>
		<div class="crumbs">
		    <el-breadcrumb separator="/">
		        <el-breadcrumb-item>
		            <i class="el-icon-lx-cascades"></i> 系统首页
		        </el-breadcrumb-item>
		    </el-breadcrumb>
		</div>
		<section class="dashboard-user">
		  <el-row>
			<el-col :span="15">
			  <dl class="user-info">
				<dd class="user-avatar"><img :src='userData.photo' width="100px"></dd>
				<dt>{{ userData.username }}</dt>
				<dd class="tips">{{ userData.intro }}</dd>
			  </dl>
			</el-col>
			<el-col :span="3">
			  <div class="num">
				{{ userData.fansNum }}
			  </div>
			  <div class="text">粉丝数</div>
			</el-col>
			<el-col :span="3">
			  <div class="num">
				{{ userData.readNum }}
			  </div>
			  <div class="text">累计阅读量</div>
			</el-col>
			<el-col :span="3">
			  <div class="num">
				{{ userData.commentNum }}
			  </div>
			  <div class="text">累计评论数</div>
			</el-col>
		  </el-row>
		</section>
		
		<section class="dashboard-article">
		  <header class="header">
		    最新图文
		    <a href="#" class="">更多</a>
		  </header>
		  <ul>
		    <li v-for="data in articleData">
		      <img class="article-img" :src="data.headImage" v-if="data.headImage != ''">
		      <dl class="article-content">
		        <dt><a href="#" class="">{{ data.title }}</a></dt>
		        <dd class="time">{{ data.publishTime }}</dd>
		        <dd>阅读 {{ data.readNum }}   评论 {{ data.commentNum }}</dd>
		      </dl>
		    </li>
		  </ul>
		</section>
		
		<section class="dashboard-article">
		  <header class="header">
		    消息
		    <a href="#" class="">更多</a>
		  </header>
		  <ul>
		    <li v-for="data in msgData">
		      <!-- <img class="article-img" :src="data.optUserPhoto"> -->
		      <dl>
					<dt>{{ data.content }}</a> </dt>
					<dd>{{data.createTime}}</dd>
		      </dl>
		    </li>
		  </ul>
		</section>
	</div>
</template>

<script>
import local from '@/utils/local';
export default {
	name: 'dashboard',
	data() {
		return {
			userQuery: {
				userId: null
			},
			articleQuery: {
				currPage: 1,
				rows: 3,
				status: 2,
				userId: null,
			},
			msgQuery: {
				currPage: 1,
				rows: 3,
				type: 2,
				userId: '',
			},
			userData: {
				readNum: '',
				commentNum: '',
				fansNum: ''
			},
			articleData: [],
			msgData: []
		}
	},
	mounted() {
		this.userData = local.getUser()
		this.userQuery.userId = this.userData.id
		this.articleQuery.userId = this.userData.id
		this.msgQuery.userId = this.userData.id
		this.getUserData();
		this.getArticleNew();
		this.getMsgData();
	},
	methods: {
		async getUserData(){
			let ret = await local.sendGet(this.ADMIN + '/article/getReadAndComm', this.userQuery)
			if (ret != null){
				this.userData.readNum = ret.data.readNum
				this.userData.commentNum = ret.data.commentNum
			}
			ret = await local.sendGet(this.ADMIN + '/fan/countFans', this.userQuery)
			if (ret != null){
				this.userData.fansNum = ret.data
			}
		},
		async getArticleNew(){
			let ret = await local.sendGet(this.ADMIN + '/article', this.articleQuery)
			if (ret != null){
				this.articleData = ret.data.dataList
			}
		},
		async getMsgData(){
			let ret = await local.sendGet(this.ADMIN + '/msg', this.msgQuery)
			if (ret != null){
				this.msgData = ret.data.dataList
			}
		}
	}
}
</script>

<style lang="less">
	dt {
	  color: #333333;
	  font-size: 14px;
	  margin: 2px 0;
	  a {
	    color: #3296fa;
	  }
	}
	dd {
	  font-size: 14px;
	  margin: 5px 0;
	  color: #999999;
	}
	
	.dashboard-user {
	  background-color: #fff;
	  border: 1px solid #e7e7e9;
	  .user-info {
	    text-align: left;
	    padding: 8px;
	    overflow: hidden;
	    dt {
	      font-size: 14px;
	      margin:4px 0 4px 60px;
	    }
	    .tips {
	      margin-left: 60px;
	      font-size: 12px;
	      color: #999999;
	    }
	    .user-avatar{
	      vertical-align: middle;
	      font-size: 0;
	      float: left;
	      img {
	        width: 50px;
	        height: 50px;
	        border-radius: 50%;
	      }
	    }
	  }
	  .num {
	    color: #333333;
	  }
	  .text {
	    color: #999999;
	    font-size: 12px;
	    margin-top: 2px;
	  }
	  .el-col-3 {
	    border-right: 1px solid #eeeeee;
	    margin-top: 13px;
		padding-left: 20px;
	  }
	}
	
	.dashboard-article {
	  background-color: #ffffff;
	  border: 1px solid #e7e7e9;
	  margin-top: 10px;
	  .header {
	    display: flex;
	    justify-content: space-between;
	    color: #323745;
	    font-size: 14px;
	    height: 40px;
	    line-height: 40px;
	    padding: 0 15px;
	    background-color: #fbfbfb;
	    border-bottom: 1px solid #e8e8e8;
	  }
	  li {
	    overflow: hidden;
	    padding: 15px 5px;
	    margin: 0 10px;
	    border-top: 1px solid #f2f3f5;
	  }
	  li:first-child {
	    border: none;
	  }
	  .article-img {
	    float: left;
	    width: 150px;
	    height: 85px;
	  }
	  .article-content {
	    text-align: left;
	    dt {
	      color: #333333;
	      font-size: 14px;
	      margin: 7px 0;
	    }
	    .time {
	      margin-bottom: 10px;
	      span {
	        color:#3296fa;
	        margin-right: 8px;
	      }
	    }
	    dd {
	      font-size: 12px;
	      margin: 5px 0;
	      color: #999999;
	    }
	  }
	}
	
	.dashboard-article {
	  background-color: #ffffff;
	  border: 1px solid #e7e7e9;
	  margin-top: 10px;
	  .header {
	    display: flex;
	    justify-content: space-between;
	    color: #323745;
	    font-size: 14px;
	    height: 40px;
	    line-height: 40px;
	    padding: 0 15px;
	    background-color: #fbfbfb;
	    border-bottom: 1px solid #e8e8e8;
	  }
	  li {
	    overflow: hidden;
	    padding: 15px 5px;
	    margin: 0 10px;
	    border-top: 1px solid #f2f3f5;
	  }
	  li:first-child {
	    border: none;
	  }
	  .article-img {
	    float: left;
	    width: 50px;
	    height: 50px;
	    border-radius: 50%;
	  }
	  .article-content {
	    margin-left: 60px;
	    text-align: left;
	    dt {
	      color: #333333;
	      font-size: 14px;
	      margin: 2px 0;
	      a {
	        color: #3296fa;
	      }
	    }
	    dd {
	      font-size: 14px;
	      margin: 5px 0;
	      color: #999999;
	    }
	  }
	}
</style>
