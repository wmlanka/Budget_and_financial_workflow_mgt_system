package com.finance.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "PAYMENT_REQUEST")
@Proxy(lazy=false)
public class PaymentRequest {
	private int paymentRequestId;
	private Integer departmentId;
	private String subject;
	private String costCenter;
	private Date prDate;
	private String budgetType;
	private int actionCodeId;
	private int budgetCodeId;
	private int budgetId;
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
	private String payMode;
	
	private String rejectReason;
	private String cancelReason;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PR_ID")
	public int getPaymentRequestId() {
		return paymentRequestId;
	}
	public void setPaymentRequestId(int paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}
	
	@Column(name = "DEPARTMENT_ID")
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	@Column(name = "SUBJECT")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name = "COST_CENTER")
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	@Column(name = "PR_DATE")
	public Date getPrDate() {
		return prDate;
	}
	public void setPrDate(Date prDate) {
		this.prDate = prDate;
	}
	
	@Column(name = "BUDGET_TYPE")
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	
	@Column(name = "ACTION_CODE_ID")
	public int getActionCodeId() {
		return actionCodeId;
	}
	public void setActionCodeId(int actionCodeId) {
		this.actionCodeId = actionCodeId;
	}
	
	@Column(name = "BUDGET_CODE_ID")
	public int getBudgetCodeId() {
		return budgetCodeId;
	}
	public void setBudgetCodeId(int budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}
	
	@Column(name = "BUDGET_ID")
	public int getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}
	
	@Column(name = "PAYABLE_AMOUNT")
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	
	@Column(name = "VAT_AMOUNT")
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	
	@Column(name = "NBT_AMOUNT")
	public double getNbtAmount() {
		return nbtAmount;
	}
	public void setNbtAmount(double nbtAmount) {
		this.nbtAmount = nbtAmount;
	}
	
	@Column(name = "PAYABLE_OTHER_AMOUNT")
	public double getPayableOtherAmount() {
		return payableOtherAmount;
	}
	public void setPayableOtherAmount(double payableOtherAmount) {
		this.payableOtherAmount = payableOtherAmount;
	}
	
	@Column(name = "PAYABLE_TOTAL")
	public double getPayableTotal() {
		return payableTotal;
	}
	public void setPayableTotal(double payableTotal) {
		this.payableTotal = payableTotal;
	}
	
	@Column(name = "WITHHOLDING_TAX")
	public double getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(double withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	
	@Column(name = "RETAINED_AMOUNT")
	public double getRetainedAmount() {
		return retainedAmount;
	}
	public void setRetainedAmount(double retainedAmount) {
		this.retainedAmount = retainedAmount;
	}
	
	@Column(name = "DEDUCTION_OTHER")
	public double getDeductionOther() {
		return deductionOther;
	}
	public void setDeductionOther(double deductionOther) {
		this.deductionOther = deductionOther;
	}
	
	@Column(name = "PENALTY_AMOUNT")
	public double getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	
	@Column(name = "DEDUCTION_TOTAL")
	public double getDeductionTotal() {
		return deductionTotal;
	}
	public void setDeductionTotal(double deductionTotal) {
		this.deductionTotal = deductionTotal;
	}
	
	@Column(name = "NET_AMOUNT")
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	
	@Column(name = "CURRENCY_TYPE")
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	
	@Column(name = "PRF_STATUS")
	public String getPrfStatus() {
		return prfStatus;
	}
	public void setPrfStatus(String prfStatus) {
		this.prfStatus = prfStatus;
	}
	
	@Column(name = "PR_NUMBER")
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	
	@Column(name = "BUDGET_YEAR")
	public String getBudgetYear() {
		return budgetYear;
	}
	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
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
	
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(name = "STAKEHOLDER_ID")
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	@Column(name = "PAY_MODE")
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	
	@Transient
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	@Transient
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	
}
