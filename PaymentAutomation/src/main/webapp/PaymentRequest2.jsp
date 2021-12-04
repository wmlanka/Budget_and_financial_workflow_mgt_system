<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%-- <%@ taglib uri="/struts-dojo-tags" prefix="sd" %> --%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Pay Request</title>
	<%-- <sj:head/> --%>
	
	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	 
 	 <script type="text/javascript" src="bootstraps/js/jquery.min.js"></script>
 	 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
 	   
	 <%--<script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> --%>
 	 
 	  	 <script type="text/javascript">
 	  	 
 	  	/* $('#exampleModalCenter').on('shown.bs.modal', function () {
 	  	  $('#myInput').trigger('focus')
 	  	}) */
 	  	
 			$(document).ready(function() { 
 	    		$("#payableAmount").change(function(event) {
 	    			var payableAmount = $("#payableAmount").val();
 	    			if(!payableAmount){
 	    				payableAmount = '0.00';
 			    	}
	    	    
	    	    	var vatAmount = $("#vatAmount").val();
		  			var payableOther = $("#payableOther").val();
		  			var payableTotal = $("#payableTotal").val();
		  			var deductTotal = $("#deductTotal").val();
		  			var netAmount =0.0;
		  			
	    	    	if (payableAmount.indexOf(',') > -1) {
	    	    		payableAmount = $("#payableAmount").val().replace(/,/g, '');  
	    	    	}
	    	     	if (vatAmount.indexOf(',') > -1) {
	    	     		vatAmount = $("#vatAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (payableOther.indexOf(',') > -1) {
	    	    		payableOther = $("#payableOther").val().replace(/,/g, '');  
		    	    }
	    	    	if (deductTotal.indexOf(',') > -1) {
	    	    		deductTotal = $("#deductTotal").val().replace(/,/g, '');  
		    	    }

		  			var error = false;
		  			var grossAmount = 0.00;
					$("#grossAmount").val(0.00);

		  		    if (isNaN(payableAmount)) {
						payableAmount = 0.00;
		  		    }
			  		 
		  		    payableAmount = parseFloat(payableAmount);
		  		  	vatAmount	  = parseFloat(vatAmount);
		  		  	payableOther  = parseFloat(payableOther);
		  		    payableTotal = payableAmount + vatAmount + payableOther;//parseFloat need before calculation
		  		  	netAmount = parseFloat(payableTotal) - parseFloat(deductTotal);
		  		  		
		  		    payableAmount = payableAmount.toLocaleString('en-US', {minimumFractionDigits: 2});//payableAmount parsefloat is required for this
		  		    vatAmount = vatAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
		  		    payableOther = payableOther.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		payableTotal = payableTotal.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});

			  		$("#payableAmount").val(payableAmount);
			  		$("#vatAmount").val(vatAmount);
			  		$("#payableOther").val(payableOther);
			  		$("#payableTotal").val(payableTotal);
			  		$("#netAmount").val(netAmount);
		    	});
 	    		
 	    		$("#vatAmount").change(function(event) {
	    	    	var payableAmount = $("#payableAmount").val();
	    	    	var vatAmount = $("#vatAmount").val();
	    	    	if(!vatAmount){
	    	    		vatAmount = '0.00';
 			    	}
	    	    	
		  			var payableOther = $("#payableOther").val();
		  			var payableTotal = $("#payableTotal").val();
		  			var deductTotal = $("#deductTotal").val();
		  			var netAmount =0.0;
		  			
	    	    	if (payableAmount.indexOf(',') > -1) {
	    	    		payableAmount = $("#payableAmount").val().replace(/,/g, '');  
	    	    	}
	    	     	if (vatAmount.indexOf(',') > -1) {
	    	     		vatAmount = $("#vatAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (payableOther.indexOf(',') > -1) {
	    	    		payableOther = $("#payableOther").val().replace(/,/g, '');  
		    	    }
	    	    	if (deductTotal.indexOf(',') > -1) {
	    	    		deductTotal = $("#deductTotal").val().replace(/,/g, '');  
		    	    }

		  		    if (isNaN(vatAmount)) {
		  		    	vatAmount = 0.00;
		  		    }
			  		 
		  		    payableAmount = parseFloat(payableAmount);
		  		  	vatAmount	  = parseFloat(vatAmount);
		  		  	payableOther  = parseFloat(payableOther);
		  		    payableTotal = payableAmount + vatAmount + payableOther;//parseFloat need before calculation
		  		  	netAmount = parseFloat(payableTotal) - parseFloat(deductTotal);
		  		  		
		  		    payableAmount = payableAmount.toLocaleString('en-US', {minimumFractionDigits: 2});//payableAmount parsefloat is required for this
		  		    vatAmount = vatAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
		  		    payableOther = payableOther.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		payableTotal = payableTotal.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});

			  		$("#payableAmount").val(payableAmount);
			  		$("#vatAmount").val(vatAmount);
			  		$("#payableOther").val(payableOther);
			  		$("#payableTotal").val(payableTotal);
			  		$("#netAmount").val(netAmount);
		    	});
 	    		
 	    		$("#payableOther").change(function(event) {
	    	    	var payableAmount = $("#payableAmount").val();
	    	    	var vatAmount = $("#vatAmount").val();
		  			var payableOther = $("#payableOther").val();
		  			if(!payableOther){
		  				payableOther = '0.00';
 			    	}
		  			
		  			var payableTotal = $("#payableTotal").val();
		  			var deductTotal = $("#deductTotal").val();
		  			var netAmount =0.0;
		  			
	    	    	if (payableAmount.indexOf(',') > -1) {
	    	    		payableAmount = $("#payableAmount").val().replace(/,/g, '');  
	    	    	}
	    	     	if (vatAmount.indexOf(',') > -1) {
	    	     		vatAmount = $("#vatAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (payableOther.indexOf(',') > -1) {
	    	    		payableOther = $("#payableOther").val().replace(/,/g, '');  
		    	    }
	    	    	if (deductTotal.indexOf(',') > -1) {
	    	    		deductTotal = $("#deductTotal").val().replace(/,/g, '');  
		    	    }
	
		  		    if (isNaN(payableOther)) {
		  		    	payableOther = 0.00;
		  		    }
			  		 
		  		    payableAmount = parseFloat(payableAmount);
		  		  	vatAmount	  = parseFloat(vatAmount);
		  		  	payableOther  = parseFloat(payableOther);
		  		    payableTotal = payableAmount + vatAmount + payableOther;//parseFloat need before calculation
		  		  	netAmount = parseFloat(payableTotal) - parseFloat(deductTotal);
		  		  		
		  		    payableAmount = payableAmount.toLocaleString('en-US', {minimumFractionDigits: 2});//payableAmount parsefloat is required for this
		  		    vatAmount = vatAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
		  		    payableOther = payableOther.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		payableTotal = payableTotal.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
	
			  		$("#payableAmount").val(payableAmount);
			  		$("#vatAmount").val(vatAmount);
			  		$("#payableOther").val(payableOther);
			  		$("#payableTotal").val(payableTotal);
			  		$("#netAmount").val(netAmount);
	    		});
 	    		
 	   		$("#withholdingTax").change(function(event) {
    	    	var withholdingTax = $("#withholdingTax").val();
    	    	if(!withholdingTax){
    	    		withholdingTax = '0.00';
			    }
    	    	
    	    	var retainedAmount = $("#retainedAmount").val();
	  			var deductOther = $("#deductOther").val();
	  			var deductTotal = $("#deductTotal").val();
	  			var payableTotal = $("#payableTotal").val();
	  			
	  			var netAmount =0.0;
	  			
    	    	if (withholdingTax.indexOf(',') > -1) {
    	    		withholdingTax = $("#withholdingTax").val().replace(/,/g, '');  
    	    	}
    	     	if (retainedAmount.indexOf(',') > -1) {
    	     		retainedAmount = $("#retainedAmount").val().replace(/,/g, '');  
	    	    }
    	    	if (deductOther.indexOf(',') > -1) {
    	    		deductOther = $("#deductOther").val().replace(/,/g, '');  
	    	    }
    	    	if (payableTotal.indexOf(',') > -1) {
    	    		payableTotal = $("#payableTotal").val().replace(/,/g, '');  
	    	    }

	  		    if (isNaN(withholdingTax)) {
	  		    	withholdingTax = 0.00;
	  		    }
		  		 
	  		    withholdingTax = parseFloat(withholdingTax);
	  		  	retainedAmount	  = parseFloat(retainedAmount);
	  			deductOther  = parseFloat(deductOther);
	  			deductTotal = withholdingTax + retainedAmount + deductOther;//parseFloat need before calculation
	  		  	netAmount = parseFloat(payableTotal) - parseFloat(deductTotal);
	  		  		
	  		  	withholdingTax = withholdingTax.toLocaleString('en-US', {minimumFractionDigits: 2});//payableAmount parsefloat is required for this
	  			retainedAmount = retainedAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
	  			deductOther = deductOther.toLocaleString('en-US', {minimumFractionDigits: 2});
	  			deductTotal = deductTotal.toLocaleString('en-US', {minimumFractionDigits: 2});
		  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});

		  		$("#withholdingTax").val(withholdingTax);
		  		$("#retainedAmount").val(retainedAmount);
		  		$("#deductOther").val(deductOther);
		  		$("#deductTotal").val(deductTotal);
		  		$("#netAmount").val(netAmount);
    		});
 	   		
	 	   	$("#retainedAmount").change(function(event) {
		    	var withholdingTax = $("#withholdingTax").val();
		    	var retainedAmount = $("#retainedAmount").val();
		    	if(!retainedAmount){
		    		retainedAmount = '0.00';
			    }
		    	
	  			var deductOther = $("#deductOther").val();
	  			var deductTotal = $("#deductTotal").val();
	  			var payableTotal = $("#payableTotal").val();
	  			
	  			var netAmount =0.0;
	  			
		    	if (withholdingTax.indexOf(',') > -1) {
		    		withholdingTax = $("#withholdingTax").val().replace(/,/g, '');  
		    	}
		     	if (retainedAmount.indexOf(',') > -1) {
		     		retainedAmount = $("#retainedAmount").val().replace(/,/g, '');  
	    	    }
		    	if (deductOther.indexOf(',') > -1) {
		    		deductOther = $("#deductOther").val().replace(/,/g, '');  
	    	    }
		    	if (payableTotal.indexOf(',') > -1) {
		    		payableTotal = $("#payableTotal").val().replace(/,/g, '');  
	    	    }
	
	  		    if (isNaN(retainedAmount)) {
	  		    	retainedAmount = 0.00;
	  		    }
		  		 
	  		    withholdingTax = parseFloat(withholdingTax);
	  		  	retainedAmount	  = parseFloat(retainedAmount);
	  			deductOther  = parseFloat(deductOther);
	  			deductTotal = withholdingTax + retainedAmount + deductOther;//parseFloat need before calculation
	  		  	netAmount = parseFloat(payableTotal) - parseFloat(deductTotal);
	  		  		
	  		  	withholdingTax = withholdingTax.toLocaleString('en-US', {minimumFractionDigits: 2});//payableAmount parsefloat is required for this
	  			retainedAmount = retainedAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
	  			deductOther = deductOther.toLocaleString('en-US', {minimumFractionDigits: 2});
	  			deductTotal = deductTotal.toLocaleString('en-US', {minimumFractionDigits: 2});
		  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
	
		  		$("#withholdingTax").val(withholdingTax);
		  		$("#retainedAmount").val(retainedAmount);
		  		$("#deductOther").val(deductOther);
		  		$("#deductTotal").val(deductTotal);
		  		$("#netAmount").val(netAmount);
			});
	 	   	
	 		$("#deductOther").change(function(event) {
		    	var withholdingTax = $("#withholdingTax").val();
		    	var retainedAmount = $("#retainedAmount").val();
	  			var deductOther = $("#deductOther").val();
	  			if(!deductOther){
	  				deductOther = '0.00';
			    }
	  			
	  			var deductTotal = $("#deductTotal").val();
	  			var payableTotal = $("#payableTotal").val();
	  			
	  			var netAmount =0.0;
	  			
		    	if (withholdingTax.indexOf(',') > -1) {
		    		withholdingTax = $("#withholdingTax").val().replace(/,/g, '');  
		    	}
		     	if (retainedAmount.indexOf(',') > -1) {
		     		retainedAmount = $("#retainedAmount").val().replace(/,/g, '');  
	    	    }
		    	if (deductOther.indexOf(',') > -1) {
		    		deductOther = $("#deductOther").val().replace(/,/g, '');  
	    	    }
		    	if (payableTotal.indexOf(',') > -1) {
		    		payableTotal = $("#payableTotal").val().replace(/,/g, '');  
	    	    }
	
	  		    if (isNaN(deductOther)) {
	  		    	deductOther = 0.00;
	  		    }
		  		 
	  		    withholdingTax = parseFloat(withholdingTax);
	  		  	retainedAmount	  = parseFloat(retainedAmount);
	  			deductOther  = parseFloat(deductOther);
	  			deductTotal = withholdingTax + retainedAmount + deductOther;//parseFloat need before calculation
	  		  	netAmount = parseFloat(payableTotal) - parseFloat(deductTotal);
	  		  		
	  		  	withholdingTax = withholdingTax.toLocaleString('en-US', {minimumFractionDigits: 2});//payableAmount parsefloat is required for this
	  			retainedAmount = retainedAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
	  			deductOther = deductOther.toLocaleString('en-US', {minimumFractionDigits: 2});
	  			deductTotal = deductTotal.toLocaleString('en-US', {minimumFractionDigits: 2});
		  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
	
		  		$("#withholdingTax").val(withholdingTax);
		  		$("#retainedAmount").val(retainedAmount);
		  		$("#deductOther").val(deductOther);
		  		$("#deductTotal").val(deductTotal);
		  		$("#netAmount").val(netAmount);
			});
 		
	 		//entries add
	 		$("#amount").change(function(event) {
		    	var amount = $("#amount").val();
		    	if(!amount){
		    		amount = '0.00';
		    	}
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
	 		
	 		 $("#customerAccountNo").change(function(event) {
			    	var customerAccountNo = $("#customerAccountNo").val();
		  		    if (isNaN(customerAccountNo)) {
		  		    	customerAccountNo = '';
		  		    }
			  		$("#customerAccountNo").val(customerAccountNo);
		
		    	});
 		
 			   
 			});
	
 	 </script>
 	 <style type="text/css">
 	 
 	 .dojoTab.current {
	    padding-bottom: 1px;
	    border-bottom: 0;
	    background-position: 0 -150px;
	}
 	 
 	 .dojoTab div {
	    display: block;
	    padding: 4px 15px 4px 6px;
	    background: url(/PaymentAutomation/struts/dojo/src/widget/templates/images/tab_top_right.gif) no-repeat right top;
	    color: #333;
	    font-size: 18px;
	    font-family: sans-serif;
	    letter-spacing: 1px;
	}

	.dojoTab {
	    position: relative;
	    float: left;
	    padding-left: 9px;
	    border-bottom: 1px solid #6290d2;
	    background: url(/PaymentAutomation/struts/dojo/src/widget/templates/images/tab_left.gif) no-repeat left top;
	    cursor: pointer;
	    white-space: nowrap;
	    z-index: 3;
	 }
 	 </style>
</head>
<body>

    <s:set var="uType"><s:property value='userSession.userType'/></s:set>
    <s:set var="depId"><s:property value='userSession.departmentId'/></s:set>
    <s:set var="wuType"><s:property value='approvalWorkflow.userType'/></s:set>
    <s:set var="wdepId"><s:property value='approvalWorkflow.departmentId'/></s:set>
   
	<s:set var="payId"><s:property value='paymentRequestId'/></s:set>
	<s:set var="prStatus"><s:property value='prfStatus'/></s:set> <!-- for update btn -->
	<s:set var="rejectReason"><s:property value='rejectReason'/></s:set>
	<s:set var="cancelReason"><s:property value='cancelReason'/></s:set>
	
	<s:set var="disableUI"><s:property value='disableUI'/></s:set>
	
	   <div id="buttons" align="center" class="headerMain" >
	   <form>
		     <table style="width: 100%;">
		     	<tr>
		     		<td align="left" class="img-payment">
		     		   <s:if test="%{#payId==0}">
					   		<b>Payment Request Creation</b><br> 
					   </s:if>
					   <s:else>
					   		<b>Payment No: <font color="#5cb85c"><s:property value="prNumber"/></font><br>
					   		Status :<font color="#5cb85c"><s:property value='prfStatus'/></font></b>
					   		&nbsp;&nbsp;&nbsp;&nbsp;
					   		<s:if test="%{#rejectReason!=''}">
					   			<b>Reject Reason: <font color="#5cb85c"><s:property value='rejectReason'/></font> </b>
					   		</s:if>
					   		<s:if test="%{#cancelReason!=''}">
					   			<b>Cancel Reason: <font color="#5cb85c"><s:property value='cancelReason'/></font> </b>
					   		</s:if>
					   </s:else>
		     		</td>
		     	
		      		 <td  class="tdRight">
		     		 	<s:if test="%{#payId!=0}">
		     		 	  <s:if test="%{#uType==#wuType && #depId==#wdepId}">
		     		 	  		<input type="button" name="" value="Approve" data-toggle="modal" data-target="#exampleModalCenter3">
						      	<input type="button" name="" value="Reject" data-toggle="modal" data-target="#exampleModalCenter">
						      	<input type="button" name="" value="Cancel" data-toggle="modal" data-target="#exampleModalCenter2">
					      </s:if>

					      <s:if test="%{#prStatus=='DRAFT' && (#uType=='SYSTEM_ADMIN' || #uType=='SYSTEM_OPERATOR') }">
					        <input type="button" name="" value="Cancel" data-toggle="modal" data-target="#exampleModalCenter2">
					      	<input type="submit"  value="Send for Approval" formaction="sendPRApprovalAction">
					      </s:if>
				      	</s:if>
		      		</td>
		     	</tr>
		     </table>
		     </form>
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
	 	<s:form> 	
			
			<s:if test='%{#disableUI=="Y"}'>
				<div style=" pointer-events: none;">
			</s:if>		   
			<s:else>
				<div>
			</s:else>
			
				<div class="card">
						  <div class="card-body">
						  		<h5 class="card-title">Payable/Deduction</h5>
						  		<s:include value="PRFAmounts.jsp"/>
						  </div>
				</div>
				
				</br>
				
				<div class="card" style="margin-bottom: 0px;">
						  <div class="card-body">
						  		<h6 class="card-title"><b>Ledger Entries</b></h6>
						  		<s:include value="PREntries.jsp"/>
						  </div>
				</div>
			
			</div>	
		  
		    <div id="buttons" align="center">
			     <table style="width: 90%;">
			     	<tr>
			     		 <td align="left">
			     		    <input type="submit" name="" value="Go Back" formaction="goBackPREntryAction">
			     		 </td>
			     		 <td  class="tdRight">
			     			<input type="hidden" name="command" value="save1">
			     			
			     			<s:if test="%{#payId==0}">
			     		    	<input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>" formaction="addPayRequestAction">
			     		    	<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="paymentRequestAction">		
			     			</s:if>
			     			<s:elseif test="%{#prStatus=='DRAFT' || #prStatus=='REJECTED'}">
			     		    	<input type="submit" name="saveSubmit" value="<s:text name="button.update"/>" formaction="editPayRequestAction">
			     		    	<input type="submit" name="" value="<s:text name="button.reset"/>" formaction="editPRAction">		
			     			</s:elseif>
			     			
			     			<%-- <s:if test="%{#payId==0 || #prStatus=='DRAFT'}" > <!-- draft and new only -->
			      	        	<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetSourceDocAction">		
							</s:if> --%>
			      		</td>
			     	</tr>
			     </table>
		    </div> 
    
		</s:form> 
 	</div>

	<!-- Modal -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Reject Payment</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <form>
		      <div class="modal-body">
		        	<input type="text" name=rejectReason id=""  class="form-control" placeholder="Enter Reject Reason" maxlength="200">
		      </div>
		      <div class="modal-footer">
			        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			        <input type="submit" class="btn btn-primary" formaction="rejectPRAction" value="Submit">
		      </div>
	       </form>
	    </div>
	  </div>
	</div>
	
	<!-- Cancel Modal2 -->
	<div class="modal fade" id="exampleModalCenter2" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Cancel Payment</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <form>
		      <div class="modal-body">
		        	<input type="text" name=cancelReason id=""  class="form-control" placeholder="Enter Cancel Reason" maxlength="200">
		      </div>
		      <div class="modal-footer">
			        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			        <input type="submit" class="btn btn-primary" formaction="cancelPRAction" value="Submit">
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	<!-- Approve Modal3 -->
	<div class="modal fade" id="exampleModalCenter3" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Approve Payment</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        	<p>Are you sure you want to approve this payment?</p>
	      </div>
	      <div class="modal-footer">
	      	<form>
		        <button type="button" class="btn btn-danger" data-dismiss="modal">No</button>
		        <input type="submit" class="btn btn-primary"  formaction="approvePRAction" value="Yes">
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>