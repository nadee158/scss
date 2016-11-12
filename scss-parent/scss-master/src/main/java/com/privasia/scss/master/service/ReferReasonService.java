/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.ResultsNotFoundException;
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map<Boolean, Set<ReferReason>> findAllReferReason() throws ResultsNotFoundException {

    Stream<ReferReason> referReasonStream =
        referReasonRepository.findByReferStatus(RecordStatus.fromCode(RecordStatus.ACTIVE.getValue()));

    if (!(referReasonStream == null || referReasonStream.count() <= 0)) {

      Map<Boolean, Set<ReferReason>> map = referReasonStream.collect(Collectors.groupingBy(ReferReason::isParent,
          Collectors.mapping(ReferReason::getReferReason, Collectors.toSet())));

      // referReasonStream.forEach((ReferReason r ) ->System.out.println(r.getReferReasonID()));
      map.forEach((k, v) -> System.out.println("IsParent new: " + k + " Object new: " + v));

      return map;

    } else {
      throw new ResultsNotFoundException("No ReferReason Records were found!");
    }
  }

}
