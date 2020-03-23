import axios from '@/utils/request'
import { Message } from 'element-ui'
// 导出一个本地操作用户信息信息的模块(设置用户,获取用户,清除用户)
const KEY = 'user'
const local = {
  setUser (user) {
    // 存储 setItem(key,value) key将来获取时候需要使用  value json字符串格式
    const jsonStr = JSON.stringify(user)
    localStorage.setItem(KEY, jsonStr)
  },
  getUser () {
    const jsonStr = localStorage.getItem(KEY)
	  if (jsonStr == 'undefined') return null
      return JSON.parse(jsonStr)
  },
  delUser () {
    localStorage.removeItem(KEY)
    localStorage.removeItem('sessionId')
    // clear() 清除所有的数据  不建议使用
    // setItem(KEY,'') 删除除
  },
  async sendPost(url, data){
    let ret = await axios({
      url: url,
      method: 'post',
      data: data,
    }).catch(error => {
      console.log(error)
      return null
    })
    if (ret.status == 200 && ret.data.code == 0){
      if (ret.data.data === true)
        Message({type: 'success', message: ret.data.message})
      return ret.data;
    }else if (ret.status == 200 && ret.data.code == 500){
      Message({type: 'error', message: ret.data.message})
      return null;
    }
  },
  async sendGet(url, params){
    let ret = await axios({
      url: url,
      method: 'get',
      params: params,
    }).catch(error => {
      console.log(error)
      return null
    })
    if (ret != null){
      if (ret.status == 200 && ret.data.code == 0){
        if (ret.data.data === true){
          Message({type: 'success', message: ret.data.message})
		}
        return ret.data;
      }else if (ret.status == 200 && ret.data.code == 500){
        Message({type: 'error', message: ret.data.message})
        return null;
      }
    }else{
      return null;
    }
  }
}
export default local
