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
@Table(name = "PR_STK_SOURCE_DOC")
@Proxy(lazy=false)
public class PRStakeholderSourceDoc {
	private int prStkSourceDocId;
	private int prId;
	private int stakeholderId;
	private int sourceDocId;
	private String createdBy;
	private Date createdDate;
	private int departmentId;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PR_STK_SRC_DOC_ID")
	public int getPrStkSourceDocId() {
		return prStkSourceDocId;
	}
	public void setPrStkSourceDocId(int prStkSourceDocId) {
		this.prStkSourceDocId = prStkSourceDocId;
	}
	
	@Column(name = "PR_ID")
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	
	@Column(name = "STAKEHOLDER_ID")
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	@Column(name = "SOURCE_DOC_ID")
	public int getSourceDocId() {
		return sourceDocId;
	}
	public void setSourceDocId(int sourceDocId) {
		this.sourceDocId = sourceDocId;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "DEPARTMENT_ID")
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}	
	
}
