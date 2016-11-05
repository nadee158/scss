/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.ReferReason;
import com.privasia.scss.core.repository.ReferReasonRepository;
import com.privasia.scss.core.util.constant.RecordStatus;

/**
 * @author Janaka
 *
 */
@Service("referReasonService")
public class ReferReasonService {
	
	
	@Autowired
	private ReferReasonRepository referReasonRepository;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public void findAllReferReason(){
		
		Stream<ReferReason> referReasonStream = referReasonRepository.findByReferStatus(RecordStatus.fromCode(RecordStatus.ACTIVE.getValue()));
		
		referReasonStream.collect(Collectors.groupingBy(ReferReason::isParent))
			.forEach((k,v)->System.out.println("IsParent : " + k + " Object : " + v));
		
		//referReasonStream.forEach((ReferReason r ) ->System.out.println(r.getReferReasonID()));
		
		
	}

}
