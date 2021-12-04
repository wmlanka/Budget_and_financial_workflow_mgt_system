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
@Table(name = "PR_ENTRIES")
@Proxy(lazy=false)
public class PREntries {
	private int prEntryId;
	private int prId;
	private int stakeholderId;
	private String drCr;
	private String stkAccountNo;
	private Integer ledgerAccountId;
	private double amount;
	private String createdBy;
	private Date createdDate;
	private int departmentId;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PR_ENTRIES_ID")
	public int getPrEntryId() {
		return prEntryId;
	}
	public void setPrEntryId(int prEntryId) {
		this.prEntryId = prEntryId;
	}
	
	@Column(name = "PR_ID")
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	
	@Column(name = "STAKEHOLDER_ID")
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	@Column(name = "DR_CR")
	public String getDrCr() {
		return drCr;
	}
	public void setDrCr(String drCr) {
		this.drCr = drCr;
	}
	
	@Column(name = "STK_ACCOUNT_NO")
	public String getStkAccountNo() {
		return stkAccountNo;
	}
	public void setStkAccountNo(String stkAccountNo) {
		this.stkAccountNo = stkAccountNo;
	}
	
	@Column(name = "LEDGER_ACCOUNT_ID")
	public Integer getLedgerAccountId() {
		return ledgerAccountId;
	}
	public void setLedgerAccountId(Integer ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}
	
	@Column(name = "AMOUNT")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	
	
}
