package com.finance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "TEMP_PR_STK_SOURCE_DOC")
@Proxy(lazy=false)
public class TempPRStkSourceDoc {
	private int stakeholderId;
	private int sourceDocId;
	private double vatAmount;
	private double otherTaxAmount;
	private double netAmount;
	private double grossAmount;
	private String userId;
	private int prId;
	
	@Column(name = "STAKEHOLDER_ID")
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	@Column(name = "SOURCE_DOC_ID")
	public int getSourceDocId() {
		return sourceDocId;
	}
	public void setSourceDocId(int sourceDocId) {
		this.sourceDocId = sourceDocId;
	}
	
	@Column(name = "VAT_AMOUNT")
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	
	@Column(name = "OTHER_TAX")
	public double getOtherTaxAmount() {
		return otherTaxAmount;
	}
	public void setOtherTaxAmount(double otherTaxAmount) {
		this.otherTaxAmount = otherTaxAmount;
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
	
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "PR_ID")
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	
	
}
