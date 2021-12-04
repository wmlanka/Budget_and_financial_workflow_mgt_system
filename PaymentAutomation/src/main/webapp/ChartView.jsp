<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@page import="java.text.DecimalFormat" %>   
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%-- <%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="/struts-dojo-tags" prefix="sd" %> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

	 <link rel="stylesheet" href="assets/css/commonStyle.css">
 	 <link rel="stylesheet" href="assets/css/myStyle.css">
 	  <!-- <script  type="text/javascript" src="assets/js/ajax.js"></script> -->
  	 <script type="text/javascript" src="bootstraps/js/jquery.min.js"></script> 

        
        <script type="text/javascript" src="https://code.highcharts.com/highcharts.js"></script>
        
        <script type="text/javascript">
        
            $(document).ready(function () {
            	
                var monthArray = [];
                var capitalBudgetArray =[];
                var marketingBudgetArray =[];
                var retailBudgetArray =[];
                var iTBudgetArray =[];
                
                $.getJSON("getMonthBudgetChratAction.action", function (data) {
                	
                    $.each(data.monthList, function (index) {
                    	
                    	monthArray[index] = [''+this.budgetMonth+''];
                    	
                    });
                    
 			 		$.each(data.capitalBudgetList, function (index) {
                    	
 						capitalBudgetArray[index] = [this.utilizedAmount];
                    	
                    });
 					
					$.each(data.marketingBudgetList, function (index) {
				                    	
						marketingBudgetArray[index] = [this.utilizedAmount];
                    	
                    });
					
					$.each(data.retailBudgetList, function (index) {
				    	
						retailBudgetArray[index] = [this.utilizedAmount];
				    	
				    }); 
					
					$.each(data.iTBudgetList, function (index) {
				    	
						iTBudgetArray[index] = [this.utilizedAmount];
				    	
				    });


                    var chart = {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type : 'column'
                    };

                    var title = {
                        text: 'Monthly Budget Utilization'
                    };

                    var tooltip = {
                        pointFormat: '{series.name}: <b>{point.y}</b>'
                    };
                    
                    var xAxis = {
                    		/* type: 'category' */
                    		categories: monthArray
	                    
                    };
                    
                     var yAxis = {
                        title: {
                            text: 'Utilized Amount'
                        }
                    }; 
                    
                /*     var xAxis= {
                        type: 'category',
                        labels: {
                            formatter: function() {
                                var labelVal = this.value;
                                var reallyVal = '';
                                var lvl = labelVal.length;
                                if(lvl > 1){
                                    for(var i=1;i<=lvl;i++){
                                        reallyVal += labelVal.substr(i-1,1)+"<br/>";
                                    }
                                }
                                return reallyVal.substring(0,reallyVal.length-5);
                            }
                        }
                    }; */

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
	                    /*   bar: {
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
	                    }  */ 
                    };

                /*     var series = [{
                            type: 'column', 
                            name: 'A',
                            data: populationDataArray2
                        },
                        {
                            type: 'column', 
                            name: 'B',
                            data: populationDataArray
                        }]; */
                    
                    /*var series = [{
                        name: 'Capital Expenses',
                        data: [0, 0, 650000, 320000, 650000,0, 0, 650000, 320000]
                    }, {
                        name: 'Marketing Expenses',
                        data: [0, 65000, 48000, 800000, 40000,0, 0, 258,25693]
                    }, {
                        name: 'Retail Banking Expenses',
                        data: [0, 40000, 358000, 426000, 350500,0, 0, 650000, 25693]
                    }, {
                        name: 'IT Expenses',
                        data: iTBudgetArray
                    }];*/
                    
                    var series = [{
                        name: 'Capital Expenses',
                        data: capitalBudgetArray
                    }, {
                        name: 'Marketing Expenses',
                        data: marketingBudgetArray
                    }, {
                        name: 'Retail Banking Expenses',
                        data: retailBudgetArray
                    }, {
                        name: 'IT Expenses',
                        data: iTBudgetArray
                    }];

                    var json = {};
                    json.chart = chart;
                    json.title = title;
                    json.tooltip = tooltip;
                    json.series = series;
                    json.plotOptions = plotOptions;
                    json.xAxis = xAxis;
                    json.yAxis = yAxis;
                    $('#budgetm').highcharts(json);
                });
            });
        </script>
        
</head>
<body>
	<div id="budgetm" style=""></div>
	
	
	 <s:if test="hasActionMessages()"> 
			 <%if((((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).getActionMessages()).toString().contains("not found")){%>							
					<div class="alert-box notice">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span>
						<s:actionmessage/>
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
					</div>
			  <% }else{ %> 
				  <div class="alert-box warning">
						<span class="closebtn" onclick="this.parentElement.style.display='none'; ">
							&times;
						</span> 
						<s:actionmessage/>
						<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>
						
					</div>
		  	  <%} %>
	</s:if>
</body>
</html>