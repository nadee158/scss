/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.repository.ClientRepository;


/**
 * @author Janaka
 *
 */
@Service("clientMasterDataService")
public class ClientMasterDataService {

  @Autowired
  private ClientRepository clientRepository;
  
  @Autowired
  private ModelMapper modelMapper;

  @Transactional(propagation=Propagation.REQUIRED, readOnly = true)
  public ClientDTO getClientByWebIP(String webIPAddress) {
    Optional<Client> client = clientRepository.findByWebIPAddress(webIPAddress);
    
    return convertDomainToDto(client.orElseThrow(() -> new ResultsNotFoundException("Client Info not Found for IP : "+webIPAddress)));
    
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly = true)
  public ClientDTO getClientByID(Long primaryKey) {
    
	Optional<Client> client = clientRepository.findOne(primaryKey);
    
    return convertDomainToDto(client.orElseThrow(() -> new ResultsNotFoundException("Client Info not Found for ID : "+primaryKey)));
    
  }
  
  
  private ClientDTO convertDomainToDto(Client client) {
	  ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
	  return clientDTO;
  }

 
}
