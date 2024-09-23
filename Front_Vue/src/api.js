import axios from 'axios';
import router from './router/router';

const api = axios.create({
    baseURL: '/api', //기본 url prefix
});

let isRefreshing = false; //현재 재발급중인지 여부 
let failedQueue = []; //토큰 재발급 실패시 대기중인 요청 저장 큐

//큐에 있는 요청 처리 
const processQueue = (error, token = null) => {
  //큐 요청을 모두 처리하거나  오류 전달
    failedQueue.forEach(prom => {
        if (token) {
            prom.resolve(token); //재발급된 토큰 으로 요청 처리 
        } else {
            prom.reject(error); //에러발생 
        }
    });
    failedQueue = [];//큐 초기화 
};

// 요청 인터셉터 설정
api.interceptors.request.use(config => {
  //로컬스토리지에 있는 access토큰 가져오기 
    const token = localStorage.getItem('access');
   
    //만약 있다면 
    if (token) {
      //헤더에 담아 전송
      
        config.headers.access = token;
    }
   
    
 
    return config;
}, error => {
  //요청중 오류 발생시 x 
    return Promise.reject(error);
});

// 응답 인터셉터 설정
api.interceptors.response.use(response => {
  //응답 성공시 그대로 반환 

    return response;
}, error => {
  //만약 응답오류가 있다면 
  if(error?.response?.status===403){
    alert('접근 불가합니다.')
    return Promise.reject(error);
  }
  const token = localStorage.getItem("access"); // 토큰이 있다면 
  const originalRequest = error.config;

  // 특정 URL에서 아무 것도 수행하지 않도록 설정
  if(error?.response?.data?.message==='서버 내부 오류입니다. 잠시 뒤 이용해 주세요.' 
    ||error?.response?.data?.message==='서버 내부 오류입니다.'
    ||error?.response?.data?.message==='정지된 사용자입니다.'
    ||error?.response?.data?.message==='회원님의 계정이 정지처리 되었습니다. 관리자에게 문의하세요.'
  ){
    router.push('/error'); // this 대신 router 사용
    return;
  } 
  if(error?.response?.status===417){
    alert('회원님의 계정이 정지처리 되었습니다. 관리자에게 문의하세요.')
    localStorage.removeItem('access')
    router.push('/login')
  }  
  
  // 410 상태 코드 오류가 발생하고, 엑세스 토큰이 있고 ,재발급 시도하지 않았으면
  if (error.response && error.response.status === 410 && !originalRequest._retry &&token ) {
        //만약 재발급 진행중이라면 
     
        if (isRefreshing) {
  
          //새로 요청을 큐에 추가, 재발급 완료 후 요청 처리 
            return new Promise((resolve, reject) => {
                failedQueue.push({ resolve, reject });
            })
            .then(token => {
                originalRequest.headers.access = token;
                return api(originalRequest);
            })
            .catch(err => {
                return Promise.reject(err);
            });
        }

        originalRequest._retry = true; //재발급 요청 플래그 
        isRefreshing = true;//재발급 상태로 설정

        return new Promise((resolve, reject) => {
       
            api.post('/reissue')
                .then(({ headers }) => {
                  // 새 액세스 토큰 저장
                    const newToken = headers['access'];
                    localStorage.setItem('access', newToken);                 
                    processQueue(null, newToken); // 큐에 있는 요청을 새 토큰으로 처리
                    isRefreshing = false; // 재발급 완료 상태로 변경
                    resolve(newToken); // 새 토큰 반환
                })
                .catch(err => {
                  // 재발급 실패 시 큐에 있는 요청에 오류 전달
                  processQueue(err, null);
                  localStorage.removeItem('access'); // 액세스 토큰 삭제
                  router.push({ name: 'login' }); // 로그인 페이지로 리다이렉트
                  isRefreshing = false; // 재발급 완료 상태로 변경
                  reject(err); // 오류 반환
                });
        })       
        .then(token => {
               // 재발급 완료 후 원래 요청에 새 토큰을 추가하고 재시도
            originalRequest.headers.access = token;
            return api(originalRequest);
            

        })
        .catch(err => {
            return Promise.reject(err);
        });
    }
     // 기타 오류는 그대로 반환
    return Promise.reject(error);
});

export default api;
