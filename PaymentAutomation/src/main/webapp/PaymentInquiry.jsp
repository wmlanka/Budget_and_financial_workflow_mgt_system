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
<title>Report Payment</title>

	  <sd:head />
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> <!-- Required for .indexOf(',') -->
 	 
 	 
 	 <script type="text/javascript">
		 	$(document).ready(function() { 
		 		
		 		$("#prNo").change(function(event) {
			    	var prNo = $("#prNo").val();
		  			
		  		    if (isNaN(prNo)) {
		  		    	prNo = '';
		  		    }
			  		$("#prNo").val(prNo);
		
		    	});
		 		
		 		$("#amount").change(function(event) {
			    	var amount = $("#amount").val();
			    	
			    	if (amount.indexOf(',') > -1) {
			  		  		amount = $("#amount").val().replace(/,/g, '');  
		    	    }
			    	 
		  		    if (isNaN(amount)) {
		  		    	amount = 0.00;
		  		    }
		  		   
		  		  	amount  = parseFloat(amount);
		  		  	amount = amount.toLocaleString('en-US', {minimumFractionDigits: 2});	  		  
			  		$("#amount").val(amount);
		
		    	});
		 		
		 		 $("#year").change(function(event) {
				    	var year = $("#year").val();
				    	$("#yearErr").empty();
			  		    if (isNaN(year)) {
			  		    	year = '';
			  		    }
				  		$("#year").val(year);
			
			    	});
		 		 
		 		 $("#save").click(function(event) {
						$("#msgDiv").empty();
						$("#yearErr").empty();

			 	});
			});
 	 </script>
 	 
</head>
<body>
	
<!-- 	<div class="important img-user" id="instructions" style="width: 80%;">
		Payment Summary
	</div> -->
	
	<s:set var="uType"><s:property value='userSession.userType'/></s:set>
    <s:set var="fUser"><s:property value='financeUser'/></s:set>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-report">
					 <h5>
						  Payment Summary
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
				    <label for="a" class="col-sm-3 col-form-label">Payment Request No</label>
				    <div class="col-sm-3">
						<input type="text" name="prNo"  value="<s:property value="prNo"/>" class="form-control" placeholder="Enter No (1-999999)" id="prNo">
				    </div>
				    <label for="a" class="col-sm-2 col-form-label"><s:text name="label.year"/></label>
				    <div class="col-sm-3">
						<input type="text" name="year" value="<s:property value="year"/>" class="form-control" id="year" maxlength="4">
				    </div>
			  </div>
			  <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label">Cost Center</label>
				    <div class="col-sm-3">
						<s:set var="depId"><s:property value='departmentId'/></s:set>
					    <select name="departmentId"  class="form-control">
					    
					    <s:if test='%{#fUser=="Y" || #uType=="SYSTEM_ADMIN"}'>
		   					<option value="-1">Select Department</option>
		   				</s:if>
		   					
		   					
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
				    </div>
				    
				    <label for="a" class="col-sm-2 col-form-label">Payment Status</label>
			  		<div class="col-sm-3">
						<select name="status"  class="form-control">
		   					<option value="-1">Select Status</option>		
						    <option value="DRAFT" <s:if test="status == 'DRAFT'">selected="selected"</s:if>>Draft</option>
						    <option value="PENDING" <s:if test="status == 'PENDING'">selected="selected"</s:if>>Pending</option>
						    <option value="APPROVED" <s:if test="status == 'APPROVED'">selected="selected"</s:if>>Approved</option>
						    <option value="REJECTED" <s:if test="status == 'REJECTED'">selected="selected"</s:if>>Rejected</option>
						    <option value="CANCELED" <s:if test="status == 'CANCELED'">selected="selected"</s:if>>Canceled</option>
						</select>
				    </div>
			  </div>
			  
			<%--   <div class="form-group row">
			  		<label for="a" class="col-sm-3 col-form-label">Payment Status</label>
			  		<div class="col-sm-4">
						<select name="status"  class="form-control">
		   					<option value="-1">Select Status</option>		
						    <option value="DRAFT" <s:if test="status == 'DRAFT'">selected="selected"</s:if>>Draft</option>
						    <option value="PENDING" <s:if test="status == 'PENDING'">selected="selected"</s:if>>Pending</option>
						    <option value="APPROVED" <s:if test="status == 'APPROVED'">selected="selected"</s:if>>Approved</option>
						    <option value="REJECTED" <s:if test="status == 'REJECTED'">selected="selected"</s:if>>Rejected</option>
						    <option value="CANCELED" <s:if test="status == 'CANCELED'">selected="selected"</s:if>>Canceled</option>
						</select>
				    </div>
			  </div> --%>
			  
			  <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label">Minimum Payment Amount</label>
				    <div class="col-sm-3">
						<input type="text" name="amount" value="<s:property value="amount"/>" class="form-control" id="amount">
				    </div>
				    <label for="a" class="col-sm-2 col-form-label">Supplier/Beneficiary</label>
			  		<div class="col-sm-3">
						 	  <s:set var="sId"><s:property value='stakeholderId'/></s:set>
						       <select name="stakeholderId" class="form-control" id="a">
		 				       		<option value="">Select Stakeholder</option>  	
		  			    
							        <s:iterator value="stakeholderList" var="stakeholderList">
							        	<s:if test="%{#stakeholderList.stakeholderId==#sId}">
								        	<option value="<s:property value='stakeholderId'/>" selected="selected">
				                					<s:property value='fullName' />
				            				</option>
							        	</s:if>
							        	<s:else>
							        		<option value="<s:property value='stakeholderId'/>" >
				                					<s:property value='fullName' />
				            				</option>
							        	</s:else>
							      	</s:iterator>
							    </select>
				    </div>
			  </div>
			  
  			  <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.fromDate"/></label>
				    <div class="form-inline">
					   	&nbsp;&nbsp;&nbsp;&nbsp;
					   	<sd:datetimepicker  name="fromDate" displayFormat="dd-MMM-yyyy" cssClass="form-control"/>
		          	</div>
			  </div>
			  
			  <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.toDate"/></label>
				    <div class="form-inline">
					  	&nbsp;&nbsp;&nbsp;&nbsp;
					  	<sd:datetimepicker  name="toDate" displayFormat="dd-MMM-yyyy" cssClass="form-control" value="%{'today'}"/>
		          	</div>
			  </div>
			  
			  <div class="form-group">
			   		  <div class="myErrorMessage" id="yearErr"><s:property value="fieldErrors['year'][0]" /></div>
					  <div class="myErrorMessage"><s:property value="fieldErrors['fromDate'][0]" /></div>
					  <div class="myErrorMessage"><s:property value="fieldErrors['toDate'][0]" /></div>					  
			  </div>
			  

			  <div class="form-group row">
			  		<div class="col-sm-8">
				    </div>
				    <div class="col-sm-0">
	     	        	<input type="submit" name="" value="Generate Excel" formaction="callPaymentInqAction" onclick="" id="save">
	     	        	<!-- <input type="submit" name="" value="Reset" formaction="paymentInquiryAction"> -->	
				    </div>
			  </div>
			  
			   
			
			  <input type="hidden" name="command" value="search">
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