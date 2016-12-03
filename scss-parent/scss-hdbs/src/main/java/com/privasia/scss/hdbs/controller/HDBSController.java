/**
 * 
 */
package com.privasia.scss.hdbs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.WHODDDTO;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.hdbs.service.HDBSService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/hdbs")
public class HDBSController {
	
	@Autowired
	private HDBSService hdbsService;

	@RequestMapping(value = "/findbooking/{cardID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HDBSBkgDetail>> getHDBSBooking(@PathVariable Long cardID, HttpServletRequest request) {
		
		System.out.println("cardID : "+cardID);
		
		//List<HDBSBkgDetail> hdbsDetails = hdbsService.findHDBSBookingDetailByIDList(bkgDetailIDList);
		//return new ResponseEntity<List<HDBSBkgDetail>>(hdbsDetails, HttpStatus.OK);
		
		return null;
	}
	
	@RequestMapping(value = "/validateselection", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> validateBookingSelection(@RequestBody List<String> bkgDetailIDList, HttpServletRequest request) {
		
		String validateMessage = hdbsService.validateSelectedHDBSBookingDetails(bkgDetailIDList);
		
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, validateMessage), HttpStatus.OK);
	}
	
}
