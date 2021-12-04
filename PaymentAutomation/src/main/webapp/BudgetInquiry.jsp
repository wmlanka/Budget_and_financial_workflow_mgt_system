<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@page import="java.text.DecimalFormat" %>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="/struts-dojo-tags" prefix="sd" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Report Budget</title>

	  <sd:head />
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	 
 	 <script type="text/javascript">
 			/* function loadActionCodes(var x){
 				alert(x);
 			} */
 			
    		   $(document).ready(function() { 
 			   $('#budgetTypes').change(function(event) {
 			      var budgetTypes = $("select#budgetTypes").val();
 			      var actionCodeMap = "";
 			      var dummyMsg="";
 			      $("#budgetErr").empty();
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
 			    
 			    $("#year").change(function(event) {
			    	var year = $("#year").val();
			    	$("#yearErr").empty();
		  		    if (isNaN(year)) {
		  		    	year = '';
		  		    }
			  		$("#year").val(year);
		
		    	});
 			    
 				 $("#savee").click(function(event) {
						$("#msgDiv").empty();

			 	});
 			   
 			});
 			
 			

 	 </script>
 	 
</head>
<body>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-report">
					 <h5>
						  Budget Summary
				   	</h5>
			   </td>
			</tr>
		</table>
	</div>	
	
	<div  class="cardContainer">
	
	 <div class="card">
		  <div class="card-body">
		  
	<form>
			  <div class="form-group row">
				    <label for="a" class="col-sm-2 col-form-label"><s:text name="label.budgetType"/><font class="myErrorMessage"> &nbsp; * </font></label>
				    <div class="col-sm-4">
				    	<s:set var="bType"><s:property value='budgetType'/></s:set>
		    			<select name="budgetType" id="budgetTypes"  class="form-control">
 				       				<option value="0">Select Budget Type</option>  	
  			    
					        <s:iterator value="budgetTypeList" status="outer" var="budgetTypeList">
					        	<s:if test="%{#budgetTypeList.budgetType.equals(#bType)}">
						        	<option value="<s:property value='budgetType'/>" selected="selected">
		                					<s:property value='description' />
		            				</option>
					        	</s:if>
					        	<s:else>
					        		<option value="<s:property value='budgetType'/>" >
		                					<s:property value='description' />
		            				</option>
					        	</s:else>
					      	</s:iterator>				      
				       </select>
				    </div>
				    <div id="budgetErr">
				    	<font class="myErrorMessage"><s:property value="fieldErrors['budgetType'][0]" /></font>	        		        
				    </div>
			  </div>
			  
			  <div class="form-group row">
				    <label for="a" class="col-sm-2 col-form-label"><s:text name="label.actionCode"/></label>
				    <div class="col-sm-4">
		   			<s:set var="aCode"><s:property value='actionId'/></s:set>
        			<select name="actionId" id="actionCodes"  class="form-control">
 				       	<option value="0">Select Action Code </option>				      
 				        <s:iterator value="actionCodeList" var="actionCodeList">
					        	<s:if test="%{#actionCodeList.actionId==#aCode}">
						        	<option value="<s:property value='actionId'/>" selected="selected">
		                					<s:property value='description' />
		            				</option>
					        	</s:if>
					        	<s:else>
					        		<option value="<s:property value='actionId'/>" >
		                					<s:property value='description' />
		            				</option>
					        	</s:else>
					     </s:iterator>
					  </select>					     
				    </div>
				    <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['actionId'][0]" /></font>	        		        
				    </div>
			  </div>
			  
			  <div class="form-group row">
				    <label for="a" class="col-sm-2 col-form-label"><s:text name="label.budgetCode"/></label>
				    <div class="col-sm-4">
						<s:set var="bCode"><s:property value='budgetCodeId'/></s:set>            			
        				<select name="budgetCodeId" id="budgetCodes" class="form-control">
				       	<option value="0">Select Budget Code </option>		
				       	
				       	 <s:iterator value="budgetCodeList" var="budgetCodeList">
					        	<s:if test="%{#budgetCodeList.budgetCodeId==#bCode}">
						        	<option value="<s:property value='budgetCodeId'/>" selected="selected">
		                					<s:property value='description' />
		            				</option>
					        	</s:if>
					        	<s:else>
					        		<option value="<s:property value='budgetCodeId'/>" >
		                					<s:property value='description' />
		            				</option>
					        	</s:else>
					     </s:iterator>
					     		      
				     </select>				     
				    </div>
				    <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['budgetCodeId'][0]" /></font>	        		        
				    </div>
			  </div>
			  
			  <div class="form-group row">
				    <label for="a" class="col-sm-2 col-form-label"><s:text name="label.year"/></label>
				    <div class="col-sm-4">
						<input type="text" name="year" value="<s:property value="year"/>" class="form-control" id="year" maxlength="4">
				    </div>
					<div id="YearErr">
				    	<font class="myErrorMessage"><s:property value="fieldErrors['year'][0]" /></font>	        		        
				    </div>
			  </div>
			  
			 <%--  <div><font class="myErrorMessage"><s:property value="fieldErrors['year'][0]" /></font></div> --%>
			  
 			  <div class="form-group row">
			<!-- 	  <label for="a" class="col-sm-2 col-form-label">Report Option</label>
				  <div class="form-inline">
				  		  <div class="form-check-inline">
							  <label class="form-check-label">
							    &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" class="form-check-input" name="output" value="html" checked="checked">HTML
							  </label>
						  </div>
							<div class="form-check-inline">
							  <label class="form-check-label">
							    &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" class="form-check-input" name="output" checked="checked">PDF
							  </label>
							</div>
							<div class="form-check-inline">
							  <label class="form-check-label">
							    <input type="radio" class="form-check-input" name="output">Excel
							  </label>
							</div> 
				
				  </div> -->
		  		  <div class="col-sm-6" align="right">
	     	        	<input type="submit" name="" value="Generate Excel" formaction="callBudgetInqAction" id="savee">	
	     	        	<input type="hidden" name="command" value="save">
				  </div> 
			  </div>
			
			<div id="msgDiv">  
			<s:if test="hasActionMessages()">
			 <%if((((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).getActionMessages()).toString().contains("not found")){%>							
					<div class="alert-box notice">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span>
						<s:actionmessage/> 
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
					</div>
			  <% }else{ %>
				  <div class="alert-box warning">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span>
						<s:actionerror/> 
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
						
					</div>
		  	  <%} %>
			</s:if>
			</div>


	</form>
	
	</div>
	</div>
	</div>
</body>
</html>