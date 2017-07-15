/**
 * 
 */
package com.privasia.scss.express.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.BookingInfoDTO;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.express.service.ExpressService;


/**
 * @author Nadeeshani Senevirathna
 *
 */
@RestController
@RequestMapping("**/express")
public class ExpressController {

  @Autowired
  private ExpressService expressService;

  @RequestMapping(value = "/getbookinginfo/{cardNo}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getBookingInfo(@PathVariable Long cardNo,
      HttpServletRequest request) {

    BookingInfoDTO dto = expressService.getBookingInfo(cardNo);

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<BookingInfoDTO>(HttpStatus.OK, dto),
        HttpStatus.OK);
  }

}
