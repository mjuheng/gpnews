<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 个人设置
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
			
		  <el-tabs v-model="activeName">
			<el-tab-pane label="基础信息" name="info">
		    <el-form label-width="120px">
				<el-form-item label="名称：">{{data.username}}</el-form-item>
				<el-form-item label="手机：">{{data.phone}}</el-form-item>
				<el-form-item label="个人介绍：">
				  <el-input :rows="3" type="textarea" v-model="data.intro"></el-input>
				</el-form-item>
				<el-form-item label="邮箱：">
				  <el-input v-model="data.email"></el-input>
				</el-form-item>
				<el-form-item>
				  <el-button @click="saveInfo" type="primary">保存设置</el-button>
				</el-form-item>
		     </el-form>
			</el-tab-pane>
			<el-tab-pane label="个人头ddd像" name="headImage">
				<el-upload
				:http-request="updatePhoto"
				:show-file-list="false"
				class="avatar-uploader"
				>
					<img :src="data.photo" class="avatar" v-if="data.photo" style="width: 100%;" />
					<i class="el-icon-plus avatar-uploader-icon" v-else></i>
				</el-upload>
				<el-button @click="saveInfo" type="primary">保存设置</el-button>
			</el-tab-pane>
			<el-tab-pane label="密码" name="pwd">
			 
			</el-tab-pane>
		  </el-tabs>
        </div>
    </div>
</template>

<script>
import local from '@/utils/local';
export default {
    name: 'userSetting',
    data() {
        return {
			activeName: 'info',
			data: {
				username: null,
				intro: null,
				email: null,
				photo: null
			}
        };
    },
    created() {
		this.getData()
    },
    methods: {
		// 修改头像（上传图片）
		async updatePhoto (result) {
			console.log("haha");
		  const file = result.file
		  const formData = new FormData()
		  formData.append('image', file)
		  let ret = await local.sendPost(this.FILE + '/image/uploads', formData)
		  if (ret != null){
			console.log(ret);
			this.data.photo = ret.data
			local.setUser(this.data)
		  }
		},
		// 获取用户信息
		async getData () {
			console.log(this.data);
		  this.data = local.getUser();
		},
		// 保存用户信息 （除去头像不能保存）
		async saveInfo () {
		  let ret = await local.sendPost(this.PUBLISH + '/updateUser', this.data);
		  if (ret != null){
			local.setUser(this.data)
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
