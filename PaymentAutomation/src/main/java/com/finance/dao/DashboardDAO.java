package com.finance.dao;

import java.util.List;

import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.finance.dto.PaymentRequestDTO;
import com.finance.util.BaseException;

public interface DashboardDAO {
	public int getApprovedPaymentsToday(UserSession userSession) throws BaseException;
	public int getRejectedPaymentsToday(UserSession userSession) throws BaseException;
	public int getTotalPendingPayments(UserSession userSession) throws BaseException;
	public int getTotalPendingSourceDocs(UserSession userSession) throws BaseException;
	public Double getRequestedPaymentsToday(UserSession userSession) throws BaseException;
	
	public List<BudgetDTO> getTotalUtilizedOfBudgetType(UserSession userSession) throws BaseException;
	public List<PaymentRequestDTO> getTotalPRByDepartments(UserSession userSession) throws BaseException;
}
