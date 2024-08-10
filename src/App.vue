<template>
  <v-app class="background no-cursor ">
    <v-main :class="!isLoginPage && !isJoinPage ? 'main-container' : ''">
      <v-container v-if="!isLoginPage && !isJoinPage" class="main-content ">
        <v-row no-gutters>
          <!-- 사이드 메뉴 -->
          <v-col cols="2" class="side-menu-col ">
            <SideMenu />
          </v-col>

          <!-- 헤더와 콘텐츠 영역 -->
          <v-col cols="10" class="content-col" >
            <Header v-if="!isLoginPage && !isJoinPage" class="fixed-header "  />

            <!-- 콘텐츠 영역 -->
            <router-view />
            <Footer v-if="!isLoginPage && !isJoinPage" />
          </v-col>
        </v-row>
      </v-container>

      <!-- 로그인 페이지와 가입 페이지인 경우에 표시할 router-view -->
      <router-view v-else style="background-color: ghostwhite;"/>
    </v-main>
  </v-app>
</template>
<script>
import Header from './components/common/Header.vue';
import Footer from './components/common/Footer.vue';
import SideMenu from './components/common/Sidemenu.vue';

export default {
  name: 'App',
  components: {
    Header,
    Footer,
    SideMenu
  },
  computed: {
    isLoginPage() {
      return this.$route.path === '/login';
    },
    isJoinPage() {
      return this.$route.path === '/join';
    }
  }
}
</script>
<style>
.background {
  height: 100vh;
  overflow: hidden;
  margin: 0;
  background-image: url("@/assets/background2.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
.card-with-bg {
  background-image: url('@/assets/background2.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.textcard-with-bg {
  background-image: url('@/assets/background3.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}
.fixed-header {
  position: fixed;
  top: 0;
  left: 255px; /* 사이드 메뉴 너비만큼 왼쪽으로 위치 조정 */
  width: calc(100% - 255px); /* 전체 너비에서 사이드 메뉴 너비를 제외한 나머지 */
  background-color: transparent;
  z-index: 1000;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.side-menu-col {
  padding: 0; /* 사이드 메뉴의 기본 패딩을 제거합니다 */
}

.content-col {
  padding-left: 0; /* 헤더가 고정되어 있으므로 패딩을 제거합니다 */
  margin-top: 60px; /* 헤더 높이만큼 여백 추가 */
}

.main-container {
  display: flex;
  height: 100vh;
}

.main-content {
  display: flex;
  flex: 1;
  flex-direction: column;
}

.no-cursor {
  caret-color: transparent;
}

.v-text-field input, .v-text-field textarea {
  caret-color: auto;
}


/* Vuetify의 기본 텍스트 색상을 하얀색으로 설정 */
.v-typography {
  color: white !important;
}

.white-text {
  color: white;
}
</style>
