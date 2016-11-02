package com.privasia.scss.gatein.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.ClientRepository;

/**
 * @author nadee158 - Has methods which were in client object
 */
@Service("clientService")
@Transactional
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;

  public String getLaneNo(String clientId) throws Exception {
    // String sql =
    // "SELECT cli_unitno" + " FROM" + " scss_client" + " WHERE" + " cli_clientid_seq=" +
    // SQL.format(clientId);
    Optional<Client> clientOpt = clientRepository.findOne(Long.parseLong(StringUtils.trim(clientId)));
    Client client = clientOpt.orElse(null);
    if (!(client == null)) {
      return client.getUnitNo();
    }
    return null;
  }


}
