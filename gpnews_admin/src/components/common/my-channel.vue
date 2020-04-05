<template>
  <el-select :value="value" @change="categoryChange" clearable placeholder="全部分类">
	<el-option label="" value=""></el-option>
    <el-option :key="item.id" :label="item.name" :value="item.id" v-for="item in categorys"></el-option>
  </el-select>
</template>

<script>
import local from '@/utils/local'
export default {
  name: 'my-channel',
  props: ['value'],
  data () {
    return {
      // value: null,
      categorys: []    }
  },
  created () {
    this.getCategory()
  },
  methods: {
    // 频道改变函数
    categoryChange (categoryId) {
      // 清空值是'' 改成null
      if (categoryId === '') categoryId = null
      // 把ID提交给父组件
      this.$emit('input', categoryId)
    },
    // 获取分类选项数据
    async getCategory(){
      let ret = await local.sendGet(this.ADMIN + "/category");
      if (ret == null) return;
      this.categorys = ret.data.dataList;
    }
  }
}
</script>

<style scoped lang='less'>
</style>
