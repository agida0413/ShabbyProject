<template>
    <div class="pa-4 text-center ">
      <v-dialog
        v-model="localDialog"
        max-width="600"
      >
        
        <v-card
          prepend-icon="mdi-account"
          title="비밀번호 변경"
          class="to-blackMode"
        >
          <v-card-text>
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
       <span style="color: aliceblue;"> 현재 비밀번호</span>
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
   
    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
      <span style="color: aliceblue;">새 비밀번호</span>
        </div>
        <!--비밀번호 확인-->
        <v-row>
            <v-col cols="12">
              <v-text-field
                :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
                :type="visible ? 'text' : 'password'"
                density="compact"
                placeholder="새 비밀번호"
                prepend-inner-icon="mdi-lock-outline"
                variant="outlined"
                @click:append-inner="visible = !visible"
                v-model="newPassword"
                :rules="newPasswordRules"
                  required
                :error-messages="newPaasswordErrors"
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
              @click="submitPasswordChange()"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </template>

  <script>
  import api from '@/api';
export default {
    name: 'ChangerPwd',
  

    props: {
      value: {//세팅 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
        password:'',
        newPassword:'',
        passwordRules: [
                v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가

              ],
        
        newPasswordRules: [
                v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
                v => (v && v.length >= 8) || '비밀번호는 최소 8자 이상이어야 합니다.', // 최소 8자
                v => (v && v.length <= 20) || '비밀번호는 최대 20자까지 입력할 수 있습니다.', // 최대 20자
                v => /[a-zA-Z]/.test(v) || '비밀번호에는 최소 하나의 문자(알파벳)가 포함되어야 합니다.', // 문자 포함
                v => /\d/.test(v) || '비밀번호에는 최소 하나의 숫자가 포함되어야 합니다.', // 숫자 포함
                v => /[!@#$%^&*(),.?":{}|<>]/.test(v) || '비밀번호에는 최소 하나의 특수문자가 포함되어야 합니다.', // 특수문자 포함
              ],
        newPaasswordErrors:[],
        passwordErrors:[],
        visible:false
    }
}
 ,computed:{
    localDialog:{
        get(){
            return this.value // 현재 비밀번호 변경 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
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
     

        this.$emit('changePwdClose');// 비밀번호 변경 컴포넌트로 닫는 이벤트 전송
      },
      submitPasswordChange(){

        this.passwordErrors = this.validateField(this.password, this.passwordRules); //비밀번호 검증 에러메시지 배열
        this.newPaasswordErrors = this.validateField(this.newPassword, this.newPasswordRules); //비밀번호 검증 에러메시지 배열
          if (this.passwordErrors.length > 0 ||this.newPaasswordErrors.length>0) { //만약 비밀번호 검증에러가 있을 시   return
          
            return;//불필요한 axios 요청 방지 
         
                }
                  const formdata= new FormData();
                formdata.append('password',this.password)
                formdata.append('newPassword',this.newPassword)

        api.put("/setting/pwdChange",formdata) // 두개의 서로다른 객체에 매핑 되어야 하므로 formdata  @requestbody로받을 수 없음 
        .then(()=>{
          alert("비밀번호 변경에 성공하였습니다. 다시 로그인 해주세요.")
          this.closeDialog()

                    api.post('/logout')//로그아웃
                      .then(()=>{
                          localStorage.removeItem('access')//엑세스 토큰 지움 
                          this.$router.push('/login');//로그인 페이지로 이동
                        })
                        .catch((err)=>{
       
                          alert(err.response&&err.response.data.message)
                        
                      })
                       
              })
              .catch((err)=>{
       
                  alert(err.response&&err.response.data.message)
                
              })
     

        //이전비밀번호 검증 후 맞다면 

       
      }

    }
  }
  </script>