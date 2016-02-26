package com.yy.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

 
public class Provider {
 
    public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "provider.xml", "spring-mybatis.xml" });
		//new String[] { "provider.xml", "spring-mybatis.xml", "redis.xml" });*2*
    	//DemoService demoService = (DemoService)ContextLoaderListener.getCurrentWebApplicationContext().getBean("provider.xml");
        context.start();
        //context.destroy();
        //context.stop();
        //context.close();
 
        System.in.read(); 
    }
 
}