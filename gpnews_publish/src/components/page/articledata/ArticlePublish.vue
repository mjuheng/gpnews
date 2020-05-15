<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 发布文章
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
		<div class="container">
		  <el-form label-width="120px">
			<el-form-item label="标题：">
			  <el-input style="width:400px" v-model="form.title"></el-input>
			</el-form-item>
			<el-form-item label="内容：">
			  <mavon-editor 
				v-model="form.content" 
				ref="md"
				@imgAdd="imgAdd"
				@change="contentChange"
				style="min-height: 500px"
			  />
			</el-form-item>
			<el-form-item label="封面：">
			  <!-- 封面图组件 -->
				<selectedImage v-model="form.headImage"></selectedImage>
			</el-form-item>
			<el-form-item label="分类：">
			  <channel v-model="form.categoryId"></channel>
			</el-form-item>
			<el-form-item v-if="$route.query.id">
			  <el-button @click="update(false)" type="success">修改</el-button>
			  <el-button @click="update(true)">存入草稿</el-button>
			</el-form-item>
			<el-form-item v-else>
			  <el-button @click="create(false)" type="primary">发表</el-button>
			  <el-button @click="create(true)">存入草稿</el-button>
			</el-form-item>
		  </el-form>
		</div>
    </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import local from '@/utils/local';
import channel from '@/components/common/my-channel.vue'
import selectedImage from '@/components/common/my-image.vue'
export default {
    name: 'userManage',
	components: {
		channel,
		mavonEditor,
		selectedImage
	},
    data() {
        return {
			form: {
				title: '',
				content: '',
				headImage: '',
				draft: '',
				categoryId: ''
			}
        };
    },
	watch: {
		'$route.query.id': function (newVal, oldVal) {
		  if (newVal) {
			// 修改 填充表单
			this.getData(newVal)
		  } else {
			// 新建 重置表单
			this.form = {
				title: '',
				content: '',
				headImage: '',
				draft: '',
				categoryId: ''
			}
		  }
		}
	},
    created() {
		let articleId = this.$route.query.id
		if (articleId) {
		  // 修改逻辑
		  // 填充表单内容
		  this.getData(articleId)
		}
    },
    methods: {
		// 图片上传
		async imgAdd(pos, file){
		  let formdata = new FormData();
		  formdata.append('image', file)
		  this.$axios({
			url: this.FILE + '/image/upload',
			method: 'post',
			data: formdata,
			header: {'Content-type': 'multipart/form-data'}
		  }).then(ret => {
			this.$refs.md.$img2Url(pos, ret.data.data)
		  })
		},
		// 所有操作都会被解析重新渲染
		contentChange(value, render){
			// render 为 markdown 解析后的结果[html]
			this.html = render;
			this.form.content = render
		},
		// 获取文章详情
		async getData (id) {
		  let ret = await local.sendGet(this.ADMIN + '/article/' + id)
		  this.form = ret.data
		},
		// 新建
		async create (draft) {
		  this.form.draft = draft
		  let ret = await local.sendPost(this.ADMIN + '/article/add', this.form);
		  if (ret != null){
			this.$router.push({path: 'articleContent'})
		  }
		},
		// 修改
		async update (draft) {
		  this.form.draft = draft;
		  let ret = await local.sendPost(this.ADMIN + '/article/update', this.form);
		  if (ret != null){
			this.$router.push({path: 'articleContent'})
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
</style>
