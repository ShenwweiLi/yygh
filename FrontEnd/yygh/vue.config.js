// 在 webpack.config.js 或 vue.config.js 中配置
module.exports = {
  devServer: {
    proxy: {
      '/yygh': {
        target: 'http://localhost:8204',
        changeOrigin: true,
        pathRewrite: {
          '^/yygh': '/yygh/'
        }
      },
  
    },
  },
};