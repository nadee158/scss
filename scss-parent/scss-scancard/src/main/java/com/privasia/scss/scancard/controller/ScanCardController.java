/**
 * 
 */
package com.privasia.scss.scancard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("/scss/scancard")
public class ScanCardController {
  
  
	
	@RequestMapping(value = "/{cardNo}", method = RequestMethod.GET)
    public ResponseEntity<String> scanCard(@PathVariable String cardNo) {
	
		
		return new ResponseEntity<String>(cardNo, HttpStatus.OK);
    }
	
//	for above query
//	/{cardNo}/scuinfo
//
//	new method to get companycode - select query
//	/{cardNo}/compnay/code

}
