/**
 * 
 */
package com.privasia.scss.opus.service;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */
@Service("opusRequestResponseService")
public class OpusRequestResponseService {
	
	@Async
	public Future<Long> saveOpusRequest(OpusRequestResponseDTO opusRequestResponseDTO){
		
		return new AsyncResult<Long>(1l);
	}
	
	@Async
	public void updateOpusResponse(OpusRequestResponseDTO opusRequestResponseDTO){
		
	}

}
