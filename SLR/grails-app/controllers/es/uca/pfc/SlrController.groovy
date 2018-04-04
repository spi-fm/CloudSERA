package es.uca.pfc

import grails.transaction.Transactional;

class SlrController {
	
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def springSecurityService
	def toolService
	def mendeleyToolService
	def exportService
	def graphService
	def strSearch = ""
	int maxPerPage = 10
	int maxStrTitle = 20
	def fileNameTemplateExcel = "templates/template.xlsx"
	
    def index() 
	{ 
		redirect(controller: 'slr', action: 'myList')
	}
	
	@Transactional
	def myList()
	{
		def isLogin = springSecurityService.loggedIn
		
		if(isLogin)
		{
			def userInstance = User.get(springSecurityService.principal.id)
			
			if(params.guidNotif != null)
			{
				if(params.guidNotif.toString().equals("all"))
				{
					def notifications = Notification.findAllByProfileAndLeido(userInstance.userProfile, false, [sort: 'fecha', order: 'desc'])
					for(Notification not : notifications)
					{
						not.leido = true;
						not.save(failOnError: true, flush: true)
					}
				}
				else
				{
					def notification = Notification.findByGuidLike(params.guidNotif.toString())
					notification.leido = true;
					notification.save(failOnError: true, flush: true)
				}
			}
			
			// Comprobamos si se va a crear un nuevo SLR.
			def error = ""
			def errorCriterion = ""
			def errorAttribute = ""
			def errorQuestion = ""
			def tituloSlr = ""
			def justificacionSlr = ""
			def success = false
			def successCriterion = false
			def successAttribute = false
			def successQuestion = false
			def nombreCriterion = ""
			def descripcionCriterion = ""
			def guidSlrError = "0"
			def nombreAttribute = ""
			def opcionesAttribute = ""
			def tipoAttribute = "string"
			def enunciadoQuestion = ""
			
			if(null != params.success)
			{
				success = true
			}
			
			if(params.successCriterion.toString().equals("true"))
			{
				successCriterion = true
			}
			
			if(params.successAttribute.toString().equals("true"))
			{
				successAttribute = true
			}
			
			if(params.successQuestion.toString().equals("true"))
			{
				successQuestion = true
			}
			
			if(!(null == params.error || params.error.equals(null)))
			{
				error = params.error.toString()
			}
			
			if(!(null == params.errorCriterion || params.errorCriterion.equals(null)))
			{
				errorCriterion = params.errorCriterion.toString()
			}
			
			if(!(null == params.errorAttribute || params.errorAttribute.equals(null)))
			{
				errorAttribute = params.errorAttribute.toString()
			}
			
			if(!(null == params.errorQuestion || params.errorQuestion.equals(null)))
			{
				errorQuestion = params.errorQuestion.toString()
			}
			
			if(!(params.tituloSlr == null || params.tituloSlr.equals(null)))
			{
				tituloSlr = params.tituloSlr.toString()
			}
			
			if(!(params.justificacionSlr == null || params.justificacionSlr.equals(null)))
			{
				justificacionSlr = params.justificacionSlr.toString()
			}
			
			if(!(params.nombreCriterion == null || params.nombreCriterion.equals(null) || successCriterion))
			{
				nombreCriterion = params.nombreCriterion.toString()
			}
			
			if(!(params.descripcionCriterion == null || params.descripcionCriterion.equals(null) || successCriterion))
			{
				descripcionCriterion = params.descripcionCriterion.toString()
			}
			
			if(!(params.guidSlrError == null || params.guidSlrError.equals(null)))
			{
				guidSlrError = params.guidSlrError.toString()
			}
			
			if(!(params.nombreAttribute == null || params.nombreAttribute.equals(null) || successAttribute))
			{
				nombreAttribute = params.nombreAttribute.toString()
			}
			
			if(!(params.opcionesAttribute == null || params.opcionesAttribute.equals(null) || successAttribute))
			{
				opcionesAttribute = params.opcionesAttribute.toString()
			}
			
			if(!(params.tipoAttribute == null || params.tipoAttribute.equals(null) || successAttribute))
			{
				tipoAttribute = params.tipoAttribute.toString()
			}
			
			if(!(params.enunciadoQuestion == null || params.enunciadoQuestion.equals(null) || successQuestion))
			{
				enunciadoQuestion = params.enunciadoQuestion.toString()
			}
			
			def errorSynchro = ""

			if(params.isSynchro != null && params.isSynchro.toString().equals("false"))
			{
				errorSynchro = "Problems with synchronization. Try later please."
			}
						
			def slrListInstance = userInstance.userProfile.slrs
			
			for(Slr slr : slrListInstance)
			{
				slr.state = toolService.updateStatus(slr)
				slr.save(flush: true)
			}
			
			[slrListInstance: slrListInstance,
			 error: error,
			 errorCriterion: errorCriterion,
			 tituloSlr: tituloSlr,
			 justificacionSlr: justificacionSlr,
			 success: success,
			 successCriterion: successCriterion,
			 nombreCriterion: nombreCriterion,
			 descripcionCriterion: descripcionCriterion,
			 guidSlrError: guidSlrError,
			 nombreAttribute: nombreAttribute,
			 opcionesAttribute: opcionesAttribute,
			 tipoAttribute: tipoAttribute,
			 successAttribute: successAttribute,
			 errorAttribute: errorAttribute,
			 enunciadoQuestion: enunciadoQuestion,
			 errorQuestion: errorQuestion,
			 successQuestion: successQuestion,
			 errorSynchro: errorSynchro
			]
		}
		else
		{
			redirect(controller: 'index', action: 'index')
		}
	}
	
	def searchs()
	{
		def isLogin = springSecurityService.loggedIn
		
		// Si no existe el guid, redirigimos a index
		if(!isLogin || !params.guid || params.guid.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def slrInstance = Slr.findByGuid(params.guid)
			
			def userLogin = User.get(springSecurityService.principal.id)
			if(userLogin.userProfile != slrInstance.userProfile)
			{
				redirect(controller: 'index', action: 'index')
			}
			
			if(params.guidNotif != null)
			{
				def notification = Notification.findByGuidLike(params.guidNotif.toString())
				notification.leido = true;
				notification.save(failOnError: true, flush: true)
			}
			
			def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle)+"..." : slrInstance.title)
			
			def isCreating = false
			if(params.isCreating != null)
			{
				isCreating = params.isCreating
			}
			
			[slrInstance: slrInstance, searchListInstance: slrInstance.searchs, slrBreadCrumb: slrBreadCrumb, isCreating: isCreating]
		}
	}
	
	def references()
	{
		def isLogin = springSecurityService.loggedIn
		
		// Si no existe el guid, redirigimos a index
		if(!isLogin || !params.guid || params.guid.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def slrInstance = Slr.findByGuid(params.guid)
			def userLogin = User.get(springSecurityService.principal.id)
			
			if(null != slrInstance && userLogin.userProfile != slrInstance.userProfile)
			{
				redirect(controller: 'index', action: 'index')
			}
			else
			{
				def searchInstance = Search.findByGuid(params.guid)
				
				SortedSet<Reference> referenceListInstance = new TreeSet<Reference>()
				
				if (searchInstance == null && slrInstance == null)
				{
					redirect(controller: 'index', action: 'index')
				}
				else
				{
					if (searchInstance != null)
					{
						referenceListInstance.addAll(searchInstance.references)
						slrInstance = searchInstance.slr
					}
					else
					{
						referenceListInstance = toolService.getReferences(slrInstance)
					}

					// Obtenemos los parametros de filtro
					toolService.getAllParamsFilter(referenceListInstance)
					
					// Paginamos los resultados
					def totalRefs  = referenceListInstance.size()
					def totalPages = Math.round(totalRefs / maxPerPage);
					def page = 1
					def offset = (page * maxPerPage) - maxPerPage
										
					referenceListInstance = toolService.getPaginatedReferences(referenceListInstance, maxPerPage,offset)
					
					def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle) : slrInstance.title)
					
					strSearch = ""
					
					[
						referenceListInstance: referenceListInstance, 
						slrInstance: slrInstance, 
						guidSlr: slrInstance.guid,
						authorsListInstance: toolService.getAuthors(),
						criterionsListInstance: toolService.getCriterions(),
						specificAttributesMapInstance: toolService.getSpecificAttributesMap(),
						minYear: Integer.parseInt(toolService.getMinYear()),
						maxYear: Integer.parseInt(toolService.getMaxYear()),
						enginesListInstance: toolService.getEngines(),
						languagesListInstance: toolService.getLanguages(),
						departmentsListInstance: toolService.getDepartaments(),
						typesListInstance: toolService.getTypes(),
						strSearch: strSearch,
						referenceListCount: totalRefs,
						totalPages: totalPages,
						page: page,
						offset: offset,
						slrBreadCrumb: slrBreadCrumb
					]
				}
			}
		}
	}
	
	def filtredReferencesByParam = {
		
		int page = 1
		String filter = (null == params.filter ? "" : params.filter.toString())
		String guidSlr =  (null == params.guidSlr ? "" : params.guidSlr.toString())
		
		if (!filter.equals(""))
		{
			strSearch = toolService.formatSearchString(strSearch.toString(), filter)
		}
		
		page = Integer.parseInt(null == params.page ? "1" : params.page.toString())
		
		def offset = (page * maxPerPage) - maxPerPage
		
		SortedSet<Reference> referenceListInstance = toolService.getReferencesWithFilter(guidSlr, strSearch)

		def totalRefs  = referenceListInstance.size()
		def totalPages = Math.round(totalRefs / maxPerPage);
		
		referenceListInstance = toolService.getPaginatedReferences(referenceListInstance, maxPerPage, offset)
		
		render(template:'referencesSearchResult', model:[referenceListInstance:referenceListInstance, 
														 strSearch: strSearch, 
														 guidSlr:guidSlr,
														 referenceListCount: totalRefs,
														 totalPages: totalPages,
														 page: page,
														 offset: offset
														 ])
	}
	
	@Transactional
	def save()
	{
		String tituloSlr = params.titulo.toString()
		String justificacionSlr = params.justificacion.toString()
		User userInstance = User.get(Long.parseLong(springSecurityService.principal.id.toString()))
		String error = ""
		
		if(userInstance == null)
		{
			redirect(controller: 'index', action: 'index')
		}
		else if (tituloSlr.equals("null") || tituloSlr.length() <= 5 )
		{
			error = "ERROR: The name must have 5 or more characters."
		}
		else if (justificacionSlr.equals("null") || justificacionSlr.length() <= 2 )
		{
			error = "ERROR: The justification must have 5 or more characters"
		}
		else
		{
			tituloSlr = "SLR: " + params.titulo.toString().trim()
			
			def mySlrs = Slr.findAllByUserProfileAndTitleLike(userInstance.userProfile, tituloSlr)
			
			if(mySlrs.size() > 0) // Hay repetidos con ese nombre
			{
				error = "ERROR: The name of SLR is used."
			}
		}
		
		if(error.equals(""))
		{
			// Creamos el Slr en BD
			Slr newSlrInstance = new Slr(title: tituloSlr, justification: justificacionSlr, userProfile: userInstance.userProfile, idmend: tituloSlr)
			newSlrInstance.save flush: true
			
			// Creamos el Slr en Mendeley
			boolean isCreated = mendeleyToolService.createSlrMendeley(userInstance, newSlrInstance)
			
			if (isCreated)
			{
				redirect(controller: 'slr', action: 'myList', params: [success: true])
			}
			else
			{
				error = "ERROR: Problems with the synchro. Try again later.."
				redirect(controller: 'slr', action: 'myList',
					params: [error: error,
							 tituloSlr: params.titulo,
							 justificacionSlr: params.justificacion])
			}
		}
		else
		{
			redirect(controller: 'slr', action: 'myList',
				params: [error: error,
						 tituloSlr: params.titulo,
						 justificacionSlr: params.justificacion])
		}
	}
	
	@Transactional
	def delete()
	{
		def isLogin = springSecurityService.loggedIn
		
		if (isLogin)
		{
			def userLogin = User.get(springSecurityService.principal.id)
			
			def slrInstance = Slr.findByGuid(params.guidSlr.toString())
			
			toolService.deleteSlr(slrInstance)
			
			mendeleyToolService.deleteSlr(slrInstance, userLogin)
			
			redirect(controller: 'slr', action: 'myList')
		}
		else
		{
			redirect(controller: 'index', action: 'index')
		}
	}
	
	def show()
	{
		def slrInstance = Slr.findByGuid(params.guidSlr.toString())
		
		if(slrInstance == null)
		{
			redirect(controller: 'slr', action: 'myList')
		}
		else
		{
			List<String> queriesChart = graphService.chartsByTag(slrInstance)
			
			def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle)+"..." : slrInstance.title)
			
			[slrInstance: slrInstance, queryCriterion1: queriesChart.get(1), slrBreadCrumb: slrBreadCrumb]
		}
	}
	
	def criterions()
	{
		def isLogin = springSecurityService.loggedIn
		
		// Si no existe el guid, redirigimos a index
		if(!isLogin || !params.guid || params.guid.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			// Comprobamos si se va a crear un nuevo SLR.
			def errorCriterion = ""
			def successCriterion = false
			def nombreCriterion = ""
			def descripcionCriterion = ""
			def errorEditCriterion = ""
			def successEditCriterion = false
			def nombreEditCriterion = ""
			def descripcionEditCriterion = ""
			def idEditCriterion = 0
			
			if(!(null == params.idEditCriterion || params.idEditCriterion.equals(null)))
			{
				idEditCriterion = Long.parseLong(params.idEditCriterion.toString())
			}
			
			if(params.successCriterion.toString().equals("true"))
			{
				successCriterion = true
			}
			
			if(params.successEditCriterion.toString().equals("true"))
			{
				successEditCriterion = true
			}

			if(!(null == params.errorCriterion || params.errorCriterion.equals(null)))
			{
				errorCriterion = params.errorCriterion.toString()
			}
			
			if(!(null == params.errorEditCriterion || params.errorEditCriterion.equals(null)))
			{
				errorEditCriterion = params.errorEditCriterion.toString()
			}
			
			if(!(params.nombreCriterion == null || params.nombreCriterion.equals(null) || successCriterion))
			{
				nombreCriterion = params.nombreCriterion.toString()
			}
			
			if(!(params.nombreEditCriterion == null || params.nombreEditCriterion.equals(null) || successEditCriterion))
			{
				nombreEditCriterion = params.nombreEditCriterion.toString()
			}
			
			if(!(params.descripcionCriterion == null || params.descripcionCriterion.equals(null) || successCriterion))
			{
				descripcionCriterion = params.descripcionCriterion.toString()
			}

			if(!(params.descripcionEditCriterion == null || params.descripcionEditCriterion.equals(null) || successCriterion))
			{
				descripcionEditCriterion = params.descripcionEditCriterion.toString()
			}

			def slrInstance = Slr.findByGuid(params.guid)
			
			def userLogin = User.get(springSecurityService.principal.id)
			if(userLogin.userProfile != slrInstance.userProfile)
			{
				redirect(controller: 'index', action: 'index')
			}
			
			// Calculamos el n�mero de referencias con los criterios
			Map<String, Integer> totalReferences = new HashMap<String,Integer>()
			for(Search search : slrInstance.searchs)
			{
				for(Reference reference : search.references)
				{
					if (null == totalReferences.get(reference.criterion.name))
					{
						totalReferences.put(reference.criterion.name, 1)
					}
					else
					{
						totalReferences.put(reference.criterion.name, totalReferences.get(reference.criterion.name).value+1)
					}
				}
			}
			
			def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle)+"..." : slrInstance.title)
			
			[slrInstance: slrInstance, criterionListInstance: slrInstance.criterions,
			 errorCriterion: errorCriterion, successCriterion: successCriterion,
			 nombreCriterion: nombreCriterion, descripcionCriterion: descripcionCriterion,
			 errorEditCriterion: errorEditCriterion, successEditCriterion: successEditCriterion,
			 nombreEditCriterion: nombreEditCriterion, descripcionEditCriterion: descripcionEditCriterion,
			 totalReferences: totalReferences, slrBreadCrumb: slrBreadCrumb, idEditCriterion: idEditCriterion]
		}
	}
	
	def specAttributes()
	{
		def isLogin = springSecurityService.loggedIn
		
		// Si no existe el guid, redirigimos a index
		if(!isLogin || !params.guid || params.guid.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			// Comprobamos si se va a crear un nuevo SLR.
			def errorAttribute = ""
			def successAttribute = false
			def nombreAttribute = ""
			def opcionesAttribute = ""
			def tipoAttribute = "string"
			def errorEditAttribute = ""
			def successEditAttribute = false
			def nombreEditAttribute = ""
			def idEditAttribute = 0
			
			if(!(null == params.idEditAttribute || params.idEditAttribute.equals(null)))
			{
				idEditAttribute = Long.parseLong(params.idEditAttribute.toString())
			}
			
			if(params.successAttribute.toString().equals("true"))
			{
				successAttribute = true
			}
			
			if(params.successEditAttribute.toString().equals("true"))
			{
				successEditAttribute = true
			}
		
			if(!(null == params.errorAttribute || params.errorAttribute.equals(null)))
			{
				errorAttribute = params.errorAttribute.toString()
			}
			
			if(!(null == params.errorEditAttribute || params.errorEditAttribute.equals(null)))
			{
				errorEditAttribute = params.errorEditAttribute.toString()
			}
			
			if(!(params.nombreAttribute == null || params.nombreAttribute.equals(null) || successAttribute))
			{
				nombreAttribute = params.nombreAttribute.toString()
			}
			
			if(!(params.nombreEditAttribute == null || params.nombreEditAttribute.equals(null) || successEditAttribute))
			{
				nombreEditAttribute = params.nombreEditAttribute.toString()
			}
			
			if(!(params.tipoAttribute == null || params.tipoAttribute.equals(null) || successAttribute))
			{
				tipoAttribute = params.tipoAttribute.toString()
			}
			
			if(!(params.opcionesAttribute == null || params.opcionesAttribute.equals(null) || successAttribute))
			{
				opcionesAttribute = params.opcionesAttribute.toString()
			}
			
			def slrInstance = Slr.findByGuid(params.guid)
			
			def userLogin = User.get(springSecurityService.principal.id)
			if(userLogin.userProfile != slrInstance.userProfile)
			{
				redirect(controller: 'index', action: 'index')
			}
			
			def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle)+"..." : slrInstance.title)
			
			[slrInstance: slrInstance, specAttributesListInstance: slrInstance.specAttributes,
			 errorAttribute: errorAttribute, successAttribute: successAttribute,
			 nombreAttribute: nombreAttribute, opcionesAttribute: opcionesAttribute,
			 tipoAttribute: tipoAttribute, slrBreadCrumb: slrBreadCrumb,
			 errorEditAttribute: errorEditAttribute, successEditAttribute: successEditAttribute,
			 nombreEditAttribute: nombreAttribute,idEditAttribute: idEditAttribute]
		}
	}
	
	def researchQuestions()
	{
		def isLogin = springSecurityService.loggedIn
		
		// Si no existe el guid, redirigimos a index
		if(!isLogin || !params.guid || params.guid.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			// Comprobamos si se va a crear un nuevo SLR.
			def errorQuestion = ""
			def successQuestion = false
			def enunciadoQuestion = ""
			def errorEditQuestion = ""
			def successEditQuestion = false
			def enunciadoEditQuestion = ""
			def idEditQuestion = 0

			if(!(null == params.idEditQuestion || params.idEditQuestion.equals(null)))
			{
				idEditQuestion = Long.parseLong(params.idEditQuestion.toString())
			}
			
			if(params.successQuestion.toString().equals("true"))
			{
				successQuestion = true
			}
			
			if(params.successEditQuestion.toString().equals("true"))
			{
				successEditQuestion = true
			}
		
			if(!(null == params.errorQuestion || params.errorQuestion.equals(null)))
			{
				errorQuestion = params.errorQuestion.toString()
			}
			
			if(!(null == params.errorEditQuestion || params.errorEditQuestion.equals(null)))
			{
				errorEditQuestion = params.errorEditQuestion.toString()
			}
			
			if(!(params.enunciadoQuestion == null || params.enunciadoQuestion.equals(null) || successQuestion))
			{
				enunciadoQuestion = params.enunciadoQuestion.toString()
			}

			if(!(params.enunciadoEditQuestion == null || params.enunciadoEditQuestion.equals(null) || successEditQuestion))
			{
				enunciadoEditQuestion = params.enunciadoEditQuestion.toString()
			}

			def slrInstance = Slr.findByGuid(params.guid)
			
			def userLogin = User.get(springSecurityService.principal.id)
			if(userLogin.userProfile != slrInstance.userProfile)
			{
				redirect(controller: 'index', action: 'index')
			}
			
			def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle)+"..." : slrInstance.title)
			
			
			[slrInstance: slrInstance, questionListInstance: slrInstance.questions,
			 errorQuestion: errorQuestion, successQuestion: successQuestion,
			 enunciadoQuestion: enunciadoQuestion, slrBreadCrumb: slrBreadCrumb,
			 errorEditQuestion: errorEditQuestion, successEditQuestion: successEditQuestion,
			 enunciadoEditQuestion: enunciadoEditQuestion, idEditQuestion: idEditQuestion]
		}
	}
	
	def exportToExcel()
	{
		def slrInstance = Slr.findByGuidLike(params.guid.toString())
		
		if(slrInstance == null)
		{
			redirect(controller: 'slr', action: 'myList')
		}
		else
		{
			def templateExcel = grailsAttributes.getApplicationContext().getResource(fileNameTemplateExcel).getFile().toString()
			def folderTmp = grailsAttributes.getApplicationContext().getResource("tmp").getFile().toString() + "/"
			
			def file = exportService.exportToExcel(slrInstance, templateExcel, folderTmp)
			
			if (file.exists()) {
				response.setContentType("application/octet-stream")
				response.setHeader("Content-disposition", "filename=${file.name}")
				response.outputStream << file.bytes
				if(!file.delete())
					log.info "No se ha borrado el fichero " + file.name
				return
			 }
		}
	}
	
	def exportToWord()
	{
		def slrInstance = Slr.findByGuidLike(params.guid.toString())
		
		if(slrInstance == null)
		{
			redirect(controller: 'slr', action: 'myList')
		}
		else
		{
			def folderTmp = grailsAttributes.getApplicationContext().getResource("tmp").getFile().toString() + "/"
			def fileLogoUCA = grailsAttributes.getApplicationContext().getResource("templates/logoUCA.PNG").getFile().toString() + "/"
			def file = exportService.exportToWord(slrInstance, folderTmp, fileLogoUCA)
			
			if (file.exists()) {
				response.setContentType("application/octet-stream")
				response.setHeader("Content-disposition", "filename=${file.name}")
				response.outputStream << file.bytes
				if(!file.delete())
					log.info "No se ha borrado el fichero " + file.name
				return
			 }
		}
	}
	
	def exportToPdf()
	{
		def slrInstance = Slr.findByGuidLike(params.guid.toString())
		
		if(slrInstance == null)
		{
			redirect(controller: 'slr', action: 'myList')
		}
		else
		{	
			def folderTmp = grailsAttributes.getApplicationContext().getResource("tmp").getFile().toString() + "/"
			def fileLogoUCA = grailsAttributes.getApplicationContext().getResource("templates/logoUCA.PNG").getFile().toString() + "/"
			def file = exportService.exportToPdf(slrInstance, folderTmp, fileLogoUCA)
			
			if (file.exists()) {
				response.setContentType("application/octet-stream")
				response.setHeader("Content-disposition", "filename=${file.name}")
				response.outputStream << file.bytes
				if(!file.delete())
					log.info "No se ha borrado el fichero " + file.name
				return
			 }
		}
	}
	
	def exportToBibTex()
	{
		def slrInstance = Slr.findByGuidLike(params.guid.toString())
		
		if(slrInstance == null)
		{
			redirect(controller: 'slr', action: 'myList')
		}
		else
		{
			def folderTmp = grailsAttributes.getApplicationContext().getResource("tmp").getFile().toString() + "/"

			def file = exportService.exportToBibTex(slrInstance, folderTmp)
			
			if (file.exists()) {
				response.setContentType("application/octet-stream")
				response.setHeader("Content-disposition", "filename=${file.name}")
				response.outputStream << file.bytes
				if(!file.delete())
					log.info "No se ha borrado el fichero " + file.name
				return
			}
		}
	}
		
	def graphs()
	{
		def isLogin = springSecurityService.loggedIn
		
		if(!isLogin)
		{
			redirect(controller: 'index', action: 'index')
		}
		
		def slrInstance = Slr.findByGuidLike(params.guid)
		
		if(slrInstance == null)
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle)+"..." : slrInstance.title)
			
			List<String> queriesChart = graphService.chartsByTag(slrInstance)
			
			[
				slrBreadCrumb: slrBreadCrumb,
				slrInstance: slrInstance, 
				criterionShowTextEvery: queriesChart.get(0),
				queryCriterion1: queriesChart.get(1),
				queryCriterion2: queriesChart.get(2),
				queryCriterion3: queriesChart.get(3),
				totalCriterions: Integer.parseInt(queriesChart.get(4)),
				queryEngine1: queriesChart.get(5),
				queryEngine2: queriesChart.get(6),
				queryEngine3: queriesChart.get(7),
				totalEngines: Integer.parseInt(queriesChart.get(8)),
				queryDepartment1: queriesChart.get(9),
				queryDepartment2: queriesChart.get(10),
				queryDepartment3: queriesChart.get(11),
				totalDepartments: Integer.parseInt(queriesChart.get(12)),
				queryType1: queriesChart.get(13),
				queryType2: queriesChart.get(14),
				queryType3: queriesChart.get(15),
				totalTypes: Integer.parseInt(queriesChart.get(16)),
				queryLanguage1: queriesChart.get(17),
				queryLanguage2: queriesChart.get(18),
				queryLanguage3: queriesChart.get(19),
				totalLanguages: Integer.parseInt(queriesChart.get(20)),
				querySearch1: queriesChart.get(21)
			]
		}			
	}
		
	@Transactional
	def syncronizeListSlrMendeley()
	{
		def isLogin = springSecurityService.loggedIn
		
		if(isLogin)
		{
			// Si no existe el guid, redirigimos a index
			def userLogin = User.get(springSecurityService.principal.id)
			
			boolean isSynchro = mendeleyToolService.synchronizeSlrList(userLogin)

			redirect(controller: 'slr', action: 'myList', params: [isSynchro: isSynchro])
		}
		else
		{
			redirect(controller: 'index', action: 'index')
		}
	}
	
	@Transactional
	def syncronizeSlrMendeley()
	{
		def isLogin = springSecurityService.loggedIn
		
		if(!isLogin || !params.guidSlr || params.guidSlr.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def slrInstance = Slr.findByGuidIlike(params.guidSlr.toString())
			
			if (slrInstance == null)
			{
				redirect(controller: 'index', action: 'index')
			}
			else
			{
				// Si no existe el guid, redirigimos a index
				def userLogin = User.get(springSecurityService.principal.id)
				
				mendeleyToolService.synchronizeSlr(userLogin, slrInstance)
				
				redirect(controller: 'slr', action: 'searchs', params: [guid: slrInstance.guid])
			}
		}
	}
}
