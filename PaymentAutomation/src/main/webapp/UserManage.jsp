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
<title>Approvals</title>

 <link rel="stylesheet" href="bootstraps/css/bootstrap2.min.css">
 <link rel="stylesheet" href="assets/css/commonStyle.css">
 <link rel="stylesheet" href="assets/css/myStyle.css">
 
 <script type="text/javascript" src="bootstraps/js/jquery.min.js"></script>  
 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script>  

</head>
<body>

	<div class="gridContainer">
		
		<table style="width: 100%">
			<tr> 
				<td class="img-userManage"><h3>User Access</h3></td>
		<!-- 		<td style="text-align: right;"> 
					<h4><a href="addApprovalDocAction?command=add">
  					<img border="0" src="assets/images/addNew.png" width="45" height="45"/> Add New</a>
  					</h4>
				</td> -->
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

		<!-- <br> -->
		<display:table export="true" id="" name="list" pagesize="15" requestURI="" class="table table-bordered" uid="row">
			<display:column><c:out value="${row_rowNum}"/></display:column>
			<display:column property="userId" title="User Id" sortable="true"></display:column>
			<display:column property="userName" title="User Name" sortable="true"></display:column>
			<display:column property="costCenter" title="Department" sortable="true"></display:column>
			<display:column property="userTypeCode" title="User Type" sortable="true"></display:column>
			<display:column property="userTypeDescr" title="Role Description" sortable="true"></display:column>
			<display:column property="status" title="Status" sortable="true"></display:column>
			<display:column property="userId" class="hidden" headerClass="hidden" media="html"></display:column>
			
		<%-- 	<display:column media="html">			
				<s:url var="editUrl" action="editUserAction?command=update">
			  		<s:param name="userId" value="%{#attr.row.userId}" />
				</s:url>
				
			 	<s:a href="%{#editUrl}">Edit</s:a>
			</display:column> --%>
			
		    <display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.excel" value="false" />
			<display:setProperty name="export.csv" value="false" />
			<display:setProperty name="export.xml" value="false" />

		</display:table>
		
	</div>
</body>
</html>