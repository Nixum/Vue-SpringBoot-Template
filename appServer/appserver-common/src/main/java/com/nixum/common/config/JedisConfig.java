package com.nixum.common.config;

import com.nixum.common.redis.JedisSetting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

@Configuration
public class JedisConfig {

    @Resource
    JedisSetting jedisSetting;

    @Bean(name = "jedisPool")
    public JedisPool redisPoolFactory() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(jedisSetting.getPool().getMinIdle());
        jedisPoolConfig.setMaxIdle(jedisSetting.getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(jedisSetting.getPool().getMaxWait());

        JedisPool jedisPool = new JedisPool(jedisPoolConfig,
                jedisSetting.getHost(),
                jedisSetting.getPort(),
                jedisSetting.getTimeout(),
                jedisSetting.getPassword());

        return jedisPool;
    }

}
