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

		Optional<ExportContainer> optFirstContainer = exportContainers.stream()
				.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
						expCon.getContainer().getContainerFullOrEmpty()))
				.findFirst();

		Optional<ExportContainer> optLastContainer = null;

		if (optFirstContainer.isPresent()) {
			optLastContainer = exportContainers.stream()
					.filter(expCon -> (StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
							expCon.getContainer().getContainerFullOrEmpty())
							&& (!(StringUtils.equals(expCon.getContainer().getContainerNumber(),
									optFirstContainer.get().getContainer().getContainerNumber())))))
					.findFirst();
			ExportContainer firstContainer = optFirstContainer.get();
			

			Optional<List<SolasWeightConfig>> optSolasMasterData = solasWeightConfigRepository.findByWeightTypeIn(
					Arrays.asList(SolasWeightType.FUEL, SolasWeightType.TYRE, SolasWeightType.VARIANCE, SolasWeightType.PM, SolasWeightType.TRAILER));

			List<SolasWeightConfig> solasMasterData = optSolasMasterData.orElseThrow(
					() -> new ResultsNotFoundException("SolasApplicable Master data for Fuel, Type, Tolerance not Found ! "));

			Optional<SolasWeightConfig> optFuelWeight = solasMasterData.stream().filter(solasConfig -> StringUtils
					.equalsIgnoreCase(SolasWeightType.FUEL.getValue(), solasConfig.getWeightType().getValue()))
					.findFirst();

			int fuelWeight = optFuelWeight
					.orElseThrow(() -> new ResultsNotFoundException("SolasApplicable data for Fuel not Found ! "))
					.getDefaultValue();

			Optional<SolasWeightConfig> optTyreWeight = solasMasterData.stream().filter(solasConfig -> StringUtils
					.equalsIgnoreCase(SolasWeightType.TYRE.getValue(), solasConfig.getWeightType().getValue()))
					.findFirst();

			int tireWeight = optTyreWeight
					.orElseThrow(() -> new ResultsNotFoundException("SolasApplicable data for Tyre not Found ! "))
					.getDefaultValue();

			Optional<SolasWeightConfig> optTolerance = solasMasterData.stream().filter(solasConfig -> StringUtils
					.equalsIgnoreCase(SolasWeightType.VARIANCE.getValue(), solasConfig.getWeightType().getValue()))
					.findFirst();

			int tolerance = optTolerance
					.orElseThrow(() -> new ResultsNotFoundException("SolasApplicable data for Tolerance not Found ! "))
					.getDefaultValue();
			
			int pmWeight = 0;
			int axelWeight = 0;

			if (StringUtils.isNotEmpty(firstContainer.getPmWeight())) {
				pmWeight = Integer.parseInt(firstContainer.getPmWeight());
			}else{
				Optional<SolasWeightConfig> optPM = solasMasterData.stream().filter(solasConfig -> StringUtils
						.equalsIgnoreCase(SolasWeightType.PM.getValue(), solasConfig.getWeightType().getValue()))
						.findFirst();
				pmWeight = optPM
						.orElseThrow(() -> new ResultsNotFoundException("SolasApplicable data for PM not Found ! "))
						.getDefaultValue();
			}
			
			if (StringUtils.isNotEmpty(firstContainer.getTrailerWeight())) {
				axelWeight = Integer.parseInt(firstContainer.getTrailerWeight());
			}else{
				Optional<SolasWeightConfig> optAxel = solasMasterData.stream().filter(solasConfig -> StringUtils
						.equalsIgnoreCase(SolasWeightType.TRAILER.getValue(), solasConfig.getWeightType().getValue()))
						.findFirst();
				axelWeight = optAxel
						.orElseThrow(() -> new ResultsNotFoundException("SolasApplicable data for Trailer not Found ! "))
						.getDefaultValue();
			}

			firstContainer.setWithinTolerance(false);
			if (optLastContainer != null && optLastContainer.isPresent()) {
				ExportContainer lastContainer = optLastContainer.get();
				lastContainer.setWithinTolerance(false);
				if (firstContainer.getExpWeightBridge() - lastContainer.getExpWeightBridge() == 0) {
					int terminalVGM = firstContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight
							- tireWeight;
					firstContainer.setExpNetWeight(terminalVGM / 2);
					firstContainer.setTireWeight(String.valueOf(tireWeight));
					firstContainer.setFuelWeight(String.valueOf(fuelWeight));

					lastContainer.setExpNetWeight(terminalVGM / 2);
					lastContainer.setTireWeight(String.valueOf(tireWeight));
					lastContainer.setFuelWeight(String.valueOf(fuelWeight));

				} else if (firstContainer.getExpWeightBridge() < lastContainer.getExpWeightBridge()) {

					int terminalVGMC1 = firstContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight
							- tireWeight;
					firstContainer.setExpNetWeight(terminalVGMC1);

					int terminalVGMC2 = lastContainer.getExpWeightBridge() - firstContainer.getExpWeightBridge();
					lastContainer.setExpNetWeight(terminalVGMC2);

				} else {// container 02 lifted

					int terminalVGMC2 = lastContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight
							- tireWeight;
					lastContainer.setExpNetWeight(terminalVGMC2);

					int terminalVGMC1 = firstContainer.getExpWeightBridge() - lastContainer.getExpWeightBridge();
					firstContainer.setExpNetWeight(terminalVGMC1);

				}

			} else {// this is for single container
				int terminalVGM = firstContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight - tireWeight;
				firstContainer.setExpNetWeight(terminalVGM);
				firstContainer.setTireWeight(String.valueOf(tireWeight));
				firstContainer.setFuelWeight(String.valueOf(fuelWeight));
			}

			if (StringUtils.equalsIgnoreCase(firstContainer.getSolas().getSolasInstruction(),
					SolasInstructionType.VGM_INSTRUCTION_SHIPPER.getValue())) {
				calculateTolerance(exportContainers, tolerance);
			}
		} else {
			throw new BusinessException("SolasApplicable Applicable only for Full Containers !");
		}

		return exportContainers;

	}

	public List<ExportContainer> calculateTolerance(List<ExportContainer> exportContainers, int tolerance) {

		BigDecimal tolerancePercentage = new BigDecimal(tolerance);

		exportContainers.stream().filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
				expCon.getContainer().getContainerFullOrEmpty())).forEach(container -> {
					BigDecimal shipperVGM = new BigDecimal(container.getSolas().getShipperVGM()).setScale(2,
							BigDecimal.ROUND_UP);
					if(shipperVGM == null || shipperVGM.intValue() == 0 )
						throw new BusinessException("Provided Shipper VGM : "+shipperVGM.intValue()+" cannot calculate variance");
					BigDecimal terminalVGM = new BigDecimal(container.getExpNetWeight()).setScale(2, BigDecimal.ROUND_UP);
					BigDecimal variance = ((shipperVGM.subtract(terminalVGM)).divide(shipperVGM, 4,
							BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
					container.setCalculatedVariance(String.valueOf(variance));
					
					// checking if in range
					if (variance.compareTo(tolerancePercentage) <= 0
							&& variance.compareTo(tolerancePercentage.negate()) >= 0) {
						container.setWithinTolerance(true);
					} else {
						container.setWithinTolerance(false);
					}
				});

		return exportContainers;

	}

}
