import { createRouter, createWebHistory } from 'vue-router'
import Login from "@/components/member/Login.vue"
import Join from '@/components/member/Join.vue'
import UserFeed from '@/components/feed/userfeeds/UserFeed.vue'
import globalfeed from '@/components/feed/otherfeeds/Globalfeeds.vue'
import Mainfeed from '@/components/feed/otherfeeds/Mainfeeds.vue'
import Searchmain from '@/components/feed/searchfeeds/Searchmain.vue'
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
    component: UserFeed
  },
  {
    path: '/',
    name: 'mainfeed',
    component:Mainfeed
   
  }
  ,
  {
    path: '/globalfeed',
    name: 'globalfeed',
    component:globalfeed
   
  }
  ,
  {
    path: '/searchfeed',
    name: 'searchfeed',
    component:Searchmain
   
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
