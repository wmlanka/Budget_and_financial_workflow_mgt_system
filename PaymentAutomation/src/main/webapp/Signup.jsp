<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign In</title>
 	 <link rel="stylesheet" href="assets/css/signup.css">
 	 <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
<!--  	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 --> 	 
 
 	<script type="text/javascript">
	 	if (top.location != location){
	 	 	top.location.href = location.href;
	 	}
 	</script>
</head>
<body>
<div class="container-fluid px-1 px-md-5 px-lg-1 px-xl-5 py-5 mx-auto">
    <div class="card card0 border-0">
        <div class="row d-flex">
            <div class="col-lg-6">
                <div class="card1 pb-5">
                    <div class="row"> <img src="assets/images/peoples-bank.png" class="logo"> </div>
<!--                     <div class="row"> <img src="https://i.imgur.com/CXQmsmF.png" class="logo"> </div> -->                    
                    <div class="row px-3 justify-content-center mt-4 mb-4 border-line"> <img src="assets/images/pb new.jpg" class="image"> </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card2 card border-0 px-4 py-2"> <!-- <div class="card2 card border-0 px-4 py-5"> -->
                     <div class="row mb-4 px-3">
                        <h3 class="mb-0 mr-4 mt-2">Enterprise Budget & <br>Financial Workflow Management System</h3>                       
                       <!--  <div class="facebook text-center mr-3">
                            <div class="fa fa-facebook"></div>
                        </div>
                        <div class="twitter text-center mr-3">
                            <div class="fa fa-twitter"></div>
                        </div>
                        <div class="linkedin text-center mr-3">
                            <div class="fa fa-linkedin"></div>
                        </div> -->
                    </div> 
                     <div class="row px-3 mb-4">
                        <!-- <div class="line"></div> --> 
                        <%-- <small class="or text-center">
                        </small>
                        <div class="line"></div> --%>
                    </div> 
                    
                        
                    <div class="row px-3"> <label class="mb-1">
                                          <s:form action="loginAction" method="post">      
                    
                            <h6 class="mb-0 text-sm">User ID</h6>
                        </label> <input class="mb-4" type="text" name="userId" placeholder="Enter User Id"> </div>
                    <div class="row px-3"> <label class="mb-1">
                            <h6 class="mb-0 text-sm">Password</h6>
                        </label> <input type="password" name="password" placeholder="Enter password"> </div>
                        
                    <div class="row px-3 mb-4">
                    	 <s:if test="hasActionErrors()">
                    	 
                    	 <!-- <a onclick='javascript:alert("Login Error");return 0;'>
                    	 								
                    	 </a> -->
 
						 	<div class="error" style="color: red;">
								<%-- <span class="closebtn" onclick="this.parentElement.style.display='none'; ">
									&times;
								</span> --%>
								<s:actionerror/>
								<s:actionmessage/>
							</div> 
						</s:if>	
<!--                         <div class="custom-control custom-checkbox custom-control-inline"> <input id="chk1" type="checkbox" name="chk" class="custom-control-input"> <label for="chk1" class="custom-control-label text-sm">Remember me</label> </div> <a href="#" class="ml-auto mb-0 text-sm">Forgot Password?</a>
 -->                    </div>
                    <div class="row mb-3 px-3"> <button type="submit" class="btn btn-blue text-center">Login</button> 
                    </s:form>
                    </div>
<!--                     <div class="row mb-4 px-3"> <small class="font-weight-bold">Don't have an account? <a class="text-danger ">Register</a></small> </div>
 -->                </div>
            </div>
        </div>
        <div class="bg-blue py-4">
            <div class="row px-3"> <small class="ml-4 ml-sm-5 mb-2">Copyright &copy; 2021. All rights reserved.</small>
                <div class="social-contact ml-4 ml-sm-auto"> <span class="fa fa-facebook mr-4 text-sm"></span> <span class="fa fa-google-plus mr-4 text-sm"></span> <span class="fa fa-linkedin mr-4 text-sm"></span> <span class="fa fa-twitter mr-4 mr-sm-5 text-sm"></span> </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>