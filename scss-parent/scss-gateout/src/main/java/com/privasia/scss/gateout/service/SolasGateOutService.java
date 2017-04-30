package com.privasia.scss.gateout.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.SolasPassFileDTO;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.etpws.dto.SolasETPDTO;
import com.privasia.scss.etpws.service.client.ETPWebserviceClient;
import com.privasia.scss.gateout.dto.FileDTO;

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

	private GateOutODDService gateOutODDService;

	private ETPWebserviceClient etpWebserviceClient;
	
	private ResourceLoader resourceLoader;

	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	@Autowired
	public void setEtpWebserviceClient(ETPWebserviceClient etpWebserviceClient) {
		this.etpWebserviceClient = etpWebserviceClient;
	}

	@Autowired
	public void setGateOutODDService(GateOutODDService gateOutODDService) {
		this.gateOutODDService = gateOutODDService;
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

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public boolean isSolasApplicable(List<Exports> exportsList) throws JRException, IOException, ParseException {
		boolean isSolasApplicable = false;
		// check if at least one export container is solas container
		// at least one should be within tolerance = false stream and select

		Optional<List<Exports>> solasApplicableExportsListOpt = Optional.ofNullable(
				exportsList.stream().filter(exp -> (!exp.isWithinTolerance())).collect(Collectors.toList()));

		if (solasApplicableExportsListOpt.isPresent()) {

			List<Exports> solasApplicableExportsList = solasApplicableExportsListOpt.get();

			final List<SolasETPDTO> solasETPDTOs = new ArrayList<SolasETPDTO>();
			isSolasApplicable = true;
			final SolasPassFileDTO solasPassFileDTO = new SolasPassFileDTO();

			solasApplicableExportsList.forEach(exports -> {

				// generate solas certification - add data to SolasPassFileDTO
				// and pass
				constructSolasPassFileDTO(exports, solasPassFileDTO);
				// issue by - gate in cleark - name - sys_name set data here
				constructSolasETPDTO(exports, solasETPDTOs);

			});

			// generate certificate
			generateSolasCertificate(solasPassFileDTO);

			byte[] pdfBytes = solasPassFileDTO.getCertificate();
			if ((pdfBytes == null || pdfBytes.length <= 0)) {
				throw new BusinessException("Failed to generate the certificate : ");
			}

			FileDTO fileInfoDTO = new FileDTO();
			fileInfoDTO.setFileName(Optional.ofNullable(solasPassFileDTO.getCertificateNo()));
			fileInfoDTO.setTrxType(TransactionType.EXPORT);
			fileInfoDTO.setExportNoSeq1(Optional.ofNullable(Long.parseLong(solasPassFileDTO.getExportSEQ01())));
			fileInfoDTO.setFileSize(pdfBytes.length);
			fileInfoDTO.setFileStream(new ByteArrayInputStream(pdfBytes));

			// save to mongodb
			Optional<String> file1Id = fileService.saveFileToMongoDB(fileInfoDTO);
			if (!(file1Id.isPresent())) {
				throw new BusinessException("Failed to save in MongoDB : ");
			}
			// save to file references
			saveReference(fileInfoDTO);

			// assign values from solasPassFileDTO
			updateSolasETPDTOList(solasETPDTOs, solasPassFileDTO);
			// send solas info to etp
			etpWebserviceClient.updateSolasToEtp(solasETPDTOs);
				
			//save to scss data base
		}

		return isSolasApplicable;

	}

	public List<SolasETPDTO> constructSolasETPDTO(Exports exports, List<SolasETPDTO> solasETPDTOs) {
		SolasETPDTO solasETPDTO = new SolasETPDTO();
		solasETPDTO.setCalculatedVariance(new BigDecimal(exports.getCalculatedVariance(), MathContext.DECIMAL64));
		solasETPDTO.setGrossWeight(exports.getGrossWeight());

		if ((exports.getBaseCommonGateInOutAttribute() == null)) {
			throw new BusinessException("Required Common Gate In Out Details not found! ");
		}
		solasETPDTO.setGateInOK(exports.getBaseCommonGateInOutAttribute().getTimeGateInOk());

		solasETPDTO.setWithInTolerance(exports.isWithinTolerance());
		solasETPDTO.setExportSEQ(Long.toString(exports.getExportID()));

		if (exports.getSolas() == null) {
			throw new BusinessException("Solas not found! ");
		}
		solasETPDTO.setMgw(exports.getSolas().getMgw());
		if(StringUtils.isNotBlank(exports.getSolas().getShipperVGM())){
			solasETPDTO.setShipperVGM(Integer.parseInt(exports.getSolas().getShipperVGM()));
		}
		
		solasETPDTO.setSolasDetail(exports.getSolas().getSolasDetailID());
		solasETPDTO.setSolasRefNumber(exports.getSolas().getSolasRefNumber());
		solasETPDTO.setTerminalVGM(exports.getSolas().getMgw());
		
		if(StringUtils.isNotBlank(exports.getCalculatedVariance())){
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

	public SolasPassFileDTO constructSolasPassFileDTO(Exports exports, SolasPassFileDTO solasPassFileDTO) {
		if ((exports.getContainer() == null)) {
			throw new BusinessException("Container Details not found To Generate Solas Cert! ");
		}
		if ((exports.getSolas() == null)) {
			throw new BusinessException("Solas not found to Generate Solas Cert! ");
		}
		if (solasPassFileDTO == null) {

			solasPassFileDTO = new SolasPassFileDTO();

			if ((exports.getBaseCommonGateInOutAttribute() == null)) {
				throw new BusinessException("Required Gate In Out Details not found to Generate Solas Cert! ");
			}

			if ((exports.getBaseCommonGateInOutAttribute().getTimeGateInOk() == null)) {
				throw new BusinessException("Gate in time not Found to Generate Solas Cert! ");
			}

			solasPassFileDTO
					.setGateInOK(exports.getBaseCommonGateInOutAttribute().getTimeGateInOk().format(dateTimeFormatter));

			solasPassFileDTO.setCertificateNo(generateSolasCertificateId(solasPassFileDTO.getGateInOK()));

			if ((exports.getBaseCommonGateInOutAttribute().getGateInClerk() == null)) {
				throw new BusinessException("Issuer Not Found to Generate Solas Cert! ");
			}

			SystemUser systemUser = exports.getBaseCommonGateInOutAttribute().getGateInClerk();
			solasPassFileDTO.setIssuerId(systemUser.getSystemUserID());
			solasPassFileDTO.setIssueBy(systemUser.getCommonContactAttribute().getPersonName());
			String nicNo = StringUtils.isNotEmpty(systemUser.getCommonContactAttribute().getNewNRICNO()) == true
					? systemUser.getCommonContactAttribute().getNewNRICNO()
					: systemUser.getCommonContactAttribute().getOldNRICNO();
			solasPassFileDTO.setIssuerNRIC(nicNo);

			if ((exports.getBaseCommonGateInOutAttribute().getGateInClient() == null)) {
				throw new BusinessException("Client Not Found to Generate Solas Cert ! ");
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

	public SolasPassFileDTO generateSolasCertificate(SolasPassFileDTO solasPassFileDTO) throws JRException, IOException {

		List<SolasPassFileDTO> dataList = new ArrayList<SolasPassFileDTO>();
			if (!solasPassFileDTO.isC1WithInTolerance() || !solasPassFileDTO.isC2WithInTolerance()) {

				dataList.add(solasPassFileDTO);

				HashMap<String, Object> params = new HashMap<String, Object>();
				
				// 1. Add report parameters
				JRDataSource reportData = new JRBeanCollectionDataSource(dataList); 
				
				params.put("logoPath", ImageIO.read(resourceLoader.getResource(ApplicationConstants.E_SOLAS_PASS_LOGO).getInputStream()));

				// 3. Convert template to JasperDesign
				JasperDesign jd = JRXmlLoader.load(resourceLoader.getResource(ApplicationConstants.E_SOLAS_PASS).getInputStream());
				// 4. Compile design to JasperReport
				JasperReport jr = JasperCompileManager.compileReport(jd);
				// 5. Create the JasperPrint object
				// Make sure to pass the JasperReport, report parameters, and
				// data source
				JasperPrint jp = JasperFillManager.fillReport(jr, params, reportData);

				byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);

				solasPassFileDTO.setCertificate(pdfBytes);

				// convert array of bytes into file /
				// FileOutputStream fileOuputStream = new
				// FileOutputStream("D://pass.pdf");
				// fileOuputStream.write(pdfBytes);
				// fileOuputStream.close();
			}

		
		return solasPassFileDTO;

	}

	public void saveReference(FileDTO fileDTO) {
		TransactionType trxType = fileDTO.getTrxType();
		switch (trxType) {
		case ODD_EXPORT:
		case ODD_IMPORT:
		case ODD_IMPORT_EXPORT:
			gateOutODDService.updateODDReference(fileDTO);
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
