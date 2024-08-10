<template>
    
         <v-row style=" " >
                    <v-col cols="3" >
                         <v-avatar image="https://tse2.mm.bing.net/th?id=OIP.kKqmVQKQfggQbuEborDDAAHaFa&pid=Api&P=0&h=220" size="300" style="margin-left: 30px;"></v-avatar> <!--프로필 ,닉네임 -->

                    </v-col >
                    <v-col cols="3" >
                         <v-col cols="auto">
                                   <v-btn elevation="16" width="300" color="primary">Follow</v-btn>
                                        <v-row class="mt-2"> 
                                             <v-col>
                                                  <v-btn elevation="16" width="300" color="success">프로필 편집</v-btn>
                                             </v-col>        
                                        </v-row>

                                        <v-row class="mt-5"> 
                                             <v-col cols="3">
                                                  게시물 0
                                             </v-col>        
                                             <v-col cols="3">
                                                  팔로워 0
                                             </v-col>        
                                             <v-col cols="3">
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
                                   class="mx-auto "
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


          <v-row style="">

 <v-card
    class="mx-auto"
    width="100%"
  >
    <template v-slot:title >
   
      <span class="font-weight-black">관심사</span>
     
     </template>

    <v-divider></v-divider>
    <v-sheet class="py-4 px-1  " >
    <v-chip-group
      selected-class="text-primary"
      mandatory
    >
      <v-chip
        v-for="tag in tags"
        :key="tag"
        :text="tag"
      ></v-chip>
    </v-chip-group>
  </v-sheet>
 
  </v-card>
          </v-row>

          
         <v-row style=" "  > 
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
   
         

  </template>
  <script>
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
    }),
  }
</script>
  <style>
 
  </style>