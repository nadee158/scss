/**
 * 
 */
package com.privasia.scss.core.util.service;

import org.springframework.data.domain.AuditorAware;

/**
 * @author Janaka
 *
 */
public class UserIDAuditorAwareService implements AuditorAware<Long> {

	@Override
	public Long getCurrentAuditor() {
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
 
        return ((User) authentication.getPrincipal()).getUsername();*/
        
        return null;
	}

}
