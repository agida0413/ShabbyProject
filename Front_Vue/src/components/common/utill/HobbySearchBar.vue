<template>
    <div class="autocomplete-container" ref="container">
      <ul v-if="results.length" class="results-list">
        <li
          v-for="(result, index) in results"
          :key="index"
          class="result-item"
        >
          {{ result.hobby }}
        </li>
        <!-- 스크롤 끝을 감지하기 위한 더미 요소 -->
        <li ref="sentinel" class="result-item sentinel">loading</li>
      </ul>
      <!-- 로딩 인디케이터 -->
      <div v-if="isFetching" class="loading-indicator ">
        <v-progress-circular indeterminate color="primary" size="30"></v-progress-circular>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted, onUnmounted, watch } from 'vue';
  import api from "@/api";
  import debounce from 'lodash/debounce';
  
  export default {
    props: {
      keyword: String,
      isHashtag: Boolean
    },
    setup(props) {
      const results = ref([]);
      const container = ref(null); // 컨테이너를 참조
      const sentinel = ref(null); // 더미 요소를 참조
      const page = ref(1); // 페이지 변수를 자식 컴포넌트에서 관리
      const isFetching = ref(false); // 데이터 fetching 상태 관리
      const nodata = ref(false); // 데이터가 더 이상 없는 상태 관리
  
      // 데이터 요청 및 병합
      const fetchResults = async (keyword, page) => {
        if (isFetching.value || !props.isHashtag) return; // 이미 데이터를 가져오는 중이거나 isHashtag가 false이면 리턴
        isFetching.value = true; // 데이터 요청 중 상태로 변경
  
        try {
          const res = await api.get(`/hobby/${keyword}/${page}`);
          const newHobbies = res.data.reqData.findList;
          if (newHobbies.length) {
            results.value = [...results.value, ...newHobbies]; // 기존 결과와 새 결과 병합
          } else {
            nodata.value = true; // 더 이상 결과가 없음을 표시
          }
        } catch (error) {
          console.error('API 호출 실패:', error);
        } finally {
          isFetching.value = false; // 데이터 요청 완료 상태로 변경
        }
      };
  
      // 디바운스된 API 호출 메소드
      const debouncedFetchResults = debounce((keyword, page) => {
        fetchResults(keyword, page);
      }, 150); // 디바운스 시간 조정 (150ms로 설정)
  
      // Intersection Observer를 통한 페이지 로딩
      const handleIntersection = (entries) => {
        console.log('실행')
        entries.forEach((entry) => {
         
          if (entry.isIntersecting && entry.target === sentinel.value) {
            console.log('Sentinel is intersecting');
            page.value += 1; // 페이지 업데이트
            console.log(page.value)
            debouncedFetchResults(props.keyword, page.value); // 새 페이지의 결과를 가져옵니다.
          }
        });
      };
  
      // 검색어가 변경될 때 처리
      watch(() => props.keyword, (newKeyword) => {
        if (newKeyword && props.isHashtag) {
          page.value = 1; // 키워드가 변경되면 페이지를 1로 초기화
          results.value = []; // 기존 결과 초기화
          nodata.value = false; // 데이터가 더 이상 없음 상태 초기화
          debouncedFetchResults(newKeyword, page.value);
        } else {
          results.value = []; // 키워드가 없으면 결과도 초기화
          nodata.value = false; // 데이터가 더 이상 없음 상태 초기화
        }
      });
  
      // 컴포넌트가 마운트되었을 때
      onMounted(() => {
        
        if (props.isHashtag) {
            console.log('Container element:', container.value); // 확인
    console.log('Sentinel element:', sentinel.value); // 확인

           
          // Intersection Observer 설정
          const observer = new IntersectionObserver(handleIntersection, {
            root: container.value,
            rootMargin: '0px',
            threshold: 0.1, // 조정된 threshold (10% 영역이 보일 때 트리거)
          });
          if (sentinel.value) {
          observer.observe(sentinel.value);
        } else {
          console.error('Sentinel element is null');
        }
          if (sentinel.value) {
            observer.observe(sentinel.value); // 스크롤 끝을 감지할 더미 요소 관찰
          }
        }
      });
  
      // 컴포넌트가 언마운트될 때
      onUnmounted(() => {
        if (sentinel.value) {
          const observer = new IntersectionObserver(() => {});
          observer.unobserve(sentinel.value);
        }
      });
  
      return {
        results,
        container,
        sentinel,
        nodata,
        isFetching
      };
    },
  };
  </script>
  
  <style>
 
  .results-list {
    margin: 0;
    padding: 0;
    list-style-type: none;
  }
  
  .result-item {
    padding: 8px;
    cursor: pointer;
  }
  
  .result-item:hover {
    background-color: #f0f0f0;
  }
  
  .sentinel {
    height: 20px; /* 높이를 충분히 크게 설정 */
    background: transparent; /* 배경을 투명으로 설정하여 시각적으로 방해하지 않음 */
  }
  
  .loading-indicator {
    text-align: center;
    padding: 10px;
    color: #888;
  }
  </style>
  