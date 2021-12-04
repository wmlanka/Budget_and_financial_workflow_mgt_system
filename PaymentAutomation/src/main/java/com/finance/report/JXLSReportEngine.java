package com.finance.report;

import java.io.ByteArrayOutputStream;
import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.finance.dao.BudgetDAO;
import com.finance.domain.Budget;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.mysql.jdbc.Connection;

import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;
import net.sf.jxls.report.ReportManagerImpl;

//POI libraries to read Excel File
import java.io.FileInputStream;
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import java.util.Iterator;

public class JXLSReportEngine {
	String reportDirecory = "D:\\eclipse_workspace\\reports\\";
	Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public ReportEngineOutput generateReport(ReportEngineInput input, UserSession userSession) throws Exception
	{
		Connection conn = null;

		try
		{
			Report report = input.getReport();
						
			// create new HashMap to send to JXLS in order to maintain original map of parameters
			Map<String,Object> jxlsReportMap = new HashMap<String,Object>(input.getParameters());
//			if (report.getQuery() != null && report.getQuery().trim().length() > 0)
//			{
//				QueryReportEngine queryEngine = new QueryReportEngine(dataSourceProvider, directoryProvider, propertiesProvider);				
//				
//				// set ExportType to null so QueryReportEngine just returns a list of results
//				input.setExportType(null);
//				
//				QueryEngineOutput output = (QueryEngineOutput) queryEngine
//						.generateReport(input);		
//				
//				jxlsReportMap.put(ORStatics.JXLS_REPORT_RESULTS, output.getResults());
//			}
//			else
//			{
				conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_system?" + "user=root&password=root");
				//dataSourceProvider.getConnection(report.getDataSource().getId());
				ReportManagerImpl rm = new ReportManagerImpl(conn, jxlsReportMap);
				jxlsReportMap.put("rm", rm);
				jxlsReportMap.put("created_date", formatter.format(new Date()));  
				jxlsReportMap.put("user", userSession.getUserId());
				jxlsReportMap.put("cost_center", userSession.getDepartmentId()+"");
				jxlsReportMap.put("data", report.getDataList());
//			}
				
			//if(report.equals("Budget")) {
				//List<BudgetDTO> list = getBudgetDAO().getAllBudgetDTO();
				//jxlsReportMap.put("budget", list);
			//}

			FileInputStream template = new FileInputStream(reportDirecory+ report.getFileName());

			XLSTransformer transformer = new XLSTransformer(new Configuration());
			//HSSFWorkbook workBook = new HSSFWorkbook();  
			HSSFWorkbook workBook = transformer.transformXLS(template, jxlsReportMap);			
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workBook.write(out);
//			
			ReportEngineOutput output = new ReportEngineOutput();
			output.setContent(out.toByteArray());
			output.setContentType(ReportEngineOutput.CONTENT_TYPE_XLS);
			
			return output;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e);
		}
		finally
		{
			try
			{
				if (conn != null) conn.close();
			}
			catch (Exception c){
				//log.error("Error closing");
			}
		}
	}	
}
