package com.privasia.scss.refer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.refer.dto.ReferRejectDetailUpdateObjetDto;
import com.privasia.scss.refer.dto.ReferRejectListDto;
import com.privasia.scss.refer.dto.ReferRejectObjetDto;
import com.privasia.scss.refer.dto.ReferRejectUpdateObjetDto;
import com.privasia.scss.refer.service.ReferRejectService;

/**
 * @author nadee158
 *
 */
@RestController
@RequestMapping("scss/refer")
public class ReferRejectController {

  @Autowired
  private ReferRejectService referRejectService;

  @RequestMapping(value = "/referrejectList/{statusCode}/{page}/{pageSize}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<ReferRejectListDto>> getReferRejectList(@PathVariable String statusCode,
      @PathVariable int page, @PathVariable int pageSize) {

    List<ReferRejectListDto> list = referRejectService.getReferRejectList(statusCode, page, pageSize);

    return new ResponseEntity<List<ReferRejectListDto>>(list, HttpStatus.OK);
  }

  @RequestMapping(value = "/referreject/{referId}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ReferRejectListDto> getReferRejectById(@PathVariable long referId) {

    ReferRejectListDto referRejectListDto = referRejectService.getReferRejectByReferId(referId);

    return new ResponseEntity<ReferRejectListDto>(referRejectListDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/savereferreject", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> saveReferReject(@RequestBody ReferRejectObjetDto referRejectObjetDto) {

    String status = referRejectService.saveReferReject(referRejectObjetDto);

    return new ResponseEntity<String>(status, HttpStatus.OK);
  }

  @RequestMapping(value = "/updatereferreject", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> updateReferReject(@RequestBody ReferRejectUpdateObjetDto referRejectUpdateObjetDto) {

    String status = referRejectService.updateReferReject(referRejectUpdateObjetDto);

    return new ResponseEntity<String>(status, HttpStatus.OK);
  }

  @RequestMapping(value = "/updatereferrejectdetaillinecodegateindate", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> updateLineCodeANdGateInDateForReferRejectDetail(
      @RequestBody ReferRejectDetailUpdateObjetDto dto) {

    String status = referRejectService.updateLineCodeAndGateInDateForReferRejectDetail(dto);

    return new ResponseEntity<String>(status, HttpStatus.OK);
  }

}
