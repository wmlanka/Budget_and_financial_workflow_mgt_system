package com.finance.action;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.finance.dao.DashboardDAO;
import com.finance.dao.PaymentRequestDAO;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;

public class DashboardAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserSession userSession;

	private PaymentRequestDAO paymentRequestDAO;
	private DashboardDAO dashboardDAO;
	
	private String notification;
	private int approvedPayCount;
	private int rejectedPayCount;
	private int pendingPayment;
	private int pendingSourceDoc;
	private double todayRequestedAmount;
	
	String systemDate;
	Format formatter = new SimpleDateFormat("dd-MMM-yyyy");

	@Override
	public String execute() throws Exception {
		try {
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			clearErrorsAndMessages();
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			clearFields();
			
			int pendingApprovals= getPaymentRequestDAO().getPendingApprovalsforUser(userSession);
			if(pendingApprovals>0) {
				String notification = "You have "+ pendingApprovals+" payments for approvals.";
				setNotification(notification);
			}
			
			int totalPendingPR = getDashboardDAO().getTotalPendingPayments(userSession);
			int totalPendingInvoices = getDashboardDAO().getTotalPendingSourceDocs(userSession);
			int approvedPaymentsToday = getDashboardDAO().getApprovedPaymentsToday(userSession);
			int rejectedPaymentsToday = getDashboardDAO().getRejectedPaymentsToday(userSession);
			double totalPRAmount = getDashboardDAO().getRequestedPaymentsToday(userSession)==null?0.00:getDashboardDAO().getRequestedPaymentsToday(userSession);
			
			setPendingPayment(totalPendingPR);
			setPendingSourceDoc(totalPendingInvoices);
			setApprovedPayCount(approvedPaymentsToday);
			setRejectedPayCount(rejectedPaymentsToday);
			setTodayRequestedAmount(totalPRAmount);
			setSystemDate(formatter.format(new Date()));
			
			return SUCCESS;
		} catch (BaseException e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	
	public void clearFields() {
		setNotification(null);
	}

//	public String getBudgetTypeChart() {
//		try {
//			List<Object[]> list= getDashboardDAO().getTotalUtilizedOfBudgetType(userSession);
//		
//		}catch (BaseException e) {
//			e.printStackTrace();
//		}
//		return SUCCESS;
//	}
	

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public PaymentRequestDAO getPaymentRequestDAO() {
		return paymentRequestDAO;
	}

	public void setPaymentRequestDAO(PaymentRequestDAO paymentRequestDAO) {
		this.paymentRequestDAO = paymentRequestDAO;
	}

	public DashboardDAO getDashboardDAO() {
		return dashboardDAO;
	}

	public void setDashboardDAO(DashboardDAO dashboardDAO) {
		this.dashboardDAO = dashboardDAO;
	}

	public int getApprovedPayCount() {
		return approvedPayCount;
	}

	public void setApprovedPayCount(int approvedPayCount) {
		this.approvedPayCount = approvedPayCount;
	}

	public int getRejectedPayCount() {
		return rejectedPayCount;
	}

	public void setRejectedPayCount(int rejectedPayCount) {
		this.rejectedPayCount = rejectedPayCount;
	}

	public int getPendingPayment() {
		return pendingPayment;
	}

	public void setPendingPayment(int pendingPayment) {
		this.pendingPayment = pendingPayment;
	}

	public int getPendingSourceDoc() {
		return pendingSourceDoc;
	}

	public void setPendingSourceDoc(int pendingSourceDoc) {
		this.pendingSourceDoc = pendingSourceDoc;
	}

	public double getTodayRequestedAmount() {
		return todayRequestedAmount;
	}

	public void setTodayRequestedAmount(double todayRequestedAmount) {
		this.todayRequestedAmount = todayRequestedAmount;
	}

	public String getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}	
}
