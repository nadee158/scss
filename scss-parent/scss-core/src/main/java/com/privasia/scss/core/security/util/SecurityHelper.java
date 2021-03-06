package com.privasia.scss.core.security.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.config.WebSecurityConfig;
import com.privasia.scss.core.security.jwt.extractor.TokenExtractor;

public final class SecurityHelper {



  private static final String SECURITY_CONTEXT = "SECURITY_CONTEXT";


  public static SecurityContext getSecurityContext(TokenExtractor tokenExtractor) {
    HttpServletRequest request = getRequest();
    SecurityContext securityContext = null;
    if (request.getAttribute(SECURITY_CONTEXT) == null) {
      String token = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
      // RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
      securityContext = new SecurityContext(token);
      request.setAttribute(SECURITY_CONTEXT, securityContext);
    }
    return (SecurityContext) request.getAttribute(SECURITY_CONTEXT);
  }

  public static Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    UserContext userContext = (UserContext) authentication.getPrincipal();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    return ((UserContext) authentication.getPrincipal()).getUserID();

  }


  private static HttpServletRequest getRequest() {
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return sra.getRequest();
  }

  public static String getStaffName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    UserContext userContext = (UserContext) authentication.getPrincipal();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    return ((UserContext) authentication.getPrincipal()).getStaffName();
  }

  public static String getStaffNumber() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    UserContext userContext = (UserContext) authentication.getPrincipal();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    return ((UserContext) authentication.getPrincipal()).getStaffNumber();
  }



}
