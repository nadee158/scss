
package com.privasia.scss.gateout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.gateout.whodd.service.ODDGateOutService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/odd")
public class GateOutODDController {

	private ODDGateOutService oddGateOutService;

	@Autowired
	public void setOddGateOutService(ODDGateOutService oddGateOutService) {
		this.oddGateOutService = oddGateOutService;
	}

	@RequestMapping(value = "/validatecontainer", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> validateContainers(
			@RequestBody ContainerValidationInfo validationInfo) {
		validationInfo = oddGateOutService.validateODDContainers(validationInfo);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<ContainerValidationInfo>(HttpStatus.OK, validationInfo), HttpStatus.OK);
	}

	@RequestMapping(value = "/whodd/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> saveWhodd(@RequestBody GateOutWriteRequest gateOutWriteRequest) {

		GateOutReponse gateOutReponse = oddGateOutService.saveODDGateOutInFo(gateOutWriteRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateOutReponse>(HttpStatus.CREATED, gateOutReponse), HttpStatus.OK);
	}

}
