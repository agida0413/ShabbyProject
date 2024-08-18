package com.sist.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 중복(따닥 방지 ) 인터셉터 
@Component
public class DuplicateRequestInterceptor implements HandlerInterceptor {

    private final Map<String, Boolean> requestMap = new ConcurrentHashMap<>(); //요청 을 저장하는 map 

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestKey = generateRequestKey(request);

        // 요청이 이미 처리 중이라면 중복 요청으로 간주하고 처리 중단
        if (requestMap.containsKey(requestKey)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);//409 에러 
            response.getWriter().write("이미 처리중인 요청");
            return false;
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
        // 요청의 고유 키를 생성 (HTTP 메소드와 URI를 조합)
        return request.getMethod() + ":" + request.getRequestURI();
    }
}
