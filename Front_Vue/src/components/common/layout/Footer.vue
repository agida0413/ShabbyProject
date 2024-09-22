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
  
            <div>
              <span v-for="(follow,index) in recommnedData " :key="index">
              <v-col cols="2"  style="cursor:pointer" >
                    <v-avatar :image="follow.profile" size="35" class="avatar"
                    v-if="follow.profile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="35" class="avatar" v-if="follow.profile === null" ></v-avatar>
                  </v-col>

                  <v-col cols="6" style="cursor:pointer" >
                     
                      <span class="large-font" >{{ follow.nickname }}</span>
                   
                  </v-col>
                </span>
            </div>
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
    }
  }
    }
  </script>