package com.privasia.scss.core.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.privasia.scss.core.model.HDBSBkgDetail;

public interface HDBSBookingDetailRepository extends BaseRepository<HDBSBkgDetail, String>, QueryDslPredicateExecutor<HDBSBkgDetail> {

	
	public Stream<HDBSBkgDetail> findByHdbsBKGDetailIDIn(List<String> bkgDetailIDList);
}
