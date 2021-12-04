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
<title>Invoices</title>
<%-- 
 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	 
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script>  --%>
 	 
</head>
<body>
			<!-- <form> -->
				<div class="form-inline"  style="margin-bottom: 10px;">
				  <div class="form-group">
				    	    <label for=""><s:text name="label.drcr"/> <font class="myErrorMessage"> &nbsp; * </font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				    		<select name="crDr" class="form-control" style="width:150px;">
								    <option value="CR" <s:if test="standardType == 'CR'">selected="selected"</s:if>>Credit</option>
								    <option value="DR"<s:if test="standardType == 'DR'">selected="selected"</s:if>>Debit</option>
							</select>
				  </div>
				  <div class="form-group">
				    	<label for=""> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="label.ledgeraccount"/><font color="blue"> &nbsp; * </font>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				    	<s:set var="account"><s:property value='ledgerAccountId'/></s:set>
	        			<select name="ledgerAccountId" id="ledgerAccountId" class="selectpicker form-control" style="width:210px;">
	 				       	<option value="0">Select Account No </option>				      
	 				        <s:iterator value="ledgerAccounts" var="ledgerAccounts">
						        	<s:if test="%{#ledgerAccounts.ledgerAccountId==#account}">
							        	<option value="<s:property value='ledgerAccountId'/>" selected="selected">
			                					<s:property value='accountStr' />
			            				</option>
						        	</s:if>
						        	<s:else>
						        		<option value="<s:property value='ledgerAccountId'/>">
			                					<s:property value='accountStr' />
			            				</option>
						        	</s:else>
						     </s:iterator>
						  </select>	
				  </div>
				  
				  <div class="form-group">
				    	    <label for="">&nbsp;&nbsp;&nbsp;
				    	    <s:text name="label.customerAccount"/> <font color="blue"> &nbsp; * </font>&nbsp;&nbsp;&nbsp;</label>
				    		<input type="text" name="customerAccountNo" value="<s:property value="customerAccountNo"/>" class="form-control" maxlength="15"
				    		id="customerAccountNo">

				  </div>
				  
				</div>
			 <div class="form-inline"  style="margin-bottom: 5px;">
				
				  <div class="form-group">
				    	<label for=""><s:text name="label.amount"/><font class="myErrorMessage"> &nbsp; * </font>&nbsp;&nbsp;</label>
				    	<input type="text" name="amount" class="form-control" id="amount"
				    	 value="<s:property value="getText('{0,number,#,##0.00}',{amount})"/>" >
				  </div>
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="submit" name="" value="Add" formaction="addPREntryAction">
				  &nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="submit" name="" value="Delete" formaction="delTempPREntryAction">
			 </div>
			 
			 <div class="form-group">
				  <div class="myErrorMessage"><s:property value="fieldErrors['ledgerAccount'][0]" /></div>
				  <div class="myErrorMessage"><s:property value="fieldErrors['amount'][0]" /></div>
			 </div>
					
			 <input type="hidden" name="command" value="save3">
			 <s:include value="PREntryGrid.jsp"/>
			 
		<!-- </form> -->
</body>
</html>