<template>
  <v-card-text >
    
      
      
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

     

      <!-- 추천 목록이 검색바 아래에 자연스럽게 나타나도록 스타일 조정 -->
     <GlobalSearchBar 
     v-show="isSearchBarOpen"
     @searchHobby="searchHobby"
     @searchMember="searchMember"
     @closeSearch="closeSearch"
     :keyword="keyword"
        style="background-color: white; z-index: 1000000; pointer-events: auto;"
       ref="globalSearchBar"></GlobalSearchBar>
   
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
   
      this.isSearchBarOpen=true
    }
  }
},
  methods: {
   
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
searchHobby(hobby){
  this.isSearchBarOpen=false
  this.$router.push({
      name: 'searchfeed',
      params: { type: 'hobby', keyword: hobby }
    });
},
searchMember(member){
  this.isSearchBarOpen=false
  this.$router.push({
      name: 'searchfeed',
      params: { type: 'member', keyword: member }
    });
},
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
