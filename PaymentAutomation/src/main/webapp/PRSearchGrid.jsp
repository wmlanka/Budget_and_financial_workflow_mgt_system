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
<title>Search Result</title>

 <link rel="stylesheet" href="bootstraps/css/bootstrap2.min.css">
 <link rel="stylesheet" href="assets/css/commonStyle.css">
 <link rel="stylesheet" href="assets/css/myStyle.css">
 
 
 <script type="text/javascript" src="bootstraps/js/jquery.min.js"></script>  
 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script>  

	<script type="text/javascript">
	</script>
	
	 <style type="text/css">
 	 	.errorMessage {display: none;}
 	 	
		input.linkAdd { 
			 	display: inline-block;
			    border: none;
			    padding: 7px 40px;
			    margin: 0;
			    text-decoration: none;
			    background: rgb(23, 162, 184); /*  rgb(109, 127, 204) #0069ed;*/ 
			    color: #ffffff;
			    font-family: sans-serif;
			    font-size: 17px;
			    cursor: pointer;
			    text-align: center;
			    transition: background 250ms ease-in-out, transform 150ms ease;
			    -webkit-appearance: none;
			    -moz-appearance: none; 
		} 
		
		input[type=submit] {

}
		 

 	 </style>
</head>
<body>
		<form>
 		 <div class="gridContainer">
 		 	 
			<div align="right">
				<input type="submit" name="" value="Go Back" formaction="goBackPRExplore" class="linkAdd">
			</div>	
			<!-- <br> -->	
 			<div id="searchDiv">
		  		<display:table export="false" id="" name="prDTOList" pagesize="15" requestURI="" class="table table-bordered" uid="row" >
					<display:column><c:out value="${row_rowNum}"/></display:column>
					<display:column property="prNumber" title="Ref No" sortable="true"></display:column>
					<display:column property="subject" title="Subject" sortable="true" headerScope=""></display:column>
					<display:column property="budgetYear" title="Year" sortable="true"></display:column>
					<%-- <display:column property="budgetCode" title="Budget Code" sortable="true"></display:column> --%>
					<display:column property="stakeholderName" title="Supplier/Beneficiary" sortable="true"></display:column>
					<display:column property="prDateStr" title="Date" sortable="true"></display:column>
					<display:column property="prfStatus" title="Status" sortable="true"></display:column>
					<display:column property="nextApproval" title="Next Approval" sortable="true"></display:column>
					<display:column property="netAmount" title="Net Amount" sortable="true" format="{0,number,#,##0.00}" style="text-align:right;"></display:column>
					<display:column>			
						<s:url var="editUrl" action="editPRAction?command=update">
					  		<s:param name="paymentRequestId" value="%{#attr.row.paymentRequestId}" />
						</s:url>
						
					 	<s:a href="%{#editUrl}">View</s:a>
					</display:column>
					<%-- <display:column property="paymentRequestId" class="hidden" headerClass="hidden"></display:column> --%>
					
					<display:setProperty name="paging.banner.placement">top</display:setProperty>		
				    <display:setProperty name="export.pdf" value="true" />
					<display:setProperty name="export.excel" value="false" />
					<display:setProperty name="export.csv" value="false" />
					<display:setProperty name="export.xml" value="false" />
				</display:table>
			
			
		  	
		  	</div>

		 </div>
		</form>
</body>
</html>