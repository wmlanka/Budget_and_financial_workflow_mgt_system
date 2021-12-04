package com.finance.action;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.finance.dao.DashboardDAO;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.finance.dto.PaymentRequestDTO;
import com.finance.util.BaseException;

public class DashboardChartAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<BudgetDTO> list;
	List<BudgetDTO> list2;
	
	private DashboardDAO dashboardDAO;
	
	private UserSession userSession; 
	List<BudgetDTO> budgetTypeList;
	List<PaymentRequestDTO> paymentDeptList;
	
	private String month;
	private int currentYear;

	@Override
	public String execute() throws Exception {
		try {
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			budgetTypeList = getDashboardDAO().getTotalUtilizedOfBudgetType(userSession);
			paymentDeptList = getDashboardDAO().getTotalPRByDepartments(userSession);
			LocalDate currentdate = LocalDate.now();
			Month currentMonth = currentdate.getMonth();
			String str = currentMonth.name().toLowerCase();
			month = str.substring(0, 1).toUpperCase() + str.substring(1);
			currentYear = Calendar.getInstance().get(Calendar.YEAR);
		} catch (BaseException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*public String getBudetTypePayments() {
		list = new ArrayList<BudgetDTO>();

		BudgetDTO o = new BudgetDTO();
		o.setBudgetType("Marketing");
		o.setAllocatedAmount(52);

		BudgetDTO o2 = new BudgetDTO();
		o2.setBudgetType("IT");
		o2.setAllocatedAmount(41);

		BudgetDTO o3 = new BudgetDTO();
		o3.setBudgetType("Supplies");
		o3.setAllocatedAmount(39);

		BudgetDTO o4 = new BudgetDTO();
		o4.setBudgetType("Legal");
		o4.setAllocatedAmount(25);

		BudgetDTO o5 = new BudgetDTO();
		o5.setBudgetType("Other");
		o5.setAllocatedAmount(50);

		list.add(o);
		list.add(o2);
		list.add(o3);
		list.add(o4);
		list.add(o5);

		list2 = new ArrayList<BudgetDTO>();
		BudgetDTO o9 = new BudgetDTO();
		o9.setBudgetType("Capital Expenses");
		o9.setAllocatedAmount(910000);

		BudgetDTO o6 = new BudgetDTO();
		o6.setBudgetType("Marketing Expenses");
		o6.setAllocatedAmount(1229000);

		BudgetDTO o7 = new BudgetDTO();
		o7.setBudgetType("Retail Banking Expenses");
		o7.setAllocatedAmount(119200);

		BudgetDTO o8 = new BudgetDTO();
		o8.setBudgetType("IT Expenses");
		o8.setAllocatedAmount(959000);

		list2.add(o9);
		list2.add(o6);
		list2.add(o7);
		list2.add(o8);

		return SUCCESS;
	}*/

	public List<BudgetDTO> getList() {
		return list;
	}

	public void setList(List<BudgetDTO> list) {
		this.list = list;
	}

	
	public List<BudgetDTO> getList2() {
		return list2;
	}

	public void setList2(List<BudgetDTO> list2) {
		this.list2 = list2;
	}

	@JSON (serialize = false)
	public DashboardDAO getDashboardDAO() {
		return dashboardDAO;
	}

	public void setDashboardDAO(DashboardDAO dashboardDAO) {
		this.dashboardDAO = dashboardDAO;
	}

	public List<BudgetDTO> getBudgetTypeList() {
		return budgetTypeList;
	}

	public void setBudgetTypeList(List<BudgetDTO> budgetTypeList) {
		this.budgetTypeList = budgetTypeList;
	}

	public List<PaymentRequestDTO> getPaymentDeptList() {
		return paymentDeptList;
	}

	public void setPaymentDeptList(List<PaymentRequestDTO> paymentDeptList) {
		this.paymentDeptList = paymentDeptList;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}	

}
