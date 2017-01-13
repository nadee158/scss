/**
 * 
 */
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
import com.privasia.scss.gateout.service.GateOutODDService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/odd")
public class GateOutODDController {

	private GateOutODDService gateOutODDService;

	@Autowired
	public void setGateOutODDService(GateOutODDService gateOutODDService) {
		this.gateOutODDService = gateOutODDService;
	}

	@RequestMapping(value = "/validatecontainer", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, 
															consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> validateContainers(@RequestBody ContainerValidationInfo validationInfo) {
		System.out.println(validationInfo.getContainerNo1());
		System.out.println(validationInfo.getContainerNo2());
		validationInfo = gateOutODDService.validateODDContainers(validationInfo);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<ContainerValidationInfo>(HttpStatus.OK, validationInfo), HttpStatus.OK);
	}

	

}
