<template>
    <div class="text-center ">
      <v-dialog
        v-model="localDialog"
        max-width="600"
      >
       <v-progress-linear
          color="cyan"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>
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
                :disabled="isLoading"
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
                :disabled="isLoading"
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
                  :disabled="isLoading"
                ></v-btn>
      
                <v-btn
                  color="primary"
                  text="Save"
                  variant="tonal"
                  @click="submitPasswordChange()"
                  :disabled="isLoading"
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
    
      },
      data(){
        return{
        password:'',//비밀번호
        newPassword:'',//새로운 비밀번호 
        passwordRules: [//비밀번호 규칙 => 검증 용도이므로 필수 validation만 
                v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가

              ],
        
        newPasswordRules: [//새 비밀번호 규칙 
                v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
                v => (v && v.length >= 8) || '비밀번호는 최소 8자 이상이어야 합니다.', // 최소 8자
                v => (v && v.length <= 20) || '비밀번호는 최대 20자까지 입력할 수 있습니다.', // 최대 20자
                v => /[a-zA-Z]/.test(v) || '비밀번호에는 최소 하나의 문자(알파벳)가 포함되어야 합니다.', // 문자 포함
                v => /\d/.test(v) || '비밀번호에는 최소 하나의 숫자가 포함되어야 합니다.', // 숫자 포함
                v => /[!@#$%^&*(),.?":{}|<>]/.test(v) || '비밀번호에는 최소 하나의 특수문자가 포함되어야 합니다.', // 특수문자 포함
              ],
        newPaasswordErrors:[],//새 비밀번호 에러배열 
        passwordErrors:[],//현재 비밀번호 에러 배열 
        visible:false,
        isLoading:false//현재 로딩 상태값 
     }
      } ,
     computed:{
      // 현재 비밀번호 변경 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
          localDialog:{
              get(){
                  return this.value 
              }
          }
     },  
  
    methods: {
      //rules 에러를 저장 
      validateField(value, rules) { 
          return rules
            .map(rule => rule(value))
            .filter(error => typeof error === 'string');
        },
        //모달을 닫는 메소드 
      closeDialog() {
        this.password='',//비밀번호 
        this.newPassword='',//새 비밀번호 
        this.isLoading=false,//로딩 상태값 
        this.passwordErrors=[],//비밀번호 에러 배열 
        this.newPaasswordErrors=[]//새 비밀번호 에러 배열 


        this.$emit('changePwdClose');// 비밀번호 변경 컴포넌트로 닫는 이벤트 전송
      },
      //비밀번호 변경 메소드 
      submitPasswordChange(){

        //만약 현재 로딩상태가 true이면 return 
        if(this.isLoading===true){
            return
          }

        this.passwordErrors = this.validateField(this.password, this.passwordRules); //비밀번호 검증 에러메시지 배열
        this.newPaasswordErrors = this.validateField(this.newPassword, this.newPasswordRules); //새 비밀번호 검증 에러메시지 배열

          //만약 비밀번호 검증에러가 있을 시   return
          if (this.passwordErrors.length > 0 ||this.newPaasswordErrors.length>0) { 
          
            return;//불필요한 axios 요청 방지 
         
          }
                  //form-data 형식  데이터 append
                  const formdata= new FormData();
                  formdata.append('password',this.password)
                  formdata.append('newPassword',this.newPassword)

                  this.isLoading=true;//전송중인 상태 버튼 비활성화 

                        //비밀번호 변경 요청 api
                        api.put("/setting/pwdChange",formdata) // 두개의 서로다른 객체에 매핑 되어야 하므로 formdata  @requestbody로받을 수 없음 
                        .then(()=>{
                        //성공시 서버로 부터 받은 메시지 출력 
                        alert("비밀번호 변경에 성공하였습니다. 다시 로그인 해주세요.")
                        this.closeDialog()//모달을 닫음 

                                  //비밀번호 변경 성공시 then 절에서 로그아웃 요청api
                                  api.post('/logout')
                                  .then(()=>{

                                      localStorage.removeItem('access')//엑세스 토큰 지움 
                                      this.$router.push('/login');//로그인 페이지로 이동
                                    })
                                  .catch((err)=>{
                                      //에러 발생시 서버로 부터 받은 에러 출력 
                                      if(err?.response?.data?.message){
                                          alert(err?.response?.data?.message)  
                                      }
                                    
                                  })
                                  .finally(()=>{
                                    this.isLoading=false;//현재 로딩상태 false 
                                  })
                       
                          })
                          .catch((err)=>{
                            //비밀번호 변경 실패시 서버로부터 받은 에러 메시지 출력 
                            if(err?.response?.data?.message){
                                alert(err?.response?.data?.message)  
                            }
                            
                          })
                          .finally(()=>{
                            this.isLoading=false;//로딩 상태 변경
                          })
      
      }

    }
  }
  </script>