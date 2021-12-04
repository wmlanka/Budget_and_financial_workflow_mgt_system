package com.finance.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.LedgerAccountDAO;
import com.finance.domain.LedgerAccount;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class LedgerAccountAction extends BaseAction implements Parameterizable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private LedgerAccountDAO ledgerAccountDAO;
	private List<LedgerAccount> list;
	private String command;
	private UserSession userSession;
	
	private int ledgerAccountId;
	private String accountNo;
	private String accountName;
	private String accountCategory;
	private String standardType;
	private String remark;
	private String status;
		
	public LedgerAccountDAO getLedgerAccountDAO() {
		return ledgerAccountDAO;
	}

	public void setLedgerAccountDAO(LedgerAccountDAO ledgerAccountDAO) {
		this.ledgerAccountDAO = ledgerAccountDAO;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<LedgerAccount> getList() {
		return list;
	}

	public void setList(List<LedgerAccount> list) {
		this.list = list;
	}

	public int getLedgerAccountId() {
		return ledgerAccountId;
	}

	public void setLedgerAccountId(int ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}

	public String getStandardType() {
		return standardType;
	}

	public void setStandardType(String standardType) {
		this.standardType = standardType;
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


	LedgerAccount ledgerAccount = null;
	
	@Override
	public String execute() throws Exception {
		try {
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			clearErrorsAndMessages();//clear action messages
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			ledgerAccount = new LedgerAccount();
			list = getLedgerAccountDAO().getAllLedgerAccount();
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
	}
	
	public String refresh() throws Exception {//return after create,update,delete
		try {
			list = getLedgerAccountDAO().getAllLedgerAccount();
			return SUCCESS;
		} catch (BaseException e) {
			addActionError(e.toString());
		}
		return SUCCESS;
	}
	
	public String create() {
		if (getCommand().equals("add")){
			clearFields();
			setCommand("add");
			ledgerAccount = new LedgerAccount();
			return INPUT;
		}else {
			ledgerAccount = new LedgerAccount();
			ledgerAccount.setLedgerAccountId(getLedgerAccountId());
			ledgerAccount.setAccountNo(getAccountNo());
			ledgerAccount.setAccountName(getAccountName());
			ledgerAccount.setAccountCategory(getAccountCategory());
			ledgerAccount.setStandardType(getStandardType());
			clearErrorsAndMessages();
			try {
				getLedgerAccountDAO().createLedgerAccount(ledgerAccount,userSession);
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
			if (getCommand().equals("update") && getLedgerAccountId()>0){
				ledgerAccount = getLedgerAccountDAO().getLedgerAccountById(getLedgerAccountId());
				setAccountNo(ledgerAccount.getAccountNo()); 
				setAccountName(ledgerAccount.getAccountName());
				setRemark(ledgerAccount.getRemark());
				setAccountCategory(ledgerAccount.getAccountCategory());
				setStandardType(ledgerAccount.getStandardType());
				setStatus(ledgerAccount.getStatus());
				return INPUT;
			}		
			else if(getCommand().equals("save")) {
				ledgerAccount.setAccountNo(getAccountNo());
				ledgerAccount.setAccountName(getAccountName());
				ledgerAccount.setAccountCategory(getAccountCategory());
				ledgerAccount.setStandardType(getStandardType());
				ledgerAccount.setRemark(getRemark());
				ledgerAccount.setStatus(getStatus());
				try {
					getLedgerAccountDAO().updateLedgerAccount(ledgerAccount,userSession);
					clearErrorsAndMessages();
					addActionMessage(getText("message.updateSuccess"));
					return SUCCESS;
				}catch(BaseException e) {
					setCommand("update");
					addActionMessage(getText(e.getMessage()));
					return INPUT;
				}
			}
		}catch (Exception e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
		
	}
	
	public String delete() {
		try {
			clearErrorsAndMessages();
			if (getLedgerAccountId()>0){
				getLedgerAccountDAO().deleteLedgerAccount(getLedgerAccountId(),userSession);
				//clearErrorsAndMessages();
				addActionMessage(getText("message.deleteSuccess"));
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			//addActionError(e.toString());
			return INPUT;		
		}
		return SUCCESS;
	}
	
	public void validate(){
		clearFieldErrors();
		//clearErrorsAndMessages();
		clearActionErrors();
		boolean error = false;
		
		if(getCommand()!=null && getCommand().equals("save") && !isClearScreen()) {
			String[] arg= new String[1];
			
		    if (getAccountNo() == null || getAccountNo().trim().equals("")) {
		    	arg[0] = getText("label.accountNo");
		        addFieldError("accountNo", getText("errors.required",arg));
		        error = true;
		    } 
		    if (getAccountName() == null || getAccountName().trim().equals("")) {
		    	arg[0] = getText("label.accountName");
		        addFieldError("accountName", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getAccountCategory().equals("-1")) {
		    	arg[0] = getText("label.accountCategory");
		        addFieldError("accountCategory", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getStandardType().equals("-1")) {
		    	arg[0] = getText("label.standardType");
		        addFieldError("standardType", getText("errors.required",arg));
		    	error = true;
		    }
		    
		    if(error && getLedgerAccountId()>0)
		    	setCommand("update");
		    if(error && getLedgerAccountId()<=0)
		    	setCommand("add");
		}	
	}
	
	public void clearFields() {
		setLedgerAccountId(0);
		setAccountNo(null);
		setAccountName(null);
		setRemark(null);
		setAccountCategory("-1");
		setStandardType("-1");
		setStatus("A");
	}
	
	public String reset() {
		clearErrorsAndMessages();
		String out = null;
		
		if(getLedgerAccountId()>0) {
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

}
