<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Add Stakeholder</title>
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 
 	  <script type="text/javascript" src="bootstraps/js/bootstrap.min.js"></script> 
 	 
 	
 	<script type="text/javascript">
 		function clearValues(){
 			
 		}
 	</script>
</head>
<body>

	<%-- <div class="important img-stakeholder" id="instructions" style="width: 80%;vertical-align: center;">
		   <s:if test="command.equals('add')">
		   	Add Supplier/Beneficiary
		   </s:if>
		   <s:if test="!command.equals('add')">
		   	Edit Supplier/Beneficiary
		   </s:if>
		   
	</div> --%>	
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-stakeholder">
					 <h5>
						 <s:if test="command.equals('add')">
						   		Add Supplier/Beneficiary
						  </s:if>
						  <s:if test="!command.equals('add')">
						   		Edit Supplier/Beneficiary
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

	<!--  <br> --> 
	
	<div class="cardContainer">
  		<form <s:if test="command.equals('add')"> action="addStakeholderAction" </s:if><s:else> action="editStakeholderAction" </s:else> 
  			style="width: 100%;">
  			
  	<div class="card">
		<div class="card-body">
  			
		  <div class="form-group row">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.fullName"/><font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
		      <input type="text" name="fullName" value="<s:property value="fullName"/>" class="form-control" id="a">
		    </div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['fullName'][0]"/></font>
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.address"/><font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
		      <input type="text" size="60" name="address" value="<s:property value="address"/>" class="form-control">
		    </div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['address'][0]"/></font>
		    </div>
		  </div>
		   <div class="form-group row">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.remark"/></label>
		    <div class="col-sm-6">
		      <input type="text" size="60" name="remark" value="<s:property value="remark"/>" class="form-control">
		    </div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['remark'][0]"/></font>
		    </div>
		  </div>
	   	  <div class="form-group row">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.contactNo"/>
		    <font class="myErrorMessage"> * </font></label>
		    <div class="col-sm-6">
		      <input type="text" size="60" name="contactNo" value="<s:property value="contactNo"/>" class="form-control">
		    </div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['contactNo'][0]"/></font>
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="" class="col-sm-3 col-form-label"><s:text name="label.email"/></label>
		    <div class="col-sm-6">
		      <input type="text" size="60" name="email" value="<s:property value="email"/>" class="form-control">
		    </div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['email'][0]"/></font>
		    </div>
		  </div>
		   <s:if test="command.equals('update')">
			  <div class="form-group row">
			    <label for="" class="col-sm-3 col-form-label"><s:text name="label.status"/></label>
			    <div class="col-sm-3">
			    	<input type="radio" name="status" value="A" <s:if test='status=="A"'>CHECKED</s:if>>&nbsp;Active &nbsp;
			        <input type="radio" name="status" value="I" <s:if test='status=="I"'>CHECKED</s:if>>&nbsp;Inactive
			    </div>
			    <div>
			    	<font class="myErrorMessage"><s:property value="fieldErrors['email'][0]"/></font>
			    </div>
			  </div>
		   </s:if>
		 
		 </div>
	</div>
    
<%--     <s:actionerror/>
 --%>
    <br>
    
    <div id="buttons" align="center">
     <table style="width: 90%">
     	<tr>
     		<td align="left"><a href="stakeholderAction?" class="back-link">Go Back</a></td>
     	
     		<td class="tdRight">
     		    <input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>">
     		
     			<s:if test="command.equals('add')">
      	        		<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetStakeholderAction">		
		      	</s:if>
		      	<s:else>
		      	      	<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetStakeholderAction">		
		      	</s:else>
      		</td>
     	</tr>
     </table>
<%--         <a href="stakeholderAction?" class="back-link">Go Back</a>
      	<input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>">
      	<s:if test="command.equals('add')">
      	        <input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetStakeholderAction">		
      	</s:if>
      	<s:else>
      	      	<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetStakeholderAction">		
      	</s:else> --%>
      	
    </div>
    
    <input type="hidden" name="stakeholderId" value="<s:property value="stakeholderId"/>">
    <input type="hidden" name="command" value="save">
    </form>
</div>
</body>
</html>



