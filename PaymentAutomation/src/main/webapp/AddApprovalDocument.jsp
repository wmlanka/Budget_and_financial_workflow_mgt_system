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
	<title>Add Approvals</title>
 	 	 
 	
 	<sd:head />
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script type="text/javascript" src="assets/js/ajax.js"></script>
 	 <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	 <script type="text/javascript" src="assets/js/jquery-1.8.2.js" ></script>
 	 
 	 
 	 <script type="text/javascript">
 	 	
 	 	function updateBalance(){
			var amount = document.getElementById('packageId');

 	 		//alert(amount);
			var xmlhttp = createAjaxRequest();
			var url = 'addApprovalDocAction?method=updateBalance&amount='+amount+'&rand='+Math.random()*9999999;
			xmlhttp.onreadystatechange=function(){
			  if (xmlhttp.readyState==4 && xmlhttp.status==200){
				  var message =  eval('(' + xmlhttp.responseText + ')');
				  //var array = message['mainArray'];
				  //var select = document.getElementById('packageId');
					try{
						//alert(responseText);
					}catch(e){
					}
			  }
				  
			};
			xmlhttp.open("POST",url,true);//"basicform.php?name="+namevalue+"&age="+agevalue
			xmlhttp.send();
		}
 	 	
 	 	$(document).ready(function() { 
	 		$("#approvedFullAmount").change(function(event) {
		    	var approvedFullAmount = $("#approvedFullAmount").val();
		    	
		    	if(!approvedFullAmount){
		    		approvedFullAmount = '0.00';
		    	}
		    	
		    	if (approvedFullAmount.indexOf(',') > -1) {
		    		approvedFullAmount = $("#approvedFullAmount").val().replace(/,/g, '');  
	    	    }
		    	 
	  		    if (isNaN(approvedFullAmount)) {
	  		    	approvedFullAmount = 0.00;
	  		    }
	  		   
	  		  approvedFullAmount  = parseFloat(approvedFullAmount);
	  		  approvedFullAmount = approvedFullAmount.toLocaleString('en-US', {minimumFractionDigits: 2});	  		  
		  	  $("#approvedFullAmount").val(approvedFullAmount);
	
	    	});
		});
 	 </script>
 </head>
<body>

<%-- 	<div class="important img-user" id="instructions" style="width: 80%;">
		   <s:if test="command.equals('add')">
		   		Add Approval Document
		   </s:if>
		   <s:if test="!command.equals('add')">
		   		Edit Approval Document
		   </s:if>
		   
	</div> --%>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-approval">
					 <h5> <br>
					   <s:if test="command.equals('add')">
					   		Add Approval Document
					   </s:if>
					   <s:if test="!command.equals('add')">
					   		Edit Approval Document
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
		  
		<%-- <s:if test="hasActionMessages()">
			<div class="alert-box success">
				<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
					&times;
				</span>
				<s:actionmessage/>
			    <%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>							
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
		
	<%-- 	<s:if test="hasActionErrors()">
			<div class="alert-box error">
				<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
					&times;
				</span>
				<ul class="myErrorMessage">
					<s:actionerror/>
				</ul>
			</div>
		</s:if>	 --%>	
			
	<div class="cardContainer">
  		<form <s:if test="command.equals('add')"> action="addApprovalDocAction" </s:if><s:else> action="editApprovalDocAction" </s:else> 
  			style="width: 85%;">
  	
  	<div class="card">
		<div class="card-body">
				
  		 <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.approvalType"/>
		    <font class="myErrorMessage"> * </font></label>
		    <div class="col-sm-6">
		  <%-- 	<s:select label="Approval Type"  headerKey="1" 
						        list="approvalTypeList"  name="approvalType" 
						        value="" class="form-control"/>  --%>
					<s:set var="bType"><s:property value='approvalType'/></s:set>
						        
					<select name="approvalType" id="approvalType"  class="form-control">
 				       	<!-- <option value="">Select Budget Type</option>  --> 	
  			    
					        <s:iterator value="approvalTypeList" status="outer" var="approvalTypeList">
					        	<s:if test="%{#approvalTypeList.approvalType.equals(#bType)}">
						        	<option value="<s:property value='approvalType'/>" selected="selected">
		                					<s:property value='description' />
		            				</option>
					        	</s:if>
					        	<s:else>
					        		<option value="<s:property value='approvalType'/>" >
		                					<s:property value='description' />
		            				</option>
					        	</s:else>
					      	</s:iterator>				      
				       </select>
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['approvalType'][0]"/></font>
		    </div>
		  </div>
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.refNo"/>
		    <font class="myErrorMessage"> * </font></label>
		    <div class="col-sm-6">
		  	 <input type="text" size="60" name="referenceNo" value="<s:property value="referenceNo"/>" class="form-control">
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['referenceNo'][0]"/></font>
		    </div>
		  </div>
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.description"/>
		    </label>
		    <div class="col-sm-6">
		  	 <input type="text" size="60" name="description" value="<s:property value="description"/>" class="form-control">
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['description'][0]"/></font>
		    </div>
		  </div>
		  
		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.fullAmount"/>
		    <font class="myErrorMessage"> * </font></label>
		    <div class="col-sm-6">
				<input type="text" size="60" id="approvedFullAmount" name="approvedFullAmount" 
				value="<s:property value="getText('{0,number,#,##0.00}',{approvedFullAmount})"/>"
				 class="form-control" style="text-align: center;">
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['approvedFullAmount'][0]"/></font>
		    </div>
		  </div>
  			
  		  <div class="form-group row" style="margin-bottom: 10px;">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.partPayment"/>
		    </label>
		    <div class="col-sm-9">
		    	<!-- <input type="checkbox" id="partPayment" name="partPayment" value="Bike"> -->
		    	<s:checkbox label="" name="partPayment" labelposition="left"/>
		    </div>
		    <div>
		    </div>
		  </div>
		  
		   <s:if test="command.equals('update')"> 
			    <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="" class="col-sm-3 col-form-label"><s:text name="label.utilizedAmount"/>
				    </label>
				    <div class="col-sm-6">
						<input type="text" size="60" id="utilizedAmount" name="utilizedAmount" 
						value="<s:property value="getText('{0,number,#,##0.00}',{utilizedAmount})"/>"
						readonly="readonly"  class="form-control" style="text-align: center;">		
					</div>
				    <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['utilizedAmount'][0]"/></font>
				    </div>
			  	</div>
 		
 		  </s:if>
 		  
 		  <div class="form-group row" style="margin-bottom: 10px;">
				    <label for="" class="col-sm-3 col-form-label"><s:text name="label.approvalDate"/>
				    </label> <!--  label="Approval Date(dd-MMM-yyyy)"  -->
				    <div class="col-sm-6">
			   				<s:if test="command.equals('update')"> 
			          			<sd:datetimepicker label="" name="approvedDate" displayFormat="dd-MMM-yyyy"  
			          			/> 
			          		</s:if>
			          		<s:else>
			          		     <sd:datetimepicker label="" name="approvedDate" displayFormat="dd-MMM-yyyy" value="%{'today'}"/> 
			          		</s:else>
			         </div>
          			 <div>
				    	<font class="myErrorMessage"><s:property value="fieldErrors['approvedDate'][0]"/></font>
				     </div>
		</div>
		
		<s:if test="command.equals('update')">
		  <div class="form-group row" style="margin-bottom: 5px;">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.status"/>
		    <font class="myErrorMessage"> * </font></label>
		    <div class="col-sm-6">
			      		<input type="radio" name="status" value="A" <s:if test='status=="A"'>CHECKED</s:if>> &nbsp; Active
			          	<input type="radio" name="status" value="I" <s:if test='status=="I"'>CHECKED</s:if>> &nbsp; Inactive
			</div>
			<div></div>
		  </div>
		</s:if>
 	</div>
 	</div>	  

			<%-- <div align="left">
       			<s:select label="Approval Type"  headerKey="-1" headerValue="Select Approval Type"
				        list="approvalTypeList"  name="approvalType" 
				        value="" />  
		     	<s:property value="fieldErrors['approvalType'][0]"/> </div --%>  

    
    <div id="buttons" align="center">
     <table style="width: 90%">
     	<tr>
     		<td align="left"><a href="approvalDocAction" class="back-link">Go Back</a></td>
     	
     		<td class="tdRight">
     		    <input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>">
     		
     			<s:if test="command.equals('add')">
      	        		<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetApprovalDocAction">		
		      	</s:if>
		      	<s:else>
		      	      	<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetApprovalDocAction">		
		      	</s:else>
      		</td>
     	</tr>
     </table>
      	
    </div>
    
    <input type="hidden" name="stakeholderId" value="<s:property value="stakeholderId"/>">
    <input type="hidden" name="command" value="save">
    </form>
</div>
</body>
</html>



