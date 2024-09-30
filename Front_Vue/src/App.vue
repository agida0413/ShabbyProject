<template >
  <v-row v-if="isLoading " class="loading-overlay">
      <v-col class="d-flex justify-center align-center">
        <v-progress-circular indeterminate color="primary"></v-progress-circular>
      </v-col>
    </v-row>

  <v-app class="no-cursor to-blackMode ">
    <v-main :class="!isLoginPage && !isJoinPage ? 'main-container' : ''"  >
      <v-container v-if="!isLoginPage && !isJoinPage" class="main-content " >
        <v-row no-gutters >
          <!-- 사이드 메뉴 -->
         
            <SideMenu 
            @childLoading="isLoadingHandle"
            v-if="!isLoginPage" />
        

          <!-- 헤더와 콘텐츠 영역 -->
          <!-- 로그인 페이지와 회원가입페이지는 헤더 노출 x-->
            <Header v-if="!isLoginPage && !isJoinPage" class="fixed-header "  ></Header>

            <!-- 콘텐츠 영역 -->
             <v-container style=" margin-top: 60px; background-color: #2C2C2C;"  class="scrollable-row "> <router-view /></v-container>
           <!-- 로그인 페이지와 회원가입페이지는 푸터 노출 x-->
            <Footer v-if="!isLoginPage && !isJoinPage" ></Footer>
        
        </v-row>
      </v-container>

      <!-- 로그인 페이지와 가입 페이지인 경우에 표시할 router-view -->
      <router-view v-else style="background-color: ghostwhite;"/>

    </v-main>
  </v-app>
</template>
<script>
import Header from './components/common/layout/Header.vue';
import Footer from './components/common/layout/Footer.vue';
import SideMenu from './components/common/layout/Sidemenu.vue';

export default {
  name: 'App',
  data(){
    return{
      isLoading:true
    }
  },
  methods:{
    isLoadingHandle(){
      this.isLoading=false
    }
  },
  components: {
    //전역 컴포넌트 
    Header,  //헤더 , 검색바
    Footer,  //푸터 , 팔로우 추천 
    SideMenu // 사이드메뉴 
  },
  computed: {
    
    isLoginPage() {  //현재 라우터가 로그인페이지인지 확인
      return this.$route.path === '/login';
    },
    isJoinPage() {//현재 라우터가 회원가입인지 확인
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



.main-content {
  display: flex;
  flex: 1;
  flex-direction: column;
  
  height: calc(100vh - 60px); /* 헤더 높이를 뺀 나머지 높이 */
  
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




.to-blackMode{
  background-color: #2C2C2C;
  color: aliceblue;
}
.to-blackMode2{
  background-color: #4B4B4B ;
  color: aliceblue;
}
.button-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 4px; 
  }
  
  .load-more-btn {
    display: flex;
    align-items: center;
    font-size: 12px;
    font-weight: bold;
  }
  
  .load-more-btn .v-btn__content {
    margin-left: 8px; 
  }

  .to-doubleBlack{
  background-color: #2C2C2C;
}
  

.parentContainer{
  scrollbar-width: thin; /* Firefox */
  scrollbar-color: #888 transparent; /* Firefox */
}
.parentContainer::-webkit-scrollbar {
  width: 5px; /* 스크롤 바의 너비 */
}

.parentContainer::-webkit-scrollbar-track {
  background: transparent; /* 스크롤 바 트랙 배경색 */
}

.parentContainer::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 색상 */
  border-radius: 10px; /* 스크롤 바 모서리 둥글게 */
}

.parentContainer::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 색상 (호버 시) */
}
</style>