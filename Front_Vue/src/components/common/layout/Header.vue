<template>
  <v-card-text >
    
      
      <!--상단 헤더 글로벌 검색 바-->
      <v-text-field    
        :class="isSearchBarOpen?' globalaBarInSearch':''"
        append-inner-icon="mdi-magnify"
        density="compact"
        label="검색어를 입력하세요"
        hide-details
        variant="solo"
        single-line
        @keydown="handleSearchKeyDown"      
        v-model="keyword"     
      ></v-text-field>

     

    <!-- 글로벌 검색 결과 리스트 -->
     <GlobalSearchResult 
        v-show="isSearchBarOpen"
        @searchHobby="searchHobby"
        @searchMember="searchMember"
        @closeSearch="closeSearch"
        :keyword="keyword"
        style="background-color: white; z-index: 1000000; pointer-events: auto;"
        ref="globalSearchResult"></GlobalSearchResult>
   
  </v-card-text>
</template>

<script>

export default {
  data() {
    return {
      keyword:'', //검색 키워드 
      loading: false, // 로딩 상태 
      isSearchBarOpen:false //검색결과 열고닫음 제어
      
    }
  },
  //감지 
watch:{
  //새로운 키워드 
  keyword(keyword){
    
    //만약 키워드가 공백이 아니면
    if(keyword!==''){
      //검색결과 리스트를 열음 
      this.isSearchBarOpen=true
    }
  }
},
  methods: {
   
//부모요소(현재 컴포넌트) 에서 키다운이벤트 발생시 팔로우검색 컴포넌트에게 이벤트를 전달하기위함 
handleSearchKeyDown(event) {
switch(event.key) {
  case 'ArrowDown'://아래 방향키 
    this.handleSearchArrowDown();
    break;
  case 'ArrowUp'://위방향키
    this.handleSearchArrowUp();
    break;
  case 'Enter'://엔터
  event.preventDefault(); //기본 textarea시 줄바꿈되는 현상막기 위함  
    this.handleSearchEnter();
    break;
  default:
    break;
}
},
//자식컴포넌트에게 ref(참조값) 에 해당하는 메소드로 전달 
handleSearchArrowDown(){

this.$refs.globalSearchResult.handleArrowDown();
},
handleSearchArrowUp(){
this.$refs.globalSearchResult.handleArrowUp();
},
handleSearchEnter(){
  
this.$refs.globalSearchResult.handleEnter();
},
//자식으로 부터 매개변수로 자동완성된 해당항목의 value를 받아 검색결과 라우터로 푸시
//관심사 타입일시 
searchHobby(hobby){
  //검색리스트를 닫음 
  this.isSearchBarOpen=false
  //관심사 검색피드로 푸시 
  this.$router.push({
      name: 'searchfeed',
      params: { type: 'hobby', keyword: hobby }
    });
},
//회원 검색타입일시 
searchMember(member){
  //검색 리스트를 닫음 
  this.isSearchBarOpen=false
  //회원 검색 결과피드로 푸시 
  this.$router.push({
      name: 'searchfeed',
      params: { type: 'member', keyword: member }
    });
},
//검색 결과컴포넌트를 닫는 메소드 
closeSearch(){
 this.keyword=''
this.isSearchBarOpen=false
}
    
  }
}
</script>

<style scoped>
.globalaBar{
    background-color: aliceblue;
    
    border-radius: 8px 8px 8px 8px 
  }
  .globalaBarInSearch{
    background-color: aliceblue;
    border-radius: 8px 8px 0 0
  }
  .v-text-field {
    width: 100%; /* 검색바가 컨테이너의 전체 너비를 차지하도록 설정 */
  }
  .test{
    pointer-events: none;
  }
  
  
  .v-list-item {
    cursor: pointer;
  }
  
  .v-list-item:hover {
    background-color: #f0f0f0; /* 추천 목록 아이템의 hover 색상 */
  }
  

</style>