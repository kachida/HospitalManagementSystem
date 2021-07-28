package com.usersvc.aspect;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * The Interface Loggable.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Loggable {

}
