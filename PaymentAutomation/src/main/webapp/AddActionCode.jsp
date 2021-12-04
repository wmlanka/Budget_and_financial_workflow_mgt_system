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
	<title>Action Code</title>
 	
 	<sd:head />
 	
 	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
 	 <script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
 	 
 </head>
<body>

<%-- 	<div class="important img-user" id="instructions" style="width: 80%;">
		   <s:if test="command.equals('add')">
		   		Add Action Code
		   </s:if>
		   <s:if test="!command.equals('add')">
		   		Edit Action Code
		   </s:if>
	</div>	 --%>
	
	<div align="center">
		<table style="width: 95%;">
			<tr> 
				<td class="img-budget">
					 <h5> <br>
					   <s:if test="command.equals('add')">
					   		Add Action Code
					   </s:if>
					   <s:if test="!command.equals('add')">
					   		Edit Action Code
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
  		<form <s:if test="command.equals('add')"> action="addActionCodeAction" </s:if><s:else> action="editActionCodeAction" </s:else> 
  			style="width: 100%;">
  			
    	<div class="card">
		<div class="card-body">
		     	  
		<div class="form-group row">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.budgetType"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
				<s:set var="bType"><s:property value='budgetType'/></s:set>
				       
		       <select name="budgetType" id="budgetTypes" class="form-control">
			       				<option value="">Select Budget Type</option>  	
			    
			        <s:iterator value="budgetTypeList" var="budgetTypeList">
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
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['budgetType'][0]"/></font>
		    </div>
		</div> 
		        
		<div class="form-group row">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.actionCode"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
				<input type="text" name="code" value="<s:property value="code"/>" class="form-control">
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['code'][0]"/></font>
		    </div>
		</div> 
		
		<div class="form-group row">
		    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.description"/>
		    <font class="myErrorMessage"> &nbsp; * </font></label>
		    <div class="col-sm-6">
		      	<input type="text" name="description" value="<s:property value="description"/>" class="form-control">
			</div>
		    <div>
		    	<font class="myErrorMessage"><s:property value="fieldErrors['description'][0]"/></font>
		    </div>
		</div> 
		
	
		
			<s:if test="command.equals('update')">
			
			  <div class="form-group row">
				    <label for="a" class="col-sm-3 col-form-label"><s:text name="label.status"/>
				    <font class="myErrorMessage"> &nbsp; * </font></label>
				    <div class="col-sm-6">
						<input type="radio" name="status" value="A" <s:if test='status=="A"'>CHECKED</s:if>>&nbsp;Active
						&nbsp;
			          	<input type="radio" name="status" value="I" <s:if test='status=="I"'>CHECKED</s:if>>&nbsp;Inactive					
			        </div>
				    <div>
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

    <br>
    
     <div id="buttons" align="center">
     <table style="width: 90%;">
     	<tr>
     		<td align="left"><a href="actionCodeAction" class="back-link">Go Back</a></td>
     	
     		<td  class="tdRight">
     		    <input type="submit" name="saveSubmit" value="<s:text name="button.submit"/>">
     		
     			<s:if test="command.equals('add')">
      	        		<input type="submit" name="clearSubmit" value="<s:text name="button.clear"/>" formaction="resetActionCodeAction">		
		      	</s:if>
		      	<s:else>
		      	      	<input type="submit" name="clearSubmit" value="<s:text name="button.reset"/>" formaction="resetActionCodeAction">		
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



