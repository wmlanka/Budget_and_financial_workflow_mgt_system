package com.finance.dto;

import java.util.Date;

public class PREntriesDTO {
	private int prEntryId;
	private int prId;
	private int stakeholderId;
	private String drCr;
	private String stkAccountNo;
	private Integer ledgerAccountId;
	private double amount;
	private Date createdDate;
	private String prNo;
	private String ledgerAccountName;
	private String accountNo;
	private String stakeholderName;
	private String payMode;
	
	public int getPrEntryId() {
		return prEntryId;
	}
	public void setPrEntryId(int prEntryId) {
		this.prEntryId = prEntryId;
	}
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	public String getDrCr() {
		return drCr;
	}
	public void setDrCr(String drCr) {
		this.drCr = drCr;
	}
	public String getStkAccountNo() {
		return stkAccountNo;
	}
	public void setStkAccountNo(String stkAccountNo) {
		this.stkAccountNo = stkAccountNo;
	}
	public Integer getLedgerAccountId() {
		return ledgerAccountId;
	}
	public void setLedgerAccountId(Integer ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getPrNo() {
		return prNo;
	}
	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}
	public String getLedgerAccountName() {
		return ledgerAccountName;
	}
	public void setLedgerAccountName(String ledgerAccountName) {
		this.ledgerAccountName = ledgerAccountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getStakeholderName() {
		return stakeholderName;
	}
	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
}
