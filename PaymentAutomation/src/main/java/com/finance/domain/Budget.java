package com.finance.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "BUDGET")
@Proxy(lazy=false)
public class Budget {
	private int budgetId;
	private String budgetType;
	private int actionId;
	private int budgetCodeId;
	private String description;
	private String approvalType;
	private Integer approvalDocId;
	private String year;
	private double allocatedAmount;
	private double balanceAmount;
	private double utilizedAmount;
	private String remark;
	private String status;
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private int version;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BUDGET_ID")
	public int getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}
	
	@Column(name = "ACTION_ID")
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	@Column(name = "BUDGET_CODE_ID")
	public int getBudgetCodeId() {
		return budgetCodeId;
	}
	public void setBudgetCodeId(int budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "APPROVAL_TYPE")
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	
	@Column(name = "APPROVAL_DOC_ID")
	public Integer getApprovalDocId() {
		return approvalDocId;
	}
	public void setApprovalDocId(Integer approvalDocId) {
		this.approvalDocId = approvalDocId;
	}
	
	@Column(name = "YEAR")
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@Column(name = "ALLOCATED_AMOUNT")
	public double getAllocatedAmount() {
		return allocatedAmount;
	}
	public void setAllocatedAmount(double allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}
	
	@Column(name = "BALANCE_AMOUNT")
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	@Column(name = "UTILIZED_AMT")
	public double getUtilizedAmount() {
		return utilizedAmount;
	}
	public void setUtilizedAmount(double utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	@Column(name = "LAST_UPDATED_BY")
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Column(name = "LAST_UPDATED_DATE")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@Column(name = "BUDGET_TYPE")
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
