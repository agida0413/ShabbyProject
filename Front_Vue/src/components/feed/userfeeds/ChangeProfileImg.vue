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
                      :disabled="isLoading || resizeLoading"
                      @click:clear="imageToNull"
                    ></v-file-input> 
                    <v-progress-linear
                  color="cyan"
                  indeterminate
                  v-if="resizeLoading"
                ></v-progress-linear>
                   
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
                       :disabled="isLoading ||resizeLoading"
                    ></v-btn>
    
                    <v-btn
                      class="text-none"
                      color="primary"
                      rounded="xl"
                      text="Send"
                      variant="flat"
                      @click="changeProfile()"
                      :disabled="isLoading || resizeLoading"
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
        isLoading:false ,
        fileSize:0,
        resizeLoading:false    
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
          this.fileSize=0
        },
        closeDialog() {
          this.profileImgFile=null
          this.fileSize=0
          this.isLoading=false
          this.resizeLoading=false
          this.$emit('changeProfileImgClose');
        },
         //사진 업로드 핸들러 
         handleFileEvent(event){
          this.resizeLoading=true;
          const file= event.target.files[0]
          if(!file){
            this.profileImgFile = null;
            this.fileSize=0
            this.resizeLoading=false
            return;
          } 
          // 파일 크기 제한 
         
    
                const reader = new FileReader(); // 파일 읽기 객체 생성

              reader.onload = (e) => {
                
                const img = new Image(); // 이미지 객체 생성
                img.src = e.target.result; // 파일 데이터를 이미지 소스로 설정

                img.onload = () => {
                  const canvas = document.createElement('canvas'); // 캔버스 생성
                  const ctx = canvas.getContext('2d'); // 2D 컨텍스트 가져옴
                  const originalWidth = img.width;
                  const originalHeight = img.height;
                  const targetWidth = 550; // 리사이즈할 너비
                  const targetHeight = Math.round((originalHeight / originalWidth) * targetWidth); // 비율 유지v
                  canvas.width = targetWidth; 
                  canvas.height = targetHeight;
                  ctx.imageSmoothingEnabled = true; // 이미지 스무딩 활성화
                  ctx.imageSmoothingQuality = 'high'; // 높은 품질로 설정
                  ctx.drawImage(img, 0, 0, targetWidth, targetHeight); // 이미지 그리기
                

                  canvas.toBlob((blob) => { // 캔버스를 Blob으로 변환
                    if (blob) {
                      const resizedFile = new File([blob], file.name, { type: 'image/jpg' }); // 새 파일 생성
                    
                      this.profileImgFile = resizedFile;
                      this.fileSize=resizedFile.size
                      this.resizeLoading=false
                      
                    } else {
                      console.error('Blob 생성 실패');
                      this.resizeLoading=false
                    }
                  }, 'image/jpg'); // Blob 형식 설정
                };

                img.onerror = () => {
                  this.resizeLoading=false
                  console.error('이미지 로드 실패');
                };
              };

              reader.onerror = () => {
                this.resizeLoading=false
                console.error('파일 읽기 실패');
              };

              reader.readAsDataURL(file); // 파일 읽기 시작
                        // 파일을 data 속성에 저장
                      
                  
     },
      
       //사진 변경 API 
       changeProfile(){

        const MAX_SIZE_MB = 2;
        const MAX_SIZE_BYTES = MAX_SIZE_MB * 1024 * 1024; // 최대 파일 크기 (바이트)
          if(this.resizeLoading){
            alert('이미지 재조정 중입니다.')
            return
          }
          //만약 파일크기가 5mb를 초과하면 메서드 종료 
          if (this.fileSize > MAX_SIZE_BYTES) {
           
            alert(`변경할 프로필 사진이 ${MAX_SIZE_MB}MB를 초과합니다. 다른사진을 선택해 주세요.`);
            return;
          }
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