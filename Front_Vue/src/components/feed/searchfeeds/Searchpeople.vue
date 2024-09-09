<template lang="">
        <v-progress-linear
          color="cyan"
          indeterminate
          v-if="isLoading"
         ></v-progress-linear>

       <v-row v-if="!memberData.length&&!isLoading">
          <v-col class="d-flex child-flex justify-center align-center" cols="12">
          '{{ this.keyword }}' 와 관련된 회원이 없습니다.
          </v-col>
      </v-row>

      <v-row v-if="memberData.length&&!isLoading">
        <v-col style="opacity: 0.8;">'{{this.keyword}}'' &nbsp;와 관련된 회원</v-col>
      </v-row>

     <v-row v-if="memberData.length&&!isLoading">
     <!--팔로잉 추천 목록-->
     <v-col cols="12" >

        <v-list lines="three"  class="to-blackMode">
           <v-list-item>
                                
               <v-list-item-content>

                <v-divider></v-divider>
      
                  <v-row  class="mt-1 mb-1 pa-2"
                  style="cursor:pointer;"
                  v-for="(member,index) in memberData" 
                  :key="index" 
                  
                  >
                      <v-col cols="2" style="text-align:center;"  @click="goUserFeed(member.nickname)">
                    
                              <v-avatar :image="member.profile" size="80" class="avatar"
                                v-if="member.profile !== null" ></v-avatar>

                                <v-avatar :image="require('@/assets/ikmyung.png')"  
                                size="80" class="avatar" v-if="member.profile === null" ></v-avatar>
                                        

                      </v-col>

                          <v-col cols="2" class="mt-5"  @click="goUserFeed(member.nickname)">
                            <span class=" large-font" >{{member.nickname}}</span>
                          </v-col>
                          <v-col cols="2"  class="mt-5" style="text-align:center;"  @click="goUserFeed(member.nickname)">
                          <span class=" large-font">게시물 &nbsp;{{member.postCount}}</span>
                          </v-col>
                          <v-col cols="2"  class="mt-5" style="text-align:center;"  @click="goUserFeed(member.nickname)">
                          <span class=" large-font">팔로우 {{member.followingAmount}}</span>
                          </v-col>
                          <v-col cols="2" class="mt-5" style="text-align:center;"  @click="goUserFeed(member.nickname)">
                          <span class=" large-font">팔로워 {{member.followerAmount}}</span>
                          </v-col>
                          <v-col cols="2" class="mt-5"  style="text-align:center;" > 
                            <span class=" large-font" v-if="member.hobby!==null">

                              <v-chip style="background-color: floralwhite;"
                              @click=" goSearchFeed(member.hobby)"
                              >
                              <span style="color:black;">  #{{member.hobby}}</span>
                              </v-chip>
                            
                            </span>
                          <span style="opacity:0.7;" v-if="member.hobby===null">
                            아직 관심사 없음
                          </span>
                          </v-col>
                         <v-divider></v-divider>
                   </v-row>


        <!-- 페이징-->
        
         <div class="text-center">
            <v-pagination
              v-model="page"
              :length="totalPage"
              :total-visible=10           
            ></v-pagination>
        </div>

            </v-list-item-content>
          </v-list-item>
        </v-list>
        
      </v-col>
    
      
     </v-row>

   
         

</template>
<script>

import api from '@/api';
export default {
  props:{
    keyword:String, //키워드
    type:String  // 검색 타입 ( 관심사  OR 사람 )
  },
  watch:{
    //키워드 변경감지 
    keyword(){
      //새 데이터 로드 
      this.callMemberList()
    },
    //타입 변경 감지 
    type(){
      //새 데이터 로드 
      this.callMemberList()
    },
    //페이지 변경 감지 
    page(){
      //새 데이터 로드 
      this.callMemberList()
    }
  },
  //마운트시 
  mounted(){
    //새 데이터 로드 
    this.callMemberList()
  },
    data(){
        return{
           
            memberData:[], //회원관련 정보 데이터 
            page:1, //페이지 
            totalPage:1, //총페이지 
            isLoading:false //데이터 로딩중 변수 
        }
    },
    methods:{
      //회원 정보를 가져오는 메소드 
      callMemberList(){
        //로딩중이면 리턴 
        if(this.isLoading)return
        //로딩중 
        this.isLoading=true
        //API 호출 
        api.get(`/search/member/${this.page}`,{
          params:{
          keyword:this.keyword
          }
        })
        .then((res)=>{
        //회원 정보 데이터 저장 
        this.memberData=res?.data?.reqData?.list
        //총 페이지 데이터 저장 
        this.totalPage=res?.data?.reqData?.totalPage
        
        })
        .catch((err)=>{
          alert(err?.response?.data?.message)
        })
        .finally(()=>{
          this.isLoading=false
        })
        
      },
      //클릭시 해당 유저피드로 이동 
      goUserFeed(nickname){
        
        this.$router.push({
          name: 'userfeed',
          params: { nickname:nickname}
        });
      },
      //관심사 클릭시 해당 관심사 기반 검색 피드로 이동 
      goSearchFeed(hobby){
        this.$router.push({
          name: 'searchfeed',
          params: { type:'hobby',keyword:hobby}
        });
      }
    }
}
</script>
<style scoped>
.avatar-wrapper {
  display: flex;
  align-items: flex-start;
  justify-content: center;
  position: relative;
  width: 800px;
}

.avatar {
  width: 100%;
  height: 100%;
}

.edit-icon {
  position: absolute;
  right: 30px;
  bottom: 10px;
  background-color: floralwhite;
  border-radius: 50%;
  padding: 21px;
  color: black;
  font-size: 24px;
}

.isHover {
  cursor: pointer;
}

.image-container {
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease;
}

.image-container:hover {
  transform: scale(1.05);
  cursor: pointer;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
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
  font-size: 48px;
  color: white;
  margin: 0 10px;
  cursor: default;
}


</style>