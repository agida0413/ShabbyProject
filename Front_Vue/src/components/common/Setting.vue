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
              @click="openEmailSend"
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
              subtitle="transfer "
              title="비공개모드로 전환"
              link
            ></v-list-item>
  
            <v-list-item
              subtitle="change password"
              title="회원탈퇴"
              class="red-text"
              link
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
    <EmailSend v-model:value="emailSendToChangePwd" @emailSendClose="closeEmailSend"></EmailSend>
    <EmailVerify v-model:value="emailSendVerify" @emailVerifyClose="closeEmailVerify"></EmailVerify>
    <ChangePwd v-model:value="changePwdDialog" @changePwdClose="closeChangePwd"></ChangePwd>
    <ChangePhone v-model:value="phoneChangeDialog" @changePhone="closePhoneChange"></ChangePhone>
    <ChangeNickname v-model:value="nicknameChangeDialog" @changeNickname="closeChangeNickname"></ChangeNickname>
  </template>

<script>
import EmailSend from "./email/EmailSend.vue"
import EmailVerify from "./email/EmailVerify.vue"
import ChangePwd from "./setting/ChangePwd.vue"
import ChangePhone from "./setting/ChangePhone.vue"
import ChangeNickname from "./setting/ChangeNickName.vue"
export default {
    name:'SettingComponent',
    props: {
       value: {//사이드메뉴로부터  받은 모달 불리안값 
         type: Boolean,
         required: true
       }},
  data () {
    return {
     
      notifications: false, 
      sound: true,
      widgets: false,
      emailSendVerify:false, //이메일 전송 물음 모달
      changePwdDialog:false, //비밀번호 변경 모달
      emailSendToChangePwd:false, // 비밀번호 변경시 이메일 전송 물음 다이얼로그 값
      phoneChangeDialog:false, // 핸드폰 번호 변경 모달
      nicknameChangeDialog:false //닉네임 변경모달
    }
  },computed:{
     localDialog:{
         get(){
             return this.value // 현재 사이드 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
         }
     }
  }    ,
     methods: {
       closeDialog() {
         this.$emit('settingClose');// 로그인 컴포넌트로 닫는 이벤트 전송
       },
       openEmailSend(){//이메일 전송 물음 컴포넌트 오픈
        this.emailSendToChangePwd=true;
       },
       closeEmailSend(isOpenEmailVerify){ //이메일 전송 물음 컴포넌트 닫고 인증번호 입력 컴포넌트 호출
        this.emailSendVerify=isOpenEmailVerify
        this.emailSendToChangePwd=false;
        console.log(this.emailSendVerify)
       },
       openEmailVerify(){//이메일 전송 물음 컴포넌트 오픈
        this.emailSendVerify=true;
       },
       closeEmailVerify(isOpenChangePwdDialog){ //이메일 전송 물음 컴포넌트 닫고 인증번호 입력 컴포넌트 호출
        this.changePwdDialog=isOpenChangePwdDialog
        this.emailSendVerify=false;
        console.log(this.changePwdDialog)
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
       }
     },
     components:{
      EmailSend ,// 이메일 전송물음 컴포넌트,
      EmailVerify ,//인증번호 확인 컴포넌트
      ChangePwd ,//비밀번호 변경 모달
      ChangePhone, //핸드폰 번호 변경
      ChangeNickname // 닉네임 변경
     }
  

}
</script>
<style>
.red-text{
  color:#ff6f6f ;
}
</style>