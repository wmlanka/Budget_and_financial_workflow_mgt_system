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

 	  	 <script type="text/javascript">
 			$(document).ready(function() {  		  	   
 	
 			    $("#addB").click(function(e) { //alert("2");
 	    		   //this line will prevent the default form submission on click of link
 	    		   e.preventDefault();
 	    		   
 	    		   var checksId = $("#checksId").val();
 	    		   
 	    		   //fire the ajax request on this object referring to the clicked anchor link element
 	    		   $.ajax({
 	    		   url: "addStkSourceDocAction.action?checksId="+checksId,
 	    		   cache: false
 	    		   }).done(function( html ) { alert(html);
 	    		   		$("#amountTotalsDiv").html(html);
 	    		   });
 	    		 });
 			    
 			/*    $("addB").click(function(e) {
	  			      var dummyMsg="";
	  			      $.getJSON('ajaxAction?method=getPRAmountSummary', {
	  			      }, function(jsonResponse) {// alert(jsonResponse.fullAmount);
	  			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
	  			        $("#payableAmount").val(jsonResponse.payableAmount);
	  			        $("#utilizedAmount").val(jsonResponse.utilizedAmount);
	  			      	$("#payableOther").val(jsonResponse.payableOther);
	  			        $("#payableTotal").val(jsonResponse.payableTotal);
	  			      });
	  		    }); */
 			   
 			});
	
 	 </script>
 	 
</head>
<body>
			
		<s:if test="%{tempEntryList.isEmpty()}">
			 	<table class="table table-bordered">
				    <tr>
				        <th></th>
				        <th>Dr/Cr</th>
				        <th>Ledger Account No</th>
				        <th>Stakeholder Account</th>
				        <th>Amount</th>
				        <th>Select</th>
				    </tr>
				    <tr>
				        <td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				    </tr>
			    </table>
	</s:if> 
			
<!-- 	<form action="">addStkSourceDocAction
 -->			<div id="">
			 	<display:table export="false" id="" name="tempEntryList" requestURI="" class="table table-bordered" uid="row">
					<display:column><c:out value="${row_rowNum}"/></display:column>
					<display:column property="drCr" title="Dr/Cr"></display:column>
					<display:column property="ledgerAccountNo" title="Ledger Account No"></display:column>
					<display:column property="stkAccountNo" title="Customer Account"></display:column>
					<display:column property="amount" title="Amount" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
					<display:column title="Select">
						<input type="checkbox" name="checksEntries" value="${row.tempPREntryId}" id="checksEntries">
					</display:column>
					<%-- <display:column property="tempPREntryId" class="hidden" headerClass="hidden" media="html"/> --%>
					
					<%-- <display:column> --%>
					
						<%-- <s:url var="deleteUrl" action="delTempPREntryAction">
					  		<s:param name="tempPREntryId" value="%{#attr.row.tempPREntryId}" />
					  		<s:param name="tempPREntryId" value="%{#attr.row.tempPREntryId}" />
						</s:url>
						<s:a href="%{#deleteUrl}" onclick="">Delete</s:a>
				
						<input type="submit" name="" value="Delete" formaction="delTempPREntryAction"> --%>
					<%-- </display:column> --%>
				</display:table>
		   
		</div>

  
<!-- 	</form>
 --></body>
</html>