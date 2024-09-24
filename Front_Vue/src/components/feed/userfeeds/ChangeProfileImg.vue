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
                     <span style="color: white;"> 프로필 사진 변경</span>
                    </div>
    
                    <v-btn  
                      variant="text"
                      @click="isActive.value = false"
                      :disabled="isLoading"
                    ></v-btn>

                  </v-card-title>
    
                  <v-divider class="mb-4"></v-divider>
    
                  <v-card-text>
                    <v-file-input
                      label="프로필 사진 변경"
                      prepend-icon="mdi-camera"
                      variant="filled"
                      accept="image/*"
                      @change="handleFileEvent"
                      :disabled="isLoading"
                      @click:clear="imageToNull"
                    ></v-file-input> 
    
                   
                        <div class="info-text">
                            ⚠️ 아무 사진도 선택하지 않고 수정을 하면 기본 이미지로 변경됩니다.
                        </div>
                                
                   
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
                      @click="changeProfile()"
                      :disabled="isLoading"
                    ></v-btn>
                  </v-card-actions>
                </v-card>
              </template>
            </v-dialog>
    
    </template>
    
    <script>
import api from '@/api';

    export default {
      name: 'ChangeProfileImg',
    
    
      props: {
        value: {//유저피드로부터  받은 모달 불리안값 
          type: Boolean,
          required: true
        }
      
    },data(){
      return{
        profileImgFile:null, //파일 객체 
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
        // 해당 컴포넌트를 닫는 메소드 
        imageToNull(){
          this.profileImgFile=null
        },
        closeDialog() {
          this.profileImgFile=null,
          this.isLoading=false
          this.$emit('changeProfileImgClose');
        },
        //사진 업로드 핸들러 
         handleFileEvent(event){
          const file= event.target.files[0]
          if(!file)return;
          // 파일 크기 제한 
          const MAX_SIZE_MB = 5;
          const MAX_SIZE_BYTES = MAX_SIZE_MB * 1024 * 1024; // 최대 파일 크기 (바이트)
    
          //만약 파일크기가 5mb를 초과하면 메서드 종료 
          if (file.size > MAX_SIZE_BYTES) {
            alert(`파일 '${file.name}'의 크기가 ${MAX_SIZE_MB}MB를 초과합니다.`);
            return;
          }
    
           // 파일을 data 속성에 저장
           this.profileImgFile = file;
    
       },
       //사진 변경 API 
       changeProfile(){
        //통신 중이면 리턴
        if(this.isLoading){
        return
        }
        //파일 객체 FORMDATA에 담음 
       const formData= new FormData()
       formData.append('profileImgFile',this.profileImgFile)
       //로딩중 
       this.isLoading=true;
       //API 호출 
       api.put('/feed/userfeed',formData)
       .then(()=>{
           alert('성공적으로 프로필 사진이 변경되었습니다.')
           this.closeDialog()
       })
       .catch((err)=>{
        if(err?.response?.data?.message){
        alert(err?.response?.data?.message)  
        }
       })
       .finally(()=>{
        this.isLoading=false
       })
       } 
      }
    }
    </script>
    
 <style>
.info-text {
  color: grey; 
  font-size: 0.875rem; 
  margin-top: 8px; 
  text-align: center;
}
 </style>