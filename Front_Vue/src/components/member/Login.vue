<template >
    <div>
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
    
            rel="noopener noreferrer"
            target="_blank"
           @click="openFindIdDialog"
           
          >
            아이디를 잊으셨나요?</button>

            &nbsp;&nbsp;&nbsp;/ &nbsp;&nbsp;&nbsp;

            <!--비밀번호 찾기 모달 true 버튼-->
            <button
            class="text-caption text-decoration-none text-blue"
    
            rel="noopener noreferrer"
            target="_blank"
           @click="openFindPwdDialog"
           
          >
          비밀번호를 잊으셨나요?</button>

      </v-card-text>

        <v-card-text class="text-center">
            
          <!--회원가입으로 라우터 이동-->
            <router-link to="/join">
                <button
                  class="text-blue text-decoration-none"
                  
                  rel="noopener noreferrer"
                  target="_blank"
                >
                  회원가입<v-icon icon="mdi-chevron-right"></v-icon>
        </button>
          </router-link>


        </v-card-text>
      </v-card>


     
    </div>

    <FindIdComponent v-model:value="findIdDialog" @findIdClose=closeFindIdDiolog />
    <FindPwdComponent v-model:value="findPwdDialog" @findPwdClose=closeFindPwdDiolog />
  </template>



  <script>
    import FindIdComponent from "@/components/member/FindId.vue"
 import FindPwdComponent from "./FindPwd.vue";
import axios from"axios"
    export default {
      data: () => ({
        visible: false,
        email:'', 
        emailRules: [
          v => !!v || '이메일을 입력해 주세요.' //이메일 검증
        
        ],
        password:'',
        passwordRules: [
          v => !!v || '비밀번호를 입력해주세요.' //비밀번호 입력검증
        
        ],
        findIdDialog:false, //아이디 찾기 모달 값 초기 false
        findPwdDialog:false//비밀번호 찾기 모달 값 초기 false
        
      }),
      components:{
        FindIdComponent, // 아이디 찾기 모달 컴포넌트 등록
        FindPwdComponent// 비밀번호 찾기 모달 컴포넌트 등록
      },
      methods:{
       closeFindIdDiolog(){  //아이디 찾기모달값 false
        this.findIdDialog=false;
       },
       openFindIdDialog(){//아이디 찾기모달값 true
        this.findIdDialog=true
       },
       closeFindPwdDiolog(){ //비밀번호 찾기 모달값 false
        this.findPwdDialog=false;
       },
       openFindPwdDialog(){//비밀번호 찾기 모달값 true
        this.findPwdDialog=true
       },
       login(){
        
            const formdata = new FormData()

            formdata.append('username',this.email)
            formdata.append('password',this.password)
              axios.post('/api/login',formdata)
              .then((res)=>{
                     //로그인 성공시 
                const accessToken = res.headers['access'];//  엑세스토큰을 읽어옴
                localStorage.setItem('access',accessToken) //엑세스 토큰을 로컬에 저장

                const reqUrl=localStorage.getItem('requestUrl')//로그인 전 저장했던 이전요청 url을 가져옴

                if(reqUrl===undefined || reqUrl==='' || reqUrl===null){ 
                  //이전요청이 없다면 메인피드로 이동
                  this.$router.push('/');
                }else{
                  this.$router.push(reqUrl); //이전요청이 있으면 이전요청으로 이동 
                }
             
                
              }).catch((err)=>{
                console.log(err)
                alert('로그인 실패')
            })
          
            
        
    }

      }
    }
  </script>