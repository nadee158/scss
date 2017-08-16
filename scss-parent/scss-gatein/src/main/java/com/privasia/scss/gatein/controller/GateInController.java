package com.privasia.scss.gatein.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateInfo;
import com.privasia.scss.common.dto.ManualPlanDTO;
import com.privasia.scss.gatein.service.ExportGateInService;
import com.privasia.scss.gatein.service.GateInService;
import com.privasia.scss.gatein.service.ImportExportGateInService;
import com.privasia.scss.gatein.util.GateInRequestValidator;
import com.privasia.scss.gatein.util.GateInWriteRequestValidator;

@RestController
@RequestMapping("**/gatein")
public class GateInController {

	private static Logger logger = Logger.getLogger(GateInController.class.getName());

	private GateInService gateInService;

	private ImportExportGateInService importExportGateInService;

	private GateInRequestValidator gateInRequestValidator;

	private GateInWriteRequestValidator gateInWriteRequestValidator;

	private ExportGateInService exportGateInService;

	@Autowired
	public void setGateInService(GateInService gateInService) {
		this.gateInService = gateInService;
	}

	@Autowired
	public void setImportExportGateInService(ImportExportGateInService importExportGateInService) {
		this.importExportGateInService = importExportGateInService;
	}

	@Autowired
	public void setGateInRequestValidator(GateInRequestValidator gateInRequestValidator) {
		this.gateInRequestValidator = gateInRequestValidator;
	}

	@Autowired
	public void setGateInWriteRequestValidator(GateInWriteRequestValidator gateInWriteRequestValidator) {
		this.gateInWriteRequestValidator = gateInWriteRequestValidator;
	}

	@Autowired
	public void setExportGateInService(ExportGateInService exportGateInService) {
		this.exportGateInService = exportGateInService;
	}

	@RequestMapping(value = "/allow", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> gateInImpNormal(@RequestBody GateInfo gateInfo) {

		gateInfo = gateInService.checkGateInAllow(gateInfo);
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateInfo>(HttpStatus.OK, gateInfo),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/populategatein", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> populateGateIn(@Valid @RequestBody GateInRequest gateInRequest,
			BindingResult bindingResult) throws BindException {
		gateInRequestValidator.validate(gateInRequest, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		GateInResponse gateInResponse = importExportGateInService.populateGateIn(gateInRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateInResponse>(HttpStatus.OK, gateInResponse), HttpStatus.OK);
	}

	@RequestMapping(value = "/savegateininfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> saveGateInInfo(
			@Valid @RequestBody GateInWriteRequest gateInWriteRequest, BindingResult bindingResult)
			throws BindException {
		gateInWriteRequestValidator.validate(gateInWriteRequest, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		GateInResponse gateInWriteReponse = importExportGateInService.saveGateInInfo(gateInWriteRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateInResponse>(HttpStatus.OK, gateInWriteReponse), HttpStatus.OK);
	}

	@RequestMapping(value = "/manualplaninfo/{exportID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getManualplaninfo(@PathVariable long exportID)
			throws BindException {

		ManualPlanDTO manualPlanInfo = exportGateInService.fetchManualplaninfo(exportID);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<ManualPlanDTO>(HttpStatus.OK, manualPlanInfo), HttpStatus.OK);
	}

}
