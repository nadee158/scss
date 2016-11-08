package com.privasia.scss.gatein.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.dto.Container;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.VesselOmit;
import com.privasia.scss.core.model.VesselOmitPK;
import com.privasia.scss.core.repository.VesselOmitRepository;

/**
 * @author janakaw
 */
@Service("vesselOmitService")
public class VesselOmitService {

  @Autowired
  private VesselOmitRepository vesselOmitRepository;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
  public VesselOmit getVesselOmit(String lineCode, String agentCode) {
    VesselOmitPK primaryKey = new VesselOmitPK();
    primaryKey.setAgent(StringUtils.trim(agentCode));
    primaryKey.setLine(StringUtils.trim(lineCode));
    Optional<VesselOmit> vesselOmit = vesselOmitRepository.findOne(primaryKey);
    return vesselOmit.orElseThrow(() -> new ResultsNotFoundException("VesselOmit not found for linecode / agentCode : "+lineCode+" / "+agentCode));
  }
  
  public boolean isValidVesselOmit(Container c){
	  
	  VesselOmit vesselOmit = getVesselOmit(c.getLine(), c.getAgentCode());
	  if (StringUtils.contains(c.getVesselVoyageIn(), vesselOmit.getVesselVoyIN())) {
		 //return business exception
		  /*returnmsg += MessageCode.format("ERR_MSG_081",
					new Object[] { f.getContainerNoC2(), vesselOmitDto.getLineCode(),
							vesselOmitDto.getAgentCode(), vesselOmitDto.getVesselVoyIn() })
					+ ReturnMsg.SEPARATOR;*/
	  }
	  
	  if (StringUtils.contains(c.getVesselVoyageOut(), vesselOmit.getVesselVoyOUT())) {
			 //return business exception
			  /*returnmsg += MessageCode.format("ERR_MSG_081",
					new Object[] { f.getContainerNoC2(), vesselOmitDto.getLineCode(),
					vesselOmitDto.getAgentCode(), vesselOmitDto.getVesselVoyOut() })
					+ ReturnMsg.SEPARATOR;*/
	  }
	  return true;
  }
  

 
}
