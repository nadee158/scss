package com.privasia.scss.gatein.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.model.VesselOmit;
import com.privasia.scss.core.model.VesselOmitPK;
import com.privasia.scss.core.repository.VesselOmitRepository;
import com.privasia.scss.gatein.dto.VesselOmitDto;

/**
 * @author nadee158 - this code was taken from GateInDAOImpl in to VesselOmit's own service
 */
@Service("vesselOmitService")
@Transactional
public class VesselOmitService {

  @Autowired
  private VesselOmitRepository vesselOmitRepository;

  public VesselOmitDto getVesselOmit(String lineCode, String agentCode) throws Exception {
    VesselOmitPK primaryKey = new VesselOmitPK();
    primaryKey.setAgent(StringUtils.trim(agentCode));
    primaryKey.setLine(StringUtils.trim(lineCode));
    // String sql = " SELECT LINE, AGENT, VESSEL_VOY_IN, VESSEL_VOY_OUT \n"//
    // + " FROM SCSS_VESSEL_OMIT " + " WHERE LINE = ? AND AGENT = ? ";
    Optional<VesselOmit> vesselOmit = vesselOmitRepository.findOne(primaryKey);
    return convertModelToDto(vesselOmit.orElse(null));
  }

  private VesselOmitDto convertModelToDto(VesselOmit vesselOmit) {
    if (!(vesselOmit == null)) {
      VesselOmitDto vesselOmitDto = new VesselOmitDto();
      vesselOmitDto.setLineCode(vesselOmit.getVesselOmitID().getLine());
      vesselOmitDto.setAgentCode(vesselOmit.getVesselOmitID().getAgent());
      vesselOmitDto.setVesselVoyIn(vesselOmit.getVesselVoyIN());
      vesselOmitDto.setVesselVoyOut(vesselOmit.getVesselVoyOUT());
      return vesselOmitDto;
    }
    return null;
  }

}
