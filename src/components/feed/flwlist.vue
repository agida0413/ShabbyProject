<template>
   
    <!-- 아이디 찾기 모달-->
    
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

    <v-list
      :items="items"
      lines="three"
      item-props
    >
      <template v-slot:subtitle="{ subtitle }">
        <div v-html="subtitle"></div>
      </template>
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
        items: [
        
        {
          prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          title: 'Brunch this weekend?',
          subtitle: `<span class="text-primary">Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
        },
        { type: 'divider', inset: true },
        {
          prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
          title: 'Summer BBQ',
          subtitle: `<span class="text-primary">to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend.`,
        },
        { type: 'divider', inset: true },
        {
          prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
          title: 'Oui oui',
          subtitle: '<span class="text-primary">Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?',
        },
        { type: 'divider', inset: true },
        {
          prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
          title: 'Birthday gift',
          subtitle: '<span class="text-primary">Trevor Hansen</span> &mdash; Have any ideas about what we should get Heidi for her birthday?',
        },
        { type: 'divider', inset: true },
        {
          prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
          title: 'Recipe to try',
          subtitle: '<span class="text-primary">Britta Holt</span> &mdash; We should eat this: Grate, Squash, Corn, and tomatillo Tacos.',
        },
      ]
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