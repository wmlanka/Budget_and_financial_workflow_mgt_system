<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Add Ledger Account</title>
	
	<link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	<link rel="stylesheet" href="assets/css/myStyle.css">
 	<link rel="stylesheet" href="assets/css/commonStyle.css">
 	
 	<script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	<script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	
 	 <script type="text/javascript">
	 	$(document).ready(function() { 
	
			 
			  $('#accountCategory').change(function(event) {
 			      var accountCategory = $("select#accountCategory").val(); //alert(accountCategory);
 			      if(accountCategory=='ASSET')
 			    	 $("select#standardType").val("DR");
 			      
 			     if(accountCategory=='LIABILITY')
			    	 $("select#standardType").val("CR");
 			     
 			    if(accountCategory=='EQUITY')
			    	 $("select#standardType").val("CR");
 			    
 			    if(accountCategory=='INCOME')
			    	 $("select#standardType").val("CR");
 			    
 			    if(accountCategory=='EXPENSE')
			    	 $("select#standardType").val("DR");
 			      
 			      });
			  	
			     
			 
	 	});
	 	 </script>

 	
 	<%-- <s:head /> --%>
</head>

<body>

<%-- 	<div class="important img-user" id="instructions" style="width: 80%;">
		   <s:if test="command.equals('add')">
		   	Add Ledger Account
		   </s:if>
		   <s:if test="!command.equals('add')">
		   	Edit Ledger Account
		   </s:if>
	</div> --%>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-ledgerAccount">
					 <h5>
					  <br>
						   <s:if test="command.equals('add')">
						   		Add Ledger Account
						   </s:if>
						   <s:if test="!command.equals('add')">
						   		Edit Ledger Account
						   </s:if>
						   
				   	</h5>
			   </td>
			</tr>
		</table>
	</div>
	
	 		<s:if test="hasActionMessages()">
			 <%if((((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).getActionMessages()).toString().contains("SUCCESS")){%>							
					<div class="alert-box success">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span>
						<s:actionmessage/>
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
					</div>
			  <% }else{ %>
				  <div class="alert-box error">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span>
						<s:actionmessage/>
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
						
					</div>
		  		<%} %>
		  </s:if>
	
	<!-- <br> -->
	
	<div class="cardContainer">
	
		<s:set var="formAction" />
		<s:if test="command.equals('add')">
			<s:set var="formAction" value="'addLedgerAccountAction.action'" />
		</s:if>
		<s:else>
			<s:set var="formAction" value="'editLedgerAccountAction.action'" />
		</s:else>
		
		<s:form action="%{#formAction}">
	
	<div class="card">
		<div class="card-body">
	

			 <div class="form-group row">
			    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.accountNo"/>
			    <font class="myErrorMessage"> &nbsp; * </font></label>
			    <div class="col-sm-6">
			      <input type="text" name="accountNo" value="<s:property value="accountNo"/>" class="form-control" id="a">
			    </div>
			    <div>
			    	<font class="myErrorMessage"><s:property value="fieldErrors['accountNo'][0]"/></font>
			    </div>
		  	</div>		
		  	
		  	<div class="form-group row">
			    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.accountName"/>
			    <font class="myErrorMessage"> &nbsp; * </font></label>
			    <div class="col-sm-6">
			      <input type="text" name="accountName" value="<s:property value="accountName"/>" class="form-control" id="a">
			    </div>
			    <div>
			    	<font class="myErrorMessage"><s:property value="fieldErrors['accountName'][0]"/></font>
			    </div>
		  	</div>
		  	
		  	<div class="form-group row">
			    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.accountCategory"/>
			    <font class="myErrorMessage"> &nbsp; * </font></label>
			    <div class="col-sm-6">
							<select name="accountCategory" class="form-control" id="accountCategory">
		  					    <option value="-1">Select Account Category</option>
							    <option value="ASSET"  <s:if test="accountCategory == 'ASSET'">selected="selected"</s:if>>Asset</option>
							    <option value="LIABILITY" <s:if test="accountCategory == 'LIABILITY'">selected="selected"</s:if>>Liability</option>
							    <option value="EQUITY" <s:if test="accountCategory == 'EQUITY'">selected="selected"</s:if>>Equity</option>
							    <option value="INCOME" <s:if test="accountCategory == 'INCOME'">selected="selected"</s:if>>Income</option>
							    <option value="EXPENSE" <s:if test="accountCategory == 'EXPENSE'">selected="selected"</s:if>>Expense</option>
							  </select>
				</div>
			    <div>
			    	<font class="myErrorMessage"><s:property value="fieldErrors['accountCategory'][0]"/></font>
			    </div>
		  	</div>
		  	
		  	  	
		  	<div class="form-group row">
			    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.standardType"/>
			    <font class="myErrorMessage"> &nbsp; * </font></label>
			    <div class="col-sm-6">
							 <select name="standardType" class="form-control" id="standardType">
			  					    <option value="-1">Select Account Standard Type</option>
								    <option value="CR" <s:if test="standardType == 'CR'">selected="selected"</s:if>>Credit</option>
								    <option value="DR"<s:if test="standardType == 'DR'">selected="selected"</s:if>>Debit</option>
							</select>
				</div>
			    <div>
			    	<font class="myErrorMessage"><s:property value="fieldErrors['standardType'][0]"/></font>
			    </div>
		  	</div>		
		  	
		  	 <div class="form-group row">
			    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.remark"/>
			    </label>
			    <div class="col-sm-6">
					<input type="text" name="remark" value="<s:property value="remark"/>" class="form-control">
				</div>
			    <div>
			    </div>
		  	</div>
		  	
 			<s:if test="command.equals('update')">
	 			 <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.status"/>
				    <font class="myErrorMessage"> &nbsp; * </font></label>
				    <div class="col-sm-6">
						<input type="radio" name="status" value="A" <s:if test='status=="A"'>CHECKED</s:if>>&nbsp;Active &nbsp;
			          	<input type="radio" name="status" value="I" <s:if test='status=="I"'>CHECKED</s:if>>&nbsp;Inactive					
			        </div>
				    <div>
				    </div>
			  	</div>

		    </s:if>
		 </div>
	</div>
		  				  			
		  	<%-- <s:actionerror/> --%>

				<br>
						    
			<div align="center" id="buttons" >
			     <table style="width: 90%">
			     	<tr>
			     		<td align="left"><a href="ledgerAccountAction?" class="back-link">Go Back</a></td>
			     	
			     		<td class="tdRight">
			     		    <input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>">
			     		
			     			<s:if test="command.equals('add')">
			      	        		<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetLedgerAccountAction">		
					      	</s:if>
					      	<s:else>
					      	      	<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetLedgerAccountAction">		
					      	</s:else>
			      		</td>
			     	</tr>
			     </table>	
			    </div>
			  			  			
		  		<s:hidden name="command" value="save"/>
		  		<s:hidden name="ledgerAccountId" />
			  				  		
	</s:form>
	</div>
</body>
</html>