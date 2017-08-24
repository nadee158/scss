package com.privasia.scss.gatein.express.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BookingDTO;
import com.privasia.scss.common.dto.BookingInfoDTO;
import com.privasia.scss.common.dto.HDBSBkgGridDTO;
import com.privasia.scss.common.dto.HpatDto;
import com.privasia.scss.common.dto.InProgressTrxDTO;
import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CompanyType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.hdbs.service.HDBSService;
import com.privasia.scss.hpat.service.HPABService;

/**
 * @author Nadeeshani Senevirathna
 *
 */
@Service("expressBookingService")
public class ExpressBookingService {

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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
	public BookingInfoDTO getBookingInfo(Long cardNo) {
		BookingInfoDTO dto = new BookingInfoDTO();

		Card card = commonCardService.getCardByCardNumber(cardNo);

		boolean isCardValid = commonCardService.checkIfValidCard(card);

		if (!isCardValid) {
			if (StringUtils.equals(card.getCardStatus().getValue(), CardStatus.BLACKLIST.getValue())) {
				throw new BusinessException(BookingInfoDTO.BLACKLISTED_CARD_MESSAGE);
			} else {
				throw new BusinessException(BookingInfoDTO.INVALID_CARD_MESSAGE);
			}
		}

		if (card.getCompany() == null || card.getCompany().getCompanyType() == null) {
			throw new BusinessException("Card compay or  card company type is not available for card no " + cardNo);
		}

		if (card.getSmartCardUser() == null) {
			throw new BusinessException("The Smart Card User is not available for card no " + cardNo);
		}

		if (!StringUtils.equalsIgnoreCase(card.getCompany().getCompanyType().getValue(),
				CompanyType.HAULAGE.getValue())) {
			throw new BusinessException("The Smart Card User is not a Haulage Driver");
		}

		InProgressTrxDTO inProgressTrxDTO = commonCardService.isTrxInProgress(card.getCardID());

		if (inProgressTrxDTO.isInProgress()) {
			throw new BusinessException(BookingInfoDTO.IMPEXP_NOT_COMPLETED_MESSAGE);
		}
		log.error("Seq:" + card.getCardID());

		SmartCardUser smartCardUser = card.getSmartCardUser();
		dto.setDriverName(smartCardUser.getPersonName());
		dto.setDriverPhoto(smartCardUser.getPhoto());
		dto.setCompanyName(card.getCompany().getCompanyName());

		List<String> bookingTypes = new ArrayList<String>();
		bookingTypes.add(BookingType.IMPORT.getValue());
		bookingTypes.add(BookingType.IMPORT_ITT.getValue());

		List<BookingDTO> bookingDTos = new ArrayList<BookingDTO>();

		List<HpatDto> hpats = null;
		try {
			hpats = hpabService.findEtpHpab4ImpAndExp(card.getCardID(), LocalDateTime.now(),
					bookingTypes);
			if (!(hpats == null || hpats.isEmpty())) {
				hpats.forEach(hpat -> {
					bookingDTos.add(new BookingDTO(hpat));
				});
			}
		} catch (ResultsNotFoundException re) {
			log.info("No HPAT Bookings were found");
		}

		HDBSBkgGridDTO gridDto = hdbsService.findHDBSForExpressLane(card);
		if (!(gridDto == null || gridDto.getHdbsBkgDetailGridDTOList() == null
				|| gridDto.getHdbsBkgDetailGridDTOList().isEmpty())) {
			gridDto.getHdbsBkgDetailGridDTOList().forEach(gridDtoItem -> {
				bookingDTos.add(new BookingDTO(gridDtoItem));
			});
		}

		if (bookingDTos.isEmpty()) {
			throw new BusinessException(BookingInfoDTO.NO_BOOKING_MESSAGE);
		}

		dto.setBookings(bookingDTos);
		dto.setMessageCode("OK");

		return dto;
	}

}
