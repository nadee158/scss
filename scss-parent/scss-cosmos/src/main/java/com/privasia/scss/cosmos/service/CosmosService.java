package com.privasia.scss.cosmos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.interfaces.OpusCosmosBusinessService;

@Service("cosmos")
public class CosmosService implements OpusCosmosBusinessService {

  private CosmosGateOutImportService cosmosGateOutImportService;

  private CosmosGateOutExportService cosmosGateOutExportService;

  @Autowired
  public void setCosmosGateOutImportService(CosmosGateOutImportService cosmosGateOutImportService) {
    this.cosmosGateOutImportService = cosmosGateOutImportService;
  }

  @Autowired
  public void setCosmosGateOutExportService(CosmosGateOutExportService cosmosGateOutExportService) {
    this.cosmosGateOutExportService = cosmosGateOutExportService;
  }

  @Override
  public GateInReponse sendGateInRequest(GateInWriteRequest gateInWriteRequest) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {
    // TODO Auto-generated method stub
    // gateOutReponse-transaction type - switch

    TransactionType trxType = TransactionType.fromCode(gateOutReponse.getTransactionType());

    switch (trxType) {
      case IMPORT:
        if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
          List<ImportContainer> updatedImportList =
              cosmosGateOutImportService.fetchCosmosSeal(gateOutReponse.getImportContainers());
          gateOutReponse.setImportContainers(updatedImportList);
        }
        break;
      case EXPORT:
        if (!(gateOutReponse.getExportContainers() == null || gateOutReponse.getExportContainers().isEmpty())) {
          List<ExportContainer> updatedExportList =
              cosmosGateOutExportService.setContainerStatus(gateOutReponse.getExportContainers());
          gateOutReponse.setExportContainers(updatedExportList);
        }
        break;
      case IMPORT_EXPORT:
        if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
          List<ImportContainer> updatedImportList =
              cosmosGateOutImportService.fetchCosmosSeal(gateOutReponse.getImportContainers());
          gateOutReponse.setImportContainers(updatedImportList);
        }
        if (!(gateOutReponse.getExportContainers() == null || gateOutReponse.getExportContainers().isEmpty())) {
          List<ExportContainer> updatedExportList =
              cosmosGateOutExportService.setContainerStatus(gateOutReponse.getExportContainers());
          gateOutReponse.setExportContainers(updatedExportList);
        }
        break;

      default:
        break;
    }


    return gateOutReponse;
  }

  @Override
  public GateOutReponse sendGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest,
      GateOutReponse gateOutReponse) {
    ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());


    switch (impExpFlag) {
      case IMPORT:
//getImpRequestXML
//        requestXML2 = "<Message Index=\"3\">\n"
//            + "<CSMCTL>\n"
//            + "<RQST>GSRQS</RQST>\n" //Request Code : To hard code
//            + "<ACTN>CRT</ACTN>\n" //Action Code : To hard code
//            + "<RTNC>0</RTNC>\n" //Return Code : To hard code
//            + errXMLMsg3
//            + "<RQDS>CTEDSE</RQDS>\n" //Requestor Data Structure : To hard code
//            + "<RTNM>AS</RTNM>\n" //Return Mode : To hard code
//            + "<USID>" + username + "</USID>\n" //User ID : To capture SCSS user id
//            + "<RQUI>" + msgUniqueId + "</RQUI>\n" //To input msg unique id
//            + "<TRMC>WPT1</TRMC>\n" //Terminal : To hard code
//            + "</CSMCTL>\n"
//            + "<GOTCNTINF>\n"
//            + "<UNITSE>" + containerNoC2 + "</UNITSE>\n"
//            + sealC2
//            + "</GOTCNTINF>\n"
//            + "</Message>\n";
        break;
      case EXPORT:
        // getExpRequestXML - CosmosGateOutExport
//        String requestXML = "<?xml version=\"1.0\" encoding=\'ASCII\'?>\n"
//            + "<SGS2Cosmos>\n"
//            + "<Message Index=\"1\">\n"
//            + "<CSMCTL>\n"
//            + "<RQST>GSRQS</RQST>\n" //Request Code : To hard code
//            + "<ACTN>CRT</ACTN>\n" //Action Code : To hard code
//            + "<RTNC>0</RTNC>\n" //Return Code : To hard code
//            + errXMLMsg1
//            + "<RQDS>CTEDSC</RQDS>\n" //Requestor Data Structure : To hard code
//            + "<RTNM>AS</RTNM>\n" //Return Mode : To hard code
//            + "<USID>" + toUpperCase(username) + "</USID>\n" //User ID : To capture SCSS user id
//            + "<RQUI>" + msgUniqueId + "</RQUI>\n" //To input msg unique id
//            + "<TRMC>WPT1</TRMC>\n" //Terminal : To hard code
//            + "</CSMCTL>\n"
//            + "<GOTTRCINF>\n" // For Gate In Truck Information
//            + "<MSGTSC>GOTTRCINF</MSGTSC>\n" //Message Type : To hard code
//            + "<LANESC>" + toUpperCase(laneNo) + "</LANESC>\n" // Lane : To capture gate lane no
//            + "<VMIDSC>" + toUpperCase(truckNo) + "</VMIDSC>\n" // Truck License Plate : To capture truck no
//            + "<ATDDSC>" + date + "</ATDDSC>\n" // Date of Arrival : To capture current date??
//            + "<ATDTSC>" + time + "</ATDTSC>\n" // Time of Arrival : To capture current time??
//            + "</GOTTRCINF>\n"
//            + "</Message>\n"
//            + "</SGS2Cosmos>\n";

        break;
      case IMPORT_EXPORT:
        //gateoutxmlrequest
        // getImpExpRequestXML
        break;
      default:
        break;
    }

    return null;
  }

}
