package com.finance.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

import com.finance.domain.SourceDocApprovals;


public class SourceDocumentDTO{
	private int sourceDocumentId;
	private String documentType;//SourceDocTypeEnum.sourceDocumentType
	private String referenceNo;
	private Date documentDate;
	private String documentDateStr;
	
	private int stakeholderId;
	private String stakeholderName;
	
	private String description;
	private String remark;
	
//	private double netAmount;
//	private double grossAmount;//netAmount+vatAmount+otherTaxAmount
//	private double vatAmount;
//	private double vatPercentage;
//	private double otherTaxAmount;
	
	private BigDecimal netAmount;
	private BigDecimal grossAmount;//netAmount+vatAmount+otherTaxAmount
	private BigDecimal vatAmount;
	private BigDecimal vatPercentage;
	private BigDecimal otherTaxAmount;
	
	private String status;
	private int departmentId;
	
	private String isPRUsed;
	private String isPRUsedStr;
	
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private int version;
	private String select;
	
	private Set<SourceDocApprovals> approvalsList;
	

	public int getSourceDocumentId() {
		return sourceDocumentId;
	}
	public void setSourceDocumentId(int sourceDocumentId) {
		this.sourceDocumentId = sourceDocumentId;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	
	public BigDecimal getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}
	
	public BigDecimal getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}
	
	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}
	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
	
	public BigDecimal getOtherTaxAmount() {
		return otherTaxAmount;
	}
	public void setOtherTaxAmount(BigDecimal otherTaxAmount) {
		this.otherTaxAmount = otherTaxAmount;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getIsPRUsed() {
		return isPRUsed;
	}
	public void setIsPRUsed(String isPRUsed) {
		this.isPRUsed = isPRUsed;
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

	public Set<SourceDocApprovals> getApprovalsList() {
		return approvalsList;
	}
	public void setApprovalsList(Set<SourceDocApprovals> approvalsList) {
		this.approvalsList = approvalsList;
	}
	public String getStakeholderName() {
		return stakeholderName;
	}
	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}
	public String getIsPRUsedStr() {
		return isPRUsedStr;
	}
	public void setIsPRUsedStr(String isPRUsedStr) {
		this.isPRUsedStr = isPRUsedStr;
	}
	
	public String getDocumentDateStr() {
		return documentDateStr;
	}
	public void setDocumentDateStr(String documentDateStr) {
		this.documentDateStr = documentDateStr;
	}
	
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	
//	public boolean isSelect() {
//		return select;
//	}
//	public void setSelect(boolean select) {
//		this.select = select;
//	}		
}

