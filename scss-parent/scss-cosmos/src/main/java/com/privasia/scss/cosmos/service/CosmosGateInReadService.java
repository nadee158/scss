/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.service.export.ExportUtilService;
import com.privasia.scss.cosmos.model.ISOCode;
import com.privasia.scss.cosmos.oracle.repository.ISOCodeCosmosRepository;
import com.privasia.scss.cosmos.util.TextString;

@Service("cosmosGateInReadService")
public class CosmosGateInReadService {

  private static final Log log = LogFactory.getLog(CosmosGateInReadService.class);

  private CosmosGateInImportService cosmosGateInImportService;

  private ISOCodeCosmosRepository isoCodeCosmosRepository;

  private CosmosGateInExportService cosmosGateInExportService;

  @Autowired
  public void setCosmosGateInImportService(CosmosGateInImportService cosmosGateInImportService) {
    this.cosmosGateInImportService = cosmosGateInImportService;
  }

  @Autowired
  public void setIsoCodeCosmosRepository(ISOCodeCosmosRepository isoCodeCosmosRepository) {
    this.isoCodeCosmosRepository = isoCodeCosmosRepository;
  }

  @Autowired
  public void setCosmosGateInExportService(CosmosGateInExportService cosmosGateInExportService) {
    this.cosmosGateInExportService = cosmosGateInExportService;
  }

  @Async
  public Future<GateInResponse> populateCosmosGateInImport(GateInResponse gateInResponse) {

    if (!(gateInResponse.getImportContainers() == null
        || gateInResponse.getImportContainers().isEmpty())) {

      gateInResponse.getImportContainers().forEach(importContainer -> {
        cosmosGateInImportService.fetchContainerInfo(importContainer);

        if (StringUtils.isNotEmpty(importContainer.getContainer().getContainerISOCode())) {
          Optional<ISOCode> optISOCode =
              isoCodeCosmosRepository.findOne(importContainer.getContainer().getContainerISOCode());
          ISOCode iso = optISOCode
              .orElseThrow(() -> new ResultsNotFoundException("Cosmos Iso Code Not found in SCSS "
                  + importContainer.getContainer().getContainerISOCode()));

          importContainer.getContainer().setContainerHeight(iso.getHeight());
          importContainer.setContainerLength(iso.getLength());
          importContainer.getContainer()
              .setContainerSize(ExportUtilService.getStringValueFromInteger(iso.getLength()));
          importContainer.setContainerType(iso.getType());
          importContainer.setTareWeight(iso.getTareWeight());
        }
        
        System.out.println("********************* POSITION @ 1.1****************** "+ importContainer.getContainerPosition());
        System.out.println("********************* POSITION @ 1.1****************** "+ importContainer.getCurrentPosition());

      });

    } else {
      gateInResponse.setImportContainers(null);
      
    }

    return new AsyncResult<GateInResponse>(gateInResponse);
  }

  @Async
  public Future<GateInResponse> populateCosmosGateInExport(GateInResponse gateInResponse) {

    if (!(gateInResponse.getExportContainers() == null
        || gateInResponse.getExportContainers().isEmpty())) {

      gateInResponse.getExportContainers().forEach(exportContainer -> {
        System.out.println("Running in populateCosmosGateInExport **************************** "
            + exportContainer.getContainer().getContainerNumber());
        Future<Boolean> primary =
            cosmosGateInExportService.fetchPrimanyInfoContainerInfo(exportContainer);
        Future<Boolean> Secondary =
            cosmosGateInExportService.fetchSecondaryContainerInfo(exportContainer);

        while (true) {
          // System.out.println(
          // "inside the loop fetchPrimanyInfoContainerInfo &
          // fetchSecondaryContainerInfo");
          if (primary.isDone() && Secondary.isDone()) {
            // check if any excpetions are thrown from async tasks
            try {
              primary.get();
            } catch (ExecutionException e) {
              e.printStackTrace();
              log.error("ExecutionException while retrieve data data from cosmos");
              log.error(e.getMessage());
              Throwable ee = e.getCause();
              if (ee instanceof BusinessException) {
                BusinessException ex = (BusinessException) ee;
                throw new BusinessException(ex.getErrorKey());
              }
              throw new BusinessException(e.getCause().getMessage());
            } catch (CancellationException | InterruptedException e) {
              e.printStackTrace();
              log.error(
                  "CancellationException | InterruptedException while retrieve data data from cosmos");
              log.error(e.getMessage());
              e.printStackTrace();
              throw new BusinessException(e.getCause().getMessage());
            }

            try {
              Secondary.get();
            } catch (ExecutionException e) {
              e.printStackTrace();
              log.error("ExecutionException while retrieve data data from cosmos");
              log.error(e.getMessage());
              Throwable ee = e.getCause();
              if (ee instanceof BusinessException) {
                BusinessException ex = (BusinessException) ee;
                throw new BusinessException(ex.getErrorKey());
              }
              throw new BusinessException(e.getCause().getMessage());
            } catch (CancellationException | InterruptedException e) {
              e.printStackTrace();
              log.error(
                  "CancellationException | InterruptedException while retrieve data data from cosmos");
              log.error(e.getMessage());
              e.printStackTrace();
              throw new BusinessException(e.getCause().getMessage());
            }

            System.out
                .println("now done fetchPrimanyInfoContainerInfo & fetchSecondaryContainerInfo");
            exportContainer.setExpWeightBridge(gateInResponse.getExpWeightBridge());
            if (StringUtils.isNotEmpty(exportContainer.getContainer().getContainerISOCode())) {
              Optional<ISOCode> optISOCode = isoCodeCosmosRepository
                  .findOne(exportContainer.getContainer().getContainerISOCode());
              ISOCode iso = optISOCode.orElseThrow(
                  () -> new ResultsNotFoundException("Cosmos Iso Code Not found in SCSS "
                      + exportContainer.getContainer().getContainerISOCode()));

              exportContainer.getContainer().setContainerHeight(iso.getHeight());
              // exportContainer.setContainerLength(iso.getLength());
              exportContainer.getContainer()
                  .setContainerSize(ExportUtilService.getStringValueFromInteger(iso.getLength()));
              exportContainer.setContainerType(iso.getType());
              exportContainer.setTareWeight(iso.getTareWeight());
            }
            break;
          } else {
            // System.out.println(
            // "not done yet fetchPrimanyInfoContainerInfo &
            // fetchSecondaryContainerInfo");
          }

          try {
            System.out
                .println("waiting  fetchPrimanyInfoContainerInfo & fetchSecondaryContainerInfo ");
            Thread.sleep(5);
          } catch (InterruptedException e) {
            log.error(e.getMessage());
            break;
          }
        }

      });
    } else {
      gateInResponse.setExportContainers(null);
      System.out
          .println("else part running in populateCosmosGateInExport **************************** ");
    }

    return new AsyncResult<GateInResponse>(gateInResponse);
  }
  
  public GateInResponse constructGateInReponse(GateInRequest gateInRequest,
			GateInResponse gateInResponse) {

		gateInResponse.setUserId(String.valueOf(gateInRequest.getUserId()));
		//LocalDateTime localDateTime = DateUtil.getLocalDategFromString(gateInReadResponse.getGateInDateTime());

		//gateInReponse.setGateINDateTime(localDateTime);
		gateInResponse.setHaulageCode(gateInRequest.getHaulageCode());
		gateInResponse.setLaneNo(gateInRequest.getLaneNo());
		gateInResponse.setTruckHeadNo(gateInRequest.getTruckHeadNo());
		return gateInResponse;
	}

}
