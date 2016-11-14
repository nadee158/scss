package com.privasia.scss.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.core.model.Client;

/**
 * @author Janaka
 *
 */

public interface ClientRepository extends BaseRepository<Client, Long> {

  @Query(name = "Client.getClientUnitNoByIp")
  Optional<String> getClientUnitNoByIp(@Param("webIPAddress") String webIPAddress);


  @Query(name = "Client.getlpsIPAddressByClientID")
  Optional<ClientDTO> getlpsIPAddressInfoByClientID(@Param("clientID") long clientID);

  @Query(name = "Client.getClientUnitNoByClientID")
  Optional<ClientDTO> getClientUnitNoByClientID(@Param("clientID") long clientID);

  @Query(name = "Client.getClientUnitNoAndCosmosPortNoByClientID")
  Optional<ClientDTO> getClientUnitNoAndCosmosPortNoByClientID(@Param("clientID") long clientID,
      @Param("cosmosPortNo") int cosmosPortNo);



}
