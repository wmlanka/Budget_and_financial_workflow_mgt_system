package com.finance.dao;

import java.util.List;

import com.finance.domain.ActionCode;
import com.finance.domain.LedgerAccount;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;

public interface ActionCodeDAO {
	public List<ActionCode> getAllActionCodeByBudgetType(String budgetType) throws BaseException; 
	List<ActionCode> getAllActionCode() throws BaseException;
	ActionCode getActionCodeById(int actionId) throws BaseException;
	void createActionCode(ActionCode actionCode,UserSession userSession) throws BaseException;
	void updateActionCode(ActionCode actionCode,UserSession userSession) throws BaseException;
	void deleteActionCode(int actionId,UserSession userSession) throws BaseException;
}
