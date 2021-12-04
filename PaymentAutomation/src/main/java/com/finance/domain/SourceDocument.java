package com.finance.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "SOURCE_DOCUMENT_HEADER")
@Proxy(lazy=false)
public class SourceDocument{
	private int sourceDocumentId;
	private String documentType;//SourceDocTypeEnum.sourceDocumentType
	private String referenceNo;
	private Date documentDate;
	private int stakeholderId;
	private String description;
	private String remark;
	
	private double netAmount;
	private double grossAmount;//netAmount+vatAmount+otherTaxAmount
	private double vatAmount;
	private double vatPercentage;
	private double otherTaxAmount;
	
	private String status;
	private int departmentId;
	private String isPRUsed;
	
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private int version;
	
	private DocumentUpload documentUpload;	
	//private Set<SourceDocApprovals> approvalsList;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SOURCE_DOC_ID")
	public int getSourceDocumentId() {
		return sourceDocumentId;
	}
	public void setSourceDocumentId(int sourceDocumentId) {
		this.sourceDocumentId = sourceDocumentId;
	}
	
	@Column(name = "SOURCE_DOC_TYPE")
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	@Column(name = "REFERENCE_NO")
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	@Column(name = "SOURCE_DOD_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name = "STAKEHOLDER_ID")
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "NET_AMOUNT")
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	
	@Column(name = "GROSS_AMOUNT")
	public double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}
	
	@Column(name = "VAT_AMOUNT")
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	
	@Column(name = "VAT_PERCENTAGE")
	public double getVatPercentage() {
		return vatPercentage;
	}
	public void setVatPercentage(double vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
	
	@Column(name = "OTHER_TAX_AMOUNT")
	public double getOtherTaxAmount() {
		return otherTaxAmount;
	}
	public void setOtherTaxAmount(double otherTaxAmount) {
		this.otherTaxAmount = otherTaxAmount;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "DEPARTMENT_ID")
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	@Column(name = "IS_PR_USED")
	public String getIsPRUsed() {
		return isPRUsed;
	}
	public void setIsPRUsed(String isPRUsed) {
		this.isPRUsed = isPRUsed;
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
	
	@Transient
	public DocumentUpload getDocumentUpload() {
		return documentUpload;
	}
	public void setDocumentUpload(DocumentUpload documentUpload) {
		this.documentUpload = documentUpload;
	}
	
	
	/*
	 * @OneToMany( cascade={CascadeType.ALL}, //fetch=FetchType.LAZY, orphanRemoval
	 * = true, targetEntity = com.finance.domain.SourceDocApprovals.class, mappedBy
	 * = "sourceDocumentId" )
	 */
	/*
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinTable( name = "SOURCE_DOC_APPROVALS", joinColumns = @JoinColumn(name =
	 * "SOURCE_DOC_ID"))
	 */
	
//	@OneToMany(targetEntity=SourceDocApprovals.class,cascade = CascadeType.ALL, 
//    fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "sourceDocumentId", referencedColumnName = "SOURCE_DOC_ID")
//	public Set<SourceDocApprovals> getApprovalsList() {
//		return approvalsList;
//	}
//	public void setApprovalsList(Set<SourceDocApprovals> approvalsList) {
//		this.approvalsList = approvalsList;
//	}
	
	
	
	
}
