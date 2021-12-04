package com.finance.report;

import java.util.List;

public class Report {
	private String reportName;
	private List<Object> dataList;
	private String fileName;

	public Report(String reportName, String fileName, List<Object> dataList) {
		super();
		this.reportName = reportName;
		this.fileName = fileName;
		this.dataList = dataList;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public List<Object> getDataList() {
		return dataList;
	}
	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
