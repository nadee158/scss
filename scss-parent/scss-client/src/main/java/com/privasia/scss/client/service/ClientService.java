package com.privasia.scss.client.service;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.repository.ClientRepository;

@Service("clientService")
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;


  @Transactional(propagation=Propagation.REQUIRED, readOnly = true)
  public String getClientUnitNoByIp(String webIPAddress) {
    Optional<String> unitNo = clientRepository.getClientUnitNoByIp(webIPAddress);
    
    return unitNo.orElseThrow(() -> new ResultsNotFoundException("Unit No was not found!"));
    
  }

}
