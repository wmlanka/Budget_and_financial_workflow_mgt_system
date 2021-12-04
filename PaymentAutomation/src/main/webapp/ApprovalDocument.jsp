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

   <s:set var="uType"><s:property value='userSession.userType'/></s:set>
   <s:set var="depId"><s:property value='userSession.departmentId'/></s:set>

	<div class="gridContainer">
		
		<table style="width: 100%">
			<tr> 
				<td class="img-approval"><h3>Approval Document</h3></td>
				<td style="text-align: right;"> 
				
				 <s:if test="%{#uType=='SYSTEM_OPERATOR' || #uType=='SYSTEM_ADMIN'}">
					<h4><a href="addApprovalDocAction?command=add">
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

  	 	<%-- <s:if test="hasActionMessages()">
			<div class="alert-box success">
				<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
					&times;
				</span>
				<s:actionmessage/>
			    <%(((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).getErrorMessages()).toString().startsWith("ERROR");%>							
			</div>
		</s:if>
		
		<s:if test="hasActionErrors()">
			<div class="alert-box error">
				<span class="closebtn" onclick="this.parentElement.style.display='none';
					 ">
					&times;
				</span>
				<s:actionerror/>
			   <%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>							
			</div>
		</s:if> --%>
		
	<%-- 	<s:if test="actionMessages!=null && actionMessages.size > 0">
    <script>
        var actionMessages;
        <s:iterator value="actionMessages" >
            // Iterate the messages, and build the JS String
            actionMessages += '-' + '<s:property />' + '\n';
        </s:iterator>        
        alert (actionMessages);
    </script>
</s:if> --%>


		<!-- <br> -->
		<display:table export="true" id="approvalTable" name="list" pagesize="10" requestURI="" class="table table-bordered" uid="row">
			<display:column><c:out value="${row_rowNum}"/></display:column>
			<display:column property="docName" title="Approval Type" sortable="true"></display:column>
			<display:column property="referenceNo" title="Reference No" sortable="true"></display:column>
			<display:column property="dateStr" title="Approved Date" sortable="true"></display:column>
			<display:column property="description" title="Description" sortable="true"></display:column>
			<display:column property="approvedFullAmount" title="Full Amount" sortable="true" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
			<display:column property="balanceAmount" title="Balance Amount" sortable="true" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>   							
			<display:column property="statusTrans" title="Status" sortable="true"></display:column>   							
			
			<%-- <display:column title="Status" sortable="true">
					 <s:if test="%{#attr.row.status.equals('A')}">
							Active
					 </s:if>
					 <s:else>
					      	Inactive
					 </s:else>
			</display:column> --%>
			<display:column property="approvalDocId" class="hidden" headerClass="hidden" media="html"></display:column>
						
			<s:if test="%{#uType=='SYSTEM_OPERATOR' || #uType=='SYSTEM_ADMIN'}">
				<display:column media="html">			
					<s:url var="editUrl" action="editApprovalDocAction?command=update">
				  		<s:param name="approvalDocId" value="%{#attr.row.approvalDocId}" />
					</s:url>
					
					<s:url var="deleteUrl" action="deleteApprovalDocAction">
				  		<s:param name="approvalDocId" value="%{#attr.row.approvalDocId}" />
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