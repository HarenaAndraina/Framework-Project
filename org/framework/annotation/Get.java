package org.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Déclaration de l'annotation @Get
@Retention(RetentionPolicy.RUNTIME) // Disponible à l'exécution
@Target(ElementType.METHOD)         // Applicable sur les méthodes
public @interface Get {

}
