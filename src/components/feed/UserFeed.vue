<template>
    
         <v-row style=" " >

                         <!-- 프로필 사진--> 
                         <v-col cols="3">
                              <div class="avatar-wrapper">
                                        <v-avatar
                                             image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220"
                                             size="300"
                                             class="avatar"
                                        ></v-avatar>

                                        <button>
                                        <v-icon
                                        
                                             class="edit-icon"
                                        >mdi-pencil</v-icon>
                                        </button>
                              
                              </div>
                          </v-col>

                              
                    <v-col cols="3" >
                         <v-col cols="auto">
                                        <!--팔로우 , 언팔로우 버튼-->
                                   <v-btn elevation="16" width="300" color="primary">Follow</v-btn>
                                        <!--프로필 편집-->
                                        <v-row class="mt-2"> 
                                             <v-col>
                                                  <v-btn elevation="16" width="300" color="success">프로필 편집</v-btn>
                                             </v-col>        
                                        </v-row>
                                             <!--게시물, 팔로워 ,팔로잉 노출-->
                                        <v-row class="mt-5"> 

                                             <v-col cols="3">
                                                  게시물 0
                                             </v-col>    

                                             
                                             <v-col cols="3" class="isHover" @click="flwListDialogOpen('팔로워')">
                                                  팔로워 0
                                             </v-col>      
                                            

                                             <v-col cols="3" class="isHover" @click="flwListDialogOpen('팔로잉')">
                                                팔로잉 0
                                             </v-col>    

                                        </v-row>

                                        <v-row class="mt-8"> 
                                             <v-col >
                                                 닉네임
                                             </v-col>        
                                          
                                         </v-row>
                         </v-col>

                    </v-col >


                         <v-col cols="6" >
                              <v-card
                                   class="mx-auto to-blackMode"
                                   elevation="16"
                                   max-width="100%"
                                   height="100%"

                                   >
                                   <v-card-item>
                                        <v-card-title>
                                             자기소개
                                        </v-card-title>

                                        <v-card-subtitle>
                                        
                                        </v-card-subtitle>
                                   </v-card-item>

                                   <v-card-text>
                                      아직 작성된 소개글이 없습니다.
                                   </v-card-text>
                                   </v-card>
                         </v-col>
       </v-row>



       <!-- 관심사 해시태그-->
          <v-row style="">

                    <v-card
                    class="mx-auto to-blackMode"
                    width="100%"
                    
                    >
                          <template v-slot:title >
                         
                         <span class="font-weight-black ">관심사</span>
                         
                         </template>

                    <v-divider></v-divider>


                    <v-sheet class="py-4 px-1 " style="background-color: #2C2C2C;" >
                              <!--해시태그 -->
                              <v-chip-group
                                   selected-class="text-primary"
                                   mandatory
                              >
                                   <v-chip style="background-color:floralwhite;"
                                   v-for="tag in tags"
                                   :key="tag"
                                   :text="tag"
                                   ></v-chip>
                              </v-chip-group>

                    </v-sheet>
                    
                    </v-card>
          </v-row>

          
          <!-- 피드 게시글 -->
         <v-row class="mt-7"  > 
               <v-row>
                    <v-col
                         v-for="n in 9"
                         :key="n"
                         class="d-flex child-flex"
                         cols="4"
                    >
                              <v-img
                              :lazy-src="`https://picsum.photos/10/6?image=${n * 5 + 10}`"
                              :src="`https://picsum.photos/500/300?image=${n * 5 + 10}`"
                              aspect-ratio="1"
                              class="bg-grey-lighten-2"
                              cover
                              >
                                   <template v-slot:placeholder>
                                        <v-row
                                        align="center"
                                        class="fill-height ma-0"
                                        justify="center"
                                        >
                                                  <v-progress-circular
                                                  color="grey-lighten-5"
                                                  indeterminate
                                                  ></v-progress-circular>
                                        </v-row>
                                   </template>
                         </v-img>
                    </v-col>
               </v-row>
         </v-row>
   <FlwListComponent v-model:value="flwListDialog"
     v-bind:flwType="this.flwType"
   @flwListClose="flwListDialogClose()"></FlwListComponent>
         

  </template>
  <script>
  import FlwListComponent from "./flwlist.vue"
  export default {
    data: () => ({
    
      tags: [
        'Work',
        'Home Improvement',
        'Vacation',
        'Food',
        'Drawers',
        'Shopping',
        'Art',
        'Tech',
        'Creative Writing',
      ],
      flwListDialog:false,
      flwType:''
     
    }),
    components:{
     FlwListComponent
    },
    methods:{
     flwListDialogOpen(str){
          this.flwType=str
          console.log(this.flwType)
          this.flwListDialog=true;
     },
     flwListDialogClose(){
          this.flwListDialog=false;
     }

    }
  }
</script>
  <style>
 .avatar-wrapper {
  display: flex;
  align-items: flex-start; /* 상단에 정렬 */
  justify-content: center; /* 가운데 정렬 */
  position: relative;
  width: 300px; /* 프로필 사진의 너비와 동일하게 설정 */
  height: 300px; /* 프로필 사진의 높이와 동일하게 설정 */
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
  </style>