package com.finance.dto;

public class UserDTO {
	private String userId;
	private String userName;
	private String password;
	private int departmentd;
	private int accessGroupId;
	private String userTypeCode;
	private String userTypeDescr;
	private String status;
	private String costCenter;
	//private String designation;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDepartmentd() {
		return departmentd;
	}
	public void setDepartmentd(int departmentd) {
		this.departmentd = departmentd;
	}
	public int getAccessGroupId() {
		return accessGroupId;
	}
	public void setAccessGroupId(int accessGroupId) {
		this.accessGroupId = accessGroupId;
	}
	public String getUserTypeCode() {
		return userTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	public String getUserTypeDescr() {
		return userTypeDescr;
	}
	public void setUserTypeDescr(String userTypeDescr) {
		this.userTypeDescr = userTypeDescr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
}
