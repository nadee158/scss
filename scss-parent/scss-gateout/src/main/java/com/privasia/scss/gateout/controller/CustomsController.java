package com.privasia.scss.gateout.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.CustomsDTO;
import com.privasia.scss.gateout.service.CustomsService;

@RestController
@RequestMapping("**/gateout")
public class CustomsController {

	private static Logger logger = Logger.getLogger(CustomsController.class.getName());

	private CustomsService customsService;

	@Autowired
	public void setCustomsService(CustomsService customsService) {
		this.customsService = customsService;
	}

	@RequestMapping(value = "/updatecustoms", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> updateCustoms(@RequestBody CustomsDTO customsDTO)
			throws BindException {

		String result = customsService.updateCustoms(customsDTO);

		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, result),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/checkcustomstatus/{clientID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> checkCustomStatus(@PathVariable Long clientID) {
		String status = customsService.checkCustomStatus(clientID);
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
				HttpStatus.OK);
	}

}
