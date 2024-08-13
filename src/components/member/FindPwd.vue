<template>
    <v-dialog v-model="localDialog" max-width="600">

        <v-card 
        prepend-icon="mdi-account"
        title="비밀번호 초기화"
        class="pa-3"
      >
      <v-divider></v-divider>
    <v-card-text class="mt-6">

      <v-row>
            
        <!--이메일 입력-->
                  <v-col cols="8">
                    <div class="text-subtitle-1 text-medium-emphasis">Email</div>
              <v-text-field 
                density="compact"
                placeholder="Email"
                prepend-inner-icon="mdi-email-outline"
                variant="outlined"
                v-model="email"
                :rules="emailRules"
                
              ></v-text-field>
                </v-col>

      <!--인증코드 보내기 버튼-->
              <v-col cols="4" class="mt-7 ml-0">
                      <v-btn 
                  :key="`subscribe-${isSubscriber}`"
                  :border="`thin ${isSubscriber ? 'error' : 'success'}`"
                  :color="isSubscriber ? 'error' : 'success'"
                  :prepend-icon="isSubscriber ? 'mdi-close' : 'mdi-email'"
                  :slim="isSubscriber"
                  :text="isSubscriber ? 'Cancel' : '인증코드 보내기'"
                  :variant="isSubscriber ? 'plain' : 'tonal'"
                  class="me-2 text-none"
                  
                  flat
                  @click="isSubscriber = !isSubscriber"
                ></v-btn>
           </v-col>


      </v-row>

          <div class="text-subtitle-1 text-medium-emphasis">인증코드</div>

          <!--인증코드 입력 및 남은시간-->
    <v-row >
        <v-col cols="5">
          <v-text-field 
            density="compact"
            placeholder="인증코드"
        
            variant="outlined"
            v-model="email"
            :rules="emailRules"
            
          ></v-text-field>
      </v-col>
   
        <v-col cols="3" class="mt-4"><small class="text-caption text-medium-emphasis">남은시간 59</small></v-col>
      
    
       </v-row>

         
        </v-card-text>

    
        <v-card-actions>
          <v-spacer></v-spacer>
          

          <v-btn
            text="Close"
            variant="plain"
            @click=closeDialog()
          ></v-btn>

          <v-btn
            color="primary"
            text="Submit"
            variant="tonal"
            @click=closeDialog()
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  export default {
    name: 'FindPwdComponent',
  

    props: {
      value: {//로그인 컴포넌트로부터 받은 모달 불리안값
        type: Boolean,
        required: true
      }
    
}
 ,computed:{
    localDialog:{//props로 받은 모달 불리안값을 함수를 통해 리턴 후 바인딩
        get(){
            return this.value
        }
    }
 }   
    ,
    methods: {
      closeDialog() {
        this.$emit('findPwdClose'); // 로그인 컴포넌트로 모달을 닫는 이벤트 전송
      }
    }
  }
  </script>