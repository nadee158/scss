package com.privasia.scss.refer.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.repository.ReferRejectDetailRepository;
import com.privasia.scss.refer.dto.ReferRejectListDto;

@Service("referRejectService")
public class ReferRejectService {

  @Autowired
  private ReferRejectDetailRepository referRejectDetailRepository;

  @Transactional()
  public List<ReferRejectListDto> getReferRejectList(String statusCode, int page, int pageSize) {
    PageRequest pageRequest = new PageRequest(page, pageSize);

    List<ReferRejectDetail> list = referRejectDetailRepository
        .findByReferReject_StatusCodeOrderByReferReject_ReferDateTimeDesc(statusCode, pageRequest);
    if (!(list == null || list.isEmpty())) {
      List<ReferRejectListDto> dtoList = new ArrayList<ReferRejectListDto>();
      list.forEach(referRejectDetail -> {
        dtoList.add(new ReferRejectListDto(referRejectDetail));
      });
      return dtoList;
    } else {
      throw new ResultsNotFoundException("No refer rejects were found!");
    }
  }

  public ReferRejectListDto getReferRejectByReferId(long referId) {
    ReferRejectDetail referRejectDetail = referRejectDetailRepository.findByReferReject_ReferRejectID(referId);
    if (!(referRejectDetail == null)) {
      return new ReferRejectListDto(referRejectDetail);
    } else {
      throw new ResultsNotFoundException("Refer reject was not found!");
    }
  }


}
