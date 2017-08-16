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

	public List<ExportContainer> calculateSolasVGM(List<ExportContainer> exportContainers,  boolean initialRequest) {

		boolean fullPresent = exportContainers.stream()
				.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
						expCon.getContainer().getContainerFullOrEmpty()))
				.findAny().isPresent();

		if (fullPresent) {

			ExportContainer firstContainer = exportContainers.stream()
					.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
							expCon.getContainer().getContainerFullOrEmpty()))
					.findFirst().get();

			Optional<ExportContainer> optContainer = exportContainers.stream()
					.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
							expCon.getContainer().getContainerFullOrEmpty())
							&& (!StringUtils.equalsIgnoreCase(firstContainer.getContainer().getContainerNumber(),
									expCon.getContainer().getContainerNumber())))
					.findAny();

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

			int pmWeight = 0;
			int axelWeight = 0;

			if (StringUtils.isNotEmpty(firstContainer.getPmWeight())) {
				pmWeight = Integer.parseInt(firstContainer.getPmWeight());
			} else {
				Optional<SolasWeightConfig> optPM = solasMasterData.stream().filter(solasConfig -> StringUtils
						.equalsIgnoreCase(SolasWeightType.PM.getValue(), solasConfig.getWeightType().getValue()))
						.findFirst();
				pmWeight = optPM
						.orElseThrow(() -> new ResultsNotFoundException("For Solas PM Information not Found ! "))
						.getDefaultValue();
			}

			if (StringUtils.isNotEmpty(firstContainer.getTrailerWeight())) {
				axelWeight = Integer.parseInt(firstContainer.getTrailerWeight());
			} else {
				Optional<SolasWeightConfig> optAxel = solasMasterData.stream().filter(solasConfig -> StringUtils
						.equalsIgnoreCase(SolasWeightType.TRAILER.getValue(), solasConfig.getWeightType().getValue()))
						.findFirst();
				axelWeight = optAxel
						.orElseThrow(() -> new ResultsNotFoundException("For Solas Trailer Information not Found ! "))
						.getDefaultValue();
			}

			int PFAT = pmWeight + axelWeight + fuelWeight + tireWeight;
			
			
			if(firstContainer.getExpWeightBridge() == null || firstContainer.getExpWeightBridge().intValue() == 0) {
				
				calculateWeightBridge(firstContainer, optContainer, PFAT, initialRequest);
				
			}
			calculateTernimalVGM(firstContainer, optContainer, PFAT);

			exportContainers.stream()
					.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
							expCon.getContainer().getContainerFullOrEmpty()))
					.forEach(exportContainer -> {

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

		return exportContainers;

	}

	private void calculateTernimalVGM(ExportContainer firstContainer, Optional<ExportContainer> optContainer,
			int PFAT) {

		firstContainer.setWithinTolerance(false);
		boolean dirctEntryFirst = firstContainer.getSolas().isDirectEntry();

		if (dirctEntryFirst) {
			// we know the ternimal vgm for first container

			if (optContainer.isPresent()) {
				ExportContainer secondContainer = optContainer.get();
				secondContainer.setWithinTolerance(false);
				boolean dirctEntrySecond = secondContainer.getSolas().isDirectEntry();

				if (dirctEntrySecond) {

					// back to back container directEntry true we no need to
					// calculate ternimal vgm
					log.info(firstContainer.getContainer().getContainerNumber()
							+ "is a back to back container directEntry  " + dirctEntryFirst + " ternimanal vgm "
							+ firstContainer.getExpNetWeight());

					log.info(secondContainer.getContainer().getContainerNumber()
							+ "is a back to back container directEntry  " + secondContainer + " ternimanal vgm "
							+ secondContainer.getExpNetWeight());

				} else {
					// first container ternimal vgm we know
					// second container ternimal vgm need to calcuate

					int terminalVGM = secondContainer.getExpWeightBridge() - firstContainer.getExpNetWeight() - PFAT;
					secondContainer.setExpNetWeight(terminalVGM);

				}

			} else {
				// single container directEntry true we no need to calculate
				// ternimal vgm
				log.info(firstContainer.getContainer().getContainerNumber() + "is a single container directEntry  "
						+ dirctEntryFirst + " ternimanal vgm " + firstContainer.getExpNetWeight());
			}

		} else {

			// we have to calculate ternimal vgm for first container

			if (optContainer.isPresent()) {
				ExportContainer secondContainer = optContainer.get();
				secondContainer.setWithinTolerance(false);
				boolean dirctEntrySecond = secondContainer.getSolas().isDirectEntry();

				if (dirctEntrySecond) {

					// back to back container directEntry false first container
					// we no need to calculate ternimal vgm this
					// second container we no need to calculate ternimal vgm
					int terminalVGM = firstContainer.getExpWeightBridge() - secondContainer.getExpNetWeight() - PFAT;
					firstContainer.setExpNetWeight(terminalVGM);

				} else {
					// back to back containers.
					// we need to calculate both ternimal vgm need to calcuate

					int terminalVGM = (firstContainer.getExpWeightBridge() - PFAT) / 2;
					firstContainer.setExpNetWeight(terminalVGM);
					secondContainer.setExpNetWeight(terminalVGM);

				}

			} else {
				// single container directEntry false we need to calculate
				// ternimal vgm for first container
				int terminalVGM = firstContainer.getExpWeightBridge() - PFAT;
				firstContainer.setExpNetWeight(terminalVGM);
			}

		}

	}

	private void calculateWeightBridge(ExportContainer firstContainer, Optional<ExportContainer> optContainer, 
			int PFAT, boolean initialRequest) {
		
		boolean dirctEntryFirst = firstContainer.getSolas().isDirectEntry();

		if (dirctEntryFirst) {
			// we know the ternimal vgm for first container

			if (optContainer.isPresent()) {
				ExportContainer secondContainer = optContainer.get();
				boolean dirctEntrySecond = secondContainer.getSolas().isDirectEntry();

				if (dirctEntrySecond) {
					//back to back containers . ternimal vgm provided to calculate weigh bridge
					int weighBridge = firstContainer.getExpNetWeight() + + secondContainer.getExpNetWeight() + PFAT;
					firstContainer.setExpWeightBridge(weighBridge);
					secondContainer.setExpWeightBridge(weighBridge);

				} else {
					
					// ternimal vgm should be provided to calculate weight bridge
					
					log.info(secondContainer.getContainer().getContainerNumber() + "container directEntry  "
							+ dirctEntrySecond + " ternimanal vgm " + secondContainer.getExpNetWeight());
					
					if(!initialRequest){
						
						throw new BusinessException("container "+secondContainer.getContainer().getContainerNumber()
								+" need to provide ternimanal vgm to calculate weigh bridge");
					}

				}

			} else {
				// single container directEntry true we no need to calculate
				// ternimal vgm can calculate weigh bridge
				int weighBridge = firstContainer.getExpNetWeight() + PFAT;
				firstContainer.setExpWeightBridge(weighBridge);
			}

		} else {

			// ternimal vgm should be provided to calculate weigh bridge
			log.info(firstContainer.getContainer().getContainerNumber() + "container directEntry  "
					+ dirctEntryFirst + " ternimanal vgm " + firstContainer.getExpNetWeight());
			
			if(!initialRequest){
				throw new BusinessException("container "+firstContainer.getContainer().getContainerNumber()
						+" need to provide ternimanal vgm to calculate weigh bridge");
			}
			
		}
		
	}

	private ExportContainer calculateTolerance(ExportContainer exportContainer, int tolerance) {

		log.info("Container " + exportContainer.getContainer().getContainerNumber() + " SolasInstructionType "
				+ exportContainer.getSolas().getSolasInstruction() + " ContainerFullEmpty "
				+ exportContainer.getContainer().getContainerFullOrEmpty());

		BigDecimal tolerancePercentage = new BigDecimal(tolerance);

		if (exportContainer.getSolas().getShipperVGM() == null
				|| Integer.parseInt(exportContainer.getSolas().getShipperVGM()) == 0)
			throw new BusinessException(
					"For Container " + exportContainer.getContainer().getContainerNumber() + " Provided Shipper VGM : "
							+ exportContainer.getSolas().getShipperVGM() + " cannot calculate variance");

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
