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
          <span style="color: aliceblue;">새로운 전화번호</span>
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
            v-model="middlePhoneNum"
            maxlength="4" 
            variant="outlined"
            
          
    
          ></v-text-field>

    </v-col>

    <v-col cols="3">
        <v-text-field
         
          type="text"
          density="compact"
          maxlength="4" 
          variant="outlined"
          v-model="lastPhoneNum"
         
   
        ></v-text-field>

    </v-col>

    </v-row>

            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
              <span style="color: aliceblue;">비밀번호</span>
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
                required
                :error-messages="passwordErrors"
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
              @click="closeDialog()"
            ></v-btn>
  
            <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="submitChangePhone()"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </template>

  <script>
import api from '@/api';

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
                v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
        
        ],
        passwordErrors:[],
        visible:false,
        items:['010','011','016','017','019'], //대한민국 앞자리번호 리스트
        firstPhoneNum:'010', //디폴트값 - > 맨앞 번호
        middlePhoneNum:'',//두번째 번호
        lastPhoneNum:'', //세번째 번호 
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
      validateField(value, rules) { //rules 에러를 저장 
          return rules
            .map(rule => rule(value))
            .filter(error => typeof error === 'string');
        },
      closeDialog() {
        this.$emit('changePhone');// 세팅 컴포넌트로 닫는 이벤트 전송
      },
      submitChangePhone(){
        this.passwordErrors = this.validateField(this.password, this.passwordRules); //비밀번호 검증 에러메시지 배열
          
          if (this.passwordErrors.length > 0 ) { //만약 비밀번호 검증에러가 있을 시   return
          
            return;//불필요한 axios 요청 방지 
 
                }

                const fullPhone=(this.firstPhoneNum+this.middlePhoneNum+this.lastPhoneNum); 
               
               if (fullPhone.length !== 11) {
                  alert('핸드폰 번호는 11자리여야 합니다.');
                  return;
                }

                // 한글 포함 여부 검증
                if (/[가-힣]/.test(fullPhone)) {
                  alert('핸드폰 번호에 한글이 포함될 수 없습니다.');
                  return;
                }

                // 공백 포함 여부 검증
                if (/\s/.test(fullPhone)) {
                  alert('핸드폰 번호에 공백이 포함될 수 없습니다.');
                  return;
                }

                // 숫자 이외의 문자 포함 여부 검증
                if (!/^\d+$/.test(fullPhone)) {
                  alert('핸드폰 번호는 숫자만 포함해야 합니다.');
                  return;
                }


             api.put('/setting/phoneChange',{
                phone:fullPhone,
                password:this.password
             })
             .then(()=>{
                alert('휴대폰 번호 변경에 성공 하였습니다.')
                this.closeDialog()
            })
            .catch((err)=>{
       
              alert(err.response&&err.response.data.message)
              
            })
            
      }

    }
  }
  </script>