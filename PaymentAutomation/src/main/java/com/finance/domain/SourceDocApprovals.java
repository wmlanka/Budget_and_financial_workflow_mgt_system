package com.finance.domain;

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
@Table(name = "SOURCE_DOC_APPROVALS")
@Proxy(lazy=false)
public class SourceDocApprovals {
	private int sourceDocApprovalId;
	private int sourceDocumentId;
	private String approvalType;
	private int approvalDocId;
	private String approvalRefNo;
	private double appliedAmount;
	private int version;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SOURCE_DOC_APPROVAL_ID")
	public int getSourceDocApprovalId() {
		return sourceDocApprovalId;
	}
	public void setSourceDocApprovalId(int sourceDocApprovalId) {
		this.sourceDocApprovalId = sourceDocApprovalId;
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
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}	
}
