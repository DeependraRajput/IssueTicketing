package me.rajput.practice.it.controller.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CrudRestControllerAspect {
	
	@Pointcut("target(me.rajput.practice.it.controller.aspect.CrudRestController)")
	public void crudAnnotation() {
		
	}
	
	public Object execute(Class<?> klass) {
		return null;
	}
	

}
