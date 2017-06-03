package com.privasia.scss.gateout.service;

import java.io.ByteArrayInputStream;
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
			fileDTO.setFileStream(new ByteArrayInputStream(fileDTO.getFile()));
			fileDTO.setFileSize(fileDTO.getFile().length);
			
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
			case ODD:
				if (fileDTO.getOddSeq1().isPresent()) {
					uniqueId.append("_").append(fileDTO.getOddSeq1().get());
				}
				if (fileDTO.getOddSeq2().isPresent()) {
					uniqueId.append("_").append(fileDTO.getOddSeq2().get());
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
		case ODD:
			return "oddWHTransaction";
		default:
			return "noTransaction";
		}
	}
	
	public String saveFile(FileDTO fileDTO, CollectionType collectionType) throws IOException {
		
		fileDTO.setCollectionType(collectionType);
		Optional<String> optFileID  = saveFileToMongoDB(fileDTO);
		
		// save to file references
		if (optFileID.isPresent()) {
			System.out.println("SAVED FILE ID"+ optFileID.get());
			
			
			saveReference(fileDTO);
		}else{
			throw new BusinessException("Failed to save in MongoDB : ");
		}
		
		return "success";
	}
	
	public void saveReference(FileDTO fileDTO) {
		TransactionType trxType = TransactionType.fromCode(fileDTO.getTrxType());
		switch (trxType) {
		case ODD:
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
