package com.finance.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.finance.dao.ActionCodeDAO;
import com.finance.dao.BudgetCodeDAO;
import com.finance.dao.BudgetDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.BudgetCode;
import com.finance.domain.UserSession;
import com.finance.enumeration.ApprovalTypeEnum;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.report.JXLSReportEngine;
import com.finance.report.Report;
import com.finance.report.ReportEngineInput;
import com.finance.report.ReportEngineOutput;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;

public class BudgetInquiryAction extends BaseAction{
	private UserSession userSession;
	private List<BudgetTypeEnum> budgetTypeList = new ArrayList();
	private List<ActionCode> actionCodeList = new ArrayList();
	private List<BudgetCode> budgetCodeList = new ArrayList();
	private BudgetDAO budgetDAO;
	private ActionCodeDAO actionCodeDAO;
	private BudgetCodeDAO budgetCodeDAO;
	
	private String budgetType;
	private int actionId;
	private int budgetCodeId;
	private Map<String, String> actionCodeMap = new LinkedHashMap<String, String>();
	private String year;
	private String command;
//	private String dummyMsg;
	
	public BudgetDAO getBudgetDAO() {
		return budgetDAO;
	}
	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}

	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	
	public List<BudgetTypeEnum> getBudgetTypeList() {
		return budgetTypeList;
	}
	public void setBudgetTypeList(List<BudgetTypeEnum> budgetTypeList) {
		this.budgetTypeList = budgetTypeList;
	}
	public List<ActionCode> getActionCodeList() {
		return actionCodeList;
	}
	public void setActionCodeList(List<ActionCode> actionCodeList) {
		this.actionCodeList = actionCodeList;
	}
	
	public ActionCodeDAO getActionCodeDAO() {
		return actionCodeDAO;
	}
	public void setActionCodeDAO(ActionCodeDAO actionCodeDAO) {
		this.actionCodeDAO = actionCodeDAO;
	}
	
	public Map<String, String> getActionCodeMap() {
		return actionCodeMap;
	}
	public void setActionCodeMap(Map<String, String> actionCodeMap) {
		this.actionCodeMap = actionCodeMap;
	}

	
	public BudgetCodeDAO getBudgetCodeDAO() {
		return budgetCodeDAO;
	}
	public void setBudgetCodeDAO(BudgetCodeDAO budgetCodeDAO) {
		this.budgetCodeDAO = budgetCodeDAO;
	}
	
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	public int getBudgetCodeId() {
		return budgetCodeId;
	}
	public void setBudgetCodeId(int budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public List<BudgetCode> getBudgetCodeList() {
		return budgetCodeList;
	}
	public void setBudgetCodeList(List<BudgetCode> budgetCodeList) {
		this.budgetCodeList = budgetCodeList;
	}
	
	@Override
	public String execute() throws Exception {
		try {
			setCommand(null);
			clearFields();
			clearErrorsAndMessages();
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
			return INPUT;
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}
	
	public String generateReport() throws Exception {
		Map<String,Object> parameters = getReportParameterMap();
		
		try{
			List list = getBudgetDAO().getBudgetSummaryData(getBudgetType(), getActionId(), getBudgetCodeId(), year);
			if(list.size()<=0) {
				addActionMessage(getText("message.searchResultNotfound"));
				return INPUT;
			}
			Report report = new Report("Budget","BudgetSummary.xls",list);
			
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
		return NONE;
	}
	
	public void clearFields() {
		setBudgetType("0");
		setActionId(0);
		setBudgetCodeId(0);
		setYear(null);
	}
	
	public void validate(){
		clearFieldErrors();
		clearActionErrors();
		boolean error = false;
		int cyear = Calendar.getInstance().get(Calendar.YEAR);
		//int fieldCount = 0;
		
		if(getCommand()!=null && getCommand().equals("save")) {
			String[] arg= new String[1];
			
		    if (getBudgetType() == null || getBudgetType().trim().equals("0")) {
		    	arg[0] = getText("label.budgetType");
		        addFieldError("budgetType", getText("errors.required",arg));
		        error = true;
		        
		    }
		    
		    try {
		    	 if (getYear()!=null && !getYear().equals("")) {
		    		int num =  Integer.parseInt(getYear()); 
				    if(getYear().length()!=4 || num>cyear) {
				        error = true;
				    }
				    	
				 }
		    } catch (NumberFormatException nfe) {
		        error = true;
		    }
		    
		    /*else {
		    	fieldCount++;
		    }
		    if (getActionId()>0) {
		    	fieldCount++;
		    }
		    if (getBudgetCodeId()>01) {
		    	fieldCount++;
		    }
		    if (Integer.parseInt(getYear())>0) {
		    	fieldCount++;
		    }
		    if(fieldCount<1) {
		    	addFieldError("year", getText("errors.twoFieldRequired"));
		    	error = true;
		    }*/
		    	try {
					loadDropDownData();
				} catch (BaseException e) {
					e.printStackTrace();
				}
		    }
	}
	
	protected Map<String,Object> getReportParameterMap(){
		String budgetType = (getBudgetType()==null || getBudgetType().equals(""))?"All":getBudgetType();
		String actionCode = null;
		String budgetCode = null;
		String year = (getYear()==null || getYear().equals(""))?"All":getYear();
		
		try {
			if(getActionId()==0) 
				actionCode = "All";
			else 
				actionCode = (getActionCodeDAO().getActionCodeById(getActionId())).getDescription();
		
			if(getBudgetCodeId()==0)
				budgetCode = "All";
			else
				budgetCode = getBudgetCodeDAO().getBudgetCodeById(getBudgetCodeId()).getDescription();//(getBudgetDAO().getBudgetById(getBudgetCodeId())).getDescription();
		
		}catch (BaseException e) {
		}catch (Exception e) {
		}
		
		Map<String,Object> reportParameters = new HashMap<String,Object>();
		reportParameters.put("user_id", userSession.getUserId());
		reportParameters.put("budget_type", BudgetTypeEnum.getEnumByBudgetType(budgetType).getDescription());
		reportParameters.put("action_code", actionCode);
		reportParameters.put("budget_code", budgetCode);
		reportParameters.put("year", year);
		return reportParameters;
	}
	
	public void loadDropDownData() throws BaseException {
		budgetTypeList = BudgetTypeEnum.getAllBudgetTypes();
		if(budgetType!=null)
			actionCodeList = getActionCodeDAO().getAllActionCodeByBudgetType(budgetType);
		if(actionId != 0)
			budgetCodeList = getBudgetCodeDAO().getAllBudgetCodeByActionType(actionId);
	}

}
