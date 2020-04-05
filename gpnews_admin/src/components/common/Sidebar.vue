<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.children">
                    <el-submenu :index="item.name" :key="item.name">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.cname }}</span>
                        </template>
                        <template v-for="subItem in item.children">
                            <el-submenu
                                v-if="subItem.children"
                                :index="subItem.name"
                                :key="subItem.name"
                            >
                                <template slot="title">{{ subItem.cname }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.children"
                                    :key="i"
                                    :index="threeItem.name"
                                >{{ threeItem.cname }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.name"
                                :key="subItem.name"
                            >{{ subItem.cname }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
               <template v-else>
                    <el-menu-item :index="item.name" :key="item.name">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.cname }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import local from '@/utils/local';
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: []
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
		this.getData()
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    },
	methods: {
		async getData(){
			let ret = await local.sendGet(this.ADMIN + '/perm/getPerm')
			if (ret != null){
				this.items = ret.data
			}
			this.items.unshift({
				name: 'dashboard',
				cname: '系统首页',
				icon: 'el-icon-s-home'
			})
		}
	}
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
