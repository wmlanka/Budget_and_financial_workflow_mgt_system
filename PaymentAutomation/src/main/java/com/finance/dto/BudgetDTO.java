package com.finance.dto;

import java.util.Date;

public class BudgetDTO {
	private int budgetId;
	private String budgetType;
	private int actionId;
	private String actionCode;	
	private int budgetCodeId;
	private String budgetCode;
	private String description;
	private String approvalType;
	private Integer approvalDocId;
	private String approveDocNo;
	private String approvalRefNo;
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
	
	private String budgetMonth;//for budget-month chart
	
	public int getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public int getBudgetCodeId() {
		return budgetCodeId;
	}
	public void setBudgetCodeId(int budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}
	public String getBudgetCode() {
		return budgetCode;
	}
	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public Integer getApprovalDocId() {
		return approvalDocId;
	}
	public void setApprovalDocId(Integer approvalDocId) {
		this.approvalDocId = approvalDocId;
	}
	public String getApproveDocNo() {
		return approveDocNo;
	}
	public void setApproveDocNo(String approveDocNo) {
		this.approveDocNo = approveDocNo;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public double getAllocatedAmount() {
		return allocatedAmount;
	}
	public void setAllocatedAmount(double allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public double getUtilizedAmount() {
		return utilizedAmount;
	}
	public void setUtilizedAmount(double utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getApprovalRefNo() {
		return approvalRefNo;
	}
	public void setApprovalRefNo(String approvalRefNo) {
		this.approvalRefNo = approvalRefNo;
	}
	public String getBudgetMonth() {
		return budgetMonth;
	}
	public void setBudgetMonth(String budgetMonth) {
		this.budgetMonth = budgetMonth;
	}
}
