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
import com.privasia.scss.common.dto.GateInRequest;
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
import com.privasia.scss.cosmos.dto.request.CosmosGateInExport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateInWriteRequest;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutImport;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutWriteRequest;

@Service("cosmos")
public class CosmosService implements OpusCosmosBusinessService {

  private CosmosGateOutImportService cosmosGateOutImportService;

  private CosmosGateOutExportService cosmosGateOutExportService;

  private CosmosGateInImportService cosmosGateInImportService;

  private CosmosGateInExportService cosmosGateInExportService;


  @Autowired
  public void setCosmosGateInImportService(CosmosGateInImportService cosmosGateInImportService) {
    this.cosmosGateInImportService = cosmosGateInImportService;
  }

  @Autowired
  public void setCosmosGateInExportService(CosmosGateInExportService cosmosGateInExportService) {
    this.cosmosGateInExportService = cosmosGateInExportService;
  }

  @Autowired
  public void setCosmosGateOutImportService(CosmosGateOutImportService cosmosGateOutImportService) {
    this.cosmosGateOutImportService = cosmosGateOutImportService;
  }

  @Autowired
  public void setCosmosGateOutExportService(CosmosGateOutExportService cosmosGateOutExportService) {
    this.cosmosGateOutExportService = cosmosGateOutExportService;
  }

  private int index = 0;

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



  @Override
  public GateInReponse sendGateInReadRequest(GateInRequest gateInRequest) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public GateInReponse sendGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
    ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());

    CosmosCommonValuesDTO commonValuesDTO = getCommonValues(gateInWriteRequest);
    commonValuesDTO.setMessageIndex(1);
    commonValuesDTO.setErrorMessage(null);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    commonValuesDTO.setLoginUser(userContext.getUsername());

    CosmosGateInWriteRequest cosmosGateInWriteRequest = new CosmosGateInWriteRequest();
    switch (impExpFlag) {
      case IMPORT:
        index = 0;
        // getImpRequestXML
        if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
          cosmosGateInWriteRequest.setImportList(new ArrayList<CosmosGateInImport>());
          gateInWriteRequest.getImportContainers().forEach(importContainer -> {
            index++;
            cosmosGateInWriteRequest.getImportList()
                .add(cosmosGateInImportService.constructCosmosGateInImport(commonValuesDTO, importContainer, index));
          });
        }
        break;
      case EXPORT:
        index = 0;
        // getExpRequestXML - CosmosGateOutExport
        if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
          cosmosGateInWriteRequest.setExportList(new ArrayList<CosmosGateInExport>());
          gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
            index++;
            cosmosGateInWriteRequest.getExportList()
                .add(cosmosGateInExportService.constructCosmosGateInExport(exportContainer, commonValuesDTO, index));
          });
        }
        break;
      case IMPORT_EXPORT:
        index = 0;
        if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
          cosmosGateInWriteRequest.setExportList(new ArrayList<CosmosGateInExport>());
          gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
            index++;
            cosmosGateInWriteRequest.getExportList()
                .add(cosmosGateInExportService.constructCosmosGateInExport(exportContainer, commonValuesDTO, index));
          });
        }
        // gateoutxmlrequest
        // getImpExpRequestXML
        if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
          cosmosGateInWriteRequest.setImportList(new ArrayList<CosmosGateInImport>());
          gateInWriteRequest.getImportContainers().forEach(importContainer -> {
            index++;
            cosmosGateInWriteRequest.getImportList()
                .add(cosmosGateInImportService.constructCosmosGateInImport(commonValuesDTO, importContainer, index));
          });
        }
        break;
      default:
        break;
    }
    return null;
  }

  private CosmosCommonValuesDTO getCommonValues(GateInWriteRequest gateInWriteRequest) {
    CosmosCommonValuesDTO cosmosCommonValuesDTO = new CosmosCommonValuesDTO();
    cosmosCommonValuesDTO.setMsgUniqueId(System.currentTimeMillis() + "");
    cosmosCommonValuesDTO.setDate(DateUtil.getFormatteDate(LocalDate.now(), "yyyyMMdd"));
    cosmosCommonValuesDTO.setTime(DateUtil.getFormatteTime(LocalTime.now(), "HHmmss"));
    cosmosCommonValuesDTO.setExpMsgInx("3");
    cosmosCommonValuesDTO.setLaneNo(gateInWriteRequest.getLaneNo());
    cosmosCommonValuesDTO.setTruckNo(gateInWriteRequest.getTruckHeadNo());
    cosmosCommonValuesDTO.setCompCode(gateInWriteRequest.getHaulageCode());
    return cosmosCommonValuesDTO;
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
