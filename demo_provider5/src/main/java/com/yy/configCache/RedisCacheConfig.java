package com.yy.configCache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * *1*
 * 注解方式配置redis缓存
 * @author yaoliang
 *
 */
//@Configuration  
//@ComponentScan(basePackages = "com.yy.**.service")  
//@EnableCaching  
public class RedisCacheConfig extends CachingConfigurerSupport {  
  
    // @Bean  
    public JedisConnectionFactory redisConnectionFactory() {  
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();  
  
        // Defaults  
        redisConnectionFactory.setHostName("localhost");  
        redisConnectionFactory.setPort(6379);  
        return redisConnectionFactory;  
    }  
  
    // @Bean  
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {  
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();  
        redisTemplate.setConnectionFactory(cf);  
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;  
    }  
  
    // @Bean  
    public CacheManager cacheManager(RedisTemplate redisTemplate) {  
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);  
  
        // Number of seconds before expiration. Defaults to unlimited (0)  
        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)  
        return cacheManager;  
    }

    // @Bean  
	/*@Override
	public KeyGenerator keyGenerator() {
		// TODO Auto-generated method stub
		return new SimpleKeyGenerator();
	}  */
      
}  