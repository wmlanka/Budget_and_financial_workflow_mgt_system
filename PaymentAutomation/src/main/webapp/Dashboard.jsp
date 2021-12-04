<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Dashboard</title>
    
    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="bootstraps/css/bootstrap.min.css">
	<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">-->    
    
    <!-- Our Custom CSS -->
    <link rel="stylesheet" href="bootstraps/css/style.css">

    <!-- Font Awesome JS -->
	<script type="text/javascript" src="bootstraps/js/solid.js"></script>   
	<!-- <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script> -->
	
	<script type="text/javascript" src="bootstraps/js/fontawesome.js"></script>  
	<!-- <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script> -->	
	
	<style type="text/css">
		.img-profile{height:2rem;width:2rem}
		.rounded-circle{border-radius:50%!important}
		 .dropdown-menu{position:absolute;padding: 0;}
		 .dropdown-menu-right{right:0;left:auto}
		 .shadow{box-shadow:0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important}
		  .dropdown.no-arrow .dropdown-toggle::after{display:none}
		  .back{background-color: "";}
		   .dropdown-item-text{display:block;padding:.25rem 1.5rem;color:#3a3b45;font-family: sans-serif;}
	</style>	
	
	<script type="text/javascript">
		function getStakeholderPage(){
			url = 'stakeholderAction.action?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getLedgerAccountPage(){
			url = 'ledgerAccountAction?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getApprovalDocPage(){
			url = 'approvalDocAction?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getBudgetPage(){
			url = 'budgetAction?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getBudgetReport(){
			url = 'budgetInquiryAction?&rand='+Math.random()*9999999+'&command=null';
		    window.open(url,"mainbody",'');
		}
		
		function getActionCode(){
			url = 'actionCodeAction?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getSourceDoc(){
			url = 'sourceDocAction?&rand='+Math.random()*9999999+"&command=null";
		    window.open(url,"mainbody",'');
		}
		
		function getPaymentReport(){
			url = 'paymentInquiryAction?&rand='+Math.random()*9999999+"&command=null";
		    window.open(url,"mainbody",'');
		}
		
		function getDailyReports(){
			url = 'dailyReportAction?&rand='+Math.random()*9999999+"&command=null";
		    window.open(url,"mainbody",'');
		}
		
		function getBudgetCode(){
			url = 'budgetCodeAction?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getPRCreation(){
			url = 'paymentRequestAction?&rand='+Math.random()*9999999+"&command=add";
		    window.open(url,"mainbody",'');
		}
		
		function getPRExplorer(){
			url = 'prExplorerAction?&rand='+Math.random()*9999999+"&command=null";
		    window.open(url,"mainbody",'');
		}
		
		function getDashboardPage(){
			url = 'dashboardAction?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getCharts(){
			url = 'chartAction?&rand='+Math.random()*9999999;
		    window.open(url,"mainbody",'');
		}
		
		function getUserPage(){
			url = 'userManageAction?&rand='+Math.random()*9999999+"&command=null";
		    window.open(url,"mainbody",'');
		}
	</script>
	
	<style type="text/css">
		.navlink1 {
		    /* display: block; */
		    padding: .5rem 1rem;
		}
	</style>
</head>

<body onload="getDashboardPage();" style=" overflow-y: scroll;">
	<s:set var="uType"><s:property value='userType'/></s:set>
	<s:set var="fUser"><s:property value='financeUser'/></s:set>
	
    <div class="wrapper">
        <!-- Sidebar  -->
        <nav id="sidebar">
            <div class="sidebar-header">
                <h5>Budget & Financial Workflow Management System</h5>
            </div>

            <ul class="list-unstyled components">
                <!-- <p>Dummy Heading</p> -->
                <li>
                    <a href="#" onclick="getDashboardPage();">Dashboard </a>
                </li>
               
                <s:if test="%{#uType=='SYSTEM_ADMIN'}">
		        	    <li>  <!-- class="active" -->
		                    <a href="#userSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">User Management</a>
		                    <ul class="collapse list-unstyled" id="userSubmenu">
		                        <li>
		                            <a href="#" onclick="getUserPage();">User Access</a>
		                        </li>
		                    </ul>
		                </li>
			    </s:if>
                
                <s:if test="%{#uType!='BUDGET_USER'}">
	                <li>  <!-- class="active" -->
	                    <a href="#refSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Reference Data</a>
	                    <ul class="collapse list-unstyled" id="refSubmenu">
	                    
	                        <li>
	                            <a href="#" onclick="getStakeholderPage();">Stakeholder</a>
	                        </li>
	                        
	                        <s:if test='%{#uType=="SYSTEM_ADMIN" || #fUser=="Y"}'>
		                        <li>
		                            <a href="#" onclick="getLedgerAccountPage();">Ledger Account</a>
		                        </li>
	                        </s:if>
	                        
	                        <li>
	                            <a href="#" onclick="getApprovalDocPage();">Approval Documents</a>
	                        </li>
	                        
	                    </ul>
	                </li>
                </s:if>
                
                <s:if test="%{#uType=='BUDGET_USER' || #uType=='SYSTEM_ADMIN'}">
	                <li>
	                    <a href="#budgetSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Budget</a>
	                    <ul class="collapse list-unstyled" id="budgetSubmenu">
	                        <li>
	                            <a href="#" onclick="getActionCode()">Action Code</a>
	                        </li>
	                        <li>
	                            <a href="#" onclick="getBudgetCode()">Budget Code</a>
	                        </li>
	                        <li>
	                            <a href="#" onclick="getBudgetPage();">Manage Budget</a>
	                        </li>
	                    </ul>
	                </li>
                </s:if>
               
               <s:if test="%{#uType!='BUDGET_USER'}"> 
	               <li>
		              <a href="#transSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Transaction Module</a>
		              <ul class="collapse list-unstyled" id="transSubmenu">
		                <li>
		                    <a href="#" onclick="getSourceDoc();">Invoice/Source Document</a>
		                </li>
		                
		                <s:if test="%{#uType=='SYSTEM_ADMIN' || #uType=='SYSTEM_OPERATOR'}">
			                <li>
			                    <a href="#" onclick="getPRCreation();">Payment Request</a>
			                </li>
		                </s:if>
		                
		                <li>
		                    <a href="#" onclick="getPRExplorer();">Payment Explorer</a>
		                </li>
		              </ul>
		          </li>
	          </s:if>
	          
 			  <li>
 			  	  <a href="#reportSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Reports & Charts</a>
	                <ul class="collapse list-unstyled" id="reportSubmenu">
	                	<li>
		                    <a href="#" onclick="getDailyReports();">Daily Reports</a>
		                </li>
 		                
 		                <s:if test="%{#uType!='SYSTEM_OPERATOR'}">
 			                <li>
			                    <a href="#" onclick="getPaymentReport();">Payments Summary</a>
			                </li>
		                </s:if>
		                
 		               <s:if test='%{#uType=="SYSTEM_ADMIN" || (#uType=="OFFICER_A" && #fUser=="Y") || (#uType=="OFFICER_B" && #fUser=="Y")
 		               		|| #uType=="SUPER_USER" || #uType=="BUDGET_USER"}'>
 			                <li>
			                    <a href="#" onclick="getBudgetReport();">Budget Summary</a>
			                </li>
			           </s:if>
			            
			       <s:if test='%{#uType=="SYSTEM_ADMIN" || (#uType=="OFFICER_A" && #fUser=="Y") || (#uType=="OFFICER_B" && #fUser=="Y") 
			       				|| #uType=="SUPER_USER" || #uType=="BUDGET_USER"}'>
		                <li>
		                    <a href="#" onclick="getCharts();">Chart - Budget Utilization</a>
		                </li>
		            </s:if>
		                
	                </ul>
	          </li>
            </ul>

         <!--    <ul class="list-unstyled CTAs">
                <li>
                    <a href="https://bootstrapious.com/tutorial/files/sidebar.zip" class="download">Download source</a>
                </li>
                <li>
                    <a href="https://bootstrapious.com/p/bootstrap-sidebar" class="article">Back to article</a>
                </li>
            </ul> -->
        </nav>

        <!-- Page Content  -->
        <div id="content">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">

                    <button type="button" id="sidebarCollapse" class="btn btn-info">
                        <i class="fas fa-align-left"></i>
                        <span></span>
                    </button>
                    <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <i class="fas fa-align-justify"></i>
                    </button>	
                    <div class="" id="navbarSupportedContent" style="vertical-align: bottom;">
                    	<ul class="nav navbar-nav ml-auto">
                           <a class="nav-link" href="#"><c:out value="${userTypeStr}"/></a>
                        </ul>
                    </div>	
                   <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    	<ul class="nav navbar-nav ml-auto">
                              <a class="nav-link" href="#"><c:out value="${costCenter}"/></a>
                        </ul>
                    </div>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    
                        <ul class="nav navbar-nav ml-auto">

                            <li class="nav-item dropdown no-arrow">
                              <%--   <a class="nav-link dropdown-toggle"  href="#">Wellcome &nbsp; <c:out value="${userName}"/>
                                 <img class="img-profile rounded-circle" src="assets/images//undraw_profile.svg">
                                 </a>  --%>
                                <a class="nav-link dropdown-toggle back" href="#" id="userDropdown" role="button"
	                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                                <span class="mr-2 d-none d-lg-inline text-gray-600">Wellcome &nbsp; <c:out value="${userName}"/></span>
	                                <img class="img-profile rounded-circle" src="assets/images//undraw_profile.svg">
                              	</a>
                                  <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
	                                aria-labelledby="userDropdown">
	                               <!--  <a class="dropdown-item" href="#">
	                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
	                                    Profile
	                                </a>
	                                <a class="dropdown-item" href="#">
	                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
	                                    Settings
	                                </a>
	                                <a class="dropdown-item" href="#">
	                                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
	                                    Activity Log
	                                </a> -->
	                                <!-- <div class="dropdown-divider"></div> -->
	                                <a class="dropdown-item" href="logoutAction?command"  data-target="#logoutModal" style="font-size: 0.9em !important;">
	                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
	                                    Logout
	                                </a>
	                            </div>
                            </li>
                       
                            <!-- <li class="nav-item">
                                <a class="nav-link" href="logoutAction?command">Log out</a>
                            </li> -->
                        </ul>
                    </div>
                    
                    
                    
                </div>
            </nav>

            <!-- <div class="line"></div> -->
            
          <!-- Load Page -->
	      <div id="content" class="p-4 p-md-0" style="scrolling:no"> <!-- height="850px" -->
	          <iframe width="100%" height="630px" frameborder="0" name="mainbody"  id="mainbody" > </iframe> 
	      </div>

        </div>
    </div>

    <!-- jQuery CDN - Slim version (=without AJAX) -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <!-- Popper.JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $('#sidebarCollapse').on('click', function () {
                $('#sidebar').toggleClass('active');
            });
        });
    </script>
</body>

</html>