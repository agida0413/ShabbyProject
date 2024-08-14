<template lang="">
    <div class="fixed-tabs" >
       <Searchselect @hobby="selectSearchHobby()"
                     @people="selectSearchpeople()"
       ></Searchselect>
    </div>
    <SearchHobby v-show="isSearchHobby"></SearchHobby>
    <Searchpeople v-show="isSearchPeople"></Searchpeople>
</template>
<script>
import Searchselect from './Searchselect.vue'; //관심사 or 사람 검색 선택 컴포넌트
import SearchHobby from './SearchHobby.vue';  //관심사 검색 컴포넌트
import Searchpeople from './Searchpeople.vue'; //사람검색 컴포넌트 

export default {
  data(){
return{
  isSearchHobby:true, //관심사
  isSearchPeople:false //사람
}
  },
  methods:{
    selectSearchHobby(){ //관심사 검색을 여는 메소드
      this.isSearchHobby=true;
      this.isSearchPeople=false;
    },
    selectSearchpeople(){ //사람 검색을 여는 메소드 
      this.isSearchHobby=false;
      this.isSearchPeople=true;
    }
  },
    components:{
        Searchselect,
        SearchHobby,
        Searchpeople
    }
}
</script>
<style scoped>
.fixed-tabs {
  position: fixed;
  top:70px;
  left: 257px;
  right: 0;

}

.content {
  margin-top: 56px; /* 탭의 높이만큼 여백을 주어 콘텐츠가 겹치지 않도록 합니다. */
}
</style>