package com.finance.domain;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "APPROVAL_DOC")
@Proxy(lazy=false)
public class ApprovalDocument {
	private int approvalDocId;
	private String approvalType;//BOARD_PAPER
	private String docName;//Board Paper
	private String description;
	private String referenceNo;
	private Date approvedDate;
	private String isPartPayment;
	private double approvedFullAmount;
	private double balanceAmount;//should update in source doc
	private String status;
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private double utilizedAmount;//should update in source doc
	private int version;
	private String isUsed;//should update in source doc
	private int departmentId;//DEPARTMENT_ID
	
	//Transient variable
	private String statusTrans;
	private String dateStr;
	private double adjustAmount;
	private boolean isRelease;//should remove amount
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APPROVAL_DOC_ID")
	public int getApprovalDocId() {
		return approvalDocId;
	}
	public void setApprovalDocId(int approvalDocId) {
		this.approvalDocId = approvalDocId;
	}
	
	@Column(name = "DOC_NAME")
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "REF_NUMBER")
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name = "IS_PART_PAYMENT")
	public String getIsPartPayment() {
		return isPartPayment;
	}
	public void setIsPartPayment(String isPartPayment) {
		this.isPartPayment = isPartPayment;
	}
	
	@Column(name = "APPROVED_FULL_AMOUNT")
	public double getApprovedFullAmount() {
		return approvedFullAmount;
	}
	public void setApprovedFullAmount(double approvedFullAmount) {
		this.approvedFullAmount = approvedFullAmount;
	}
	
	@Column(name = "BALANCE_AMOUNT")
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
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
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(name = "APPROVAL_TYPE")
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	
	@Column(name = "UTILIZED_AMOUNT")
	public double getUtilizedAmount() {
		return utilizedAmount;
	}
	public void setUtilizedAmount(double utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}
	
	@Transient
	public String getStatusTrans() {
		return status.equals("A")?"Active":"Inactive";
	}
	public void setStatusTrans(String statusTrans) {
		this.statusTrans = statusTrans;
	}
	
	Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	@Transient
	public String getDateStr() {
		return (approvedDate!=null && !approvedDate.equals(""))?formatter.format(approvedDate):"";
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	@Column(name = "IS_USED")
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	
	@Transient
	public double getAdjustAmount() {
		return adjustAmount;
	}
	public void setAdjustAmount(double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}
	
	@Transient
	public boolean isRelease() {
		return isRelease;
	}
	public void setRelease(boolean isRelease) {
		this.isRelease = isRelease;
	}
	
	@Column(name = "DEPARTMENT_ID")
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
}
