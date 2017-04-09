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
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
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

		Optional<ExportContainer> optLastContainer = exportContainers.stream()
				.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
						expCon.getContainer().getContainerFullOrEmpty()))
				.reduce((a, b) -> b);

		if (optFirstContainer.isPresent()) {
			ExportContainer firstContainer = optFirstContainer.get();
			int pmWeight = Integer.parseInt(firstContainer.getPmWeight());
			int axelWeight =  Integer.parseInt(firstContainer.getTrailerWeight());
			
			Optional<List<SolasWeightConfig>> optSolasMasterData = 
					solasWeightConfigRepository.findByWeightTypeIn(Arrays.asList(SolasWeightType.FUEL, SolasWeightType.TYRE, SolasWeightType.VARIANCE));
			
			List<SolasWeightConfig> solasMasterData = optSolasMasterData.orElseThrow(
			          () -> new ResultsNotFoundException("Solas Master data for Fuel, Type, Tolerance not Found ! "));
			
			Optional<SolasWeightConfig> optFuelWeight = solasMasterData.stream()
					.filter(solasConfig -> StringUtils.equalsIgnoreCase(SolasWeightType.FUEL.getValue(),
							solasConfig.getWeightType().getValue()))
					.findFirst();
			
			int fuelWeight = optFuelWeight.orElseThrow(
			          () -> new ResultsNotFoundException("Solas data for Fuel not Found ! ")).getDefaultValue();
			
			Optional<SolasWeightConfig> optTyreWeight = solasMasterData.stream()
					.filter(solasConfig -> StringUtils.equalsIgnoreCase(SolasWeightType.TYRE.getValue(),
							solasConfig.getWeightType().getValue()))
					.findFirst();
			
			int tireWeight = optTyreWeight.orElseThrow(
			          () -> new ResultsNotFoundException("Solas data for Tyre not Found ! ")).getDefaultValue();
			
			Optional<SolasWeightConfig> optTolerance = solasMasterData.stream()
					.filter(solasConfig -> StringUtils.equalsIgnoreCase(SolasWeightType.VARIANCE.getValue(),
							solasConfig.getWeightType().getValue()))
					.findFirst();
			
			int tolerance = optTolerance.orElseThrow(
			          () -> new ResultsNotFoundException("Solas data for Tolerance not Found ! ")).getDefaultValue();
		
			
			firstContainer.setWithinTolerance(false);
			if (optLastContainer.isPresent()) {
				ExportContainer lastContainer = optLastContainer.get();
				lastContainer.setWithinTolerance(false);
				if (firstContainer.getExpWeightBridge() - lastContainer.getExpWeightBridge() == 0) { 
					int terminalVGM = firstContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight
							- tireWeight;
					firstContainer.setNetWeight(terminalVGM / 2);
					firstContainer.setTireWeight(String.valueOf(tireWeight));
					firstContainer.setFuelWeight(String.valueOf(fuelWeight));

					lastContainer.setNetWeight(terminalVGM / 2);
					lastContainer.setTireWeight(String.valueOf(tireWeight));
					lastContainer.setFuelWeight(String.valueOf(fuelWeight));

				} else if (firstContainer.getExpWeightBridge() < lastContainer.getExpWeightBridge()) {

					int terminalVGMC1 = firstContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight
							- tireWeight;
					firstContainer.setNetWeight(terminalVGMC1);

					int terminalVGMC2 = lastContainer.getExpWeightBridge() - firstContainer.getExpWeightBridge();
					lastContainer.setNetWeight(terminalVGMC2);

				} else {// container 02 lifted

					int terminalVGMC2 = lastContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight
							- tireWeight;
					lastContainer.setNetWeight(terminalVGMC2);

					int terminalVGMC1 = firstContainer.getExpWeightBridge() - lastContainer.getExpWeightBridge();
					firstContainer.setNetWeight(terminalVGMC1);

				}

			} else {// this is for single container
				int terminalVGM = firstContainer.getExpWeightBridge() - pmWeight - axelWeight - fuelWeight - tireWeight;
				firstContainer.setNetWeight(terminalVGM);
				firstContainer.setTireWeight(String.valueOf(tireWeight));
				firstContainer.setFuelWeight(String.valueOf(fuelWeight));
			}

			if (StringUtils.equalsIgnoreCase(firstContainer.getSolas().getSolasInstruction(),
					SolasInstructionType.VGM_INSTRUCTION_SHIPPER.getValue())) {
					calculateTolerance(exportContainers, tolerance);
			} 
		} else {
			throw new BusinessException("Solas Applicable only for Full Containers !");
		}

		return exportContainers;

	}

	public List<ExportContainer> calculateTolerance(List<ExportContainer> exportContainers, int tolerance) {

		BigDecimal tolerancePercentage = new BigDecimal(tolerance);
		
		exportContainers.forEach(container->{
			BigDecimal shipperVGM = new BigDecimal(container.getSolas().getShipperVGM()).setScale(2, BigDecimal.ROUND_UP);
			BigDecimal terminalVGM = new BigDecimal(container.getNetWeight()).setScale(2, BigDecimal.ROUND_UP);
			BigDecimal variance = ((shipperVGM.subtract(terminalVGM)).divide(shipperVGM, 4, BigDecimal.ROUND_HALF_UP))
					.multiply(new BigDecimal(100));
			container.setVariance(String.valueOf(variance));
			
			// checking if in range
			if (variance.compareTo(tolerancePercentage) <= 0 && variance.compareTo(tolerancePercentage.negate()) >= 0) {
				container.setWithinTolerance(true);
			} else {
				container.setWithinTolerance(false);
			}
		});

		return exportContainers;

	}

}
