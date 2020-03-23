<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 素材管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
		<div class="container">
		  <div class="btn_box">
			<el-button @click="open" size="small" style="float:right" type="success">添加素材</el-button>
		  </div>
		  <!-- 素材区域 -->
		  <div class="img_list">
			<div :key="item.id" class="img_item" v-for="item in data">
			  <img :src="item.imageUrl" />
			  <div class="footer" v-show="!query.collect">
				<span @click="deleteMaterial(item.id)" class="el-icon-delete"></span>
			  </div>
			</div>
		  </div>
		  <!-- 分页 -->
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
		</el-card>
		<!-- 对话框 -->
		<el-dialog :visible.sync="dialogVisible" title="添加素材" width="400px" style="padding-bottom: 50px;">
		  <!-- action 是图片上传的接口地址 -->
		  <!-- header 上传组件的请求头 -->
		  <!-- name 提交文件的字段名称  需要和后台保持一致  -->
		  <el-upload
		    class="avatar-uploader"
			:headers="headers"
		    :action='this.FILE + "/image/upload"'
		    :show-file-list="false"
		    :on-success="handleSuccess"
			name="image"
			>
		    <img v-if="imageUrl" :src="imageUrl" class="avatar">
		    <i class="el-icon-plus avatar-uploader-icon" v-else></i>
		  </el-upload>
<!-- 		  <el-upload
			:headers="headers"
			:on-success="handleSuccess"
			:show-file-list="false"
			:action='this.FILE + "/image/upload"'
			class="avatar-uploader"
			name="image"
		  >
			<img :src="imageUrl" class="avatar" v-if="imageUrl" />
			<i class="el-icon-plus" v-else></i>
		  </el-upload> -->
		</el-dialog>
		</div>
    </div>
</template>

<script>
import local from '@/utils/local';
import channel from '@/components/common/my-channel.vue'
import selectedImage from '@/components/common/my-image.vue'
export default {
    name: 'materialManage',
    data() {
        return {
		  query: {
			// collect: false,
			currPage: 1,
			rows: 10
		  },
		  materialParams: {
			imageUrl: null
		  },
		  // 素材列表
		  data: [],
		  // 总条数
		  total: 0,
		  // 控制对话框 显示与隐藏
		  dialogVisible: false,
		  // 上传成功后的图片地址
		  imageUrl: null,
		  // 请求头
		  headers: {
			Authorization: localStorage.sessionId
		  }
        };
    },
    created() {
	  this.getData()
    },
    methods: {
		// 打开对话框
		open () {
		  this.dialogVisible = true
		  this.imageUrl = null
		},
		// 上传图片成功
		async handleSuccess (res) {
		  // res 就是响应主体  res.data.url 就是图片地址
		  // 预览
		  this.materialParams.imageUrl = res.data
		  let ret = await local.sendPost(this.ADMIN + '/material/add', this.materialParams)
		  if (ret != null){
			this.dialogVisible = false;
			this.getData();
		  }
		},
		// 获取素材列表
		async getData () {
		  // 获取数据
		  let ret = await local.sendGet(this.ADMIN + '/material', this.query);
		  if (ret != null){
			this.data = ret.data.dataList
			this.total = ret.data.totalCount
		  }
		},
		handlePageChange (newPage) {
		  this.query.currPage = newPage
		  this.getData()
		},
		// 切换 全部与收藏 列表
		toggleList () {
		  this.query.currPage = 1
		  this.getData()
		},
		// 删除
		deleteMaterial (id) {
		  // 显示确认框
		  this.$confirm('此操作将永久删除该素材, 是否继续?', '温馨提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		  }).then(async () => {
			let ret = await local.sendGet(this.ADMIN + '/material/del/' + id);
			if (ret != null){
			  this.getData();
			}
		  })
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
.img_list {
  margin-top: 20px;
  .img_item {
    width: 180px;
    height: 180px;
    border: 1px dashed #ddd;
    position: relative;
    display: inline-block;
    margin-right: 50px;
    margin-bottom: 20px;
    img {
      width: 100%;
      height: 100%;
    }
    .footer {
      width: 100%;
      height: 30px;
      line-height: 30px;
      background: rgba(0, 0, 0, 0.3);
      color: #fff;
      position: absolute;
      left: 0;
      bottom: 0;
      text-align: center;
      // span .red{} 选择器无效
      // span.red{} 选择器有效  &连接符
      span {
        margin: 0 20px;
        &.red {
          color: red;
        }
      }
    }
  }
}
</style>
