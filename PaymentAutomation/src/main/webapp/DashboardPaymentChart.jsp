<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@ taglib prefix="s" uri="/struts-tags" %>  
<%--<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="/struts-dojo-tags" prefix="sd" %> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

        <script type="text/javascript" src="https://code.highcharts.com/highcharts.js"></script>
        
        <script type="text/javascript">
        
            $(document).ready(function () {
            	
                var populationDataArray = [];
                var monthe = '';
                
                $.getJSON("getBudetTypeChratAction.action", function (data) {
                	
                    $.each(data.paymentDeptList, function (index) {
                    	
                        populationDataArray[index] = ['' + this.costCenter + '', this.prCount];
                        
                    });
                    
                    monthe = data.month;

                    var chart = {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    };

                    var title = {
                        text: monthe + ' Payments' 
                    };

                    var tooltip = {
                        pointFormat: '{series.name}: <b>{point.y}</b>'
                    };
                    
                    var xAxis = {
                    		type: 'category'
	                    
                    };
                    
                     var yAxis = {
                        title: {
                            text: 'No of Payments'
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
	                   /*   column: {
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
	                    } */ 
                    };

                    var series = [{
                            type: 'column',
                            name: 'Payments By Departments',
                            data: populationDataArray
                        }];

                    var json = {};
                    json.chart = chart;
                    json.title = title;
                    json.tooltip = tooltip;
                    json.series = series;
                    json.plotOptions = plotOptions;
                    json.xAxis = xAxis;
                    json.yAxis = yAxis;
                    $('#budget2').highcharts(json);
                });
            });
        </script>
        
</head>
<body>
	<div id="budget2" style="float: left;width: 500px;"></div>
</body>
</html>