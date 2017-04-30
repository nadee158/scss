package com.privasia.scss.gateout.service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.SolasPassFileDTO;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.etpws.service.UpdateSolasForScssGateInResponseType;
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.gateout.mongo.repository.GridFSRepository;
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

  private SystemUserRepository systemUserRepository;

  private ClientRepository clientRepository;

  private GridFSRepository gridFSRepository;

  private FileService fileService;

  private ExportGateOutService exportGateOutService;

  private ImportGateOutService importGateOutService;

  private GateOutODDService gateOutODDService;

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
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setGridFSRepository(GridFSRepository gridFSRepository) {
    this.gridFSRepository = gridFSRepository;
  }

  public boolean isSolasApplicable(List<Exports> exports) {
    // check if at least one export container is solas container
    // at least one should be within tolerance = false stream and select
    //

    // generate solas certification - add data to SolasPassFileDTO and pass
    // issue by - gate in cleark - name - sys_name set data here

    // save to mongodb
    // save to file references
    // send solas info to etp
    return false;
  }


  // public static void main(String[] args) {
  // SolasPassFileDTO solasPassFileDTO = new SolasPassFileDTO();
  // solasPassFileDTO.setC1WithInTolerance(false);
  // solasPassFileDTO.setC2WithInTolerance(false);
  // solasPassFileDTO.setExportSEQ01("1500");
  // solasPassFileDTO.setExportSEQ02("1250");
  // solasPassFileDTO.setGateInOK("01-04-2017 14:05:25");
  // solasPassFileDTO.setContainer01No("CONT001");
  // solasPassFileDTO.setTerminalVGMC1(5822);
  // solasPassFileDTO.setContainer02No("CONT002");
  // solasPassFileDTO.setTerminalVGMC2(5825);
  // SolasGateOutService gateOutService = new SolasGateOutService();
  // gateOutService.generateSolasCertificate(solasPassFileDTO);
  // }

  public String generateSolasCertificateId(String gateInOK) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("WPT");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(gateInOK, dateTimeFormatter);
    dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    String timeString = localDateTime.format(dateTimeFormatter);
    buffer.append(timeString);
    return buffer.toString();

  }


  public SolasPassFileDTO generateSolasCertificate(SolasPassFileDTO solasPassFileDTO) {

    List<SolasPassFileDTO> dataList = new ArrayList<SolasPassFileDTO>();

    InputStream is = null;
    Connection conn = null;

    try {

      if (!solasPassFileDTO.isC1WithInTolerance() || !solasPassFileDTO.isC2WithInTolerance()) {

        // FOR TESTING
        // SystemUser systemUser = new SystemUser();
        // systemUser.setCommonContactAttribute(new CommonContactAttribute());
        // systemUser.getCommonContactAttribute().setNewNRICNO("NewNRICNO");
        // systemUser.getCommonContactAttribute().setNewNRICNO("OldNRICNO");
        // systemUser.getCommonContactAttribute().setPersonName("Person Name");

        SystemUser systemUser = systemUserRepository.findOne(solasPassFileDTO.getIssuerId())
            .orElseThrow(() -> new ResultsNotFoundException("Issuer Not Found! " + solasPassFileDTO.getIssuerId()));

        solasPassFileDTO.setIssueBy(systemUser.getCommonContactAttribute().getPersonName());
        String nicNo = StringUtils.isNotEmpty(systemUser.getCommonContactAttribute().getNewNRICNO()) == true
            ? systemUser.getCommonContactAttribute().getNewNRICNO()
            : systemUser.getCommonContactAttribute().getOldNRICNO();
        solasPassFileDTO.setIssuerNRIC(nicNo);

        // FOR TESTING
        // Client client = new Client();
        // client.setUnitNo("Unit No");

        Client client = clientRepository.findOne(Long.parseLong(solasPassFileDTO.getWeighStation()))
            .orElseThrow(() -> new ResultsNotFoundException("Client Not Found! " + solasPassFileDTO.getWeighStation()));


        solasPassFileDTO.setWeighStation(client.getUnitNo());

        if (StringUtils.isBlank(solasPassFileDTO.getCertificateNo())) {
          solasPassFileDTO.setCertificateNo(generateSolasCertificateId(solasPassFileDTO.getGateInOK()));
        }
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
        FileOutputStream fileOuputStream = new FileOutputStream("D://pass.pdf");
        fileOuputStream.write(pdfBytes);
        fileOuputStream.close();
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

  public void updateSolasETPStatus(SolasDTO solasDTO, boolean update) {

    EtpIntegrationService etpIntegrationService = new EtpIntegrationService();



    int retry = 0;

    Date today = null;

    try {



      UpdateSolasForScssGateInResponseType response = null;



      if (StringUtils.isNotBlank(solasDTO.getSolasDetailC2())) {

        retry = 0;

        parameters.setSolasDetId(solasDTO.getSolasDetailC2().toLowerCase());
        parameters.setTerminalVgm(solasDTO.getTerminalVGMC2());
        parameters.setVgmReferenceNo(solasDTO.getExportSEQ02());
        parameters.setGrossWeight(solasDTO.getGrossWeightC2());
        parameters.setIsTolerance(solasDTO.isC2WithInTolerance());
        parameters.setTerminalMgw(solasDTO.getMgwC2());

        if (solasDTO.getShipperVGMC2() > 0 && !solasDTO.isC2WithInTolerance()) {
          parameters
              .setVariance(solasDTO.getCalculatedVarianceC2() == null ? null : solasDTO.getCalculatedVarianceC2());
        }

        today = Calendar.getInstance().getTime();
        solasDTO.setRequestSendTimeC1(formatter.format(today));
        response = etpIntegrationService.getEtpServicePortTypePort().updateSolasForScssGateIn(parameters);

        if ("FAIL".equals(response.getResponseCode())) {
          while (retry <= 1) {
            response = etpIntegrationService.getEtpServicePortTypePort().updateSolasForScssGateIn(parameters);
            retry++;
          }
          log.info("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 02 : "
              + solasDTO.getContainer02No() + " and error message : " + response.getErrorMessage());
          System.out
              .println("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 02 : "
                  + solasDTO.getContainer02No() + " and error message : " + response.getErrorMessage());
        } else {
          log.info("updateSolasETPStatus response code : " + response.getResponseCode() + " for container 02 : "
              + solasDTO.getContainer02No());
          System.out.println("updateSolasETPStatus response code : " + response.getResponseCode()
              + " for container 02 : " + solasDTO.getContainer02No());
        }

        today = Calendar.getInstance().getTime();
        solasDTO.setResponseReceivedTimeC2(formatter.format(today));
        solasDTO.setEtpC2ResponseCode(response.getResponseCode());
        solasDTO.setEtpC2ResponseMessage(response.getErrorMessage());

      }

      log.info("******************* Update ETP Solas Web Service End *******************  ");
      System.out.println("******************* Update ETP Solas Web Service End *******************  ");

    } catch (Exception e) {
      e.printStackTrace();
      String stackTrace = ExceptionUtil.getExceptionStacktrace(e);
      log.error(stackTrace);
    } finally {
      // save to db
      SolasDAOImpl solasDAOImpl = SolasDAOImpl.getInstance();

      if (update) {
        solasDAOImpl.updateETPSolasLog(solasDTO);
      } else {
        solasDAOImpl.saveETPSolasLog(solasDTO);
      }

    }

  }



}
