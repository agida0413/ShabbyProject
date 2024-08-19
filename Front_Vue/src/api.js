
import axios from "axios";
import router from './router/router'



const api = axios.create({
    baseURL: '/api', // 모든 요청의 기본 URL에 /api를 추가
    
  });

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
 
  // `410 Unauthorized` 응답을 받은 경우
  if (error.response && error.response.status === 410 && !originalRequest._retry) {
    originalRequest._retry = true;

    // access 토큰 재발급 요청 
    
    return api.post('/reissue')
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
   else if(error.response && error.response.status === 403){//시큐리티 관련 에러(글로벌 익셉션이 안먹으므로)
  alert('권한이 없습니다.')
  }
  else if(error.response && error.response.status === 401){
    alert('잘못된 접근입니다.')
  }
  
  


return Promise.reject(error);
});


  export default api;