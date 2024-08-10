<template>
    

        
    <v-card
      class="pt-0"
      color="surface-light"
    height="70px"
    
    >
   
      <v-card-text
      
      >
        <v-text-field
          :loading="loading"
          append-inner-icon="mdi-magnify"
          density="compact"
          label="검색어를 입력하세요"
          variant="solo"
          hide-details
          single-line
          @click:append-inner="onClick"
          
        ></v-text-field>
      </v-card-text>
    </v-card>

  </template>
  <script>
    export default {
      data: () => ({
        loaded: false,
        loading: false,
      }),
  
      methods: {
        onClick () {
          this.loading = true
  
          setTimeout(() => {
            this.loading = false
            this.loaded = true
          }, 2000)
        },
      },
    }
  </script>