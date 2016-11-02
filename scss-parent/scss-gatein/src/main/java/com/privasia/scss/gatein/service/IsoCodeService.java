package com.privasia.scss.gatein.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.dto.IsoCodeDto;
import com.privasia.scss.core.model.ISOCode;
import com.privasia.scss.core.repository.ISOCodeRepository;

@Service("isoCodeService")
@Transactional
public class IsoCodeService {

  @Autowired
  private ISOCodeRepository isoCodeRepository;


  public IsoCodeDto getIsoCodeTarWeight(String isoCodeString) {
    ISOCode isoCode = isoCodeRepository.findByIsoCode(isoCodeString);
    return convertToDTO(isoCode);
  }


  private IsoCodeDto convertToDTO(ISOCode isoCode) {
    // isoCodeDto.setIsoCode(rs.getString("CN_ISO_CODE"));
    // isoCodeDto.setTareWeight(rs.getDouble("CN_TARE_WEIGHT"));
    // isoCodeDto.setContSize(rs.getString("CN_LENGTH"));
    if (!(isoCode == null)) {
      IsoCodeDto dto = new IsoCodeDto();
      dto.setContSize(Integer.toString(isoCode.getLength()));
      dto.setIsoCode(isoCode.getIsoCode());
      dto.setTareWeight((double) isoCode.getTareWeight());
    }

    return null;
  }

}
