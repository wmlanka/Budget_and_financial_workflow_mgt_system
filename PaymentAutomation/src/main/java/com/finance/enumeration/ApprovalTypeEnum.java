package com.finance.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum ApprovalTypeEnum {
	EOC(1,"EOC", "EOC","A"),
	BOARD_PAPER(2,"BOARD_PAPER", "Board Paper", "A"),
	SDGM_APPROVAL(3,"SDGM_APPROVAL", "SDGM Approval","A"),
	DGM_APPROVAL(4,"DGM_APPROVAL", "DGM Approval", "A"),
	HOD_APPROVAL(5,"HOD_APPROVAL", "HOD Approval", "A"),
	TOP_MANAGEMENT(6,"TOP_MANAGEMENT", "Top Management Approval","A"),
	TENDER(7,"TENDER", "Tender", "A"),
	LETTER(8,"LETTER", "Letter", "A"),
	OTHER(9,"OTHER", "Other", "A");
	
	private int approvalTypeId;
	private String approvalType;//use as reference in table
	private String description;
	private String status;
	
	private ApprovalTypeEnum(int approvalTypeId, String approvalType, String description, String status) {
		this.approvalTypeId = approvalTypeId;
		this.approvalType = approvalType;
		this.description = description;
		this.status = status;
	}
		
	public int getApprovalTypeId() {
		return approvalTypeId;
	}

	public void setApprovalTypeId(int approvalTypeId) {
		this.approvalTypeId = approvalTypeId;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
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

	public static ApprovalTypeEnum getEnumByApprovalType(String approvalType) {
		Class clazz=ApprovalTypeEnum.class;
		Object[] cons=clazz.getEnumConstants();
		for(Object o:cons) {
			if(((ApprovalTypeEnum)o).getApprovalType().equals(approvalType))
				return (ApprovalTypeEnum)o;
		}
		return null;
	}
	
	public static List<ApprovalTypeEnum> getAllApprovalTypes() {
		Class clazz=ApprovalTypeEnum.class;
		Object[] cons=clazz.getEnumConstants();
		List<ApprovalTypeEnum> list = new ArrayList<ApprovalTypeEnum>();
		for(Object o:cons) {
	         list.add((ApprovalTypeEnum)o);
        }
		return list;
	}
}
