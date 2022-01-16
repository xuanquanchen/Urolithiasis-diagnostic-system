module.exports = {
  /** 适配IE，转es5 */
  transpileDependencies: [/node_modules[/\\\\](crypto-js)[/\\\\]/],
  /** 开发环境开启代理 */
  devServer: {
    open: false,
    proxy: {
      '/api': {
        target: 'https://health.raccooncc.top',
        changeOrigin: true
      }
    }
  }
}
