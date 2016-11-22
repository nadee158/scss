package com.privasia.scss.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.model.ReferRejectDetail;

public interface ReferRejectDetailRepository extends BaseRepository<ReferRejectDetail, Long> {

  List<ReferRejectDetail> findByReferReject_StatusCodeOrderByReferReject_ReferDateTimeDesc(HpatReferStatus statusCode,
      Pageable pageRequest);

  public ReferRejectDetail findByReferRejectReferRejectID(Long referId);

  ReferRejectDetail findByReferReject_ReferRejectIDAndContainerNo(Long referRejectID, String containerNo);


}
