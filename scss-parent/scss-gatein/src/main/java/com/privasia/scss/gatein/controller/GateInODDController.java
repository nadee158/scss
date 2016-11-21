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

import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.hdbs.service.HDBSService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/odd")
public class GateInODDController {

	@Autowired
	private HDBSService hdbsService;

	@RequestMapping(value = "/hdbs/bkgdetails/ids", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HDBSBkgDetail>> gateInImpNormal(@RequestBody List<String> bkgDetailIDList,
			HttpServletRequest request) {
		List<HDBSBkgDetail> hdbsDetails = hdbsService.findHDBSBookingDetailByIDList(bkgDetailIDList);
		return new ResponseEntity<List<HDBSBkgDetail>>(hdbsDetails, HttpStatus.OK);
	}

}