package com.privasia.scss.core.repository;

import com.privasia.scss.core.model.ISOCode;

public interface ISOCodeRepository extends BaseRepository<ISOCode, String> {

  // " SELECT CN_ISO_CODE, CN_TARE_WEIGHT, CN_LENGTH \n"//
  // + " FROM SCSS_ISO_CODES " + " WHERE CN_ISO_CODE = ? ";

  public ISOCode findByIsoCode(String isoCode);

}
