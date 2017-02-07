package com.privasia.scss.core.repository;

import java.util.Optional;

import com.privasia.scss.core.model.Login;

public interface LoginRepository extends BaseRepository<Login, Long> {

  public Login findByLoginID(long loginID);

  public Optional<Login> findByUserNameContainingIgnoreCase(String username);

  public Optional<Login> findByUserNameIgnoreCase(String username);

  public Optional<Login> findByUserName(String username);


}
