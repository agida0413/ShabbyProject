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
          <button v-if="userFeedData.itsMe">
            <v-icon class="edit-icon">mdi-pencil</v-icon>
          </button>
        </div>
      </v-col>

      <v-col cols="3">
        <v-col cols="auto">
          <!-- 팔로우, 언팔로우 버튼 -->
          <v-row v-if="!userFeedData.itsMe">
            <v-col>
              <v-btn elevation="16" width="300" color="primary">Follow</v-btn>
            </v-col>
          </v-row>
          <!-- 프로필 편집 -->
          <v-row class="mt-2" v-if="userFeedData.itsMe">
            <v-col>
              <v-btn elevation="16" width="300" color="success" @click="feedEditDialogOpen">프로필 편집</v-btn>
            </v-col>
          </v-row>
          <!-- 게시물, 팔로워, 팔로잉 노출 -->
          <v-row class="mt-5">
            <v-col cols="3">
              게시물 {{ userFeedData.postAmount }}
            </v-col>

            <v-col cols="3" class="isHover" @click="flwListDialogOpen('팔로워')">
              팔로워 {{ userFeedData.followerAmount }}
            </v-col>

            <v-col cols="3" class="isHover" @click="flwListDialogOpen('팔로잉')">
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
        <v-card class="mx-auto to-blackMode" elevation="16" max-width="100%" height="100%">
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
    <v-row class="mt-7" style="height: 880px; width: 100%;">
    <UserFeedPostList
      @childLoadingComplete="childLoadingComplete"
      :nickname="nickname"
    ></UserFeedPostList>
    </v-row>


    <!-- 팔로우/팔로잉 목록 모달 -->
    <FlwListComponent v-model:value="flwListDialog" v-bind:flwType="flwType" @flwListClose="flwListDialogClose"></FlwListComponent>

    <!-- 프로필 편집 모달 -->
    <FeedEditComponent v-model:value="feedEditDialog" @feedEditClose="feedEditDialogClose"></FeedEditComponent>

</template>

<script>
import FlwListComponent from "./Flwlist.vue";
import FeedEditComponent from "./FeedEdit.vue";
import UserFeedPostList from "./UserFeedPostList.vue";
import api from "@/api";

export default {
  props: ['nickname'],
  data() {
    return {
      userFeedData: {},
      flwListDialog: false, // 팔로우/팔로잉 목록 모달 제어값
      flwType: '', // 팔로우 ? 팔로잉?
      feedEditDialog: false, // 프로필 편집 모달 제어값
      isLoading: true, // 로딩 상태 초기화
      childIsLoading:true //자식 로딩
    };
  },
  components: {
    FlwListComponent,
    FeedEditComponent,
    UserFeedPostList
  },
  mounted() {
    this.getInfoData();
  },
  watch: {
    nickname() {
      // nickname이 변경될 때 호출되는 메서드
      this.getInfoData();
    }
  },
  methods: {
    getInfoData() {
      api.get(`/feed/userfeed/${this.nickname}`)
        .then((res) => {
          this.userFeedData = res.data.reqData;
        })
        .catch((err) => {
          alert(err.response.data.message);
        })
        .finally(() => {
          this.isLoading = false;
        });
       
    },
    childLoadingComplete(){
      this.childIsLoading=false
    },
    flwListDialogOpen(str) {  // 팔로워, 팔로잉 각각 버튼 클릭 시 str로 매개변수 받아와 flwType을 초기화 
                            // => flwList 컴포넌트 하나로 운영 (str 전달 -> 팔로잉 목록 or 팔로우 목록)
      this.flwType = str;
      this.flwListDialog = true; // 모달 열음
    },
    flwListDialogClose() {   // 팔로우, 팔로잉 목록 닫음
      this.flwListDialog = false;
    },
    // 프로필 편집 모달 제어
    feedEditDialogOpen() {
      this.feedEditDialog = true;
    },
    feedEditDialogClose() {
      this.feedEditDialog = false;
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
