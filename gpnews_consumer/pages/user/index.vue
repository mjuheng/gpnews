<template>
  <view>
    <view class="person-head">
      <cmd-avatar :src="userInfo.photo" @click="fnInfoWin" size="lg" :make="{'background-color': '#fff'}"></cmd-avatar>
      <view class="person-head-box">
        <view class="person-head-nickname">{{ userInfo.username }}</view>
      </view>
    </view>
    <view class="person-list">
      <cmd-cell-item title="我的收藏" slot-left arrow @click="toFavorites">
        <cmd-icon type="star" size="24" color="#368dff"></cmd-icon>
      </cmd-cell-item>
	  <cmd-cell-item title="反馈意见" slot-left arrow @click="toSendMessage">
	    <cmd-icon type="message" size="24" color="#368dff"></cmd-icon>
	  </cmd-cell-item>
      <cmd-cell-item title="退出登录" slot-left arrow @click="logout">
        <cmd-icon type="alert-circle" size="24" color="#368dff"></cmd-icon>
      </cmd-cell-item>
    </view>
  </view>
</template>

<script>
  import cmdAvatar from "@/components/cmd-avatar/cmd-avatar.vue"
  import cmdIcon from "@/components/cmd-icon/cmd-icon.vue"
  import cmdCellItem from "@/components/cmd-cell-item/cmd-cell-item.vue"

  export default {
    components: {
      cmdAvatar,
      cmdCellItem,
      cmdIcon
    },
    data() {
      return {
		  userInfo: {}
	  };
    },
	onLoad() {
		this.checkPerm()
	},
	onShow(){
		this.userInfo = uni.getStorageSync("userInfo")
	},
    methods: {
	  async checkPerm(){
		let ret = await this.$http({url: '/checkPerm'})
		if (!ret.data) {
			uni.navigateTo({
				url: "/pages/login/login",
			})
		}
	  },
      /**
       * 打开用户信息页
       */
      fnInfoWin() {
        uni.navigateTo({
          url: '/pages/user/info'
        })
      },
	  toFavorites() {
		uni.navigateTo({
			url: '/pages/favorites/index'
		})
	  },
	  // 跳转至问题反馈界面
	  toSendMessage(){
		uni.navigateTo({
			url: '/pages/user/send_message'
		})
	  },
	  // 退出登录
	  logout(){
		  this.$http({url: '/logout'});
		  uni.removeStorageSync("sessionId");
		  uni.removeStorageSync("userInfo");
		  uni.navigateTo({
		  	url: '/pages/login/login'
		  })
	  }
    }
  }
</script>

<style>
  .person-head {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 150px;
    padding-left: 20px;
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .person-head-box {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    margin-left: 10px;
  }

  .person-head-nickname {
    font-size: 18px;
    font-weight: 500;
    color: #fff;
  }

  .person-head-username {
    font-size: 14px;
    font-weight: 500;
    color: #fff;
  }

  .person-list {
    line-height: 0;
  }
</style>
