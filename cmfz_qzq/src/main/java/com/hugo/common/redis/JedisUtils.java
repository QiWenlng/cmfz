package com.hugo.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisUtils {

    @Bean("jedis")
    public Jedis getJedis() {
        return new Jedis("192.168.146.128", 3900);
    }
}
