<template>
  
    <!-- 팔로우/팔로워 목록 -->
    <v-dialog v-model="localDialog">
      <div ref="container">
      <v-card class="mx-auto to-blackMode" width="600">
        
       

        <v-btn
          style="margin-left: 560px; margin-top: 10px; background-color: gray;"
          icon
          class="close-btn"
          size="30"
          @click="closeDialog()"
          v-if="!isLoading"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>

        <span v-if="!isLoading" style="display: flex; font-size: 20px; align-items: center; justify-content: center;">
          {{ flwType }}
        </span>

        <v-col style="display: flex; align-items: center; justify-content: center;">
          <v-divider></v-divider>        
        </v-col>

          <div v-if="!FollowData.length && !isLoading" class="no-data-message">
            <span>아직 목록이 존재하지 않습니다.</span>
          </div>

        <div class="scroll-container" >
             
          <v-list  lines="three" class="to-blackMode pa-3">
            <v-list-item>
           
              <v-list-item-content >
               
                <v-row v-for="(follow, index) in FollowData" :key="index">
                  <v-col cols="2"  style="cursor:pointer" @click="goOtherFeed(follow.nickname)">
                    <v-avatar :image="follow.profile" size="35" class="avatar"
                    v-if="follow.profile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="35" class="avatar" v-if="follow.profile === null" ></v-avatar>
                  </v-col>

                  <v-col cols="6" style="cursor:pointer" @click="goOtherFeed(follow.nickname)">
                     
                      <span class="large-font" >{{ follow.nickname }}</span>
                   
                  </v-col>

                  <v-col cols="4" v-if="follow.myApprove === null && follow.nickname !== myNickname" class="d-flex align-center justify-center">
                    <v-btn  color="primary"  @click="doFollow(follow.nickname)"> FOLLOW </v-btn>
                  </v-col>

                  <v-col cols="4" v-if="follow.myApprove === 'FOLLOWOK' && follow.nickname !== myNickname" class="d-flex align-center justify-center">
                    <v-btn  color="red" @click="unFollow(follow.nickname)">UNFOLLOW</v-btn>
                  </v-col>

                  <v-col cols="4" v-if="follow.myApprove === 'FOLLOWNO' && follow.nickname !== myNickname" class="d-flex align-center justify-center">
                    <v-btn  color="grey"  @click="unFollow(follow.nickname)">Follow 요청취소</v-btn>
                  </v-col>
                </v-row>
              </v-list-item-content>
            
            </v-list-item>
            
          </v-list>
          
          <div v-if="isLoading" class="loading-indicator">
            <v-progress-circular indeterminate color="primary" size="30"></v-progress-circular>
          </div>
          <div ref="sentinel" class="sentinel"></div>
        </div>
      </v-card>
    </div>
    </v-dialog>

</template>

<script>
import api from '@/api';

export default {
  name: 'FlwListComponent',

  props: {
    //컴포넌트가 열린상태인지
    value: {
      type: Boolean,
      required: true
    },
    //팔로우 OR 팔로워 
    flwType: {
      type: String,
      required: true
    },
    //닉네임 
    nickname: {
      type: String,
      required: true
    },
    //해당 피드가 자신의 피드인지 
    itsMe: {
      type: Boolean,
      required: true
    }
  },

  data() {
    return {
      isLoading: false, //데이터 통신중인지 여부 
      FollowData: [], //팔로우/팔로잉 목록 데이터 (서버로 부터 받은 값 )
      page: 1, //페이지
      myNickname: '', //현재 로그인한 유저의 닉네임 == > 서버로 부터 받음  
      observer: null,  //intersection Observer 객체 - > 무한스크롤
      isNomoreData:false //무한 스크롤 할 시 불필요한 통신을 방지하기 위해 
    };
  },
//변화 감지 
  watch: {
    // 모달 open 값의 변화 감지 (열린상태, 안열린상태)
    value(newVal) {
      if (newVal) {
        this.initObserver(); // IntersectionObserver 초기화
        this.callFollowList(); //초기 1페이지 데이터 목록 콜
      }
    }
  },

  beforeUnmount() {
    // 컴포넌트 언마운트 시 옵저버 해제
    if (this.observer) {
      this.observer.disconnect();
    }
  },

  computed: {
    //모달 open, close 값
    localDialog: {  
      get() {
        return this.value;
      }
    }
  },

  methods: {
    // intersection observer 초기화 함수
    initObserver() {
      this.$nextTick(() => {
        const sentinel = this.$refs.sentinel; // 해당 요소를 감시 
        const container = this.$refs.container; // 현재 모달에서 진행될 것이기 떄문에 모달 내 container 정의 후 ref
       
        if (sentinel && container) {
          this.observer = new IntersectionObserver((entries) => {
            
            entries.forEach(entry => {
              if (entry.isIntersecting) {
                // sentinel 을 감지 할때마다 
                this.callFollowList(); // Sentinel이 뷰포트에 들어올 때 데이터 로드
                this.page++;//페이지 증가 
              }
            });
          }, {
            root: container, // container를 root로 설정
            rootMargin: '0px',
            threshold: 1.0 // Sentinel이 완전히 보일 때 트리거
          });

          this.observer.observe(sentinel); // Sentinel 관찰 시작
        }
      });
    },

    //모달을 닫는 함수 
    closeDialog() {
      //옵저버 해제
      if (this.observer) {
        this.observer.disconnect();
      }
      //초기값으로 세팅
      this.FollowData = [];
      this.page=1
      this.isNomoreData=false
      //부모에게 모달닫는 emit 전송
      this.$emit('flwListClose');
    },
    //팔로우/팔로잉 목록 가져오는 함수 
    callFollowList() {
      //현재 로딩중이거나 , 더이상 가져올 데이터가 없으면 return
      if (this.isLoading || this.isNomoreData) {
        return;
      }
      //로딩정보 true
      this.isLoading = true;
      //닉네임/ 팔로우인지 팔로잉인지 타입 / 페이지 ==> pathvariable
      api.get(`/feed/userfeed/${this.nickname}/${this.flwType}/${this.page}`)
        .then((res) => {
          //성공시 만약 데이터가 없으면 noMoreDate 를 true로 설정해 더이상 axios를 호출 못하게함 
          if(res?.data?.reqData?.followList?.length===0){
            this.isNomoreData=true;
          }
          //아니라면 
          else{ 
            //기존배열에 목록 추가 
            this.FollowData = [...this.FollowData, ...res?.data?.reqData?.followList];
            //내 닉네임 ( 현재 로그인 한 회원의 닉네임 )
            this.myNickname = res?.data?.reqData?.myNickname;
          }
         
        })
        .catch((err) => {
          alert(err?.response?.data?.message);
          this.closeDialog();
        })
        .finally(() => {
          this.isLoading = false;
        });
    },
    //팔로우 하기 (닉네임 , 비공개/공개  여부 )
    doFollow(nickname) {
      //로딩 상태 true 
      this.isLoading = true;
      //api 호출
      api.post("/feed/follow", {
        nickname: nickname, //닉네임      
      })
        .then((res) => {
          //성공시 현재 데이터를 업데이트 하기 위한 함수 호출 ( dom 업데이트 위해 )
         this.updateFollowData(nickname,res?.data?.reqData) // 닉네임 , 서버로부터 받은 바뀐  상태 값 
        })
        .catch((err) => {
          alert(err?.response?.data?.message);
        })
        .finally(()=>{
          this.isLoading=false
        });
    },
    //언팔로우 
    unFollow(nickname){
      //데이터 로딩중 
      this.isLoading=true;
      api.delete("/feed/follow",{
        data:{
          nickname:nickname //닉네임   
        }
       
      })
      .then(()=>{
        //성공시 현재 데이터를 업데이트 하기 위한 함수 호출 (작업한 닉네임과 null 을 전달)
        // = > why null?  팔로우 시에는 FOLLOWOK 상태값이냐, FOLLOWNO값이냐 서버로부터 값을 받아올 이유가 있었지만
        //언팔로우 시에는 어차피 행을 삭제하는 작업이기때문에 상태값을 NULL 전달 
        this.updateFollowData(nickname,null)
      })
      .catch((err)=>{
        alert(err?.response?.data?.message);
        this.isLoading=false
      })
      .finally(()=>{
        this.isLoading=false
      })
    },
    // FollowData 배열을 업데이트하는 메서드
  updateFollowData(nickname, changeState) {
    //현재까지 쌓인 데이터를 순회
    this.FollowData = this.FollowData.map(follow => {
      //만약 해당닉네임과 매개변수로 닉네임이 같다면
      // !!!현재 프로젝트에서는 닉네임을 중복되게 할 수 없고 ,유니크 키로 설정하였기때문에 이러한 작업이 가능하다.
      if (follow.nickname === nickname) {
        return {
          ...follow,
          myApprove: changeState // 상태 업데이트 == > DOM 업데이트 
        };
      }
      return follow;
    });
  },
  //클릭시 해당 유저의 피드로 이동 
  goOtherFeed(nickname) {//닉네임 매개변수 

      this.closeDialog(); // 모달 닫기 = > 팔로우/팔로잉 목록 모달 
      this.$nextTick(() => {
        this.$router.push({ name: 'userfeed', params: { nickname: nickname } }); // 페이지 이동 ( 닉네임 param)
      });
    }
  }
};
</script>

<style>
.large-font {
  font-size: 18px;
}

.scroll-container {
  max-height: 500px;
  overflow-y: auto;
}

.sentinel {
  height: 20px;
}

.loading-indicator {
  text-align: center;
  padding: 10px;
  color: #888;
}

.scroll-container::-webkit-scrollbar {
  width: 12px;
}

.scroll-container::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

.scroll-container::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 10px;
}

.scroll-container::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.no-data-message {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: gray;
  font-size: 16px;
  text-align: center;
}

</style>
