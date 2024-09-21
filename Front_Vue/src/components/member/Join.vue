<template >
    <div >
      

      <v-card
        class="mx-auto pa-16 pb-8 card-scroll mt-13" 
        elevation="15"
        max-width="600"
        rounded="lg"
        max-height="1000"        
      >

      <!--로딩 스피너-->
      <v-progress-linear
          color="black"
          indeterminate
          v-if="loading"
         ></v-progress-linear>
     
    <!--뒤로가기-->
   <router-link to="/login"> <v-btn icon="mdi-arrow-left" size="32"  class="btn-back" :disabled="loading"></v-btn></router-link>

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
                      :disabled="loading"
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
                        :disabled="loading"
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
                        :disabled="loading"
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
                    :disabled="loading"                
                  ></v-text-field>
               </v-col>

               <v-col cols="2">
                  <v-btn
                        height="40"
                        min-width="50"
                        color="blue"
                        @click="emailValidation()"
                        :disabled="loading"
                    >
                    검증
                  </v-btn>
               </v-col>

               <!--타이머-->
              <v-col cols="6" class="mt-4">
                <small class="text-caption text-medium-emphasis">
                  <Timer ref="emailTimer"></Timer>
                </small>
              </v-col>
      
    
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
                  :disabled="loading"
                ></v-text-field>
            </v-col>

         <!--중복 검증버튼-->
           <v-col cols="4">
                    <v-btn
                      v-show="!isNickNameClear"
                      height="40"
                      min-width="50"
                      color="blue"
                      :disabled="loading"
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
                  :disabled="loading"          
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
                      :disabled="loading"
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
              :disabled="loading"
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
                  :disabled="loading"
                ></v-select>
            </v-col>


            <v-col cols="3">
                  <v-text-field                 
                        type="text"
                        density="compact"
                        variant="outlined"
                        v-model="middlePhoneNum"                      
                        required                
                        maxlength="4" 
                        :disabled="loading"                   
                  ></v-text-field>

            </v-col>

            <v-col cols="3">
                <v-text-field        
                  type="text"
                  density="compact"
                  variant="outlined"
                  v-model="lastPhoneNum"         
                  required        
                  maxlength="4"   
                  :disabled="loading"
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
              v-model="introduce"
              maxlength="100" 
              :disabled="loading"
            ></v-textarea>
        </v-row>

    <!--회원가입 버튼-->
        <v-btn
          class="mb-8"
          color="blue"
          size="large"
          variant="tonal"
          block
          @click="submitJoin()"
          :disabled="loading"
        >
         회원가입
        </v-btn>
        
     
      </v-card>


     
    </div>
    <!-- 아이디(이메일) 찾기 컴포넌트-->
    <FindIdComponent v-model:value="findIdDialog" @findIdClose=closeFindIdDiolog />
    <!-- 비밀번호 찾기 컴포넌트 -->
    <FindPwdComponent v-model:value="findPwdDialog" @findPwdClose=closeFindPwdDiolog />
  </template>



  <script>
   import api from "@/api"
   import Timer from "@/components/common/utill/Timer.vue"
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
              introduce:'',//자기소개
              loading: false, // 로딩 상태 = > 이메일 인증시 사용자에게 로딩상태 알려줄려고 함 .

              showAuthCode:false, // 이메일 인증코드 전송시 인증코드입력 필드 노출에 관한 변수 
              isEmailReadonly:false, //이메일 전송이 완료되었으면 이메일 정보를 수정할 수 없게함 
              isNickNameReadonly:false,//닉네임 중복검증이 완료되면 닉네임 수정할 수 없게함 

              isEmailAuthClear:false ,//이메일 검증 여부
              isNickNameClear:false, //닉네임 중복 검증여부
              resend:false, //이메일 인증 다시보내기 
              
              //룰
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

              // 에러 모음 배열들  = >검증할때마다 저장해서 배열의 길이를 확인할 것이다.
              nameErrors:[],//이름 에러 
              nickNameErrors:[],//닉네임 에러
              codeErrors:[],//인증코드에러
              emailErrors: [],//이메일 에러 
              passwordErrors: [],//비밀번호 에러 
              PasswordValidationErrors:[],//비밀번호 확인 해러 
              
          }
         
      },
      computed:{
        fullPhoneNumber() {
        // 핸드폰 번호를 통합하여 검증할 수 있도록 문자열을 형성
        return `${this.firstPhoneNum}-${this.middlePhoneNum}-${this.lastPhoneNum}`;
        }
      },
      components:{
        Timer
      },
      methods:{
        validateField(value, rules) { //rules 에러를 저장 
          return rules
            .map(rule => rule(value))
            .filter(error => typeof error === 'string');
        },
        //회원가입 메시지를 띄우기 위해서 비동기처리를 위한 메시지 메소드
        showAlert(message) { 
          return new Promise((resolve) => {
            alert(message);
            resolve();
          });
        },
      
        validatePhoneNumber() {
          // 통합된 핸드폰 번호를 검증
          this.phoneErrors = this.validateField(this.fullPhoneNumber, this.phoneRules);
        },
        AuthCodeOpen(){ //인증코드 필드 노출 
          this.showAuthCode=true
        },
        AuthCodeClose(){
          this.showAuthCode=false
        },
        //이메일 중복검사 및 인증코드전송 
        emailAuth(){
          if(this.loading===true){
            return
          }

          this.emailErrors = this.validateField(this.email, this.emailRules); //이메일 검증 에러메시지 배열

          if (this.emailErrors.length > 0 ) { //만약 이메일 검증에러가 있을 시   return
            
            return;//불필요한 axios 요청 방지  
          }

         
         
         
                  this.loading = true; // 로딩 시작 = > 사용자에게 알려줄려고
                  //인증코드 이메일 전송 API
                    api.post('/members/emailAuth',{
            
                      email:this.email
                    })
                    .then(()=>{
                      //성공시              
                      this.AuthCodeOpen()//인증코드 필드 노출 
                      this.isEmailReadonly=true//이메일 정보 수정 불가 
                      this.resend=true//다시보내기 버튼 활성화
                      
                      this.$refs.emailTimer.resetTimer();//타이머 작동 
                    
                      alert('이메일 전송이 완료되었습니다.')
                      
                    })
                    .catch(err => {
                      //실패시 서버로부터 받은 메시지 출력 
                      if(err?.response?.data?.message){
                      alert(err?.response?.data?.message)  
                      }
                    })
                    .finally(() => {
                      this.loading = false; // 로딩 종료
                    });
              
                
               
         
        },
        //이메일 인증코드 검증 
        emailValidation(){
          if(this.isLoading===true){
            return
          }

          this.codeErrors = this.validateField(this.code, this.codeRules); //인증코드 검증 에러메시지 배열
          if (this.codeErrors.length > 0 ) { //만약 인증코드 검증에러가 있을 시   return
            
            return;//불필요한 axios 요청 방지  
          }
          //로딩 스피너 FALSE
          this.isLoading=true

          //인증번호 검증 API
          api.post('/members/emailValidate',{
            'email':this.email,//이메일 
            'code':this.code//인증번호 
          })
          .then(()=>{
            //성공시      
            this.AuthCodeClose();// 인증코드 필드 닫음
            this.isEmailAuthClear=true; //이메일 검증완료
            alert('검증되었습니다.')
          })
          .catch((err)=>{
            //실패시
            if(err?.response?.data?.message){
            alert(err?.response?.data?.message)  
            }
       
         })
         .finally(()=>{
          this.isLoading=false//로딩 변수 FALSE 
         })
         
        },
        //닉네임 중복검증 메서드 
        nickNameValidation(){
          if(this.isLoading===true){
            return
          }

          //닉네임 검증 에러메시지 배열
          this.nickNameErrors = this.validateField(this.nickName, this.nickNameRules); 

                 //만약 닉네임 검증에러가 있을 시   return
                if (this.nickNameErrors.length > 0 ) {
          
                return;//불필요한 axios 요청 방지 
         
                }
                
                //로딩스피너값 true= 진행되고있는 http요청
                this.isLoading=true
                
                //닉네임 검증 api 호출 
                api.post('/members/nickValidate',{
                  nickname:this.nickName//body에 닉네임값 
                })
                .then(()=>{
                  //성공시 
                    alert('사용가능한 닉네임입니다.')
                    this.isNickNameClear =true; //닉네임 중복검증 완료 
                    this.isNickNameReadonly=true;//검증이 완료 되면 닉네임 수정불가
                  
                })
                .catch((err)=>{
                    //실패시
                    if(err?.response?.data?.message){
                    alert(err?.response?.data?.message)  
                    }
                    
                })
                .finally(()=>{
                  //로딩스피너 값 false 
                  this.isLoading=false
                })
              
            
        },
        // 최종적인 회원가입 메서드 ( 닉네임검증, 이메일검증 을 진행해야함 )
        submitJoin(){
          //만약 로딩스피너가 true면 리턴 
          if(this.isLoading===true){
            return
          }
          
          //이메일 인증을 아직진행하지않았을때 리턴 
          if(!this.isEmailAuthClear){ 
            alert('이메일 인증을 진행해주세요.')
            return
          }
          //닉네임 중복검사를 진행하지않았을때 
          if(!this.isNickNameClear){ 
            alert('닉네임 중복확인을 진행해주세요.')
            return
          }
          //이름검증
          this.nameErrors = this.validateField(this.name, this.nameRules); 
          //패스워드검증
          this.passwordErrors=this.validateField(this.password,this.passwordRules);
          //비밀번호 확인검증
          this.PasswordValidationErrors=this.validateField(this.PasswordValidation,this.PasswordValidationRules); 

                //이중 하나라도 충족하지않으면 리턴
                if (this.nameErrors.length > 0 || this.passwordErrors.length>0 ||this.PasswordValidationErrors.length>0 ) {  
        
                return;//불필요한 axios 요청 방지  
                }

                //휴대폰 번호 결합 (010 + 두번째 필드+ 세번쨰 필드)
               const fullPhone=(this.firstPhoneNum+this.middlePhoneNum+this.lastPhoneNum); 
               
               //아래부터 휴대폰번호 관련 validation
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

               //회원가입에 필요한 정보
                const formdata=new FormData();
                formdata.append('email',this.email)//이메일
                formdata.append('password',this.password)//패스워드
                formdata.append('nickname',this.nickName)//닉네임
                formdata.append('name',this.name)//이름
                formdata.append('phone',fullPhone)//휴대폰번호
                formdata.append('introduce',this.introduce)//자기소개(널값 허용)

                //회원가입진행중 상태업데이트
                this.isLoading=true

                //회원가입 api 호출 
                api.post("/members", formdata) 
                .then(
                  async () => {//async / await 활용 
                 
                    // showAlert 메서드로 alert을 먼저 띄우고 라우터로 푸시하도록 
                    await this.showAlert('회원가입이 완료되었습니다. 환영합니다!!!');
                    // alert이 닫힌 후 router.push 호출
                    this.$router.push('/login');
                  
                })
                .catch((err)=>{
                  //실패시
                  if(err?.response?.data?.message){
                  alert(err?.response?.data?.message)  
                  }
                  
                })
                .finally(()=>{
                  //로딩스피너 false
                  this.isLoading=false
                })
               
                
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