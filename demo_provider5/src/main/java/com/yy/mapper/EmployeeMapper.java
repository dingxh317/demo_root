package com.yy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.yy.dao.EmployeeDao;
import com.yy.model.Employee;

//@Repository
public class EmployeeMapper implements EmployeeDao {

	private Logger log = Logger.getLogger(EmployeeMapper.class);

	//@Resource
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 添加雇员到数据库
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要添加的雇员信息
	 */
	@Override
	public int add(Employee e) {

		log.info("mapper.java");
		int count = sqlSession.insert("com.yy.mapper.EmployeeMapper.add", e);
		log.info("mapper.java：Employee.id=" + e.getId() + "-->count=" + count);
		return e.getId();
	}

	/**
	 * 删除雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要删除的雇员id
	 */
	@Override
	public boolean deleteEmployeeById(int id) {
		int count = sqlSession.delete("com.yy.mapper.EmployeeMapper.deleteEmployeeById", id);
		return count == 0 ? false : true;
	}

	/**
	 * 修改雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要修改的雇员id
	 */
	@Override
	public boolean update(Employee e) {
		int count = sqlSession.update("com.yy.mapper.EmployeeMapper.update", e);
		return count == 0 ? false : true;
	}

	/**
	 * 获取雇员
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @param 要获取的雇员id
	 */
	@Override
	public Employee getEmployeeById(int id) {
		Employee e = sqlSession.selectOne("com.yy.mapper.EmployeeMapper.getEmployeeById", id);
		return e;
	}

	/*
	 * @Override public List<Employee> getEmployees(int pageno, int pagesize) {
	 * // TODO Auto-generated method stub return null; }
	 */

	/*
	 * @Override public List<Employee> getEmployees() { // TODO Auto-generated
	 * method stub return null; }
	 */

	/**
	 * 获取最后保存到数据库的雇员id
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @return 最后保存到数据库的雇员id
	 */
	@Override
	public Integer getLastId() {
		return sqlSession.selectOne("com.yy.mapper.EmployeeMapper.getLastId");
	}

	/**
	 * 显示雇员列表，分页0,20
	 * @author yy
	 * @time 2015-12-07 9:30:30
	 * @return
	 */
	@Override
	public List<Employee> getEmployees(int offset,int limit) {
		return sqlSession.selectList("com.yy.mapper.EmployeeMapper.getEmployees",new RowBounds(offset, limit));
	}
}
