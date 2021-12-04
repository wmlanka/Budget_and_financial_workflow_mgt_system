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
<title>Ledger Account</title>
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
				<td class="img-ledgerAccount"><h3>Ledger Account</h3></td>
				<td style="text-align: right;"> 
					<h4><a href="addLedgerAccountAction?command=add">
  					<img border="0" src="assets/images/addNew.png" width="45" height="45"/> Add New</a>
  					</h4> 
				</td>
			</tr>
		</table>
		 
<%--   	 	<s:if test="hasActionMessages()">
			<div class="alert-box success">
				<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
					&times;
				</span>
				<s:actionmessage/>
			</div>
		</s:if>	
		<s:if test="hasActionErrors()">
			<div class="alert-box error">
				<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
					&times;
				</span>
				<s:actionerror/>
			</div>
		</s:if>	 --%>
		
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
		<display:table export="true" id="ledgerTable" name="list" pagesize="10" requestURI="" class="table table-bordered" uid="row">
			<display:column><c:out value="${row_rowNum}"/></display:column>
			<display:column property="accountNo" title="Account No" sortable="true"></display:column>
			<display:column property="accountName" title="Account Name" sortable="true"></display:column>
			<display:column property="categoryStr" title="Account Category" sortable="true"></display:column>
			<display:column property="standardTypeStr" title="Standard Type" sortable="true"></display:column>
			<display:column property="statusStr" title="Status" sortable="true"></display:column>
			<display:column property="ledgerAccountId" class="hidden" headerClass="hidden" media="html"></display:column>
						
			<display:column media="html">			
				<s:url var="editUrl" action="editLedgerAccountAction?command=update">
			  		<s:param name="ledgerAccountId" value="%{#attr.row.ledgerAccountId}" />
				</s:url>
				
				<s:url var="deleteUrl" action="deleteLedgerAccountAction">
			  		<s:param name="ledgerAccountId" value="%{#attr.row.ledgerAccountId}" />
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