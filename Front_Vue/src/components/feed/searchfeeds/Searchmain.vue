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
    type:String, //관심사 검색인지 사람검색인지 
    keyword:String // 검색어 
  },
  watch: {
    //새로운 검색 타입을 감지 (관심사 검색인지 사람검색인지 )
    type(newType) {
      this.selectedTab = newType; // prop 변경 시 탭 업데이트
    }
  },
  data(){
return{
selectedTab: this.type //탭 selected 표시 변수 
}
  },
  methods:{
    //관심사 검색 , 사람검색 변경 
 changeType(newtype){
  //새로운 타입으로 라우터 푸시 
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
  margin-top: 56px; 
}
</style>