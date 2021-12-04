package com.finance.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum SourceDocTypeEnum {
	INVOICE(1,"INVOICE", "Invoice","A"),
	PURCHASE_ORDER(2,"PURCHASE_ORDER", "Purchase Order", "A"),
	RECEIPT(3,"RECEIPT", "Receipt", "A"),
	GRN(4,"GRN", "GRN", "A");
	
	private int sourceDocTypeId;
	private String sourceDocumentType;//in table
	private String description;
	private String status;
	
	private SourceDocTypeEnum(int sourceDocTypeId, String sourceDocumentType, String description, String status) {
		this.sourceDocTypeId = sourceDocTypeId;
		this.sourceDocumentType = sourceDocumentType;
		this.description = description;
		this.status = status;
	}

	public int getSourceDocTypeId() {
		return sourceDocTypeId;
	}

	public void setSourceDocTypeId(int sourceDocTypeId) {
		this.sourceDocTypeId = sourceDocTypeId;
	}

	public String getSourceDocumentType() {
		return sourceDocumentType;
	}

	public void setSourceDocumentType(String sourceDocumentType) {
		this.sourceDocumentType = sourceDocumentType;
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
	
	public static SourceDocTypeEnum getEnumBySourceDocType(String sourceDocumentType) {
		Class clazz=SourceDocTypeEnum.class;
		Object[] cons=clazz.getEnumConstants();
		for(Object o:cons) {
			if(((SourceDocTypeEnum)o).getSourceDocumentType().equals(sourceDocumentType))
				return (SourceDocTypeEnum)o;
		}
		return null;
	}
	
	public static List<SourceDocTypeEnum> getAllSourceDocTypes() {
		Class clazz=SourceDocTypeEnum.class;
		Object[] cons=clazz.getEnumConstants();
		List<SourceDocTypeEnum> list = new ArrayList<SourceDocTypeEnum>();
		for(Object o:cons) {
	         list.add((SourceDocTypeEnum)o);
        }
		return list;
	}
}
