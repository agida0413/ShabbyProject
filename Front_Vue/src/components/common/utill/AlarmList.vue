<template>
    <div class="text-center ">
        <v-dialog v-model="localDialog">
   
      <v-card class="mx-auto to-blackMode" width="700">
        
       

        <v-btn
          style="margin-left: 560px; margin-top: 10px; background-color: gray;"
          icon
          class="close-btn"
          size="30"
          @click="closeDialog()"
       
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>

     
      

        <v-col style="display: flex; align-items: center; justify-content: center;">
          <v-divider></v-divider>        
        </v-col>

        

        <div class="scroll-container" >
             
          <v-list  lines="three" class="to-blackMode pa-3">
            <v-list-item>
           
              <v-list-item-content >
               
                <v-row v-for="(alarm, index) in alarmData" :key="index">
                  <v-col cols="2"  style="cursor:pointer" >
                    <v-avatar :image="alarm.senderProfile" size="35" class="avatar"
                    v-if="alarm.senderProfile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="35" class="avatar" v-if="alarm.senderProfile === null" ></v-avatar>
                  </v-col>

                  <v-col cols="6" style="cursor:pointer" >
                     
                      <span class="large-font" >{{ alarm.sender }}</span>
                   
                  </v-col>

                  
                </v-row>
              </v-list-item-content>
            
            </v-list-item>
            
          </v-list>
          
       
          <div ref="sentinel" class="sentinel"></div>
        </div>
      </v-card>
 
    </v-dialog>
    </div>
    
  </template>
<script>
import api from "@/api"
export default {
    name:'AlarmList',
    props: {
       value: {//사이드메뉴로부터  받은 모달 불리안값 
         type: Boolean,
         required: true
       }     
      },
      computed:{
    // 현재 사이드 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
     localDialog:{
         get(){
       
             return this.value 
         }
     }
  },
  watch:{
    value(){
        this.alarmGet()
    }
  },
  data(){
    return{
        alarmData:[]
    }
  },
  methods:{
    closeDialog(){
        this.$emit('closeAlarm');
    },
    alarmGet(){
      api.get('/alarm')
      .then((res)=>{
        this.alarmData=res?.data?.reqData
      })
      .catch((err)=>{
        console.log(err)
      })
    }
  }
}
</script>
<style lang="">
    
</style>