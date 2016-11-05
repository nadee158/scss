/**
 * 
 */
package com.privasia.scss.hpat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.hpat.service.HPATService;


/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("scss/hpat")
public class HPATController {
	
	@Autowired
	private HPATService hpatService;
	
	
	@RequestMapping(value = "/impexp/{cardID}", method = RequestMethod.GET)
    public ResponseEntity<Void> findEtpHpat4ImpAndExp(@PathVariable Long cardID) {
		
		System.out.println(cardID + "cardID");
		
		//hpatService.findEtpHpat4ImpAndExp(cardID, LocalDateTime.now());
		
		return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
