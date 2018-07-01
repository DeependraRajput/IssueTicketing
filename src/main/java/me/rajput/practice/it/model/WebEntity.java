package me.rajput.practice.it.model;

/**
 * 
 * Description: Interface for entities to commonize the getId method. 
 * 
 * @author Deependra Rajput
 * @date Jun 24, 2018
 *
 */
public interface WebEntity {
	
	/**
	 * Method to return the entity id.
	 * @return
	 */
	Long getId();
	
	/**
	 * Method to set the entity id.
	 * @return
	 */
	void setId(Long id);

}
