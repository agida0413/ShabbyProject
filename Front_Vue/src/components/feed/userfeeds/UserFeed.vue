<template>
  
        <!-- 로딩 상태일 때 스피너만 보이도록 -->
    <v-row v-if="isLoading || childIsLoading" class="loading-overlay">
      <v-col class="d-flex justify-center align-center">
        <v-progress-circular indeterminate color="primary"></v-progress-circular>
      </v-col>
    </v-row>

    <!-- 로딩 상태가 아닐 때만 나머지 콘텐츠가 보이도록 -->
    <v-row v-else >
      <!-- 프로필 사진 -->
      <v-col cols="3">
        <div class="avatar-wrapper">
          <v-avatar
            :image="userFeedData.profile"
            size="300"
            class="avatar"
            v-if="userFeedData.profile !== null"
          ></v-avatar>

          <v-avatar
            :image="require('@/assets/ikmyung.png')"
            size="300"
            class="avatar"
            v-if="userFeedData.profile === null"
          ></v-avatar>
          <button 
          v-if="userFeedData.itsMe"
          @click="changeProfileImgOpen"
          >
            <v-icon class="edit-icon">mdi-pencil</v-icon>
          </button>
        </div>
      </v-col>

      <v-col cols="3">
        <v-col cols="auto">
          <!-- 팔로우, 언팔로우 버튼 -->
          
         

             <!--내 계정이 아닐 경우 -->
             <v-row v-if="!userFeedData.itsMe">
               <!-- 공개 계정일 경우 -->
                <v-col v-if="userFeedData.locked==='PUBLICID'">
                      
                       <!--팔로우 상태가 아닌경우-->
                    <v-btn elevation="16" width="300" color="primary" v-if="userFeedData.followState==='isNotFollow'"
                    @click="doFollow()"
                    >Follow</v-btn>
                     <!--팔로우 상태인 경우 -->
                    <v-btn elevation="16" width="300" color="red" v-if="userFeedData.followState==='alreadyFollow'"
                    @click="unFollow()"
                    >UNFollow</v-btn>
                </v-col>

                  <!-- 비공개 계정일 경우 -->
                  <v-col v-if="userFeedData.locked==='LOCKED'">
                    <!--팔로우 상태가 아닌경우-->
                    <v-btn elevation="16" width="300" color="primary" v-if="userFeedData.followState==='isNotFollow'"
                      @click="doFollow()"
                    >Follow</v-btn>
                    <!--해당계정 팔로우 상태가 OK인경우-->
                    <v-btn elevation="16" width="300" color="red" v-if="userFeedData.followState==='alreadyFollow'"
                    @click="unFollow()"
                    >UNFollow</v-btn>
                    <!--해당계정 팔로우 상태가 NO 인경우-->
                    <v-btn elevation="16" width="300" color="grey" v-if="userFeedData.followState==='alreadyRequest'"
                    @click="unFollow()"
                    >FOLLOW 요청 취소</v-btn>
                </v-col>

             </v-row>

         
        
          <!-- 프로필 편집 -->
          <v-row class="mt-2" v-if="userFeedData.itsMe">
            <v-col>
              <v-btn elevation="16" width="300" color="success" @click="feedEditDialogOpen">자기소개 편집</v-btn>
            </v-col>
          </v-row>
          <!-- 게시물, 팔로워, 팔로잉 노출 -->
          <v-row class="mt-5">
            <v-col cols="3">
              게시물 {{ userFeedData.postAmount }}
            </v-col>

            <v-col cols="3" :class="userFeedData.itsMe || userFeedData.locked ==='PUBLICID'||
             userFeedData.followState==='alreadyFollow'?'isHover':''" @click="flwListDialogOpen('FOLLOWER')">
              팔로워 {{ userFeedData.followerAmount }}
            </v-col>

            <v-col cols="3" :class="userFeedData.itsMe || userFeedData.locked ==='PUBLICID'|| 
            userFeedData.followState==='alreadyFollow'?'isHover':''" @click="flwListDialogOpen('FOLLOWING')">
              팔로잉 {{ userFeedData.followingAmount }}
            </v-col>
          </v-row>

          <v-row class="mt-8">
            <v-col>
              {{ userFeedData.nickname }}
            </v-col>
          </v-row>
        </v-col>
      </v-col>

      <v-col cols="6">


        <v-card class="mx-auto to-blackMode" elevation="16" max-width="100%" height="100%" 
        v-if="userFeedData.itsMe || userFeedData.locked ==='PUBLICID'|| userFeedData.followState==='alreadyFollow'">
          <v-card-item>
            <v-card-title>자기소개</v-card-title>
            <v-card-subtitle></v-card-subtitle>
          </v-card-item>


          <v-card-text v-if="userFeedData.introduce !== null">
            {{ userFeedData.introduce }}
          </v-card-text>

          <v-card-text v-if="userFeedData.introduce === null">
            아직 등록된 자기소개가 없습니다.
          </v-card-text>
        </v-card>
        <v-card class="mx-auto to-blackMode" elevation="16" max-width="100%" height="100%" v-else>
        


        <v-card-text  class="d-flex child-flex justify-center align-center" 
          style="font-size: 25px;"
        >
        비공개 계정입니다.
        </v-card-text>


       

        </v-card>
      </v-col>
    </v-row>

    <!-- 관심사 해시태그 -->
    <v-row>
      <v-card class="mx-auto to-blackMode" width="100%">
        <template v-slot:title>
          <span class="font-weight-black">관심사</span>
        </template>

        <v-divider></v-divider>

        <v-sheet class="py-4 px-1" style="background-color: #2C2C2C;">
          <!-- 해시태그 -->
          <v-chip-group selected-class="text-primary" mandatory>
            <v-chip style="background-color: floralwhite;"
              v-for="(hobby, index) in userFeedData.hobbies"
              :key="index">
              {{ hobby.hobby }}
            </v-chip>
          </v-chip-group>
        </v-sheet>
      </v-card>
    </v-row>

    <!-- 피드 게시글 -->
    <v-row class="mt-7" style="height: 880px; width: 100%;" >
      <UserFeedPostList
        v-if="userFeedData.itsMe || userFeedData.locked ==='PUBLICID'|| userFeedData.followState==='alreadyFollow'"
        @childLoadingComplete="childLoadingComplete"
        :nickname="nickname"
        
      ></UserFeedPostList>

      <!-- 비공개 계정, 팔로우 상태가 아닌경우 -->
      <v-row v-else>
        <v-col class="d-flex child-flex justify-center align-center" cols="12" style="font-size: 25px;">
          비공개 계정입니다.
        </v-col>
      </v-row>

    </v-row>


    <!-- 팔로우/팔로잉 목록 모달 -->
    <FlwListComponent 
    :value="flwListDialog" 
    :flwType="flwType"
    :nickname="nickname"
    :itsMe="userFeedData.itsMe"
     @flwListClose="flwListDialogClose"></FlwListComponent>

    <!-- 프로필 편집 모달 -->
    <FeedEditComponent 
     v-model:value="feedEditDialog"
     @feedEditClose="feedEditDialogClose"
     :introduce="userFeedData.introduce"
     ></FeedEditComponent>
    <ChangeProfileImg v-model:value="changeProfileImgDialog" @changeProfileImgClose="changeProfileImgClose"></ChangeProfileImg>
</template>

<script>
import FlwListComponent from "./Flwlist.vue";
import FeedEditComponent from "./FeedEdit.vue";
import UserFeedPostList from "./UserFeedPostList.vue";
import ChangeProfileImg from "./ChangeProfileImg.vue";
import api from "@/api";

export default {
  props: ['nickname'],
  data() {
    return {
      userFeedData: {},//게시글 정보를 제외한 사용자피드에 필요한 데이터
      flwListDialog: false, // 팔로우/팔로잉 목록 모달 제어값
      flwType: '', // 팔로우 ? 팔로잉?
      feedEditDialog: false, // 자기소개 편집 모달 제어값
      changeProfileImgDialog:false,//프로필 이미지 편집 모달 제억밧 
      isLoading: true, // 로딩 상태 초기화
      childIsLoading:true //자식 로딩 상태
    };
  },
  components: {
    FlwListComponent, //팔로우/팔로워 목록 컴포넌트
    FeedEditComponent,//프로필 편집 컴포넌트
    UserFeedPostList, //게시물 목록 컴포넌트
    ChangeProfileImg// 프로필 이미지 변경 컴포넌트
  },
  //마운트시 해당 유저의 피드정보를 불러온다
  mounted() {
    this.getInfoData();
  },
  //닉네임(router 의 props) 변경시 감지하여 다시 정보를 로드한다.
  watch: {
    nickname() {      
      // nickname이 변경될 때 호출되는 메서드==> 값 초기화
      this.getInfoData();
      this.childIsLoading=true;
      this.flwType=''
      this.feedEditDialog=false;
      this.changeProfileImgDialog=false;
      this.isLoading=true

    }
  },
  methods: {
    //유저정보를 읽어오는 api 메서드
    getInfoData() {
      //props 닉네임을 api pathvariable로 
      api.get(`/feed/userfeed/${this.nickname}`)
        .then((res) => {
          //성공시 정보를 담는다.
          this.userFeedData = res?.data?.reqData;
          // 비공개 계정일시 팔로우상태가 아니면 게시물 정보도 노출하지않는데 , 불필요한 api호출을 방지하기 위해 
          // 컴포넌트 자체를 마운트시키지않는다 . 
          if(!this.userFeedData.itsMe && this.userFeedData.locked !== 'PUBLICID' && this.userFeedData.followState !== 'alreadyFollow'){
            //따라서 기존 자식컴포넌트 api호출 완료 상태 여부를 임의로 완료 상태로 설정 
            this.childIsLoading=false
          }
        })
        .catch((err) => {
          //실패시 에러 alert
          alert(err?.response?.data?.message);
        })
        .finally(() => {
          //현재 로딩 정보를 false로 하여 스켈레톤 로딩 스피너가 동작을 멈추도록 
          this.isLoading = false;
        });
       
    },
    //팔로우를 하는 메서드
    doFollow(){ 
      //현재 데이터통신이 일어나는지 여부 
      this.isLoading=true;
      //인서트 팔로우 api
        api.post("/feed/follow",{
          nickname:this.userFeedData.nickname, //팔로우할 닉네임
          locked:this.userFeedData.locked // 해당 계정의 공개/비공개  상태 여부 
        })
        .then(()=>{
          //성공시 데이터 리로드 
          this.getInfoData()
        })
        .catch((err)=>{
          alert(err?.response?.data?.message)
        })
        .finally(()=>{
          //데이터 통신 종료
          this.isLoading=false
        })
       }
      ,
      //언팔로우(delete follow)
      unFollow(){
        //현재 데이터 통신이 일어나고 있는지 정보 
        this.isLoading=true;

      api.delete("/feed/follow",{
        data:{
          nickname:this.userFeedData.nickname //닉네임
        }
       
      })
      .then(()=>{
        //언팔로우 성공 후 데이터 리로드 
        this.getInfoData()
      })
      .catch((err)=>{
        alert(err?.response?.data?.message);
        
      })
      .finally(()=>{
        this.isLoading=false
      })
      },

    // 유저피드에서의 게시물 정보를 읽어오는데 자식컴포넌트에서 데이터 로드가 완료되면 현 컴포넌트로 이벤트를 전송하여 스켈레톤 로딩 스피너가 동작을 멈추도록한다
    //스켈레톤 스피너 동작 조건 현 컴포넌트 데이터 로딩완료 && 자식컴포넌트 데이터 로딩완료
    childLoadingComplete(){
      this.childIsLoading=false
    },
    
    flwListDialogOpen(str) {  // 팔로워, 팔로잉 각각 버튼 클릭 시 str로 매개변수 받아와 flwType을 초기화 
                            // => flwList 컴포넌트 하나로 운영 (str 전달 -> 팔로잉 목록 or 팔로우 목록)
      if(!this.userFeedData.itsMe && this.userFeedData.locked !== 'PUBLICID' && this.userFeedData.followState !== 'alreadyFollow'){
        return
      }
      this.flwType = str;
      this.flwListDialog = true; // 모달 열음
    },
    flwListDialogClose() {
      this.getInfoData()   // 팔로우, 팔로잉 목록 닫음
      this.flwListDialog = false;
    },
    // 프로필 편집 모달 제어
    feedEditDialogOpen() {
      this.feedEditDialog = true;
    },
    feedEditDialogClose() {
      this.getInfoData()
      this.feedEditDialog = false;
    },
    changeProfileImgOpen(){
      this.changeProfileImgDialog=true;
    },
    changeProfileImgClose(){
      this.getInfoData()
      this.changeProfileImgDialog=false;
    }
  }
}
</script>

<style>
html, body, #app, .v-application {
  background-color: #2C2C2C !important;
  color: aliceblue;
  height: 100%;
  margin: 0;
  padding: 0;
}

.v-container, .v-main, .v-row, .v-col {
  background-color: transparent !important; /* 상속된 스타일이 덮어쓰지 않도록 설정 */
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

.avatar-wrapper {
  display: flex;
  align-items: flex-start; /* 상단에 정렬 */
  justify-content: center; /* 가운데 정렬 */
  position: relative;
  width: 300px; /* 프로필 사진의 너비와 동일하게 설정 */
  height: 300px; /* 프로필 사진의 높이와 동일하게 설정 */
}

.avatar {
  width: 100%;
  height: 100%;
}

.edit-icon {
  position: absolute;
  right: 30px;
  bottom: 10px;
  background-color: floralwhite;
  border-radius: 50%;
  padding: 21px;
  color: black; /* 아이콘 색상 조정 */
  font-size: 24px; /* 아이콘 크기 조정 */
}

.isHover {
  cursor: pointer; /* 커서를 손 모양으로 변경 */
}

.image-container {
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease;
}

.image-container:hover {
  transform: scale(1.05);
  cursor: pointer; /* 사진 호버 시 포인터 커서 */
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5); /* 사진 어두워지기 */
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.image-container:hover .overlay {
  opacity: 1;
}

.overlay-icon {
  font-size: 48px; /* 아이콘 크기 조정 */
  color: white; /* 아이콘 색상 */
  margin: 0 10px;
  cursor: default; /* 아이콘에 기본 커서 적용 */
}
</style>
