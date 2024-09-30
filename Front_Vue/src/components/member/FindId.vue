<template>
   
   <!-- 아이디 찾기 모달-->
   
   <v-dialog v-model="localDialog"  max-width="500" >
      
      <v-card  
        prepend-icon="mdi-account"
        title="이메일 찾기"
        class="pa-3"   
      >

      <v-divider></v-divider>
        <v-progress-linear
          color="black"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>
    <v-card-text class="mt-6" v-show="isAuth" >

      <table width="400px" style="border:1px #D3D3D3 solid;">
        <tr >
          <th style="border-bottom: 1px #D3D3D3 solid;">이메일</th>
          <th style="border-bottom: 1px #D3D3D3 solid;">생성일 </th>
        </tr>
        
        <tr class="text-center" >
          <td><span style="font-weight: bold; "> {{ findEmail }} </span></td>
          <td><span style=" opacity: 0.7; ">{{ findCreateDate }}</span></td>
        </tr>
      </table>

 
    </v-card-text>
           <v-card-text class="mt-6" v-show="!isAuth">
              <div class="text-subtitle-1 text-medium-emphasis">닉네임 </div>
                 <v-row>
                      <v-col cols="8">
                            <v-text-field 
                               density="compact"
                               placeholder="닉네임"
                               prepend-inner-icon="mdi-account"
                               variant="outlined"
                               v-model="nickname"
                              :disabled="isLoading"
                               :rules="nickNameRules"
                               required
                               :error-messages="nickNameErrors"
                            ></v-text-field>
                      </v-col>
                 </v-row>

        <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">이름</div>
   
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
                   :disabled="isLoading"
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
                  :disabled="isLoading"   
                ></v-select>
           </v-col>

            <v-col cols="4">             
                  <v-text-field               
                    type="text"
                    density="compact"
                    maxlength="4" 
                    v-model="middlePhoneNum"
                    variant="outlined"      
                    :disabled="isLoading"   
                  ></v-text-field>
            </v-col>

              <v-col cols="4">
                  <v-text-field
                    maxlength="4" 
                    type="text"
                    density="compact"
                    variant="outlined"
                    v-model="lastPhoneNum"
                    :disabled="isLoading"
                  ></v-text-field>
              </v-col>

        </v-row>
         
  </v-card-text>

    
        <v-card-actions>

          <v-spacer></v-spacer>
          
          <v-btn
            text="Close"
            variant="plain"
            :disabled="isLoading"
            @click=closeDialog()> 
          </v-btn>

          <v-btn
          v-show="!isAuth"
            color="primary"
            text="Submit"
            variant="tonal"
            :disabled="isLoading"
            @click=sumbmitFindId()
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  import api from '@/api';
  export default {
    name: 'FindIdComponent',
  

 props: {
      //현재 컴포넌트의 열리고 닫힘을 제어 
      value: {
        type: Boolean,
        required: true
      }
    
},
data(){
    return{
        items:['010','011','016','017','019'],// 우리나라 앞번호 종류 리스트 
        firstPhoneNum:'010', // 초기값 010 
        middlePhoneNum:'',//두번째 핸드폰 번호값
        lastPhoneNum:'',//마지막 핸드폰 번호값
        name:'',//이름
        nickname:'',//닉네임
        findEmail:'',//찾은 이메일
        findCreateDate:'',
        isAuth: false, //정보를 입력한값이 true이면 찾은아이디 목록을 보여주기 위해 아이디 찾기 정보 입력폼을 숨기고 아이디를 보여주기 위함이다.                
        isLoading:false,//로딩상태
        //규칙 변수 
         nickNameRules:[
         v => !!v || '닉네임을 입력해 주세요.', // 닉네임이 입력되지 않았을 경우
         v => v.trim().length > 0 || '닉네임은 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
         v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
        //  v => !/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(v) || '닉네임에는 한글을 사용할 수 없습니다.', // 한글 사용 금지 = > 아이디 찾기에서는 비활성화
        //  v => /^[a-zA-Z0-9_ ]+$/.test(v) || '닉네임에는 _를 제외한 특수문자를 사용할 수 없습니다.', // 특수문자 검증 아이디 찾기에서는 비활성화 
        //  v => v.length <= 15 || '닉네임은 최대 15자까지 입력할 수 있습니다.', // 최대 길이 검증  아이디 찾기에서는 비활성화 
            
         ],
        nameRules:[
        v => !!v || '이름을 입력해 주세요.', // 닉네임이 입력되지 않았을 경우
        v => v.trim().length > 0 || '이름은 공백을 포함할 수 없습니다.', // 공백이 포함된 경우
        v => !/\s/.test(v) || '공백을 사용할 수 없습니다.', // 공백 불가
        ],  
        //에러모음
        nameErrors:[],
        nickNameErrors:[],
      }
  },
  computed:{
    localDialog:{
        get(){
            return this.value // 현재 아이디찾기 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
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
      //모달을 닫는 메소드
      closeDialog() {
        //초기화
        this.isAuth=false;//다시 로그인찾기를 해도 처음부터 입력할수 있도록  초기값 설정 
        this.nickname='';//닉네임 
        this.name=''; //실명 
        this.firstPhoneNum='010' //핸드폰 번호 첫3자리 
        this.middlePhoneNum='' //두번째
        this.lastPhoneNum='' //세번째 
        this.$emit('findIdClose');// 로그인 컴포넌트로 닫는 이벤트 전송
      
      },
      //이메일 찾기 메소드 
      sumbmitFindId(){
        //현재 로딩값이 true이면 return
        if(this.isLoading===true){
            return
        }

        this.nameErrors = this.validateField(this.name, this.nameRules); //이름검증
        this.nickNameErrors = this.validateField(this.nickname, this.nickNameRules); //닉네임검증

        if (this.nameErrors.length > 0 || this.nickNameErrors.length>0  ) { //이중 하나라도 충족하지않으면 리턴 
        
            return;//불필요한 axios 요청 방지  
        }

        //전체 핸드폰번호 
        const fullPhoneNum= (this.firstPhoneNum+this.middlePhoneNum+this.lastPhoneNum); 

                //전체 휴대폰번호에 대한 validation
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

              //현재 로딩상태 참 
              this.isLoading=true;    

              //이메일 찾기 api 전송
              api.post('/members/findEmail',{
                name:this.name,//이름
                nickname:this.nickname,//닉네임
                phone:fullPhoneNum//휴대폰 번호
              })
              .then((res)=>{  
                //성공시
                  this.findEmail=res?.data?.reqData?.email;//응답객체에서 이메일정보를 가져옴 
                  this.findCreateDate=res?.data?.reqData?.vueCreateDate//응답객체에서 생성일을 가져옴 
                  this.isAuth=true; // 아이디를 보여주는 변수 
              })
              .catch((err)=>{
                //실패시 서버로부터 받은 메시지 출력 
                if(err?.response?.data?.message){
                  alert(err?.response?.data?.message)  
                  }
                    
              })
              .finally(()=>{
                this.isLoading=false; //현재 로딩상태 false
              })

      }
    }
  }
  </script>