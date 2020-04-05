<template>
<mavon-editor
  class="md"
  :value="data.content"
  :subfield = "false"
  :defaultOpen = "'preview'"
  :toolbarsFlag = "false"
  :editable="false"
  :scrollStyle="true"
  :ishljs = "true"
></mavon-editor>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import local from '@/utils/local';
export default {
	name: 'articleDetail',
	components: {
		mavonEditor
	},
	data() {
		return {
			data: {},
			articleId: ''
		}
	},
	created() {
		this.articleId = this.$route.query.id
		this.getData()
	},
	methods: {
		async getData(){
			let ret = await local.sendGet(this.ADMIN + '/article/' + this.articleId)
			if (ret != null){
				this.data = ret.data
			}
		}
	},
}
</script>
<style>
</style>
