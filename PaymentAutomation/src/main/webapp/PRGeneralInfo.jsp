<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
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
<title>General Info</title>
</head>
<body>
			  <%-- <s:form>	 --%>	<!-- callPRTabAction1 -->		  
				<div class="form-inline"  style="margin-bottom: 10px;">
				  <div class="form-group">
				    	<label for=""><s:text name="label.department"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				 		<input type="text" class="form-control" name="department" readonly="readonly" value="<s:property value="department"/>">
				  </div>
				  <div class="form-group">
				    	<label for=""> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="label.costCenter"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				    	<input type="text" name="costCenter" value="<s:property value="costCenter"/>" class="form-control" readonly="readonly">
				  </div>
				  
				  <div class="form-group ">
				    <label for="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="label.provisionYear"/><font class="myErrorMessage"> &nbsp; * </font>&nbsp;</label>
				    <div class="col-sm-2">
				      <select name="provisionYear" class="form-control" id="provisionYear">
				       	<!-- <option value="0">Select Year</option>	 -->
				       	<s:set var="vYear"><s:property value='provisionYear'/></s:set>
				       	
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
					</div>
				  </div>				  
			 	</div>
			 				 
			 	<div class="form-inline"  style="margin-bottom: 10px;">
					  <div class="form-group ">
					    <label for=""><s:text name="label.budgetType"/><font class="myErrorMessage"> &nbsp; * </font></label>
					    <div class="col-sm-1">
					       <s:set var="bType"><s:property value='budgetType'/></s:set>
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
						       <%-- <font class="myErrorMessage">dd<s:property value="fieldErrors['budgetType'][0]" /></font> --%>
						</div>
				  </div>
				  
				  <div class="form-group ">
					    <label for=""><s:text name="label.actionCode"/><font class="myErrorMessage"> &nbsp; * </font></label>
					    <div class="col-sm-1">
					    <s:set var="aCode"><s:property value='actionId'/></s:set>
	        			<select name="actionId" id="actionCodes" class="selectpicker form-control" style="width:190px;">
	 				       	<option value="0">Select Action Code </option>				      
	 				        <s:iterator value="actionCodeList" var="actionCodeList">
						        	<s:if test="%{#actionCodeList.actionId==#aCode}">
							        	<option value="<s:property value='actionId'/>" selected="selected">
			                					<s:property value='description' />
			            				</option>
						        	</s:if>
						        	<s:else>
						        		<option value="<s:property value='actionId'/>">
			                					<s:property value='description' />
			            				</option>
						        	</s:else>
						     </s:iterator>
						  </select>					     
					     <%-- <font class="myErrorMessage">dd<s:property value="fieldErrors['actionId'][0]" /> </font> --%>
						</div>
				  </div>
				  	
				  	
				  <div class="form-group ">
					    <label for=""><s:text name="label.budgetCode"/><font class="myErrorMessage"> &nbsp; * </font></label>
					    <div class="col-sm-1">
							<s:set var="bCode"><s:property value='budgetCodeId'/></s:set>            			
		        			<select name="budgetCodeId" id="budgetCodes" class="form-control" style="width:190px;">
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
							<%--<font class="myErrorMessage">dd<s:property value="fieldErrors['budgetCodeId'][0]" /> </font>--%>						</div>
				  </div>
			 	</div>
		   		
			   	<div class="form-group">
			   		  <div class="myErrorMessage"><s:property value="fieldErrors['budgetType'][0]" /></div>
					  <div class="myErrorMessage"><s:property value="fieldErrors['actionId'][0]" /></div>
					  <div class="myErrorMessage"><s:property value="fieldErrors['budgetCodeId'][0]" /></div>
					  <div class="myErrorMessage"><s:property value="fieldErrors['budgetDescr'][0]" /></div>
					  
				 </div>
		   		
		   		<div class="form-group row"  style="margin-bottom: 10px;">		   		
		   			 <label class="col-sm-3 col-form-label"><s:text name="label.budgetDescr"/>&nbsp;</label>
				 	 <div class="col-sm-6">
				    	<input type="text" name="budgetDescr" id="budgetDescr" value="<s:property value="budgetDescr"/>" class="form-control" readonly="readonly">
				 	 </div>
				 	 <%-- <div>
				 	 	<font class="myErrorMessage"><s:property value="fieldErrors['budgetDescr'][0]" /> </font>
				 	 </div> --%>
		   		</div>
		   		<div class="form-group row"  style="margin-bottom: 10px;">		   		
		   			 <label class="col-sm-3 col-form-label"><s:text name="label.allocatedAmount"/>&nbsp;</label>
				 	 <div class="col-sm-6">
				    	<input type="text" name="allocatedAmount" id="allocatedAmount" value="<s:property value="getText('{0,number,#,##0.00}',{allocatedAmount})"/>" class="form-control" readonly="readonly">
				 	 </div>
		   		</div>
		   		<div class="form-group row"  style="margin-bottom: 10px;">		   		
		   			 <label class="col-sm-3 col-form-label"><s:text name="label.utilizedAmount"/>&nbsp;</label>
				 	 <div class="col-sm-6">
				    	<input type="text" name="utilizedAmount" id="utilizedAmount" value="<s:property value="getText('{0,number,#,##0.00}',{utilizedAmount})"/>" class="form-control" readonly="readonly">
				 	 </div>
		   		</div>
		   		<div class="form-group row"  style="margin-bottom: 10px;">		   		
		   			 <label class="col-sm-3 col-form-label"><s:text name="label.balanceAmount"/>&nbsp;</label>
				 	 <div class="col-sm-6">
				    	<input type="text" name="balanceAmount" id="balanceAmount" value="<s:property value="getText('{0,number,#,##0.00}',{balanceAmount})"/>" class="form-control" readonly="readonly">
				 	 </div>
		   		</div>
		   		<div class="form-group row"  style="margin-bottom: 10px;">		   		
		   			 <label class="col-sm-3 col-form-label"><s:text name="label.subject"/><font class="myErrorMessage"> &nbsp; * </font>&nbsp;</label>
				 	 <div class="col-sm-8">
				    	<input type="text" name="subject" id="" value="<s:property value="subject"/>" class="form-control">
				 	 </div>
		   		</div>
		   		<div class="form-group">
		   		  <div class="myErrorMessage"><s:property value="fieldErrors['subject'][0]" /></div>
			   </div>
<%-- 		   		<div align="right" class="col-sm-11">
		   		     <input type="submit" name="" value="<s:text name="button.next"/>">
		   		</div> --%>
		   		
			   <%-- </s:form> --%>
</body>
</html>