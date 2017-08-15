/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.SolasInstructionType;
import com.privasia.scss.common.enums.SolasWeightType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.SolasWeightConfig;
import com.privasia.scss.core.repository.SolasWeightConfigRepository;

/**
 * @author Janaka
 *
 */
@Service("solasService")
public class SolasService {

	private static final Log log = LogFactory.getLog(SolasService.class);

	@Value("${solas.cert.name}")
	private String solasCertName;

	private SolasWeightConfigRepository solasWeightConfigRepository;

	@Autowired
	public void setSolasWeightConfigRepository(SolasWeightConfigRepository solasWeightConfigRepository) {
		this.solasWeightConfigRepository = solasWeightConfigRepository;
	}

	public String generateSolasCertificateId(LocalDateTime gateInOK) throws ParseException {

		StringBuffer buffer = new StringBuffer();
		buffer.append(solasCertName);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		buffer.append(gateInOK.format(formatter));
		return buffer.toString();

	}

	public List<ExportContainer> calculateTerminalVGM(List<ExportContainer> exportContainers) {

		boolean fullPresent = exportContainers.stream()
				.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
						expCon.getContainer().getContainerFullOrEmpty()))
				.findAny().isPresent();

		if (fullPresent) {

			Optional<List<SolasWeightConfig>> optSolasMasterData = solasWeightConfigRepository
					.findByWeightTypeIn(Arrays.asList(SolasWeightType.FUEL, SolasWeightType.TYRE,
							SolasWeightType.VARIANCE, SolasWeightType.PM, SolasWeightType.TRAILER));

			List<SolasWeightConfig> solasMasterData = optSolasMasterData.orElseThrow(
					() -> new ResultsNotFoundException("For Solas Fuel, Type, Tolerance Information not Found ! "));

			Optional<SolasWeightConfig> optFuelWeight = solasMasterData.stream().filter(solasConfig -> StringUtils
					.equalsIgnoreCase(SolasWeightType.FUEL.getValue(), solasConfig.getWeightType().getValue()))
					.findFirst();

			int fuelWeight = optFuelWeight
					.orElseThrow(() -> new ResultsNotFoundException("For Solas data for Fuel Information not Found ! "))
					.getDefaultValue();

			Optional<SolasWeightConfig> optTyreWeight = solasMasterData.stream().filter(solasConfig -> StringUtils
					.equalsIgnoreCase(SolasWeightType.TYRE.getValue(), solasConfig.getWeightType().getValue()))
					.findFirst();

			int tireWeight = optTyreWeight
					.orElseThrow(() -> new ResultsNotFoundException("For Solas data for Tyre Information not Found ! "))
					.getDefaultValue();

			Optional<SolasWeightConfig> optTolerance = solasMasterData.stream().filter(solasConfig -> StringUtils
					.equalsIgnoreCase(SolasWeightType.VARIANCE.getValue(), solasConfig.getWeightType().getValue()))
					.findFirst();

			int tolerance = optTolerance
					.orElseThrow(() -> new ResultsNotFoundException("For Solas Tolerance Information not Found ! "))
					.getDefaultValue();

			exportContainers.stream()
					.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
							expCon.getContainer().getContainerFullOrEmpty()))
					.forEach(exportContainer -> {

						int pmWeight = 0;
						int axelWeight = 0;

						exportContainer.setWithinTolerance(false);
						boolean dirctEntry = exportContainer.getSolas().isDirectEntry();

						if (StringUtils.isNotEmpty(exportContainer.getPmWeight())) {
							pmWeight = Integer.parseInt(exportContainer.getPmWeight());
						} else {
							Optional<SolasWeightConfig> optPM = solasMasterData.stream()
									.filter(solasConfig -> StringUtils.equalsIgnoreCase(SolasWeightType.PM.getValue(),
											solasConfig.getWeightType().getValue()))
									.findFirst();
							pmWeight = optPM
									.orElseThrow(
											() -> new ResultsNotFoundException("For Solas PM Information not Found ! "))
									.getDefaultValue();
						}

						if (StringUtils.isNotEmpty(exportContainer.getTrailerWeight())) {
							axelWeight = Integer.parseInt(exportContainer.getTrailerWeight());
						} else {
							Optional<SolasWeightConfig> optAxel = solasMasterData.stream()
									.filter(solasConfig -> StringUtils.equalsIgnoreCase(
											SolasWeightType.TRAILER.getValue(), solasConfig.getWeightType().getValue()))
									.findFirst();
							axelWeight = optAxel.orElseThrow(
									() -> new ResultsNotFoundException("For Solas Trailer Information not Found ! "))
									.getDefaultValue();
						}

						int PFAT = pmWeight + axelWeight + fuelWeight + tireWeight;

						if (dirctEntry && (exportContainer.getExpWeightBridge() == null 
								|| exportContainer.getExpWeightBridge().intValue() == 0)) {
							// calculate ternimalVGM
							calculateWeightBridge(exportContainer, PFAT);

						}else{
							// calculate ternimalVGM
							calculateTernimalVGM(exportContainer, PFAT);
						}

						exportContainer.setTireWeight(String.valueOf(tireWeight));
						exportContainer.setFuelWeight(String.valueOf(fuelWeight));
						
						if (StringUtils.equalsIgnoreCase(exportContainer.getSolas().getSolasInstruction(),
								SolasInstructionType.VGM_INSTRUCTION_SHIPPER.getValue())) {
							calculateTolerance(exportContainer, tolerance);
						}
					});

		} else {
			throw new BusinessException("SolasApplicable Applicable only for Full Containers !");
		}
		// both ternimal vgm
		// when direct entry true no calculation require why - weightBridge
		// knows, ternimal vgm provided
		// when direct entry false calculation required ternimal VGM
		// both Shipper
		// when direct entry has to be fasle calculation require for ternimal
		// vgm, ( for tollernce ) weightBridge knows
		// when direct entry has to be true no calculation require why -
		// weightBridge knows, ternimal vgm provided
		// one terminal vgm and one shipper vgm
		// for terminal vgm
		// direct entry true no calculation require why - weightBridge knows,
		// ternimal vgm provided
		// direct entry false calculation required ternimal VGM
		// for shipper vgm
		// always direct entry has to be fasle
		// calculation require for ternimal vgm, weightBridge knows

		return exportContainers;

	}

	private ExportContainer calculateTernimalVGM(ExportContainer exportContainer, int PFAT) {
		int terminalVGM = exportContainer.getExpWeightBridge() - PFAT;
		exportContainer.setExpNetWeight(terminalVGM);
		return exportContainer;
	}
	
	private ExportContainer calculateWeightBridge(ExportContainer exportContainer, int PFAT) {
		int weighBridge = exportContainer.getExpNetWeight() + PFAT;
		exportContainer.setExpWeightBridge(weighBridge);
		return exportContainer;
	}

	private ExportContainer calculateTolerance(ExportContainer exportContainer, int tolerance) {

		log.info("Container " + exportContainer.getContainer().getContainerNumber() + " SolasInstructionType "
				+ exportContainer.getSolas().getSolasInstruction() + " ContainerFullEmpty "
				+ exportContainer.getContainer().getContainerFullOrEmpty());

			BigDecimal tolerancePercentage = new BigDecimal(tolerance);

			if (exportContainer.getSolas().getShipperVGM() == null
					|| Integer.parseInt(exportContainer.getSolas().getShipperVGM()) == 0)
				throw new BusinessException("For Container " + exportContainer.getContainer().getContainerNumber()
						+ " Provided Shipper VGM : " + exportContainer.getSolas().getShipperVGM()
						+ " cannot calculate variance");

			BigDecimal shipperVGM = new BigDecimal(exportContainer.getSolas().getShipperVGM()).setScale(2,
					BigDecimal.ROUND_UP);

			BigDecimal terminalVGM = new BigDecimal(exportContainer.getExpNetWeight()).setScale(2, BigDecimal.ROUND_UP);
			BigDecimal variance = ((shipperVGM.subtract(terminalVGM)).divide(shipperVGM, 4, BigDecimal.ROUND_HALF_UP))
					.multiply(new BigDecimal(100));
			exportContainer.setCalculatedVariance(String.valueOf(variance));

			// checking if in range
			if (variance.compareTo(tolerancePercentage) <= 0 && variance.compareTo(tolerancePercentage.negate()) >= 0) {
				exportContainer.setWithinTolerance(true);
			} else {
				exportContainer.setWithinTolerance(false);
			}

		return exportContainer;

	}

}
