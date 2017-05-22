package com.privasia.scss.gateout.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.SolasPassFileDTO;
import com.privasia.scss.common.enums.CollectionType;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.SolasInstructionType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.etpws.dto.SolasETPDTO;
import com.privasia.scss.etpws.service.client.ETPWebserviceClient;
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.gateout.whodd.service.ODDGateOutService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service("solasGateOutService")
public class SolasGateOutService {

	@Value("${solas.cert.name}")
	private String solasCertName;

	private FileService fileService;

	private ExportGateOutService exportGateOutService;

	private ImportGateOutService importGateOutService;

	private ODDGateOutService oDDGateOutService;

	private ETPWebserviceClient etpWebserviceClient;

	private ResourceLoader resourceLoader;

	private ETPSolasLogService etpSolasLogService;

	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private ExportsRepository exportsRepository;

	@Autowired
	public void setExportsRepository(ExportsRepository exportsRepository) {
		this.exportsRepository = exportsRepository;
	}

	@Autowired
	public void setEtpSolasLogService(ETPSolasLogService etpSolasLogService) {
		this.etpSolasLogService = etpSolasLogService;
	}

	@Autowired
	public void setEtpWebserviceClient(@Qualifier("etpWebserviceClient") ETPWebserviceClient etpWebserviceClient) {
		this.etpWebserviceClient = etpWebserviceClient;
	}

	@Autowired
	public void setGateOutODDService(ODDGateOutService oDDGateOutService) {
		this.oDDGateOutService = oDDGateOutService;
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
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Autowired
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void updateSolasInfo(List<Long> expIDList, SolasPassFileDTO solasPassFileDTO) {

		if (expIDList == null || expIDList.isEmpty()) {
			throw new BusinessException("No exports id available");
		}

		Predicate byExportsIDList = ExportsPredicates.byExportsIDList(expIDList);
		Predicate byTransactionStatus = ExportsPredicates.byTransactionStatus(TransactionStatus.APPROVED);
		Predicate bySolasInstructionTerminal = ExportsPredicates
				.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_TERMINAL);
		Predicate bySolasInstructionShipper = ExportsPredicates
				.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_SHIPPER);
		//Predicate byWithinTolerance = ExportsPredicates.byWithinTolerance(false);
		Predicate byHpabBooking = ExportsPredicates.byHpabNotNull();
		Predicate byContainerFullOrEmpty = ExportsPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
		Predicate byGateInStatus = ExportsPredicates.byGateInStatus(TransactionStatus.APPROVED);

		Predicate condition = ExpressionUtils.allOf(byExportsIDList, byTransactionStatus,
				ExpressionUtils.or(bySolasInstructionTerminal, bySolasInstructionShipper),
				byHpabBooking, byContainerFullOrEmpty, byGateInStatus);

		Iterable<Exports> exportsList = exportsRepository.findAll(condition);

		if (exportsList != null) {

			Iterator<Exports> solasIterator = exportsList.iterator();

			if (solasIterator.hasNext()) {

				final List<SolasETPDTO> solasETPDTOs = new ArrayList<SolasETPDTO>();

				try {

					solasIterator.forEachRemaining(exports -> {
						
						constructSolasETPDTO(exports, solasETPDTOs);
					});

					// assign values from solasPassFileDTO
					if(solasPassFileDTO != null){
						updateSolasETPDTOList(solasETPDTOs, solasPassFileDTO);
					}
					// send solas info to etp
					etpWebserviceClient.updateSolasToEtp(solasETPDTOs);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					etpSolasLogService.saveETPSolasLog(solasETPDTOs);
				}
			}
		}
	}

	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Future<SolasPassFileDTO> generateSolasCertificateInfo(List<Long> expIDList) {
		if (expIDList == null || expIDList.isEmpty()) {
			throw new BusinessException("No exports id available");
		}

		Predicate byExportsIDList = ExportsPredicates.byExportsIDList(expIDList);
		Predicate byTransactionStatus = ExportsPredicates.byTransactionStatus(TransactionStatus.APPROVED);
		Predicate bySolasInstructionTerminal = ExportsPredicates
				.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_TERMINAL);
		Predicate bySolasInstructionShipper = ExportsPredicates
				.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_SHIPPER);
		Predicate byWithinTolerance = ExportsPredicates.byWithinTolerance(false);
		Predicate byHpabBooking = ExportsPredicates.byHpabNotNull();
		Predicate byContainerFullOrEmpty = ExportsPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
		Predicate byGateInStatus = ExportsPredicates.byGateInStatus(TransactionStatus.APPROVED);

		Predicate condition = ExpressionUtils.allOf(byExportsIDList, byTransactionStatus,
				ExpressionUtils.or(bySolasInstructionTerminal,
						ExpressionUtils.and(bySolasInstructionShipper, byWithinTolerance)),
				byHpabBooking, byContainerFullOrEmpty, byGateInStatus);

		Iterable<Exports> exportsList = exportsRepository.findAll(condition);

		if (exportsList != null) {

			Iterator<Exports> solasIterator = exportsList.iterator();

			if (solasIterator.hasNext()) {

				final SolasPassFileDTO solasPassFileDTO = new SolasPassFileDTO();
				FileDTO fileInfoDTO = null;

				Optional<String> fileId = Optional.ofNullable(null);

				try {

					solasIterator.forEachRemaining(exports -> {
						constructSolasPassFileDTO(exports, solasPassFileDTO);
					});

					// generate certificate
					generateSolasCertificate(solasPassFileDTO);

					byte[] pdfBytes = solasPassFileDTO.getCertificate();
					if ((pdfBytes == null || pdfBytes.length <= 0)) {
						throw new BusinessException("Failed to generate the certificate : ");
					}

					fileInfoDTO = new FileDTO();
					fileInfoDTO.setFileName(Optional.ofNullable(solasPassFileDTO.getCertificateNo()));
					fileInfoDTO.setTrxType(TransactionType.EXPORT);

					if (StringUtils.isNotEmpty(solasPassFileDTO.getExportSEQ01())) {
						fileInfoDTO.setExportNoSeq1(
								Optional.ofNullable(Long.parseLong(solasPassFileDTO.getExportSEQ01())));
					}
					if (StringUtils.isNotEmpty(solasPassFileDTO.getExportSEQ02())) {
						fileInfoDTO.setExportNoSeq2(
								Optional.ofNullable(Long.parseLong(solasPassFileDTO.getExportSEQ02())));
					}

					fileInfoDTO.setFileSize(pdfBytes.length);
					fileInfoDTO.setFileStream(new ByteArrayInputStream(pdfBytes));
					fileInfoDTO.setCollectionType(CollectionType.SOLAS_CERTIFICATE_COLLECTION);

					// save to mongodb
					fileId = fileService.saveFileToMongoDB(fileInfoDTO);
					// save to file references
					if (fileId.isPresent()) {
						saveReference(fileInfoDTO);
					}else{
						throw new BusinessException("Failed to save in MongoDB : ");
					}

				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				return new AsyncResult<SolasPassFileDTO>(solasPassFileDTO);
			}
		}
		return new AsyncResult<SolasPassFileDTO>(null);
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<SolasETPDTO> constructSolasETPDTO(Exports exports, List<SolasETPDTO> solasETPDTOs) {
		SolasETPDTO solasETPDTO = new SolasETPDTO();

		if (StringUtils.isNotEmpty(exports.getCalculatedVariance())) {
			solasETPDTO.setCalculatedVariance(new BigDecimal(exports.getCalculatedVariance(), MathContext.DECIMAL64));
		}

		solasETPDTO.setGrossWeight(exports.getGrossWeight());

		if ((exports.getBaseCommonGateInOutAttribute() == null)) {
			throw new BusinessException("Required Common Gate In Out Details not found! ");
		}
		solasETPDTO.setGateInOK(exports.getBaseCommonGateInOutAttribute().getTimeGateInOk());

		solasETPDTO.setWithInTolerance(exports.isWithinTolerance());
		solasETPDTO.setExportSEQ(Long.toString(exports.getExportID()));

		if (exports.getSolas() == null) {
			throw new BusinessException("SolasApplicable not found! ");
		}
		solasETPDTO.setMgw(exports.getSolas().getMgw());
		if (StringUtils.isNotBlank(exports.getSolas().getShipperVGM())) {
			solasETPDTO.setShipperVGM(Integer.parseInt(exports.getSolas().getShipperVGM()));
		}

		solasETPDTO.setSolasDetail(exports.getSolas().getSolasDetailID());
		solasETPDTO.setSolasRefNumber(exports.getSolas().getSolasRefNumber());
		solasETPDTO.setTerminalVGM(exports.getSolas().getMgw());

		if (StringUtils.isNotBlank(exports.getCalculatedVariance())) {
			solasETPDTO.setTolerance(Integer.parseInt(exports.getCalculatedVariance()));
		}
		solasETPDTOs.add(solasETPDTO);

		return solasETPDTOs;
	}

	public List<SolasETPDTO> updateSolasETPDTOList(List<SolasETPDTO> solasETPDTOs, SolasPassFileDTO solasPassFileDTO) {
		if (!(solasETPDTOs == null || solasETPDTOs.isEmpty())) {
			solasETPDTOs.forEach(solasETPDTO -> {
				solasETPDTO.setCertificateNo(solasPassFileDTO.getCertificateNo());
				solasETPDTO.setIssueBy(solasPassFileDTO.getIssueBy());
				solasETPDTO.setIssuerId(Long.toString(solasPassFileDTO.getIssuerId()));
				solasETPDTO.setIssuerNRIC(solasPassFileDTO.getIssuerNRIC());
				solasETPDTO.setWeighingMethod(solasPassFileDTO.getWeighingMethod());
				solasETPDTO.setWeighStation(solasPassFileDTO.getWeighStation());
				solasETPDTO.setCertificate(solasPassFileDTO.getCertificate());
			});
		}
		return solasETPDTOs;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public SolasPassFileDTO constructSolasPassFileDTO(Exports exports, SolasPassFileDTO solasPassFileDTO) {
		if ((exports.getContainer() == null)) {
			throw new BusinessException("Container Details not found To Generate SolasApplicable Cert! ");
		}
		if ((exports.getSolas() == null)) {
			throw new BusinessException("SolasApplicable not found to Generate SolasApplicable Cert! ");
		}
		// to check if this is first time
		if (StringUtils.isEmpty(solasPassFileDTO.getExportSEQ01())) {

			if ((exports.getBaseCommonGateInOutAttribute() == null)) {
				throw new BusinessException(
						"Required Gate In Out Details not found to Generate SolasApplicable Cert! ");
			}

			if ((exports.getBaseCommonGateInOutAttribute().getTimeGateInOk() == null)) {
				throw new BusinessException("Gate in time not Found to Generate SolasApplicable Cert! ");
			}

			solasPassFileDTO
					.setGateInOK(exports.getBaseCommonGateInOutAttribute().getTimeGateInOk().format(dateTimeFormatter));

			solasPassFileDTO.setCertificateNo(generateSolasCertificateId(solasPassFileDTO.getGateInOK()));

			if ((exports.getBaseCommonGateInOutAttribute().getGateInClerk() == null)) {
				throw new BusinessException("Issuer Not Found to Generate SolasApplicable Cert! ");
			}

			SystemUser systemUser = exports.getBaseCommonGateInOutAttribute().getGateInClerk();
			solasPassFileDTO.setIssuerId(systemUser.getSystemUserID());
			solasPassFileDTO.setIssueBy(systemUser.getCommonContactAttribute().getPersonName());
			String nicNo = StringUtils.isNotEmpty(systemUser.getCommonContactAttribute().getNewNRICNO()) == true
					? systemUser.getCommonContactAttribute().getNewNRICNO()
					: systemUser.getCommonContactAttribute().getOldNRICNO();
			solasPassFileDTO.setIssuerNRIC(nicNo);

			if ((exports.getBaseCommonGateInOutAttribute().getGateInClient() == null)) {
				throw new BusinessException("Client Not Found to Generate SolasApplicable Cert ! ");
			}
			Client client = exports.getBaseCommonGateInOutAttribute().getGateInClient();
			solasPassFileDTO.setWeighStation(client.getUnitNo());

			solasPassFileDTO.setC1WithInTolerance(exports.isWithinTolerance());
			solasPassFileDTO.setContainer01No(exports.getContainer().getContainerNumber());
			solasPassFileDTO.setTerminalVGMC1(exports.getSolas().getMgw());
			solasPassFileDTO.setExportSEQ01(Long.toString(exports.getExportID()));

		} else {
			solasPassFileDTO.setC2WithInTolerance(exports.isWithinTolerance());
			solasPassFileDTO.setContainer02No(exports.getContainer().getContainerNumber());
			solasPassFileDTO.setTerminalVGMC2(exports.getSolas().getMgw());
			solasPassFileDTO.setExportSEQ02(Long.toString(exports.getExportID()));
		}
		return solasPassFileDTO;
	}

	public String generateSolasCertificateId(String gateInOK) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(solasCertName);
		LocalDateTime localDateTime = LocalDateTime.parse(gateInOK, dateTimeFormatter);
		DateTimeFormatter localdateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		String timeString = localDateTime.format(localdateTimeFormatter);
		buffer.append(timeString);
		return buffer.toString();

	}

	public SolasPassFileDTO generateSolasCertificate(SolasPassFileDTO solasPassFileDTO)
			throws JRException, IOException {

		List<SolasPassFileDTO> dataList = new ArrayList<SolasPassFileDTO>();

		if (!solasPassFileDTO.isC1WithInTolerance() || !solasPassFileDTO.isC2WithInTolerance()) {

			dataList.add(solasPassFileDTO);

			HashMap<String, Object> params = new HashMap<String, Object>();

			// 1. Add report parameters
			JRDataSource reportData = new JRBeanCollectionDataSource(dataList);

			params.put("logoPath",
					ImageIO.read(resourceLoader.getResource(ApplicationConstants.E_SOLAS_PASS_LOGO).getInputStream()));

			// 3. Convert template to JasperDesign
			JasperDesign jd = JRXmlLoader
					.load(resourceLoader.getResource(ApplicationConstants.E_SOLAS_PASS).getInputStream());
			// 4. Compile design to JasperReport
			JasperReport jr = JasperCompileManager.compileReport(jd);
			// 5. Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and
			// data source
			JasperPrint jp = JasperFillManager.fillReport(jr, params, reportData);

			byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);

			solasPassFileDTO.setCertificate(pdfBytes);

			// convert array of bytes into file /
			/*
			 * FileOutputStream fileOuputStream = new
			 * FileOutputStream("C://scss-opus//pass.pdf");
			 * fileOuputStream.write(pdfBytes); fileOuputStream.close();
			 */
		}

		return solasPassFileDTO;

	}

	public void saveReference(FileDTO fileDTO) {
		TransactionType trxType = fileDTO.getTrxType();
		switch (trxType) {
		case ODD_EXPORT:
		case ODD_IMPORT:
		case ODD_IMPORT_EXPORT:
			oDDGateOutService.updateODDReference(fileDTO);
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
