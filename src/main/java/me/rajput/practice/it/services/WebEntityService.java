package me.rajput.practice.it.services;

import org.springframework.data.domain.Pageable;

import me.rajput.practice.it.model.WebEntity;
import me.rajput.practice.it.model.dto.WebDto;

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
	
	/**
	 * Get the entity by the id.
	 * @param id
	 * @param pageable 
	 * @return
	 */
	WebDto<T> getDtoById(Long id, Pageable pageable);

}
