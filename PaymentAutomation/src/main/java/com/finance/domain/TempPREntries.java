package com.finance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "TEMP_PR_ENTRIES")
@Proxy(lazy=false)
public class TempPREntries {
	private int tempPREntryId;
	private int prId;
	//private int prStakeholderId;
	private int stakeholderId;
	private String drCr;
	private String stkAccountNo;
	private int ledgerAccountId;
	private double amount;
	private String userId;
	private String stakeholderName;
	private String ledgerAccountNo;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEMP_PR_ENTRIES_ID")
	public int getTempPREntryId() {
		return tempPREntryId;
	}
	public void setTempPREntryId(int tempPREntryId) {
		this.tempPREntryId = tempPREntryId;
	}
	
	@Column(name = "PR_ID")
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	
//	@Column(name = "PR_STK_ID")
//	public int getPrStakeholderId() {
//		return prStakeholderId;
//	}
//	public void setPrStakeholderId(int prStakeholderId) {
//		this.prStakeholderId = prStakeholderId;
//	}
	
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
	public int getLedgerAccountId() {
		return ledgerAccountId;
	}
	public void setLedgerAccountId(int ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}
	
	@Column(name = "AMOUNT")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "STK_NAME")
	public String getStakeholderName() {
		return stakeholderName;
	}
	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}
	
	@Column(name = "ACCOUNT_NO")
	public String getLedgerAccountNo() {
		return ledgerAccountNo;
	}
	public void setLedgerAccountNo(String ledgerAccountNo) {
		this.ledgerAccountNo = ledgerAccountNo;
	}
	
	
}
