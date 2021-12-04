package com.finance.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.ActionCodeDAO;
import com.finance.dao.BudgetCodeDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.BudgetCode;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetCodeDTO;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class BudgetCodeAction extends BaseAction implements Parameterizable{
	BudgetCode budgetCode = null;
	private String command;
	private UserSession userSession;
	private List<BudgetCodeDTO> list;
	private BudgetCodeDAO budgetCodeDAO;
	private ActionCodeDAO actionCodeDAO;
	private int budgetCodeId;
	private int actionId;
	private String code;
	private String budgetType;//BudgetTypeEnum
	private String description;
	private String status;
	private List<BudgetTypeEnum> budgetTypeList = new ArrayList();
	private List<ActionCode> actionCodeList = new ArrayList<ActionCode>();


	public BudgetCodeDAO getBudgetCodeDAO() {
		return budgetCodeDAO;
	}

	public void setBudgetCodeDAO(BudgetCodeDAO budgetCodeDAO) {
		this.budgetCodeDAO = budgetCodeDAO;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<BudgetCodeDTO> getList() {
		return list;
	}
	
	public void setList(List<BudgetCodeDTO> list) {
		this.list = list;
	}

	public List<BudgetTypeEnum> getBudgetTypeList() {
		return budgetTypeList;
	}

	public void setBudgetTypeList(List<BudgetTypeEnum> budgetTypeList) {
		this.budgetTypeList = budgetTypeList;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBudgetCodeId() {
		return budgetCodeId;
	}

	public void setBudgetCodeId(int budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}
	
	public ActionCodeDAO getActionCodeDAO() {
		return actionCodeDAO;
	}

	public void setActionCodeDAO(ActionCodeDAO actionCodeDAO) {
		this.actionCodeDAO = actionCodeDAO;
	}

	public List<ActionCode> getActionCodeList() {
		return actionCodeList;
	}

	public void setActionCodeList(List<ActionCode> actionCodeList) {
		this.actionCodeList = actionCodeList;
	}

	@Override
	public String execute() throws Exception {
		try {
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			budgetCode = new BudgetCode();
			list = getBudgetCodeDAO().getAllBudgetCodeDTO();
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}

	public String create() {
		if (getCommand().equals("add")) {
			clearFields();
			budgetCode = new BudgetCode();
			budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
			return INPUT;
		} else {
			budgetCode = new BudgetCode();
			budgetCode.setBudgetType(getBudgetType());
			budgetCode.setActionId(getActionId());
			budgetCode.setBudgetCode(getCode());
			budgetCode.setDescription(getDescription());
			clearErrorsAndMessages();
			try {
				getBudgetCodeDAO().createBudgetCode(budgetCode, userSession);
			} catch (BaseException e) {
				setCommand("add");
				addActionMessage(getText(e.getMessage()));
				return INPUT;
			}
			addActionMessage(getText("message.addSuccess"));
			return SUCCESS;
		}

	}

	public String update() {
		try {
			if (getCommand().equals("update") && getBudgetCodeId() > 0) {
				budgetCode = getBudgetCodeDAO().getBudgetCodeById(getBudgetCodeId());
				budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
				actionCodeList = getActionCodeDAO().getAllActionCodeByBudgetType(budgetCode.getBudgetType());
				setBudgetCodeId(budgetCode.getBudgetCodeId());
				setBudgetType(budgetCode.getBudgetType());
				setActionId(budgetCode.getActionId());
				setCode(budgetCode.getBudgetCode());
				setDescription(budgetCode.getDescription());
				setStatus(budgetCode.getStatus());
				return INPUT;
				
			} else if (getCommand().equals("save")) {
				budgetCode.setBudgetType(getBudgetType());
				budgetCode.setActionId(getActionId());
				budgetCode.setBudgetCode(getCode());
				budgetCode.setDescription(getDescription());
				budgetCode.setStatus(getStatus());
				clearErrorsAndMessages();
				try {
					getBudgetCodeDAO().updateBudgetCode(budgetCode, userSession);
					addActionMessage(getText("message.updateSuccess"));
					return SUCCESS;
				} catch (Exception e) {
					setCommand("update");
					addActionMessage(getText(e.getMessage()));
					return INPUT;				
				}
				
			}
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}

	public String delete() {
		try {
			clearErrorsAndMessages();
			if (getBudgetCodeId() > 0) {
				getBudgetCodeDAO().deleteBudgetCode(getBudgetCodeId(),userSession);
				addActionMessage(getText("message.deleteSuccess"));
			}
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}

	public void validate() {
		clearFieldErrors();
		clearActionErrors();
		boolean error = false;

		if (getCommand() != null && getCommand().equals("save") && !isClearScreen()) {			
			String[] arg = new String[1];

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
			if (getCode() == null || getCode().trim().equals("")) {
				arg[0] = getText("label.budgetCode");
				addFieldError("code", getText("errors.required", arg));
				error = true;
			}
		    if (getDescription()==null || getDescription().trim().equals("")) {
		    	arg[0] = getText("label.description");
		    	addFieldError("description", getText("errors.required",arg));
		    	error = true;
		    }			 
			
			if (error && getBudgetCodeId() > 0)
				setCommand("update");
			if (error && getBudgetCodeId() <= 0)
				setCommand("add");
		}
	}

	public void clearFields() {
		setBudgetCodeId(0);
		setActionId(0);	
		setBudgetType(null);
		setCode(null);
		setDescription(null);
		setStatus("A");
	}

	public String reset() {
		clearErrorsAndMessages();
		String out = null;

		if (getBudgetCodeId() > 0) {
			setCommand("update");
			out = update();
		} else {
			setCommand("add");
			out = create();
		}
		return out;
	}

	private Map<String, String> params = new HashMap<String, String>();

	@Override
	public void addParam(String name, String value) {
	}

	@Override
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public Map<String, String> getParams() {
		return null;
	}

	boolean isClearScreen() {
		if (this.params.get("clear") != null && this.params.get("clear").equals("YES")) {
			return true;
		}
		return false;
	}
}
