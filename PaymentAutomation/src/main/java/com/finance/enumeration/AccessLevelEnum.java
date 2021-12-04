package com.finance.enumeration;

public enum AccessLevelEnum {
	SYSTEM_ADMIN(1,"SYSTEM_ADMIN", "System Administrator"),
	SYSTEM_OPERATOR(2,"SYSTEM_OPERATOR", "System Operator"),
	OFFICER_A(3,"OFFICER_A", "Officer A"),
	OFFICER_B(4,"OFFICER_B", "Officer B"),
	SUPER_USER(5,"SUPER_USER", "Super User"),
	BUDGET_USER(6,"BUDGET_USER", "Budget Manager");
	
	private int accessGroupId;
	private String code;
	private String description;

	AccessLevelEnum(int accessGroupId, String code, String description) {
		this.accessGroupId = accessGroupId;
		this.code = code;	
		this.description = description;	
	}
	
	public static AccessLevelEnum getEnumByCode(String cod) {
		Class clazz=AccessLevelEnum.class;
		Object[] cons=clazz.getEnumConstants();
		for(Object o:cons) {
			if(((AccessLevelEnum)o).getCode().equals(cod))
				return (AccessLevelEnum)o;
		}
		return null;
	}

	public int getAccessGroupId() {
		return accessGroupId;
	}

	public void setAccessGroupId(int accessGroupId) {
		this.accessGroupId = accessGroupId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

}
