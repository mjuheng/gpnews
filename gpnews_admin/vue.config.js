module.exports = {
    baseUrl: './',
    assetsDir: 'static',
    productionSourceMap: false,
    // devServer: {
    //     proxy: {
    //         '/api':{
    //             target:'http://jsonplaceholder.typicode.com',
    //             changeOrigin:true,
    //             pathRewrite:{
    //                 '/api':''
    //             }
    //         }
    //     }
    // }
	devServer: {
		open: true,
		host: 'localhost',
		port: 8080,
		https: false,
		proxy: {
		  '/data-publish':{
			target: 'http://127.0.0.1:9002/',
			pathRewrite: {
			  '^/data-publish': ''
			},
			changeOrigin: true
		  },
		  '/data-file':{
			target: 'http://127.0.0.1:9000/',
			pathRewrite: {
			  '^/data-file': ''
			},
			changeOrigin: true
		  },
		  '/data-admin':{
			target: 'http://127.0.0.1:9003/',
			pathRewrite: {
			  '^/data-admin': ''
			},
			changeOrigin: true
		  }
			
		}
    }
}