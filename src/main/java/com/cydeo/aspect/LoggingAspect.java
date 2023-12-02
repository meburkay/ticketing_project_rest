package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
//Creates private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class); Instead of manually creat in the log object with this lombok annotation it automatically created as "log" name and we use it directly.
@Slf4j
public class LoggingAspect {

//    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    //this method retrieve the keycloak details then reach to token and take the username from it.
    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void anyProjectAndTaskControllerPC() {}

    @Before("anyProjectAndTaskControllerPC()")
    public void beforeAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint) {
        log.info("Before -> Method: {}, User: {}"
        , joinPoint.getSignature().toShortString()
        , getUsername());
    }

    @AfterReturning(pointcut = "anyProjectAndTaskControllerPC()", returning = "results")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Object results) {
        log.info("After Returning -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername()
                , results.toString());
    }

    @AfterThrowing(pointcut = "anyProjectAndTaskControllerPC()", throwing = "exception")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Exception exception) {
        log.info("After Returning -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername()
                , exception.getMessage());
    }

}
