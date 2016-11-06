package com.privasia.scss.client.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.ClientRepository;

@Service("clientService")
@Transactional
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;


  @Transactional()
  public String getClientUnitNoByIp(String webIPAddress) {
    Optional<Client> client = clientRepository.getClientUnitNoByIp(webIPAddress);
    if (client.isPresent()) {
      Client found = client.orElse(null);
      if (!(found == null)) {
        return found.getUnitNo();
      }
    }
    throw new ResultsNotFoundException("Client was not found!");
  }



}
