/**
 * 
 */
package com.privasia.scss.master.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.core.model.ODDExportReason;
import com.privasia.scss.core.model.ODDImportReason;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.util.service.CurrentDateTimeService;
import com.privasia.scss.master.dto.ReferReasonDTO;
import com.privasia.scss.master.service.GlobalSettingService;
import com.privasia.scss.master.service.ODDMasterDataService;
import com.privasia.scss.master.service.ReferReasonService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/masterdata")
public class MasterDataController {

  @Autowired
  private CurrentDateTimeService currentDateTimeService;

  @Autowired
  private GlobalSettingService globalSettingService;

  @Autowired
  private ReferReasonService referReasonService;

  @Autowired
  private ODDMasterDataService oddMasterDataService;

  @RequestMapping(value = "serverdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getServerDateTime() {

    String date = currentDateTimeService.getFormattedCurrentDateAndTime();

    System.out.println("date : " + date);

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, date),
        HttpStatus.OK);
  }

  @RequestMapping(value = "customcheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> isCustomCheckAfterTransaction() {

    boolean check = globalSettingService.isCustomCheckBeforeTransaction();

    System.out.println("check : " + check);

    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<Boolean>(HttpStatus.OK, check),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/referreason", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getReferReasonList() {
    System.out.println("dtoList before:");
    List<ReferReasonDTO> dtoList = referReasonService.findAllReferReason();
    System.out.println("dtoList :" + dtoList);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ReferReasonDTO>>(HttpStatus.OK, dtoList), HttpStatus.OK);
  }

  @RequestMapping(value = "/oddlocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getODDActionLocation() {

    List<ODDLocation> locationList = oddMasterDataService.findActiveODDLocation();

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ODDLocation>>(HttpStatus.OK, locationList), HttpStatus.OK);
  }

  @RequestMapping(value = "/oddexportreason", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getAllExportReason() {

    List<ODDExportReason> oddExportReason = oddMasterDataService.findAllExporteason();

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ODDExportReason>>(HttpStatus.OK, oddExportReason), HttpStatus.OK);
  }

  @RequestMapping(value = "/oddimportreason", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getAllImportReason() {

    List<ODDImportReason> oddImportReason = oddMasterDataService.findAllImportReason();

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ODDImportReason>>(HttpStatus.OK, oddImportReason), HttpStatus.OK);
  }

}
