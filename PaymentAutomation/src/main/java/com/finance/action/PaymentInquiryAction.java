package com.finance.action;

import java.text.DecimalFormat;
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
import com.finance.dao.StakeholderDAO;
import com.finance.domain.Location;
import com.finance.domain.Stakeholder;
import com.finance.domain.UserSession;
import com.finance.report.JXLSReportEngine;
import com.finance.report.Report;
import com.finance.report.ReportEngineInput;
import com.finance.report.ReportEngineOutput;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;

public class PaymentInquiryAction extends BaseAction{
	//String reportDirecory = "D:\\eclipse_workspace\\reports\\";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserSession userSession;
	private String command;
	private PaymentRequestDAO paymentRequestDAO;
	private LocationDAO locationDAO;
	private StakeholderDAO stakeholderDAO;
	private Integer prNo =null;
	private int departmentId;
	private int stakeholderId;
	private Date fromDate;
	private Date toDate;
	private String status;
	private double amount;
	private String year;
	private List<Location> locationList =  new ArrayList<Location>();
	private List<Stakeholder> stakeholderList =  new ArrayList<Stakeholder>();
	private String financeUser;
	
	public UserSession getUserSession() {
		return userSession;
	}
	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}

	public PaymentRequestDAO getPaymentRequestDAO() {
		return paymentRequestDAO;
	}
	public void setPaymentRequestDAO(PaymentRequestDAO paymentRequestDAO) {
		this.paymentRequestDAO = paymentRequestDAO;
	}
	
	public Integer getPrNo() {
		return prNo;
	}
	public void setPrNo(Integer prNo) {
		this.prNo = prNo;
	}
	
	public LocationDAO getLocationDAO() {
		return locationDAO;
	}
	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	
	public int getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	public StakeholderDAO getStakeholderDAO() {
		return stakeholderDAO;
	}
	public void setStakeholderDAO(StakeholderDAO stakeholderDAO) {
		this.stakeholderDAO = stakeholderDAO;
	}
	
	public List<Stakeholder> getStakeholderList() {
		return stakeholderList;
	}
	public void setStakeholderList(List<Stakeholder> stakeholderList) {
		this.stakeholderList = stakeholderList;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			setCommand(null);
			clearFields();
			clearErrorsAndMessages();
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			stakeholderList = getStakeholderDAO().getAllActiveStakeholder();
			//locationList = getLocationDAO().getAllLocation("A");
			locationList = getLocationDAO().getAllLocationByUser("A", userSession);
			setFinanceUser(600==userSession.getDepartmentId()?"Y":"N");

//			if(locationList!=null && locationList.size()==1) {
//				setDepartmentId(locationList.get(0).getLocationId());
//			}
			return INPUT;
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}
	
	public String generateReport() throws Exception {
		Map<String,Object> parameters = getReportParameterMap();
		
		try{
			List list = getPaymentRequestDAO().getPaymentSummaryData(getPrNo()!=null?getPrNo().intValue():0, getYear(), getDepartmentId(), getStatus(), getStakeholderId(), getAmount(), getFromDate(), getToDate(), 0, userSession);
			if(list.size()<=0) {
				addActionMessage(getText("message.searchResultNotfound"));
				return INPUT;
			}
			Report report = new Report("Payment","PaymentSummary.xls",list);
			
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
		setStakeholderId(-1);
		setFromDate(new Date());
		setToDate(new Date());
		setPrNo(null);
		setDepartmentId(-1);
		setStatus("-1");
		setYear(null);
		setAmount(0.00);
	}
	
	public void validate(){
		clearFieldErrors();
		clearActionErrors();
		int criteria = 0;
		boolean error = false;
		
		if (getCommand() != null && getCommand().equals("search")) {			
			
			if(getYear()!=null && !getYear().trim().equals("") && getYear().length()!=4) {
		    	addFieldError("year", getText("errors.invalidYear"));
		        error = true;
			}
			if(!error) {
				if (getStakeholderId()>1)
					 criteria++;
				if(getPrNo()!=null && getPrNo()>0)
					 criteria++;
				if(fromDate!=null)
					criteria++;
				if(toDate!=null)
					criteria++;
				if(getDepartmentId()>1)
					criteria++;
				if(getStatus()!=null && !getStatus().equals("-1"))
					criteria++;
				if(getAmount()>0)
					criteria++;
				if(getYear()!=null && !getYear().equals(""))
					criteria++;
				
				if(criteria<1) { 
					addActionMessage(getText("error.searchCriteraRequired"));
					addActionError("");
				}
			}
	
		}
		
	}
	
	protected Map<String,Object> getReportParameterMap(){
		DecimalFormat decimalFormat = new DecimalFormat("000000");
		String stakeholder = null;
		String costCenter = getDepartmentId()>0?getDepartmentId()+"":"";
		String status = getStatus().equals("-1")?"":getStatus();
		String year = (getYear()==null || getYear().equals(""))?"":getYear();
		String prNo = "All";
		
		try {
			if(getStakeholderId()<=0) 
				stakeholder = "All";
			else 
				stakeholder = getStakeholderDAO().getStakeholderById(getStakeholderId()).getFullName();
			
			if(getYear()!=null && !getYear().equals("") && getPrNo()!=null && departmentId>0) {
				prNo = getYear() + getDepartmentId() + decimalFormat.format(getPrNo());
			}
		
		}catch (BaseException e) {
		}
		
		Map<String,Object> reportParameters = new HashMap<String,Object>();
		reportParameters.put("user_id", userSession.getUserId());
		reportParameters.put("stakeholder", stakeholder);
		reportParameters.put("costCenter", costCenter);
		reportParameters.put("status", status);
		reportParameters.put("year", year);
		reportParameters.put("prNo", prNo);
		reportParameters.put("minimumPayment", getAmount());
		reportParameters.put("fromDate", getFromDate());
		reportParameters.put("toDate", getToDate());
		return reportParameters;
	}
	/*@Override
	public String execute() throws Exception {
		try {
		  List cars = generateTestCarData();
	      File initialFile = new File(reportDirecory+"input_excel_template.xls");
	      try (InputStream is = new FileInputStream(initialFile)) {
	            try (OutputStream os = new FileOutputStream("output_excel_template.xls")) {
	                Context context = new Context();
	                context.putVar("cars", cars);
	                JxlsHelper.getInstance().processTemplate(is, os, context);
	            }
	        }
		
		}catch(Exception e) {
			System.out.println(e);
		}
		    
		return INPUT;
	}
	
	public static List<Car> generateTestCarData() {
        return Arrays.asList(new Car("BMW", new BigDecimal("10000")), new Car("Subaru", new BigDecimal("12000")));
    }*/
}
