import { createRouter, createWebHistory } from 'vue-router'
import Login from "@/components/member/Login.vue"
import Join from '@/components/member/Join.vue'
import UserFeed from '@/components/feed/userfeeds/UserFeed.vue'
import globalfeed from '@/components/feed/otherfeeds/globalfeed.vue'
import Mainfeed from '@/components/feed/otherfeeds/mainfeed.vue'
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
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
