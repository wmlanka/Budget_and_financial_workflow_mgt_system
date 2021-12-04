package com.finance.action;

import javax.servlet.http.HttpSession;

import com.finance.dao.LocationDAO;
import com.finance.dao.LoginDAO;
import com.finance.domain.Location;
import com.finance.domain.User;
import com.finance.domain.UserSession;
import com.finance.enumeration.AccessLevelEnum;
import com.finance.util.BaseException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends BaseAction{
	private LoginDAO loginDAO;
    private User user;
    private UserSession userSession;
    
	private String userId;
	private String password;
	private String userName;
	
	private String userType;
	private int departmentId;
	private String userTypeStr;
	private String costCenter;
	private LocationDAO locationDAO;
	private String financeUser;
 
    public LoginDAO getLoginDAO() {
		return loginDAO;
	}
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getUserTypeStr() {
		return userTypeStr;
	}
	public void setUserTypeStr(String userTypeStr) {
		this.userTypeStr = userTypeStr;
	}
	
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	public LocationDAO getLocationDAO() {
		return locationDAO;
	}
	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFinanceUser() {
		return financeUser;
	}
	public void setFinanceUser(String financeUser) {
		this.financeUser = financeUser;
	}
	
	public String execute() {
        try {
        	clearErrorsAndMessages();
        	
        	if(getUserId()==null || getUserId().equals("") || getPassword()==null || getPassword().equals("")) {
        		addActionError(getText("error.enterUserIDPassword"));
        		return INPUT;
        	}
        	
        	user = new User();
        	user.setUserId(getUserId());
        	user.setPassword(getPassword());
			userSession = getLoginDAO().createLogin(user);
			
			setUserName(userSession.getUserName());
			setUserType(userSession.getUserType());
			setDepartmentId(userSession.getDepartmentId());
			setUserTypeStr(AccessLevelEnum.getEnumByCode(userSession.getUserType()).getDescription());
			setFinanceUser(600==userSession.getDepartmentId()?"Y":"N");
			
			Location location = getLocationDAO().getLocationById(userSession.getDepartmentId());
			setCostCenter(location.getLocationName()+"-"+location.getLocationId());
			
			HttpSession sessionHTTP = getHttpServletRequest().getSession(false);// check existence of session
			if (sessionHTTP != null) {
				sessionHTTP.invalidate();
			}
			sessionHTTP = getHttpServletRequest().getSession(true);
			sessionHTTP.setAttribute("LOGIN_USER", userSession);			
			clearErrorsAndMessages();
		    ((ActionSupport) ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();
		
        } catch (BaseException e) {
			userId = null;
			password = null;
			userType = null;
			departmentId = 0;
			userTypeStr = null;
			costCenter = null;
			financeUser = "N";
			addActionError(getText(e.getMessage()));//addActionError(getText("error.invalidUserPassword"));
			return INPUT;
			
		} catch(Exception e) {
			userId = null;
			password = null;
			userType = null;
			departmentId = 0;
			userTypeStr = null;
			costCenter = null;
			financeUser = "N";
			addActionError(getText(e.getMessage()));
			return INPUT;
		}
        return SUCCESS;
    }
	
	@Override
	public void validate() {
		clearErrorsAndMessages();
	}
}
