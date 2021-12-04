package com.finance.action;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.finance.dao.LocationDAO;
import com.finance.dao.PaymentRequestDAO;
import com.finance.domain.Location;
import com.finance.domain.UserSession;
import com.finance.report.JXLSReportEngine;
import com.finance.report.Report;
import com.finance.report.ReportEngineInput;
import com.finance.report.ReportEngineOutput;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;

public class DailyReportAction extends BaseAction{
	
	private String command;
	private UserSession userSession;
	private int reportNo;
	private int departmentId;
	private Date reportDate;
	private LocationDAO locationDAO;
	private PaymentRequestDAO paymentRequestDAO;
	private List<Location> locationList =  new ArrayList<Location>();
	private String financeUser;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}

	public UserSession getUserSession() {
		return userSession;
	}
	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
	
	public int getReportNo() {
		return reportNo;
	}
	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
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
	
	public PaymentRequestDAO getPaymentRequestDAO() {
		return paymentRequestDAO;
	}
	public void setPaymentRequestDAO(PaymentRequestDAO paymentRequestDAO) {
		this.paymentRequestDAO = paymentRequestDAO;
	}

	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	public String getFinanceUser() {
		return financeUser;
	}
	public void setFinanceUser(String financeUser) {
		this.financeUser = financeUser;
	}
	
	@Override
	public String execute() throws Exception {
		try {
			setCommand(null);
			clearFields();
			clearErrorsAndMessages();
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			setFinanceUser(600==userSession.getDepartmentId()?"Y":"N");
			locationList = getLocationDAO().getAllLocationByUser("A", userSession);
//
//			if(locationList!=null && locationList.size()==1) {
//				setDepartmentId(locationList.get(0).getLocationId());
//			}
			return INPUT;
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}
	
	public void clearFields() {
		setDepartmentId(-1);
		setReportNo(-1);
		setReportDate(new Date());;
	}
	
	public String generateReport() throws Exception {
		Map<String,Object> parameters = getReportParameterMap();
		
		try{
			List list = null;
			if(getReportNo()==1) {
				list = getPaymentRequestDAO().getDailyPRReport(getDepartmentId(), getReportDate());
			}else if(getReportNo()==2) {
				list = getPaymentRequestDAO().getDailyGLEntries(getDepartmentId(),getReportDate());
			}
			
			if(list==null || list.size()<=0) {
				addActionMessage(getText("message.searchResultNotfound"));
				return INPUT;
			}
			Report report = null;
			if(getReportNo()==2) {
				report = new Report("DailyEntries","DailyEntries.xls",list);
			}else {
				report = new Report("DailyPR","DailyPRReport.xls",list);
			}
			ReportEngineInput input = new ReportEngineInput(report, parameters);
			JXLSReportEngine reportEngine = new JXLSReportEngine();
				
			ReportEngineOutput output = reportEngine.generateReport(input,userSession);

			HttpServletResponse response = ServletActionContext.getResponse();			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "inline; filename="+ StringUtils.deleteWhitespace(report.getReportName()) + ".xls");
			ServletOutputStream out = response.getOutputStream();

			out.write(output.getContent(), 0, output.getContent().length);
			out.flush();
			out.close();
		}
		
		catch (Exception e){
			return INPUT;
		}	
		finally{}
		return INPUT;
	}
	
	public void validate(){
		clearFieldErrors();
		clearActionErrors();
		
		if(getCommand()!=null && getCommand().equals("search")) {
			String[] arg= new String[1];
			
		    if (getReportNo()<=0) {
		    	arg[0] = getText("label.reportName");
		        addFieldError("reportName", getText("errors.required",arg));
		        
		    }
		    if (getReportDate()==null || getReportDate().equals("")) {
		    	arg[0] = getText("label.reportDate");
		        addFieldError("reportDate", getText("errors.required",arg));
		    }
	
		}
	}
	
	protected Map<String,Object> getReportParameterMap(){ 
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String location = "";
		if(getDepartmentId()>0)
			try {
				location = getLocationDAO().getLocationById(getDepartmentId()).getLocationName();
			} catch (BaseException e) {
				e.printStackTrace();
			}
		
		Map<String,Object> reportParameters = new HashMap<String,Object>();
		reportParameters.put("user_id", userSession.getUserId());
		reportParameters.put("report_date", formatter.format(getReportDate()));
		reportParameters.put("department_id", getDepartmentId()<=0?"All":getDepartmentId());
		reportParameters.put("department", getDepartmentId()+"");
		return reportParameters;
	}
}
