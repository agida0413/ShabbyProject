import { createRouter, createWebHistory } from 'vue-router'
import Login from "@/components/member/Login.vue"
import Join from '@/components/member/Join.vue'
import UserFeed from '@/components/feed/userfeeds/UserFeed.vue'
import globalfeed from '@/components/feed/otherfeeds/Globalfeeds.vue'
import Mainfeed from '@/components/feed/otherfeeds/Mainfeeds.vue'
import Searchmain from '@/components/feed/searchfeeds/Searchmain.vue'
import axios from 'axios'
const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/join',
    name: 'join',
    component: Join
  },
  {
    path: '/userfeed',
    name: 'userfeed',
    component: UserFeed,
    meta: { requiresAuth: true }, // 인증이 필요한 라우터
  },
  {
    path: '/',
    name: 'mainfeed',
    component:Mainfeed,
    meta: { requiresAuth: true }, // 인증이 필요한 라우터
   
  }
  ,
  {
    path: '/globalfeed',
    name: 'globalfeed',
    component:globalfeed,
    meta: { requiresAuth: true }, // 인증이 필요한 라우터
   
  }
  ,
  {
    path: '/searchfeed',
    name: 'searchfeed',
    component:Searchmain,
    meta: { requiresAuth: true }, // 인증이 필요한 라우터
   
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


//서버 인증 전 1차적으로 라우터 가드로 페이지별 검증 = > 인증이 필요한 라우터는  토큰이 필요하다 
// 토큰 만료, 유효성 여부는 서버에서 검증, 프론트엔드 1차적 조치
router.beforeEach((to, from, next) => {
 
  const token = localStorage.getItem('access');

  //로그인 인증이 된상태에서 로그인페이지 요청을 했을경우
  if ((to.fullPath === '/login' || to.fullPath === '/join')) {

    if (token) {

      // 토큰이 존재하면 재발급 요청을 보냄
      axios.post('/reissue', {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(()=> {
        // 토큰 재발급 성공 후 홈으로 리다이렉트

        next({ path:  "/"});
      })
      .catch(() => {
        //실패하면 엑세스토큰 지우고 로그인 페이지로 라우팅
      localStorage.removeItem('access')
       router.push('/login')
    
       
      });
    } else {
      // 토큰이 없으면 로그인 페이지로 이동
      next();
    }
  } else if (to.matched.some(record => record.meta.requiresAuth)) {
    // 인증이 필요한 라우트인 경우
    if (token) {
      next();
    } else {
      
      localStorage.setItem('requestUrl', to.fullPath);// 이전요청을 localstorage에 저장함 - > 로그인성공시 해당요청으로 이동하기 위함
      next({ name: 'login' });// 토큰이 없으면 로그인 페이지로 리다이렉트
    }
  } else {
    next();
  }
});

export default router
