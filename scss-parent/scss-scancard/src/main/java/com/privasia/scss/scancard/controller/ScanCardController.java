/**
 * 
 */
package com.privasia.scss.scancard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.SmartCardUserDTO;
import com.privasia.scss.scancard.dto.CardValidationDto;
import com.privasia.scss.scancard.service.CardService;
import com.privasia.scss.scancard.service.CardValidationService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/scancard")
public class ScanCardController {

	@Autowired
	private CardService cardService;

	@Autowired
	private CardValidationService cardValidationService;

	@RequestMapping(value = "/{cardNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> scanCardByCardNo(@PathVariable("cardNo") String cardNo) {
		CardValidationDto cardValidation = cardValidationService.validateCard(cardNo);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<CardValidationDto>(HttpStatus.OK, cardValidation), HttpStatus.OK);
	}

	@RequestMapping(value = "/cardid/{cardID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> validateCardByCardId(@PathVariable("cardID") Long cardID) {
		CardValidationDto cardValidation = cardValidationService.validateCard(cardID);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<CardValidationDto>(HttpStatus.OK, cardValidation), HttpStatus.OK);
	}

	@RequestMapping(value = "/cardid/{cardID}/scuinfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> selectSCUInfo(@PathVariable("cardID") Long cardID,
			HttpServletRequest request) {
		SmartCardUserDTO dto = cardService.selectSCUInfo(cardID, null, request);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<SmartCardUserDTO>(HttpStatus.OK, dto), HttpStatus.OK);
	}

	@RequestMapping(value = "/cardno/{cardNo}/scuinfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> selectSCUInfoByCardId(@PathVariable("cardNo") Long cardNo,
			HttpServletRequest request) {
		SmartCardUserDTO dto = cardService.selectSCUInfo(null, cardNo, request);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<SmartCardUserDTO>(HttpStatus.OK, dto), HttpStatus.OK);
	}

	@RequestMapping(value = "/cardno/{cardNo}/compnay/code", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> selectCompanyCode(@PathVariable("cardNo") String cardNo) {
		String companyCode = cardService.selectCompanyCode(cardNo);
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, companyCode),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/cardid/{cardID}/compnay/code", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> selectCompanyCodeByCardId(@PathVariable("cardID") Long cardID) {
		String companyCode = cardService.selectCompanyCode(cardID);
		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, companyCode),
				HttpStatus.OK);
	}

}
