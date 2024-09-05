<template>
  <v-card-text>
    <div class="search-container">
      <v-text-field
        
        append-inner-icon="mdi-magnify"
        density="compact"
        label="검색어를 입력하세요"
        variant="solo"
        hide-details
        single-line

        v-model="keyword"
      ></v-text-field>
<v-divider></v-divider>
      <!-- 추천 목록이 검색바 아래에 자연스럽게 나타나도록 스타일 조정 -->
     <GlobalSearchBar :keyword="keyword" style="background-color: darkgrey;"></GlobalSearchBar>
    </div>
  </v-card-text>
</template>

<script>
export default {
  data() {
    return {
      keyword:'',
      loading: false
      
    }
  },

  methods: {
    

    

  }
}
</script>

<style scoped>
.search-container {
  position: relative; /* 검색바와 추천 목록이 서로 겹치지 않도록 위치 설정 */
}

.v-text-field {
  width: 100%; /* 검색바가 컨테이너의 전체 너비를 차지하도록 설정 */
}

.suggestions-list {
  
  max-height: 200px;
  overflow-y: auto; /* 세로 스크롤바가 필요할 때만 표시 */
  background-color: white;
  border: 1px solid #ccc; /* 추천 목록의 경계선 색상 */
  border-top: none; /* 검색바와 추천 목록 사이의 경계선 제거 */
  position: absolute;
  top: 100%; /* 검색바 아래쪽에 위치하도록 설정 */
  left: 0;
  width: 100%; /* 검색바와 같은 너비를 가지도록 설정 */
  z-index: 11; /* 검색바 위에 표시되도록 설정 */
}

.v-list-item {
  cursor: pointer;
}

.v-list-item:hover {
  background-color: #f0f0f0; /* 추천 목록 아이템의 hover 색상 */
}
</style>
