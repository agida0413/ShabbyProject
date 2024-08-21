<template>
   남은시간 : {{ minutes }}:{{ seconds }}
 
  </template>
  
  <script>
export default {
  data() {
    return {
      timeLeft: 180, //3분으로 설정
      endTime: null, //타이머 종료시간 
      timer: null, //타이머 인스턴스 저장 
    };
  },
  computed: {
    minutes() {
      return Math.floor(this.timeLeft / 60);// 남은시간 분
    },
    seconds() {// 남은시간 초
      return String(this.timeLeft % 60).padStart(2, '0'); 
    },
  },
  //타이머 초기화
  methods: {
    resetTimer() {
        this.timeLeft = 180; // 3분 (180초)
        this.startTimer(); // 타이머 시작
    },
    //타이머 시작
      startTimer() {
        if (this.timer) {
          clearInterval(this.timer);//기존 타이머 = > 삭제
        }

        this.endTime = Date.now() + this.timeLeft * 1000; // 현재시간 +180초 타이머종료시간 설정
        this.timer = setInterval(() => {
        const currentTime = Date.now();//현재시간 
        this.timeLeft = Math.max(Math.floor((this.endTime - currentTime) / 1000), 0); //남은시간 계산 
        
        if (this.timeLeft === 0) { // 남은시간 0 이면 타이머 x 
          clearInterval(this.timer);
        }
      }, 1000);//1초마다 타이머 업데이트
    },
  },
  //mount 되기전 호출
  beforeUnmount() {
    if (this.timer) {
      clearInterval(this.timer); // 현재 컴포넌트 언만운트 = > 타이머  x 
    }
  },
};
</script>

<style scoped>
p {
  font-size: 20px;
  font-weight: bold;
}
</style>