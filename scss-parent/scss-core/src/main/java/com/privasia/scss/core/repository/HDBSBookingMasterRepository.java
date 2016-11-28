package com.privasia.scss.core.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.privasia.scss.core.model.HDBSBkgMaster;

public interface HDBSBookingMasterRepository extends BaseRepository<HDBSBkgMaster, String>, QueryDslPredicateExecutor<HDBSBkgMaster> {

	
	public Stream<HDBSBkgMaster> findByHdbsBKGMasterIDIn(List<String> bkgMasterIDList);
	
}
