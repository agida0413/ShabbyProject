<template >



  <v-card >
   
      <v-navigation-drawer
     style="background-color:ghostwhite"
       
      >
        <template v-slot:prepend >
          <v-list-item
      
          >
          <v-img
        class="mx-auto"
        max-width="60"
        src="@/assets/logo2.png"
      ></v-img> 
        
        </v-list-item>
        </template>

        <v-divider></v-divider>

        <v-list density="compact" nav>
        
          <v-list-item prepend-icon="mdi-account" title="새 게시물" value="account"></v-list-item>
          <v-list-item prepend-icon="mdi-account-group-outline" title="글로벌" value="users"></v-list-item>
          <v-list-item prepend-icon="mdi-account-group-outline" title="활동" value="users"></v-list-item>
          <v-list-item prepend-icon="mdi-home-city" title="설정" value="home"></v-list-item>
          <v-list-item prepend-icon="mdi-home-city" title="내 피드" value="home"></v-list-item>
          <v-list-item prepend-icon="mdi-account-group-outline" title="로그아웃" value="users"></v-list-item>
        </v-list>
      </v-navigation-drawer>
    
    
  </v-card>
</template>