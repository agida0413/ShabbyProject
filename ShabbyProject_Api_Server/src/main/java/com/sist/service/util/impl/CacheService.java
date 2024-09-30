package com.sist.service.util.impl;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void printCacheStatus() {
        // 모든 캐시 이름 가져오기
        Collection<String> cacheNames = cacheManager.getCacheNames();
        
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            System.out.println("Cache: " + cacheName);
            System.out.println("Entries: " + cache.getNativeCache());

            // 각 캐시의 값을 출력 (예시로, 특정 키의 값만 출력할 수 있습니다)
            // 특정 키에 대한 값을 확인하려면 다음과 같이 사용할 수 있습니다.
            // Object value = cache.get(key).get();
        }
    }
}