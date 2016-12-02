package com.privasia.scss.express.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.express.dto.BookingInfoDTO;
import com.privasia.scss.hdbs.service.HDBSService;
import com.privasia.scss.hpat.service.HPATService;

/**
 * @author Nadeeshani Senevirathna
 *
 */
@Service("expressService")
@Transactional()
public class ExpressService {

  @Autowired
  private HPATService hpatService;

  @Autowired
  private HDBSService hdbsService;

  public BookingInfoDTO getBookingInfo(String cardNo) {
    // TODO Auto-generated method stub
    return null;
  }

}
