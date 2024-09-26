<template>

  <v-container >

    <v-tabs
      class="to-blackMode"
      fixed-tabs
      v-model="type"
    >
      <v-tab value="NORMAL" @click="changeType('NORMAL')" ><span style="color:floralwhite">{{nickname}}님</span>의 게시물</v-tab>
      <v-tab value="TAGGED" @click="changeType('TAGGED')"><span style="color:floralwhite">{{ nickname }}님</span>을 태그한 게시물</v-tab>
    
    </v-tabs>
    <v-divider></v-divider>
    <v-progress-linear
          color="white"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>

    <v-row v-if="!postData.length && !isLoading" style="margin-top: 5px;">
      <v-col class="d-flex child-flex justify-center align-center" cols="12">
        아직 등록된 게시물이 없습니다.
      </v-col>
    </v-row>

    <v-row v-if="postData.length " style="margin-top: 1px;" >
      <v-col
        v-for="(post, index) in postData"
        :key="index"
        class="d-flex child-flex"
        cols="4"
      >
        <v-img
          
          :src="post.postImgUrl"
          aspect-ratio="1"
          class="image-container"
          cover
           @click="openPostDetailDialog(post.postNum)"
        >
          <template v-slot:placeholder>
            <v-row align="center" class="fill-height ma-0" justify="center" v-if="isLoading">
              <v-progress-circular color="grey-lighten-5" indeterminate></v-progress-circular>
            </v-row>
          </template>
          <div v-if="post.onlyMe==='ONLYME'" class="overlayOnlyMe">
            <span class="overlayOnlyMeText"><span> 
              <v-icon>mdi-lock</v-icon></span>
              &nbsp;<span>나만 보기</span></span>
          </div>
          <div class="overlay" >
          <span >  <v-icon class="overlay-icon">mdi-heart</v-icon>
            {{ post.likeCount }}
          </span>
          </div>
        </v-img>
   
      </v-col>
    
    </v-row>
    <div ref="sentinel" class="sentinel" ></div>
  </v-container>
  <PostDetail 
   :value="postDetailDialog"
   :postNum="sendPostNum"
   @postDetailClose="closePostDetailDialog"
   ></PostDetail>
</template>

<script>
import api from "@/api"
import PostDetail from '../post/PostDetail.vue';
import eventBus from "@/eventBus"
export default{
    name:'UserFeedPostList',

  //부모로 부터 받은 닉네임 값 
  props:{
    nickname:String,
   
  },
  data(){
    return{
      postData:[], //게시물 정보 데이터 
      isLoading:false, // 로딩상태를 저장할  변수 
      page:1, // 페이지
      observer:null, //intersection observer 객체
      noMoreNeedData:false, //더이상 로드할 데이터가 없다면 불필요한 api 호출을 방지하기 위한 변수 
      sendPostNum:0, //게시물 고유번ㅇ호 
      type:'NORMAL', //내가 작성한 게시물인지 , 내가 태그를 당한 게시물인지 
      
    }
  },
  // intersection observer 참조할 태그 ref
  computed:{
    sentinel() {
      return this.$refs.sentinel;
    }
  },
  components:{
    PostDetail
  },
    //마운트 시 게시물 정보 , intersection observer 초기화
    mounted(){
      eventBus.on('resetPostList',this.resetData);
    
     this.initObserver(); // IntersectionObserver 초기화
    },
    watch: {
    //닉네임 변경 감지 
    nickname: {
      immediate: true, // 컴포넌트가 마운트될 때도 실행
      handler(newNickname, oldNickname) {
        // nickname이 변경될 때 호출
        if (newNickname !== oldNickname) {
          //데이터 리셋후 재 로드 
          this.type='NORMAL'
          this.resetData();
        }
      }
    },
    //타입 변경 감지 (내가 작성한 게시물 OR 다른 회원이 해당피드유저를 태그한 게시물)
    type: {
      handler(newType,oldType) {
       if(newType!==oldType){
        this.resetData();
       }
             
      }
    }
  },

    // 컴포넌트 언마운트 시 옵저버 해제
    beforeUnmount() {
      eventBus.off('resetPostList',this.resetData);
    if (this.observer) {
      this.observer.disconnect(); 
    }
  },
    methods:{
     //타입을 변경 
    changeType(type) {
      this.type=type;
    },
    //상세보기를 열음 
    openPostDetailDialog(postNum){
      this.sendPostNum=postNum
      this.postDetailDialog=true;
    },
    //상세보기를 닫음 
    closePostDetailDialog(){
      this.sendPostNum=0;
      this.postDetailDialog=false;
    },
      //게시물 정보 읽어오는 api
      loadPost(){ 
        if(this.noMoreNeedData ||this.isLoading)return 
        //api 호출중 상태 
        this.isLoading=true

        api.get(`/feed/userfeed/post/${this.type}/${this.nickname}/${this.page}`)
        .then((res) => {
         
          //만약 더이상 추가할 데이터가 없다면 제어변수 true 로 변경하여 더이상 api 호출하지 않도록 
          if(res?.data?.reqData?.length===0){
            this.noMoreNeedData=true;
            this.page--;
          }else{
             //무한스크롤 기존 배열에 데이터 추가 
         this.postData=[...this.postData,...res.data.reqData]
         this.page++;
          }
        })
        .catch((err) => {
          //실패시 에러 alert
          if(err?.response?.data?.message){
        alert(err?.response?.data?.message)  
        }
        })
        .finally(() => {
          //api호출상태 false
          this.isLoading = false;
          //부모에게 자식컴포넌트 로딩이 끝난 것을 전송
          this.$emit('childLoadingComplete')
        });
      },
      
    // 데이터를 초기화하고 새로 로드
    resetData() {
   
      this.page = 1;
      this.postData = [];
      this.noMoreNeedData = false;
      this.isLoading=false;
      this.observer=null;
      this.sendPostNum=0;

      this.isFirstLoad=true;
     
      this.loadPost();
    },
      //intersectionobserver 초기화 메서드
      initObserver() {
      this.$nextTick(() => {
       //ref sentinel을 참조
        const sentinel = this.$refs.sentinel;

        if (sentinel) {
          this.observer = new IntersectionObserver((entries) => {
            //감지시
            entries.forEach(entry => {
              if (entry.isIntersecting) {
               
                this.loadPost(); // Sentinel이 뷰포트에 들어올 때 데이터 로드
              }
            });
          }, {
            root:null, // viewport를 root로 설정
            rootMargin: '0px',
            threshold: 0.1 
          });
          this.observer.observe(sentinel); // Sentinel 관찰 시작
        }
      });
    }
    }
}
</script>
<style>
.overlayOnlyMe {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
}

.overlayOnlyMeText {
  color: white; /* 글씨 색상 */
  font-size: 1.5rem; /* 글씨 크기 */
  text-align: center; /* 글씨 정렬 */
  opacity: 0.7;
}
</style>