package com.finance.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum BudgetTypeEnum {
	CAPITAL_EXPENSES(1,"CAPITAL_EXPENSES", "Capital Expenses","A"),
	MARKETING_EXPENSES(2,"MARKETING_EXPENSES", "Marketing Expenses", "A"),
	RETAIL_BANKING_EXPENSES(3,"RETAIL_BANKING_EXPENSES","Retail Banking Expenses","A"),
	IT_EXPENSES(4,"IT_EXPENSES","IT Expenses","A"),
	OTHER(5,"OTHER","Other","A");
	
	private int budgetTypeId;
	private String budgetType;
	private String description;
	private String status;
	
	private BudgetTypeEnum(int budgetTypeId, String budgetType, String description, String status) {
		this.budgetType = budgetType;
		this.budgetType = budgetType;
		this.description = description;
		this.status = status;
	}

	public int getBudgetTypeId() {
		return budgetTypeId;
	}

	public void setBudgetTypeId(int budgetTypeId) {
		this.budgetTypeId = budgetTypeId;
	}

	public String getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static BudgetTypeEnum getEnumByBudgetType(String budgetType) {
		Class clazz=BudgetTypeEnum.class;
		Object[] cons=clazz.getEnumConstants();
		for(Object o:cons) {
			if(((BudgetTypeEnum)o).getBudgetType().equals(budgetType))
				return (BudgetTypeEnum)o;
		}
		return null;
	}
	
	public static List<BudgetTypeEnum> getAllBudgetTypes() {
		Class clazz=BudgetTypeEnum.class;
		Object[] cons=clazz.getEnumConstants();
		List<BudgetTypeEnum> list = new ArrayList<BudgetTypeEnum>();
		for(Object o:cons) {
	         list.add((BudgetTypeEnum)o);
        }
		return list;
	}
}
