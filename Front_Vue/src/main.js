import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import Footer from './components/common/layout/Footer.vue'
import Header from './components/common/layout/Header.vue'
import Sidemenu from './components/common/layout/Sidemenu.vue'

/*
문제 해결 기록 
1. axios 를 이용해서 api를 호출하고 서버에서는 요청을 받아서 처리하고 있는데 , 해당 컴포넌트를 닫아서 unmount 된 상태이므로 , catch 블록에서 err.response 가 undefined여서 에러구문이 뜸
 ====>  catch 블록에서 err.response가 존재하면 이라는 구문을 추가해서 에러 출력을 막음 
 
 2. 중복호출 방지 (일명 따닥 ) = > 1차적으로 프론트단에서 전송중일때는 버튼을 비활성화 해서막고 2차적으로 서버단에서 인터셉터를 통해서 맵에 요청고유값을 담고 요청이 완료되면 맵에서 지우고 
 요청이 들어왔을때 해당요청이 존재하면 409를 반환하는 방식으로 막음 

 3. axios 인터셉터에서 토큰 재발급을 위해 response를 인터셉터 할때  410 응답코드를 받으면 리이슈 api를 보내고 받으면 재요청을 보내는 식으로 구성했었는데
 만약 기존요청에도 응답오류코드가 있다면 자꾸 재발급 도중 다른 에러가 끼어 토큰이 무조건 만료되는 현상이 발생했다 .
 따라서 토큰 리이슈 요청중에는 다른 요청을 보내지 않도록 하기위해 큐를 이용하여 isRefreshing 상태변수를 주어 현재 재발급 진행중이면 큐에 넣어놓고 재발급에 성공하면 
 요청을 보내는 식으로 구성하였다.
*/



loadFonts();

createApp(App)
.component('Footer', Footer)
.component('Header', Header)
.component('Sidemenu', Sidemenu)
.use(router)
.use(vuetify)
.mount('#app');

