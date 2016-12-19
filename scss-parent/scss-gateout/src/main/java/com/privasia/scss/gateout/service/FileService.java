package com.privasia.scss.gateout.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.gateout.mongo.repository.GridFSRepository;

@Service("fileService")
public class FileService {

  @Autowired
  private GridFSRepository gridFSRepository;

  @Autowired
  private ODDRepository oddRepository;

  @Autowired
  private GatePassRepository gatePassRepository;

  @Autowired
  private ExportsRepository exportsRepository;

  public String saveFileToMongoDB(FileDTO fileDTO) {

    String uniqueId = createFileId(fileDTO);
    fileDTO.setFileName(uniqueId);

    DBObject metaData = new BasicDBObject();

    Document doc = new Document().append("trxType", fileDTO.getTrxType()).append("trxId", fileDTO.getFileName())
        .append("fileSize", fileDTO.getFileSize());

    metaData.put("trxType", fileDTO.getTrxType());
    metaData.put("trxId", fileDTO.getFileName());
    metaData.put("fileSize", fileDTO.getFileSize());

    metaData.put(fileDTO.getFileName() + "_Info", doc);

    GridFSFile gridFSFile = gridFSRepository.storeFile(fileDTO.getCameraImage(), metaData);

    if (!(gridFSFile == null)) {
      saveReference(fileDTO);
    }


    return uniqueId;
  }



  public String createFileId(FileDTO fileDTO) {
    StringBuilder uniqueId = new StringBuilder("");

    String trxType = fileDTO.getTrxType();

    switch (fileDTO.getCollectionType()) {
      case ApplicationConstants.PDF_FILE_COLLECTION:
        uniqueId.append("PDF");
        break;
      case ApplicationConstants.SOLAS_CERTIFICATE_COLLECTION:
        uniqueId.append("SOLAS");
        break;
      case ApplicationConstants.ZIP_FILE_COLLECTION:
        uniqueId.append("ZIP");
        break;
      default:
        break;
    }

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

  @Transactional(readOnly = false)
  public void saveReference(FileDTO fileDTO) {
    String trxType = fileDTO.getTrxType();
    switch (trxType) {
      case ApplicationConstants.ODD_TRANSACTION:
        updateODDReference(fileDTO);
        break;
      case ApplicationConstants.IMP_TRANSACTION:
        updateGatePassReference(fileDTO);
        break;
      case ApplicationConstants.EXP_TRANSACTION:
        updateExportReference(fileDTO);
        break;
      case ApplicationConstants.IMP_EXP_TRANSACTION:
        updateGatePassReference(fileDTO);
        updateExportReference(fileDTO);
        break;
      default:
        break;
    }
  }



  private void updateExportReference(FileDTO fileDTO) {
    if (StringUtils.isNotEmpty(fileDTO.getExpExportNoSeq1())) {
      Optional<Exports> exportsOpt = exportsRepository.findOne(Long.parseLong(fileDTO.getExpExportNoSeq1()));
      if (exportsOpt.isPresent()) {
        Exports exports = assignUpdatedValuesExports(exportsOpt.get(), fileDTO);
        exportsRepository.save(exports);
      }
    }
    if (StringUtils.isNotEmpty(fileDTO.getExpExportNoSeq2())) {
      Optional<Exports> exportsOpt = exportsRepository.findOne(Long.parseLong(fileDTO.getExpExportNoSeq2()));
      if (exportsOpt.isPresent()) {
        Exports exports = assignUpdatedValuesExports(exportsOpt.get(), fileDTO);
        exportsRepository.save(exports);
      }
    }
  }



  private Exports assignUpdatedValuesExports(Exports exports, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case ApplicationConstants.PDF_FILE_COLLECTION:
        exports.getCommonGateInOut().setTrxSlipNo(fileDTO.getFileName());
        break;
      case ApplicationConstants.ZIP_FILE_COLLECTION:
        exports.getCommonGateInOut().setZipFileNo(fileDTO.getFileName());
        break;
      case ApplicationConstants.SOLAS_CERTIFICATE_COLLECTION:
        exports.setSolasCertNo(fileDTO.getFileName());
        break;
      default:
        break;
    }
    return exports;
  }



  private void updateGatePassReference(FileDTO fileDTO) {
    if (StringUtils.isNotEmpty(fileDTO.getGTPPassNo1())) {
      Optional<GatePass> gatePassOpt = gatePassRepository.findOne(Long.parseLong(fileDTO.getGTPPassNo1()));
      if (gatePassOpt.isPresent()) {
        GatePass gatePass = assignUpdatedValuesGatePass(gatePassOpt.get(), fileDTO);
        gatePassRepository.save(gatePass);
      }
    }
    if (StringUtils.isNotEmpty(fileDTO.getGTPPassNo2())) {
      Optional<GatePass> gatePassOpt = gatePassRepository.findOne(Long.parseLong(fileDTO.getGTPPassNo2()));
      if (gatePassOpt.isPresent()) {
        GatePass gatePass = assignUpdatedValuesGatePass(gatePassOpt.get(), fileDTO);
        gatePassRepository.save(gatePass);
      }
    }
  }



  private GatePass assignUpdatedValuesGatePass(GatePass gatePass, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case ApplicationConstants.PDF_FILE_COLLECTION:
        gatePass.getCommonGateInOut().setTrxSlipNo(fileDTO.getFileName());
        break;
      case ApplicationConstants.ZIP_FILE_COLLECTION:
        gatePass.getCommonGateInOut().setZipFileNo(fileDTO.getFileName());
        break;
      default:
        break;
    }
    return gatePass;
  }



  public void updateODDReference(FileDTO fileDTO) {
    if (StringUtils.isNotEmpty(fileDTO.getOddIDSeq1())) {
      Optional<WHODD> whodd = oddRepository.findOne(Long.parseLong(fileDTO.getOddIDSeq1()));
      if (whodd.isPresent()) {
        WHODD whoDDobj = whodd.get();
        whoDDobj = assignUpdatedValuedWHODDobj(whoDDobj, fileDTO);
        oddRepository.save(whoDDobj);
      }
    }
    if (StringUtils.isNotEmpty(fileDTO.getOddIDSeq2())) {
      Optional<WHODD> whodd = oddRepository.findOne(Long.parseLong(fileDTO.getOddIDSeq2()));
      if (whodd.isPresent()) {
        WHODD whoDDobj = whodd.get();
        whoDDobj = assignUpdatedValuedWHODDobj(whoDDobj, fileDTO);
        oddRepository.save(whoDDobj);
      }
    }
  }



  public WHODD assignUpdatedValuedWHODDobj(WHODD whoDDobj, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case ApplicationConstants.PDF_FILE_COLLECTION:
        whoDDobj.setTrxSlipNo(fileDTO.getFileName());
        break;
      case ApplicationConstants.ZIP_FILE_COLLECTION:
        whoDDobj.setZipFileNo(fileDTO.getFileName());
        break;
      default:
        break;
    }
    return whoDDobj;
  }

}
