<template>
    
  
      <v-dialog
        v-model="localDialog"
        width="auto"
      >
        <v-card
          max-width="400"
         class="to-blackMode"
          text="회원가입 시 등록된 이메일로 인증번호가 전송됩니다.
           전송하시겠습니까?"
          
        >
        
          <template v-slot:actions>
            <v-btn
          color="warning"
          text="NO"
          @click="NO()"
        ></v-btn>
            <v-btn
            color="primary"
              text="Ok"
              @click="OK()"
            ></v-btn>
          
          </template>
        </v-card>
      </v-dialog>
   
  </template>
  <script>
  export default {
    name: 'EmailSend',
  

    props: {
      value: {//세팅 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
       isOk:false // 이메일 전송 ok 눌렀을땐 true
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
      closeDialog() { //이메일 전송 시작
        this.$emit('emailSendClose',this.isOk)// 로그인 컴포넌트로 닫는 이벤트 전송 , 이메일전송 응답 여부도 같이 전송
      },
      OK(){
        this.isOk=true; //ok일시 이메일 인증모달도 띄어야하니..
        this.closeDialog()
      },
      NO(){
        this.isOk=false;
        this.closeDialog() // 
      }
    }
  }
  </script>

 
