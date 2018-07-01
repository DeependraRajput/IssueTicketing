package me.rajput.practice.it.controller;

import org.junit.Assert;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rajput.practice.it.exceptions.ApplicationException;
import me.rajput.practice.it.exceptions.ApplicationException.ErrorType;
import me.rajput.practice.it.model.WebEntity;
import me.rajput.practice.it.model.dto.WebDto;
import me.rajput.practice.it.services.WebEntityService;

@Slf4j
@AllArgsConstructor
public abstract class BaseController<T extends WebEntity> {
	
	/** Context Path of the web application. */
	private static final String CONTEXT_PATH = "/rajput";
	
	/** ID parameter. */
	protected static final String ID_PARAM = "/{id}";
	
	private String uriMapping;
	
	private String entityName;
	
	/**
	 * Template method to get the entity and set the apposite HTTP response.  
	 * @param id
	 * @return
	 */
	protected ResponseEntity<?> getEntity(Long id, Pageable pageable) {
		LOGGER.info("Fetching {entityName} with id [{id}]", entityName, id);
		
		ResponseEntity<?> response = null;
		try {
			Assert.assertNotNull("Id must not be null", id);
			WebDto<T> entity = this.getEntityService().getDtoById(id, pageable);
			response = new ResponseEntity<WebDto<T>>(entity, HttpStatus.OK);
			if (entity == null) {
				LOGGER.error("{entityName} with id [{id}] not found.", entityName, id);
				response = new ResponseEntity<>(new ApplicationException(ErrorType.READ_FAILURE,
						entityName+ " with id [" + id + "] not found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while fetching {entityName} with id [{id}] not found.", entityName, id);
			response = new ResponseEntity<>(new ApplicationException(ErrorType.READ_FAILURE,
					entityName+ " with id [" + id + "] not found"), HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	/**
	 * Template method to create an entity and set the apposite HTTP response.
	 * @param user
	 * @param ucBuilder
	 * @return the URI location of new user.
	 */
	protected ResponseEntity<?> createEntity(T entity, UriComponentsBuilder ucBuilder) {
		LOGGER.info("Creating {entityName} : {entity}", entityName, entity);

		ResponseEntity<?> response = null;
		try {
			Assert.assertNull("Id must be null", entity.getId());
			Long id = this.getEntityService().create(entity);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path(CONTEXT_PATH + uriMapping + "/{id}").buildAndExpand(id).toUri());
			response = new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch(Exception e) {
			response = new ResponseEntity<>(new ApplicationException(ErrorType.WRITE_FAILURE, 
					"Unable to create. A " + entityName + " with name [" + entity.getId() + "] already exist."),
					HttpStatus.CONFLICT);
		}

		return response;
	}

	/**
	 * Template method to update an existing entity and set the apposite HTTP response.
	 * @param id
	 * @param user
	 * @return new values of updated user.
	 */
	protected ResponseEntity<?> updateEntity(Long id, T entity) {
		LOGGER.info("Updating {entityName} : {entity}", entityName, entity);

		ResponseEntity<?> response = null;
		try {
			Assert.assertNotNull("Id must not be null", id);
			entity.setId(id);
			entity = this.getEntityService().update(entity);
			response = new ResponseEntity<T>(entity, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Unable to update. {entityName} with id [{id}] not found.", entityName, id);
			response = new ResponseEntity<>(new ApplicationException(ErrorType.WRITE_FAILURE,
					"Unable to upate. " + entityName + " with id [" + id + "] not found."),
					HttpStatus.NOT_FOUND);
		}

		return response;
	}

	/**
	 * Template method to delete an existing entity and set the apposite HTTP response.
	 * @param id
	 * @return
	 */
	protected ResponseEntity<?> deleteEntity(Long id) {
		LOGGER.info("Fetching & Deleting {entityName} with id [{id}]", entityName, id);

		try {
			Assert.assertNotNull("Id must not be null", id);
			this.getEntityService().deleteById(id);
		} catch (Exception e) {
			LOGGER.error("Unable to delete. {entityName} with id [{id}] not found.", entityName, id);
			return new ResponseEntity<>(
					new ApplicationException(ErrorType.WRITE_FAILURE,
							"Unable to delete. " + entityName + " with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Returns the service instance for this controller.
	 * @return
	 */
	abstract protected WebEntityService<T> getEntityService();

}
