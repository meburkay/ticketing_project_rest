package com.cydeo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//this is how we create annotations in java.
//Here we define the implementations of the annotation. Here we said that we will use this annotation for the methods means top of the method signature.
@Target(ElementType.METHOD)
//Here we say it will be active in the runtime.
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultExceptionMessage {

    //Here our purpose is when we handle the exception by this annotation we want to throw a message and it will be empty as  default but if we want we can modify it by DefaultExceptionMessageDto class.
    String defaultMessage() default "";//We use default keyword here for defining the default implementation of the method. And as a return type String we return "" empty string as a default here.

}