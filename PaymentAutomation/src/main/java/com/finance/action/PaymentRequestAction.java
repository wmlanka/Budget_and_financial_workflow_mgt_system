package com.finance.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.ActionCodeDAO;
import com.finance.dao.BudgetCodeDAO;
import com.finance.dao.BudgetDAO;
import com.finance.dao.LedgerAccountDAO;
import com.finance.dao.PaymentRequestDAO;
import com.finance.dao.SourceDocumentDAO;
import com.finance.dao.StakeholderDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.ApprovalWorkflow;
import com.finance.domain.Budget;
import com.finance.domain.BudgetCode;
import com.finance.domain.LedgerAccount;
import com.finance.domain.Location;
import com.finance.domain.PaymentRequest;
import com.finance.domain.Stakeholder;
import com.finance.domain.TempPREntries;
import com.finance.domain.UserSession;
import com.finance.dto.PRAmountDTO;
import com.finance.dto.SourceDocumentDTO;
import com.finance.enumeration.AccessLevelEnum;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class PaymentRequestAction extends BaseAction implements Parameterizable{
	private UserSession userSession;
	private PaymentRequest paymentRequest;
	private ApprovalWorkflow approvalWorkflow;
	Location location;
	private String command;
	private List<Integer> years = new ArrayList<Integer>();
	private PaymentRequestDAO paymentRequestDAO;
	private ActionCodeDAO actionCodeDAO;
	private BudgetCodeDAO budgetCodeDAO;
	private BudgetDAO budgetDAO;
	private StakeholderDAO stakeholderDAO;
	private SourceDocumentDAO sourceDocumentDAO;
	private LedgerAccountDAO ledgerAccountDAO;
	private String selectTab;
	private PRAmountDTO prAmountDTO;
	
	private List<BudgetTypeEnum> budgetTypeList = new ArrayList();
	private List<ActionCode> actionCodeList = new ArrayList<ActionCode>();
	private List<BudgetCode> budgetCodeList = new ArrayList<BudgetCode>();
	private List<Stakeholder> stakeholderList =  new ArrayList<Stakeholder>();
	private List<SourceDocumentDTO> sourceDocList =  new ArrayList<SourceDocumentDTO>();
	private List<TempPREntries> tempEntryList =  new ArrayList<TempPREntries>();
	private List<Integer> checkInvoices =  new ArrayList<Integer>();
	private List<LedgerAccount> ledgerAccounts =  new ArrayList<LedgerAccount>();

	private int paymentRequestId;
	private String department;
	private int costCenter;
	private String provisionYear;
	private String budgetType;
	private int actionId;
	private int budgetCodeId;
	private int budgetId;//not used
	private String budgetDescr;
	private double allocatedAmount;
	private double balanceAmount;
	private double utilizedAmount;
	private String paymentMode;
	private String[] checksId;
	private int stakeholderId;
	private String subject;
	private String prNumber;
	private String prfStatus;
	
	private double payableAmount;
	private double vatAmount;
	private double payableOther;
	private double payableTotal;
	
	private double withholdingTax;
	private double retainedAmount;
	private double deductOther;
	private double deductTotal;
	private double netAmount;
	
	//entry
	private int ledgerAccountId;
	private String crDr;
	private String customerAccountNo;
	private double amount;
	private int tempPREntryId;
	
	private int searchStkId;
	private String[] checksEntries;
	
	Budget budget;
	
	//popup
	private String rejectReason;
	private String cancelReason;
	
	private String disableUI = "N";//Y-Yes,N-No
	
	@Override
	public String execute() throws Exception {
		try {
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			//setSelectTab("1");
			setCommand("add");
			clearErrorsAndMessages();
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			paymentRequest = null;
			setPaymentRequestId(0);
			clearFields();
			location = getPaymentRequestDAO().getLocationById(userSession.getDepartmentId());
			setDepartment(location.getLocationName());
			setCostCenter(userSession.getDepartmentId());
			loadDropDownData();
			return INPUT;
		} catch (Exception e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
	}
	
	public void loadDropDownData() throws BaseException {
		getYears();
		budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
		stakeholderList = getStakeholderDAO().getAllStakeholder();
		ledgerAccounts = getLedgerAccountDAO().getAllLedgerAccountByStatus("A");
		if(budgetType!=null)
			actionCodeList = getActionCodeDAO().getAllActionCodeByBudgetType(budgetType);
		if(actionId != 0)
			budgetCodeList = getBudgetCodeDAO().getAllBudgetCodeByActionType(actionId);
	}
	
	public List<Integer> getYears(){
		years = new ArrayList<Integer>();
        int year=Calendar.getInstance().get(Calendar.YEAR);
        int startYear = year;
        for(int a=0;a<4;a++) {
        	years.add(startYear);
        	startYear--;
        }
		return years;
	}
	
	public void clearFields() {
		try {
			//setPaymentRequestId(0); dont do this here. update not work
			setPrNumber(null);
			setPrfStatus(null);
			setDepartment(null);
			setCostCenter(0);
			setProvisionYear("0");
			setBudgetId(0);
			setActionId(0);
			setBudgetCodeId(0);
			setBudgetDescr(null);
			setAllocatedAmount("0.0");
		    setBalanceAmount("0.0");
			setUtilizedAmount("0.0");			
			setBudgetType(null);
			setStakeholderId(0);
			setSubject(null);
			actionCodeList = new ArrayList<ActionCode>();
			budgetCodeList = new ArrayList<BudgetCode>();		
			
			//2 - Invoices
			sourceDocList = new ArrayList<SourceDocumentDTO>();
			checkInvoices = new ArrayList<Integer>(); 
			setChecksId(null);
			setPayableAmount("0.00");
			setPayableOther("0.00");
			setPayableTotal("0.00");
			setVatAmount("0.00");
			setWithholdingTax("0.00");
			setRetainedAmount("0.00");
			setDeductOther("0.00");
			setDeductTotal("0.00");
			setNetAmount("0.00");
			prAmountDTO = new PRAmountDTO();
			
			//3 - Entry
			setAmount("0.00");
			setLedgerAccountId(0);
			setCustomerAccountNo("");
			setCrDr("CR");
			tempEntryList = new ArrayList<TempPREntries>();
			getPaymentRequestDAO().deleteTempPREntries(userSession.getUserId());
			setChecksEntries(null);
			
			//popup
			setRejectReason(null);
			setCancelReason(null);
			
			setDisableUI("N");
			
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	
	private Map<String, String> params = new HashMap<String, String>();
	
	@Override
	public void addParam(String name, String value) {}
	
	@Override
	public void setParams(Map<String, String> params) {
		 this.params = params;
	}
	
	@Override
	public Map<String, String> getParams() {
		return null;
	}
	
	boolean isClearScreen() {
		if(this.params.get("clear")!=null && this.params.get("clear").equals("YES")) {
			return true;
		}
		return false;
	}
	
	boolean isSave1() {
		if(this.params.get("save")!=null && this.params.get("save").equals("one")) {
			return true;
		}
		return false;
	}
	
	boolean isSave2() {
		if(this.params.get("save")!=null && this.params.get("save").equals("two")) {
			return true;
		}
		return false;
	}
	
	boolean isSave3() {
		if(this.params.get("save")!=null && this.params.get("save").equals("three")) {
			return true;
		}
		return false;
	}
	
	boolean isSave4() {
		if(this.params.get("save")!=null && this.params.get("save").equals("four")) {
			return true;
		}
		return false;
	}
	
	public double[] validateCrDrTotal() {
		try {
			double[] crDr = getPaymentRequestDAO().checkDrCRofTempPREntries(userSession.getUserId());
			return crDr;
		}catch (Exception e) {
			addFieldError("ledgerAccount", getText(e.getMessage()));
			return null;
		}
	}	
	
	public void validate() {
		clearFieldErrors();
		clearActionErrors();
		boolean error = false;
		String[] arg = new String[1];
		System.out.println(getCommand());

		// Last save in Step 02
		if (isSave1() && !isClearScreen()) {
				if(getNetAmount()<=0) {
					addFieldError("payableAmount", getText("errors.prAmountRequired"));
					error = true;
				}
				if(tempEntryList!=null && tempEntryList.size()>0) {
					double total[] = validateCrDrTotal();
					double dr = total[0];
					double cr = total[1];
					if(dr!=cr) {
						addFieldError("ledgerAccount", getText("error.drcrnottally"));
						error = true;
					}
					else if(dr!=getNetAmount()) {
						addFieldError("ledgerAccount", getText("errors.debitnotequaltopayment"));
						error = true;
					}
//					else if(dr<getPayableAmount()) {
//						addFieldError("ledgerAccount", getText("errors.entriesInvalid"));
//						error = true;
//					}else if(dr<getNetAmount()) {
//						addFieldError("ledgerAccount", getText("error.entriesInvalid"));
//						error = true;
//					}
				}else if(tempEntryList==null || tempEntryList.size()<=0) {
					error = true;
					addFieldError("ledgerAccount", getText("errors.ledgerEntries"));
				}
		}
		
		//Step 01 - submit 
		else if(isSave2() && !isClearScreen()) {
		    if (getBudgetType() == null || getBudgetType().trim().equals("")) {
		    	arg[0] = getText("label.budgetType");
		        addFieldError("budgetType", getText("errors.required",arg));
		        error = true;
		    }
		    if (getActionId()<1) {
		    	arg[0] = getText("label.actionCode");
		    	addFieldError("actionId", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getBudgetCodeId()<1) {
		    	arg[0] = getText("label.budgetCode");
		    	addFieldError("budgetCodeId", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getProvisionYear()==null || getProvisionYear().equals("0")) {
		    	arg[0] = getText("label.year");
		    	addFieldError("year", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getBudgetCodeId()>1 && (getBudgetDescr()==null || getBudgetDescr().trim().equals(""))) {
		    	addFieldError("budgetDescr", getText("errors.budgetNotFound"));
		    	error = true;
		    }
			if (getBalanceAmount()<=0.00) {
				arg[0] = getText("label.balanceAmount");
				addFieldError("balanceAmount", getText("errors.double", arg));
				error = true;
			}
			if (getSubject()==null || getSubject().trim().equals("")) {
		    	arg[0] = getText("label.subject");
		    	addFieldError("subject", getText("errors.required",arg));
		    	error = true;
		    }
			if(getStakeholderId()<=0) {
				arg[0] = getText("label.stakeholder");
				addFieldError("stakeholder", getText("errors.required", arg));
				error = true;
				clearSourceDocSelection();// amounts also creared
			}
			if(getChecksId()==null || getChecksId().length==0) {
				arg[0] = getText("label.invoicesources");
				addFieldError("invoice", getText("errors.required", arg));
				error = true;
			}
			if(error) {
				if(getStakeholderId()>0) {
					getStkSourceDoc();//search invoices
					addStkSourceDoc();//amounts calculated for next page
				}
			}
		}
		
		//Save 3 - Entry add to table
		else if(isSave3() && !isClearScreen()) {
			if(getStakeholderId()<=0) {
				arg[0] = getText("label.stakeholder");
				addFieldError("stakeholder", getText("errors.required", arg));
				error = true;
				clearSourceDocSelection();
			}
			if(getAmount()<=0) {
				arg[0] = getText("label.amount");
				addFieldError("amount", getText("errors.double", arg));
				error = true;
			}
			if(getLedgerAccountId()<=0 && (getCustomerAccountNo()==null || getCustomerAccountNo().trim().equals(""))) {
				arg[0] = getText("label.ledgerOrCustomerAccount");
				addFieldError("ledgerAccount", getText("errors.required", arg));
				error = true;
			}
			if(getLedgerAccountId()>0 && getCustomerAccountNo()!=null && !getCustomerAccountNo().trim().equals("")) {
				addFieldError("ledgerAccount", getText("errors.bothaccountcantadd"));
				error = true;
			}
		}
		
		//search invoices
		else if(isSave4() && !isClearScreen()) {
			if(getStakeholderId()<=0) {
				arg[0] = getText("label.stakeholder");
				addFieldError("stakeholder", getText("errors.required", arg));
				error = true;
				clearSourceDocSelection();// amounts also creared
			}
			if(error) {
				if(getStakeholderId()>0) {
					getStkSourceDoc();
					addStkSourceDoc();
				}
			}
		}
		
		try {
			//if(getCommand()!=null) {
				loadDropDownData();//check this. when first loading page (add/update) all filed are not clear. this become error. ex:budgetType exist
			//}
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	//get Souce Doc Page - Search Invoices
	public String getStkSourceDoc() {
		try {
			//sourceDocList = getSourceDocumentDAO().getSourceDocByStakeholderId(getStakeholderId());
			clearSourceDocSelection();//invoice selection clear, amounts also clear
			sourceDocList = getPaymentRequestDAO().getSourceDocForPR(getStakeholderId(), getPaymentRequestId(), userSession, getPrfStatus());
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			//e.printStackTrace();
		}
		return INPUT;
		
	}
	
	public void clearSourceDocSelection() {
		sourceDocList = new ArrayList<SourceDocumentDTO>();
		setChecksId(null);
		checkInvoices = new ArrayList<Integer>(); 
		
		setPayableAmount("0.00");
		setVatAmount("0.00");
		setPayableOther("0.00");
		setPayableTotal("0.00");
		setWithholdingTax("0.00");
		setRetainedAmount("0.00");
		setDeductOther("0.00");
		setDeductTotal("0.00");
		setNetAmount("0.00");
		prAmountDTO = new PRAmountDTO();
	}
	
	
	//get Total counts
	public String addStkSourceDoc() {
		try {
//			if(getPaymentRequestId()>0 && getChecksId()==null) {//update screen and source doc not selected 
//				setPayableAmount(paymentRequest.getPayableAmount());
//				setVatAmount(paymentRequest.getVatAmount());
//				setPayableOther(paymentRequest.getPayableOtherAmount());
//				setPayableTotal(paymentRequest.getPayableTotal());
//			}else{
				PaymentRequest paymentRequestTemp = getPaymentRequestDAO().getPayDeductionSummary(getChecksId());
				if(paymentRequestTemp!=null) {//when source doc selected
					setPayableAmount(paymentRequestTemp!=null?paymentRequestTemp.getPayableAmount()+"":"0.00");
					setVatAmount(paymentRequestTemp!=null?paymentRequestTemp.getVatAmount()+"":"0.00");
					setPayableOther(paymentRequestTemp!=null?paymentRequestTemp.getPayableOtherAmount()+"":"0.00");
					setPayableTotal(paymentRequestTemp!=null?paymentRequestTemp.getPayableTotal()+"":"0.00");
					setNetAmount((getPayableTotal()-getDeductTotal())+"");
					
				}else if(prAmountDTO!=null){//alreday amount exist
					setPayableAmount(prAmountDTO.getPayableAmount()+"");
					setVatAmount(prAmountDTO.getVatAmount()+"");
					setPayableOther(prAmountDTO.getPayableOtherAmount()+"");
					setPayableTotal(prAmountDTO.getPayableTotal()+"");
					setNetAmount(getPayableTotal()+"");
				}
				
				if(prAmountDTO!=null) {//alreday amount exist
					setWithholdingTax(prAmountDTO.getWithholdingTax()+"");
					setRetainedAmount(prAmountDTO.getRetainedAmount()+"");
					setDeductOther(prAmountDTO.getDeductionOther()+"");
					setDeductTotal(prAmountDTO.getDeductionTotal()+"");
					setNetAmount(getPayableTotal()-getDeductTotal()+"");
				}
				
				checkInvoices = new ArrayList<Integer>();
				//if(paymentRequest!=null) {
					for(SourceDocumentDTO o: sourceDocList) {
						boolean found = false;
						if(getChecksId()!=null) {
							for(String id : getChecksId()) { 
								if(o.getSourceDocumentId()==(Integer.parseInt(id))) {
									o.setSelect("checked");
									found = true;
									checkInvoices.add(Integer.parseInt(id));
								}
							}
						}
						if(!found) {
							o.setSelect("");//this is essentail to uncheck invoice
						}
					}
				//}
				setChecksId(null);//otherwise null coming seeter method not call
			//}
			return SUCCESS;
			//SourceDocument sourceDocument = getSourceDocumentDAO().getSourceDocForPR(getStakeholderId());
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
		}
		return INPUT;
		
	}
	
	public String addTempLedgerEntry() {
		try {
			TempPREntries tempPREntries = new TempPREntries();
			tempPREntries.setLedgerAccountId(getLedgerAccountId());
			tempPREntries.setDrCr(getCrDr());
			tempPREntries.setStkAccountNo(getCustomerAccountNo());
			tempPREntries.setStakeholderId(getStakeholderId());
			tempPREntries.setAmount(getAmount());
			tempPREntries.setPrId(0);
		
			getPaymentRequestDAO().createTempPREntry(tempPREntries, userSession);
			tempEntryList = getPaymentRequestDAO().getTempPREntries(userSession.getUserId());
			
			setCrDr("CR");
			setLedgerAccountId(0);
			setCustomerAccountNo(null);
			setAmount("0.00");
			
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			e.printStackTrace();
		}
		return INPUT;
		
	}
	
	public String callPRTab2() {
		return addStkSourceDoc();
	}
	
	public String goBack() {
		if(prAmountDTO!=null){
			prAmountDTO.setPayableAmount(getPayableAmount());
			prAmountDTO.setVatAmount(getVatAmount());
			prAmountDTO.setPayableOtherAmount(getPayableOther());
			prAmountDTO.setPayableTotal(getPayableAmount()+getVatAmount()+getPayableOther());
		
			prAmountDTO.setWithholdingTax(getWithholdingTax());
			prAmountDTO.setRetainedAmount(getRetainedAmount());
			prAmountDTO.setDeductionOther(getDeductOther());
			prAmountDTO.setDeductionTotal(getWithholdingTax()+getRetainedAmount()+getDeductOther());
			
		}
		return INPUT;
	}
	
	public String create() {
		if(paymentRequest==null)
			paymentRequest = new PaymentRequest();
		
		paymentRequest.setBudgetType(getBudgetType());
		paymentRequest.setSubject(getSubject());
		paymentRequest.setActionCodeId(getActionId());
		paymentRequest.setBudgetCodeId(getBudgetCodeId());
		paymentRequest.setBudgetId(getBudgetId());
		
		paymentRequest.setPayableAmount(getPayableAmount());
		paymentRequest.setVatAmount(getVatAmount());
		paymentRequest.setPayableOtherAmount(getPayableOther());
		paymentRequest.setPayableTotal(getPayableTotal());
		
		paymentRequest.setWithholdingTax(getWithholdingTax());
		paymentRequest.setRetainedAmount(getRetainedAmount());
		paymentRequest.setDeductionOther(getDeductOther());
		paymentRequest.setDeductionTotal(getDeductTotal());
		
		paymentRequest.setNetAmount(getPayableTotal()-getDeductTotal());
		paymentRequest.setCurrencyType("LKR");
		paymentRequest.setPrfStatus("DRAFT");
		paymentRequest.setBudgetYear(getProvisionYear());
		paymentRequest.setPayMode(getPaymentMode());

		clearErrorsAndMessages();
		try {
			if(getPaymentRequestId()>0) {//update
				PaymentRequest pr = getPaymentRequestDAO().updatePaymentRequest(paymentRequest, getStakeholderId(), checkInvoices, userSession);
				//setPrNumber(refNo); no need
				setPaymentRequestId(pr.getPaymentRequestId());
				setPrfStatus(pr.getPrfStatus());
				addActionMessage(getText("message.updateSuccess"));
			}else {
				PaymentRequest pr = getPaymentRequestDAO().createPaymentRequest(paymentRequest, getStakeholderId(), checkInvoices, userSession);
				setPrNumber(pr.getPrNumber());
				setPaymentRequestId(pr.getPaymentRequestId());
				setPrfStatus(pr.getPrfStatus());
				addActionMessage(getText("message.addSuccess"));
			}
			
		} catch (BaseException e) {
			//setCommand("edit");
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;

	}
	
//	public String deleteTempLedgerEntry() {
//		try {
//			if (getTempPREntryId()>0){
//				getPaymentRequestDAO().deleteTempPREntryById(getTempPREntryId());
//				tempEntryList = getPaymentRequestDAO().getTempPREntries(userSession.getUserId());//available temp entries after delete
//			}
//		}catch (BaseException e) {
//			addActionMessage(getText(e.getMessage()));
//			return INPUT;		
//		}
//		return INPUT;
//	}
	
	public String deleteTempLedgerEntry() {
		try {
			if(checksEntries!=null) {
				for(String id : checksEntries) {
					setTempPREntryId(Integer.parseInt(id));
					getPaymentRequestDAO().deleteTempPREntryById(getTempPREntryId());
				}
				tempEntryList = getPaymentRequestDAO().getTempPREntries(userSession.getUserId());//available temp entries after delete
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;		
		}
		return INPUT;
	}
	
	public String update() {
		try {
			if (getCommand().equals("update") && getPaymentRequestId() > 0) {
				setCommand("edit");
				userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");//this should be first. dont chnage
				clearFields();
				
				setPaymentRequestId(getPaymentRequestId());
				paymentRequest = getPaymentRequestDAO().getPaymentRequestAllDataById(getPaymentRequestId(), userSession);
				location = getPaymentRequestDAO().getLocationById(paymentRequest.getDepartmentId());//PR location
				approvalWorkflow = getPaymentRequestDAO().getCurrentApprovalByPRId(getPaymentRequestId(), userSession);
				
				setDepartment(location.getLocationName());
				setCostCenter(paymentRequest.getDepartmentId());
				setPrNumber(paymentRequest.getPrNumber());
				setRejectReason(paymentRequest.getRejectReason());
				setCancelReason(paymentRequest.getCancelReason());
				
				setBudgetType(paymentRequest.getBudgetType());
				setSubject(paymentRequest.getSubject());
				setActionId(paymentRequest.getActionCodeId());
				setBudgetCodeId(paymentRequest.getBudgetCodeId());
				setBudgetId(paymentRequest.getBudgetId());
				
				budget = getBudgetDAO().getBudgetById(paymentRequest.getBudgetId());
				setBudgetDescr(budget.getDescription());
				setAllocatedAmount(budget.getAllocatedAmount()+"");
				setUtilizedAmount(budget.getUtilizedAmount()+"");
				setBalanceAmount(budget.getBalanceAmount()+"");
				
				prAmountDTO.setPayableAmount(paymentRequest.getPayableAmount());
				prAmountDTO.setVatAmount(paymentRequest.getVatAmount());
				prAmountDTO.setPayableOtherAmount(paymentRequest.getPayableOtherAmount());
				prAmountDTO.setPayableTotal(paymentRequest.getPayableTotal());
				
				prAmountDTO.setWithholdingTax(paymentRequest.getWithholdingTax());
				prAmountDTO.setRetainedAmount(paymentRequest.getRetainedAmount());
				prAmountDTO.setDeductionOther(paymentRequest.getDeductionOther());
				prAmountDTO.setDeductionTotal(paymentRequest.getDeductionTotal());
				prAmountDTO.setStakeholderId(paymentRequest.getStakeholderId());				
				setPrfStatus(paymentRequest.getPrfStatus());
				
				setProvisionYear(paymentRequest.getBudgetYear());
				loadDropDownData();
				
				setStakeholderId(paymentRequest.getStakeholderId());
				setPaymentMode(paymentRequest.getPayMode());
				
				//set Invoices
				sourceDocList = getPaymentRequestDAO().getSourceDocForPR(getStakeholderId(), getPaymentRequestId(),userSession,getPrfStatus());
				checkInvoices = new ArrayList();
				for(SourceDocumentDTO o: sourceDocList) {
					if(o.getSelect()!=null && o.getSelect().equals("checked")) {
						checkInvoices.add(o.getSourceDocumentId());
					}
				}
				prAmountDTO.setCheckInvoices(checkInvoices);//selected invoice set
				//load entries
				tempEntryList = getPaymentRequestDAO().getTempPREntries(userSession.getUserId());
				
					setDisableUI("N");
			
			} 

		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String sendPRforApproval() {
		try {
			if(paymentRequest!=null) {
				getPaymentRequestDAO().sendForApproval(paymentRequest.getPaymentRequestId(), userSession);
				approvalWorkflow = getPaymentRequestDAO().getCurrentApprovalByPRId(getPaymentRequestId(), userSession);
				setPrfStatus("PENDING");
				addActionMessage(getText("message.sendForApproval"));
				
				
				setDisableUI("N");
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String approvePR() {
		try {
			if(paymentRequest!=null) {
				getPaymentRequestDAO().approvePayment(paymentRequest.getPaymentRequestId(), approvalWorkflow.getWorkflowId(), userSession);
				approvalWorkflow = getPaymentRequestDAO().getCurrentApprovalByPRId(getPaymentRequestId(), userSession);//new current workflow
				if(approvalWorkflow==null) {
					setPrfStatus("APPROVED");
					addActionMessage(getText("message.paymentApproved"));
				}else {
					setPrfStatus("PENDING");
					addActionMessage(getText("message.sendForNextApproval"));
				}
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String rejectPR() {
		try {
			if(paymentRequest!=null) {
				String reason = getRejectReason();
				getPaymentRequestDAO().rejectPayment(paymentRequest.getPaymentRequestId(), approvalWorkflow.getWorkflowId(), reason, userSession);
				approvalWorkflow = getPaymentRequestDAO().getCurrentApprovalByPRId(getPaymentRequestId(), userSession);//new current workflow
				if(approvalWorkflow==null) {
					setPrfStatus("REJECTED");
					setRejectReason(reason);
					addActionMessage(getText("message.paymentRejected"));
				}
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String cancelPR() {
		try {
			if(paymentRequest!=null) {
				String reason = getCancelReason();
				getPaymentRequestDAO().cancelPayment(paymentRequest.getPaymentRequestId(), approvalWorkflow==null?0:approvalWorkflow.getWorkflowId(), reason, userSession);
				approvalWorkflow = getPaymentRequestDAO().getCurrentApprovalByPRId(getPaymentRequestId(), userSession);//new current workflow
				if(approvalWorkflow==null) {
					setPrfStatus("CANCELED");
					setCancelReason(reason);
					addActionMessage(getText("message.paymentCanceled"));
				}
				
				//disable UI
				if((getPrfStatus().equals("DRAFT") || getPrfStatus().equals("REJECTED")) && 
						(userSession.getUserType().equals(AccessLevelEnum.SYSTEM_ADMIN.getCode()) || userSession.getUserType().equals(AccessLevelEnum.SYSTEM_OPERATOR.getCode()))) {
					setDisableUI("N");
				}else {
					setDisableUI("Y");
				}
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}
		
	public void setYears(List<Integer> years) {
		this.years = years;
	}
	public PaymentRequestDAO getPaymentRequestDAO() {
		return paymentRequestDAO;
	}
	public void setPaymentRequestDAO(PaymentRequestDAO paymentRequestDAO) {
		this.paymentRequestDAO = paymentRequestDAO;
	}	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}	
	public int getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(int costCenter) {
		this.costCenter = costCenter;
	}	
	public String getProvisionYear() {
		return provisionYear;
	}
	public void setProvisionYear(String provisionYear) {
		this.provisionYear = provisionYear;
	}	
	public List<BudgetTypeEnum> getBudgetTypeList() {
		return budgetTypeList;
	}
	public void setBudgetTypeList(List<BudgetTypeEnum> budgetTypeList) {
		this.budgetTypeList = budgetTypeList;
	}		
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public int getBudgetCodeId() {
		return budgetCodeId;
	}
	public void setBudgetCodeId(int budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}
	public int getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}
	public String getBudgetDescr() {
		return budgetDescr;
	}
	public void setBudgetDescr(String budgetDescr) {
		this.budgetDescr = budgetDescr;
	}
	public double getAllocatedAmount() {
		return allocatedAmount;
	}
	public void setAllocatedAmount(String allocatedAmount) {
		this.allocatedAmount =  Double.parseDouble(allocatedAmount.replace(",", ""));
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = Double.parseDouble(balanceAmount.replace(",", ""));
	}
	public ActionCodeDAO getActionCodeDAO() {
		return actionCodeDAO;
	}
	public void setActionCodeDAO(ActionCodeDAO actionCodeDAO) {
		this.actionCodeDAO = actionCodeDAO;
	}
	public BudgetCodeDAO getBudgetCodeDAO() {
		return budgetCodeDAO;
	}
	public void setBudgetCodeDAO(BudgetCodeDAO budgetCodeDAO) {
		this.budgetCodeDAO = budgetCodeDAO;
	}
	public BudgetDAO getBudgetDAO() {
		return budgetDAO;
	}
	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}
	public StakeholderDAO getStakeholderDAO() {
		return stakeholderDAO;
	}
	public void setStakeholderDAO(StakeholderDAO stakeholderDAO) {
		this.stakeholderDAO = stakeholderDAO;
	}
	public SourceDocumentDAO getSourceDocumentDAO() {
		return sourceDocumentDAO;
	}
	public void setSourceDocumentDAO(SourceDocumentDAO sourceDocumentDAO) {
		this.sourceDocumentDAO = sourceDocumentDAO;
	}
	public double getUtilizedAmount() {
		return utilizedAmount;
	}
	public void setUtilizedAmount(String utilizedAmount) {
		this.utilizedAmount =  Double.parseDouble(utilizedAmount.replace(",", ""));
	}
	public List<ActionCode> getActionCodeList() {
		return actionCodeList;
	}
	public void setActionCodeList(List<ActionCode> actionCodeList) {
		this.actionCodeList = actionCodeList;
	}
	public List<BudgetCode> getBudgetCodeList() {
		return budgetCodeList;
	}
	public void setBudgetCodeList(List<BudgetCode> budgetCodeList) {
		this.budgetCodeList = budgetCodeList;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getSelectTab() {
		return selectTab;
	}
	public void setSelectTab(String selectTab) {
		this.selectTab = selectTab;
	}
	public List<Stakeholder> getStakeholderList() {
		return stakeholderList;
	}
	public void setStakeholderList(List<Stakeholder> stakeholderList) {
		this.stakeholderList = stakeholderList;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public List<SourceDocumentDTO> getSourceDocList() {
		return sourceDocList;
	}
	public void setSourceDocList(List<SourceDocumentDTO> sourceDocList) {
		this.sourceDocList = sourceDocList;
	}
	public String[] getChecksId() {
		return checksId;
	}
	public void setChecksId(String[] checksId) {
		this.checksId = checksId;
	}
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}

	public double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(String payableAmount) {
		this.payableAmount =  Double.parseDouble(payableAmount.replace(",", ""));

	}

	public double getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(String vatAmount) {
		this.vatAmount =  Double.parseDouble(vatAmount.replace(",", ""));
		//this.vatAmount = vatAmount;
	}

	public double getPayableOther() {
		return payableOther;
	}

	public void setPayableOther(String payableOther) {
		this.payableOther =  Double.parseDouble(payableOther.replace(",", ""));
	}

	public double getPayableTotal() {
		return payableTotal;
	}
	public void setPayableTotal(String payableTotal) {
		this.payableTotal =  Double.parseDouble(payableTotal.replace(",", ""));
	}
	
	public double getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(String withholdingTax) {
		this.withholdingTax =  Double.parseDouble(withholdingTax.replace(",", ""));
	}
	
	public double getRetainedAmount() {
		return retainedAmount;
	}
	public void setRetainedAmount(String retainedAmount) {
		this.retainedAmount =  Double.parseDouble(retainedAmount.replace(",", ""));
	}
	
	public double getDeductOther() {
		return deductOther;
	}
	public void setDeductOther(String deductOther) {
		this.deductOther =  Double.parseDouble(deductOther.replace(",", ""));
	}
	
	public double getDeductTotal() {
		return deductTotal;
	}
	public void setDeductTotal(String deductTotal) {
		this.deductTotal =  Double.parseDouble(deductTotal.replace(",", ""));
	}
	
	public List<TempPREntries> getTempEntryList() {
		return tempEntryList;
	}
	public void setTempEntryList(List<TempPREntries> tempEntryList) {
		this.tempEntryList = tempEntryList;
	}
	
	public LedgerAccountDAO getLedgerAccountDAO() {
		return ledgerAccountDAO;
	}
	public void setLedgerAccountDAO(LedgerAccountDAO ledgerAccountDAO) {
		this.ledgerAccountDAO = ledgerAccountDAO;
	}
	
	public List<LedgerAccount> getLedgerAccounts() {
		return ledgerAccounts;
	}
	public void setLedgerAccounts(List<LedgerAccount> ledgerAccounts) {
		this.ledgerAccounts = ledgerAccounts;
	}

	public int getLedgerAccountId() {
		return ledgerAccountId;
	}

	public void setLedgerAccountId(int ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}

	public String getCrDr() {
		return crDr;
	}

	public void setCrDr(String crDr) {
		this.crDr = crDr;
	}

	public String getCustomerAccountNo() {
		return customerAccountNo;
	}

	public void setCustomerAccountNo(String customerAccountNo) {
		this.customerAccountNo = customerAccountNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount =  Double.parseDouble(amount.replace(",", ""));
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getTempPREntryId() {
		return tempPREntryId;
	}

	public void setTempPREntryId(int tempPREntryId) {
		this.tempPREntryId = tempPREntryId;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public int getPaymentRequestId() {
		return paymentRequestId;
	}

	public void setPaymentRequestId(int paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}

	public String getPrfStatus() {
		return prfStatus;
	}

	public void setPrfStatus(String prfStatus) {
		this.prfStatus = prfStatus;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public ApprovalWorkflow getApprovalWorkflow() {
		return approvalWorkflow;
	}

	public void setApprovalWorkflow(ApprovalWorkflow approvalWorkflow) {
		this.approvalWorkflow = approvalWorkflow;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount =  Double.parseDouble(netAmount.replace(",", ""));
	}

	public String[] getChecksEntries() {
		return checksEntries;
	}

	public void setChecksEntries(String[] checksEntries) {
		this.checksEntries = checksEntries;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getDisableUI() {
		return disableUI;
	}

	public void setDisableUI(String disableUI) {
		this.disableUI = disableUI;
	}	
}
