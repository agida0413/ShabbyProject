<template >



  <v-card >
   
      <v-navigation-drawer
     class="to-blackMode"
       style="border-right:floralwhite 1px solid"
      >
        <template v-slot:prepend >
          <v-list-item
      
          >
          <!--로고-->
          <v-img
        class="mx-auto"
        max-width="60"
        src="@/assets/logo2.png"
      ></v-img> 
        
        </v-list-item>
        </template>

        <v-divider></v-divider>

        <v-list density="compact" nav>
        
          <v-list-item prepend-icon="mdi-pencil" title="새 게시물" value="account" @click="postInsertDialogOpen"></v-list-item>
              <router-link to="/globalfeed" class="router-link"> <v-list-item prepend-icon="mdi-account-group-outline" title="글로벌 피드" value="users"></v-list-item></router-link>
              <router-link to="/" class="router-link"> <v-list-item prepend-icon="mdi-home-city" title="메인 피드" value="users"></v-list-item></router-link>
              <router-link to="/userfeed" class="router-link"><v-list-item prepend-icon="mdi-home" title="내 피드" value="home"></v-list-item></router-link>
              <v-list-item prepend-icon="mdi-heart-outline" title="활동" value="users"></v-list-item>
                <v-list-item prepend-icon="mdi-wrench" title="설정" value="home" @click="settingDialogOpen()"></v-list-item>
                <v-list-item prepend-icon="mdi-lock" title="로그아웃" value="users"></v-list-item>
        </v-list>
      </v-navigation-drawer>
    
    
  </v-card>
  <SettingComponent v-model:value="settingDialog" @settingClose="ifSettingClose"></SettingComponent> <!-- 세팅 모달 컴포넌트 -->
  <PostInsert v-model:value="postInsertDialog" @postInsertClose="postInsertDialogClose"></PostInsert><!--새 게시물 작성 모달 컴포넌트-->
</template>

<script>
  import PostInsert from "../feed/PostInsert.vue";
import SettingComponent from "./Setting.vue"
export default{

  data(){
    return{
      settingDialog:false, //세팅 모달 제어값
      postInsertDialog:false //새 게시물 작성 제어값 
    }
  },
  components:{
    SettingComponent,
    PostInsert
  },
  methods:{
    //모달 열고,닫음 이벤트 제어 메소드
    ifSettingClose(){
      this.settingDialog=false 
    },
    settingDialogOpen(){
      this.settingDialog=true
    },
    postInsertDialogOpen(){
      this.postInsertDialog=true
    },
    postInsertDialogClose(){
      this.postInsertDialog=false
    }
  }
}

</script>

<style>
.router-link {
  text-decoration: none; /* 링크의 기본 밑줄 제거 */
  color: inherit; /* 링크 색상을 상속 받아서 v-list-item의 색상에 영향을 주지 않도록 설정 */
}
</style>