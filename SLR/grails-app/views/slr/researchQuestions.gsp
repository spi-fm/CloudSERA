<%@ page import="es.uca.pfc.User" %>
<%@ page import="es.uca.pfc.Slr" %>
<%@ page import="es.uca.pfc.ResearchQuestion" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <%-- Head Meta --%>
	<g:render template="headMeta" contextPath="/"/>

    <title>CloudSERA | Research Questions</title>
    <link rel="icon" href="https://github.com/spi-fm/CloudSERA/raw/master/images/CloudSERA-sm.jpeg">

	<%-- CSS --%>
    <g:render template="css" contextPath="/"/>

	<script type="text/javascript">
		function getIdQuestion(id)
		{
			document.getElementById("idQuestion").value = id.toString();
			document.getElementById('divErrorQuestion').style.display = "none";
		}
		function getQuestionForEdit(id,statement)
		{
			document.getElementById("idEditQuestion").value = id.toString();
			document.getElementById("enunciadoEdit").value = statement.toString();
			document.getElementById('divErrorEditQuestion').style.display = "none";
		}
		function loadModal()
		{
			if(${null != errorQuestion && errorQuestion != ""})
			{
				$('#myModalQuestion').modal('show');
			}
			else if (${successQuestion})
			{
				document.getElementById('divSuccessQuestion').style.display = "";
				//setTimeout(hideSuccess, 5000);
				setTimeout(function(){
					document.getElementById('divSuccessQuestion').style.display = "none";
				}, 5000);
			}

			if(${null != errorEditQuestion && errorEditQuestion != ""})
			{
				$('#myModalEditQuestion').modal('show');
			}
			else if (${successEditQuestion})
			{
				document.getElementById('divSuccessEditQuestion').style.display = "";
				//setTimeout(hideSuccess, 5000);
				setTimeout(function(){
					document.getElementById('divSuccessEditQuestion').style.display = "none";
				}, 5000);
			}
		}
		function getIdSlr(id)
		{
			document.getElementById("guidSlr").value = id.toString();
			document.getElementById('divErrorQuestion').style.display = "none";
		}
	</script>
</head>

<body onload="loadModal();">

	<%--Ventana modal crear question --%>
	<div class="modal fade" id="myModalQuestion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
	    	<div class="modal-content">
	    		<div class="modal-header">
      				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      				<h4 class="modal-title" id="myModalLabel">Create Research Question</h4>
			    </div>
		    	<g:form controller="researchQuestion" action="save" method="POST" name="myFormQuestion" id="myFormQuestion">
		    		<g:hiddenField name="guidSlr" value="${slrInstance.guid}" />
		    		<div class="modal-body">
				    	<g:if test="${null != errorQuestion && !errorQuestion.equals("")}">
					    	<div id="divErrorQuestion" class="alert alert-danger" role="alert"><i class="fa fa-remove fa-fw"></i> ${errorQuestion}</div>
				    	</g:if>
				    	<div class="form-inline">
				    		<table>
				    			<tr>
				    				<td><b>Statement:</b></td>
				    				<td><input id="enunciado" type="text" name="enunciado" size=55 class="form-control" value="${enunciadoQuestion}" /></td>
				    			</tr>
				    		</table>
				    	</div>
				    </div>
				    <div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
						<g:submitButton name="create" class="btn btn-primary" value="Create Research Question"/>
					</div>
				</g:form>
		    </div>
		</div>
    </div>

    <%--Ventana modal editar question --%>
	<div class="modal fade" id="myModalEditQuestion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
	    	<div class="modal-content">
	    		<div class="modal-header">
      				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      				<h4 class="modal-title" id="myModalLabel">Edit Research Question</h4>
			    </div>
		    	<g:form controller="researchQuestion" action="edit" method="POST" name="myFormEditQuestion" id="myFormEditQuestion">
		    		<g:hiddenField name="idEditQuestion" value="${idEditQuestion}" />
		    		<g:hiddenField name="guidSlr" value="${slrInstance.guid}" />
		    		<div class="modal-body">
				    	<g:if test="${null != errorEditQuestion && !errorEditQuestion.equals("")}">
					    	<div id="divErrorEditQuestion" class="alert alert-danger" role="alert"><i class="fa fa-remove fa-fw"></i> ${errorEditQuestion}</div>
				    	</g:if>
				    	<div class="form-inline">
				    		<table>
				    			<tr>
				    				<td><b>Statement:</b></td>
				    				<td><input id="enunciadoEdit" type="text" name="enunciadoEdit" class="form-control" value="${enunciadoEditQuestion}" /></td>
				    			</tr>
				    		</table>
				    	</div>
				    </div>
				    <div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
						<g:submitButton name="create" class="btn btn-primary" value="Edit Research Question"/>
					</div>
				</g:form>
		    </div>
		</div>
    </div>

	<%-- Ventana modal para eliminar question --%>
    <div class="modal fade" id="myModalDrop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">Delete Research Question</h4>
				</div>
				<g:form controller="researchQuestion" action="delete" id="myFormDelete" name="myFormDelete" method="DELETE">
					<g:hiddenField name="idQuestion" value="0" />
					<g:hiddenField name="guidSlr" value="${slrInstance.guid}" />
		    		<div class="modal-body">
						<p>Do you want to remove this question ?</p>
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
                    <h1 class="page-header">Researchs Questions in ${slrInstance.title}</h1>

                    <ol class="breadcrumb">
					  <li><g:link controller="index" action="menu">Home</g:link></li>
					  <li><g:link controller="slr" action="myList">Review List</g:link></li>
					  <li><g:link controller="slr" action="show" params="[guidSlr: "${slrInstance.guid}"]">${slrBreadCrumb}</g:link></li>
					  <li class="active">Researchs Questions</li>
					</ol>
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <div class="row">
                <div id="divSuccessQuestion" class="alert alert-success" role="alert" style="display: none;"><i class="fa fa-check fa-fw"></i> Research question created correctly.</div>
            	<div id="divSuccessEditQuestion" class="alert alert-success" role="alert" style="display: none;"><i class="fa fa-check fa-fw"></i> Research question edited correctly.</div>
            	<div class="col-lg-12">
                  	<div style="margin-top: 5px; margin-bottom: 20px;">
                  		<button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModalQuestion">Create Research Question</button>
	            	</div>
					<table class="table table-striped table-bordered table-hover" id="dataTables-myquestions">
                        <thead>
                            <tr>
                                <th>Statement</th>
                                <th>Created</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${questionListInstance}" var="questionInstance">
                            	<g:set var="contReference" value="0" />
                            	<tr class="gradeX">
                            		<td>${questionInstance.enunciado}</td>
                            		<td>${formatDate(format: 'HH:mm - dd/MMM/yyyy', date: questionInstance.submitDate)}</td>
                            		<td>
                            			<button title="Editar Pregunta" type="button" class="btn btn-default btn-circle" data-toggle="modal" data-target="#myModalEditQuestion" onclick="getQuestionForEdit('${questionInstance.id.toString()}','${questionInstance.enunciado.toString()}')"><i class="fa fa-edit"></i></button>
                            			<g:if test="${slrInstance.noDrop == false}">
											<button title="Eliminar Pregunta" type="button" class="btn btn-default btn-circle" data-toggle="modal" data-target="#myModalDrop" onclick="getIdQuestion('${questionInstance.id.toString()}')"><i class="fa fa-times"></i></button>
										</g:if>
										<g:else>
											<button title="Eliminar Pregunta" type="button" class="btn btn-default btn-circle disabled" data-toggle="modal" data-target="#myModalDrop"><i class="fa fa-times"></i></button>
										</g:else>
                            		</td>
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
	        $('#dataTables-myquestions').DataTable({
	                responsive: true
	        });
	    });
    </script>

</body>

</html>
