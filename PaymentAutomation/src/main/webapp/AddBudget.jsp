<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/struts-dojo-tags" prefix="sd" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Budget Creation</title>
 	 	 
 	
 	<sd:head />
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 
 	 <script type="text/javascript">
 			/* function loadActionCodes(var x){
 				alert(x);
 			} */
 			
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
 			    
 			   $('#approvalTypes').change(function(event) {
  			      var approvalTypes = $("select#approvalTypes").val();
  			      var approvalDocMap = "";
  			      var dummyMsg="";
  			      $.getJSON('ajaxAction?method=getAvailableApprovalDocs', {//$.getJSON('ajaxAction?method=getApprovalDocs', {
  			    	approvalType : approvalTypes
  			      }, function(jsonResponse) { 
  			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
  			         $("#approvalFullAmount").val("0.00");
	  			     $("#approvalBalanceAmount").val("0.00");
	  			      	
  			        var select = $('#approvalDocs');
  			        select.find('option').remove();
  			        $.each(jsonResponse.approvalDocMap, function(key, value) {
  			          $('<option>').val(key).text(value).appendTo(select);
  			        });  
  			      });
  			      });
  			      
  			      
 			   $('#approvalDocs').change(function(event) {
	  			      var approvalDocs= $("select#approvalDocs").val();
	  			      var fullAmount = "";
	  			      var balanceAmount = "";
	  			      var dummyMsg="";
	  			      
	  			      if(approvalDocs=='0'){
	  			    	   $("#approvalFullAmount").val("0.00");
	  	  			       $("#approvalBalanceAmount").val("0.00");
	  			      }else{
	  			      
		  			      $.getJSON('ajaxAction?method=getApprovalDocsData', {
		  			    	approvalDocId : approvalDocs // property : id
		  			      }, function(jsonResponse) {// alert(jsonResponse.fullAmount);
		  			        //$('#ajaxResponse').text(jsonResponse.dummyMsg);
		  			        
		  			        fullAmount = jsonResponse.fullAmount;
		  			        fullAmount = fullAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
		  			        
		  			        balanceAmount = jsonResponse.balanceAmount;
		  			        balanceAmount = balanceAmount.toLocaleString('en-US', {minimumFractionDigits: 2});
				  		
		  			        $("#approvalFullAmount").val(fullAmount);
		  			      	$("#approvalBalanceAmount").val(balanceAmount);
		  			      });
	  			      }
	  			});
 			   
 		 		$("#allocatedAmount").change(function(event) {
			    	var allocatedAmount = $("#allocatedAmount").val();
			    	
			    	if (allocatedAmount.indexOf(',') > -1) {
			    		allocatedAmount = $("#allocatedAmount").val().replace(/,/g, '');  
		    	    }
			    	 
		  		    if (isNaN(allocatedAmount)) {
		  		    	allocatedAmount = 0.00;
		  		    }
		  		   
		  		  	allocatedAmount  = parseFloat(allocatedAmount);
		  			allocatedAmount = allocatedAmount.toLocaleString('en-US', {minimumFractionDigits: 2});	  		  
			  		$("#allocatedAmount").val(allocatedAmount);
		
		    	});
 			   
 			});
 			
 			
 	 </script>
 	 
 </head>
<body>

	<%-- <div class="important img-user" id="instructions" style="width: 80%;">
		   <s:if test="command.equals('add')">
		   		Budget Creation
		   </s:if>
		   <s:if test="!command.equals('add')">
		   		Edit Budget
		   </s:if>
		   
	</div>	 --%>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-budget">
					 <h5> <br>
					   <s:if test="command.equals('add')">
					   		Budget Creation
					   </s:if>
					   <s:if test="!command.equals('add')">
					   		Edit Budget
					   </s:if>
				   	</h5>
			   </td>
			</tr>
		</table>
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
	
	<div class="cardContainer">
  		<form <s:if test="command.equals('add')"> action="addBudgetAction" </s:if><s:else> action="editBudgetAction" </s:else> 
  			style="width: 100%;">
  	
  	<div class="card">
	<div class="card-body">
  			
  		 <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.budgetType"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">

					<s:set var="bType"><s:property value='budgetType'/></s:set>
				      <s:if test="command.equals('add')"> 
					       <select name="budgetType" id="budgetTypes" class="form-control">
	 				       				<option value="">Select Budget Type</option>  	
	  			    
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
				       </s:if>
				       <s:else>
				        <select name="budgetType" id="budgetTypes" class="form-control" disabled="disabled">
	 				       				<option value="">Select Budget Type</option>  	
	  			    
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
				       </s:else>
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['budgetType'][0]"/></font>
		    </div>
		  </div>
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.actionCode"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">

				<s:set var="aCode"><s:property value='actionId'/></s:set>
				<s:if test="command.equals('add')">
					<select name="actionId" id="actionCodes" class="form-control">
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
				</s:if>
				<s:else>
					<select name="actionId" id="actionCodes" class="form-control" disabled="disabled">
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
				</s:else>
        			
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['actionId'][0]"/></font>
		    </div>
		  </div>
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.budgetCode"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">

				<s:set var="bCode"><s:property value='budgetCodeId'/></s:set> 
					  <s:if test="command.equals('add')">
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
					  </s:if>
					  <s:else>
					  			<select name="budgetCodeId" id="budgetCodes" class="form-control" disabled="disabled">
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
					  </s:else>           			
        			
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['budgetCodeId'][0]"/></font>
		    </div>
		  </div>
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.year"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
					   <s:if test="command.equals('add')">
					   				<select name="year" class="form-control">
								       	<option value="0">Select Year</option>	
								       	<s:set var="vYear"><s:property value='year'/></s:set>
								       	
								       	<s:iterator value="years" var="years">
								       	<s:set var="vYears"><s:property/></s:set>
								       			<s:if test="%{#vYears.equals(#vYear)}">
										        	<option value="<s:property/>" selected="selected">
						                					<s:property/>
						            				</option>
					            				</s:if>
					            				<s:else>
					            					<option value="<s:property/>">
						                					<s:property/>
						            				</option>
					            				</s:else>
									     </s:iterator>			      
								     </select>
					   </s:if>
					   <s:else>
					   			<select name="year" class="form-control" disabled="disabled">
								       	<option value="0">Select Year</option>	
								       	<s:set var="vYear"><s:property value='year'/></s:set>
								       	
								       	<s:iterator value="years" var="years">
								       	<s:set var="vYears"><s:property/></s:set>
								       			<s:if test="%{#vYears.equals(#vYear)}">
										        	<option value="<s:property/>" selected="selected">
						                					<s:property/>
						            				</option>
					            				</s:if>
					            				<s:else>
					            					<option value="<s:property/>">
						                					<s:property/>
						            				</option>
					            				</s:else>
									     </s:iterator>			      
								     </select>
					   </s:else>
			
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['year'][0]"/></font>
		    </div>
		  </div>	
		  	
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.approvalType"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
		    		   <s:set var="aType"><s:property value='approvalType'/></s:set>
		    		   <s:if test="command.equals('add')">
		 		   				<select name="approvalType" id="approvalTypes" class="form-control">
		 		   					<option value="0">Select Approval Type</option>		
		 		   							      
							      	<s:iterator value="approvalTypeList" var="approvalTypeList">
								       	<s:set var="vTypeList"><s:property/></s:set>
								       			<s:if test="%{#vTypeList.equals(#aType)}">
										        	<option value="<s:property/>" selected="selected">
						                					<s:property/>
						            				</option>
					            				</s:if>
					            				<s:else>
					            					<option value="<s:property/>">
						                					<s:property/>
						            				</option>
					            				</s:else>
									 </s:iterator>
									 
						       </select>
					   </s:if>
					   <s:else>
		 		   				<select name="approvalType" id="approvalTypes" class="form-control" disabled="disabled">
		 		   					<option value="0">Select Approval Type</option>		
		 		   							      
							      	<s:iterator value="approvalTypeList" var="approvalTypeList">
								       	<s:set var="vTypeList"><s:property/></s:set>
								       			<s:if test="%{#vTypeList.equals(#aType)}">
										        	<option value="<s:property/>" selected="selected">
						                					<s:property/>
						            				</option>
					            				</s:if>
					            				<s:else>
					            					<option value="<s:property/>">
						                					<s:property/>
						            				</option>
					            				</s:else>
									 </s:iterator>
									 
						       </select>
					   </s:else>

					
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['approvalType'][0]"/></font>
		    </div>
		  </div>
		   
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.approvalRefNo"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">

					<s:set var="aDoc"><s:property value='approvalDocId'/></s:set>
					<s:if test="command.equals('add')">
							 	<select name="approvalDocId" id="approvalDocs" class="form-control">
				       			<option value="0">Select Approval No</option>				      
							     
						        <s:iterator value="approvalDocumentList" var="approvalDocumentList">
							        	<s:if test="%{#approvalDocumentList.approvalDocId==#aDoc}">
								        	<option value="<s:property value='approvalDocId'/>" selected="selected">
				                					<s:property value='referenceNo' />
				            				</option>
							        	</s:if>
							        	<s:else>
							        		<option value="<s:property value='approvalDocId'/>" >
				                					<s:property value='referenceNo' />
				            				</option>
							        	</s:else>
							     </s:iterator>
							     </select>
					</s:if>
					<s:else>
					 			<select name="approvalDocId" id="approvalDocs" class="form-control" disabled="disabled">
				       			<option value="0">Select Approval No</option>			      
							     
						        <s:iterator value="approvalDocumentList" var="approvalDocumentList">
							        	<s:if test="%{#approvalDocumentList.approvalDocId==#aDoc}">
								        	<option value="<s:property value='approvalDocId'/>" selected="selected">
				                					<s:property value='referenceNo' />
				            				</option>
							        	</s:if>
							        	<s:else>
							        		<option value="<s:property value='approvalDocId'/>" >
				                					<s:property value='referenceNo' />
				            				</option>
							        	</s:else>
							     </s:iterator>
							     </select>
					</s:else>
					            							
				
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['approvalDocId'][0]"/></font>
		    </div>
		  </div> 
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label">Approval Full Amount
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
				<input type="text" id="approvalFullAmount" name="approvalFullAmount" 
				value="<s:property value="getText('{0,number,#,##0.00}',{approvalFullAmount})"/>"
				style="text-align: center;" class="form-control" readonly="readonly">	
			</div>
		    <div>
		    </div>
		  </div> 
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label">Approval Balance Amount
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
				<input type="text" id="approvalBalanceAmount" name="approvalBalanceAmount" 
				value="<s:property value="getText('{0,number,#,##0.00}',{approvalBalanceAmount})"/>"
				style="text-align: center;" class="form-control" readonly="readonly">	
			</div>
		    <div>
		    </div>
		  </div> 
		  
		   <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.allocatedAmount"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
				<input type="text"  name="allocatedAmount" id="allocatedAmount"
				value="<s:property value="getText('{0,number,#,##0.00}',{allocatedAmount})"/>"
				style="text-align: center;" class="form-control">	
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['allocatedAmount'][0]"/></font>
		    </div>
		  </div>   
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.utilizedAmount"/>
		    </label>
		    <div class="col-sm-6">
		        	<input type="text"  name="utilizedAmount" 
		        	value="<s:property value="getText('{0,number,#,##0.00}',{utilizedAmount})"/>"
		        	readonly="readonly" style="text-align: center;" class="form-control"> 		        	 		        		        
			</div>
		    <div>
		    </div>
		  </div>   
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.description"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
		      		<input type="text" name="description" value="<s:property value="description"/>" class="form-control">
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['description'][0]"/></font>
		    </div>
		  </div>
		    
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.remark"/>
		    </label>
		    <div class="col-sm-6">
		      	<input type="text" name="remark" value="<s:property value="remark"/>" class="form-control">
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['remark'][0]"/></font>
		    </div>
		   </div>
		    
		   <s:if test="command.equals('update')">
		     <div class="form-group row" style="margin-bottom: 10px;">
			    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.status"/>
			    <font class="myErrorMessage"> &nbsp; * </font></label>
			    <div class="col-sm-3">
						<input type="radio" name="status" value="A" <s:if test='status=="A"'>CHECKED</s:if>>&nbsp;Active 
						&nbsp;
			          	<input type="radio" name="status" value="I" <s:if test='status=="I"'>CHECKED</s:if>>&nbsp;Inactive				</div>
			    <div>
			    	<font class="myErrorMessage"><s:property value="fieldErrors['status'][0]"/></font>
			   </div>
			   </div>
		    </s:if>
		    
		   </div> 
		 </div>


    
<%-- 
<s:property value='budgetType'/>
<c:out value="${budgetType}"/>
<s:property value="#bType"/>
<s:property value="#vYear"/>
<s:property value="#aCode"/>
 --%>
    
    <!-- <div class="button-bar" align="center"> -->
     <div id="buttons" align="center">
     <table style="width: 90%;">
     	<tr>
     		<td align="left"><a href="budgetAction" class="back-link">Go Back</a></td>
     	
     		<td  class="tdRight">
     		    <input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>">
     		
     			<s:if test="command.equals('add')">
      	        		<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetBudgetAction">		
		      	</s:if>
		      	<s:else>
		      	      	<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetBudgetAction">		
		      	</s:else>
      		</td>
     	</tr>
     </table>
     </div>
      	
    
    <input type="hidden" name="budgetId" value="<s:property value="budgetId"/>">
    <input type="hidden" name="command" value="save">
    </form>
</div>
</body>
</html>



