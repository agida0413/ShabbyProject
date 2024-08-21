package com.sist.dto.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// api 응답 포멧 = > 에러 응답과 ,성공응답 형식을 일정하게 유지하기 위해
@Data
public class ResponseDTO<T> {
	
	
    private boolean success; // 성공여부 
    private int code;//응답코드
    private String message;//메시지 ( 클라이언트에서 표시할 메시지)
    private T reqData;//보낼 데이터 
    private LocalDateTime timestamp; // 시간 
    
    
  
    /*
     * 성공시와 실패시의 생성자를 달리한다 . 
     */
    
  //요청 성공시의 ResponseApi(데이터가 없는경우)
    public ResponseDTO() {
    	
        this.success=true; //성공
        this.code = 200;//200코드 고정 
        this.message = "요청성공";//특별히 메시지를 줄 필요가 없다 판단
        this.reqData = null;
        this.timestamp = LocalDateTime.now();//현재시간 
    }
    
    //요청 성공시의 ResponseApi
    public ResponseDTO(T data) {
    	
        this.success=true; //성공
        this.code = 200;//200코드 고정 
        this.message = "요청성공";//특별히 메시지를 줄 필요가 없다 판단
        this.reqData = data;//제네릭으로 받은 데이터 
        this.timestamp = LocalDateTime.now();//현재시간 
    }
    
    
    
  //요청 실패시의 ResponseApi
    public ResponseDTO(int code,String message) {
    
        this.success=false;//실패 
        this.code = code;//코드
        this.message = message;//전달할 메시지
        this.reqData = null; //보낼데이터는 없다 
        this.timestamp = LocalDateTime.now();//현재시간 
    }
    
    
    //시큐리티 필터에서 발생한 예외는 컨트롤러에 전달되지 못하기 때문에 전역 예외 핸들러에서 처리를 하지못한다 = > 따라서 시큐리티에서 발생할 
    //예외를 정의하여 ResponseApi를 생성하고 매개변수로 받아 , objectMapper로 json 형식으로 데이터를 내보낸다 ( 일정한 형식을 유지하고 싶기 떄문 ) 
    public void set405Response(HttpServletResponse response,ResponseDTO<Void> responseApi,ObjectMapper objectMapper) throws JsonProcessingException, IOException {
    	  
    		
    	   response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405 응답코드
           response.setContentType("application/json");
           response.getWriter().write(objectMapper.writeValueAsString(responseApi));
         
    }
    public void set401esponse(HttpServletResponse response,ResponseDTO<Void> responseApi,ObjectMapper objectMapper) throws JsonProcessingException, IOException {
  	  
    	
 	   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401 응답코드 
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseApi));
      
 }
    
    public void set403esponse(HttpServletResponse response,ResponseDTO<Void> responseApi,ObjectMapper objectMapper) throws JsonProcessingException, IOException {
    	  
    	
  	   response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403 응답코드 
         response.setContentType("application/json");
         response.getWriter().write(objectMapper.writeValueAsString(responseApi));
       
  }
    
    
    public void set410esponse(HttpServletResponse response,ResponseDTO<Void> responseApi,ObjectMapper objectMapper) throws JsonProcessingException, IOException {
  	  
    	
   	   response.setStatus(HttpServletResponse.SC_GONE);//410 응답코드 ==> 리프레시 토큰 재발행시에만 사용예정
          response.setContentType("application/json");
   
			response.getWriter().write(objectMapper.writeValueAsString(responseApi));
		
        
   }
    public void setOkesponse(HttpServletResponse response,ResponseDTO<Void> responseApi,ObjectMapper objectMapper) throws JsonProcessingException, IOException {
    	  
    	
    	   response.setStatus(HttpServletResponse.SC_OK);//200 응답코드 ==> 리프레시 토큰 재발행시에만 사용예정
           response.setContentType("application/json");
    
 			response.getWriter().write(objectMapper.writeValueAsString(responseApi));
 		
         
    }
}
