<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>

<!--  <link rel="stylesheet" href="bootstraps/css/bootstrap2.min.css">
 --> <link rel="stylesheet" href="assets/css/commonStyle.css">
 
</head>
<body>
    <div align="center">
        <h1>Budget & Financial Workflow Management System</h1>
        <br>
        
        <s:if test="hasActionErrors()">
			<div class="alert-box error">
				<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
					&times;
				</span>
				<s:actionerror/>
			</div>
		</s:if>	
		
		<div>
	        <s:form action="loginAction" method="post">
	            <s:textfield key="label.username" name="user.userName" />
	            
	            <s:password key="label.password" name="user.password" />
	            
	            <s:submit value="Login" />
	        </s:form>  
	        
	     
        </div>          
    </div>  
</body>
</html>