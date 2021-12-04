package com.finance.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.ActionCodeDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.UserSession;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class ActionCodeAction extends BaseAction implements Parameterizable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ActionCode actionCode = null;
	private String command;
	private UserSession userSession;
	private List<ActionCode> list;
	private ActionCodeDAO actionCodeDAO;
	private List<BudgetTypeEnum> budgetTypeList = new ArrayList();
	private int actionId;
	private String code;
	private String budgetType;//BudgetTypeEnum
	private String description;
	private String status;
	
	public ActionCode getActionCode() {
		return actionCode;
	}

	public void setActionCode(ActionCode actionCode) {
		this.actionCode = actionCode;
	}

	public ActionCodeDAO getActionCodeDAO() {
		return actionCodeDAO;
	}

	public void setActionCodeDAO(ActionCodeDAO actionCodeDAO) {
		this.actionCodeDAO = actionCodeDAO;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<ActionCode> getList() {
		return list;
	}
	
	public void setList(List<ActionCode> list) {
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

	@Override
	public String execute() throws Exception {
		try {
			
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			actionCode = new ActionCode();
			list = getActionCodeDAO().getAllActionCode();
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}

	public String create() {
		if (getCommand().equals("add")) {
			clearFields();
			actionCode = new ActionCode();
			budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
			return INPUT;
		} else {
			actionCode = new ActionCode();
			actionCode.setBudgetType(getBudgetType());
			actionCode.setActionCode(getCode());
			actionCode.setDescription(getDescription());
			clearErrorsAndMessages();
			try {
				getActionCodeDAO().createActionCode(actionCode,userSession);
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
			if (getCommand().equals("update") && getActionId() > 0) {
				actionCode = getActionCodeDAO().getActionCodeById(getActionId());
				budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
				setBudgetType(actionCode.getBudgetType());
				setCode(actionCode.getActionCode());
				setDescription(actionCode.getDescription());
				setStatus(actionCode.getStatus());
				return INPUT;
				
			} else if (getCommand().equals("save")) {
				actionCode.setBudgetType(getBudgetType());
				actionCode.setActionCode(getCode());
				actionCode.setDescription(getDescription());
				actionCode.setStatus(getStatus());
				clearErrorsAndMessages();
				try {
					getActionCodeDAO().updateActionCode(actionCode, userSession);
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
			if (getActionId() > 0) {
				getActionCodeDAO().deleteActionCode(getActionId(),userSession);
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
			if (getCode() == null || getCode().trim().equals("")) {
				arg[0] = getText("label.actionCode");
				addFieldError("code", getText("errors.required", arg));
				error = true;
			}
		    if (getDescription()==null || getDescription().trim().equals("")) {
		    	arg[0] = getText("label.description");
		    	addFieldError("description", getText("errors.required",arg));
		    	error = true;
		    }			 
			
			if (error && getActionId() > 0)
				setCommand("update");
			if (error && getActionId() <= 0)
				setCommand("add");
		}
	}

	public void clearFields() {
		setActionId(0);	
		setBudgetType(null);
		setCode(null);
		setDescription(null);
		setStatus("A");
	}

	public String reset() {
		clearErrorsAndMessages();
		String out = null;

		if (getActionId() > 0) {
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

