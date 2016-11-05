/**
 * 
 */
package com.privasia.scss.hpat.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.hpat.dto.HpatDto;
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


  @RequestMapping(value = "/impexp/{cardID}/{bookingTypes}/{impExpScreen}/{menuId}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<HpatDto>> findEtpHpat4ImpAndExp(@PathVariable Long cardID,
      @PathVariable List<String> bookingTypes, @PathVariable String impExpScreen, @PathVariable String menuId) {

    System.out.println(cardID + "cardID");
    System.out.println(bookingTypes + "bookingTypes");
    System.out.println(impExpScreen + "impExpScreen");
    System.out.println(menuId + "menuId");

    List<HpatDto> dtos = hpatService.findHpats(cardID, LocalDateTime.now(), bookingTypes, impExpScreen, menuId);

    return new ResponseEntity<List<HpatDto>>(dtos, HttpStatus.OK);
  }

}
