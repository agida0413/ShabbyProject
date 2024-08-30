<template>
   
    <!-- 팔로우/팔로워 목록-->
    
    <v-dialog v-model="localDialog"   >
        <v-card
    class="mx-auto to-blackMode"
    width="400"
  >
 
          <v-btn
          style="margin-left: 360px; margin-top: 10px; background-color: gray;"
          icon
          class="close-btn"
          size="30"
          @click="closeDialog()"
          >
          <v-icon >mdi-close</v-icon>
          </v-btn> 

          <span 
          style=" display: flex;
          font-size: 20px;
          align-items: center; /* 수직 중앙 정렬 */
          justify-content: center; /* 수평 중앙 정렬 (선택적) */">
          {{ flwType }} 
          </span>
            <v-col style=" display: flex;
                align-items: center; /* 수직 중앙 정렬 */
                justify-content: center; /* 수평 중앙 정렬 (선택적) */">

          
            <v-divider></v-divider>
              
            </v-col>

    <v-list lines="three" class="to-blackMode pa-3"> 
     <v-list-item>
                                
      <v-list-item-content>

        <v-row v-for="(follow,index) in FollowData" :key="index">
            <v-col cols="2">
          
              <v-avatar
                :image="follow.profile"
                size="35"
                class="avatar"
                v-if="follow.profile !== null"
              ></v-avatar>

           <v-avatar
            :image="require('@/assets/ikmyung.png')"
            size="35"
            class="avatar"
            v-if="follow.profile === null"
            ></v-avatar>


             
                              

            </v-col>

            <v-col cols="6">
              <span class="large-font">{{ follow.nickname }}</span>
            </v-col>

            <v-col cols="4">
              <v-btn color="primary">FOLLOW</v-btn>
            </v-col>
        </v-row>

        <!-- 순회부분 -->

      </v-list-item-content>
     </v-list-item>
    </v-list>
    
  </v-card>
     </v-dialog>
   </template>
   
   <script>
   import api from '@/api';
   export default {
     name: 'FlwListComponent',
   
 
     props: {
       value: {//유저피드로부터  받은 모달 불리안값 
         type: Boolean,
         required: true
       },
       flwType:{ // 팔로워 목록이냐 팔로잉 목록이냐
        type: String,
        required:true
       },
       nickname:{
        type:String,
        required:true 
       }
     
    },data(){
     return{
        isLoading:false,
        FollowData:[],
        page:1              
     }
    },
    watch: {
    value(newVal) {
      if(newVal===true){
        this.callFollowList()
      }
      
    }
  }
  ,computed:{
     localDialog:{
         get(){
             return this.value // 현재 아이디찾기 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
         }
     }
  }   
     ,
     methods: {
       closeDialog() {
         this.$emit('flwListClose');// 로그인 컴포넌트로 닫는 이벤트 전송
       },
       callFollowList(){
        if(this.isLoading){
          return
        }
       
        this.isLoading=true

        api.get(`/feed/userfeed/follow/${this.flwType}/${this.page}`)
        .then((res)=>{
          this.FollowData=res?.data?.reqData
          console.log(this.FollowData)
        })
        .catch((err)=>{
          alert(err?.response?.data?.message)
          this.closeDialog()
        })
        .finally(()=>{
          this.isLoading=false
        })

       }
     }
   }
   </script>
   <style>
.large-font {
  font-size: 18px; /* 원하는 폰트 사이즈로 조정 */
}
   </style>