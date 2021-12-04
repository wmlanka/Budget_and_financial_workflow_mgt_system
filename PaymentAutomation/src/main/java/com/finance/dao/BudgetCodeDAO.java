package com.finance.dao;

import java.util.List;

import com.finance.domain.BudgetCode;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetCodeDTO;
import com.finance.util.BaseException;

public interface BudgetCodeDAO {
	public List<BudgetCode> getAllBudgetCodeByActionType(int actionId) throws BaseException; 
	List<BudgetCode> getAllBudgetCode() throws BaseException;
	public List<BudgetCodeDTO> getAllBudgetCodeDTO() throws BaseException;
	BudgetCode getBudgetCodeById(int budgetCodeId) throws BaseException;
	void createBudgetCode(BudgetCode budgetCode,UserSession userSession) throws BaseException;
	void updateBudgetCode(BudgetCode budgetCode,UserSession userSession) throws BaseException;
	void deleteBudgetCode(int budgetCodeId,UserSession userSession) throws BaseException;

}
