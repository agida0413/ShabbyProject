<template>
    <div class="text-center pa-4 ">
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
            <v-list-subheader  class="to-blackMode">정보변경</v-list-subheader>
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
            <v-list-subheader  class="to-blackMode">계정관리</v-list-subheader>
            <v-list-item
            v-if="memberData.locked==='PUBLICID'"
              subtitle="transfer "
              title="비공개모드로 전환"
              link
              @click="clickPublicMode"
            ></v-list-item>

            <v-list-item
            v-if="memberData.locked==='LOCKED'"
              subtitle="transfer "
              title="공개모드로 전환"
              link
              @click="clickPrivateMode()"
            ></v-list-item>
  
            <v-list-item
              subtitle="change password"
              title="회원탈퇴"
              class="red-text"
              link
              @click="memberDelete()"
            ></v-list-item>
            <v-divider></v-divider>


          <!--기타 설정-->
            <v-list-subheader  class="to-blackMode">임시</v-list-subheader>
  
            <v-list-item
              subtitle="Notify me about updates to apps or games that I downloaded"
              title=""
              @click="notifications = !notifications"
            >
              <template v-slot:prepend>
                <v-list-item-action start>
                  <v-checkbox-btn v-model="notifications" color="primary"></v-checkbox-btn>
                </v-list-item-action>
              </template>
            </v-list-item>
  
            <v-list-item
              subtitle="Auto-update apps at any time. Data charges may apply"
              title="Sound"
              @click="sound = !sound"
            >
              <template v-slot:prepend>
                <v-list-item-action start>
                  <v-checkbox-btn v-model="sound" color="primary"></v-checkbox-btn>
                </v-list-item-action>
              </template>
            </v-list-item>
  
            <v-list-item
              subtitle="Automatically add home screen widgets"
              title="Auto-add widgets"
              @click="widgets = !widgets"
            >
              <template v-slot:prepend>
                <v-list-item-action start>
                  <v-checkbox-btn v-model="widgets" color="primary"></v-checkbox-btn>
                </v-list-item-action>
              </template>
            </v-list-item>
          </v-list>
        </v-card>
      </v-dialog>
    </div>
    <Alert
      v-model:value="isAlertOpen"
      v-model:altype="alertType"
      v-model:message="alertMessage"
      @closeAlertDialog="closeAlert(isOk,type)"
    >
  </Alert>
    <ChangePwd v-model:value="changePwdDialog" @changePwdClose="closeChangePwd"></ChangePwd>
    <ChangePhone v-model:value="phoneChangeDialog" @changePhone="closePhoneChange"></ChangePhone>
    <ChangeNickname v-model:value="nicknameChangeDialog" @changeNickname="closeChangeNickname"></ChangeNickname>
  </template>

<script>


import ChangePwd from "./setting/ChangePwd.vue"
import ChangePhone from "./setting/ChangePhone.vue"
import ChangeNickname from "./setting/ChangeNickName.vue"
import Alert from "./utill/Alert.vue"
import api from "@/api"
export default {
    name:'SettingComponent',
    props: {
       value: {//사이드메뉴로부터  받은 모달 불리안값 
         type: Boolean,
         required: true
       }},
  data () {
    return {
      memberData:{},
      notifications: false, 
      sound: true,
      widgets: false,
      changePwdDialog:false, //비밀번호 변경 모달
      phoneChangeDialog:false, // 핸드폰 번호 변경 모달
      nicknameChangeDialog:false, //닉네임 변경모달
      alertType:'', // 알림창을 띄울때 어떤 타입인지(비공개 모드 전환? 회원탈퇴?)
      isAlertOpen:false,//알림창모달의 띄움 여부 
      alertMessage:'' //알림창모달에 전달할 메시지 
    }
  },
  computed:{
     localDialog:{
         get(){
             return this.value // 현재 사이드 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
         }
     }
  },
  mounted(){
   this.getInitInfo()
   
  },
     methods: {
      getInitInfo(){
        api.get('/setting')
        .then((res)=>{
          this.memberData=res.data
          console.log(this.memberData)
        })
        .catch((err)=>{
          if(err.response.status===401){
            alert('잘못된 접근입니다.')
          }
          else{
            alert('서버 내부오류입니다. 잠시 뒤 이용해주세요.')
          }
        })
      },
       closeDialog() {
         this.$emit('settingClose');// 부모 컴포넌트로 닫는 이벤트 전송
       },
       openChangePwd(){
        this.changePwdDialog=true // 비밀번호 변경 모달
       },
       closeChangePwd(){
        this.changePwdDialog=false // 비밀번호 최종변경 완료
       },
       openPhoneChange(){
        this.phoneChangeDialog=true //핸든폰 번호 변경모달 열림
       },
       closePhoneChange(){
        this.phoneChangeDialog=false//핸든폰 번호 변경모달 닫힘
       },
       openChangeNickname(){ // 닉네임 변경 모달열림
          this.nicknameChangeDialog=true
       },
       closeChangeNickname(){ //닉네임 변경 모달 닫힘
          this.nicknameChangeDialog=false
       },
       openAlert(){
        this.isAlertOpen=true
       },
       closeAlert(isOk,type){
        console.log(isOk)
        console.log(type)
        this.isAlertOpen=false
       },
       clickPrivateMode(){
        this.alertMessage="비공개모드로 전환시 회원님의 팔로워 외에는 회원님을 찾을 수 없습니다. 그래도 진행하시겠습니까?"
        this.type="toPrivateMode"
        this.openAlert()
       },
       clickPublicMode(){
        this.alertMessage="계정을 공개 계정으로 전환 하시겠습니까?"
        this.type="toPublicMode"
        this.openAlert()
       },
       memberDelete(){
        this.alertMessage="회원 탈퇴 시 작성한 게시글,댓글 등 모든 정보가 삭제됩니다. 진행하시겠습니까?"
        this.type="memberDelete"
        this.openAlert()
      }
     },
     components:{
      ChangePwd ,//비밀번호 변경 모달
      ChangePhone, //핸드폰 번호 변경
      ChangeNickname, // 닉네임 변경
      Alert //알림 
     }
  

}
</script>
<style>
.red-text{
  color:#ff6f6f ;
}
</style>