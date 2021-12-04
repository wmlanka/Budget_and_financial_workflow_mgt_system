package com.finance.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.StakeholderDAO;
import com.finance.domain.Stakeholder;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class StakeholderAction extends BaseAction implements Parameterizable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StakeholderDAO stakeholderDAO;
	private List<Stakeholder> list;
	private String command;//add,update,save
	private UserSession userSession;
	
	private int stakeholderId;
	private String fullName;
	private String address;
	private String email;
	private String contactNo;
	private String status;
	private String remark;
	 
	public StakeholderDAO getStakeholderDAO() {
		return stakeholderDAO;
	}
	public void setStakeholderDAO(StakeholderDAO stakeholderDAO) {
		this.stakeholderDAO = stakeholderDAO;
	}
	
	public List<Stakeholder> getList() {
		return list;
	}
	public void setList(List<Stakeholder> list) {
		this.list = list;
	}
		
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	

	public UserSession getUserSession() {
		return userSession;
	}
	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	Stakeholder stakeholder = null;

	@Override
	public String execute() throws Exception {
		try {
			clearErrorsAndMessages();//clear action messages
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			stakeholder = new Stakeholder();
			list = getAllStakeholders();
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String refresh() throws Exception {//return after create,update,delete
		try {
			list = getAllStakeholders();
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String create() {
		if (getCommand().equals("add")){
			clearFields();
			stakeholder = new Stakeholder();
			return INPUT;
		}else {
			stakeholder = new Stakeholder();
			stakeholder.setFullName(getFullName());
			stakeholder.setAddress(getAddress());
			stakeholder.setContactNo(getContactNo());
			stakeholder.setRemark(getRemark());
			stakeholder.setEmail(getEmail());		
			clearErrorsAndMessages();
			
			try {
				getStakeholderDAO().createStakeholder(stakeholder, userSession);
			} catch (BaseException e) {
				setCommand("add");
				addActionMessage(e.getMessage());
				return INPUT;
			}
		}
		addActionMessage(getText("message.addSuccess"));
		return SUCCESS;
	}
	
	public String update() {
		try {
			if(getCommand().equals("update") && getStakeholderId()>0){
				stakeholder = getStakeholderDAO().getStakeholderById(getStakeholderId());
				setFullName(stakeholder.getFullName()); 
				setAddress(stakeholder.getAddress());
				setRemark(stakeholder.getRemark());
				setContactNo(stakeholder.getContactNo());
				setEmail(stakeholder.getEmail());
				setStatus(stakeholder.getStatus());
				return INPUT;
			}		
			else if(getCommand().equals("save")) {
				stakeholder.setFullName(getFullName());
				stakeholder.setAddress(getAddress());
				stakeholder.setContactNo(getContactNo());
				stakeholder.setRemark(getRemark());
				stakeholder.setEmail(getEmail());	
				stakeholder.setStatus(getStatus());
				getStakeholderDAO().updateStakeholder(stakeholder, userSession);
				clearErrorsAndMessages();
				addActionMessage(getText("message.updateSuccess"));
			}
		}catch (BaseException e) {
			setCommand("update");
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String delete() {
		try {
			clearErrorsAndMessages();
			if (getStakeholderId()>0){
				getStakeholderDAO().deleteStakeholder(getStakeholderId(), userSession);
				//clearErrorsAndMessages();
				addActionMessage(getText("message.deleteSuccess"));
			}
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;		
		}
		return SUCCESS;
	}
	
	public void clearFields() {
		setStakeholderId(0);
		setFullName(null);
		setAddress(null);
		setEmail(null);
		setContactNo(null);
		setRemark(null);
		setStatus("A");
	}
	
	public List<Stakeholder> getAllStakeholders() throws BaseException {
		return stakeholderDAO.getAllStakeholder();
	}
	
	public void validate(){
		clearFieldErrors();
		clearActionErrors();
		//clearErrorsAndMessages(); //success messages also cleared
		boolean error = false;
		
		if(getCommand()!=null && getCommand().equals("save") && !isClearScreen()) {
			String[] arg= new String[1];
			
		    if (getFullName() == null || getFullName().trim().equals("")) {
		    	arg[0] = getText("label.fullName");
		        addFieldError("fullName", getText("errors.required",arg));
		    }
		    if (getAddress() == null || getAddress().trim().equals("")) {
		    	arg[0] = getText("label.address");
		    	addFieldError("address", getText("errors.required",arg));
		    	error = true;
		    }
		    if (getContactNo() == null || getContactNo().trim().equals("")) {
		    	arg[0] = getText("label.contactNo");
		    	addFieldError("contactNo", getText("errors.required",arg));
		    	error = true;
		    }
		    if(error && getStakeholderId()>0)
		    	setCommand("update");
		    if(error && getStakeholderId()<=0)
		    	setCommand("add");
		}	
	}
	
	public String reset() {
		clearErrorsAndMessages();
		String out = null;
		
		if(getStakeholderId()>0) {
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
