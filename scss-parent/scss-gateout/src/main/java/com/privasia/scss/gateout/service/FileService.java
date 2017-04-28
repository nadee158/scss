package com.privasia.scss.gateout.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.core.exception.BusinessException;
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

  // tomorrows tasks
  // write a methods generateSoalsService(List<Export> list)

  // old app:-
  // web-inf->solaspass.jrxml logo.png copy to this app
  // implement following in this app

  private GridFSRepository gridFSRepository;

  private ODDRepository oddRepository;

  private GatePassRepository gatePassRepository;

  private ExportsRepository exportsRepository;

  @Autowired
  public void setGridFSRepository(GridFSRepository gridFSRepository) {
    this.gridFSRepository = gridFSRepository;
  }

  @Autowired
  public void setOddRepository(ODDRepository oddRepository) {
    this.oddRepository = oddRepository;
  }

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  public String saveFileToMongoDB(FileDTO fileDTO) {

    DBObject metaData = new BasicDBObject();

    Document doc = new Document().append("trxType", fileDTO.getTrxType().getValue())
        .append("trxId", fileDTO.getFileName()).append("fileSize", fileDTO.getFileSize());

    metaData.put("trxType", fileDTO.getTrxType());
    metaData.put("trxId", fileDTO.getFileName());
    metaData.put("fileSize", fileDTO.getFileSize());

    metaData.put(fileDTO.getFileName() + "_Info", doc);

    GridFSFile gridFSFile = gridFSRepository.storeFile(fileDTO.getCameraImage(), metaData);

    if (!(gridFSFile == null)) {
      saveReference(fileDTO);
    }

    return fileDTO.getFileName();
  }

  public FileDTO createFileId(FileDTO fileDTO) {
    StringBuilder uniqueId = new StringBuilder("");

    TransactionType trxType = fileDTO.getTrxType();

    switch (fileDTO.getCollectionType()) {
      case PDF_FILE_COLLECTION:
        uniqueId.append("PDF");
        break;
      case SOLAS_CERTIFICATE_COLLECTION:
        uniqueId.append("SOLAS");
        break;
      case ZIP_FILE_COLLECTION:
        uniqueId.append("ZIP");
        break;
      default:
        break;

    }

    switch (trxType) {
      case ODD_EXPORT:
      case ODD_IMPORT:
      case ODD_IMPORT_EXPORT:
        if (fileDTO.getOddImpSeq1().isPresent()) {
          uniqueId.append("_").append(fileDTO.getOddImpSeq1().get());
        }
        if (fileDTO.getOddImpSeq2().isPresent()) {
          uniqueId.append("_").append(fileDTO.getOddImpSeq2().get());
        }
        if (fileDTO.getOddExpSeq1().isPresent()) {
          uniqueId.append("_").append(fileDTO.getOddExpSeq1().get());
        }
        if (fileDTO.getOddExpSeq2().isPresent()) {
          uniqueId.append("_").append(fileDTO.getOddExpSeq2().get());
        }
        break;
      case IMPORT:
        if (fileDTO.getGatePassNo1().isPresent()) {
          uniqueId.append("_").append(fileDTO.getGatePassNo1().get());
        }
        if (fileDTO.getGatePassNo2().isPresent()) {
          uniqueId.append("_").append(fileDTO.getGatePassNo2().get());
        }
        break;
      case EXPORT:
        if (fileDTO.getExportNoSeq1().isPresent()) {
          uniqueId.append("_").append(fileDTO.getExportNoSeq1().get());
        }
        if (fileDTO.getExportNoSeq2().isPresent()) {
          uniqueId.append("_").append(fileDTO.getExportNoSeq2().get());
        }
        break;
      case IMPORT_EXPORT:
        if (fileDTO.getGatePassNo1().isPresent()) {
          uniqueId.append("_").append(fileDTO.getGatePassNo1().get());
        }
        if (fileDTO.getGatePassNo2().isPresent()) {
          uniqueId.append("_").append(fileDTO.getGatePassNo2().get());
        }
        if (fileDTO.getExportNoSeq1().isPresent()) {
          uniqueId.append("_").append(fileDTO.getExportNoSeq1().get());
        }
        if (fileDTO.getExportNoSeq2().isPresent()) {
          uniqueId.append("_").append(fileDTO.getExportNoSeq2().get());
        }
        break;
      default:
        break;
    }

    fileDTO.setFileName(uniqueId.toString());
    return fileDTO;
  }

  public void saveReference(FileDTO fileDTO) {
    TransactionType trxType = fileDTO.getTrxType();
    switch (trxType) {
      case ODD_EXPORT:
      case ODD_IMPORT:
      case ODD_IMPORT_EXPORT:
        updateODDReference(fileDTO);
        break;
      case IMPORT:
        updateGatePassReference(fileDTO);
        break;
      case EXPORT:
        updateExportReference(fileDTO);
        break;
      case IMPORT_EXPORT:
        updateGatePassReference(fileDTO);
        updateExportReference(fileDTO);
        break;
      default:
        break;
    }
  }

  public void updateExportReference(FileDTO fileDTO) {

    Optional<List<Exports>> exportsOptList = exportsRepository
        .findByExportIDIn(Arrays.asList(fileDTO.getExportNoSeq1().orElse(0l), fileDTO.getExportNoSeq2().orElse(0l)));

    if (!(exportsOptList.orElse(null) == null || exportsOptList.get().isEmpty())) {
      exportsOptList.get().forEach(exports -> {
        assignUpdatedValuesExports(exports, fileDTO);
        exportsRepository.save(exports);
      });
    } else {
      throw new BusinessException("Invalid Exports ID to update file reference : "
          + fileDTO.getExportNoSeq1().orElse(null) + " / " + fileDTO.getExportNoSeq2().orElse(null));
    }
  }

  private Exports assignUpdatedValuesExports(Exports exports, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case PDF_FILE_COLLECTION:
        exports.getCommonGateInOut().setTrxSlipNo(fileDTO.getFileName());
        break;
      case ZIP_FILE_COLLECTION:
        exports.getCommonGateInOut().setZipFileNo(fileDTO.getFileName());
        break;
      case SOLAS_CERTIFICATE_COLLECTION:
        exports.setSolasCertNo(fileDTO.getFileName());
        break;
      default:
        break;
    }
    return exports;
  }

  public void updateGatePassReference(FileDTO fileDTO) {

    Optional<List<GatePass>> gatePassOptList = gatePassRepository
        .findByGatePassNoIn(Arrays.asList(fileDTO.getGatePassNo1().orElse(0l), fileDTO.getGatePassNo2().orElse(0l)));

    if (!(gatePassOptList.orElse(null) == null || gatePassOptList.get().isEmpty())) {
      gatePassOptList.get().forEach(gatePass -> {
        assignUpdatedValuesGatePass(gatePass, fileDTO);
        gatePassRepository.save(gatePass);
      });
    } else {
      throw new BusinessException("Invalid GatePass ID to update file reference : "
          + fileDTO.getGatePassNo1().orElse(null) + " / " + fileDTO.getGatePassNo2().orElse(null));
    }
  }

  private GatePass assignUpdatedValuesGatePass(GatePass gatePass, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case PDF_FILE_COLLECTION:
        gatePass.getCommonGateInOut().setTrxSlipNo(fileDTO.getFileName());
        break;
      case ZIP_FILE_COLLECTION:
        gatePass.getCommonGateInOut().setZipFileNo(fileDTO.getFileName());
        break;
      default:
        break;
    }
    return gatePass;
  }

  public void updateODDReference(FileDTO fileDTO) {

    Optional<List<WHODD>> oddOptList = oddRepository.findByOddIdSeqIn(Arrays.asList(fileDTO.getOddImpSeq1().orElse(0l),
        fileDTO.getOddImpSeq2().orElse(0l), fileDTO.getOddExpSeq1().orElse(0l), fileDTO.getOddExpSeq2().orElse(0l)));

    if (!(oddOptList.orElse(null) == null || oddOptList.get().isEmpty())) {
      oddOptList.get().forEach(whODD -> {
        assignUpdatedValuedWHODDobj(whODD, fileDTO);
        oddRepository.save(whODD);
      });
    } else {
      throw new BusinessException("Invalid WhODD ID to update file reference : " + fileDTO.getOddImpSeq1().orElse(null)
          + " / " + fileDTO.getOddImpSeq2().orElse(null) + " / " + fileDTO.getOddExpSeq1().orElse(null) + " / "
          + fileDTO.getOddExpSeq2().orElse(null));
    }
  }

  private WHODD assignUpdatedValuedWHODDobj(WHODD whoDDobj, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case PDF_FILE_COLLECTION:
        whoDDobj.setTrxSlipNo(fileDTO.getFileName());
        break;
      case ZIP_FILE_COLLECTION:
        whoDDobj.setZipFileNo(fileDTO.getFileName());
        break;
      default:
        break;
    }
    return whoDDobj;
  }

}
