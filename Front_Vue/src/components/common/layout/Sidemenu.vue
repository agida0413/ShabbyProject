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
                src="https://ta9termproject.s3.ap-northeast-2.amazonaws.com/logo2.png"
            ></v-img> 
          
          </v-list-item>

          
          </template>

        <v-divider></v-divider>
          <!--사이드 메뉴 항목들-->
        <v-list density="compact" nav        
        >
        
          <v-list-item 
          prepend-icon="mdi-pencil" 
          title="새 게시물" 
          value="1" 
          @click="postInsertDialogOpen"
         
          ></v-list-item>
              <router-link to="/globalfeed" class="router-link"> 
                <v-list-item prepend-icon="mdi-account-group-outline"  value="2" title="글로벌 피드" ></v-list-item></router-link>
              <router-link to="/" class="router-link"> <v-list-item prepend-icon="mdi-home-city"  title="메인 피드" value="3"></v-list-item></router-link>
                <template v-if="memberData.nickname">
                  <router-link :to="{ name: 'userfeed', params: {nickname:this.memberData.nickname }}" class="router-link" value="4"><v-list-item prepend-icon="mdi-home" title="내 피드" value="home"></v-list-item></router-link>
                </template>
               <v-list-item prepend-icon="mdi-heart-outline" @click="AlarmDialogOpen()" title="알람"  value="5" v-if="noreadAlarmCount===0"></v-list-item>
               <v-list-item  @click="AlarmDialogOpen()" value="5" v-if="noreadAlarmCount>0">
              <v-icon color="red">
                mdi-heart
              </v-icon>
                <span style="font-size: 14px; margin-left: 32px; color: #FFCCCC;">알람</span>
                <span style="font-size: 10px; opacity: 0.7; margin-left: 3px ;color: #FFCCCC;" >(새 알람 {{ noreadAlarmCount }}건)</span>

               </v-list-item>
                <v-list-item prepend-icon="mdi-wrench" title="설정" value="6" @click="settingDialogOpen()"></v-list-item>
                <v-list-item prepend-icon="mdi-lock" title="로그아웃" value="7" @click="logout()"></v-list-item>
        </v-list>

      </v-navigation-drawer>
    
     
  </v-card>
  <!-- 세팅 모달 컴포넌트 -->
  <SettingComponent 
  v-model:value="settingDialog"
  v-model:memberData="memberData"
  @updateSideMenuInfo="reGetInfo"
  @settingClose="ifSettingClose">
 </SettingComponent> 
 <AlarmList
 v-model:value="AlarmDialog"
 @closeAlarm="AlarmDialogClose"
 >
 </AlarmList>
<!--새 게시물 작성 모달 컴포넌트-->
  <PostInsert v-model:value="postInsertDialog" @postInsertClose="postInsertDialogClose"></PostInsert>
</template>

<script>
import PostInsert from "@/components/feed/post/PostInsert.vue";
import SettingComponent from "../setting/Setting.vue"
import AlarmList from "../utill/AlarmList.vue";
import api from "@/api"
import eventBus from "@/eventBus"

export default{

  data(){
    return{
      settingDialog:false, //세팅 모달 제어값
      postInsertDialog:false, //새 게시물 작성 제어값 
      memberData:{}, //회원정보
      isLoading:false ,//로딩상태
      AlarmDialog:false,//알람 리스트 모달 열고닫음 
      selectedValue:null,
      noreadAlarmCount:0,

    }
  },
 
  components:{
    SettingComponent,//세팅 컴포넌트
    PostInsert,//게시물 작성 컴포넌트
    AlarmList
  },
 mounted(){
  
  this.getInitInfo()
  eventBus.on('getAlarmData',this.alarmCountGet);
 },
 unmounted(){
  eventBus.off('getAlarmData',this.alarmCountGet);
 },
 //변경추적
    watch:{
      //현재 컴포넌트에서 데이터 교환이 완료됫음을 app.vue에전달(스켈레톤 요소제어)
      isLoading(newLoading){
        if(newLoading){
          this.$emit('childLoading')
        }
      },
      //세팅 컴포넌트내에서 닉네임 변경등 변경되면 변경된값으로 다시 정보를 읽기위함
      settingDialog(){
        this.getInitInfo()
      }
     
    },
  methods:{
   alarmCountGet(){
    if(this.isLoading)return

      this.isLoading=true;
      api.get('/alarm')
      .then((res)=>{
        this.noreadAlarmCount=res?.data?.reqData
        console.log(this.noreadAlarmCount)
      })
      .catch((err)=>{
             if(err?.response?.data?.message){
              alert(err?.response?.data?.message)  
            }
      })
      .finally(()=>{
        this.isLoading=false
      })
   },
    resetValue(){
     
     this.selectedValue=10
    },
    //자식에서 받은 변한 상태값 매개변수로 받음 
    reGetInfo(changeState){
      this.memberData.locked=changeState // 사이드메뉴에서 변한 상태값 세팅 (리랜더링)
    },
    //세팅 컴포넌트 클릭시 공개/비공개 여부 가져오는 메소드 ==> 여부에따라 세팅 내 비공개 전환 / 공개전환 여부가 달라지므로 
    getInitInfo(){ // 회원정보를 가져옴 ( 공개/비공개 여부) 
      
      if(this.isLoading===true){
        return;
      }

      this.isLoading=true
      // 로그인, 회원가입 페이지에서의 api호출을 방지 ( 사이드메뉴이기 떄문에 마운트를 막지않으면 api 호출함 )
      const token= localStorage.getItem('access')
      if(!token){

        return
      }
        //상태정보 가져오는 api 호출 
        api.get('/setting')
        .then((res)=>{
          //성공시 
          this.memberData=res?.data?.reqData//공개여부 정보 저장 
          
          
        })
        .catch((err)=>{
          if(err?.response?.data?.message){
            alert(err?.response?.data?.message)  
            }
          this.$router.push({name:'error'})
        })
        .finally(()=>{
          this.isLoading=false
          this.alarmCountGet()
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
    AlarmDialogOpen(){
      this.AlarmDialog=true
    },
    AlarmDialogClose(){
      this.AlarmDialog=false
    },
    //로그아웃 진행 
    logout(){
      if(this.isLoading){
        return
      }

      this.isLoading=true
      //로그아웃 api 호출 
      api.post('/logout')
      .then(()=>{
        //성공시  로컬스토리지 제거 , 로그인 페이지로 푸시 
        localStorage.removeItem('requestUrl');   
        localStorage.removeItem('access');
        this.$router.push('/login');

          
        })
        .catch((err)=>{
          //실패시 에러메시지 출력 
          if(err?.response?.data?.message){
            alert(err?.response?.data?.message)  
          }
          
          localStorage.removeItem('access')//엑세스 토큰 지움 
          this.$router.push('/login');//에러 발생시 로그인 페이지로이동
        })
        .finally(()=>{
          this.isLoading=false
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