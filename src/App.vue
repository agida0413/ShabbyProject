<template>
  <v-app class="background">
    <v-main :class="!isLoginPage && !isJoinPage ? 'main-container' : ''">
      
      
     

      <!-- 로그인 페이지와 가입 페이지가 아닌 경우에만 v-card와 v-container 표시 -->
     
     
      <v-card
        v-if="!isLoginPage && !isJoinPage"
        class="mx-auto fixed-background islogged "
        width="1400"
        height="850"
      >

        <v-container >
          <Header v-if="!isLoginPage && !isJoinPage" ></Header>
        <!-- Header와 Footer는 로그인 페이지와 가입 페이지가 아닌 경우에만 표시 -->
          <router-view />

          <Footer v-if="!isLoginPage && !isJoinPage" ></Footer>
        </v-container>
      </v-card>

      <!-- 로그인 페이지와 가입 페이지인 경우에 표시할 router-view -->
     
      <router-view v-else />
    </v-main>
    
  </v-app>
</template>

<script>
import Header from './components/common/Header.vue';
import Footer from './components/common/Footer.vue';

export default {
  name: 'App',
  components: {
    Header, // 전역컴포넌트 헤더 ,푸터 
    Footer
  },
  computed: {
    isLoginPage() {
      return this.$route.path === '/login'; // 로그인 페이지 라우터이냐 returen boolean
    },
    isJoinPage() {
      return this.$route.path === '/join'; // 회원가입 페이지 라우터이냐 returen boolean
    }
  }
}
</script>

<style>
.background {
  height: 100vh;
  overflow: hidden;
  margin: 0;
  background-image: url("@/assets/background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}

.fixed-background {
  background-size: cover;
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
}

.fixed-background .v-container {
  overflow-y: auto;
  max-height: 100%;
}
.main-container {
  display: flex;
  justify-content: center; /* 수평 중앙 배치 */
  align-items: center; /* 수직 중앙 배치 */
  height: 100vh; /* 화면 전체 높이 */
}
</style>
