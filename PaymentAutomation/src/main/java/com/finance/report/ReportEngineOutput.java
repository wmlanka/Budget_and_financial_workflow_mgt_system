package com.finance.report;

import java.io.Serializable;

public class ReportEngineOutput implements Serializable
{		
	private static final long serialVersionUID = -1891016289500829002L;
	
	public static final String CONTENT_TYPE_PDF = "application/pdf";
	public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";	
	
	private String contentType;
	private String contentMessage;	
	private byte[] content;			
	
	public byte[] getContent()
	{
		return content;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	
	public String getContentMessage()
	{
		return contentMessage;
	}

	public void setContentMessage(String contentMessage)
	{
		this.contentMessage = contentMessage;
	}

	public String getContentExtension()
	{	
		if (contentType == null) return "";
		
		if (contentType.equals(CONTENT_TYPE_PDF))
		{
			return ".pdf";
		}
		else if (contentType.equals(CONTENT_TYPE_XLS))
		{
			return ".xls";
		}
		return "";
	}
}