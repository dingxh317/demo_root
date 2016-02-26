package com.yy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.yy.dao.EmployeeDao;
import com.yy.model.Employee;
import com.yy.redis.util.JedisFactory;
import com.yy.redis.util.JedisUtil;
import com.yy.redis.util.JsonUtil;
import com.yy.service.EmployeeService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * 此service适用于demo_customer5的EmployeeController、EmployeeController2
 * @author yaoliang
 *
 */
//@Service
public class EmployeeServiceImpl implements EmployeeService {

	//@Resource
	EmployeeDao employeeDao;

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * 添加雇员到数据库
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要添加的雇员信息
	 */
	@Override
	@Transactional
	//@CacheEvict(value = { "getEmployees"}, allEntries = true)  
	public int add(Employee e) {
		int i = employeeDao.add(e);
		if(i>0){
			String key = this.getClass().getName()+"getEmployees";
			
			JedisUtil.hset(key, e.getId()+"", e);
		}
		return i;
	}

	/**
	 * 删除雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要删除的雇员id
	 */
	@Transactional
	@Override
	//@CacheEvict(value = { "getEmployees", "getEmployeeById" }, allEntries = true)  
	public boolean deleteEmployeeById(int id) {
		boolean b = employeeDao.deleteEmployeeById(id);
		if(b){
			String key = this.getClass().getName()+"getEmployees";
			
			JedisUtil.hdel(key, id+"");
		}
		return b;
	}

	/**
	 * 修改雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要修改的雇员id
	 * @CacheEvict(value = "user", allEntries = true) //移除所有数据  
	 * @CacheEvict(value = "user", key = "#user.id") //移除指定key的数据  
	 */
	@Transactional
	@Override
	//@CachePut(value = { "getEmployees", "getEmployeeById" })  
	public boolean update(Employee e) {
		return employeeDao.update(e);
	}

	/**
	 * 获取雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要获取的雇员id
	 */
	@Override
	//@Cacheable(value="getEmployeeById",key="#root.target+#root.methodName+#employee.getName()")
	public Employee getEmployeeById(Employee employee) {
		String key = this.getClass().getName()+"getEmployees";
		Employee e = JedisUtil.hget(key, employee.getId()+"", Employee.class);
		if(e == null){
			e = employeeDao.getEmployeeById(employee.getId());
			JedisUtil.hset(key, e.getId()+"", e);
		}
		return e;
	}

	@Override
	public List<Employee> getEmployees(Map<String, Integer> map) {
		// return employeeDao.getEmployees(map);
		return null;
	}

	/**
	 * 显示雇员列表，分页0,20
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @return
	 */
	//@Cacheable(value="getEmployees",key="#root.target+#root.methodName")
	@Override
	public List<Employee> getEmployees() {
		String key = this.getClass().getName()+"getEmployees";
		
		/*使用RedisUtil工具类及管道操作redis的实例：使用的是redis的hash类型存储的list列表数据 start*/
		List<Employee> list = null;
		
		Jedis jedis = JedisFactory.getInstance().getSource();
        //jedis.flushDB();  
        Pipeline p = jedis.pipelined();
        Response<List<String>> res = p.hvals(key);
        p.sync();// 这段代码获取所有的response 
        List<String> dataList = res.get();
        if(dataList == null || dataList.isEmpty()){
        	
			list = employeeDao.getEmployees(0, 20);
			for (Employee e : list) {
				p.hset(key, e.getId()+"", JsonUtil.toJson(e));
			}

			// System.out.println(p.get("age2" + i));
			
        }else{
        	list = new ArrayList<Employee>(dataList.size());
        	
        	for (int i = 0; i < dataList.size(); i++) {
        		list.add(JsonUtil.fromJson(dataList.get(i), Employee.class));
			}
        }
  
        JedisFactory.getInstance().release(jedis);
        
        return list;
		/*使用RedisUtil工具类及管道操作redis的实例：使用的是redis的hash类型存储的list列表数据 end*/
		
		//----------------------------------------------------------------------------
		
		/*使用RedisLink工具类操作redis的实例：使用的是redis的list类型存储的list列表数据 start*/
        
		/*List<Employee> datas = new ArrayList<Employee>();
		RedisLink rl = new RedisLink("localhost",6379,null);*/
		
		/* 从redis客户端中取出数据样式
		 * 127.0.0.1:6379> keys *
			1) "com.yy.service.impl.EmployeeServiceImplgetEmployees"
			 127.0.0.1:6379> LRANGE  "com.yy.service.impl.EmployeeServiceImplgetEmployees" 0 20
			 1) "{\"id\":168,\"name\":\"1\",\"email\":\"111@qq.com\",\"address\":\"1\",\"phoneNumber\":\"18611278794\",\"remark\":\"aaa\",\"joinTime\":0,\"status\":1}"
			 2) "{\"id\":196,\"name\":\"10\",\"email\":\"10@qq.com\",\"address\":\"10\",\"phoneNumber\":\"010-88888888\",\"remark\":\"101010\",\"joinTime\":1453910400000,\"status\":1}"
			 3) "{\"id\":195,\"name\":\"10\",\"email\":\"10@qq.com\",\"address\":\"10\",\"phoneNumber\":\"010-88888888\",\"remark\":\"101010\",\"joinTime\":1453910400000,\"status\":1}"
			 4) "{\"id\":194,\"name\":\"10\",\"email\":\"10@qq.com\",\"address\":\"10\",\"phoneNumber\":\"010-88888888\",\"remark\":\"101010\",\"joinTime\":1453910400000,\"status\":1}"
			 5) "{\"id\":176,\"name\":\"5\",\"email\":\"5@qq.com\",\"address\":\"5\",\"phoneNumber\":\"010-88888888\",\"remark\":\"555\",\"joinTime\":1454688000000,\"status\":0}"
			 6) "{\"id\":175,\"name\":\"5\",\"email\":\"5@qq.com\",\"address\":\"5\",\"phoneNumber\":\"010-88888888\",\"remark\":\"555\",\"joinTime\":1454688000000,\"status\":1}"
			 7) "{\"id\":173,\"name\":\"4\",\"email\":\"4@qq.com\",\"address\":\"4\",\"phoneNumber\":\"010-88888888\",\"remark\":\"44444\",\"joinTime\":1455120000000,\"status\":1}"
			 8) "{\"id\":193,\"name\":\"9\",\"email\":\"9@qq.com\",\"address\":\"9\",\"phoneNumber\":\"010-88888888\",\"remark\":\"9999\",\"joinTime\":1455724800000,\"status\":1}"
			 9) "{\"id\":192,\"name\":\"9\",\"email\":\"9@qq.com\",\"address\":\"9\",\"phoneNumber\":\"010-88888888\",\"remark\":\"999\",\"joinTime\":1455724800000,\"status\":1}"
			10) "{\"id\":191,\"name\":\"8\",\"email\":\"8@qq.com\",\"address\":\"8\",\"phoneNumber\":\"010-88888888\",\"remark\":\"888\",\"joinTime\":1455724800000,\"status\":1}"
			11) "{\"id\":181,\"name\":\"77\",\"email\":\"7@qq.com\",\"address\":\"7\",\"phoneNumber\":\"010-88888888\",\"remark\":\"777\",\"joinTime\":1455897600000,\"status\":1}"
			12) "{\"id\":197,\"name\":\"11\",\"email\":\"10@qq.com\",\"address\":\"11\",\"phoneNumber\":\"010-88888888\",\"remark\":\"111111\",\"joinTime\":1456070400000,\"status\":1}"
			13) "{\"id\":177,\"name\":\"6\",\"email\":\"6@qq.com\",\"address\":\"6\",\"phoneNumber\":\"010-88888888\",\"remark\":\"666\",\"joinTime\":1456416000000,\"status\":1}"
			127.0.0.1:6379>
		 * */
		/*List<String> list = rl.lrange(key, 0, 20);
		for (String s : list) {
			Employee e = JsonUtil.fromJson(s, Employee.class);
			datas.add(e);
		}
		
		if(list == null || list.isEmpty()){
			datas = employeeDao.getEmployees(0, 20);
			
			List<String> strValues = new ArrayList<String>();
			
			for (int i = 0; i < datas.size(); i++) {
				strValues.add(JsonUtil.toJson(datas.get(i)));
			}
			rl.lpushMultiInString(key, strValues);
		}
		return datas;*/
		/*使用RedisLink工具类操作redis的实例：使用的是redis的list类型存储的list列表数据 end*/
	}
	
	public static List<Employee> listEmployee(List<Long> ids, String key) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			Pipeline pipeline = jedis.pipelined();
			for (int i = 0; i < ids.size(); i++) {
				pipeline.hget(key, ids.get(i) + "");
				//pipeline.hset(key, field, value);
			}

			List<Object> result = pipeline.syncAndReturnAll();
			List<Employee> list = new ArrayList<Employee>();
			for (int i = 0; i < ids.size(); i++) {
				String json = (String) result.get(i);
				if (StringUtils.isEmptyOrWhitespaceOnly(json)) {
					continue;
				}
				Employee e = JsonUtil.fromJson(json, Employee.class);
				list.set(i, e);
			}
			return list;
		} finally {
			JedisFactory.getInstance().release(jedis);
		}
		
	}

	/**
	 * 获取最后保存到数据库的雇员id
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @return 最后保存到数据库的雇员id
	 */
	@Override
	public Integer getLastId() {
		return employeeDao.getLastId();
	}

}
