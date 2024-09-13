package org.zerock.myapp.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ViewResolver {	
	String description() default "Resolving View Path with Prefix & Suffix";
	
} // end @interface


