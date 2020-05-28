<template>
  <view>
    <!-- <cmd-nav-bar back title="修改密码"></cmd-nav-bar> -->
    <cmd-page-body type="top">
      <cmd-transition name="fade-up">
        <view class="modify">
<!--          <view class="modify-phone">
            <cmd-input v-model="mobile.phone" type="number" maxlength="11" placeholder="请输入手机号"></cmd-input>
            <view class="modify-phone-getcode" :style="safety.state ? 'color: #2196F3;':''" @tap="!safety.state ? fnGetPhoneCode() : ''">{{!safety.state&&'获取验证码'||(safety.time+' s')}}</view>
          </view> -->

          <view class="modify-password">
            <cmd-input v-model="query.oldPwd" type="password" displayable maxlength="26" placeholder="请输入旧密码"></cmd-input>
          </view>
		  <view class="modify-password">
		    <cmd-input v-model="query.newPwd" type="password" displayable maxlength="26" placeholder="请输入新密码"></cmd-input>
		  </view>
          <view class="modify-password">
            <cmd-input v-model="query.againPwd" type="password" maxlength="26" placeholder="请再次确认新密码"></cmd-input>
          </view>
          <button class="btn-modify btn-modify-active"
            hover-class="btn-modify-hover" @click="commitData">提交</button>
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
        query: {
          oldPwd: '',
		  newPwd: '',
		  againPwd: '',
        },
        phoneReg: /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/,
      };
    },

    methods: {
	  // 提交信息
	  async commitData(){
		  if (this.validData()){
			  let ret = await this.$http({
				  url: '/changePwd',
				  method: 'POST',
				  data: this.query
			  })
			  if (ret.code == 0){
				  uni.showToast({
					  title: ret.message + ",请重新登录"
				  })
				  setTimeout(function() {
					  uni.navigateTo({
					  	url: '/pages/login/login'
					  })
				  }, 1000);
				  console.log("hello");
			  }else{
				  uni.showToast({
				  	icon: 'none',
					title: ret.message
				  })
			  }
		  }
	  },
	  // 验证表单
	  validData(){
		if (this.query.oldPwd == ''){
			uni.showToast({
				icon: 'none',
				title: '旧密码不能为空'
			})
			return false;
		}
		if (this.query.newPwd == ''){
			uni.showToast({
				icon: 'none',
				title: '新密码不能为空'
			})
			return false;
		}
		if (this.query.againPwd != this.query.newPwd){
			uni.showToast({
				icon: 'none',
				title: '两次密码不一致'
			})
			return false;
		}
		return true;
	  },
      /**
       * 获取验证码
       */
      fnGetPhoneCode() {
        if (this.phoneReg.test(this.mobile.phone)) {
          uni.showToast({
            title: "正在发送验证码",
            icon: "loading",
            success: () => {
              // 成功后显示倒计时60s后可在点击
              this.safety.state = true;
              // 倒计时
              this.safety.interval = setInterval(() => {
                if (this.safety.time-- <= 0) {
                  this.safety.time = 60;
                  this.safety.state = false;
                  clearInterval(this.safety.interval);
                }
              }, 1000);
              uni.showToast({
                title: "发送成功",
                icon: "success"
              })
            }
          })
        } else {
          uni.showToast({
            title: "手机号不正确",
            icon: "none"
          })
        }
      }
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
