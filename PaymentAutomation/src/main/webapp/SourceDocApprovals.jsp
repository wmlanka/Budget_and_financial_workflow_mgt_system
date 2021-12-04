<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<title></title>
 <script type="text/javascript">
			 $("a.linkDelete").click(function(e) {
				 //this line will prevent the default form submission on click of link
				 e.preventDefault();
				  var id= $("#tempSourceDocAppId").val(); alert(id);
				 //fire the ajax request on this object referring to the clicked anchor link element
				 $.ajax({
				 url: "deleteSourceApprovalAction.action?",
				 cache: false
				 }).done(function( html ) {
				 		$("#apporvalsDiv").html(html);
				 });
			});
			 
			 $("a.linkDelete").click(function(e) {
				 //this line will prevent the default form submission on click of link
				 e.preventDefault();
				  var id= $("#tempSourceDocAppId").val(); alert(id);
				 //fire the ajax request on this object referring to the clicked anchor link element
				 $.ajax({
				 url: "deleteSourceApprovalAction.action?",
				 cache: false
				 }).done(function( html ) {
				 		$("#apporvalsDiv").html(html);
				 });
			});
			 
			 function callDelete(var m){
				 alert(m);
			 }
 </script>
 
</head>
<body>

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
			 
			<s:if test="%{tempSourceApprovalList.isEmpty()}">
			 	<table class="table table-bordered">
				    <tr>
				        <th></th>
				        <th>Approval Type</th>
				        <th>Approval No</th>
				        <th>Applied Amount</th>
				        <th></th>
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
    
		 	<s:else>
		 	 <div id="result">

			 	<display:table export="false" id="stkTable" name="tempSourceApprovalList" requestURI="" class="table table-bordered" uid="row">
					<display:column><c:out value="${row_rowNum}"/></display:column>
					<display:column property="approvalType" title="Approval Type"></display:column>
					<display:column property="approvalRefNo" title="Approval No"></display:column>
					<display:column property="appliedAmount" title="Applied Amount" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
<%-- 					<display:column property="tempSourceDocAppId" class="hidden" headerClass="hidden"></display:column> --%>
					<display:column title="Select"><input type="checkbox" name="checksId" value="${row.tempSourceDocAppId}"></display:column>
				<%-- 	<display:column>			
						<s:url var="deleteUrl" action="deleteApprovals">
					  		<s:param name="tempSourceDocAppId1" value="%{#attr.row.tempSourceDocAppId}" />
						</s:url><!-- href="%{#deleteUrl}" --> <s:url action='deleteApprovals'/>"
					 	<s:a class=""  href="${row.tempSourceDocAppId}">Delete</s:a>
					</display:column> --%>			
				</display:table>
				</div>
 			</s:else>
 		<%-- 	<s:property value="#tempSourceDocAppId"/>
 			<c:out value="${tempSourceDocAppId}"/>
 			<input type="hidden" name="tempSourceDocAppId" value="%{#attr.row.tempSourceDocAppId}" > --%>
 						
</body>
</html>