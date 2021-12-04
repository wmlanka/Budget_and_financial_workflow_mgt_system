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
	<sj:head/>
	
	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 
 	 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 	 
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script>
 	 
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
	  			        $("#allocatedAmount").val(jsonResponse.allocatedAmount);
	  			        $("#utilizedAmount").val(jsonResponse.utilizedAmount);
	  			      	$("#balanceAmount").val(jsonResponse.balanceAmount);
	  			        $("#budgetDescr").val(jsonResponse.description);
	  			      });
	  		    });
 		  	   
 		  	  $("#stakeholderId").change(function(event) {
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
 /* 		.dojoTab { 
			position : relative; 
			float : left; 
			padding-left : 9px; 
			border-bottom : 1px solid #6290d2; 
			background : 
			url(/struts2-showcase/struts/dojo/src/widget/templates/images/tab_left.gif) 
			no-repeat left top; 
			cursor: pointer; 
			white-space: nowrap; 
			z-index: 3; 
			background-color: blue;
			} 
		.dojoTab div { 
			display : block; 
			padding : 4px 15px 4px 6px; 
			background : 
			url(/struts2-showcase/struts/dojo/src/widget/templates/images/tab_top_right.gif) 
			no-repeat right top; 
			color : #333; 
			font-size : 90%; 
			background-color: red;
		}  */
 	 </style>
</head>
<body>

	<div class="important img-user" id="instructions" style="width: 80%;text-align: right">
			   <s:if test="command.equals('add')">
			   		Payment Request Creation
			   </s:if>
			   <s:if test="!command.equals('add')">
			   		Edit Source Document
			   </s:if>
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
		<s:set var="st"><s:property value='selectTab'/></s:set> 
		
		<sj:tabbedpanel id="test" selectedTab="5" useSelectedTabCookie="false" show="true" hide="'fade'" collapsible="true" sortable="true">
		
		  <sj:tab id="tab1" label="General Info" cssStyle="margin:10px;" >
				<s:include value="PRGeneralInfo.jsp"/>
		  </sj:tab>
		  
		  <!-- ===================================================================== -->
		
		  <sj:tab id="tab2" label="Supplier/Benificiary" cssStyle="margin:10px;" >
				 <s:include value="PRSupplier.jsp"/>
		  </sj:tab>
		
		 <!-- ===================================================================== -->  
		  <sj:tab id="tab3" label="Payable/Deduction"  cssStyle="margin:20px;" href="callPRTabAction3">
 		    	<s:include value="PRFAmounts.jsp"/>
		  </sj:tab>
		  
		 <!-- ===================================================================== --> 
		   <sj:tab id="tab4" label="Entries" cssStyle="margin:20px;">
		   </sj:tab>
		
	<%-- 	  <sd:div id="four" label="Tab 4" theme="ajax">
		    java4s.com  --> 4th Tab<br><br>
		  </sd:div> --%>
		
		 </sj:tabbedpanel>
 	</div>
</body>
</html>