package com.privasia.scss.cosmos.oracle.repository;

import java.util.Optional;

import com.privasia.scss.cosmos.model.ISOCode;


public interface ISOCodeCosmosRepository extends BaseRepository<ISOCode, String> {

  public Optional<ISOCode> findByIsoCode(String isoCode);

}
