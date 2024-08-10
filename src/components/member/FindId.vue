<template>
   
   <!-- 아이디 찾기 모달-->
   
   <v-dialog v-model="localDialog"  max-width="500" >

    <v-card 
        prepend-icon="mdi-account"
        title="아이디 찾기"
        class="pa-3"
      >
           <v-card-text class="mt-6">

              <div class="text-subtitle-1 text-medium-emphasis">Email
                
              </div>

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
          전화번호
        </div>


        <v-row>
            <v-col cols="4">
                <v-select
                  density="compact"
                
                  variant="outlined"
                  v-model="firstPhoneNum"
                :items="items"
              
              
                required
              
                ></v-select>
           </v-col>

            <v-col cols="4">
              
                <v-text-field
                
                  type="text"
                  density="compact"
                
              
                  variant="outlined"
                  
                
          
                ></v-text-field>

            </v-col>

              <v-col cols="4">
                  <v-text-field
                    type="text"
                    density="compact"
                     variant="outlined"
                  
                  ></v-text-field>

              </v-col>

    </v-row>
         
  </v-card-text>

    
        <v-card-actions>
          <v-spacer></v-spacer>
          

          <v-btn
            text="Close"
            variant="plain"
            @click=closeDialog()> 
          ></v-btn>

          <v-btn
            color="primary"
            text="Submit"
            variant="tonal"
            @click=closeDialog()
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  export default {
    name: 'FindIdComponent',
  

    props: {
      value: {//로그인 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
        items:['010','011','016','017','019'],// 우리나라 앞번호 종류 리스트 
        firstPhoneNum:'010' // 초기값 010 
    }
}
 ,computed:{
    localDialog:{
        get(){
            return this.value // 현재 아이디찾기 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
        }
    }
 }   
    ,
    methods: {
      closeDialog() {
        this.$emit('findIdClose');// 로그인 컴포넌트로 닫는 이벤트 전송
      }
    }
  }
  </script>