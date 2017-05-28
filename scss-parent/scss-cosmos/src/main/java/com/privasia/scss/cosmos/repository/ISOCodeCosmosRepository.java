package com.privasia.scss.cosmos.repository;

import java.util.Optional;

import com.privasia.scss.cosmos.model.ISOCode;


public interface ISOCodeCosmosRepository extends BaseRepository<ISOCode, String> {

  public Optional<ISOCode> findByIsoCode(String isoCode);

}
