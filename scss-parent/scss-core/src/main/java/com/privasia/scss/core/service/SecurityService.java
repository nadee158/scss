package com.privasia.scss.core.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.caching.Cache;
import com.privasia.scss.common.caching.CacheFactory;
import com.privasia.scss.common.security.AuditContext;
import com.privasia.scss.common.security.SecurityContext;
import com.privasia.scss.common.security.Session;
import com.privasia.scss.common.security.User;
import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.reponse.SaveSessionDetailsResponse;
import com.privasia.scss.core.repository.LoginRepository;
import com.privasia.scss.core.request.SaveSessionDetailsRequest;



@Service("securityService")
public class SecurityService {

  @Autowired
  private LoginRepository loginRepository;

  private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

  protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


  public UserDetails loadUserByUsername(String username) {

    return null;

  }



  public Session ensureSessionValidity(SecurityContext securityContext, AuditContext auditContext) {
    // TODO: check in cache and return session if 1) existent 2) non-expired 3) user is still valid

    Cache cache = CacheFactory.getCache();

    // if 1) existent
    Session session = cache.get(securityContext.getToken());

    if (!(session == null)) {
      // 2) non-expired
      Date lastAccessedTime = session.getLastRequestTimestamp();
      Date now = Calendar.getInstance().getTime();
      long diff = now.getTime() - lastAccessedTime.getTime();
      System.out.println("diff :" + diff);
      long expiryPeriod = session.getExpires();
      System.out.println("expiryPeriod :" + expiryPeriod);
      if (!(diff > expiryPeriod)) {
        User userFromCache = cache.get(session.getName());
        detailsChecker.check(userFromCache);
        session.setUser(userFromCache);
      } else {
        // user expired
        cache.remove(securityContext.getToken());
        session = null;
        throw new AccountExpiredException(
            messages.getMessage("AccountStatusUserDetailsChecker.expired", "User account has expired"));
      }
    } else {
      // invalid token
      throw new BadCredentialsException("Invalid Token");
    }

    session.setLastRequestTimestamp(Calendar.getInstance().getTime());
    cache.set(securityContext.getToken(), session);

    return session;
  }



  public SaveSessionDetailsResponse saveSessionDetails(SaveSessionDetailsRequest request) {
    Session session = request.getSession();
    Login login = loginRepository.findByLoginID(session.getOwnerId());
    SystemUser systemUser = login.getSystemUser();
    User user = login.constructUser();
    SaveSessionDetailsResponse response=new SaveSessionDetailsResponse();
    
    return response;
  }


}
