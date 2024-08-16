<template >
    <div >
     

      <v-card
      
        class="mx-auto pa-16 pb-8 card-scroll mt-13" 
        elevation="8"
        max-width="600"
        rounded="lg"
        max-height="1000"
        
      >
   <router-link to="/login">   <v-btn icon="mdi-arrow-left" size="32"  class="btn-back"></v-btn></router-link>
        <!--이메일-->
        
            <div class="text-subtitle-1 text-medium-emphasis">Email</div>
           
           <v-row>

                <v-col cols="7">
            <v-text-field 
             :rules="emailRules"
              density="compact"
              placeholder="Email"
              prepend-inner-icon="mdi-email-outline"
              variant="outlined"
              v-model="email"
              :readonly="isEmailReadonly"
                 required
                 :error-messages="emailErrors"
            ></v-text-field>
              </v-col>

                <v-col cols="5">
                  <v-btn 
                  v-show="!resend"
                      :key="`subscribe-${isSubscriber}`"
                      :border="`thin ${isSubscriber ? 'error' : 'success'}`"
                      :color="isSubscriber ? 'error' : 'success'"
                      :prepend-icon="isSubscriber ? 'mdi-close' : 'mdi-email'"
                      :slim="isSubscriber"
                      :text="isSubscriber ? 'Cancel' : '인증코드 보내기'"
                      :variant="isSubscriber ? 'plain' : 'tonal'"
                      class="me-2 text-none"
                      
                      flat
                     @click="emailAuth()"
                    ></v-btn>

                    <v-btn 
                    v-show="resend"
                      :key="`subscribe-${isSubscriber}`"
                      :border="`thin ${isSubscriber ? 'error' : 'success'}`"
                      :color="isSubscriber ? 'error' : 'success'"
                      :prepend-icon="isSubscriber ? 'mdi-close' : 'mdi-email'"
                      :slim="isSubscriber"
                      :text="isSubscriber ? 'Cancel' : '인증코드 다시 보내기'"
                      :variant="isSubscriber ? 'plain' : 'tonal'"
                      class="me-2 text-none"
                      
                      flat
                     @click="emailAuth()"
                    ></v-btn>
                </v-col>

         </v-row>
  

    <div class="text-subtitle-1 text-medium-emphasis" v-show="showAuthCode">인증코드</div>
    <v-row v-show="showAuthCode">
      <!--인증코드-->
        <v-col cols="4">
          <v-text-field 
            density="compact"
            placeholder="인증코드"
        
            variant="outlined"
            v-model="code"
            :rules="codeRules"
            required
            :error-messages="codeErrors"
            
          ></v-text-field>
       </v-col>

    <v-col cols="5">
                    <v-btn
                      height="40"
                      min-width="50"
                      color="blue"
                      @click="emailValidation()"
                    >
                      검증
                    </v-btn>

     </v-col>
        <v-col cols="3" class="mt-4"><small class="text-caption text-medium-emphasis">남은시간 59</small></v-col>
      
    
       </v-row>

       <div class="text-subtitle-1 text-medium-emphasis d-flex align-center ">
          닉네임 <span class="text-caption ms-2 text-medium-emphasis">
              (사이트 내 다른 회원들의 식별 수단이 됩니다.)
            </span>
        </div>
        
        <v-row>
          <!--닉네임-->
            <v-col cols="7">
                <v-text-field
          
                
                  density="compact"
                  placeholder="닉네임"
                  prepend-inner-icon="mdi-account"
                  variant="outlined"
                  required
                  v-model="nickName"
                  :rules="nickNameRules"
                  :error-messages="nickNameErrors"
                  :readonly="isNickNameReadonly"
                ></v-text-field>
        </v-col>

        <!--중복 검증버튼-->
        <v-col cols="4">
                    <v-btn
                      height="40"
                      min-width="50"
                      color="blue"
                      @click="nickNameValidation()"
                    >
                      중복확인
                    </v-btn>
                </v-col>

    </v-row>  

       <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          이름
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

   
       
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          비밀번호
        </div>
        <!--비밀번호-->
        <v-row>
            <v-col cols="7">
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
   
    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
       비밀번호 확인
        </div>
        <!--비밀번호 확인-->
        <v-row>
            <v-col cols="7">
              <v-text-field
              :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
              :type="visible ? 'text' : 'password'"
              density="compact"
              placeholder="비밀번호"
              prepend-inner-icon="mdi-lock-outline"
              variant="outlined"
              @click:append-inner="visible = !visible"
              v-model="PasswordValidation"
              :rules="PasswordValidationRules"
              required
              :error-messages="PasswordValidationErrors"
              ></v-text-field>
             </v-col>
    </v-row>

    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          전화번호
        </div>

        <v-row>

            <v-col cols="3">
                <v-select
                  density="compact"
                
                  variant="outlined"
                  v-model="firstPhoneNum"
                :items="items"
              
              
                required
                maxlength="4" 
                ></v-select>
            </v-col>


    <v-col cols="3">
          <v-text-field
          
                type="text"
                density="compact"
                variant="outlined"
                v-model="middlePhoneNum"
                :rules="phoneRules"
                required
                :error-messages="phoneErrors"
                maxlength="4" 
          ></v-text-field>

    </v-col>

    <v-col cols="3">
        <v-text-field
         
          type="text"
          density="compact"
          variant="outlined"
          v-model="lastPhoneNum"
          :rules="phoneRules"
          required
          :error-messages="phoneErrors"
         
   
        ></v-text-field>

    </v-col>

    </v-row>

        <v-row>

          <v-textarea
          label="본인을 알릴 자기소개를 입력하세요."
          row-height="30"
          rows="4"
          variant="filled"
          auto-grow
          shaped
        ></v-textarea>
        </v-row>
    <!--회원가입 버튼-->
        <v-btn
          class="mb-8"
          color="blue"
          size="large"
          variant="tonal"
          block
        >
         회원가입
        </v-btn>
        
     
      </v-card>


     
    </div>

    <FindIdComponent v-model:value="findIdDialog" @findIdClose=closeFindIdDiolog />
    <FindPwdComponent v-model:value="findPwdDialog" @findPwdClose=closeFindPwdDiolog />
  </template>



  <script>
   import axios from 'axios';

    export default {
    data(){
        return{
               visible: false, //비밀번호 값 보이게 or 안보이게
              items:['010','011','016','017','019'], //대한민국 앞자리번호 리스트
              firstPhoneNum:'010', //디폴트값 - > 맨앞 번호
              middlePhoneNum:'',//두번째 번호
              lastPhoneNum:'', //세번째 번호 

              password:'',//패스워드 변수
              PasswordValidation:'',//패스워드 확인 변수 
              email:'', //이메일 변수 
              code:'',//인증코드에 관한 변수 
              nickName:'',//사용할 닉네임 
              name:'',//실명 

              showAuthCode:false, // 이메일 인증코드 전송시 인증코드입력 필드 노출에 관한 변수 
              isEmailReadonly:false, //이메일 전송이 완료되었으면 이메일 정보를 수정할 수 없게함 
              isNickNameReadonly:false,//닉네임 중복검증이 완료되면 닉네임 수정할 수 없게함 

              isEmailAuthClear:false ,//이메일 검증 여부
              isNickNameClear:false, //닉네임 중복 검증여부
              resend:false, //이메일 인증 다시보내기 
              
              //룰s 
              emailRules: [
                v => !!v || '이메일을 입력해 주세요.', //이메일이 입력되지 않았을 경우
                v => v.trim().length > 0 || '이메일은 공백일 수 없습니다.', //이메일에 공백이 들어갔을 경우
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
                v => /.+@.+\..+/.test(v) || '이메일 형식이 올바르지 않습니다.' //@와 . 이 들어가지않았을 경우
              ],
              passwordRules: [
                v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
                v => (v && v.length >= 8) || '비밀번호는 최소 8자 이상이어야 합니다.', // 최소 8자
                v => (v && v.length <= 20) || '비밀번호는 최대 20자까지 입력할 수 있습니다.', // 최대 20자
                v => /[a-zA-Z]/.test(v) || '비밀번호에는 최소 하나의 문자(알파벳)가 포함되어야 합니다.', // 문자 포함
                v => /\d/.test(v) || '비밀번호에는 최소 하나의 숫자가 포함되어야 합니다.', // 숫자 포함
                v => /[!@#$%^&*(),.?":{}|<>]/.test(v) || '비밀번호에는 최소 하나의 특수문자가 포함되어야 합니다.', // 특수문자 포함
              ],
              PasswordValidationRules:[
              v => !!v || '비밀번호 확인을 입력해 주세요.', // 비밀번호 확인이 비어 있는 경우 메시지
              v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
              v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              v => v === this.password || '비밀번호가 일치하지 않습니다.', // 비밀번호 확인이 비밀번호와 일치하지 않을 경우 메시지
              ],
              codeRules:[
                v => v.trim().length > 0 || '인증코드를 입력해 주세요',// 공백불가
               v => !!v || '인증코드를 입력 해주세요.', //null 불가 
               v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              ],
              nickNameRules:[
              v => !!v || '닉네임을 입력해 주세요.', // 닉네임이 입력되지 않았을 경우
              v => v.trim().length > 0 || '닉네임은 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
              v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              v => !/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(v) || '닉네임에는 한글을 사용할 수 없습니다.', // 한글 사용 금지
              v => /^[a-zA-Z0-9_ ]+$/.test(v) || '닉네임에는 _를 제외한 특수문자를 사용할 수 없습니다.', // 특수문자 검증
              v => v.length <= 15 || '닉네임은 최대 15자까지 입력할 수 있습니다.', // 최대 길이 검증
            
              ],
              nameRules:[
              v => !!v || '이름을 입력해 주세요.', // 닉네임이 입력되지 않았을 경우
              v => v.trim().length > 0 || '이름은 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
              v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              ],
              phoneRules:[
              v => !!v || '핸드폰 번호를 입력해주세요.', // 핸드폰 번호를 입력하지 않았을때 
              v => v.trim().length > 0 || '핸드폰번호는 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
              v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              v => v.length === 4 || '잘못된 전화번호 형식입니다.', // 각 핸드폰 번호 길이가  4자 길이인지 확인
              ],

              nameErrors:[],
              nickNameErrors:[],
              codeErrors:[],
              emailErrors: [],
              passwordErrors: [],
              PasswordValidationErrors:[],
              phoneErrors:[]
                }
         
      },
      methods:{
        validateField(value, rules) { //rules 에러를 저장 
          return rules
            .map(rule => rule(value))
            .filter(error => typeof error === 'string');
        },
        AuthCodeOpen(){ //인증코드 필드 노출 
          this.showAuthCode=true
        },
        AuthCodeClose(){
          this.showAuthCode=false
        },
        //이메일 중복검사 및 인증코드전송 
        emailAuth(){

          this.emailErrors = this.validateField(this.email, this.emailRules); //이메일 검증 에러메시지 배열
          if (this.emailErrors.length > 0 ) { //만약 이메일 검증에러가 있을 시   return
            
            return;//불필요한 axios 요청 방지  
                }
          let formData = new FormData();
          formData.append('email',this.email)

          axios.post('/api/members/emailAuth',formData)
          .then(()=>{
            this.AuthCodeOpen()//인증코드 필드 노출 
            this.isEmailReadonly=true//이메일 정보 수정 불가 
            this.resend=true//다시보내기 버튼 활성화
            alert('이메일 전송이 완료되었습니다.')
            
          }).catch((err)=>{
            if(err.response.status===400){//400일시 중복이메일 
              alert('이미 사용중인 이메일입니다.')
            }
            if(err.response.status===404){//404일시 기타오류 
              alert('잘못된 이메일 형식이거나 , 잘못된 입력입니다.')
            }
          })
        },
        //이메일 인증코드 검증 
        emailValidation(){

          this.codeErrors = this.validateField(this.code, this.codeRules); //인증코드 검증 에러메시지 배열
          if (this.codeErrors.length > 0 ) { //만약 인증코드 검증에러가 있을 시   return
            
            return;//불필요한 axios 요청 방지  
                }

          let formData=new FormData();
          formData.append('email',this.email)
          formData.append('code',this.code)//이메일 정보수정을 못하게 하였으므로 현재 email값을 가져올 수 있음 

          axios.post('/api/members/emailValidation',formData)
          .then(()=>{
            this.AuthCodeClose();// 인증코드 필드 닫음
            this.isEmailAuthClear=true; //이메일 검증완료
            alert('검증되었습니다.')
          })
          .catch((err)=>{
            if(err.response.status===400){//400일시 인증코드 불일치 
              alert('인증코드가 맞지않습니다.')
            }
            if(err.response.status===422){//422 일시 만료된 인증 
              alert('만료된 인증입니다.')
            }
          })
        },
        nickNameValidation(){
          this.nickNameErrors = this.validateField(this.nickName, this.nickNameRules); //닉네임 검증 에러메시지 배열
          if (this.codeErrors.length > 0 ) { //만약 닉네임 검증에러가 있을 시   return
            
            return;//불필요한 axios 요청 방지  
                }



              this.isNickNameClear =true; //닉네임 중복검증 완료 
              this.isNickNameReadonly=true;//검증이 완료 되면 닉네임 수정불가
        }
      }
    }
  </script>

  <style>

.btn-back {
  position: absolute;
  top:8px; 
  left: 10px; 
}

.card-scroll {
  overflow-y: auto; 
  max-height: 500px; 
}

  </style>