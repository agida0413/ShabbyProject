<template>
    <v-card
      class="mx-auto to-blackMode"
      width="100%"
      height="165px"
    >
      <v-list lines="two" class="to-blackMode">
        <v-list-item
          prepend-avatar="https://cdn.vuetifyjs.com/images/lists/1.jpg"
          title="Brunch this weekend?"
        >
          <template v-slot:subtitle>
            <span class="font-weight-bold">Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?
          </template>
        </v-list-item>
  
        <v-divider inset></v-divider>
        
        <div class="button-container">
          <v-btn
            size="60"
            class="load-more-btn "
            color="blue"
            icon="mdi-chat-outline"
            text
            @click="wantMoreReply()"
          >
            more.. <!--클릭시 더 많은 댓글 볼수 있음 -->
          </v-btn>
        </div>
      </v-list>
    </v-card>
  </template>
  
  <script>
  export default {
    name: 'ReplyComponent',
    methods:{
       wantMoreReply() {
        this.$emit('wantMoreReply');// 댓글 더보기를 원하는 이벤트 함수 전송
      }
    }
  }
  </script>
  
  <style scoped>
  .button-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 4px; 
  }
  
  .load-more-btn {
    display: flex;
    align-items: center;
    font-size: 12px;
    font-weight: bold;
  }
  
  .load-more-btn .v-btn__content {
    margin-left: 8px; 
  }
  </style>
  