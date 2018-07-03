package me.rajput.practice.it.services;

import me.rajput.practice.it.domain.WebEntity;

/**
 * 
 * Description: Interface to define contract to support CRUD operations on Entities.  
 * 
 * @author Deependra Rajput
 * @date Jun 24, 2018
 *
 */
public interface WebEntityService<T extends WebEntity> {
	
	/**
	 * Create the entity into repository.
	 * @param entity
	 * @return
	 */
	Long create(T entity);
	
	/**
	 * Update the entity into repository.
	 * @param entity
	 * @return
	 */
	T update(T entity);
	
	/**
	 * Get the entity by the id.
	 * @param id
	 * @param pageable 
	 * @return
	 */
	T getById(Long id);
	
	/**
	 * Delete the entity from repository.
	 * @param id
	 * @return
	 */
	void deleteById(Long id);

}
