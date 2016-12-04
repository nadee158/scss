<html>
<head>
<style>

	table {

	  border: 2px solid black;
	  width: 80%;
	  height: 50px;
	  text-align: center;
	  border-collapse: collapse;
	  
	}
	
	td {
	
	   border: 1px solid black;
	   border-bottom: 2px solid black; 
	   vertical-align: center;
	   color: #FF0000;
	   font: bold 20px/25px Arial, serif;
	}
	
	th{
	
	   border: 1px solid black;
	   background-color: #D9D9D9;
	   color: #870050;
	   font: bold 20px/30px Arial, serif; 
	}

</style>
</head>
<body>

<h2><u>Health Kiosk Notification</u></h2>
<h3>Please be alert that the following's kiosk's health level were low. </h3>

<#assign count = 1> 
<table>
  <tr>
    <th>KIOSK NO.</th>
	<th>ISSUE</th>
    <th>DESCRIPTION</th>    
  </tr>
  
  

<#list resultMap?keys as key> 

    <#assign rowspan = resultMap[key]?size>
    <#assign count = resultMap[key]?size>
    
    <tr>
  	<td rowspan=${rowspan}>${key}</td>
    
    <#list resultMap[key] as issue>
    	  <td>${issue.root}</td>
		  <td>${issue.description}</td>
		  </tr>
		 
		  <#assign count = (count-1)>
     		<#if count > 1>
  				<tr>
			</#if>
	</#list>
   
    
    
</#list> 

  
</table>

<h3>Kindly attend the mentioned kiosk immediately to avoid hiccups during operation.</h3>
	<b>Thank you</b>,<br/>
	Logistics Department<br/>
	Westports Holdings Berhad
</body>
</html>