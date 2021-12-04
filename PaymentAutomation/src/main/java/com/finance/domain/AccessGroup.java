package com.finance.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


@Entity
@Table(name = "ACCESS_GROUP")
@Proxy(lazy=false)
public class AccessGroup {
	private int accessGroupId;
	private String code;
	private String description;
	private String cretedBy;
	private Date createdDate;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACCESS_GROUP_ID")
	public int getAccessGroupId() {
		return accessGroupId;
	}
	public void setAccessGroupId(int accessGroupId) {
		this.accessGroupId = accessGroupId;
	}
	
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "CREATED_BY")
	public String getCretedBy() {
		return cretedBy;
	}
	public void setCretedBy(String cretedBy) {
		this.cretedBy = cretedBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
