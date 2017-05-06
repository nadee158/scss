/**
 * 
 */
package com.privasia.scss.gatein.controller;

import java.util.List;

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
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.gatein.service.ImportExportGateInService;
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
	private ImportExportGateInService importExportGateInService;
	
	@Autowired
	public void setHdbsService(HDBSService hdbsService) {
		this.hdbsService = hdbsService;
	}
	
	@Autowired
	public void setImportExportGateInService(ImportExportGateInService importExportGateInService) {
		this.importExportGateInService = importExportGateInService;
	}

	@RequestMapping(value = "/whodd/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> saveWhodd(@RequestBody GateInWriteRequest gateInWriteRequest) {

		Long generatedId = importExportGateInService.saveODDGateInFo(gateInWriteRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<Long>(HttpStatus.CREATED, generatedId), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/populate/bybkgdetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GateInReponse> populateODD(@RequestBody GateInRequest gateInRequest,
			HttpServletRequest request) {
		GateInReponse gateInResponse = hdbsService.populateODDInfo(gateInRequest);
		return new ResponseEntity<GateInReponse>(gateInResponse, HttpStatus.OK);
	}

}
