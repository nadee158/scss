/**
 * 
 */
package com.privasia.scss.master.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.core.util.service.CurrentDateTimeService;
import com.privasia.scss.master.service.GlobalSettingService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("scss/masterdata")
public class MasterDataController {
	
	
	@Autowired
	private CurrentDateTimeService currentDateTimeService;
	
	@Autowired
	private GlobalSettingService globalSettingService;
	
	@RequestMapping(value = "serverdate", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getServerDateTime() {
		
		String date = currentDateTimeService.getFormattedCurrentDateAndTime();
		
		System.out.println("date : "+date);
		
		return new ResponseEntity<String>(date, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "customcheck", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isCustomCheckAfterTransaction() {
		
		boolean check = globalSettingService.isCustomCheckBeforeTransaction();
		
		System.out.println("check : "+check);
		
		return new ResponseEntity<Boolean>(check, HttpStatus.OK);
    }

}