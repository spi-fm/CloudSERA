<%@ page import="es.uca.pfc.User" %>
<%@ page import="es.uca.pfc.Slr" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <%-- Head Meta --%>
	<g:render template="headMeta" contextPath="/"/>

    <title>CloudSERA | Search</title>
    <link rel="icon" href="https://github.com/spi-fm/CloudSERA/raw/master/images/CloudSERA-sm.jpeg">

	<%-- CSS --%>
    <g:render template="css" contextPath="/"/>

</head>

<body onload="loadModal();">

    <div id="wrapper">
        <%-- Head --%>
        <g:render template="head" contextPath="/"/>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Searches in: <small>'${query}'</small></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <g:if test="${totalUserList + totalSlrList == 0}">
            	<div class="row">
	            	<div class="col-lg-12">
	   	            	<h3>No results.</h3>
	            	</div>
	            </div>
            </g:if>

            <g:if test="${userList.size() > 0}">
	            <div class="row">
	            	<div class="col-lg-12">
	   	            	<h3>Users <small>${totalUserList} founds. ${userList.size()} showing.</small></h3>
	            	</div>
	            </div>

	            <div class="row" style="margin-bottom: 20px;">
	               	<g:if test="${userList.size() > 0}">
	               		<g:each in="${userList}" var="userInstance">
		               		<div class="col-lg-4" style="margin-bottom: 10px;">
			                    <div class="media">
			                    	<div class="media-left">
			                    		<img src="${userInstance.url_foto}" alt="${userInstance.display_name}" class="media-object" style="width: 70px; height: 70px;" />
			                    	</div>
			                    	<div class="media-body">
			                    		<h4 class="media-heading"><g:link controller="user" action="show" params="[guid: "${userInstance.guid}"]">${userInstance.display_name}</g:link></h4>
			                    		Registred: ${formatDate(format: 'dd/MMM/yyyy', date: userInstance.fechaRegistro)} <br/>
			                    		SLR's created: ${userInstance.slrs.size()}
			                    	</div>
			                    </div>
		                    </div>
	               		</g:each>
	               	</g:if>
	            </div>
           </g:if>

           <g:if test="${slrList.size() > 0}">
	           <div class="row">
	            	<div class="col-lg-12">
	   	            	<h3>SLR's <small>${totalSlrList} founds. ${slrList.size()} showing.</small></h3>
	            	</div>
	           </div>

	            <div class="row" style="margin-bottom: 20px;">
	               	<g:if test="${slrList.size() > 0}">
	               		<g:each in="${slrList}" var="slrInstance">
		               		<div class="col-lg-4" style="margin-bottom: 10px;">
			                    <div class="media">
			                    	<div class="media-left">
			                    		<img src="${resource(dir:'images/logger',file:'libro.jpg')}" alt="${slrInstance.title}" class="media-object" style="width: 70px; height: 70px;" />
			                    	</div>
			                    	<div class="media-body">
			                    		<h4 class="media-heading"><g:link controller="slr" action="show" params="[guidSlr: "${slrInstance.guid}"]">${slrInstance.title}</g:link></h4>
			                    		Created by <g:link controller="user" action="show" params="[guid: "${slrInstance.userProfile.guid}"]">${slrInstance.userProfile.display_name}</g:link><br />
			                    		Total references: ${slrInstance.totalReferences}
			                    	</div>
			                    </div>
		                    </div>
	               		</g:each>
	               	</g:if>
	            </div>
	        </g:if>

        </div>
        <!-- /#page-wrapper -->

		<%-- Foot --%>
	    <g:render template="foot" contextPath="/"/>

    </div>
    <!-- /#wrapper -->

    <%-- JavaScript --%>
    <g:render template="javascript" contextPath="/"/>
</body>

</html>
