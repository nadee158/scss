package com.privasia.scss.gateout.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.gateout.dto.FileDTO;

@Service("fileService")
public class FileService {

  public String saveFileToMongoDB(FileDTO fileDTO) {
    String uniqueId = createFileId(fileDTO);

    return uniqueId;
  }

  public String createFileId(FileDTO fileDTO) {
    String fileType = fileDTO.getFileType();
    StringBuilder uniqueId = new StringBuilder("");

    String trxType = fileDTO.getTrxType();

    if (StringUtils.equals(fileType, "pdf")) {
      fileType = ApplicationConstants.PDF_FILE_COLLECTION;
      uniqueId.append("PDF");
    } else if (StringUtils.equals(fileType, "solas")) {
      fileType = ApplicationConstants.SOLAS_CERTIFICATE_COLLECTION;
      uniqueId.append("SOLAS");
    } else {
      fileType = ApplicationConstants.ZIP_FILE_COLLECTION;
      uniqueId.append("ZIP");
    }
    fileDTO.setFileType(fileType);

    switch (trxType) {
      case ApplicationConstants.ODD_TRANSACTION:
        if (StringUtils.isNotEmpty(fileDTO.getOddIDSeq1())) {
          uniqueId.append("_").append(fileDTO.getOddIDSeq1());
        }
        if (StringUtils.isNotEmpty(fileDTO.getOddIDSeq2())) {
          uniqueId.append("_").append(fileDTO.getOddIDSeq2());
        }
        break;
      case ApplicationConstants.IMP_TRANSACTION:
        if (StringUtils.isNotEmpty(fileDTO.getGTPPassNo1())) {
          uniqueId.append("_").append(fileDTO.getGTPPassNo1());
        }
        if (StringUtils.isNotEmpty(fileDTO.getGTPPassNo2())) {
          uniqueId.append("_").append(fileDTO.getGTPPassNo2());
        }
        break;
      case ApplicationConstants.EXP_TRANSACTION:
        if (StringUtils.isNotEmpty(fileDTO.getExpExportNoSeq1())) {
          uniqueId.append("_").append(fileDTO.getExpExportNoSeq1());
        }
        if (StringUtils.isNotEmpty(fileDTO.getExpExportNoSeq2())) {
          uniqueId.append("_").append(fileDTO.getExpExportNoSeq2());
        }
        break;
      case ApplicationConstants.IMP_EXP_TRANSACTION:
        if (StringUtils.isNotEmpty(fileDTO.getGTPPassNo1())) {
          uniqueId.append("_").append(fileDTO.getGTPPassNo1());
        }
        if (StringUtils.isNotEmpty(fileDTO.getGTPPassNo2())) {
          uniqueId.append("_").append(fileDTO.getGTPPassNo2());
        }
        if (StringUtils.isNotEmpty(fileDTO.getExpExportNoSeq1())) {
          uniqueId.append("_").append(fileDTO.getExpExportNoSeq1());
        }
        if (StringUtils.isNotEmpty(fileDTO.getExpExportNoSeq2())) {
          uniqueId.append("_").append(fileDTO.getExpExportNoSeq2());
        }
        break;
      default:
        break;
    }

    return uniqueId.toString();
  }



}
