<template lang="">
     <v-row>
     <!--팔로잉 추천 목록-->
     <v-col cols="6" style="border-right:2px white solid;">

        <v-list lines="three"  class="to-blackMode">
    <v-list-item>
                                
      <v-list-item-content>
        <v-divider></v-divider>
        <v-row class="mt-1 mb-1">
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="80"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6" class="mt-5">
            <span class="large-font ">agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary" class="mt-5">FOLLOW</v-btn>
          </v-col>
        </v-row>
      
        <!-- 순회부분 -->
        <v-divider></v-divider>
        <v-row  class="mt-1 mb-1">
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="80"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6" class="mt-5" >
            <span class=" large-font" >agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary" class="mt-5">FOLLOW</v-btn>
          </v-col>
        </v-row>
        
        <v-divider></v-divider>
        <v-row  class="mt-1 mb-1">
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="80"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6" class="mt-5" >
            <span class=" large-font" >agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary" class="mt-5">FOLLOW</v-btn>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row  class="mt-1 mb-1">
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="80"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6" class="mt-5" >
            <span class=" large-font" >agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary" class="mt-5">FOLLOW</v-btn>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row  class="mt-1 mb-1">
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="80"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6" class="mt-5" >
            <span class=" large-font" >agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary" class="mt-5">FOLLOW</v-btn>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row  class="mt-1 mb-1">
           <v-col cols="2">
        
               <v-avatar
                  image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                  size="80"
                  class="avatar"
               ></v-avatar>
                            

          </v-col>

          <v-col cols="6" class="mt-5" >
            <span class=" large-font" >agida0413</span>
          </v-col>
          <v-col cols="4">
            <v-btn color="primary" class="mt-5">FOLLOW</v-btn>
          </v-col>
        </v-row>


        <!-- 페이징-->
         <v-divider></v-divider>
         <div class="text-center">
    <v-pagination
      v-model="page"
      :length="15"
      :total-visible="7"
    ></v-pagination>
  </div>
      </v-list-item-content>
    </v-list-item>
  </v-list>
        
     </v-col>
    
     <!--미리보기-->
     <v-col cols="6"   >
        <!--프로필사진 -->
        <v-row> 
            
            <div class="avatar-wrapper ">
                         <v-avatar
                               image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                               size="300"
                               class="avatar"
                         ></v-avatar>
              
               </div>
                      
                           
          </v-row>
          <v-row>
            <v-col cols="2" class="mt-16" style="margin-left:225px">
                                게시물 0
                            </v-col>    


                            <v-col cols="2" class="isHover mt-16" @click="flwListDialogOpen('팔로워')">
                                팔로워 0
                            </v-col>      


                            <v-col cols="2" class="isHover mt-16" @click="flwListDialogOpen('팔로잉')">
                            팔로잉 0
                            </v-col>   

          </v-row>
          <v-row >
            <v-col style="margin-left:360px">
            닉네임
            </v-col>
          
        </v-row>

        <v-row>
            <v-card
               class="mx-auto to-blackMode"
               elevation="16"
               style="width:600px; height:200px"
                                 

                 >
                    <v-card-item>
                     <v-card-title>
                                자기소개
                      </v-card-title>

                         
                     </v-card-item>

                         <v-card-text>
                           아직 작성된 소개글이 없습니다.
                         </v-card-text>
             </v-card>
         </v-row>
                         

         <v-row>
            <v-card
               class="mx-auto to-blackMode mt-4"
               elevation="16"
               style="width:600px; height:100px"
                                 

                 >
                    <v-card-item>
                     <v-card-title>
                                관심사
                      </v-card-title>

                         
                     </v-card-item>

                         <v-card-text>
                           
                         </v-card-text>
             </v-card>
         </v-row>
                          

     </v-col>
      
    </v-row>

    <FlwListComponent v-model:value="flwListDialog"
     v-bind:flwType="this.flwType"
   @flwListClose="flwListDialogClose()"></FlwListComponent>
         

</template>
<script>
import FlwListComponent from "../userfeeds/Flwlist.vue"
export default {
    data(){
        return{
            flwListDialog:false, //팔로우/팔로잉 목록 모달 제어값
            flwType:'', //팔로우 ? 팔로잉? 
            page:1
        }
    },components:{
        FlwListComponent
    },
    methods:{
        flwListDialogOpen(str){  //팔로워, 팔로잉 각각 버튼클릭시 str로 매개변수 받아와 flwType을 초기화 
                              //= > flwList 컴포넌트 하나로 운영 (str 전달- > 팔로잉 목록 or 팔로우 목록)
          this.flwType=str   
          this.flwListDialog=true; //모달 열음 
     },
     flwListDialogClose(){   //팔로우,팔로잉 목록닫음
          this.flwListDialog=false;
     }
    }
}
</script>
<style scoped>
.avatar-wrapper {
  display: flex;
  align-items: flex-start; /* 상단에 정렬 */
  justify-content: center; /* 가운데 정렬 */
  position: relative;
  width: 800px; /* 프로필 사진의 너비와 동일하게 설정 */
   /* 프로필 사진의 높이와 동일하게 설정 */
}

.avatar {
  width: 100%;
  height: 100%;
}

.edit-icon {
  position: absolute;
  right: 30px;
bottom: 10px;
  background-color: floralwhite;
  border-radius: 50%;
  padding: 21px;
  color: black; /* 아이콘 색상 조정 */
  font-size: 24px; /* 아이콘 크기 조정 */
}
.isHover{
     cursor: pointer; /* 커서를 손 모양으로 변경 */
}
.image-container {
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease;
}

.image-container:hover {
  transform: scale(1.05);
  cursor: pointer; /* 사진 호버 시 포인터 커서 */
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5); /* 사진 어두워지기 */
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.image-container:hover .overlay {
  opacity: 1;
}

.overlay-icon {
  font-size: 48px; /* 아이콘 크기 조정 */
  color: white; /* 아이콘 색상 */
  margin: 0 10px;
  cursor: default; /* 아이콘에 기본 커서 적용 */
}

</style>