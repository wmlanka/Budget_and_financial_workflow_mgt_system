package com.finance.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,ServletRequestAware,ServletResponseAware{
	private HttpServletRequest httpServletRequest = null;
	private HttpServletResponse httpServletResponse = null;
	private Map<String, Object> sessionAttributes = null;
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		  this.httpServletResponse = response;
	}
	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		 this.httpServletRequest = request;
	}
	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}


	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionAttributes = session;		
	}
	public Map<String, Object> getSessionAttributes() {
		return sessionAttributes;
	}

}
