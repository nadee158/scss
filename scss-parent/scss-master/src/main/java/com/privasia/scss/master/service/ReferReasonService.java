/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ReferReasonDTO;
import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.common.exception.ResultsNotFoundException;
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

  public List<ReferReasonDTO> findAllReferReason() throws ResultsNotFoundException {


    List<ReferReason> referReasonList =
        referReasonRepository.findByReferStatus(RecordStatus.fromCode(RecordStatus.ACTIVE.getValue()));

    if (!(referReasonList == null || referReasonList.isEmpty())) {
      List<ReferReasonDTO> dtoList = new ArrayList<ReferReasonDTO>();
      try {

        Stream<ReferReason> parentStream = referReasonList.stream();

        Map<ReferReason, List<ReferReason>> map = parentStream.filter(ch -> ch.getReferReason() != null)
            .collect(Collectors.groupingBy(ReferReason::getReferReason));


        if (!(map == null || map.isEmpty())) {
          map.forEach((key, val) -> {
            dtoList.add(constructReferReasonDTO(key, val));
          });
        }
        Stream<ReferReason> aloneStream = referReasonList.stream();

        List<ReferReason> aloneList =
            aloneStream.filter(r -> r.getReferReason() == null && r.isParent() == false).collect(Collectors.toList());
        if (!(aloneList == null || aloneList.isEmpty())) {
          aloneList.forEach(rfr -> {
            dtoList.add(constructReferReasonDTO(rfr, null));
          });
        }

        return dtoList;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      throw new ResultsNotFoundException("No ReferReason Records were found!");
    }
    throw new ResultsNotFoundException("No ReferReason Records were found!");

  }

  public ReferReasonDTO constructReferReasonDTO(ReferReason parent, List<ReferReason> childList) {
    ReferReasonDTO referReasonDTO = new ReferReasonDTO();
    referReasonDTO = constructDTO(referReasonDTO, parent);
    if (!(childList == null || childList.isEmpty())) {
      List<ReferReasonDTO> childrenList = new ArrayList<ReferReasonDTO>();
      childList.forEach(child -> {
        childrenList.add(constructDTO(new ReferReasonDTO(), child));
      });
      referReasonDTO.setChildList(childrenList);
    }
    return referReasonDTO;
  }

  private ReferReasonDTO constructDTO(ReferReasonDTO referReasonDTO, ReferReason parent) {

    referReasonDTO.setReferReasonID(parent.getReferReasonID());

    referReasonDTO.setReasonDescription(parent.getReasonDescription());

    referReasonDTO.setSortSEQ(parent.getSortSEQ());

    if (!(parent.getReferStatus() == null)) {
      referReasonDTO.setReferStatus(parent.getReferStatus().toString());
    }
    referReasonDTO.setParent(parent.isParent());
    return referReasonDTO;
  }


}
