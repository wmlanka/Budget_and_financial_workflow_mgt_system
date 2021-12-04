package com.finance.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum ApprovalFlowEnum {
	BASIC_DEPARTMENT(AccessLevelEnum.OFFICER_B,AccessLevelEnum.OFFICER_A),
	BASIC_FINANCE(AccessLevelEnum.OFFICER_B,AccessLevelEnum.OFFICER_A);//,

	
	private AccessLevelEnum level1;
	private AccessLevelEnum level2;
	private AccessLevelEnum level3;

	ApprovalFlowEnum(AccessLevelEnum level1, AccessLevelEnum level2) {
		this.level1 = level1;
		this.level2 = level2;	
	}
	
	ApprovalFlowEnum(AccessLevelEnum level1, AccessLevelEnum level2,  AccessLevelEnum level3) {
		this.level1 = level1;
		this.level2 = level2;	
		this.level3 = level3;
	}

	public AccessLevelEnum getLevel1() {
		return level1;
	}

	public void setLevel1(AccessLevelEnum level1) {
		this.level1 = level1;
	}

	public AccessLevelEnum getLevel2() {
		return level2;
	}

	public void setLevel2(AccessLevelEnum level2) {
		this.level2 = level2;
	}

	public AccessLevelEnum getLevel3() {
		return level3;
	}

	public void setLevel3(AccessLevelEnum level3) {
		this.level3 = level3;
	}
	
	
	
//	public static SourceDocTypeEnum getEnumByType(ApprovalFlowEnum enum) {
//		Class clazz=ApprovalFlowEnum.class;
//		Object[] cons=clazz.getEnumConstants();
//		for(Object o:cons) {
//			if(((SourceDocTypeEnum)o).getSourceDocumentType().equals(sourceDocumentType))
//				return (SourceDocTypeEnum)o;
//		}
//		return null;
//	}
//	
//	public static List<SourceDocTypeEnum> getAllSourceDocTypes() {
//		Class clazz=SourceDocTypeEnum.class;
//		Object[] cons=clazz.getEnumConstants();
//		List<SourceDocTypeEnum> list = new ArrayList<SourceDocTypeEnum>();
//		for(Object o:cons) {
//	         list.add((SourceDocTypeEnum)o);
//        }
//		return list;
//	}
}
