/**
 * 
 */
package com.privasia.scss.gatein.express.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.BookingInfoDTO;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateTicketInfoDTO;
import com.privasia.scss.gatein.express.service.ExpressBookingService;
import com.privasia.scss.gatein.express.service.ExpressGatePassService;
import com.privasia.scss.gatein.express.service.ExpressODDService;
import com.privasia.scss.gatein.util.GateInWriteRequestValidator;

/**
 * @author Nadeeshani Senevirathna
 *
 */
@RestController
@RequestMapping("**/express")
public class ExpressController {

	private ExpressBookingService expressBookingService;

	private ExpressGatePassService expressGatePassService;

	private ExpressODDService expressODDService;

	@Autowired
	public void setExpressBookingService(ExpressBookingService expressBookingService) {
		this.expressBookingService = expressBookingService;
	}

	@Autowired
	public void setExpressGatePassService(ExpressGatePassService expressGatePassService) {
		this.expressGatePassService = expressGatePassService;
	}

	@Autowired
	public void setExpressODDService(ExpressODDService expressODDService) {
		this.expressODDService = expressODDService;
	}

	@RequestMapping(value = "/getbookinginfo/{cardNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getBookingInfo(@PathVariable Long cardNo,
			HttpServletRequest request) {

		BookingInfoDTO dto = expressBookingService.getBookingInfo(cardNo);

		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<BookingInfoDTO>(HttpStatus.OK, dto),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/getgateticketinfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getGateTicketInfo(@RequestBody GateInRequest gateInRequest)
			throws BindException {

		GateTicketInfoDTO gateTicketInfoDTO = expressGatePassService.getGateTicketInfo(gateInRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateTicketInfoDTO>(HttpStatus.OK, gateTicketInfoDTO), HttpStatus.OK);
	}

	@RequestMapping(value = "/getgateticketinfoodd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getGateTicketInfoODD(
			@RequestBody GateInWriteRequest gateInWriteRequest) {

		GateTicketInfoDTO gateTicketInfoDTO = expressODDService.getGateTicketInfoODD(gateInWriteRequest);

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<GateTicketInfoDTO>(HttpStatus.OK, gateTicketInfoDTO), HttpStatus.CREATED);
	}

}
