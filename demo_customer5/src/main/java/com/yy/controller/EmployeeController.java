package com.yy.controller;

import java.util.ArrayList;
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
 * 使用HashMap模拟缓存实现
 * @time 2015-11-20
 * @author yaoliang
 *
 */
@Controller
@RequestMapping("/employeeController")
public class EmployeeController {

	private static Logger log = Logger.getLogger(EmployeeController.class);
	@Resource
	private EmployeeService employeeService;

	// 模拟缓存
	private Map<Integer, Employee> cacheMap = new HashMap<Integer, Employee>();

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
		 
		if (cacheMap.isEmpty()) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("pageno", 0);
			map.put("pagesize", 20);
			// 从数据库中取
			list = employeeService.getEmployees();
			// 放到缓存中
			for (Employee e : list) {
				cacheMap.put(e.getId(), e);
			}
		} else {
			// 从缓存取
			list = new ArrayList<Employee>(cacheMap.values());
		}
		printCacheMap();// 打印
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
			cacheMap.clear();//由于加入新的元素，清空缓存
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.toString());
		}
		return new ModelAndView("redirect:/employeeController/getEmployees.jing");
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
			// 清空缓存
			cacheMap.clear();//由于删除元素，清空缓存
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

		Employee e = cacheMap.get(employee.getId());
		if (e == null) {
			e = employeeService.getEmployeeById(employee);
			cacheMap.put(e.getId(), e);
		}

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
		
		cacheMap.clear();//由于元素变化，清空缓存

		// return result;
		return null;
	}

	/**
	 * 打印雇员信息列表数据
	 */
	public void printCacheMap() {
		log.info(cacheMap.values());
	}

}
