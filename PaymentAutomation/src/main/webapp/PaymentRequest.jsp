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
<title>Pay Request Step 1</title>
	<%-- <sj:head/> --%>
	
	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	 
 	 <%-- <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script> --%>
 	 <%-- <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> --%>
 	 
 	<%--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> --%>
 	 <script type="text/javascript" src="bootstraps/js/jquery.min.js"></script>
 	 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
 	 
 	 
 	  	 <script type="text/javascript">
 			$(document).ready(function() { 
 			   $('#budgetTypes').change(function(event) {
 			      var budgetTypes = $("select#budgetTypes").val();
 			      var actionCodeMap = "";
 			      var dummyMsg="";
 			      $.getJSON('ajaxAction?method=getActionCodes', {
 			        budgetType : budgetTypes
 			      }, function(jsonResponse) { 
 			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
 			        var select = $('#actionCodes');
 			        
 			        /* other field clear
 			        var select2 = $('#budgetCodes');
 			        select2.find('option').remove();
 			        $("#allocatedAmount").val(0.00);
 			        $("#utilizedAmount").val(0.00);
 			      	$("#balanceAmount").val(0.00);
 			        $("#budgetDescr").empty();*/
 			        
 			        select.find('option').remove();
 			        $.each(jsonResponse.actionCodeMap, function(key, value) {
 			          $('<option>').val(key).text(value).appendTo(select);
 			        });  
 			      });
 			      });
 			   
 			   
 			    $('#actionCodes').change(function(event) {
 			      var actionCodes = $("select#actionCodes").val();
 			      var budgetCodeMap = "";
 			      var dummyMsg="";
 			      $.getJSON('ajaxAction?method=getBudgetCodes', {
 			        actionCodeId : actionCodes
 			      }, function(jsonResponse) { 
 			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
 			        var select = $('#budgetCodes');
 			        
 			       /* other field clear*/
 			        $("#allocatedAmount").val('0.00');
 			        $("#utilizedAmount").val('0.00');
 			      	$("#balanceAmount").val('0.00');
 			        $("#budgetDescr").val("");
 			        
 			        select.find('option').remove();
 			        $.each(jsonResponse.budgetCodeMap, function(key, value) {
 			          $('<option>').val(key).text(value).appendTo(select);
 			        });  
 			      });
 			      });
 			   
 		  	   $('#budgetCodes').change(function(event) {
	  			      var budgetCodes= $("select#budgetCodes").val();
	  			      var provisionYear=$("select#provisionYear").val();
	  			      var dummyMsg="";
	  			      $.getJSON('ajaxAction?method=getBudgetData', {
	  			    	budgetCodeId: budgetCodes, // property : id
	  			    	year:provisionYear
	  			      }, function(jsonResponse) {// alert(jsonResponse.fullAmount);
	  			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
	  			         var allocatedAmount = (jsonResponse.allocatedAmount).toLocaleString('en-US', {minimumFractionDigits: 2});
	  			         var utilizedAmount = (jsonResponse.utilizedAmount).toLocaleString('en-US', {minimumFractionDigits: 2});
	  			         var balanceAmount = (jsonResponse.balanceAmount).toLocaleString('en-US', {minimumFractionDigits: 2});
		
	  			        $("#allocatedAmount").val(allocatedAmount);
	  			        $("#utilizedAmount").val(utilizedAmount);
	  			      	$("#balanceAmount").val(balanceAmount);
	  			        $("#budgetDescr").val(jsonResponse.description);
	  			      });
	  		    });
 		  	   
 		  	   $('#provisionYear').change(function(event) { 
	  			      var budgetCodes= $("select#budgetCodes").val();
	  			      var provisionYear=$("select#provisionYear").val();
	  			      var dummyMsg="";
	  			      $.getJSON('ajaxAction?method=getBudgetData', {
	  			    	budgetCodeId: budgetCodes, // property : id
	  			    	year:provisionYear
	  			      }, function(jsonResponse) {// alert(jsonResponse.fullAmount);
	  			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
	  			        
	  			         var allocatedAmount = (jsonResponse.allocatedAmount).toLocaleString('en-US', {minimumFractionDigits: 2});
	  			         var utilizedAmount = (jsonResponse.utilizedAmount).toLocaleString('en-US', {minimumFractionDigits: 2});
	  			         var balanceAmount = (jsonResponse.balanceAmount).toLocaleString('en-US', {minimumFractionDigits: 2});
	  			         
	  			        $("#allocatedAmount").val(allocatedAmount);
	  			        $("#utilizedAmount").val(utilizedAmount);
	  			      	$("#balanceAmount").val(balanceAmount);
	  			        $("#budgetDescr").val(jsonResponse.description);
	  			      });
	  		    });
 		  	   
 		  	/*   $("#stakeholderId").change(function(event) {
	    		   //this line will prevent the default form submission on click of link
	    		   event.preventDefault();
	    		   
	  			   var stakeholderId = $("select#stakeholderId").val();

	    		   //fire the ajax request on this object referring to the clicked anchor link element
	    		   $.ajax({
	    		   stakeholderId : stakeholderId,

	    		   url: "getStkSourceDocAction.action?stakeholderId="+stakeholderId,
	    		   cache: false
	    		   }).done(function( html ) {
	    		   		$("#sourceDocDiv").html(html);
	    		   });
	    		}); */
 			   
 			});
	
 			
 		/* 	$('#exampleModalCenter').on('shown.bs.modal', function () {
 		 	  	 $('#myInput').focus()
 		 	}) */
 		 	
 		 	/*  $(document).ready(function() {
			    $("#exampleModalCenter").modal();
			  }); */
 		 	  	
 		 	  	
 	 </script>
 	 
 	 <script>
 	    $('#exampleModalCenter').on('shown.bs.modal', function () {
 		  /* $('#myInput').trigger('focus') */
 		})
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
   <s:set var="prStatus"><s:property value='prfStatus'/></s:set> 
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
						      	<input type="submit" name="" value="Send for Approval" formaction="sendPRApprovalAction">
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
		
	
	<div class="cardContainer">
	 	<form> 
			
			<div>
				<s:include value="PRSupplier.jsp"/>
			</div>
    
		</form> 
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
		        	<input type="text" name=rejectReason id=""  class="form-control" placeholder="Enter Reject Reason">
		      </div>
		      <div class="modal-footer">
		      	
			        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			        <input type="submit" class="btn btn-primary" formaction="rejectPRAction" value="Submit" maxlength="200px">
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
		        	<input type="text" name=cancelReason id=""  class="form-control" placeholder="Enter Cancel Reason" maxlength="200px">
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