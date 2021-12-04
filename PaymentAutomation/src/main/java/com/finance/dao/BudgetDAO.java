package com.finance.dao;

import java.util.List;

import com.finance.domain.Budget;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;

public interface BudgetDAO {
	List<Budget> getAllBudget() throws BaseException;
	List<BudgetDTO> getAllBudgetDTO() throws BaseException;
	Budget getBudgetById(int budgetId) throws BaseException;
	void deleteBudget(int budgetId, UserSession userSession) throws BaseException;
	void createBudget(Budget budget,UserSession userSession) throws BaseException;
	void updateBudget(Budget budget, UserSession userSession) throws BaseException;
	Budget getBudgetForPayRequest(int budgetCodeId, String year) throws BaseException;
	public List<BudgetDTO> getBudgetSummaryData(String budgetType, int actionCodeId, int budgetCodeId, String year) throws BaseException;
	public double getBudgetUtilizeOfMonth(int year, int month, BudgetTypeEnum budgetTypeEnum) throws BaseException;
	public List<BudgetDTO> searchAllBudgetDTO(String year) throws BaseException;
}
