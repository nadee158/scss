package com.privasia.scss.gateout.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.stereotype.Service;

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
import com.sun.corba.se.pept.transport.Connection;

import net.sf.jasperreports.engine.JRDataSource;
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

  private FileService fileService;

  private ExportGateOutService exportGateOutService;

  private ImportGateOutService importGateOutService;

  private GateOutODDService gateOutODDService;

  private ETPWebserviceClient etpWebserviceClient;

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



  public boolean isSolasApplicable(List<Exports> exportsList) {
    boolean isSolasApplicable = false;
    // check if at least one export container is solas container
    // at least one should be within tolerance = false stream and select
    List<Exports> solasApplicableExportsList =
        exportsList.stream().filter(exp -> (exp.isWithinTolerance() == false)).collect(Collectors.toList());
    if (!(solasApplicableExportsList == null || solasApplicableExportsList.isEmpty())) {
      List<SolasETPDTO> solasETPDTOs = new ArrayList<SolasETPDTO>();
      isSolasApplicable = true;
      SolasPassFileDTO solasPassFileDTO = null;
      for (Exports exports : solasApplicableExportsList) {
        // generate solas certification - add data to SolasPassFileDTO and pass
        solasPassFileDTO = constructSolasPassFileDTO(exports, solasPassFileDTO);
        // issue by - gate in cleark - name - sys_name set data here
        // generate certificate
        // save to mongodb
        // save to file references
        solasPassFileDTO = generateSolasCertificate(solasPassFileDTO);
      }

      try {
        // send solas info to etp
        solasETPDTOs = etpWebserviceClient.updateSolasToEtp(solasETPDTOs);
      } catch (Exception e) {
        throw new BusinessException("Error from web service : " + e.getMessage());
      }
    }
    return isSolasApplicable;
  }



  private SolasPassFileDTO constructSolasPassFileDTO(Exports exports, SolasPassFileDTO solasPassFileDTO) {
    if (solasPassFileDTO == null) {

      solasPassFileDTO = new SolasPassFileDTO();
      if (!(exports.getBaseCommonGateInOutAttribute() == null)) {

        if (!(exports.getBaseCommonGateInOutAttribute().getTimeGateInOk() == null)) {
          solasPassFileDTO
              .setGateInOK(exports.getBaseCommonGateInOutAttribute().getTimeGateInOk().format(dateTimeFormatter));
        } else {
          throw new BusinessException("Gate in time not Found! ");
        }

        solasPassFileDTO.setCertificateNo(generateSolasCertificateId(solasPassFileDTO.getGateInOK()));

        if (!(exports.getBaseCommonGateInOutAttribute().getGateInClerk() == null)) {

          SystemUser systemUser = exports.getBaseCommonGateInOutAttribute().getGateInClerk();
          solasPassFileDTO.setIssuerId(systemUser.getSystemUserID());
          solasPassFileDTO.setIssueBy(systemUser.getCommonContactAttribute().getPersonName());
          String nicNo = StringUtils.isNotEmpty(systemUser.getCommonContactAttribute().getNewNRICNO()) == true
              ? systemUser.getCommonContactAttribute().getNewNRICNO()
              : systemUser.getCommonContactAttribute().getOldNRICNO();
          solasPassFileDTO.setIssuerNRIC(nicNo);

        } else {
          throw new BusinessException("Issuer Not Found! ");
        }

        if (!(exports.getBaseCommonGateInOutAttribute().getGateInClient() == null)) {
          Client client = exports.getBaseCommonGateInOutAttribute().getGateInClient();
          solasPassFileDTO.setWeighStation(client.getUnitNo());
        } else {
          throw new BusinessException("Client Not Found! ");
        }
      } else {
        throw new BusinessException("Required Common Gate In Out Details not found! ");
      }

      solasPassFileDTO.setC1WithInTolerance(exports.isWithinTolerance());
      if (!(exports.getContainer() == null)) {
        solasPassFileDTO.setContainer01No(exports.getContainer().getContainerNumber());
      } else {
        throw new BusinessException("Container Details not found! ");
      }
      if (!(exports.getSolas() == null)) {
        solasPassFileDTO.setTerminalVGMC1(exports.getSolas().getMgw());
      } else {
        throw new BusinessException("VGM not found! ");
      }
      solasPassFileDTO.setExportSEQ01(Long.toString(exports.getExportID()));

    } else {
      solasPassFileDTO.setC2WithInTolerance(exports.isWithinTolerance());
      if (!(exports.getContainer() == null)) {
        solasPassFileDTO.setContainer02No(exports.getContainer().getContainerNumber());
      } else {
        throw new BusinessException("Container Details not found! ");
      }
      if (!(exports.getSolas() == null)) {
        solasPassFileDTO.setTerminalVGMC2(exports.getSolas().getMgw());
      } else {
        throw new BusinessException("VGM not found! ");
      }
      solasPassFileDTO.setExportSEQ02(Long.toString(exports.getExportID()));
    }
    return solasPassFileDTO;
  }

  public String generateSolasCertificateId(String gateInOK) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("WPT");
    LocalDateTime localDateTime = LocalDateTime.parse(gateInOK, dateTimeFormatter);
    DateTimeFormatter localdateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    String timeString = localDateTime.format(localdateTimeFormatter);
    buffer.append(timeString);
    return buffer.toString();

  }


  public SolasPassFileDTO generateSolasCertificate(SolasPassFileDTO solasPassFileDTO) {

    List<SolasPassFileDTO> dataList = new ArrayList<SolasPassFileDTO>();

    InputStream is = null;
    Connection conn = null;

    try {

      if (!solasPassFileDTO.isC1WithInTolerance() || !solasPassFileDTO.isC2WithInTolerance()) {

        dataList.add(solasPassFileDTO);

        HashMap<String, Object> params = new HashMap<String, Object>();

        JRDataSource reportData = new JRBeanCollectionDataSource(dataList); // 1. Add report
                                                                            // parameters
        params.put("logoPath", ImageIO.read(getClass().getResource(ApplicationConstants.E_SOLAS_PASS_LOGO)));
        is = this.getClass().getResourceAsStream(ApplicationConstants.E_SOLAS_PASS);

        // 3. Convert template to JasperDesign
        JasperDesign jd = JRXmlLoader.load(is);
        // 4. Compile design to JasperReport
        JasperReport jr = JasperCompileManager.compileReport(jd);
        // 5. Create the JasperPrint object
        // Make sure to pass the JasperReport, report parameters, and data source
        JasperPrint jp = JasperFillManager.fillReport(jr, params, reportData);

        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);

        solasPassFileDTO.setCertificate(pdfBytes);


        if (StringUtils.isNotBlank(solasPassFileDTO.getExportSEQ01()) && !solasPassFileDTO.isC1WithInTolerance()) {
          FileDTO fileInfoDTO = new FileDTO(); // fileInfoDTO.setFileID(solasPassFileDTO.getCertificateNo());
          fileInfoDTO.setTrxType(TransactionType.EXPORT);
          fileInfoDTO.setExportNoSeq1(Optional.ofNullable(Long.parseLong(solasPassFileDTO.getExportSEQ01())));
          fileInfoDTO.setFileSize(pdfBytes.length);
          fileInfoDTO.setFileStream(new ByteArrayInputStream(pdfBytes));
          Optional<String> file1Id = fileService.saveFileToMongoDB(fileInfoDTO);
          if (file1Id.isPresent()) {
            saveReference(fileInfoDTO);
          }
        }

        if (StringUtils.isNotBlank(solasPassFileDTO.getExportSEQ02()) && !solasPassFileDTO.isC2WithInTolerance()) {
          FileDTO fileInfoDTO2 = new FileDTO(); // fileInfoDTO.setFileID(solasPassFileDTO.getCertificateNo());
          fileInfoDTO2.setTrxType(TransactionType.EXPORT);
          fileInfoDTO2.setExportNoSeq2(Optional.ofNullable(Long.parseLong(solasPassFileDTO.getExportSEQ02())));
          fileInfoDTO2.setFileSize(pdfBytes.length);
          fileInfoDTO2.setFileStream(new ByteArrayInputStream(pdfBytes));
          Optional<String> file1Id = fileService.saveFileToMongoDB(fileInfoDTO2);
          if (file1Id.isPresent()) {
            saveReference(fileInfoDTO2);
          }
        }

        // convert array of bytes into file /
        // FileOutputStream fileOuputStream = new FileOutputStream("D://pass.pdf");
        // fileOuputStream.write(pdfBytes);
        // fileOuputStream.close();
      }


    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (is != null) {
          is.close();
        }

        if (conn != null) {
          conn.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
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
