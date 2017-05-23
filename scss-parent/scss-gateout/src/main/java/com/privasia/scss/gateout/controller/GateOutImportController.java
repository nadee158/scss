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
import com.privasia.scss.common.dto.CancelPickUpDTO;
import com.privasia.scss.common.dto.ConfirmedKioskDTO;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.UpdateSealDTO;
import com.privasia.scss.gateout.service.ImportGateOutService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/import")
public class GateOutImportController {
	
	private ImportGateOutService importGateOutService;
	
	@Autowired
	public void setImportGateOutService(ImportGateOutService importGateOutService) {
		this.importGateOutService = importGateOutService;
	}

	@RequestMapping(value = "/cancelpickup", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> cancelPickUp(@RequestBody CancelPickUpDTO cancelPickUpDTO) {
		String response = importGateOutService.cancelPickUp(cancelPickUpDTO);
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, response), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/confirmedkiosk", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> confirmedKiosk(@RequestBody ConfirmedKioskDTO confirmedKioskDTO) {
		String response = importGateOutService.confirmedKioskTransaction(confirmedKioskDTO);
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, response), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateseal", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> updateseal(@RequestBody UpdateSealDTO updateSealDTO) {
		String response = importGateOutService.updateSeal(updateSealDTO);
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, response), HttpStatus.OK);
	}

}
