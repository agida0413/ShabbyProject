<template>
    <div class="error-page-container to-blackMode">
      <div class="error-page " >
        <div class="error-icon" >
          <span class="material-icons" >warning</span>
        </div>
        <h1>페이지를 찾을 수 없습니다.</h1>
        <p>사이드 메뉴를 통해 다시 이용해 주세요.</p>
      
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "ErrorPage",
  };
  </script>
  
  <style scoped>
  .error-page-container {
    display: flex;
    justify-content: center;
  
    height: 100vh;
   
  }
  
  .error-page {
    width: 500px;
    height: 500px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
   
   
    border-radius: 8px;
  }
  
  .error-icon {
   
    font-size: 200px;
    color: yellow;
    margin-bottom: 20px;
  }
  
  h1 {
    font-size: 36px;
    margin-bottom: 10px;
  }
  
  p {
    font-size: 18px;
    margin-bottom: 20px;
  }
  
  
  
  .back-home-btn:hover {
    background-color: #0056b3;
  }
  </style>
  