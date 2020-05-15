module.exports = {
    baseUrl: './',
    assetsDir: 'static',
    productionSourceMap: false,
	devServer: {
		open: true,
		host: 'localhost',
		port: 8081,
		https: false,
		proxy: {
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