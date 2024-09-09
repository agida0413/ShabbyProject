<template>
    <div class="text-center ">
      <v-dialog
        v-model="localDialog"
        transition="dialog-bottom-transition"
        fullscreen   
      > 

        <v-card  class="to-blackMode">

          <!--closeDialog = > 부모에게 이벤트 전송-->
          <v-toolbar  style="background-color: gray; color: aliceblue;">

              <v-btn
                icon="mdi-close"
                @click="closeDialog()"
              ></v-btn>

              <v-toolbar-title >Settings</v-toolbar-title>
         
          </v-toolbar>
  
          <v-list
            lines="two"
            subheader
             class="to-blackMode"
          >
            <v-list-subheader  class="to-blackMode" style="opacity: 0.7;">정보변경</v-list-subheader>
         
            <!-- 각 버튼 클릭시 해당되는 메소드실행 후 변수값 true로 변경-->
                  <v-list-item
                    subtitle="change nickname"
                    title="닉네임변경"
                    link
                    @click="openChangeNickname"
                  ></v-list-item>
        
                  <v-list-item
                    subtitle="change password"
                    title="비밀번호 변경"
                    link
                    @click="openChangePwd"
                  ></v-list-item>
        
                  <v-list-item
                    subtitle="change phone number"
                    title="휴대폰 번호 변경"
                    link
                    @click="openPhoneChange"
                  ></v-list-item>
                  <v-divider></v-divider>
                  <v-list-subheader  class="to-blackMode" style="opacity: 0.7;">계정관리</v-list-subheader>
                
                  <v-list-item
                  v-if="memberData.locked==='PUBLICID'"
                    :key="updateKey"
                    subtitle="transfer "
                    title="비공개모드로 전환"
                    link
                    @click="openAlert(
                      '비공개모드로 전환시 회원님의 팔로워 외에는 회원님을 찾을 수 없습니다. 그래도 진행하시겠습니까?',
                    'ChangeLokedState')"
                  ></v-list-item>

                  <v-list-item
                  v-if="memberData.locked==='LOCKED'"
                    subtitle="transfer "
                    title="공개모드로 전환"
                    link
                    @click="openAlert(
                      '계정을 공개 계정으로 전환 하시겠습니까',
                      'ChangeLokedState'
                    )"
                  ></v-list-item>
        
                  <v-list-item
                    subtitle="change password"
                    title="회원탈퇴"
                    class="red-text"
                    link
                    @click="openAlert(
                      '회원 탈퇴 시 작성한 게시글,댓글 등 모든 정보가 삭제됩니다. 진행하시겠습니까?',
                      'memberDelete'
                      )"
                  ></v-list-item>

            <v-divider></v-divider>


      
          </v-list>
        </v-card>
      </v-dialog>
    </div>
    <!--경고창 모달 컴포넌트 -->
    <Alert
      v-model:value="isAlertOpen"
      v-model:altype="alertType"
      v-model:message="alertMessage"
      @closeAlertDialog="closeAlert"
    >
  </Alert>
    <!--회원 탈퇴전 검증하는 컴포넌트-->
    <DeleteVerify v-model:value="deleteVerifyDialog" @DeleteVerifyClose="closeDeleteVerifyDialog"></DeleteVerify>
    <!--패스워드 변경 컴포넌트 -->
    <ChangePwd v-model:value="changePwdDialog" @changePwdClose="closeChangePwd"></ChangePwd>
    <!--핸드폰번호 변경 컴포넌트-->
    <ChangePhone v-model:value="phoneChangeDialog" @changePhone="closePhoneChange"></ChangePhone>
    <!--닉네임 중복검증 컴포넌트-->
    <ChangeNickname v-model:value="nicknameChangeDialog" 
    @changeNickname="closeChangeNickname"></ChangeNickname>
  </template>

<script>


import ChangePwd from "./ChangePwd.vue"
import ChangePhone from "./ChangePhone.vue"
import ChangeNickname from "./ChangeNickName.vue"
import DeleteVerify from "./DeleteVerify.vue"
import Alert from "../utill/Alert.vue"
import api from "@/api"
export default {
    name:'SettingComponent',
    props: {
       value: {//사이드메뉴로부터  받은 모달 불리안값 
         type: Boolean,
         required: true
       },
       memberData :{//회원데이터
        type:{},
        required:true
       }
      
      },
  data () {
    return {
     
      notifications: false, 
      sound: true,
      widgets: false,
      changePwdDialog:false, //비밀번호 변경 모달
      phoneChangeDialog:false, // 핸드폰 번호 변경 모달
      nicknameChangeDialog:false, //닉네임 변경모달
      deleteVerifyDialog:false,//회원탈퇴전 검증 모달
      alertType:'', // 알림창을 띄울때 어떤 타입인지(비공개 모드 전환? 회원탈퇴?)
      isAlertOpen:false,//알림창모달의 띄움 여부 
      alertMessage:'' ,//알림창모달에 전달할 메시지 
    
   
    }
  },
  computed:{
    // 현재 사이드 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
     localDialog:{
         get(){
       
             return this.value 
         }
     },
     currentLockState:{
      get(){ //부모로 부터 받은 공개/비공개 여부 값 
        return this.memberData.locked
      }
      
     }
  },
     methods: {
       // 부모 컴포넌트로 현재 세팅컴포넌트 닫는 이벤트 전송
       closeDialog() {
         this.$emit('settingClose');
       },
       // 비밀번호 변경 모달 컴포넌트를 여는 메소드 
       openChangePwd(){
        this.changePwdDialog=true 
       },
       // 비밀번호 변경 모달 컴포넌트를 닫는 메소드 
       closeChangePwd(){
        this.changePwdDialog=false 
       },
       //핸드폰 번호 변경모달 열림
       openPhoneChange(){
        this.phoneChangeDialog=true 
       },
       //핸든폰 번호 변경모달 닫힘
       closePhoneChange(){
        this.phoneChangeDialog=false
       },
       // 닉네임 변경 모달열림
       openChangeNickname(){ 
          this.nicknameChangeDialog=true
       },
       //닉네임 변경 모달 닫힘
       closeChangeNickname(){ 
          this.nicknameChangeDialog=false
       },
       //각 기능별 경고창이 필요한 경우 클릭시 경고창 모달 컴포넌트를 띄우는 메소드 
       //경고창에 띄울 메시지와 , 띄울 타입(비공개 여부변경, 회원탈퇴 등 ) 매개변수로 받음 
       openAlert(message,type){

        this.alertMessage=message //매개변수 메시지 값을 담음 ,자식컴포넌트에 전달
        this.alertType=type//매개변수 타입 값을 담음, 자식컴포넌트에 전달

        this.isAlertOpen=true //경고창 띄우는 변수 
       },
       //경고창을 닫는 메서드 (경고창에는 no와 ok 두가지 버튼이 있다 . )
       closeAlert(isOk,type){//ok or no 변수 , 띄운 경고창의 타입 (비공개여부변경, 회원탈퇴 등)
        
        //ok버튼을 클릭하였고 , 비공개 /공개 여부 변경 경고창 이면 (type에 따라)       
        if (isOk===true&&type==='ChangeLokedState') {
          const curstate= this.currentLockState //부모로 부터 받은 공개/비공개 여부 값 을 curstate에 담음
          //공개/비공개여부 상태를 변경하는 api 호출 
          api.put('/setting/lockStateChange',{
            locked:curstate //현재 비공개/공개여부 바디에담음
          })
          .then((res)=>{
            //성공시 전환 알람 
            alert('전환되었습니다.')
            const locked=res?.data?.reqData?.locked
            this.$emit("updateSideMenuInfo",locked)// locked 정보다시가져와서 리랜더링 해야하므로 부모컴포넌트로 이벤트 전송
              //res.data.reqData.locked  는 전환 성공후 바뀐 상태값 = > 부모로 전달 후 리랜더링
          })
          .catch((err)=>{
            //에러시 서버에서 내린 data에 있는 message 동적으로 출력 
            alert(err?.response?.data?.message);
          })
        }

        //ok를 클릭하였고 , 회원탈퇴 경고 이면 
        if(isOk&&type==="memberDelete"){
          this.deleteVerifyDialog=true//회원검증 모달 컴포넌트를 염 
          this.isAlertOpen=false//경고창을 닫음 

        }

        this.isAlertOpen=false//그외 no 버튼을 클릭 시 경고창을 닫음 
       },
       //회원검증 모달 컴포넌트를 닫는 메ㅔ서드 
       closeDeleteVerifyDialog(){
        this.deleteVerifyDialog=false//회원탈퇴시 검증다이얼로그 닫음
       }

     },
     components:{
      ChangePwd ,//비밀번호 변경 모달
      ChangePhone, //핸드폰 번호 변경
      ChangeNickname, // 닉네임 변경
      Alert ,//알림,
      DeleteVerify//회원탙퇴전 회원검증 
     }
  

}
</script>
<style>
.red-text{
  color:#ff6f6f ;
}
</style>