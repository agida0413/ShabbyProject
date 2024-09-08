<template>
    <div
      :class="results.length?'autocomplete-container':''"
      ref="container"
      style="display: block;"
      
      @keydown.arrow-down="handleArrowDown"
      @keydown.arrow-up="handleArrowUp"
      @keydown.enter="handleEnter"
      tabindex="0"
    >
      <ul  class="results-list" v-show="results.length" style="color: black; " >
        
        <li
          v-for="(item, index) in results"
          :key="index"
          class="result-item"
          :class="{ selected: index === selectedIndex }"
          @click="handleClick(item.result,item.type)"
          @mouseover="handleMouseOver(index)"
          :ref="index === selectedIndex ? 'selectedItem' : null"
        >
    

        <span v-if="item.type==='hobby'" >
          #
          </span>
        <span v-if="item.type==='member'" style="margin-right: 5px;">
          <v-avatar :image="item.profile" size="20" class="avatar"
                    v-if="item.profile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="20" class="avatar" v-if="item.profile === null" ></v-avatar>
        </span>
       {{ item.result }}
       <span style="float: right; color: gray; opacity: 0.7; font-size: 12px;" >
        <span v-if="item.type==='member'">
            인물
          </span>
          <span v-if="item.type==='hobby'">
            관심사
          </span>
        <v-icon>
            mdi-magnify
          </v-icon>
         
        </span>
        </li>
      
        <li ref="sentinel" class="result-item sentinel"></li>
      </ul>
      <ul v-show="!results.length" style="height: 40px;" >
        <li style="color: black;">
          검색결과가 없습니다.
        </li>
      </ul>
      <div v-if="isFetching" class="loading-indicator">
        <v-progress-circular indeterminate color="primary" size="30"></v-progress-circular>
      </div>
    </div>
 
  </template>
  
  <script>
  
  import debounce from 'lodash/debounce';
  import api from '../../../api';
  export default {
  
    name:'GlobalSearchBar',
    props: {
      keyword: String,
     
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
        isNomoreData:false,//더이상 로드할 데이터가 있는지에 대한 변수.
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

         if(newKeyword.trim()===''){
      
         this.$emit('closeSearch')
         return
       
         }
         console.log(newKeyword)
         console.log(this.previousKeyword)
          if (newKeyword && newKeyword!==this.previousKeyword ) {
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
      async fetchResults(keyword) {
       // 정규 표현식: `_`, 알파벳, 숫자, 한글만 허용
      const forbiddenChars = /[^a-zA-Z0-9_가-힣]/;

      if (forbiddenChars.test(keyword)) {
        return
      }

   

        if (this.isFetching || this.isNomoreData) {
          return
        } // 이미 데이터 가져오는 중이거나 #이 아닌 경우 , 더이상 로드할 데이터가 없는경우
      
        if(keyword.trim()==='' || keyword.trim()===null){
          return
        }
        
        
         // 만약 첫 번째 로드이면 페이지를 증가시키지 않고 아니면 페이지를 증가시킴 
         if(!this.firstCall){
          this.page += 1;
         }
         //통과 하면 , 이제 더이상 첫번째 페이지로드가 아님 
         this.firstCall=false;
         

         this.isFetching = true; // 데이터 가져오기 시작
         api.get(`/search/${this.page}`,{
          params:{
            keyword:keyword
          }
         })
         .then((res)=>{
          const newResult=res?.data?.reqData
          if(newResult.length){
            this.results=[...this.results,...res?.data?.reqData] 
          }
          else{
            this.page--;
            this.isNomoreData = true;
          }
        
          this.previousKeyword=this.keyword
         })
         .catch((err)=>{
          alert(err?.response?.data?.message)
         })
         .finally(()=>{
          this.isFetching=false
         })
       
        
      
       
      },//디바운싱
      debouncedFetchResults: debounce(function (keyword ) {
        this.fetchResults(keyword);// 디바운스 처리 후 fetchResults 호출
      }, 300),
      // intersection observer에따른 무한스크롤 데이터 호출
      handleIntersection(entries) {
       
        entries.forEach((entry) => {
          if (entry.isIntersecting && entry.target === this.sentinel) { // Sentinel이 보일 때
            if (!this.isFetching && !this.isNomoreData) {
              
              this.debouncedFetchResults(this.keyword); // 결과 가져오기
            }
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
      //부모로 부터 받은 키업 이벤트
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
        

          if (this.selectedIndex >= 0 && this.selectedIndex < this.results.length) {
           
            this.handleClick(this.results[this.selectedIndex].result,this.results[this.selectedIndex].type); // 선택된 항목 클릭 처리
          } else {
            this.$emit('enterNoramlSearch');
        }
      
    },
    //클릭제어 이벤트
      handleClick(keyword,type){
        
        if(type==='member'){
          this.handleMemberClick(keyword)
        }
        if(type==='hobby'){
          this.handleHobbyClick(keyword)
        }
      },
      //관심사 클릭이벤트
      handleHobbyClick(hobby) {
       
        this.$emit('searchHobby', hobby); // 선택된 관심사 전달
      },
     
      //사람 클릭이벤트
      handleMemberClick(member){
        this.$emit('searchMember', member); // 선택된 관심사 전달
      },
      handleMouseOver(index) {
        this.previousIndex = this.selectedIndex; // 이전 인덱스 업데이트
        this.selectedIndex = index; // 현재 인덱스 업데이트
        this.scrollToSelectedItem(); // 선택된 항목으로 스크롤 이동
      },
      //인덱스를통한 스크롤 조정
      scrollToSelectedItem() {
      
        const selectedItem = this.container?.querySelector('.selected'); // 선택된 항목 찾기
       
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
    
              this.container.scrollTop = selectedItemTop - itemHeight * 2.0; // 2칸 반 이동
            } else if (selectedItemTop + itemHeight > scrollTop + containerHeight) {
              this.container.scrollTop = selectedItemTop + itemHeight * 2.0 - containerHeight; // 2칸 반 이동
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
        
    const containerElement = this.$refs.container;
    const selectedItem = containerElement.querySelector('.selected');
        
    if (selectedItem) {
      const containerRect = containerElement.getBoundingClientRect();
      const itemRect = selectedItem.getBoundingClientRect();

      // 선택된 항목이 컨테이너 하단에서 540픽셀 이내에 있을 때
      if (itemRect.bottom > containerRect.bottom - 100) {
        if (!this.isFetching && !this.isNoMoreData) {
          this.debouncedFetchResults(this.keyword);
        }
      }

     
    }
  },

      initObserver() {
      
        if (this.container) {
          this.$nextTick(() => {
            if (this.sentinel) {
              this.observer = new IntersectionObserver(this.handleIntersection, {
                root: this.container,
                rootMargin: '0px',
                threshold: 0.1,
              });
              this.observer.observe(this.sentinel);
            }
          });
        }
      },
      closeSearch(){
        this.$emit('closeSearch')
      },
      handleClickOutside(event) {
     
      if (this.$refs.container && !this.$refs.container.contains(event.target)) {
        this.closeSearch(); // 컨테이너 외부를 클릭했을 때의 동작
      }
     }
    }, 
   
    mounted() {
  
      document.addEventListener('click', this.handleClickOutside.bind(this)); // bind(this) 추가
      this.initObserver();
    },
    beforeUnmount() {
     
      if (this.observer) {
  
        this.observer.disconnect();
      } 
      document.removeEventListener('click', this.handleClickOutside.bind(this)); // bind(this) 추가
    }
  };
  </script>
  
  <style scoped>
 .autocomplete-container{
  overflow-y: auto; /* 스크롤 가능 */
  margin: 0;
  padding: 0;
  max-height: 545px; /* 필요에 따라 높이 조정 */
  /* 스크롤 바 커스텀 */
  scrollbar-width: thin; /* Firefox */
  scrollbar-color: #888 transparent; /* Firefox */
}


.autocomplete-container::-webkit-scrollbar {
  width: 5px; /* 스크롤 바의 너비 */
}

.autocomplete-container::-webkit-scrollbar-track {
  background: transparent; /* 스크롤 바 트랙 배경색 */
}

.autocomplete-container::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 색상 */
  border-radius: 10px; /* 스크롤 바 모서리 둥글게 */
}

.autocomplete-container::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 색상 (호버 시) */
}

  .selected {
    background-color: #f5f5f5;
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
  