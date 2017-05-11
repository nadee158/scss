package com.privasia.scss.cosmos.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.dto.common.UserContext;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutWriteRequest;

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

    CosmosCommonValuesDTO commonValuesDTO = getCommonValues(gateOutWriteRequest);
    commonValuesDTO.setMessageIndex(1);
    commonValuesDTO.setErrorMessage(null);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    commonValuesDTO.setLoginUser(userContext.getUsername());

    CosmosGateOutWriteRequest cosmosGateOutWriteRequest = new CosmosGateOutWriteRequest();

    switch (impExpFlag) {
      case IMPORT:
        // getImpRequestXML
        if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
          cosmosGateOutWriteRequest.setImportList(new ArrayList<CosmosGateOutImport>());
          gateOutReponse.getImportContainers().forEach(importContainer -> {
            cosmosGateOutWriteRequest.getImportList()
                .add(cosmosGateOutImportService.constructCosmosGateOutImport(commonValuesDTO, importContainer));
          });
        }
        break;
      case EXPORT:
        // getExpRequestXML - CosmosGateOutExport
        cosmosGateOutWriteRequest.setExport(cosmosGateOutExportService.constructCosmosGateOutExport(commonValuesDTO));
        break;
      case IMPORT_EXPORT:
        cosmosGateOutWriteRequest.setExport(cosmosGateOutExportService.constructCosmosGateOutExport(commonValuesDTO));
        // gateoutxmlrequest
        // getImpExpRequestXML
        if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {
          cosmosGateOutWriteRequest.setImportList(new ArrayList<CosmosGateOutImport>());
          gateOutReponse.getImportContainers().forEach(importContainer -> {
            cosmosGateOutWriteRequest.getImportList()
                .add(cosmosGateOutImportService.constructCosmosGateOutImport(commonValuesDTO, importContainer));
          });
        }
        break;
      default:
        break;
    }

    return null;
  }


  public CosmosCommonValuesDTO getCommonValues(GateOutWriteRequest gateOutWriteRequest) {
    CosmosCommonValuesDTO cosmosCommonValuesDTO = new CosmosCommonValuesDTO();
    cosmosCommonValuesDTO.setMsgUniqueId(System.currentTimeMillis() + "");
    cosmosCommonValuesDTO.setDate(DateUtil.getFormatteDate(LocalDate.now(), "yyyyMMdd"));
    cosmosCommonValuesDTO.setTime(DateUtil.getFormatteTime(LocalTime.now(), "HHmmss"));
    cosmosCommonValuesDTO.setExpMsgInx("3");
    cosmosCommonValuesDTO.setLaneNo(gateOutWriteRequest.getLaneNo());
    cosmosCommonValuesDTO.setTruckNo(gateOutWriteRequest.getTruckHeadNo());
    cosmosCommonValuesDTO.setCompCode(gateOutWriteRequest.getHaulageCode());
    return cosmosCommonValuesDTO;
  }

}
