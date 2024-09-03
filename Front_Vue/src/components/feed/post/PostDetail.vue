<template>
  <!--게시물 상세 모달-->
  <v-dialog v-model="localDialog" max-width="1100" class="modal">
    <v-row v-if="isLoading" class="loading-overlay">
      <v-col class="d-flex justify-center align-center">
        <v-progress-circular indeterminate color="primary"></v-progress-circular>
      </v-col>
    </v-row>
    <v-card class="to-doubleBlack" v-if="!isLoading">
      
      
      
      <v-container fluid >
        <v-row no-gutters >
      
          
          <v-col cols="6" class="image-section">
       
      <!-- 사진 목록-->
      <v-carousel show-arrows="hover" >
        <div  v-for="(image,index) in imgList" :key="index">
          <v-carousel-item
             width="550px"
             height="500px"
             style="object-fit: contain;"
            :src="image"
            cover
          ></v-carousel-item>

        </div>
      </v-carousel>
          </v-col >

          <!-- 게시물 상세 오른쪽 정보(내용,댓글 등 ) -->
          <v-col cols="6" class="content-section " >
            <v-icon
              class="close-icon"
              @click="closeDialog"
              color="white"
            >mdi-close</v-icon><!-- x아이콘 - > 모달 닫음-->

           
            <v-card-text  ><!-- 아닐시 게시물 내용 ,좋아요, 수정,삭제 , 댓글 하나만 보임 -->
              <!-- Post Information Layout -->
              <v-row no-gutters >
                <!-- Post Content (50%) -->
                <v-col cols="12" style="height: 150px;" class="mt-1">
                  <v-card class="to-blackMode"  elevation="1" max-width="100%" height="100%" >
   
                    <v-card-text v-if="postDetailData.content!==null">
                     {{ postDetailData.content }}
                    </v-card-text>

                    <v-card-text v-if="postDetailData.content===null">
                      등록된 게시물 내용이 없습니다.
                    </v-card-text>
                  </v-card>

                </v-col>
              
                <v-col cols="12" class="mt-1" style="height: 150px;">
                  <v-card class="to-blackMode" elevation="1" max-width="100%" height="100%" >
   
                    <v-card-item>
                     
                      <v-card-subtitle>관심사</v-card-subtitle>
                    </v-card-item>                 

                    <v-card-text >
                      등록된 관심사가 없습니다.
                    </v-card-text>
                  </v-card>

                </v-col>
                
                <v-col cols="12" class="mt-2">
                  <v-card class="to-blackMode" elevation="1" max-width="100%" height="100%" >
   
                    <v-card-item>
                     
                      <v-card-subtitle>관련인물</v-card-subtitle>
                    </v-card-item>                 

                    <v-card-text >
                     태그한 인물이 없습니다.
                    </v-card-text>
                  </v-card>

                </v-col>
                <v-divider style="color: aliceblue;"></v-divider>
                <v-col cols="12" class="like-icon-col  to-blackMode">
                  <span ><v-icon size="40">mdi-heart</v-icon></span>
                  <span style="margin-left: 20px;">1212명이 좋아합니다.</span>
                 
                  <span style="margin-left: 213px;">
                    <v-btn class="ms-5" icon="mdi-pencil" size="30" color="black" @click="postEditOpen"></v-btn><!--수정 버튼-->
                  </span>
                  
                  <span style="margin-left: 8px;">
                  <v-btn icon="mdi-delete-outline"  color="red" size="30"></v-btn> <!--삭제 버튼-->
                </span>
                </v-col>
                
               
              </v-row>
             
            </v-card-text>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-dialog>
  <PostEdit v-model:value="postEditDialog" @postEditClose="postEditClose"></PostEdit>
</template>

<script>

import PostEdit from "./PostEdit.vue"
import api from "@/api";
export default {
  name: 'PostDetail',

  props: {
    value: {//부모로 부터받음 모달열림,닫음 제어 변수
      type: Boolean,
      required: true
    },
    postNum:{
      type:Number,
      required: true
    }
  },
  data() {
    return {
      postEditDialog:false, //게시물 수정
      postDetailData:{},//상세 데이터 
      imgList:[],
      isLoading:false
    }
  },
  computed: {
    localDialog: {
      get() {
        return this.value;
      }
    }
  },
  watch:{
   
    postNum(newValue){
      if(newValue!=0){
        this.callPostDetailData(newValue)
      }
     
    }
  },
  methods: {

    callPostDetailData(newValue){
      if(this.isLoading)return

      this.isLoading=true
      api.get(`post/${newValue}`)
      .then((res)=>{
        
        this.postDetailData=res?.data?.reqData
        this.imgList=res?.data?.reqData?.imgList
      
      })
      .catch((err)=>{
        alert(err?.response?.data?.message)
      })
      .finally(()=>{
        this.isLoading=false
      })
    },
    closeDialog() {
      this.$emit('postDetailClose', false); //상세보기 닫음 
    },
    postEditOpen(){
      this.postEditDialog=true;
    },
    postEditClose(){
      this.postEditDialog=false;
      
    }
  },
  components: {
    PostEdit
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
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.7) !important;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* 스피너를 최상위에 표시 */
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
