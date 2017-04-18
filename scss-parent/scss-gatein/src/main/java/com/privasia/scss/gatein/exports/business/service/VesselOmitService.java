package com.privasia.scss.gatein.exports.business.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.VesselOmit;
import com.privasia.scss.core.model.VesselOmitPK;
import com.privasia.scss.core.repository.VesselOmitRepository;

/**
 * @author janakaw
 */
@Service("vesselOmitService")
public class VesselOmitService {

	private VesselOmitRepository vesselOmitRepository;

	@Autowired
	public void setVesselOmitRepository(VesselOmitRepository vesselOmitRepository) {
		this.vesselOmitRepository = vesselOmitRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<VesselOmit> getVesselOmit(String lineCode, String agentCode) {
		VesselOmitPK primaryKey = new VesselOmitPK();
		primaryKey.setAgent(StringUtils.trim(agentCode));
		primaryKey.setLine(StringUtils.trim(lineCode));
		Optional<VesselOmit> optVesselOmit = vesselOmitRepository.findOne(primaryKey);
		return optVesselOmit;
	}

	public boolean isValidVesselOmit(ExportContainer c) {

		if (StringUtils.isNotBlank(c.getShippingLine()) && StringUtils.isNotBlank(c.getShippingAgent())) {

			Optional<VesselOmit> optVesselOmit = getVesselOmit(c.getShippingLine(), c.getShippingAgent());

			if (optVesselOmit.isPresent()) {
				VesselOmit vesselOmit = optVesselOmit.get();
				if (StringUtils.isNotBlank(vesselOmit.getVesselVoyIN())) {
					if (StringUtils.contains(c.getVesselVoyageIN(), vesselOmit.getVesselVoyIN())) {

						throw new BusinessException("Container : " + c.getContainer().getContainerNumber()
								+ "Vessel for " + vesselOmit.getVesselOmitID().getLine() + " / "
								+ vesselOmit.getVesselOmitID().getAgent() + " is " + vesselOmit.getVesselVoyIN() + "!");
					}
				} else if (StringUtils.isNotBlank(vesselOmit.getVesselVoyOUT())) {
					if (StringUtils.contains(c.getVesselVoyageOUT(), vesselOmit.getVesselVoyOUT())) {
						throw new BusinessException("Container : " + c.getContainer().getContainerNumber()
								+ "Vessel for " + vesselOmit.getVesselOmitID().getLine() + " / "
								+ vesselOmit.getVesselOmitID().getAgent() + " is " + vesselOmit.getVesselVoyOUT()
								+ "!");
					}
				}
			}
		}

		return true;
	}

}