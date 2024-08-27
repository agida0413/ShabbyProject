<template>
<v-row>
    <v-col
      v-for="n in 9"
      :key="n"
      class="d-flex child-flex"
      cols="4"
    >
      <v-img
        :lazy-src="`https://picsum.photos/10/6?image=${n * 5 + 10}`"
        src="https://picsum.photos/500/300?image="
        aspect-ratio="1"
        class="image-container"
        cover
      >
        <template v-slot:placeholder>
          <v-row align="center" class="fill-height ma-0" justify="center">
            <v-progress-circular color="grey-lighten-5" indeterminate></v-progress-circular>
          </v-row>
        </template>
        <div class="overlay">
          <v-icon class="overlay-icon">mdi-heart</v-icon>
          123
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-icon class="overlay-icon">mdi-comment</v-icon>
          123
        </div>
      </v-img>
    </v-col>
  </v-row>

</template>

<script>
export default{
    name:'UserFeedPostList'
}
</script>