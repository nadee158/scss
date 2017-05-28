/**
 * 
 */
package com.privasia.scss.gatein.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.gatein.odd.service.ODDGateInService;
import com.privasia.scss.hdbs.service.HDBSService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/gatein/odd")
public class GateInODDController {

	@Autowired
	private HDBSService hdbsService;

	@Autowired
	private ODDGateInService oddGateInService;
	
	@Autowired
	public void setHdbsService(HDBSService hdbsService) {
		this.hdbsService = hdbsService;
	}
	
	@Autowired
	public void setOddGateInService(ODDGateInService oddGateInService) {
		this.oddGateInService = oddGateInService;
	}
	
	@RequestMapping(value = "/whodd/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> saveWhodd(@RequestBody GateInWriteRequest gateInWriteRequest) {

		GateInReponse gateInReponse = oddGateInService.saveODDGateInInFo(gateInWriteRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateInReponse>(HttpStatus.CREATED, gateInReponse), HttpStatus.CREATED);
	}

	

	@RequestMapping(value = "/populate/bybkgdetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GateInReponse> populateODD(@RequestBody GateInRequest gateInRequest, 
			HttpServletRequest request) {
		GateInReponse gateInResponse = hdbsService.populateGateInODD(gateInRequest);
		return new ResponseEntity<GateInReponse>(gateInResponse, HttpStatus.OK);
	}

}
