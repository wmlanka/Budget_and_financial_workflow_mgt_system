package com.finance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "USER")
@Proxy(lazy=false)
public class User {
	private String userId;
	private String userName;
	private String password;
	private int departmentd;
	private int accessGroupId;
	private String status;
	
	@Id
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "DEPARTMENT_ID")
	public int getDepartmentd() {
		return departmentd;
	}
	public void setDepartmentd(int departmentd) {
		this.departmentd = departmentd;
	}
	
	@Column(name = "ACCESS_GROUP_ID")
	public int getAccessGroupId() {
		return accessGroupId;
	}
	public void setAccessGroupId(int accessGroupId) {
		this.accessGroupId = accessGroupId;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
   
}
