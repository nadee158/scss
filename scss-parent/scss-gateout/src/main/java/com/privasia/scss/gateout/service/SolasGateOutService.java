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
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;
import com.privasia.scss.common.dto.SolasPassFileDTO;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
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


  public String generateSolasCertificateId(String gateInOK) {
    System.out.println("gateInOK " + gateInOK);
    StringBuffer buffer = new StringBuffer();
    buffer.append("WPT");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(gateInOK, dateTimeFormatter);
    System.out.println("localDateTime: " + localDateTime);
    dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    String timeString = localDateTime.format(dateTimeFormatter);
    System.out.println("timeString: " + timeString);
    buffer.append(timeString);
    System.out.println("buffer.toString(): " + buffer.toString());
    return buffer.toString();

  }


  public SolasPassFileDTO generateSolasCertificate(SolasPassFileDTO solasPassFileDTO) {

    List<SolasPassFileDTO> dataList = new ArrayList<SolasPassFileDTO>();

    InputStream is = null;
    Connection conn = null;

    try {

      if (!solasPassFileDTO.isC1WithInTolerance() || !solasPassFileDTO.isC2WithInTolerance()) {

        SystemUser systemUser = systemUserRepository.findOne(solasPassFileDTO.getIssuerId())
            .orElseThrow(() -> new ResultsNotFoundException("Issuer Not Found! " + solasPassFileDTO.getIssuerId()));

        solasPassFileDTO.setIssueBy(systemUser.getCommonContactAttribute().getPersonName());
        String nicNo = StringUtils.isNotEmpty(systemUser.getCommonContactAttribute().getNewNRICNO()) == true
            ? systemUser.getCommonContactAttribute().getNewNRICNO()
            : systemUser.getCommonContactAttribute().getOldNRICNO();
        solasPassFileDTO.setIssuerNRIC(nicNo);

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
          fileInfoDTO.setCameraImage(new ByteArrayInputStream(pdfBytes));
          saveFileToBucket(ApplicationConstants.SOLAS_CERTIFICATE_COLLECTION, fileInfoDTO);
        }

        if (StringUtils.isNotBlank(solasPassFileDTO.getExportSEQ02()) && !solasPassFileDTO.isC2WithInTolerance()) {
          FileDTO fileInfoDTO2 = new FileDTO(); // fileInfoDTO.setFileID(solasPassFileDTO.getCertificateNo());
          fileInfoDTO2.setTrxType(TransactionType.EXPORT);
          fileInfoDTO2.setExportNoSeq2(Optional.ofNullable(Long.parseLong(solasPassFileDTO.getExportSEQ02())));
          fileInfoDTO2.setFileSize(pdfBytes.length);
          fileInfoDTO2.setCameraImage(new ByteArrayInputStream(pdfBytes));
          saveFileToBucket(ApplicationConstants.SOLAS_CERTIFICATE_COLLECTION, fileInfoDTO2);
        }

        // convert array of bytes into file /
        FileOutputStream fileOuputStream = new FileOutputStream("C://pass.pdf");
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
        if (!solasPassFileDTO.isC1WithInTolerance() || !solasPassFileDTO.isC2WithInTolerance()) {
          // gateInDAOImpl.updateSolasCertificateNumber(solasPassFileDTO);
        }

      } catch (IOException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return solasPassFileDTO;

  }

  private String saveFileToBucket(String solasCertificateCollection, FileDTO fileDTO) {
    DBObject metaData = new BasicDBObject();

    Document doc = new Document().append("trxType", fileDTO.getTrxType().getValue())
        .append("trxId", fileDTO.getFileName()).append("fileSize", fileDTO.getFileSize());

    metaData.put("trxType", fileDTO.getTrxType());
    metaData.put("trxId", fileDTO.getFileName());
    metaData.put("fileSize", fileDTO.getFileSize());

    metaData.put(fileDTO.getFileName() + "_Info", doc);

    GridFSFile gridFSFile = gridFSRepository.storeFile(fileDTO.getCameraImage(), metaData);

    return fileDTO.getFileName();

  }



}
