package com.privasia.scss.gateout.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.BoothTransactionInfo;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.enums.CollectionType;
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.gateout.service.FileService;
import com.privasia.scss.gateout.service.ImportExportGateOutService;
import com.privasia.scss.gateout.util.GateOutRequestValidator;
import com.privasia.scss.gateout.util.GateOutWriteRequestValidator;

@RestController
@RequestMapping("**/gateout")
public class GateOutController {

	private static Logger logger = Logger.getLogger(GateOutController.class.getName());

	private ImportExportGateOutService importExportGateOutService;

	private GateOutRequestValidator gateOutRequestValidator;

	private GateOutWriteRequestValidator gateOutWriteRequestValidator;
	
	private FileService fileService;
	
	@Autowired
	public void setImportExportGateOutService(ImportExportGateOutService importExportGateOutService) {
		this.importExportGateOutService = importExportGateOutService;
	}
	
	@Autowired
	public void setGateOutRequestValidator(GateOutRequestValidator gateOutRequestValidator) {
		this.gateOutRequestValidator = gateOutRequestValidator;
	}
	
	@Autowired
	public void setGateOutWriteRequestValidator(GateOutWriteRequestValidator gateOutWriteRequestValidator) {
		this.gateOutWriteRequestValidator = gateOutWriteRequestValidator;
	}
	
	@Autowired
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@RequestMapping(value = "/populategateout", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> populateGateOut(@Valid @RequestBody GateOutRequest gateOutRequest,
			BindingResult bindingResult) throws BindException {
		gateOutRequestValidator.validate(gateOutRequest, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		GateOutReponse gateOutReponse = importExportGateOutService.populateGateOut(gateOutRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateOutReponse>(HttpStatus.OK, gateOutReponse), HttpStatus.OK);
	}

	@RequestMapping(value = "/savegateoutinfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> saveGateOutOutfo(
			@Valid @RequestBody GateOutWriteRequest gateOutWriteRequest, BindingResult bindingResult)
			throws BindException {
		gateOutWriteRequestValidator.validate(gateOutWriteRequest, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		GateOutReponse gateOutWriteReponse = importExportGateOutService.saveGateOutInfo(gateOutWriteRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateOutReponse>(HttpStatus.OK, gateOutWriteReponse), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save/transactionslip", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> saveTransactionSlip( @RequestBody FileDTO fileDTO) throws IOException {
		
		String response = fileService.saveFile(fileDTO, CollectionType.PDF_FILE_COLLECTION);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<String>(HttpStatus.CREATED, response), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/populatetranx", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> populateSavedTransactionInFot(@RequestBody BoothTransactionInfo boothTransactionInfo) {
		
		GateOutReponse gateOutReponse = importExportGateOutService.populateTransaction(boothTransactionInfo);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateOutReponse>(HttpStatus.OK, gateOutReponse), HttpStatus.OK);
	}

}
