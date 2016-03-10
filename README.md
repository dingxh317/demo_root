# demo_root

this is a simple and test javaweb project which is also a server-client one which contains dubbo2.5.3,redis2.8,jedis2.7.3,mariadb1.3.1,spring4.2.1.RELEASE,springMVC ,mybatis3.2.8,bootstrap,jquery,js,jsp and so on it has CURD operation about a custom java bean named Employee cache has three style one:java APIs+annotation+spring-data-redis two:xml+spring-data-redis three:Jedis

this is a simple and test javaweb project which is also a server-client one. it has CURD operation about a custom java bean named Employee cache has three style one:java APIs+annotation+spring-data-redis two:xml+spring-data-redis three:Jedis

used tools and java APIs:
project encoding:UTF-8
jdk:1.8.0_45
eclipse:Mars.1 Release (4.5.1)
dubbo2.5.3
redis2.8
jedis2.7.3
mariadb1.3.1
spring4.2.1.RELEASE
springMVC 
mybatis3.2.8
bootstrap
jquery
jsp

Attention
if there are errors when you import the demo_root into eclipse,please follow me
the first step:right-click onto the demo_provider5
the second step:choose perproties-->Java Compiler-->compiler compliance level:1.8;perproties-->Project Facts-->java:1.8
the thrid step:refresh the demo_provider5 project
you wil find demo_provider5 is ok

now go on with me and According to the previous threes steps to operate onto the demo_customer5 project.
the first step:right-click onto the demo_customer5
the second step:choose perproties-->Java Compiler-->compiler compliance level:1.8;perproties-->Project Facts-->java:1.8.
Now you find there are still some errors,don't worry.error information:Cannot change version of project facet Dynamic Web Module to 3.0.To solve this problem,please do it.eclipse-->Navigator view-->.settings,open org.eclipse.wst.common.project.facet.core.xml file and make the following changes:<installed facet="jst.web" version="3.0"/>
.then right-click onto the demo_customer5-->Maven-->Update Project...,and you find an other error on Problems view of eclipse:
Dynamic Web Module 3.0 requires Java 1.6 or newer.the error is solved simply,you only add some code in <build></build> of pom.xml of the demo_customer5 project
<build>
  ...
  <-- To add the code -->
  <plugins>  
      <plugin>  
          <groupId>org.apache.maven.plugins</groupId>  
          <artifactId>maven-compiler-plugin</artifactId>  
          <version>2.3.2</version>  
          <configuration>  
              <source>1.6</source>  
              <target>1.6</target>  
          </configuration>  
      </plugin>  
  </plugins> 
</build>

Finally,right-click onto the demo_customer5-->Maven-->Update Project... Now,OK,All questions are disappeared,you can go on your coding

