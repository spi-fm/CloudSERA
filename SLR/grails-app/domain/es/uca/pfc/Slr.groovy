package es.uca.pfc

class Slr {
	
	static belongsTo = [userProfile: UserProfile]
	static hasMany = [searchs: Search, criterions: Criterion, questions: ResearchQuestion, 
					  specAttributes: SpecificAttribute, taskSearchs: TaskSearch]
	
	String title
	String justification
	String state = 'fase1'
	boolean noDrop = false
	int numVisits = 0
	Date submitDate = new Date()	// fecha creacion
	Date lastModified = new Date()	// fecha modificacion
	String timeString = ""
	String guid = UUID.randomUUID().toString();
	String idmend = "" 				//id de folder en mendeley
	int totalReferences = 0
	//String formatTitle
	
	static constraints = {
		state(inList:['fase1','fase2','fase3'], display: false, blank: false)
		noDrop(display: false)
		numVisits(display: false)
		submitDate(display: false)
		userProfile(nullable: true)
	}
	
	// Sobrecarga m�todo toString()
	String toString()
	{
		return "${title}";
	}
	
	// Indicamos que la justificacion es un texto
	static mapping = {
		justification type: 'text'
		searchs lazy: false
	}
	
	def beforeUpdate = {
		// Actualizamos la fecha de modificacion
		lastModified = new Date()
		
		// Actualizamos el estado
		// ...
	}
	
	def beforeInsert = {
		// Creamos un criterio "Included"
		addToCriterions(new Criterion(name: "included", description: "Referencia incluida en el estudio.", nomenclatura: "cr_included"))
		//criterions = [ new Criterion(name: "included", description: "Referencia incluida en el estudio.", nomenclatura: "cr_included", slr: this) ] // para que lo evalue el test de integracion
	}
	
	def afterInsert = {
		// Insertamos un logger al usuario
		userProfile.addToLoggers(new LoggerSlr(slr: this, tipo: 'crear')).save(failOnError: true)
	}
}
