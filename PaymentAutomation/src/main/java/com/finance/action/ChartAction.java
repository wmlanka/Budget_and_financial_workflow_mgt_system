package com.finance.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.finance.dao.BudgetDAO;
import com.finance.dto.BudgetDTO;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;

public class ChartAction extends BaseAction{
	List<BudgetDTO> monthList;
	List<BudgetDTO> iTBudgetList;
	List<BudgetDTO> capitalBudgetList;
	List<BudgetDTO> marketingBudgetList;
	List<BudgetDTO> retailBudgetList;
	
	private BudgetDAO budgetDAO;
	
	@Override
	public String execute() throws Exception {
		return INPUT;
	}
		
	public String getMonthBudgetChart(){
		
		monthList = new ArrayList<BudgetDTO>();
		iTBudgetList = new ArrayList<BudgetDTO>();
		marketingBudgetList = new ArrayList<BudgetDTO>();
		capitalBudgetList = new ArrayList<BudgetDTO>();
		retailBudgetList = new ArrayList<BudgetDTO>();
		
		try {
			Calendar cal = Calendar.getInstance();
			int year  = cal.get(Calendar.YEAR); 
			int month = cal.get(Calendar.MONTH); // 0-January
			
			for(int i=0 ; i<=month;i++) {
				BudgetDTO mDTO = new BudgetDTO();
				
				BudgetDTO itBudgetDTO = new BudgetDTO();
				BudgetDTO marketingBudgetDTO = new BudgetDTO();
				BudgetDTO capitalBudgetDTO = new BudgetDTO();
				BudgetDTO retailBudgetDTO = new BudgetDTO();
				
				double itUtilizedAmt = 0.00;
				double marketingUtilizedAmt = 0.00;
				double capitalUtilizedAmt = 0.00;
				double retailUtilizedAmt = 0.00;
				
				if(i==0) {
					mDTO.setBudgetMonth("January");		
				}else if(i==1) {
					mDTO.setBudgetMonth("February");
				}else if(i==2) {
					mDTO.setBudgetMonth("March");
				}else if(i==3) {
					mDTO.setBudgetMonth("April");
				}else if(i==4) {
					mDTO.setBudgetMonth("May");
				}else if(i==5) {
					mDTO.setBudgetMonth("June");
				}else if(i==6) {
					mDTO.setBudgetMonth("July");
				}else if(i==7) {
					mDTO.setBudgetMonth("August");	
				}else if(i==8) {
					mDTO.setBudgetMonth("September");	
				}else if(i==9) {
					mDTO.setBudgetMonth("October");	
				}else if(i==10) {
					mDTO.setBudgetMonth("November");	
				}else if(i==11) {
					mDTO.setBudgetMonth("December");
				}
				
				itBudgetDTO.setUtilizedAmount(itUtilizedAmt);
				
				marketingUtilizedAmt = getBudgetDAO().getBudgetUtilizeOfMonth(year, i+1, BudgetTypeEnum.MARKETING_EXPENSES);
				marketingBudgetDTO.setUtilizedAmount(0.0);
				
				capitalUtilizedAmt = getBudgetDAO().getBudgetUtilizeOfMonth(year, i+1, BudgetTypeEnum.CAPITAL_EXPENSES);
				capitalBudgetDTO.setUtilizedAmount(capitalUtilizedAmt);
				
				retailBudgetDTO.setUtilizedAmount(retailUtilizedAmt);
				
				monthList.add(mDTO);
				iTBudgetList.add(itBudgetDTO);
				marketingBudgetList.add(marketingBudgetDTO);
				capitalBudgetList.add(capitalBudgetDTO);
				retailBudgetList.add(retailBudgetDTO);
			}
			return SUCCESS;
		}catch (BaseException e) {
			addActionMessage(getText("errors.cannotRetrieveData"));
			return INPUT;
		}
	}

	public List<BudgetDTO> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<BudgetDTO> monthList) {
		this.monthList = monthList;
	}

	public List<BudgetDTO> getiTBudgetList() {
		return iTBudgetList;
	}

	public void setiTBudgetList(List<BudgetDTO> iTBudgetList) {
		this.iTBudgetList = iTBudgetList;
	}

	public List<BudgetDTO> getCapitalBudgetList() {
		return capitalBudgetList;
	}

	public void setCapitalBudgetList(List<BudgetDTO> capitalBudgetList) {
		this.capitalBudgetList = capitalBudgetList;
	}

	public List<BudgetDTO> getMarketingBudgetList() {
		return marketingBudgetList;
	}

	public void setMarketingBudgetList(List<BudgetDTO> marketingBudgetList) {
		this.marketingBudgetList = marketingBudgetList;
	}

	public List<BudgetDTO> getRetailBudgetList() {
		return retailBudgetList;
	}

	public void setRetailBudgetList(List<BudgetDTO> retailBudgetList) {
		this.retailBudgetList = retailBudgetList;
	}

	@JSON (serialize = false)
	public BudgetDAO getBudgetDAO() {
		return budgetDAO;
	}

	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}
}


