<template>
  <v-dialog v-model="localDialog">
    <v-card
      class="py-8 px-6 text-center mx-auto ma-4 "
      elevation="12"
      max-width="400"
      width="100%"
    >
      <h3 class="text-h6 mb-4">Verify Your Account</h3>
  
      <div class="text-body-2 ">
        We sent a verification code to john..@gmail.com <br>
  
        Please check your email and paste the code below.
      </div>
  
      <v-sheet color="surface" >
        <v-otp-input
          v-model="otp"
          type="password"
          variant="solo"
          
        ></v-otp-input>
      </v-sheet>
  
      <v-btn
        class="my-4"
        color="purple"
        height="40"
        text="Verify"
        variant="flat"
        width="100%"
        @click="isVerify"
      ></v-btn>
  
      <div class="text-caption">
        Didn't receive the code? <a href="#" @click.prevent="otp = ''">Resend</a>
      </div>
    </v-card>

  </v-dialog>
  </template>
  <script>
  export default {
    name: 'EmailVerify',
  

    props: {
      value: {//세팅 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
      isOk:false
    }
}
 ,computed:{
    localDialog:{
        get(){
            return this.value // 현재  컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
        }
    }
 }   
    ,
    methods: {
      closeDialog() { //이메일 인증 종료
        this.$emit('emailVerifyClose',this.isOk)// 이메일 인증 컴포넌트로 닫는 이벤트 전송 , 이메일전송 응답 여부도 같이 전송
      },
      OK(){
        this.isOk=true; //ok일시 비밀번호 변경 컴포넌트(검증)
        this.closeDialog()
      },
      NO(){
        this.isOk=false;
        this.closeDialog() // 
      },
      isVerify(){
        //인증번호가 맞는지 검증후 ok or no 
        //this.NO()

        this.OK()
      }
    }
  }
  </script>