package com.privasia.scss.core.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.Login;

public interface LoginRepository extends BaseRepository<Login, Long> {

  public Login findByLoginID(long loginID);

  public Optional<Login> findByUserNameContainingIgnoreCase(String username);

  public Optional<Login> findByUserNameIgnoreCase(String username);

  public Optional<Login> findByUserName(String username);
  
  @Query(name = "Login.clientAndRoleAccessRights", nativeQuery = true)
  public Optional<List<BigDecimal>> fetchRoleAndClientRights(@Param("roleID") long roleID, @Param("clientIP") String clientIP);
  
  


}
