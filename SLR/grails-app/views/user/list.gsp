<%@ page import="es.uca.pfc.User" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <%-- Head Meta --%>
	<g:render template="headMeta" contextPath="/"/>

    <title>CloudSERA | Users</title>
    <link rel="icon" href="https://github.com/spi-fm/CloudSERA/raw/master/images/CloudSERA-sm.jpeg">

	<%-- CSS --%>
    <g:render template="css" contextPath="/"/>

    <script type="text/javascript">
	function getIdUser(id)
	{
		document.getElementById("guidUserDisabled").value = id.toString();
		document.getElementById("guidUserEnabled").value = id.toString();
		document.getElementById("guidUserRoleAdmin").value = id.toString();
		document.getElementById("guidUserRoleUser").value = id.toString();
	}
    </script>

</head>

<body>

	<div class="modal fade" id="myModalEnabled" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">Enable User</h4>
				</div>
				<g:form controller="user" action="enabledUser" id="myFormEnabled" name="myFormEnabled" method="POST">
					<g:hiddenField name="guidUserEnabled" value="0" />
					<div class="modal-body">
						Do you want to enable this user ?
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal" type="button">No</button>
						<g:submitButton id="boton" name="boton" class="btn btn-primary" value="Yes"/>
					</div>
				</g:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModalDisabled" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">Disabled User</h4>
				</div>
				<g:form controller="user" action="disabledUser" id="myFormDisabled" name="myFormDisabled" method="POST">
					<g:hiddenField name="guidUserDisabled" value="0" />
					<div class="modal-body">
						Do you want to disable this user ?
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal" type="button">No</button>
						<g:submitButton id="boton" name="boton" class="btn btn-primary" value="Yes"/>
					</div>
				</g:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModalAdminRole" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">Change rol</h4>
				</div>
				<g:form controller="user" action="changeToAdminRole" id="myFormAdminRole" name="myFormAdminRole" method="POST">
					<g:hiddenField name="guidUserRoleAdmin" value="0" />
					<div class="modal-body">
						Do you want to change this rol to admin ?
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal" type="button">No</button>
						<g:submitButton id="boton" name="boton" class="btn btn-primary" value="Yes"/>
					</div>
				</g:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModalUserRole" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">Change Rol</h4>
				</div>
				<g:form controller="user" action="changeToUserRole" id="myFormUserRole" name="myFormUserRole" method="POST">
					<g:hiddenField name="guidUserRoleUser" value="0" />
					<div class="modal-body">
						Do you want to change this rol to user ?
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal" type="button">No</button>
						<g:submitButton id="boton" name="boton" class="btn btn-primary" value="Yes"/>
					</div>
				</g:form>
			</div>
		</div>
	</div>

    <div id="wrapper">

        <%-- Head --%>
        <g:render template="head" contextPath="/"/>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">User List</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <div class="row">
                <div class="col-lg-12">

					<table class="table table-striped table-bordered table-hover" id="dataTables-users">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Usename</th>
                                <th>Email</th>
                                <g:if test="${roleUserLogin != "U"}">
                                <th>Enabled</th>
                                </g:if>
                                <th>Login date</th>
                                <th>Last visited</th>
                                <th>Rol</th>
                                <g:if test="${roleUserLogin != "U"}">
	                                <th>Actions</th>
                                </g:if>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${userListInstance}" var="userInstance" status="i">
                            	<tr class="gradeX">
                            		<td>${i+1}</td>
                            		<td>
                            			<g:if test="${userInstance.userProfile.isOnline}">
	                            			<i class="fa fa-user fa-fw" style="color: green;"></i>
																		<g:link controller="user" action="show" params="[guid: "${userInstance.userProfile.guid}"]">${userInstance.userProfile.display_name}</g:link>
                            			</g:if>
                            			<g:else>
                            				<i class="fa fa-user fa-fw" style="color: blue;"></i>
																		<g:link controller="user" action="show" params="[guid: "${userInstance.userProfile.guid}"]">${userInstance.userProfile.display_name}</g:link>
                            			</g:else>
                            		</td>
                            		<td>${userInstance.username}</td>
                            		<g:if test="${roleUserLogin != "U"}">
                            		<td>${userInstance.enabled ? "Enabled" : "Disabled"}</td>
                            		</g:if>
                            		<td>${formatDate(format: 'dd/MMM/yyyy', date: userInstance.userProfile.fechaRegistro)}</td>
                            		<td>${formatDate(format: 'dd/MMM/yyyy', date: userInstance.userProfile.ultimaConexion)}</td>
                            		<td>${userInstance.getAuthorities().toString().contains("USER") ? "User" : (userInstance.getAuthorities().toString().contains("SUPER") ? "Super Admin" : "Administrador")}</td>
                            		<g:if test="${roleUserLogin != "U"}">
	                            		<td>
	                            			<%-- El usuario logado que sea superadmin puede modificar cualquier usuario de diferente role, y los administradores solo pueden modificar
	                            			los que posean nivel usuario. Los de rol usuario no pueden realizar ninguna acción. --%>
	                            			<g:if test="${userLogin.id != userInstance.id && ((roleUserLogin == "S" && !userInstance.getAuthorities().toString().contains("SUPER")) || (roleUserLogin == "A" && userInstance.getAuthorities().toString().contains("USER")))}">
	                            				<g:if test="${!userInstance.enabled}">
	                            					<button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModalEnabled" onclick="getIdUser('${userInstance.userProfile.guid}')">Enable User</button>
	                            				</g:if>
	                            				<g:else>
	                            					<button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModalDisabled" onclick="getIdUser('${userInstance.userProfile.guid}')">Disable User</button>
	                            				</g:else>
	                            				<g:if test="${userInstance.getAuthorities().toString().contains("ADMIN")}">
	                            					<p><button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModalUserRole" onclick="getIdUser('${userInstance.userProfile.guid}')">Change to role user</button></p>
	                            				</g:if>
	                            				<g:else>
	                            					<p><button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModalAdminRole" onclick="getIdUser('${userInstance.userProfile.guid}')">Change to role admin</button></p>
	                            				</g:else>
	                            			</g:if>
	                            		</td>
                            		</g:if>
                            	</tr>
                            </g:each>
                        </tbody>
                    </table>

                </div>

            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

		<%-- Foot --%>
        <g:render template="foot" contextPath="/"/>

    </div>
    <!-- /#wrapper -->

    <%-- JavaScript --%>
    <g:render template="javascript" contextPath="/"/>

	<script src="${resource(dir: 'bower_components', file: 'datatables/media/js/jquery.dataTables.min.js')}"></script>
	<script src="${resource(dir: 'bower_components', file: 'datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js')}"></script>

	<script type="text/javascript">
	    $(document).ready(function() {
	        $('#dataTables-users').DataTable({
	                responsive: true
	        });
	    });
    </script>

</body>

</html>
