package es.uca.pfc

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date;
import org.apache.commons.validator.routines.EmailValidator

import grails.transaction.Transactional

@Transactional
class ToolService {

	Set<String> authors = new TreeSet<String>()
	Set<String> criterions = new TreeSet<String>()
	//Set<String> years = new TreeSet<String>()
	Set<String> engines = new TreeSet<String>()
	Set<String> languages = new TreeSet<String>()
	Set<String> departaments = new TreeSet<String>()
	Set<String> types = new TreeSet<String>()
	Map<String, Set<String>> specificAttributesMap = new HashMap<String, Set<String>>()
	String minYear = ""
	String maxYear = ""
	
	def sessionRegistry
	
    def serviceMethod() {

    }
	
	boolean isDigit(String strNumber)
	{
		boolean ok = true;
		for (char c : strNumber.toCharArray())
		{
			if (!Character.isDigit(c))
			{
				ok = false;
				break;
			}
		}
		return ok;
	}
	
	// Método para actualizar el estado de un SLR
	String updateStatus(Slr slrInstance)
	{
		String estado = slrInstance.state.toString();
		
		// Un SLR pasa a estado 2 si tiene como m�nimo 2 criterios y 1 pregunta
		// Un SLR pasa a estado 3 si tiene todo lo del estado 2 y una b�squeda realizada
		
		if(slrInstance.state.equals("fase1"))
		{
			if(slrInstance.criterions.size() >=2 && slrInstance.questions.size() >= 1 && slrInstance.searchs.size() >= 1)
			{
				estado = "fase3"
			}
			else if(slrInstance.criterions.size() >=2 && slrInstance.questions.size() >= 1)
			{
				estado = "fase2"
			}
		}
		else if(slrInstance.state.equals("fase2"))
		{
			if(slrInstance.criterions.size() >=2 && slrInstance.questions.size() >= 1 && slrInstance.searchs.size() >= 1)
			{
				estado = "fase3"
			}
			else if(!(slrInstance.criterions.size() >=2 && slrInstance.questions.size() >= 1))
			{
				estado = "fase1"
			}
		}
		else // fase 3
		{
			if(slrInstance.criterions.size() >=2 && slrInstance.questions.size() >= 1 && slrInstance.searchs.size() == 0)
			{
				estado = "fase2"
			}
			else if(!(slrInstance.criterions.size() >=2 && slrInstance.questions.size() >= 1))
			{
				estado = "fase1"
			}
		}
		
		return estado
	}
	
	public Set<String> getTypes() {
		return types;
	}

	public void setTypes(Set<String> types) {
		this.types = types;
	}

	public Set<String> getAuthors() {
		return authors;
	}

	public Set<String> getCriterions() {
		return criterions;
	}

	public String getMinYear() {
		return minYear;
	}
	
	public String getMaxYear() {
		return maxYear;
	}

	public Set<String> getEngines() {
		return engines;
	}

	public Set<String> getLanguages() {
		return languages;
	}

	public Set<String> getDepartaments() {
		return departaments;
	}
	
	public Map<String, Set<String>> getSpecificAttributesMap() {
		return specificAttributesMap;
	}
	
	// Método para obtener el �ltimo login de un usuario en formato cadena
	String getTimeString(Date date)
	{
		String timeToString = "";
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal1.setTime(date);
		cal2.setTime(new Date());
		
		def milis1 = cal1.getTimeInMillis();
		def milis2 = cal2.getTimeInMillis();
		
		def diff = milis2 - milis1;
		
		// Calculamos la diferencia en segundos
		def diffSeconds = diff / 1000;
		if(diffSeconds < 60)
		{
			timeToString = Math.round(diffSeconds) + " seconds ago.";
		}
		else
		{
			def diffMinutes = diff / (60 * 1000);
			if(diffMinutes < 60)
			{
				timeToString = Math.round(diffMinutes) + " minutes ago.";
			}
			else
			{
				def diffHours = diff / (60 * 60 * 1000);
				if(diffHours < 24)
				{
					timeToString = Math.round(diffHours) + " hours ago.";
				}
				else
				{
					def diffDays = diff / (24 * 60 * 60 * 1000);
					if(diffDays < 30)
					{
						timeToString = Math.round(diffDays) + " days ago.";
					}
					else
					{
						def diffMonths = diff / (30 * 24 * 60 * 60 * 1000);
						if(diffMonths <= 6)
						{
							timeToString = Math.round(diffMonths) + " months ago.";
						}
						else
						{
							timeToString = "6 or more months ago";
						}
					}
				}
			}
		}
		
		return timeToString;
	}
	
	List<UserProfile> updateTimeStringUser(List<UserProfile> profiles)
	{
		List<UserProfile> updateProfiles = new ArrayList<UserProfile>()
		for(UserProfile userProfileInstance : profiles)
		{
			Date date = userProfileInstance.ultimaConexion
			String timeToString = getTimeStringFromNow(date);
			
			userProfileInstance.timeString = timeToString
			userProfileInstance.save(failOnError: true)
			updateProfiles.add(userProfileInstance)
		}
		
		return updateProfiles
	}
	
	List<Logger> updateTimeStringLogger(List<Logger> loggers)
	{
		List<Logger> updateLoggers = new ArrayList<Logger>()
		for(Logger loggerInstance : loggers)
		{
			Date date = loggerInstance.submitDate
			String timeToString = getTimeStringFromNow(date);
			
			loggerInstance.timeString = timeToString
			loggerInstance.save(failOnError: true)
			updateLoggers.add(loggerInstance)
		}
		
		return updateLoggers
	}
	
	List<Slr> updateTimeStringSlr(List<Slr> slrs)
	{
		List<Slr> updateSlrs = new ArrayList<Slr>()
		for(Slr slrInstance : slrs)
		{
			Date date = slrInstance.submitDate
			String timeToString = getTimeStringFromNow(date);
			
			slrInstance.timeString = timeToString
			slrInstance.save(failOnError: true)
			updateSlrs.add(slrInstance)
		}
		
		return updateSlrs
	}
	
	String getTimeStringFromNow(Date date)
	{
		String timeToString = "";
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
			
		cal1.setTime(date);
		cal2.setTime(new Date());
			
		def milis1 = cal1.getTimeInMillis();
		def milis2 = cal2.getTimeInMillis();
			
		def diff = milis2 - milis1;
			
		// Calculamos la diferencia en segundos
		def diffSeconds = diff / 1000;
		if(diffSeconds < 60)
		{
			timeToString = Math.round(diffSeconds) + " seconds ago.";
		}
		else
		{
			def diffMinutes = diff / (60 * 1000);
			if(diffMinutes < 60)
			{
				timeToString = Math.round(diffMinutes) + " minutes ago.";
			}
			else
			{
				def diffHours = diff / (60 * 60 * 1000);
				if(diffHours < 24)
				{
					timeToString = Math.round(diffHours) + " hours ago.";
				}
				else
				{
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
					timeToString = df.format(date)
				}
			}
		}
		
		return timeToString
	}
	
	Set<Reference> getReferences(Slr slrInstance)
	{
		SortedSet<Reference> references = new TreeSet<Reference>(new Reference());
		
		for(Search search : slrInstance.searchs)
		{
			for (Reference reference : search.references)
			{
				references.add(reference)
			}
			//references.addAll(search.references)
		}
		
		return references;
	}	
	
	void getAllParamsFilter(Set<Reference> references)
	{
		authors = new TreeSet<String>()
		criterions = new TreeSet<String>()
		engines = new TreeSet<String>()
		languages = new TreeSet<String>()
		departaments = new TreeSet<String>()
		types = new TreeSet<String>()
		specificAttributesMap = new HashMap<String, Set<String>>()
		
		minYear = "9999"
		maxYear = "0"
		
		for(Reference reference : references)
		{
			// Criterio
			if(!criterions.contains(reference.criterion.name))
			{
				criterions.add(reference.criterion.name)
			}
			
			// Min Year
			if (Integer.parseInt(minYear) > Integer.parseInt(reference.year))
			{
				minYear = reference.year
			}
			
			// Max Year
			if (Integer.parseInt(maxYear) < Integer.parseInt(reference.year))
			{
				maxYear = reference.year
			}
			
			// Engine
			if (!engines.contains(reference.engine.name))
			{
				engines.add(reference.engine.name)
			}
			
			// Idioma
			if (!languages.contains(reference.language.name))
			{
				languages.add(reference.language.name)
			}
			
			// Departamento
			if (reference.department != null && !reference.department.equals("") 
				&& !departaments.contains(reference.department))
			{
				departaments.add(reference.department)
			}
			
			// Type
			if (!types.contains(reference.type.nombre))
			{
				types.add(reference.type.nombre)
			}
			
			// Autores
			for(String author : reference.authors)
			{
				if(!authors.contains(author))
				{
					authors.add(author)
				}
			}
			
			// Atributos especificos
			for (SpecificAttributeReference sar : reference.specificAttributes)
			{
				Set<String> values = new HashSet<String>()
				if(specificAttributesMap.get(sar.attribute.name) != null)
				{
					values = specificAttributesMap.get(sar.attribute.name)
				}
				if(!sar.value.equals("") && !sar.value.equals("--"))
				{
					values.add(sar.value)
					specificAttributesMap.put(sar.attribute.name, values)
				}
			}
		}
	}
	
	SortedSet<Reference> getReferencesWithFilter(String guidSlr, String strSearch)
	{
		SortedSet<Reference> referencesFilter = new TreeSet<Reference>(new Reference())
		
		Slr slrInstance = Slr.findByGuid(guidSlr)
		
		String[] filters = strSearch.trim().split("AND")
		
		List<String> engines = new ArrayList<String>()
		List<String> criterions = new ArrayList<String>()
		List<String> languages = new ArrayList<String>()
		List<String> departments = new ArrayList<String>()
		List<String> types = new ArrayList<String>()
		List<String> authors = new ArrayList<String>()
		Map<String, Set<String>> specificAttributesMap = new HashMap<String, Set<String>>()
		
		String min_year = "";
		String max_year = "";

		for(String filter : filters)
		{
			if (filter.trim().contains("engine="))
			{
				engines.add(filter.trim().replaceAll("engine=", ""))
			}
			else if (filter.trim().contains("criterion="))
			{
				criterions.add(filter.trim().replaceAll("criterion=", ""))
			}
			else if (filter.trim().contains("language="))
			{
				languages.add(filter.trim().replaceAll("language=", ""))
			}
			else if (filter.trim().contains("department="))
			{
				departments.add(filter.trim().replaceAll("department=", ""))
			}
			else if (filter.trim().contains("type="))
			{
				types.add(filter.trim().replaceAll("type=", ""))
			}
			else if (filter.trim().contains("author="))
			{
				authors.add(filter.trim().replaceAll("author=", ""))
			}
			else if (filter.trim().contains("min_year="))
			{
				min_year = filter.trim().replaceAll("min_year=")
			}
			else if (filter.trim().contains("max_year="))
			{
				max_year = filter.trim().replaceAll("max_year=")
			}			
			// Insertar aqui otro campo de filtro antes que los atributos especificos
			// ...
			else if (slrInstance.specAttributes.size() > 0)
			{
				String[] fieldsFilter = filter.split("=")
				if(fieldsFilter.length == 2)
				{
					String nameAS = fieldsFilter[0].trim().replaceAll("@as@","")
					
					def attributeSpecific = SpecificAttribute.findBySlrAndNameIlike(slrInstance,nameAS)
					
					String value = fieldsFilter[1].trim()
					if(attributeSpecific != null && !value.equals(""))
					{
						Set<String> values = new HashSet<String>()
						if(specificAttributesMap.get(nameAS) != null)
						{
							values = specificAttributesMap.get(nameAS)
						}
						values.add(value)
						specificAttributesMap.put(nameAS, values)
					}
				}
			}
		}
		
		int yearMinInt = (min_year.equals("") ? 1800 : Integer.parseInt(min_year));
		int yearMaxInt = (min_year.equals("") ? Calendar.getInstance().get(Calendar.YEAR) : Integer.parseInt(max_year));
		
		for(Search search : slrInstance.searchs)
		{
			for(Reference reference : search.references)
			{
				boolean inserted = true;
				
				if(strSearch == null || !strSearch.equals(""))
				{
					if (!(Integer.parseInt(reference.year.toString()) >= yearMinInt && Integer.parseInt(reference.year.toString()) <= yearMaxInt))
					{
						inserted = false;
					}
					else if (engines.size() > 0 && !engines.contains(reference.engine.name))
					{
						inserted = false;
					}
					else if (criterions.size() > 0 && !criterions.contains(reference.criterion.name))
					{
						inserted = false;
					}
					else if (languages.size() > 0 && !languages.contains(reference.language.name))
					{
						inserted = false;
					}
					else if (departments.size() > 0 && !departments.contains(reference.department))
					{
						inserted = false;
					}
					else if (types.size() > 0 && !types.contains(reference.type.nombre))
					{
						inserted = false;
					}
					else if (authors.size() > 0)
					{
						boolean isAuthor = false
						for(String author : reference.authors) {
							if (authors.contains(author))
							{
								isAuthor = true
								break;
							}
						}
						
						inserted = inserted && isAuthor
					}
					// Hacer comprobaciones aqui de otros campos antes de los atributos
					// especificos
					// ....
					else if (specificAttributesMap.size() > 0)
					{
						for(Map.Entry<String, Set<String>> entry : specificAttributesMap.entrySet())
						{
							String nameAS = entry.getKey()
							Set<String> values = entry.getValue()
							
							def specificAtt = SpecificAttribute.findBySlrAndNameIlike(slrInstance,nameAS)
							
							if (specificAtt == null)
							{
								inserted = false
							}
							else
							{
								def specificAttRef = SpecificAttributeReference.findByAttributeAndReference(specificAtt,reference)
								
								if (specificAttRef == null || !values.contains(specificAttRef.value))
								{
									inserted = false
								}
							}
						}
					}
				} // fin-filters 
				
				if (inserted)
				{
					referencesFilter.add(reference)
				}
			}
		}
		
		return referencesFilter;
	}
	
	String formatSearchString(String strSearch, String filter)
	{
		String strSearchAux = strSearch.replaceAll("@as@","");
		String filterAux = filter.replaceAll("@as@","");
		String result = "";
		
		if (strSearchAux.indexOf(filterAux) != -1)
		{
			String[] strArray = strSearchAux.trim().split("AND")
			
			for(String str : strArray)
			{
				if (!str.trim().equals(filterAux.trim()) && !str.trim().equals(""))
				{
					if(result.equals(""))
					{
						result = str.trim()
					}
					else
					{
						result += " AND " + str.trim()
					}
				}
			}
		}
		else
		{
			if (strSearchAux.equals(""))
			{
				result = filterAux;
			}
			else
			{
				result = strSearchAux.trim() + " AND " + filterAux.trim()
			}
		}
		
		return result;
	}
	
	SortedSet<Reference> getPaginatedReferences(SortedSet<Reference> references, int maxPerPage, int offset)
	{
		SortedSet<Reference> referencesPaginated = new TreeSet<Reference>(new Reference())
		
		int cont = offset;
		
		while (cont < (offset+maxPerPage) && cont < references.size())
		{
			referencesPaginated.add(references.getAt(cont))
			cont++
		}
		
		return referencesPaginated
	}
	
	boolean validateAuthorString(String strAuthors)
	{
		boolean ok = true;
		
		String[] authors = strAuthors.toLowerCase().trim().split(",")
		
		if(authors.size() == 0)
		{
			ok = false;
		}
		else
		{
			for(String author : authors)
			{
				String[] datas = author.split(" ")
				if(datas.size() < 2)
				{
					ok = false;
					break;
				}
			}
		}
		
		return ok;
	}
	
	String getCitationKey(Reference referenceInstance, String[] authors)
	{
		String citation_key = "";
		
		for(String author : authors)
		{
			String[] names = author.split(" ")
			if(names.length >= 2)
			{
				citation_key = names[1].trim().toLowerCase() + referenceInstance.year
			}
		}
		
		return citation_key;
	}
	
	String getCitationKey(String bibtex)
	{
		String citKey = "";
		
		if(bibtex == null) {
			return "";
		}
		
		String bibtexFormat = bibtex.replaceAll("\n", "").replaceAll("\t", "").trim()
		String[] fields = bibtexFormat.split(",")
		
		// El citation key esta en el primer campo --> Ej: @article{McConaghy2011TrustworthyBlocks
		if(fields.length >= 1) {
			String[] fields01 = fields[0].split("\\{")
			if (fields01.length >= 1) {
				citKey = fields01[1].trim()
			}
		}
		return citKey;
	}
	
	void removeLoggersBetweenUsers(UserProfile userLoginProfile, UserProfile userFriendProfile)
	{
		Logger log = null;
		List<Logger> loggersToRemove = new ArrayList<Logger>()
		
		for (Logger loggerInstance : userLoginProfile.loggers)
		{
			log = null;
			if(loggerInstance instanceof LoggerFriend)
			{
				LoggerFriend loggerFriend = (LoggerFriend)loggerInstance;
				if(loggerInstance.friendProfile == userFriendProfile)
				{
					log = loggerFriend
				}
			}
			
			if(log != null)
			{
				loggersToRemove.add(log)
			}
		}
		
		for(Logger loggerInstance : loggersToRemove)
		{
			userLoginProfile.removeFromLoggers(loggerInstance)
		}
		
		Logger.deleteAll(loggersToRemove)
		
		userLoginProfile.save(failOnError: true)
	}
	
	void createLoggersBetweenUsers(UserProfile userLoginProfile, UserProfile userFriendProfile)
	{
		Logger log = null
		
		for(Logger loggerFriendInstance : userFriendProfile.loggers)
		{
			log = null
			if(!loggerFriendInstance.tipo.contains("fr-"))
			{
				if (loggerFriendInstance.tipo == 'bienvenida')
				{
					log = new LoggerFriend(friendProfile: userFriendProfile, tipo: 'fr-bienvenida', submitDate: loggerFriendInstance.submitDate)
				}
				else if (loggerFriendInstance.tipo == 'crear')
				{
					log = new FriendLoggerSlr(friendProfile: userFriendProfile, tipo: 'fr-crear', submitDate: loggerFriendInstance.submitDate,
												slr: loggerFriendInstance.slr)
				}
				else if (loggerFriendInstance.tipo == 'buscar')
				{
					log = new FriendLoggerSlr(friendProfile: userFriendProfile, tipo: 'fr-buscar', submitDate: loggerFriendInstance.submitDate,
						slr: loggerFriendInstance.slr, isSearch: true)
				}
				else if (loggerFriendInstance.tipo == 'seguir')
				{
					log = new FriendLoggerFriend(friendProfile: userFriendProfile, tipo: 'fr-seguir',
												 submitDate: loggerFriendInstance.submitDate,
												 friendFriendProfile: loggerFriendInstance.friendProfile)
				}
				userLoginProfile.addToLoggers(log)
			}
		}

		log = new LoggerFriend(friendProfile: userFriendProfile, tipo: 'seguir')
		userLoginProfile.addToLoggers(log)
		userLoginProfile.save(failOnError: true)
	}
	
	String converterToStrOptions(List<Object> elements)
	{
		String strOptions = "";

		for(Object element : elements)
		{
			String value = "";
			String name = "";
			if(element instanceof SearchOperator)
			{
				value = ((SearchOperator)element).value
				name = ((SearchOperator)element).name
			}
			else if(element instanceof SearchComponent)
			{
				value = ((SearchComponent)element).value
				name = ((SearchComponent)element).name
			}
			
			strOptions += "<option value='" + value + "'>" + name + "</option>"
		}
		
		return strOptions;
	}
	
	void deleteSlr(Slr slrInstance)
	{
		if(slrInstance != null)
		{
			// Borramos rreferencias con autores y atributos especificos
			for(Search search : slrInstance.searchs)
			{
				for (Reference reference : search.references)
				{
					// Borramos las referencias con los atributos especificos
					SpecificAttributeReference.deleteAll(SpecificAttributeReference.findAllByReference(reference))
				
					reference.criterion = null // criterio a nulo
				}
			}
			
			// Borramos loggers
			LoggerSlr.deleteAll(LoggerSlr.findAllBySlr(slrInstance))
			FriendLoggerSlr.deleteAll(FriendLoggerSlr.findAllBySlr(slrInstance))
			
			// Borramos notificaciones
			Collection<NotificationSlr> notificationsRemove = NotificationSlr.findAllBySlr(slrInstance);
			notificationsRemove*.delete();
			
			slrInstance.delete flush: true
		}
	}
	
	boolean isValidEmail(String email)
	{
		return EmailValidator.getInstance(true).isValid(email);
	}
	
	boolean canEnabledDisabled(User userLogin, User userInstance)
	{
		boolean success = false;
		
		if((userLogin.authorities.any { it.authority == "ROLE_SUPER" } && userInstance.authorities.any { it.authority != "ROLE_SUPER" }) || 
		   (userLogin.authorities.any { it.authority == "ROLE_ADMIN" } && userInstance.authorities.any { it.authority == "ROLE_USER" }))
		{
			success = true;
		}
		
		return success;
	}
	
	boolean canChangeRole(User userLogin, User userInstance)
	{
		return canEnabledDisabled(userLogin, userInstance)
	}
	
	List<UserProfile> checkStatusOnline(List<UserProfile> usersProfiles, List<User> usersOnline)
	{		
		if(usersProfiles.size() > 0)
		{		
			List<UserProfile> userProfilesOnline = new ArrayList<UserProfile>()

			for(User user : usersOnline)
			{
				userProfilesOnline.add(user.userProfile)
			}
			
			for(UserProfile userProfile : usersProfiles)
			{
				if(userProfilesOnline.contains(userProfile))
				{
					userProfile.isOnline = true;
					userProfile.save(failOnError: true, flush: true)
				}
			}
		}
		
		return usersProfiles
	}
	
	List<User> getUsersOnline()
	{
		// Obtenemos los usuarios que se encuentran conectados y posteriormente hacemos una 'conversion' para
		// poder obtener toda su informacion
		def usersOnlineAux = new ArrayList<User>(sessionRegistry.getAllPrincipals())
		List<User> usersOnline = new ArrayList<User>()
		for(int i = 0; i<usersOnlineAux.size(); i++)
		{
			usersOnline.add(User.get(Long.parseLong(usersOnlineAux.get(i).id.toString())))
		}
		
		return usersOnline
	}
	
	String getStringIfNotNull(String strValue)
	{
		if (strValue != null)
		{
			return strValue;
		}
		
		return "";
	}
	
	Date addMonthToDate(Date fecha, int months)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.MONTH, months);  // numero de d�as a a�adir, o restar en caso de d�as<0
		return calendar.getTime();
	}
	
	List<String> extractForenameAndSurnameAuthor(String forename, String surname)
	{
		List<String> names = new ArrayList<String>()
		
		if(forename != null && surname != null && !forename.equals("") && !surname.equals(""))
		{
			names.add(forename)
			names.add(surname)
		}
		else if(forename == null || forename.equals(""))
		{
			names = extractForenameAndSurnameAuthorAux(surname)
		}
		else if(surname == null || surname.equals(""))
		{
			names = extractForenameAndSurnameAuthorAux(forename)
		}
		else
		{
			names.add("Mr")
			names.add("X")
		}
		
		return names
	}
	
	private List<String> extractForenameAndSurnameAuthorAux(String name)
	{
		List<String> names = new ArrayList<String>()
		String[] nameAuxArray = name.replaceAll(","," ").trim().split(" ")
		if(nameAuxArray.length > 1)
		{
			names.add(nameAuxArray[0])
			String nameRest = ""
			for(int i = 1; i<nameAuxArray.length; i++)
			{
				nameRest += nameAuxArray[i] + " "
			}
			names.add(nameRest.trim())
		}
		else
		{
			names.add("Mr.")
			names.add(name)
		}
		
		return names
	}
	
	List<TaskSearch> getNotCompletedTaskSearchFromUser(User userInstance)
	{
		List<TaskSearch> taskSearchList = getAllTaskSearchFromUser(userInstance)
		List<TaskSearch> taskNotCompleted = new ArrayList<TaskSearch>()
		
		for(TaskSearch t : taskSearchList)
		{
			if (t.percentage != 100 && !t.hasErrors)
			{
				taskNotCompleted.add(t)
			}
		}
		
		return taskNotCompleted
	}
	
	TaskSearch getTaskSearchByGuid(String guid, User userInstance)
	{
		TaskSearch taskSearch = null
		
		List<TaskSearch> allTaskSearchs =  getAllTaskSearchFromUser(userInstance)
		
		for(TaskSearch ts : allTaskSearchs)
		{
			if(ts.guid.equals(guid)) {
				taskSearch = ts;
				break;
			}
		}
		
		return taskSearch
	}
	
	List<TaskSearch> getAllTaskSearchFromUser(User userInstance)
	{
		def taskSearchList = TaskSearch.list(sort: 'submitDate', order: 'desc')
		List<TaskSearch> taskSearchUser = new ArrayList<TaskSearch>()
		
		List<String> guidsSlr = new ArrayList<String>()
		for(Slr slrInstance : userInstance.userProfile.slrs)
		{
			guidsSlr.add(slrInstance.guid)
		}
		
		for(TaskSearch t : taskSearchList)
		{
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			
			cal1.setTime(t.endDate);
			cal2.setTime(new Date());
			
			def milis1 = cal1.getTimeInMillis();
			def milis2 = cal2.getTimeInMillis();
			def diff = milis2 - milis1;
			def diffMinutes = diff / (60 * 1000);
			
			// Cuando pase media hora, los progress bar desapareceran de la lista
			if(guidsSlr.contains(t.guidSlr) && diffMinutes < 30)
			{
				taskSearchUser.add(t);
			}
		}
		
		return taskSearchUser
	}
	
	List<Slr> getSlrFromFriendsAndMe(UserProfile userProfileInstance)
	{
		return Slr.findAllByUserProfileInListOrUserProfileLike(userProfileInstance.friends,
																userProfileInstance, 
																[max: 5, sort: 'submitDate', order: 'desc'])
	}
	
	boolean deleteSpecificAttribute(SpecificAttribute attributeInstance, Slr slrInstance)
	{
		boolean isDeleted = true;
		
		try
		{
			if(attributeInstance != null)
			{
				// Actualizamos las referencias
				for(Search search : slrInstance.searchs)
				{
					for(Reference reference : search.references)
					{
						SpecificAttributeReference.deleteAll(SpecificAttributeReference.findAllByReferenceAndAttribute(reference, attributeInstance))
					}
				}
				
				attributeInstance.delete flush: true
			}
		}
		catch(Exception ex) { isDeleted = false; }
		
		return isDeleted;
	}
}
