<%@ page import="es.uca.pfc.User" %>
<%@ page import="es.uca.pfc.UserProfile" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <%-- Head Meta --%>
	<g:render template="headMeta" contextPath="/"/>

    <title>
   		CloudSERA | My Loggers
    </title>
    <link rel="icon" href="https://github.com/spi-fm/CloudSERA/raw/master/images/CloudSERA-sm.jpeg">

    <%-- CSS --%>
    <g:render template="css" contextPath="/"/>

</head>

<body>

    <div id="wrapper">

        <%-- Head --%>
        <g:render template="head" contextPath="/"/>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
		    		<h1 class="page-header">My Loggers</h1>

			    	<ol class="breadcrumb">
					  <li><g:link controller="index" action="menu">Home</g:link></li>
					  <li class="active">My Loggers</li>
					</ol>
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <div class="row">

                <div class="col-lg-12">

                	<div style="width: 100%;">
						<ul id="myTab" class="nav nav-tabs">
							<li class="${(currentOnglet == 'all' ? 'active' : '')}"><a href="#allLoggers" data-toggle="tab">All</a></li>
							<li class="${(currentOnglet == 'my' ? 'active' : '')}"><a href="#myLoggers" data-toggle="tab">My Loggers</a></li>
							<li class="${(currentOnglet == 'friend' ? 'active' : '')}"><a href="#friendLoggers" data-toggle="tab">Friends Loggers</a></li>
						</ul>

						<div id="myTabContent" class="tab-content" style="margin-top: 20px;">

						<%-- TODOS LOS LOGGERS --%>
						<div class="tab-pane fade in active" id="allLoggers">
							<div class="chat-panel panel panel-default">
								<div class="panel-heading">
									<i class="fa fa-comments fa-fw"></i>
									Logger
								</div>
								<div class="panel-body" style="min-height: 500px;">
									<ul class="chat">
										<g:if test="${totalAllLoggers == 0}">
											<li class="left clearfix">
												No loggers.
											</li>
										</g:if>
										<g:else>
											<g:each in="${allLoggers}" var="loggerInstance">
												<li class="left clearfix">
													<span class="chat-img pull-left">
														<g:if test="${loggerInstance.tipo.contains('fr-')}">
															<img src="${loggerInstance.friendProfile.url_foto}" alt="User Avatar" class="img-circle" style="width: 50px; height: 50px;" />
														</g:if>
														<g:else>
															<img src="${loggerInstance.profile.url_foto}" alt="User Avatar" class="img-circle" style="width: 50px; height: 50px;" />
														</g:else>
													</span>
													<div class="chat-body clearfix">
														<div class="header">
															<%--<g:if test="${userProfileInstance.id != loggerInstance.profile.id}"> --%>
															<g:if test="${loggerInstance.tipo.contains('fr-')}">
																<strong class="primary-font">${loggerInstance.friendProfile.display_name}</strong>
															</g:if>
															<g:else>
																<strong class="primary-font">Tú</strong>
															</g:else>
															<small class="pull-right text-muted">
																<i class="fa fa-clock-o fa-fw"></i> ${loggerInstance.timeString}
															</small>
														</div>
														<p>
															<g:if test="${loggerInstance.tipo == 'bienvenida'}">
																<img src="${resource(dir:'images/logger',file:'start.png')}" alt="" width="20" height="20" /> Welcome to Systematic Review !
															</g:if>
															<g:elseif test="${loggerInstance.tipo == 'fr-bienvenida'}">
																<img src="${resource(dir:'images/logger',file:'start.png')}" alt="" width="20" height="20" /> He's registred in SR.
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'crear'}">
																<img src="${resource(dir:'images/logger',file:'libro.jpg')}" alt="" width="20" height="20" /> You have created a new SR: <g:link controller="slr" action="myList">${loggerInstance.slr.title}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-crear'}">
																<img src="${resource(dir:'images/logger',file:'libro.jpg')}" alt="" width="20" height="20" /> He has created a new SR: ${loggerInstance.slr.title}
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'buscar'}">
																<img src="${resource(dir:'images/logger',file:'lupa.jpg')}" alt="" width="20" height="20" /> You have created news searchs: <g:link controller="slr" action="searchs" params="[guid: "${loggerInstance.slr.guid}"]">${loggerInstance.slr.title}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-buscar'}">
																<img src="${resource(dir:'images/logger',file:'lupa.jpg')}" alt="" width="20" height="20" /> He has created news searchs: ${loggerInstance.slr.title}
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'seguir'}">
																<img src="${resource(dir:'images/logger',file:'friend.png')}" alt="" width="20" height="20" /> You are following to <g:link controller="user" action="show" params="[guid: "${loggerInstance.friendProfile.guid}"]">${loggerInstance.friendProfile.display_name}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-seguir'}">
																<img src="${resource(dir:'images/logger',file:'friend.png')}" alt="" width="20" height="20" /> He is following to ${loggerInstance.friendFriendProfile.display_name}
															</g:elseif>
															<g:else>
																No definido
															</g:else>
														</p>
													</div>
												</li>
											</g:each>
										</g:else>
									</ul>
								</div>
							</div>
							<g:render template="paginationAllLogger" contextPath="/index"/>
						</div>

						<%-- LOGGERS FRIENDS --%>
						<div class="tab-pane fade" id="friendLoggers">
							<div class="chat-panel panel panel-default">
								<div class="panel-heading">
									<i class="fa fa-comments fa-fw"></i>
									Friends Logger
								</div>
								<div class="panel-body" style="min-height: 500px;">
									<ul class="chat">
										<g:if test="${totalFriendsLoggers == 0}">
											<li class="left clearfix">
												No loggers
											</li>
										</g:if>
										<g:else>
											<g:each in="${friendsLogers}" var="loggerInstance">
												<li class="left clearfix">
													<span class="chat-img pull-left">
														<g:if test="${loggerInstance.tipo.contains('fr-')}">
															<img src="${loggerInstance.friendProfile.url_foto}" alt="User Avatar" class="img-circle" style="width: 50px; height: 50px;" />
														</g:if>
														<g:else>
															<img src="${loggerInstance.profile.url_foto}" alt="User Avatar" class="img-circle" style="width: 50px; height: 50px;" />
														</g:else>
													</span>
													<div class="chat-body clearfix">
														<div class="header">
															<%--<g:if test="${userProfileInstance.id != loggerInstance.profile.id}"> --%>
															<g:if test="${loggerInstance.tipo.contains('fr-')}">
																<strong class="primary-font">${loggerInstance.friendProfile.display_name}</strong>
															</g:if>
															<g:else>
																<strong class="primary-font">Tú</strong>
															</g:else>
															<small class="pull-right text-muted">
																<i class="fa fa-clock-o fa-fw"></i> ${loggerInstance.timeString}
															</small>
														</div>
														<p>
															<g:if test="${loggerInstance.tipo == 'bienvenida'}">
																<img src="${resource(dir:'images/logger',file:'start.png')}" alt="" width="20" height="20" /> Welcome to Systematic Review !
															</g:if>
															<g:elseif test="${loggerInstance.tipo == 'fr-bienvenida'}">
																<img src="${resource(dir:'images/logger',file:'start.png')}" alt="" width="20" height="20" /> He's registred in SR.
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'crear'}">
																<img src="${resource(dir:'images/logger',file:'libro.jpg')}" alt="" width="20" height="20" /> You have created a new SR: <g:link controller="slr" action="myList">${loggerInstance.slr.title}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-crear'}">
																<img src="${resource(dir:'images/logger',file:'libro.jpg')}" alt="" width="20" height="20" /> He has created a new SR: ${loggerInstance.slr.title}
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'buscar'}">
																<img src="${resource(dir:'images/logger',file:'lupa.jpg')}" alt="" width="20" height="20" /> You have created news searchs: <g:link controller="slr" action="searchs" params="[guid: "${loggerInstance.slr.guid}"]">${loggerInstance.slr.title}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-buscar'}">
																<img src="${resource(dir:'images/logger',file:'lupa.jpg')}" alt="" width="20" height="20" /> He has created news searchs: ${loggerInstance.slr.title}
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'seguir'}">
																<img src="${resource(dir:'images/logger',file:'friend.png')}" alt="" width="20" height="20" /> You are following to <g:link controller="user" action="show" params="[guid: "${loggerInstance.friendProfile.guid}"]">${loggerInstance.friendProfile.display_name}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-seguir'}">
																<img src="${resource(dir:'images/logger',file:'friend.png')}" alt="" width="20" height="20" /> He is following to ${loggerInstance.friendFriendProfile.display_name}
															</g:elseif>
															<g:else>
																No definido
															</g:else>
														</p>
													</div>
												</li>
											</g:each>
										</g:else>
									</ul>
								</div>
							</div>
							<g:render template="paginationFriendsLogger" contextPath="/index"/>
						</div>


						<%-- Mis loggers --%>
						<div class="tab-pane fade" id="myLoggers">
							<div class="chat-panel panel panel-default">
								<div class="panel-heading">
									<i class="fa fa-comments fa-fw"></i>
									Mis Loggers
								</div>
								<div class="panel-body" style="min-height: 500px;">
									<ul class="chat">
										<g:if test="${totalMyLoggers == 0}">
											<li class="left clearfix">
												No existen loggers disponibles.
											</li>
										</g:if>
										<g:else>
											<g:each in="${myLoggers}" var="loggerInstance">
												<li class="left clearfix">
													<span class="chat-img pull-left">
														<g:if test="${loggerInstance.tipo.contains('fr-')}">
															<img src="${loggerInstance.friendProfile.url_foto}" alt="User Avatar" class="img-circle" style="width: 50px; height: 50px;" />
														</g:if>
														<g:else>
															<img src="${loggerInstance.profile.url_foto}" alt="User Avatar" class="img-circle" style="width: 50px; height: 50px;" />
														</g:else>
													</span>
													<div class="chat-body clearfix">
														<div class="header">
															<%--<g:if test="${userProfileInstance.id != loggerInstance.profile.id}"> --%>
															<g:if test="${loggerInstance.tipo.contains('fr-')}">
																<strong class="primary-font">${loggerInstance.friendProfile.display_name}</strong>
															</g:if>
															<g:else>
																<strong class="primary-font">Tú</strong>
															</g:else>
															<small class="pull-right text-muted">
																<i class="fa fa-clock-o fa-fw"></i> ${loggerInstance.timeString}
															</small>
														</div>
														<p>
															<g:if test="${loggerInstance.tipo == 'bienvenida'}">
																<img src="${resource(dir:'images/logger',file:'start.png')}" alt="" width="20" height="20" /> Welcome to Systematic Review !
															</g:if>
															<g:elseif test="${loggerInstance.tipo == 'fr-bienvenida'}">
																<img src="${resource(dir:'images/logger',file:'start.png')}" alt="" width="20" height="20" /> He's registred in SR.
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'crear'}">
																<img src="${resource(dir:'images/logger',file:'libro.jpg')}" alt="" width="20" height="20" /> You have created a new SR: <g:link controller="slr" action="myList">${loggerInstance.slr.title}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-crear'}">
																<img src="${resource(dir:'images/logger',file:'libro.jpg')}" alt="" width="20" height="20" /> He has created a new SR: ${loggerInstance.slr.title}
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'buscar'}">
																<img src="${resource(dir:'images/logger',file:'lupa.jpg')}" alt="" width="20" height="20" /> You have created news searchs: <g:link controller="slr" action="searchs" params="[guid: "${loggerInstance.slr.guid}"]">${loggerInstance.slr.title}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-buscar'}">
																<img src="${resource(dir:'images/logger',file:'lupa.jpg')}" alt="" width="20" height="20" /> He has created news searchs: ${loggerInstance.slr.title}
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'seguir'}">
																<img src="${resource(dir:'images/logger',file:'friend.png')}" alt="" width="20" height="20" /> You are following to <g:link controller="user" action="show" params="[guid: "${loggerInstance.friendProfile.guid}"]">${loggerInstance.friendProfile.display_name}</g:link>
															</g:elseif>
															<g:elseif test="${loggerInstance.tipo == 'fr-seguir'}">
																<img src="${resource(dir:'images/logger',file:'friend.png')}" alt="" width="20" height="20" /> He is following to ${loggerInstance.friendFriendProfile.display_name}
															</g:elseif>
															<g:else>
																No definido
															</g:else>
														</p>
													</div>
												</li>
											</g:each>
										</g:else>
									</ul>
								</div>
							</div>
							<g:render template="paginationMyLogger" contextPath="/index"/>
						</div>

					</div>
    				</div>
                </div>
            </div>

        </div>
        <!-- /#page-wrapper -->

		<%-- Foot --%>
        <g:render template="foot" contextPath="/"/>

    </div>
    <!-- /#wrapper -->

    <%-- JavaScript --%>
    <g:render template="javascript" contextPath="/" />

</body>

</html>
