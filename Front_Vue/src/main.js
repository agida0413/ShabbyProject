import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import Footer from './components/common/Footer.vue'
import Header from './components/common/Header.vue'
import Sidemenu from './components/common/Sidemenu.vue'

/*
문제 해결 기록 
1. axios 를 이용해서 api를 호출하고 서버에서는 요청을 받아서 처리하고 있는데 , 해당 컴포넌트를 닫아서 unmount 된 상태이므로 , catch 블록에서 err.response 가 undefined여서 에러구문이 뜸
 ====>  catch 블록에서 err.response가 존재하면 이라는 구문을 추가해서 에러 출력을 막음 
 
 2. 중복호출 방지 (일명 따닥 ) = > 1차적으로 프론트단에서 전송중일때는 버튼을 비활성화 해서막고 2차적으로 서버단에서 인터셉터를 통해서 맵에 요청고유값을 담고 요청이 완료되면 맵에서 지우고 
 요청이 들어왔을때 해당요청이 존재하면 409를 반환하는 방식으로 막음 
*/



loadFonts();

createApp(App)
.component('Footer', Footer)
.component('Header', Header)
.component('Sidemenu', Sidemenu)
.use(router)
.use(vuetify)
.mount('#app');

