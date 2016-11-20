/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.List;
import java.util.Map;
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
  public Map<ReferReason, List<ReferReason>> findAllReferReason() throws ResultsNotFoundException {

    List<ReferReason> referReasonList =
        referReasonRepository.findByReferStatus(RecordStatus.fromCode(RecordStatus.ACTIVE.getValue()));
    System.out.println("referReasonList :" + referReasonList);
    if (!(referReasonList == null || referReasonList.isEmpty())) {
      try {

        Stream<ReferReason> parentStream = referReasonList.stream();

        Map<ReferReason, List<ReferReason>> map = parentStream.filter(ch -> ch.getReferReason() != null)
            .collect(Collectors.groupingBy(ReferReason::getReferReason));

        Stream<ReferReason> aloneStream = referReasonList.stream();

        List<ReferReason> aloneList =
            aloneStream.filter(r -> r.getReferReason() == null && r.isParent() == false).collect(Collectors.toList());
        if (!(aloneList == null || aloneList.isEmpty())) {
          aloneList.forEach(rfr -> {
            map.put(rfr, null);
          });
        }

        return map;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      throw new ResultsNotFoundException("No ReferReason Records were found!");
    }
    throw new ResultsNotFoundException("No ReferReason Records were found!");

  }

}
