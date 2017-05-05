package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.core.model.ReferRejectDetail;

public interface ReferRejectDetailRepository extends BaseRepository<ReferRejectDetail, Long> {

  List<ReferRejectDetail> findByReferReject_StatusCodeOrderByReferReject_ReferDateTimeDesc(HpabReferStatus statusCode,
      Pageable pageRequest);

  public ReferRejectDetail findByReferRejectReferRejectID(Long referId);

  Optional<ReferRejectDetail> findByReferReject_ReferRejectIDAndContainerNoAndReferReject_StatusCode(Long referRejectID,
      String containerNo, HpabReferStatus statusCode);


}
