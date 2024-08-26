<template>
  <!--게시물 상세 모달-->
  <v-dialog v-model="localDialog" max-width="1100" class="modal" persistent> 
    
    <v-card class="to-doubleBlack">
      <v-container fluid>
        
        <v-row no-gutters>
          
          <v-col cols="6" class="image-section">
            <!-- 사진 목록-->
            <v-carousel v-model="currentPage" show-arrows="hover">
              <div v-if="images.length">
                <v-carousel-item
                  width="550px"
                  height="500px"
                  style="object-fit: contain;"
                  v-for="(image, index) in images" :key="index"
                  :src="image"
                  cover
                ></v-carousel-item>
              </div>
            </v-carousel>
          </v-col>
          
          <!-- 게시물 상세 오른쪽 정보(내용,댓글 등 ) -->
          <v-col cols="6" class="content-section to-blackMode">
      
            <v-icon
              class="close-icon"
              @click="closeDialog"
            >mdi-close</v-icon><!-- x아이콘 - > 모달 닫음-->

            <v-card-text v-show="!moreReply"><!-- 아닐시 게시물 내용, 좋아요, 수정, 삭제, 댓글 하나만 보임 -->
        
              <v-row no-gutters>
                <v-col cols="12" class="">
                  <v-btn
                    class="ma-2"
                    color="primary"
                    @click="triggerFileInput"
                    rounded
                  >
                    <v-icon left>mdi-attachment</v-icon>
                    사진 첨부
                    <input
                      type="file"
                      ref="fileInput"
                      multiple
                      @change="handleFileChange"
                      style="display: none"
                      accept="image/*"
                    />
                  </v-btn>
                  <v-textarea
                    label="게시물 내용"
                    row-height="30"
                    rows="4"
                    variant="filled"
                    auto-grow
                    shaped
                    v-model="content"
                  ></v-textarea>
                </v-col>

                <v-chip
                  v-for="(hobby, index) in hobbiesRequest" :key="index"
                  class="ma-2 custom-chip"
                  color="blue"
                  label
                >
                  <v-icon icon="mdi-label" start></v-icon>
                <span style="margin-right:10px"> {{ hobby }}</span>
                  <v-icon
                    class="v-close-icon"
                    @click.stop="removeHobby(index)"
                  >mdi-close</v-icon>
                </v-chip>

                <v-col cols="12" class="autocomplete-container">
                  <v-textarea
                    label="관심사 (#을 통해 태그할 수 있습니다.)"
             
                    rows="1"
                    variant="filled"
                    auto-grow
                    shaped
                    @input=" checkIsHashTag"
                    @keydown=" handleHobbyKeyDown"
                    v-model="searchHobby"
                    class="autocomplete-input"
                  ></v-textarea>
                 
                   
                    
               
                  <HobbySearchBar
                    ref="hobbySearchBar"
                    :keyword="searchHobby"
                    :isHashtag="isHashtag"
                    @selectHobby="selectHobby"
                    @enterNoSearch="enterNoSearchHobby"
                    class="autocomplete-list"             
                  >
                  </HobbySearchBar>
                 
                </v-col>
                <v-chip
                  v-for="(follow, index) in followRequest" :key="index"
                  class="ma-2 custom-chip"
                  color="green"
                  label
                >
                  <v-icon icon="mdi-account" start></v-icon>
                <span style="margin-right:10px"> {{ follow }}</span>
                  <v-icon
                    class="v-close-icon"
                    @click.stop="removeFollow(index)"
                  >mdi-close</v-icon>
                </v-chip>

                <v-col cols="12" class="like-icon-col mt-4 autocomplete-container">
                  <v-textarea
                    label="인물 태그 (@을 통해 태그할 수 있습니다.)"
                    row-height="30"
                    rows="1"
                    variant="filled"
                    auto-grow
                    shaped
                    @input=" checkIsAt"
                    @keydown=" handleFollowKeyDown"
                    v-model="searchFollow"
                  ></v-textarea>
                  <FollowSearchBar
                    ref="followSearchBar"
                    :keyword="searchFollow"
                    :isAt="isAt"
                    @selectFollow="selectFollow"
                    @enterNoSearch="enterNoSearchFollow"
                    class="fow-autocomplete-list"             
                  >
                  </FollowSearchBar>
                </v-col>
                <v-divider></v-divider>
                
                <span >
                  <v-checkbox v-model="canReplyCheck" label="댓글기능 해제" style="float: left;"></v-checkbox>
                  <v-checkbox v-model="onlymeCheck" label="나만보기" style="float:left;" ></v-checkbox>
                 <span style="margin-left:200px">
                   <v-btn icon="mdi-pencil" size="50" color="black" @click="submitPost()" style="float:right;">

                   </v-btn>
                  </span><!--수정 버튼-->
                </span>
              </v-row>
            </v-card-text>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script>

import HobbySearchBar from "@/components/common/utill/HobbySearchBar.vue"
import FollowSearchBar from "@/components/common/utill/FollowSearchBar.vue";
import api from "@/api";
export default {
name: 'PostInsert',

props: {
  value: {// 부모로부터 받음 모달 열림, 닫음 제어 변수
    type: Boolean,
    required: true
  }
},
data() {
  return {
    images: [], // 선택한 이미지 파일들의 URL을 저장할 배열
    sendImg:[], // 이미지들 서버로보낼 파일객체들 url과 요소는 동일함 
    content: '', // 게시물 내용(글)
    currentPage: 0, // 사진 첨부 후 처음 인덱스로 돌아가게

  
    searchHobby: '', // 관심사 검색어
    hobbiesRequest: [], // 관심사 태그 목록,서버에 보낼 데이터 
    isHashtag:false,//#을할시 자동완성 검색리스트 노출 

    searchFollow: '', // 사람 태그
    followRequest: [], // 사람 태그 목록
    isAt:false,//입력값이 @인지 확인 
    
    canReplyCheck: false, // 댓글 기능 해제 체크박스
    onlymeCheck: false // 나만 보기 기능 체크박스
  }
},
computed: {
  localDialog: {
    get() {
      return this.value;
    }
  }
},
methods: {
  //부모요소(현재 컴포넌트) 에서 키다운이벤트 발생시 관심사 검색 컴포넌트에게 이벤트를 전달하기위함 
  handleHobbyKeyDown(event) {

      switch(event.key) {
        case 'ArrowDown'://아래방향키
          this.handleHobbyArrowDown();
          break;
        case 'ArrowUp'://위방향키
          this.handleHobbyArrowUp();
          break;
        case 'Enter'://엔터
        event.preventDefault(); //기본 textarea시 줄바꿈되는 현상막기 위함  
          this.handleHobbyEnter();
          break;
        default:
          break;
      }
    },
    //부모요소(현재 컴포넌트) 에서 키다운이벤트 발생시 팔로우검색 컴포넌트에게 이벤트를 전달하기위함 
    handleFollowKeyDown(event) {
      switch(event.key) {
        case 'ArrowDown'://아래 방향키 
          this.handleFollowArrowDown();
          break;
        case 'ArrowUp'://위방향키
          this.handleFollowArrowUp();
          break;
        case 'Enter'://엔터
        event.preventDefault(); //기본 textarea시 줄바꿈되는 현상막기 위함  
          this.handleFollowEnter();
          break;
        default:
          break;
      }
    },
    //자식컴포넌트에게 ref(참조값) 에 해당하는 메소드로 전달 
    handleHobbyArrowDown(){
     
      this.$refs.hobbySearchBar.handleArrowDown();
    },
    handleHobbyArrowUp(){
      this.$refs.hobbySearchBar.handleArrowUp();
    },
    handleHobbyEnter(){
      this.$refs.hobbySearchBar.handleEnter();
    },
    handleFollowArrowDown(){
     
     this.$refs.followSearchBar.handleArrowDown();
   },
   handleFollowArrowUp(){
     this.$refs.followSearchBar.handleArrowUp();
   },
   handleFollowEnter(){
     this.$refs.followSearchBar.handleEnter();
   },
    
   handleFileChange(event) {
  const files = event.target.files; // 파일 목록 가져옴
  const newImages = []; // 새 이미지 URL 저장 배열
  const newSendImgs = []; // 새 이미지 파일객체 저장 배열
  const MAX_SIZE_MB = 10; // 최대 파일 크기 설정 (MB)
  const MAX_SIZE_BYTES = MAX_SIZE_MB * 1024 * 1024; // 최대 파일 크기 (바이트)

  if (files.length === 0) return; // 파일 없으면 리턴

  Array.from(files).forEach(file => { // 파일 리스트를 배열로 변환 후 반복 처리
    if (file.size > MAX_SIZE_BYTES) { // 파일 크기 검사
      alert(`파일 '${file.name}'의 크기가 ${MAX_SIZE_MB}MB를 초과합니다.`); // 크기 초과 시 경고
      return; // 파일 크기 초과 시 처리 중지
    }

    const reader = new FileReader(); // 파일 읽기 객체 생성

    reader.onload = (e) => { // 파일 읽기 완료 시 실행되는 콜백
      const img = new Image(); // 이미지 객체 생성
      img.src = e.target.result; // 파일 데이터를 이미지 소스로 설정

      img.onload = () => { // 이미지 로드 완료 시 실행되는 콜백
        const canvas = document.createElement('canvas'); // 캔버스 생성
        const ctx = canvas.getContext('2d'); // 2D 컨텍스트 가져옴
        const width = 550; // 캔버스 너비 설정
        const height = 500; // 캔버스 높이 설정

        canvas.width = width; // 캔버스 너비 설정
        canvas.height = height; // 캔버스 높이 설정
        ctx.drawImage(img, 0, 0, width, height); // 이미지 그리기

        canvas.toBlob((blob) => { // 캔버스를 Blob으로 변환
          if (blob) { // Blob 생성 성공 시
            const resizedFile = new File([blob], file.name, { type: 'image/jpeg' }); // 새 파일 생성
            newImages.push(URL.createObjectURL(resizedFile)); // 새 이미지 URL 배열에 추가
            newSendImgs.push(resizedFile); // 새 이미지 파일 배열에 추가

            if (newImages.length === files.length) { // 모든 파일 처리 완료 시
              this.images = newImages; // 이미지 URL 업데이트
              this.sendImg = newSendImgs; // 이미지 파일 업데이트
              this.currentPage = 0; // 현재 페이지 리셋
            }
          } else {
            console.error('Blob 생성 실패'); // Blob 생성 실패 시 에러 출력
          }
        }, 'image/jpeg'); // Blob 형식 설정
      };

      img.onerror = () => {
        console.error('이미지 로드 실패'); // 이미지 로드 실패 시 에러 출력
      };
    };

    reader.onerror = () => {
      console.error('파일 읽기 실패'); // 파일 읽기 실패 시 에러 출력
    };

    reader.readAsDataURL(file); // 파일 읽기 시작
  });
},
  checkIsHashTag(){
    //#으로 시작하면 HobbySearchBar가 열림 
    this.isHashtag = this.searchHobby.startsWith('#');
   
    //#으로 시작해서 공백으로 끝나고,#만입력한 경우가 아니면 관심사 태그목록배열에 자동추가
    //요약= > #테스트 하고 스페이스를 누르면 등록
    if(this.searchHobby.endsWith(' ')&&this.searchHobby.startsWith('#')&&this.searchHobby.length>1&&this.searchHobby.replace(/#/g, '').trim().length>0){
      this.searchHobby=this.searchHobby.substring(1,this.searchHobby.lastIndexOf(' '))
      this.hobbiesRequest.push(this.searchHobby); // 인덱스 없이 배열에 추가
      this.searchHobby = '#';
      this.isHashtag = false;
    }
     //#으로 시작해서 ,으로 끝나고,#만입력한 경우가 아니면 관심사 태그목록배열에 자동추가
    //요약= > #테스트 하고 ,를 누르면 등록
    if(this.searchHobby.endsWith(',')&&this.searchHobby.startsWith('#')&&this.searchHobby.length>1&&this.searchHobby.replace(/#/g, '').trim().length>0){
      this.searchHobby=this.searchHobby.substring(1,this.searchHobby.lastIndexOf(','))
      this.hobbiesRequest.push(this.searchHobby); // 인덱스 없이 배열에 추가
      this.searchHobby = '#';
      this.isHashtag = false;
    }
     //#으로 시작해서 #으로 끝나고,#만입력한 경우가 아니면 관심사 태그목록배열에 자동추가
    //요약= > #테스트 하고 #하면 자동등록 
    if(this.searchHobby.endsWith('#')&&this.searchHobby.startsWith('#')&&this.searchHobby.length>1&&this.searchHobby.replace(/#/g, '').trim().length>0){
      this.searchHobby=this.searchHobby.substring(1,this.searchHobby.lastIndexOf('#'))
      this.hobbiesRequest.push(this.searchHobby); // 인덱스 없이 배열에 추가
      this.searchHobby = '#';//초기값을 #으로해서 바로 태그입력 편의성 제공 
      this.isHashtag = false;
    }
  },
  //@으로 시작하면 followSearchBar가 열림 
  checkIsAt(){
  this.isAt = this.searchFollow.startsWith('@');

  },
    //자식에서 해당항목 엔터를 누를시 호출한 메서드
    selectHobby(hobby) {
      this.hobbiesRequest.push(hobby); // 인덱스 없이 배열에 추가
      this.searchHobby = '';//검색값은 초기값
      this.isHashtag = false;//검색창 닫힘

    },

    removeHobby(index) {
      this.hobbiesRequest.splice(index, 1); // 인덱스로 배열에서 제거

    },
    //자식 컴포넌트에서 엔터시 자동완성 검색을 통한 태그입력이 아닌 본인이 원하는 태그입력 
    enterNoSearchHobby() {
      
      const hobby = this.searchHobby.substring(1);//#제거
      this.hobbiesRequest.push(hobby); // 인덱스 없이 배열에 추가
      this.searchHobby = '';//초기값 설정
      this.isHashtag = false;//검색창 닫음
   
    },
    //자식컴포넌트에서 검색을 통한 항목에서 엔터이벤트시 수행
    selectFollow(follow) {
      //이미 인물태그배열에 존재하는 항목이면 
      const exists = this.followRequest.some(fw => fw === follow);

  // 배열에 이미 존재하지 않으면 추가
  if (!exists) {
    this.followRequest.push(follow); // 인덱스 없이 배열에 추가
  }
      this.searchFollow = '';//초기값 설정 
      this.isAt = false;//검색창 닫음

    },
    //배열에서 인물태그 제거
    removeFollow(index) {
      this.followRequest.splice(index, 1); // 인덱스로 배열에서 제거

    },

  
    //게시물 등록 
  submitPost() {
    //사진없이는 게시물작성 불가 
    if(this.images.length===0){
      alert('사진한장이상을 첨부 해주세요.')
      return
    }
    //사진을 포함한 formdata 객체
    let formData = new FormData();
    this.sendImg.forEach((image) => {
        // 이미지 파일을 FormData에 추가
        formData.append('imgList', image); //이미지
    });
    formData.append('hobbyList',this.hobbiesRequest)//관심사 태그 목록 
    formData.append('followTagList',this.followRequest)//인물 태그 목록
    formData.append('content',this.content)//내용
    formData.append('canReply',this.canReplyCheck)//댓글 사용여부 
    formData.append('onlyMe',this.onlymeCheck)//나만보기 여부
    
    //게시물 작성 api
    api.post('/post',formData,{
      headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
    .then((res)=>{
      console.log(res)
    })
    .catch((err)=>{
      console.log(err)
    })

  },

  //css커스텀으로 인한 첨부파일 모듈 띄우기 위한 메소드(ref)
  triggerFileInput() {
    this.$refs.fileInput.click();
  },
  // 게시물 작성 모달 닫음 
  closeDialog() {
    this.$emit('postInsertClose', false); 
    this.moreReply = false;
  },
  //댓글 더보기 열음
  moreReplyOpen() {
    this.moreReply = true;
  },
  //댓글 더보기 닫음
  moerReplyClose() {
    this.moreReply = false;
  }
},
components: {
  HobbySearchBar,
  FollowSearchBar
}
}
</script>

<style scoped>
.modal {
  max-height: 90vh;
  overflow: hidden;
  pointer-events: none; /* 부모는 클릭 불가능 */
  
  z-index:1;
  
}

.image-section {
  padding: 0;
  position: relative;
}

.content-section {
  padding: 16px;
  position: relative;
}

.close-icon {
  position: absolute;
  top: -10px;
  right: -10px;
  font-size: 30px;
  cursor: pointer;
  z-index: 1;
}

.overlay-text {
  position: absolute;
  top: 20px;
  left: 20px;
  right: 20px;
  color: white;
  padding: 10px;
  border-radius: 4px;
  font-size: 24px;
  z-index: 2;
  white-space: normal;
}

.post-content-col {
  flex: 1;
  padding-right: 16px;
}

.like-icon-col {
  display: flex;
  align-items: center;
  padding-right: 16px;
  font-size: 14px;
}

.comment-icon-col {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.username {
  font-weight: bold;
  display: block;
}

.post-text {
  width: 520px;
  height: 150px;
  display: block;
  margin-top: 8px;
  padding: 16px;
  background-color: #2C2C2C;
  color: aliceblue;
  border-radius: 4px;
}

.hobby-text {
  width: 520px;
  height: 50px;
  display: block;
  margin-top: 8px;
  padding: 16px;
  background-color: #2C2C2C;
  color: aliceblue;
  border-radius: 4px;
}

.v-divider {
  margin: 16px 0;
}

.autocomplete-container {
  position: relative;
 
  
}

.autocomplete-input {
  margin-bottom: 8px;
}

.autocomplete-list {
  position: absolute;
  background: #4A4A4A;
  
  border-radius: 4px;
  width: calc(100% );
  max-height: 180px; /* 높이를 지정하여 스크롤이 가능하게 함 */
  overflow: auto; /* 스크롤 활성화 */
  z-index: 1000; /* 충분히 높은 값 */
  top: 67%; /* 입력 필드 바로 아래에 위치 */
  pointer-events: auto; /* 클릭 이벤트 허용 */
  left: 0;
   /* 스크롤 바 커스텀 */
   scrollbar-width: thin; /* Firefox */
  scrollbar-color: #888 transparent; /* Firefox */
}
.fow-autocomplete-list{
  position: absolute;
  background: #4A4A4A;
  
  border-radius: 4px;
  width: calc(100% );
  max-height: 150px; /* 높이를 지정하여 스크롤이 가능하게 함 */
  overflow: auto; /* 스크롤 활성화 */
  z-index: 1001; /* 충분히 높은 값 */
  top: 71%; /* 입력 필드 바로 아래에 위치 */
  pointer-events: auto; /* 클릭 이벤트 허용 */
  left: 0;
   /* 스크롤 바 커스텀 */
   scrollbar-width: thin; /* Firefox */
  scrollbar-color: #888 transparent; /* Firefox */
}
.autocomplete-list::-webkit-scrollbar {
  width: 5px; /* 스크롤 바의 너비 */
}

.autocomplete-list::-webkit-scrollbar-track {
  background: transparent; /* 스크롤 바 트랙 배경색 */
}

.autocomplete-list::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤 바 색상 */
  border-radius: 10px; /* 스크롤 바 모서리 둥글게 */
}

.autocomplete-list::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤 바 색상 (호버 시) */
}
.autocomplete-item {
  padding: 8px;
  cursor: pointer;
}

.autocomplete-item:hover,
.autocomplete-item.selected {
  background: #f0f0f0;
}
/* v-chip 스타일 */
.custom-chip {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 3px 12px;
  position: relative; /* 아이콘 위치 조정을 위해 상대적으로 설정 */
  background-color: white; /* 배경색을 하얀색으로 설정 */
  color: black; /* 텍스트 색상 */
  border-radius: 50px; /* 동그란 배경을 만들기 위한 설정 */
  border: 1px solid #ddd; /* 연한 회색 테두리 추가 (필요시) */
}

.custom-chip .v-icon {
  font-size: 15px; /* 아이콘 크기 조정 */
}

.v-close-icon {
  cursor: pointer;
  margin-left: 8px; /* 닫기 아이콘과 텍스트 사이의 간격을 조정 */
  font-size: 15px; /* 아이콘 크기 조정 (필요에 따라 조정) */
  position: absolute; /* 아이콘을 칩의 우측에 위치시키기 위해 절대 위치 사용 */
  right: 5px; /* 칩의 우측으로부터의 거리 조정 */
  top: 50%; /* 칩의 세로 중앙에 위치시키기 위한 설정 */
  transform: translateY(-50%); /* 세로 중앙 조정을 위한 변환 */
}
</style>
