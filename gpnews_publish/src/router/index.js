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
					component: () => import('../components/page/dashboard/dashboard.vue'),
					meta: { title: '系统首页' }
				},
				{
					path: 'articleContent',
					component: () => import('../components/page/articledata/ArticleContent.vue'),
					meta: {title: '内容管理'}
				},
				{
					path: 'articlePublish',
					component: () => import('../components/page/articledata/ArticlePublish.vue'),
					meta: {title: '发布文章'}
				},
				{
					path: 'commentManage',
					component: () => import('../components/page/articledata/CommentManage.vue'),
					meta: {title: '评论管理'}
				},
				{
					path: 'materialManage',
					component: () => import('../components/page/articledata/MaterialManage.vue'),
					meta: {title: '素材管理'}
				},
				{
					path: 'fanSummary',
					component: () => import('../components/page/fan/FanSummary.vue'),
					meta: {title: '粉丝概述'}
				},
				{
					path: 'fanList',
					component: () => import('../components/page/fan/FanList.vue'),
					meta: {title: '粉丝列表'}
				},
				{
					path:'myMsg',
					component: () => import('../components/page/msgcenter/MyMsg.vue')
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
