<template lang="">

    <v-row v-if="!memberData.length&&!isLoading">
        <v-col class="d-flex child-flex justify-center align-center" cols="12">
        '{{ this.keyword }}' 에 해당하는 회원이 없습니다.
        </v-col>
      </v-row>
      <v-row v-if="memberData.length">
        <v-col style="opacity: 0.8;">'{{this.keyword}}'' &nbsp;와 관련된 회원</v-col>
      </v-row>
     <v-row v-if="memberData.length">
     <!--팔로잉 추천 목록-->
     <v-col cols="12" >

        <v-list lines="three"  class="to-blackMode">
    <v-list-item>
                                
      <v-list-item-content>
        <v-divider></v-divider>
      
    
        <v-row  class="mt-1 mb-1 pa-2" v-for="(member,index) in memberData" :key="index" >
           <v-col cols="2" style="text-align:center;">
        
                  <v-avatar :image="member.profile" size="80" class="avatar"
                    v-if="member.profile !== null" ></v-avatar>

                    <v-avatar :image="require('@/assets/ikmyung.png')"  
                     size="80" class="avatar" v-if="member.profile === null" ></v-avatar>
                            

          </v-col>

          <v-col cols="2" class="mt-5" >
            <span class=" large-font" >{{member.nickname}}</span>
          </v-col>
          <v-col cols="2"  class="mt-5" style="text-align:center;">
          <span class=" large-font">게시물 &nbsp;{{member.postCount}}</span>
          </v-col>
          <v-col cols="2"  class="mt-5" style="text-align:center;">
          <span class=" large-font">팔로우 {{member.followingAmount}}</span>
          </v-col>
          <v-col cols="2" class="mt-5" style="text-align:center;">
          <span class=" large-font">팔로워 {{member.followerAmount}}</span>
          </v-col>
          <v-col cols="2" class="mt-5"  style="text-align:center;"> 
            <span class=" large-font" v-if="member.hobby!==null">
              <v-chip style="background-color: floralwhite;">
              <span style="color:black;">  #{{member.hobby}}</span>
              </v-chip>
            
          </span>
          <span style="opacity:0.7;" v-if="member.hobby===null">
            아직 관심사 없음
          </span>
          </v-col>
        
        </v-row>


        <!-- 페이징-->
         <v-divider></v-divider>
         <div class="text-center">
    <v-pagination
      v-model="page"
        :length="totalPage"
      :total-visible=totalPage
    
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
    keyword:String,
    type:String 
  },
  watch:{
    keyword(){
      this.callMemberList()
    },
    type(){
    
      this.callMemberList()
    },
    page(){
    this.callMemberList()
    }
  },
  mounted(){
    this.callMemberList()
  },
    data(){
        return{
           
           memberData:[],
            page:1,
            totalPage:1
        }
    },components:{
       
    },
    methods:{
      callMemberList(){
        
        api.get(`/search/member/${this.page}`,{
          params:{
          keyword:this.keyword
          }
        })
        .then((res)=>{
        this.memberData=res?.data?.reqData?.list
        this.totalPage=res?.data?.reqData?.totalPage
        console.log(this.totalPage)
        })
        .catch((err)=>{
          alert(err?.response?.data?.message)
        })
        
      }
    }
}
</script>
<style scoped>
.avatar-wrapper {
  display: flex;
  align-items: flex-start; /* 상단에 정렬 */
  justify-content: center; /* 가운데 정렬 */
  position: relative;
  width: 800px; /* 프로필 사진의 너비와 동일하게 설정 */
   /* 프로필 사진의 높이와 동일하게 설정 */
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
  color: black; /* 아이콘 색상 조정 */
  font-size: 24px; /* 아이콘 크기 조정 */
}
.isHover{
     cursor: pointer; /* 커서를 손 모양으로 변경 */
}
.image-container {
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease;
}

.image-container:hover {
  transform: scale(1.05);
  cursor: pointer; /* 사진 호버 시 포인터 커서 */
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5); /* 사진 어두워지기 */
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
  font-size: 48px; /* 아이콘 크기 조정 */
  color: white; /* 아이콘 색상 */
  margin: 0 10px;
  cursor: default; /* 아이콘에 기본 커서 적용 */
}

</style>