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
        >
          Log In
        </v-btn>
        
      <v-card-text class="text-center">

        <button
            class="text-caption text-decoration-none text-blue"
    
            rel="noopener noreferrer"
            target="_blank"
           @click="openFindIdDialog"
           
          >
            아이디를 잊으셨나요?</button>
            &nbsp;&nbsp;&nbsp;/ &nbsp;&nbsp;&nbsp;
            <button
            class="text-caption text-decoration-none text-blue"
    
            rel="noopener noreferrer"
            target="_blank"
           @click="openFindPwdDialog"
           
          >
          비밀번호를 잊으셨나요?</button>

      </v-card-text>
        <v-card-text class="text-center">
            
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

    export default {
      data: () => ({
        visible: false,
        email:'',
        emailRules: [
          v => !!v || '이메일을 입력해 주세요.'
        
        ],
        password:'',
        passwordRules: [
          v => !!v || '비밀번호를 입력해주세요.'
        
        ],
        findIdDialog:false,
        findPwdDialog:false
        
      }),
      components:{
        FindIdComponent,
        FindPwdComponent
      },
      methods:{
       closeFindIdDiolog(){
        this.findIdDialog=false;
       },
       openFindIdDialog(){
        this.findIdDialog=true
       },
       closeFindPwdDiolog(){
        this.findPwdDialog=false;
       },
       openFindPwdDialog(){
        this.findPwdDialog=true
       }
      }
    }
  </script>