<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html>
<html lang="en">
  <head>
  	<title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900" rel="stylesheet">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="bootstraps/css/style.css">
  </head>
  <body>
		
		<div class="wrapper d-flex align-items-stretch">
			<nav id="sidebar">
				<div class="p-4 pt-5">
		  		<a href="#" class="img logo rounded-circle mb-5" style="background-image: url(assets/images/bank_logo.jpg);"></a>
	        <ul class="list-unstyled components mb-5">
	          <li>
	              <a href="#">Dashboard</a>
	          </li>
	          
	          <li>
	              <a href="#userSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">User Management</a>
	              <ul class="collapse list-unstyled" id="userubmenu">
	                <li>
	                    <a href="#">User</a>
	                </li>
	              </ul>
	          </li>
	          
	          <li class="active">
	            <a href="#referenceSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Reference Data</a>
	            <ul class="collapse list-unstyled" id="referenceSubmenu">
                <li>
                    <a href="<s:url action="stakeholderAction"/>">Stakeholder</a>
                </li>
                <li>
                    <a href="login.action">Ledger Account</a>
                </li>
                <li>
                    <a href="#">Approval Documents</a>
                </li>
	            </ul>
	          </li>

	          <li>
	              <a href="#budgetSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Budget</a>
	              <ul class="collapse list-unstyled" id="budgetSubmenu">
	                <li>
	                    <a href="#">Action Code</a>
	                </li>
	                <li>
	                    <a href="#">Budget Code</a>
	                </li>
	                <li>
	                    <a href="#">Manage Budget</a>
	                </li>
	              </ul>
	          </li>
	          
	          <li>
	              <a href="#transSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Transaction</a>
	              <ul class="collapse list-unstyled" id="transSubmenu">
	                <li>
	                    <a href="#">Source Document</a>
	                </li>
	                <li>
	                    <a href="#">Payment Request</a>
	                </li>
	                <li>
	                    <a href="#">Payment Explore</a>
	                </li>
	              </ul>
	          </li>
	          
 			  <li>
	              <a href="#">Reports</a>
	          </li>
	       
	        </ul>

	        <div class="footer">
	        	<p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib.com</a>
						  <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
	        </div>

	      </div>
    	</nav>      
		</div>

    <script src="bootstraps/js/jquery.min.js"></script>
    <script src="bootstraps/js/popper.js"></script>
    <script src="bootstraps/js/bootstrap.min.js"></script>
    <script src="bootstraps/js/main.js"></script>
  </body>
</html>