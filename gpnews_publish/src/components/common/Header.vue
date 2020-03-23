<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            <i v-if="!collapse" class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div class="logo">新闻发布系统自媒体端</div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>
				<el-dialog
				  title="个人设置"
				  :visible.sync="dialogVisible"
				  width="30%"
				  >
					  <el-tabs v-model="activeName">
						<el-tab-pane label="基础信息" name="info">
							<el-form label-width="120px">
								<el-form-item label="名称：">{{userInfo.username}}</el-form-item>
								<el-form-item label="手机：">{{userInfo.phone}}</el-form-item>
								<el-form-item label="个人介绍：">
								  <el-input :rows="3" type="textarea" v-model="userInfo.intro"></el-input>
								</el-form-item>
								<el-form-item label="邮箱：">
								  <el-input v-model="userInfo.email"></el-input>
								</el-form-item>
								<el-form-item>
									<el-button @click="saveInfo" type="primary">保存设置</el-button>
								</el-form-item>
							 </el-form>
						</el-tab-pane>
						<el-tab-pane label="个人头像" name="headImage">
							<el-form>
								<el-form-item>
									<el-upload
									:http-request="updatePhoto"
									:show-file-list="false"
									action
									class="avatar-uploader"
									>
										<img :src="userInfo.photo" class="avatar" v-if="userInfo.photo" />
										<i class="el-icon-plus avatar-uploader-icon" v-else></i>
									</el-upload>
								</el-form-item>
								<el-form-item>
									<el-button @click="saveInfo" type="primary">保存设置</el-button>
								</el-form-item>
							</el-form>
						</el-tab-pane>
						<el-tab-pane label="密码" name="pwd">
							<el-form label-width="120px" ref="pwdForm" :model="pwd" :rules="pwdRules">
								<el-form-item label="旧密码：" prop="oldPwd">
								  <el-input type="password" v-model="pwd.oldPwd"></el-input>
								</el-form-item>
								<el-form-item label="新密码：" prop="newPwd">
								  <el-input type="password" v-model="pwd.newPwd"></el-input>
								</el-form-item>
								<el-form-item label="确认密码：" prop="againPwd">
								  <el-input type="password" v-model="pwd.againPwd"></el-input>
								</el-form-item>
								<el-form-item>
									<el-button @click="savePwd" type="primary">保存设置</el-button>
								</el-form-item>
							  </el-form>
						</el-tab-pane>
					  </el-tabs>

				</el-dialog>
                <!-- 消息中心 -->
                <!-- <div class="btn-bell">
                    <el-tooltip
                        effect="dark"
                        :content="message?`有${message}条未读消息`:`消息中心`"
                        placement="bottom"
                    >
                        <router-link to="/tabs">
                            <i class="el-icon-bell"></i>
                        </router-link>
                    </el-tooltip>
                    <span class="btn-bell-badge" v-if="message"></span>
                </div> -->
                <!-- 用户头像 -->
                <div class="user-avator">
                    <img :src='userInfo.photo' />
                    <!-- <img src="../../assets/img/img.jpg" /> -->
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{userInfo.username}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
						<el-dropdown-item divided command="setting">个人设置</el-dropdown-item>
                        <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
				

            </div>
        </div>

    </div>
</template>
<script>
import bus from '../common/bus';
import local from '@/utils/local'
export default {
    data() {
        return {
			activeName: 'info',
			dialogVisible: false,
            collapse: false,
            fullscreen: false,
            userInfo: {
				username: null,
				intro: null,
				email: null,
				photo: null
			},
			pwd: {
				oldPwd: '',
				newPwd: '',
				againPwd: '',
			},
            message: 2,
			pwdRules: {
                oldPwd: [{ required: true, message: '必填', trigger: 'blur' }],
                newPwd: [{ required: true, message: '必填', trigger: 'blur' }],
                againPwd: [{ required: true, message: '必填', trigger: 'blur' }]
            }
        };
    },
    computed: {
        username() {
            let username = local.getUser().username;
            return username ? username : this.name;
        }
    },

    methods: {
		// 保存密码
		savePwd(){
			this.$refs.pwdForm.validate(async valid => {
				if (valid){
					local.sendPost(this.ADMIN + '/user/changePwd', this.pwd)
					this.dialogVisible = false
					return;
				}
			})
		},
		// 保存用户信息 （除去头像不能保存）
		async saveInfo () {
		  let ret = await local.sendPost(this.ADMIN + '/updateUser', this.userInfo);
		  if (ret != null){
			local.setUser(this.data)
		  }
		},
		// 修改头像（上传图片）
		async updatePhoto (result) {
		  const file = result.file
		  const formData = new FormData()
		  formData.append('image', file)
		  let ret = await local.sendPost(this.FILE + '/image/upload', formData)
		  if (ret != null){
			this.data.photo = ret.data
			local.setUser(this.data)
			this.dialogVisible = false
		  }
		},
        // 用户名下拉菜单选择事件
        handleCommand(command) {
            if (command == 'loginout') {
                local.sendGet(this.ADMIN + '/logout')
                local.setUser(null)
                localStorage.sessionId = ''
                this.$router.push('/login');    
            } else if (command == 'setting'){
				this.dialogVisible = true
			}
        },
        // 侧边栏折叠
        collapseChage() {
            this.collapse = !this.collapse;
            bus.$emit('collapse', this.collapse);
        },
        // 全屏事件
        handleFullScreen() {
            let element = document.documentElement;
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        }
    },
    mounted() {
        this.userInfo = local.getUser();
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
    }
};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
}
.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}
.header .logo {
    float: left;
    width: 250px;
    line-height: 70px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 20px;
}
.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
.el-dropdown-menu__item {
    text-align: center;
}
</style>
