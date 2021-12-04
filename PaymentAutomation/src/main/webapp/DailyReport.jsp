<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@page import="java.text.DecimalFormat" %>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="/struts-dojo-tags" prefix="sd" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Report Budget</title>

	  <sd:head />
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	 
 	 <script type="text/javascript">
	 	$(document).ready(function() { 
			 $("#save").click(function(event) {
					$("#reportErr").empty();
					$("#dateErr").empty();
					$("#msgDiv").empty();

		 	});
		<%-- 	 
			 $("#reportNo").click(function(event) {
					<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
					<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>

		 	});
			 
			 $("#departmentId").change(function(event) {
					<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
					<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>

		 	});
			 
			 $("#reportDate").change(function(event) {
					<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
					<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
		 	}); --%>
			 

	 	});
 	 </script>
 	 
</head>
<body>

   <s:set var="uType"><s:property value='userSession.userType'/></s:set>
   <s:set var="fUser"><s:property value='financeUser'/></s:set>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-report">
					 <h5>
						  Daily Reports
				   	</h5>
			   </td>
			</tr>
		</table>
	</div>	
	
	<div  class="cardContainer">
	
	<form>
	<div class="card">
		  <div class="card-body">
	
			  <div class="form-group row">
			  		<label for="a" class="col-sm-3 col-form-label">Report Name</label>
			  		<div class="col-sm-4">
			  		 		<select name="reportNo" class="form-control" id="reportNo">
			  					    <option value="-1">Select Report Name</option>
								    <option value="1" <s:if test="reportNo== 1">selected="selected"</s:if>>Payment Requests</option>
<!-- 								    <option value="DR">Invoice/Source Documents Summary</option>
 -->							    <option value="2" <s:if test="reportNo== 2">selected="selected"</s:if>>Ledger Entries</option>
							</select>
				    </div>
				    <div id="reportErr">
				    	<font class="myErrorMessage"><s:property value="fieldErrors['reportName'][0]"/></font>
				    </div>
			  </div>
			  
			  <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label">Cost Center</label>
				    <div class="col-sm-4">
						<s:set var="depId"><s:property value='departmentId'/></s:set>
						
						<s:if test='%{#fUser=="Y" || #uType=="SYSTEM_ADMIN"}'>
							 <select name="departmentId"  class="form-control" id="departmentId">
			   					<option value="-1">Select Department</option>
			   					
			   					<s:iterator value="locationList" var="locationList">
						        	<s:if test="%{#locationList.locationId==#depId}">
							        	<option value="<s:property value='departmentId'/>" selected="selected">
			                					<s:property value='locationName' />
			            				</option>
						        	</s:if>
						        	<s:else>
						        		<option value="<s:property value='locationId'/>" >
			                					<s:property value='locationName' />
			            				</option>
						        	</s:else>
						      	</s:iterator>
						      </select>
		   				</s:if>
		   				<s:else>
		   					 <select name="departmentId"  class="form-control" id="departmentId">
			   					<s:iterator value="locationList" var="locationList">
						        	<s:if test="%{#locationList.locationId==#depId}">
							        	<option value="<s:property value='departmentId'/>" selected="selected">
			                					<s:property value='locationName' />
			            				</option>
						        	</s:if>
						        	<s:else>
						        		<option value="<s:property value='locationId'/>" >
			                					<s:property value='locationName' />
			            				</option>
						        	</s:else>
						      	</s:iterator>
						      </select>
		   				</s:else>
		   					
					   
				    </div>
			  </div>
			  <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.reportDate"/></label>
				    <div class="form-inline">
					   	&nbsp;&nbsp;&nbsp;&nbsp;
					   	<sd:datetimepicker  name="reportDate" displayFormat="dd-MMM-yyyy" cssClass="form-control" id="reportDate"/>
		          	</div>
		          	<div id="dateErr">
				    	&nbsp;&nbsp;&nbsp;<font class="myErrorMessage"><s:property value="fieldErrors['reportDate'][0]"/></font>
				    </div>
			  </div>
			  	  
			  <div class="form-group row">
				  <label for="a" class="col-sm-3 col-form-label"></label>
				  <!-- <div align="right"> -->
				  		<!--   <div class="form-check-inline">
							  <label class="form-check-label">
							    &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" class="form-check-input" name="output" value="html" checked="checked">HTML
							  </label>
						  </div> -->
					<!-- 		<div class="form-check-inline">
							  <label class="form-check-label">
							    &nbsp;&nbsp;&nbsp;<input type="radio" class="form-check-input" name="output" checked="checked">PDF
							  </label>
							</div>
							<div class="form-check-inline">
							  <label class="form-check-label">
							    <input type="radio" class="form-check-input" name="output">Excel
							  </label>
							</div> -->
							
						<div class="col-sm-4" align="right">
							<input type="hidden" name="command" value="search">
	     	        		<input type="submit" name="" value="Generate Excel" formaction="callDailyInqAction" id="save">		
				  		</div>  
				
				  <!-- </div> -->
		  		 <!--  <div class="col-sm-5" align="right">
	     	        	<input type="submit" name="" value="Generate Report" formaction="resetBudgetDocAction">		
				  </div> --> 
			  </div>
		</div>
	</div>
	</form>
	
	<div id="msgDiv">
		 <s:if test="hasActionMessages()"> 
			 <%if((((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).getActionMessages()).toString().contains("not found")){%>							
					<div class="alert-box notice">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span>
						<s:actionmessage/>
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
					</div>
			  <% }else{ %> 
				  <div class="alert-box warning">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span> 
						<s:actionmessage/>
						<%-- <s:actionerror/> --%>
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
						
					</div>
		  	  <%} %>
		  	  
		</s:if>
	</div>
	
	</div>
</body>
</html>