<template>
  <div class="my-image">
    <!-- 按钮 -->
    <div @click="open" class="btn_box">
      <!-- 如果value有图片传入的地址，使用默认图的地址 -->
      <img :src="value||defaultImage" alt />
    </div>
    <!-- 对话框 -->
    <el-dialog :visible.sync="dialogVisible" width="750px">
      <el-tabs type="card" v-model="activeName">
        
        <el-tab-pane label="上传图片" name="upload">
          <!-- 上传组件 -->
          <el-upload
            :headers="headers"
            :on-success="handleSuccess"
            :show-file-list="false"
            :action='this.FILE + "/image/upload"'
            class="avatar-uploader"
            name="image"
          >
            <img :src="uploadImageUrl" class="avatar" v-if="uploadImageUrl" />
            <i class="el-icon-plus avatar-uploader-icon" v-else></i>
          </el-upload>
        </el-tab-pane>
        <el-tab-pane label="素材库" name="image">
          <!-- 按钮 -->
          <!-- <el-radio-group @change="toggleList" size="small" v-model="reqParams.collect">
            <el-radio-button :label="false">全部</el-radio-button>
            <el-radio-button :label="true">收藏</el-radio-button>
          </el-radio-group> -->
          <!-- 列表 -->
          <div class="img_list">
            <div
              :class="{selected:selectedImageUrl===item.imageUrl}"
              :key="item.id"
              @click="selectedImage(item.imageUrl)"
              class="img_item"
              v-for="item in materials"
            >
              <img :src="item.imageUrl" />
            </div>
          </div>
          <!-- 分页 -->
          <el-pagination
            :current-page="reqParams.currPage"
            :page-size="reqParams.rows"
            :total="total"
            @current-change="pager"
            background
            layout="prev, pager, next"
          ></el-pagination>
        </el-tab-pane>
      </el-tabs>
      <span class="dialog-footer" slot="footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button @click="confirmImage" type="primary">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import local from '@/utils/local'
import defaultImage from '@/assets/img/default.png'
export default {
  // value 在发布文章的时候没有值，编辑的时候才有图片数据。
  props: ['value'],
  name: 'my-image',
  data () {
    return {
      // 控制对话框显示与隐藏
      dialogVisible: false,
      // 当前激活选项卡的name的值
      activeName: 'upload',
      // 提交的筛选参数
      reqParams: {
        collect: false,
        currPage: 1,
        rows: 8
      },
      // 图片列表
      materials: [],
      // 总条数
      total: 0,
      // 当前选中的图片地址
      selectedImageUrl: null,
      // 请求头 上传组件
      headers: {
        Authorization: window.localStorage.sessionId
      },
      // 当前上传的图片地址
      uploadImageUrl: null,
      // 按钮的默认图片
      defaultImage
    }
  },
  methods: {
    // 确认图片
    confirmImage () {
      if (this.activeName === 'image') {
        // 素材库
        if (!this.selectedImageUrl) {
          return this.$message.warning('请选中一张图片')
        }
        // 给img的src赋值图片地址
        // this.defaultImage = this.selectedImageUrl
        // 提交图片地址给父组件
        this.$emit('input', this.selectedImageUrl)
        this.dialogVisible = false
      } else {
        // 上传图片
        if (!this.uploadImageUrl) {
          return this.$message.warning('请上传一张图片')
        }
        // 给img的src赋值图片地址
        // this.defaultImage = this.uploadImageUrl
        // 提交图片地址给父组件
        this.$emit('input', this.uploadImageUrl)
        this.dialogVisible = false
      }
    },
    // 上传成功
    handleSuccess (res) {
      // 预览
      this.uploadImageUrl = res.data
    },
    // 选中图片
    selectedImage (url) {
      this.selectedImageUrl = url
    },
    // 切换 全部与收藏 列表
    toggleList () {
      this.reqParams.currPage = 1
      this.getMaterial()
    },
    // 分页
    pager (newPage) {
      this.reqParams.currPage = newPage
      this.getMaterial()
    },
    // 打开对话框
    open () {
      this.dialogVisible = true
      // 获取素材列表
      this.getMaterial()
    },
    // 获取素材列表
    async getMaterial () {
      // 获取数据
      let ret = await local.sendGet(this.PUBLISH + '/material', this.reqParams)
      if (ret != null){
        this.materials = ret.data.dataList
        this.total = ret.data.totalCount
      }
      // const {
      //   data: { data }
      // } = await this.$http.get('user/images', { params: this.reqParams })
      // // 赋值给images
      // this.images = data.results
      // // 总条数赋值
      // this.total = data.total_count
    }
  }
}
</script>

<style scoped lang='less'>
.my-image {
  display: inline-block;
  margin-right: 20px;
}
.btn_box {
  width: 150px;
  height: 150px;
  border: 1px dashed #ddd;
  img {
    width: 100%;
    height: 100%;
    display: block;
  }
}
.dialog-footer {
  width: 100%;
  text-align: center;
  display: inline-block;
}
.img_list {
  margin-top: 20px;
  .img_item {
    width: 150px;
    height: 120px;
    border: 1px dashed #ddd;
    position: relative;
    display: inline-block;
    margin-right: 20px;
    margin-bottom: 20px;
    img {
      width: 100%;
      height: 100%;
    }
    &.selected::after {
      content: '';
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.3) url(../../assets/img/selected.png) no-repeat
        center / 50px 50px;
    }
  }
}
</style>
