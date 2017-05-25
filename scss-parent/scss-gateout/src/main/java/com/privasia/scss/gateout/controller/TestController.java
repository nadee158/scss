package com.privasia.scss.gateout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;

@RestController
@RequestMapping("**/test")
public class TestController {

	@Autowired
	private CosmosImportRepository cosmosImportRepository;
	

	@RequestMapping(value = "/{containerNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ImportContainer> scanCardByCardNo(@PathVariable("containerNo") String containerNo) {
		System.out.println("CAME HERE CONTROLLER :" + containerNo);
		ImportContainer importContainer = new ImportContainer();
		importContainer = cosmosImportRepository.getContainerInfo(importContainer);
		return new ResponseEntity<ImportContainer>(importContainer, HttpStatus.OK);
	}

}
