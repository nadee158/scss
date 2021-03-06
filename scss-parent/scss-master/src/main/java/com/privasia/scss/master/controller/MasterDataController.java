/**
 * 
 */
package com.privasia.scss.master.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.DamageCodeDTO;
import com.privasia.scss.common.dto.ReferReasonDTO;
import com.privasia.scss.common.dto.SolasWeightConfigDTO;
import com.privasia.scss.core.model.ODDExportReason;
import com.privasia.scss.core.model.ODDImportReason;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.util.service.CurrentDateTimeService;
import com.privasia.scss.master.service.ClientMasterDataService;
import com.privasia.scss.master.service.DamageCodeService;
import com.privasia.scss.master.service.GlobalSettingService;
import com.privasia.scss.master.service.ODDMasterDataService;
import com.privasia.scss.master.service.ReferReasonService;
import com.privasia.scss.master.service.SolasWeightConfigService;

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

	@Autowired
	private ClientMasterDataService clientMasterDataService;

	@Autowired
	private DamageCodeService damageCodeService;

	@Autowired
	private SolasWeightConfigService solasWeightConfigService;

	@RequestMapping(value = "serverdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getServerDateTime() {

		String date = currentDateTimeService.getFormattedCurrentDateAndTime();

		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, date),
				HttpStatus.OK);
	}

	@RequestMapping(value = "customcheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> isCustomCheckAfterTransaction() {

		boolean check = globalSettingService.isCustomCheckBeforeTransaction();

		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<Boolean>(HttpStatus.OK, check),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/referreason", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getReferReasonList() {
		List<ReferReasonDTO> dtoList = referReasonService.findAllReferReason();
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<List<ReferReasonDTO>>(HttpStatus.OK, dtoList), HttpStatus.OK);
	}

	@RequestMapping(value = "/oddlocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getODDActionLocation() {

		List<ODDLocation> locationList = oddMasterDataService.findActiveODDLocation();

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<List<ODDLocation>>(HttpStatus.OK, locationList), HttpStatus.OK);
	}

	@RequestMapping(value = "/oddexportreason", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getAllExportReason() {

		List<ODDExportReason> oddExportReason = oddMasterDataService.findAllExporteason();

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<List<ODDExportReason>>(HttpStatus.OK, oddExportReason), HttpStatus.OK);
	}

	@RequestMapping(value = "/oddimportreason", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getAllImportReason() {

		List<ODDImportReason> oddImportReason = oddMasterDataService.findAllImportReason();

		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<List<ODDImportReason>>(HttpStatus.OK, oddImportReason), HttpStatus.OK);
	}

	@RequestMapping(value = "client/bywebip", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getClientInfoByWebIP(@RequestBody Map<String, String> json) {
		ClientDTO clientDTO = clientMasterDataService.getClientByWebIP(json.get("ipAddress"));
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<ClientDTO>(HttpStatus.OK, clientDTO), HttpStatus.OK);

	}

	@RequestMapping(value = "client/byid/{clientID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getClientInfoByID(@PathVariable Long clientID) {
		ClientDTO clientDTO = clientMasterDataService.getClientByID(clientID);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<ClientDTO>(HttpStatus.OK, clientDTO), HttpStatus.OK);

	}

	@RequestMapping(value = "/damagecodelist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getDamageList() {
		List<DamageCodeDTO> damageCodeDTOList = damageCodeService.getDamageList();
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<List<DamageCodeDTO>>(HttpStatus.OK, damageCodeDTOList), HttpStatus.OK);

	}

	@RequestMapping(value = "/solasmasterconfig/byweighttype/weighttype/weighttypesize", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getSolasMasterConfig(@PathVariable boolean byWeightType,
			@PathVariable String weightType, @PathVariable int weightTypeSize) {
		Map<String, SolasWeightConfigDTO> map = solasWeightConfigService.fetchSolasWeightConfig(byWeightType,
				weightType, weightTypeSize);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<Map<String, SolasWeightConfigDTO>>(HttpStatus.OK, map), HttpStatus.OK);
	}

	@RequestMapping(value = "/solasmasterconfig", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> getAllSolasMasterConfig() {
		Map<String, SolasWeightConfigDTO> map = solasWeightConfigService.fetchSolasWeightConfig(false, null, 0);
		return new CustomResponseEntity<ApiResponseObject<?>>(
				new ApiResponseObject<Map<String, SolasWeightConfigDTO>>(HttpStatus.OK, map), HttpStatus.OK);
	}

	@RequestMapping(value = "maingatecustomcheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CustomResponseEntity<ApiResponseObject<?>> mainGateCustomCheck() {

		boolean check = globalSettingService.mainGateCustomCheck();

		return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<Boolean>(HttpStatus.OK, check),
				HttpStatus.OK);
	}

}
