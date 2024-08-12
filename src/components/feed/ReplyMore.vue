<template>
    <v-card
      class="mx-auto mt-5 ml-2 to-blackMode"
      width="100%"
    >
      <v-list lines="two" v-for="i in 4" :key="i" class="to-blackMode">
        <v-list-item
          prepend-avatar="https://cdn.vuetifyjs.com/images/lists/1.jpg"
          title="Brunch this weekend?"
          class="to-blackMode"
        >
          <template v-slot:subtitle>
            <span class="font-weight-bold">Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?
          </template>
        </v-list-item>
  
        <v-divider inset></v-divider>
      </v-list>
  
      <v-row class="input-row">
        <v-col cols="2" class="mb-6">
          <v-btn icon="mdi-arrow-left" size="50" color="#757575"></v-btn>
        </v-col>
  
        <v-col cols="10">
          <v-textarea
            class="mx-2"
            label="댓글 입력"
            rows="1"
            append-inner-icon="mdi-comment"
            @click:append-inner="handleAppendIconClick" 
          ></v-textarea>
        </v-col>
      </v-row>
    </v-card>
  </template>
  
  <script>
  export default {
    name: 'ReplyComponent',
    methods: {
      handleAppendIconClick() {
        // 댓글입력 창 안 아이콘 클릭시 발생되는 이벤트
        alert('Inner icon clicked!');
      }
    }
  }
  </script>
  
  <style scoped>
  .input-row {
    align-items: center; 
  }
  
  .v-textarea .v-input__append-inner {
    cursor: pointer; 
  }
  
  .v-textarea .v-input__append-inner:hover {
    color: #1976d2; 
  }
  </style>
  