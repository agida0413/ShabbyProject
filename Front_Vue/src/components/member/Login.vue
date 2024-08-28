<template>
 
    <!--로고-->
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
        <!--이메일-->
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

        <!--비밀번호-->
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

        <!--로그인 버튼 -->
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

        <!--이메일 찾기 모달 true 버튼-->
            <button
              class="text-caption text-decoration-none text-blue"
              @click="openFindIdDialog"
            >
            이메일을 잊으셨나요?
            </button>

        <!--구분선-->
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
    <!--아이디(이메일) 찾기 컴포넌트-->
    <FindIdComponent v-model:value="findIdDialog" @findIdClose="closeFindIdDialog" />
    <!--비밀번호 찾기 (초기화) 컴포넌트-->
    <FindPwdComponent v-model:value="findPwdDialog" @findPwdClose="closeFindPwdDialog" />

</template>

<script>
import FindIdComponent from "@/components/member/FindId.vue"
import FindPwdComponent from "./FindPwd.vue";
import api from "@/api"

export default {
  data: () => ({
    visible: false,//패스워드 **  or 노출 변수
    email: '',//이메일
    password: '',//패스워드
    //룰(validation)
    emailRules: [
      v => !!v || '이메일을 입력해 주세요.', //이메일이 입력되지 않았을 경우
      v => v.trim().length > 0 || '이메일은 공백일 수 없습니다.', //이메일에 공백이 들어갔을 경우
      v => /.+@.+\..+/.test(v) || '이메일 형식이 올바르지 않습니다.' //@와 . 이 들어가지않았을 경우
    ],
    passwordRules: [
      v => !!v || '비밀번호를 입력해주세요.', // 비밀번호가 입력되지않았을 경우
      v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
    ],
   
    emailErrors: [], //이메일validtion에러 발생시 배열 
    passwordErrors: [],//패스워드validation에러 발생시 배열 
    findIdDialog: false, // 아이디 찾기 모달 
    findPwdDialog: false,// 비밀번호 찾기 모달 
    isLoading:false//로딩스피너 변수
  }),
  components: {
    FindIdComponent,//아이디 찾기 컴포넌트
    FindPwdComponent//패스워드 찾기 컴포넌트
  },
  methods: {
    //rules 에러를 저장
    validateField(value, rules) {  
      return rules
        .map(rule => rule(value))
        .filter(error => typeof error === 'string');
    },
    // 아이디 찾기 모달 닫음
    closeFindIdDialog() { 
      this.findIdDialog = false;
    },
    // 아이디 찾기 모달 열음
    openFindIdDialog() {
      this.findIdDialog = true;
    },
    // 비밀번호  찾기 모달 닫음
    closeFindPwdDialog() {
      this.findPwdDialog = false;
    },
    // 비밀번호 찾기 모달 열음
    openFindPwdDialog() {  
      this.findPwdDialog = true;
    }, 
    //로그인 메소드 
    login() { 
      //로딩스피너가 true면 return
      if(this.isLoading===true){
            return
      }
      //이메일 검증 에러메시지 배열
      this.emailErrors = this.validateField(this.email, this.emailRules); 
      //비밀번호 검증 에러메시지 배열
      this.passwordErrors = this.validateField(this.password, this.passwordRules);

      //만약 이메일 검증배열과 비밀번호검증배열의 길이가 하나라도 0보다 크면 return
      if (this.emailErrors.length > 0 || this.passwordErrors.length > 0) { 

        return;//불필요한 axios 요청 방지  
      }

      //이메일 유효성, 비밀번호 유효성 검사 통과시

      //formdata에 email과 비밀번호를 담는다.
      const formdata = new FormData();
      //시큐리티 내부에서 username으로 request에서 꺼내고 있기때문에 프론트쪽에서 key값을 username으로
      //하지만 실제 값은 email
      formdata.append('username', this.email);
      //패스워드
      formdata.append('password', this.password);

      //로그인이 진행중임을 나타내는변수 ( 로딩스피너 true )
      this.isLoading=true 

      //로그인 api 호출
      console.log('로그인에서의 토큰:'+localStorage.getItem('access'))
      api.post('/login', formdata)
      .then((res) => { 
          
          //성공했을시 
                //서버로 부터 받은 access헤더에서 토큰을 꺼내옴   
                const accessToken = res?.headers['access'];
                //로컬스토리지에 access토큰 저장 
                localStorage.setItem('access', accessToken); 

                //로컬스토리지에 있는 이전요청 url 가져옴 ==> why? 만약 회원이 url로 다른 자원에 접근했을때 라우터가드나 시큐리티인증에 실패하여
                //login 라우터로 리다이렉트 되었을때 로그인 라우터에서 로그인을 진행 후 이전요청으로 라우터 푸시하기 위함  
                const reqUrl = localStorage.getItem('requestUrl');

                //만약 이전요청이 없다면 /로 푸시(메인피드 default)
                if (!reqUrl) {
                  this.$router.push('/');
                } else {
                //있다면 이전요청으로 이동 
                this.$router.push(reqUrl);
              }
             
        })
        .catch((err)=>{
        //실패시 서버에서 전송한 에러메시지 출력
       
        alert(err?.response?.data?.message)
       
        })
        .finally(()=>{
          //로그인 진행이 끝남
          this.isLoading=false
        })
       
    }
  }
}
</script>
