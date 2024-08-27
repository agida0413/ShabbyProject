<template>

<v-row v-if="!postData.length" >
    <v-col class="d-flex child-flex justify-center align-center" cols="12">
      아직 등록된 게시물이 없습니다.
    </v-col>
  </v-row>

<v-row v-if="postData.length">
  
    <v-col
      v-for="(post,index) in postData"
      :key="index"
      class="d-flex child-flex"
      cols="4"
    >
      <v-img
      
        :src="post.postImgUrl"
        aspect-ratio="1"
        class="image-container"
        cover
      >
        <template v-slot:placeholder>
          <v-row align="center" class="fill-height ma-0" justify="center">
            <v-progress-circular color="grey-lighten-5" indeterminate></v-progress-circular>
          </v-row>
        </template>
        <div class="overlay">
          <v-icon class="overlay-icon">mdi-heart</v-icon>
          {{post.likeCount }}
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-icon class="overlay-icon">mdi-comment</v-icon>
          {{ post.replyCount }}
        </div>
      </v-img>
    </v-col>
  </v-row>

</template>

<script>
import api from "@/api"
export default{
    name:'UserFeedPostList',
  props:{
    nickname:String
  },
  data(){
    return{
      postData:{},
      isLoading:true
    }
  },
    mounted(){
      this.isLoading=true
      api.get(`/feed/userfeed/${this.nickname}/1`)
        .then((res) => {
         this.postData=res.data.reqData
        })
        .catch((err) => {

          alert(err.response.data.message);
        })
        .finally(() => {
          this.isLoading = false;
          this.$emit('childLoadingComplete')
        });
    }
}
</script>