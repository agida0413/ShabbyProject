<template >
  <div
    class="autocomplete-container"
    style="border-radius: 0px 0px 8px 8px;"
    ref="container"
    @keydown.arrow-down="handleArrowDown"
    @keydown.arrow-up="handleArrowUp"
    @keydown.enter="handleEnter"
    tabindex="0"
  >
    <ul v-show="results.length" class="results-list"  >
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
import api from "@/api";
import debounce from 'lodash/debounce';

export default {
  name:'FollowSearchBar',
  props: {
    keyword: String,
    isAt: Boolean,
  },
  data() {
    return {
      results: [], // 검색 결과를 담는 배열
      selectedIndex: -1, // 현재 선택된 인덱스
      previousIndex: -1, // 이전 인덱스 저장
      isFetching: false, // 데이터 가져오고 있는지 여부
      page: 1, // 페이지 번호
      observer: null, // IntersectionObserver 인스턴스
      firstCall:true,// 첫번째 페이지 로드인지에 대한 변수 
      isNomoreData:false,//더이상 로드할 데이터가 있는지에 대한 변수
      previousKeyword:''
      
    };
  },
  computed: {
    container() {
      return this.$refs.container;
    },
    sentinel() {
      return this.$refs.sentinel;
    },
  },
  watch: {
    keyword: {
      handler(newKeyword) {
      
        if (newKeyword && this.isAt &&newKeyword!==this.previousKeyword) {
          this.page = 1; // 페이지 1로 초기화
          this.results = []; // 결과 배열 초기화
          this.selectedIndex = -1; // 선택된 인덱스 초기화
          this.previousIndex = -1; // 이전 인덱스 초기화
          this.isNomoreData = false;//더이상 로드할 데이터가 있는지에 대한 변수
          this.firstCall=true// 첫번째 페이지 로드인지에 대한 변수 
          this.debouncedFetchResults(newKeyword); // 결과 가져오기
        } else {
          this.results = []; // 결과 배열 초기화
          this.selectedIndex = -1; // 선택된 인덱스 초기화
          this.previousIndex = -1; // 이전 인덱스 초기화
          this.firstCall=true // 첫번째 페이지 로드인지에 대한 변수 
        }
      },
      immediate: true, // 초기 렌더링 시에도 실행
    },
  },
  methods: {
    //실제 데이터 로드 작업 
    fetchResults(keyword) {
  if (this.isFetching || !this.isAt||this.isNomoreData) return; // 이미 데이터 가져오는 중이거나 @가 아닌 경우 , 더이상 로드할 데이터가 없는경우
  if (keyword === '@') return; // 아무 작업도 하지 않음
  this.isFetching = true; // 데이터 가져오기 시작

  const sendKeyword = keyword.substring(1); // '@' 제거
  const rowSize = 4; // 행개수 
        // 만약 첫 번째 로드이면 페이지를 증가시키지 않고 아니면 페이지를 증가시킴 
       if(!this.firstCall){
        this.page += 1;
      }
      //통과 하면 , 이제 더이상 첫번째 페이지로드가 아님 
      this.firstCall=false;

    api.get(`/members/following/${sendKeyword}/${this.page}/${rowSize}`) // API 호출
    .then((res) => {
   
      const newFollows = res?.data?.reqData?.followList; // 새로운 결과 리스트
      //결과가 있으면 
      if (newFollows?.length) {
        this.results = [...this.results, ...newFollows]; // 기존 결과에 새로운 결과 추가
        this.previousKeyword=this.keyword
      }
      else{ //결과가 없으면
        //페이지를 다시 원복시키고
        this.page--;
        //더이상 로드할 페이지가 없다는 변수를 true
        this.isNomoreData=true;
       
      }
    
    })
    .catch((err) => {
      alert(err?.response?.data?.message)
    })
    .finally(() => {
      this.isFetching = false; // 데이터 가져오기 종료
    });
},  //디바운싱
    debouncedFetchResults: debounce(function (keyword) {
      this.fetchResults(keyword,); // 디바운스 처리 후 fetchResults 호출
    }, 150)
    ,// intersection observer에따른 무한스크롤 데이터 호출
    handleIntersection(entries) {
      entries.forEach((entry) => {
        if (entry.isIntersecting && entry.target === this.sentinel) { // Sentinel이 보일 때
         
          this.debouncedFetchResults(this.keyword); // 결과 가져오기
        }
      });
    },
    //부모로 부터 받은 키다운 이벤트 
    handleArrowDown() {
      if (this.results.length === 0) return; // 결과가 없으면 아무 작업도 안 함

      this.previousIndex = this.selectedIndex; // 이전 인덱스 업데이트
      if (this.selectedIndex === -1) {
        this.selectedIndex = 0; // 선택된 항목이 없으면 첫 번째 항목 선택
      } else {
        this.selectedIndex = (this.selectedIndex + 1) % this.results.length; // 다음 항목 선택
      }
      this.scrollToSelectedItem(); // 선택된 항목으로 스크롤 이동
      this.checkAndLoadMore(); // 추가 데이터 로드 여부 확인
    },
    //부모로 부터 받은 키업이벤트
    handleArrowUp() {
      if (this.results.length === 0) return; // 결과가 없으면 아무 작업도 안 함

      this.previousIndex = this.selectedIndex; // 이전 인덱스 업데이트

      if (this.selectedIndex === 0) {
        this.selectedIndex = this.results.length - 1; // 첫 번째 항목에서 위로 이동 시 마지막 항목 선택
      } else if (this.selectedIndex === -1) {
        this.selectedIndex = this.results.length - 1; // 선택된 항목이 없으면 마지막 항목 선택
      } else {
        this.selectedIndex = (this.selectedIndex - 1 + this.results.length) % this.results.length; // 이전 항목 선택
      }
      this.scrollToSelectedItem(); // 선택된 항목으로 스크롤 이동
      this.checkAndLoadMore(); // 추가 데이터 로드 여부 확인
    },
    //부모로 부터 받은 엔터이벤트
    handleEnter() {
      if (this.isAt) {
        if (this.selectedIndex >= 0 && this.selectedIndex < this.results.length) {
          this.handleClick(this.results[this.selectedIndex].nickname); // 선택된 항목 클릭 처리
        }
      }
    },
    //클릭이벤트
    handleClick(follow) {
      this.$emit('selectFollow', follow); // 선택된 팔로우인원 전달
    },
    handleMouseOver(index) {
      this.previousIndex = this.selectedIndex; // 이전 인덱스 업데이트
      this.selectedIndex = index; // 현재 인덱스 업데이트
      this.scrollToSelectedItem(); // 선택된 항목으로 스크롤 이동
    },
    //인덱스를통한 스크롤 조정
    scrollToSelectedItem() {
      const selectedItem = this.container?.querySelector('.selected'); // 선택된 항목 찾기
      console.log(selectedItem)
      if (selectedItem && this.container) {
        const itemHeight = selectedItem.offsetHeight; // 항목의 높이
        const containerHeight = this.container.clientHeight; // 컨테이너의 높이
        const scrollTop = this.container.scrollTop; // 현재 스크롤 위치
        const selectedItemTop = selectedItem.offsetTop; // 선택된 항목의 상단 위치

        if (this.selectedIndex === 0) { // 첫 번째 항목일 때
          if (this.previousIndex !== -1 && this.previousIndex < this.selectedIndex) {
            const previousItem = this.container?.querySelectorAll('.result-item')[this.previousIndex]; // 이전 항목 찾기
            if (previousItem) {
              this.container.scrollTop = previousItem.offsetTop; // 이전 항목으로 스크롤 이동
            }
          } else {
            this.container.scrollTop = 0; // 첫 번째 항목은 컨테이너 상단으로 이동
          }
        } else if (this.selectedIndex === this.results.length - 1) { // 마지막 항목일 때
          this.container.scrollTop = this.container.scrollHeight; // 컨테이너 하단으로 이동
        } else { // 일반 항목일 때
          if (selectedItemTop < scrollTop) {
            this.container.scrollTop = selectedItemTop - itemHeight * 2.5; // 2칸 반 이동
          } else if (selectedItemTop + itemHeight > scrollTop + containerHeight) {
            this.container.scrollTop = selectedItemTop + itemHeight * 2.5 - containerHeight; // 2칸 반 이동
          }
        }

        // 스크롤 위치가 범위를 벗어나지 않도록 조정
        if (this.container.scrollTop < 0) {
          this.container.scrollTop = 0;
        }
        if (this.container.scrollTop > this.container.scrollHeight - containerHeight) {
          this.container.scrollTop = this.container.scrollHeight - containerHeight;
        }
      }
    },
    //인덱스를 통한 데이터 호출 
    checkAndLoadMore() {
      const containerElement = this.container;
      const selectedItem = containerElement?.querySelector('.selected'); // 선택된 항목 찾기
      if (selectedItem) {
        const containerRect = containerElement.getBoundingClientRect(); // 컨테이너의 위치 및 크기
        const itemRect = selectedItem.getBoundingClientRect(); // 선택된 항목의 위치 및 크기

        if (itemRect.bottom > containerRect.bottom - 50) { // 항목이 컨테이너 하단 가까이에 있을 때
          if (!this.isFetching) {
            
            this.debouncedFetchResults(this.keyword); // 결과 가져오기
          }
        }

        if (itemRect.top < containerRect.top + 50) { // 항목이 컨테이너 상단 가까이에 있을 때
          if (!this.isFetching) {
           
            this.debouncedFetchResults(this.keyword); // 결과 가져오기
          }
        }
      }
    },
    //intersection observer 초기화 
    initObserver() {
      if (this.container) {
        this.$nextTick(() => {
          if (this.sentinel) {
            this.observer = new IntersectionObserver(this.handleIntersection, {
              root: this.container, // 관찰할 컨테이너
              rootMargin: '0px',
              threshold: 1.0, // 100% 노출 시 콜백 호출
            });
            this.observer.observe(this.sentinel); // Sentinel 관찰 시작
          }
        });
      }
    },
    handleClickOutside(event) {
      
     if (this.$refs.container && !this.$refs.container.contains(event.target)) {
      console.log('컨테이너 외부 클릭')
      this.$emit('closeSearchFl')
     }
    }
   
  },
  mounted() {
    document.addEventListener('click', this.handleClickOutside.bind(this)); // bind(this) 추가
    this.initObserver(); // 컴포넌트가 마운트될 때 IntersectionObserver 초기화
  },
  beforeUnmount() { document.removeEventListener('click', this.handleClickOutside.bind(this)); // bind(this) 추가
    if (this.observer) { 
      this.observer.unobserve(this.sentinel); // 컴포넌트 언마운트 시 IntersectionObserver 중지
    }
    
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
