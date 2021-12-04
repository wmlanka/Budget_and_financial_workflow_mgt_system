package com.finance.dao;

import java.util.List;

import com.finance.domain.LedgerAccount;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;

public interface LedgerAccountDAO {
	List<LedgerAccount> getAllLedgerAccount() throws BaseException;
	LedgerAccount getLedgerAccountById(int ledgerAccountId) throws BaseException;
	void createLedgerAccount(LedgerAccount ledgerAccount, UserSession userSession) throws BaseException;
	void updateLedgerAccount(LedgerAccount ledgerAccount, UserSession userSession) throws BaseException;
	void deleteLedgerAccount(int ledgerAccountId, UserSession userSession) throws BaseException;
	public List<LedgerAccount> getAllLedgerAccountByStatus(String status) throws BaseException;
}
