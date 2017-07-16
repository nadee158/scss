/**
 * 
 */
package com.privasia.scss.gateout.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ClientType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.gateout.imports.business.service.PortService;
import com.privasia.scss.gateout.test.GateOutAbstractTest;

/**
 * @author Janaka
 *
 */
@Transactional(value = "transactionManager")
public class PortServiceTest extends GateOutAbstractTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private PortService portService;

  @Test
  public void testCheckContainerTobeReleasedByPort() {

    ImportContainer importContainer = new ImportContainer();
    importContainer.setContainer(new CommonContainerDTO());
    importContainer.getContainer().setContainerNumber("ATOS12345611");
    importContainer.getContainer().setContainerFullOrEmpty("F");
    importContainer.setContainerPosition("A");
    importContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
    importContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
    importContainer.setLpkBlock("R");
    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    importContainers.add(importContainer);

    Client client = new Client();
    client.setClientID(546l);
    client.setType(ClientType.GATE_IN);
    client.setDescription("AGS LANE 2B1");

    portService.checkContainerTobeReleasedByPort(client, importContainers);



  }



}
