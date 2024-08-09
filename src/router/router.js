import { createRouter, createWebHistory } from 'vue-router'
import Login from "@/components/member/Login.vue"
import HelloWorld from '@/components/HelloWorld.vue'
import Join from '@/components/member/Join.vue'
import UserFeed from '@/components/feed/UserFeed.vue'
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
    name: 'HelloWorld',
    component:HelloWorld
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
   
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
