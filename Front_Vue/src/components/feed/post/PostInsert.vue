<template>
    <!--게시물 상세 모달-->
    <v-dialog v-model="localDialog" max-width="1100" class="modal">
      <v-card class="to-doubleBlack">
        
        
        
        <v-container fluid>
          <v-row no-gutters >
        
            <v-col cols="6" class="image-section">
             
        <!-- 사진 목록-->
        <v-carousel show-arrows="hover">
      <v-carousel-item
        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
        cover
      ></v-carousel-item>
  
      <v-carousel-item
        src="https://cdn.vuetifyjs.com/images/cards/hotel.jpg"
        cover
      ></v-carousel-item>
  
      <v-carousel-item
        src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
        cover
      ></v-carousel-item>
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
                  <!-- Post Content (50%) -->
                  <v-col cols="12" class=" ">
                    <v-file-input
                      label="사진을 첨부해 주세요."
                      multiple
                    ></v-file-input>
                    <v-textarea
                      label="게시물 내용"
                      row-height="30"
                      rows="4"
                      variant="filled"
                      auto-grow
                      shaped
                      
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
                      
                    ></v-textarea>
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
                  
                    
                    <v-checkbox label="댓글기능 해제"></v-checkbox>
                    <v-checkbox label="나만보기"></v-checkbox>
                  </v-col>
                  <v-divider></v-divider>
                
                  <span style="margin-left: 425px;">
                    
                    <v-btn  icon="mdi-pencil" size="50" color="black"></v-btn><!--수정 버튼-->
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
  