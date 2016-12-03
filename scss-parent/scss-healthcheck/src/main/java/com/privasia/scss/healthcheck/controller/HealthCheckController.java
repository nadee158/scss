/**
 * 
 */
package com.privasia.scss.healthcheck.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.HealthCheckInfoDTO;
import com.privasia.scss.healthcheck.service.HealthCheckService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/healthcheck")
public class HealthCheckController {

  @Autowired
  private HealthCheckService healthCheckService;

  @RequestMapping(value = "/getinfo/{pageno}/{pagesize}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getHealthCheckInfo(@PathVariable Integer pageNo,
      @PathVariable Integer pagesize, HttpServletRequest request) {

    System.out.println("pageNo : " + pageNo);
    System.out.println("pagesize : " + pagesize);

    List<HealthCheckInfoDTO> list = healthCheckService.getHealthCheckInfo(pageNo, pagesize);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<HealthCheckInfoDTO>>(HttpStatus.OK, list), HttpStatus.OK);
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> saveReferReject(
      @RequestBody HealthCheckInfoDTO healthCheckInfoDTO) {

    Map<String, String> result = healthCheckService.saveKioskHealthCheckInfo(healthCheckInfoDTO);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<Map<String, String>>(HttpStatus.CREATED, result), HttpStatus.CREATED);
  }

}
