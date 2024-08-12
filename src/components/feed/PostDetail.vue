<template>
  <!--게시물 상세 모달-->
  <v-dialog v-model="localDialog" max-width="1100" class="modal">
    <v-card class="to-blackMode">
      
      
      
      <v-container fluid>
        <v-row no-gutters >
      
          <v-col cols="6" class="image-section">
            <div class="overlay-text " >
              <!--사람 태그 목록 , 사진에 zindex로 올림 / 사진 넓이 초과시 줄 바꿈-->
                <v-chip
                  class="ma-2"
                  color="white"
                  prepend-icon="mdi-account-circle"
                  v-for="i in 10" :key="i"
                >
                  Mike
                </v-chip>
      </div>
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
          <v-col cols="6" class="content-section " >
            <v-icon
              class="close-icon"
              @click="closeDialog"
            >mdi-close</v-icon><!-- x아이콘 - > 모달 닫음-->

            <ReplyMore v-show="moreReply"></ReplyMore><!-- moreReply값이 true이면 댓글만 보여짐 -->
            <v-card-text v-show="!moreReply" ><!-- 아닐시 게시물 내용 ,좋아요, 수정,삭제 , 댓글 하나만 보임 -->
              <!-- Post Information Layout -->
              <v-row no-gutters >
                <!-- Post Content (50%) -->
                <v-col cols="12" class="post-content-col ">
                  <span class="post-text">게시물 내용</span>
                </v-col>
                <v-col cols="12" class="post-content-col">
                  <span class="hobby-text">관심사</span>
                </v-col>
                <v-col cols="12" class="like-icon-col mt-4">
                  <v-icon>mdi-heart</v-icon>
                  &nbsp;<span style="width: 80%;">1212명이 좋아합니다.</span>

                  <span>
                    <v-btn class="ms-5" icon="mdi-pencil" size="30" color="black"></v-btn><!--수정 버튼-->
                  </span>
                  
                  <span style="margin-left: 8px;">
                  <v-btn icon="mdi-delete-outline"  color="red" size="30"></v-btn> <!--삭제 버튼-->
                </span>
                </v-col>
                <v-divider></v-divider>
              
                <v-col cols="12"  class="comment-icon-col">
                  
                  <v-icon>mdi-comment</v-icon>
                  
                  &nbsp;<span> comments</span>

                  <span style="margin-left: 250px;" > <v-btn color="#757575">
                  댓글 비활성화 하기 <!-- 댓글 기능을 비활성화 함 --> 
                </v-btn></span>
                </v-col>
              </v-row>
              <v-row style="height: 180px;">
                <ReplyComponent @wantMoreReply="moreReplyOpen"></ReplyComponent> <!--하단 부를 댓글 컴포넌트로 따로 빼놓음 -->
              </v-row>
            </v-card-text>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script>
import ReplyMore from "./ReplyMore.vue";
import ReplyComponent from "./Reply.vue"
export default {
  name: 'PostDetail',

  props: {
    value: {//부모로 부터받음 모달열림,닫음 제어 변수
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      moreReply: false //댓글 더보기 클릭시 true
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
      this.$emit('postDetailClose', false); //상세보기 닫음 
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
    ReplyComponent, //댓글 정보 컴포넌트
    ReplyMore //댓글 더보기 컴포넌트
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
