<template>
 
<v-dialog activator="parent" v-model="localDialog" max-width="500">
  
          <template v-slot:default="{ isActive }">
              
               <v-progress-linear
                  color="cyan"
                  indeterminate
                  v-if="isLoading"
                ></v-progress-linear>

            <v-card rounded="lg" class="to-blackMode">
              <v-card-title class="d-flex justify-space-between align-center">
                <div class="text-h5 text-medium-emphasis ps-2">
                 <span style="color: white;"> 자기소개 수정</span>
                </div>

                <v-btn  
                  variant="text"
                  @click="isActive.value = false"
                  :disabled="isLoading"
                ></v-btn>
              </v-card-title>

              <v-divider class="mb-4"></v-divider>

              <v-card-text>
             
                  <div class="mb-2">자기소개 수정</div>

                    <v-textarea
                      maxlength="100"
                      class="mb-2"
                      rows="3"
                      variant="outlined"
                      persistent-counter
                      no-resize
                    :disabled="isLoading"
                      v-model="introduce"
                    ></v-textarea>

               

               
              </v-card-text>

              <v-divider class="mt-2"></v-divider>

              <v-card-actions class="my-2 d-flex justify-end">
                <v-btn
                  class="text-none"
                  rounded="xl"
                  text="Cancel"
                  @click="closeDialog()"
                  :disabled="isLoading"
                ></v-btn>

                <v-btn
                  class="text-none"
                  color="primary"
                  rounded="xl"
                  text="Send"
                  variant="flat"
                  @click="UpdateIntroduceSubmit()"
                  :disabled="isLoading"
                ></v-btn>
              </v-card-actions>
            </v-card>
          </template>
        </v-dialog>

</template>

<script>
import api from "@/api"
export default {
  name: 'FeedEdit',

  props: {
    value: {//유저피드로부터  받은 모달 불리안값 
      type: Boolean,
      required: true
    }
  
},data(){
  return{
     originalIntroduce:'',//기존 자기소개 
     introduce:'', // 변경될 자기소개 
     isLoading:false  //데이터 로드 여부 
  }
}
,computed:{
  localDialog:{
      get(){
          return this.value // 현재 아이디찾기 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
      }
  }
},
watch: {
  //모달 열림,닫힘 감지 
    value(newVal) {
      //모달이 열렸으면 
      if(newVal===true){
        //기존 자기소개를 가져옴 
        this.getOriginalIntroduce()
      }
      
    }
  },
mounted() {
  
},
  methods: {
    closeDialog() {
      this.$emit('feedEditClose');// 로그인 컴포넌트로 닫는 이벤트 전송
    },
    //기존 자기소개를 가져오는 메소드 
    getOriginalIntroduce(){
    //데이터 로드중이면 리턴 
    if(this.isLoading===true){
      return
    }
    //데이터 로드 
    this.isLoading=true
    // 기존 자기소개를 가져오는 API 
    api.get('/feed/introduce')
    .then((res)=>{
      //결과값 저장 
      this.introduce=res?.data?.reqData?.introduce
      this.originalIntroduce=res?.data?.reqData?.introduce    

    })
    .catch((err)=>{
      alert(err?.response?.data?.message)
      this.closeDialog()
    })
    .finally(()=>{

      this.isLoading=false
    })
   },
   //자기소개 변경 
   UpdateIntroduceSubmit(){
    
    // 기존 자기소개 와 동일하게 변경시도 시  리턴 
    if(this.introduce===this.originalIntroduce){
      alert('이전 자기소개와 동일합니다.')
      return
    }
    //변경할 자기소개가 ''일시 NULL로 지정 
    if(this.introduce===''){
      this.introduce=null
    }
      //전송중     
      this.isLoading=true;
      
      api.put('/feed/introduce',{
       introduce:this.introduce
      })
      .then(()=>{
        alert('성공적으로 변경되었습니다.') 
      })
      .catch((err)=>{
        alert(err?.response?.data?.message)
      })
      .finally(()=>{
        this.isLoading=false
        this.closeDialog()
      })

   } 
  }
}
</script>

<style>

</style>