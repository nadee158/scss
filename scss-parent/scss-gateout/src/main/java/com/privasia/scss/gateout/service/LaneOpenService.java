package com.privasia.scss.gateout.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.LaneOpenDTO;
import com.privasia.scss.common.enums.LaneOpenFlag;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.LaneOpen;
import com.privasia.scss.core.repository.LaneOpenRepository;

@Service("laneOpenService")
public class LaneOpenService {

  private LaneOpenRepository laneOpenRepository;

  private ModelMapper modelMapper;

  @Autowired
  public void setLaneOpenRepository(LaneOpenRepository laneOpenRepository) {
    this.laneOpenRepository = laneOpenRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
  public String updateOpenGate(Long openGateSEQ) {
    Optional<LaneOpen> laneOpenOptional = laneOpenRepository.findOne(openGateSEQ);

    LaneOpen laneOpen =
        laneOpenOptional.orElseThrow(() -> new ResultsNotFoundException("Invalid Gate Open SEQ ! " + openGateSEQ));
    laneOpen.setLaneOpenFlag(LaneOpenFlag.OPENED);
    laneOpenRepository.save(laneOpen);

    return "success";
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
  public LaneOpenDTO checkLaneOpenStatus(Long laneID) {
    Optional<LaneOpen> laneOpenOptional = laneOpenRepository.findByLaneID_ClientID(laneID);
    LaneOpen laneOpen = laneOpenOptional
        .orElseThrow(() -> new BusinessException("Lane opened could not be found for the client id " + laneID));
    LaneOpenDTO laneOpenDTO = new LaneOpenDTO();
    modelMapper.map(laneOpen, laneOpenDTO);
    return laneOpenDTO;
  }

}
