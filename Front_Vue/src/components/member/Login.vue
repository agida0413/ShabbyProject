<template>
 
    <div>
      <v-img
        class="mx-auto my-6"
        max-width="150"
        src="@/assets/logo2.png"
      ></v-img> 
    </div>

    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="8"
      max-width="448"
      rounded="lg"
    >
      
        <div class="text-subtitle-1 text-medium-emphasis">Email</div>

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

        <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          비밀번호
        </div>

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

        <v-btn
          class="mb-8"
          color="blue"
          size="large"
          variant="tonal"
          block
          @click="login"
        >
          Log In
        </v-btn>
 

      <v-card-text class="text-center">
        <!--아이디 찾기 모달 true 버튼-->
        <button
          class="text-caption text-decoration-none text-blue"
          @click="openFindIdDialog"
        >
          아이디를 잊으셨나요?
        </button>

        &nbsp;&nbsp;&nbsp;/ &nbsp;&nbsp;&nbsp;

        <!--비밀번호 찾기 모달 true 버튼-->
        <button
          class="text-caption text-decoration-none text-blue"
          @click="openFindPwdDialog"
        >
          비밀번호를 잊으셨나요?
        </button>
      </v-card-text>

      <v-card-text class="text-center">
        <!--회원가입으로 라우터 이동-->
        <router-link to="/join">
          <button class="text-blue text-decoration-none">
            회원가입<v-icon icon="mdi-chevron-right"></v-icon>
          </button>
        </router-link>
      </v-card-text>
    </v-card>

    <FindIdComponent v-model:value="findIdDialog" @findIdClose="closeFindIdDialog" />
    <FindPwdComponent v-model:value="findPwdDialog" @findPwdClose="closeFindPwdDialog" />

</template>

<script>
import FindIdComponent from "@/components/member/FindId.vue"
import FindPwdComponent from "./FindPwd.vue";
import api from "@/api"

export default {
  data: () => ({
    visible: false,
    email: '',
    password: '',
    emailRules: [
      v => !!v || '이메일을 입력해 주세요.', //이메일이 입력되지 않았을 경우
      v => v.trim().length > 0 || '이메일은 공백일 수 없습니다.', //이메일에 공백이 들어갔을 경우
     
      v => /.+@.+\..+/.test(v) || '이메일 형식이 올바르지 않습니다.' //@와 . 이 들어가지않았을 경우
    ],
    passwordRules: [
      v => !!v || '비밀번호를 입력해주세요.', // 비밀번호가 입력되지않았을 경우
      v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
    ],
    emailErrors: [],
    passwordErrors: [],
    findIdDialog: false, // 아이디 찾기 모달 
    findPwdDialog: false// 비밀번호 찾기 모달 
  }),
  components: {
    FindIdComponent,
    FindPwdComponent
  },
  methods: {

    validateField(value, rules) { //rules 에러를 저장 
      return rules
        .map(rule => rule(value))
        .filter(error => typeof error === 'string');
    },
    closeFindIdDialog() { // 아이디 찾기 모달 닫음
      this.findIdDialog = false;
    },
    openFindIdDialog() {// 아이디 찾기 모달 열음
      this.findIdDialog = true;
    },
    closeFindPwdDialog() {// 비밀번호  찾기 모달 닫음
      this.findPwdDialog = false;
    },
    openFindPwdDialog() { // 비밀번호 찾기 모달 열음 
      this.findPwdDialog = true;
    }, 
    login() { //로그인 시도
      
      this.emailErrors = this.validateField(this.email, this.emailRules); //이메일 검증 에러메시지 배열
      this.passwordErrors = this.validateField(this.password, this.passwordRules);//비밀번호 검증 에러메시지 배열
 
      if (this.emailErrors.length > 0 || this.passwordErrors.length > 0) { //만약 이메일 검증과 비밀번호검증이 하나라도 해당되면 return

        return;//불필요한 axios 요청 방지  
            }

            //이메일 유효성, 비밀번호 유효성 검사 통과시

      const formdata = new FormData();//formdata에 email과 비밀번호를 담는다.
      formdata.append('username', this.email);
      formdata.append('password', this.password);

      api.post('/login', formdata)
        .then((res) => { 
          
          //성공했을시 
          if(res.status===200){
                const accessToken = res.headers['access'];//헤더에 엑세스토큰 저장 
                localStorage.setItem('access', accessToken); 

                const reqUrl = localStorage.getItem('requestUrl'); //로컬스토리지에 있는 이전요청 url 가져옴 

                if (!reqUrl) {//만약 이전요청이 없다면 /로 푸시
                  this.$router.push('/');
                } else {
                this.$router.push(reqUrl);//있다면 이전요청으로 이동 
              }
          }else{
            throw new Error("something err");
            
          }          
        })
        .catch((err) => {

          if(err.response.status===400){
            alert('입력정보를 확인해 주세요.'); //로그인 일치정보가 없을시 
            
          }else{
            alert('예기치 못한 오류가 발생했습니다. 잠시 뒤에 이용해주세요.')
          }
        
        });
    }
  }
}
</script>
