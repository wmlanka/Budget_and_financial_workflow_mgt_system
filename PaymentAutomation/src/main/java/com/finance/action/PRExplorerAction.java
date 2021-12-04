package com.finance.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.LocationDAO;
import com.finance.dao.PaymentRequestDAO;
import com.finance.dao.StakeholderDAO;
import com.finance.domain.Location;
import com.finance.domain.Stakeholder;
import com.finance.domain.UserSession;
import com.finance.dto.PaymentRequestDTO;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class PRExplorerAction extends BaseAction implements Parameterizable{
	private static final long serialVersionUID = 1L;
	
	private UserSession userSession;
	private String command;
	private int stakeholderId1;
	private String status;
	private Integer prNo =null;
	private Date fromDate;
	private Date toDate;
	private StakeholderDAO stakeholderDAO;
	private PaymentRequestDAO paymentRequestDAO;
	private LocationDAO locationDAO;
	private int departmentId1;
	private String userType;
	
	private List<Stakeholder> stakeholderList =  new ArrayList<Stakeholder>();
	private List<PaymentRequestDTO> prDTOList =  new ArrayList<PaymentRequestDTO>();
	private List<Location> locationList =  new ArrayList<Location>();

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public StakeholderDAO getStakeholderDAO() {
		return stakeholderDAO;
	}
	public void setStakeholderDAO(StakeholderDAO stakeholderDAO) {
		this.stakeholderDAO = stakeholderDAO;
	}

	public PaymentRequestDAO getPaymentRequestDAO() {
		return paymentRequestDAO;
	}
	public void setPaymentRequestDAO(PaymentRequestDAO paymentRequestDAO) {
		this.paymentRequestDAO = paymentRequestDAO;
	}

	public List<Stakeholder> getStakeholderList() {
		return stakeholderList;
	}
	public void setStakeholderList(List<Stakeholder> stakeholderList) {
		this.stakeholderList = stakeholderList;
	}
	
	public int getStakeholderId1() {
		return stakeholderId1;
	}
	public void setStakeholderId1(int stakeholderId1) {
		this.stakeholderId1 = stakeholderId1;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPrNo() {
		return prNo;
	}
	public void setPrNo(Integer prNo) {
		this.prNo = prNo;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public List<PaymentRequestDTO> getPrDTOList() {
		return prDTOList;
	}
	public void setPrDTOList(List<PaymentRequestDTO> prDTOList) {
		this.prDTOList = prDTOList;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public LocationDAO getLocationDAO() {
		return locationDAO;
	}
	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	
	public int getDepartmentId1() {
		return departmentId1;
	}
	public void setDepartmentId1(int departmentId1) {
		this.departmentId1 = departmentId1;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Override
	public String execute(){
		try {
			stakeholderList = getStakeholderDAO().getAllActiveStakeholder();
			//locationList = getLocationDAO().getAllLocation("A");
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			locationList = getLocationDAO().getAllLocationByUser("A", userSession);
			setCommand(null);
			clearErrorsAndMessages();
			clearFields();
			return INPUT;
		} catch (Exception e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
	}
	public void clearFields() {
		prDTOList = new ArrayList<PaymentRequestDTO>();
		setStakeholderId1(-1);
//		setFromDate(null);
//		setToDate(null);
		setFromDate(new Date());
		setToDate(new Date());
		setPrNo(null);
		setDepartmentId1(-1);
		setUserType("-1");
		setStatus("-1");
		
	}
	
	public String search() {
		try {
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");//need back from payment request
			stakeholderList = getStakeholderDAO().getAllActiveStakeholder();
			prDTOList = getPaymentRequestDAO().searchPR(getStakeholderId1(), getPrNo(), getFromDate(), getToDate(), getStatus(), getUserType(), getDepartmentId1(), userSession);
			if(prDTOList.size()>0) {
				return SUCCESS;
			}else {
				addActionMessage(getText("message.searchResultNotfound"));
			}
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			e.printStackTrace();
		}
		return INPUT;
	}
	
	public String goBack() {
		return SUCCESS;
	}
	
	public String loadGrid() {
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		clearFieldErrors();
		clearActionErrors();
		int criteria = 0;

		if (getCommand() != null && getCommand().equals("search") && !isClearScreen()) {			
			String[] arg = new String[1];

			if (getStakeholderId1()>1) {
				 criteria++;
				 
			}else if(getPrNo()!=null && getPrNo()>0) {
				 criteria++;
				 
			}else if(fromDate!=null) {
				criteria++;
				
			}else if(toDate!=null) {
				criteria++;
				
			}else if(userType!=null && !userType.equals("-1")) {
				criteria++;
				
			}else if(getDepartmentId1()>1) {
				criteria++;
				
			}else if(getStatus()!=null && !getStatus().equals("-1")) {
				criteria++;
				
			}
			if(criteria<1) { 
				addActionMessage(getText("error.searchCriteraRequired"));
				addActionError("");
			}
		}
		
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
