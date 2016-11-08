package com.privasia.scss.core.repository;

import java.util.Optional;

import com.privasia.scss.core.model.ISOCode;

public interface ISOCodeRepository extends BaseRepository<ISOCode, String> {

  public Optional<ISOCode> findByIsoCode(String isoCode);

}
