<template>
    <div class="text-center">
     
      <v-bottom-sheet v-model="sheet">
        <v-card
          class="text-center"
          height="200"
        >
          <v-card-text>
            <v-btn
              variant="text"
              @click="sheet = !sheet"
            >
              close
            </v-btn>
  
            <br>
            <br>
  
            <div>
             팔로우 추천
            </div>
          </v-card-text>
        </v-card>
      </v-bottom-sheet>
    </div>
  </template>
  <script>
    export default {
      data: () => ({
        sheet: false,
      }),
  watch: {
    '$route.path'(newPath) {
      // This will be called whenever the route path changes
      this.sheet = newPath === '/userfeed';
    }
  },
  // Optionally, initialize `sheet` based on the current route when the component is created
  created() {
    this.sheet = this.$route.path === '/userfeed';
  }
    }
  </script>