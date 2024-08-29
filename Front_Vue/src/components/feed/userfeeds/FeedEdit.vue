<template>

<v-dialog activator="parent" v-model="localDialog" max-width="500">
          <template v-slot:default="{ isActive }">
            <v-card rounded="lg" class="to-blackMode">
              <v-card-title class="d-flex justify-space-between align-center">
                <div class="text-h5 text-medium-emphasis ps-2">
                 <span style="color: white;"> 자기소개 수정</span>
                </div>

                <v-btn  
                  variant="text"
                  @click="isActive.value = false"
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
                 placeholder="변경할 자기소개 입력"
                  v-model="UpdateIntroduce"
                ></v-textarea>

               

               
              </v-card-text>

              <v-divider class="mt-2"></v-divider>

              <v-card-actions class="my-2 d-flex justify-end">
                <v-btn
                  class="text-none"
                  rounded="xl"
                  text="Cancel"
                  @click="closeDialog()"
                ></v-btn>

                <v-btn
                  class="text-none"
                  color="primary"
                  rounded="xl"
                  text="Send"
                  variant="flat"
                  @click="UpdateIntroduceSubmit()"
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
    
    
     isLoading:false 

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
      this.$emit('feedEditClose');// 로그인 컴포넌트로 닫는 이벤트 전송
    },
  
   UpdateIntroduceSubmit(){
    
     
      if(this.introduce===this.UpdateIntroduce){
        alert('이전 자기소개와 동일합니다.')
        return
      }

      api.put('/feed/introduce',{
       introduce:this.UpdateIntroduce
      })
      .then(()=>{
        alert('성공적으로 변경되었습니다.')
        this.closeDialog()
      })
      .catch((err)=>{
        alert(err?.response?.data?.message)
      })
      

   } 
  }
}
</script>

<style>

</style>