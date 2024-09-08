<template lang="">
    <div class="fixed-tabs" >
      <v-tabs
      class="to-blackMode"
      fixed-tabs
      v-model="selectedTab"
    >
      <v-tab value="hobby" @click="changeType('hobby')" >관심사 검색</v-tab>
      <v-tab value="member" @click="changeType('member')" >사람 검색</v-tab>
    </v-tabs>  
   
    </div>
   
    <div style="margin-top:30px;">
     
      
    <SearchHobby v-if="type==='hobby'"
    :keyword="keyword"
      :type="type"
    ></SearchHobby>
    <Searchpeople v-if="type==='member'"
    :keyword="keyword"
      :type="type"
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
  watch: {
    type(newType) {
      this.selectedTab = newType; // prop 변경 시 탭 업데이트
    }
  },
  data(){
return{
childType:'',
selectedTab: this.type 
}
  },
  methods:{
 changeType(newtype){
  this.$router.push({
      name: 'searchfeed',
      params: { type: newtype, keyword: this.keyword }
    });
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