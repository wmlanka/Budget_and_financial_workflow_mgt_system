package com.finance.daoImpl;

import java.io.InputStream;

import com.finance.dao.IExcelService;
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
//import jxl.Workbook;  
//import jxl.write.WritableSheet;  
//import jxl.write.WritableWorkbook;  

public class ExcelServiceImpl implements IExcelService {
	@Override  
    public InputStream getExcelInputStream() {  
        //Convert OutputStream to InputStream  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        putDataOnOutputStream(out);  
        return new ByteArrayInputStream(out.toByteArray());  
    }  
    private void putDataOnOutputStream(OutputStream os) {  
//    	Label
//        jxl.write.Label label;  
//        WritableWorkbook workbook;  
//        try {  
//            workbook = Workbook.createWorkbook(os);  
//            WritableSheet sheet = workbook.createSheet("Sheet1", 0);  
//            label = new jxl.write.Label(0, 0, "struts2 export excel");  
//            sheet.addCell(label);  
//            workbook.write();  
//            workbook.close();  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
    } 
}
