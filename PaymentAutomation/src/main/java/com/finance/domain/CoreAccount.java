package com.finance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

//@Entity
//@Table(name = "CORE_ACCOUNTS")
//@Proxy(lazy=false)
public class CoreAccount {
	private String accountNo;

	//@Column(name = "ACCOUNT_NO")
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	
}
