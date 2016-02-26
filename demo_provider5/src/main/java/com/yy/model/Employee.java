package com.yy.model;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;//名字
	private String email;//邮箱
	//@JsonIgnore
	private String address;//地址
	private String phoneNumber;//电话
	private String remark;//说明
	private long joinTime;//时间
	private int status;//状态
	
	public Employee(){}
	
	public Employee(int id, String name, String email, String address, String phoneNumber, String remark, long joinTime,
			int status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.remark = remark;
		this.joinTime = joinTime;
		this.status = status;
	}
	public Employee(String name, String email, String address, String phoneNumber, String remark, long joinTime,
			int status) {
		super();
		this.name = name;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.remark = remark;
		this.joinTime = joinTime;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getJoinTimeValue() {
		Date date = new Date(joinTime);
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
		return adf.format(date);
		
	}
	public long getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}
	
	public static Map<Integer,String> map = new HashMap<Integer,String>();
	static{
		map.put(0,"启用");
		map.put(1,"停用");
	}
	public String getStatusName() {
		return map.get(status);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", remark=" + remark + ", joinTime=" + joinTime + ", status=" + status +"\n";
	}
}
