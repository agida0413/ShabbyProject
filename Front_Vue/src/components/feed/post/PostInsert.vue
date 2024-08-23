<template>
    <!--게시물 상세 모달-->
    <v-dialog v-model="localDialog" max-width="1100" class="modal">
      <v-card class="to-doubleBlack">
        
        
        
        <v-container fluid>
          <v-row no-gutters >
        
            <v-col cols="6" class="image-section">
             
        <!-- 사진 목록-->
     
        <v-carousel v-model="currentPage" show-arrows="hover">
          <div v-if="images.length">
            <v-carousel-item
              width="550px"
              height="500px"
               v-for="(image,index) in images" :key="index"
              :src="image"
              cover
            ></v-carousel-item>
          </div>
    
        </v-carousel>
            </v-col>
  
            <!-- 게시물 상세 오른쪽 정보(내용,댓글 등 ) -->
            <v-col cols="6" class="content-section to-blackMode" >
              <v-icon
                class="close-icon"
                @click="closeDialog"
              >mdi-close</v-icon><!-- x아이콘 - > 모달 닫음-->
  
           
              <v-card-text v-show="!moreReply" ><!-- 아닐시 게시물 내용 ,좋아요, 수정,삭제 , 댓글 하나만 보임 -->
                <!-- Post Information Layout -->
                <v-row no-gutters >
                
                  <v-col cols="12" class=" ">
                    <v-btn
                      class="ma-2"
                      color="primary"
                      @click="triggerFileInput"
                      rounded
                    >
                      <v-icon left>mdi-attachment</v-icon>
                      사진 첨부
                      <input
                        type="file"
                        ref="fileInput"
                        multiple
                        @change="handleFileChange"
                        style="display: none"
                        accept="image/*"
                      />
                    </v-btn>
                    <v-textarea
                      label="게시물 내용"
                      row-height="30"
                      rows="4"
                      variant="filled"
                      auto-grow
                      shaped
                      v-model="content"
                      
                    ></v-textarea>
                  </v-col>
                  <v-col cols="12" class="">
                    <v-textarea
                      label="관심사 (#을 통해 태그할 수 있습니다.)"
                      row-height="30"
                      rows="2"
                      variant="filled"
                      auto-grow
                      shaped
                     @keyup="handleFindInput"
                      v-model="searchHobby"
                    ></v-textarea>
                    <div v-for="(hobbyObj,index) in hobbyResponse" :key="index">
                      <span style="color: aliceblue;">{{ hobbyObj.hobby }}</span>

                    </div>
                  </v-col>
                  <v-col cols="12" class="like-icon-col mt-4">
                    <v-textarea
                      label="인물 태그 (@을 통해 태그할 수 있습니다.)"
                      row-height="30"
                      rows="2"
                      variant="filled"
                      auto-grow
                      shaped
                      
                    ></v-textarea>
                  
                    
                    <v-checkbox v-model="canReplyCheck" label="댓글기능 해제"></v-checkbox>
                    <v-checkbox v-model="onlymeCheck" label="나만보기"></v-checkbox>
                  </v-col>
                  <v-divider></v-divider>
                
                  <span style="margin-left: 425px;">
                    
                    <v-btn  icon="mdi-pencil" size="50" color="black" @click="submitPost()"></v-btn><!--수정 버튼-->
                  </span>
                </v-row>
               
              </v-card-text>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  import api from '@/api';
   export default {
    name: 'PostInsert',
  
    props: {
      value: {//부모로 부터받음 모달열림,닫음 제어 변수
        type: Boolean,
        required: true
      }
    },
    data() {
      return {
        images: [], // 선택한 이미지 파일들의 URL을 저장할 배열
        content:'',//게시물 내용(글)
        currentPage:0 ,//사진 첨부후 처음 인덱스로 돌아가게
        searchHobby:'',//관심사 검색어
        hobbies:[],//관심사 태그 목록
        hobbyResponse:{},//관심사 axios 결과데이터
        isHobbyFetching:false,//현재 관심사가 검색되고 있는지 
        person:'',//사람 태그
        peoples:[],//사람 태그 목록
        canReplyCheck:false,//댓글기능해제 체크박스 
        onlymeCheck:false//나만보기기능 체크박스
      }
    },
    computed: {
      localDialog: {
        get() {
          return this.value;
        }
      }
    },
    methods: {
     async handleFindInput(){
      if(this.searchHobby===''){
        this.hobbyResponse={}
      }
        if (this.isHobbyFetching) {
            return;
        }


        this.isHobbyFetching=true;
          try{
            await this.findHobby()
          }finally{
            this.isHobbyFetching=false;
          }
      },
      handleFileChange(event) {
          const files = event.target.files; // 파일 객체 배열을 가져옵니다.
          const newImages = [];

          // 용량 제한 (예: 5MB)
          const MAX_SIZE_MB = 1;
          const MAX_SIZE_BYTES = MAX_SIZE_MB * 1024 * 1024;

          // 파일 배열을 순회하며 검사 및 읽기 처리
          for (let i = 0; i < files.length; i++) {
            const file = files[i];

            // 용량 제한 검사
            if (file.size > MAX_SIZE_BYTES) {
              alert(`파일 '${file.name}'의 크기가 ${MAX_SIZE_MB}MB를 초과합니다.`);
              return;
            }

            const reader = new FileReader();
            
            // 파일이 읽히면 결과를 newImages 배열에 추가
            reader.onload = (e) => {
                    newImages.push(e.target.result);

                  // 모든 파일이 읽힌 후에만 배열을 업데이트
                  if (newImages.length === files.length) {
                    this.images = newImages;
                    this.currentPage = 0; // 새 파일이 추가되면 페이지를 첫 번째로 설정
                  }
            };

          // 파일을 데이터 URL로 읽기 시작
           reader.readAsDataURL(file);
           }
        },

        async findHobby(){
        
         
          if(this.searchHobby===''||this.searchHobby===null){
            return
          }

          if(this.searchHobby.length<1){
            this.resultHobby=[];
            return;

          }

        
         
            api.get(`/hobby/${this.searchHobby}`)
            .then((res)=>{
              const hobby= res.data.reqData.findList[0].hobby
              console.log(hobby)
              if(hobby!=='noData'){
              this.hobbyResponse=res.data.reqData.findList
              }
             
            })
            .catch(()=>{

              this.resultHobby=[]
            })
          
          
            
              
          
          
       
        },
submitPost(){


},
   triggerFileInput() {
      this.$refs.fileInput.click();
    },
      closeDialog() {
        this.$emit('postInsertClose', false); //상세보기 닫음 
        this.moreReply = false;
      },
      moreReplyOpen() {
        this.moreReply = true;
      },
      moerReplyClose() {
        this.moreReply = false;
      }
    },
    components: {
    
    }
  }
  </script>
  
  <style scoped>
  .modal {
    max-height: 90vh;
    overflow: hidden;
  }
  
  .image-section {
    padding: 0;
    position: relative;
  }
  
  .content-section {
    padding: 16px;
    position: relative;
  }
  
  .close-icon {
    position: absolute;
    top: -10px;
    right: -10px;
    font-size: 30px;
    cursor: pointer;
    z-index: 1; 
  }
  
  .overlay-text {
    position: absolute;
    top: 20px; 
    left: 20px; 
    right: 20px; 
    color: white; 
    padding: 10px;
    border-radius: 4px; 
    font-size: 24px;
    z-index: 2; 
    white-space: normal; 
   
  }
  
  .post-content-col {
    flex: 1;
    padding-right: 16px;
  }
  
  .like-icon-col {
    display: flex;
    align-items: center;
    padding-right: 16px;
    font-size: 14px;
  }
  
  .comment-icon-col {
    display: flex;
    align-items: center;
    font-size: 14px;
  }
  
  .username {
    font-weight: bold;
    display: block;
  }
  
  .post-text {
    width: 520px;
    height: 150px;
    display: block;
    margin-top: 8px;
    padding: 16px;
    background-color: #2C2C2C;
    color: aliceblue;
    border-radius: 4px; 
  }
  
  .hobby-text {
    width: 520px;
    height: 50px;
    display: block;
    margin-top: 8px;
    padding: 16px;
    background-color: #2C2C2C;
    color: aliceblue;
    border-radius: 4px; 
  }
  
  .v-divider {
    margin: 16px 0;
  }

 
  
  </style>
  