package com.finance.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.finance.dao.ActionCodeDAO;
import com.finance.dao.ApprovalDocumentDAO;
import com.finance.dao.BudgetCodeDAO;
import com.finance.dao.BudgetDAO;
import com.finance.dao.PaymentRequestDAO;
import com.finance.dao.SourceDocumentDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.Budget;
import com.finance.domain.BudgetCode;
import com.finance.domain.PaymentRequest;
import com.finance.domain.TempSourceDocApprovals;
import com.finance.domain.UserSession;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;
import com.opensymphony.xwork2.ActionSupport;


public class AjaxAction extends BaseAction{
	private List<BudgetTypeEnum> budgetTypeList = new ArrayList();
	private List<ActionCode> actionCodeList = new ArrayList();
	private ActionCodeDAO actionCodeDAO;
	private BudgetCodeDAO budgetCodeDAO;
	private ApprovalDocumentDAO approvalDocumentDAO;
	private SourceDocumentDAO sourceDocumentDAO;
	private BudgetDAO budgetDAO;
//	private PaymentRequestDAO paymentRequestDAO;
	private String budgetType;
	private int actionCodeId;
	private String approvalType;
	private Map<String, String> actionCodeMap = new LinkedHashMap<String, String>();
	private Map<String, String> budgetCodeMap = new LinkedHashMap<String, String>();
	private Map<String, String> approvalDocMap = new LinkedHashMap<String, String>();
	
	//source document
	private int approvalDocId;
	private double fullAmount;
	private double balanceAmount;
	
	//budget
	private int budgetCodeId;
	private String year;
	private double allocatedAmount;
	private double utilizedAmount;
	private String description;
	
	private String dummyMsg;

	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	
	@JSON (serialize = false)
	public List<BudgetTypeEnum> getBudgetTypeList() {
		return budgetTypeList;
	}
	public void setBudgetTypeList(List<BudgetTypeEnum> budgetTypeList) {
		this.budgetTypeList = budgetTypeList;
	}	
	
	public List<ActionCode> getActionCodeList() {
		return actionCodeList;
	}
	public void setActionCodeList(List<ActionCode> actionCodeList) {
		this.actionCodeList = actionCodeList;
	}
	
    @JSON (serialize = false)
	public ActionCodeDAO getActionCodeDAO() {
		return actionCodeDAO;
	}
	public void setActionCodeDAO(ActionCodeDAO actionCodeDAO) {
		this.actionCodeDAO = actionCodeDAO;
	}
	
	public Map<String, String> getActionCodeMap() {
		return actionCodeMap;
	}
	public void setActionCodeMap(Map<String, String> actionCodeMap) {
		this.actionCodeMap = actionCodeMap;
	}
	
	//@JSON (serialize = false)
	public String getDummyMsg() {
		return dummyMsg;
	}
	public void setDummyMsg(String dummyMsg) {
		this.dummyMsg = dummyMsg;
	}
	
	public Map<String, String> getBudgetCodeMap() {
		return budgetCodeMap;
	}
	public void setBudgetCodeMap(Map<String, String> budgetCodeMap) {
		this.budgetCodeMap = budgetCodeMap;
	}
	
	public int getActionCodeId() {
		return actionCodeId;
	}
	public void setActionCodeId(int actionCodeId) {
		this.actionCodeId = actionCodeId;
	}
	
    @JSON (serialize = false)
	public BudgetCodeDAO getBudgetCodeDAO() {
		return budgetCodeDAO;
	}
	public void setBudgetCodeDAO(BudgetCodeDAO budgetCodeDAO) {
		this.budgetCodeDAO = budgetCodeDAO;
	}
	
	public Map<String, String> getApprovalDocMap() {
		return approvalDocMap;
	}
	public void setApprovalDocMap(Map<String, String> approvalDocMap) {
		this.approvalDocMap = approvalDocMap;
	}
	
    @JSON (serialize = false)
	public ApprovalDocumentDAO getApprovalDocumentDAO() {
		return approvalDocumentDAO;
	}
	public void setApprovalDocumentDAO(ApprovalDocumentDAO approvalDocumentDAO) {
		this.approvalDocumentDAO = approvalDocumentDAO;
	}
	
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	
	@JSON (serialize = false)
	public SourceDocumentDAO getSourceDocumentDAO() {
		return sourceDocumentDAO;
	}
	public void setSourceDocumentDAO(SourceDocumentDAO sourceDocumentDAO) {
		this.sourceDocumentDAO = sourceDocumentDAO;
	}
	
	public int getApprovalDocId() {
		return approvalDocId;
	}
	public void setApprovalDocId(int approvalDocId) {
		this.approvalDocId = approvalDocId;
	}
	public double getFullAmount() {
		return fullAmount;
	}
	public void setFullAmount(double fullAmount) {
		this.fullAmount = fullAmount;
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}	
	@JSON (serialize = false)
	public BudgetDAO getBudgetDAO() {
		return budgetDAO;
	}
	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}	
	public int getBudgetCodeId() {
		return budgetCodeId;
	}
	public void setBudgetCodeId(int budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public double getAllocatedAmount() {
		return allocatedAmount;
	}
	public void setAllocatedAmount(double allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}
	public double getUtilizedAmount() {
		return utilizedAmount;
	}
	public void setUtilizedAmount(double utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
//	@JSON (serialize = false)
//	public PaymentRequestDAO getPaymentRequestDAO() {
//		return paymentRequestDAO;
//	}
//	public void setPaymentRequestDAO(PaymentRequestDAO paymentRequestDAO) {
//		this.paymentRequestDAO = paymentRequestDAO;
//	}
	
	public String execute() throws Exception {
		try {
			String method = getHttpServletRequest().getParameter("method");
			if(method!=null && method.equals("getActionCodes")) {
			    //budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
	
				actionCodeMap = new  LinkedHashMap<String, String>();;
				//(budgetType.equals("Select Budget Type")) {
					actionCodeMap.put("0","Select Action Code");
				//}
				List<ActionCode> list = getActionCodeDAO().getAllActionCodeByBudgetType(budgetType);
				for(ActionCode ac : list) {
					actionCodeMap.put(ac.getActionId()+"", ac.getDescription());
				}
			}else if(method!=null && method.equals("getBudgetCodes")) {
				budgetCodeMap = new  LinkedHashMap<String, String>();;
				budgetCodeMap.put("0","Select Budget Code");
	     		List<BudgetCode> list = getBudgetCodeDAO().getAllBudgetCodeByActionType(actionCodeId);
				for(BudgetCode bc : list) {
					budgetCodeMap.put(bc.getBudgetCodeId()+"", bc.getDescription());
				}
			}else if(method!=null && method.equals("getApprovalDocs")) {
				approvalDocMap = new  LinkedHashMap<String, String>();;
				approvalDocMap.put("0", "Select Approval No");
				UserSession userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
				if(userSession!=null) {
					List<ApprovalDocument> list = getApprovalDocumentDAO().getApprovalDocByApprovalType(approvalType, userSession);
		     		for(ApprovalDocument ad : list) {
		     			approvalDocMap.put(ad.getApprovalDocId()+"", ad.getReferenceNo());
					}
				}
	     		
			}else if(method!=null && method.equals("getApprovalDocsData")) {
				ApprovalDocument approvalDocument = getApprovalDocumentDAO().getApprovalDocumentById(getApprovalDocId());
				fullAmount = approvalDocument.getApprovedFullAmount();
				balanceAmount = approvalDocument.getBalanceAmount();
			}
			else if(method!=null && method.equals("getAvailableApprovalDocs")) {
				approvalDocMap = new  LinkedHashMap<String, String>();;
				approvalDocMap.put("0", "Select Approval No");
				UserSession userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
				if(userSession!=null) {
					List<ApprovalDocument> list = getApprovalDocumentDAO().getAvailableApprovalDoc(approvalType,0, userSession);
		     		for(ApprovalDocument ad : list) {
		     			approvalDocMap.put(ad.getApprovalDocId()+"", ad.getReferenceNo()+(ad.getIsPartPayment().equals("Y")?" (Allow Part Payment)":" (Full Payment)"));
					}
				}
	     		
	     		
			}else if(method!=null && method.equals("getBudgetData")) {
				try {
					if(budgetCodeId>0 && year!=null) {
						Budget budget = getBudgetDAO().getBudgetForPayRequest(budgetCodeId,year);
						allocatedAmount = budget!=null?budget.getAllocatedAmount():0.00;
						balanceAmount = budget!=null?budget.getBalanceAmount():0.00;
						utilizedAmount = budget!=null?budget.getUtilizedAmount():0.00;
						description = budget!=null?budget.getDescription():null;	
					}else {
						allocatedAmount = 0.00;
						balanceAmount = 0.00;
						utilizedAmount = 0.00;
						description = null;
					}
				}catch(BaseException e) {
					allocatedAmount = 0.00;
					balanceAmount = 0.00;
					utilizedAmount = 0.00;
					description = null;
				}
				
				
			}
			/*
			 * else if(method!=null && method.equals("getPRAmountSummary")) { PaymentRequest
			 * pr = getPaymentRequestDAO().getPayDeductionSummary(null)
			 * 
			 * }
			 */
	        dummyMsg = "Ajax action Triggered";
		}catch (BaseException e) {
			addActionMessage(e.getMessage());
		}
		return SUCCESS;
	}
}
