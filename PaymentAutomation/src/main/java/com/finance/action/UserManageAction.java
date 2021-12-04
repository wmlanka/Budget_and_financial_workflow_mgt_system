package com.finance.action;

import java.util.List;

import com.finance.dao.UserDAO;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.User;
import com.finance.domain.UserSession;
import com.finance.dto.UserDTO;
import com.finance.enumeration.ApprovalTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;

public class UserManageAction extends BaseAction{
	private UserSession userSession;
	private UserDAO userDAO;
	private List<UserDTO> list;
	private User user;
	private String command;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public List<UserDTO> getList() {
		return list;
	}
	public void setList(List<UserDTO> list) {
		this.list = list;
	}
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public String execute() throws Exception {
		try {
			clearErrorsAndMessages();
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			user = new User();
			list = getUserDAO().getAllUserDTO();
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
	}
	
	public void validate() {
		clearFieldErrors();
		clearActionErrors();//remove commented pl check
		boolean error = false;

		if (getCommand() != null && getCommand().equals("save")) {			
			String[] arg = new String[1];

		}
	}
	
	
}
