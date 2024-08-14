import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import Footer from './components/common/Footer.vue'
import Header from './components/common/Header.vue'
import Sidemenu from './components/common/Sidemenu.vue'

loadFonts()

createApp(App)
.component('Footer',Footer)
.component('Header',Header)
.component('Sidemenu',Sidemenu)
  .use(router)
  .use(vuetify)
  .mount('#app')
