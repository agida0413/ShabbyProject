<template lang="">
    <div class="fixed-tabs" >
      <v-tabs
      class="to-blackMode"
      fixed-tabs
      
    >
      <v-tab @click="changeType('hobby')" >관심사 검색</v-tab>
      <v-tab @click="changeType('member')" >사람 검색</v-tab>
    </v-tabs>  
   
    </div>
   
    <div style="margin-top:30px;">
     
      
    <SearchHobby v-if="childType==='hobby'"
    :keyword="keyword"
    ></SearchHobby>
    <Searchpeople v-if="childType==='member'"
    :keyword="keyword"
    :type="childType"
    ></Searchpeople>
    </div>
</template>
<script>

import SearchHobby from './SearchHobby.vue';  //관심사 검색 컴포넌트
import Searchpeople from './Searchpeople.vue'; //사람검색 컴포넌트 

export default {
  props:{
    type:String,
    keyword:String
  },
  
  data(){
return{
childType:''
}
  },
  methods:{
 changeType(type){
  this.childType = type;
 }
  },
    components:{
     
        SearchHobby,
        Searchpeople
    }
}
</script>
<style scoped>


.content {
  margin-top: 56px; /* 탭의 높이만큼 여백을 주어 콘텐츠가 겹치지 않도록 합니다. */
}
</style>