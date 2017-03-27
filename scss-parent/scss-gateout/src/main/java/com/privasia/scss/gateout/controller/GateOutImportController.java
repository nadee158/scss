/**
 * 
 */
package com.privasia.scss.gateout.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CancelPickUpDTO;
import com.privasia.scss.common.dto.ConfirmedKioskDTO;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.dto.UpdateSealDTO;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/import")
public class GateOutImportController {
	
	@RequestMapping(value = "/fetchtrx/{cardnumber}/{clientID}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> fetchTrx(@PathVariable("cardnumber") String cardNumber, @PathVariable("clientID") Long clientID) {
		System.out.println("cardNumber : "+cardNumber);
		System.out.println("clientID : "+clientID);
		GateOutMessage gateOutMessage = null;
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateOutMessage>(HttpStatus.OK, gateOutMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/cancelpickup", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> cancelPickUp(@RequestBody CancelPickUpDTO cancelPickUpDTO) {
		GateOutMessage gateOutMessage = null;
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateOutMessage>(HttpStatus.OK, gateOutMessage), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/confirmedkiosk", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> confirmedKiosk(@RequestBody ConfirmedKioskDTO confirmedKioskDTO) {
		GateOutMessage gateOutMessage = null;
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateOutMessage>(HttpStatus.OK, gateOutMessage), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateseal", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> updateseal(@RequestBody UpdateSealDTO updateSealDTO) {
		GateOutMessage gateOutMessage = null;
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateOutMessage>(HttpStatus.OK, gateOutMessage), HttpStatus.OK);
	}

}
