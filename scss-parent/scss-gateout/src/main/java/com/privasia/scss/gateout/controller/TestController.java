package com.privasia.scss.gateout.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;
import com.privasia.scss.gateout.service.ExportGateOutService;

@RestController
@RequestMapping("**/test")
public class TestController {

	@Autowired
	private CosmosImportRepository cosmosImportRepository;
	
	@Autowired
	private ExportGateOutService exportGateOutService;

	@RequestMapping(value = "/{containerNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ImportContainer> scanCardByCardNo(@PathVariable("containerNo") String containerNo) {
		System.out.println("CAME HERE CONTROLLER :" + containerNo);
		ImportContainer importContainer = new ImportContainer();
		importContainer = cosmosImportRepository.getContainerInfo(importContainer, containerNo);
		return new ResponseEntity<ImportContainer>(importContainer, HttpStatus.OK);
	}

	@RequestMapping(value = "/checksolas", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> checkSolas(@RequestBody List<ExportContainer> exportList) {
		
		for (ExportContainer exportContainer : exportList) {
			System.out.println("exportContainer.getExportID :" + exportContainer.getExportID());
		}
		
		exportGateOutService.testSolas(exportList);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
