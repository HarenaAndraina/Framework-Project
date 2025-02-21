package org.framework.annotation.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Déclaration de l'annotation @Post
@Retention(RetentionPolicy.RUNTIME) // Disponible à l'exécution
@Target(ElementType.METHOD)
public @interface Auth {
    
}
