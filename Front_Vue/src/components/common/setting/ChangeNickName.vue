<template>
    <div class="pa-4 text-center">
      <v-dialog
        v-model="localDialog"
        max-width="600"
        
      >
        
  
        <v-card
          prepend-icon="mdi-account"
          title="닉네임 변경"
          class="to-blackMode"
        >
          <v-card-text >
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between " >
        <span class="to-blackMode">새  닉네임</span>
        </div>
            <v-row>
          <!--닉네임-->
            <v-col cols="7">
                <v-text-field
                  density="compact"
                  placeholder="새 닉네임"
                  prepend-inner-icon="mdi-account"
                  variant="outlined"
                  v-model="nickname"  
                  :rules="nickNameRules"
                  required
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
            @click="nickNameValidation"
            >
              검증
            </v-btn>
          </v-col>

    </v-row>  
            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
              <span class="to-blackMode">현재 비밀번호</span>
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
                required
                :error-messages="passwordErrors"
                
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
              @click="submitNickChange()"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </template>

  <script>
  import api from '@/api';
export default {
    name: 'ChangeNickname',
  

    props: {
      value: {//세팅 컴포넌트로 부터 받은 모달 불리안값 
        type: Boolean,
        required: true
      }
    
},data(){
    return{
       nickname:'',//입력한 닉네임 변수
       password:'',//비밀번호 
       nickNameRules:[
       v => !!v || '닉네임을 입력해 주세요.', // 닉네임이 입력되지 않았을 경우
              v => v.trim().length > 0 || '닉네임은 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
              v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              v => !/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(v) || '닉네임에는 한글을 사용할 수 없습니다.', // 한글 사용 금지
              v => /^[a-zA-Z0-9_ ]+$/.test(v) || '닉네임에는 _를 제외한 특수문자를 사용할 수 없습니다.', // 특수문자 검증
              v => v.length <= 15 || '닉네임은 최대 15자까지 입력할 수 있습니다.', // 최대 길이 검증
          ] ,
       passwordRules: [
                v => !!v || '비밀번호를 입력 해주세요.', // 비밀번호가 입력되지않았을 경우
                v => v.trim().length > 0 || '잘못된 형식입니다.', // 공백입력시 
                v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
              ],
       nickNameErrors:[],//닉네임에러를 저장 
       passwordErrors:[],//패스워드에러를 저장 
       isNickNameClear:false,
       isNickNameReadonly:false 
                    
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
      validateField(value, rules) { //rules 에러를 저장 
          return rules
            .map(rule => rule(value))
            .filter(error => typeof error === 'string');
        }, 
        nickNameValidation(){
          this.nickNameErrors = this.validateField(this.nickname, this.nickNameRules); //닉네임 검증 에러메시지 배열
          
          if (this.nickNameErrors.length > 0 ) { //만약 닉네임 검증에러가 있을 시   return
          
            return;//불필요한 axios 요청 방지 
         
                }
                
                api.post('/members/nickValidate',{
                  nickname:this.nickname
                })
                .then(()=>{
                  
                    alert('사용가능한 닉네임입니다.')
                    this.isNickNameClear =true; //닉네임 중복검증 완료 
                    this.isNickNameReadonly=true;//검증이 완료 되면 닉네임 수정불가
                  
                })
                .catch((err)=>{
       
                  alert(err.response&&err.response.data.message)
                  
                })
                

            
        },
      closeDialog() {
        this.nickname=''
        this.nickNameErrors=[]
        this.isNickNameClear=false
        this.isNickNameReadonly=false
        this.password=''

        this.$emit('changeNickname');// 세팅 컴포넌트로 닫는 이벤트 전송
      },
      submitNickChange(){
        if(!this.isNickNameClear){
          alert('닉네임 검증을 진행 해주세요.')
          return
        }
        this.passwordErrors = this.validateField(this.password, this.passwordRules); //비밀번호 검증 에러메시지 배열
          if (this.passwordErrors.length > 0 ) { //만약 패스워드 검증에러가 있을 시   return
            
            return;//불필요한 axios 요청 방지  
                }
        //비밀번호 검증 후 맞다면 
            
        
        api.put("/setting/nickChange",{
          nickname:this.nickname,
          password:this.password
        })
        .then(()=>{
          alert('성공적으로 변경되었습니다.')
          this.closeDialog()
        })
        .catch((err)=>{
       
          alert(err.response&&err.response.data.message)
          
        })
       
      },
      cancle(){
        //취소
        this.closeDialog()
      }

    }
  }
  </script>