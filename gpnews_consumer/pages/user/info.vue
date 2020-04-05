<template>
  <view>
    <!-- <cmd-nav-bar back title="信息设置"></cmd-nav-bar> -->
    <cmd-page-body type="top">
      <cmd-transition name="fade-up">
        <view>
          <cmd-cel-item title="头像" slot-right arrow @click="chooseImage">
            <cmd-avatar :src="userInfo.photo" ></cmd-avatar>
          </cmd-cel-item>
		  <image class="image" :src="imageUrl" v-if="imageUrl != ''"></image>
		  <image-cutter @ok="chooseImageOk" @cancel="chooseImageCancel" :url="imageUrl" :fixed="true" :width="300" :height="200"></image-cutter>
		  
          <cmd-cel-item title="昵称" :addon="userInfo.username"></cmd-cel-item>
          <cmd-cel-item title="联系方式" :addon="userInfo.phone"></cmd-cel-item>
          <cmd-cel-item title="邮箱地址" :addon="userInfo.email"></cmd-cel-item>
          <cmd-cel-item title="个人签名" :addon="userInfo.intro"></cmd-cel-item>
          <button class="btn-logout" @click="fnClick('modify_pwd')">修改密码</button>
		  <button class="btn-logout" @click="fnClick('modify')">修改资料</button>
        </view>
      </cmd-transition>
    </cmd-page-body>
  </view>
</template>

<script>
  import cmdNavBar from "@/components/cmd-nav-bar/cmd-nav-bar.vue"
  import cmdPageBody from "@/components/cmd-page-body/cmd-page-body.vue"
  import cmdTransition from "@/components/cmd-transition/cmd-transition.vue"
  import cmdCelItem from "@/components/cmd-cell-item/cmd-cell-item.vue"
  import cmdAvatar from "@/components/cmd-avatar/cmd-avatar.vue"
  import imageCutter from '@/components/image-cutter/image-cutter.vue'

  export default {
    components: {
      cmdNavBar,
      cmdPageBody,
      cmdTransition,
      cmdCelItem,
      cmdAvatar,
	  imageCutter
    },

    data() {
      return {
		  userInfo: {},
		  imageUrl: ''
	  };
    },
	onShow() {
		this.userInfo = uni.getStorageSync("userInfo");
	},
    methods:{
	  // 选择图片
	  chooseImage(){
		  uni.chooseImage({
		  	count: 1,
			sourceType: ['album'],
			success: res => {
				this.imageUrl = res.tempFilePaths[0]
			}
		  })
	  },
	  // 图片裁剪成功
	  chooseImageOk(ev){
		  uni.uploadFile({
			  url: this.FILE + '/image/upload',
			  filePath: ev.path,
			  name: 'image',
			  success: res => {
				  this.userInfo.photo = JSON.parse(res.data).data;
				  this.commitUpdateUser()
			  },
			  fail: err => {
				  console.log(err);
				  uni.showToast({
					icon: 'none',
					title: '上传失败'
				  })
			  }
		  })
		  this.imageUrl = '';
	  },
	  // 修改信息
	  async commitUpdateUser(){
	    let ret = await this.$http({
			url: '/updateUser',
			method: 'POST',
			data: this.userInfo
		})
		if (ret.code == 0){
			uni.showToast({
				title: ret.message
			})
			uni.setStorageSync('userInfo', this.userInfo)
			uni.navi
		}
	  },
	  chooseImageCancel(){
		  this.imageUrl = '';
	  },
      /**
       * 点击触发
       * @param {Object} type 跳转页面名或者类型方式
       */
      fnClick(type){
        if(type == 'modify'){
          uni.navigateTo({
            url:'/pages/user/modify'
          })
        }else if (type == 'modify_pwd'){
			uni.navigateTo({
				url: '/pages/user/modify_pwd'
			})
		}
      }
    }
  }
</script>

<style>
  .btn-logout {
    margin-top: 50upx;
    width: 80%;
    border-radius: 50upx;
    font-size: 16px;
    color: #fff;
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .btn-logout-hover {
    background: linear-gradient(to right, #365fdd, #36bbfa);
  }
  
  .image {
      width: 200px;
      height: 200px;
  }
</style>
