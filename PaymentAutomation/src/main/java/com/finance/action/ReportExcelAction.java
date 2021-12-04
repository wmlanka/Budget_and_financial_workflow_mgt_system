package com.finance.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.finance.dao.BudgetDAO;
import com.finance.domain.Car;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.finance.report.JXLSReportEngine;
import com.finance.report.Report;
import com.finance.report.ReportEngineInput;
import com.finance.report.ReportEngineOutput;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReportExcelAction extends BaseAction{
	
	private BudgetDAO budgetDAO;
	private UserSession userSession;

	public BudgetDAO getBudgetDAO() {
		return budgetDAO;
	}
	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}
	
	@Override
	public String execute() throws Exception {
		
		Map<String,Object> parameters = getReportParameterMap();
		
		try{
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");

			List list = getBudgetDAO().getBudgetSummaryData("MARKETING_EXPENSES", 1, 0, null);
			Report report = new Report("Budget","BudgetSummary.xls",list);
			
			ReportEngineInput input = new ReportEngineInput(report, parameters);
			
			JXLSReportEngine reportEngine = new JXLSReportEngine();
				
			ReportEngineOutput output = reportEngine.generateReport(input,userSession);

			HttpServletResponse response = ServletActionContext.getResponse();			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "inline; filename="
					+ StringUtils.deleteWhitespace("report") + ".xls");

			ServletOutputStream out = response.getOutputStream();

			out.write(output.getContent(), 0, output.getContent().length);
			
			out.flush();
			out.close();
		}
		
		catch (Exception e){
			//addActionError(e.getMessage());
			return INPUT;
		}	
		finally{
		}
		return NONE;
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> getReportParameterMap()
	{
		Map<String,Object> reportParameters = new HashMap<String,Object>();
		reportParameters.put("USER_ID", "Lanka");
		return reportParameters;
	}
}
