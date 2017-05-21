package com.privasia.scss.core.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.core.model.ReferReject;

public interface ReferRejectRepository
    extends Repository<ReferReject, Long>, QueryDslPredicateExecutor<ReferReject> {

  public Optional<Page<ReferReject>> findByStatusCode(HpabReferStatus statusCode,
      Pageable pageRequest);

  public Optional<ReferReject> findOne(Long id);

  public ReferReject save(ReferReject persisted);
}
