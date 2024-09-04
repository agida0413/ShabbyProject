<template  >
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
                <v-row cols="12" class="ml-1">
                  <v-col cols="1"  style="cursor:pointer" @click="goOtherUserfeed(postDetailData.nickname)">
                    <v-avatar :image="postDetailData.profile" size="35" class="avatar"
                    v-if="postDetailData.profile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="35" class="avatar" v-if="postDetailData.profile === null" ></v-avatar>
                  </v-col>
                  <v-col cols="5" class="mt-1 ml-1" >
                    <span class="large-font " style="cursor:pointer" @click="goOtherUserfeed(postDetailData.nickname)">{{postDetailData.nickname }}</span>
                  </v-col>
                  <v-col cols="5" class="mt-2" v-if="postDetailData.editDate===null">
                    <span  style="color: white; opacity: 0.7;">작성일:&nbsp;&nbsp;{{postDetailData.createDate }}</span>
                  </v-col>

                  <v-col cols="5" class="mt-2" v-if="postDetailData.editDate!==null">
                    <span  style="color: white; opacity: 0.7;">수정일:&nbsp;&nbsp;{{postDetailData.editDate }}</span>
                  </v-col>
                </v-row>
              <v-divider></v-divider>
                <v-col cols="12" style="height: 100px;" class="mt-1">
                  <v-card class="to-blackMode"  elevation="1" max-width="100%" height="100%" >
   
                    <v-card-text v-if="postDetailData.content!==null">
                  
                     {{ postDetailData.content }}
                    </v-card-text>

                    <v-card-text v-if="postDetailData.content===null">
                      등록된 게시물 내용이 없습니다.
                    </v-card-text>
                  </v-card>

                </v-col>
              
                <v-col cols="12" class="mt-1" style="height: 130px;"  >
                  <v-card class="to-blackMode scroll-container" elevation="1" max-width="100%" height="100%" >
   
                    <v-card-item>
                     
                      <v-card-subtitle>관심사</v-card-subtitle>
                    </v-card-item>     
                     <v-card-text v-if="postDetailData.hobbyList!==null" >  
                      <span   v-for="(hobby,index) in postDetailData.hobbyList" :key="index">
                        <v-chip
                          class="ma-2 clickcursor"
                          color="green"
                          label                                                
                          >
                           <v-icon icon="mdi-label " start></v-icon>
                          {{ hobby }}
                        </v-chip>
                      </span> 
                  </v-card-text>
                    <v-card-text v-if="postDetailData.hobbyList===null">
                      등록된 관심사가 없습니다.
                    </v-card-text>
                  </v-card>

                </v-col>
                
                <v-col cols="12" class="mt-2" style="height: 100px;">
                  <v-card class="to-blackMode  scroll-container" elevation="1" max-width="100%" height="100%" >
   
                    <v-card-item>
                     
                      <v-card-subtitle>관련인물</v-card-subtitle>
                    </v-card-item>                 

                    <v-card-text v-if="postDetailData.tagList!==null" >           
                      <span  v-for="(tag,index) in postDetailData.tagList" :key="index">  
                  
                        <v-chip
                          class="ma-2 clickcursor"
                          color="blue"
                          label  
                          @click="goOtherUserfeed(tag)"                        
                          >
                           <v-icon icon="mdi-account-circle-outline clickcursor" start></v-icon>
                          {{ tag }}
                        </v-chip>
                    
                      </span>
                  </v-card-text>

                    <v-card-text v-if="postDetailData.tagList===null">
                     태그한 인물이 없습니다.
                    </v-card-text>
                  </v-card>

                </v-col>
                <v-divider style="color: aliceblue;"></v-divider>
                <v-col cols="12" class="like-icon-col  to-blackMode">
                  <v-icon
                    style="cursor: pointer;"
                    :class="{'red--text': postDetailData.liked, 'grey--text': !postDetailData.liked}"
                    large
                    size="30"
                    :color="postDetailData.liked?'red':''"
                    @click="doPostLike()"
                  >
                    {{ postDetailData.liked ? 'mdi-heart' : 'mdi-heart-outline' }}
                  </v-icon>
                  <span style="margin-left: 20px; font-size: 16px;">{{postDetailData.likeCount}}명이 좋아합니다.</span>
                 
                  <span style="margin-left: 200px;" v-if="postDetailData.itsMe">
                    <v-btn class="ms-5" icon="mdi-pencil" size="30" color="black" @click="postEditOpen"></v-btn><!--수정 버튼-->
                  </span>
                  
                  <span style="margin-left: 8px;"  v-if="postDetailData.itsMe">
                  <v-btn icon="mdi-delete-outline"  color="red" size="30"
                  @click="openAlert('정말 이 게시물을 삭제하시겠습니까?','postDel')"
                  ></v-btn> <!--삭제 버튼-->
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
  <Alert
      v-model:value="isAlertOpen"
      v-model:altype="alertType"
      v-model:message="alertMessage"
      @closeAlertDialog="closeAlert"
    >
  </Alert>
</template>

<script>
import Alert from "@/components/common/utill/Alert.vue"
import PostEdit from "./PostEdit.vue"
import api from "@/api";
import eventBus from "@/eventBus.js"
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
      imgList:[], //사진 목록 
      isLoading:false, //게시물 상세 데이터 로딩상태
      isLikeLoading:false, //좋아요 버튼 데이터 로딩상태 (스켈레톤요소 떄문에 따로 분리 )
      alertType:'', // 알림창을 띄울때 어떤 타입인지(게시물 삭제 타입 )
      isAlertOpen:false,//알림창모달의 띄움 여부 
      alertMessage:'' ,//알림창모달에 전달할 메시지
     
      
    }
  },
  computed: {
    //모달 다이얼로그 open,close 
    localDialog: {
      get() {
        return this.value;
      }
    }
  },
  //감지
  watch:{
    //게시물 번호 가 변경되면 
    postNum(newValue){
      
      if(newValue!=0){
        //새롭게 데이터 호출 
        this.callPostDetailData(newValue)
      }
     
    }
  },
  methods: {
    
    //상세 데이터를 불러옴 
    callPostDetailData(newValue){
      //로딩중일때 는 return 
      if(this.isLoading)return
      //로딩중 true
      this.isLoading=true

      api.get(`post/${newValue}`)
      .then((res)=>{
        //이미지 목록 
        this.imgList=res?.data?.reqData?.imgList
        //나머지 데이터  
        this.postDetailData=res?.data?.reqData
       
      
      })
      .catch((err)=>{
        alert(err?.response?.data?.message)
        this.closeDialog()
      })
      .finally(()=>{
        this.isLoading=false
      })
    },
    //좋아요 작업 
    doPostLike(){
      //좋아요 작업중이면 return (중복호출 방지 )
      if(this.isLikeLoading)return
      //로딩상태 true
      this.isLikeLoading=true
      api.post('/post/like',{
        //게시물 번호
        postNum:this.postDetailData.postNum,
        //현재 로그인한 회원기준 좋아요 상태(눌럿나 안눌럿나)
        liked:this.postDetailData.liked

      })
      .then((res)=>{
        //트랜잭션 완료 후 좋아요 상태 저장 = >  새롭게 dom업데이트 
        const resLiked=res?.data?.reqData?.liked;
        //트랜잭션 완료 후 좋아요 수 저장 
        const resLikeCount= res?.data?.reqData?.likeCount;
        this.postDetailData.liked=resLiked
        this.postDetailData.likeCount=resLikeCount 

      })
      .catch((err)=>{
        alert(err?.response?.data?.message)
        })
        .finally(()=>{
          this.isLikeLoading=false
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
      
    },

    openAlert(message,type){

    this.alertMessage=message //매개변수 메시지 값을 담음 ,자식컴포넌트에 전달
    this.alertType=type//매개변수 타입 값을 담음, 자식컴포넌트에 전달

    this.isAlertOpen=true //경고창 띄우는 변수 
  },

  closeAlert(isOk,type){//ok or no 변수 , 띄운 경고창의 타입 (비공개여부변경, 회원탈퇴 등)
        
        //ok버튼을 클릭하였고 , 비공개 /공개 여부 변경 경고창 이면 (type에 따라)       
        if (isOk&&type==='postDel') {
          if(this.isLoading)return
          this.isLoading=true
          api.delete('/post',{
            data:{
              postNum:this.postNum
            }
          })
          .then(()=>{
         
            alert('성공적으로 삭제가 완료되었습니다.')
            eventBus.emit('resetPostList');
          })
          .catch((err)=>{
            alert(err?.response?.data?.message)
          })
          .finally(()=>{
            this.isLoading=false
            this.closeDialog()
          })
       
        }

        //ok를 클릭하였고 , 회원탈퇴 경고 이면 
        if(!isOk&&type==="postDel"){
           
          this.isAlertOpen=false//경고창을 닫음 

        }

        this.isAlertOpen=false//그외 no 버튼을 클릭 시 경고창을 닫음 
       },
    //클릭시 해당 유저의 피드로 이동 
    goOtherUserfeed(nickname) {//닉네임 매개변수 

      this.closeDialog(); // 모달 닫기 = > 팔로우/팔로잉 목록 모달 
      this.$nextTick(() => {
        this.$router.push({ name: 'userfeed', params: { nickname: nickname } }); // 페이지 이동 ( 닉네임 param)
      });
    }
  
  },
  components: {
    PostEdit,
    Alert
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
.scroll-container {

  overflow-y: auto; /* 세로 스크롤 추가 */
}
/* 스크롤바 전체 영역 */
.scroll-container::-webkit-scrollbar {
  width: 12px; /* 스크롤바의 너비 */
}

/* 스크롤바의 트랙 (스크롤바가 없을 때 배경 영역) */
.scroll-container::-webkit-scrollbar-track {
  background: #f1f1f1; /* 트랙의 배경색 */
  border-radius: 10px; /* 트랙의 모서리 둥글기 */
}

/* 스크롤바의 막대 (드래그 가능한 부분) */
.scroll-container::-webkit-scrollbar-thumb {
  background: #888; /* 막대의 배경색 */
  border-radius: 10px; /* 막대의 모서리 둥글기 */
}

/* 스크롤바의 막대가 호버될 때 */
.scroll-container::-webkit-scrollbar-thumb:hover {
  background: #555; /* 막대의 호버 배경색 */
}
.clickcursor{
cursor: pointer;
}
.large-font {
  font-size: 18px; /* 원하는 폰트 사이즈로 조정 */
  color:antiquewhite
}
</style>
@/eventBus.js