package com.finance.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.ActionCodeDAO;
import com.finance.dao.ApprovalDocumentDAO;
import com.finance.dao.BudgetCodeDAO;
import com.finance.dao.BudgetDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.Budget;
import com.finance.domain.BudgetCode;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.finance.enumeration.ApprovalTypeEnum;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class BudgetAction extends BaseAction implements Parameterizable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BudgetDAO budgetDAO;
	private List<Budget> list;
	private List<BudgetDTO> listDTO;
	private String command;//add,update,save
	private UserSession userSession;
	Budget budget = null;

	private int budgetId;
	private int actionId;
	private int budgetCodeId;
	private String description;
	private String approvalType;
	private Integer approvalDocId;
	private String year;
	private double allocatedAmount;
	private double balanceAmount;
	private double utilizedAmount;
	private String remark;
	private String status;	
	private String budgetType;
	
	private List<BudgetTypeEnum> budgetTypeList = new ArrayList();
	private List<Integer> years = new ArrayList<Integer>();
	private List<ApprovalTypeEnum> approvalTypeList;
	
	private List<ActionCode> actionCodeList = new ArrayList<ActionCode>();
	private List<BudgetCode> budgetCodeList = new ArrayList<BudgetCode>();
	private List<ApprovalDocument> approvalDocumentList = new ArrayList<ApprovalDocument>();

	private ActionCodeDAO actionCodeDAO;
	private BudgetCodeDAO budgetCodeDAO;
	private ApprovalDocumentDAO approvalDocumentDAO;
	
	private double approvalFullAmount;
	private double approvalBalanceAmount;
	
	private String searchYear;

	public BudgetDAO getBudgetDAO() {
		return budgetDAO;
	}

	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}

	public List<Budget> getList() {
		return list;
	}

	public void setList(List<Budget> list) {
		this.list = list;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public Integer getApprovalDocId() {
		return approvalDocId;
	}

	public void setApprovalDocId(Integer approvalDocId) {
		this.approvalDocId = approvalDocId;
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

	public void setAllocatedAmount(String allocatedAmount) {
		this.allocatedAmount =  Double.parseDouble(allocatedAmount.replace(",", ""));
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public double getUtilizedAmount() {
		return utilizedAmount;
	}

	public void setUtilizedAmount(String utilizedAmount) {
		this.utilizedAmount =  Double.parseDouble(utilizedAmount.replace(",", ""));
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}

	public List<BudgetDTO> getListDTO() {
		return listDTO;
	}

	public void setListDTO(List<BudgetDTO> listDTO) {
		this.listDTO = listDTO;
	}

	public List<BudgetTypeEnum> getBudgetTypeList() {
		return budgetTypeList;
	}

	public void setBudgetTypeList(List<BudgetTypeEnum> budgetTypeList) {
		this.budgetTypeList = budgetTypeList;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<ApprovalTypeEnum> getApprovalTypeList() {
		return approvalTypeList;
	}

	public void setApprovalTypeList(List<ApprovalTypeEnum> approvalTypeList) {
		this.approvalTypeList = approvalTypeList;
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

	public ApprovalDocumentDAO getApprovalDocumentDAO() {
		return approvalDocumentDAO;
	}

	public void setApprovalDocumentDAO(ApprovalDocumentDAO approvalDocumentDAO) {
		this.approvalDocumentDAO = approvalDocumentDAO;
	}

	public List<ApprovalDocument> getApprovalDocumentList() {
		return approvalDocumentList;
	}

	public void setApprovalDocumentList(List<ApprovalDocument> approvalDocumentList) {
		this.approvalDocumentList = approvalDocumentList;
	}
	
	public double getApprovalFullAmount() {
		return approvalFullAmount;
	}

	public void setApprovalFullAmount(String approvalFullAmount) {
		this.approvalFullAmount =  Double.parseDouble(approvalFullAmount.replace(",", ""));
	}

	public double getApprovalBalanceAmount() {
		return approvalBalanceAmount;
	}

	public void setApprovalBalanceAmount(String approvalBalanceAmount) {
		this.approvalBalanceAmount =  Double.parseDouble(approvalBalanceAmount.replace(",", ""));
	}
	
	public String getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public String execute() throws Exception {
		try {
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        } 
			
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			budget = new Budget();
			listDTO = getAllBudgetDTO();
			budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
			approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
			searchYear="";
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
		}
		return SUCCESS;
	}

	public List<Integer> getYears(){
		years = new ArrayList<Integer>();
        int year=Calendar.getInstance().get(Calendar.YEAR);
        int startYear = year-2;
        for(int a=1;a<7;a++) {
        	years.add(startYear);
        	startYear++;
        }
		return years;
	}
	
	public String create() {
		if (getCommand().equals("add")){
			clearFields();
			budget = new Budget();
			budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
			approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
			getYears();
			return INPUT;
		}else {
			Budget budget = new Budget();
			budget.setBudgetType(getBudgetType());
			budget.setActionId(getActionId());
			budget.setBudgetCodeId(getBudgetCodeId());
			budget.setDescription(getDescription());
			budget.setYear(getYear());
			budget.setApprovalType(getApprovalType());
			budget.setApprovalDocId(getApprovalDocId());
			budget.setAllocatedAmount(getAllocatedAmount());
			budget.setBalanceAmount(getAllocatedAmount());
			budget.setUtilizedAmount(0.0);
			budget.setRemark(getRemark());		
			clearErrorsAndMessages();			
			try {
				getBudgetDAO().createBudget(budget, userSession);
			} catch (BaseException e) {
				setCommand("add");
				addActionMessage(getText(e.getMessage()));
				return INPUT;
			}
		}
		addActionMessage(getText("message.addSuccess"));
		return SUCCESS;
	}
	
	public String update() {
		try {
			if(getCommand().equals("update") && getBudgetId()>0){
				budget = getBudgetDAO().getBudgetById(getBudgetId());
				setBudgetId(budget.getBudgetId());
				setBudgetType(budget.getBudgetType());
				setActionId(budget.getActionId());
				setBudgetCodeId(budget.getBudgetCodeId());
				setDescription(budget.getDescription());
				setYear(budget.getYear());
				setApprovalType(budget.getApprovalType());
				setApprovalDocId(budget.getApprovalDocId());
				setAllocatedAmount(budget.getAllocatedAmount()+"");
				setBalanceAmount(budget.getBalanceAmount());
				setUtilizedAmount(budget.getUtilizedAmount()+"");
				setRemark(budget.getRemark());
				setStatus(budget.getStatus());
				loadDropDownData();
				return INPUT;
			}		
			else if(getCommand().equals("save")) {
				if (getAllocatedAmount() < getUtilizedAmount()) {
					addFieldError("utilizedAmount",getText("message.allocatedAmount"));
					setCommand("update");
					return INPUT;
				}
				budget.setBudgetType(getBudgetType());
				budget.setActionId(getActionId());
				budget.setBudgetCodeId(getBudgetCodeId());
				budget.setDescription(getDescription());
				budget.setYear(getYear());
				budget.setApprovalType(getApprovalType());
				budget.setApprovalDocId(getApprovalDocId());
				budget.setAllocatedAmount(getAllocatedAmount());
				budget.setBalanceAmount(getAllocatedAmount()-budget.getUtilizedAmount());
				budget.setUtilizedAmount(budget.getUtilizedAmount());
				budget.setRemark(getRemark());	
				budget.setStatus(getStatus());
				clearErrorsAndMessages();

				try {
					getBudgetDAO().updateBudget(budget, userSession);
					addActionMessage(getText("message.updateSuccess"));
				}catch(BaseException e) {
					setCommand("update");
					addActionMessage(getText(e.getMessage()));
					return INPUT;
				}
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}
	
	public void loadDropDownData() throws BaseException {
		getYears();
		budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
		approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
		
		if(budgetType!=null)
			actionCodeList = getActionCodeDAO().getAllActionCodeByBudgetType(budgetType);
		if(actionId != 0)
			budgetCodeList = getBudgetCodeDAO().getAllBudgetCodeByActionType(actionId);
		if(approvalType!=null) {
			//approvalDocumentList = getApprovalDocumentDAO().getApprovalDocByApprovalType(approvalType, userSession);
			approvalDocumentList = getApprovalDocumentDAO().getAvailableApprovalDoc(approvalType, getApprovalDocId(), userSession);
		}
		if(getApprovalDocId()!=null && getApprovalDocId()>0) {
			ApprovalDocument o = getApprovalDocumentDAO().getApprovalDocumentById(getApprovalDocId());
			setApprovalFullAmount(o.getApprovedFullAmount()+"");
			setApprovalBalanceAmount(o.getBalanceAmount()+"");
		}
	}
	
	public String delete() {		
		try {
			clearErrorsAndMessages();
			if (getBudgetId()>0){
				getBudgetDAO().deleteBudget(getBudgetId(), userSession);
				addActionMessage(getText("message.deleteSuccess"));
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;		
		}
		return SUCCESS;
	}
	
	public void clearFields() {
		setBudgetId(0);
		setActionId(0);
		setBudgetCodeId(0);
		setDescription(null);
		setApprovalType(null);
		setApprovalDocId(0);
		setYear(null);
		setAllocatedAmount("0.0");
	    setBalanceAmount(0.0);
		setUtilizedAmount("0.0");
		setRemark(null);
		setStatus("A");	
		setBudgetType(null);
		actionCodeList = new ArrayList<ActionCode>();
		budgetCodeList = new ArrayList<BudgetCode>();
		approvalDocumentList = new ArrayList<ApprovalDocument>();
		setApprovalBalanceAmount("0.00");
		setApprovalFullAmount("0.00");
	}
	
	public List<Budget> getAllBudget() throws BaseException {
		return getBudgetDAO().getAllBudget();
	}
	
	public List<BudgetDTO> getAllBudgetDTO() throws BaseException {
		return getBudgetDAO().getAllBudgetDTO();
	}
	
	public void validate(){
		clearFieldErrors();
		clearActionErrors();
		boolean error = false;
		
		if(getCommand()!=null && getCommand().equals("save") && !isClearScreen() && !isSearch()) {
			String[] arg= new String[1];
			
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
		    if (getYear()==null || getYear().equals("0")) {
		    	arg[0] = getText("label.year");
		    	addFieldError("year", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getApprovalType()==null || getApprovalType().equals("0")) {
		    	arg[0] = getText("label.approvalType");
		    	addFieldError("approvalType", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getApprovalDocId()<1) {
		    	arg[0] = getText("label.approvalRefNo");
		    	addFieldError("approvalDocId", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getAllocatedAmount()<=0) {
		    	arg[0] = getText("label.allocatedAmount");
		    	addFieldError("allocatedAmount", getText("errors.double", arg));
		    	error = true;
		    }
		    if (getDescription()==null || getDescription().trim().equals("")) {
		    	arg[0] = getText("label.description");
		    	addFieldError("description", getText("errors.required",arg));
		    	error = true;
		    }
		    if(error && getBudgetId()>0)
		    	setCommand("update");
		    if(error && getBudgetId()<=0)
		    	setCommand("add");
		}
		try {
			loadDropDownData();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	public String reset() {
		clearErrorsAndMessages();
		String out = null;
		
		if(getBudgetId()>0) {
			setCommand("update");
			out = update();
		}else {
			setCommand("add");
			out = create();
		}
		return out;	
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
	
	boolean isSearch() {
		if(this.params.get("search")!=null && this.params.get("search").equals("ONE")) {
			return true;
		}
		return false;
	}
	
	public String search() throws Exception {
		try {
			
			
			budget = new Budget();
			if(getSearchYear()!=null && !getSearchYear().equals(""))
				listDTO = budgetDAO.searchAllBudgetDTO(getSearchYear());
			budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
			approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
			return INPUT;
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
		}
		return INPUT;
	}
}


