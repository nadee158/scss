package com.privasia.scss.core.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.repository.LoginRepository;



@Service("securityService")
public class SecurityService {

  @Autowired
  private LoginRepository loginRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Optional<Login> getByUsername(String username) throws UsernameNotFoundException {
    return this.loginRepository.findByUserNameContainingIgnoreCase(username);
  }



}
