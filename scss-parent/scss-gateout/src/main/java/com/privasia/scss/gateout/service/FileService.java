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
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.gateout.mongo.repository.GridFSRepository;

@Service("fileService")
public class FileService {

	private GridFSRepository gridFSRepository;

	@Autowired
	public void setGridFSRepository(GridFSRepository gridFSRepository) {
		this.gridFSRepository = gridFSRepository;
	}

	public Optional<String> saveFileToMongoDB(FileDTO fileDTO) throws IOException {

		fileDTO = createFileId(fileDTO);

		if (fileDTO.getFileName().isPresent()) {

			DBObject metaData = new BasicDBObject();

			Document doc = new Document().append("trxType", convertTransactionTypeToString(fileDTO.getTrxType()))
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

	private String convertTransactionTypeToString(TransactionType trxType) {

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

}
