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
	<title>Source Document</title>
 	
 	<sd:head />
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	 
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	 
 	 <style type="text/css">
 	 .errorMessage {display: none;}
 	 </style>  
 	 
 	<script>
	 // Add the following code if you want the name of the file appear on select
	/*  $(".custom-file-input").on("change", function() {
	   var fileName = $(this).val().split("\\").pop();
	   $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
	 }); */		   
	 
	    $(document).ready(function() {
	    	   $('#approvalTypes').change(function(event) {
	  			      var approvalTypes = $("select#approvalTypes").val();
	  			      var approvalDocMap = "";
	  			      var dummyMsg="";
	  			      $.getJSON('ajaxAction?method=getAvailableApprovalDocs', {
	  			    	approvalType : approvalTypes
	  			      }, function(jsonResponse) { 
	  			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
	  			        $("#fullAmount").val("0.00");
	  			      	$("#balanceAmount").val("0.00");
	  			      	
	  			        var select = $('#approvalDocs');
	  			        select.find('option').remove();
	  			        $.each(jsonResponse.approvalDocMap, function(key, value) {
	  			          $('<option>').val(key).text(value).appendTo(select);
	  			        });  
	  			      });
	  			      });
	    	   
	    	   $('#approvalDocs').change(function(event) {
	  			      var approvalDocs= $("select#approvalDocs").val();
	  			      var fullAmount = "";
	  			      var balanceAmount = "";
	  			      var dummyMsg="";
	  			      
	  			    if(approvalDocs=='0'){
	  			    	   $("#fullAmount").val("0.00");
	  	  			       $("#balanceAmount").val("0.00");
	  			    }else{
	  			    
		  			      $.getJSON('ajaxAction?method=getApprovalDocsData', {
		  			    	approvalDocId : approvalDocs // property : id
		  			      }, function(jsonResponse) {// alert(jsonResponse.fullAmount);
		  			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
		  			        
		  			        fullAmount = jsonResponse.fullAmount;
		  			        fullAmount = fullAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
		  			        
		  			        balanceAmount = jsonResponse.balanceAmount;
		  			        balanceAmount = balanceAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
				  		
		  			        $("#fullAmount").val(fullAmount);
		  			      	$("#balanceAmount").val(balanceAmount);
		  			      });
	  			    }
	  			});
	 			   
	    	    $("a.linkAdd").click(function(e) {
	    		   //this line will prevent the default form submission on click of link
	    		   e.preventDefault();
	    		   
	  			   var approvalTypes = $("select#approvalTypes").val();
	  			   var approvalDocs= $("select#approvalDocs").val();
	  			   var amount= $("#appliedAmount").val();
	  			   var balanceAmount = $("#balanceAmount").val();
	  			   var error = false;
	  			   
	  			   if (amount.indexOf(',') > -1) {
	  				 amount = $("#appliedAmount").val().replace(/,/g, '');  
	    	       }
	  			   if (balanceAmount.indexOf(',') > -1) {
	  				 balanceAmount = $("#balanceAmount").val().replace(/,/g, '');  
	    	       }
	  			 
					$("#approvalTypeErr").empty();
					$("#approvalDocErr").empty();
					$("#appliedAmountErr").empty();

 				   //alert(amount + " "+ balanceAmount +" "+approvalTypes+" "+approvalDocs);
	  			   if(approvalTypes=="0"){ 
						$("#approvalTypeErr").html("Approval Type is required.");
						error = true;
				   }
	  			   if(approvalDocs=="0"){
						$("#approvalDocErr").html("Approval No is required.");
						error = true;
				   }
		  		   if (isNaN(amount)) {
						$("#appliedAmountErr").html("Not a valid amount.");		  
		  		        error = true;
		  		   } else if (parseFloat(amount) > parseFloat(balanceAmount)) {
						$("#appliedAmountErr").html("Applied amount must be less than or equal to Balance Amount.");
		  		        error = true;
		  		   }else if (parseFloat(amount) == 0.0) {
						$("#appliedAmountErr").html("Applied amount must be greater than 0.0.");
		  		        error = true;
		  		   }
		  		   
		  		   if(error)
		  			   return;

	    		   //fire the ajax request on this object referring to the clicked anchor link element
	    		   $.ajax({
	  			   approvalType : approvalTypes  ,
	    		   approvalDocId :approvalDocs,
	    		   //url: "addSourceDocApprovalAction.action",

	    		   url: "addSourceDocApprovalAction.action?approvalType="+approvalTypes+"&approvalDocId="+approvalDocs+"&appliedAmount="+amount,
	    		   cache: false
	    		   }).done(function( html ) { 
	    		   		$("#apporvalsDiv").html(html);
	    		   });
	    		 });
	    	    
	    	    /*===========================*/
	    	    
	    	    $("#netAmount").change(function(event) {
	    	    	var netAmount = $("#netAmount").val();
	    	    	var vatAmount = $("#vatAmount").val();
		  			var otherTaxAmount = $("#otherTaxAmount").val();
		  			var vatPercentage = $("#vatPercentage").val();
		  			var grossAmount = 0.00;
		  			
		  			$("#netAmountErr").empty();
		  			$("#vatPercentErr").empty();
	  		    	$("#othTaxErr").empty();
		  			
	    	    	if (netAmount.indexOf(',') > -1) {
	    	    	   netAmount = $("#netAmount").val().replace(/,/g, '');  
	    	    	}
	    	     	if (vatAmount.indexOf(',') > -1) {
	    	    		vatAmount = $("#vatAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (otherTaxAmount.indexOf(',') > -1) {
	    	    		otherTaxAmount = $("#otherTaxAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (vatPercentage.indexOf(',') > -1) {
	    	    		vatPercentage = $("#vatPercentage").val().replace(/,/g, '');  
		    	    }

		  			var error = false;
		  			var grossAmount = 0.00;
					$("#grossAmount").val(0.00);

		  		    if (isNaN(netAmount)) {
						$("#netAmountErr").html("Not a valid amount.");	
						netAmount = 0.00;
						vatAmount = 0.00;
						vatPercentage = 0.00;
						//$("#netAmount").val(0.00);
				  		//grossAmount = parseFloat(vatAmount) + parseFloat(otherTaxAmount) + parseFloat(netAmount);
						//$("#grossAmount").val(grossAmount);
		  		        error = true;
		  		    }else{
		  		    	$("#netAmountErr").empty();
		  		    	netAmount = parseFloat(netAmount);
		  		    	
		  		    	 //vat adjustment
			  		    vatPercentage = parseFloat(vatPercentage); 
			  		    vatAmount = netAmount * (vatPercentage/100);
		  		    }
			  		   
			  		grossAmount = parseFloat(vatAmount) + parseFloat(otherTaxAmount) + parseFloat(netAmount);
			  		grossAmount = grossAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		vatAmount = vatAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		vatPercentage = vatPercentage.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		$("#grossAmount").val(grossAmount);
			  		$("#netAmount").val(netAmount);
			  		$("#vatAmount").val(vatAmount);
			  		$("#vatPercentage").val(vatPercentage);
		    	});
	    	    
	    	    /*===========================*/
	    	    
	    		$("#vatPercentage").change(function(event) {
	    	    	var netAmount = $("#netAmount").val();
	    	    	var vatAmount = $("#vatAmount").val();
		  			var otherTaxAmount = $("#otherTaxAmount").val();
		  			var vatPercentage = $("#vatPercentage").val(); // comes without commas
		  			var grossAmount = 0.00;
		  			
		  			$("#netAmountErr").empty();
		  			$("#vatPercentErr").empty();
	  		    	$("#othTaxErr").empty();
		  			
	    	    	if (netAmount.indexOf(',') > -1) {
	    	    	   netAmount = $("#netAmount").val().replace(/,/g, '');  
	    	    	}
	    	     	if (vatAmount.indexOf(',') > -1) {
	    	    		vatAmount = $("#vatAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (otherTaxAmount.indexOf(',') > -1) {
	    	    		otherTaxAmount = $("#otherTaxAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (otherTaxAmount.indexOf(',') > -1) {
	    	    		vatPercentage = $("#vatPercentage").val().replace(/,/g, '');  
		    	    }

		  			var error = false;
		  			var grossAmount = 0.00;
					$("#grossAmount").val(0.00);

		  		    if (isNaN(vatPercentage)) {
						$("#vatPercentErr").html("Not a valid value for VAT percentage.");	
						netAmount = 0.00;
						vatAmount = 0.00;
						vatPercentage = 0.00;
						//$("#netAmount").val(0.00);
				  		//grossAmount = parseFloat(vatAmount) + parseFloat(otherTaxAmount) + parseFloat(netAmount);
						//$("#grossAmount").val(grossAmount);
		  		        error = true;
		  		    }else{
		  		    	$("#vatPercentErr").empty();
		  		    	vatPercentage = parseFloat(vatPercentage);
		  		    	netAmount = parseFloat(netAmount);
		  		    }
			  		   
		  		    vatAmount = netAmount * (vatPercentage/100);
			  		grossAmount = parseFloat(vatAmount) + parseFloat(otherTaxAmount) + parseFloat(netAmount);
		  		    
			  		grossAmount = grossAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});//not change here
			  		vatAmount = vatAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		vatPercentage = vatPercentage.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		
			  		$("#grossAmount").val(grossAmount);
			  		$("#netAmount").val(netAmount);
			  		$("#vatAmount").val(vatAmount);
			  		$("#vatPercentage").val(vatPercentage);
		    	});
	    	    
	    		/*===========================*/
	    		
	    		$("#otherTaxAmount").change(function(event) {
	    	    	var netAmount = $("#netAmount").val();
	    	    	var vatAmount = $("#vatAmount").val();
		  			var otherTaxAmount = $("#otherTaxAmount").val();
		  			var vatPercentage = $("#vatPercentage").val();
		  			var grossAmount = 0.00;
		  			
		  			$("#netAmountErr").empty();
		  			$("#vatPercentErr").empty();
	  		    	$("#othTaxErr").empty();
		  			
	    	    	if (netAmount.indexOf(',') > -1) {
	    	    	   netAmount = $("#netAmount").val().replace(/,/g, '');  
	    	    	}
	    	     	if (vatAmount.indexOf(',') > -1) {
	    	    		vatAmount = $("#vatAmount").val().replace(/,/g, '');  
		    	    }
	    	    	if (otherTaxAmount.indexOf(',') > -1) {
	    	    		otherTaxAmount = $("#otherTaxAmount").val().replace(/,/g, '');  
		    	    }

		  			var error = false;
		  			var grossAmount = 0.00;
					$("#grossAmount").val(0.00);

		  		    if (isNaN(otherTaxAmount)) {
						$("#othTaxErr").html("Not a valid amount for other Tax.");	
						otherTaxAmount = 0.00;
						//$("#netAmount").val(0.00);
				  		//grossAmount = parseFloat(vatAmount) + parseFloat(otherTaxAmount) + parseFloat(netAmount);
						//$("#grossAmount").val(grossAmount);
		  		        error = true;
		  		    }else{
		  		    	$("#othTaxErr").empty();
		  		    	otherTaxAmount = parseFloat(otherTaxAmount);
		  		    	netAmount = parseFloat(netAmount);
		  		    }
			  		   
			  		grossAmount = parseFloat(vatAmount) + parseFloat(otherTaxAmount) + parseFloat(netAmount);//parseFloat need before calculation
		  		    
			  		otherTaxAmount = otherTaxAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		grossAmount = grossAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		netAmount = netAmount.toLocaleString('en-US', {minimumFractionDigits: 2});//not change here
			  		vatAmount = vatAmount.toLocaleString('en-US', {minimumFractionDigits: 2});//not change here
			  		vatPercentage = vatPercentage.toLocaleString('en-US', {minimumFractionDigits: 2});//not change here
			  		
			  		$("#grossAmount").val(grossAmount);
			  		$("#netAmount").val(netAmount);
			  		$("#vatAmount").val(vatAmount);
			  		$("#vatPercentage").val(vatPercentage);
			  		$("#otherTaxAmount").val(otherTaxAmount);
		    	});
	    	    
	    	    /*===========================*/
	    	    $("#delBtn").click(function(e) {
		    		   //this line will prevent the default form submission on click of link
		    		   e.preventDefault();
		    		   //fire the ajax request on this object referring to the clicked anchor link element
		    		   $.ajax({

		    		   url: "deleteSourceApprovalAction.action?",
		    		   cache: false
		    		   }).done(function( html ) {
		    		   		$("#apporvalsDiv").html(html);
		    		   });
		    	});
	    	    
	    	    /*===========================*/
	    	    $("#appliedAmount").change(function(event) {
	    	    	var appliedAmount = $("#appliedAmount").val();
	  		    	$("#appliedAmountErr").empty();
		  			
	    	    	if (appliedAmount.indexOf(',') > -1) {
	    	    		appliedAmount = $("#appliedAmount").val().replace(/,/g, '');  
	    	    	}

		  		    if (isNaN(appliedAmount)) {
						$("#appliedAmountErr").html("Not a valid amount.");	
						appliedAmount = 0.00;
		  		        error = true;
		  		    }
		  		    
		  		    appliedAmount = parseFloat(appliedAmount);
			  		appliedAmount = appliedAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
			  		$("#appliedAmount").val(appliedAmount);
		    	});
	    	    
	    	   /*  $(this).scrollTop(0); */
	 	});
	 			
	             
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

<%-- 	<div class="important img-user" id="instructions" style="width: 80%;">
		   <s:if test="command.equals('add')">
		   		Add Source Document
		   </s:if>
		   <s:if test="!command.equals('add')">
		   		Edit Source Document
		   </s:if>
	</div>	 --%>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-invoice">
					 <h5> <br>
					   <s:if test="command.equals('add')">
					   		Add Source Document
					   </s:if>
					   <s:if test="!command.equals('add')">
					   		Edit Source Document
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


	<div class="cardContainer">

  		<form <s:if test="command.equals('add')"> action="addSourceDocAction" </s:if><s:else> action="editSourceDocAction" </s:else> 
  			style="width: 100%;" enctype="multipart/form-data" method="post">
		 
	 	<div class="card">
		  <div class="card-body">	

			  <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="" class="col-sm-3 col-form-label"><s:text name="label.documentType"/><font class="myErrorMessage"> &nbsp; * </font></label>
				    <div class="col-sm-6">
				      	  <s:set var="sType"><s:property value='sourceDocType'/></s:set>
						       <select name="sourceDocType" class="form-control">
		 				       		<option value="">Select Document Type</option>  	
		  			    
							        <s:iterator value="sourceDocTypeList" var="sourceDocTypeList">
							        	<s:if test="%{#sourceDocTypeList.sourceDocumentType.equals(#sType)}">
								        	<option value="<s:property value='sourceDocumentType'/>" selected="selected">
				                					<s:property value='description' />
				            				</option>
							        	</s:if>
							        	<s:else>
							        		<option value="<s:property value='sourceDocumentType'/>" >
				                					<s:property value='description' />
				            				</option>
							        	</s:else>
							      	</s:iterator>
							    </select>
				    </div>
				    <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['sourceDocType'][0]" /></font>	        		        
				    </div>
				  </div>
				  
				  <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="b" class="col-sm-3 col-form-label"><s:text name="label.refNo"/><font class="myErrorMessage"> &nbsp; * </font></label>
				    <div class="col-sm-6" align="left">
				      <input type="text" name="referenceNo" value="<s:property value="referenceNo"/>" class="form-control" id="b" maxlength="100"><!-- class="form-control form-control-sm" -->
				     </div> 
				     <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['referenceNo'][0]" /></font>	        		        
				    </div>
				  </div>
				  
				  <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="" class="col-sm-3 col-form-label">Document Date <font class="myErrorMessage"> &nbsp; * </font></label>
				    <div class="col-sm-6" align="left">
					
 						<s:if test="command.equals('update')"> 
		          			<sd:datetimepicker  name="documentDate" displayFormat="dd-MMM-yyyy" cssStyle="border:1px solid #ced4da;padding:3px;" 
		          			/> 
		          		</s:if>
		          		<s:else>
		          		     <sd:datetimepicker  name="documentDate" displayFormat="dd-MMM-yyyy" value="%{'today'}" cssStyle="border:1px solid #ced4da;padding:3px;"
		          		     id="dDate" onkeypress="disabled"/> 
		          		</s:else>
				     </div> 
				     <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['documentDate'][0]" /></font>	        		        
				    </div>
				  </div>
				  
				  
				  <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="" class="col-sm-3 col-form-label">Supplier/Beneficiary<font class="myErrorMessage"> &nbsp; * </font></label>
				    <div class="col-sm-6">
				      	  <s:set var="sId"><s:property value='stakeholderId'/></s:set>
						       <select name="stakeholderId" class="form-control">
		 				       		<option value="0">Select Stakeholder</option>  	
		  			    
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
				    <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['stakeholderId'][0]" /></font>	        		        
				    </div>
				  </div>
				  
	  			<div class="form-group row" style="margin-bottom: 10px;">
				    <label for="a" class="col-sm-3 col-form-label">
				    		<s:text name="label.description"/> <font class="myErrorMessage"> &nbsp; * </font>
				    </label>
				    <div class="col-sm-6" align="left">
				      		<input type="text" name="description" value="<s:property value="description"/>" class="form-control">
					</div> 
				     <div>
				    	<font class="myErrorMessage"> <s:property value="fieldErrors['description'][0]" /></font>	        		        
				    </div>
				 </div>
				 <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="a" class="col-sm-3 col-form-label">
				    		<s:text name="label.remark"/>
				    </label>
				    <div class="col-sm-6" align="left">
				      		<input type="text" name="remark" value="<s:property value="remark"/>" class="form-control">
					</div> 
				 </div>
				<s:if test="command.equals('update')">
				  <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="a" class="col-sm-3 col-form-label">
				    		<s:text name="label.status"/> <font class="myErrorMessage"> &nbsp; * </font>
				    </label>
				    <div class="col-sm-6" align="left">
							<input type="radio" name="status" value="A" <s:if test='status=="A"'>CHECKED</s:if>> &nbsp;Active 
							&nbsp;
				          	<input type="radio" name="status" value="I" <s:if test='status=="I"'>CHECKED</s:if>>&nbsp;Inactive					
				     </div> 
				     <div>
				    	<font class="myErrorMessage"> <s:property value="fieldErrors['status'][0]" /></font>	        		        
				    </div>
				 </div>
			    </s:if>
	
	 	  </div>
		</div>
	
		<br>
		
		<div class="card">
		  <div class="card-body">	
		  
		   <div class="form-inline" style="margin-bottom: 5px;">
			   	  <div class="form-group">
				    <label for="a" >
				    		<s:text name="label.netAmount"/> <font class="myErrorMessage"> &nbsp; * </font>&nbsp;
				    </label>
				    <input type="text" name="netAmount" id="netAmount" value="<s:property value="getText('{0,number,#,##0.00}',{netAmount})"/>" class="form-control" style="text-align: right;">
				    
<%-- 				    <input type="text" name="netAmount" id="netAmount" value="<s:property value="netAmount"/>" class="form-control">
 --%>				  </div>
				  <div class="form-group">
				    &nbsp;&nbsp;&nbsp;<s:text name="label.grossAmount"/>&nbsp;&nbsp;
				    <input type="text" name="grossAmount" id="grossAmount" value="<s:property value="getText('{0,number,#,##0.00}',{grossAmount})"/>" class="form-control" readonly="readonly" style="text-align: right;">
				  </div>

		   </div>
		   <div class="form-inline" style="margin-bottom: 7px;">
			   	 <div id="netAmountErr" class="myErrorMessage"><s:property value="fieldErrors['netAmount'][0]" /></div>
		   </div>
		   
		   <div class="form-inline">
			   	  <div class="form-group">
				    <label for=""><s:text name="label.vatAmount"/> &nbsp;&nbsp;</label>
				    <input type="text" id="vatAmount" name="vatAmount" value="<s:property value="getText('{0,number,#,##0.00}',{vatAmount})"/>" class="form-control" readonly="readonly" style="text-align: right;">
				  </div>
				  <div class="form-group">
				    <label for=""> &nbsp;<s:text name="label.vatPercentage"/> &nbsp;</label>
				    <input type="text" name="vatPercentage" id="vatPercentage" value="<s:property value="getText('{0,number,#,##0.00}',{vatPercentage})"/>" class="form-control" style="text-align: right;">
				  </div>
				   <div class="form-group">
				    <label for=""> &nbsp;&nbsp;<s:text name="label.otherTaxAmount"/>&nbsp;</label>
				    <input type="text" name="otherTaxAmount" id="otherTaxAmount" value="<s:property value="getText('{0,number,#,##0.00}',{otherTaxAmount})"/>" class="form-control" style="text-align: right;">
				  </div>
		   </div>
		   
		    <%-- <div class="form-inline" style="margin-bottom: 7px;">
		    	<div id="" class="col-sm-4"></div>
			   	 <div id="vatPercentErr" class="col-sm-4">
			   	 	<font class="myErrorMessage"><s:property value="fieldErrors['vatPercentage'][0]" /></font>
			   	 </div>
			   	 <div id="othTaxErr" class="col-sm-1">
			   	 	<font class="myErrorMessage"><s:property value="fieldErrors['otherTaxAmount'][0]" /></font>
			   	 </div>
		    </div> --%>

			 <div class="form-group" style="margin-bottom: 0px;">
			 	  <div class="myErrorMessage" id="vatPercentErr"></div>
				  <div class="myErrorMessage" id="othTaxErr"></div>
			 </div>
		  </div>
		 </div>
		 
		 <br>
		 
		 <div class="card">
		  <div class="card-body">
		  	   <div class="form-group row" style="margin-bottom: 1px;">
				    <label for="a" class="col-sm-3 col-form-label">
				    	<s:if test="command.equals('add')">
				    		<s:text name="label.fileToUpload"/> (PDF/JPG/PNG)
				    	</s:if>
						<s:if test="!command.equals('add')">
						   	Edit Upload File (Pdf/Jpg)
						</s:if>
				    </label>
				    <div class="col-sm-3">
				    		<s:file name="fileUpload" size="40"/>
				    		<div class="myErrorMessage"><s:property value="fieldErrors['fileUpload'][0]" /></div>
					      <%-- <input type="file" class="custom-file-input" id="customFile" name="filename">
					      <label class="custom-file-label" for="customFile"><s:text name="label.chooseFile"/></label> --%>
					 </div>
					 <div class="col-sm-3">
					 	  <s:if test="downloadFileName!=null"> 
						   	<s:url id="fileDownload" namespace="/" action="downloadSourceDocAction" ></s:url>
							 Download file : <s:a href="%{fileDownload}"><s:property value="downloadFileName"/></s:a>
						 </s:if> 
					 </div>
		   		</div>
		  </div>
		 </div>
		 
		 <br>
		 
 		  <div class="card">
		  <div class="card-body">
		  	<p class="card-title"><b>Approval Detail<font class="myErrorMessage"> &nbsp; * <s:property value="fieldErrors['approvalDetail'][0]" /></font></b></p>
		  	
		 	<div class="form-inline" style="margin-bottom: 5px;">
				<div class="form-group">
				    <label for=""><s:text name="label.approvalType"/> &nbsp;&nbsp;&nbsp;</label>
				    <select name="approvalType" id="approvalTypes" class="form-control">
 		   					<option value="0">Select Approval Type</option>		
 		   							      
					      	<s:iterator value="approvalTypeList" var="approvalTypeList">
						       	<s:set var="vTypeList"><s:property/></s:set>
						       			<s:if test="%{#vTypeList.equals(#aType)}">
								        	<option value="<s:property/>" selected="selected">
				                					<s:property/>
				            				</option>
			            				</s:if>
			            				<s:else>
			            					<option value="<s:property/>">
				                					<s:property/>
				            				</option>
			            				</s:else>
							 </s:iterator>							 
				       </select>
				       <font class="myErrorMessage"><s:property value="fieldErrors['approvalType'][0]" /></font>
				  </div>
				  <div class="form-group">
				    <label for=""> &nbsp; <s:text name="label.approvalRefNo"/>&nbsp;&nbsp;</label>
				    
				    <s:set var="aDoc"><s:property value='approvalDocId'/></s:set>            							
				 	<select name="approvalDocId" id="approvalDocs" class="form-control" style="width:190px;">
				       			<option value="0">Select Approval No</option>				      
				     
			        <s:iterator value="approvalDocumentList" var="approvalDocumentList">
				        	<s:if test="%{#approvalDocumentList.approvalDocId==#aDoc}">
					        	<option value="<s:property value='approvalDocId'/>" selected="selected">
	                					<s:property value='referenceNo' />
	            				</option>
				        	</s:if>
				        	<s:else>
				        		<option value="<s:property value='approvalDocId'/>" >
	                					<s:property value='referenceNo' />
	            				</option>
				        	</s:else>
				     </s:iterator>
				     </select>
				     
				  </div>
				  <div class="form-group">
				    <label for=""> &nbsp;&nbsp;<s:text name="label.fullAmount"/>&nbsp;</label>
				    <input type="text" name="fullAmount" id="fullAmount" value="<s:property value="fullAmount"/>" class="form-control" readonly="readonly" style="text-align: right;">
				  </div>
			 </div>
			 
			 <div class="form-inline">
			   	  <div class="form-group">
				    <label for=""><s:text name="label.balanceAmount"/>&nbsp;</label>
				    <input type="text" name="balanceAmount" id="balanceAmount" value="<s:property value="balanceAmount"/>" class="form-control" readonly="readonly" style="text-align: right;">
				  </div>
				  <div class="form-group">
				    <label for=""> &nbsp;<s:text name="label.appliedAmount"/> &nbsp;</label>
				    <input type="text" name="appliedAmount" value="<s:property value="appliedAmount"/>" class="form-control" id="appliedAmount" style="text-align: right;">
				  </div>
				   <div class="col-auto my-1">
						<s:url var="addUrl" action="addSourceDocApproval"/>
			 			<a class="linkAdd" href="%{#addUrl}">Add Approval</a>
			 			<input type="submit" name="" value="Delete" formaction="deleteSourceApprovalAction">
			 			
			 	<%-- 		<s:url action="addSourceDocApproval.action" var="urlTag" >
							 <s:param name="name">mkyong</s:param>
						</s:url>
						<a href="<s:property value="#urlTag" />" >URL Tag Action (via property)</a>
						
						<s:url action="addSourceDocApproval.action" var="urlTag" >
						    <s:param name="age">99</s:param>
						</s:url>
						<s:a href="%{urlTag}">URL Tag Action (via %)</s:a> --%>
						
				   </div>
			 </div>
			 <div class="form-group">
			 	  <div class="myErrorMessage" id="approvalTypeErr"> </div>
				  <div class="myErrorMessage" id="approvalDocErr"> </div>
				  <div class="myErrorMessage" id="appliedAmountErr"> </div>
			 </div>
			
		  	<div id="apporvalsDiv">
		    	<s:include value="SourceDocApprovals.jsp"/>
		  	</div>
		 </div>
		 </div>
    
<%-- 
<s:property value='budgetType'/>
<c:out value="${budgetType}"/>
<s:property value="#bType"/>
<s:property value="#vYear"/>
<s:property value="#aCode"/>
 --%>

   <!--  <br> -->
    <div id="buttons" align="center">
     <table style="width: 90%;">
     	<tr>
     		<td align="left"><a href="sourceDocAction" class="back-link">Go Back</a></td>
     	 	<s:set var="isPRUsed"><s:property value='isPRUsed'/></s:set>
     	 	
     		<td  class="tdRight">
     			<s:if test='%{#isPRUsed!="Y"}'>
     		    	<input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>">
     		    </s:if>
     		    <s:else>
     		    	<%-- <input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>" disabled="disabled"> --%>
     		    </s:else>
     		
     			<s:if test="command.equals('add')">
      	        		<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetSourceDocAction">		
		      	</s:if>
		      	<s:else>
		      	      	<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetSourceDocAction">		
		      	</s:else>
      		</td>
     	</tr>
     </table>
    </div> 	    
    <input type="hidden" name="budgetId" value="<s:property value="budgetId"/>">
    <input type="hidden" name="command" value="save">
    </form>
</div>
</body>
</html>



