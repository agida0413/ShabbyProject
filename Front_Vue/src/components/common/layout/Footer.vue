<template>
    <div class="text-center ">
     
      <v-bottom-sheet v-model="sheet">
        <v-card
          class="text-center "
          height="200"
        >
          <v-card-text>
            <v-btn
              variant="text"
              @click="sheet = !sheet"
            >
              close
            </v-btn>
  
            <br>
            <br>
            <v-row style="margin-bottom: 10px;  ">
              <span style="font-size: 25px; font-weight: bold;" class="justify-center align-center">회원님을 위한 추천 팔로우</span>
            </v-row>
            <v-divider></v-divider>
             <v-row style="margin-top: 10px;">
              <v-col cols="4" v-for="(follow,index) in recommnedData " :key="index">
              
                 <span  style="cursor:pointer; margin-right:10px;"  @click="goOtherFeed(follow.nickname)">
                    <v-avatar :image="follow.profile" size="35" class="avatar"
                    v-if="follow.profile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="35" class="avatar" v-if="follow.profile === null" ></v-avatar>
                 </span>

                  <span style="cursor:pointer; margin-right:3px;" @click="goOtherFeed(follow.nickname)">
                     
                      <span class="large-font" >{{ follow.nickname }}</span>
                   
                  </span>
                  <span @click="goOtherFeed(follow.nickname)"> 
                    <span style="opacity: 0.7; font-size: 12px; cursor:pointer;" >(연관 관심사{{ follow.relateCount }}개)</span>
                  </span>
                </v-col>
              </v-row>
          </v-card-text>
        </v-card>
      </v-bottom-sheet>
    </div>
  </template>
  <script>
  import api from '@/api';
    export default {
     data(){
      return{
        sheet: false,
        recommnedData:[]
      }
     },
     
  watch: {
    '$route.path'(newPath) {
      
      this.sheet = newPath === '/';
      this.getRecommend()
    }
  },

  created() {
    this.sheet = this.$route.path === '/';
  },
  methods:{
    getRecommend(){
      api.get('/members/recommend')
      .then((res)=>{
        this.recommnedData=res?.data?.reqData
      })
      .catch((err)=>{
        if(err?.response?.data?.message){
          alert(err?.response?.data?.message)
        }
      })
    },
    goOtherFeed(nickname) {//닉네임 매개변수 


    this.$nextTick(() => {
      this.$router.push({ name: 'userfeed', params: { nickname: nickname } }); // 페이지 이동 ( 닉네임 param)
    });
}
  }
    }
  </script>