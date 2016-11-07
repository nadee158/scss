package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.Login;

public interface LoginRepository extends BaseRepository<Login, Long> {

  public Login findByLoginID(long loginID);
  
  public Optional<Login> findByUserNameContainingIgnoreCase(String username);
  
  @Query(name="Login.accessUserFunction", nativeQuery = true )
  public List<Integer> accessUserFunction(@Param("userName") String userName);

}
