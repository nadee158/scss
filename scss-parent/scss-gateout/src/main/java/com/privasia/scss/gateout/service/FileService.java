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
  
//tomorrows tasks
  //write a methods generateSoalsService(List<Export> list)
  
  //old app:-
  //web-inf->solaspass.jrxml logo.png copy to this app
  //implement following in this app
  /*
  public String generateSolasCertificateId(String gateInOK) throws ParseException{
    
    StringBuffer buffer = new StringBuffer();
    buffer.append("WPT");
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date d = sdf.parse(gateInOK);
    sdf.applyPattern("yyyyMMddHHmmssSSS");
    buffer.append(sdf.format(d));
    return buffer.toString();
    
   }
*/
  
//convert array of bytes into file (use this to check file)
  /*FileOutputStream fileOuputStream = 
               new FileOutputStream("C://pass.pdf"); 
  fileOuputStream.write(pdfBytes);
  fileOuputStream.close();*/
  
  
/* 
 * Only following data is required to generate the repprt
 *  solasDTO.setIssueBy(SYSUser.getUserFullName(solasDTO.getIssuerId()));
  solasDTO.setIssuerNRIC(SYSUser.getUserNricNo(solasDTO.getIssuerId()));
  conn = SCSSDatabase.getInstance().getConnection();
  String gateNO = Client.selectGateNo(conn, solasDTO.getWeighStation());
  solasDTO.setWeighStation(gateNO);
solasDTO.setCertificateNo(generateSolasCertificateId(solasDTO.getGateInOK()));*/
  
  //to generate the report - first hard code data and generate report
  /*
   * public SolasDTO generateSolasCertificate(SolasDTO solasDTO){
  
  List<SolasDTO> dataList = new ArrayList<SolasDTO>();
  InputStream is = null;
  Connection conn =  null;
  GateInDAOImpl gateInDAOImpl = GateInDAOImpl.getInstance();
  
  try{
   
   if(!solasDTO.isC1WithInTolerance() || !solasDTO.isC2WithInTolerance()){
    
    solasDTO.setIssueBy(SYSUser.getUserFullName(solasDTO.getIssuerId()));
    solasDTO.setIssuerNRIC(SYSUser.getUserNricNo(solasDTO.getIssuerId()));
    conn = SCSSDatabase.getInstance().getConnection();
    String gateNO = Client.selectGateNo(conn, solasDTO.getWeighStation());
    solasDTO.setWeighStation(gateNO);
    conn.commit(); 
    
    if(StringUtils.isBlank(solasDTO.getCertificateNo())){
     solasDTO.setCertificateNo(generateSolasCertificateId(solasDTO.getGateInOK()));
    }
    dataList.add(solasDTO);
    
    HashMap<String, Object> params = new HashMap<String, Object>();
    
    
    JRDataSource reportData = new JRBeanCollectionDataSource(dataList);        
    // 1. Add report parameters
    params.put("logoPath", ImageIO.read(getClass().getResource(SCSSConstant.E_SOLAS_PASS_LOGO)));
    is = this.getClass().getResourceAsStream(SCSSConstant.E_SOLAS_PASS);
    
    // 3. Convert template to JasperDesign
    JasperDesign jd = JRXmlLoader.load(is);
    // 4. Compile design to JasperReport
    JasperReport jr = JasperCompileManager.compileReport(jd);
    // 5. Create the JasperPrint object
    // Make sure to pass the JasperReport, report parameters, and data
    // source
    JasperPrint jp = JasperFillManager.fillReport(jr, params, reportData);

    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);
    solasDTO.setCertificate(pdfBytes);
    
    MongoDBDAOImpl mongoDBDAOImpl = MongoDBDAOImpl.getInstance();
    
    if(StringUtils.isNotBlank(solasDTO.getExportSEQ01()) && !solasDTO.isC1WithInTolerance()){
     FileInfo fileInfo = new FileInfo();
     //fileInfo.setFileID(solasDTO.getCertificateNo());
     fileInfo.setTransactionType(SCSSConstant.EXP_TRANSACTION);
     fileInfo.setTrxId(Long.parseLong(solasDTO.getExportSEQ01()));
     
     fileInfo.setFileSize(pdfBytes.length);
     fileInfo.setInputStream(new ByteArrayInputStream(pdfBytes));
     mongoDBDAOImpl.saveFileToBucket(SCSSConstant.SOLAS_CERTIFICATE_COLLECTION, fileInfo);
    }
    
    if(StringUtils.isNotBlank(solasDTO.getExportSEQ02()) && !solasDTO.isC2WithInTolerance()){
     FileInfo fileInfo2 = new FileInfo();
     //fileInfo.setFileID(solasDTO.getCertificateNo());
     fileInfo2.setTransactionType(SCSSConstant.EXP_TRANSACTION);
     fileInfo2.setTrxId(Long.parseLong(solasDTO.getExportSEQ02()));
     fileInfo2.setFileSize(pdfBytes.length);
     fileInfo2.setInputStream(new ByteArrayInputStream(pdfBytes));
     mongoDBDAOImpl.saveFileToBucket(SCSSConstant.SOLAS_CERTIFICATE_COLLECTION, fileInfo2);
     
    }
    
    //convert array of bytes into file
       /*FileOutputStream fileOuputStream = 
                    new FileOutputStream("C://pass.pdf"); 
       fileOuputStream.write(pdfBytes);
       fileOuputStream.close();
   }
   
   
  }catch(Exception e){
   String stackTrace = ExceptionUtil.getExceptionStacktrace(e);
   log.error(stackTrace);
    
  }finally{
   try {
    if(is!=null){
     is.close();
    }
    
    if(conn!=null){
     conn.close();
    }
    if(!solasDTO.isC1WithInTolerance() || !solasDTO.isC2WithInTolerance()){
     gateInDAOImpl.updateSolasCertificateNumber(solasDTO);
    }
    
   } catch (IOException e) {
    String stackTrace = ExceptionUtil.getExceptionStacktrace(e);
    log.error(stackTrace);
   } catch (SQLException e) {
    String stackTrace = ExceptionUtil.getExceptionStacktrace(e);
    log.error(stackTrace);
   } catch (Exception e) {
    String stackTrace = ExceptionUtil.getExceptionStacktrace(e);
    log.error(stackTrace);
   }
  }
  
  return solasDTO;
  
 }
   * */
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

		Optional<List<Exports>> exportsOptList = exportsRepository.findByExportIDIn(
				Arrays.asList(fileDTO.getExportNoSeq1().orElse(0l), fileDTO.getExportNoSeq2().orElse(0l)));

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

		Optional<List<GatePass>> gatePassOptList = gatePassRepository.findByGatePassNoIn(
				Arrays.asList(fileDTO.getGatePassNo1().orElse(0l), fileDTO.getGatePassNo2().orElse(0l)));

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
		
		Optional<List<WHODD>> oddOptList = oddRepository.findByOddIdSeqIn(
				Arrays.asList(fileDTO.getOddImpSeq1().orElse(0l), fileDTO.getOddImpSeq2().orElse(0l), 
						fileDTO.getOddExpSeq1().orElse(0l), fileDTO.getOddExpSeq2().orElse(0l)));

		if (!(oddOptList.orElse(null) == null || oddOptList.get().isEmpty())) {
			oddOptList.get().forEach(whODD -> {
				assignUpdatedValuedWHODDobj(whODD, fileDTO);
				oddRepository.save(whODD);
			});
		} else {
			throw new BusinessException("Invalid WhODD ID to update file reference : "
					+ fileDTO.getOddImpSeq1().orElse(null) + " / " + fileDTO.getOddImpSeq2().orElse(null)
					+ " / " + fileDTO.getOddExpSeq1().orElse(null) + " / " + fileDTO.getOddExpSeq2().orElse(null));
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
