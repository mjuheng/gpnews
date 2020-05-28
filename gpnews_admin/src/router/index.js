import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
				{
					path: '/dashboard',
					component: () => import('../components/page/dashboard/Dashboard.vue'),
					meta: { title: '系统首页' }
				},
                {
                    path: 'userManage',
                    component: () => import('../components/page/system/UserManage.vue'),
                    meta: {title: '用户管理'}
                },
                {
                    path: 'roleManage',
                    component: () => import('../components/page/system/RoleManage.vue'),
                    meta: {title: '角色管理'}
                },
				{
					path: 'onlineUser',
					component: () => import('../components/page/monitor/OnlineUser.vue'),
					meta: {title: '在线用户'}
                },
                {
					path: 'systemLog',
					component: () => import('../components/page/monitor/SystemLog.vue'),
					meta: {title: '系统日志'}
				},
				{
					path: 'operationMonitoring',
					component: () => import('../components/page/monitor/OperationMonitoring.vue'),
					meta: {title: '运行监控'}
				},
				{
					path: 'articleReview',
					component: () => import('../components/page/censor/ArticleReview.vue'),
					meta: {title: '待审文章'}
				},
				{
					path: 'articleContent',
					component: () => import('../components/page/censor/ArticleContent.vue'),
					meta: {title: '内容管理'}
				},
				{
					path: 'categoryManage',
					component: () => import('../components/page/censor/CategoryManage.vue'),
					meta: {title: '分类管理'}
				},
				{
					path: 'myMsg',
					component: () => import('../components/page/msgcenter/MyMsg.vue'),
					meta: {title: '我的消息'}
				},
				{
					path: 'taskInfo',
					component: () => import('../components/page/systemtask/TaskInfo.vue'),
					meta: {title: '任务信息'}
				},
				{
					path: 'taskLog',
					component: () => import('../components/page/systemtask/TaskLog.vue'),
					meta: {title: '任务记录'}
				},
				{
					path: 'userSetting',
					component: () => import('../components/page/usersetting/UserSetting.vue'),
					meta: {title: '个人设置'}
				},
				{
					path: 'articleDetail',
					component: () => import('../components/page/ArticleDetail.vue'),
					meta: {title: '文章详情'}
				},
                {
                    // vue-schart组件
                    path: '/charts',
                    component: () => import(/* webpackChunkName: "chart" */ '../components/page/BaseCharts.vue'),
                    meta: { title: 'schart图表' }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                }

            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
