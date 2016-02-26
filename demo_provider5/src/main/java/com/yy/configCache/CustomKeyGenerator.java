package com.yy.configCache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class CustomKeyGenerator {
	public KeyGenerator customKeyGenerator() {  
	    return new KeyGenerator() {  
	        @Override  
	        public Object generate(Object o, Method method, Object... objects) {  
	            StringBuilder sb = new StringBuilder();  
	            sb.append(o.getClass().getName());  
	            sb.append(method.getName());  
	            for (Object obj : objects) {  
	                sb.append(obj.toString());  
	            }  
	            return sb.toString();  
	        }  
	    };  
	} 
}
