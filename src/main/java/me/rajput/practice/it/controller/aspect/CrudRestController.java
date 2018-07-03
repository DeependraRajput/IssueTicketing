package me.rajput.practice.it.controller.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.RestController;

import me.rajput.practice.it.domain.WebEntity;
import me.rajput.practice.it.services.WebEntityService;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
public @interface CrudRestController {
	
	/**
	 * The web entity supported
	 */
	Class<? extends WebEntity> webEntity();
	
	/**
	 * The service for this entity.
	 */
	Class<? extends WebEntityService<? extends WebEntity>> webEntityService();
	
	/**
	 * URI mapping if given
	 */
	String uriMapping() default "";

}
