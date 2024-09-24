<template>
    <div class="text-center ">
        <v-dialog v-model="localDialog">
   
      <div class="mx-auto to-blackMode" style="width: 900px; height: 800px;" >
        <v-toolbar  style="background-color: gray; color: aliceblue;">
              <v-toolbar-title >
                <v-icon style="margin-right: 5px;">
                  mdi-heart-outline
                  </v-icon>
                알람</v-toolbar-title>
                <v-btn
                  icon="mdi-close"
                  @click="closeDialog()"
                ></v-btn>
          </v-toolbar>
       
              
          <v-progress-linear
          color="cyan"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>
         <div class="d-flex child-flex justify-center align-center" v-if="!alarmData.length && !this.isLoading">
          <span style="font-size: 20px; opacity: 0.7; margin-top: 200px;">아직 알람이 없습니다.</span>
          </div>
        <div  v-if="alarmData.length && !this.isLoading">
             
          <v-list  lines="three" class="to-blackMode pa-3">
            <v-list-item>
           
              <v-list-item-content >
               
                <v-row v-for="(alarm, index) in alarmData" :key="index" >
                  <v-col cols="1" >
                    <v-avatar :image="alarm.senderProfile" size="35" class="avatar"
                    v-if="alarm.senderProfile !== null" ></v-avatar>
                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="35" class="avatar" v-if="alarm.senderProfile === null" ></v-avatar>
                  </v-col>
                  <!--팔로우 요청 알람 -->
                  <v-col cols="8"  v-if="alarm.alarmType==='FOLLOWREQ'">                    
                      <span class="large-font" :class="alarm.isread?'read':''">
                        <span style="color: bisque; margin-right: 5px;">{{ alarm.sender }}</span>
                        님이 팔로우 요청을 보냈습니다.
                      </span>     
                      <span style="font-size: 12px;  opacity: 0.7; margin-left: 10px;" :class="alarm.isread?'read':''">
                        {{alarm.createDate  }}
                      <span style="margin-left: 1px;" v-if="alarm.isread">읽음</span>
                      <span class="new-badge" v-if="!alarm.isread">NEW</span>  
                      </span>                 
                  </v-col>
                
                  <v-col cols="3" v-if="alarm.alarmType==='FOLLOWREQ'">
                    <span style="float: right;">
                    <v-btn  color="primary"  @click="handleFollowRequest(alarm.sender,'ACCEPT')" style="margin-right: 8px;"> 수락</v-btn>
                    <v-btn  color="red"  @click="handleFollowRequest(alarm.sender,'REFUSE')"> 거절</v-btn>
                  </span>
                  </v-col>
                   <!--팔로우 요청 알람 종료 -->

                      <!--단순 팔로우  알람 -->
                  <v-col cols="11"  v-if="alarm.alarmType==='FOLLOW'">                    
                      <span class="large-font" >
                        <span style="color: bisque; margin-right: 5px;">{{ alarm.sender }}</span>
                        <span :class="alarm.isread?'read':''">님이 나를 팔로우 합니다.</span>
                      </span>     
                      <span style="font-size: 12px; opacity: 0.7; margin-left: 10px;" :class="alarm.isread?'read':''">
                        {{alarm.createDate  }}
                        <span style="margin-left: 1px;" v-if="alarm.isread">읽음</span>
                        <span class="new-badge" v-if="!alarm.isread">NEW</span>  
                      </span>                    
                  </v-col>
              
                    <!--단순 팔로우  알람 종료 -->

                        <!--게시물 좋아요 알람  -->
                  <v-col cols="8"  v-if="alarm.alarmType==='LIKE'">                    
                      <span class="large-font" >
                        <span style="color: bisque; margin-right: 5px;">{{ alarm.sender }}</span>
                        <span :class="alarm.isread?'read':''">님이 내 게시물을 좋아합니다.</span>
                      </span>         
                      <span style="font-size: 12px; opacity: 0.7; margin-left: 10px;" :class="alarm.isread?'read':''">
                        {{alarm.createDate  }}
                        <span style="margin-left: 1px;" v-if="alarm.isread">읽음</span>
                        <span class="new-badge" v-if="!alarm.isread">NEW</span>  
                      </span>         
                  </v-col>
                
                  <v-col cols="3"  v-if="alarm.alarmType==='LIKE'" >
                    <v-btn  color="grey" style="float: right;" @click="openPostDetailDialog(alarm.postNum)"> 게시물 이동</v-btn>
                  </v-col>
                    <!--게시물 좋아요 알람 종료 -->


                    
                        <!--게시물 태그 알람  -->
                  <v-col cols="8"  v-if="alarm.alarmType==='TAG'">                    
                      <span class="large-font" >
                        <span style="color: bisque; margin-right: 5px;">{{ alarm.sender }}</span>
                        <span :class="alarm.isread?'read':''">님이 게시물에 나를 태그합니다.</span>
                      </span>
                      <span style="font-size: 12px; opacity: 0.7; margin-left: 10px;" :class="alarm.isread?'read':''">
                        {{alarm.createDate  }}
                        <span style="margin-left: 1px;" v-if="alarm.isread">읽음</span>
                        <span class="new-badge" v-if="!alarm.isread">NEW</span>  
                      </span>                         
                  </v-col>
                  <v-col cols="3"  v-if="alarm.alarmType==='TAG'" >
                    <v-btn  color="green" style="float: right;" @click="openPostDetailDialog(alarm.postNum)"> 게시물 이동</v-btn>
                  </v-col>
                    <!--게시물 태그 알람 종료 -->
                </v-row>
              </v-list-item-content>
            
            </v-list-item>
            
          </v-list>
          
       
          <div class="pagination-container">
            <v-pagination
              v-model="page"
              :length="totalPage"
              :total-visible=5           
            ></v-pagination>
        </div>
        </div>
      </div>
 
    </v-dialog>
    </div>
    <PostDetail 
    :value="postDetailDialog"
    :postNum="sendPostNum"
    @postDetailClose="closePostDetailDialog"
    @closeAlarm="closeDialog"
   ></PostDetail>
  </template>
<script>
import api from "@/api"
import PostDetail from "@/components/feed/post/PostDetail.vue"
import eventBus from "@/eventBus"
export default {
    name:'AlarmList',
    components:{
      PostDetail
    },
    props: {
       value: {//사이드메뉴로부터  받은 모달 불리안값 
         type: Boolean,
         required: true
       }     
      },
      computed:{
    // 현재 사이드 컴포넌트에서의 다이얼로그 (true/false) 리턴 , props로 value를받아 메소드를 통해 리턴해야한다.
     localDialog:{
         get(){
       
             return this.value 
         }
     }
  },
  watch:{
    value(){
        this.alarmGet()
    },
     //페이지 변경 감지 
     page(){
      //새 데이터 로드 
      this.alarmGet()
    }
  },
  data(){
    return{
        alarmData:[], //알람 데이터
        page:1, //페이지 
        totalPage:0, //총페이지 
        postDetailDialog:false, //알람 리스트 내 게시물 이동을 통해 상세 게시물을 확인, 제어하는 변수 
        sendPostNum:0, //게시물 번호를 전달
        isLoading:false //로딩중인지 
    }
  },
  methods:{
    //게시물 번호를 받아 상세게시물을 열음 
    openPostDetailDialog(postNum){
      
      this.sendPostNum=postNum
      this.postDetailDialog=true
    },
    //상세 게시물을 닫음 
    closePostDetailDialog(){
      this.postDetailDialog=false
    },
    //현 컴포넌트를 닫음 
    closeDialog(){
      //컴포넌트 변수 초기화 
        this.isLoading=false
        this.alarmData=[]
        this.page=1
        this.totalPage=0
        this.postDetailDialog=false
        this.sendPostNum=0
        eventBus.emit('getAlarmData')
        this.$emit('closeAlarm');
    },
    //알람 리스트를 받아오는 메소드 
    alarmGet(){
      //로딩중이면 리턴 
      if(this.isLoading) return
      //로딩 변수 true
      this.isLoading=true
      //api호출
      api.get(`/alarm/${this.page}`)
      .then((res)=>{
        //알람데이터 세팅
        this.alarmData=res?.data?.reqData?.list
        //널이면 [] 초기화
        if(this.alarmData==null){
          this.alarmData=[]
        }
        //총페이지 
        this.totalPage=res?.data?.reqData?.totalPage
      })
      .catch((err)=>{
        if(err?.response?.data?.message){
        alert(err?.response?.data?.message)  
        }
        this.closeDialog()
      })
      .finally(()=>{
        this.isLoading=false
      })
    },
    //알람 리스트 내에서 팔로우 요청,수락 
    handleFollowRequest(nickname,type){
      //닉네임과 , 요청or 거절인지를 받음 
      api.post('/members/following',{
        nickname:nickname,
        type:type
      })
      .then(()=>{
        //성공시 데이터 리로드
        this.alarmGet()
      })
      .catch((err)=>{
        if(err?.response?.data?.message){
        alert(err?.response?.data?.message)  
        }
      })
    }
  }
}
</script>
<style >
   .dialog-container {
    width: 600px;
    height: 700px;
    position: relative;
    background-color: white; /* 배경 색상 설정 */
}

.read{
  color:gray;
  opacity: 0.7;
}

.dialog-content {
    display: flex;
    flex-direction: column;
    height: 100%;
    position: relative;
}


.pagination-container {
    position: absolute;
    bottom: 10px;
    left: 0;
    right: 0;
    text-align: center;
    padding: 0 10px; /* Optional: Add padding for spacing */
}
.new-badge {
  color: #ff4d4d; /* 좀 더 세련된 붉은 색 */
  background-color: rgba(255, 77, 77, 0.1); /* 붉은색 투명 배경 */
  border-radius: 4px; /* 둥근 모서리 */
  padding: 2px 6px; /* 텍스트 주변 여백 */
  font-weight: bold; /* 두꺼운 글씨 */
  font-size: 0.85em; /* 크기를 살짝 조정 */
  opacity: 0.9; /* 약간의 투명도 */
  margin-left: 1px;
}
</style>