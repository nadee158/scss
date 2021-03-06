/**
 * 
 */
package com.privasia.scss.cosmos.util;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.privasia.scss.common.security.model.UserContext;

/**
 * @author Janaka
 *
 */
public class UserIDAuditorAwareService implements AuditorAware<Long> {

  @Override
  public Long getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    return ((UserContext) authentication.getPrincipal()).getUserID();

  }

}
