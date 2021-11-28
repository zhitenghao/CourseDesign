// 跨域请求后端接口
let proxyObj = {}

proxyObj['/auth'] = {
  // wedSocket（代理，带/auth的请求会访问后端）
  // /test自动带前缀http://localhost:8080
  // 会被替换为target:http://localhost:8081，即/auth/test变成http://localhost:8081/auth/test
  // 向后端的请求都需要带上/auth
  ws: false,
  // 目标地址
  target: 'http://localhost:8081',
  // 发送请求头host会被设置target
  changeOrigin: true,
  // 重写请求地址
  pathRewrite: {
    '^/auth': '/'  //传向后端的路径替换/auth为/
  }
}

module.exports = {
  devServer: {
    host: 'localhost',
    port: 8080,//前端端口
    proxy: proxyObj
  }
}
