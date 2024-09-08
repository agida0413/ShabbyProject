<template>
    
  <v-container>
   
        <v-progress-linear
          color="cyan"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>
  
      <v-row v-if="!postData.length&&!isLoading">
        <v-col class="d-flex child-flex justify-center align-center" cols="12">
          아직 팔로우하는 사람이 없거나 등록된 게시물이 없습니다.
        </v-col>
      </v-row>

    <v-row v-if="postData.length">
      <!--게시글 순회시작-->
      <v-col
        v-for="(post,index) in postData" :key="index"
        class="d-flex child-flex"
        cols="4"
      
      >
        <v-img 
          :lazy-src="`https://picsum.photos/10/6?image=${n * 5 + 10}`"
          :src="post.postImgUrl"
          aspect-ratio="1"
          class="image-container"
          cover
          @click="openPostDetailDialog(post.postNum)"
        >
          <template v-slot:placeholder>
            <v-row
              align="center"
              class="fill-height ma-0"
              justify="center"
            >
              <v-progress-circular
                color="grey-lighten-5"
                indeterminate
              ></v-progress-circular>
            </v-row>
          </template>
          
          <div class="overlay">
            <v-icon class="overlay-icon">mdi-heart</v-icon>
            {{ post.likeCount }}
          </div>
        </v-img>
      </v-col>
      <!--순회 종료-->
    </v-row>

  <div ref="sentinel" class="sentinel" ></div>

  <PostDetail 
   :value="postDetailDialog"
   :postNum="sendPostNum"
   @postDetailClose="closePostDetailDialog"
   ></PostDetail>
  
  </v-container>
  


</template>

<script>
import api from '@/api';
import PostDetail from '../post/PostDetail.vue';
export default {
  data() {
    return {
      postDetailDialog:false, //게시물 상세 다이얼로그 
      page:1, //페이지 
      postData:[], //서버로부터 받을 게시물 데이터 
      isLoading:false, // 로딩상태를 저장할  변수 
      observer:null, //intersection observer 객체
      noMoreNeedData:false, //더이상 로드할 데이터가 없다면 불필요한 api 호출을 방지하기 위한 변수 
      firstCall:false ,//마운트시 첫번쨰 로드시 페이지 증가 x 위한 변수 
      sendPostNum:0// 상세보기 모달에 전달할 
    };
    
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
  //마운트시 
  mounted(){
    this.initObserver(); // IntersectionObserver 초기화
    this.callMainPostList()//1페이지 게시글 불러오기 
  },
   // 컴포넌트 언마운트 시 옵저버 해제
   beforeUnmount() {
    if (this.observer) {
      this.observer.disconnect(); 
    }
  },
  methods:{
    openPostDetailDialog(postNum){
      this.sendPostNum=postNum
      this.postDetailDialog=true;
    },
    closePostDetailDialog(){
      this.sendPostNum=0;
      this.postDetailDialog=false;
    },
    //메인피드의 게시물 리스트 불러오기 메서드 
    callMainPostList(){
      //만약 로딩중이거나 , 더이상 불러올 데이터가 없으면 리턴 
      if(this.isLoading || this.noMoreNeedData)return
      //데이터 불러오기 시작 
      this.isLoading=true
      //만약 첫번째 콜이라면 페이지 증가x 그외엔 증가 
      if(this.firstCall){
        this.page++;//페이지 증가
      }
      //처음 콜 이후엔 페이지 증가 위해 상태 변경 
      this.firstCall=true
      //게시물 정보 불러오기 api
     api.get(`/feed/mainfeed/${this.page}`) 
     .then((res)=>{
        //서버로 부터 받은 데이터 
        const resData=res?.data?.reqData
        //만약 데이터가 있다면 
        if(resData.length){
          //기존 데이터에 추가 
          this.postData=[...this.postData,...resData]
        }
        else{
          //데이터가 없다면 이제 더이상 데이터를 안불러와도 된다는 변수 및 페이지 원복 
          this.noMoreNeedData=true;
          this.page--;
        }

     })
     .catch((err)=>{
      alert(err?.response?.data?.message)
     })
     .finally(()=>{
      //데이터 로딩 끝 
      this.isLoading=false
     })
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
               
                this.callMainPostList() // Sentinel이 뷰포트에 들어올 때 데이터 로드
              }
            });
          }, {
            root:null, // viewport를 root로 설정
            rootMargin: '100px',
            threshold: 0.1 
          });
          this.observer.observe(sentinel); // Sentinel 관찰 시작
        }
      });
    }
  }
}
</script>

<style scoped>


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
