package es.uca.pfc

import grails.transaction.Transactional;

class CriterionController {
	
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def springSecurityService
	
	def index() 
	{ 
		redirect(controller: 'slr', action: 'myList')
	}
	
	@Transactional
	def save()
	{
		boolean isCriterionList = false
		def guidSlr = ""
		
		if (params.guidSlrCriterion.toString().equals("null") || params.guidSlrCriterion.toString().equals(""))
		{
			isCriterionList = true
			guidSlr = params.guidSlr.toString()
		}
		else
		{
			guidSlr = params.guidSlrCriterion.toString()
		}
		
		def slrInstance = Slr.findByGuid(guidSlr)
		
		if (null == slrInstance)
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			String nombreCriterion = params.nombre.toString()
			String descripcionCriterion = params.descripcion.toString()
			String errorCriterion = ""
			boolean successCriterion = false
			
			if(nombreCriterion.equals("null") || nombreCriterion.length() <= 5)
			{
				errorCriterion = "ERROR: The name must have 5 or more characters."
			}
			else if (descripcionCriterion.equals("null") || descripcionCriterion.length() <= 5)
			{
				errorCriterion = "ERROR: The description must have 5 or more characters."
			}
			else
			{
				nombreCriterion = nombreCriterion.toLowerCase();
				def criterionInstance = Criterion.findBySlrAndNameLike(slrInstance, nombreCriterion)
				
				if (null != criterionInstance)
				{
					errorCriterion = "ERROR: The criterion is used."
				}
				
				if (errorCriterion.equals(""))
				{
					criterionInstance = new Criterion(name: nombreCriterion, description: descripcionCriterion, nomenclatura: "cr_"+nombreCriterion)
					slrInstance.addToCriterions(criterionInstance)
					slrInstance.save(failOnError: true)
					successCriterion = true
				}
			}
			
			// Procede de la lista de criterios
			if (isCriterionList)
			{
				redirect(controller: 'slr', action: 'criterions', params: [errorCriterion: errorCriterion, successCriterion: successCriterion, 
																	   nombreCriterion: nombreCriterion, descripcionCriterion: descripcionCriterion,
																	   guid: guidSlr])
			}
			else // Procede de la lista de slrs
			{
				redirect(controller: 'slr', action: 'myList', params: [errorCriterion: errorCriterion, successCriterion: successCriterion,
																		nombreCriterion: nombreCriterion, descripcionCriterion: descripcionCriterion,
																		guidSlrError: guidSlr])
			}
		}
	}
	
	@Transactional
	def edit()
	{
		boolean isCriterionList = false
		def guidSlr = ""
		
		if (params.guidSlrCriterion.toString().equals("null") || params.guidSlrCriterion.toString().equals(""))
		{
			isCriterionList = true
			guidSlr = params.guidSlr.toString()
		}
		else
		{
			guidSlr = params.guidSlrCriterion.toString()
		}
		
		def slrInstance = Slr.findByGuid(guidSlr)
		
		if (null == slrInstance)
		{
			redirect(controller: 'index', action: 'index')
		}
		else
		{
			def idEditCriterion = params.idEditCriterion.toString()
			def criterionEditInstance = Criterion.get(Long.parseLong(idEditCriterion))
			
			String nombreEditCriterion = params.nombreEdit.toString()
			String descripcionEditCriterion = params.descripcionEdit.toString()
			String errorEditCriterion = ""
			boolean successEditCriterion = false
			
			if(nombreEditCriterion.equals("null") || nombreEditCriterion.length() <= 5)
			{
				errorEditCriterion = "ERROR: The name must have 5 or more characters."
			}
			else if (descripcionEditCriterion.equals("null") || descripcionEditCriterion.length() <= 5)
			{
				errorEditCriterion = "ERROR: The description must have 5 or more characters."
			}
			else
			{
				nombreEditCriterion = nombreEditCriterion.toLowerCase();
				def criterionInstance = Criterion.findBySlrAndNameLike(slrInstance, nombreEditCriterion)
				
				if (null != criterionInstance)
				{
					errorEditCriterion = "ERROR: The criterion is used."
				}
				
				if (errorEditCriterion.equals(""))
				{
					criterionEditInstance.name = nombreEditCriterion
					criterionEditInstance.description = descripcionEditCriterion
					slrInstance.save(failOnError: true)
					successEditCriterion = true

				}
			}
			
			redirect(controller: 'slr', action: 'criterions', params: [errorEditCriterion: errorEditCriterion, successEditCriterion: successEditCriterion,
																		nombreEditCriterion: nombreEditCriterion, descripcionEditCriterion: descripcionEditCriterion,
																		guid: guidSlr, idEditCriterion: idEditCriterion])
		}
	}
	
	@Transactional
	def delete()
	{
		def idCriterion = params.idCriterion.toString()
		def guidSlr = params.guidSlr.toString()
		
		def criterionInstance = Criterion.get(Long.parseLong(idCriterion))
		def slrInstance = Slr.findByGuid(guidSlr)
		def criterionIncluded = Criterion.findByNomenclaturaLikeAndSlr("cr_included",slrInstance)
		
		if(criterionInstance != null)
		{
			// Actualizamos las referencias
			for(Search search : slrInstance.searchs)
			{
				for(Reference reference : search.references)
				{
					if (reference.criterion == criterionInstance)
					{
						reference.criterion = criterionIncluded
						reference.save(failOnError: true)
					}
				}
			}
			
			criterionInstance.delete flush: true
		}
		
		redirect(controller: 'slr', action: 'criterions', params: [guid: guidSlr])
	}
}
