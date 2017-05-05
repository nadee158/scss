/**
 * 
 */
package com.privasia.scss.hdbs.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.HDBSBkgGridDTO;
import com.privasia.scss.hdbs.service.HDBSService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/hdbs")
public class HDBSController {

	private HDBSService hdbsService;

	@Autowired
	public void setHdbsService(HDBSService hdbsService) {
		this.hdbsService = hdbsService;
	}

	@RequestMapping(value = "/findbooking/{cardID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getHDBSBooking(@PathVariable Long cardID,
			HttpServletRequest request) {

		System.out.println("cardID : " + cardID);

		HDBSBkgGridDTO hdbsBkgGridDTO = hdbsService.findHDBSBookingDetailByCard(cardID);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<HDBSBkgGridDTO>(HttpStatus.OK, hdbsBkgGridDTO), HttpStatus.OK);
	}

	@RequestMapping(value = "/validateselection", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> validateBookingSelection(
			@RequestBody HDBSBkgGridDTO bkgDetailGridDTO, HttpServletRequest request) {

		String validateMessage = hdbsService.validateSelectedHDBSBookingDetails(bkgDetailGridDTO);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<String>(HttpStatus.OK, validateMessage), HttpStatus.OK);
	}

}
