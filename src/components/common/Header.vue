<template>
  <v-card-text>
    <div class="search-container">
      <v-text-field
        :loading="loading"
        append-inner-icon="mdi-magnify"
        density="compact"
        label="검색어를 입력하세요"
        variant="solo"
        hide-details
        single-line
        @click:append="onClick"
        @input="onInput"
        v-model="query"
      ></v-text-field>
<v-divider></v-divider>
      <!-- 추천 목록이 검색바 아래에 자연스럽게 나타나도록 스타일 조정 -->
      <v-list v-if="filteredSuggestions.length > 0" class="suggestions-list">
        <v-list-item-group
          v-for="(suggestion, index) in filteredSuggestions"
          :key="index"
          @click="selectSuggestion(suggestion)"
        >
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>{{ suggestion }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </div>
  </v-card-text>
</template>

<script>
export default {
  data() {
    return {
      query: '',
      loading: false,
      suggestions: ['축구', '농구', '리그오브레전드', '카페', '공부'], // 추천 목록 예시
      filteredSuggestions: [],
    }
  },

  methods: {
    onClick() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 2000);
    },

    onInput() {
      const value = this.query; // v-model로 바인딩된 query 값을 사용합니다.
      if (typeof value === 'string') {
        this.filteredSuggestions = this.suggestions.filter(suggestion =>
          suggestion.toLowerCase().includes(value.toLowerCase())
        );
      } else {
        this.filteredSuggestions = [];
      }
    },

    selectSuggestion(suggestion) {
      this.query = suggestion;
      this.filteredSuggestions = [];
    }
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
