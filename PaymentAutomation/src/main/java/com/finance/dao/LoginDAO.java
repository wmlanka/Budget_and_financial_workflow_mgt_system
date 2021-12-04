package com.finance.dao;

import com.finance.domain.User;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;

public interface LoginDAO {
	UserSession createLogin(User user) throws BaseException;

}
