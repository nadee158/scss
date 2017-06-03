package com.privasia.scss.gateout.controller;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SolasPassFileDTO;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;
import com.privasia.scss.gateout.service.ExportGateOutService;
import com.privasia.scss.gateout.service.SolasGateOutService;

@RestController
@RequestMapping("**/test")
public class TestController {

	@Autowired
	private CosmosImportRepository cosmosImportRepository;

	@Autowired
	private ExportGateOutService exportGateOutService;

	@Autowired
	private SolasGateOutService solasGateOutService;

	@RequestMapping(value = "/{containerNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ImportContainer> scanCardByCardNo(@PathVariable("containerNo") String containerNo) {
		System.out.println("CAME HERE CONTROLLER :" + containerNo);
		ImportContainer importContainer = new ImportContainer();
		importContainer = cosmosImportRepository.getContainerInfo(importContainer);
		return new ResponseEntity<ImportContainer>(importContainer, HttpStatus.OK);
	}

	@RequestMapping(value = "/solas", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> solas(@RequestBody GateOutWriteRequest gateOutWriteRequest) {

		exportGateOutService.testSolas(gateOutWriteRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, "OK"),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>>  downloadCert() {

		SolasPassFileDTO solasPassFileDTO = new SolasPassFileDTO();
		solasPassFileDTO.setContainer01No("GOBI22222222");
		solasPassFileDTO.setContainer02No("GOBI11111111");
		solasPassFileDTO.setCertificateNo("WPT20170531020341");
		solasPassFileDTO.setGateInOK("04-08-2017 14:18:19");
		solasPassFileDTO.setIssueBy("JANAKA");
		solasPassFileDTO.setWeighStation("B14");
		solasPassFileDTO.setTerminalVGMC1(1500);
		solasPassFileDTO.setTerminalVGMC2(1500);
		
		try {
			solasPassFileDTO = solasGateOutService.generateSolasCertificate(solasPassFileDTO);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(solasPassFileDTO.getCertificate());
			InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(solasPassFileDTO.getCertificate().length);
			return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, "ok"),
					HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

}
