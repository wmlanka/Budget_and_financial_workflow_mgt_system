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
<title>Budget Code</title>
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

	<div class="gridContainer">
		
		<table style="width: 100%">
			<tr> 
				<td class="img-budget"><h3>Budget Code</h3></td>
				<td style="text-align: right;"> 
					<h4><a href="addBudgetCodeAction?command=add">
  					<img border="0" src="assets/images/addNew.png" width="45" height="45"/> Add New</a>
  					</h4> 
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
		<%-- <%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearMessages();% --%>

		<display:table export="true" id="actTable" name="list" pagesize="10" requestURI="" class="table table-bordered" uid="row">
		<%-- 	<display:column value="<%=stkTable_rowNum%>"/> --%>
			<display:column><c:out value="${row_rowNum}"/></display:column>
			<display:column property="budgetTypeStr" title="Budget Type" sortable="true"></display:column>
			<display:column property="actionCodeDescr" title="Action Code" sortable="true"></display:column>
			<display:column property="budgetCode" title="Budget Code" sortable="true"></display:column>
			<display:column property="description" title="Description" sortable="true"></display:column>
			<display:column property="status" title="Status" sortable="true"></display:column>			
			<display:column property="budgetCodeId" class="hidden" headerClass="hidden" media="html"></display:column>
			
			<display:column media="html">			
				<s:url var="editUrl" action="editBudgetCodeAction?command=update">
			  		<s:param name="budgetCodeId" value="%{#attr.row.budgetCodeId}" />
				</s:url>
				
				<s:url var="deleteUrl" action="deleteBudgetCodeAction">
			  		<s:param name="budgetCodeId" value="%{#attr.row.budgetCodeId}" />
				</s:url>
				
			 	<s:a href="%{#editUrl}">Edit</s:a>
			 	<s:a href="%{#deleteUrl}" onclick="return confirm('Are you sure you want to delete ?');">Delete</s:a>
			</display:column>
			
			<display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.excel" value="false" />
			<display:setProperty name="export.csv" value="false" />
			<display:setProperty name="export.xml" value="false" />

		</display:table>
	</div>
</body>
</html>