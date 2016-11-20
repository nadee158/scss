/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.ReferReason;
import com.privasia.scss.core.repository.ReferReasonRepository;

/**
 * @author Janaka
 *
 */
@Service("referReasonService")
public class ReferReasonService {


  @Autowired
  private ReferReasonRepository referReasonRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map<ReferReason, Set<ReferReason>> findAllReferReason() throws ResultsNotFoundException {

    Stream<ReferReason> referReasonStream =
        referReasonRepository.findByReferStatus(RecordStatus.fromCode(RecordStatus.ACTIVE.getValue()));
    
    
    
    
    
    return referReasonStream.filter(r -> r.isParent() == true).collect(Collectors.groupingBy(ReferReason::getParentReferReason,
                Collectors.mapping(ReferReason::getReferReasonID, Collectors.toSet())));
    
    

      // referReasonStream.forEach((ReferReason r ) ->System.out.println(r.getReferReasonID()));
      //map.forEach((k, v) -> System.out.println("IsParent new: " + k + " Object new: " + v));

      //return map;

    //} else {
      //throw new ResultsNotFoundException("No ReferReason Records were found!");
    //}
  }

}
