package com.privasia.scss.core.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.annotation.HasAuthority;
import com.privasia.scss.common.security.model.UserContext;


@Aspect
@Component
public class SecurityAspect {

  private static final Log log = LogFactory.getLog(SecurityAspect.class);

  @Before(value = "execution(* *.*(..)) && @annotation(hasAuthority)")
  public void hasAuthority(JoinPoint joinPoint, HasAuthority hasAuthority) {
    log.debug("Methode Signature : " + joinPoint.getSignature().getName());
    String[] functions = hasAuthority.functions();

    log.debug("functions fro annotation " + functions);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.debug("authentication " + authentication);

    UserContext userContext = (UserContext) authentication.getPrincipal();
    log.debug("userContext " + userContext);
    log.debug("userContext.getFunctions() " + userContext.getFunctions());

    if (userContext.getFunctions() == null || userContext.getFunctions().isEmpty()) {
      throw new AccessDeniedException("You are not authorized to access this method!");
    }

    boolean isAuthorized = false;
    for (String function : functions) {
      log.debug("function " + function);
      if (userContext.getFunctions().contains(function)) {
        isAuthorized = true;
      }
    }
    if (!(isAuthorized)) {
      throw new AccessDeniedException("You are not authorized to access this method!");
    }
  }



}
