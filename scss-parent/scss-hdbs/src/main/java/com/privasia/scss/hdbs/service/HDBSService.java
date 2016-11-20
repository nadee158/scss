/**
 * 
 */
package com.privasia.scss.hdbs.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;

/**
 * @author Janaka
 *
 */
@Service("hdbsService")
public class HDBSService {
	
	@Autowired
	private HDBSBookingDetailRepository hdbsBookingDetailRepository;
	
	public List<HDBSBkgDetail> findHDBSBookingDetailByIDList(List<String> bkgDetailIDList){
		
		Stream<HDBSBkgDetail> bkgDetails = hdbsBookingDetailRepository.findByHdbsBKGDetailIDIn(bkgDetailIDList);
		
		return bkgDetails.collect(Collectors.toList());
	}

}
