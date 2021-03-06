/**
 * 
 */
package com.privasia.scss.hpat.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.HpatDto;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.hpat.service.HPABService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/hpat")
public class HPATController {

  @Autowired
  private HPABService hpatService;


  @RequestMapping(value = "/{cardID}/{bookingTypes}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> findEtpHpat4ImpAndExp(@PathVariable Long cardID,
      @PathVariable List<String> bookingTypes) {

    List<HpatDto> dtos = hpatService.findEtpHpab4ImpAndExp(cardID, LocalDateTime.now(), bookingTypes, false);

    if (dtos == null) {
      dtos = new ArrayList<HpatDto>();
    }

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<List<HpatDto>>(HttpStatus.OK, dtos),
        HttpStatus.OK);
  }


  @RequestMapping(value = "/{bookingID}/details", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getEtpHpat4ImpAndExp(@PathVariable String bookingID) {

    TransactionDTO dto = hpatService.getEtpHpab4ImpAndExp(bookingID);

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<TransactionDTO>(HttpStatus.OK, dto),
        HttpStatus.OK);
  }



}
