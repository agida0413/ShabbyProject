<template>
  <v-card-text>
    <div class="search-container">
      <v-text-field
        
        append-inner-icon="mdi-magnify"
        density="compact"
        label="검색어를 입력하세요"
        variant="solo"
        hide-details
        
        single-line
        @keydown="handleSearchKeyDown"
       v-model="keyword"
      
       
      ></v-text-field>
<v-divider></v-divider>
      <!-- 추천 목록이 검색바 아래에 자연스럽게 나타나도록 스타일 조정 -->
     <GlobalSearchBar 
     v-show="isSearchBarOpen"
     @closeSearch="closeSearch"
     :keyword="keyword"
      style="background-color: white;"
       ref="globalSearchBar"></GlobalSearchBar>
    </div>
  </v-card-text>
</template>

<script>
export default {
  data() {
    return {
      keyword:'',
      loading: false,
      isSearchBarOpen:false
      
    }
  },
watch:{
  keyword(keyword){
   
    if(keyword!==''){
      console.log(keyword)
      this.isSearchBarOpen=true
    }
  }
},
  methods: {
    updateKeyword(value){
      this.keyword=value
    },
    handleHobbyKeyDown(event) {

switch(event.key) {
  case 'ArrowDown'://아래방향키
    this.handleHobbyArrowDown();
    break;
  case 'ArrowUp'://위방향키
    this.handleHobbyArrowUp();
    break;
  case 'Enter'://엔터
  event.preventDefault(); //기본 textarea시 줄바꿈되는 현상막기 위함  
    this.handleHobbyEnter();
    break;
  default:
    break;
}
},
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

this.$refs.globalSearchBar.handleArrowDown();
},
handleSearchArrowUp(){
this.$refs.globalSearchBar.handleArrowUp();
},
handleSearchEnter(){
this.$refs.globalSearchBar.handleEnter();
},

closeSearch(){
 this.keyword=''
this.isSearchBarOpen=false
}
    

  }
}
</script>

<style scoped>
.search-container {
  position: relative; /* 검색바와 추천 목록이 서로 겹치지 않도록 위치 설정 */
}

.v-text-field {
  width: 100%; /* 검색바가 컨테이너의 전체 너비를 차지하도록 설정 */
}

.suggestions-list {
  
  max-height: 200px;
  overflow-y: auto; /* 세로 스크롤바가 필요할 때만 표시 */
  background-color: white;
  border: 1px solid #ccc; /* 추천 목록의 경계선 색상 */
  border-top: none; /* 검색바와 추천 목록 사이의 경계선 제거 */
  position: absolute;
  top: 100%; /* 검색바 아래쪽에 위치하도록 설정 */
  left: 0;
  width: 100%; /* 검색바와 같은 너비를 가지도록 설정 */
  z-index: 11; /* 검색바 위에 표시되도록 설정 */
}

.v-list-item {
  cursor: pointer;
}

.v-list-item:hover {
  background-color: #f0f0f0; /* 추천 목록 아이템의 hover 색상 */
}
</style>
