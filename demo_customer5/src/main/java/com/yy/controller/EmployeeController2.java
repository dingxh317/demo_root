package com.yy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yy.model.Employee;
import com.yy.service.EmployeeService;

/**
 * 一、使用spring-redis作为缓存，相关的配置支持有两种形式：1.注解+API;2.xml
 * 1.注解+API:demo_provider5的com.yy.configCache.RedisCacheConfig;
 * 2.xml:demo_provider5的redis.xml+spring-mybatis.xml中的部分代码：
 *  <bean id="simpleKeyGenerator" class="org.springframework.cache.interceptor.SimpleKeyGenerator"/>
 *  <cache:annotation-driven cache-manager="cacheManager" proxy-target-class="true"/>
 *  注意：以上两种配置在service层的应用都是通过@Cacheable、@CachePut、@CacheEvict等注解来实现的
 * 二、使用jedis作为缓存，相关配置在RedisLink.java(kaige提供)、JedisUtil.java与JedisFactory.java、JsonUtil.java(liangtao提供)两套工具类，各有优点
 * @time 2015-11-20
 * @author yy
 *
 */
@Controller
@RequestMapping("/employeeController2")
public class EmployeeController2 {

	private static Logger log = Logger.getLogger(EmployeeController2.class);
	@Resource
	private EmployeeService employeeService;

	/**
	 * 显示雇员列表
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @return
	 */
	@RequestMapping("/getEmployees")
	public ModelAndView getEmployees() {
		log.info("-->start getEmployees");
		
		ModelMap model = new ModelMap();
		List<Employee> list;
		 
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pageno", 0);
		map.put("pagesize", 20);
		// 从数据库中取
		list = employeeService.getEmployees();
		model.put("list", list);
		return new ModelAndView("dataList", model);
	}

	/**
	 * 添加雇员到数据库
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要添加的雇员信息
	 */
	@RequestMapping("/addEmployee")
	public ModelAndView addEmployee(Employee e, HttpServletRequest request, HttpServletResponse response) {
		log.info("-->start addEmployee data:"+e);
		
		String status = request.getParameter("status");
		Long createTime = (Long)request.getAttribute("createTime");//过滤器中取值，因为过滤器已经帮我们处理好了，所以我们只需要从request中取就可以了
		
		e.setJoinTime((createTime == null?0:createTime));
		
		try {
			int id = employeeService.add(e);// 添加到数据库
			// int id = employeeService.getLastId();
			// employee.setId(id);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.toString());
		}
		return new ModelAndView("redirect:/employeeController2/getEmployees.jing");
	}

	/**
	 * 删除雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要删除的雇员id
	 */
	@RequestMapping("/deleteEmployee")
	public @ResponseBody boolean deleteEmployee(int id) {
		log.info("detele");
		boolean b = false;
		try {
			// 从数据库删除
			b = employeeService.deleteEmployeeById(id);
			log.info("deleteEmployee Employee[id:" + id + "] delete from db and cache");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}

		return b;
	}

	/**
	 * 获取雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要获取的雇员id
	 */
	@RequestMapping("/getEmployeeById")
	public ModelAndView getEmployeeById(Employee employee,ModelMap model) {
		log.info("getEmployeeById");

		Employee e = employeeService.getEmployeeById(employee);
		
		model.put("employee", e);
		return new ModelAndView("employee/employee_detail",model);
	}

	/**
	 * 修改雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要修改的雇员id
	 */
	@RequestMapping("/updateEmployee")
	public ModelAndView updateEmployee(Employee employee) {
		log.info("update");
		ModelAndView result = new ModelAndView("redirect:/employeeController/getEmployees.jing");

		Employee e = employeeService.getEmployeeById(employee);
		if (e == null) {
			log.info("updateEmployee-->Employee[id:" + employee.getId() + "] is null");
			return result;
		}
		e.setName(e.getName() + "new");

		employeeService.update(e);
		

		// return result;
		return null;
	}
}
