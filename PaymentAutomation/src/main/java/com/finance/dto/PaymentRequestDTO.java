package com.finance.dto;

import java.util.Date;

public class PaymentRequestDTO {
	private int paymentRequestId;
	private Integer departmentId;
	private String subject;
	private String costCenter;
	private Date prDate;
	private String prDateStr;
	private String budgetType;
	private String payMode;
	
	private int actionCodeId;
	private String actionCode;
	
	private int budgetCodeId;
	private String budgetCode;
	
	private int budgetId;
	private String budgetDescr;
	
	private double payableAmount;
	private double vatAmount;
	private double nbtAmount;
	private double payableOtherAmount;
	private double payableTotal;
	private double withholdingTax;
	private double retainedAmount;
	private double deductionOther;	
	private double penaltyAmount;
	private double deductionTotal;			
	private double netAmount;		
	
	private String currencyType;			
	private String prfStatus;
	private String prNumber;
	private String budgetYear;
	
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private int version;
	
	private int stakeholderId;
	private String stakeholderName;
	
	private int prCount;//for dashboard chart
	private String nextApproval;
	
	public int getPaymentRequestId() {
		return paymentRequestId;
	}
	public void setPaymentRequestId(int paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public Date getPrDate() {
		return prDate;
	}
	public void setPrDate(Date prDate) {
		this.prDate = prDate;
	}
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public int getActionCodeId() {
		return actionCodeId;
	}
	public void setActionCodeId(int actionCodeId) {
		this.actionCodeId = actionCodeId;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
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
	public int getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}
	public String getBudgetDescr() {
		return budgetDescr;
	}
	public void setBudgetDescr(String budgetDescr) {
		this.budgetDescr = budgetDescr;
	}
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	public double getNbtAmount() {
		return nbtAmount;
	}
	public void setNbtAmount(double nbtAmount) {
		this.nbtAmount = nbtAmount;
	}
	public double getPayableOtherAmount() {
		return payableOtherAmount;
	}
	public void setPayableOtherAmount(double payableOtherAmount) {
		this.payableOtherAmount = payableOtherAmount;
	}
	public double getPayableTotal() {
		return payableTotal;
	}
	public void setPayableTotal(double payableTotal) {
		this.payableTotal = payableTotal;
	}
	public double getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(double withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public double getRetainedAmount() {
		return retainedAmount;
	}
	public void setRetainedAmount(double retainedAmount) {
		this.retainedAmount = retainedAmount;
	}
	public double getDeductionOther() {
		return deductionOther;
	}
	public void setDeductionOther(double deductionOther) {
		this.deductionOther = deductionOther;
	}
	public double getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	public double getDeductionTotal() {
		return deductionTotal;
	}
	public void setDeductionTotal(double deductionTotal) {
		this.deductionTotal = deductionTotal;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getPrfStatus() {
		return prfStatus;
	}
	public void setPrfStatus(String prfStatus) {
		this.prfStatus = prfStatus;
	}
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	public String getBudgetYear() {
		return budgetYear;
	}
	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	public String getStakeholderName() {
		return stakeholderName;
	}
	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}
	public String getPrDateStr() {
		return prDateStr;
	}
	public void setPrDateStr(String prDateStr) {
		this.prDateStr = prDateStr;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public int getPrCount() {
		return prCount;
	}
	public void setPrCount(int prCount) {
		this.prCount = prCount;
	}
	public String getNextApproval() {
		return nextApproval;
	}
	public void setNextApproval(String nextApproval) {
		this.nextApproval = nextApproval;
	}
	
}
