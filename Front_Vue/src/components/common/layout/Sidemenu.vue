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
              <template v-if="memberData.nickname">
              <router-link :to="{ name: 'userfeed', params: {nickname:this.memberData.nickname }}" class="router-link"><v-list-item prepend-icon="mdi-home" title="내 피드" value="home"></v-list-item></router-link>
              </template>
              <v-list-item prepend-icon="mdi-heart-outline" title="활동" value="users"></v-list-item>
                <v-list-item prepend-icon="mdi-wrench" title="설정" value="home" @click="settingDialogOpen()"></v-list-item>
                <v-list-item prepend-icon="mdi-lock" title="로그아웃" value="users" @click="logout()"></v-list-item>
        </v-list>

      </v-navigation-drawer>
    
    
  </v-card>
  <!-- 세팅 모달 컴포넌트 -->
  <SettingComponent v-model:value="settingDialog"
                    v-model:memberData="memberData"
                    @updateSideMenuInfo="reGetInfo"
   @settingClose="ifSettingClose"></SettingComponent> 

<!--새 게시물 작성 모달 컴포넌트-->
  <PostInsert v-model:value="postInsertDialog" @postInsertClose="postInsertDialogClose"></PostInsert>
</template>

<script>
import PostInsert from "@/components/feed/post/PostInsert.vue";
import SettingComponent from "../setting/Setting.vue"
import api from "@/api"
export default{

  data(){
    return{
      settingDialog:false, //세팅 모달 제어값
      postInsertDialog:false, //새 게시물 작성 제어값 
      memberData:{} //회원정보 
    }
  },
 
  components:{
    SettingComponent,//세팅 컴포넌트
    PostInsert//게시물 작성 컴포넌트
  },
 mounted(){
  this.getInitInfo()
 },
  methods:{
    //자식에서 받은 변한 상태값 매개변수로 받음 
    reGetInfo(changeState){
      this.memberData.locked=changeState // 사이드메뉴에서 변한 상태값 세팅 (리랜더링)
    },
    //세팅 컴포넌트 클릭시 공개/비공개 여부 가져오는 메소드 ==> 여부에따라 세팅 내 비공개 전환 / 공개전환 여부가 달라지므로 
    getInitInfo(){ // 회원정보를 가져옴 ( 공개/비공개 여부) 

        //상태정보 가져오는 api 호출 
        api.get('/setting')
        .then((res)=>{
          //성공시 
          this.memberData=res.data.reqData//공개여부 정보 저장 
          console.log(this.memberData)
          
        })
        .catch((err)=>{
        
          alert(err.response&&err.response.data.message)//에러 출력 
        })
      
       
      },
    //모달 열고,닫음 이벤트 제어 메소드
    ifSettingClose(){
      this.settingDialog=false 
    },
    settingDialogOpen(){
     this.getInitInfo()
      this.settingDialog=true
    },
    postInsertDialogOpen(){
      this.postInsertDialog=true
    },
    postInsertDialogClose(){
      this.postInsertDialog=false
    },
    //로그아웃 진행 
    logout(){
      //로그아웃 api 호출 
      api.post('/logout')
      .then(()=>{
        //성공시 

            
        localStorage.removeItem('access');
        this.$router.push('/login');

          
        })
        .catch((err)=>{
          //실패시 에러메시지 출력 
          alert(err.response&&err.response.data.message)
          
          localStorage.removeItem('access')//엑세스 토큰 지움 
          this.$router.push('/login');//에러 발생시 로그인 페이지로이동
        })
         
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