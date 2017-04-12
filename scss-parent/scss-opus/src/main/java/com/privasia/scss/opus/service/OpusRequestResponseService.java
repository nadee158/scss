/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.OpusRequestResponse;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.OpusRequestResponseRepository;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */
@Service("opusRequestResponseService")
public class OpusRequestResponseService {

  private static final Logger log = LoggerFactory.getLogger(OpusRequestResponseService.class);

  private OpusRequestResponseRepository opusRepository;

  private ModelMapper modelMapper;

  private CardRepository cardRepository;

  @Autowired
  public void setCardRepository(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Autowired
  public void setOpusRepository(OpusRequestResponseRepository opusRepository) {
    this.opusRepository = opusRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public Future<Long> saveOpusRequest(OpusRequestResponseDTO opusRequestResponseDTO) {

    OpusRequestResponse opusRequestResponse = null;
    Optional<Card> cardOpt = cardRepository.findOne(opusRequestResponseDTO.getCardID());
    Card card = cardOpt.orElseThrow(
        () -> new ResultsNotFoundException("Invalid Scan Card ID ! " + opusRequestResponseDTO.getCardID()));
    try {
      opusRequestResponse = modelMapper.map(opusRequestResponseDTO, OpusRequestResponse.class);
      opusRequestResponse.setCard(card);
      opusRequestResponse.setSendTime(LocalDateTime.now());
      System.out.println("BEFORE SAVED opusRequestResponse " + opusRequestResponse);
      opusRequestResponse = opusRepository.save(opusRequestResponse);
      System.out.println("SAVED opusRequestResponse " + opusRequestResponse);
    } catch (Exception e) {
      log.error("Error Occured when update Opus Response " + opusRequestResponse);
      log.error(e.getMessage());
    }
    return new AsyncResult<Long>(opusRequestResponse.getOpusReqResID());
  }

  @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public void updateOpusResponse(OpusRequestResponseDTO opusRequestResponseDTO, Future<Long> future) throws InterruptedException, ExecutionException {
    
    Optional<OpusRequestResponse> OptOpus = opusRepository.findOne(future.get());
	if (OptOpus.isPresent()) {
		OpusRequestResponse opusRequestResponse = OptOpus.get();
		opusRequestResponse.setResponse(opusRequestResponseDTO.getResponse());
		opusRequestResponse.setReceivedTime(LocalDateTime.now());
        opusRepository.save(opusRequestResponse);
    }else{
    	log.info("OpusRequestResponse not found "+future.get());
    }
 
  }

}
