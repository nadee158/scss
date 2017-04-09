/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.core.exception.BusinessException;


/**
 * @author Janaka
 *
 */
@Service("solasService")
public class SolasService {
	
	@Value("${solas.cert.name}")
	private String solasCertName;

	public String generateSolasCertificateId(LocalDateTime gateInOK) throws ParseException {

		StringBuffer buffer = new StringBuffer();
		buffer.append(solasCertName);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		buffer.append(gateInOK.format(formatter));
		return buffer.toString();

	}
	
	public List<ExportContainer>  calculateTerminalVGM(List<ExportContainer> exportContainers) {
		
		Set<ExportContainer> fullContainers =  exportContainers.stream()
				.filter(expCon -> StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
						expCon.getContainer().getContainerFullOrEmpty()))
				.collect(Collectors.toSet());
		
		if(!fullContainers.isEmpty()){
			
			int pmWeight = 4000;
			int axelWeight = 4000;
			int fuelWeight = 4000;
			int tireWeight = 4000;
			int variance = 5;
			
			if(fullContainers.size()==1){//this is for single container
				fullContainers.forEach(singleContainer ->{
					int terminalVGM = singleContainer.getExpWeightBridge() - pmWeight -axelWeight - fuelWeight - tireWeight;
					singleContainer.setNetWeight(terminalVGM); //After that need to set
					exportContainers.stream().findFirst().get().setNetWeight(terminalVGM);
					exportContainers.stream().findFirst().get().setTireWeight(String.valueOf(tireWeight));
					exportContainers.stream().findFirst().get().setFuelWeight(String.valueOf(fuelWeight));
					exportContainers.stream().findFirst().get().setVariance(String.valueOf(variance));
				});
			}else{
				exportContainers.forEach(container ->{
					int terminalVGM = container.getExpWeightBridge() - pmWeight -axelWeight - fuelWeight - tireWeight;
					container.setNetWeight(terminalVGM/2);
					container.setTireWeight(String.valueOf(tireWeight));
					container.setFuelWeight(String.valueOf(fuelWeight));
					container.setVariance(String.valueOf(variance));
				});
			}
		}else{
			throw new BusinessException("Solas Applicable only for Full Containers !");
		}

		return exportContainers;

	}
	

}
