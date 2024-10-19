# 관심사 기반의 SNS 프로젝트
 
 


 
``` <h4>인스타그램을 벤치마킹, 관심사 기반으로 동작하는 SNS 애플리케이션</h4>

 - **기능**

   - **회원가입** 

   - **로그인**

   - **이메일찾기 / 패스워드 초기화**

   - **설정 페이지**
   - **사용자 피드 (내 피드, 다른유저 피드)**

   - **메인 피드**

   - **글로벌 피드**

   - **검색 피드**

   - **통합 검색** 

   - **알람**

   - **게시물 작성 / 수정 / 삭제**

      

---















  # 📝 핵심 화면 요약 

 
![핵심화면 요약 ](https://github.com/user-attachments/assets/5642d190-ae05-4548-9510-52fc79bbdf08)




---












 # 🔧 Skills

   ✔️ Back-end
 
   
 <div>
       <span><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"></span> &nbsp
       <span><img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"></span> &nbsp
       <span> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"></span>&nbsp
       <span> <img src="https://img.shields.io/badge/MyBatis-DC382D?style=for-the-badge&logo=mybatis&logoColor=white"></span>   
      </div>

><br>



   ✔️ Front-end
<div>  <span><img src="https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D"></span> &nbsp    
      <span><img src="https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=vuetify&logoColor=AEDDFF"></span>
 </div>   
     
<br>


   ✔️ DB / CLOUD



 <div>  <span><img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"></span> &nbsp
   <span><img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white"></span>&nbsp
  <span><img src="https://img.shields.io/badge/Amazon_RDS-527FFF?style=for-the-badge&logo=amazonaws&logoColor=white"></span>
</div>

   



---










  # 📝 비즈니스 로직 외 중점 사항 

 - **JWT 토큰을 이용한 인증 / 인가** 

  - **스케줄러 배치 처리를 통한 토큰, 이메일 인증관리**

   - **한국어 초, 중 ,종성 분리를 통한 자동완성 기능 제공** 

  - **서비스 레벨에서의 캐싱을 통한 자동완성 기능 제공** 

   - **중복요청 방지 인터셉터** 

   - **S3를 통한 이미지 파일 관리** 

    


---















#  📊 아키텍쳐

 ![arshab drawio](https://github.com/user-attachments/assets/6614b392-cbff-4ad1-8c00-a42ecde47cad)







---











 # 🖥️ ERD


![ERD](https://github.com/user-attachments/assets/8191d2ab-1412-49a1-a07d-33acf142b9aa)














# ✔️ PROBLEM & SOLVE

### <u>**Security Filter 내 예외 발생 시 공통 예외 처리 불가한 문제**</u> 

- *Filter는  Servlet 앞단에 위치하여 ControllerAdvice에 접근 불가* 

  - <u>***ResponseApi 클래스에 필요한 예외처리 항목에 대하여 response를 직접 보내주는 메소드를 작성하여 filter 내부에서 예외처리***</u> 
---

### <u>**검색어 추천 기능 중 한글 초,중,종성으로 검색 불가 문제**</u> 

- *한글은 유니코드 체계상 초,중,종성 분리검색 필요* 

  - <u>***데이터베이스 내 초,종,종성컬럼을 추가하여 별도로 저장 및 , 검색컬럼 구성***</u> 
---

### <u>**동일 유저가 중복요청시 데이터 일관성 깨짐 문제**</u> 

- *같은 사용자에 대해 동일한 api요청시 막는 로직이 필요* 

  - <u>***인터셉터, ConcurrentHashMap을 이용하여 동일한 유저에대한 동일한 요청API 발생시 리터해주는 로직 추가***</u>
---
























# 📝 개선방안 및 추후학습



- *자동추천 검색, jwt 토큰관리 관련 작업은 RDBMS에 직접접근하는 방식이 성능저하를 일으킴 * 

  - <u>***REDIS 학습을 통해 메모리(RAM)을 활용하여 캐싱하자***</u> 




- *배포를 할때 수동으로 하니 상당한 시간 및 관리가 어려움* 

  - <u>***CI/CD 학습을 통해 자동화 전략을 이용하자***</u> 



- *JWT 토큰의 이점인 STATELESS를 느끼지 못하였음* 

  - <u>***로드밸런싱을 학습하여 STATLESS의 이점을 느껴보자***</u>
    ---
    
