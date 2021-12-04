<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%-- <%@ taglib uri="/struts-dojo-tags" prefix="sd" %> --%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%-- <%@ taglib prefix="sj" uri="/struts-jquery-tags"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Invoices</title>

 	  	 <script type="text/javascript">
 			$(document).ready(function() {  		  	   
 	
 			    $("#addB").click(function(e) { alert("2");
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

 	<s:if test="%{sourceDocList.isEmpty()}">
			 	<table class="table table-bordered">
				    <tr>
				        <th></th>
				        <th>Document Type</th>
				        <th>Reference No</th>
				        <th>Date</th>
				        <th>Net Amount</th>
				        <th>Vat Amount</th>
				        <th>Gross Amount</th>
				    </tr>
				    <tr>
				        <td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				    </tr>
			    </table>
			</s:if>
			

			
	 <form action="addStkSourceDocAction">
			<div id="">
			 	<display:table export="false" id="" name="sourceDocList" requestURI="" class="table table-bordered" uid="row">
					<display:column><c:out value="${row_rowNum}"/></display:column>
					<display:column property="documentType" title="Document Type"></display:column>
					<display:column property="referenceNo" title="Reference No"></display:column>
					<display:column property="documentDateStr" title="Date"></display:column>
					<display:column property="netAmount" title="Net Amount" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
					<display:column property="vatAmount" title="Vat Amount" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
					<display:column property="grossAmount" title="Gross Amount" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
					
					
					<display:column title="Select">
						<input type="checkbox" name="checksId" value="${row.sourceDocumentId}" id="checksId" ${row.select}>
					</display:column>
				
				</display:table>
				
				<%-- <div align="right" class="col-sm-11">
				
					<input type="hidden" name="command" value="save2">
					<input type="submit" name="" value="<s:text name="button.back"/>2" formaction="payRequestAction">
		    		<input type="submit" name="" value="Next" formaction="callPRTabAction2">
		   		</div> --%>
				
		<%-- 	<s:if test="%{!sourceDocList.isEmpty()}">
				<div align="right" class="col-sm-11">
		   		     <input type="submit" name="" value="<s:text name="button.add"/>">
		   		</div>
		   	</s:if>
		   	<br> --%>
				
<%-- 		  <div class="form-row">
			    <div class="form-group col-md-6">
			      <label for="">Payable Amount</label>
			      <input type="text" class="form-control" id="payableAmount" placeholder="" name="payableAmount" value="<s:property value="payableAmount"/>">
			    </div>
			    <div class="form-group col-md-6">
			      <label for="inputPassword4">Withholding Tax</label>
			      <input type="text" class="form-control" id="" placeholder="" name="withholdingTax">
			    </div>
		   </div>
		   
		   	<div class="form-row">
			    <div class="form-group col-md-6">
			      <label for="">VAT Amount</label>
			      <input type="text" class="form-control" id="" placeholder="" name="vatAmount">
			    </div>
			    <div class="form-group col-md-6">
			      <label for="inputPassword4">Retained Amount</label>
			      <input type="text" class="form-control" id="" placeholder="" name="retainedAmount">
			    </div>
		   </div>
		   
		    <div class="form-row">
			    <div class="form-group col-md-6">
			      <label for="">Payable Other</label>
			      <input type="text" class="form-control" id="" placeholder="" name="payableOther">
			    </div>
			    <div class="form-group col-md-6">
			      <label for="inputPassword4">Deduction Other</label>
			      <input type="text" class="form-control" id="" placeholder="" name="deductOther">
			    </div>
		   </div>
		   
		   <div class="form-row">
			    <div class="form-group col-md-6">
			      <label for="">Payable Total</label>
			      <input type="text" class="form-control" id="payableTotal" placeholder="" readonly="readonly" name="payableTotal"  value="<s:property value="payableTotal"/>">
			    </div>
			    <div class="form-group col-md-6">
			      <label for="inputPassword4">Deduction Total</label>
			      <input type="text" class="form-control" id="" placeholder="" readonly="readonly" name="deductTotal">
			    </div>
		   </div> --%>
		   
		</div>
		
		<div id="buttons" align="center">
			     <table style="width: 90%;">
			     	<tr>
			     		<td align="left">
			     			<s:if test="!command.equals('add')">
						   		<!-- <a href="sourceDocAction" class="back-link">Go Back</a> -->
						   		<input type="submit" name="" value="Go Back" formaction="searchPRAction">
						   </s:if>
			     		</td>
			     		 <!-- <td align="left">
			     		    <input type="submit" name="" value="Go Back" formaction="goBackPREntryAction">
			     		 </td> -->
			     		 <td  class="tdRight">
			     		 	<input type="submit" name="" value="Next" formaction="callPRTabAction2">
							<%--<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetSourceDocAction">--%>		
 			      		</td>
			     	</tr>
			     </table>
		 </div> 
	   	

  
  
	 </form> 
</body>
</html>