package com.privasia.scss.express.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.BookingDTO;
import com.privasia.scss.common.dto.BookingInfoDTO;
import com.privasia.scss.common.dto.HDBSBkgGridDTO;
import com.privasia.scss.common.dto.HpatDto;
import com.privasia.scss.common.dto.InProgressTrxDTO;
import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CompanyType;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.hdbs.service.HDBSService;
import com.privasia.scss.hpat.service.HPABService;

/**
 * @author Nadeeshani Senevirathna
 *
 */
@Service("expressService")
@Transactional()
public class ExpressService {

	private Logger log = Logger.getLogger(this.getClass()); 

	private HPABService hpabService;

	private HDBSService hdbsService;

	private CommonCardService commonCardService;
	
	@Autowired
	public void setHpabService(HPABService hpabService) {
		this.hpabService = hpabService;
	}
	
	@Autowired
	public void setHdbsService(HDBSService hdbsService) {
		this.hdbsService = hdbsService;
	}
	
	@Autowired
	public void setCommonCardService(CommonCardService commonCardService) {
		this.commonCardService = commonCardService;
	}

	public BookingInfoDTO getBookingInfo(String cardNo) {
		BookingInfoDTO dto = new BookingInfoDTO();
		try {
			Card card = commonCardService.getCardByCardNumber(Long.parseLong(cardNo));
			boolean isCardValid = commonCardService.checkIfValidCard(card);
			if (isCardValid) {
				if (!(card.getCompany() == null || card.getCompany().getCompanyType() == null)) {
					if (StringUtils.equals(card.getCompany().getCompanyType().getValue(),
							CompanyType.HAULAGE.getValue())) {
						InProgressTrxDTO inProgressTrxDTO = commonCardService.isTrxInProgress(card.getCardID());
						if (!inProgressTrxDTO.isInProgress()) {
							log.error("Seq:" + card.getCardID());

							if (!(card.getSmartCardUser() == null || card.getCompany() == null)) {

								SmartCardUser smartCardUser = card.getSmartCardUser();
								dto.setDriverName(smartCardUser.getPersonName());
								dto.setDriverPhoto(smartCardUser.getPhoto());
								dto.setCompanyName(card.getCompany().getCompanyName());

								List<String> bookingTypes = new ArrayList<String>();
								bookingTypes.add(BookingType.IMPORT.getValue());
								bookingTypes.add(BookingType.IMPORT_ITT.getValue());

								List<BookingDTO> bookingDTos = new ArrayList<BookingDTO>();

								List<HpatDto> hpats = hpabService.findEtpHpab4ImpAndExp(card.getCardID(),
										LocalDateTime.now(), bookingTypes);
								if (!(hpats == null || hpats.isEmpty())) {
									hpats.forEach(hpat -> {
										bookingDTos.add(new BookingDTO(hpat));
									});
								}

								HDBSBkgGridDTO gridDto = hdbsService.createPredicatesAndFindHDBS(card.getCardNo());
								if (!(gridDto == null || gridDto.getHdbsBkgDetailGridDTOList() == null
										|| gridDto.getHdbsBkgDetailGridDTOList().isEmpty())) {
									gridDto.getHdbsBkgDetailGridDTOList().forEach(gridDtoItem -> {
										bookingDTos.add(new BookingDTO(gridDtoItem));
									});
								}

								if (!(bookingDTos.isEmpty())) {

									dto.setBookings(bookingDTos);
									dto.setMessageCode(BookingInfoDTO.OK);
									dto.setMessageDesc("");

								} else {

									dto.setMessageCode(BookingInfoDTO.NOK);
									dto.setMessageDesc(BookingInfoDTO.NO_BOOKING_MESSAGE);
								}

							} else {
								dto.setMessageCode(BookingInfoDTO.NOK);
								dto.setMessageDesc("Smart card user or company is not available!");
							}
						} else {
							dto.setMessageCode(BookingInfoDTO.NOK);
							dto.setMessageDesc(BookingInfoDTO.IMPEXP_NOT_COMPLETED_MESSAGE);
						}
					} else {
						dto.setMessageCode(BookingInfoDTO.NOK);
						dto.setMessageDesc(CommonUtil.getMessageCode("ERR_MSG_066"));
					}
				} else {
					dto.setMessageCode(BookingInfoDTO.NOK);
					dto.setMessageDesc(CommonUtil.getMessageCode("ERR_MSG_066"));
				}
			} else {
				if (StringUtils.equals(card.getCardStatus().getValue(), CardStatus.BLACKLIST.getValue())) {
					dto.setMessageCode(BookingInfoDTO.NOK);
					dto.setMessageDesc(BookingInfoDTO.BLACKLISTED_CARD_MESSAGE);
				} else {
					dto.setMessageCode(BookingInfoDTO.NOK);
					dto.setMessageDesc(BookingInfoDTO.INVALID_CARD_MESSAGE);
				}
			}
		} catch (ResultsNotFoundException e) {
			dto.setMessageCode(BookingInfoDTO.NOK);
			dto.setMessageDesc(BookingInfoDTO.CARD_NOT_FOUND_MESSAGE);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return dto;
	}

}
