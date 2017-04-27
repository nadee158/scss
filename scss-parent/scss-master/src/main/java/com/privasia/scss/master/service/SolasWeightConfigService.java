/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.SolasWeightConfigDTO;
import com.privasia.scss.common.enums.SolasWeightType;
import com.privasia.scss.common.enums.SolasWeightTypeSize;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.SolasWeightConfig;
import com.privasia.scss.core.repository.SolasWeightConfigRepository;

/**
 * @author Janaka
 *
 */
@Service("solasWeightConfigService")
public class SolasWeightConfigService {

  private SolasWeightConfigRepository solasWeightConfigRepository;

  private ModelMapper modelMapper;

  @Autowired
  public void setSolasWeightConfigRepository(SolasWeightConfigRepository solasWeightConfigRepository) {
    this.solasWeightConfigRepository = solasWeightConfigRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public Map<String, SolasWeightConfigDTO> fetchSolasWeightConfig(boolean byWeightType, String weightType,
      int weightTypeSize) {

    List<SolasWeightConfig> list = null;

    if (byWeightType) {
      SolasWeightType solasWeightType = SolasWeightType.fromValue(weightType);
      SolasWeightTypeSize solasWeightTypeSize = SolasWeightTypeSize.fromValue(weightTypeSize);
      list = solasWeightConfigRepository.findByWeightTypeAndWeightTypeSize(solasWeightType, solasWeightTypeSize)
          .orElse(null);
    } else {
      list = solasWeightConfigRepository.findAll().collect(Collectors.toList());
    }

    if (list == null || list.isEmpty())
      throw new ResultsNotFoundException("No records could be found!");

    Map<String, SolasWeightConfigDTO> map = new HashMap<String, SolasWeightConfigDTO>();
    list.forEach(item -> {
      SolasWeightConfigDTO dto = convertDomainToDto(item);
      map.put(dto.getWeightType() + "_" + dto.getWeightTypeSize(), dto);
    });
    return map;

  }

  private SolasWeightConfigDTO convertDomainToDto(SolasWeightConfig solasWeightConfig) {
    SolasWeightConfigDTO solasWeightConfigDTO = modelMapper.map(solasWeightConfig, SolasWeightConfigDTO.class);
    return solasWeightConfigDTO;
  }

}
