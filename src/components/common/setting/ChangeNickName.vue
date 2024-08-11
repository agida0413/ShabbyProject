<template>
    <div class="pa-4 text-center">
      <v-dialog
        v-model="localDialog"
        max-width="600"
      >
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn
            class="text-none font-weight-regular"
            prepend-icon="mdi-account"
            text="Edit Profile"
            variant="tonal"
            v-bind="activatorProps"
          ></v-btn>
        </template>
  
        <v-card
          prepend-icon="mdi-account"
          title="닉네임 변경"
        >
          <v-card-text>
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
        새  닉네임
        </div>
            <v-row>
          <!--닉네임-->
            <v-col cols="7">
                <v-text-field
          
                
                  density="compact"
                  placeholder="새 닉네임"
                  prepend-inner-icon="mdi-account"
                  variant="outlined"
              
                  v-model="password"
                  :rules="passwordRules"
                ></v-text-field>
        </v-col>

        <!--중복 검증버튼-->
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
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
        현재 비밀번호
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
              @click="cancle"
            ></v-btn>
  
            <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="verifyPwd"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </template>

  <script>
export default {
    name: 'ChangeNickname',
  

    props: {
      value: {//세팅 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
       
       
    }
}
 ,computed:{
    localDialog:{
        get(){
            return this.value // 현재 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
        }
    }
 }   
    ,
    methods: {
      closeDialog() {
        this.$emit('changeNickname');// 세팅 컴포넌트로 닫는 이벤트 전송
      },
      verifyPwd(){
        //비밀번호 검증 후 맞다면 

        this.closeDialog()
      },
      cancle(){
        //취소
        this.closeDialog()
      }

    }
  }
  </script>