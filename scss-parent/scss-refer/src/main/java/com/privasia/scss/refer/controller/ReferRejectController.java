package com.privasia.scss.refer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.util.UserIpAddressUtil;
import com.privasia.scss.refer.dto.ReferRejectDTO;
import com.privasia.scss.refer.dto.ReferRejectDetailUpdateObjetDto;
import com.privasia.scss.refer.dto.ReferRejectObjetDto;
import com.privasia.scss.refer.dto.ReferRejectUpdateObjetDto;
import com.privasia.scss.refer.service.ReferRejectService;

/**
 * @author nadee158
 *
 */
@RestController
@RequestMapping("**/referreject")
public class ReferRejectController {

  @Autowired
  private ReferRejectService referRejectService;

  @RequestMapping(value = "/referlist/{page}/{pageSize}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getReferRejectList(ModelMap map, @PathVariable int page,
      @PathVariable int pageSize) {
    map = referRejectService.getReferRejectList(map, page, pageSize);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<ModelMap>(HttpStatus.OK, map),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/get/{referId}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getReferRejectById(@PathVariable long referId) {
    
	  ReferRejectDTO referReject = referRejectService.getReferRejectByReferId(referId);
     
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<ReferRejectDTO>(HttpStatus.OK, referReject), HttpStatus.OK);
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> saveReferReject(@RequestBody ReferRejectObjetDto referRejectObjetDto) {

    Long referId = referRejectService.saveReferReject(referRejectObjetDto);

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<Long>(HttpStatus.OK, referId),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> updateReferReject(
      @RequestBody ReferRejectUpdateObjetDto referRejectUpdateObjetDto) {

    String status = referRejectService.updateReferReject(referRejectUpdateObjetDto);

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/updatereferdetail", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> updateLineCodeANdGateInDateForReferRejectDetail(
      @RequestBody ReferRejectDetailUpdateObjetDto dto) {

    String status = referRejectService.updateLineCodeAndGateInDateForReferRejectDetail(dto);

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/{referId}/saveprintreject", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> savePrintReject(HttpServletRequest request,
      @PathVariable("referId") long referId) {
    String ipAddress = UserIpAddressUtil.getUserIp(request);
    String status = referRejectService.savePrintReject(referId, ipAddress);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

}
