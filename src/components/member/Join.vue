<template >
    <div >
      <div>
      <v-img
        class="mx-auto my-6"
        max-width="150"
        src="@/assets/logo2.png"
      ></v-img> 
    </div>
      <v-card
        class="mx-auto pa-16 pb-8 card-scroll" 
        elevation="8"
        max-width="600"
        rounded="lg"
        max-height="600"
        
      >
      <v-btn icon="mdi-arrow-left" size="32"  class="btn-back"></v-btn>
      
            <div class="text-subtitle-1 text-medium-emphasis">Email</div>
           <v-row>
            <v-col cols="8">
        <v-text-field 
          density="compact"
          placeholder="Email"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          v-model="email"
          :rules="emailRules"
          
        ></v-text-field>
    </v-col>
    <v-col cols="4">
        <v-btn
          height="40"
          min-width="50"
        color="blue"
        >
          중복확인
        </v-btn>
    </v-col>

    </v-row>
    <div class="text-subtitle-1 text-medium-emphasis">인증코드</div>
    <v-row >
        <v-col cols="4">
        <v-text-field 
          density="compact"
          placeholder="인증코드"
      
          variant="outlined"
          v-model="email"
          :rules="emailRules"
          
        ></v-text-field>
    </v-col>
    <v-col cols="5">
        <v-btn 
          :key="`subscribe-${isSubscriber}`"
          :border="`thin ${isSubscriber ? 'error' : 'success'}`"
          :color="isSubscriber ? 'error' : 'success'"
          :prepend-icon="isSubscriber ? 'mdi-close' : 'mdi-email'"
          :slim="isSubscriber"
          :text="isSubscriber ? 'Cancel' : '인증코드 보내기'"
          :variant="isSubscriber ? 'plain' : 'tonal'"
          class="me-2 text-none"
          
          flat
          @click="isSubscriber = !isSubscriber"
        ></v-btn>

     </v-col>
        <v-col cols="3" class="mt-4"><small class="text-caption text-medium-emphasis">남은시간 59</small></v-col>
      
    
       </v-row>
       <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          이름
        </div>
        <v-row>
            <v-col cols="7">
        <v-text-field
   
        
          density="compact"
          placeholder="이름"
          prepend-inner-icon="mdi-account"
          variant="outlined"
       
          v-model="password"
          :rules="passwordRules"
        ></v-text-field>
    </v-col>
    </v-row>

   
       
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          비밀번호
        </div>
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
        ></v-text-field>
    </v-col>
    </v-row>
   
    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
       비밀번호 확인
        </div>
        <v-row>
            <v-col cols="7">
        <v-text-field
          :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="visible ? 'text' : 'password'"
          density="compact"
          placeholder="비밀번호 확인"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          @click:append-inner="visible = !visible"
          v-model="password"
          :rules="passwordRules"
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
       
        ></v-select>
    </v-col>
    <v-col cols="3">
        <v-text-field
         
          type="text"
          density="compact"
         
       
          variant="outlined"
          
         
   
        ></v-text-field>

    </v-col>
    <v-col cols="3">
        <v-text-field
         
          type="text"
          density="compact"
         
       
          variant="outlined"
          
         
   
        ></v-text-field>

    </v-col>
    </v-row>


    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          닉네임
        </div>
        <v-row>
            <v-col cols="7">
        <v-text-field
   
        
          density="compact"
          placeholder="닉네임"
          prepend-inner-icon="mdi-account"
          variant="outlined"
       
          v-model="password"
          :rules="passwordRules"
        ></v-text-field>
    </v-col>

    <v-col cols="4">
        <v-btn
          height="40"
          min-width="50"
        color="blue"
        >
          검증
        </v-btn>
    </v-col>
    </v-row>
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
   
    export default {
    data(){
        return{
              items:['010','011','016','017','019'],
        firstPhoneNum:'010'
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