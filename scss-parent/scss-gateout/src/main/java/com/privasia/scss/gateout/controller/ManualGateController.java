/**
 * 
 */
package com.privasia.scss.gateout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.gateout.service.LaneOpenService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/manualgate")
public class ManualGateController {
	
	
	private LaneOpenService laneOpenService;
	
	@Autowired
	public void setLaneOpenService(LaneOpenService laneOpenService) {
		this.laneOpenService = laneOpenService;
	}

	
	@RequestMapping(value = "/open/{openGateSEQ}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, 
															consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> updateOpenGate(@PathVariable Long openGateSEQ) {

		String responseMessage = laneOpenService.updateOpenGate(openGateSEQ);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<String>(HttpStatus.OK, responseMessage), HttpStatus.OK);
	}

}
