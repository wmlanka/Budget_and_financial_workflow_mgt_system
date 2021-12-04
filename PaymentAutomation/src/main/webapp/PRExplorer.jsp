<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@page import="java.text.DecimalFormat" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/struts-dojo-tags" prefix="sd" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>PR Explorer</title>
 	
 	<sd:head /> 
 	
 	  <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	 
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	 
 	  <script type="text/javascript">
 			$(document).ready(function() { 
 	    		$("#prNo").change(function(event) {
	    	    	var prNo = $("#prNo").val();
		  			
		  		    if (isNaN(prNo)) {
		  		    	prNo = '';
		  		    }
			  		$("#prNo").val(prNo);

		    	});
 		});
 	   </script>
 	 
 	 <style type="text/css">
 	 .errorMessage {display: none;}
 	 
 	 table.displaytag td {
		border: none;
		padding: 4px;	
		font-size: 8pt;
		border-bottom: 1px solid black;
	}
	
	table.displaytag a {		
		font-size: 8pt;
	}
	
	table.displaytag th {
		padding-left: 5px;
		padding-right: 15px;
		font-size: 8pt;
		border-bottom: 1px solid black;
	}

 	 </style>  
 	 
 	<script>

           
	</script>
 			
 	 <style type="text/css">
		.linkAdd { 
			 	display: inline-block;
			    border: none;
			    padding: 0.4rem 2rem;
			    margin: 0;
			    text-decoration: none;
			    background: rgb(23, 162, 184); /*  rgb(109, 127, 204) #0069ed;*/ 
			    color: #ffffff;
			    font-family: sans-serif;
			    font-size: 1rem;
			    cursor: pointer;
			    text-align: center;
			    transition: background 250ms ease-in-out, transform 150ms ease;
			    -webkit-appearance: none;
			    -moz-appearance: none; 
		} 
		 

	</style>
 </head>
<body>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-search">
					 <h5>
						  Payment Explorer
				   	</h5>
			   </td>
			</tr>
		</table>
	</div>	

	<div class="cardContainer">

  		<form>
 		  <div class="card">
		  <div class="card-body">
		  		  	
		 	<div class="form-inline" style="margin-bottom: 5px;">
		 		<div class="form-group">
				    <label for=""><s:text name="label.prNo"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				    <input type="text" name="prNo"  value="<s:property value="prNo"/>" class="form-control" placeholder="Enter No (1-999999)" id="prNo">
				    &nbsp;&nbsp;
				</div>
				<div class="form-group">
				    <label for=""> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<s:text name="label.paymentStatus"/> &nbsp;&nbsp;&nbsp;
				    </label>
				    <select name="status"  class="form-control">
	   					<option value="-1">Select Status</option>		
					    <option value="DRAFT" <s:if test="status == 'DRAFT'">selected="selected"</s:if>>Draft</option>
					    <option value="PENDING" <s:if test="status == 'PENDING'">selected="selected"</s:if>>Pending</option>
					    <option value="APPROVED" <s:if test="status == 'APPROVED'">selected="selected"</s:if>>Approved</option>
					    <option value="REJECTED" <s:if test="status == 'REJECTED'">selected="selected"</s:if>>Rejected</option>
					    <option value="CANCELED" <s:if test="status == 'CANCELED'">selected="selected"</s:if>>Canceled</option>
					</select>
				    <font class="myErrorMessage"><s:property value="fieldErrors['status'][0]" /></font>
				  </div>		
			 </div>
			 
			 <div class="form-inline" style="margin-bottom: 5px;">
			 	<div class="form-group">
				     <label for="">Supplier/Beneficiary&nbsp;&nbsp;</label>
				    
				     <s:set var="sId"><s:property value='stakeholderId1'/></s:set>
			       	  <select name="stakeholderId1" class="form-control" id="">
				       		<option value="0">Select Stakeholder</option>  	
 			    
				        <s:iterator value="stakeholderList" var="stakeholderList">
				        	<s:if test="%{#stakeholderList.stakeholderId==#sId}">
					        	<option value="<s:property value='stakeholderId1'/>" selected="selected">
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
			 
			 <div class="form-inline">
			   	  <div class="form-group">
				    	<label for="">
				    			<s:text name="label.fromDate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	</label>
					   	<sd:datetimepicker  name="fromDate" displayFormat="dd-MMM-yyyy" cssClass="form-control" /> <!-- value="%{'today'}" -->
					   	&nbsp;&nbsp;&nbsp;  
		          </div>
				  <div class="form-group">
				    	<label for=""> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	
				    					<s:text name="label.toDate"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	</label>
						<sd:datetimepicker  name="toDate" displayFormat="dd-MMM-yyyy" cssClass="form-control" /> <!-- value="%{'today'}" -->
						&nbsp;&nbsp;&nbsp;  
				  </div>
	<!-- 			   <div class="col-auto my-1">
						<input type="submit" name="" value="Search" formaction="searchPRAction">
				   </div>
				    <div class="col-auto my-1">
						<input type="submit" name="" value="Reset" formaction="searchPRAction">
				   </div> -->
			 </div>
		<!-- 	 <div class="form-group">
			 	  <div class="myErrorMessage" id="approvalTypeErr"> </div>
				  <div class="myErrorMessage" id="approvalDocErr"> </div>
				  <div class="myErrorMessage" id="appliedAmountErr"> </div>
			 </div> -->
			 <br>
			 <div style="margin-bottom: 5px;"> <b> Pending Approval : </b></div>
			 
			 <div class="form-inline" style="margin-bottom: 5px;">
		 		<div class="form-group">
				    <label for=""><s:text name="label.approvalLevel"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				     <select name="userType"  class="form-control">
	   					<option value="-1">Select Approval Level</option>		
					    <option value="SUPER_USER" <s:if test="userType == 'SUPER_USER'">selected="selected"</s:if>>Super User/Higher Approval</option>
					    <option value="OFFICER_A" <s:if test="userType == 'OFFICER_A'">selected="selected"</s:if>>Officer A</option>
					    <option value="OFFICER_B" <s:if test="userType == 'OFFICER_B'">selected="selected"</s:if>>Officer B</option>
					</select>
				    &nbsp;&nbsp;
				</div>
				<div class="form-group">
				    <label for=""> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    			<s:text name="label.department"/> &nbsp;&nbsp;&nbsp;
				    </label>
				    <s:set var="depId"><s:property value='departmentId1'/></s:set>
				    <select name="departmentId1"  class="form-control">
	   					<option value="-1">Select Department</option>
	   					<s:iterator value="locationList" var="locationList">
				        	<s:if test="%{#locationList.locationId==#depId}">
					        	<option value="<s:property value='departmentId1'/>" selected="selected">
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
				
				    <font class="myErrorMessage"><s:property value="fieldErrors['status'][0]" /></font>
				  </div>	
			 </div>
			 
			 <div align="right">
			 	   <div class="col-auto my-1">
						<input type="submit" name="" value="Search" formaction="searchPRAction">
						<input type="submit" name="" value="Reset" formaction="prExplorerAction">
				   </div>
				    <div class="col-auto my-1">
						
				   </div>
			 </div>
			<%-- *<s:actionerror/>* --%>
	
		 </div>
		 </div> 
		 
		 <br>
	    
    	<input type="hidden" name="command" value="search">
    </form>
</div>

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
		
	
</body>
</html>



