<template>
    <v-dialog v-model="localDialog"  max-width="500" >
<!--찾은 아이디 목록-->
        <v-card 
        prepend-icon="mdi-account"
        title="아이디 찾기"
        class="pa-3"
      >
      <v-list>
      <v-list-subheader>REPORTS</v-list-subheader>

      <v-list-item
        v-for="(item, i) in items"
        :key="i"
        :value="item"
        color="primary"
        rounded="xl"
      >
        <template v-slot:prepend>
          <v-icon :icon="item.icon"></v-icon>
        </template>

        <v-list-item-title v-text="item.text"></v-list-item-title>
      </v-list-item>
    </v-list>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  export default {
  
    
}

 
  </script>