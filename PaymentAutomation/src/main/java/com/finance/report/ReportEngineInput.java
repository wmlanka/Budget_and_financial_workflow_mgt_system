package com.finance.report;

import java.util.Locale;
import java.util.Map;

public class ReportEngineInput
{		
	private Report report;
	private Map<String,Object> parameters;
	private String exportType;	
	private Locale locale;
    
    private String xmlInput;  

	
	public ReportEngineInput(Report report, Map<String,Object> parameters)
	{
		this.report = report;
		this.parameters = parameters;
	}

	public Map<String,Object> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String,Object> parameters)
	{
		this.parameters = parameters;
	}

	public Report getReport()
	{
		return report;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}	

	public String getExportType()
	{
		return exportType;
	}

	public void setExportType(String exportType)
	{
		this.exportType = exportType;
	}
    
    public String getXmlInput() 
    {
        return xmlInput;
    }
    
    public void setXmlInput(String xmlInput) 
    {
        this.xmlInput = xmlInput;
    }

	public Locale getLocale() 
	{
		return locale;
	}

	public void setLocale(Locale locale) 
	{
		this.locale = locale;
	}	
}