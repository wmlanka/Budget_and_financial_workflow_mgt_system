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
@Table(name = "DOCUMENT_UPLOAD")
@Proxy(lazy=false)
public class DocumentUpload {
	private int documentUploadId;
	private Integer sourceDocId;//foreign key
	private Integer prId;//foreign key
	private String attachmentType;//PR, Source doc
	private int sequenceNo;
	private String referenceNo;
	private String docPath;
	private String fileSize;
	private String fileName;//original
	private String renameFileName;
	private String contentType;
	private int departmentId;
	private String createdBy;
	private Date createdDate;
	private int version;
	private String tempDocPath;
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DOC_UPLOAD_ID")
	public int getDocumentUploadId() {
		return documentUploadId;
	}
	public void setDocumentUploadId(int documentUploadId) {
		this.documentUploadId = documentUploadId;
	}
	
	@Column(name = "SOURCE_DOC_ID")
	public Integer getSourceDocId() {
		return sourceDocId;
	}
	public void setSourceDocId(Integer sourceDocId) {
		this.sourceDocId = sourceDocId;
	}
	
	@Column(name = "PR_ID")
	public Integer getPrId() {
		return prId;
	}
	public void setPrId(Integer prId) {
		this.prId = prId;
	}
	
	@Column(name = "ATTACHMENT_TYPE")
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	
	@Column(name = "SEQUENCE_NO")
	public int getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	@Column(name = "REFERENCE_NO")
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	@Column(name = "DOC_PATH")
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	
	@Column(name = "FILE_SIZE")
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	@Column(name = "FILE_NAME")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name = "CONTENT_TYPE")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Column(name = "DEPARTMENT_ID")
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
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
	
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(name = "RENAME_FILE_NAME")
	public String getRenameFileName() {
		return renameFileName;
	}
	public void setRenameFileName(String renameFileName) {
		this.renameFileName = renameFileName;
	}
	
	@Column(name = "TEMP_DOC_PATH")
	public String getTempDocPath() {
		return tempDocPath;
	}
	public void setTempDocPath(String tempDocPath) {
		this.tempDocPath = tempDocPath;
	}

}
