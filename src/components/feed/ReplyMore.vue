<template>
    <v-card
      class="mx-auto mt-5 ml-2 to-blackMode"
      width="100%"
    >
    
    <v-list lines="two" class="to-blackMode" v-for="i in 2" :key="i">
      <v-list-item
      
        prepend-avatar="https://cdn.vuetifyjs.com/images/lists/1.jpg"
        title="Brunch this weekend?"
        
      >
        <template v-slot:subtitle>
          
          <v-row no-gutters>
            <v-col cols="9">
              <span class="item-text">
                I'll be in your neighborhood doing errands this weekend. Do you want to hang out?asdddddddddddddddddddddddddddddd
              </span>
              <div class="post-date"> <span style="margin-right: 10px;">2024-08-13 | 09:30</span><v-icon >mdi-pin-outline</v-icon> 작성자가 고정함</div> <!-- 작성일시 -->
            </v-col>
            <v-col cols="3" class="action-buttons">
              <span class="like-button">
                <v-btn
                  icon
                  size="50"
                  color="white"
                >
                  <v-icon>mdi-heart-outline</v-icon>
                  <span class="like-count">42</span> <!-- 숫자를 직접 입력합니다 -->
                </v-btn>
              </span>
              <span class="delete-button">
                <v-btn
                  icon
                  color="red"
                  size="30"
                >
                  <v-icon>mdi-delete-outline</v-icon>
                </v-btn>
              </span>
              <span class="pin-button">
                <v-btn
                  icon
                  size="30"
                  color="grey"
                >
                  <v-icon>mdi-pin-outline</v-icon> <!-- 고정 핀 아이콘 -->
                </v-btn>
              </span>
            </v-col>
          </v-row>
        </template>
      </v-list-item>



      
      <v-divider inset></v-divider>
    </v-list>

    <!--대 댓글 예시-->
   
    <v-list-item
        elevation="3"
        rounded
      prepend-avatar="https://cdn.vuetifyjs.com/images/lists/1.jpg"
      title="Brunch this weekend?"
      style="background-color: darkslategray; margin-left: 30px;"
      
      >
      <template v-slot:subtitle >
        
        <v-row no-gutters >
          <v-col cols="9">

            <span class="item-text">
              I'll be in your neighborhood doing errands this weekend. Do you want to hang out?asdddddddddddddddddddddddddddddd
            </span>
            <div class="post-date"> <span style="margin-right: 10px;">2024-08-13 | 09:30</span><v-icon >mdi-pin-outline</v-icon> 작성자가 고정함</div> <!-- 작성일시 -->
          </v-col>
          <v-col cols="3" class="action-buttons">
            <span class="like-button">
              <v-btn
                icon
                size="50"
                color="white"
              >
                <v-icon>mdi-heart-outline</v-icon>
                <span class="like-count">42</span> <!-- 숫자를 직접 입력합니다 -->
              </v-btn>
            </span>
            <span class="delete-button">
              <v-btn
                icon
                color="red"
                size="30"
              >
                <v-icon>mdi-delete-outline</v-icon>
              </v-btn>
            </span>
           
          </v-col>
        </v-row>
      </template>
    </v-list-item>

      <v-row class="input-row" style="margin-top: 10px;">
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
  .to-blackMode {
  background-color: #2C2C2C;
  color: aliceblue;
}

.item-text {
  display: block;
  margin-top: 8px;
  color: aliceblue;
}

.post-date {
  font-size: 12px;
  color: #757575;
  margin-top: 4px;
  font-weight: bold;
}

.action-buttons {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.like-button, .delete-button, .pin-button {
  margin: 0 4px;
}
  </style>
  