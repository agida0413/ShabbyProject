<template>
  <v-container >
   
    <v-row v-if="!postData.length">
      <v-col class="d-flex child-flex justify-center align-center" cols="12">
        아직 등록된 게시물이 없습니다.
      </v-col>
    </v-row>

    <v-row v-if="postData.length">
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
        >
          <template v-slot:placeholder>
            <v-row align="center" class="fill-height ma-0" justify="center">
              <v-progress-circular color="grey-lighten-5" indeterminate></v-progress-circular>
            </v-row>
          </template>
          <div class="overlay">
            <v-icon class="overlay-icon">mdi-heart</v-icon>
            {{ post.likeCount }}
          </div>
        </v-img>
      </v-col>
    
    </v-row>
    <div ref="sentinel" class="sentinel" ></div>
  </v-container>
</template>

<script>
import api from "@/api"
export default{
    name:'UserFeedPostList',
    //부모로 부터 받은 닉네임 값 
  props:{
    nickname:String
  },
  data(){
    return{
      postData:[], //게시물 정보 데이터 
      isLoading:true, // 로딩상태를 저장할  변수 
      page:1, // 페이지
      observer:null, //intersection observer 객체
      noMoreNeedData:false //더이상 로드할 데이터가 없다면 불필요한 api 호출을 방지하기 위한 변수 
    }
  },
  // intersection observer 참조할 태그 ref
  computed:{
    sentinel() {
      return this.$refs.sentinel;
    },
  },
    //마운트 시 게시물 정보 , intersection observer 초기화
    mounted(){
     this.loadPost()
     this.initObserver(); // IntersectionObserver 초기화
    },
    // 컴포넌트 언마운트 시 옵저버 해제
    beforeUnmount() {
    if (this.observer) {
      this.observer.disconnect(); 
    }
  },
    methods:{
      //게시물 정보 읽어오는 api
      loadPost(){
        //api 호출중 상태 
        this.isLoading=true
        //noMoreNeedData가 true 이면 리턴 ( 더이상 불러올 데이터가 없는경우)
       if(this.noMoreNeedData)return 

      api.get(`/feed/userfeed/${this.nickname}/${this.page}`)
        .then((res) => {
            //무한스크롤 기존 배열에 데이터 추가 
         this.postData=[...this.postData,...res.data.reqData]
          //만약 더이상 추가할 데이터가 없다면 제어변수 true 로 변경하여 더이상 api 호출하지 않도록 
          if(res?.data?.reqData?.length===0){
            this.noMoreNeedData=true;
          }
        })
        .catch((err) => {
          //실패시 에러 alert
          alert(err?.response?.data?.message);
        })
        .finally(() => {
          //api호출상태 false
          this.isLoading = false;
          //부모에게 자식컴포넌트 로딩이 끝난 것을 전송
          this.$emit('childLoadingComplete')
        });
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
                this.page++;//페이지 증가
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