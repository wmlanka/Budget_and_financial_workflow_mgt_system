<%@page import="org.springframework.beans.factory.config.SetFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	 <script  type="text/javascript" src="assets/js/ajax.js"></script>
  	 <script type="text/javascript" src="bootstraps/js/jquery.min.js"></script> 
  	 
	 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  
 	 
 	 <script>
		$(document).ready(function(){
		  $('.toast').toast('show');
		});
	</script>
	
	   <!-- <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script> -->
        <script  src="https://code.highcharts.com/highcharts.js"></script>
        
         <script type="text/javascript">
        
            $(document).ready(function () {
            	
                var populationDataArray = [];
                var currentYear;
                
                $.getJSON("getBudetTypeChratAction", function (data) { 
                	
                    $.each(data.budgetTypeList, function (index) {
                    	
                      populationDataArray[index] = ['' + this.budgetType + '', this.utilizedAmount];
                        
                    });
                    
                    currentYear = data.currentYear;

                    var chart = {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    };

                    var title = {
                        text: 'Budget Utilization ' + currentYear
                    };

                    var tooltip = {
                        pointFormat: '{series.name}: <b>{point.y}</b>'
                    };

                    var plotOptions = {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.y}',
                                style: {
                                    fontSize: 10,
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    };

                    var series = [{
                            type: 'pie',
                            name: 'Budget Utilization',
                            data: populationDataArray
                        }];

                    var json = {};
                    json.chart = chart;
                    json.title = title;
                    json.tooltip = tooltip;
                    json.series = series;
                    json.plotOptions = plotOptions;
                    $('#budgetC').highcharts(json);
                });
            });
        </script>

<style>

 .main-header {
    display: flex;
    justify-content: space-between;
    /* margin: 20px; */
    margin-left: 10px;
    margin-right: 10px;
    margin-top : 0;
    padding: 10px;
    height: 70px; /* Force our height since we don't have actual content yet */
    background-color: #e3e4e6;
    color: slategray;
  }
  
  .main-overview {
  	margin-top : 10px;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); /* Where the magic happens */
    grid-auto-rows: 84px; /* 94px */
    grid-gap: 20px;
    /* margin: 20px; */
    margin-left: 10px;
    margin-right: 10px;
  }
  
  .overviewcard {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 20px;
    background-color: #d3d3;
  }
  
   .main-cards {
    column-count: 2;
    column-gap: 20px;
    margin: 10px;
    
  }
  
  .card {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    /* background-color: #82bef6; */
    /* margin-bottom: 20px; */
    -webkit-column-break-inside: avoid;
    /* padding: 24px; */
    box-sizing: border-box;
    vertical-align: center;
  }

  /* Force varying heights to simulate dynamic content */
  .card:first-child {
    height: 405px;
     /* width: 650px; */ 
    
  }

  .card:nth-child(2) {
    height: 405px;
    /* width: 280px; */
    align-content: right;
  }

   /* .card:nth-child(3) {
    height: 265px;
  } */ 
  
  .overviewcard__icon{
  	font-weight: bold;
  }
  
   .overviewcard__info{
  	font-weight: bold;
  }
</style>
</head>
<body>

<s:set var="notifi"><s:property value='notification'/></s:set>
 <s:if test="%{#notifi!=''}"> 
 	<div aria-live="polite" aria-atomic="true" style="position:  min-height: 200px; min-width: 500px;">
	  <div class="toast" data-autohide="false" style="position: absolute; top: 0; right: 0;border-width:6px;  
	    border-style:ridge;">
	    <div class="toast-header">
	      <strong class="mr-auto text-primary"><font color="black">Pending Notification</font> </strong>
	      <small class="text-muted"></small>
	      <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">&times;</button>
	    </div>
	    <div class="toast-body" style="background-color:#008000;">
	      <b><font color="white"><c:out value="${notification}"/></font></b>
	    </div>
	  </div>
  </div>
</s:if>
  
 <div class="main-header">
  <div class="main-header__heading">
  	<font color="black"><b>Payments Status on <c:out value="${systemDate}"/> </b></font><br>   
  	Requested <s:property value="getText('{0,number,#,##0.00}',{todayRequestedAmount})"/>
  </div>
 </div>
  
<div class="main-overview">
  <div class="overviewcard">
    <div class="overviewcard__icon img-approved">Approved Payments</div>
    <div class="overviewcard__info"><c:out value="${approvedPayCount}"/></div>
  </div>
    <div class="overviewcard">
    <div class="overviewcard__icon img-rejected">Rejected Payments</div>
    <div class="overviewcard__info"><c:out value="${rejectedPayCount}"/></div>
  </div>
  <div class="overviewcard">
    <div class="overviewcard__icon img-pending">Pending Payments</div>
    <div class="overviewcard__info"><c:out value="${pendingPayment}"/></div>
  </div>
  <div class="overviewcard">
    <div class="overviewcard__icon img-inviceDash">Pending Invoices/Source Docs</div>
    <div class="overviewcard__info"><c:out value="${pendingSourceDoc}"/></div>
  </div>

</div>


<div class="main-cards">
  <div class="card">
  	<div id="budgetC" style="float: left;width: 500px;"></div>
  </div>
 <div class="card">
   	<s:include value="DashboardPaymentChart.jsp"></s:include>
 </div>
</div>
</body>
</html>