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
<title>PR supplier</title>

	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	 
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script>

 	  	 <script type="text/javascript">
 			$(document).ready(function() { 
 		  	   
 		   	  $("#stakeholderId").change(function(event) {
	    		   //this line will prevent the default form submission on click of link
	    		   event.preventDefault();

	    		   //fire the ajax request on this object referring to the clicked anchor link element
	    		   $.ajax({
	    		   stakeholderId : stakeholderId,

	    		   url: "getStkSourceDocAction.action?stakeholderId="+stakeholderId+"&command=save2",
	    		   cache: false
	    		   }).done(function( html ) {
	    		   		$("#sourceDocDiv").html(html);
	    		   });
	    		}); 
 			   
 			});
	
 	 </script>
 	 
</head>
<body>


		    <s:form >
			
			<!-- =========================GENERAL INFO============================ -->
			<s:if test='%{#disableUI=="Y"}'>
				<div class="card" style=" pointer-events: none;">
			</s:if>		   
			<s:else>
				<div class="card">
			</s:else>
					  <div class="card-body">
					  		<h6 class="card-title"><b>General Info</b></h6>
					  		<s:include value="PRGeneralInfo.jsp"/>
					  </div>
			  	</div>
			  	<br>
			  	
			  	
		 	<!-- ==========================STAKEHOLDER - INVOICE SEARCH=========================== -->
			  	
			<s:if test='%{#disableUI=="Y"}'>
				<div class="card" style=" pointer-events: none;">
			</s:if>		   
			<s:else>
				<div class="card">
			</s:else>
					  <div class="card-body">
					  		<h6 class="card-title"><b>Supplier/Beneficiary Selection</b></h6>

					    	<div>
						    	<div class="form-inline"  style="margin-bottom: 10px;">
								  <div class="form-group ">
								    <label for="">Supplier/Beneficiary<font class="myErrorMessage"> &nbsp; * </font>&nbsp;</label>
								    <div class="col-sm-3">
										  <s:set var="sId"><s:property value='stakeholderId'/></s:set>
								       	  <select name="stakeholderId" class="form-control" id="">
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
								  </div>	
								  
								  <div class="form-group ">
								    <label for=""><s:text name="label.paymentMode"/><font class="myErrorMessage"> &nbsp; * </font>&nbsp;</label>
								    <div class="col-sm-2">
										 <select name="paymentMode" class="form-control">
												    <option value="PAY_ORDER" <s:if test="paymentMode == 'PAY_ORDER'">selected="selected"</s:if>>Pay Order</option>
												    <option value="CUSTOMER_ACCOUNT" <s:if test="paymentMode == 'CUSTOMER_ACCOUNT'">selected="selected"</s:if>>Customer Account</option>
										 </select>
									</div>
								  </div>
								   
								  <div class="form-group ">
								  	<input type="submit" name="" value="Search" formaction="searchStkSourceDocAction">
								  </div> 
								  
							 	</div>
						 	<div>
							 	<font class="myErrorMessage"><s:property value="fieldErrors['stakeholder'][0]" /> </font>
							</div>
							<div>
							 	<font class="myErrorMessage"><s:property value="fieldErrors['invoice'][0]" /> </font>
							</div>
							<!-- <input type="hidden" name="command" value="save2"> -->
					   	</div>
					  </div>
			 	</div>
			 <!-- ===================================================== -->
			 		   	
		   		<div id="sourceDocDiv">
 		    		<s:include value="StakeholderSourceDoc.jsp"/>
			    </div>
		   	
					
		    </s:form>
</body>
</html>