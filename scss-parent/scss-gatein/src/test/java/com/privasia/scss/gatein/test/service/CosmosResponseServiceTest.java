/**
 * 
 */
package com.privasia.scss.gatein.test.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.AGSMessageStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.cosmos.model.AGSLog;
import com.privasia.scss.cosmos.oracle.repository.AGSLogRepository;
import com.privasia.scss.cosmos.service.CosmosResponseService;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosResponse;
import com.privasia.scss.gatein.test.GateInAbstractTest;

/**
 * @author Janaka
 *
 */
@Transactional(value = "cosmosOracleTransactionManager")
public class CosmosResponseServiceTest extends GateInAbstractTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private CosmosResponseService cosmosResponseService;

  @Autowired
  private AGSLogRepository agsLogRepository;

  @Test
  public void testExtractCosmosGateInResponse() {

    GateInWriteRequest gateInWriteRequest = new GateInWriteRequest();


    gateInWriteRequest.setUserName("TESTT");
    gateInWriteRequest.setHaulageCode("KN");
    gateInWriteRequest.setCosmosPort(12222);
    gateInWriteRequest.setTruckHeadNo("KN6785");
    gateInWriteRequest.setTruckPlateNo("WHY7778");
    gateInWriteRequest.setLaneNo("K7");// SELECT cli_unitno



    List<AGSLog> logs = agsLogRepository.findBySendRCV(AGSMessageStatus.RECEIVE);

    if (!(logs == null || logs.isEmpty())) {
      logs.forEach(log -> {
        String cosmosResponse = log.getXmlData();

        if (!((StringUtils.contains(cosmosResponse, "GOTTRCINFR"))
            || (StringUtils.contains(cosmosResponse, "<GOTTRCINFR>")))) {
          List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
          List<ImportContainer> importContainers = new ArrayList<ImportContainer>();

          GateInResponse gateInResponse = null;
          try {
            SGS2CosmosResponse response = getUnmarshalledObject(cosmosResponse);
            if (!(response == null || response.getResponseMessage() == null
                || response.getResponseMessage().isEmpty())) {
              response.getResponseMessage().forEach(msg -> {

                if (!(msg.getGINCNTDRPR() == null)) {
                  String exportContainerNumber = msg.getGINCNTDRPR().getUNITSE();
                  if (StringUtils.isNotEmpty(exportContainerNumber)) {
                    ExportContainer exportContainer = new ExportContainer();
                    exportContainer.setContainer(new CommonContainerDTO());
                    exportContainer.getContainer().setContainerNumber(exportContainerNumber);
                    exportContainers.add(exportContainer);
                  }
                }

                if (!(msg.getGINCNTPUPR() == null)) {
                  String importContainerNumber = msg.getGINCNTPUPR().getUNITSE();
                  if (StringUtils.isNotEmpty(importContainerNumber)) {
                    ImportContainer importContainer = new ImportContainer();
                    importContainer.setContainer(new CommonContainerDTO());
                    importContainer.getContainer().setContainerNumber(importContainerNumber);
                    importContainer.getContainer().setContainerFullOrEmpty("F");
                    importContainer.setContainerPosition("A");
                    importContainers.add(importContainer);
                  }
                }

              });
            }

            gateInWriteRequest.setImportContainers(importContainers);
            gateInWriteRequest.setExportContainers(exportContainers);

            if (!(importContainers.isEmpty() && exportContainers.isEmpty())) {
              gateInWriteRequest.setImpExpFlag(ImpExpFlagStatus.IMPORT_EXPORT.getValue());
            } else if (!(importContainers.isEmpty())) {
              gateInWriteRequest.setImpExpFlag(ImpExpFlagStatus.IMPORT.getValue());
            } else if (!(exportContainers.isEmpty())) {
              gateInWriteRequest.setImpExpFlag(ImpExpFlagStatus.EXPORT.getValue());
            }

            gateInResponse = cosmosResponseService.extractCosmosGateInResponse(cosmosResponse, gateInWriteRequest);
          } catch (JAXBException e) {
            e.printStackTrace();
          } catch (Exception e2) {
            System.out.println("log.getXmlData() " + log.getXmlData());
            e2.printStackTrace();
          }
          // Assert.assertNotNull(gateInResponse);
        }


      });
    }
  }

  private SGS2CosmosResponse getUnmarshalledObject(String cosmosResponse) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(SGS2CosmosResponse.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    StringReader reader = new StringReader(cosmosResponse);
    SGS2CosmosResponse response = (SGS2CosmosResponse) unmarshaller.unmarshal(reader);
    return response;
  }



}
