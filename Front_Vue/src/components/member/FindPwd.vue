<template>
    <v-dialog v-model="localDialog" max-width="600">

        <v-card 
        prepend-icon="mdi-account"
        title="비밀번호 초기화"
        class="pa-3"
      >
      <v-divider></v-divider>
        <!--로딩 스피너-->
        <v-row
        align="center"
        justify="center"
        class="mt-5"
        v-if="loading"
        >
            <v-col
              cols="auto"
              class="text-center"
            >
              
                    <v-progress-circular
                      
                      indeterminate
                      color="primary"
                      size="64"
                    ></v-progress-circular>
            </v-col>

    </v-row>
    <v-card-text class="mt-6">
<!--이메일 입력-->
<v-row>
                  <v-col cols="8">
                    <div class="text-subtitle-1 text-medium-emphasis">Email</div>
              <v-text-field 
                density="compact"
                placeholder="Email"
                prepend-inner-icon="mdi-email-outline"
                variant="outlined"
                v-model="email"
                :rules="emailRules"
                 :error-messages="emailErrors"
              ></v-text-field>
                </v-col>
              </v-row>
      
        <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
          이름
        </div>

        <v-row>
            <v-col cols="7">
                  <v-text-field
            
                  :rules="nameRules"
                   required
                  :error-messages="nameErrors"
                    density="compact"
                    placeholder="이름"
                    prepend-inner-icon="mdi-account"
                    variant="outlined"
                
                    v-model="name"
                   
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
                  maxlength="4" 
                  v-model="middlePhoneNum"
                  variant="outlined"
                  
                
          
                ></v-text-field>

            </v-col>

              <v-col cols="3">
                  <v-text-field
                  maxlength="4" 
                    type="text"
                    density="compact"
                     variant="outlined"
                      v-model="lastPhoneNum"
                  ></v-text-field>

              </v-col>

    </v-row>
        
     



       

         
        </v-card-text>

    
        <v-card-actions>
          <v-spacer></v-spacer>
          

          <v-btn
            text="Close"
            variant="plain"
            @click=closeDialog()
          ></v-btn>

          <v-btn
            color="primary"
            text="Submit"
            variant="tonal"
            @click=submitFindPwd
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  import api from "@/api"
  export default {
    name: 'FindPwdComponent',
    data(){
      return{  
        items:['010','011','016','017','019'],// 우리나라 앞번호 종류 리스트 
        firstPhoneNum:'010', // 초기값 010 
        middlePhoneNum:'',//두번째 핸드폰 번호값
        lastPhoneNum:'',//마지막 핸드폰 번호값
        name:'',//이름
        email:'', //이메일 변수 
        loading:false,//로딩스피너 변수 

        
        emailRules: [
                v => !!v || '이메일을 입력해 주세요.', //이메일이 입력되지 않았을 경우
                v => v.trim().length > 0 || '이메일은 공백일 수 없습니다.', //이메일에 공백이 들어갔을 경우
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
                v => /.+@.+\..+/.test(v) || '이메일 형식이 올바르지 않습니다.' //@와 . 이 들어가지않았을 경우
              ],

        nameRules:[
        v => !!v || '이름을 입력해 주세요.', // 닉네임이 입력되지 않았을 경우
        v => v.trim().length > 0 || '이름은 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
        v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
                  ],  


        //에러모음
         nameErrors:[],
         emailErrors: []
      }
    },

    props: {
      value: {//로그인 컴포넌트로부터 받은 모달 불리안값
        type: Boolean,
        required: true
      }
    
}
 ,computed:{
    localDialog:{//props로 받은 모달 불리안값을 함수를 통해 리턴 후 바인딩
        get(){
            return this.value
        }
    }
 }   
    ,
    methods: {
      closeDialog() {
        if(this.loading===true){ //서버로 전송중일때 모달 못닫게함 . 
          
          return;
        }
        this.firstPhoneNum='010', 
                   this.middlePhoneNum=''
                   this.lastPhoneNum=''
                    this.name=''
                    this.email=''

        this.$emit('findPwdClose'); // 패스워드 찾기 컴포넌트로 모달을 닫는 이벤트 전송
      }, 
      validateField(value, rules) { //rules 에러를 저장 
          return rules
            .map(rule => rule(value))
            .filter(error => typeof error === 'string');
        },
        submitFindPwd(){
          if(this.loading===true){ //서버로 전송중일때  클릭못하게 
          return;
        }

          this.nameErrors = this.validateField(this.name, this.nameRules); //이름검증
          this.emailErrors = this.validateField(this.email, this.emailRules); //이름검증

          if (this.nameErrors.length > 0  || this.emailErrors.length) { //이중 하나라도 충족하지않으면 리턴 
        
        return;//불필요한 axios 요청 방지  
            }
            
            
            const fullPhoneNum= (this.firstPhoneNum+this.middlePhoneNum+this.lastPhoneNum); //전체 핸드폰번호 
        if (fullPhoneNum.length !== 11) {
                  alert('핸드폰 번호는 11자리여야 합니다.');
                  return;
                }

                // 한글 포함 여부 검증
                if (/[가-힣]/.test(fullPhoneNum)) {
                  alert('핸드폰 번호에 한글이 포함될 수 없습니다.');
                  return;
                }

                // 공백 포함 여부 검증
                if (/\s/.test(fullPhoneNum)) {
                  alert('핸드폰 번호에 공백이 포함될 수 없습니다.');
                  return;
                }

                // 숫자 이외의 문자 포함 여부 검증
                if (!/^\d+$/.test(fullPhoneNum)) {
                  alert('핸드폰 번호는 숫자만 포함해야 합니다.');
                  return;
                }

                this.loading=true; //로딩 스피너 시작 
                api.post('/members/findPassword',{
                  name:this.name,
                  phone:fullPhoneNum,
                  email:this.email
                })
                .then(()=>{
                 
                
                    alert('입력하신 이메일로 임시비밀번호를 보냈습니다.')
                    //변수  초기화 
             
                    this.closeDialog()
                  

                })
                .catch((err)=>{
                 
                alert(err.response&&err.response.data.message)
                
              })
              .finally(()=>{
                this.loading=false //로딩 끝
              })
               
        }
        
    }
  }
  </script>