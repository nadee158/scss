/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.enums.SolasWeightType;
import com.privasia.scss.common.enums.SolasWeightTypeSize;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.SolasWeightConfig;
import com.privasia.scss.core.repository.SolasWeightConfigRepository;
import com.privasia.scss.master.dto.SolasWeightConfigDTO;


/**
 * @author Janaka
 *
 */
@Service("solasWeightConfigService")
public class SolasWeightConfigService {

  @Autowired
  private SolasWeightConfigRepository solasWeightConfigRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ClientDTO getClientByWebIP(String webIPAddress) {
    Optional<Client> client = clientRepository.findByWebIPAddress(webIPAddress);

    return convertDomainToDto(
        client.orElseThrow(() -> new ResultsNotFoundException("Client Info not Found for IP : " + webIPAddress)));

  }

  public Map<String, SolasWeightConfigDTO> fetchSolasWeightConfig(boolean byWeightType, String weightType,
      int weightTypeSize) {

    List<SolasWeightConfig> list = null;

    SolasWeightType solasWeightType = SolasWeightType.fromValue(weightType);
    SolasWeightTypeSize solasWeightTypeSize = SolasWeightTypeSize.fromValue(weightTypeSize);

    if (byWeightType) {
      list = solasWeightConfigRepository.findByWeightTypeAndWeightTypeSize(solasWeightType, solasWeightTypeSize)
          .orElse(null);
    } else {
      list = solasWeightConfigRepository.findAll().collect(Collectors.toList());
    }

    if (!(list == null || list.isEmpty())) {
      // Map<Object, Object> result2 = list.stream().collect(Collectors.toMap(x -> x.getClass(), x
      // -> x.getName()));

      // Map<String, SolasWeightConfigDTO> result1 =
      // list.stream().collect(Collectors.toMap(SolasWeightConfigDTO::getId, SolasWeightConfigDTO));
      //
      // Map<String, SolasWeightConfigDTO> result1 = list.stream().collect(Collectors
      // .toMap((SolasWeightConfigDTO::getWeightType + "_" +
      // SolasWeightConfigDTO::getWeightTypeSize), x -> x));

    }

    throw new ResultsNotFoundException("No records could be found!");
  }


  private ClientDTO convertDomainToDto(Client client) {
    ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
    return clientDTO;
  }


}
