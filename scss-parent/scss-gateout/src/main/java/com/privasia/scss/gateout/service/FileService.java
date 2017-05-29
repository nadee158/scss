package com.privasia.scss.gateout.service;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSInputFile;
import com.privasia.scss.common.enums.CollectionType;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.gateout.mongo.repository.GridFSRepository;
import com.privasia.scss.gateout.whodd.service.ODDGateOutService;

@Service("fileService")
public class FileService {

	private GridFSRepository gridFSRepository;
	
	private ImportGateOutService importGateOutService;
	
	private ExportGateOutService exportGateOutService;
	
	private ODDGateOutService oddGateOutService;

	@Autowired
	public void setGridFSRepository(GridFSRepository gridFSRepository) {
		this.gridFSRepository = gridFSRepository;
	}
	
	@Autowired
	public void setImportGateOutService(ImportGateOutService importGateOutService) {
		this.importGateOutService = importGateOutService;
	}

	@Autowired
	public void setExportGateOutService(ExportGateOutService exportGateOutService) {
		this.exportGateOutService = exportGateOutService;
	}

	@Autowired
	public void setOddGateOutService(ODDGateOutService oddGateOutService) {
		this.oddGateOutService = oddGateOutService;
	}



	public Optional<String> saveFileToMongoDB(FileDTO fileDTO) throws IOException {

		fileDTO = createFileId(fileDTO);

		if (fileDTO.getFileName().isPresent()) {

			DBObject metaData = new BasicDBObject();

			Document doc = new Document().append("trxType", getTransactionTypeValue(fileDTO))
					.append("trxId", fileDTO.getFileName().get()).append("fileSize", fileDTO.getFileSize());

			metaData.put(fileDTO.getFileName().get() + "_Info", doc);
			
			GridFSInputFile gridFSFile = gridFSRepository.storeFile(fileDTO, metaData);
			if (gridFSFile != null) {
				
				return fileDTO.getFileName();
			}
		}

		return Optional.of(null);
	}

	public FileDTO createFileId(FileDTO fileDTO) {

		if (!(StringUtils.equals(fileDTO.getCollectionType().getValue(),
				CollectionType.SOLAS_CERTIFICATE_COLLECTION.getValue()))) {
			StringBuilder uniqueId = new StringBuilder("");

			TransactionType trxType = TransactionType.fromCode(fileDTO.getTrxType());

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

			switch (trxType) {// logic issue
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
			case ODD_EXPORT:
				if (fileDTO.getOddExpSeq1().isPresent()) {
					uniqueId.append("_").append(fileDTO.getOddExpSeq1().get());
				}
				if (fileDTO.getOddExpSeq2().isPresent()) {
					uniqueId.append("_").append(fileDTO.getOddExpSeq2().get());
				}
				break;
			case ODD_IMPORT:

				if (fileDTO.getOddImpSeq1().isPresent()) {
					uniqueId.append("_").append(fileDTO.getOddImpSeq1().get());
				}
				if (fileDTO.getOddImpSeq2().isPresent()) {
					uniqueId.append("_").append(fileDTO.getOddImpSeq2().get());
				}

				break;
			default:
				break;
			}

			fileDTO.setFileName(Optional.ofNullable(uniqueId.toString()));
		}

		return fileDTO;

	}

	private String getTransactionTypeValue(FileDTO fileDTO) {
		
		TransactionType trxType = TransactionType.fromCode(fileDTO.getTrxType());

		switch (trxType) {
		case IMPORT:
			return "importTransaction";
		case EXPORT:
			return "exportTransaction";
		case IMPORT_EXPORT:
			return "importExportTransaction";
		case ODD_IMPORT:
		case ODD_EXPORT:
		case ODD_IMPORT_EXPORT:
			return "oddWHTransaction";
		default:
			return "noTransaction";
		}
	}
	
	public String saveTransactionSlip(FileDTO fileDTO, CollectionType collectionType) throws IOException {
		
		fileDTO.setCollectionType(collectionType);
		
		Optional<String> optFileID  = saveFileToMongoDB(fileDTO);
		
		// save to file references
		if (optFileID.isPresent()) {
			fileDTO.setFileSize(fileDTO.getFile().length);
			
			saveReference(fileDTO);
		}else{
			throw new BusinessException("Failed to save in MongoDB : ");
		}
		
		return "success";
	}
	
	public void saveReference(FileDTO fileDTO) {
		TransactionType trxType = TransactionType.fromCode(fileDTO.getTrxType());
		switch (trxType) {
		case ODD_EXPORT:
		case ODD_IMPORT:
		case ODD_IMPORT_EXPORT:
			oddGateOutService.updateODDReference(fileDTO);
			break;
		case IMPORT:
			importGateOutService.updateGatePassReference(fileDTO);
			break;
		case EXPORT:
			exportGateOutService.updateExportReference(fileDTO);
			break;
		case IMPORT_EXPORT:
			importGateOutService.updateGatePassReference(fileDTO);
			exportGateOutService.updateExportReference(fileDTO);
			break;
		default:
			break;
		}
	}

}
