<template>
  <v-row>
    <!--게시글 순회시작-->
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
        class="image-container"
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
        <div class="overlay">
          <v-icon class="overlay-icon">mdi-heart</v-icon>
          123
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-icon class="overlay-icon">mdi-comment</v-icon>
          123
        </div>
      </v-img>
    </v-col>

    <!--순회 종료-->
  </v-row>
</template>

<script>
export default {
  data() {
    return {};
  }
}
</script>

<style scoped>
.image-container {
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease;
}

.image-container:hover {
  transform: scale(1.05);
  cursor: pointer; /* 사진 호버 시 포인터 커서 */
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5); /* 사진 어두워지기 */
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.image-container:hover .overlay {
  opacity: 1;
}

.overlay-icon {
  font-size: 48px; /* 아이콘 크기 조정 */
  color: white; /* 아이콘 색상 */
  margin: 0 10px;
  cursor: default; /* 아이콘에 기본 커서 적용 */
}


</style>
