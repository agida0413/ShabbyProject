<template>
    <div class="pa-4 text-center">
      <v-dialog
        v-model="localDialog"
        max-width="600"
      >
       
  
        <v-card
          prepend-icon="mdi-account"
          title="핸드폰 번호 변경"
          class="to-blackMode"
        >
        <v-divider></v-divider>
          <v-card-text>

            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          <span style="color: aliceblue;">전화번호</span>
        </div>

        <v-row>

            <v-col cols="3">
                <v-select
                  density="compact"
                
                  variant="outlined"
                  v-model="firstPhoneNum"
                :items="items"
              
              
                required
              
                ></v-select>
            </v-col>


    <v-col cols="3">
          <v-text-field
          
            type="text"
            density="compact"
          
        
            variant="outlined"
            
          
    
          ></v-text-field>

    </v-col>

    <v-col cols="3">
        <v-text-field
         
          type="text"
          density="compact"
         
       
          variant="outlined"
          
         
   
        ></v-text-field>

    </v-col>

    </v-row>

            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
              <span style="color: aliceblue;">현재 비밀번호</span>
        </div>
        <!--비밀번호-->
        <v-row>
            <v-col cols="12">
              <v-text-field
                :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
                :type="visible ? 'text' : 'password'"
                density="compact"
                placeholder="현재 비밀번호"
                prepend-inner-icon="mdi-lock-outline"
                variant="outlined"
                @click:append-inner="visible = !visible"
                v-model="password"
                :rules="passwordRules"
              ></v-text-field>
            </v-col>
        </v-row>
   
    
    </v-card-text>
  
          <v-divider></v-divider>
  
          <v-card-actions>
            <v-spacer></v-spacer>
  
            <v-btn
              text="Close"
              variant="plain"
              @click="cancle"
            ></v-btn>
  
            <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="verifyPwd"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </template>

  <script>
export default {
    name: 'ChangerPhone',
  

    props: {
      value: {//세팅 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
        password:'',
        passwordRules: [
          v => !!v || '비밀번호를 입력해주세요.' //비밀번호 입력검증
        
        ],visible:false,
        items:['010','011','016','017','019'], //대한민국 앞자리번호 리스트
        firstPhoneNum:'010' //디폴트값
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
      closeDialog() {
        this.$emit('changePhone');// 세팅 컴포넌트로 닫는 이벤트 전송
      },
      verifyPwd(){
        //비밀번호 검증 후 맞다면 

        this.closeDialog()
      },
      cancle(){
        //취소
        this.closeDialog()
      }

    }
  }
  </script>