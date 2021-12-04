<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/struts-dojo-tags" prefix="sd" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PR Amount</title>
 	 
</head>
<body>
			
		    <%-- <s:form> --%> 
			   	<div class="form-row" style="margin-bottom: 2px;">
				    <div class="form-group col-md-4">
				      <label for="">Payable Amount<font class="myErrorMessage"> &nbsp; * </font>&nbsp;</label>
				      <input type="text" class="form-control" id="payableAmount" placeholder="" name="payableAmount" value="<s:property value="getText('{0,number,#,##0.00}',{payableAmount})"/>" 
				      style="text-align: right;">
				    </div>
				    <div class="form-group col-md-4">
				      <label for="inputPassword4">Withholding Tax</label>
				      <input type="text" class="form-control" id="withholdingTax" placeholder="" name="withholdingTax" value="<s:property value="getText('{0,number,#,##0.00}',{withholdingTax})"/>" 
				      style="text-align: right;">
				    </div>
			   </div>
			   
			   	<div class="form-row" style="margin-bottom: 2px;">
				    <div class="form-group col-md-4">
				      <label for="">VAT Amount</label>
				      <input type="text" class="form-control" id="vatAmount" placeholder="" name="vatAmount" value="<s:property value="getText('{0,number,#,##0.00}',{vatAmount})"/>" 
				      style="text-align: right;">
				    </div>
				    <div class="form-group col-md-4">
				      <label for="inputPassword4">Retained Amount</label>
				      <input type="text" class="form-control" id="retainedAmount" placeholder="" name="retainedAmount" value="<s:property value="getText('{0,number,#,##0.00}',{retainedAmount})"/>" 
				      style="text-align: right;">
				    </div>
			   </div>
			   
			    <div class="form-row" style="margin-bottom: 2px;">
				    <div class="form-group col-md-4">
				      <label for="">Payable Other</label>
				      <input type="text" class="form-control" id="payableOther" placeholder="" name="payableOther" value="<s:property value="getText('{0,number,#,##0.00}',{payableOther})"/>" 
				      style="text-align: right;">
				    </div>
				    <div class="form-group col-md-4">
				      <label for="inputPassword4">Deduction Other</label>
				      <input type="text" class="form-control" id="deductOther" placeholder="" name="deductOther" value="<s:property value="getText('{0,number,#,##0.00}',{deductOther})"/>" 
				      style="text-align: right;">
				    </div>
			   </div>
			   
			   <div class="form-row" style="margin-bottom: 2px;">
				    <div class="form-group col-md-4">
				      <label for="">Payable Total</label>
				      <input type="text" class="form-control" id="payableTotal" placeholder="" readonly="readonly" name="payableTotal" value="<s:property value="getText('{0,number,#,##0.00}',{payableTotal})"/>" 
				      style="text-align: right;">
				    </div>
				    <div class="form-group col-md-4">
				      <label for="inputPassword4">Deduction Total</label>
				      <input type="text" class="form-control" id="deductTotal" placeholder="" readonly="readonly" name="deductTotal" value="<s:property value="getText('{0,number,#,##0.00}',{deductTotal})"/>" 
				      style="text-align: right;">
				    </div>
				    <div class="form-group col-md-4">
				      <label for="inputPassword4"><b>Net Amount</b></label>
				      <input type="text" class="form-control" id="netAmount" placeholder="" readonly="readonly" name="netAmount" value="<s:property value="getText('{0,number,#,##0.00}',{netAmount})"/>" 
				      style="text-align: right;">
				    </div>
			   </div>
			    <div class="form-inline" style="margin-bottom: 0px;">
				  <div class="myErrorMessage"><s:property value="fieldErrors['payableAmount'][0]" /></div>
			   </div>
		<%-- 	   <font class="myErrorMessage"><s:property value="fieldErrors['payableAmount'][0]" /></font> --%>
			   
		   		
			<%--  </s:form> --%> 
</body>
</html>