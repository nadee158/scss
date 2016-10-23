package com.privasia.scss.core.repository;

import com.privasia.scss.core.model.Login;

public interface LoginRepository extends BaseRepository<Login, Long> {

  public Login findByLoginID(long loginID);

}
