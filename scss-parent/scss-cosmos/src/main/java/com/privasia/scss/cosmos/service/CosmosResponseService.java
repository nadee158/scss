/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.util.COSMOSMessageCode;
import com.privasia.scss.cosmos.xml.element.CSMCTL;
import com.privasia.scss.cosmos.xml.element.GINCNTDRPR;
import com.privasia.scss.cosmos.xml.element.GINCNTPUPR;
import com.privasia.scss.cosmos.xml.element.GINTRCINFR;
import com.privasia.scss.cosmos.xml.element.ResponseMessage;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosResponse;

/**
 * @author Janaka
 *
 */
@Service("cosmosResponseService")
public class CosmosResponseService {

  private COSMOSMessageCode cosmosMessageCode;

  @Autowired
  public void setCosmosMessageCode(COSMOSMessageCode cosmosMessageCode) {
    this.cosmosMessageCode = cosmosMessageCode;
  }

  // get the cosmosResponse from db
  // GateInWriteRequest set as new object, set flag as impexp, new importcontainer list, add 2 also
  // to export container list
  public GateInResponse extractCosmosGateInResponse(String cosmosResponse, GateInWriteRequest gateInWriteRequest)
      throws JAXBException {

    JAXBContext jaxbContext = JAXBContext.newInstance(SGS2CosmosResponse.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    StringReader reader = new StringReader(cosmosResponse);
    SGS2CosmosResponse response = (SGS2CosmosResponse) unmarshaller.unmarshal(reader);

    ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());
    GateInResponse gateInResponse = new GateInResponse();
    gateInResponse.setImportContainers(gateInWriteRequest.getImportContainers());
    gateInResponse.setExportContainers(gateInWriteRequest.getExportContainers());

    String errorCode = getCosmosError(response);

    System.out.println("errorCode : " + errorCode);

    if (StringUtils.isNotEmpty(errorCode)) {

      if (StringUtils.equalsIgnoreCase(errorCode, "INF0016")) {

        // set manual plan indicator
        setManualPlanIndicatorForExports(gateInResponse);
        setCallCardCode(response, gateInResponse);
        gateInResponse.setManualPlanIndicator(true);
        return gateInResponse;

      } else {
        // read the error
        String messageDescription = cosmosMessageCode.getMessageFromCode(errorCode);
        throw new BusinessException(messageDescription);
      }
    } else {
      setCallCardCode(response, gateInResponse);

      switch (impExpFlag) {
        case IMPORT:
          if (!(gateInResponse.getImportContainers() == null || gateInResponse.getImportContainers().isEmpty())) {
            setImportYardPositionAndBayCode(response, gateInResponse.getImportContainers());

          }
          break;
        case EXPORT:
          if (!(gateInResponse.getExportContainers() == null || gateInResponse.getExportContainers().isEmpty())) {
            setExportYardPositionAndBayCode(response, gateInResponse.getExportContainers());

          }
          break;
        case IMPORT_EXPORT:
          if (!(gateInResponse.getImportContainers() == null || gateInResponse.getImportContainers().isEmpty())) {
            setImportYardPositionAndBayCode(response, gateInResponse.getImportContainers());

          }
          if (!(gateInResponse.getExportContainers() == null || gateInResponse.getExportContainers().isEmpty())) {
            setExportYardPositionAndBayCode(response, gateInResponse.getExportContainers());

          }
          break;
        default:
          throw new BusinessException("Invalid transaction Type ! " + impExpFlag.name());
      }
    }

    return gateInResponse;

  }

  public void extractCosmosGateOutResponse(String cosmosResponse) throws JAXBException {

    JAXBContext jaxbContext = JAXBContext.newInstance(SGS2CosmosResponse.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    StringReader reader = new StringReader(cosmosResponse);
    SGS2CosmosResponse response = (SGS2CosmosResponse) unmarshaller.unmarshal(reader);

    String errorCode = getCosmosError(response);

    if (StringUtils.isNotEmpty(errorCode)) {

      // read the error
      String messageDescription = cosmosMessageCode.getMessageFromCode(errorCode);
      throw new BusinessException(messageDescription);

    }

  }

  private List<ImportContainer> setImportYardPositionAndBayCode(SGS2CosmosResponse cosmosResponse,
      List<ImportContainer> importContainers) {


    List<ResponseMessage> elementList = cosmosResponse.getResponseMessage();

    if (elementList == null || elementList.isEmpty())
      throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

    List<GINCNTPUPR> yardPositionlist =
        elementList.stream().map(ResponseMessage::getGINCNTPUPR).collect(Collectors.toList());

    if (yardPositionlist == null || yardPositionlist.isEmpty())
      throw new BusinessException("Cannot find Yard Position information from cosmos ");

    if (!(importContainers == null || importContainers.isEmpty())) {

      importContainers.stream().forEach(importContainer -> {
        Optional<GINCNTPUPR> optElement = yardPositionlist.stream().filter(element -> ((element != null) && StringUtils
            .equalsIgnoreCase(element.getUNITSE(), importContainer.getContainer().getContainerNumber()))).findAny();

        GINCNTPUPR gincntpupr = optElement.orElseThrow(() -> new BusinessException(
            "Container No " + importContainer.getContainer().getContainerNumber() + " not found to populate data"));

        importContainer.setYardBayCode(gincntpupr.getPKIDSE().trim());
        importContainer.setYardPosition(gincntpupr.getPSIDSE().trim());

      });
    }


    return importContainers;
  }

  private List<ExportContainer> setExportYardPositionAndBayCode(SGS2CosmosResponse cosmosResponse,
      List<ExportContainer> exportContainers) {


    List<ResponseMessage> elementList = cosmosResponse.getResponseMessage();

    if (elementList == null || elementList.isEmpty())
      throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

    List<GINCNTDRPR> yardPositionlist =
        elementList.stream().map(ResponseMessage::getGINCNTDRPR).collect(Collectors.toList());

    if (yardPositionlist == null || yardPositionlist.isEmpty())
      throw new BusinessException("Cannot find Yard Position information from cosmos ");

    if (!(exportContainers == null || exportContainers.isEmpty())) {
      exportContainers.stream().forEach(exportContainer -> {

        Optional<GINCNTDRPR> optElement = yardPositionlist.stream().filter(element -> ((element != null) && (StringUtils
            .equalsIgnoreCase(element.getUNITSE(), exportContainer.getContainer().getContainerNumber())))).findAny();

        GINCNTDRPR gincntdrpr = optElement.orElseThrow(() -> new BusinessException(
            "Container No " + exportContainer.getContainer().getContainerNumber() + " not found to populate data"));

        exportContainer.setYardBayCode(gincntdrpr.getPKIDSE().trim());
        exportContainer.setYardPosition(gincntdrpr.getPSIDSE().trim());

      });
    }


    return exportContainers;
  }

  private GateInResponse setCallCardCode(SGS2CosmosResponse cosmosResponse, GateInResponse gateInResponse) {

    List<ResponseMessage> elementList = cosmosResponse.getResponseMessage();

    if (elementList == null || elementList.isEmpty())
      throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

    List<GINTRCINFR> callCardlist = elementList.stream().map(ResponseMessage::getGINTRCINFR)
        .filter(element -> element != null).collect(Collectors.toList());

    if (callCardlist == null || callCardlist.isEmpty())
    	//return gateInResponse;
      throw new BusinessException("Cannot find Call card code information from cosmos ");



    Optional<GINTRCINFR> optElement = callCardlist.stream().findAny();

    GINTRCINFR gintrcinfr = optElement.get();

    if (!(gateInResponse.getImportContainers() == null || gateInResponse.getImportContainers().isEmpty())) {

      gateInResponse.getImportContainers().stream().forEach(importContainer -> {
        importContainer.setCallCard(Long.parseLong(gintrcinfr.getBZKNSC().trim()));
        System.out.println("importContainer.getCallCard() " + importContainer.getCallCard());
      });
    }

    if (!(gateInResponse.getExportContainers() == null || gateInResponse.getExportContainers().isEmpty())) {

      gateInResponse.getExportContainers().stream().forEach(exportContainer -> {
        exportContainer.setCallCard(Long.parseLong(gintrcinfr.getBZKNSC().trim()));
        System.out.println("exportContainer.getCallCard() " + exportContainer.getCallCard());
      });
    }


    return gateInResponse;
  }

  private String getCosmosError(SGS2CosmosResponse cosmosResponse) {

    List<ResponseMessage> elementList = cosmosResponse.getResponseMessage();

    if (elementList == null || elementList.isEmpty())
      throw new BusinessException("Invalid response received from cosmos. No Message elements found! ");

    List<CSMCTL> csmctlList = elementList.stream().map(ResponseMessage::getCSMCTL).collect(Collectors.toList());

    if (csmctlList == null || csmctlList.isEmpty())
      throw new BusinessException("Invalid cosmos response. No CSMCTL elements");

    Optional<CSMCTL> optElement = csmctlList.stream().findFirst();

    CSMCTL csmctl = optElement.orElseThrow(() -> new BusinessException("Invalid cosmos response. No CSMCTL elements"));

    if (csmctl.getERRI() == null || StringUtils.isEmpty(csmctl.getERRI())) {
      return null;
    } else {
      return csmctl.getERRI().trim();

    }

  }

  private GateInResponse setManualPlanIndicatorForExports(GateInResponse gateInResponse) {

    if (!(gateInResponse.getExportContainers() == null || gateInResponse.getExportContainers().isEmpty())) {

      gateInResponse.getExportContainers().stream().forEach(exportContainer -> {
        exportContainer.setManualPlanIndicator(true);

      });
    }

    return gateInResponse;
  }

}
