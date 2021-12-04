package com.finance.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
	
	public static boolean validateSession(HttpServletRequest request){
		if (request.getSession(false) == null ||(request.getSession().getAttribute( "LOGIN_USER" ) == null)){
			return false;
		} else{
			return true;
		}      
	}
}

