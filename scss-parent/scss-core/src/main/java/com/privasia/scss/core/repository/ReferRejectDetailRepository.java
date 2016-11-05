package com.privasia.scss.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.privasia.scss.core.model.ReferRejectDetail;

public interface ReferRejectDetailRepository extends PagingAndSortingRepository<ReferRejectDetail, Long> {

  List<ReferRejectDetail> findByReferReject_StatusCodeOrderByReferReject_ReferDateTimeDesc(String statusCode,
      Pageable pageRequest);

  ReferRejectDetail findByReferReject_ReferRejectID(Long referId);


}
