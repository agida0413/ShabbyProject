<template>
       
    <div class="text-center">
      <v-dialog
        v-model="localDialog"
        max-width="600"    
      >
        
         <v-progress-linear
          color="cyan"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>

        <v-card
          prepend-icon="mdi-account"
          title="닉네임 변경"
          class="to-blackMode"
        >

          <v-card-text >

            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between " >
               <span class="to-blackMode">새  닉네임</span>
            </div>

           <!--닉네임-->
            <v-row>
      
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
                  :disabled="isLoading"
                ></v-text-field>
            </v-col>

        <!--중복 검증버튼-->
          <v-col cols="4">
                <v-btn
                  height="40"
                  min-width="50"
                color="blue"
                @click="nickNameValidation"
                :disabled="isLoading"
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
                :disabled="isLoading"
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
              :disabled="isLoading"
            ></v-btn>
  
            <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="submitNickChange()"
              :disabled="isLoading"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

    </div>
  </template>

  <script>
  import api from '@/api';
  import eventBus from "@/eventBus"
export default {
    name: 'ChangeNickname',
  
   //세팅 컴포넌트로 부터 받은 모달 불리안값 
    props: {
      value: {
        type: Boolean,
        required: true
      }
    
    },
    data(){

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
       isNickNameClear:false,//닉네임 중복검증 여부를 저장
       isNickNameReadonly:false,//닉네임 중복검증이 완료되면 readonly상태로 변경 
       isLoading:false, // 로딩상태정보를 저장 = > 버튼 여러번 클릭 방지 
       visible:false             
    }
 },
 computed:{
  // 현재 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
    localDialog:{
        get(){
            return this.value 
        }
    }
 },   
   
    methods: {
        //rules 에러를 저장
        validateField(value, rules) {  
            return rules
              .map(rule => rule(value))
              .filter(error => typeof error === 'string');
        }, 

        //닉네임 중복검증 메소드 
        nickNameValidation(){
                //로딩상태가 진행중이면 return
                if(this.isLoading===true){
                  return;
                }

                //닉네임 검증 에러모음 배열 
                this.nickNameErrors = this.validateField(this.nickname, this.nickNameRules); 

                //만약 닉네임 검증에러가 있을 시   return
                if (this.nickNameErrors.length > 0 ) {
                    
                     return;//불필요한 axios 요청 방지 
                  
                }

                this.isLoading=true;//에러 배열에 데이터가 없으면 로딩상태를 true로
                  
                //서버에서 닉네임 중복검증 진행 
                  api.post('/members/nickValidate',{
                  nickname:this.nickname//닉네임 
                  })
                  .then(()=>{
                    //성공시 
                    alert('사용가능한 닉네임입니다.')
                    this.isNickNameClear =true; //닉네임 중복검증 완료 
                    this.isNickNameReadonly=true;//검증이 완료 되면 닉네임 수정불가
                  
                   })
                  .catch((err)=>{
                    //실패시 서버로부터 받은 에러 출력 
                    if(err?.response?.data?.message){
                        alert(err?.response?.data?.message)  
                    }
                  
                   })
                 .finally(()=>{
                  //현재 로딩상태정보를 false로 
                  this.isLoading=false;
                   })
               
               
                

            
        },
      //현재 컴포넌트 닫는 메서드 
      closeDialog() {
        //변수 초기화
        this.nickname=''
        this.nickNameErrors=[]
        this.isNickNameClear=false
        this.isNickNameReadonly=false
        this.password=''

        this.$emit('changeNickname');// 세팅 컴포넌트로 닫는 이벤트 전송
      },
      //닉네임 변경 메서드
      submitNickChange(){
        if(this.isLoading){
          return
        }
        //만약 닉네임 검증이 진행되지않았다면 return
        if(!this.isNickNameClear){
          alert('닉네임 검증을 진행 해주세요.')
          return
        } 

        this.isLoading=true

        //비밀번호 검증 에러메시지 배열
        this.passwordErrors = this.validateField(this.password, this.passwordRules); 

        if (this.passwordErrors.length > 0 ) { //만약 패스워드 검증에러가 있을 시   return
            this.isLoading=false
            return;//불필요한 axios 요청 방지  
        }

        //비밀번호 validation이 맞다면 
            
        //최종적으로 닉네임 변경 api 호출 ==>서버단에서 현재 세션의 정보와 입력 패스워드가 일치한지 검증 
        api.put("/setting/nickChange",{
          nickname:this.nickname,//닉네임 
          password:this.password//패스워드
        })
        .then((res)=>{
        //성공시 새로운 엑세스 토큰 저장 
        //why? 현재 프로젝트에서 jwt 토큰에 닉네임값도 들어가 있는데 닉네임을 변경할 시 토큰 재발급을 받지않으면 , 이전 닉네임 정보를 
        //파싱해서 사용한다. 즉 이전 닉네임을 통한 데이터 교환이 일어난다.
        const newToken =res?.headers['access'];
        const nickname = res?.headers['nickname']
        localStorage.setItem('access', newToken);
        localStorage.setItem('nickname',nickname)
        eventBus.emit('changeNickname');
          alert('성공적으로 변경되었습니다.')
          //컴포넌트 닫음 
          this.closeDialog()
        })
        .catch((err)=>{
          //실패시 서버로부터 받은 에러메시지 출력
            if(err?.response?.data?.message){
              alert(err?.response?.data?.message)  
            }        
        })
        .finally(()=>{
                  //현재 로딩상태정보를 false로 
                  this.isLoading=false;
        })
       
      },
      cancle(){
        //취소
        this.closeDialog()
      }

    }
  }
  </script>