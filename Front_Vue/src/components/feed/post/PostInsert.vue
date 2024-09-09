<template>
  <!--게시물 상세 모달-->
  <v-dialog v-model="localDialog" max-width="1100" class="modal" persistent> 
    
    <v-card class="to-doubleBlack">
      <v-container fluid>
        <v-progress-linear
          color="cyan"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>
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
                >
                    <v-icon
                      class="remove-icon"
                      @click="removeImage(index)"
                     :class="{ 'icon-disabled': isLoading }"
                    >mdi-close
                    </v-icon>
              </v-carousel-item>
              </div>
              <div v-if="!images.length">
                <v-carousel-item
                  width="550px"
                  height="500px"
                  style="object-fit: contain;"
                 
                  src="@/assets/noImg.png"
                  cover
                >
                   
              </v-carousel-item>
              </div>

              
            </v-carousel>
          </v-col>
          
          <!-- 게시물 상세 오른쪽 정보(내용,댓글 등 ) -->
          <v-col cols="6" class="content-section to-blackMode">
      
            <v-icon
              class="close-icon"
              @click="closeDialog"
              :class="{ 'icon-disabled': isLoading }"
            >mdi-close</v-icon><!-- x아이콘 - > 모달 닫음-->

            <v-card-text v-show="!moreReply"><!-- 아닐시 게시물 내용, 좋아요, 수정, 삭제, 댓글 하나만 보임 -->
        
              <v-row no-gutters>
                <v-col cols="12" class="">
                  <v-btn
                    class="ma-2"
                    color="primary"
                    @click="triggerFileInput"
                    rounded
                    :disabled="isLoading"  
                  >
                    <v-icon left>mdi-attachment</v-icon>
                    사진 여러장 첨부
                    <input
                      type="file"
                      ref="fileInput"
                      multiple
                      @change="handleFileChange"
                      style="display: none"
                      accept="image/*"
                      :disabled="isLoading"  
                    />
                  </v-btn>
                  <v-btn
                    class="ma-2"
                    color="primary"
                    @click="triggerSingleFileInput"
                    rounded
                    :disabled="isLoading" 
                  >
                    <v-icon left>mdi-attachment</v-icon>
                    사진 한장 씩 첨부
                    <input
                      type="file"
                      ref="fileSingleInput"
                      @change="handleSingleFileChange"
                      style="display: none"
                      accept="image/*"
                      :disabled="isLoading"  
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
                    :disabled="isLoading" 
                  ></v-textarea>
                </v-col>

                <v-chip
                  v-for="(hobby, index) in hobbiesRequest" :key="index"
                  class="ma-2 custom-chip"
                  color="blue"
                  label
                >
                  <v-icon icon="mdi-label" start></v-icon>
                <span style="margin-right:10px"> #{{ hobby }}</span>
                  <v-icon
                    class="v-close-icon"
                    @click.stop="removeHobby(index)"
                    :class="{ 'icon-disabled': isLoading }" 
                  >mdi-close</v-icon>
                </v-chip>

                <v-col cols="12" class="autocomplete-container">
                  <v-textarea
                    label="관심사 (#을 통해 태그할 수 있습니다.)"
                    rows="1"
                    variant="filled"
                    auto-grow
                    shaped
                    @input="checkIsHashTag"
                    @keydown=" handleHobbyKeyDown"
                    v-model="searchHobby"
                    class="autocomplete-input"
                    :disabled="isLoading"
                  ></v-textarea>
                 
                   
                    
               
                  <HobbySearchResult
                    ref="hobbySearchResult"
                    :keyword="searchHobby"
                    :isHashtag="isHashtag"
                    @selectHobby="selectHobby"
                    @enterNoSearch="enterNoSearchHobby"
                    @closeSearch="closeSearch"
                    class="autocomplete-list"             
                  >
                  </HobbySearchResult>
                 
                </v-col>
                <v-chip
                  v-for="(follow, index) in followList" :key="index"
                  class="ma-2 custom-chip"
              
                  label
                >
                <v-avatar :image="follow.profile" size="20" class="avatar"
                    v-if="follow.profile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="20" class="avatar" v-if="follow.profile === null" ></v-avatar>
                <span style="margin-right:10px;margin-left: 5px;"> {{ follow.follow }}</span>
                  <v-icon
                    class="v-close-icon"
                    @click.stop="removeFollow(index)"
                    :class="{ 'icon-disabled': isLoading }" 
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
                    :disabled="isLoading"
                  ></v-textarea>
                  <FollowSearchResult
                    ref="followSearchResult"
                    :keyword="searchFollow"
                    :isAt="isAt"
                    @closeSearchFl="closeSearchFl"
                    @selectFollow="selectFollow"
                    @enterNoSearch="enterNoSearchFollow"

                    class="fow-autocomplete-list"             
                  >
                  </FollowSearchResult>
                </v-col>
                <v-divider></v-divider>
                
                <span style="margin-left: 290px;">
               
                  <v-checkbox v-model="onlymeCheck" label="나만보기" style="float:left;" :disabled="isLoading"></v-checkbox>
                 <span style="margin-left: 30px;">
                   <v-btn icon="mdi-pencil" size="50" color="black" @click="submitPost()" :disabled="isLoading" >

                   </v-btn>
                  </span>
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


import api from "@/api";
import eventBus from "@/eventBus"
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
    followList:[],
    isAt:false,//입력값이 @인지 확인 
    
  
    onlymeCheck: false, // 나만 보기 기능 체크박스
    isLoading:false //서버전송중 
  }
},
computed: {
  //부모로부터 받은 모달 열림 제어변수 리턴
  localDialog: {
    get() {
      return this.value;
    }
  }
},
methods: {
  //관심사 자동검색 리스트 닫기 
  closeSearch(){
    this.searchHobby=''
    this.isHashtag=false
  },
  //회원 태그 자동검색 리스트 닫기 
  closeSearchFl(){
    this.searchFollow=''
    this.isAt=false
  },
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
     
      this.$refs.hobbySearchResult.handleArrowDown();
    },
    handleHobbyArrowUp(){
      this.$refs.hobbySearchResult.handleArrowUp();
    },
    handleHobbyEnter(){
      this.$refs.hobbySearchResult.handleEnter();
    },
    handleFollowArrowDown(){
     
     this.$refs.followSearchResult.handleArrowDown();
   },
   handleFollowArrowUp(){
     this.$refs.followSearchResult.handleArrowUp();
   },
   handleFollowEnter(){
     this.$refs.followSearchResult.handleEnter();
   },
   //사진 한장씩 추가 하는 메서드
   handleSingleFileChange(event) {
    const file = event.target.files[0]; // 선택한 첫 번째 파일 가져오기
    if (file.length === 0) return; // 파일이 선택되지 않았으면 리턴

    // 파일 크기 제한 
    const MAX_SIZE_MB = 5;
    const MAX_SIZE_BYTES = MAX_SIZE_MB * 1024 * 1024; // 최대 파일 크기 (바이트)

    //만약 파일크기가 5mb를 초과하면 메서드 종료 
    if (file.size > MAX_SIZE_BYTES) {
      alert(`파일 '${file.name}'의 크기가 ${MAX_SIZE_MB}MB를 초과합니다.`);
      return;
    }

  const reader = new FileReader(); // 파일 읽기 객체 생성

  reader.onload = (e) => {
    const img = new Image(); // 이미지 객체 생성
    img.src = e.target.result; // 파일 데이터를 이미지 소스로 설정

    img.onload = () => {
      const canvas = document.createElement('canvas'); // 캔버스 생성
      const ctx = canvas.getContext('2d'); // 2D 컨텍스트 가져옴
      const width = 550; // 캔버스 너비 설정
      const height = 500; // 캔버스 높이 설정

      canvas.width = width; // 캔버스 너비 설정
      canvas.height = height; // 캔버스 높이 설정
      ctx.drawImage(img, 0, 0, width, height); // 이미지 그리기

      canvas.toBlob((blob) => { // 캔버스를 Blob으로 변환
        if (blob) {
          const resizedFile = new File([blob], file.name, { type: 'image/jpeg' }); // 새 파일 생성
          this.images.push(URL.createObjectURL(resizedFile)); // 새 이미지 URL 배열에 추가
          this.sendImg.push(resizedFile); // 새 이미지 파일 배열에 추가
          this.currentPage = this.images.length - 1; // 마지막으로 추가된 이미지로 페이지 이동
        } else {
          console.error('Blob 생성 실패');
        }
      }, 'image/jpeg'); // Blob 형식 설정
    };

    img.onerror = () => {
      console.error('이미지 로드 실패');
    };
  };

  reader.onerror = () => {
    console.error('파일 읽기 실패');
  };

  reader.readAsDataURL(file); // 파일 읽기 시작
},
   //사진 여러장 한번에 추가하는 메서드
   handleFileChange(event) {
    const files = event.target.files; // 파일 목록 가져옴
    const newImages = []; // 새 이미지 URL 저장 배열
    const newSendImgs = []; // 새 이미지 파일객체 저장 배열
    const MAX_SIZE_MB = 5; // 최대 파일 크기 설정 (MB)
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
          const width = 1024; // 캔버스 너비 설정
          const height = 1024; // 캔버스 높이 설정

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
//사진의 우상단 x아이콘 클릭시 사진배열에서 제거메서드
removeImage(index) {//해당 인덱스를 받음
  this.images.splice(index, 1);//url배열에서 인덱스 위치에서 한개 제거
  this.sendImg.splice(index, 1);//파일객체 배열에서 인덱스 위치에서 한개 제거

  // 사진 페이지 인덱스 조정
  if (this.currentPage >= this.images.length) {
    this.currentPage = this.images.length - 1; 
  }
},
checkIsHashTag(){
    //#으로 시작하면 HobbySearchBar가 열림 
    this.isHashtag = this.searchHobby.startsWith('#');
    
   if(this.searchHobby.replace(/#/g, '').replace(/\s+/g, '').length === 0&&this.searchHobby!=='#'){
    this.isHashtag=false
    return
   }
   if(this.searchHobby.replace(/#/g, '').replace(/\s+/g, '')==='#'){
    return
   }
   
    //#으로 시작해서 공백으로 끝나고,#만입력한 경우가 아니면 관심사 태그목록배열에 자동추가
    //요약= > #테스트 하고 스페이스를 누르면 등록
    if(this.searchHobby.endsWith(' ')&&this.searchHobby.startsWith('#')&&this.searchHobby.length>1&&this.searchHobby.replace(/#/g, '').trim().length>0){
      this.searchHobby=this.searchHobby.substring(1,this.searchHobby.lastIndexOf(' '))
      //현재 관심사 입력값 
      let  hobby=this.searchHobby.replace(/#/g, '').trim()
      // 이미 현재 검색값이 배열에 존재하는지 확인 
      const exists = this.hobbiesRequest.some(hb => hb === hobby);
      //존재하지않다면 
      if(!exists){
        if(hobby!==''){
          this.hobbiesRequest.push(hobby); // 인덱스 없이 배열에 추가
        }
        
      }
      //초기값으로 # 
      this.searchHobby = '#';
      //검색목록 닫기
      this.isHashtag = false;
    }
     //#으로 시작해서 ,으로 끝나고,#만입력한 경우가 아니면 관심사 태그목록배열에 자동추가
    //요약= > #테스트 하고 ,를 누르면 등록
    if(this.searchHobby.endsWith(',')&&this.searchHobby.startsWith('#')&&this.searchHobby.length>1&&this.searchHobby.replace(/#/g, '').trim().length>0){
      this.searchHobby=this.searchHobby.substring(1,this.searchHobby.lastIndexOf(','))
      //현재 관심사 입력값 
      let  hobby=this.searchHobby.replace(/#/g, '').trim()
      // 이미 현재 검색값이 배열에 존재하는지 확인 
      const exists = this.hobbiesRequest.some(hb => hb === hobby);
      //존재하지않다면 
      if(!exists){
        if(hobby!==''){
          this.hobbiesRequest.push(hobby); // 인덱스 없이 배열에 추가
        }
      }
      //초기값으로 # 
      this.searchHobby = '#';
      //검색목록 닫기 
      this.isHashtag = false;
    }
     //#으로 시작해서 #으로 끝나고,#만입력한 경우가 아니면 관심사 태그목록배열에 자동추가
    //요약= > #테스트 하고 #하면 자동등록 
    if(this.searchHobby.endsWith('#')&&this.searchHobby.startsWith('#')&&this.searchHobby.length>1&&this.searchHobby.replace(/#/g, '').trim().length>0){
      this.searchHobby=this.searchHobby.substring(1,this.searchHobby.lastIndexOf('#'))
      //현재 관심사 입력값 
      let  hobby=this.searchHobby.replace(/#/g, '').trim()
      // 이미 현재 검색값이 배열에 존재하는지 확인 
      const exists = this.hobbiesRequest.some(hb => hb === hobby);
      //존재하지않다면 
      if(!exists){
        if(hobby!==''){
          this.hobbiesRequest.push(hobby); // 인덱스 없이 배열에 추가
        }
      }
      //초기값으로 # 
      this.searchHobby = '#';
      //검색목록 닫기 
      this.isHashtag = false;
    }
  },
  //@으로 시작하면 followSearchBar가 열림 
  checkIsAt(){
  this.isAt = this.searchFollow.startsWith('@');

  },
    //자식에서 해당항목 엔터를 누를시 호출한 메서드
    selectHobby(hobby) {
      //만약 현재 배열에 현재입력값이 존재하는지 확인
       hobby=hobby.replace(/#/g, '').trim()
     
      const exists = this.hobbiesRequest.some(hb => hb === hobby);
      //배열에 현재값이 없다면 
      if(!exists){
        if(hobby!==''){
          this.hobbiesRequest.push(hobby); // 인덱스 없이 배열에 추가
        }
      }
     
      this.searchHobby = '';//검색값은 초기값
      this.isHashtag = false;//검색창 닫힘

    },
    //관심사 배열에서 해당인덱스 관심사 삭제 메서드
    removeHobby(index) {
      this.hobbiesRequest.splice(index, 1); // 인덱스로 배열에서 제거

    },
    //자식 컴포넌트에서 엔터시 자동완성 검색을 통한 태그입력이 아닌 본인이 원하는 태그입력 
    enterNoSearchHobby() {
      //#제거
      let hobby = this.searchHobby.replace(/#/g, '').trim();
      //현재 배열에 현재 관심사 입력값이 존재하는지 확인
      const exists = this.hobbiesRequest.some(hb => hb === hobby);
      //만약 배열에 현재값이 존재하지않다면 
      if(!exists){
        hobby = hobby.replace(/#/g, '');
        if(hobby!==''){
          this.hobbiesRequest.push(hobby); // 인덱스 없이 배열에 추가
        }
       
      }
      this.searchHobby = '';//초기값 설정
      this.isHashtag = false;//검색창 닫음
   
    },
    //자식컴포넌트에서 검색을 통한 항목에서 엔터이벤트시 수행
    selectFollow(follow,profile) {
      //이미 인물태그배열에 존재하는 항목이면 
      const exists = this.followRequest.some(fw => fw === follow);

  // 배열에 이미 존재하지 않으면 추가
  if (!exists) {
    this.followList.push({follow:follow,profile:profile})
    this.followRequest.push(follow); // 인덱스 없이 배열에 추가
    console.log(this.followList)
  }
      this.searchFollow = '';//초기값 설정 
      this.isAt = false;//검색창 닫음

    },
    //배열에서 사람태그 제거
    removeFollow(index) {
      this.followList.splice(index,1)
      this.followRequest.splice(index, 1); // 인덱스로 배열에서 제거

    },

  
  //게시물 등록 
  submitPost() {
    //이미 서버로 전송중인경우 방지
    if(this.isLoading){
      alert('이미 처리중인 요청입니다.')
      return
    }
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
    formData.append('onlyMe',this.onlymeCheck)//나만보기 여부
    
    //서버전송 true
    this.isLoading=true
    
    //게시물 작성 api
    api.post('/post',formData,{
      headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
    .then(()=>{
      //성공시 alert 띄우고 컴포넌트 해제
      alert('게시물 등록이 완료되었습니다.')
      //이벤트 버스를 통한 데이터베이스 리로드
      eventBus.emit('resetPostList');
    })
    .catch((err)=>{
      alert(err?.response?.data?.message);
    })
    .finally(()=>{
      //서버 전송끝
      this.isLoading=false
      this.closeDialog()
    })

  },

  //css커스텀으로 인한 다중첨부파일 모듈 띄우기 위한 메소드(ref)
  triggerFileInput() {
    this.$refs.fileInput.click();
  },
  //css커스텀으로 인한 단일첨부파일 모듈 띄우기 위한 메소드(ref)
  triggerSingleFileInput(){
    this.$refs.fileSingleInput.click();
  },
  // 게시물 작성 모달 닫음 
  closeDialog() {
    //컴포넌트 값 초기화
    this.images=[],
    this.sendImg=[],
    this.content='',
    this.currentPage=0,
    this.searchHobby='',
    this.hobbiesRequest=[],
    this.isHashtag=false,
    this.searchFollow='',
    this.followRequest=[],
    this.isAt=false,
    this.canReplyCheck=false,
    this.onlymeCheck=false,
    this.followList=[]
    // 게시물 작성 컴포넌트 닫기 이벤트 전송 
    this.$emit('postInsertClose', false); 
   
  },
 
},
components: {
  
}
}
</script>

<style scoped>
.modal {
  max-height: 90vh;
  overflow: hidden;
  pointer-events: none; 
  z-index: 1;
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
  width: calc(100%);
  max-height: 180px;
  overflow: auto;
  z-index: 1000;
  top: 67%;
  pointer-events: auto;
  left: 0;
  scrollbar-width: thin;
  scrollbar-color: #888 transparent;
}

.fow-autocomplete-list {
  position: absolute;
  background: #4A4A4A;
  border-radius: 4px;
  width: calc(100%);
  max-height: 150px;
  overflow: auto;
  z-index: 1001;
  top: 71%;
  pointer-events: auto;
  left: 0;
  scrollbar-width: thin;
  scrollbar-color: #888 transparent;
}

.autocomplete-list::-webkit-scrollbar {
  width: 5px;
}

.autocomplete-list::-webkit-scrollbar-track {
  background: transparent;
}

.autocomplete-list::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 10px;
}

.autocomplete-list::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}

.autocomplete-item {
  padding: 8px;
  cursor: pointer;
}

.autocomplete-item:hover,
.autocomplete-item.selected {
  background: #f0f0f0;
}

.custom-chip {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 3px 12px;
  position: relative;
  background-color: white;
  color: black;
  border-radius: 50px;
  border: 1px solid #ddd;
}

.custom-chip .v-icon {
  font-size: 15px;
}

.v-close-icon {
  cursor: pointer;
  margin-left: 8px;
  font-size: 15px;
  position: absolute;
  right: 5px;
  top: 50%;
  transform: translateY(-50%);
}

.remove-icon {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #ff4d4d;
  color: white;
  border-radius: 50%;
  font-size: 20px;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: background-color 0.3s, transform 0.2s;
}

.remove-icon:hover {
  background-color: #e60000;
  transform: scale(1.1);
}

.icon-disabled {
  pointer-events: none;
  cursor: not-allowed;
}

</style>
