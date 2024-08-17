
import axios from "axios";
const api = axios.create({
    baseURL: '/api', // 모든 요청의 기본 URL에 /api를 추가
    
  });
  export default api;