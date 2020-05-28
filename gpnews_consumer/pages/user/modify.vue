<template>
  <view>
    <!-- <cmd-nav-bar back title="修改密码"></cmd-nav-bar> -->
    <cmd-page-body type="top">
      <cmd-transition name="fade-up">
        <view class="modify">
		  <view class="modify-password">
		    昵称：<cmd-input v-model="userInfo.username" type="text" maxlength="26" placeholder="昵称" disabled></cmd-input>
		  </view>
          <view class="modify-password">
            手机号：<cmd-input v-model="userInfo.phone" type="text" maxlength="26" placeholder="手机号" disabled></cmd-input>
          </view>
		  <view class="modify-password">
		    邮箱地址：<cmd-input v-model="userInfo.email" type="text" maxlength="26" placeholder="邮箱地址" disabled></cmd-input>
		  </view>
		  <view class="modify-password">
		    个人介绍：<cmd-input v-model="userInfo.intro" type="text" maxlength="26" placeholder="个人介绍"></cmd-input>
		  </view>
          <button class="btn-modify btn-modify-active" hover-class="btn-modify-hover" @tap="commitData">提交</button>
        </view>
      </cmd-transition>
    </cmd-page-body>
  </view>
</template>

<script>
  import cmdNavBar from "@/components/cmd-nav-bar/cmd-nav-bar.vue"
  import cmdPageBody from "@/components/cmd-page-body/cmd-page-body.vue"
  import cmdTransition from "@/components/cmd-transition/cmd-transition.vue"
  import cmdInput from "@/components/cmd-input/cmd-input.vue"

  export default {
    components: {
      cmdNavBar,
      cmdPageBody,
      cmdTransition,
      cmdInput
    },

    data() {
      return {
        userInfo: {
			id: ''
		},
        phoneReg: /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/,
        safety: {
          time: 60,
          state: false,
          interval: ''
        },
        modifyMobile: false
      };
    },
	onLoad() {
		this.userInfo = uni.getStorageSync("userInfo");
	},

    // watch: {
    //   /**
    //    * 监听表单数值
    //    */
    //   mobile: {
    //     handler(newValue) {
    //       if (this.phoneReg.test(newValue.phone) && newValue.code.length == 6 && newValue.passwordOne.length >= 8 &&
    //         newValue.passwordOne == newValue.passwordTwo) {
    //         this.modifyMobile = true;
    //       } else {
    //         this.modifyMobile = false;
    //       }
    //     },
    //     deep: true
    //   }
    // },

    methods: {
		async commitData(){
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
				setTimeout(function() {
					// uni.navigateTo({
					// 	url: '/pages/user/info'
					// })
					uni.navigateBack()
				}, 1000);
			}else{
				uni.showToast({
					icon: 'none',
					title: ret.message
				})
			}
		}
      /**
       * 获取验证码
       */
      // fnGetPhoneCode() {
      //   if (this.phoneReg.test(this.mobile.phone)) {
      //     uni.showToast({
      //       title: "正在发送验证码",
      //       icon: "loading",
      //       success: () => {
      //         // 成功后显示倒计时60s后可在点击
      //         this.safety.state = true;
      //         // 倒计时
      //         this.safety.interval = setInterval(() => {
      //           if (this.safety.time-- <= 0) {
      //             this.safety.time = 60;
      //             this.safety.state = false;
      //             clearInterval(this.safety.interval);
      //           }
      //         }, 1000);
      //         uni.showToast({
      //           title: "发送成功",
      //           icon: "success"
      //         })
      //       }
      //     })
      //   } else {
      //     uni.showToast({
      //       title: "手机号不正确",
      //       icon: "none"
      //     })
      //   }
      // }
    },

    beforeDestroy() {
      /**
       * 关闭页面清除轮询器
       */
      clearInterval(this.safety.interval);
    }
  }
</script>

<style>
  .modify {
    margin-top: 118upx;
    margin-right: 72upx;
    margin-left: 72upx;
  }

  .modify-phone {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2upx #dedede solid;
    margin-top: 118upx;
    margin-bottom: 40upx;
  }

  .modify-phone-getcode {
    color: #3F51B5;
    text-align: center;
    min-width: 140upx;
  }

  .modify-password,
  .modify-code {
    margin-bottom: 40upx;
    border-bottom: 2upx #dedede solid;
  }

  .btn-modify {
    margin-top: 100upx;
    border-radius: 50upx;
    font-size: 16px;
    color: #fff;
    background: linear-gradient(to right, #88a1f9, #9ac6ff);
  }

  .btn-modify-active {
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .btn-modify-hover {
    background: linear-gradient(to right, #365fdd, #36bbfa);
  }

  button[disabled] {
    color: #fff;
  }
</style>
