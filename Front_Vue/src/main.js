import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import Footer from './components/common/Footer.vue'
import Header from './components/common/Header.vue'
import Sidemenu from './components/common/Sidemenu.vue'
import api from '@/api'



// axios 요청 전 인터셉터 설정
api.interceptors.request.use(config => {
  const token = localStorage.getItem('access'); // 로컬 스토리지에서 토큰 가져오기
  if (token) {
    config.headers.access = token; //  헤더에 access토큰 추가
  }
  return config;
}, error => {
  return Promise.reject(error);
});

// axios 응답 인터셉터 설정
api.interceptors.response.use(response => {
  return response;
}, error => {
  const originalRequest = error.config;

  // `401 Unauthorized` 응답을 받은 경우
  if (error.response && error.response.status === 401 && !originalRequest._retry) {
    originalRequest._retry = true;

    // access 토큰 재발급 요청 
    return api.post('/api/reissue', null, {
      headers: { //쿠키에 refresh토큰은 저장되어있다.
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(response => {
      // 새 액세스 토큰 저장
      const newToken = response.headers['access'];
      localStorage.setItem('access', newToken);
      
      // 새 액세스 토큰을 포함한 원래 요청 재시도
      // originalRequest.headers.access = newToken; // 재시도 요청에 새로운 토큰 포함 (axios요청 전 인터셉터로 헤더에 담고있으니 일단 주석처리) 
      return api(originalRequest);
    })
    .catch(err => {
      console.error('Token refresh failed', err);
      
      
      // 토큰 갱신 실패 로그인 페이지로 리다이렉트
      router.push({ name: 'login' });

     
    });
  }

  // 다른 오류 처리
  return Promise.reject(error);
});

loadFonts();

createApp(App)
.component('Footer', Footer)
.component('Header', Header)
.component('Sidemenu', Sidemenu)
.use(router)
.use(vuetify)
.mount('#app');
