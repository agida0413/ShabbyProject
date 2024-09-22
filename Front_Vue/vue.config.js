const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  // devServer: {
    
  //   // proxy: {
  //   //   '/api': { 
  //   //     target: 'http://localhost:8080',
  //   //     changeOrigin: true,
  //   //     ws:false,
        
  //   //   }, 
  //   // },
  // },
  // outputDir:'C:/Users/TA9/git/termPj/ShabbyProject_Api_Server/src/main/resources/static',
  outputDir:'C:/Users/user/git/ShabbyProject/ShabbyProject_Api_Server/src/main/resources/static',
  
  transpileDependencies: true,
  pluginOptions: {
    vuetify: {
			// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
		}
  }
})
