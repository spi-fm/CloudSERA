package es.uca.pfc

import grails.transaction.Transactional;

class SearchController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def springSecurityService
	def toolService
	def mendeleyToolService
	int maxStrTitle = 20
    
	def index() 
	{
		redirect(controller: 'index', action: 'index')
	}
	
	def create()
	{
		def isLogin = springSecurityService.loggedIn
		
		String guidSlr = (null == params.guidSlr ? "" : params.guidSlr.toString())
		
		if(!isLogin || guidSlr.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def slrInstance = Slr.findByGuid(guidSlr)
			def error = (null == params.error ? "" : params.error.toString())
			
			if (null == slrInstance)
			{
				redirect(controller: 'index', action: 'index')
			}
			else
			{
				def minYearSearch = (null == params.minYearSearch ? 1980 : Integer.parseInt(params.minYearSearch.toString()))
				def maxYearSearch = (null == params.maxYearSearch ? Calendar.getInstance().get(Calendar.YEAR) : Integer.parseInt(params.maxYearSearch.toString()))
				def maxTotalSearch = params.maxTotalSearch
				
				def engineListInstance = EngineSearch.findAllByStatusAndNameNotIlike(true, "other")
				def operatorListInstance = SearchOperator.list(sort:'name', order: 'asc')
				def componentListInstance = SearchComponent.list(sort:'name', order: 'asc')
				def minYear = 1980
				def maxYear = Calendar.getInstance().get(Calendar.YEAR);
				
				def strOptionsOperators = toolService.converterToStrOptions(operatorListInstance)
				def strOptionsComponents = toolService.converterToStrOptions(componentListInstance)
				
				def slrBreadCrumb = (slrInstance.title.toString().length() > maxStrTitle ? slrInstance.title.toString().substring(0,maxStrTitle)+"..." : slrInstance.title)
				
				[
					slrInstance: slrInstance, error: error, engineListInstance: engineListInstance,
					operatorListInstance: operatorListInstance, componentListInstance: componentListInstance,
					minYear: minYear, maxYear: maxYear,
					minYearSearch: minYearSearch,
					maxYearSearch: maxYearSearch,
					maxTotalSearch: maxTotalSearch,
					strOptionsOperators: strOptionsOperators,
					strOptionsComponents: strOptionsComponents,
					slrBreadCrumb: slrBreadCrumb
				]
			}
		}		
	}
	
	@Transactional
	def save()
	{
		def isLogin = springSecurityService.loggedIn
		String guidSlr = (null == params.guidSlr ? "" : params.guidSlr.toString())
		
		Slr slrInstance = (guidSlr == "" ? null : Slr.findByGuidLike(guidSlr))
		
		if(!isLogin || guidSlr.equals("") || slrInstance == null)
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def terminos = (null == params.inputTerminos ? "" : params.inputTerminos)
			def componentes =  (null == params.selectComponent ? "" : params.selectComponent)
			def operators = (null == params.selectOperator ? "" : params.selectOperator)
			boolean opACM = (null == params.engineACM ? false : true)
			boolean opIEEE = (null == params.engineIEEE ? false : true)
			boolean opSCIENCE = (null == params.engineSCIENCE ? false : true)
			boolean opSPRINGER = (null == params.engineSPRINGER ? false : true)
			String[] years = params.inputYears.toString().split(",")
			String minYear = years[0].trim()
			String maxYear = years[1].trim()
			String maxTotal = (null == params.inputTotalMax ? "" : params.inputTotalMax.toString().trim())
			String error = ""
			List<String> okTerminos = new ArrayList<String>()
			List<SearchOperator> okOperators = new ArrayList<SearchOperator>()
			List<SearchComponent> okComponents = new ArrayList<SearchComponent>()
			
			if (opACM || opIEEE || opSCIENCE || opSPRINGER)
			{
				if(terminos instanceof String && terminos != "") // Un solo termino
				{
					okTerminos.add(terminos)
					okOperators.add(SearchOperator.findByValue(operators))
					okComponents.add(SearchComponent.findByValue(componentes))
				}
				else if (!(terminos instanceof String))
				{
					for(int i = 0; i<terminos.size(); i++)
					{
						if(terminos[i].toString().trim() != "")
						{
							okTerminos.add(terminos[i].toString())
							okOperators.add(SearchOperator.findByValue(operators[i].toString()))
							okComponents.add(SearchComponent.findByValue(componentes[i].toString()))
						}
					}
				}

				if(okTerminos.size() == 0)
				{
					error = "ERROR: You must insert a search term."
				}
			}
			else
			{
				error = "ERROR: You must choose a engine search."
			}
			
			if(error != "")
			{
				redirect(controller: 'search', action: 'create',
				 params: [
							 error: error,
							 guidSlr: guidSlr
						 ])
			}
			else
			{
				Map<String, Boolean> engines = new HashMap<String, Boolean>();
				
				engines.put("ACM", opACM)
				engines.put("IEEE", opIEEE)
				engines.put("SCIENCE", opSCIENCE)
				engines.put("SPRINGER", opSPRINGER)
				
				// Creamos el progressBar
				String guidTask = UUID.randomUUID().toString();
				TaskSearch taskSearchInstance = new TaskSearch(percentage: 0,
					titleSlr: slrInstance.title, guidSlr: slrInstance.guid, 
					guid: guidTask, username: slrInstance.userProfile.user.username)
				taskSearchInstance = taskSearchInstance.save(failOnError: true)
				
				mendeleyToolService.insertSearchsBackground(guidTask, slrInstance.title, slrInstance.guid, okTerminos, okOperators, okComponents, minYear, maxYear, maxTotal, engines)
				
				redirect(controller: 'slr', action: 'searchs', params: [guid: guidSlr, isCreating: true])
			}
		}
	}
	
	@Transactional
	def delete()
	{
		String guid = (null == params.guidSearch ? "" : params.guidSearch.toString())
		
		if (guid.equals(""))
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def searchInstance = Search.findByGuid(guid)
			
			if (null == searchInstance)
			{
				redirect(controller: 'index', action: 'index')
			}
			else
			{
				String guidSlr = searchInstance.slr.guid
				
				Reference.deleteAll(searchInstance.references)
				
				searchInstance.delete flush: true
				
				redirect(controller: 'slr', action: 'searchs', params: [guid: guidSlr])
			}
		}
	}
	
	def errorsSearchs()
	{
		def isLogin = springSecurityService.loggedIn
		
		if(isLogin)
		{
			def userLogin = User.get(springSecurityService.principal.id)
			
			if(userLogin.authorities.any { it.authority != "ROLE_USER" })
			{
				[
					taskWithErrors: TaskSearch.findAllByStrExceptionIsNotNull([sort: 'submitDate', order: 'desc'])
				]
			}
			else
			{
				redirect(controller: "index", action: "index")
			}
		}
		else
		{
			redirect(controller: "index", action: "index")
		}
	}
}
