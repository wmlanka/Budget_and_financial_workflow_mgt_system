package com.finance.domain;

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
@Table(name = "LEDGER_ACCOUNT")
@Proxy(lazy=false)
public class LedgerAccount {
	private int ledgerAccountId;
	private String accountNo;
	private String accountName;
	private String accountCategory;
	private String standardType;
	private String remark;
	private String status;
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	private int version;
	
	private String statusStr;
	private String accountStr;
	private String standardTypeStr;
	private String categoryStr;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LEDGER_ACCOUNT_ID")
	public int getLedgerAccountId() {
		return ledgerAccountId;
	}
	public void setLedgerAccountId(int ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}
	
	@Column(name = "ACCOUNT_NO")
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Column(name = "ACCOUNT_NAME")
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Column(name = "ACCOUNT_CATEGORY")
	public String getAccountCategory() {
		return accountCategory;
	}
	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}
	
	@Column(name = "STANDARD_TYPE")
	public String getStandardType() {
		return standardType;
	}
	public void setStandardType(String standardType) {
		this.standardType = standardType;
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
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	@Transient
	public String getStatusStr() {
		return status.equals("A")?"Active":"Inactive";
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	
	@Transient
	public String getAccountStr() {
		return getAccountNo()+"-"+getAccountName();
	}
	public void setAccountStr(String accountStr) {
		this.accountStr = accountStr;
	}
	
	@Transient
	public String getStandardTypeStr() {
		return standardType.equals("DR")?"Debit":"Credit";
	}
	public void setStandardTypeStr(String standardTypeStr) {
		this.standardTypeStr = standardTypeStr;
	}
	
	@Transient
	public String getCategoryStr() {
		return accountCategory.equals("ASSET")?"Asset":accountCategory.equals("LIABILITY")?"Liability":accountCategory.equals("Equity")?"Equity":
			accountCategory.equals("Income")?"Income":"Expense";
	}
	public void setCategoryStr(String categoryStr) {
		this.categoryStr = categoryStr;
	}	
}
