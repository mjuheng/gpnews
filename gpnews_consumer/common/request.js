// const BASE_URL = 'http://192.168.1.100:9001'
// const BASE_URL = 'http://192.168.43.38:9001'
const BASE_URL = 'http://192.168.1.102:9001'
export const $request = (options) => {
	return new Promise((resolve, reject) => {
		if (options.method == 'POST'){
			options.content_type = 'application/json';
			options.data = JSON.stringify(options.data);
		}
		uni.request({
			header: {
				'Content-Type': options.content_type || 'application/x-www-form-urlencoded',
				'Authorization': uni.getStorageSync("sessionId")
			},
			url: BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			success: (res) => {
				// 权限不足时跳转
				if (res.statusCode == 401){
					uni.navigateTo({
						url: "/pages/login/login?msg=请登录账号"
					})
				}
				// 错误信息
				if (res.data.code == 500){
					uni.showToast({
						icon: 'none',
						title: res.data.message
					})
					reject(res.data)
				}	
				
				resolve(res.data)
			},
			fail: (error) => {
				console.log("出错了");
				uni.showToast({
					icon: 'none',
					title: '请求接口失败'
				})
				reject(error)
			}
		})
	})

}