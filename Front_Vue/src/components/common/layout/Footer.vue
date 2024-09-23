<template>
    <div class="text-center ">
     
      <v-bottom-sheet v-model="sheet">
        <v-card
          class="text-center to-blackMode2"
          height="250"
        >
            <v-card-text>
            
   
    
            <v-row style="margin-bottom: 3px;" class="align-center">
           
     
              <v-col
  style="font-size: 18px; font-weight: bold; display: flex;"
  
>
  <v-col style="margin-left: 200px;">회원님을 위한 추천 팔로우</v-col>
  <span>
    <v-checkbox label="오늘 하루 보지않기" v-model="todayNosee" style="font-size: 10px;"/>
  </span>
  <span style="margin-top: 7px;">
    <v-btn
      variant="text"
      @click="close"
      style="font-size: 18px;"
      color="red"
    >
      닫기
    </v-btn>
  </span>
</v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row style="margin-top: 8px;" v-if="!recommnedData.length">
              <v-col >
                추천할 만한 데이터가 부족합니다. 관심사를 등록 해 보세요.
              </v-col>
            </v-row>
             <v-row style="margin-top: 8px;" v-if="recommnedData.length">
             
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
  import Cookies from "js-cookie";
    export default {
     data(){
      return{
        sheet: false,
        recommnedData:[],
        todayNosee:false,
        noSeeCookie:''
      }
     },
     
  watch: {
    '$route.path'(newPath) {
      const check=Cookies.get('todatNoSee')
      if(check==='YES'){
        return
      }
    
      this.sheet = newPath === '/';
      this.getRecommend()
    }
  },

  created() {
    const check=Cookies.get('todatNoSee')
      if(check==='YES'){
        return
      }
   
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
   },
   close(){
    if(this.todayNosee){
      Cookies.set('todatNoSee','YES',{expires:1})
    }
    this.todayNosee=false;
    this.sheet=false
   }
  }
    }
  </script>