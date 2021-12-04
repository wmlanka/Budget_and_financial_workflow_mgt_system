<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Source Document</title>
 <!-- <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->

 <link rel="stylesheet" href="bootstraps/css/bootstrap2.min.css">
 <link rel="stylesheet" href="assets/css/commonStyle.css">
 <link rel="stylesheet" href="assets/css/myStyle.css">
 
 
 <script type="text/javascript" src="bootstraps/js/jquery.min.js"></script>  
 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script>  

	<script type="text/javascript">

	</script>

</head>
<body>

   <s:set var="uType"><s:property value='userSession.userType'/></s:set>
   <s:set var="depId"><s:property value='userSession.departmentId'/></s:set>
   
	<div class="gridContainer">
		
		<table style="width: 100%">
			<tr> 
				<td class="img-invoice"><h3>Source Document</h3></td>
				<td class="tdRight"> 
					<s:if test="%{#uType=='SYSTEM_OPERATOR' || #uType=='SYSTEM_ADMIN'}">
						<h4><a href="addSourceDocAction?command=add">
	  					<img border="0" src="assets/images/addNew.png" width="45" height="45"/> Add New</a>
	  					</h4> 
  					</s:if>
				</td>
			</tr>
		</table>
		
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
		
		<display:table export="true" id="" name="listDTO" pagesize="10" requestURI="" class="table table-bordered" uid="row">
			<display:column><c:out value="${row_rowNum}"/></display:column>
			<display:column property="documentType" title="Document Type" sortable="true"></display:column>
			<display:column property="referenceNo" title="Ref No" sortable="true"></display:column>
			<display:column property="stakeholderName" title="Stakeholder" sortable="true"></display:column>
			<display:column property="documentDateStr" title="Date" sortable="true"></display:column>
			<display:column property="netAmount" title="Net Amount" sortable="true" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
			<display:column property="vatAmount" title="VAT Amount" sortable="true" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
			<display:column property="grossAmount" title="Gross Amount" sortable="true" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
			<display:column property="isPRUsed" title="Is Paid" sortable="true"></display:column>
			<display:column property="sourceDocumentId" class="hidden" headerClass="hidden" media="html"></display:column>
			
			<s:if test="%{#uType=='SYSTEM_OPERATOR' || #uType=='SYSTEM_ADMIN'}">
				<display:column media="html">			
					<s:url var="editUrl" action="editSourceDocAction?command=update">
				  		<s:param name="sourceDocumentId" value="%{#attr.row.sourceDocumentId}" />
					</s:url>
					
					<s:url var="deleteUrl" action="deleteSourceDocAction">
				  		<s:param name="sourceDocumentId" value="%{#attr.row.sourceDocumentId}" />
					</s:url>
					
				 	<s:a href="%{#editUrl}">Edit</s:a>
				 	<s:a href="%{#deleteUrl}" onclick="return confirm('Are you sure you want to delete ?');">Delete</s:a>
				</display:column>
			</s:if>
					
		    <display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.excel" value="false" />
			<display:setProperty name="export.csv" value="false" />
			<display:setProperty name="export.xml" value="false" />
		</display:table>
		
	</div>
</body>
</html>