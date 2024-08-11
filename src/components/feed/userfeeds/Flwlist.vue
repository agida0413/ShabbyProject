<template>
   
    <!-- 팔로우/팔로워 목록-->
    
    <v-dialog v-model="localDialog"   >
        <v-card
    class="mx-auto"
    max-width="450"
  >
 
  <v-btn
  style="margin-left: 405px; background-color: burlywood;"
  icon
  class="close-btn"
  size="40"
  @click="closeDialog()"
>
  <v-icon>mdi-close</v-icon>
</v-btn>
  <v-col style=" display: flex;
        align-items: center; /* 수직 중앙 정렬 */
        justify-content: center; /* 수평 중앙 정렬 (선택적) */">

    <span >{{ flwType }}  </span>
   
      
    </v-col>

    <v-list lines="three">
    <v-list-item>
                                
      <v-list-item-content>

        <v-row>
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="35"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6">
            <span class="text-primary large-font">agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary">FOLLOW</v-btn>
          </v-col>
        </v-row>

        <!-- 순회부분 -->

        <v-row>
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="35"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6">
            <span class="text-primary large-font">agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary">FOLLOW</v-btn>
          </v-col>
        </v-row>
        

      </v-list-item-content>
    </v-list-item>
  </v-list>
    
  </v-card>
     </v-dialog>
   </template>
   
   <script>
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
       }
     
 },data(){
     return{
        
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
       }
     }
   }
   </script>
   <style>
.large-font {
  font-size: 18px; /* 원하는 폰트 사이즈로 조정 */
}
   </style>