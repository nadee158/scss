/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.ClientRepository;


/**
 * @author Janaka
 *
 */
@Service("clientMasterDataService")
public class ClientMasterDataService {

  @Autowired
  private ClientRepository clientRepository;

  @Transactional(propagation=Propagation.REQUIRED, readOnly = true)
  public Client getClientByWebIP(String webIPAddress) {
    Optional<Client> client = clientRepository.findByWebIPAddress(webIPAddress);
    
    return client.orElseThrow(() -> new ResultsNotFoundException("Client Info not Found for IP : "+webIPAddress));
    
  }

 
}
