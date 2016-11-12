package com.privasia.scss.client.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.repository.ClientRepository;

@Service("clientService")
@Transactional
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;


  @Transactional()
  public String getClientUnitNoByIp(String webIPAddress) {
    Optional<String> unitNo = clientRepository.getClientUnitNoByIp(webIPAddress);
    
    return unitNo.orElseThrow(() -> new ResultsNotFoundException("Unit No was not found!"));
    
  }



}
