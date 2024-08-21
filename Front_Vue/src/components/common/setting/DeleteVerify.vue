<template>
    <div class="pa-4 text-center ">
      <v-dialog
        v-model="localDialog"
        max-width="600"
      >
        
        <v-card
          prepend-icon="mdi-account"
          title="개인정보 확인"
          class="to-blackMode"
        >
          <v-card-text>
            
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between "
            >
            <span style="color: aliceblue;">이름</span>
            </div>
        <!--이름-->
        <v-row>
            <v-col cols="7">
                <v-text-field
                  density="compact"
                  placeholder="이름"
                  prepend-inner-icon="mdi-account"
                  variant="outlined"
               
                  required
                  v-model="name"
                  :rules="nameRules"
                  :error-messages="nameErrors"
                
                ></v-text-field>
           </v-col>
         </v-row>

            <div class="text-subtitle-1 text-medium-emphasis">
                <span style="color: aliceblue;">Email</span>
            </div>
                 <v-row>
                    <v-col>
                        <v-text-field
                        density="compact"
                        placeholder="Email"
                        prepend-inner-icon="mdi-email-outline"
                        variant="outlined"
                        v-model="email"
                        :rules="emailRules"
                        required
                        :error-messages="emailErrors"
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
                placeholder="비밀번호"
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
              @click="memberDelete()"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </template>

  <script>
import api from '@/api';

 
export default {
    name: 'DeleteVerify',
  

    props: {
      value: {//세팅 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
        name:'',//실명 
        email: '', //이메일
        password:'', //패스원드
            emailRules: [
                v => !!v || '이메일을 입력해 주세요.', //이메일이 입력되지 않았을 경우
                v => v.trim().length > 0 || '이메일은 공백일 수 없습니다.', //이메일에 공백이 들어갔을 경우
                
                v => /.+@.+\..+/.test(v) || '이메일 형식이 올바르지 않습니다.' //@와 . 이 들어가지않았을 경우
             ],
            passwordRules: [
                    v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                    v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                    v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가

             ],
              nameRules:[
              v => !!v || '이름을 입력해 주세요.', // 닉네임이 입력되지 않았을 경우
              v => v.trim().length > 0 || '이름은 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
              v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              ],
        passwordErrors:[],//비밀번호 에러모음
        emailErrors: [],//이메일 에러모음 
        nameErrors:[],//이름 에러모음 
        visible:false,
        isLoading:false//로딩 변수
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
        this.password='',
        this.name='',
        this.email='',
        this.passwordErrors=[],
        this.nameErrors=[],
        this.emailErrors=[],
        this.isLoading=false,
        

        this.$emit('DeleteVerifyClose');// 비밀번호 변경 컴포넌트로 닫는 이벤트 전송
      },
      memberDelete(){
        if(this.isLoading===true){
            return
          }

        this.passwordErrors = this.validateField(this.password, this.passwordRules); //비밀번호 검증 에러메시지 배열
        this.nameErrors = this.validateField(this.name, this.nameRules); //이름 검증 에러메시지 배열
        this.emailErrors = this.validateField(this.email, this.emailRules); //이메일 검증 에러메시지 배열

          if (this.passwordErrors.length > 0 ||this.nameErrors.length>0||this.emailErrors.length>0) { //만약 검증에러중 하나라도 있을 시   return
          
            return;//불필요한 axios 요청 방지 
        

                }
           api.delete('/setting',{
            data:{
                name:this.name,
                password:this.password,
                email:this.email
            }
          
           })
           .then(()=>{
                alert('그동안 이용해주셔서 감사합니다. 회원님의 정보는 안전하게 삭제하였습니다.')

                localStorage.removeItem("access")
                this.$router.push('/login');//로그인 페이지로 이동
            })
            .catch((err)=>{
                alert(err.response&&err.response.data.message)
            })


       
      }

    }
  }
  </script>