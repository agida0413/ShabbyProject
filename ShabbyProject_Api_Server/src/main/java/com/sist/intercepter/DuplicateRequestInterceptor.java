package com.sist.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sist.common.exception.ConflictException;
import com.sist.common.util.SimpleCodeGet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 중복(따닥 방지 ) 인터셉터 
@Component
@RequiredArgsConstructor
public class DuplicateRequestInterceptor implements HandlerInterceptor {

    private final Map<String, Boolean> requestMap = new ConcurrentHashMap<>(); //요청 을 저장하는 map = > concurrenthashmap은 멀티스레드에서 안전함 (LOCK) 
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestKey = generateRequestKey(request);

        // 요청이 이미 처리 중이라면 중복 요청으로 간주하고 처리 중단
        if (requestMap.containsKey(requestKey)) {
        	
           throw new ConflictException("이미 처리중인 요청입니다."); //사용자 정의 중복처리 예외 던짐 
          
        }

        // 요청을 Map에 추가하여 중복 요청 차단
        requestMap.put(requestKey, true);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestKey = generateRequestKey(request);
        // 요청이 완료되면 맵에서 해당 요청을 제거하여 다음 요청이 가능하도록 함
        requestMap.remove(requestKey);
    }
    

    private String generateRequestKey(HttpServletRequest request) { 
    	
    		int idNum;
       
	    	try {//사용자 인증후 요청에 대해서 
	    		 idNum=SimpleCodeGet.getIdNum();
			}
	    	catch (Exception e) {//아직로그인 하지않은 요청에 대해서 
				// TODO: handle exception
				idNum=0;
	    	}
	       	
    	
        // 요청의 고유 키를 생성 (HTTP 메소드와 URI , ip , 사용자 고유번호를 조합) 같은 ip여도 다른 브라우저에서 전송할 수도 있으니.. , 회원인증이 안되었을때는 0으로 통일 
        return request.getMethod() + ":" + request.getRequestURI()+":"+request.getLocalAddr()+","+idNum;
   
    }


}

