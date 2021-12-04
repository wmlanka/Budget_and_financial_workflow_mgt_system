package com.finance.dto;

import java.util.ArrayList;
import java.util.List;

public class PRAmountDTO {
	private int prId;
	private double payableAmount;
	private double vatAmount;
	private double nbtAmount;
	private double payableOtherAmount;
	private double payableTotal;
	private double withholdingTax;
	private double retainedAmount;
	private double deductionOther;	
	private double penaltyAmount;
	private double deductionTotal;
	private int stakeholderId;
	private List<Integer> checkInvoices ;
	
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	public double getNbtAmount() {
		return nbtAmount;
	}
	public void setNbtAmount(double nbtAmount) {
		this.nbtAmount = nbtAmount;
	}
	public double getPayableOtherAmount() {
		return payableOtherAmount;
	}
	public void setPayableOtherAmount(double payableOtherAmount) {
		this.payableOtherAmount = payableOtherAmount;
	}
	public double getPayableTotal() {
		return payableTotal;
	}
	public void setPayableTotal(double payableTotal) {
		this.payableTotal = payableTotal;
	}
	public double getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(double withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public double getRetainedAmount() {
		return retainedAmount;
	}
	public void setRetainedAmount(double retainedAmount) {
		this.retainedAmount = retainedAmount;
	}
	public double getDeductionOther() {
		return deductionOther;
	}
	public void setDeductionOther(double deductionOther) {
		this.deductionOther = deductionOther;
	}
	public double getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	public double getDeductionTotal() {
		return deductionTotal;
	}
	public void setDeductionTotal(double deductionTotal) {
		this.deductionTotal = deductionTotal;
	}
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	public List<Integer> getCheckInvoices() {
		return checkInvoices;
	}
	public void setCheckInvoices(List<Integer> checkInvoices) {
		this.checkInvoices = checkInvoices;
	}
}
