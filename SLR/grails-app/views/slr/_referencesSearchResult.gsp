<div id="searchresults" class="row">
	<div class="col-lg-12">
		
		<g:if test="${!strSearch.equals("")}">
			<div class="panel panel-default">
				<div class="panel-heading">Filter search</div>
				<div class="panel-body">
			    	${strSearch}
			  	</div>
			</div>
		</g:if>
		
		<%-- PAGINATION --%>
		<g:render template="pagination" contextPath="/"/>
		
		<div class="chat-panel panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-book fa-fw"></i>
				Total founds: ${referenceListCount}
		    </div>
			<div class="panel-body" style="font-size: 11px; height: auto; overflow: hidden;">
				<g:if test="${referenceListCount == 0 && strSearch.equals("")}">
					No reference.
				</g:if>
				<g:elseif test="${referenceListCount == 0 && !strSearch.equals("")}">
					No reference with filter inserted.
				</g:elseif>
				<g:else>
					<ul class="chat">                    
						<g:each in="${referenceListInstance}" var="referenceInstance" status="i">
				        	<li class="left clearfix" style="padding-bottom: 10px;">
								<span class="chat-img pull-left">
									<img src="http://images.gofreedownload.net/document-icon-clip-art-17716.jpg" alt="User Avatar" style="width: 40px; height: 40px;" />
									<div style="clear: both; margin-left: 5px; margin-top: 8px;">
										<img src="${resource(dir: 'images/flags', file: referenceInstance.language.image)}" class="img-circle" style="width: 25px; height: 25px;" />
									</div>
									<div style="clear: both; margin-left: 5px; margin-top: 8px;">
										<img src="${resource(dir: 'images/logos_engines', file: referenceInstance.engine.image)}" alt="${referenceInstance.engine.name}" class="img-square" style="width: 25px; height: 25px;" />
									</div>
								</span>
								<div class="chat-body clearfix">
									<div class="header" style="overflow: hidden;">
										<div style="font-size: 14px;">
											<g:link controller="reference" action="show" params="[idmend: "${referenceInstance.idmend}"]"><b>${referenceInstance.title}</b></g:link>
											<small>
												${referenceInstance.citation_key}
											</small>
											<small class="pull-right text-muted">
												<i class="fa fa-book fa-fw"></i> ${referenceInstance.type.nombre}
											</small>
											<div>
												<g:set var="displayAllAuthors" value="" />
												<g:each in="${referenceInstance.authors}" var="authorReference" status="ar">
													<g:if test="${ar == 0 || ar == (referenceInstance.authors.size())}">
														${authorReference}
													</g:if>
													<g:else>
														${", " + authorReference}
													</g:else>
												</g:each>
											</div>
											<div>
												${referenceInstance.month + ", " + referenceInstance.year}
												<div style="display: inline-block;">
													<div style="display: inline-block; margin-left: 30px;">${referenceInstance.publisher != null && referenceInstance.publisher != '' ? referenceInstance.publisher : ''}</div>
													<div style="display: inline-block; font-size: x-small;">${referenceInstance.volume != null && referenceInstance.volume != '' ? 'Volume ' + referenceInstance.volume : ''}  ${referenceInstance.pages != null && referenceInstance.pages != '' ? 'Páginas ' + referenceInstance.pages : ''}</div>
													<div style="display: inline-block; margin-left: 30px;">${referenceInstance.department != null && referenceInstance.department != '' ? referenceInstance.department : ''}</div>
												</div>
											</div>
											<div style="margin-top: 5px;">
												${(referenceInstance.city != null && referenceInstance.city != '' ? referenceInstance.city : '') + (referenceInstance.country != null && referenceInstance.country != '' ? ', '+referenceInstance.country : '')}
												<g:if test="${referenceInstance.keywords.size() > 0}"><div style="display: inline-block; margin-left: 30px;"><b>Keywords: </b>${referenceInstance.keywords.toString()}</div></g:if>
											</div>
											<g:if test="${referenceInstance.docAbstract != null && !referenceInstance.docAbstract.equals('')}">
												<div style="margin-top: 5px;">
													<p><b>Abstract</b></p>
													<g:each in="${referenceInstance.docAbstract.split(" ")}" var="word" status="w">
														<g:if test="${w < 30}">
															${word + " "}
														</g:if>
													</g:each>
													....
												</div>
											</g:if>
											<small class="pull-right text-muted">
												${i+offset+1} de ${referenceListCount}
											</small>
										</div>
									</div>
								</div>
							</li>
						</g:each>
					</ul>
				</g:else>
			</div>
			
			<%-- PAGINATION --%>
			<g:render template="pagination" contextPath="/"/>
			
		</div>		
	</div>
</div>