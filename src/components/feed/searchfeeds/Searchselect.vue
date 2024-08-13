<template>
    <v-tabs
      class="to-blackMode"
      fixed-tabs
    >
      <v-tab @click="transportSearchHobby()">관심사 검색</v-tab>
      <v-tab @click="transportSearchPeople()">사람 검색</v-tab>
    </v-tabs>
    <v-divider></v-divider>
  </template>
  
  <script>
  export default {
    name: 'Searchselect',
    data () {
      return {
        // Data properties if any
      }
    },
    methods:{
      transportSearchHobby() {
        this.$emit('hobby');// 로그인 컴포넌트로 닫는 이벤트 전송
      },
      transportSearchPeople() {
        this.$emit('people');// 로그인 컴포넌트로 닫는 이벤트 전송
      }
    }
  }
  </script>
  
  <style scoped>
  
  </style>
  