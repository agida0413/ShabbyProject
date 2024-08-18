<template>
    
  
      <v-dialog
        v-model="localDialog"
        width="auto"
      >
        <v-card
          max-width="400"
         class="to-blackMode"
        :text="message"
          
        >
 
          <template v-slot:actions>
            <v-btn
          color="warning"
          text="NO"
          @click="handleNo()"
        ></v-btn>
            <v-btn
            color="primary"
              text="Ok"
              @click="handleOk()"
            ></v-btn>
          
          </template>
        </v-card>
      </v-dialog>
   
  </template>
  <script>
  export default {
    name: 'Alert',
  

    props: {
      value: {// 부모컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      },
      message:{ //표시할 메시지를 부모로 부터받음 
        type: String,
        required: true
      },
      altype:{  // 어떤 유형의 요청인지 
        type: String,
        required: true
      }
    
},data(){
    return{
       isOk:false // ok 눌렀을땐 true
    }
}
 ,computed:{
    localDialog:{
        get(){
            return this.value // 현재  컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
        }
    },
    getType:{
      get(){
            return this.altype // 현재  어떤타입의 alert 인지
      }
    } ,
    getMessage:{
      get(){
        return this.message
      }
    }
   
  }

  ,
    methods: {
      closeDialog() { 
       const type=this.getType
        this.$emit("closeAlertDialog",this.isOk,type)// 컴포넌트 닫는 이벤트 전송 , 이벤트명을 동적으로 ,  응답 여부도 같이 전송
      },
      handleOk() { //ok일시 
      this.isOk = true;
      this.closeDialog();
    },
    handleNo() {//no일시 
      this.isOk = false;
      this.closeDialog();
    }
    }
}
  </script>

 
