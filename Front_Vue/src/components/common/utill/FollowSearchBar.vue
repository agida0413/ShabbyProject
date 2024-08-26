<template>
  <div
    class="autocomplete-container"
    ref="container"
    @keydown.arrow-down="handleArrowDown"
    @keydown.arrow-up="handleArrowUp"
    @keydown.enter="handleEnter"
    tabindex="0"
  >
    <ul v-show="results.length" class="results-list">
      <li
        v-for="(result, index) in results"
        :key="index"
        class="result-item"
        :class="{ selected: index === selectedIndex }"
        @click="handleClick(result.nickname)"
        @mouseover="handleMouseOver(index)"
        :ref="index === selectedIndex ? 'selectedItem' : null"
      >
        {{ result.nickname }}
      </li>
      <li ref="sentinel" class="result-item sentinel"></li>
    </ul>
    <div v-if="isFetching" class="loading-indicator">
      <v-progress-circular indeterminate color="primary" size="30"></v-progress-circular>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted, watch, nextTick } from 'vue';
import api from "@/api";
import debounce from 'lodash/debounce';

export default {
  props: {
    keyword: String,
    isAt: Boolean,
  },

  setup(props, { emit }) {
    const results = ref([]); // 검색 결과를 담는 배열
    const container = ref(null); // 검색 결과 컨테이너를 참조
    const sentinel = ref(null); // 무한 스크롤을 위한 Sentinel 참조
    const selectedIndex = ref(-1); // 현재 선택된 인덱스
    const previousIndex = ref(-1); // 이전 인덱스 저장
    const isFetching = ref(false); // 데이터 가져오고 있는지 여부
    const page = ref(1); // 페이지 번호

    // 결과를 서버에서 가져오는 함수
    const fetchResults = async (keyword, page) => {
      if (isFetching.value || !props.isAt) return; // 이미 데이터 가져오는 중이거나 @가 아닌 경우
      if (keyword === '@') return; //  단지 '@'인 경우 아무 작업도 하지 않음
      isFetching.value = true; // 데이터 가져오기 시작

      const sendKeyword = keyword.substring(1); // '@' 제거
      const rowSize=4
      try {
        const res = await api.get(`/members/following/${sendKeyword}/${page}/${rowSize}`); // API 호출
        const newFollows = res.data.reqData.followList; // 새로운 결과 리스트
        if (newFollows.length) {
          results.value = [...results.value, ...newFollows]; // 기존 결과에 새로운 결과 추가
        }
      } catch (error) {
        console.error('Error fetching results:', error); // 오류 발생 시 로그 출력
      } finally {
        isFetching.value = false; // 데이터 가져오기 종료
      }
    };

    // 디바운스된 결과 가져오기 함수
    const debouncedFetchResults = debounce((keyword, page) => {
      fetchResults(keyword, page); // 디바운스 처리 후 fetchResults 호출
    }, 150);

    // IntersectionObserver 콜백 함수
    const handleIntersection = (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && entry.target === sentinel.value) { // Sentinel이 보일 때
          page.value += 1; // 페이지 증가
          debouncedFetchResults(props.keyword, page.value); // 결과 가져오기
        }
      });
    };

    // 키워드가 변경될 때 호출되는 함수
    watch(() => props.keyword, (newKeyword) => {
      if (newKeyword && props.isAt) {
        page.value = 1; // 페이지 1로 초기화
        results.value = []; // 결과 배열 초기화
        selectedIndex.value = -1; // 선택된 인덱스 초기화
        previousIndex.value = -1; // 이전 인덱스 초기화
        debouncedFetchResults(newKeyword, page.value); // 결과 가져오기
      } else {
        results.value = []; // 결과 배열 초기화
        selectedIndex.value = -1; // 선택된 인덱스 초기화
        previousIndex.value = -1; // 이전 인덱스 초기화
      }
    });

    // 컴포넌트가 마운트될 때 호출되는 함수
    onMounted(async () => {
      if (props.isAt && container.value) {
        await nextTick(); // 다음 틱을 기다림
        if (sentinel.value) {
          const observer = new IntersectionObserver(handleIntersection, {
            root: container.value, // 관찰할 컨테이너
            rootMargin: '0px',
            threshold: 1.0, // 100% 노출 시 콜백 호출
          });
          observer.observe(sentinel.value); // Sentinel 관찰 시작

          onUnmounted(() => {
            observer.unobserve(sentinel.value); // 컴포넌트 언마운트 시 관찰 중지
          });
        }
      }
    });

    // 선택된 항목으로 스크롤 이동하는 함수
    const scrollToSelectedItem = () => {
      const selectedItem = container.value?.querySelector('.selected'); // 선택된 항목 찾기
      if (selectedItem && container.value) {
        const itemHeight = selectedItem.offsetHeight; // 항목의 높이
        const containerHeight = container.value.clientHeight; // 컨테이너의 높이
        const scrollTop = container.value.scrollTop; // 현재 스크롤 위치
        const selectedItemTop = selectedItem.offsetTop; // 선택된 항목의 상단 위치

        if (selectedIndex.value === 0) { // 첫 번째 항목일 때
          if (previousIndex.value !== -1 && previousIndex.value < selectedIndex.value) {
            const previousItem = container.value?.querySelectorAll('.result-item')[previousIndex.value]; // 이전 항목 찾기
            if (previousItem) {
              container.value.scrollTop = previousItem.offsetTop; // 이전 항목으로 스크롤 이동
            }
          } else {
            container.value.scrollTop = 0; // 첫 번째 항목은 컨테이너 상단으로 이동
          }
        } else if (selectedIndex.value === results.value.length - 1) { // 마지막 항목일 때
          container.value.scrollTop = container.value.scrollHeight; // 컨테이너 하단으로 이동
        } else { // 일반 항목일 때
          if (selectedItemTop < scrollTop) {
            container.value.scrollTop = selectedItemTop - itemHeight * 2.5; // 2칸 반 이동
          } else if (selectedItemTop + itemHeight > scrollTop + containerHeight) {
            container.value.scrollTop = selectedItemTop + itemHeight * 2.5 - containerHeight; // 2칸 반 이동
          }
        }

        // 스크롤 위치가 범위를 벗어나지 않도록 조정
        if (container.value.scrollTop < 0) {
          container.value.scrollTop = 0;
        }
        if (container.value.scrollTop > container.value.scrollHeight - containerHeight) {
          container.value.scrollTop = container.value.scrollHeight - containerHeight;
        }
      }
    };

    // 아래 방향 화살표 키 처리 함수
    const handleArrowDown = () => {
      if (results.value.length === 0) return; // 결과가 없으면 아무 작업도 안 함

      previousIndex.value = selectedIndex.value; // 이전 인덱스 업데이트
      if (selectedIndex.value === -1) {
        selectedIndex.value = 0; // 선택된 항목이 없으면 첫 번째 항목 선택
      } else {
        selectedIndex.value = (selectedIndex.value + 1) % results.value.length; // 다음 항목 선택
      }
      scrollToSelectedItem(); // 선택된 항목으로 스크롤 이동
      checkAndLoadMore(); // 추가 데이터 로드 여부 확인
    };

    // 위 방향 화살표 키 처리 함수
    const handleArrowUp = () => {
      if (results.value.length === 0) return; // 결과가 없으면 아무 작업도 안 함

      previousIndex.value = selectedIndex.value; // 이전 인덱스 업데이트
      
      if (selectedIndex.value === 0) {
        selectedIndex.value = results.value.length - 1; // 첫 번째 항목에서 위로 이동 시 마지막 항목 선택
      } else if (selectedIndex.value === -1) {
        selectedIndex.value = results.value.length - 1; // 선택된 항목이 없으면 마지막 항목 선택
      } else {
        selectedIndex.value = (selectedIndex.value - 1 + results.value.length) % results.value.length; // 이전 항목 선택
      }
      scrollToSelectedItem(); // 선택된 항목으로 스크롤 이동
      checkAndLoadMore(); // 추가 데이터 로드 여부 확인
    };

    // Enter 키 처리 함수
    const handleEnter = () => {
      if(props.isAt){
        if (selectedIndex.value >= 0 && selectedIndex.value < results.value.length) {
        handleClick(results.value[selectedIndex.value].nickname); // 선택된 항목 클릭 처리
      }
      }
    
    };

    // 항목 클릭 처리 함수
    const handleClick = (follow) => {
      emit('selectFollow', follow); // 선택된 팔로우인원 전달
    };

    // 항목 마우스 오버 처리 함수
    const handleMouseOver = (index) => {
      previousIndex.value = selectedIndex.value; // 이전 인덱스 업데이트
      selectedIndex.value = index; // 현재 인덱스 업데이트
      scrollToSelectedItem(); // 선택된 항목으로 스크롤 이동
    };

    // 스크롤 위치에 따라 추가 데이터 로드 여부 확인 함수
    const checkAndLoadMore = () => {
      const containerElement = container.value;
      const selectedItem = containerElement?.querySelector('.selected'); // 선택된 항목 찾기
      if (selectedItem) {
        const containerRect = containerElement.getBoundingClientRect(); // 컨테이너의 위치 및 크기
        const itemRect = selectedItem.getBoundingClientRect(); // 선택된 항목의 위치 및 크기

        if (itemRect.bottom > containerRect.bottom - 50) { // 항목이 컨테이너 하단 가까이에 있을 때
          if (!isFetching.value) {
            page.value += 1; // 페이지 증가
            debouncedFetchResults(props.keyword, page.value); // 결과 가져오기
          }
        }

        if (itemRect.top < containerRect.top + 50) { // 항목이 컨테이너 상단 가까이에 있을 때
          if (!isFetching.value) {
            page.value += 1; // 페이지 증가
            debouncedFetchResults(props.keyword, page.value); // 결과 가져오기
          }
        }
      }
    };

    return {
      results,
      container,
      sentinel,
      isFetching,
      selectedIndex,
      handleArrowDown,
      handleArrowUp,
      handleEnter,
      handleClick,
      handleMouseOver
    };
  }
};
</script>

<style scoped>
.selected {
  background-color: black; 
}
.results-list {
  margin: 0;
  padding: 0;
  list-style-type: none;
  max-height: 300px; 
}
.result-item {
  padding: 8px; 
  cursor: pointer; 
}
.result-item:hover {
  background-color: #e0e0e0; 
}
.sentinel {
  height: 20px; 
  background: transparent; 
}
.loading-indicator {
  text-align: center;
  padding: 10px;
  color: #888;
}
</style>
