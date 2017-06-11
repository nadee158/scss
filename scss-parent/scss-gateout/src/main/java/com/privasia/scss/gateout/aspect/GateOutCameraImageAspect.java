/**
 * 
 */
package com.privasia.scss.gateout.aspect;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.annotation.CameraImage;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.CollectionType;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.gateout.service.FileService;

/**
 * @author Janaka
 *
 */
@Aspect
public class GateOutCameraImageAspect {

  private static final Log log = LogFactory.getLog(GateOutCameraImageAspect.class);

  private FileService fileService;

  @Autowired
  public void setFileService(FileService fileService) {
    this.fileService = fileService;
  }



  @AfterReturning(pointcut = "@annotation(cameraImage)")
  public void saveCameraImage(JoinPoint joinPoint, CameraImage cameraImage) {
    log.info("*****************   sendDsoSealDifferentEmail called *************************");
    if (joinPoint.getArgs()[0] instanceof GateOutWriteRequest) {

      GateOutWriteRequest gateOutWriteRequest = (GateOutWriteRequest) joinPoint.getArgs()[0];
      if (gateOutWriteRequest.getTransactionZipFile() != null
          && gateOutWriteRequest.getTransactionZipFile().length > 0) {
        byte[] transactionZipFile = gateOutWriteRequest.getTransactionZipFile();
        // collectionType - ZIP_FILE_COLLECTION("zipFile")
        // transactionType- Export/Import (get from a condition, switch case)
        FileDTO fileDTO = new FileDTO();
        fileDTO.setCollectionType(CollectionType.ZIP_FILE_COLLECTION);
        fileDTO.setFile(transactionZipFile);
        fileDTO.setFileName(Optional.of("camera_image"));
        ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());
        switch (impExpFlag) {
          case IMPORT:
            setGatePassNumbers(gateOutWriteRequest.getImportContainers(), fileDTO);
            break;
          case EXPORT:
            setExportIds(gateOutWriteRequest.getExportContainers(), fileDTO);
            break;
          case IMPORT_EXPORT:
            setGatePassNumbers(gateOutWriteRequest.getImportContainers(), fileDTO);
            setExportIds(gateOutWriteRequest.getExportContainers(), fileDTO);
            break;
          default:
            break;
        }
        try {
          fileService.saveFile(fileDTO, CollectionType.ZIP_FILE_COLLECTION);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return;
    } else {
      throw new BusinessException("Invalid agruments for send Dso Seal Different Email Email");
    }

  }

  private void setExportIds(List<ExportContainer> exportContainers, FileDTO fileDTO) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      fileDTO.setExportNoSeq1(Optional.of(exportContainers.get(0).getExportID()));
      if (exportContainers.size() > 1) {
        fileDTO.setExportNoSeq2(Optional.of(exportContainers.get(1).getExportID()));
      }
    }
  }



  private void setGatePassNumbers(List<ImportContainer> importContainers, FileDTO fileDTO) {
    if (!(importContainers == null || importContainers.isEmpty())) {
      fileDTO.setGatePassNo1(Optional.of(importContainers.get(0).getGatePassNo()));
      if (importContainers.size() > 1) {
        fileDTO.setGatePassNo2(Optional.of(importContainers.get(1).getGatePassNo()));
      }
    }
  }



}


