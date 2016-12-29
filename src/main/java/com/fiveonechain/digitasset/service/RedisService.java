package com.fiveonechain.digitasset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by fanjl on 2016/11/30.
 */
@Component
public class RedisService {
    // CACHE_NAME在redis中会作为key的前缀
    private static final String PREFIX_NAME = "TELEPHONE:";

    // 过期时间设为0，表示永不过期。
    private static final int EXPIRE_TIME = 1;

    @Autowired
    private StringRedisTemplate template;


    // redis set <K,V>
    public void put(String key, String value) {
        template.boundValueOps(PREFIX_NAME + String.valueOf(key))
             .set(value, EXPIRE_TIME, TimeUnit.MINUTES);
    }

    // redis get <K>
    public String get(String key) {
        BoundValueOperations boundValueOperations = template.boundValueOps(PREFIX_NAME + String.valueOf(key));
        //根据key获取value的值
        String value = (String) boundValueOperations.get();
        return value;
    }
//
//    // redis del <K>
//    public void de(String key) {
//        cache.evict(key);
//    }
}
