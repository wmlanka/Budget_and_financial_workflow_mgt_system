package com.finance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.finance.enumeration.ApprovalTypeEnum;

@Entity
@Table(name = "TEMP_SOURCE_DOC_APPROVALS")
@Proxy(lazy=false)
public class TempSourceDocApprovals {
	private int tempSourceDocAppId;
	private int sourceDocumentId;
	private String approvalType;
	private int approvalDocId;
	private String approvalRefNo;
	private double appliedAmount;
	private String userId;
	private String approvalTypeStr;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEMP_SOURCE_DOC_APP_ID")
	public int getTempSourceDocAppId() {
		return tempSourceDocAppId;
	}
	public void setTempSourceDocAppId(int tempSourceDocAppId) {
		this.tempSourceDocAppId = tempSourceDocAppId;
	}
	@Column(name = "SOURCE_DOC_ID")
	public int getSourceDocumentId() {
		return sourceDocumentId;
	}
	public void setSourceDocumentId(int sourceDocumentId) {
		this.sourceDocumentId = sourceDocumentId;
	}
	
	@Column(name = "APPROVAL_TYPE")
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	
	@Column(name = "APPROVAL_DOC_ID")
	public int getApprovalDocId() {
		return approvalDocId;
	}
	public void setApprovalDocId(int approvalDocId) {
		this.approvalDocId = approvalDocId;
	}
	
	@Column(name = "APPROVAL_REF_NO")
	public String getApprovalRefNo() {
		return approvalRefNo;
	}
	public void setApprovalRefNo(String approvalRefNo) {
		this.approvalRefNo = approvalRefNo;
	}
	
	@Column(name = "APPLIED_AMOUNT")
	public double getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
	
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Transient
	public String getApprovalTypeStr() {
		return ApprovalTypeEnum.getEnumByApprovalType(approvalType).getDescription();
	}
	public void setApprovalTypeStr(String approvalTypeStr) {
		this.approvalTypeStr = approvalTypeStr;
	}
	
}
